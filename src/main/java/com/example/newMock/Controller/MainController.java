package com.example.newMock.Controller;

import com.example.newMock.Model.RequestDTO;
import com.example.newMock.Model.ResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.sql.SQLOutput;
import java.util.Random;

@RestController
public class MainController {
    private Logger log = LoggerFactory.getLogger(MainController.class);

    private ObjectMapper mapper = new ObjectMapper();

    @PostMapping(
            value = "/info/postBalances",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE

    )
    public Object postBalances(@RequestBody RequestDTO requestDTO){
        try{
            String clientId = requestDTO.getClientId();
            char firstDigit = clientId.charAt(0);
            String currency;
            int maxLimit;
            String rqUID = requestDTO.getRqUID();

            if(firstDigit == '8'){
                maxLimit = 2000;
                currency = "US";
            } else if (firstDigit == '9') {
                maxLimit = 1000;
                currency = "EU";
            } else {
                maxLimit = 10000;
                currency = "RUB";
            }

            ResponseDTO responseDTO = new ResponseDTO();
            Random random = new Random();

            responseDTO.setRqUID(rqUID);
            responseDTO.setClientId(clientId);
            responseDTO.setAccount(requestDTO.getAccount());
            responseDTO.setCurrency(currency);
            responseDTO.setBalance(random.nextInt(maxLimit+1));
            responseDTO.setMaxLimit(maxLimit);

            log.error("********* RequestDTO *********" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(requestDTO));
            log.error("********* ResponseDTO *********" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseDTO));


            return responseDTO;
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }


    }
}

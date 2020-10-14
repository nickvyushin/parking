package com.example.parking.controller;

import com.example.parking.model.Parking;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class ApiController {

    ConcurrentHashMap<Integer, String> parkingMap = new ConcurrentHashMap<>();
    public ApiController() {
        for (int i = 1; i < 21; i++) {
            parkingMap.put(i, "");
        }
    }

    @PostMapping("/park")
    public boolean park(@RequestBody Parking parking) {
        String s = parkingMap.get(parking.getPlaceNumber());
        if (s.equals("")) {
            parkingMap.put(parking.getPlaceNumber(), parking.getCarNumber());
            return true;
        } else {
            System.out.println("Место зянято!");
            return false;
        }
    }

    @PostMapping("/unpark")
    public boolean unpark(@RequestBody Parking parking) {
        int placeNumber = parking.getPlaceNumber();
        if (parkingMap.containsKey(placeNumber) && parkingMap.containsValue(parking.getCarNumber())) {
            parkingMap.remove(placeNumber);
            return true;
        }
        return false;
    }

    @GetMapping("/free")
    public List<Integer> free() {
        List<Integer> list = new ArrayList<>();

        for (Map.Entry<Integer, String> entry : parkingMap.entrySet()) {
            if (entry.getValue().equals(""))
                list.add(entry.getKey());
        }
        return list;
    }
}

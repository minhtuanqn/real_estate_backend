package com.realestatebackend.service;

import com.realestatebackend.customexception.DuplicatedEntityException;
import com.realestatebackend.entity.LocationEntity;
import com.realestatebackend.model.LocationModel;
import com.realestatebackend.repository.LocationRepository;
import org.springframework.stereotype.Service;

@Service
public class LocationService {

    private LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository)
    {
        this.locationRepository = locationRepository;
    }

    /**
     * create a location
     * @param model
     * @return created model
     */
    public LocationModel createLocation(LocationModel model)
    {
        //Check existed location
        if(locationRepository.existsLocationEntitiesByProvinceAndAndDistrictAndWard(model.getProvince(), model.getDistrict(), model.getWard())) {
            throw new DuplicatedEntityException("This location has been existed");
        }
        //Set id for model is null
        if(model.getId() != null) {
            model.setId(null);
        }

        //Prepare entity
        LocationEntity entity = new LocationEntity(model);

        //Save entity to DB
        LocationEntity savedEntity = locationRepository.save(entity);
        model = new LocationModel(savedEntity);

        return model;
    }
}
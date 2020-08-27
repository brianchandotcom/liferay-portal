/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.headless.commerce.admin.site.setting.internal.mapper.v1_0;

import com.liferay.commerce.inventory.model.CommerceInventoryWarehouse;
import com.liferay.commerce.model.CommerceAvailabilityEstimate;
import com.liferay.commerce.product.model.CPMeasurementUnit;
import com.liferay.commerce.product.model.CPTaxCategory;
import com.liferay.headless.commerce.admin.site.setting.dto.v1_0.AvailabilityEstimate;
import com.liferay.headless.commerce.admin.site.setting.dto.v1_0.MeasurementUnit;
import com.liferay.headless.commerce.admin.site.setting.dto.v1_0.TaxCategory;
import com.liferay.headless.commerce.admin.site.setting.dto.v1_0.Warehouse;
import com.liferay.headless.commerce.core.util.LanguageUtils;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alessio Antonio Rendina
 * @author Zoltán Takács
 */
@Component(immediate = true, service = DTOMapper.class)
public class DTOMapper {

	public AvailabilityEstimate modelToDTO(
		CommerceAvailabilityEstimate commerceAvailabilityEstimate) {

		AvailabilityEstimate availabilityEstimate = new AvailabilityEstimate();

		if (commerceAvailabilityEstimate == null) {
			return availabilityEstimate;
		}

		availabilityEstimate.setId(
			commerceAvailabilityEstimate.getCommerceAvailabilityEstimateId());
		availabilityEstimate.setPriority(
			commerceAvailabilityEstimate.getPriority());
		availabilityEstimate.setTitle(
			LanguageUtils.getLanguageIdMap(
				commerceAvailabilityEstimate.getTitleMap()));

		return availabilityEstimate;
	}

	public Warehouse modelToDTO(
		CommerceInventoryWarehouse commerceInventoryWarehouse) {

		Warehouse warehouse = new Warehouse();

		if (commerceInventoryWarehouse == null) {
			return warehouse;
		}

		warehouse.setActive(commerceInventoryWarehouse.isActive());
		warehouse.setCity(commerceInventoryWarehouse.getCity());
		warehouse.setDescription(commerceInventoryWarehouse.getDescription());
		warehouse.setId(
			commerceInventoryWarehouse.getCommerceInventoryWarehouseId());
		warehouse.setLatitude(commerceInventoryWarehouse.getLatitude());
		warehouse.setLongitude(commerceInventoryWarehouse.getLongitude());
		warehouse.setName(commerceInventoryWarehouse.getName());
		warehouse.setStreet1(commerceInventoryWarehouse.getStreet1());
		warehouse.setStreet2(commerceInventoryWarehouse.getStreet2());
		warehouse.setStreet3(commerceInventoryWarehouse.getStreet3());
		warehouse.setZip(commerceInventoryWarehouse.getZip());

		return warehouse;
	}

	public MeasurementUnit modelToDTO(CPMeasurementUnit cpMeasurementUnit) {
		MeasurementUnit measurementUnit = new MeasurementUnit();

		if (cpMeasurementUnit == null) {
			return measurementUnit;
		}

		measurementUnit.setGroupId(cpMeasurementUnit.getGroupId());
		measurementUnit.setId(cpMeasurementUnit.getCPMeasurementUnitId());
		measurementUnit.setKey(cpMeasurementUnit.getKey());
		measurementUnit.setName(
			LanguageUtils.getLanguageIdMap(cpMeasurementUnit.getNameMap()));
		measurementUnit.setPrimary(cpMeasurementUnit.isPrimary());
		measurementUnit.setPriority(cpMeasurementUnit.getPriority());
		measurementUnit.setRate(cpMeasurementUnit.getRate());
		measurementUnit.setType(cpMeasurementUnit.getType());

		return measurementUnit;
	}

	public TaxCategory modelToDTO(CPTaxCategory cpTaxCategory) {
		TaxCategory taxCategory = new TaxCategory();

		if (cpTaxCategory == null) {
			return taxCategory;
		}

		taxCategory.setDescription(
			LanguageUtils.getLanguageIdMap(cpTaxCategory.getDescriptionMap()));
		taxCategory.setId(cpTaxCategory.getCPTaxCategoryId());
		taxCategory.setName(
			LanguageUtils.getLanguageIdMap(cpTaxCategory.getNameMap()));

		return taxCategory;
	}

}
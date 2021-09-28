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

package com.liferay.headless.commerce.admin.inventory.internal.dto.v1_0;

import com.liferay.commerce.inventory.model.CommerceInventoryWarehouse;
import com.liferay.commerce.inventory.service.CommerceInventoryWarehouseService;
import com.liferay.headless.commerce.admin.inventory.dto.v1_0.Warehouse;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	property = "dto.class.name=com.liferay.commerce.inventory.model.CommerceInventoryWarehouse",
	service = {DTOConverter.class, WarehouseDTOConverter.class}
)
public class WarehouseDTOConverter
	implements DTOConverter<CommerceInventoryWarehouse, Warehouse> {

	@Override
	public String getContentType() {
		return Warehouse.class.getSimpleName();
	}

	@Override
	public Warehouse toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		CommerceInventoryWarehouse commerceInventoryWarehouse =
			_commerceInventoryWarehouseService.getCommerceInventoryWarehouse(
				(Long)dtoConverterContext.getId());

		return new Warehouse() {
			{
				active = commerceInventoryWarehouse.isActive();
				city = commerceInventoryWarehouse.getCity();
				countryISOCode =
					commerceInventoryWarehouse.getCountryTwoLettersISOCode();
				description = commerceInventoryWarehouse.getDescription();
				externalReferenceCode =
					commerceInventoryWarehouse.getExternalReferenceCode();
				id =
					commerceInventoryWarehouse.
						getCommerceInventoryWarehouseId();
				latitude = commerceInventoryWarehouse.getLatitude();
				longitude = commerceInventoryWarehouse.getLongitude();
				mvccVersion = commerceInventoryWarehouse.getMvccVersion();
				name = commerceInventoryWarehouse.getName();
				regionISOCode =
					commerceInventoryWarehouse.getCommerceRegionCode();
				street1 = commerceInventoryWarehouse.getStreet1();
				street2 = commerceInventoryWarehouse.getStreet2();
				street3 = commerceInventoryWarehouse.getStreet3();
				type = commerceInventoryWarehouse.getType();
				zip = commerceInventoryWarehouse.getZip();
			}
		};
	}

	@Reference
	private CommerceInventoryWarehouseService
		_commerceInventoryWarehouseService;

}
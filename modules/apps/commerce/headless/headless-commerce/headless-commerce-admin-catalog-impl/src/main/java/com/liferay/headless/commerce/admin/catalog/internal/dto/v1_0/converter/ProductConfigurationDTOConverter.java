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

package com.liferay.headless.commerce.admin.catalog.internal.dto.v1_0.converter;

import com.liferay.commerce.model.CPDefinitionInventory;
import com.liferay.commerce.service.CPDefinitionInventoryService;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.ProductConfiguration;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	property = "dto.class.name=com.liferay.commerce.model.CPDefinitionInventory",
	service = {DTOConverter.class, ProductConfigurationDTOConverter.class}
)
public class ProductConfigurationDTOConverter
	implements DTOConverter<CPDefinitionInventory, ProductConfiguration> {

	@Override
	public String getContentType() {
		return ProductConfiguration.class.getSimpleName();
	}

	@Override
	public ProductConfiguration toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		CPDefinitionInventory cpDefinitionInventory =
			_cpDefinitionInventoryService.
				fetchCPDefinitionInventoryByCPDefinitionId(
					(Long)dtoConverterContext.getId());

		if (cpDefinitionInventory == null) {
			return new ProductConfiguration();
		}

		return new ProductConfiguration() {
			{
				allowBackOrder = cpDefinitionInventory.isBackOrders();
				allowedOrderQuantities = ArrayUtil.toArray(
					cpDefinitionInventory.getAllowedOrderQuantitiesArray());
				inventoryEngine =
					cpDefinitionInventory.getCPDefinitionInventoryEngine();
				maxOrderQuantity = cpDefinitionInventory.getMaxOrderQuantity();
				minOrderQuantity = cpDefinitionInventory.getMinOrderQuantity();
				multipleOrderQuantity =
					cpDefinitionInventory.getMultipleOrderQuantity();
			}
		};
	}

	@Reference
	private CPDefinitionInventoryService _cpDefinitionInventoryService;

}
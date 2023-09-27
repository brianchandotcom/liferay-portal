/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.catalog.internal.dto.v1_0.converter;

import com.liferay.commerce.model.CPDefinitionInventory;
import com.liferay.commerce.service.CPDefinitionInventoryService;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.ProductConfiguration;
import com.liferay.portal.kernel.util.BigDecimalUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = "dto.class.name=com.liferay.commerce.model.CPDefinitionInventory",
	service = DTOConverter.class
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
				allowedOrderQuantities =
					cpDefinitionInventory.getAllowedOrderQuantitiesArray();
				inventoryEngine =
					cpDefinitionInventory.getCPDefinitionInventoryEngine();
				maxOrderQuantity = BigDecimalUtil.stripTrailingZeros(
					cpDefinitionInventory.getMaxOrderQuantity());
				minOrderQuantity = BigDecimalUtil.stripTrailingZeros(
					cpDefinitionInventory.getMinOrderQuantity());
				multipleOrderQuantity = BigDecimalUtil.stripTrailingZeros(
					cpDefinitionInventory.getMultipleOrderQuantity());
			}
		};
	}

	@Reference
	private CPDefinitionInventoryService _cpDefinitionInventoryService;

}
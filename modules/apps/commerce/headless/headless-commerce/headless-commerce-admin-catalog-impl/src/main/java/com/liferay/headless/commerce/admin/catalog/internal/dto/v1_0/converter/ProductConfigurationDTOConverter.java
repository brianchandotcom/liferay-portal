/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.catalog.internal.dto.v1_0.converter;

import com.liferay.commerce.model.CPDefinitionInventory;
import com.liferay.commerce.service.CPDefinitionInventoryService;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.ProductConfiguration;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import java.math.BigDecimal;

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
				allowedOrderQuantities = TransformUtil.transformToArray(
					ListUtil.fromArray(
						cpDefinitionInventory.getAllowedOrderQuantitiesArray()),
					BigDecimal::intValue, Integer.class);
				inventoryEngine =
					cpDefinitionInventory.getCPDefinitionInventoryEngine();

				setMaxOrderQuantity(
					() -> {
						BigDecimal maxOrderQuantity =
							cpDefinitionInventory.getMaxOrderQuantity();

						return maxOrderQuantity.intValue();
					});
				setMinOrderQuantity(
					() -> {
						BigDecimal minOrderQuantity =
							cpDefinitionInventory.getMinOrderQuantity();

						return minOrderQuantity.intValue();
					});
				setMultipleOrderQuantity(
					() -> {
						BigDecimal multipleOrderQuantity =
							cpDefinitionInventory.getMultipleOrderQuantity();

						return multipleOrderQuantity.intValue();
					});
			}
		};
	}

	@Reference
	private CPDefinitionInventoryService _cpDefinitionInventoryService;

}
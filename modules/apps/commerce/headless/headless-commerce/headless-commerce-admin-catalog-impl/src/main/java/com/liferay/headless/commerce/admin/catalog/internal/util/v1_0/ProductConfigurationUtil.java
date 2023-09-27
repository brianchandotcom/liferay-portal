/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.catalog.internal.util.v1_0;

import com.liferay.commerce.model.CPDefinitionInventory;
import com.liferay.commerce.service.CPDefinitionInventoryService;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.ProductConfiguration;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.BigDecimalUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Alessio Antonio Rendina
 * @author Igor Beslic
 */
public class ProductConfigurationUtil {

	public static CPDefinitionInventory updateCPDefinitionInventory(
			CPDefinitionInventoryService cpDefinitionInventoryService,
			ProductConfiguration productConfiguration, long cpDefinitionId)
		throws PortalException {

		CPDefinitionInventory cpDefinitionInventory =
			cpDefinitionInventoryService.
				fetchCPDefinitionInventoryByCPDefinitionId(cpDefinitionId);

		return cpDefinitionInventoryService.updateCPDefinitionInventory(
			cpDefinitionInventory.getCPDefinitionInventoryId(),
			GetterUtil.get(
				productConfiguration.getInventoryEngine(),
				cpDefinitionInventory.getCPDefinitionInventoryEngine()),
			productConfiguration.getLowStockAction(),
			GetterUtil.get(
				productConfiguration.getDisplayAvailability(),
				cpDefinitionInventory.isDisplayAvailability()),
			GetterUtil.get(
				productConfiguration.getDisplayStockQuantity(),
				cpDefinitionInventory.isDisplayStockQuantity()),
			BigDecimalUtil.get(
				productConfiguration.getMinStockQuantity(),
				cpDefinitionInventory.getMinStockQuantity()),
			GetterUtil.get(
				productConfiguration.getAllowBackOrder(),
				cpDefinitionInventory.isBackOrders()),
			BigDecimalUtil.get(
				productConfiguration.getMinOrderQuantity(),
				cpDefinitionInventory.getMinOrderQuantity()),
			BigDecimalUtil.get(
				productConfiguration.getMaxOrderQuantity(),
				cpDefinitionInventory.getMaxOrderQuantity()),
			_getAllowedOrderQuantities(
				cpDefinitionInventory, productConfiguration),
			BigDecimalUtil.get(
				productConfiguration.getMultipleOrderQuantity(),
				cpDefinitionInventory.getMultipleOrderQuantity()));
	}

	private static String _getAllowedOrderQuantities(
		CPDefinitionInventory cpDefinitionInventory,
		ProductConfiguration productConfiguration) {

		if (productConfiguration.getAllowedOrderQuantities() != null) {
			return StringUtil.merge(
				productConfiguration.getAllowedOrderQuantities());
		}

		if (cpDefinitionInventory == null) {
			return null;
		}

		return cpDefinitionInventory.getAllowedOrderQuantities();
	}

}
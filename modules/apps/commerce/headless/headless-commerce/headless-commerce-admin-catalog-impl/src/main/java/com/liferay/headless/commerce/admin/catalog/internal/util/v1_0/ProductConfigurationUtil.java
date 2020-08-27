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

package com.liferay.headless.commerce.admin.catalog.internal.util.v1_0;

import com.liferay.commerce.constants.CPDefinitionInventoryConstants;
import com.liferay.commerce.model.CPDefinitionInventory;
import com.liferay.commerce.service.CPDefinitionInventoryService;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.ProductConfiguration;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Alessio Antonio Rendina
 * @author Igor Beslic
 */
public class ProductConfigurationUtil {

	public static CPDefinitionInventory updateCPDefinitionInventory(
			long groupId,
			CPDefinitionInventoryService cpDefinitionInventoryService,
			ProductConfiguration productConfiguration, long cpDefinitionId)
		throws PortalException {

		CPDefinitionInventory cpDefinitionInventory =
			cpDefinitionInventoryService.
				fetchCPDefinitionInventoryByCPDefinitionId(cpDefinitionId);

		if (cpDefinitionInventory == null) {
			cpDefinitionInventory =
				cpDefinitionInventoryService.addCPDefinitionInventory(
					0, cpDefinitionId,
					productConfiguration.getInventoryEngine(),
					productConfiguration.getLowStockAction(),
					GetterUtil.get(
						productConfiguration.getDisplayAvailability(), false),
					GetterUtil.get(
						productConfiguration.getDisplayStockQuantity(), false),
					GetterUtil.get(
						productConfiguration.getMinStockQuantity(), 0),
					GetterUtil.get(
						productConfiguration.getAllowBackOrder(), false),
					GetterUtil.get(
						productConfiguration.getMinOrderQuantity(),
						CPDefinitionInventoryConstants.
							DEFAULT_MIN_ORDER_QUANTITY),
					GetterUtil.get(
						productConfiguration.getMaxOrderQuantity(),
						CPDefinitionInventoryConstants.
							DEFAULT_MAX_ORDER_QUANTITY),
					_getAllowedOrderQuantities(
						cpDefinitionInventory, productConfiguration),
					GetterUtil.get(
						productConfiguration.getMultipleOrderQuantity(),
						CPDefinitionInventoryConstants.
							DEFAULT_MULTIPLE_ORDER_QUANTITY));
		}
		else {
			cpDefinitionInventory =
				cpDefinitionInventoryService.updateCPDefinitionInventory(
					groupId, cpDefinitionInventory.getCPDefinitionInventoryId(),
					GetterUtil.get(
						productConfiguration.getInventoryEngine(),
						cpDefinitionInventory.getCPDefinitionInventoryEngine()),
					GetterUtil.get(
						productConfiguration.getInventoryEngine(),
						cpDefinitionInventory.getCPDefinitionInventoryEngine()),
					GetterUtil.get(
						productConfiguration.getDisplayAvailability(),
						cpDefinitionInventory.isDisplayAvailability()),
					GetterUtil.get(
						productConfiguration.getDisplayStockQuantity(),
						cpDefinitionInventory.isDisplayStockQuantity()),
					GetterUtil.get(
						productConfiguration.getMinStockQuantity(),
						cpDefinitionInventory.getMinStockQuantity()),
					GetterUtil.get(
						productConfiguration.getAllowBackOrder(),
						cpDefinitionInventory.isBackOrders()),
					GetterUtil.get(
						productConfiguration.getMinOrderQuantity(),
						cpDefinitionInventory.getMinOrderQuantity()),
					GetterUtil.get(
						productConfiguration.getMaxOrderQuantity(),
						cpDefinitionInventory.getMaxOrderQuantity()),
					_getAllowedOrderQuantities(
						cpDefinitionInventory, productConfiguration),
					GetterUtil.get(
						productConfiguration.getMultipleOrderQuantity(),
						cpDefinitionInventory.getMultipleOrderQuantity()));
		}

		return cpDefinitionInventory;
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
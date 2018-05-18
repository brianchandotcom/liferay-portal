/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.service.impl;

import com.liferay.commerce.constants.CommerceActionKeys;
import com.liferay.commerce.model.CPDefinitionInventory;
import com.liferay.commerce.service.base.CPDefinitionInventoryServiceBaseImpl;
import com.liferay.commerce.service.permission.CommercePermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;

/**
 * @author Alessio Antonio Rendina
 */
public class CPDefinitionInventoryServiceImpl
	extends CPDefinitionInventoryServiceBaseImpl {

	@Override
	public CPDefinitionInventory addCPDefinitionInventory(
			long cpDefinitionId, String cpDefinitionInventoryEngine,
			String lowStockActivity, boolean displayAvailability,
			boolean displayStockQuantity, int minStockQuantity,
			boolean backOrders, int minOrderQuantity, int maxOrderQuantity,
			String allowedOrderQuantities, int multipleOrderQuantity,
			ServiceContext serviceContext)
		throws PortalException {

		CommercePermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			CommerceActionKeys.MANAGE_COMMERCE_PRODUCT_DEFINITION_INVENTORY);

		return cpDefinitionInventoryLocalService.addCPDefinitionInventory(
			cpDefinitionId, cpDefinitionInventoryEngine, lowStockActivity,
			displayAvailability, displayStockQuantity, minStockQuantity,
			backOrders, minOrderQuantity, maxOrderQuantity,
			allowedOrderQuantities, multipleOrderQuantity, serviceContext);
	}

	@Override
	public void deleteCPDefinitionInventory(long cpDefinitionInventoryId)
		throws PortalException {

		CPDefinitionInventory cpDefinitionInventory =
			cpDefinitionInventoryPersistence.findByPrimaryKey(
				cpDefinitionInventoryId);

		CommercePermission.check(
			getPermissionChecker(), cpDefinitionInventory.getGroupId(),
			CommerceActionKeys.MANAGE_COMMERCE_PRODUCT_DEFINITION_INVENTORY);

		cpDefinitionInventoryLocalService.deleteCPDefinitionInventory(
			cpDefinitionInventoryId);
	}

	@Override
	public CPDefinitionInventory fetchCPDefinitionInventory(
		long cpDefinitionInventoryId) {

		return cpDefinitionInventoryLocalService.fetchCPDefinitionInventory(
			cpDefinitionInventoryId);
	}

	@Override
	public CPDefinitionInventory fetchCPDefinitionInventoryByCPDefinitionId(
			long cpDefinitionId)
		throws PortalException {

		return cpDefinitionInventoryLocalService.
			fetchCPDefinitionInventoryByCPDefinitionId(cpDefinitionId);
	}

	@Override
	public CPDefinitionInventory updateCPDefinitionInventory(
			long cpDefinitionInventoryId, String cpDefinitionInventoryEngine,
			String lowStockActivity, boolean displayAvailability,
			boolean displayStockQuantity, int minStockQuantity,
			boolean backOrders, int minOrderQuantity, int maxOrderQuantity,
			String allowedOrderQuantities, int multipleOrderQuantity,
			ServiceContext serviceContext)
		throws PortalException {

		CommercePermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			CommerceActionKeys.MANAGE_COMMERCE_PRODUCT_DEFINITION_INVENTORY);

		return cpDefinitionInventoryLocalService.updateCPDefinitionInventory(
			cpDefinitionInventoryId, cpDefinitionInventoryEngine,
			lowStockActivity, displayAvailability, displayStockQuantity,
			minStockQuantity, backOrders, minOrderQuantity, maxOrderQuantity,
			allowedOrderQuantities, multipleOrderQuantity, serviceContext);
	}

}
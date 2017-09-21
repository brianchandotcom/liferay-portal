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

package com.liferay.commerce.service.impl;

import com.liferay.commerce.constants.CommerceActionKeys;
import com.liferay.commerce.model.CommerceInventory;
import com.liferay.commerce.service.base.CommerceInventoryServiceBaseImpl;
import com.liferay.commerce.service.permission.CommercePermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceInventoryServiceImpl
	extends CommerceInventoryServiceBaseImpl {

	@Override
	public CommerceInventory addCommerceInventory(
			long cpDefinitionId, String commerceInventoryEngine,
			String lowStockActivity, boolean displayAvailability,
			boolean displayStockQuantity, int minStockQuantity,
			boolean backOrders, int minCartQuantity, int maxCartQuantity,
			String allowedCartQuantities, int multipleCartQuantity,
			ServiceContext serviceContext)
		throws PortalException {

		CommercePermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			CommerceActionKeys.MANAGE_COMMERCE_INVENTORY);

		return commerceInventoryLocalService.addCommerceInventory(
			cpDefinitionId, commerceInventoryEngine, lowStockActivity,
			displayAvailability, displayStockQuantity, minStockQuantity,
			backOrders, minCartQuantity, maxCartQuantity, allowedCartQuantities,
			multipleCartQuantity, serviceContext);
	}

	@Override
	public void deleteCommerceInventory(long commerceInventoryId)
		throws PortalException {

		CommerceInventory commerceInventory =
			commerceInventoryPersistence.findByPrimaryKey(commerceInventoryId);

		CommercePermission.check(
			getPermissionChecker(), commerceInventory.getGroupId(),
			CommerceActionKeys.MANAGE_COMMERCE_INVENTORY);

		commerceInventoryLocalService.deleteCommerceInventory(
			commerceInventoryId);
	}

	@Override
	public CommerceInventory fetchCommerceInventory(long commerceInventoryId) {
		return commerceInventoryLocalService.fetchCommerceInventory(
			commerceInventoryId);
	}

	@Override
	public CommerceInventory fetchCommerceInventoryByCPDefinitionId(
			long cpDefinitionId)
		throws PortalException {

		return commerceInventoryLocalService.
			fetchCommerceInventoryByCPDefinitionId(cpDefinitionId);
	}

	@Override
	public CommerceInventory updateCommerceInventory(
			long commerceInventoryId, String commerceInventoryEngine,
			String lowStockActivity, boolean displayAvailability,
			boolean displayStockQuantity, int minStockQuantity,
			boolean backOrders, int minCartQuantity, int maxCartQuantity,
			String allowedCartQuantities, int multipleCartQuantity,
			ServiceContext serviceContext)
		throws PortalException {

		CommercePermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			CommerceActionKeys.MANAGE_COMMERCE_INVENTORY);

		return commerceInventoryLocalService.updateCommerceInventory(
			commerceInventoryId, commerceInventoryEngine, lowStockActivity,
			displayAvailability, displayStockQuantity, minStockQuantity,
			backOrders, minCartQuantity, maxCartQuantity, allowedCartQuantities,
			multipleCartQuantity, serviceContext);
	}

}
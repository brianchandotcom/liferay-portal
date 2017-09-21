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

import com.liferay.commerce.model.CommerceInventory;
import com.liferay.commerce.service.base.CommerceInventoryLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceInventoryLocalServiceImpl
	extends CommerceInventoryLocalServiceBaseImpl {

	@Override
	public CommerceInventory addCommerceInventory(
			long cpDefinitionId, String commerceInventoryEngine,
			String lowStockActivity, boolean displayAvailability,
			boolean displayStockQuantity, int minStockQuantity,
			boolean backOrders, int minCartQuantity, int maxCartQuantity,
			String allowedCartQuantities, int multipleCartQuantity,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();

		long commerceInventoryId = counterLocalService.increment();

		CommerceInventory commerceInventory =
			commerceInventoryPersistence.create(commerceInventoryId);

		commerceInventory.setUuid(serviceContext.getUuid());
		commerceInventory.setGroupId(groupId);
		commerceInventory.setCompanyId(user.getCompanyId());
		commerceInventory.setUserId(user.getUserId());
		commerceInventory.setUserName(user.getFullName());
		commerceInventory.setCPDefinitionId(cpDefinitionId);
		commerceInventory.setCommerceInventoryEngine(commerceInventoryEngine);
		commerceInventory.setLowStockActivity(lowStockActivity);
		commerceInventory.setDisplayAvailability(displayAvailability);
		commerceInventory.setDisplayStockQuantity(displayStockQuantity);
		commerceInventory.setMinStockQuantity(minStockQuantity);
		commerceInventory.setBackOrders(backOrders);
		commerceInventory.setMinCartQuantity(minCartQuantity);
		commerceInventory.setMaxCartQuantity(maxCartQuantity);
		commerceInventory.setAllowedCartQuantities(allowedCartQuantities);
		commerceInventory.setMultipleCartQuantity(multipleCartQuantity);

		commerceInventoryPersistence.update(commerceInventory);

		return commerceInventory;
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CommerceInventory deleteCommerceInventory(
		CommerceInventory commerceInventory) {

		return commerceInventoryPersistence.remove(commerceInventory);
	}

	@Override
	public CommerceInventory deleteCommerceInventory(long commerceInventoryId)
		throws PortalException {

		CommerceInventory commerceInventory =
			commerceInventoryPersistence.findByPrimaryKey(commerceInventoryId);

		return commerceInventoryLocalService.deleteCommerceInventory(
			commerceInventory);
	}

	@Override
	public void deleteCommerceInventoryByCPDefinitionId(long cpDefinitionId) {
		CommerceInventory commerceInventory =
			commerceInventoryPersistence.fetchByCPDefinitionId(cpDefinitionId);

		if (commerceInventory != null) {
			deleteCommerceInventory(commerceInventory);
		}
	}

	@Override
	public CommerceInventory fetchCommerceInventoryByCPDefinitionId(
			long cpDefinitionId)
		throws PortalException {

		return commerceInventoryPersistence.fetchByCPDefinitionId(
			cpDefinitionId);
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

		CommerceInventory commerceInventory =
			commerceInventoryPersistence.findByPrimaryKey(commerceInventoryId);

		commerceInventory.setCommerceInventoryEngine(commerceInventoryEngine);
		commerceInventory.setLowStockActivity(lowStockActivity);
		commerceInventory.setDisplayAvailability(displayAvailability);
		commerceInventory.setDisplayStockQuantity(displayStockQuantity);
		commerceInventory.setMinStockQuantity(minStockQuantity);
		commerceInventory.setBackOrders(backOrders);
		commerceInventory.setMinCartQuantity(minCartQuantity);
		commerceInventory.setMaxCartQuantity(maxCartQuantity);
		commerceInventory.setAllowedCartQuantities(allowedCartQuantities);
		commerceInventory.setMultipleCartQuantity(multipleCartQuantity);

		commerceInventoryPersistence.update(commerceInventory);

		return commerceInventory;
	}

}
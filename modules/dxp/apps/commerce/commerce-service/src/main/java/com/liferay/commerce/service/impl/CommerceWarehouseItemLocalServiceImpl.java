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

import com.liferay.commerce.model.CommerceWarehouse;
import com.liferay.commerce.model.CommerceWarehouseItem;
import com.liferay.commerce.service.base.CommerceWarehouseItemLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * @author Andrea Di Giorgi
 * @author Alessio Antonio Rendina
 */
public class CommerceWarehouseItemLocalServiceImpl
	extends CommerceWarehouseItemLocalServiceBaseImpl {

	@Override
	public CommerceWarehouseItem addCommerceWarehouseItem(
			long commerceWarehouseId, long cpInstanceId, int quantity,
			ServiceContext serviceContext)
		throws PortalException {

		CommerceWarehouse commerceWarehouse =
			commerceWarehouseLocalService.getCommerceWarehouse(
				commerceWarehouseId);
		User user = userLocalService.getUser(serviceContext.getUserId());

		long commerceWarehouseItemId = counterLocalService.increment();

		CommerceWarehouseItem commerceWarehouseItem =
			commerceWarehouseItemPersistence.create(commerceWarehouseItemId);

		commerceWarehouseItem.setGroupId(commerceWarehouse.getGroupId());
		commerceWarehouseItem.setCompanyId(user.getCompanyId());
		commerceWarehouseItem.setUserId(user.getUserId());
		commerceWarehouseItem.setUserName(user.getFullName());
		commerceWarehouseItem.setCommerceWarehouseId(commerceWarehouseId);
		commerceWarehouseItem.setCPInstanceId(cpInstanceId);
		commerceWarehouseItem.setQuantity(quantity);

		commerceWarehouseItemPersistence.update(commerceWarehouseItem);

		return commerceWarehouseItem;
	}

	@Override
	public void deleteCommerceWarehouseItems(long commerceWarehouseId) {
		commerceWarehouseItemPersistence.removeByCommerceWarehouseId(
			commerceWarehouseId);
	}

	@Override
	public void deleteCommerceWarehouseItemsByCPInstanceId(long cpInstanceId) {
		commerceWarehouseItemPersistence.removeByCPInstanceId(cpInstanceId);
	}

	@Override
	public List<CommerceWarehouseItem> getCommerceWarehouseItems(
		long cpInstanceId) {

		return commerceWarehouseItemPersistence.findByCPInstanceId(
			cpInstanceId);
	}

	@Override
	public List<CommerceWarehouseItem> getCommerceWarehouseItems(
		long cpInstanceId, int start, int end,
		OrderByComparator<CommerceWarehouseItem> orderByComparator) {

		return commerceWarehouseItemFinder.findByCPInstanceId(
			cpInstanceId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceWarehouseItemsCount(long cpInstanceId) {
		return commerceWarehouseItemPersistence.countByCPInstanceId(
			cpInstanceId);
	}

	@Override
	public int getCPInstanceQuantity(long cpInstanceId) {
		return commerceWarehouseItemFinder.getCPInstanceQuantity(cpInstanceId);
	}

	@Override
	public CommerceWarehouseItem updateCommerceWarehouseItem(
			long commerceWarehouseItemId, int quantity,
			ServiceContext serviceContext)
		throws PortalException {

		CommerceWarehouseItem commerceWarehouseItem =
			commerceWarehouseItemPersistence.findByPrimaryKey(
				commerceWarehouseItemId);

		commerceWarehouseItem.setQuantity(quantity);

		commerceWarehouseItemPersistence.update(commerceWarehouseItem);

		return commerceWarehouseItem;
	}

}
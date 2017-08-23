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
 */
public class CommerceWarehouseItemLocalServiceImpl
	extends CommerceWarehouseItemLocalServiceBaseImpl {

	@Override
	public CommerceWarehouseItem addCommerceWarehouseItem(
			long commerceWarehouseId, String className, long classPK,
			int quantity, ServiceContext serviceContext)
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
		commerceWarehouseItem.setClassName(className);
		commerceWarehouseItem.setClassPK(classPK);
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
	public void deleteCommerceWarehouseItems(String className, long classPK) {
		long classNameId = classNameLocalService.getClassNameId(className);

		commerceWarehouseItemPersistence.removeByC_C(classNameId, classPK);
	}

	@Override
	public List<CommerceWarehouseItem> getCommerceWarehouseItems(
		String className, long classPK) {

		long classNameId = classNameLocalService.getClassNameId(className);

		return commerceWarehouseItemPersistence.findByC_C(classNameId, classPK);
	}

	@Override
	public List<CommerceWarehouseItem> getCommerceWarehouseItems(
		String className, long classPK, int start, int end,
		OrderByComparator<CommerceWarehouseItem> orderByComparator) {

		long classNameId = classNameLocalService.getClassNameId(className);

		return commerceWarehouseItemFinder.findByC_C(
			classNameId, classPK, start, end, orderByComparator);
	}

	@Override
	public int getCommerceWarehouseItemsCount(String className, long classPK) {
		long classNameId = classNameLocalService.getClassNameId(className);

		return commerceWarehouseItemPersistence.countByC_C(
			classNameId, classPK);
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
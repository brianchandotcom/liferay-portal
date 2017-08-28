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

import com.liferay.commerce.model.CommerceWarehouseItem;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.commerce.product.service.CPInstanceService;
import com.liferay.commerce.product.service.permission.CPDefinitionPermission;
import com.liferay.commerce.service.base.CommerceWarehouseItemServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.util.List;

/**
 * @author Andrea Di Giorgi
 */
public class CommerceWarehouseItemServiceImpl
	extends CommerceWarehouseItemServiceBaseImpl {

	@Override
	public CommerceWarehouseItem addCommerceWarehouseItem(
			long commerceWarehouseId, String className, long classPK,
			int quantity, ServiceContext serviceContext)
		throws PortalException {

		checkModel(className, classPK, ActionKeys.UPDATE);

		return commerceWarehouseItemLocalService.addCommerceWarehouseItem(
			commerceWarehouseId, className, classPK, quantity, serviceContext);
	}

	@Override
	public void deleteCommerceWarehouseItem(long commerceWarehouseItemId)
		throws PortalException {

		CommerceWarehouseItem commerceWarehouseItem =
			commerceWarehouseItemLocalService.getCommerceWarehouseItem(
				commerceWarehouseItemId);

		checkModel(commerceWarehouseItem, ActionKeys.UPDATE);

		commerceWarehouseItemLocalService.deleteCommerceWarehouseItem(
			commerceWarehouseItem);
	}

	@Override
	public CommerceWarehouseItem getCommerceWarehouseItem(
			long commerceWarehouseItemId)
		throws PortalException {

		CommerceWarehouseItem commerceWarehouseItem =
			commerceWarehouseItemLocalService.getCommerceWarehouseItem(
				commerceWarehouseItemId);

		checkModel(commerceWarehouseItem, ActionKeys.VIEW);

		return commerceWarehouseItem;
	}

	@Override
	public List<CommerceWarehouseItem> getCommerceWarehouseItems(
			String className, long classPK)
		throws PortalException {

		checkModel(className, classPK, ActionKeys.VIEW);

		return commerceWarehouseItemLocalService.getCommerceWarehouseItems(
			className, classPK);
	}

	@Override
	public List<CommerceWarehouseItem> getCommerceWarehouseItems(
			String className, long classPK, int start, int end,
			OrderByComparator<CommerceWarehouseItem> orderByComparator)
		throws PortalException {

		checkModel(className, classPK, ActionKeys.VIEW);

		return commerceWarehouseItemLocalService.getCommerceWarehouseItems(
			className, classPK, start, end, orderByComparator);
	}

	@Override
	public int getCommerceWarehouseItemsCount(String className, long classPK)
		throws PortalException {

		checkModel(className, classPK, ActionKeys.VIEW);

		return commerceWarehouseItemLocalService.getCommerceWarehouseItemsCount(
			className, classPK);
	}

	@Override
	public CommerceWarehouseItem updateCommerceWarehouseItem(
			long commerceWarehouseItemId, int quantity,
			ServiceContext serviceContext)
		throws PortalException {

		CommerceWarehouseItem commerceWarehouseItem =
			commerceWarehouseItemLocalService.getCommerceWarehouseItem(
				commerceWarehouseItemId);

		checkModel(commerceWarehouseItem, ActionKeys.UPDATE);

		return commerceWarehouseItemLocalService.updateCommerceWarehouseItem(
			commerceWarehouseItemId, quantity, serviceContext);
	}

	protected void checkModel(
			CommerceWarehouseItem commerceWarehouseItem, String actionId)
		throws PortalException {

		checkModel(
			commerceWarehouseItem.getClassName(),
			commerceWarehouseItem.getClassPK(), actionId);
	}

	protected void checkModel(String className, long classPK, String actionId)
		throws PortalException {

		if (className.equals(CPDefinition.class.getName())) {
			CPDefinitionPermission.check(
				getPermissionChecker(), classPK, actionId);
		}
		else if (className.equals(CPInstance.class.getName())) {
			CPDefinitionPermission.checkCPInstance(
				getPermissionChecker(), classPK, actionId);
		}
	}

	@ServiceReference(type = CPDefinitionService.class)
	protected CPDefinitionService cpDefinitionService;

	@ServiceReference(type = CPInstanceService.class)
	protected CPInstanceService cpInstanceService;

}
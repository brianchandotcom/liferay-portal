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
import com.liferay.commerce.service.base.CommerceWarehouseItemServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.util.List;

/**
 * @author Andrea Di Giorgi
 * @author Alessio Antonio Rendina
 */
public class CommerceWarehouseItemServiceImpl
	extends CommerceWarehouseItemServiceBaseImpl {

	@Override
	public CommerceWarehouseItem addCommerceWarehouseItem(
			long commerceWarehouseId, long cpInstanceId, int quantity,
			ServiceContext serviceContext)
		throws PortalException {

		CPInstance cpInstance = _cpInstanceService.getCPInstance(cpInstanceId);

		_cpDefinitionModelResourcePermission.check(
			getPermissionChecker(), cpInstance.getCPDefinitionId(),
			ActionKeys.UPDATE);

		return commerceWarehouseItemLocalService.addCommerceWarehouseItem(
			commerceWarehouseId, cpInstanceId, quantity, serviceContext);
	}

	@Override
	public void deleteCommerceWarehouseItem(long commerceWarehouseItemId)
		throws PortalException {

		CommerceWarehouseItem commerceWarehouseItem =
			commerceWarehouseItemLocalService.getCommerceWarehouseItem(
				commerceWarehouseItemId);

		CPInstance cpInstance = _cpInstanceService.getCPInstance(
			commerceWarehouseItem.getCPInstanceId());

		_cpDefinitionModelResourcePermission.check(
			getPermissionChecker(), cpInstance.getCPDefinitionId(),
			ActionKeys.UPDATE);

		commerceWarehouseItemLocalService.deleteCommerceWarehouseItem(
			commerceWarehouseItem);
	}

	@Override
	public CommerceWarehouseItem fetchCommerceWarehouseItem(
			long commerceWarehouseId, long cpInstanceId)
		throws PortalException {

		CPInstance cpInstance = _cpInstanceService.getCPInstance(cpInstanceId);

		_cpDefinitionModelResourcePermission.check(
			getPermissionChecker(), cpInstance.getCPDefinitionId(),
			ActionKeys.UPDATE);

		return commerceWarehouseItemLocalService.fetchCommerceWarehouseItem(
			commerceWarehouseId, cpInstanceId);
	}

	@Override
	public CommerceWarehouseItem getCommerceWarehouseItem(
			long commerceWarehouseItemId)
		throws PortalException {

		CommerceWarehouseItem commerceWarehouseItem =
			commerceWarehouseItemLocalService.getCommerceWarehouseItem(
				commerceWarehouseItemId);

		CPInstance cpInstance = _cpInstanceService.getCPInstance(
			commerceWarehouseItem.getCPInstanceId());

		_cpDefinitionModelResourcePermission.check(
			getPermissionChecker(), cpInstance.getCPDefinitionId(),
			ActionKeys.UPDATE);

		return commerceWarehouseItem;
	}

	@Override
	public List<CommerceWarehouseItem> getCommerceWarehouseItems(
			long cpInstanceId)
		throws PortalException {

		CPInstance cpInstance = _cpInstanceService.getCPInstance(cpInstanceId);

		_cpDefinitionModelResourcePermission.check(
			getPermissionChecker(), cpInstance.getCPDefinitionId(),
			ActionKeys.VIEW);

		return commerceWarehouseItemLocalService.getCommerceWarehouseItems(
			cpInstanceId);
	}

	@Override
	public List<CommerceWarehouseItem> getCommerceWarehouseItems(
			long cpInstanceId, int start, int end,
			OrderByComparator<CommerceWarehouseItem> orderByComparator)
		throws PortalException {

		CPInstance cpInstance = _cpInstanceService.getCPInstance(cpInstanceId);

		_cpDefinitionModelResourcePermission.check(
			getPermissionChecker(), cpInstance.getCPDefinitionId(),
			ActionKeys.VIEW);

		return commerceWarehouseItemLocalService.getCommerceWarehouseItems(
			cpInstanceId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceWarehouseItemsCount(long cpInstanceId)
		throws PortalException {

		CPInstance cpInstance = _cpInstanceService.getCPInstance(cpInstanceId);

		_cpDefinitionModelResourcePermission.check(
			getPermissionChecker(), cpInstance.getCPDefinitionId(),
			ActionKeys.VIEW);

		return commerceWarehouseItemLocalService.getCommerceWarehouseItemsCount(
			cpInstanceId);
	}

	@Override
	public int getCPInstanceQuantity(long cpInstanceId) throws PortalException {
		CPInstance cpInstance = _cpInstanceService.getCPInstance(cpInstanceId);

		_cpDefinitionModelResourcePermission.check(
			getPermissionChecker(), cpInstance.getCPDefinitionId(),
			ActionKeys.VIEW);

		return commerceWarehouseItemLocalService.getCPInstanceQuantity(
			cpInstanceId);
	}

	@Override
	public CommerceWarehouseItem updateCommerceWarehouseItem(
			long commerceWarehouseItemId, int quantity,
			ServiceContext serviceContext)
		throws PortalException {

		CommerceWarehouseItem commerceWarehouseItem =
			commerceWarehouseItemLocalService.getCommerceWarehouseItem(
				commerceWarehouseItemId);

		CPInstance cpInstance = _cpInstanceService.getCPInstance(
			commerceWarehouseItem.getCPInstanceId());

		_cpDefinitionModelResourcePermission.check(
			getPermissionChecker(), cpInstance.getCPDefinitionId(),
			ActionKeys.UPDATE);

		return commerceWarehouseItemLocalService.updateCommerceWarehouseItem(
			commerceWarehouseItemId, quantity, serviceContext);
	}

	private static volatile ModelResourcePermission<CPDefinition>
		_cpDefinitionModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				CommerceWarehouseItemServiceImpl.class,
				"_cpDefinitionModelResourcePermission", CPDefinition.class);

	@ServiceReference(type = CPDefinitionService.class)
	private CPDefinitionService _cpDefinitionService;

	@ServiceReference(type = CPInstanceService.class)
	private CPInstanceService _cpInstanceService;

}
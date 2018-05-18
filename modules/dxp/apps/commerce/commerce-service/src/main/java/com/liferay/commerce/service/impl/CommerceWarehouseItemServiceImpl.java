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

import com.liferay.commerce.model.CommerceWarehouseItem;
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
 * @author Alessio Antonio Rendina
 */
public class CommerceWarehouseItemServiceImpl
	extends CommerceWarehouseItemServiceBaseImpl {

	@Override
	public CommerceWarehouseItem addCommerceWarehouseItem(
			long commerceWarehouseId, long cpInstanceId, int quantity,
			ServiceContext serviceContext)
		throws PortalException {

		CPDefinitionPermission.checkCPInstance(
			getPermissionChecker(), cpInstanceId, ActionKeys.UPDATE);

		return commerceWarehouseItemLocalService.addCommerceWarehouseItem(
			commerceWarehouseId, cpInstanceId, quantity, serviceContext);
	}

	@Override
	public void deleteCommerceWarehouseItem(long commerceWarehouseItemId)
		throws PortalException {

		CommerceWarehouseItem commerceWarehouseItem =
			commerceWarehouseItemLocalService.getCommerceWarehouseItem(
				commerceWarehouseItemId);

		CPDefinitionPermission.checkCPInstance(
			getPermissionChecker(), commerceWarehouseItem.getCPInstanceId(),
			ActionKeys.UPDATE);

		commerceWarehouseItemLocalService.deleteCommerceWarehouseItem(
			commerceWarehouseItem);
	}

	@Override
	public CommerceWarehouseItem fetchCommerceWarehouseItem(
			long commerceWarehouseId, long cpInstanceId)
		throws PortalException {

		CPDefinitionPermission.checkCPInstance(
			getPermissionChecker(), cpInstanceId, ActionKeys.UPDATE);

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

		CPDefinitionPermission.checkCPInstance(
			getPermissionChecker(), commerceWarehouseItem.getCPInstanceId(),
			ActionKeys.UPDATE);

		return commerceWarehouseItem;
	}

	@Override
	public List<CommerceWarehouseItem> getCommerceWarehouseItems(
			long cpInstanceId)
		throws PortalException {

		CPDefinitionPermission.checkCPInstance(
			getPermissionChecker(), cpInstanceId, ActionKeys.VIEW);

		return commerceWarehouseItemLocalService.getCommerceWarehouseItems(
			cpInstanceId);
	}

	@Override
	public List<CommerceWarehouseItem> getCommerceWarehouseItems(
			long cpInstanceId, int start, int end,
			OrderByComparator<CommerceWarehouseItem> orderByComparator)
		throws PortalException {

		CPDefinitionPermission.checkCPInstance(
			getPermissionChecker(), cpInstanceId, ActionKeys.VIEW);

		return commerceWarehouseItemLocalService.getCommerceWarehouseItems(
			cpInstanceId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceWarehouseItemsCount(long cpInstanceId)
		throws PortalException {

		CPDefinitionPermission.checkCPInstance(
			getPermissionChecker(), cpInstanceId, ActionKeys.VIEW);

		return commerceWarehouseItemLocalService.getCommerceWarehouseItemsCount(
			cpInstanceId);
	}

	@Override
	public int getCPInstanceQuantity(long cpInstanceId) throws PortalException {
		CPDefinitionPermission.checkCPInstance(
			getPermissionChecker(), cpInstanceId, ActionKeys.VIEW);

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

		CPDefinitionPermission.checkCPInstance(
			getPermissionChecker(), commerceWarehouseItem.getCPInstanceId(),
			ActionKeys.UPDATE);

		return commerceWarehouseItemLocalService.updateCommerceWarehouseItem(
			commerceWarehouseItemId, quantity, serviceContext);
	}

	@ServiceReference(type = CPDefinitionService.class)
	private CPDefinitionService _cpDefinitionService;

	@ServiceReference(type = CPInstanceService.class)
	private CPInstanceService _cpInstanceService;

}
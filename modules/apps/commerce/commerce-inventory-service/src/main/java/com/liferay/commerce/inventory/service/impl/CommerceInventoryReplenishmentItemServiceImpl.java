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

package com.liferay.commerce.inventory.service.impl;

import com.liferay.commerce.inventory.constants.CommerceInventoryActionKeys;
import com.liferay.commerce.inventory.model.CommerceInventoryReplenishmentItem;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouse;
import com.liferay.commerce.inventory.service.base.CommerceInventoryReplenishmentItemServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

import java.util.Date;
import java.util.List;

/**
 * @author Luca Pellizzon
 * @author Alessio Antonio Rendina
 */
public class CommerceInventoryReplenishmentItemServiceImpl
	extends CommerceInventoryReplenishmentItemServiceBaseImpl {

	@Override
	public CommerceInventoryReplenishmentItem
			addCommerceInventoryReplenishmentItem(
				long userId, long commerceInventoryWarehouseId, String sku,
				Date availabilityDate, int quantity)
		throws PortalException {

		_commerceInventoryWarehouseModelResourcePermission.check(
			getPermissionChecker(), commerceInventoryWarehouseId,
			ActionKeys.UPDATE);

		return commerceInventoryReplenishmentItemLocalService.
			addCommerceInventoryReplenishmentItem(
				userId, commerceInventoryWarehouseId, sku, availabilityDate,
				quantity);
	}

	@Override
	public void deleteCommerceInventoryReplenishmentItem(
			long commerceInventoryReplenishmentItemId)
		throws PortalException {

		CommerceInventoryReplenishmentItem commerceInventoryReplenishmentItem =
			commerceInventoryReplenishmentItemLocalService.
				fetchCommerceInventoryReplenishmentItem(
					commerceInventoryReplenishmentItemId);

		if (commerceInventoryReplenishmentItem != null) {
			_commerceInventoryWarehouseModelResourcePermission.check(
				getPermissionChecker(),
				commerceInventoryReplenishmentItem.
					getCommerceInventoryWarehouseId(),
				ActionKeys.UPDATE);
		}

		commerceInventoryReplenishmentItemLocalService.
			deleteCommerceInventoryReplenishmentItem(
				commerceInventoryReplenishmentItemId);
	}

	@Override
	public CommerceInventoryReplenishmentItem
			getCommerceInventoryReplenishmentItem(
				long commerceInventoryReplenishmentItemId)
		throws PortalException {

		CommerceInventoryReplenishmentItem commerceInventoryReplenishmentItem =
			commerceInventoryReplenishmentItemLocalService.
				fetchCommerceInventoryReplenishmentItem(
					commerceInventoryReplenishmentItemId);

		if (commerceInventoryReplenishmentItem != null) {
			_commerceInventoryWarehouseModelResourcePermission.check(
				getPermissionChecker(),
				commerceInventoryReplenishmentItem.
					getCommerceInventoryWarehouseId(),
				ActionKeys.VIEW);
		}

		return commerceInventoryReplenishmentItemLocalService.
			getCommerceInventoryReplenishmentItem(
				commerceInventoryReplenishmentItemId);
	}

	@Override
	public List<CommerceInventoryReplenishmentItem>
			getCommerceInventoryReplenishmentItemsByCompanyIdAndSku(
				long companyId, String sku, int start, int end)
		throws PortalException {

		PortletResourcePermission portletResourcePermission =
			_commerceInventoryWarehouseModelResourcePermission.
				getPortletResourcePermission();

		portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceInventoryActionKeys.MANAGE_INVENTORY);

		return commerceInventoryReplenishmentItemLocalService.
			getCommerceInventoryReplenishmentItemsByCompanyIdAndSku(
				companyId, sku, start, end);
	}

	@Override
	public long getCommerceInventoryReplenishmentItemsCount(
			long commerceInventoryWarehouseId, String sku)
		throws PortalException {

		_commerceInventoryWarehouseModelResourcePermission.check(
			getPermissionChecker(), commerceInventoryWarehouseId,
			ActionKeys.VIEW);

		return commerceInventoryReplenishmentItemLocalService.
			getCommerceInventoryReplenishmentItemsCount(
				commerceInventoryWarehouseId, sku);
	}

	@Override
	public int getCommerceInventoryReplenishmentItemsCountByCompanyIdAndSku(
			long companyId, String sku)
		throws PortalException {

		PortletResourcePermission portletResourcePermission =
			_commerceInventoryWarehouseModelResourcePermission.
				getPortletResourcePermission();

		portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceInventoryActionKeys.MANAGE_INVENTORY);

		return commerceInventoryReplenishmentItemLocalService.
			getCommerceInventoryReplenishmentItemsCountByCompanyIdAndSku(
				companyId, sku);
	}

	@Override
	public CommerceInventoryReplenishmentItem
			updateCommerceInventoryReplenishmentItem(
				long commerceInventoryReplenishmentItemId,
				Date availabilityDate, int quantity, long mvccVersion)
		throws PortalException {

		CommerceInventoryReplenishmentItem commerceInventoryReplenishmentItem =
			commerceInventoryReplenishmentItemLocalService.
				fetchCommerceInventoryReplenishmentItem(
					commerceInventoryReplenishmentItemId);

		if (commerceInventoryReplenishmentItem != null) {
			_commerceInventoryWarehouseModelResourcePermission.check(
				getPermissionChecker(),
				commerceInventoryReplenishmentItem.
					getCommerceInventoryWarehouseId(),
				ActionKeys.UPDATE);
		}

		return commerceInventoryReplenishmentItemLocalService.
			updateCommerceInventoryReplenishmentItem(
				commerceInventoryReplenishmentItemId, availabilityDate,
				quantity, mvccVersion);
	}

	private static volatile ModelResourcePermission<CommerceInventoryWarehouse>
		_commerceInventoryWarehouseModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				CommerceInventoryReplenishmentItemServiceImpl.class,
				"_commerceInventoryWarehouseModelResourcePermission",
				CommerceInventoryWarehouse.class);

}
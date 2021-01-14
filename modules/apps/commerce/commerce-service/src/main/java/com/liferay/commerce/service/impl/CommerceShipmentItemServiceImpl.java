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
import com.liferay.commerce.constants.CommerceConstants;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.model.CommerceShipmentItem;
import com.liferay.commerce.service.base.CommerceShipmentItemServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermissionFactory;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceShipmentItemServiceImpl
	extends CommerceShipmentItemServiceBaseImpl {

	@Override
	public CommerceShipmentItem addCommerceShipmentItem(
			long commerceShipmentId, long commerceOrderItemId,
			long commerceInventoryWarehouseId, int quantity,
			ServiceContext serviceContext)
		throws PortalException {

		_portletResourcePermission.contains(
			getPermissionChecker(), null,
			CommerceActionKeys.MANAGE_COMMERCE_SHIPMENTS);

		return commerceShipmentItemLocalService.addCommerceShipmentItem(
			commerceShipmentId, commerceOrderItemId,
			commerceInventoryWarehouseId, quantity, serviceContext);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), pass boolean for restoring stock
	 */
	@Deprecated
	@Override
	public void deleteCommerceShipmentItem(long commerceShipmentItemId)
		throws PortalException {

		deleteCommerceShipmentItem(commerceShipmentItemId, false);
	}

	@Override
	public void deleteCommerceShipmentItem(
			long commerceShipmentItemId, boolean restoreStockQuantity)
		throws PortalException {

		CommerceShipmentItem commerceShipmentItem =
			commerceShipmentItemPersistence.findByPrimaryKey(
				commerceShipmentItemId);

		_portletResourcePermission.contains(
			getPermissionChecker(), null,
			CommerceActionKeys.MANAGE_COMMERCE_SHIPMENTS);

		commerceShipmentItemLocalService.deleteCommerceShipmentItem(
			commerceShipmentItem, restoreStockQuantity);
	}

	@Override
	public CommerceShipmentItem fetchCommerceShipmentItem(
			long commerceShipmentId, long commerceOrderItemId,
			long commerceInventoryWarehouseId)
		throws PortalException {

		_portletResourcePermission.contains(
			getPermissionChecker(), null,
			CommerceActionKeys.MANAGE_COMMERCE_SHIPMENTS);

		return commerceShipmentItemLocalService.fetchCommerceShipmentItem(
			commerceShipmentId, commerceOrderItemId,
			commerceInventoryWarehouseId);
	}

	@Override
	public CommerceShipmentItem getCommerceShipmentItem(
			long commerceShipmentItemId)
		throws PortalException {

		_portletResourcePermission.contains(
			getPermissionChecker(), null,
			CommerceActionKeys.MANAGE_COMMERCE_SHIPMENTS);

		return commerceShipmentItemLocalService.getCommerceShipmentItem(
			commerceShipmentItemId);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	@Override
	public List<CommerceShipmentItem> getCommerceShipmentItems(
			long commerceOrderItemId)
		throws PortalException {

		return commerceShipmentItemService.
			getCommerceShipmentItemsByCommerceOrderItemId(commerceOrderItemId);
	}

	@Override
	public List<CommerceShipmentItem> getCommerceShipmentItems(
			long commerceShipmentId, int start, int end,
			OrderByComparator<CommerceShipmentItem> orderByComparator)
		throws PortalException {

		_portletResourcePermission.contains(
			getPermissionChecker(), null,
			CommerceActionKeys.MANAGE_COMMERCE_SHIPMENTS);

		return commerceShipmentItemLocalService.getCommerceShipmentItems(
			commerceShipmentId, start, end, orderByComparator);
	}

	@Override
	public List<CommerceShipmentItem>
			getCommerceShipmentItemsByCommerceOrderItemId(
				long commerceOrderItemId)
		throws PortalException {

		CommerceOrderItem commerceOrderItem =
			commerceOrderItemLocalService.getCommerceOrderItem(
				commerceOrderItemId);

		_commerceOrderModelResourcePermission.check(
			getPermissionChecker(), commerceOrderItem.getCommerceOrderId(),
			ActionKeys.VIEW);

		return commerceShipmentItemLocalService.
			getCommerceShipmentItemsByCommerceOrderItemId(commerceOrderItemId);
	}

	@Override
	public int getCommerceShipmentItemsCount(long commerceShipmentId)
		throws PortalException {

		_portletResourcePermission.contains(
			getPermissionChecker(), null,
			CommerceActionKeys.MANAGE_COMMERCE_SHIPMENTS);

		return commerceShipmentItemLocalService.getCommerceShipmentItemsCount(
			commerceShipmentId);
	}

	@Override
	public int getCommerceShipmentItemsCountByCommerceOrderItemId(
			long commerceOrderItemId)
		throws PortalException {

		_portletResourcePermission.contains(
			getPermissionChecker(), null,
			CommerceActionKeys.MANAGE_COMMERCE_SHIPMENTS);

		return commerceShipmentItemLocalService.
			getCommerceShipmentItemsCountByCommerceOrderItemId(
				commerceOrderItemId);
	}

	@Override
	public int getCommerceShipmentOrderItemsQuantity(
			long commerceShipmentId, long commerceOrderItemId)
		throws PortalException {

		_portletResourcePermission.contains(
			getPermissionChecker(), null,
			CommerceActionKeys.MANAGE_COMMERCE_SHIPMENTS);

		return commerceShipmentItemLocalService.
			getCommerceShipmentOrderItemsQuantity(
				commerceShipmentId, commerceOrderItemId);
	}

	@Override
	public CommerceShipmentItem updateCommerceShipmentItem(
			long commerceShipmentItemId, int quantity)
		throws PortalException {

		_portletResourcePermission.contains(
			getPermissionChecker(), null,
			CommerceActionKeys.MANAGE_COMMERCE_SHIPMENTS);

		return commerceShipmentItemLocalService.updateCommerceShipmentItem(
			commerceShipmentItemId, quantity);
	}

	@Override
	public CommerceShipmentItem updateCommerceShipmentItem(
			long commerceShipmentItemId, long commerceInventoryWarehouseId,
			int quantity)
		throws PortalException {

		_portletResourcePermission.contains(
			getPermissionChecker(), null,
			CommerceActionKeys.MANAGE_COMMERCE_SHIPMENTS);

		return commerceShipmentItemLocalService.updateCommerceShipmentItem(
			commerceShipmentItemId, commerceInventoryWarehouseId, quantity);
	}

	private static volatile ModelResourcePermission<CommerceOrder>
		_commerceOrderModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				CommerceShipmentItemServiceImpl.class,
				"_commerceOrderModelResourcePermission", CommerceOrder.class);
	private static volatile PortletResourcePermission
		_portletResourcePermission =
			PortletResourcePermissionFactory.getInstance(
				CommerceShipmentItemServiceImpl.class,
				"_portletResourcePermission",
				CommerceConstants.RESOURCE_NAME_SHIPMENT);

}
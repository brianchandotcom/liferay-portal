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
import com.liferay.commerce.model.CommerceCart;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.service.base.CommerceOrderServiceBaseImpl;
import com.liferay.commerce.service.permission.CommercePermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;
import java.util.Map;

/**
 * @author Andrea Di Giorgi
 * @author Marco Leo
 */
public class CommerceOrderServiceImpl extends CommerceOrderServiceBaseImpl {

	@Override
	public CommerceOrder addCommerceOrderFromCart(
			long commerceCartId, ServiceContext serviceContext)
		throws PortalException {

		CommerceCart commerceCart = commerceCartLocalService.getCommerceCart(
			commerceCartId);

		if (commerceCart.getUserId() != serviceContext.getUserId()) {
			throw new PrincipalException();
		}

		return commerceOrderLocalService.addCommerceOrderFromCart(
			commerceCart.getCommerceCartId(), serviceContext);
	}

	@Override
	public void deleteCommerceOrder(long commerceOrderId)
		throws PortalException {

		checkCommerceOrder(commerceOrderId);

		commerceOrderLocalService.deleteCommerceOrder(commerceOrderId);
	}

	@Override
	public CommerceOrder fetchCommerceOrder(long commerceOrderId)
		throws PortalException {

		CommerceOrder commerceOrder =
			commerceOrderPersistence.fetchByPrimaryKey(commerceOrderId);

		checkCommerceOrder(commerceOrder);

		return commerceOrder;
	}

	@Override
	public CommerceOrder getCommerceOrder(long commerceOrderId)
		throws PortalException {

		CommerceOrder commerceOrder = commerceOrderPersistence.findByPrimaryKey(
			commerceOrderId);

		checkCommerceOrder(commerceOrder);

		return commerceOrder;
	}

	@Override
	public CommerceOrder getCommerceOrderByUuidAndGroupId(
			String uuid, long groupId)
		throws PortalException {

		CommerceOrder commerceOrder =
			commerceOrderLocalService.getCommerceOrderByUuidAndGroupId(
				uuid, groupId);

		checkCommerceOrder(commerceOrder);

		return commerceOrder;
	}

	@Override
	public List<CommerceOrder> getCommerceOrders(
			long groupId, int status, int start, int end,
			OrderByComparator<CommerceOrder> orderByComparator)
		throws PortalException {

		CommercePermission.check(
			getPermissionChecker(), groupId,
			CommerceActionKeys.MANAGE_COMMERCE_ORDERS);

		return commerceOrderLocalService.getCommerceOrders(
			groupId, status, start, end, orderByComparator);
	}

	@Override
	public Map<Integer, Long> getCommerceOrdersCount(long groupId)
		throws PortalException {

		CommercePermission.check(
			getPermissionChecker(), groupId,
			CommerceActionKeys.MANAGE_COMMERCE_ORDERS);

		return commerceOrderLocalService.getCommerceOrdersCount(groupId);
	}

	@Override
	public int getCommerceOrdersCount(long groupId, int status)
		throws PortalException {

		CommercePermission.check(
			getPermissionChecker(), groupId,
			CommerceActionKeys.MANAGE_COMMERCE_ORDERS);

		return commerceOrderLocalService.getCommerceOrdersCount(
			groupId, status);
	}

	@Override
	public CommerceOrder updateBillingAddress(
			long commerceOrderId, String name, String description,
			String street1, String street2, String street3, String city,
			String zip, long commerceRegionId, long commerceCountryId,
			String phoneNumber, ServiceContext serviceContext)
		throws PortalException {

		checkCommerceOrder(commerceOrderId);

		return commerceOrderLocalService.updateBillingAddress(
			commerceOrderId, name, description, street1, street2, street3, city,
			zip, commerceRegionId, commerceCountryId, phoneNumber,
			serviceContext);
	}

	@Override
	public CommerceOrder updatePurchaseOrderNumber(
			long commerceOrderId, String purchaseOrderNumber)
		throws PortalException {

		checkCommerceOrder(commerceOrderId);

		return commerceOrderLocalService.updatePurchaseOrderNumber(
			commerceOrderId, purchaseOrderNumber);
	}

	@Override
	public CommerceOrder updateShippingAddress(
			long commerceOrderId, String name, String description,
			String street1, String street2, String street3, String city,
			String zip, long commerceRegionId, long commerceCountryId,
			String phoneNumber, ServiceContext serviceContext)
		throws PortalException {

		checkCommerceOrder(commerceOrderId);

		return commerceOrderLocalService.updateShippingAddress(
			commerceOrderId, name, description, street1, street2, street3, city,
			zip, commerceRegionId, commerceCountryId, phoneNumber,
			serviceContext);
	}

	protected void checkCommerceOrder(CommerceOrder commerceOrder)
		throws PrincipalException {

		if (commerceOrder == null) {
			return;
		}

		PermissionChecker permissionChecker = getPermissionChecker();

		if ((commerceOrder.getUserId() != permissionChecker.getUserId()) &&
			(commerceOrder.getOrderUserId() != permissionChecker.getUserId()) &&
			!CommercePermission.contains(
				permissionChecker, commerceOrder.getGroupId(),
				CommerceActionKeys.MANAGE_COMMERCE_ORDERS)) {

			throw new PrincipalException();
		}
	}

	protected void checkCommerceOrder(long commerceOrderId)
		throws PortalException {

		CommerceOrder commerceOrder = commerceOrderPersistence.findByPrimaryKey(
			commerceOrderId);

		checkCommerceOrder(commerceOrder);
	}

}
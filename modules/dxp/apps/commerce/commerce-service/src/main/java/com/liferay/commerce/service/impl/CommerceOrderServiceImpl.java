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
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.service.base.CommerceOrderServiceBaseImpl;
import com.liferay.commerce.service.permission.CommercePermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * @author Andrea Di Giorgi
 * @author Marco Leo
 */
public class CommerceOrderServiceImpl extends CommerceOrderServiceBaseImpl {

	@Override
	public CommerceOrder addOrganizationCommerceOrder(
			long groupId, long userId, long siteGroupId,
			long orderOrganizationId)
		throws PortalException {

		return commerceOrderLocalService.addOrganizationCommerceOrder(
			groupId, userId, siteGroupId, orderOrganizationId);
	}

	@Override
	public CommerceOrder addUserCommerceOrder(long groupId, long userId)
		throws PortalException {

		return commerceOrderLocalService.addUserCommerceOrder(groupId, userId);
	}

	@Override
	public CommerceOrder addUserCommerceOrder(
			long groupId, long userId, long orderUserId)
		throws PortalException {

		return commerceOrderLocalService.addUserCommerceOrder(
			groupId, userId, orderUserId);
	}

	@Override
	public CommerceOrder checkoutCommerceOrder(
			long commerceOrderId, ServiceContext serviceContext)
		throws PortalException {

		_commerceOrderModelResourcePermission.check(
			getPermissionChecker(), commerceOrderId,
			CommerceActionKeys.CHECKOUT);

		return commerceOrderLocalService.checkoutCommerceOrder(
			commerceOrderId, serviceContext);
	}

	@Override
	public void deleteCommerceOrder(long commerceOrderId)
		throws PortalException {

		_commerceOrderModelResourcePermission.check(
			getPermissionChecker(), commerceOrderId, ActionKeys.DELETE);

		commerceOrderLocalService.deleteCommerceOrder(commerceOrderId);
	}

	@Override
	public CommerceOrder fetchCommerceOrder(long commerceOrderId)
		throws PortalException {

		CommerceOrder commerceOrder =
			commerceOrderLocalService.fetchCommerceOrder(commerceOrderId);

		if (commerceOrder != null) {
			_commerceOrderModelResourcePermission.check(
				getPermissionChecker(), commerceOrder, ActionKeys.VIEW);
		}

		return commerceOrder;
	}

	@Override
	public CommerceOrder fetchCommerceOrder(long groupId, int orderStatus)
		throws PortalException {

		CommerceOrder commerceOrder =
			commerceOrderLocalService.fetchCommerceOrder(
				groupId, getGuestOrUserId(), orderStatus);

		if (commerceOrder != null) {
			_commerceOrderModelResourcePermission.check(
				getPermissionChecker(), commerceOrder, ActionKeys.VIEW);
		}

		return commerceOrder;
	}

	@Override
	public CommerceOrder fetchCommerceOrder(String uuid, long groupId)
		throws PortalException {

		CommerceOrder commerceOrder =
			commerceOrderLocalService.fetchCommerceOrderByUuidAndGroupId(
				uuid, groupId);

		if (commerceOrder != null) {
			_commerceOrderModelResourcePermission.check(
				getPermissionChecker(), commerceOrder, ActionKeys.VIEW);
		}

		return commerceOrder;
	}

	@Override
	public CommerceOrder getCommerceOrder(long commerceOrderId)
		throws PortalException {

		CommerceOrder commerceOrder =
			commerceOrderLocalService.getCommerceOrder(commerceOrderId);

		_commerceOrderModelResourcePermission.check(
			getPermissionChecker(), commerceOrder, ActionKeys.VIEW);

		return commerceOrder;
	}

	@Override
	public CommerceOrder getCommerceOrderByUuidAndGroupId(
			String uuid, long groupId)
		throws PortalException {

		CommerceOrder commerceOrder =
			commerceOrderLocalService.getCommerceOrderByUuidAndGroupId(
				uuid, groupId);

		_commerceOrderModelResourcePermission.check(
			getPermissionChecker(), commerceOrder, ActionKeys.VIEW);

		return commerceOrder;
	}

	@Override
	public List<CommerceOrder> getCommerceOrders(
			long groupId, long orderUserId, int start, int end,
			OrderByComparator<CommerceOrder> orderByComparator)
		throws PortalException {

		PermissionChecker permissionChecker = getPermissionChecker();

		if ((orderUserId != permissionChecker.getUserId()) &&
			!CommercePermission.contains(
				permissionChecker, groupId,
				CommerceActionKeys.MANAGE_COMMERCE_ORDERS)) {

			throw new PrincipalException();
		}

		return commerceOrderLocalService.getCommerceOrders(
			groupId, orderUserId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceOrdersCount(long groupId, long orderUserId)
		throws PortalException {

		PermissionChecker permissionChecker = getPermissionChecker();

		if ((orderUserId != permissionChecker.getUserId()) &&
			!CommercePermission.contains(
				permissionChecker, groupId,
				CommerceActionKeys.MANAGE_COMMERCE_ORDERS)) {

			throw new PrincipalException();
		}

		return commerceOrderLocalService.getCommerceOrdersCount(
			groupId, orderUserId);
	}

	@Override
	public void mergeGuestCommerceOrder(
			long guestCommerceOrderId, long userCommerceOrderId,
			ServiceContext serviceContext)
		throws PortalException {

		_commerceOrderModelResourcePermission.check(
			getPermissionChecker(), guestCommerceOrderId, ActionKeys.VIEW);
		_commerceOrderModelResourcePermission.check(
			getPermissionChecker(), userCommerceOrderId, ActionKeys.UPDATE);

		commerceOrderLocalService.mergeGuestCommerceOrder(
			guestCommerceOrderId, userCommerceOrderId, serviceContext);
	}

	@Override
	public CommerceOrder updateBillingAddress(
			long commerceOrderId, String name, String description,
			String street1, String street2, String street3, String city,
			String zip, long commerceRegionId, long commerceCountryId,
			String phoneNumber, ServiceContext serviceContext)
		throws PortalException {

		_commerceOrderModelResourcePermission.check(
			getPermissionChecker(), commerceOrderId, ActionKeys.UPDATE);

		return commerceOrderLocalService.updateBillingAddress(
			commerceOrderId, name, description, street1, street2, street3, city,
			zip, commerceRegionId, commerceCountryId, phoneNumber,
			serviceContext);
	}

	@Override
	public CommerceOrder updateCommerceOrder(
			long commerceOrderId, long billingAddressId, long shippingAddressId,
			long commercePaymentMethodId, long commerceShippingMethodId,
			String shippingOptionName, String purchaseOrderNumber,
			double subtotal, double shippingPrice, double total,
			String advanceStatus, int paymentStatus, int orderStatus)
		throws PortalException {

		_commerceOrderModelResourcePermission.check(
			getPermissionChecker(), commerceOrderId, ActionKeys.UPDATE);

		return commerceOrderLocalService.updateCommerceOrder(
			commerceOrderId, billingAddressId, shippingAddressId,
			commercePaymentMethodId, commerceShippingMethodId,
			shippingOptionName, purchaseOrderNumber, subtotal, shippingPrice,
			total, advanceStatus, paymentStatus, orderStatus);
	}

	@Override
	public CommerceOrder updatePurchaseOrderNumber(
			long commerceOrderId, String purchaseOrderNumber)
		throws PortalException {

		_commerceOrderModelResourcePermission.check(
			getPermissionChecker(), commerceOrderId, ActionKeys.UPDATE);

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

		_commerceOrderModelResourcePermission.check(
			getPermissionChecker(), commerceOrderId, ActionKeys.UPDATE);

		return commerceOrderLocalService.updateShippingAddress(
			commerceOrderId, name, description, street1, street2, street3, city,
			zip, commerceRegionId, commerceCountryId, phoneNumber,
			serviceContext);
	}

	@Override
	public CommerceOrder updateUser(long commerceOrderId, long userId)
		throws PortalException {

		_commerceOrderModelResourcePermission.check(
			getPermissionChecker(), commerceOrderId, ActionKeys.UPDATE);

		return commerceOrderLocalService.updateUser(commerceOrderId, userId);
	}

	private static volatile ModelResourcePermission<CommerceOrder>
		_commerceOrderModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				CommerceOrderServiceImpl.class,
				"_commerceOrderModelResourcePermission", CommerceOrder.class);

}
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

package com.liferay.commerce.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.osgi.util.ServiceTrackerFactory;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for CommerceOrder. This utility wraps
 * {@link com.liferay.commerce.service.impl.CommerceOrderServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceOrderService
 * @see com.liferay.commerce.service.base.CommerceOrderServiceBaseImpl
 * @see com.liferay.commerce.service.impl.CommerceOrderServiceImpl
 * @generated
 */
@ProviderType
public class CommerceOrderServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.service.impl.CommerceOrderServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.commerce.model.CommerceOrder addOrganizationCommerceOrder(
		long groupId, long siteGroupId, long orderOrganizationId,
		long shippingAddressId, java.lang.String purchaseOrderNumber)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addOrganizationCommerceOrder(groupId, siteGroupId,
			orderOrganizationId, shippingAddressId, purchaseOrderNumber);
	}

	public static com.liferay.commerce.model.CommerceOrder addUserCommerceOrder(
		long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().addUserCommerceOrder(groupId);
	}

	public static com.liferay.commerce.model.CommerceOrder addUserCommerceOrder(
		long groupId, long orderUserId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().addUserCommerceOrder(groupId, orderUserId);
	}

	public static com.liferay.commerce.model.CommerceOrder approveCommerceOrder(
		long commerceOrderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().approveCommerceOrder(commerceOrderId);
	}

	public static com.liferay.commerce.model.CommerceOrder checkoutCommerceOrder(
		long commerceOrderId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .checkoutCommerceOrder(commerceOrderId, serviceContext);
	}

	public static void deleteCommerceOrder(long commerceOrderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteCommerceOrder(commerceOrderId);
	}

	public static com.liferay.commerce.model.CommerceOrder executeWorkflowTransition(
		long commerceOrderId, long workflowTaskId,
		java.lang.String transitionName, java.lang.String comment)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .executeWorkflowTransition(commerceOrderId, workflowTaskId,
			transitionName, comment);
	}

	public static com.liferay.commerce.model.CommerceOrder fetchCommerceOrder(
		long commerceOrderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().fetchCommerceOrder(commerceOrderId);
	}

	public static com.liferay.commerce.model.CommerceOrder fetchCommerceOrder(
		long groupId, int orderStatus)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().fetchCommerceOrder(groupId, orderStatus);
	}

	public static com.liferay.commerce.model.CommerceOrder fetchCommerceOrder(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().fetchCommerceOrder(uuid, groupId);
	}

	public static com.liferay.commerce.model.CommerceOrder getCommerceOrder(
		long commerceOrderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCommerceOrder(commerceOrderId);
	}

	public static com.liferay.commerce.model.CommerceOrder getCommerceOrderByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCommerceOrderByUuidAndGroupId(uuid, groupId);
	}

	public static java.util.List<com.liferay.commerce.model.CommerceOrder> getCommerceOrders(
		long groupId, long orderUserId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceOrder> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCommerceOrders(groupId, orderUserId, start, end,
			orderByComparator);
	}

	public static int getCommerceOrdersCount(long groupId, long orderUserId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCommerceOrdersCount(groupId, orderUserId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static void mergeGuestCommerceOrder(long guestCommerceOrderId,
		long userCommerceOrderId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.mergeGuestCommerceOrder(guestCommerceOrderId, userCommerceOrderId,
			serviceContext);
	}

	public static com.liferay.commerce.model.CommerceOrder reorderCommerceOrder(
		long commerceOrderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().reorderCommerceOrder(commerceOrderId);
	}

	public static com.liferay.commerce.model.CommerceOrder submitCommerceOrder(
		long commerceOrderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().submitCommerceOrder(commerceOrderId);
	}

	public static com.liferay.commerce.model.CommerceOrder updateBillingAddress(
		long commerceOrderId, java.lang.String name,
		java.lang.String description, java.lang.String street1,
		java.lang.String street2, java.lang.String street3,
		java.lang.String city, java.lang.String zip, long commerceRegionId,
		long commerceCountryId, java.lang.String phoneNumber,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateBillingAddress(commerceOrderId, name, description,
			street1, street2, street3, city, zip, commerceRegionId,
			commerceCountryId, phoneNumber, serviceContext);
	}

	public static com.liferay.commerce.model.CommerceOrder updateCommerceOrder(
		long commerceOrderId, long billingAddressId, long shippingAddressId,
		long commercePaymentMethodId, long commerceShippingMethodId,
		java.lang.String shippingOptionName,
		java.lang.String purchaseOrderNumber, double subtotal,
		double shippingPrice, double total, java.lang.String advanceStatus,
		int paymentStatus, int orderStatus)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCommerceOrder(commerceOrderId, billingAddressId,
			shippingAddressId, commercePaymentMethodId,
			commerceShippingMethodId, shippingOptionName, purchaseOrderNumber,
			subtotal, shippingPrice, total, advanceStatus, paymentStatus,
			orderStatus);
	}

	public static com.liferay.commerce.model.CommerceOrder updatePurchaseOrderNumber(
		long commerceOrderId, java.lang.String purchaseOrderNumber)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updatePurchaseOrderNumber(commerceOrderId,
			purchaseOrderNumber);
	}

	public static com.liferay.commerce.model.CommerceOrder updateShippingAddress(
		long commerceOrderId, java.lang.String name,
		java.lang.String description, java.lang.String street1,
		java.lang.String street2, java.lang.String street3,
		java.lang.String city, java.lang.String zip, long commerceRegionId,
		long commerceCountryId, java.lang.String phoneNumber,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateShippingAddress(commerceOrderId, name, description,
			street1, street2, street3, city, zip, commerceRegionId,
			commerceCountryId, phoneNumber, serviceContext);
	}

	public static com.liferay.commerce.model.CommerceOrder updateUser(
		long commerceOrderId, long userId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().updateUser(commerceOrderId, userId);
	}

	public static CommerceOrderService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceOrderService, CommerceOrderService> _serviceTracker =
		ServiceTrackerFactory.open(CommerceOrderService.class);
}
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

package com.liferay.shopping.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.osgi.util.ServiceTrackerFactory;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for ShoppingOrder. This utility wraps
 * {@link com.liferay.shopping.service.impl.ShoppingOrderServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see ShoppingOrderService
 * @see com.liferay.shopping.service.base.ShoppingOrderServiceBaseImpl
 * @see com.liferay.shopping.service.impl.ShoppingOrderServiceImpl
 * @generated
 */
@ProviderType
public class ShoppingOrderServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.shopping.service.impl.ShoppingOrderServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.shopping.model.ShoppingOrder getOrder(
		long groupId, long orderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getOrder(groupId, orderId);
	}

	public static com.liferay.shopping.model.ShoppingOrder updateOrder(
		long groupId, long orderId, java.lang.String billingFirstName,
		java.lang.String billingLastName, java.lang.String billingEmailAddress,
		java.lang.String billingCompany, java.lang.String billingStreet,
		java.lang.String billingCity, java.lang.String billingState,
		java.lang.String billingZip, java.lang.String billingCountry,
		java.lang.String billingPhone, boolean shipToBilling,
		java.lang.String shippingFirstName, java.lang.String shippingLastName,
		java.lang.String shippingEmailAddress,
		java.lang.String shippingCompany, java.lang.String shippingStreet,
		java.lang.String shippingCity, java.lang.String shippingState,
		java.lang.String shippingZip, java.lang.String shippingCountry,
		java.lang.String shippingPhone, java.lang.String ccName,
		java.lang.String ccType, java.lang.String ccNumber, int ccExpMonth,
		int ccExpYear, java.lang.String ccVerNumber, java.lang.String comments)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateOrder(groupId, orderId, billingFirstName,
			billingLastName, billingEmailAddress, billingCompany,
			billingStreet, billingCity, billingState, billingZip,
			billingCountry, billingPhone, shipToBilling, shippingFirstName,
			shippingLastName, shippingEmailAddress, shippingCompany,
			shippingStreet, shippingCity, shippingState, shippingZip,
			shippingCountry, shippingPhone, ccName, ccType, ccNumber,
			ccExpMonth, ccExpYear, ccVerNumber, comments);
	}

	public static com.liferay.shopping.model.ShoppingOrder updateOrder(
		long groupId, long orderId, java.lang.String ppTxnId,
		java.lang.String ppPaymentStatus, double ppPaymentGross,
		java.lang.String ppReceiverEmail, java.lang.String ppPayerEmail)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateOrder(groupId, orderId, ppTxnId, ppPaymentStatus,
			ppPaymentGross, ppReceiverEmail, ppPayerEmail);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static void completeOrder(long groupId, java.lang.String number,
		java.lang.String ppTxnId, java.lang.String ppPaymentStatus,
		double ppPaymentGross, java.lang.String ppReceiverEmail,
		java.lang.String ppPayerEmail,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.completeOrder(groupId, number, ppTxnId, ppPaymentStatus,
			ppPaymentGross, ppReceiverEmail, ppPayerEmail, serviceContext);
	}

	public static void deleteOrder(long groupId, long orderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteOrder(groupId, orderId);
	}

	public static void sendEmail(long groupId, long orderId,
		java.lang.String emailType,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().sendEmail(groupId, orderId, emailType, serviceContext);
	}

	public static ShoppingOrderService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<ShoppingOrderService, ShoppingOrderService> _serviceTracker =
		ServiceTrackerFactory.open(ShoppingOrderService.class);
}
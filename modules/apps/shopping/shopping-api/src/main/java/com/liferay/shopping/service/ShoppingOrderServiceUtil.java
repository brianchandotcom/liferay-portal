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

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

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
	public static void completeOrder(long groupId, String number,
		String ppTxnId, String ppPaymentStatus, double ppPaymentGross,
		String ppReceiverEmail, String ppPayerEmail,
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

	public static com.liferay.shopping.model.ShoppingOrder getOrder(
		long groupId, long orderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getOrder(groupId, orderId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static void sendEmail(long groupId, long orderId, String emailType,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().sendEmail(groupId, orderId, emailType, serviceContext);
	}

	public static com.liferay.shopping.model.ShoppingOrder updateOrder(
		long groupId, long orderId, String ppTxnId, String ppPaymentStatus,
		double ppPaymentGross, String ppReceiverEmail, String ppPayerEmail)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateOrder(groupId, orderId, ppTxnId, ppPaymentStatus,
			ppPaymentGross, ppReceiverEmail, ppPayerEmail);
	}

	public static com.liferay.shopping.model.ShoppingOrder updateOrder(
		long groupId, long orderId, String billingFirstName,
		String billingLastName, String billingEmailAddress,
		String billingCompany, String billingStreet, String billingCity,
		String billingState, String billingZip, String billingCountry,
		String billingPhone, boolean shipToBilling, String shippingFirstName,
		String shippingLastName, String shippingEmailAddress,
		String shippingCompany, String shippingStreet, String shippingCity,
		String shippingState, String shippingZip, String shippingCountry,
		String shippingPhone, String ccName, String ccType, String ccNumber,
		int ccExpMonth, int ccExpYear, String ccVerNumber, String comments)
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

	public static ShoppingOrderService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<ShoppingOrderService, ShoppingOrderService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(ShoppingOrderService.class);

		ServiceTracker<ShoppingOrderService, ShoppingOrderService> serviceTracker =
			new ServiceTracker<ShoppingOrderService, ShoppingOrderService>(bundle.getBundleContext(),
				ShoppingOrderService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}
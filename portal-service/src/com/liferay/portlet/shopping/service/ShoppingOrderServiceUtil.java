/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.shopping.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * Provides the remote service utility for ShoppingOrder. This utility wraps
 * {@link com.liferay.portlet.shopping.service.impl.ShoppingOrderServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see ShoppingOrderService
 * @see com.liferay.portlet.shopping.service.base.ShoppingOrderServiceBaseImpl
 * @see com.liferay.portlet.shopping.service.impl.ShoppingOrderServiceImpl
 * @generated
 */
public class ShoppingOrderServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.shopping.service.impl.ShoppingOrderServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public static java.lang.String getBeanIdentifier() {
		return getService().getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public static void setBeanIdentifier(java.lang.String beanIdentifier) {
		getService().setBeanIdentifier(beanIdentifier);
	}

	public static void completeOrder(long groupId, java.lang.String number,
		java.lang.String ppTxnId, java.lang.String ppPaymentStatus,
		double ppPaymentGross, java.lang.String ppReceiverEmail,
		java.lang.String ppPayerEmail,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.completeOrder(groupId, number, ppTxnId, ppPaymentStatus,
			ppPaymentGross, ppReceiverEmail, ppPayerEmail, serviceContext);
	}

	public static void deleteOrder(long groupId, long orderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteOrder(groupId, orderId);
	}

	public static com.liferay.portlet.shopping.model.ShoppingOrder getOrder(
		long groupId, long orderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getOrder(groupId, orderId);
	}

	public static void sendEmail(long groupId, long orderId,
		java.lang.String emailType,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().sendEmail(groupId, orderId, emailType, serviceContext);
	}

	public static com.liferay.portlet.shopping.model.ShoppingOrder updateOrder(
		long groupId, long orderId, java.lang.String ppTxnId,
		java.lang.String ppPaymentStatus, double ppPaymentGross,
		java.lang.String ppReceiverEmail, java.lang.String ppPayerEmail)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateOrder(groupId, orderId, ppTxnId, ppPaymentStatus,
			ppPaymentGross, ppReceiverEmail, ppPayerEmail);
	}

	public static com.liferay.portlet.shopping.model.ShoppingOrder updateOrder(
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
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
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
		if (_service == null) {
			_service = (ShoppingOrderService)PortalBeanLocatorUtil.locate(ShoppingOrderService.class.getName());

			ReferenceRegistry.registerReference(ShoppingOrderServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setService(ShoppingOrderService service) {
	}

	private static ShoppingOrderService _service;
}
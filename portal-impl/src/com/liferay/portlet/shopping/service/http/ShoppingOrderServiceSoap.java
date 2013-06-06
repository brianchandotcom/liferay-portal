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

package com.liferay.portlet.shopping.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import com.liferay.portlet.shopping.service.ShoppingOrderServiceUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * {@link com.liferay.portlet.shopping.service.ShoppingOrderServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.portlet.shopping.model.ShoppingOrderSoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.portlet.shopping.model.ShoppingOrder}, that is translated to a
 * {@link com.liferay.portlet.shopping.model.ShoppingOrderSoap}. Methods that SOAP cannot
 * safely wire are skipped.
 * </p>
 *
 * <p>
 * The benefits of using the SOAP utility is that it is cross platform
 * compatible. SOAP allows different languages like Java, .NET, C++, PHP, and
 * even Perl, to call the generated services. One drawback of SOAP is that it is
 * slow because it needs to serialize all calls into a text format (XML).
 * </p>
 *
 * <p>
 * You can see a list of services at http://localhost:8080/api/axis. Set the
 * property <b>axis.servlet.hosts.allowed</b> in portal.properties to configure
 * security.
 * </p>
 *
 * <p>
 * The SOAP utility is only generated for remote services.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ShoppingOrderServiceHttp
 * @see com.liferay.portlet.shopping.model.ShoppingOrderSoap
 * @see com.liferay.portlet.shopping.service.ShoppingOrderServiceUtil
 * @generated
 */
public class ShoppingOrderServiceSoap {
	public static void completeOrder(long groupId, java.lang.String number,
		java.lang.String ppTxnId, java.lang.String ppPaymentStatus,
		double ppPaymentGross, java.lang.String ppReceiverEmail,
		java.lang.String ppPayerEmail,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			ShoppingOrderServiceUtil.completeOrder(groupId, number, ppTxnId,
				ppPaymentStatus, ppPaymentGross, ppReceiverEmail, ppPayerEmail,
				serviceContext);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteOrder(long groupId, long orderId)
		throws RemoteException {
		try {
			ShoppingOrderServiceUtil.deleteOrder(groupId, orderId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.shopping.model.ShoppingOrderSoap getOrder(
		long groupId, long orderId) throws RemoteException {
		try {
			com.liferay.portlet.shopping.model.ShoppingOrder returnValue = ShoppingOrderServiceUtil.getOrder(groupId,
					orderId);

			return com.liferay.portlet.shopping.model.ShoppingOrderSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void sendEmail(long groupId, long orderId,
		java.lang.String emailType,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			ShoppingOrderServiceUtil.sendEmail(groupId, orderId, emailType,
				serviceContext);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.shopping.model.ShoppingOrderSoap updateOrder(
		long groupId, long orderId, java.lang.String ppTxnId,
		java.lang.String ppPaymentStatus, double ppPaymentGross,
		java.lang.String ppReceiverEmail, java.lang.String ppPayerEmail)
		throws RemoteException {
		try {
			com.liferay.portlet.shopping.model.ShoppingOrder returnValue = ShoppingOrderServiceUtil.updateOrder(groupId,
					orderId, ppTxnId, ppPaymentStatus, ppPaymentGross,
					ppReceiverEmail, ppPayerEmail);

			return com.liferay.portlet.shopping.model.ShoppingOrderSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.shopping.model.ShoppingOrderSoap updateOrder(
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
		throws RemoteException {
		try {
			com.liferay.portlet.shopping.model.ShoppingOrder returnValue = ShoppingOrderServiceUtil.updateOrder(groupId,
					orderId, billingFirstName, billingLastName,
					billingEmailAddress, billingCompany, billingStreet,
					billingCity, billingState, billingZip, billingCountry,
					billingPhone, shipToBilling, shippingFirstName,
					shippingLastName, shippingEmailAddress, shippingCompany,
					shippingStreet, shippingCity, shippingState, shippingZip,
					shippingCountry, shippingPhone, ccName, ccType, ccNumber,
					ccExpMonth, ccExpYear, ccVerNumber, comments);

			return com.liferay.portlet.shopping.model.ShoppingOrderSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(ShoppingOrderServiceSoap.class);
}
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

package com.liferay.commerce.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.service.CommerceOrderServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * {@link CommerceOrderServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.commerce.model.CommerceOrderSoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.commerce.model.CommerceOrder}, that is translated to a
 * {@link com.liferay.commerce.model.CommerceOrderSoap}. Methods that SOAP cannot
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
 * @author Alessio Antonio Rendina
 * @see CommerceOrderServiceHttp
 * @see com.liferay.commerce.model.CommerceOrderSoap
 * @see CommerceOrderServiceUtil
 * @generated
 */
@ProviderType
public class CommerceOrderServiceSoap {
	public static com.liferay.commerce.model.CommerceOrderSoap addCommerceOrderFromCart(
		long commerceCartId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.commerce.model.CommerceOrder returnValue = CommerceOrderServiceUtil.addCommerceOrderFromCart(commerceCartId,
					serviceContext);

			return com.liferay.commerce.model.CommerceOrderSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteCommerceOrder(long commerceOrderId)
		throws RemoteException {
		try {
			CommerceOrderServiceUtil.deleteCommerceOrder(commerceOrderId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceOrderSoap fetchCommerceOrder(
		long commerceOrderId) throws RemoteException {
		try {
			com.liferay.commerce.model.CommerceOrder returnValue = CommerceOrderServiceUtil.fetchCommerceOrder(commerceOrderId);

			return com.liferay.commerce.model.CommerceOrderSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceOrderSoap getCommerceOrder(
		long commerceOrderId) throws RemoteException {
		try {
			com.liferay.commerce.model.CommerceOrder returnValue = CommerceOrderServiceUtil.getCommerceOrder(commerceOrderId);

			return com.liferay.commerce.model.CommerceOrderSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceOrderSoap getCommerceOrderByUuidAndGroupId(
		java.lang.String uuid, long groupId) throws RemoteException {
		try {
			com.liferay.commerce.model.CommerceOrder returnValue = CommerceOrderServiceUtil.getCommerceOrderByUuidAndGroupId(uuid,
					groupId);

			return com.liferay.commerce.model.CommerceOrderSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceOrderSoap[] getCommerceOrders(
		long groupId, long orderUserId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceOrder> orderByComparator)
		throws RemoteException {
		try {
			java.util.List<com.liferay.commerce.model.CommerceOrder> returnValue =
				CommerceOrderServiceUtil.getCommerceOrders(groupId,
					orderUserId, start, end, orderByComparator);

			return com.liferay.commerce.model.CommerceOrderSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getCommerceOrdersCount(long groupId, long orderUserId)
		throws RemoteException {
		try {
			int returnValue = CommerceOrderServiceUtil.getCommerceOrdersCount(groupId,
					orderUserId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceOrderSoap updateBillingAddress(
		long commerceOrderId, java.lang.String name,
		java.lang.String description, java.lang.String street1,
		java.lang.String street2, java.lang.String street3,
		java.lang.String city, java.lang.String zip, long commerceRegionId,
		long commerceCountryId, java.lang.String phoneNumber,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.commerce.model.CommerceOrder returnValue = CommerceOrderServiceUtil.updateBillingAddress(commerceOrderId,
					name, description, street1, street2, street3, city, zip,
					commerceRegionId, commerceCountryId, phoneNumber,
					serviceContext);

			return com.liferay.commerce.model.CommerceOrderSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceOrderSoap updateCommerceOrder(
		long commerceOrderId, long commercePaymentMethodId,
		java.lang.String purchaseOrderNumber, double subtotal,
		double shippingPrice, double total, int paymentStatus, int orderStatus)
		throws RemoteException {
		try {
			com.liferay.commerce.model.CommerceOrder returnValue = CommerceOrderServiceUtil.updateCommerceOrder(commerceOrderId,
					commercePaymentMethodId, purchaseOrderNumber, subtotal,
					shippingPrice, total, paymentStatus, orderStatus);

			return com.liferay.commerce.model.CommerceOrderSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceOrderSoap updatePurchaseOrderNumber(
		long commerceOrderId, java.lang.String purchaseOrderNumber)
		throws RemoteException {
		try {
			com.liferay.commerce.model.CommerceOrder returnValue = CommerceOrderServiceUtil.updatePurchaseOrderNumber(commerceOrderId,
					purchaseOrderNumber);

			return com.liferay.commerce.model.CommerceOrderSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceOrderSoap updateShippingAddress(
		long commerceOrderId, java.lang.String name,
		java.lang.String description, java.lang.String street1,
		java.lang.String street2, java.lang.String street3,
		java.lang.String city, java.lang.String zip, long commerceRegionId,
		long commerceCountryId, java.lang.String phoneNumber,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.commerce.model.CommerceOrder returnValue = CommerceOrderServiceUtil.updateShippingAddress(commerceOrderId,
					name, description, street1, street2, street3, city, zip,
					commerceRegionId, commerceCountryId, phoneNumber,
					serviceContext);

			return com.liferay.commerce.model.CommerceOrderSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CommerceOrderServiceSoap.class);
}
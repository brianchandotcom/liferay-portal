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

import com.liferay.commerce.service.CommerceCartServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * {@link CommerceCartServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.commerce.model.CommerceCartSoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.commerce.model.CommerceCart}, that is translated to a
 * {@link com.liferay.commerce.model.CommerceCartSoap}. Methods that SOAP cannot
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
 * @see CommerceCartServiceHttp
 * @see com.liferay.commerce.model.CommerceCartSoap
 * @see CommerceCartServiceUtil
 * @generated
 */
@ProviderType
public class CommerceCartServiceSoap {
	public static com.liferay.commerce.model.CommerceCartSoap addCommerceCart(
		java.lang.String name, boolean defaultCart,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.commerce.model.CommerceCart returnValue = CommerceCartServiceUtil.addCommerceCart(name,
					defaultCart, serviceContext);

			return com.liferay.commerce.model.CommerceCartSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteCommerceCart(long commerceCartId)
		throws RemoteException {
		try {
			CommerceCartServiceUtil.deleteCommerceCart(commerceCartId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceCartSoap fetchCommerceCart(
		long commerceCartId) throws RemoteException {
		try {
			com.liferay.commerce.model.CommerceCart returnValue = CommerceCartServiceUtil.fetchCommerceCart(commerceCartId);

			return com.liferay.commerce.model.CommerceCartSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceCartSoap fetchCommerceCart(
		java.lang.String uuid, long groupId) throws RemoteException {
		try {
			com.liferay.commerce.model.CommerceCart returnValue = CommerceCartServiceUtil.fetchCommerceCart(uuid,
					groupId);

			return com.liferay.commerce.model.CommerceCartSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceCartSoap fetchDefaultCommerceCart(
		long groupId, long userId, boolean defaultCart)
		throws RemoteException {
		try {
			com.liferay.commerce.model.CommerceCart returnValue = CommerceCartServiceUtil.fetchDefaultCommerceCart(groupId,
					userId, defaultCart);

			return com.liferay.commerce.model.CommerceCartSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceCartSoap getCommerceCart(
		long commerceCartId) throws RemoteException {
		try {
			com.liferay.commerce.model.CommerceCart returnValue = CommerceCartServiceUtil.getCommerceCart(commerceCartId);

			return com.liferay.commerce.model.CommerceCartSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceCartSoap[] getCommerceCarts(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceCart> orderByComparator)
		throws RemoteException {
		try {
			java.util.List<com.liferay.commerce.model.CommerceCart> returnValue = CommerceCartServiceUtil.getCommerceCarts(groupId,
					start, end, orderByComparator);

			return com.liferay.commerce.model.CommerceCartSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getCommerceCartsCount(long groupId)
		throws RemoteException {
		try {
			int returnValue = CommerceCartServiceUtil.getCommerceCartsCount(groupId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void mergeGuestCommerceCart(long guestCommerceCartId,
		long userCommerceCartId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			CommerceCartServiceUtil.mergeGuestCommerceCart(guestCommerceCartId,
				userCommerceCartId, serviceContext);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceCartSoap updateCommerceCart(
		long commerceCartId, long billingAddressId, long shippingAddressId,
		long commercePaymentMethodId, long commerceShippingMethodId,
		java.lang.String shippingOptionName, double shippingPrice)
		throws RemoteException {
		try {
			com.liferay.commerce.model.CommerceCart returnValue = CommerceCartServiceUtil.updateCommerceCart(commerceCartId,
					billingAddressId, shippingAddressId,
					commercePaymentMethodId, commerceShippingMethodId,
					shippingOptionName, shippingPrice);

			return com.liferay.commerce.model.CommerceCartSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceCartSoap updateCommerceCart(
		long commerceCartId, java.lang.String name, boolean defaultCart)
		throws RemoteException {
		try {
			com.liferay.commerce.model.CommerceCart returnValue = CommerceCartServiceUtil.updateCommerceCart(commerceCartId,
					name, defaultCart);

			return com.liferay.commerce.model.CommerceCartSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceCartSoap updateUser(
		long commerceCartId, long userId) throws RemoteException {
		try {
			com.liferay.commerce.model.CommerceCart returnValue = CommerceCartServiceUtil.updateUser(commerceCartId,
					userId);

			return com.liferay.commerce.model.CommerceCartSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CommerceCartServiceSoap.class);
}
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

import com.liferay.commerce.service.CommercePaymentMethodServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * {@link CommercePaymentMethodServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.commerce.model.CommercePaymentMethodSoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.commerce.model.CommercePaymentMethod}, that is translated to a
 * {@link com.liferay.commerce.model.CommercePaymentMethodSoap}. Methods that SOAP cannot
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
 * @see CommercePaymentMethodServiceHttp
 * @see com.liferay.commerce.model.CommercePaymentMethodSoap
 * @see CommercePaymentMethodServiceUtil
 * @generated
 */
@ProviderType
public class CommercePaymentMethodServiceSoap {
	public static com.liferay.commerce.model.CommercePaymentMethodSoap createCommercePaymentMethod(
		long commercePaymentMethodId) throws RemoteException {
		try {
			com.liferay.commerce.model.CommercePaymentMethod returnValue = CommercePaymentMethodServiceUtil.createCommercePaymentMethod(commercePaymentMethodId);

			return com.liferay.commerce.model.CommercePaymentMethodSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteCommercePaymentMethod(long commercePaymentMethodId)
		throws RemoteException {
		try {
			CommercePaymentMethodServiceUtil.deleteCommercePaymentMethod(commercePaymentMethodId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommercePaymentMethodSoap getCommercePaymentMethod(
		long commercePaymentMethodId) throws RemoteException {
		try {
			com.liferay.commerce.model.CommercePaymentMethod returnValue = CommercePaymentMethodServiceUtil.getCommercePaymentMethod(commercePaymentMethodId);

			return com.liferay.commerce.model.CommercePaymentMethodSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommercePaymentMethodSoap[] getCommercePaymentMethods(
		long groupId) throws RemoteException {
		try {
			java.util.List<com.liferay.commerce.model.CommercePaymentMethod> returnValue =
				CommercePaymentMethodServiceUtil.getCommercePaymentMethods(groupId);

			return com.liferay.commerce.model.CommercePaymentMethodSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommercePaymentMethodSoap[] getCommercePaymentMethods(
		long groupId, boolean active) throws RemoteException {
		try {
			java.util.List<com.liferay.commerce.model.CommercePaymentMethod> returnValue =
				CommercePaymentMethodServiceUtil.getCommercePaymentMethods(groupId,
					active);

			return com.liferay.commerce.model.CommercePaymentMethodSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommercePaymentMethodSoap[] getCommercePaymentMethods(
		long groupId, long commerceCountryId, boolean active)
		throws RemoteException {
		try {
			java.util.List<com.liferay.commerce.model.CommercePaymentMethod> returnValue =
				CommercePaymentMethodServiceUtil.getCommercePaymentMethods(groupId,
					commerceCountryId, active);

			return com.liferay.commerce.model.CommercePaymentMethodSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getCommercePaymentMethodsCount(long groupId,
		boolean active) throws RemoteException {
		try {
			int returnValue = CommercePaymentMethodServiceUtil.getCommercePaymentMethodsCount(groupId,
					active);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommercePaymentMethodSoap setActive(
		long commercePaymentMethodId, boolean active) throws RemoteException {
		try {
			com.liferay.commerce.model.CommercePaymentMethod returnValue = CommercePaymentMethodServiceUtil.setActive(commercePaymentMethodId,
					active);

			return com.liferay.commerce.model.CommercePaymentMethodSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CommercePaymentMethodServiceSoap.class);
}
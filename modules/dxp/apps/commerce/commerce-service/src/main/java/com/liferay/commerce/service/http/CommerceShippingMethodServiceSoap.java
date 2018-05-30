/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.service.CommerceShippingMethodServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * {@link CommerceShippingMethodServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.commerce.model.CommerceShippingMethodSoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.commerce.model.CommerceShippingMethod}, that is translated to a
 * {@link com.liferay.commerce.model.CommerceShippingMethodSoap}. Methods that SOAP cannot
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
 * @see CommerceShippingMethodServiceHttp
 * @see com.liferay.commerce.model.CommerceShippingMethodSoap
 * @see CommerceShippingMethodServiceUtil
 * @generated
 */
@ProviderType
public class CommerceShippingMethodServiceSoap {
	public static com.liferay.commerce.model.CommerceShippingMethodSoap createCommerceShippingMethod(
		long commerceShippingMethodId) throws RemoteException {
		try {
			com.liferay.commerce.model.CommerceShippingMethod returnValue = CommerceShippingMethodServiceUtil.createCommerceShippingMethod(commerceShippingMethodId);

			return com.liferay.commerce.model.CommerceShippingMethodSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteCommerceShippingMethod(
		long commerceShippingMethodId) throws RemoteException {
		try {
			CommerceShippingMethodServiceUtil.deleteCommerceShippingMethod(commerceShippingMethodId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceShippingMethodSoap fetchCommerceShippingMethod(
		long groupId, String engineKey) throws RemoteException {
		try {
			com.liferay.commerce.model.CommerceShippingMethod returnValue = CommerceShippingMethodServiceUtil.fetchCommerceShippingMethod(groupId,
					engineKey);

			return com.liferay.commerce.model.CommerceShippingMethodSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceShippingMethodSoap getCommerceShippingMethod(
		long commerceShippingMethodId) throws RemoteException {
		try {
			com.liferay.commerce.model.CommerceShippingMethod returnValue = CommerceShippingMethodServiceUtil.getCommerceShippingMethod(commerceShippingMethodId);

			return com.liferay.commerce.model.CommerceShippingMethodSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceShippingMethodSoap[] getCommerceShippingMethods(
		long groupId) throws RemoteException {
		try {
			java.util.List<com.liferay.commerce.model.CommerceShippingMethod> returnValue =
				CommerceShippingMethodServiceUtil.getCommerceShippingMethods(groupId);

			return com.liferay.commerce.model.CommerceShippingMethodSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceShippingMethodSoap[] getCommerceShippingMethods(
		long groupId, boolean active) throws RemoteException {
		try {
			java.util.List<com.liferay.commerce.model.CommerceShippingMethod> returnValue =
				CommerceShippingMethodServiceUtil.getCommerceShippingMethods(groupId,
					active);

			return com.liferay.commerce.model.CommerceShippingMethodSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getCommerceShippingMethodsCount(long groupId,
		boolean active) throws RemoteException {
		try {
			int returnValue = CommerceShippingMethodServiceUtil.getCommerceShippingMethodsCount(groupId,
					active);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceShippingMethodSoap setActive(
		long commerceShippingMethodId, boolean active)
		throws RemoteException {
		try {
			com.liferay.commerce.model.CommerceShippingMethod returnValue = CommerceShippingMethodServiceUtil.setActive(commerceShippingMethodId,
					active);

			return com.liferay.commerce.model.CommerceShippingMethodSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CommerceShippingMethodServiceSoap.class);
}
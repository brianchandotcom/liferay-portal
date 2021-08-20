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

import com.liferay.commerce.service.CommerceShippingMethodServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>CommerceShippingMethodServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.commerce.model.CommerceShippingMethodSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.commerce.model.CommerceShippingMethod</code>, that is translated to a
 * <code>com.liferay.commerce.model.CommerceShippingMethodSoap</code>. Methods that SOAP
 * cannot safely wire are skipped.
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
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class CommerceShippingMethodServiceSoap {

	public static com.liferay.commerce.model.CommerceAddressRestrictionSoap
			addCommerceAddressRestriction(
				long groupId, long commerceShippingMethodId, long countryId)
		throws RemoteException {

		try {
			com.liferay.commerce.model.CommerceAddressRestriction returnValue =
				CommerceShippingMethodServiceUtil.addCommerceAddressRestriction(
					groupId, commerceShippingMethodId, countryId);

			return com.liferay.commerce.model.CommerceAddressRestrictionSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public static com.liferay.commerce.model.CommerceAddressRestrictionSoap
			addCommerceAddressRestriction(
				long commerceShippingMethodId, long countryId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.commerce.model.CommerceAddressRestriction returnValue =
				CommerceShippingMethodServiceUtil.addCommerceAddressRestriction(
					commerceShippingMethodId, countryId, serviceContext);

			return com.liferay.commerce.model.CommerceAddressRestrictionSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceShippingMethodSoap
			createCommerceShippingMethod(long commerceShippingMethodId)
		throws RemoteException {

		try {
			com.liferay.commerce.model.CommerceShippingMethod returnValue =
				CommerceShippingMethodServiceUtil.createCommerceShippingMethod(
					commerceShippingMethodId);

			return com.liferay.commerce.model.CommerceShippingMethodSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void deleteCommerceAddressRestriction(
			long commerceAddressRestrictionId)
		throws RemoteException {

		try {
			CommerceShippingMethodServiceUtil.deleteCommerceAddressRestriction(
				commerceAddressRestrictionId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void deleteCommerceAddressRestrictions(
			long commerceShippingMethodId)
		throws RemoteException {

		try {
			CommerceShippingMethodServiceUtil.deleteCommerceAddressRestrictions(
				commerceShippingMethodId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void deleteCommerceShippingMethod(
			long commerceShippingMethodId)
		throws RemoteException {

		try {
			CommerceShippingMethodServiceUtil.deleteCommerceShippingMethod(
				commerceShippingMethodId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceShippingMethodSoap
			fetchCommerceShippingMethod(long groupId, String engineKey)
		throws RemoteException {

		try {
			com.liferay.commerce.model.CommerceShippingMethod returnValue =
				CommerceShippingMethodServiceUtil.fetchCommerceShippingMethod(
					groupId, engineKey);

			return com.liferay.commerce.model.CommerceShippingMethodSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceAddressRestrictionSoap[]
			getCommerceAddressRestrictions(
				long commerceShippingMethodId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.commerce.model.CommerceAddressRestriction>
						orderByComparator)
		throws RemoteException {

		try {
			java.util.List
				<com.liferay.commerce.model.CommerceAddressRestriction>
					returnValue =
						CommerceShippingMethodServiceUtil.
							getCommerceAddressRestrictions(
								commerceShippingMethodId, start, end,
								orderByComparator);

			return com.liferay.commerce.model.CommerceAddressRestrictionSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getCommerceAddressRestrictionsCount(
			long commerceShippingMethodId)
		throws RemoteException {

		try {
			int returnValue =
				CommerceShippingMethodServiceUtil.
					getCommerceAddressRestrictionsCount(
						commerceShippingMethodId);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceShippingMethodSoap
			getCommerceShippingMethod(long commerceShippingMethodId)
		throws RemoteException {

		try {
			com.liferay.commerce.model.CommerceShippingMethod returnValue =
				CommerceShippingMethodServiceUtil.getCommerceShippingMethod(
					commerceShippingMethodId);

			return com.liferay.commerce.model.CommerceShippingMethodSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceShippingMethodSoap[]
			getCommerceShippingMethods(long groupId)
		throws RemoteException {

		try {
			java.util.List<com.liferay.commerce.model.CommerceShippingMethod>
				returnValue =
					CommerceShippingMethodServiceUtil.
						getCommerceShippingMethods(groupId);

			return com.liferay.commerce.model.CommerceShippingMethodSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceShippingMethodSoap[]
			getCommerceShippingMethods(long groupId, boolean active)
		throws RemoteException {

		try {
			java.util.List<com.liferay.commerce.model.CommerceShippingMethod>
				returnValue =
					CommerceShippingMethodServiceUtil.
						getCommerceShippingMethods(groupId, active);

			return com.liferay.commerce.model.CommerceShippingMethodSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceShippingMethodSoap[]
			getCommerceShippingMethods(
				long groupId, long countryId, boolean active)
		throws RemoteException {

		try {
			java.util.List<com.liferay.commerce.model.CommerceShippingMethod>
				returnValue =
					CommerceShippingMethodServiceUtil.
						getCommerceShippingMethods(groupId, countryId, active);

			return com.liferay.commerce.model.CommerceShippingMethodSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getCommerceShippingMethodsCount(
			long groupId, boolean active)
		throws RemoteException {

		try {
			int returnValue =
				CommerceShippingMethodServiceUtil.
					getCommerceShippingMethodsCount(groupId, active);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceShippingMethodSoap
			setActive(long commerceShippingMethodId, boolean active)
		throws RemoteException {

		try {
			com.liferay.commerce.model.CommerceShippingMethod returnValue =
				CommerceShippingMethodServiceUtil.setActive(
					commerceShippingMethodId, active);

			return com.liferay.commerce.model.CommerceShippingMethodSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CommerceShippingMethodServiceSoap.class);

}
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

import com.liferay.commerce.service.CommerceRegionServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>CommerceRegionServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.commerce.model.CommerceRegionSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.commerce.model.CommerceRegion</code>, that is translated to a
 * <code>com.liferay.commerce.model.CommerceRegionSoap</code>. Methods that SOAP
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
 * @see CommerceRegionServiceHttp
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class CommerceRegionServiceSoap {

	public static com.liferay.commerce.model.CommerceRegionSoap
			addCommerceRegion(
				long commerceCountryId, String name, String code,
				double priority, boolean active,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.commerce.model.CommerceRegion returnValue =
				CommerceRegionServiceUtil.addCommerceRegion(
					commerceCountryId, name, code, priority, active,
					serviceContext);

			return com.liferay.commerce.model.CommerceRegionSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void deleteCommerceRegion(long commerceRegionId)
		throws RemoteException {

		try {
			CommerceRegionServiceUtil.deleteCommerceRegion(commerceRegionId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceRegionSoap
			getCommerceRegion(long commerceRegionId)
		throws RemoteException {

		try {
			com.liferay.commerce.model.CommerceRegion returnValue =
				CommerceRegionServiceUtil.getCommerceRegion(commerceRegionId);

			return com.liferay.commerce.model.CommerceRegionSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceRegionSoap[]
			getCommerceRegions(long commerceCountryId, boolean active)
		throws RemoteException {

		try {
			java.util.List<com.liferay.commerce.model.CommerceRegion>
				returnValue = CommerceRegionServiceUtil.getCommerceRegions(
					commerceCountryId, active);

			return com.liferay.commerce.model.CommerceRegionSoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceRegionSoap[]
			getCommerceRegions(
				long commerceCountryId, boolean active, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.commerce.model.CommerceRegion>
						orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.commerce.model.CommerceRegion>
				returnValue = CommerceRegionServiceUtil.getCommerceRegions(
					commerceCountryId, active, start, end, orderByComparator);

			return com.liferay.commerce.model.CommerceRegionSoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceRegionSoap[]
			getCommerceRegions(
				long commerceCountryId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.commerce.model.CommerceRegion>
						orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.commerce.model.CommerceRegion>
				returnValue = CommerceRegionServiceUtil.getCommerceRegions(
					commerceCountryId, start, end, orderByComparator);

			return com.liferay.commerce.model.CommerceRegionSoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceRegionSoap[]
			getCommerceRegions(
				long companyId, String countryTwoLettersISOCode, boolean active)
		throws RemoteException {

		try {
			java.util.List<com.liferay.commerce.model.CommerceRegion>
				returnValue = CommerceRegionServiceUtil.getCommerceRegions(
					companyId, countryTwoLettersISOCode, active);

			return com.liferay.commerce.model.CommerceRegionSoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getCommerceRegionsCount(long commerceCountryId)
		throws RemoteException {

		try {
			int returnValue = CommerceRegionServiceUtil.getCommerceRegionsCount(
				commerceCountryId);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getCommerceRegionsCount(
			long commerceCountryId, boolean active)
		throws RemoteException {

		try {
			int returnValue = CommerceRegionServiceUtil.getCommerceRegionsCount(
				commerceCountryId, active);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceRegionSoap setActive(
			long commerceRegionId, boolean active)
		throws RemoteException {

		try {
			com.liferay.commerce.model.CommerceRegion returnValue =
				CommerceRegionServiceUtil.setActive(commerceRegionId, active);

			return com.liferay.commerce.model.CommerceRegionSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceRegionSoap
			updateCommerceRegion(
				long commerceRegionId, String name, String code,
				double priority, boolean active,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.commerce.model.CommerceRegion returnValue =
				CommerceRegionServiceUtil.updateCommerceRegion(
					commerceRegionId, name, code, priority, active,
					serviceContext);

			return com.liferay.commerce.model.CommerceRegionSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CommerceRegionServiceSoap.class);

}
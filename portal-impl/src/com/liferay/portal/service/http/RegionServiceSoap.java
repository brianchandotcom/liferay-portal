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

package com.liferay.portal.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.RegionServiceUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>RegionServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.portal.kernel.model.RegionSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.portal.kernel.model.Region</code>, that is translated to a
 * <code>com.liferay.portal.kernel.model.RegionSoap</code>. Methods that SOAP
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
 * @author Brian Wing Shun Chan
 * @see RegionServiceHttp
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class RegionServiceSoap {

	public static com.liferay.portal.kernel.model.RegionSoap addRegion(
			long countryId, boolean active, String name, double position,
			String regionCode,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.portal.kernel.model.Region returnValue =
				RegionServiceUtil.addRegion(
					countryId, active, name, position, regionCode,
					serviceContext);

			return com.liferay.portal.kernel.model.RegionSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x)
	 */
	@Deprecated
	public static com.liferay.portal.kernel.model.RegionSoap addRegion(
			long countryId, String regionCode, String name, boolean active)
		throws RemoteException {

		try {
			com.liferay.portal.kernel.model.Region returnValue =
				RegionServiceUtil.addRegion(
					countryId, regionCode, name, active);

			return com.liferay.portal.kernel.model.RegionSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void deleteRegion(long regionId) throws RemoteException {
		try {
			RegionServiceUtil.deleteRegion(regionId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.portal.kernel.model.RegionSoap fetchRegion(
			long regionId)
		throws RemoteException {

		try {
			com.liferay.portal.kernel.model.Region returnValue =
				RegionServiceUtil.fetchRegion(regionId);

			return com.liferay.portal.kernel.model.RegionSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.portal.kernel.model.RegionSoap fetchRegion(
			long countryId, String regionCode)
		throws RemoteException {

		try {
			com.liferay.portal.kernel.model.Region returnValue =
				RegionServiceUtil.fetchRegion(countryId, regionCode);

			return com.liferay.portal.kernel.model.RegionSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.portal.kernel.model.RegionSoap getRegion(
			long regionId)
		throws RemoteException {

		try {
			com.liferay.portal.kernel.model.Region returnValue =
				RegionServiceUtil.getRegion(regionId);

			return com.liferay.portal.kernel.model.RegionSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.portal.kernel.model.RegionSoap getRegion(
			long countryId, String regionCode)
		throws RemoteException {

		try {
			com.liferay.portal.kernel.model.Region returnValue =
				RegionServiceUtil.getRegion(countryId, regionCode);

			return com.liferay.portal.kernel.model.RegionSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.portal.kernel.model.RegionSoap[] getRegions()
		throws RemoteException {

		try {
			java.util.List<com.liferay.portal.kernel.model.Region> returnValue =
				RegionServiceUtil.getRegions();

			return com.liferay.portal.kernel.model.RegionSoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.portal.kernel.model.RegionSoap[] getRegions(
			boolean active)
		throws RemoteException {

		try {
			java.util.List<com.liferay.portal.kernel.model.Region> returnValue =
				RegionServiceUtil.getRegions(active);

			return com.liferay.portal.kernel.model.RegionSoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.portal.kernel.model.RegionSoap[] getRegions(
			long countryId)
		throws RemoteException {

		try {
			java.util.List<com.liferay.portal.kernel.model.Region> returnValue =
				RegionServiceUtil.getRegions(countryId);

			return com.liferay.portal.kernel.model.RegionSoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.portal.kernel.model.RegionSoap[] getRegions(
			long countryId, boolean active)
		throws RemoteException {

		try {
			java.util.List<com.liferay.portal.kernel.model.Region> returnValue =
				RegionServiceUtil.getRegions(countryId, active);

			return com.liferay.portal.kernel.model.RegionSoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.portal.kernel.model.RegionSoap[] getRegions(
			long countryId, boolean active, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.portal.kernel.model.Region> orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.portal.kernel.model.Region> returnValue =
				RegionServiceUtil.getRegions(
					countryId, active, start, end, orderByComparator);

			return com.liferay.portal.kernel.model.RegionSoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.portal.kernel.model.RegionSoap[] getRegions(
			long countryId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.portal.kernel.model.Region> orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.portal.kernel.model.Region> returnValue =
				RegionServiceUtil.getRegions(
					countryId, start, end, orderByComparator);

			return com.liferay.portal.kernel.model.RegionSoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.portal.kernel.model.RegionSoap[] getRegions(
			long companyId, String a2, boolean active)
		throws RemoteException {

		try {
			java.util.List<com.liferay.portal.kernel.model.Region> returnValue =
				RegionServiceUtil.getRegions(companyId, a2, active);

			return com.liferay.portal.kernel.model.RegionSoap.toSoapModels(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getRegionsCount(long countryId) throws RemoteException {
		try {
			int returnValue = RegionServiceUtil.getRegionsCount(countryId);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getRegionsCount(long countryId, boolean active)
		throws RemoteException {

		try {
			int returnValue = RegionServiceUtil.getRegionsCount(
				countryId, active);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.portal.kernel.model.RegionSoap updateActive(
			long regionId, boolean active)
		throws RemoteException {

		try {
			com.liferay.portal.kernel.model.Region returnValue =
				RegionServiceUtil.updateActive(regionId, active);

			return com.liferay.portal.kernel.model.RegionSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.portal.kernel.model.RegionSoap updateRegion(
			long regionId, boolean active, String name, double position,
			String regionCode)
		throws RemoteException {

		try {
			com.liferay.portal.kernel.model.Region returnValue =
				RegionServiceUtil.updateRegion(
					regionId, active, name, position, regionCode);

			return com.liferay.portal.kernel.model.RegionSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(RegionServiceSoap.class);

}
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

import com.liferay.commerce.service.CommerceWarehouseServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * {@link CommerceWarehouseServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.commerce.model.CommerceWarehouseSoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.commerce.model.CommerceWarehouse}, that is translated to a
 * {@link com.liferay.commerce.model.CommerceWarehouseSoap}. Methods that SOAP cannot
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
 * @see CommerceWarehouseServiceHttp
 * @see com.liferay.commerce.model.CommerceWarehouseSoap
 * @see CommerceWarehouseServiceUtil
 * @generated
 */
@ProviderType
public class CommerceWarehouseServiceSoap {
	public static com.liferay.commerce.model.CommerceWarehouseSoap addCommerceWarehouse(
		java.lang.String name, java.lang.String description, boolean active,
		java.lang.String street1, java.lang.String street2,
		java.lang.String street3, java.lang.String city, java.lang.String zip,
		long commerceRegionId, long commerceCountryId, double latitude,
		double longitude,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.commerce.model.CommerceWarehouse returnValue = CommerceWarehouseServiceUtil.addCommerceWarehouse(name,
					description, active, street1, street2, street3, city, zip,
					commerceRegionId, commerceCountryId, latitude, longitude,
					serviceContext);

			return com.liferay.commerce.model.CommerceWarehouseSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteCommerceWarehouse(long commerceWarehouseId)
		throws RemoteException {
		try {
			CommerceWarehouseServiceUtil.deleteCommerceWarehouse(commerceWarehouseId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceWarehouseSoap geolocateCommerceWarehouse(
		long commerceWarehouseId) throws RemoteException {
		try {
			com.liferay.commerce.model.CommerceWarehouse returnValue = CommerceWarehouseServiceUtil.geolocateCommerceWarehouse(commerceWarehouseId);

			return com.liferay.commerce.model.CommerceWarehouseSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceWarehouseSoap getCommerceWarehouse(
		long commerceWarehouseId) throws RemoteException {
		try {
			com.liferay.commerce.model.CommerceWarehouse returnValue = CommerceWarehouseServiceUtil.getCommerceWarehouse(commerceWarehouseId);

			return com.liferay.commerce.model.CommerceWarehouseSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceWarehouseSoap[] getCommerceWarehouses(
		long groupId, boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceWarehouse> orderByComparator)
		throws RemoteException {
		try {
			java.util.List<com.liferay.commerce.model.CommerceWarehouse> returnValue =
				CommerceWarehouseServiceUtil.getCommerceWarehouses(groupId,
					active, start, end, orderByComparator);

			return com.liferay.commerce.model.CommerceWarehouseSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceWarehouseSoap[] getCommerceWarehouses(
		long groupId, boolean active, long commerceCountryId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceWarehouse> orderByComparator)
		throws RemoteException {
		try {
			java.util.List<com.liferay.commerce.model.CommerceWarehouse> returnValue =
				CommerceWarehouseServiceUtil.getCommerceWarehouses(groupId,
					active, commerceCountryId, start, end, orderByComparator);

			return com.liferay.commerce.model.CommerceWarehouseSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceWarehouseSoap[] getCommerceWarehouses(
		long groupId, long commerceCountryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceWarehouse> orderByComparator)
		throws RemoteException {
		try {
			java.util.List<com.liferay.commerce.model.CommerceWarehouse> returnValue =
				CommerceWarehouseServiceUtil.getCommerceWarehouses(groupId,
					commerceCountryId, start, end, orderByComparator);

			return com.liferay.commerce.model.CommerceWarehouseSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getCommerceWarehousesCount(long groupId, boolean active,
		long commerceCountryId) throws RemoteException {
		try {
			int returnValue = CommerceWarehouseServiceUtil.getCommerceWarehousesCount(groupId,
					active, commerceCountryId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getCommerceWarehousesCount(long groupId,
		long commerceCountryId) throws RemoteException {
		try {
			int returnValue = CommerceWarehouseServiceUtil.getCommerceWarehousesCount(groupId,
					commerceCountryId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceWarehouseSoap getDefaultCommerceWarehouse(
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.commerce.model.CommerceWarehouse returnValue = CommerceWarehouseServiceUtil.getDefaultCommerceWarehouse(serviceContext);

			return com.liferay.commerce.model.CommerceWarehouseSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceWarehouseSoap[] search(
		long groupId, java.lang.String keywords, boolean all,
		long commerceCountryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceWarehouse> orderByComparator)
		throws RemoteException {
		try {
			java.util.List<com.liferay.commerce.model.CommerceWarehouse> returnValue =
				CommerceWarehouseServiceUtil.search(groupId, keywords, all,
					commerceCountryId, start, end, orderByComparator);

			return com.liferay.commerce.model.CommerceWarehouseSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int searchCount(long groupId, java.lang.String keywords,
		boolean all, long commerceCountryId) throws RemoteException {
		try {
			int returnValue = CommerceWarehouseServiceUtil.searchCount(groupId,
					keywords, all, commerceCountryId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceWarehouseSoap setActive(
		long commerceWarehouseId, boolean active) throws RemoteException {
		try {
			com.liferay.commerce.model.CommerceWarehouse returnValue = CommerceWarehouseServiceUtil.setActive(commerceWarehouseId,
					active);

			return com.liferay.commerce.model.CommerceWarehouseSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceWarehouseSoap updateCommerceWarehouse(
		long commerceWarehouseId, java.lang.String name,
		java.lang.String description, boolean active, java.lang.String street1,
		java.lang.String street2, java.lang.String street3,
		java.lang.String city, java.lang.String zip, long commerceRegionId,
		long commerceCountryId, double latitude, double longitude,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.commerce.model.CommerceWarehouse returnValue = CommerceWarehouseServiceUtil.updateCommerceWarehouse(commerceWarehouseId,
					name, description, active, street1, street2, street3, city,
					zip, commerceRegionId, commerceCountryId, latitude,
					longitude, serviceContext);

			return com.liferay.commerce.model.CommerceWarehouseSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceWarehouseSoap updateDefaultCommerceWarehouse(
		long commerceWarehouseId, java.lang.String name,
		java.lang.String street1, java.lang.String street2,
		java.lang.String street3, java.lang.String city, java.lang.String zip,
		long commerceRegionId, long commerceCountryId, double latitude,
		double longitude,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.commerce.model.CommerceWarehouse returnValue = CommerceWarehouseServiceUtil.updateDefaultCommerceWarehouse(commerceWarehouseId,
					name, street1, street2, street3, city, zip,
					commerceRegionId, commerceCountryId, latitude, longitude,
					serviceContext);

			return com.liferay.commerce.model.CommerceWarehouseSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CommerceWarehouseServiceSoap.class);
}
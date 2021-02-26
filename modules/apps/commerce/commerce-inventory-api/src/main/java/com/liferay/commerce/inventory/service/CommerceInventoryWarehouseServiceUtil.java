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

package com.liferay.commerce.inventory.service;

import com.liferay.commerce.inventory.model.CommerceInventoryWarehouse;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * Provides the remote service utility for CommerceInventoryWarehouse. This utility wraps
 * <code>com.liferay.commerce.inventory.service.impl.CommerceInventoryWarehouseServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Luca Pellizzon
 * @see CommerceInventoryWarehouseService
 * @generated
 */
public class CommerceInventoryWarehouseServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.inventory.service.impl.CommerceInventoryWarehouseServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 #addCommerceInventoryWarehouse(String, String, String,
	 boolean, String, String, String, String, String, String,
	 String, double, double, serviceContext)}
	 */
	@Deprecated
	public static CommerceInventoryWarehouse addCommerceInventoryWarehouse(
			String name, String description, boolean active, String street1,
			String street2, String street3, String city, String zip,
			String commerceRegionCode, String commerceCountryCode,
			double latitude, double longitude, String externalReferenceCode,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addCommerceInventoryWarehouse(
			name, description, active, street1, street2, street3, city, zip,
			commerceRegionCode, commerceCountryCode, latitude, longitude,
			externalReferenceCode, serviceContext);
	}

	public static CommerceInventoryWarehouse addCommerceInventoryWarehouse(
			String externalReferenceCode, String name, String description,
			boolean active, String street1, String street2, String street3,
			String city, String zip, String commerceRegionCode,
			String commerceCountryCode, double latitude, double longitude,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addCommerceInventoryWarehouse(
			externalReferenceCode, name, description, active, street1, street2,
			street3, city, zip, commerceRegionCode, commerceCountryCode,
			latitude, longitude, serviceContext);
	}

	public static CommerceInventoryWarehouse deleteCommerceInventoryWarehouse(
			long commerceInventoryWarehouseId)
		throws PortalException {

		return getService().deleteCommerceInventoryWarehouse(
			commerceInventoryWarehouseId);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 #fetchByExternalReferenceCode(String, long)}
	 */
	@Deprecated
	public static CommerceInventoryWarehouse fetchByExternalReferenceCode(
			long companyId, String externalReferenceCode)
		throws PortalException {

		return getService().fetchByExternalReferenceCode(
			companyId, externalReferenceCode);
	}

	public static CommerceInventoryWarehouse fetchByExternalReferenceCode(
			String externalReferenceCode, long companyId)
		throws PortalException {

		return getService().fetchByExternalReferenceCode(
			externalReferenceCode, companyId);
	}

	public static CommerceInventoryWarehouse
			geolocateCommerceInventoryWarehouse(
				long commerceInventoryWarehouseId, double latitude,
				double longitude)
		throws PortalException {

		return getService().geolocateCommerceInventoryWarehouse(
			commerceInventoryWarehouseId, latitude, longitude);
	}

	public static CommerceInventoryWarehouse getCommerceInventoryWarehouse(
			long commerceInventoryWarehouseId)
		throws PortalException {

		return getService().getCommerceInventoryWarehouse(
			commerceInventoryWarehouseId);
	}

	public static List<CommerceInventoryWarehouse>
			getCommerceInventoryWarehouses(
				long companyId, boolean active, int start, int end,
				OrderByComparator<CommerceInventoryWarehouse> orderByComparator)
		throws com.liferay.portal.kernel.security.auth.PrincipalException {

		return getService().getCommerceInventoryWarehouses(
			companyId, active, start, end, orderByComparator);
	}

	public static List<CommerceInventoryWarehouse>
			getCommerceInventoryWarehouses(
				long companyId, boolean active, String commerceCountryCode,
				int start, int end,
				OrderByComparator<CommerceInventoryWarehouse> orderByComparator)
		throws PortalException {

		return getService().getCommerceInventoryWarehouses(
			companyId, active, commerceCountryCode, start, end,
			orderByComparator);
	}

	public static List<CommerceInventoryWarehouse>
			getCommerceInventoryWarehouses(
				long companyId, int start, int end,
				OrderByComparator<CommerceInventoryWarehouse> orderByComparator)
		throws PortalException {

		return getService().getCommerceInventoryWarehouses(
			companyId, start, end, orderByComparator);
	}

	public static List<CommerceInventoryWarehouse>
			getCommerceInventoryWarehouses(
				long companyId, long groupId, boolean active)
		throws PortalException {

		return getService().getCommerceInventoryWarehouses(
			companyId, groupId, active);
	}

	public static int getCommerceInventoryWarehousesCount(long companyId)
		throws PortalException {

		return getService().getCommerceInventoryWarehousesCount(companyId);
	}

	public static int getCommerceInventoryWarehousesCount(
			long companyId, boolean active, String commerceCountryCode)
		throws PortalException {

		return getService().getCommerceInventoryWarehousesCount(
			companyId, active, commerceCountryCode);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static List<CommerceInventoryWarehouse>
			searchCommerceInventoryWarehouses(
				long companyId, Boolean active, String commerceCountryCode,
				String keywords, int start, int end,
				com.liferay.portal.kernel.search.Sort sort)
		throws PortalException {

		return getService().searchCommerceInventoryWarehouses(
			companyId, active, commerceCountryCode, keywords, start, end, sort);
	}

	public static int searchCommerceInventoryWarehousesCount(
			long companyId, Boolean active, String commerceCountryCode,
			String keywords)
		throws PortalException {

		return getService().searchCommerceInventoryWarehousesCount(
			companyId, active, commerceCountryCode, keywords);
	}

	public static CommerceInventoryWarehouse setActive(
			long commerceInventoryWarehouseId, boolean active)
		throws PortalException {

		return getService().setActive(commerceInventoryWarehouseId, active);
	}

	public static CommerceInventoryWarehouse updateCommerceInventoryWarehouse(
			long commerceInventoryWarehouseId, String name, String description,
			boolean active, String street1, String street2, String street3,
			String city, String zip, String commerceRegionCode,
			String commerceCountryCode, double latitude, double longitude,
			long mvccVersion,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateCommerceInventoryWarehouse(
			commerceInventoryWarehouseId, name, description, active, street1,
			street2, street3, city, zip, commerceRegionCode,
			commerceCountryCode, latitude, longitude, mvccVersion,
			serviceContext);
	}

	public static CommerceInventoryWarehouseService getService() {
		return _service;
	}

	private static volatile CommerceInventoryWarehouseService _service;

}
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

package com.liferay.commerce.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.osgi.util.ServiceTrackerFactory;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for CommerceWarehouse. This utility wraps
 * {@link com.liferay.commerce.service.impl.CommerceWarehouseServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceWarehouseService
 * @see com.liferay.commerce.service.base.CommerceWarehouseServiceBaseImpl
 * @see com.liferay.commerce.service.impl.CommerceWarehouseServiceImpl
 * @generated
 */
@ProviderType
public class CommerceWarehouseServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.service.impl.CommerceWarehouseServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.commerce.model.CommerceWarehouse addCommerceWarehouse(
		java.lang.String name, java.lang.String description,
		java.lang.String street1, java.lang.String street2,
		java.lang.String street3, java.lang.String city, java.lang.String zip,
		long commerceRegionId, long commerceCountryId, double latitude,
		double longitude,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCommerceWarehouse(name, description, street1, street2,
			street3, city, zip, commerceRegionId, commerceCountryId, latitude,
			longitude, serviceContext);
	}

	public static void deleteCommerceWarehouse(long commerceWarehouseId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteCommerceWarehouse(commerceWarehouseId);
	}

	public static com.liferay.commerce.model.CommerceWarehouse geolocateCommerceWarehouse(
		long commerceWarehouseId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().geolocateCommerceWarehouse(commerceWarehouseId);
	}

	public static com.liferay.commerce.model.CommerceWarehouse getCommerceWarehouse(
		long commerceWarehouseId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCommerceWarehouse(commerceWarehouseId);
	}

	public static java.util.List<com.liferay.commerce.model.CommerceWarehouse> getCommerceWarehouses(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceWarehouse> orderByComparator) {
		return getService()
				   .getCommerceWarehouses(groupId, start, end, orderByComparator);
	}

	public static java.util.List<com.liferay.commerce.model.CommerceWarehouse> getCommerceWarehouses(
		long groupId, long commerceCountryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceWarehouse> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCommerceWarehouses(groupId, commerceCountryId, start,
			end, orderByComparator);
	}

	public static int getCommerceWarehousesCount(long groupId,
		long commerceCountryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCommerceWarehousesCount(groupId, commerceCountryId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static java.util.List<com.liferay.commerce.model.CommerceWarehouse> search(
		long groupId, java.lang.String keywords, long commerceCountryId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceWarehouse> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .search(groupId, keywords, commerceCountryId, start, end,
			orderByComparator);
	}

	public static int searchCount(long groupId, java.lang.String keywords,
		long commerceCountryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().searchCount(groupId, keywords, commerceCountryId);
	}

	public static com.liferay.commerce.model.CommerceWarehouse updateCommerceWarehouse(
		long commerceWarehouseId, java.lang.String name,
		java.lang.String description, java.lang.String street1,
		java.lang.String street2, java.lang.String street3,
		java.lang.String city, java.lang.String zip, long commerceRegionId,
		long commerceCountryId, double latitude, double longitude,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCommerceWarehouse(commerceWarehouseId, name,
			description, street1, street2, street3, city, zip,
			commerceRegionId, commerceCountryId, latitude, longitude,
			serviceContext);
	}

	public static CommerceWarehouseService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceWarehouseService, CommerceWarehouseService> _serviceTracker =
		ServiceTrackerFactory.open(CommerceWarehouseService.class);
}
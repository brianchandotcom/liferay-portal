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

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

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
		String name, String description, boolean active, String street1,
		String street2, String street3, String city, String zip,
		long commerceRegionId, long commerceCountryId, double latitude,
		double longitude,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCommerceWarehouse(name, description, active, street1,
			street2, street3, city, zip, commerceRegionId, commerceCountryId,
			latitude, longitude, serviceContext);
	}

	public static void deleteCommerceWarehouse(long commerceWarehouseId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteCommerceWarehouse(commerceWarehouseId);
	}

	public static com.liferay.commerce.model.CommerceWarehouse fetchDefaultCommerceWarehouse(
		long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().fetchDefaultCommerceWarehouse(groupId);
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
		long groupId, boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceWarehouse> orderByComparator) {
		return getService()
				   .getCommerceWarehouses(groupId, active, start, end,
			orderByComparator);
	}

	public static java.util.List<com.liferay.commerce.model.CommerceWarehouse> getCommerceWarehouses(
		long groupId, boolean active, long commerceCountryId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceWarehouse> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCommerceWarehouses(groupId, active, commerceCountryId,
			start, end, orderByComparator);
	}

	public static java.util.List<com.liferay.commerce.model.CommerceWarehouse> getCommerceWarehouses(
		long groupId, long commerceCountryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceWarehouse> orderByComparator) {
		return getService()
				   .getCommerceWarehouses(groupId, commerceCountryId, start,
			end, orderByComparator);
	}

	public static int getCommerceWarehousesCount(long groupId, boolean active,
		long commerceCountryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCommerceWarehousesCount(groupId, active,
			commerceCountryId);
	}

	public static int getCommerceWarehousesCount(long groupId,
		long commerceCountryId) {
		return getService()
				   .getCommerceWarehousesCount(groupId, commerceCountryId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static java.util.List<com.liferay.commerce.model.CommerceWarehouse> search(
		long groupId, String keywords, boolean all, long commerceCountryId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceWarehouse> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .search(groupId, keywords, all, commerceCountryId, start,
			end, orderByComparator);
	}

	public static int searchCount(long groupId, String keywords, boolean all,
		long commerceCountryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .searchCount(groupId, keywords, all, commerceCountryId);
	}

	public static com.liferay.commerce.model.CommerceWarehouse setActive(
		long commerceWarehouseId, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().setActive(commerceWarehouseId, active);
	}

	public static com.liferay.commerce.model.CommerceWarehouse updateCommerceWarehouse(
		long commerceWarehouseId, String name, String description,
		boolean active, String street1, String street2, String street3,
		String city, String zip, long commerceRegionId, long commerceCountryId,
		double latitude, double longitude,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCommerceWarehouse(commerceWarehouseId, name,
			description, active, street1, street2, street3, city, zip,
			commerceRegionId, commerceCountryId, latitude, longitude,
			serviceContext);
	}

	public static com.liferay.commerce.model.CommerceWarehouse updateDefaultCommerceWarehouse(
		String name, String street1, String street2, String street3,
		String city, String zip, long commerceRegionId, long commerceCountryId,
		double latitude, double longitude,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateDefaultCommerceWarehouse(name, street1, street2,
			street3, city, zip, commerceRegionId, commerceCountryId, latitude,
			longitude, serviceContext);
	}

	public static CommerceWarehouseService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceWarehouseService, CommerceWarehouseService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CommerceWarehouseService.class);

		ServiceTracker<CommerceWarehouseService, CommerceWarehouseService> serviceTracker =
			new ServiceTracker<CommerceWarehouseService, CommerceWarehouseService>(bundle.getBundleContext(),
				CommerceWarehouseService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}
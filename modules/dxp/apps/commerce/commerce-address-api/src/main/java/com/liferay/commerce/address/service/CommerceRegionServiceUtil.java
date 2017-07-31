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

package com.liferay.commerce.address.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.osgi.util.ServiceTrackerFactory;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for CommerceRegion. This utility wraps
 * {@link com.liferay.commerce.address.service.impl.CommerceRegionServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceRegionService
 * @see com.liferay.commerce.address.service.base.CommerceRegionServiceBaseImpl
 * @see com.liferay.commerce.address.service.impl.CommerceRegionServiceImpl
 * @generated
 */
@ProviderType
public class CommerceRegionServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.address.service.impl.CommerceRegionServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.commerce.address.model.CommerceRegion addCommerceRegion(
		long commerceCountryId, java.lang.String name,
		java.lang.String abbreviation, int priority, boolean published,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCommerceRegion(commerceCountryId, name, abbreviation,
			priority, published, serviceContext);
	}

	public static com.liferay.commerce.address.model.CommerceRegion deleteCommerceRegion(
		long commerceRegionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteCommerceRegion(commerceRegionId);
	}

	public static com.liferay.commerce.address.model.CommerceRegion updateCommerceRegion(
		long commerceRegionId, java.lang.String name,
		java.lang.String abbreviation, int priority, boolean published)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCommerceRegion(commerceRegionId, name, abbreviation,
			priority, published);
	}

	public static int getCommerceRegionsCount(long commerceCountryId) {
		return getService().getCommerceRegionsCount(commerceCountryId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static java.util.List<com.liferay.commerce.address.model.CommerceRegion> getCommerceRegions(
		long commerceCountryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.address.model.CommerceRegion> orderByComparator) {
		return getService()
				   .getCommerceRegions(commerceCountryId, start, end,
			orderByComparator);
	}

	public static CommerceRegionService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceRegionService, CommerceRegionService> _serviceTracker =
		ServiceTrackerFactory.open(CommerceRegionService.class);
}
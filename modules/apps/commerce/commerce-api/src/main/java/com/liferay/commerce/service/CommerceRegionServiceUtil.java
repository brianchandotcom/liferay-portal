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

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for CommerceRegion. This utility wraps
 * <code>com.liferay.commerce.service.impl.CommerceRegionServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceRegionService
 * @generated
 */
public class CommerceRegionServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.service.impl.CommerceRegionServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.commerce.model.CommerceRegion addCommerceRegion(
			long commerceCountryId, String name, String code, double priority,
			boolean active,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addCommerceRegion(
			commerceCountryId, name, code, priority, active, serviceContext);
	}

	public static void deleteCommerceRegion(long commerceRegionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteCommerceRegion(commerceRegionId);
	}

	public static com.liferay.commerce.model.CommerceRegion getCommerceRegion(
			long commerceRegionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommerceRegion(commerceRegionId);
	}

	public static java.util.List<com.liferay.commerce.model.CommerceRegion>
		getCommerceRegions(long commerceCountryId, boolean active) {

		return getService().getCommerceRegions(commerceCountryId, active);
	}

	public static java.util.List<com.liferay.commerce.model.CommerceRegion>
			getCommerceRegions(
				long commerceCountryId, boolean active, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.commerce.model.CommerceRegion>
						orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommerceRegions(
			commerceCountryId, active, start, end, orderByComparator);
	}

	public static java.util.List<com.liferay.commerce.model.CommerceRegion>
			getCommerceRegions(
				long commerceCountryId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.commerce.model.CommerceRegion>
						orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommerceRegions(
			commerceCountryId, start, end, orderByComparator);
	}

	public static java.util.List<com.liferay.commerce.model.CommerceRegion>
			getCommerceRegions(
				long companyId, String countryTwoLettersISOCode, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommerceRegions(
			companyId, countryTwoLettersISOCode, active);
	}

	public static int getCommerceRegionsCount(long commerceCountryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommerceRegionsCount(commerceCountryId);
	}

	public static int getCommerceRegionsCount(
			long commerceCountryId, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommerceRegionsCount(commerceCountryId, active);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.commerce.model.CommerceRegion setActive(
			long commerceRegionId, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().setActive(commerceRegionId, active);
	}

	public static com.liferay.commerce.model.CommerceRegion
			updateCommerceRegion(
				long commerceRegionId, String name, String code,
				double priority, boolean active,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateCommerceRegion(
			commerceRegionId, name, code, priority, active, serviceContext);
	}

	public static CommerceRegionService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceRegionService, CommerceRegionService>
		_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CommerceRegionService.class);

		ServiceTracker<CommerceRegionService, CommerceRegionService>
			serviceTracker =
				new ServiceTracker
					<CommerceRegionService, CommerceRegionService>(
						bundle.getBundleContext(), CommerceRegionService.class,
						null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}
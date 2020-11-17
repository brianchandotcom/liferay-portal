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

package com.liferay.portal.kernel.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * Provides the remote service utility for Region. This utility wraps
 * <code>com.liferay.portal.service.impl.RegionServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see RegionService
 * @generated
 */
public class RegionServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portal.service.impl.RegionServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.portal.kernel.model.Region addRegion(
			long countryId, boolean active, String name, double position,
			String regionCode, ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addRegion(
			countryId, active, name, position, regionCode, serviceContext);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x)
	 */
	@Deprecated
	public static com.liferay.portal.kernel.model.Region addRegion(
			long countryId, String regionCode, String name, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addRegion(countryId, regionCode, name, active);
	}

	public static com.liferay.portal.kernel.model.Region fetchRegion(
		long regionId) {

		return getService().fetchRegion(regionId);
	}

	public static com.liferay.portal.kernel.model.Region fetchRegion(
		long countryId, String regionCode) {

		return getService().fetchRegion(countryId, regionCode);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.kernel.model.Region getRegion(
			long regionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getRegion(regionId);
	}

	public static com.liferay.portal.kernel.model.Region getRegion(
			long countryId, String regionCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getRegion(countryId, regionCode);
	}

	public static java.util.List<com.liferay.portal.kernel.model.Region>
		getRegions() {

		return getService().getRegions();
	}

	public static java.util.List<com.liferay.portal.kernel.model.Region>
		getRegions(boolean active) {

		return getService().getRegions(active);
	}

	public static java.util.List<com.liferay.portal.kernel.model.Region>
		getRegions(long countryId) {

		return getService().getRegions(countryId);
	}

	public static java.util.List<com.liferay.portal.kernel.model.Region>
		getRegions(long countryId, boolean active) {

		return getService().getRegions(countryId, active);
	}

	public static RegionService getService() {
		if (_service == null) {
			_service = (RegionService)PortalBeanLocatorUtil.locate(
				RegionService.class.getName());
		}

		return _service;
	}

	private static RegionService _service;

}
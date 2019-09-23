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

/**
 * Provides a wrapper for {@link RegionService}.
 *
 * @author Brian Wing Shun Chan
 * @see RegionService
 * @generated
 */
public class RegionServiceWrapper
	implements RegionService, ServiceWrapper<RegionService> {

	public RegionServiceWrapper(RegionService regionService) {
		_regionService = regionService;
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link RegionServiceUtil} to access the region remote service. Add custom service methods to <code>com.liferay.portal.service.impl.RegionServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@Override
	public com.liferay.portal.kernel.model.Region addRegion(
			long countryId, java.lang.String regionCode, java.lang.String name,
			boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _regionService.addRegion(countryId, regionCode, name, active);
	}

	@Override
	public com.liferay.portal.kernel.model.Region fetchRegion(long regionId) {
		return _regionService.fetchRegion(regionId);
	}

	@Override
	public com.liferay.portal.kernel.model.Region fetchRegion(
		long countryId, java.lang.String regionCode) {

		return _regionService.fetchRegion(countryId, regionCode);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _regionService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.Region getRegion(long regionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _regionService.getRegion(regionId);
	}

	@Override
	public com.liferay.portal.kernel.model.Region getRegion(
			long countryId, java.lang.String regionCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _regionService.getRegion(countryId, regionCode);
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.model.Region> getRegions() {
		return _regionService.getRegions();
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.model.Region> getRegions(
		boolean active) {

		return _regionService.getRegions(active);
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.model.Region> getRegions(
		long countryId) {

		return _regionService.getRegions(countryId);
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.model.Region> getRegions(
		long countryId, boolean active) {

		return _regionService.getRegions(countryId, active);
	}

	@Override
	public RegionService getWrappedService() {
		return _regionService;
	}

	@Override
	public void setWrappedService(RegionService regionService) {
		_regionService = regionService;
	}

	private RegionService _regionService;

}
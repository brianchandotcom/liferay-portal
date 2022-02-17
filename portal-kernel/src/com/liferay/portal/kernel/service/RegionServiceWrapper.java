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

	public RegionServiceWrapper() {
		this(null);
	}

	public RegionServiceWrapper(RegionService regionService) {
		_regionService = regionService;
	}

	@Override
	public com.liferay.portal.kernel.model.Region addRegion(
			long countryId, boolean active, java.lang.String name,
			double position, java.lang.String regionCode,
			ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _regionService.addRegion(
			countryId, active, name, position, regionCode, serviceContext);
	}

	@Override
	public void deleteRegion(long regionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_regionService.deleteRegion(regionId);
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
	public java.util.List<com.liferay.portal.kernel.model.Region> getRegions(
		long countryId, boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<com.liferay.portal.kernel.model.Region> orderByComparator) {

		return _regionService.getRegions(
			countryId, active, start, end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.model.Region> getRegions(
		long countryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<com.liferay.portal.kernel.model.Region> orderByComparator) {

		return _regionService.getRegions(
			countryId, start, end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.model.Region> getRegions(
			long companyId, java.lang.String a2, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _regionService.getRegions(companyId, a2, active);
	}

	@Override
	public int getRegionsCount(long countryId) {
		return _regionService.getRegionsCount(countryId);
	}

	@Override
	public int getRegionsCount(long countryId, boolean active) {
		return _regionService.getRegionsCount(countryId, active);
	}

	@Override
	public com.liferay.portal.kernel.model.Region updateActive(
			long regionId, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _regionService.updateActive(regionId, active);
	}

	@Override
	public com.liferay.portal.kernel.model.Region updateRegion(
			long regionId, boolean active, java.lang.String name,
			double position, java.lang.String regionCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _regionService.updateRegion(
			regionId, active, name, position, regionCode);
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
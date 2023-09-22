/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.service;

import com.liferay.portal.kernel.model.Region;

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
	public Region addRegion(
			long countryId, boolean active, String name, double position,
			String regionCode, ServiceContext serviceContext)
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
	public Region fetchRegion(long regionId) {
		return _regionService.fetchRegion(regionId);
	}

	@Override
	public Region fetchRegion(long countryId, String regionCode) {
		return _regionService.fetchRegion(countryId, regionCode);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _regionService.getOSGiServiceIdentifier();
	}

	@Override
	public Region getRegion(long regionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _regionService.getRegion(regionId);
	}

	@Override
	public Region getRegion(long countryId, String regionCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _regionService.getRegion(countryId, regionCode);
	}

	@Override
	public java.util.List<Region> getRegions() {
		return _regionService.getRegions();
	}

	@Override
	public java.util.List<Region> getRegions(boolean active) {
		return _regionService.getRegions(active);
	}

	@Override
	public java.util.List<Region> getRegions(long countryId) {
		return _regionService.getRegions(countryId);
	}

	@Override
	public java.util.List<Region> getRegions(long countryId, boolean active) {
		return _regionService.getRegions(countryId, active);
	}

	@Override
	public java.util.List<Region> getRegions(
		long countryId, boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Region>
			orderByComparator) {

		return _regionService.getRegions(
			countryId, active, start, end, orderByComparator);
	}

	@Override
	public java.util.List<Region> getRegions(
		long countryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Region>
			orderByComparator) {

		return _regionService.getRegions(
			countryId, start, end, orderByComparator);
	}

	@Override
	public java.util.List<Region> getRegions(
			long companyId, String a2, boolean active)
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
	public com.liferay.portal.kernel.search.BaseModelSearchResult<Region>
			searchRegions(
				long companyId, Boolean active, String keywords,
				java.util.LinkedHashMap<String, Object> params, int start,
				int end,
				com.liferay.portal.kernel.util.OrderByComparator<Region>
					orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _regionService.searchRegions(
			companyId, active, keywords, params, start, end, orderByComparator);
	}

	@Override
	public Region updateActive(long regionId, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _regionService.updateActive(regionId, active);
	}

	@Override
	public Region updateRegion(
			long regionId, boolean active, String name, double position,
			String regionCode)
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
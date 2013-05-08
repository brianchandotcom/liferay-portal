/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service;

/**
 * Provides a wrapper for {@link RegionService}.
 *
 * @author    Brian Wing Shun Chan
 * @see       RegionService
 * @generated
 */
public class RegionServiceWrapper implements RegionService,
	ServiceWrapper<RegionService> {
	public RegionServiceWrapper(RegionService regionService) {
		_regionService = regionService;
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _regionService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_regionService.setBeanIdentifier(beanIdentifier);
	}

	public com.liferay.portal.model.Region addRegion(long countryId,
		java.lang.String regionCode, java.lang.String name, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _regionService.addRegion(countryId, regionCode, name, active);
	}

	public com.liferay.portal.model.Region fetchRegion(long countryId,
		java.lang.String regionCode)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _regionService.fetchRegion(countryId, regionCode);
	}

	public com.liferay.portal.model.Region getRegion(long regionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _regionService.getRegion(regionId);
	}

	public com.liferay.portal.model.Region getRegion(long countryId,
		java.lang.String regionCode)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _regionService.getRegion(countryId, regionCode);
	}

	public java.util.List<com.liferay.portal.model.Region> getRegions()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _regionService.getRegions();
	}

	public java.util.List<com.liferay.portal.model.Region> getRegions(
		boolean active)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _regionService.getRegions(active);
	}

	public java.util.List<com.liferay.portal.model.Region> getRegions(
		long countryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _regionService.getRegions(countryId);
	}

	public java.util.List<com.liferay.portal.model.Region> getRegions(
		long countryId, boolean active)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _regionService.getRegions(countryId, active);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	public RegionService getWrappedRegionService() {
		return _regionService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	public void setWrappedRegionService(RegionService regionService) {
		_regionService = regionService;
	}

	public RegionService getWrappedService() {
		return _regionService;
	}

	public void setWrappedService(RegionService regionService) {
		_regionService = regionService;
	}

	private RegionService _regionService;
}
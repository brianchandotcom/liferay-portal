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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceRegionService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceRegionService
 * @generated
 */
@ProviderType
public class CommerceRegionServiceWrapper implements CommerceRegionService,
	ServiceWrapper<CommerceRegionService> {
	public CommerceRegionServiceWrapper(
		CommerceRegionService commerceRegionService) {
		_commerceRegionService = commerceRegionService;
	}

	@Override
	public com.liferay.commerce.model.CommerceRegion addCommerceRegion(
		long commerceCountryId, String name, String code, double priority,
		boolean active,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceRegionService.addCommerceRegion(commerceCountryId,
			name, code, priority, active, serviceContext);
	}

	@Override
	public void deleteCommerceRegion(long commerceRegionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_commerceRegionService.deleteCommerceRegion(commerceRegionId);
	}

	@Override
	public com.liferay.commerce.model.CommerceRegion getCommerceRegion(
		long commerceRegionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceRegionService.getCommerceRegion(commerceRegionId);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceRegion> getCommerceRegions(
		long commerceCountryId, boolean active) {
		return _commerceRegionService.getCommerceRegions(commerceCountryId,
			active);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceRegion> getCommerceRegions(
		long commerceCountryId, boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceRegion> orderByComparator) {
		return _commerceRegionService.getCommerceRegions(commerceCountryId,
			active, start, end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceRegion> getCommerceRegions(
		long commerceCountryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceRegion> orderByComparator) {
		return _commerceRegionService.getCommerceRegions(commerceCountryId,
			start, end, orderByComparator);
	}

	@Override
	public int getCommerceRegionsCount(long commerceCountryId) {
		return _commerceRegionService.getCommerceRegionsCount(commerceCountryId);
	}

	@Override
	public int getCommerceRegionsCount(long commerceCountryId, boolean active) {
		return _commerceRegionService.getCommerceRegionsCount(commerceCountryId,
			active);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceRegionService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.commerce.model.CommerceRegion updateCommerceRegion(
		long commerceRegionId, String name, String code, double priority,
		boolean active,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceRegionService.updateCommerceRegion(commerceRegionId,
			name, code, priority, active, serviceContext);
	}

	@Override
	public CommerceRegionService getWrappedService() {
		return _commerceRegionService;
	}

	@Override
	public void setWrappedService(CommerceRegionService commerceRegionService) {
		_commerceRegionService = commerceRegionService;
	}

	private CommerceRegionService _commerceRegionService;
}
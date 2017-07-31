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
	public com.liferay.commerce.address.model.CommerceRegion addCommerceRegion(
		long commerceCountryId, java.lang.String name,
		java.lang.String abbreviation, int priority, boolean published,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceRegionService.addCommerceRegion(commerceCountryId,
			name, abbreviation, priority, published, serviceContext);
	}

	@Override
	public com.liferay.commerce.address.model.CommerceRegion deleteCommerceRegion(
		long commerceRegionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceRegionService.deleteCommerceRegion(commerceRegionId);
	}

	@Override
	public com.liferay.commerce.address.model.CommerceRegion updateCommerceRegion(
		long commerceRegionId, java.lang.String name,
		java.lang.String abbreviation, int priority, boolean published)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceRegionService.updateCommerceRegion(commerceRegionId,
			name, abbreviation, priority, published);
	}

	@Override
	public int getCommerceRegionsCount(long commerceCountryId) {
		return _commerceRegionService.getCommerceRegionsCount(commerceCountryId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _commerceRegionService.getOSGiServiceIdentifier();
	}

	@Override
	public java.util.List<com.liferay.commerce.address.model.CommerceRegion> getCommerceRegions(
		long commerceCountryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.address.model.CommerceRegion> orderByComparator) {
		return _commerceRegionService.getCommerceRegions(commerceCountryId,
			start, end, orderByComparator);
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
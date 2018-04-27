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
 * Provides a wrapper for {@link CommerceAvailabilityRangeService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceAvailabilityRangeService
 * @generated
 */
@ProviderType
public class CommerceAvailabilityRangeServiceWrapper
	implements CommerceAvailabilityRangeService,
		ServiceWrapper<CommerceAvailabilityRangeService> {
	public CommerceAvailabilityRangeServiceWrapper(
		CommerceAvailabilityRangeService commerceAvailabilityRangeService) {
		_commerceAvailabilityRangeService = commerceAvailabilityRangeService;
	}

	@Override
	public com.liferay.commerce.model.CommerceAvailabilityRange addCommerceAvailabilityRange(
		java.util.Map<java.util.Locale, String> titleMap, double priority,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceAvailabilityRangeService.addCommerceAvailabilityRange(titleMap,
			priority, serviceContext);
	}

	@Override
	public void deleteCommerceAvailabilityRange(
		long commerceAvailabilityRangeId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_commerceAvailabilityRangeService.deleteCommerceAvailabilityRange(commerceAvailabilityRangeId);
	}

	@Override
	public com.liferay.commerce.model.CommerceAvailabilityRange getCommerceAvailabilityRange(
		long commerceAvailabilityRangeId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceAvailabilityRangeService.getCommerceAvailabilityRange(commerceAvailabilityRangeId);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceAvailabilityRange> getCommerceAvailabilityRanges(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceAvailabilityRange> orderByComparator) {
		return _commerceAvailabilityRangeService.getCommerceAvailabilityRanges(groupId,
			start, end, orderByComparator);
	}

	@Override
	public int getCommerceAvailabilityRangesCount(long groupId) {
		return _commerceAvailabilityRangeService.getCommerceAvailabilityRangesCount(groupId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceAvailabilityRangeService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.commerce.model.CommerceAvailabilityRange updateCommerceAvailabilityRange(
		long commerceAvailabilityRangeId,
		java.util.Map<java.util.Locale, String> titleMap, double priority,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceAvailabilityRangeService.updateCommerceAvailabilityRange(commerceAvailabilityRangeId,
			titleMap, priority, serviceContext);
	}

	@Override
	public CommerceAvailabilityRangeService getWrappedService() {
		return _commerceAvailabilityRangeService;
	}

	@Override
	public void setWrappedService(
		CommerceAvailabilityRangeService commerceAvailabilityRangeService) {
		_commerceAvailabilityRangeService = commerceAvailabilityRangeService;
	}

	private CommerceAvailabilityRangeService _commerceAvailabilityRangeService;
}
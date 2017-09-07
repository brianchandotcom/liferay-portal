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

package com.liferay.commerce.product.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CPAvailabilityRangeService}.
 *
 * @author Marco Leo
 * @see CPAvailabilityRangeService
 * @generated
 */
@ProviderType
public class CPAvailabilityRangeServiceWrapper
	implements CPAvailabilityRangeService,
		ServiceWrapper<CPAvailabilityRangeService> {
	public CPAvailabilityRangeServiceWrapper(
		CPAvailabilityRangeService cpAvailabilityRangeService) {
		_cpAvailabilityRangeService = cpAvailabilityRangeService;
	}

	@Override
	public com.liferay.commerce.product.model.CPAvailabilityRange addCPAvailabilityRange(
		long cpDefinitionId,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpAvailabilityRangeService.addCPAvailabilityRange(cpDefinitionId,
			titleMap, serviceContext);
	}

	@Override
	public void deleteCPAvailabilityRange(long cpAvailabilityRangeId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_cpAvailabilityRangeService.deleteCPAvailabilityRange(cpAvailabilityRangeId);
	}

	@Override
	public com.liferay.commerce.product.model.CPAvailabilityRange getCPAvailabilityRange(
		long cpAvailabilityRangeId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpAvailabilityRangeService.getCPAvailabilityRange(cpAvailabilityRangeId);
	}

	@Override
	public java.util.List<com.liferay.commerce.product.model.CPAvailabilityRange> getCPAvailabilityRanges(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPAvailabilityRange> orderByComparator) {
		return _cpAvailabilityRangeService.getCPAvailabilityRanges(groupId,
			start, end, orderByComparator);
	}

	@Override
	public int getCPAvailabilityRangesCount(long groupId) {
		return _cpAvailabilityRangeService.getCPAvailabilityRangesCount(groupId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _cpAvailabilityRangeService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.commerce.product.model.CPAvailabilityRange updateCPAvailabilityRange(
		long cpAvailabilityRangeId,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpAvailabilityRangeService.updateCPAvailabilityRange(cpAvailabilityRangeId,
			titleMap, serviceContext);
	}

	@Override
	public CPAvailabilityRangeService getWrappedService() {
		return _cpAvailabilityRangeService;
	}

	@Override
	public void setWrappedService(
		CPAvailabilityRangeService cpAvailabilityRangeService) {
		_cpAvailabilityRangeService = cpAvailabilityRangeService;
	}

	private CPAvailabilityRangeService _cpAvailabilityRangeService;
}
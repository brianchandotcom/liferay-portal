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
 * Provides a wrapper for {@link CAvailabilityRangeEntryService}.
 *
 * @author Alessio Antonio Rendina
 * @see CAvailabilityRangeEntryService
 * @generated
 */
@ProviderType
public class CAvailabilityRangeEntryServiceWrapper
	implements CAvailabilityRangeEntryService,
		ServiceWrapper<CAvailabilityRangeEntryService> {
	public CAvailabilityRangeEntryServiceWrapper(
		CAvailabilityRangeEntryService cAvailabilityRangeEntryService) {
		_cAvailabilityRangeEntryService = cAvailabilityRangeEntryService;
	}

	@Override
	public void deleteCAvailabilityRangeEntry(long cAvailabilityRangeEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_cAvailabilityRangeEntryService.deleteCAvailabilityRangeEntry(cAvailabilityRangeEntryId);
	}

	@Override
	public com.liferay.commerce.model.CAvailabilityRangeEntry fetchCAvailabilityRangeEntry(
		long groupId, long cpDefinitionId) {
		return _cAvailabilityRangeEntryService.fetchCAvailabilityRangeEntry(groupId,
			cpDefinitionId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _cAvailabilityRangeEntryService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.commerce.model.CAvailabilityRangeEntry updateCAvailabilityRangeEntry(
		long cAvailabilityRangeEntryId, long cpDefinitionId,
		long commerceAvailabilityRangeId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cAvailabilityRangeEntryService.updateCAvailabilityRangeEntry(cAvailabilityRangeEntryId,
			cpDefinitionId, commerceAvailabilityRangeId, serviceContext);
	}

	@Override
	public CAvailabilityRangeEntryService getWrappedService() {
		return _cAvailabilityRangeEntryService;
	}

	@Override
	public void setWrappedService(
		CAvailabilityRangeEntryService cAvailabilityRangeEntryService) {
		_cAvailabilityRangeEntryService = cAvailabilityRangeEntryService;
	}

	private CAvailabilityRangeEntryService _cAvailabilityRangeEntryService;
}
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
 * Provides a wrapper for {@link CPDefinitionAvailabilityRangeService}.
 *
 * @author Alessio Antonio Rendina
 * @see CPDefinitionAvailabilityRangeService
 * @generated
 */
@ProviderType
public class CPDefinitionAvailabilityRangeServiceWrapper
	implements CPDefinitionAvailabilityRangeService,
		ServiceWrapper<CPDefinitionAvailabilityRangeService> {
	public CPDefinitionAvailabilityRangeServiceWrapper(
		CPDefinitionAvailabilityRangeService cpDefinitionAvailabilityRangeService) {
		_cpDefinitionAvailabilityRangeService = cpDefinitionAvailabilityRangeService;
	}

	@Override
	public void deleteCPDefinitionAvailabilityRange(
		long cpDefinitionAvailabilityRangeId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_cpDefinitionAvailabilityRangeService.deleteCPDefinitionAvailabilityRange(cpDefinitionAvailabilityRangeId);
	}

	@Override
	public com.liferay.commerce.model.CPDefinitionAvailabilityRange fetchCPDefinitionAvailabilityRangeByCPDefinitionId(
		long cpDefinitionId) {
		return _cpDefinitionAvailabilityRangeService.fetchCPDefinitionAvailabilityRangeByCPDefinitionId(cpDefinitionId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _cpDefinitionAvailabilityRangeService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.commerce.model.CPDefinitionAvailabilityRange updateCPDefinitionAvailabilityRange(
		long cpDefinitionAvailabilityRangeId, long cpDefinitionId,
		long commerceAvailabilityRangeId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionAvailabilityRangeService.updateCPDefinitionAvailabilityRange(cpDefinitionAvailabilityRangeId,
			cpDefinitionId, commerceAvailabilityRangeId, serviceContext);
	}

	@Override
	public CPDefinitionAvailabilityRangeService getWrappedService() {
		return _cpDefinitionAvailabilityRangeService;
	}

	@Override
	public void setWrappedService(
		CPDefinitionAvailabilityRangeService cpDefinitionAvailabilityRangeService) {
		_cpDefinitionAvailabilityRangeService = cpDefinitionAvailabilityRangeService;
	}

	private CPDefinitionAvailabilityRangeService _cpDefinitionAvailabilityRangeService;
}
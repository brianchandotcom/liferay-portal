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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CPDAvailabilityEstimateService}.
 *
 * @author Alessio Antonio Rendina
 * @see CPDAvailabilityEstimateService
 * @generated
 */
public class CPDAvailabilityEstimateServiceWrapper
	implements CPDAvailabilityEstimateService,
			   ServiceWrapper<CPDAvailabilityEstimateService> {

	public CPDAvailabilityEstimateServiceWrapper() {
		this(null);
	}

	public CPDAvailabilityEstimateServiceWrapper(
		CPDAvailabilityEstimateService cpdAvailabilityEstimateService) {

		_cpdAvailabilityEstimateService = cpdAvailabilityEstimateService;
	}

	@Override
	public com.liferay.commerce.model.CPDAvailabilityEstimate
			fetchCPDAvailabilityEstimateByCPDefinitionId(long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpdAvailabilityEstimateService.
			fetchCPDAvailabilityEstimateByCPDefinitionId(cpDefinitionId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _cpdAvailabilityEstimateService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.commerce.model.CPDAvailabilityEstimate
			updateCPDAvailabilityEstimate(
				long cpdAvailabilityEstimateId, long cpDefinitionId,
				long commerceAvailabilityEstimateId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpdAvailabilityEstimateService.updateCPDAvailabilityEstimate(
			cpdAvailabilityEstimateId, cpDefinitionId,
			commerceAvailabilityEstimateId, serviceContext);
	}

	@Override
	public CPDAvailabilityEstimateService getWrappedService() {
		return _cpdAvailabilityEstimateService;
	}

	@Override
	public void setWrappedService(
		CPDAvailabilityEstimateService cpdAvailabilityEstimateService) {

		_cpdAvailabilityEstimateService = cpdAvailabilityEstimateService;
	}

	private CPDAvailabilityEstimateService _cpdAvailabilityEstimateService;

}
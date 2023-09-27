/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
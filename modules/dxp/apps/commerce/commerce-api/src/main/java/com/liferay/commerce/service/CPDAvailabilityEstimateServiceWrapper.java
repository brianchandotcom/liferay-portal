/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CPDAvailabilityEstimateService}.
 *
 * @author Alessio Antonio Rendina
 * @see CPDAvailabilityEstimateService
 * @generated
 */
@ProviderType
public class CPDAvailabilityEstimateServiceWrapper
	implements CPDAvailabilityEstimateService,
		ServiceWrapper<CPDAvailabilityEstimateService> {
	public CPDAvailabilityEstimateServiceWrapper(
		CPDAvailabilityEstimateService cpdAvailabilityEstimateService) {
		_cpdAvailabilityEstimateService = cpdAvailabilityEstimateService;
	}

	@Override
	public void deleteCPDAvailabilityEstimate(long cpdAvailabilityEstimateId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_cpdAvailabilityEstimateService.deleteCPDAvailabilityEstimate(cpdAvailabilityEstimateId);
	}

	@Override
	public com.liferay.commerce.model.CPDAvailabilityEstimate fetchCPDAvailabilityEstimateByCPDefinitionId(
		long cpDefinitionId) {
		return _cpdAvailabilityEstimateService.fetchCPDAvailabilityEstimateByCPDefinitionId(cpDefinitionId);
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
	public com.liferay.commerce.model.CPDAvailabilityEstimate updateCPDAvailabilityEstimate(
		long cpdAvailabilityEstimateId, long cpDefinitionId,
		long commerceAvailabilityEstimateId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpdAvailabilityEstimateService.updateCPDAvailabilityEstimate(cpdAvailabilityEstimateId,
			cpDefinitionId, commerceAvailabilityEstimateId, serviceContext);
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
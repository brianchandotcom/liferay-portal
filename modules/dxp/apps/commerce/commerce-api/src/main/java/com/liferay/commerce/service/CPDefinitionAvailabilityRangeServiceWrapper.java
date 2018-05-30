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
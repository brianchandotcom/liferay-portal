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

package com.liferay.commerce.service.impl;

import com.liferay.commerce.model.CPDAvailabilityEstimate;
import com.liferay.commerce.service.base.CPDAvailabilityEstimateServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;

/**
 * @author Alessio Antonio Rendina
 */
public class CPDAvailabilityEstimateServiceImpl
	extends CPDAvailabilityEstimateServiceBaseImpl {

	@Override
	public void deleteCPDAvailabilityEstimate(long cpdAvailabilityEstimateId)
		throws PortalException {

		cpdAvailabilityEstimateLocalService.deleteCPDAvailabilityEstimate(
			cpdAvailabilityEstimateId);
	}

	@Override
	public CPDAvailabilityEstimate fetchCPDAvailabilityEstimateByCPDefinitionId(
		long cpDefinitionId) {

		return cpdAvailabilityEstimateLocalService.
			fetchCPDAvailabilityEstimateByCPDefinitionId(cpDefinitionId);
	}

	@Override
	public CPDAvailabilityEstimate updateCPDAvailabilityEstimate(
			long cpdAvailabilityEstimateId, long cpDefinitionId,
			long commerceAvailabilityEstimateId, ServiceContext serviceContext)
		throws PortalException {

		return cpdAvailabilityEstimateLocalService.
			updateCPDAvailabilityEstimate(
				cpdAvailabilityEstimateId, cpDefinitionId,
				commerceAvailabilityEstimateId, serviceContext);
	}

}
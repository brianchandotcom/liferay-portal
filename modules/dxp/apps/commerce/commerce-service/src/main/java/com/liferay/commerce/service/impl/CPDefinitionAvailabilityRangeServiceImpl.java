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

package com.liferay.commerce.service.impl;

import com.liferay.commerce.model.CPDefinitionAvailabilityRange;
import com.liferay.commerce.service.base.CPDefinitionAvailabilityRangeServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;

/**
 * @author Alessio Antonio Rendina
 */
public class CPDefinitionAvailabilityRangeServiceImpl
	extends CPDefinitionAvailabilityRangeServiceBaseImpl {

	@Override
	public void deleteCPDefinitionAvailabilityRange(
			long cpDefinitionAvailabilityRangeId)
		throws PortalException {

		cpDefinitionAvailabilityRangeLocalService.
			deleteCPDefinitionAvailabilityRange(
				cpDefinitionAvailabilityRangeId);
	}

	@Override
	public CPDefinitionAvailabilityRange
		fetchCPDefinitionAvailabilityRangeByCPDefinitionId(
			long cpDefinitionId) {

		return cpDefinitionAvailabilityRangeLocalService.
			fetchCPDefinitionAvailabilityRangeByCPDefinitionId(cpDefinitionId);
	}

	@Override
	public CPDefinitionAvailabilityRange updateCPDefinitionAvailabilityRange(
			long cpDefinitionAvailabilityRangeId, long cpDefinitionId,
			long commerceAvailabilityRangeId, ServiceContext serviceContext)
		throws PortalException {

		return cpDefinitionAvailabilityRangeLocalService.
			updateCPDefinitionAvailabilityRange(
				cpDefinitionAvailabilityRangeId, cpDefinitionId,
				commerceAvailabilityRangeId, serviceContext);
	}

}
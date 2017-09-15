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

import com.liferay.commerce.model.CAvailabilityRangeEntry;
import com.liferay.commerce.service.base.CAvailabilityRangeEntryServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;

/**
 * @author Alessio Antonio Rendina
 */
public class CAvailabilityRangeEntryServiceImpl
	extends CAvailabilityRangeEntryServiceBaseImpl {

	@Override
	public void deleteCAvailabilityRangeEntry(long cAvailabilityRangeEntryId)
		throws PortalException {

		cAvailabilityRangeEntryLocalService.deleteCAvailabilityRangeEntry(
			cAvailabilityRangeEntryId);
	}

	@Override
	public CAvailabilityRangeEntry fetchCAvailabilityRangeEntry(
		long groupId, long cpDefinitionId) {

		return cAvailabilityRangeEntryLocalService.fetchCAvailabilityRangeEntry(
			groupId, cpDefinitionId);
	}

	@Override
	public CAvailabilityRangeEntry updateCAvailabilityRangeEntry(
			long cAvailabilityRangeEntryId, long cpDefinitionId,
			long commerceAvailabilityRangeId, ServiceContext serviceContext)
		throws PortalException {

		return
			cAvailabilityRangeEntryLocalService.updateCAvailabilityRangeEntry(
				cAvailabilityRangeEntryId, cpDefinitionId,
				commerceAvailabilityRangeId, serviceContext);
	}

}
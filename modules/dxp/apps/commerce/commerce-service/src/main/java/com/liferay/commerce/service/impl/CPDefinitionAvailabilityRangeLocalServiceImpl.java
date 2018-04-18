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

import com.liferay.commerce.exception.NoSuchAvailabilityRangeException;
import com.liferay.commerce.model.CPDefinitionAvailabilityRange;
import com.liferay.commerce.model.CommerceAvailabilityRange;
import com.liferay.commerce.service.base.CPDefinitionAvailabilityRangeLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;

/**
 * @author Alessio Antonio Rendina
 */
public class CPDefinitionAvailabilityRangeLocalServiceImpl
	extends CPDefinitionAvailabilityRangeLocalServiceBaseImpl {

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CPDefinitionAvailabilityRange deleteCPDefinitionAvailabilityRange(
		CPDefinitionAvailabilityRange cpDefinitionAvailabilityRange) {

		return cpDefinitionAvailabilityRangePersistence.remove(
			cpDefinitionAvailabilityRange);
	}

	@Override
	public CPDefinitionAvailabilityRange deleteCPDefinitionAvailabilityRange(
			long cpDefinitionAvailabilityRangeId)
		throws PortalException {

		CPDefinitionAvailabilityRange cpDefinitionAvailabilityRange =
			cpDefinitionAvailabilityRangePersistence.findByPrimaryKey(
				cpDefinitionAvailabilityRangeId);

		return cpDefinitionAvailabilityRangeLocalService.
			deleteCPDefinitionAvailabilityRange(cpDefinitionAvailabilityRange);
	}

	@Override
	public void deleteCPDefinitionAvailabilityRangeByCPDefinitionId(
		long cpDefinitionId) {

		CPDefinitionAvailabilityRange cpDefinitionAvailabilityRange =
			cpDefinitionAvailabilityRangePersistence.fetchByCPDefinitionId(
				cpDefinitionId);

		if (cpDefinitionAvailabilityRange != null) {
			cpDefinitionAvailabilityRangeLocalService.
				deleteCPDefinitionAvailabilityRange(
					cpDefinitionAvailabilityRange);
		}
	}

	@Override
	public void deleteCPDefinitionAvailabilityRanges(
			long commerceAvailabilityRangeId)
		throws PortalException {

		cpDefinitionAvailabilityRangePersistence.
			removeByCommerceAvailabilityRangeId(commerceAvailabilityRangeId);
	}

	@Override
	public CPDefinitionAvailabilityRange
		fetchCPDefinitionAvailabilityRangeByCPDefinitionId(
			long cpDefinitionId) {

		return cpDefinitionAvailabilityRangePersistence.fetchByCPDefinitionId(
			cpDefinitionId);
	}

	@Override
	public CPDefinitionAvailabilityRange updateCPDefinitionAvailabilityRange(
			long cpDefinitionAvailabilityRangeId, long cpDefinitionId,
			long commerceAvailabilityRangeId, ServiceContext serviceContext)
		throws PortalException {

		validate(commerceAvailabilityRangeId);

		if (cpDefinitionAvailabilityRangeId > 0) {
			CPDefinitionAvailabilityRange cpDefinitionAvailabilityRange =
				cpDefinitionAvailabilityRangePersistence.findByPrimaryKey(
					cpDefinitionAvailabilityRangeId);

			cpDefinitionAvailabilityRange.setCommerceAvailabilityRangeId(
				commerceAvailabilityRangeId);

			return cpDefinitionAvailabilityRangePersistence.update(
				cpDefinitionAvailabilityRange);
		}

		CPDefinitionAvailabilityRange cpDefinitionAvailabilityRange =
			fetchCPDefinitionAvailabilityRangeByCPDefinitionId(cpDefinitionId);

		if (cpDefinitionAvailabilityRange != null) {
			cpDefinitionAvailabilityRange.setCommerceAvailabilityRangeId(
				commerceAvailabilityRangeId);

			return cpDefinitionAvailabilityRangePersistence.update(
				cpDefinitionAvailabilityRange);
		}

		return addCPDefinitionAvailabilityRange(
			cpDefinitionId, commerceAvailabilityRangeId, serviceContext);
	}

	protected CPDefinitionAvailabilityRange addCPDefinitionAvailabilityRange(
			long cpDefinitionId, long commerceAvailabilityRangeId,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();

		long cpDefinitionAvailabilityRangeId = counterLocalService.increment();

		CPDefinitionAvailabilityRange cpDefinitionAvailabilityRange =
			cpDefinitionAvailabilityRangePersistence.create(
				cpDefinitionAvailabilityRangeId);

		cpDefinitionAvailabilityRange.setUuid(serviceContext.getUuid());
		cpDefinitionAvailabilityRange.setGroupId(groupId);
		cpDefinitionAvailabilityRange.setCompanyId(user.getCompanyId());
		cpDefinitionAvailabilityRange.setUserId(user.getUserId());
		cpDefinitionAvailabilityRange.setUserName(user.getFullName());
		cpDefinitionAvailabilityRange.setCPDefinitionId(cpDefinitionId);
		cpDefinitionAvailabilityRange.setCommerceAvailabilityRangeId(
			commerceAvailabilityRangeId);

		cpDefinitionAvailabilityRangePersistence.update(
			cpDefinitionAvailabilityRange);

		return cpDefinitionAvailabilityRange;
	}

	protected void validate(long commerceAvailabilityRangeId)
		throws PortalException {

		if (commerceAvailabilityRangeId > 0) {
			CommerceAvailabilityRange commerceAvailabilityRange =
				commerceAvailabilityRangeLocalService.
					fetchCommerceAvailabilityRange(commerceAvailabilityRangeId);

			if (commerceAvailabilityRange == null) {
				throw new NoSuchAvailabilityRangeException();
			}
		}
	}

}
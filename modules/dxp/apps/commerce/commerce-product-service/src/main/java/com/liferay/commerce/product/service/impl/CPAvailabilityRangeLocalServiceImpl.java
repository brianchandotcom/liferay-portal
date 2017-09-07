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

package com.liferay.commerce.product.service.impl;

import com.liferay.commerce.product.model.CPAvailabilityRange;
import com.liferay.commerce.product.service.base.CPAvailabilityRangeLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public class CPAvailabilityRangeLocalServiceImpl
	extends CPAvailabilityRangeLocalServiceBaseImpl {

	@Override
	public CPAvailabilityRange addCPAvailabilityRange(
			Map<Locale, String> titleMap, ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();

		long cpAvailabilityRangeId = counterLocalService.increment();

		CPAvailabilityRange cpAvailabilityRange =
			cpAvailabilityRangePersistence.create(cpAvailabilityRangeId);

		cpAvailabilityRange.setUuid(serviceContext.getUuid());
		cpAvailabilityRange.setGroupId(groupId);
		cpAvailabilityRange.setCompanyId(user.getCompanyId());
		cpAvailabilityRange.setUserId(user.getUserId());
		cpAvailabilityRange.setUserName(user.getFullName());
		cpAvailabilityRange.setTitleMap(titleMap);

		cpAvailabilityRangePersistence.update(cpAvailabilityRange);

		return cpAvailabilityRange;
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CPAvailabilityRange deleteCPAvailabilityRange(
		CPAvailabilityRange cpAvailabilityRange) {

		return cpAvailabilityRangePersistence.remove(cpAvailabilityRange);
	}

	@Override
	public CPAvailabilityRange deleteCPAvailabilityRange(
			long cpAvailabilityRangeId)
		throws PortalException {

		CPAvailabilityRange cpAvailabilityRange =
			cpAvailabilityRangePersistence.findByPrimaryKey(
				cpAvailabilityRangeId);

		return cpAvailabilityRangeLocalService.deleteCPAvailabilityRange(
			cpAvailabilityRange);
	}

	@Override
	public void deleteCPAvailabilityRanges(long groupId) {
		cpAvailabilityRangePersistence.removeByGroupId(groupId);
	}

	@Override
	public List<CPAvailabilityRange> getCPAvailabilityRanges(
		long groupId, int start, int end,
		OrderByComparator<CPAvailabilityRange> orderByComparator) {

		return cpAvailabilityRangePersistence.findByGroupId(
			groupId, start, end, orderByComparator);
	}

	@Override
	public int getCPAvailabilityRangesCount(long groupId) {
		return cpAvailabilityRangePersistence.countByGroupId(groupId);
	}

	@Override
	public CPAvailabilityRange updateCPAvailabilityRange(
			long cpAvailabilityRangeId, Map<Locale, String> titleMap,
			ServiceContext serviceContext)
		throws PortalException {

		CPAvailabilityRange cpAvailabilityRange =
			cpAvailabilityRangePersistence.findByPrimaryKey(
				cpAvailabilityRangeId);

		cpAvailabilityRange.setTitleMap(titleMap);

		cpAvailabilityRangePersistence.update(cpAvailabilityRange);

		return cpAvailabilityRange;
	}

}
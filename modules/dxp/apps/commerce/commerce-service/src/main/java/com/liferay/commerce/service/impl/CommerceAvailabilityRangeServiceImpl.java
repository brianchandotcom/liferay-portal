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

import com.liferay.commerce.constants.CommerceActionKeys;
import com.liferay.commerce.model.CommerceAvailabilityRange;
import com.liferay.commerce.service.base.CommerceAvailabilityRangeServiceBaseImpl;
import com.liferay.commerce.service.permission.CommercePermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceAvailabilityRangeServiceImpl
	extends CommerceAvailabilityRangeServiceBaseImpl {

	@Override
	public CommerceAvailabilityRange addCommerceAvailabilityRange(
			Map<Locale, String> titleMap, double priority,
			ServiceContext serviceContext)
		throws PortalException {

		CommercePermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			CommerceActionKeys.MANAGE_COMMERCE_AVAILABILITY_RANGES);

		return commerceAvailabilityRangeLocalService.
			addCommerceAvailabilityRange(titleMap, priority, serviceContext);
	}

	@Override
	public void deleteCommerceAvailabilityRange(
			long commerceAvailabilityRangeId)
		throws PortalException {

		CommerceAvailabilityRange commerceAvailabilityRange =
			commerceAvailabilityRangePersistence.findByPrimaryKey(
				commerceAvailabilityRangeId);

		CommercePermission.check(
			getPermissionChecker(), commerceAvailabilityRange.getGroupId(),
			CommerceActionKeys.MANAGE_COMMERCE_AVAILABILITY_RANGES);

		commerceAvailabilityRangeLocalService.deleteCommerceAvailabilityRange(
			commerceAvailabilityRangeId);
	}

	@Override
	public CommerceAvailabilityRange getCommerceAvailabilityRange(
			long commerceAvailabilityRangeId)
		throws PortalException {

		return commerceAvailabilityRangeLocalService.
			getCommerceAvailabilityRange(commerceAvailabilityRangeId);
	}

	@Override
	public List<CommerceAvailabilityRange> getCommerceAvailabilityRanges(
		long groupId, int start, int end,
		OrderByComparator<CommerceAvailabilityRange> orderByComparator) {

		return commerceAvailabilityRangeLocalService.
			getCommerceAvailabilityRanges(
				groupId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceAvailabilityRangesCount(long groupId) {
		return commerceAvailabilityRangeLocalService.
			getCommerceAvailabilityRangesCount(groupId);
	}

	@Override
	public CommerceAvailabilityRange updateCommerceAvailabilityRange(
			long commerceAvailabilityRangeId, Map<Locale, String> titleMap,
			double priority, ServiceContext serviceContext)
		throws PortalException {

		CommerceAvailabilityRange commerceAvailabilityRange =
			commerceAvailabilityRangePersistence.findByPrimaryKey(
				commerceAvailabilityRangeId);

		CommercePermission.check(
			getPermissionChecker(), commerceAvailabilityRange.getGroupId(),
			CommerceActionKeys.MANAGE_COMMERCE_AVAILABILITY_RANGES);

		return commerceAvailabilityRangeLocalService.
			updateCommerceAvailabilityRange(
				commerceAvailabilityRangeId, titleMap, priority,
				serviceContext);
	}

}
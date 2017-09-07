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

import com.liferay.commerce.product.constants.CPActionKeys;
import com.liferay.commerce.product.model.CPAvailabilityRange;
import com.liferay.commerce.product.service.base.CPAvailabilityRangeServiceBaseImpl;
import com.liferay.commerce.product.service.permission.CPAvailabilityRangePermission;
import com.liferay.commerce.product.service.permission.CPMeasurementUnitPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public class CPAvailabilityRangeServiceImpl
	extends CPAvailabilityRangeServiceBaseImpl {

	@Override
	public CPAvailabilityRange addCPAvailabilityRange(
			long cpDefinitionId, Map<Locale, String> titleMap,
			ServiceContext serviceContext)
		throws PortalException {

		CPAvailabilityRangePermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			CPActionKeys.MANAGE_COMMERCE_PRODUCT_AVAILABILITY_RANGES);

		return cpAvailabilityRangeLocalService.addCPAvailabilityRange(
			cpDefinitionId, titleMap, serviceContext);
	}

	@Override
	public void deleteCPAvailabilityRange(long cpAvailabilityRangeId)
		throws PortalException {

		CPAvailabilityRange cpAvailabilityRange =
			cpAvailabilityRangePersistence.findByPrimaryKey(
				cpAvailabilityRangeId);

		CPAvailabilityRangePermission.check(
			getPermissionChecker(), cpAvailabilityRange.getGroupId(),
			CPActionKeys.MANAGE_COMMERCE_PRODUCT_AVAILABILITY_RANGES);

		cpAvailabilityRangeLocalService.deleteCPAvailabilityRange(
			cpAvailabilityRange);
	}

	@Override
	public CPAvailabilityRange getCPAvailabilityRange(
			long cpAvailabilityRangeId)
		throws PortalException {

		return cpAvailabilityRangeLocalService.getCPAvailabilityRange(
			cpAvailabilityRangeId);
	}

	@Override
	public List<CPAvailabilityRange> getCPAvailabilityRanges(
		long groupId, int start, int end,
		OrderByComparator<CPAvailabilityRange> orderByComparator) {

		return cpAvailabilityRangeLocalService.getCPAvailabilityRanges(
			groupId, start, end, orderByComparator);
	}

	@Override
	public int getCPAvailabilityRangesCount(long groupId) {
		return cpAvailabilityRangeLocalService.getCPAvailabilityRangesCount(
			groupId);
	}

	@Override
	public CPAvailabilityRange updateCPAvailabilityRange(
			long cpAvailabilityRangeId, Map<Locale, String> titleMap,
			ServiceContext serviceContext)
		throws PortalException {

		CPAvailabilityRange cpAvailabilityRange =
			cpAvailabilityRangePersistence.findByPrimaryKey(
				cpAvailabilityRangeId);

		CPMeasurementUnitPermission.check(
			getPermissionChecker(), cpAvailabilityRange.getGroupId(),
			CPActionKeys.MANAGE_COMMERCE_PRODUCT_AVAILABILITY_RANGES);

		return cpAvailabilityRangeLocalService.updateCPAvailabilityRange(
			cpAvailabilityRangeId, titleMap, serviceContext);
	}
}
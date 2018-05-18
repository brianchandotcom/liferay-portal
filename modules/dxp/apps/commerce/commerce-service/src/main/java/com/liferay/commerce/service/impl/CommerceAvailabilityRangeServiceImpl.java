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
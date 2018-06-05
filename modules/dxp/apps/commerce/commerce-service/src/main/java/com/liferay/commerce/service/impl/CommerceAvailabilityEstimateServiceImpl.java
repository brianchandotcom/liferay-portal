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
import com.liferay.commerce.model.CommerceAvailabilityEstimate;
import com.liferay.commerce.service.base.CommerceAvailabilityEstimateServiceBaseImpl;
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
public class CommerceAvailabilityEstimateServiceImpl
	extends CommerceAvailabilityEstimateServiceBaseImpl {

	@Override
	public CommerceAvailabilityEstimate addCommerceAvailabilityEstimate(
			Map<Locale, String> titleMap, double priority,
			ServiceContext serviceContext)
		throws PortalException {

		CommercePermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			CommerceActionKeys.MANAGE_COMMERCE_AVAILABILITY_ESTIMATES);

		return commerceAvailabilityEstimateLocalService.
			addCommerceAvailabilityEstimate(titleMap, priority, serviceContext);
	}

	@Override
	public void deleteCommerceAvailabilityEstimate(
			long commerceAvailabilityEstimateId)
		throws PortalException {

		CommerceAvailabilityEstimate commerceAvailabilityEstimate =
			commerceAvailabilityEstimatePersistence.findByPrimaryKey(
				commerceAvailabilityEstimateId);

		CommercePermission.check(
			getPermissionChecker(), commerceAvailabilityEstimate.getGroupId(),
			CommerceActionKeys.MANAGE_COMMERCE_AVAILABILITY_ESTIMATES);

		commerceAvailabilityEstimateLocalService.
			deleteCommerceAvailabilityEstimate(commerceAvailabilityEstimateId);
	}

	@Override
	public CommerceAvailabilityEstimate getCommerceAvailabilityEstimate(
			long commerceAvailabilityEstimateId)
		throws PortalException {

		return commerceAvailabilityEstimateLocalService.
			getCommerceAvailabilityEstimate(commerceAvailabilityEstimateId);
	}

	@Override
	public List<CommerceAvailabilityEstimate> getCommerceAvailabilityEstimates(
		long groupId, int start, int end,
		OrderByComparator<CommerceAvailabilityEstimate> orderByComparator) {

		return commerceAvailabilityEstimateLocalService.
			getCommerceAvailabilityEstimates(
				groupId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceAvailabilityEstimatesCount(long groupId) {
		return commerceAvailabilityEstimateLocalService.
			getCommerceAvailabilityEstimatesCount(groupId);
	}

	@Override
	public CommerceAvailabilityEstimate updateCommerceAvailabilityEstimate(
			long commerceAvailabilityEstimateId, Map<Locale, String> titleMap,
			double priority, ServiceContext serviceContext)
		throws PortalException {

		CommerceAvailabilityEstimate commerceAvailabilityEstimate =
			commerceAvailabilityEstimatePersistence.findByPrimaryKey(
				commerceAvailabilityEstimateId);

		CommercePermission.check(
			getPermissionChecker(), commerceAvailabilityEstimate.getGroupId(),
			CommerceActionKeys.MANAGE_COMMERCE_AVAILABILITY_ESTIMATES);

		return commerceAvailabilityEstimateLocalService.
			updateCommerceAvailabilityEstimate(
				commerceAvailabilityEstimateId, titleMap, priority,
				serviceContext);
	}

}
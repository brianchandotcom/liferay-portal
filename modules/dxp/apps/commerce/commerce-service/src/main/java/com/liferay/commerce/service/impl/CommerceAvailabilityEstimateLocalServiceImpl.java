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

import com.liferay.commerce.model.CommerceAvailabilityEstimate;
import com.liferay.commerce.service.base.CommerceAvailabilityEstimateLocalServiceBaseImpl;
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
 * @author Alessio Antonio Rendina
 */
public class CommerceAvailabilityEstimateLocalServiceImpl
	extends CommerceAvailabilityEstimateLocalServiceBaseImpl {

	@Override
	public CommerceAvailabilityEstimate addCommerceAvailabilityEstimate(
			Map<Locale, String> titleMap, double priority,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();

		long commerceAvailabilityEstimateId = counterLocalService.increment();

		CommerceAvailabilityEstimate commerceAvailabilityEstimate =
			commerceAvailabilityEstimatePersistence.create(
				commerceAvailabilityEstimateId);

		commerceAvailabilityEstimate.setUuid(serviceContext.getUuid());
		commerceAvailabilityEstimate.setGroupId(groupId);
		commerceAvailabilityEstimate.setCompanyId(user.getCompanyId());
		commerceAvailabilityEstimate.setUserId(user.getUserId());
		commerceAvailabilityEstimate.setUserName(user.getFullName());
		commerceAvailabilityEstimate.setTitleMap(titleMap);
		commerceAvailabilityEstimate.setPriority(priority);

		commerceAvailabilityEstimatePersistence.update(
			commerceAvailabilityEstimate);

		return commerceAvailabilityEstimate;
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CommerceAvailabilityEstimate deleteCommerceAvailabilityEstimate(
			CommerceAvailabilityEstimate commerceAvailabilityEstimate)
		throws PortalException {

		// Commerce availability range

		commerceAvailabilityEstimatePersistence.remove(
			commerceAvailabilityEstimate);

		// Commerce product definition availability ranges

		cpdAvailabilityEstimateLocalService.deleteCPDAvailabilityEstimates(
			commerceAvailabilityEstimate.getCommerceAvailabilityEstimateId());

		return commerceAvailabilityEstimate;
	}

	@Override
	public CommerceAvailabilityEstimate deleteCommerceAvailabilityEstimate(
			long commerceAvailabilityEstimateId)
		throws PortalException {

		CommerceAvailabilityEstimate commerceAvailabilityEstimate =
			commerceAvailabilityEstimatePersistence.findByPrimaryKey(
				commerceAvailabilityEstimateId);

		return commerceAvailabilityEstimateLocalService.
			deleteCommerceAvailabilityEstimate(commerceAvailabilityEstimate);
	}

	@Override
	public void deleteCommerceAvailabilityEstimates(long groupId)
		throws PortalException {

		List<CommerceAvailabilityEstimate> commerceAvailabilityEstimates =
			commerceAvailabilityEstimatePersistence.findByGroupId(groupId);

		for (CommerceAvailabilityEstimate commerceAvailabilityEstimate :
				commerceAvailabilityEstimates) {

			commerceAvailabilityEstimateLocalService.
				deleteCommerceAvailabilityEstimate(
					commerceAvailabilityEstimate);
		}
	}

	@Override
	public List<CommerceAvailabilityEstimate> getCommerceAvailabilityEstimates(
		long groupId, int start, int end,
		OrderByComparator<CommerceAvailabilityEstimate> orderByComparator) {

		return commerceAvailabilityEstimatePersistence.findByGroupId(
			groupId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceAvailabilityEstimatesCount(long groupId) {
		return commerceAvailabilityEstimatePersistence.countByGroupId(groupId);
	}

	@Override
	public CommerceAvailabilityEstimate updateCommerceAvailabilityEstimate(
			long commerceAvailabilityId, Map<Locale, String> titleMap,
			double priority, ServiceContext serviceContext)
		throws PortalException {

		CommerceAvailabilityEstimate commerceAvailabilityEstimate =
			commerceAvailabilityEstimatePersistence.findByPrimaryKey(
				commerceAvailabilityId);

		commerceAvailabilityEstimate.setTitleMap(titleMap);
		commerceAvailabilityEstimate.setPriority(priority);

		commerceAvailabilityEstimatePersistence.update(
			commerceAvailabilityEstimate);

		return commerceAvailabilityEstimate;
	}

}
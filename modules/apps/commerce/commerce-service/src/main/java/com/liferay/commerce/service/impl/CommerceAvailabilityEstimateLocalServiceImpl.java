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
import com.liferay.commerce.service.CPDAvailabilityEstimateLocalService;
import com.liferay.commerce.service.base.CommerceAvailabilityEstimateLocalServiceBaseImpl;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.spring.extender.service.ServiceReference;

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

		User user = _userLocalService.getUser(serviceContext.getUserId());

		long commerceAvailabilityEstimateId = counterLocalService.increment();

		CommerceAvailabilityEstimate commerceAvailabilityEstimate =
			commerceAvailabilityEstimatePersistence.create(
				commerceAvailabilityEstimateId);

		commerceAvailabilityEstimate.setCompanyId(user.getCompanyId());
		commerceAvailabilityEstimate.setUserId(user.getUserId());
		commerceAvailabilityEstimate.setUserName(user.getFullName());
		commerceAvailabilityEstimate.setTitleMap(titleMap);
		commerceAvailabilityEstimate.setPriority(priority);

		return commerceAvailabilityEstimatePersistence.update(
			commerceAvailabilityEstimate);
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

		_cpdAvailabilityEstimateLocalService.deleteCPDAvailabilityEstimates(
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
	public void deleteCommerceAvailabilityEstimates(long companyId)
		throws PortalException {

		List<CommerceAvailabilityEstimate> commerceAvailabilityEstimates =
			commerceAvailabilityEstimatePersistence.findByCompanyId(companyId);

		for (CommerceAvailabilityEstimate commerceAvailabilityEstimate :
				commerceAvailabilityEstimates) {

			commerceAvailabilityEstimateLocalService.
				deleteCommerceAvailabilityEstimate(
					commerceAvailabilityEstimate);
		}
	}

	@Override
	public List<CommerceAvailabilityEstimate> getCommerceAvailabilityEstimates(
		long companyId, int start, int end,
		OrderByComparator<CommerceAvailabilityEstimate> orderByComparator) {

		return commerceAvailabilityEstimatePersistence.findByCompanyId(
			companyId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceAvailabilityEstimatesCount(long companyId) {
		return commerceAvailabilityEstimatePersistence.countByCompanyId(
			companyId);
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

		return commerceAvailabilityEstimatePersistence.update(
			commerceAvailabilityEstimate);
	}

	@BeanReference(type = CPDAvailabilityEstimateLocalService.class)
	private CPDAvailabilityEstimateLocalService
		_cpdAvailabilityEstimateLocalService;

	@ServiceReference(type = UserLocalService.class)
	private UserLocalService _userLocalService;

}
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

import com.liferay.commerce.model.CommerceAvailabilityRange;
import com.liferay.commerce.service.base.CommerceAvailabilityRangeLocalServiceBaseImpl;
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
public class CommerceAvailabilityRangeLocalServiceImpl
	extends CommerceAvailabilityRangeLocalServiceBaseImpl {

	@Override
	public CommerceAvailabilityRange addCommerceAvailabilityRange(
			Map<Locale, String> titleMap, double priority,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();

		long commerceAvailabilityRangeId = counterLocalService.increment();

		CommerceAvailabilityRange commerceAvailabilityRange =
			commerceAvailabilityRangePersistence.create(
				commerceAvailabilityRangeId);

		commerceAvailabilityRange.setUuid(serviceContext.getUuid());
		commerceAvailabilityRange.setGroupId(groupId);
		commerceAvailabilityRange.setCompanyId(user.getCompanyId());
		commerceAvailabilityRange.setUserId(user.getUserId());
		commerceAvailabilityRange.setUserName(user.getFullName());
		commerceAvailabilityRange.setTitleMap(titleMap);
		commerceAvailabilityRange.setPriority(priority);

		commerceAvailabilityRangePersistence.update(commerceAvailabilityRange);

		return commerceAvailabilityRange;
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CommerceAvailabilityRange deleteCommerceAvailabilityRange(
			CommerceAvailabilityRange commerceAvailabilityRange)
		throws PortalException {

		// Commerce availability range

		commerceAvailabilityRangePersistence.remove(commerceAvailabilityRange);

		// Commerce product definition availability ranges

		cpDefinitionAvailabilityRangeLocalService.
			deleteCPDefinitionAvailabilityRanges(
				commerceAvailabilityRange.getCommerceAvailabilityRangeId());

		return commerceAvailabilityRange;
	}

	@Override
	public CommerceAvailabilityRange deleteCommerceAvailabilityRange(
			long commerceAvailabilityRangeId)
		throws PortalException {

		CommerceAvailabilityRange commerceAvailabilityRange =
			commerceAvailabilityRangePersistence.findByPrimaryKey(
				commerceAvailabilityRangeId);

		return commerceAvailabilityRangeLocalService.
			deleteCommerceAvailabilityRange(commerceAvailabilityRange);
	}

	@Override
	public void deleteCommerceAvailabilityRanges(long groupId)
		throws PortalException {

		List<CommerceAvailabilityRange> commerceAvailabilityRanges =
			commerceAvailabilityRangePersistence.findByGroupId(groupId);

		for (CommerceAvailabilityRange commerceAvailabilityRange :
				commerceAvailabilityRanges) {

			commerceAvailabilityRangeLocalService.
				deleteCommerceAvailabilityRange(commerceAvailabilityRange);
		}
	}

	@Override
	public List<CommerceAvailabilityRange> getCommerceAvailabilityRanges(
		long groupId, int start, int end,
		OrderByComparator<CommerceAvailabilityRange> orderByComparator) {

		return commerceAvailabilityRangePersistence.findByGroupId(
			groupId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceAvailabilityRangesCount(long groupId) {
		return commerceAvailabilityRangePersistence.countByGroupId(groupId);
	}

	@Override
	public CommerceAvailabilityRange updateCommerceAvailabilityRange(
			long commerceAvailabilityId, Map<Locale, String> titleMap,
			double priority, ServiceContext serviceContext)
		throws PortalException {

		CommerceAvailabilityRange commerceAvailabilityRange =
			commerceAvailabilityRangePersistence.findByPrimaryKey(
				commerceAvailabilityId);

		commerceAvailabilityRange.setTitleMap(titleMap);
		commerceAvailabilityRange.setPriority(priority);

		commerceAvailabilityRangePersistence.update(commerceAvailabilityRange);

		return commerceAvailabilityRange;
	}

}
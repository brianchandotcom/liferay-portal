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

package com.liferay.commerce.test.util;

import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.service.CommerceCurrencyServiceUtil;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.service.CommercePriceListLocalServiceUtil;
import com.liferay.commerce.price.list.service.CommercePriceListUserSegmentEntryRelServiceUtil;
import com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry;
import com.liferay.commerce.user.segment.service.CommerceUserSegmentEntryLocalServiceUtil;
import com.liferay.commerce.user.segment.test.util.CommerceUserSegmentTestUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Calendar;
import java.util.Optional;

/**
 * @author Luca Pellizzon
 */
public class CommercePriceListTestUtil {

	public static CommercePriceList addUserPriceList(
			long groupId, double priority, long userId)
		throws Exception {

		CommerceCurrency commerceCurrency =
			CommerceCurrencyServiceUtil.fetchPrimaryCommerceCurrency(groupId);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		User user = UserLocalServiceUtil.getDefaultUser(
			serviceContext.getCompanyId());

		Calendar calendar = CalendarFactoryUtil.getCalendar(user.getTimeZone());

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		CommercePriceList commercePriceList =
			CommercePriceListLocalServiceUtil.addCommercePriceList(
				commerceCurrency.getCommerceCurrencyId(),
				RandomTestUtil.randomString(), priority,
				calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH),
				calendar.get(Calendar.YEAR), calendar.get(Calendar.HOUR_OF_DAY),
				calendar.get(Calendar.MINUTE), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH),
				calendar.get(Calendar.YEAR), calendar.get(Calendar.HOUR_OF_DAY),
				calendar.get(Calendar.MINUTE), true, serviceContext);

		CommerceUserSegmentEntry commerceUserSegmentEntry =
			CommerceUserSegmentTestUtil.addUserCommerceUserSegmentEntry(
				groupId, userId);

		CommercePriceListUserSegmentEntryRelServiceUtil.
			addCommercePriceListUserSegmentEntryRel(
				commercePriceList.getCommercePriceListId(),
				commerceUserSegmentEntry.getCommerceUserSegmentEntryId(),
				RandomTestUtil.randomInt(), serviceContext);

		return commercePriceList;
	}

	public static Optional<CommercePriceList> getCommercePriceList(
			long groupId, long organizationId, long userId)
		throws Exception {

		long[] commerceUserSegmentEntryIds =
			CommerceUserSegmentEntryLocalServiceUtil.
				getCommerceUserSegmentEntryIds(groupId, organizationId, userId);

		return CommercePriceListLocalServiceUtil.getCommercePriceList(
			groupId, commerceUserSegmentEntryIds);
	}

}
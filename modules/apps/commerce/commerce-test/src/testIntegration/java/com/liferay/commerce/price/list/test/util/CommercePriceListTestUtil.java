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

package com.liferay.commerce.price.list.test.util;

import com.liferay.commerce.account.model.CommerceAccountGroup;
import com.liferay.commerce.account.service.CommerceAccountGroupLocalServiceUtil;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.commerce.price.list.constants.CommercePriceListConstants;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.service.CommercePriceListAccountRelLocalServiceUtil;
import com.liferay.commerce.price.list.service.CommercePriceListCommerceAccountGroupRelServiceUtil;
import com.liferay.commerce.price.list.service.CommercePriceListLocalServiceUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author Luca Pellizzon
 * @author Ethan Bustad
 */
public class CommercePriceListTestUtil {

	public static CommercePriceList addAccountPriceList(
			long groupId, long commerceAccountId, String type)
		throws Exception {

		CommercePriceList commercePriceList = addCommercePriceList(
			groupId, false, type, 1.0);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		CommercePriceListAccountRelLocalServiceUtil.
			addCommercePriceListAccountRel(
				commercePriceList.getCommercePriceListId(), commerceAccountId,
				0, serviceContext);

		return commercePriceList;
	}

	public static CommercePriceList addCommercePriceList(
			long groupId, boolean catalogBasePriceList, double priority)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		CommerceCurrency commerceCurrency =
			CommerceCurrencyTestUtil.addCommerceCurrency(
				serviceContext.getCompanyId());

		User user = UserLocalServiceUtil.getDefaultUser(
			serviceContext.getCompanyId());

		Calendar calendar = CalendarFactoryUtil.getCalendar(user.getTimeZone());

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		return CommercePriceListLocalServiceUtil.addCommercePriceList(
			groupId, user.getUserId(), commerceCurrency.getCommerceCurrencyId(),
			true, CommercePriceListConstants.TYPE_PRICE_LIST, 0,
			catalogBasePriceList, RandomTestUtil.randomString(), priority,
			calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
			calendar.get(Calendar.YEAR), calendar.get(Calendar.HOUR_OF_DAY),
			calendar.get(Calendar.MINUTE), calendar.get(Calendar.MONTH),
			calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.YEAR),
			calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),
			"", true, serviceContext);
	}

	public static CommercePriceList addCommercePriceList(
			long groupId, boolean catalogBasePriceList, String type,
			double priority)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		CommerceCurrency commerceCurrency =
			CommerceCurrencyTestUtil.addCommerceCurrency(
				serviceContext.getCompanyId());

		User user = UserLocalServiceUtil.getDefaultUser(
			serviceContext.getCompanyId());

		Calendar calendar = CalendarFactoryUtil.getCalendar(user.getTimeZone());

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		return CommercePriceListLocalServiceUtil.addCommercePriceList(
			groupId, user.getUserId(), commerceCurrency.getCommerceCurrencyId(),
			true, type, 0, catalogBasePriceList, RandomTestUtil.randomString(),
			priority, calendar.get(Calendar.MONTH),
			calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.YEAR),
			calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),
			calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
			calendar.get(Calendar.YEAR), calendar.get(Calendar.HOUR_OF_DAY),
			calendar.get(Calendar.MINUTE), "", true, serviceContext);
	}

	public static CommercePriceList addCommercePriceList(
			long groupId, double priority)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		CommerceCurrency commerceCurrency =
			CommerceCurrencyTestUtil.addCommerceCurrency(
				serviceContext.getCompanyId());

		User user = UserLocalServiceUtil.getDefaultUser(
			serviceContext.getCompanyId());

		Calendar calendar = CalendarFactoryUtil.getCalendar(user.getTimeZone());

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		return CommercePriceListLocalServiceUtil.addCommercePriceList(
			groupId, user.getUserId(), commerceCurrency.getCommerceCurrencyId(),
			RandomTestUtil.randomString(), priority,
			calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
			calendar.get(Calendar.YEAR), calendar.get(Calendar.HOUR_OF_DAY),
			calendar.get(Calendar.MINUTE), calendar.get(Calendar.MONTH),
			calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.YEAR),
			calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),
			true, serviceContext);
	}

	public static void addOrganizationSegmentToPriceList(
			CommercePriceList commercePriceList, long... organizationIds)
		throws Exception {

		long groupId = commercePriceList.getGroupId();

		CommerceAccountGroup commerceAccountGroup = null;

		/*CommerceAccountGroupTestUtil.addOrganizationCommerceAccountGroup(
			groupId, organizationIds);*/

		CommercePriceListCommerceAccountGroupRelServiceUtil.
			addCommercePriceListCommerceAccountGroupRel(
				commercePriceList.getCommercePriceListId(),
				commerceAccountGroup.getCommerceAccountGroupId(),
				RandomTestUtil.randomInt(),
				ServiceContextTestUtil.getServiceContext(groupId));
	}

	public static CommercePriceList addPromotion(long groupId, double priority)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		CommerceCurrency commerceCurrency =
			CommerceCurrencyTestUtil.addCommerceCurrency(
				serviceContext.getCompanyId());

		User user = UserLocalServiceUtil.getDefaultUser(
			serviceContext.getCompanyId());

		Calendar calendar = CalendarFactoryUtil.getCalendar(user.getTimeZone());

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		return CommercePriceListLocalServiceUtil.addCommercePriceList(
			groupId, user.getUserId(), commerceCurrency.getCommerceCurrencyId(),
			CommercePriceListConstants.TYPE_PROMOTION,
			RandomTestUtil.randomString(), priority,
			calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
			calendar.get(Calendar.YEAR), calendar.get(Calendar.HOUR_OF_DAY),
			calendar.get(Calendar.MINUTE), calendar.get(Calendar.MONTH),
			calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.YEAR),
			calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),
			true, serviceContext);
	}

	public static void addRoleSegmentToPriceList(
			CommercePriceList commercePriceList, long... roleIds)
		throws Exception {

		long groupId = commercePriceList.getGroupId();

		CommerceAccountGroup commerceAccountGroup = null;

		/*CommerceAccountGroupTestUtil.addOrganizationCommerceAccountGroup(
			groupId, organizationIds);*/

		CommercePriceListCommerceAccountGroupRelServiceUtil.
			addCommercePriceListCommerceAccountGroupRel(
				commercePriceList.getCommercePriceListId(),
				commerceAccountGroup.getCommerceAccountGroupId(),
				RandomTestUtil.randomInt(),
				ServiceContextTestUtil.getServiceContext(groupId));
	}

	public static CommercePriceList addUserPriceList(
			long groupId, double priority,
			CommerceAccountGroup commerceAccountGroup)
		throws Exception {

		CommercePriceList commercePriceList = addCommercePriceList(
			groupId, priority);

		CommercePriceListCommerceAccountGroupRelServiceUtil.
			addCommercePriceListCommerceAccountGroupRel(
				commercePriceList.getCommercePriceListId(),
				commerceAccountGroup.getCommerceAccountGroupId(),
				RandomTestUtil.randomInt(),
				ServiceContextTestUtil.getServiceContext(groupId));

		return commercePriceList;
	}

	public static CommercePriceList addUserPriceList(
			long groupId, double priority, long userId)
		throws Exception {

		CommercePriceList commercePriceList = addCommercePriceList(
			groupId, priority);

		CommerceAccountGroup commerceAccountGroup = null;

		/*CommerceAccountGroupTestUtil.addOrganizationCommerceAccountGroup(
			groupId, organizationIds);*/

		CommercePriceListCommerceAccountGroupRelServiceUtil.
			addCommercePriceListCommerceAccountGroupRel(
				commercePriceList.getCommercePriceListId(),
				commerceAccountGroup.getCommerceAccountGroupId(),
				RandomTestUtil.randomInt(),
				ServiceContextTestUtil.getServiceContext(groupId));

		return commercePriceList;
	}

	public static CommercePriceList addUserPriceListToAccountGroup(
			CommercePriceList commercePriceList,
			CommerceAccountGroup commerceAccountGroup)
		throws Exception {

		CommercePriceListCommerceAccountGroupRelServiceUtil.
			addCommercePriceListCommerceAccountGroupRel(
				commercePriceList.getCommercePriceListId(),
				commerceAccountGroup.getCommerceAccountGroupId(),
				RandomTestUtil.randomInt(),
				ServiceContextTestUtil.getServiceContext(
					commercePriceList.getGroupId()));

		return commercePriceList;
	}

	public static Optional<CommercePriceList> getCommercePriceList(
			long groupId, long commerceAccountId, long userId)
		throws Exception {

		User user = UserLocalServiceUtil.getUser(userId);

		List<CommerceAccountGroup> commerceAccountGroups =
			CommerceAccountGroupLocalServiceUtil.
				getCommerceAccountGroupsByCommerceAccountId(commerceAccountId);

		Stream<CommerceAccountGroup> stream = commerceAccountGroups.stream();

		long[] commerceAccountGroupIds = stream.mapToLong(
			CommerceAccountGroup::getCommerceAccountGroupId
		).toArray();

		return CommercePriceListLocalServiceUtil.getCommercePriceList(
			user.getCompanyId(), groupId, commerceAccountId,
			commerceAccountGroupIds);
	}

}
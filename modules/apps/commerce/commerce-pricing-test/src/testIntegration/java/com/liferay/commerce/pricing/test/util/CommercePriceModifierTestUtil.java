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

package com.liferay.commerce.pricing.test.util;

import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.test.util.CommerceCurrencyTestUtil;
import com.liferay.commerce.price.list.constants.CommercePriceListConstants;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.service.CommercePriceListLocalServiceUtil;
import com.liferay.commerce.pricing.model.CommercePriceModifier;
import com.liferay.commerce.pricing.model.CommercePriceModifierRel;
import com.liferay.commerce.pricing.service.CommercePriceModifierLocalServiceUtil;
import com.liferay.commerce.pricing.service.CommercePriceModifierRelLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.math.BigDecimal;

import java.util.Calendar;

/**
 * @author Riccardo Alberti
 */
public class CommercePriceModifierTestUtil {

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
			null, groupId, user.getUserId(),
			commerceCurrency.getCommerceCurrencyId(), true,
			CommercePriceListConstants.TYPE_PRICE_LIST, 0, false,
			RandomTestUtil.randomString(), priority,
			calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
			calendar.get(Calendar.YEAR), calendar.get(Calendar.HOUR_OF_DAY),
			calendar.get(Calendar.MINUTE), calendar.get(Calendar.MONTH),
			calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.YEAR),
			calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),
			true, serviceContext);
	}

	public static CommercePriceList addCommercePriceList(
			long groupId, double priority, long commerceCurrencyId)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		User user = UserLocalServiceUtil.getDefaultUser(
			serviceContext.getCompanyId());

		Calendar calendar = CalendarFactoryUtil.getCalendar(user.getTimeZone());

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		return CommercePriceListLocalServiceUtil.addCommercePriceList(
			null, groupId, user.getUserId(), commerceCurrencyId, true,
			CommercePriceListConstants.TYPE_PRICE_LIST, 0, false,
			RandomTestUtil.randomString(), priority,
			calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
			calendar.get(Calendar.YEAR), calendar.get(Calendar.HOUR_OF_DAY),
			calendar.get(Calendar.MINUTE), calendar.get(Calendar.MONTH),
			calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.YEAR),
			calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),
			true, serviceContext);
	}

	public static CommercePriceModifier addCommercePriceModifier(
			long groupId, long commercePriceListId, String type,
			BigDecimal amount, boolean neverExpire)
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		User user = UserLocalServiceUtil.getDefaultUser(
			serviceContext.getCompanyId());

		Calendar calendar = CalendarFactoryUtil.getCalendar(user.getTimeZone());

		return CommercePriceModifierLocalServiceUtil.addCommercePriceModifier(
			groupId, RandomTestUtil.randomString(), commercePriceListId, type,
			amount, 0.0, true, calendar.get(Calendar.MONTH),
			calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.YEAR),
			calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),
			calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
			calendar.get(Calendar.YEAR), calendar.get(Calendar.HOUR_OF_DAY),
			calendar.get(Calendar.MINUTE), neverExpire, serviceContext);
	}

	public static CommercePriceModifier addCommercePriceModifier(
			long groupId, String target, long commercePriceListId, String type,
			BigDecimal amount, boolean neverExpire)
		throws PortalException {

		return addCommercePriceModifier(
			groupId, RandomTestUtil.randomString(), target, commercePriceListId,
			type, amount, neverExpire);
	}

	public static CommercePriceModifier addCommercePriceModifier(
			long groupId, String title, String target, long commercePriceListId,
			String type, BigDecimal amount, boolean neverExpire)
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		User user = UserLocalServiceUtil.getDefaultUser(
			serviceContext.getCompanyId());

		Calendar calendar = CalendarFactoryUtil.getCalendar(user.getTimeZone());

		return CommercePriceModifierLocalServiceUtil.addCommercePriceModifier(
			groupId, title, target, commercePriceListId, type, amount, 0.0,
			true, calendar.get(Calendar.MONTH),
			calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.YEAR),
			calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),
			calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
			calendar.get(Calendar.YEAR), calendar.get(Calendar.HOUR_OF_DAY),
			calendar.get(Calendar.MINUTE), neverExpire, serviceContext);
	}

	public static CommercePriceModifierRel addCommercePriceModifierRel(
			long groupId, long commercePriceModifierId, String className,
			long classPK)
		throws PortalException {

		return CommercePriceModifierRelLocalServiceUtil.
			addCommercePriceModifierRel(
				commercePriceModifierId, className, classPK,
				ServiceContextTestUtil.getServiceContext(groupId));
	}

	public static CommercePriceModifier updateCommercePriceModifier(
			long groupId, long commercePriceModifierId, String target)
		throws PortalException {

		CommercePriceModifier commercePriceModifier =
			CommercePriceModifierLocalServiceUtil.getCommercePriceModifier(
				commercePriceModifierId);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		User user = UserLocalServiceUtil.getDefaultUser(
			serviceContext.getCompanyId());

		Calendar calendar = CalendarFactoryUtil.getCalendar(user.getTimeZone());

		return CommercePriceModifierLocalServiceUtil.
			updateCommercePriceModifier(
				commercePriceModifierId, commercePriceModifier.getGroupId(),
				commercePriceModifier.getTitle(), target,
				commercePriceModifier.getCommercePriceListId(),
				commercePriceModifier.getModifierType(),
				commercePriceModifier.getModifierAmount(),
				commercePriceModifier.getPriority(),
				commercePriceModifier.isActive(), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH),
				calendar.get(Calendar.YEAR), calendar.get(Calendar.HOUR_OF_DAY),
				calendar.get(Calendar.MINUTE), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH),
				calendar.get(Calendar.YEAR), calendar.get(Calendar.HOUR_OF_DAY),
				calendar.get(Calendar.MINUTE), true, serviceContext);
	}

}
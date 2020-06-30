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

package com.liferay.commerce.product.subscription.type.web.internal.display.context;

import com.liferay.commerce.product.subscription.type.web.internal.constants.CPSubscriptionTypeConstants;
import com.liferay.commerce.product.subscription.type.web.internal.display.context.util.CPSubscriptionTypeRequestHelper;
import com.liferay.commerce.product.subscription.type.web.internal.display.context.util.comparator.YearlyCPSubscriptionTypeCalendarMonthsComparator;
import com.liferay.commerce.util.CommerceSubscriptionTypeUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class YearlyCPSubscriptionTypeDisplayContext {

	public YearlyCPSubscriptionTypeDisplayContext(
		Object object, HttpServletRequest httpServletRequest, boolean payment) {

		_object = object;
		_payment = payment;

		_cpSubscriptionTypeRequestHelper = new CPSubscriptionTypeRequestHelper(
			httpServletRequest);
	}

	public List<Integer> getCalendarMonths() {
		List<Integer> calendarMonths = new ArrayList<>();

		Map<String, Integer> calendarMonthsDisplayNames =
			getCalendarMonthsDisplayNames();

		for (Map.Entry<String, Integer> entry :
				calendarMonthsDisplayNames.entrySet()) {

			calendarMonths.add(entry.getValue());
		}

		calendarMonths.sort(
			new YearlyCPSubscriptionTypeCalendarMonthsComparator());

		return calendarMonths;
	}

	public int getMonthDay() {
		UnicodeProperties subscriptionTypeSettingsProperties =
			CommerceSubscriptionTypeUtil.getSubscriptionTypeSettingsProperties(
				_object, _payment);

		if ((subscriptionTypeSettingsProperties == null) ||
			subscriptionTypeSettingsProperties.isEmpty()) {

			return 1;
		}

		if (isPayment()) {
			return GetterUtil.getInteger(
				subscriptionTypeSettingsProperties.get("monthDay"));
		}

		return GetterUtil.getInteger(
			subscriptionTypeSettingsProperties.get("deliveryMonthDay"));
	}

	public String getMonthDisplayName(int month) {
		Map<String, Integer> calendarMonthsDisplayNames =
			getCalendarMonthsDisplayNames();

		for (Map.Entry<String, Integer> entry :
				calendarMonthsDisplayNames.entrySet()) {

			if (entry.getValue() == month) {
				return entry.getKey();
			}
		}

		return StringPool.BLANK;
	}

	public int getSelectedMonth() {
		UnicodeProperties subscriptionTypeSettingsProperties =
			CommerceSubscriptionTypeUtil.getSubscriptionTypeSettingsProperties(
				_object, _payment);

		if (subscriptionTypeSettingsProperties == null) {
			return 0;
		}

		if (isPayment()) {
			return GetterUtil.getInteger(
				subscriptionTypeSettingsProperties.get("deliveryMonth"));
		}

		return GetterUtil.getInteger(
			subscriptionTypeSettingsProperties.get("month"));
	}

	public int getSelectedYearlyMode() {
		UnicodeProperties subscriptionTypeSettingsProperties =
			CommerceSubscriptionTypeUtil.getSubscriptionTypeSettingsProperties(
				_object, _payment);

		if (subscriptionTypeSettingsProperties == null) {
			return CPSubscriptionTypeConstants.MODE_ORDER_DATE;
		}

		if (isPayment()) {
			return GetterUtil.getInteger(
				subscriptionTypeSettingsProperties.get("yearlyMode"));
		}

		return GetterUtil.getInteger(
			subscriptionTypeSettingsProperties.get("deliveryYearlyMode"));
	}

	public boolean isPayment() {
		return _payment;
	}

	protected Map<String, Integer> getCalendarMonthsDisplayNames() {
		Calendar calendar = CalendarFactoryUtil.getCalendar(
			_cpSubscriptionTypeRequestHelper.getLocale());

		return calendar.getDisplayNames(
			Calendar.MONTH, Calendar.LONG,
			_cpSubscriptionTypeRequestHelper.getLocale());
	}

	private final CPSubscriptionTypeRequestHelper
		_cpSubscriptionTypeRequestHelper;
	private final Object _object;
	private final boolean _payment;

}
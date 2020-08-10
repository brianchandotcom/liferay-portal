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

package com.liferay.commerce.product.subscription.type.web.internal;

import com.liferay.commerce.product.constants.CPConstants;
import com.liferay.commerce.product.subscription.type.web.internal.constants.CPSubscriptionTypeConstants;
import com.liferay.commerce.product.util.CPSubscriptionType;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = {
		"commerce.product.subscription.type.name=" + CPConstants.YEARLY_SUBSCRIPTION_TYPE,
		"commerce.product.subscription.type.order:Integer=40"
	},
	service = CPSubscriptionType.class
)
public class YearlyCPSubscriptionTypeImpl implements CPSubscriptionType {

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, "year");
	}

	@Override
	public String getName() {
		return CPConstants.YEARLY_SUBSCRIPTION_TYPE;
	}

	@Override
	public Date getSubscriptionNextIterationDate(
		TimeZone timeZone, int subscriptionLength,
		UnicodeProperties subscriptionTypeSettingsProperties,
		Date lastIterationDate) {

		Calendar calendar = CalendarFactoryUtil.getCalendar(timeZone);

		if (lastIterationDate == null) {
			lastIterationDate = getSubscriptionStartDate(
				timeZone, subscriptionTypeSettingsProperties);
		}

		calendar.setTime(lastIterationDate);

		calendar.add(Calendar.YEAR, subscriptionLength);

		int month = GetterUtil.getInteger(
			subscriptionTypeSettingsProperties.get("month"));
		int monthDay = GetterUtil.getInteger(
			subscriptionTypeSettingsProperties.get("monthDay"));

		int dayOfYear = _getDayOfYear(calendar, month, monthDay);

		calendar.set(Calendar.DAY_OF_YEAR, dayOfYear);

		return calendar.getTime();
	}

	@Override
	public Date getSubscriptionStartDate(
		TimeZone timeZone,
		UnicodeProperties subscriptionTypeSettingsProperties) {

		Date now = new Date();

		if ((subscriptionTypeSettingsProperties == null) ||
			subscriptionTypeSettingsProperties.isEmpty()) {

			return now;
		}

		int yearlyMode = GetterUtil.getInteger(
			subscriptionTypeSettingsProperties.get("yearlyMode"));

		if (yearlyMode == CPSubscriptionTypeConstants.MODE_EXACT_DAY_OF_YEAR) {
			return now;
		}

		Calendar calendar = CalendarFactoryUtil.getCalendar(
			now.getTime(), timeZone);

		int today = calendar.get(Calendar.DAY_OF_YEAR);

		int month = GetterUtil.getInteger(
			subscriptionTypeSettingsProperties.get("month"));
		int monthDay = GetterUtil.getInteger(
			subscriptionTypeSettingsProperties.get("monthDay"));

		int dayOfYear = _getDayOfYear(calendar, month, monthDay);

		if (dayOfYear < today) {
			return now;
		}

		calendar.set(Calendar.DAY_OF_YEAR, dayOfYear);

		return calendar.getTime();
	}

	private int _getDayOfYear(Calendar calendar, int month, int monthDay) {
		if (month > 0) {
			calendar.set(Calendar.MONTH, month);
		}
		else {
			monthDay = 0;
		}

		int dayOfMonthActualMaximum = calendar.getActualMaximum(
			Calendar.DAY_OF_MONTH);

		if (monthDay > dayOfMonthActualMaximum) {
			monthDay = dayOfMonthActualMaximum;
		}

		if (monthDay > 0) {
			calendar.set(Calendar.DAY_OF_MONTH, monthDay);
		}

		return calendar.get(Calendar.DAY_OF_YEAR);
	}

}
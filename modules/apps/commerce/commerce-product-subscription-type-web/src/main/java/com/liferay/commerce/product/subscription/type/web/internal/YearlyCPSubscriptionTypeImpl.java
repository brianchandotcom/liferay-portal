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

import com.liferay.commerce.exception.CPSubscriptionTypeSettingsException;
import com.liferay.commerce.product.constants.CPConstants;
import com.liferay.commerce.product.subscription.type.web.internal.constants.CPSubscriptionTypeConstants;
import com.liferay.commerce.product.util.CPSubscriptionType;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.kernel.util.Validator;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = {
		"commerce.product.subscription.type.name=" + CPConstants.YEARLY_SUBSCRIPTION_TYPE,
		"commerce.product.subscription.type.order:Integer=40"
	},
	service = CPSubscriptionType.class
)
public class YearlyCPSubscriptionTypeImpl implements CPSubscriptionType {

	@Override
	public String getLabel(Locale locale) {
		return _language.get(locale, "year");
	}

	@Override
	public String getName() {
		return CPConstants.YEARLY_SUBSCRIPTION_TYPE;
	}

	@Override
	public Date getSubscriptionNextIterationDate(
		TimeZone timeZone, int subscriptionLength,
		UnicodeProperties subscriptionTypeSettingsUnicodeProperties,
		Date lastIterationDate) {

		Calendar calendar = CalendarFactoryUtil.getCalendar(timeZone);

		if (lastIterationDate == null) {
			lastIterationDate = getSubscriptionStartDate(
				timeZone, subscriptionTypeSettingsUnicodeProperties);
		}

		calendar.setTime(lastIterationDate);

		calendar.add(Calendar.YEAR, subscriptionLength);

		int month = GetterUtil.getInteger(
			subscriptionTypeSettingsUnicodeProperties.get("month"));
		int monthDay = GetterUtil.getInteger(
			subscriptionTypeSettingsUnicodeProperties.get("monthDay"));

		int dayOfYear = _getDayOfYear(calendar, month, monthDay);

		calendar.set(Calendar.DAY_OF_YEAR, dayOfYear);

		return calendar.getTime();
	}

	@Override
	public Date getSubscriptionStartDate(
		TimeZone timeZone,
		UnicodeProperties subscriptionTypeSettingsUnicodeProperties) {

		Date date = new Date();

		if ((subscriptionTypeSettingsUnicodeProperties == null) ||
			subscriptionTypeSettingsUnicodeProperties.isEmpty()) {

			return date;
		}

		int yearlyMode = GetterUtil.getInteger(
			subscriptionTypeSettingsUnicodeProperties.get("yearlyMode"));

		if (yearlyMode == CPSubscriptionTypeConstants.MODE_EXACT_DAY_OF_YEAR) {
			return date;
		}

		Calendar calendar = CalendarFactoryUtil.getCalendar(
			date.getTime(), timeZone);

		int today = calendar.get(Calendar.DAY_OF_YEAR);

		int month = GetterUtil.getInteger(
			subscriptionTypeSettingsUnicodeProperties.get("month"));
		int monthDay = GetterUtil.getInteger(
			subscriptionTypeSettingsUnicodeProperties.get("monthDay"));

		int dayOfYear = _getDayOfYear(calendar, month, monthDay);

		if (dayOfYear < today) {
			return date;
		}

		calendar.set(Calendar.DAY_OF_YEAR, dayOfYear);

		return calendar.getTime();
	}

	@Override
	public UnicodeProperties validateDeliverySubscriptionTypeSettingsProperties(
			UnicodeProperties subscriptionTypeSettingsUnicodeProperties)
		throws PortalException {

		return _validateSubscriptionProperties(
			"deliveryYearlyMode", "deliveryMonth", "deliveryMonthDay",
			subscriptionTypeSettingsUnicodeProperties);
	}

	@Override
	public UnicodeProperties validateSubscriptionTypeSettingsProperties(
			UnicodeProperties subscriptionTypeSettingsUnicodeProperties)
		throws PortalException {

		return _validateSubscriptionProperties(
			"yearlyMode", "month", "monthDay",
			subscriptionTypeSettingsUnicodeProperties);
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

	private UnicodeProperties _validateSubscriptionProperties(
			String yearlyModeKey, String monthKey, String monthDayKey,
			UnicodeProperties subscriptionTypeSettingsUnicodeProperties)
		throws CPSubscriptionTypeSettingsException {

		if (subscriptionTypeSettingsUnicodeProperties == null) {
			return null;
		}

		String yearlyModeValue = subscriptionTypeSettingsUnicodeProperties.get(
			yearlyModeKey);

		if (Validator.isBlank(yearlyModeValue)) {
			throw new CPSubscriptionTypeSettingsException(
				"The " + yearlyModeKey + " field is mandatory");
		}

		int yearlyMode = GetterUtil.getInteger(yearlyModeValue, -1);

		if ((yearlyMode < CPSubscriptionTypeConstants.MODE_ORDER_DATE) ||
			(yearlyMode > CPSubscriptionTypeConstants.MODE_EXACT_DAY_OF_YEAR)) {

			throw new CPSubscriptionTypeSettingsException(
				StringBundler.concat(
					"Invalid ", yearlyModeKey, " ", yearlyModeValue));
		}

		HashMapBuilder.HashMapWrapper<String, String>
			subscriptionTypeSettingsProperties = HashMapBuilder.put(
				yearlyModeKey, String.valueOf(yearlyMode));

		if (yearlyMode == CPSubscriptionTypeConstants.MODE_EXACT_DAY_OF_YEAR) {
			String monthValue = subscriptionTypeSettingsUnicodeProperties.get(
				monthKey);

			if (Validator.isBlank(monthValue)) {
				throw new CPSubscriptionTypeSettingsException(
					"The " + monthKey + " field is mandatory");
			}

			int month = GetterUtil.getInteger(monthValue, -1);

			if ((month < 0) || (month > 11)) {
				throw new CPSubscriptionTypeSettingsException(
					StringBundler.concat(
						"Invalid ", monthKey, " ", monthValue));
			}

			subscriptionTypeSettingsProperties.put(
				monthKey, String.valueOf(month));

			String monthDayValue =
				subscriptionTypeSettingsUnicodeProperties.get(monthDayKey);

			if (Validator.isBlank(monthDayValue)) {
				throw new CPSubscriptionTypeSettingsException(
					"The " + monthDayKey + " field is mandatory");
			}

			int monthDay = GetterUtil.getInteger(monthDayValue, -1);

			if ((monthDay < 1) || (monthDay > 31)) {
				throw new CPSubscriptionTypeSettingsException(
					StringBundler.concat(
						"Invalid ", monthDayKey, " ", monthDayValue));
			}

			subscriptionTypeSettingsProperties.put(
				monthDayKey, String.valueOf(monthDay));
		}

		return UnicodePropertiesBuilder.create(
			subscriptionTypeSettingsProperties.build(), true
		).build();
	}

	@Reference
	private Language _language;

}
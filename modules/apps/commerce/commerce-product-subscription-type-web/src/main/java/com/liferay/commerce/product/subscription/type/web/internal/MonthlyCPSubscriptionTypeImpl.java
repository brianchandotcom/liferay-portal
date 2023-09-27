/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		"commerce.product.subscription.type.name=" + CPConstants.MONTHLY_SUBSCRIPTION_TYPE,
		"commerce.product.subscription.type.order:Integer=30"
	},
	service = CPSubscriptionType.class
)
public class MonthlyCPSubscriptionTypeImpl implements CPSubscriptionType {

	@Override
	public UnicodeProperties
			getDeliverySubscriptionTypeSettingsUnicodeProperties(
				UnicodeProperties subscriptionTypeSettingsUnicodeProperties)
		throws PortalException {

		return _getSubscriptionTypeSettingsUnicodeProperties(
			"deliveryMonthlyMode", "deliveryMonthDay",
			subscriptionTypeSettingsUnicodeProperties);
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(locale, "month");
	}

	@Override
	public String getName() {
		return CPConstants.MONTHLY_SUBSCRIPTION_TYPE;
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

		calendar.add(Calendar.MONTH, subscriptionLength);

		int monthlyMode = GetterUtil.getInteger(
			subscriptionTypeSettingsUnicodeProperties.get("monthlyMode"));

		if (monthlyMode ==
				CPSubscriptionTypeConstants.MODE_EXACT_DAY_OF_MONTH) {

			int monthDay = GetterUtil.getInteger(
				subscriptionTypeSettingsUnicodeProperties.get("monthDay"));

			int dayOfMonthActualMaximum = calendar.getActualMaximum(
				Calendar.DAY_OF_MONTH);

			if (monthDay > dayOfMonthActualMaximum) {
				monthDay = dayOfMonthActualMaximum;
			}

			if (monthDay > 0) {
				calendar.set(Calendar.DAY_OF_MONTH, monthDay);
			}
		}

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

		Calendar calendar = CalendarFactoryUtil.getCalendar(
			date.getTime(), timeZone);

		int dayOfMonthActualMaximum = calendar.getActualMaximum(
			Calendar.DAY_OF_MONTH);

		int today = calendar.get(Calendar.DAY_OF_MONTH);

		int monthlyMode = GetterUtil.getInteger(
			subscriptionTypeSettingsUnicodeProperties.get("monthlyMode"));

		if (monthlyMode ==
				CPSubscriptionTypeConstants.MODE_EXACT_DAY_OF_MONTH) {

			int monthDay = GetterUtil.getInteger(
				subscriptionTypeSettingsUnicodeProperties.get("monthDay"));

			if (monthDay > dayOfMonthActualMaximum) {
				monthDay = dayOfMonthActualMaximum;
			}

			if (monthDay < today) {
				return date;
			}

			calendar.set(Calendar.DAY_OF_MONTH, monthDay);
		}
		else if (monthlyMode ==
					CPSubscriptionTypeConstants.MODE_LAST_DAY_OF_MONTH) {

			calendar.set(Calendar.DAY_OF_MONTH, dayOfMonthActualMaximum);
		}

		return calendar.getTime();
	}

	@Override
	public UnicodeProperties getSubscriptionTypeSettingsUnicodeProperties(
			UnicodeProperties subscriptionTypeSettingsUnicodeProperties)
		throws PortalException {

		return _getSubscriptionTypeSettingsUnicodeProperties(
			"monthlyMode", "monthDay",
			subscriptionTypeSettingsUnicodeProperties);
	}

	private UnicodeProperties _getSubscriptionTypeSettingsUnicodeProperties(
			String monthlyModeKey, String monthDayKey,
			UnicodeProperties subscriptionTypeSettingsUnicodeProperties)
		throws CPSubscriptionTypeSettingsException {

		if (subscriptionTypeSettingsUnicodeProperties == null) {
			return null;
		}

		String monthlyModeValue = subscriptionTypeSettingsUnicodeProperties.get(
			monthlyModeKey);

		if (Validator.isBlank(monthlyModeValue)) {
			throw new CPSubscriptionTypeSettingsException(
				"The " + monthlyModeKey + " field is mandatory");
		}

		int monthlyMode = GetterUtil.getInteger(monthlyModeValue, -1);

		return UnicodePropertiesBuilder.create(
			true
		).put(
			monthDayKey,
			() -> {
				if (monthlyMode ==
						CPSubscriptionTypeConstants.MODE_EXACT_DAY_OF_MONTH) {

					String monthDayValue =
						subscriptionTypeSettingsUnicodeProperties.get(
							monthDayKey);

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

					return monthDayValue;
				}

				return null;
			}
		).put(
			monthlyModeKey,
			() -> {
				if ((monthlyMode <
						CPSubscriptionTypeConstants.MODE_ORDER_DATE) ||
					(monthlyMode >
						CPSubscriptionTypeConstants.MODE_LAST_DAY_OF_MONTH)) {

					throw new CPSubscriptionTypeSettingsException(
						StringBundler.concat(
							"Invalid ", monthlyModeKey, " ", monthlyModeValue));
				}

				return monthlyModeValue;
			}
		).build();
	}

	@Reference
	private Language _language;

}
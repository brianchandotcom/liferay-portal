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
import com.liferay.commerce.product.util.CPSubscriptionType;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
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
		"commerce.product.subscription.type.name=" + CPConstants.DAILY_SUBSCRIPTION_TYPE,
		"commerce.product.subscription.type.order:Integer=10"
	},
	service = CPSubscriptionType.class
)
public class DailyCPSubscriptionTypeImpl implements CPSubscriptionType {

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, "day");
	}

	@Override
	public String getName() {
		return CPConstants.DAILY_SUBSCRIPTION_TYPE;
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

		calendar.add(Calendar.DAY_OF_MONTH, subscriptionLength);

		return calendar.getTime();
	}

	@Override
	public Date getSubscriptionStartDate(
		TimeZone timeZone,
		UnicodeProperties subscriptionTypeSettingsProperties) {

		return new Date();
	}

}
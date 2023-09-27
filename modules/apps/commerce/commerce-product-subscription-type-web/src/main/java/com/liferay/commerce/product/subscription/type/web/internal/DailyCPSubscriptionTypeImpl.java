/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.subscription.type.web.internal;

import com.liferay.commerce.product.constants.CPConstants;
import com.liferay.commerce.product.util.CPSubscriptionType;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;

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
		"commerce.product.subscription.type.name=" + CPConstants.DAILY_SUBSCRIPTION_TYPE,
		"commerce.product.subscription.type.order:Integer=10"
	},
	service = CPSubscriptionType.class
)
public class DailyCPSubscriptionTypeImpl implements CPSubscriptionType {

	@Override
	public UnicodeProperties
			getDeliverySubscriptionTypeSettingsUnicodeProperties(
				UnicodeProperties subscriptionTypeSettingsUnicodeProperties)
		throws PortalException {

		return null;
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(locale, "day");
	}

	@Override
	public String getName() {
		return CPConstants.DAILY_SUBSCRIPTION_TYPE;
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

		calendar.add(Calendar.DAY_OF_MONTH, subscriptionLength);

		return calendar.getTime();
	}

	@Override
	public Date getSubscriptionStartDate(
		TimeZone timeZone,
		UnicodeProperties subscriptionTypeSettingsUnicodeProperties) {

		return new Date();
	}

	@Override
	public UnicodeProperties getSubscriptionTypeSettingsUnicodeProperties(
			UnicodeProperties subscriptionTypeSettingsUnicodeProperties)
		throws PortalException {

		return null;
	}

	@Reference
	private Language _language;

}
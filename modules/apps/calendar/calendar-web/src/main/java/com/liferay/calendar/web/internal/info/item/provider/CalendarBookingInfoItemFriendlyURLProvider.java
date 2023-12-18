/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.calendar.web.internal.info.item.provider;

import com.liferay.calendar.model.CalendarBooking;
import com.liferay.friendly.url.info.item.provider.InfoItemFriendlyURLProvider;
import com.liferay.friendly.url.model.FriendlyURLEntryLocalization;
import com.liferay.petra.string.StringPool;

import java.util.Collections;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Adolfo Pérez
 */
@Component(
	property = "item.class.name=com.liferay.calendar.model.CalendarBooking",
	service = InfoItemFriendlyURLProvider.class
)
public class CalendarBookingInfoItemFriendlyURLProvider
	implements InfoItemFriendlyURLProvider<CalendarBooking> {

	@Override
	public String getFriendlyURL(
		CalendarBooking calendarBooking, String languageId) {

		return StringPool.SLASH + calendarBooking.getCalendarBookingId();
	}

	@Override
	public List<FriendlyURLEntryLocalization> getFriendlyURLEntryLocalizations(
		CalendarBooking calendarBooking, String languageId) {

		return Collections.emptyList();
	}

}
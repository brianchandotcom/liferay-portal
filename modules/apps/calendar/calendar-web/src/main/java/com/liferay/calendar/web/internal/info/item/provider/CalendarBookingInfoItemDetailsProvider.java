/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.calendar.web.internal.info.item.provider;

import com.liferay.calendar.model.CalendarBooking;
import com.liferay.info.item.InfoItemClassDetails;
import com.liferay.info.item.InfoItemDetails;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.provider.InfoItemDetailsProvider;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eudaldo Alonso
 */
@Component(service = InfoItemDetailsProvider.class)
public class CalendarBookingInfoItemDetailsProvider
	implements InfoItemDetailsProvider<CalendarBooking> {

	@Override
	public InfoItemClassDetails getInfoItemClassDetails() {
		return new InfoItemClassDetails(CalendarBooking.class.getName());
	}

	@Override
	public InfoItemDetails getInfoItemDetails(CalendarBooking calendarBooking) {
		return new InfoItemDetails(
			getInfoItemClassDetails(),
			new InfoItemReference(
				CalendarBooking.class.getName(),
				calendarBooking.getCalendarBookingId()));
	}

}
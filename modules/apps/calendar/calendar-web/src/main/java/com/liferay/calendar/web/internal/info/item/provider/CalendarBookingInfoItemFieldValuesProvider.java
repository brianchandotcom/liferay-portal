/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.calendar.web.internal.info.item.provider;

import com.liferay.calendar.model.CalendarBooking;
import com.liferay.calendar.web.internal.info.CalendarBookingInfoItemFields;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.Collections;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eudaldo Alonso
 */
@Component(service = InfoItemFieldValuesProvider.class)
public class CalendarBookingInfoItemFieldValuesProvider
	implements InfoItemFieldValuesProvider<CalendarBooking> {

	@Override
	public InfoItemFieldValues getInfoItemFieldValues(
		CalendarBooking calendarBooking) {

		try {
			return InfoItemFieldValues.builder(
			).infoFieldValues(
				_getCalendarBookingInfoFieldValues(calendarBooking)
			).infoItemReference(
				new InfoItemReference(
					CalendarBooking.class.getName(),
					calendarBooking.getCalendarBookingId())
			).build();
		}
		catch (Exception exception) {
			throw new RuntimeException("Unexpected exception", exception);
		}
	}

	private List<InfoFieldValue<Object>> _getCalendarBookingInfoFieldValues(
		CalendarBooking calendarBooking) {

		return Collections.singletonList(
			new InfoFieldValue<>(
				CalendarBookingInfoItemFields.titleInfoField,
				InfoLocalizedValue.<String>builder(
				).defaultLocale(
					LocaleUtil.fromLanguageId(
						calendarBooking.getDefaultLanguageId())
				).values(
					calendarBooking.getTitleMap()
				).build()));
	}

}
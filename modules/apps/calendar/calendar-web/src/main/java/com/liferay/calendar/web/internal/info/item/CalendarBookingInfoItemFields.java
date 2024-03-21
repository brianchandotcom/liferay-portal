/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.calendar.web.internal.info.item;

import com.liferay.calendar.model.CalendarBooking;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.type.BooleanInfoFieldType;
import com.liferay.info.field.type.DateInfoFieldType;
import com.liferay.info.field.type.HTMLInfoFieldType;
import com.liferay.info.field.type.TextInfoFieldType;
import com.liferay.info.field.type.URLInfoFieldType;
import com.liferay.info.localized.InfoLocalizedValue;

/**
 * @author Eudaldo Alonso
 */
public class CalendarBookingInfoItemFields {

	public static final InfoField<BooleanInfoFieldType> allDayInfoField =
		BuilderHolder._builder.infoFieldType(
			BooleanInfoFieldType.INSTANCE
		).name(
			"allDay"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CalendarBookingInfoItemFields.class, "all-day")
		).build();
	public static final InfoField<TextInfoFieldType> calendarNameInfoField =
		BuilderHolder._builder.infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"calendarName"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CalendarBookingInfoItemFields.class, "calendar-name")
		).build();
	public static final InfoField<HTMLInfoFieldType> descriptionInfoField =
		BuilderHolder._builder.infoFieldType(
			HTMLInfoFieldType.INSTANCE
		).name(
			"description"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CalendarBookingInfoItemFields.class, "description")
		).build();
	public static final InfoField<DateInfoFieldType> endDateInfoField =
		BuilderHolder._builder.infoFieldType(
			DateInfoFieldType.INSTANCE
		).name(
			"endDate"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CalendarBookingInfoItemFields.class, "end-date")
		).build();
	public static final InfoField<URLInfoFieldType> eventURLInfoField =
		BuilderHolder._builder.infoFieldType(
			URLInfoFieldType.INSTANCE
		).name(
			"eventURL"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CalendarBookingInfoItemFields.class, "event-url")
		).build();
	public static final InfoField<TextInfoFieldType> invitationsInfoField =
		BuilderHolder._builder.infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"invitations"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CalendarBookingInfoItemFields.class, "invitations")
		).build();
	public static final InfoField<TextInfoFieldType> locationInfoField =
		BuilderHolder._builder.infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"location"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CalendarBookingInfoItemFields.class, "location")
		).build();
	public static final InfoField<TextInfoFieldType> repetitionsInfoField =
		BuilderHolder._builder.infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"repetitions"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CalendarBookingInfoItemFields.class, "repetitions")
		).build();
	public static final InfoField<DateInfoFieldType> startDateInfoField =
		BuilderHolder._builder.infoFieldType(
			DateInfoFieldType.INSTANCE
		).name(
			"startDate"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CalendarBookingInfoItemFields.class, "start-date")
		).build();
	public static final InfoField<TextInfoFieldType> titleInfoField =
		BuilderHolder._builder.infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			"title"
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(
				CalendarBookingInfoItemFields.class, "title")
		).build();

	private static class BuilderHolder {

		private static final InfoField.NamespacedBuilder _builder =
			InfoField.builder(CalendarBooking.class.getSimpleName());

	}

}
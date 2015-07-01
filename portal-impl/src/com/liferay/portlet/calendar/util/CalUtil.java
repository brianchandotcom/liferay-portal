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

package com.liferay.portlet.calendar.util;

import com.liferay.portal.kernel.cal.TZSRecurrence;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.calendar.model.CalEvent;
import com.liferay.util.ContentUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import javax.portlet.PortletPreferences;

/**
 * @author     Brian Wing Shun Chan
 * @deprecated As of 7.0.0, with no direct replacement
 */
@Deprecated
public class CalUtil {

	public static Date getDaylightSavingTimeOffsetDate(
		CalEvent event, TimeZone userTimeZone, Calendar cal, Date date) {

		TZSRecurrence recurrence = event.getRecurrenceObj();

		TimeZone eventTimeZone = recurrence.getTimeZone();

		if (eventTimeZone.inDaylightTime(cal.getTime()) ==
				userTimeZone.inDaylightTime(cal.getTime())) {

			return date;
		}

		Calendar calendar = Calendar.getInstance();

		calendar.setTime(date);

		if (eventTimeZone.inDaylightTime(cal.getTime())) {
			calendar.add(Calendar.HOUR_OF_DAY, -1);
		}
		else {
			calendar.add(Calendar.HOUR_OF_DAY, 1);
		}

		return calendar.getTime();
	}

	public static String getEmailEventReminderBody(
		PortletPreferences preferences) {

		String emailEventReminderBody = preferences.getValue(
			"emailEventReminderBody", StringPool.BLANK);

		if (Validator.isNotNull(emailEventReminderBody)) {
			return emailEventReminderBody;
		}
		else {
			return ContentUtil.get(
				PropsUtil.get(PropsKeys.CALENDAR_EMAIL_EVENT_REMINDER_BODY));
		}
	}

	public static boolean getEmailEventReminderEnabled(
		PortletPreferences preferences) {

		String emailEventReminderEnabled = preferences.getValue(
			"emailEventReminderEnabled", StringPool.BLANK);

		if (Validator.isNotNull(emailEventReminderEnabled)) {
			return GetterUtil.getBoolean(emailEventReminderEnabled);
		}
		else {
			return GetterUtil.getBoolean(
				PropsUtil.get(PropsKeys.CALENDAR_EMAIL_EVENT_REMINDER_ENABLED));
		}
	}

	public static String getEmailEventReminderSubject(
		PortletPreferences preferences) {

		String emailEventReminderSubject = preferences.getValue(
			"emailEventReminderSubject", StringPool.BLANK);

		if (Validator.isNotNull(emailEventReminderSubject)) {
			return emailEventReminderSubject;
		}
		else {
			return ContentUtil.get(
				PropsUtil.get(PropsKeys.CALENDAR_EMAIL_EVENT_REMINDER_SUBJECT));
		}
	}

	public static String getEmailFromAddress(
		PortletPreferences preferences, long companyId) {

		return PortalUtil.getEmailFromAddress(
			preferences, companyId, PropsValues.CALENDAR_EMAIL_FROM_ADDRESS);
	}

	public static String getEmailFromName(
		PortletPreferences preferences, long companyId) {

		return PortalUtil.getEmailFromName(
			preferences, companyId, PropsValues.CALENDAR_EMAIL_FROM_NAME);
	}

	public static Date getEndTime(CalEvent event) {
		long startTime = event.getStartDate().getTime();

		long endTime =
			startTime + (Time.HOUR * event.getDurationHour()) +
				(Time.MINUTE * event.getDurationMinute());

		return new Date(endTime);
	}

	public static boolean isAllDay(
		CalEvent event, TimeZone timeZone, Locale locale) {

		if (event.isAllDay()) {
			return true;
		}

		Calendar cal = null;

		if (event.getTimeZoneSensitive()) {
			cal = CalendarFactoryUtil.getCalendar(timeZone, locale);
		}
		else {
			cal = CalendarFactoryUtil.getCalendar();
		}

		cal.setTime(event.getStartDate());

		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		int second = cal.get(Calendar.SECOND);
		int millisecond = cal.get(Calendar.MILLISECOND);

		int dHour = event.getDurationHour();
		int dMinute = event.getDurationMinute();

		if ((hour == 0) && (minute == 0) && (second == 0) &&
			(millisecond == 0) && (dHour == 24) && (dMinute == 0)) {

			return true;
		}

		return false;
	}

	public static String toString(Calendar cal, String[] types) {
		StringBundler sb = new StringBundler(9);

		if (cal != null) {
			sb.append(cal.get(Calendar.YEAR));
			sb.append(StringPool.PERIOD);
			sb.append(cal.get(Calendar.MONTH));
			sb.append(StringPool.PERIOD);
			sb.append(cal.get(Calendar.DATE));
			sb.append(StringPool.PERIOD);
			sb.append(cal.getTimeZone().getRawOffset());
		}

		if ((types != null) && (types.length > 0) &&
			((types.length > 1) || Validator.isNotNull(types[0]))) {

			sb.append(StringPool.PERIOD);
			sb.append(StringUtil.merge(types, StringPool.PERIOD));
		}

		return sb.toString();
	}

}
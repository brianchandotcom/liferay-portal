/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author Brian Wing Shun Chan
 * @author Marcellus Tavares
 */
public class CalendarFactoryUtil {

	public static Calendar getCalendar() {
		return new GregorianCalendar();
	}

	public static Calendar getCalendar(int year, int month, int date) {
		return new GregorianCalendar(year, month, date);
	}

	public static Calendar getCalendar(
		int year, int month, int date, int hour, int minute) {

		return new GregorianCalendar(year, month, date, hour, minute);
	}

	public static Calendar getCalendar(
		int year, int month, int date, int hour, int minute, int second) {

		return new GregorianCalendar(year, month, date, hour, minute, second);
	}

	public static Calendar getCalendar(
		int year, int month, int date, int hour, int minute, int second,
		int millisecond) {

		return getCalendar(
			year, month, date, hour, minute, second, millisecond,
			TimeZoneUtil.getDefault());
	}

	public static Calendar getCalendar(
		int year, int month, int date, int hour, int minute, int second,
		int millisecond, TimeZone timeZone) {

		Calendar calendar = new GregorianCalendar(timeZone);

		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DATE, date);
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, second);
		calendar.set(Calendar.MILLISECOND, millisecond);

		return calendar;
	}

	public static Calendar getCalendar(Locale locale) {
		return new GregorianCalendar(locale);
	}

	public static Calendar getCalendar(long time) {
		return getCalendar(time, TimeZoneUtil.getDefault());
	}

	public static Calendar getCalendar(long time, TimeZone timeZone) {
		Calendar calendar = new GregorianCalendar(timeZone);

		calendar.setTimeInMillis(time);

		return calendar;
	}

	public static Calendar getCalendar(TimeZone timeZone) {
		return new GregorianCalendar(timeZone);
	}

	public static Calendar getCalendar(TimeZone timeZone, Locale locale) {
		return new GregorianCalendar(timeZone, locale);
	}

}
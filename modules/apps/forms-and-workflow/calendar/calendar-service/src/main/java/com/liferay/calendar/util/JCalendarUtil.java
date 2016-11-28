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

package com.liferay.calendar.util;

import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * @author Eduardo Lundgren
 * @author Peter Shin
 * @author Fabio Pezzutto
 */
public class JCalendarUtil {

	public static final long DAY = Time.HOUR * 24;

	public static final long HOUR = Time.MINUTE * 60;

	public static final long MINUTE = Time.SECOND * 60;

	public static final long MONTH = Time.DAY * 30;

	public static final long SECOND = 1000;

	public static long convertTimeToNewDay(long oldTime, long newTime) {
		Calendar oldJCalendar = getJCalendar(oldTime);
		Calendar newJCalendar = getJCalendar(newTime);

		newJCalendar.set(Calendar.AM_PM, oldJCalendar.get(Calendar.AM_PM));
		newJCalendar.set(Calendar.HOUR, oldJCalendar.get(Calendar.HOUR));
		newJCalendar.set(Calendar.MINUTE, oldJCalendar.get(Calendar.MINUTE));
		newJCalendar.set(Calendar.SECOND, oldJCalendar.get(Calendar.SECOND));

		return newJCalendar.getTimeInMillis();
	}

	public static long getDaysBetween(
		Calendar startTimeJCalendar, Calendar endTimeJCalendar) {

		startTimeJCalendar = toMidnightJCalendar(startTimeJCalendar);
		endTimeJCalendar = toMidnightJCalendar(endTimeJCalendar);

		long startTime = startTimeJCalendar.getTimeInMillis();
		long endTime = endTimeJCalendar.getTimeInMillis();

		return (endTime - startTime) / DAY;
	}

	public static int getDSTShift(
		Calendar jCalendar1, Calendar jCalendar2, TimeZone timeZone) {

		jCalendar1 = getJCalendar(jCalendar1.getTimeInMillis(), timeZone);
		jCalendar2 = getJCalendar(jCalendar2.getTimeInMillis(), timeZone);

		Calendar sameDayJCalendar = getJCalendar(
			jCalendar1.get(Calendar.YEAR), jCalendar1.get(Calendar.MONTH),
			jCalendar1.get(Calendar.DAY_OF_MONTH),
			jCalendar2.get(Calendar.HOUR_OF_DAY),
			jCalendar2.get(Calendar.MINUTE), jCalendar2.get(Calendar.SECOND),
			jCalendar2.get(Calendar.MILLISECOND), timeZone);

		Long shift =
			jCalendar1.getTimeInMillis() - sameDayJCalendar.getTimeInMillis();

		return shift.intValue();
	}

	public static Calendar getJCalendar(Calendar jCalendar, TimeZone timeZone) {
		return CalendarFactoryUtil.getCalendar(
			jCalendar.getTimeInMillis(), timeZone);
	}

	public static Calendar getJCalendar(
		int year, int month, int day, int hour, int minutes, int seconds,
		int milliseconds, TimeZone timeZone) {

		Calendar jCalendar = CalendarFactoryUtil.getCalendar(timeZone);

		jCalendar.set(Calendar.YEAR, year);
		jCalendar.set(Calendar.MONTH, month);
		jCalendar.set(Calendar.DATE, day);
		jCalendar.set(Calendar.HOUR_OF_DAY, hour);
		jCalendar.set(Calendar.MINUTE, minutes);
		jCalendar.set(Calendar.SECOND, seconds);
		jCalendar.set(Calendar.MILLISECOND, milliseconds);

		return jCalendar;
	}

	public static Calendar getJCalendar(long time) {
		return getJCalendar(time, _utcTimeZone);
	}

	public static Calendar getJCalendar(long time, TimeZone timeZone) {
		Calendar jCalendar = CalendarFactoryUtil.getCalendar(timeZone);

		jCalendar.setTimeInMillis(time);

		return jCalendar;
	}

	public static int getTimeZoneOffset(TimeZone timeZone) {
		return timeZone.getOffset(System.currentTimeMillis());
	}

	public static boolean isLaterDay(Calendar jCalendar1, Calendar jCalendar2) {
		Calendar adjustedJCalendar1 = toLastHourJCalendar(jCalendar1);
		Calendar adjustedJCalendar2 = toLastHourJCalendar(jCalendar2);

		if (adjustedJCalendar1.after(adjustedJCalendar2)) {
			return true;
		}
		else {
			return false;
		}
	}

	public static boolean isSameDayOfWeek(
		Calendar jCalendar1, Calendar jCalendar2) {

		int dayOfWeek1 = jCalendar1.get(Calendar.DAY_OF_WEEK);
		int dayOfWeek2 = jCalendar2.get(Calendar.DAY_OF_WEEK);

		if (dayOfWeek1 == dayOfWeek2) {
			return true;
		}
		else {
			return false;
		}
	}

	public static Calendar mergeJCalendar(
		Calendar dateJCalendar, Calendar timeJCalendar, TimeZone timeZone) {

		return CalendarFactoryUtil.getCalendar(
			dateJCalendar.get(Calendar.YEAR), dateJCalendar.get(Calendar.MONTH),
			dateJCalendar.get(Calendar.DAY_OF_MONTH),
			timeJCalendar.get(Calendar.HOUR_OF_DAY),
			timeJCalendar.get(Calendar.MINUTE),
			timeJCalendar.get(Calendar.SECOND),
			timeJCalendar.get(Calendar.MILLISECOND), timeZone);
	}

	public static Calendar toLastHourJCalendar(Calendar jCalendar) {
		Calendar lastHourJCalendar = (Calendar)jCalendar.clone();

		lastHourJCalendar.set(Calendar.HOUR_OF_DAY, 23);
		lastHourJCalendar.set(Calendar.MINUTE, 59);
		lastHourJCalendar.set(Calendar.SECOND, 59);
		lastHourJCalendar.set(Calendar.MILLISECOND, 999);

		return lastHourJCalendar;
	}

	public static Calendar toMidnightJCalendar(Calendar jCalendar) {
		Calendar midnightJCalendar = (Calendar)jCalendar.clone();

		midnightJCalendar.set(Calendar.HOUR_OF_DAY, 0);
		midnightJCalendar.set(Calendar.MINUTE, 0);
		midnightJCalendar.set(Calendar.SECOND, 0);
		midnightJCalendar.set(Calendar.MILLISECOND, 0);

		return midnightJCalendar;
	}

	private static final TimeZone _utcTimeZone = TimeZone.getTimeZone(
		StringPool.UTC);

}
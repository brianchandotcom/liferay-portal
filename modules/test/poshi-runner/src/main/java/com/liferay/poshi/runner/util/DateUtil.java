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

package com.liferay.poshi.runner.util;

import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Brian Wing Shun Chan
 */
public class DateUtil {

	public static String getCurrentDate() {
		return getFormattedCurrentDate("d");
	}

	public static String getCurrentDayOfWeek() {
		return getFormattedCurrentDate("EEEE");
	}

	public static String getCurrentHour() {
		return getFormattedCurrentDate("K");
	}

	public static String getCurrentMonth() {
		return getFormattedCurrentDate("M");
	}

	public static String getCurrentMonthName() {
		return getFormattedCurrentDate("MMMM");
	}

	public static String getCurrentYear() {
		return getFormattedCurrentDate("yyyy");
	}

	public static String getDate(String offsetDays) {
		return getFormattedDate(offsetDays, "d");
	}

	public static String getDayOfWeek(String offsetDays) {
		return getFormattedDate(offsetDays, "EEEE");
	}

	public static String getFormattedCurrentDate(String pattern) {
		return _format(new Date(), pattern);
	}

	public static String getFormattedDate(String offsetDays, String pattern) {
		return _format(_getOffsetDate(Integer.valueOf(offsetDays)), pattern);
	}

	public static String getMonth(String offsetDays) {
		return getFormattedDate(offsetDays, "M");
	}

	public static String getMonthName(String offsetDays) {
		return getFormattedDate(offsetDays, "MMMM");
	}

	public static String getNanoseconds() {
		return String.valueOf(System.nanoTime());
	}

	public static String getYear(String offsetDays) {
		return getFormattedDate(offsetDays, "yyyy");
	}

	private static String _format(Date date, String pattern) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		return simpleDateFormat.format(date);
	}

	private static Date _getOffsetDate(int offsetDays) {
		Calendar calendar = Calendar.getInstance();

		calendar.add(Calendar.DATE, offsetDays);

		return calendar.getTime();
	}

}
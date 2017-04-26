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
import java.util.Locale;

/**
 * @author Brian Wing Shun Chan
 */
public class DateUtil {

	@Override
	public String getCurrentDay() {
		Calendar calendar = Calendar.getInstance();

		return StringUtil.valueOf(calendar.get(Calendar.DATE));
	}

	@Override
	public String getCurrentDayName() {
		Calendar calendar = Calendar.getInstance();

		return StringUtil.valueOf(
			calendar.getDisplayName(
				Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.US));
	}

	@Override
	public String getCurrentHour() {
		Calendar calendar = Calendar.getInstance();

		return StringUtil.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
	}

	@Override
	public String getCurrentMonth() {
		Calendar calendar = Calendar.getInstance();

		return StringUtil.valueOf(calendar.get(Calendar.MONTH) + 1);
	}

	@Override
	public String getCurrentMonthName() {
		Calendar calendar = Calendar.getInstance();

		return String.valueOf(calendar.getDisplayName(
			Calendar.MONTH, Calendar.LONG, Locale.US));
	}

	@Override
	public String getCurrentYear() {
		Calendar calendar = Calendar.getInstance();

		return StringUtil.valueOf(calendar.get(Calendar.YEAR));
	}

	@Override
	public String getDay(String days) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, Integer.valueOf(days));

		return StringUtil.valueOf(calendar.get(Calendar.DATE));
	}

	@Override
	public String getDayName(String days) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, Integer.valueOf(days));

		return StringUtil.valueOf(
			calendar.getDisplayName(
				Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.US));
	}

	@Override
	public String getMonth(String days) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, Integer.valueOf(days));

		return StringUtil.valueOf(calendar.get(Calendar.MONTH) + 1);
	}

	@Override
	public String getMonthName(String days) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, Integer.valueOf(days));

		return String.valueOf(calendar.getDisplayName(
			Calendar.MONTH, Calendar.LONG, Locale.US));
	}

	public static String getNanoseconds() {
		return String.valueOf(System.nanoTime());
	}

	@Override
	public String getYear(String days) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, Integer.valueOf(days));

		return StringUtil.valueOf(calendar.get(Calendar.YEAR));
}
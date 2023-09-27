/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.poshi.runner.util;

import java.text.SimpleDateFormat;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * @author Brian Wing Shun Chan
 */
public class DateUtil {

	public static String format(
			String dateString, String oldPattern, String newPattern)
		throws Exception {

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(oldPattern);

		Date date = simpleDateFormat.parse(dateString);

		simpleDateFormat.applyPattern(newPattern);

		return simpleDateFormat.format(date);
	}

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

	public static String getDateOffsetByDays(String offset, String pattern) {
		return getFormattedDate(
			Calendar.DATE, Integer.valueOf(offset), pattern);
	}

	public static String getDateOffsetByHours(String offset, String pattern) {
		return getFormattedDate(
			Calendar.HOUR, Integer.valueOf(offset), pattern);
	}

	public static String getDateOffsetByMinutes(String offset, String pattern) {
		return getFormattedDate(
			Calendar.MINUTE, Integer.valueOf(offset), pattern);
	}

	public static String getDateOffsetByMonths(String offset, String pattern) {
		return getFormattedDate(
			Calendar.MONTH, Integer.valueOf(offset), pattern);
	}

	public static String getDateOffsetBySeconds(String offset, String pattern) {
		return getFormattedDate(
			Calendar.MINUTE, Integer.valueOf(offset), pattern);
	}

	public static String getDateOffsetByYears(String offset, String pattern) {
		return getFormattedDate(
			Calendar.YEAR, Integer.valueOf(offset), pattern);
	}

	public static String getDayOfWeek(String offsetDays) {
		return getFormattedDate(offsetDays, "EEEE");
	}

	public static String getFormattedCurrentDate(String pattern) {
		return _format(new Date(), pattern);
	}

	public static String getFormattedCurrentDate(
		String pattern, String timeZoneID) {

		return _format(new Date(), pattern, timeZoneID);
	}

	public static String getFormattedDate(
		int field, int offset, String pattern) {

		return _format(_getOffsetDate(field, offset), pattern);
	}

	public static String getFormattedDate(String offsetDays, String pattern) {
		return _format(
			_getOffsetDate(Calendar.DATE, Integer.valueOf(offsetDays)),
			pattern);
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

	public static String getTimeInMilliseconds() {
		return String.valueOf(System.currentTimeMillis());
	}

	public static String getTimeInMilliseconds(String date, String timeZone) {
		LocalDateTime localDateTime = LocalDateTime.parse(
			date, DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));

		ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of(timeZone));

		Instant instant = zonedDateTime.toInstant();

		return String.valueOf(instant.toEpochMilli());
	}

	public static String getYear(String offsetDays) {
		return getFormattedDate(offsetDays, "yyyy");
	}

	private static String _format(Date date, String pattern) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		return simpleDateFormat.format(date);
	}

	private static String _format(
		Date date, String pattern, String timeZoneID) {

		List<String> availableTimeZoneIDs = new ArrayList<>(
			Arrays.asList(TimeZone.getAvailableIDs()));

		if (!availableTimeZoneIDs.contains(timeZoneID)) {
			throw new IllegalArgumentException(
				"Invalid time zone ID: " + timeZoneID);
		}

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		simpleDateFormat.setTimeZone(TimeZone.getTimeZone(timeZoneID));

		return simpleDateFormat.format(date);
	}

	private static Date _getOffsetDate(int field, int offset) {
		Calendar calendar = Calendar.getInstance();

		calendar.add(field, offset);

		return calendar.getTime();
	}

}
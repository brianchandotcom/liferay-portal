/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.cal.Duration;
import com.liferay.portal.kernel.cal.Recurrence;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Assert;

/**
 * @author Douglas Wong
 */
public abstract class RecurrenceTestCase {

	public static final int APRIL = Calendar.APRIL;

	public static final int AUGUST = Calendar.AUGUST;

	public static final int DECEMBER = Calendar.DECEMBER;

	public static final int FEBRUARY = Calendar.FEBRUARY;

	public static final int FRIDAY = Calendar.FRIDAY;

	public static final int JANUARY = Calendar.JANUARY;

	public static final int JULY = Calendar.JULY;

	public static final int JUNE = Calendar.JUNE;

	public static final int MARCH = Calendar.MARCH;

	public static final int MAY = Calendar.MAY;

	public static final int MONDAY = Calendar.MONDAY;

	public static final int NOVEMBER = Calendar.NOVEMBER;

	public static final int OCTOBER = Calendar.OCTOBER;

	public static final int SATURDAY = Calendar.SATURDAY;

	public static final int SEPTEMBER = Calendar.SEPTEMBER;

	public static final int SUNDAY = Calendar.SUNDAY;

	public static final int THURSDAY = Calendar.THURSDAY;

	public static final int TUESDAY = Calendar.TUESDAY;

	public static final int WEDNESDAY = Calendar.WEDNESDAY;

	protected void assertRecurrenceEquals(
		boolean expected, Recurrence recurrence, Calendar calendar) {

		Assert.assertEquals(expected, recurrence.isInRecurrence(calendar));
	}

	protected Calendar getCalendar(
		int year, int month, int date, int hour, int minute) {

		Calendar calendar = new GregorianCalendar();

		calendar.set(year, month, date, hour, minute);

		return calendar;
	}

	protected Duration getDefaultDuration() {
		return getDuration(0, 0, 1, 0, 0);
	}

	protected Duration getDuration(
		int weeks, int days, int hours, int minutes, int seconds) {

		Duration duration = new Duration(days, hours, minutes, seconds);

		duration.setWeeks(weeks);

		return duration;
	}

	protected Calendar dtStart = getCalendar(2008, FEBRUARY, 5, 22, 10);
	protected Duration durationCrossWeek = getDuration(0, 8, 0, 0, 0);
	protected Duration durationOneHour = getDuration(0, 0, 1, 0, 0);
	protected Duration durationTwoHours = getDuration(0, 0, 2, 0, 0);

}
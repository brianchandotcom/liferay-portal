/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

/*
 * Copyright (c) 2000, Columbia University.  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *	  notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *	  notice, this list of conditions and the following disclaimer in the
 *	  documentation and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the University nor the names of its contributors
 *	  may be used to endorse or promote products derived from this software
 *	  without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS ``AS
 * IS'' AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.liferay.portal.kernel.cal;

import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.TimeZoneUtil;

import java.io.Serializable;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Jonathan Lennox
 */
public class Recurrence implements Serializable {

	/**
	 * Field DAILY
	 */
	public static final int DAILY = 3;

	/**
	 * Field MONTHLY
	 */
	public static final int MONTHLY = 5;

	/**
	 * Field NO_RECURRENCE
	 */
	public static final int NO_RECURRENCE = 7;

	/**
	 * Field WEEKLY
	 */
	public static final int WEEKLY = 4;

	/**
	 * Field YEARLY
	 */
	public static final int YEARLY = 6;

	/**
	 * Constructor Recurrence
	 */
	public Recurrence() {
		this(null, new Duration(), NO_RECURRENCE);
	}

	/**
	 * Constructor Recurrence
	 */
	public Recurrence(Calendar start, Duration dur) {
		this(start, dur, NO_RECURRENCE);
	}

	/**
	 * Constructor Recurrence
	 */
	public Recurrence(Calendar start, Duration dur, int freq) {
		setDtStart(start);

		duration = (Duration)dur.clone();
		frequency = freq;
		interval = 1;
	}

	/* Accessors */

	/**
	 * Method getByDay
	 *
	 * @return DayAndPosition[]
	 */
	public DayAndPosition[] getByDay() {
		if (byDay == null) {
			return null;
		}

		DayAndPosition[] b = new DayAndPosition[byDay.length];

		/*
		 * System.arraycopy isn't good enough -- we want to clone each
		 * individual element.
		 */
		for (int i = 0; i < byDay.length; i++) {
			b[i] = (DayAndPosition)byDay[i].clone();
		}

		return b;
	}

	/**
	 * Method getByMonth
	 *
	 * @return int[]
	 */
	public int[] getByMonth() {
		if (byMonth == null) {
			return null;
		}

		int[] b = new int[byMonth.length];

		System.arraycopy(byMonth, 0, b, 0, byMonth.length);

		return b;
	}

	/**
	 * Method getByMonthDay
	 *
	 * @return int[]
	 */
	public int[] getByMonthDay() {
		if (byMonthDay == null) {
			return null;
		}

		int[] b = new int[byMonthDay.length];

		System.arraycopy(byMonthDay, 0, b, 0, byMonthDay.length);

		return b;
	}

	/**
	 * Method getByWeekNo
	 *
	 * @return int[]
	 */
	public int[] getByWeekNo() {
		if (byWeekNo == null) {
			return null;
		}

		int[] b = new int[byWeekNo.length];

		System.arraycopy(byWeekNo, 0, b, 0, byWeekNo.length);

		return b;
	}

	/**
	 * Method getByYearDay
	 *
	 * @return int[]
	 */
	public int[] getByYearDay() {
		if (byYearDay == null) {
			return null;
		}

		int[] b = new int[byYearDay.length];

		System.arraycopy(byYearDay, 0, b, 0, byYearDay.length);

		return b;
	}

	/**
	 * Method getCandidateStartTime
	 *
	 * @param  current the current time
	 * @return Calendar
	 */
	public Calendar getCandidateStartTime(Calendar current) {
		if (dtStart.getTime().getTime() > current.getTime().getTime()) {
			throw new IllegalArgumentException("Current time before DtStart");
		}

		int minInterval = getMinimumInterval();
		Calendar candidate = (Calendar)current.clone();

		if (true) {

			// This block is only needed while this function is public...

			candidate.clear(Calendar.ZONE_OFFSET);
			candidate.clear(Calendar.DST_OFFSET);
			candidate.setTimeZone(TimeZoneUtil.getTimeZone(StringPool.UTC));
			candidate.setMinimalDaysInFirstWeek(4);
			candidate.setFirstDayOfWeek(dtStart.getFirstDayOfWeek());
		}

		if (frequency == NO_RECURRENCE) {
			candidate.setTime(dtStart.getTime());

			return candidate;
		}

		reduce_constant_length_field(Calendar.SECOND, dtStart, candidate);
		reduce_constant_length_field(Calendar.MINUTE, dtStart, candidate);
		reduce_constant_length_field(Calendar.HOUR_OF_DAY, dtStart, candidate);

		switch (minInterval) {

			case DAILY :

				/* No more adjustments needed */

				break;

			case WEEKLY :
				reduce_constant_length_field(
					Calendar.DAY_OF_WEEK, dtStart, candidate);
				break;

			case MONTHLY :
				reduce_day_of_month(dtStart, candidate);
				break;

			case YEARLY :
				reduce_day_of_year(dtStart, candidate);
				break;
		}

		return candidate;
	}

	/**
	 * Method getDtEnd
	 *
	 * @return Calendar
	 */
	public Calendar getDtEnd() {

		/*
		 * Make dtEnd a cloned dtStart, so non-time fields of the Calendar
		 * are accurate.
		 */
		Calendar tempEnd = (Calendar)dtStart.clone();

		tempEnd.setTime(
			new Date(dtStart.getTime().getTime() + duration.getInterval()));

		return tempEnd;
	}

	/**
	 * Method getDtStart
	 *
	 * @return Calendar
	 */
	public Calendar getDtStart() {
		return (Calendar)dtStart.clone();
	}

	/**
	 * Method getDuration
	 *
	 * @return Duration
	 */
	public Duration getDuration() {
		return (Duration)duration.clone();
	}

	/**
	 * Method getFrequency
	 *
	 * @return int
	 */
	public int getFrequency() {
		return frequency;
	}

	/**
	 * Method getInterval
	 *
	 * @return int
	 */
	public int getInterval() {
		return interval;
	}

	/**
	 * Method getOccurrence
	 *
	 * @return int
	 */
	public int getOccurrence() {
		return occurrence;
	}

	/**
	 * Method getUntil
	 *
	 * @return Calendar
	 */
	public Calendar getUntil() {
		return ((until != null) ? (Calendar)until.clone() : null);
	}

	/**
	 * Method getWeekStart
	 *
	 * @return int
	 */
	public int getWeekStart() {
		return dtStart.getFirstDayOfWeek();
	}

	/**
	 * Method isInRecurrence
	 *
	 * @param  current the current time
	 * @return boolean
	 */
	public boolean isInRecurrence(Calendar current) {
		return isInRecurrence(current, false);
	}

	/**
	 * Method isInRecurrence
	 *
	 * @param  current the current time
	 * @param  debug whether to print debug messages
	 * @return boolean
	 */
	public boolean isInRecurrence(Calendar current, boolean debug) {
		Calendar myCurrent = (Calendar)current.clone();

		// Do all calculations in GMT.  Keep other parameters consistent.

		myCurrent.clear(Calendar.ZONE_OFFSET);
		myCurrent.clear(Calendar.DST_OFFSET);
		myCurrent.setTimeZone(TimeZoneUtil.getTimeZone(StringPool.UTC));
		myCurrent.setMinimalDaysInFirstWeek(4);
		myCurrent.setFirstDayOfWeek(dtStart.getFirstDayOfWeek());
		myCurrent.set(Calendar.SECOND, 0);
		myCurrent.set(Calendar.MILLISECOND, 0);

		if (myCurrent.getTime().getTime() < dtStart.getTime().getTime()) {

			// The current time is earlier than the start time.

			if (debug) {
				System.err.println("current < start");
			}

			return false;
		}

		Calendar candidate = getCandidateStartTime(myCurrent);

		/* Loop over ranges for the duration. */

		while ((candidate.getTime().getTime() + duration.getInterval()) >
					myCurrent.getTime().getTime()) {

			if (candidateIsInRecurrence(candidate, debug)) {
				return true;
			}

			/* Roll back to one second previous, and try again. */

			candidate.add(Calendar.SECOND, -1);

			/* Make sure we haven't rolled back to before dtStart. */

			if (candidate.getTime().getTime() < dtStart.getTime().getTime()) {
				if (debug) {
					System.err.println("No candidates after dtStart");
				}

				return false;
			}

			candidate = getCandidateStartTime(candidate);
		}

		if (debug) {
			System.err.println("No matching candidates");
		}

		return false;
	}

	/**
	 * Method setByDay
	 */
	public void setByDay(DayAndPosition[] b) {
		if (b == null) {
			byDay = null;

			return;
		}

		byDay = new DayAndPosition[b.length];

		/*
		 * System.arraycopy isn't good enough -- we want to clone each
		 * individual element.
		 */
		for (int i = 0; i < b.length; i++) {
			byDay[i] = (DayAndPosition)b[i].clone();
		}
	}

	/**
	 * Method setByMonth
	 */
	public void setByMonth(int[] b) {
		if (b == null) {
			byMonth = null;

			return;
		}

		byMonth = new int[b.length];

		System.arraycopy(b, 0, byMonth, 0, b.length);
	}

	/**
	 * Method setByMonthDay
	 */
	public void setByMonthDay(int[] b) {
		if (b == null) {
			byMonthDay = null;

			return;
		}

		byMonthDay = new int[b.length];

		System.arraycopy(b, 0, byMonthDay, 0, b.length);
	}

	/**
	 * Method setByWeekNo
	 */
	public void setByWeekNo(int[] b) {
		if (b == null) {
			byWeekNo = null;

			return;
		}

		byWeekNo = new int[b.length];

		System.arraycopy(b, 0, byWeekNo, 0, b.length);
	}

	/**
	 * Method setByYearDay
	 */
	public void setByYearDay(int[] b) {
		if (b == null) {
			byYearDay = null;

			return;
		}

		byYearDay = new int[b.length];

		System.arraycopy(b, 0, byYearDay, 0, b.length);
	}

	/**
	 * Method setDtEnd
	 */
	public void setDtEnd(Calendar end) {
		Calendar tempEnd = (Calendar)end.clone();

		tempEnd.clear(Calendar.ZONE_OFFSET);
		tempEnd.clear(Calendar.DST_OFFSET);
		tempEnd.setTimeZone(TimeZoneUtil.getTimeZone(StringPool.UTC));
		duration.setInterval(
			tempEnd.getTime().getTime() - dtStart.getTime().getTime());
	}

	/**
	 * Method setDtStart
	 */
	public void setDtStart(Calendar start) {
		int oldStart;

		if (dtStart != null) {
			oldStart = dtStart.getFirstDayOfWeek();
		}
		else {
			oldStart = Calendar.MONDAY;
		}

		if (start == null) {
			dtStart = CalendarFactoryUtil.getCalendar(
				TimeZoneUtil.getTimeZone(StringPool.UTC));

			dtStart.setTime(new Date(0L));
		}
		else {
			dtStart = (Calendar)start.clone();

			dtStart.clear(Calendar.ZONE_OFFSET);
			dtStart.clear(Calendar.DST_OFFSET);
			dtStart.setTimeZone(TimeZoneUtil.getTimeZone(StringPool.UTC));
		}

		dtStart.setMinimalDaysInFirstWeek(4);
		dtStart.setFirstDayOfWeek(oldStart);
		dtStart.set(Calendar.SECOND, 0);
		dtStart.set(Calendar.MILLISECOND, 0);
	}

	/**
	 * Method setDuration
	 */
	public void setDuration(Duration d) {
		duration = (Duration)d.clone();
	}

	/**
	 * Method setFrequency
	 */
	public void setFrequency(int freq) {
		if ((frequency != DAILY) && (frequency != WEEKLY) &&
			(frequency != MONTHLY) && (frequency != YEARLY) &&
			(frequency != NO_RECURRENCE)) {

			throw new IllegalArgumentException("Invalid frequency");
		}

		frequency = freq;
	}

	/**
	 * Method setInterval
	 */
	public void setInterval(int intr) {
		interval = (intr > 0) ? intr : 1;
	}

	/**
	 * Method setOccurrence
	 */
	public void setOccurrence(int occur) {
		occurrence = occur;
	}

	/**
	 * Method setUntil
	 */
	public void setUntil(Calendar u) {
		if (u == null) {
			until = null;

			return;
		}

		until = (Calendar)u.clone();

		until.clear(Calendar.ZONE_OFFSET);
		until.clear(Calendar.DST_OFFSET);
		until.setTimeZone(TimeZoneUtil.getTimeZone(StringPool.UTC));
	}

	/**
	 * Method setWeekStart
	 */
	public void setWeekStart(int weekstart) {
		dtStart.setFirstDayOfWeek(weekstart);
	}

	/**
	 * Method toString
	 *
	 * @return String
	 */
	@Override
	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append(getClass().getName());
		sb.append("[dtStart=");
		sb.append((dtStart != null) ? dtStart.toString() : "null");
		sb.append(",duration=");
		sb.append((duration != null) ? duration.toString() : "null");
		sb.append(",frequency=");
		sb.append(frequency);
		sb.append(",interval=");
		sb.append(interval);
		sb.append(",until=");
		sb.append((until != null) ? until.toString() : "null");
		sb.append(",byDay=");

		if (byDay == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < byDay.length; i++) {
				if (i != 0) {
					sb.append(",");
				}

				if (byDay[i] != null) {
					sb.append(byDay[i].toString());
				}
				else {
					sb.append("null");
				}
			}

			sb.append("]");
		}

		sb.append(",byMonthDay=");
		sb.append(stringizeIntArray(byMonthDay));
		sb.append(",byYearDay=");
		sb.append(stringizeIntArray(byYearDay));
		sb.append(",byWeekNo=");
		sb.append(stringizeIntArray(byWeekNo));
		sb.append(",byMonth=");
		sb.append(stringizeIntArray(byMonth));
		sb.append(']');

		return sb.toString();
	}

	/**
	 * Method getDayNumber
	 *
	 * @return long
	 */
	protected static long getDayNumber(Calendar cal) {
		Calendar tempCal = (Calendar)cal.clone();

		// Set to midnight, GMT

		tempCal.set(Calendar.MILLISECOND, 0);
		tempCal.set(Calendar.SECOND, 0);
		tempCal.set(Calendar.MINUTE, 0);
		tempCal.set(Calendar.HOUR_OF_DAY, 0);

		return tempCal.getTime().getTime() / (24 * 60 * 60 * 1000);
	}

	/**
	 * Method getMonthNumber
	 *
	 * @return long
	 */
	protected static long getMonthNumber(Calendar cal) {
		return
			((cal.get(Calendar.YEAR) - 1970) * 12L) +
				((cal.get(Calendar.MONTH) - Calendar.JANUARY));
	}

	/**
	 * Method getWeekNumber
	 *
	 * @return long
	 */
	protected static long getWeekNumber(Calendar cal) {
		Calendar tempCal = (Calendar)cal.clone();

		// Set to midnight, GMT

		tempCal.set(Calendar.MILLISECOND, 0);
		tempCal.set(Calendar.SECOND, 0);
		tempCal.set(Calendar.MINUTE, 0);
		tempCal.set(Calendar.HOUR_OF_DAY, 0);

		// Roll back to the first day of the week

		int delta =
			tempCal.getFirstDayOfWeek() - tempCal.get(Calendar.DAY_OF_WEEK);

		if (delta > 0) {
			delta -= 7;
		}

		// tempCal now points to the first instant of this week.

		// Calculate the "week epoch" -- the weekstart day closest to January 1,
		// 1970 (which was a Thursday)

		long weekEpoch =
			(tempCal.getFirstDayOfWeek() - Calendar.THURSDAY) * 24L * 60 * 60 *
				1000;

		return
			(tempCal.getTime().getTime() - weekEpoch) /
				(7 * 24 * 60 * 60 * 1000);
	}

	/**
	 * Method reduce_constant_length_field
	 */
	protected static void reduce_constant_length_field(
		int field, Calendar start, Calendar candidate) {

		if ((start.getMaximum(field) != start.getLeastMaximum(field)) ||
			(start.getMinimum(field) != start.getGreatestMinimum(field))) {

			throw new IllegalArgumentException("Not a constant length field");
		}

		int fieldLength =
			(start.getMaximum(field) - start.getMinimum(field) + 1);
		int delta = start.get(field) - candidate.get(field);

		if (delta > 0) {
			delta -= fieldLength;
		}

		candidate.add(field, delta);
	}

	/**
	 * Method reduce_day_of_month
	 */
	protected static void reduce_day_of_month(
		Calendar start, Calendar candidate) {

		Calendar tempCal = (Calendar)candidate.clone();

		tempCal.add(Calendar.MONTH, -1);

		int delta = start.get(Calendar.DATE) - candidate.get(Calendar.DATE);

		if (delta > 0) {
			delta -= tempCal.getActualMaximum(Calendar.DATE);
		}

		candidate.add(Calendar.DATE, delta);

		while (start.get(Calendar.DATE) != candidate.get(Calendar.DATE)) {
			tempCal.add(Calendar.MONTH, -1);
			candidate.add(
				Calendar.DATE, -tempCal.getActualMaximum(Calendar.DATE));
		}
	}

	/**
	 * Method reduce_day_of_year
	 */
	protected static void reduce_day_of_year(
		Calendar start, Calendar candidate) {

		if ((start.get(Calendar.MONTH) > candidate.get(Calendar.MONTH)) ||
			((start.get(Calendar.MONTH) == candidate.get(Calendar.MONTH)) &&
			 (start.get(Calendar.DATE) > candidate.get(Calendar.DATE)))) {

			candidate.add(Calendar.YEAR, -1);
		}

		/* Set the candidate date to the start date. */

		candidate.set(Calendar.MONTH, start.get(Calendar.MONTH));
		candidate.set(Calendar.DATE, start.get(Calendar.DATE));

		while ((start.get(Calendar.MONTH) != candidate.get(Calendar.MONTH)) ||
			   (start.get(Calendar.DATE) != candidate.get(Calendar.DATE))) {

			candidate.add(Calendar.YEAR, -1);
			candidate.set(Calendar.MONTH, start.get(Calendar.MONTH));
			candidate.set(Calendar.DATE, start.get(Calendar.DATE));
		}
	}

	/**
	 * Method candidateIsInRecurrence
	 *
	 * @return boolean
	 */
	protected boolean candidateIsInRecurrence(
		Calendar candidate, boolean debug) {

		if ((until != null) &&
			(candidate.getTime().getTime() > until.getTime().getTime())) {

			// After "until"

			if (debug) {
				System.err.println("after until");
			}

			return false;
		}

		if ((getRecurrenceCount(candidate) % interval) != 0) {

			// Not a repetition of the interval

			if (debug) {
				System.err.println("not an interval rep");
			}

			return false;
		}
		else if ((occurrence > 0) &&
				 (getRecurrenceCount(candidate) >= occurrence)) {

			return false;
		}

		if (!matchesByDay(candidate) || !matchesByMonthDay(candidate) ||
			!matchesByYearDay(candidate) || !matchesByWeekNo(candidate) ||
			!matchesByMonth(candidate)) {

			// Doesn't match a by* rule

			if (debug) {
				System.err.println("doesn't match a by*");
			}

			return false;
		}

		if (debug) {
			System.err.println("All checks succeeded");
		}

		return true;
	}

	/**
	 * Method getMinimumInterval
	 *
	 * @return int
	 */
	protected int getMinimumInterval() {
		if ((frequency == DAILY) || (byDay != null) || (byMonthDay != null) ||
			(byYearDay != null)) {

			return DAILY;
		}
		else if ((frequency == WEEKLY) || (byWeekNo != null)) {
			return WEEKLY;
		}
		else if ((frequency == MONTHLY) || (byMonth != null)) {
			return MONTHLY;
		}
		else if (frequency == YEARLY) {
			return YEARLY;
		}
		else if (frequency == NO_RECURRENCE) {
			return NO_RECURRENCE;
		}
		else {

			// Shouldn't happen

			throw new IllegalStateException(
				"Internal error: Unknown frequency value");
		}
	}

	/**
	 * Method getRecurrenceCount
	 *
	 * @return int
	 */
	protected int getRecurrenceCount(Calendar candidate) {
		switch (frequency) {

			case NO_RECURRENCE :
				return 0;

			case DAILY :
				return (int)(getDayNumber(candidate) - getDayNumber(dtStart));

			case WEEKLY :
				Calendar tempCand = (Calendar)candidate.clone();

				tempCand.setFirstDayOfWeek(dtStart.getFirstDayOfWeek());

				return (int)(getWeekNumber(tempCand) - getWeekNumber(dtStart));

			case MONTHLY :
				return
					(int)(getMonthNumber(candidate) - getMonthNumber(dtStart));

			case YEARLY :
				return
					candidate.get(Calendar.YEAR) - dtStart.get(Calendar.YEAR);

			default :
				throw new IllegalStateException("bad frequency internally...");
		}
	}

	/**
	 * Method matchesByDay
	 *
	 * @return boolean
	 */
	protected boolean matchesByDay(Calendar candidate) {
		if ((byDay == null) || (byDay.length == 0)) {

			/* No byDay rules, so it matches trivially */

			return true;
		}

		int i;

		for (i = 0; i < byDay.length; i++) {
			if (matchesIndividualByDay(candidate, byDay[i])) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Method matchesByField
	 *
	 * @return boolean
	 */
	protected boolean matchesByField(
		int[] array, int field, Calendar candidate, boolean allowNegative) {

		if ((array == null) || (array.length == 0)) {

			/* No rules, so it matches trivially */

			return true;
		}

		int i;

		for (i = 0; i < array.length; i++) {
			int val;

			if (allowNegative && (array[i] < 0)) {

				// byMonthDay = -1, in a 31-day month, means 31

				int max = candidate.getActualMaximum(field);

				val = (max + 1) + array[i];
			}
			else {
				val = array[i];
			}

			if (val == candidate.get(field)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Method matchesByMonth
	 *
	 * @return boolean
	 */
	protected boolean matchesByMonth(Calendar candidate) {
		return matchesByField(byMonth, Calendar.MONTH, candidate, false);
	}

	/**
	 * Method matchesByMonthDay
	 *
	 * @return boolean
	 */
	protected boolean matchesByMonthDay(Calendar candidate) {
		return matchesByField(byMonthDay, Calendar.DATE, candidate, true);
	}

	/**
	 * Method matchesByWeekNo
	 *
	 * @return boolean
	 */
	protected boolean matchesByWeekNo(Calendar candidate) {
		return matchesByField(byWeekNo, Calendar.WEEK_OF_YEAR, candidate, true);
	}

	/**
	 * Method matchesByYearDay
	 *
	 * @return boolean
	 */
	protected boolean matchesByYearDay(Calendar candidate) {
		return matchesByField(byYearDay, Calendar.DAY_OF_YEAR, candidate, true);
	}

	/**
	 * Method matchesIndividualByDay
	 *
	 * @return boolean
	 */
	protected boolean matchesIndividualByDay(
		Calendar candidate, DayAndPosition pos) {

		if (pos.getDayOfWeek() != candidate.get(Calendar.DAY_OF_WEEK)) {
			return false;
		}

		int position = pos.getDayPosition();

		if (position == 0) {
			return true;
		}

		int field = Calendar.DAY_OF_MONTH;

		if (position > 0) {
			int candidatePosition = ((candidate.get(field) - 1) / 7) + 1;

			return (position == candidatePosition);
		}
		else {

			/* position < 0 */

			int negativeCandidatePosition =
				((candidate.getActualMaximum(field) - candidate.get(field)) /
					7) + 1;

			return (-position == negativeCandidatePosition);
		}
	}

	/**
	 * Method stringizeIntArray
	 *
	 * @return String
	 */
	protected String stringizeIntArray(int[] a) {
		if (a == null) {
			return "null";
		}

		StringBundler sb = new StringBundler(2 * a.length + 1);

		sb.append("[");

		for (int i = 0; i < a.length; i++) {
			if (i != 0) {
				sb.append(",");
			}

			sb.append(a[i]);
		}

		sb.append("]");

		return sb.toString();
	}

	/**
	 * Field byDay
	 */
	protected DayAndPosition[] byDay;

	/**
	 * Field byMonth
	 */
	protected int[] byMonth;

	/**
	 * Field byMonthDay
	 */
	protected int[] byMonthDay;

	/**
	 * Field byWeekNo
	 */
	protected int[] byWeekNo;

	/**
	 * Field byYearDay
	 */
	protected int[] byYearDay;

	/**
	 * Field dtStart
	 */
	protected Calendar dtStart;

	/**
	 * Field duration
	 */
	protected Duration duration;

	/**
	 * Field frequency
	 */
	protected int frequency;

	/**
	 * Field interval
	 */
	protected int interval;

	/**
	 * Field interval
	 */
	protected int occurrence = 0;

	/**
	 * Field until
	 */
	protected Calendar until;

}
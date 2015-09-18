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

package com.liferay.calendar.util.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.calendar.model.CalendarBooking;
import com.liferay.calendar.model.impl.CalendarBookingImpl;
import com.liferay.calendar.util.CalendarBookingIterator;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.text.ParseException;

import java.util.Calendar;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Adam Brandizzi
 */
@RunWith(Arquillian.class)
public class CalendarBookingIteratorTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testCount() throws ParseException {
		CalendarBooking calendarBooking = new CalendarBookingImpl();

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

		calendarBooking.setStartTime(calendar.getTimeInMillis());
		calendarBooking.setRecurrence(
			"RRULE:FREQ=WEEKLY;COUNT=2;INTERVAL=1;BYDAY=MO");

		CalendarBookingIterator iterator = new CalendarBookingIterator(
			calendarBooking);
		int count = 0;

		while (iterator.hasNext()) {
			iterator.next();
			count++;
		}

		Assert.assertEquals(2, count);
	}

	@Test
	public void testCountIncludesStartDayOutOfWeeklyRepeatingDays()
		throws ParseException {

		CalendarBooking calendarBooking = new CalendarBookingImpl();

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

		calendarBooking.setStartTime(calendar.getTimeInMillis());
		calendarBooking.setRecurrence(
			"RRULE:FREQ=WEEKLY;COUNT=2;INTERVAL=1;BYDAY=WE");

		CalendarBookingIterator iterator = new CalendarBookingIterator(
			calendarBooking);
		int count = 0;

		while (iterator.hasNext()) {
			iterator.next();
			count++;
		}

		Assert.assertEquals(2, count);
	}

	@Test
	public void testNoRecurrence() throws ParseException {
		CalendarBooking calendarBooking = new CalendarBookingImpl();

		Calendar calendar = Calendar.getInstance();

		calendarBooking.setStartTime(calendar.getTimeInMillis());
		calendarBooking.setRecurrence(null);

		CalendarBookingIterator iterator = new CalendarBookingIterator(
			calendarBooking);
		int count = 0;

		while (iterator.hasNext()) {
			iterator.next();
			count++;
		}

		Assert.assertEquals(1, count);
	}

}
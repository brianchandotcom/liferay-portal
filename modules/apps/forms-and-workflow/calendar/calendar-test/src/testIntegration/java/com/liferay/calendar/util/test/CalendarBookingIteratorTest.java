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
import com.liferay.calendar.model.CalendarBookingWrapper;
import com.liferay.calendar.recurrence.Recurrence;
import com.liferay.calendar.recurrence.RecurrenceSerializer;
import com.liferay.calendar.service.CalendarBookingLocalService;
import com.liferay.calendar.util.CalendarBookingIterator;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.TimeZoneUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.text.ParseException;

import java.util.Calendar;
import java.util.TimeZone;

import org.junit.Assert;
import org.junit.Before;
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

	@Before
	public void setUp() throws Exception {
		CalendarBooking calendarBooking =
			_calendarBookingLocalService.createCalendarBooking(
				RandomTestUtil.nextLong());

		_calendarBooking = new CalendarBookingWrapper(calendarBooking) {

			@Override
			public Recurrence getRecurrenceObj() {
				return RecurrenceSerializer.deserialize(
					getRecurrence(), getTimeZone());
			}

			@Override
			public TimeZone getTimeZone() {
				return TimeZoneUtil.getTimeZone(StringPool.UTC);
			}

		};
	}

	@Test
	public void testRecurrenceIsNull() throws ParseException {
		Calendar calendar = Calendar.getInstance();

		_calendarBooking.setStartTime(calendar.getTimeInMillis());

		_calendarBooking.setRecurrence(null);

		CalendarBookingIterator calendarBookingIterator =
			new CalendarBookingIterator(_calendarBooking);

		int count = 0;

		while (calendarBookingIterator.hasNext()) {
			calendarBookingIterator.next();

			count++;
		}

		Assert.assertEquals(1, count);
	}

	@Test
	public void testRecurrenceStartsMondayRepeatsMonday()
		throws ParseException {

		Calendar calendar = Calendar.getInstance();

		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

		_calendarBooking.setStartTime(calendar.getTimeInMillis());

		_calendarBooking.setRecurrence(
			"RRULE:FREQ=WEEKLY;COUNT=2;INTERVAL=1;BYDAY=MO");

		CalendarBookingIterator calendarBookingIterator =
			new CalendarBookingIterator(_calendarBooking);

		int count = 0;

		while (calendarBookingIterator.hasNext()) {
			calendarBookingIterator.next();

			count++;
		}

		Assert.assertEquals(2, count);
	}

	@Test
	public void testRecurrenceStartsMondayRepeatsWednesday()
		throws ParseException {

		Calendar calendar = Calendar.getInstance();

		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

		_calendarBooking.setStartTime(calendar.getTimeInMillis());

		_calendarBooking.setRecurrence(
			"RRULE:FREQ=WEEKLY;COUNT=2;INTERVAL=1;BYDAY=WE");

		CalendarBookingIterator calendarBookingIterator =
			new CalendarBookingIterator(_calendarBooking);

		int count = 0;

		while (calendarBookingIterator.hasNext()) {
			calendarBookingIterator.next();

			count++;
		}

		Assert.assertEquals(2, count);
	}

	@Inject
	private static CalendarBookingLocalService _calendarBookingLocalService;

	private CalendarBooking _calendarBooking;

}
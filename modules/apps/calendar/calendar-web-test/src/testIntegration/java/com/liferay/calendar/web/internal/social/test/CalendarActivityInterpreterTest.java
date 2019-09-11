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

package com.liferay.calendar.web.internal.social.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.calendar.constants.CalendarPortletKeys;
import com.liferay.calendar.model.Calendar;
import com.liferay.calendar.model.CalendarBooking;
import com.liferay.calendar.service.CalendarBookingLocalServiceUtil;
import com.liferay.calendar.social.CalendarActivityKeys;
import com.liferay.calendar.test.util.CalendarBookingTestUtil;
import com.liferay.calendar.test.util.CalendarTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.social.activity.test.util.BaseSocialActivityInterpreterTestCase;
import com.liferay.social.kernel.model.SocialActivityInterpreter;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Jeyvison Nascimento
 */
@RunWith(Arquillian.class)
public class CalendarActivityInterpreterTest
	extends BaseSocialActivityInterpreterTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Override
	protected void addActivities() throws Exception {
		Calendar calendar = CalendarTestUtil.addCalendar(group, serviceContext);

		_calendarBooking = CalendarBookingTestUtil.addRegularCalendarBooking(
			calendar);
	}

	@Override
	protected SocialActivityInterpreter getActivityInterpreter() {
		return getActivityInterpreter(
			CalendarPortletKeys.CALENDAR, CalendarBooking.class.getName());
	}

	@Override
	protected int[] getActivityTypes() {
		return new int[] {
			CalendarActivityKeys.ADD_CALENDAR_BOOKING,
			CalendarActivityKeys.UPDATE_CALENDAR_BOOKING
		};
	}

	@Override
	protected void moveModelsToTrash() throws Exception {
		CalendarBookingLocalServiceUtil.moveCalendarBookingToTrash(
			_calendarBooking.getUserId(), _calendarBooking);
	}

	@Override
	protected void renameModels() throws Exception {
		_calendarBooking = CalendarBookingTestUtil.updateCalendarBooking(
			_calendarBooking, RandomTestUtil.randomLocaleStringMap(),
			serviceContext);
	}

	@Override
	protected void restoreModelsFromTrash() throws Exception {
		CalendarBookingLocalServiceUtil.restoreCalendarBookingFromTrash(
			_calendarBooking.getUserId(),
			_calendarBooking.getCalendarBookingId());
	}

	private CalendarBooking _calendarBooking;

}
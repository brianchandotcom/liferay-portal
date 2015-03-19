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

package com.liferay.calendar.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.calendar.exception.CalendarBookingRecurrenceException;
import com.liferay.calendar.model.Calendar;
import com.liferay.calendar.model.CalendarBooking;
import com.liferay.calendar.model.CalendarBookingConstants;
import com.liferay.calendar.model.CalendarResource;
import com.liferay.calendar.recurrence.Frequency;
import com.liferay.calendar.recurrence.PositionalWeekday;
import com.liferay.calendar.recurrence.Recurrence;
import com.liferay.calendar.recurrence.RecurrenceSerializer;
import com.liferay.calendar.service.CalendarBookingLocalServiceUtil;
import com.liferay.calendar.util.CalendarResourceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Adam Brandizzi
 */
@RunWith(Arquillian.class)
public class CalendarBookingLocalServiceTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_user = UserTestUtil.addUser();
	}

	@After
	public void tearDown() throws Exception {
		UserLocalServiceUtil.deleteUser(_user);
	}

	@Ignore
	@Test
	public void testAddCalendarBookingWorkflowActionPublish()
		throws PortalException {

		ServiceContext serviceContext = createServiceContext();

		CalendarResource calendarResource =
			CalendarResourceUtil.getUserCalendarResource(
				_user.getUserId(), serviceContext);

		Calendar calendar = calendarResource.getDefaultCalendar();

		long startTime = DateUtil.newTime();

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		CalendarBooking calendarBooking =
			CalendarBookingLocalServiceUtil.addCalendarBooking(
				_user.getUserId(), calendar.getCalendarId(), new long[0],
				CalendarBookingConstants.PARENT_CALENDAR_BOOKING_ID_DEFAULT,
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomString(), startTime,
				startTime + (Time.HOUR * 10), false, null, 0, null, 0, null,
				serviceContext);

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, calendarBooking.getStatus());
	}

	@Ignore
	@Test
	public void testAddCalendarBookingWorkflowActionSaveDraft()
		throws PortalException {

		ServiceContext serviceContext = createServiceContext();

		CalendarResource calendarResource =
			CalendarResourceUtil.getUserCalendarResource(
				_user.getUserId(), serviceContext);

		Calendar calendar = calendarResource.getDefaultCalendar();

		long startTime = DateUtil.newTime();

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		CalendarBooking calendarBooking =
			CalendarBookingLocalServiceUtil.addCalendarBooking(
				_user.getUserId(), calendar.getCalendarId(), new long[0],
				CalendarBookingConstants.PARENT_CALENDAR_BOOKING_ID_DEFAULT,
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomString(), startTime,
				startTime + (Time.HOUR * 10), false, null, 0, null, 0, null,
				serviceContext);

		Assert.assertEquals(
			WorkflowConstants.STATUS_DRAFT, calendarBooking.getStatus());
	}

	@Test
	public void testDeleteLastCalendarBookingInstanceDeletesCalendarBooking()
		throws PortalException {

		ServiceContext serviceContext = createServiceContext();

		CalendarResource calendarResource =
			CalendarResourceUtil.getUserCalendarResource(
				_user.getUserId(), serviceContext);

		Calendar calendar = calendarResource.getDefaultCalendar();

		long startTime = DateUtil.newTime();

		Recurrence recurrence = new Recurrence();

		recurrence.setCount(2);
		recurrence.setFrequency(Frequency.DAILY);
		recurrence.setPositionalWeekdays(new ArrayList<PositionalWeekday>());

		CalendarBooking calendarBooking =
			CalendarBookingLocalServiceUtil.addCalendarBooking(
				_user.getUserId(), calendar.getCalendarId(), new long[0],
				CalendarBookingConstants.PARENT_CALENDAR_BOOKING_ID_DEFAULT,
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomString(), startTime,
				startTime + (Time.HOUR * 10), false,
				RecurrenceSerializer.serialize(recurrence), 0, null, 0, null,
				serviceContext);

		long calendarBookingId = calendarBooking.getCalendarBookingId();

		CalendarBookingLocalServiceUtil.deleteCalendarBookingInstance(
			calendarBooking, 0, false);

		calendarBooking = CalendarBookingLocalServiceUtil.fetchCalendarBooking(
			calendarBookingId);

		Assert.assertNotNull(calendarBooking);

		CalendarBookingLocalServiceUtil.deleteCalendarBookingInstance(
			calendarBooking, 0, false);

		calendarBooking = CalendarBookingLocalServiceUtil.fetchCalendarBooking(
			calendarBookingId);

		Assert.assertNull(calendarBooking);
	}

	@Test(expected = CalendarBookingRecurrenceException.class)
	public void testStartDateBeforeUntilDateThrowsRecurrenceException()
		throws PortalException {

		ServiceContext serviceContext = createServiceContext();

		CalendarResource calendarResource =
			CalendarResourceUtil.getUserCalendarResource(
				_user.getUserId(), serviceContext);

		Calendar calendar = calendarResource.getDefaultCalendar();

		long startTime = DateUtil.newTime();

		java.util.Calendar untilJCalendar = CalendarFactoryUtil.getCalendar(
			startTime);

		untilJCalendar.add(java.util.Calendar.DAY_OF_MONTH, -2);

		Recurrence recurrence = new Recurrence();

		recurrence.setFrequency(Frequency.DAILY);
		recurrence.setUntilJCalendar(untilJCalendar);
		recurrence.setPositionalWeekdays(
			Collections.<PositionalWeekday>emptyList());

		CalendarBookingLocalServiceUtil.addCalendarBooking(
			_user.getUserId(), calendar.getCalendarId(), new long[0],
			CalendarBookingConstants.PARENT_CALENDAR_BOOKING_ID_DEFAULT,
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomString(), startTime,
			startTime + (Time.HOUR * 10), false,
			RecurrenceSerializer.serialize(recurrence), 0, null, 0, null,
			serviceContext);
	}

	@Ignore
	@Test
	public void testUpdateCalendarBookingWorkflowActionPublish()
		throws PortalException {

		ServiceContext serviceContext = createServiceContext();

		CalendarResource calendarResource =
			CalendarResourceUtil.getUserCalendarResource(
				_user.getUserId(), serviceContext);

		Calendar calendar = calendarResource.getDefaultCalendar();

		long startTime = DateUtil.newTime();

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		CalendarBooking calendarBooking =
			CalendarBookingLocalServiceUtil.addCalendarBooking(
				_user.getUserId(), calendar.getCalendarId(), new long[0],
				CalendarBookingConstants.PARENT_CALENDAR_BOOKING_ID_DEFAULT,
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomString(), startTime,
				startTime + (Time.HOUR * 10), false, null, 0, null, 0, null,
				serviceContext);

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		calendarBooking = CalendarBookingLocalServiceUtil.updateCalendarBooking(
			_user.getUserId(), calendarBooking.getCalendarBookingId(),
			calendar.getCalendarId(), new long[0],
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomString(), startTime,
			startTime + (Time.HOUR * 10), false, null, 0, null, 0, null,
			RandomTestUtil.randomInt(), serviceContext);

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, calendarBooking.getStatus());
	}

	@Ignore
	@Test
	public void testUpdateCalendarBookingWorkflowActionSaveDraft()
		throws PortalException {

		ServiceContext serviceContext = createServiceContext();

		CalendarResource calendarResource =
			CalendarResourceUtil.getUserCalendarResource(
				_user.getUserId(), serviceContext);

		Calendar calendar = calendarResource.getDefaultCalendar();

		long startTime = DateUtil.newTime();

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		CalendarBooking calendarBooking =
			CalendarBookingLocalServiceUtil.addCalendarBooking(
				_user.getUserId(), calendar.getCalendarId(), new long[0],
				CalendarBookingConstants.PARENT_CALENDAR_BOOKING_ID_DEFAULT,
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomString(), startTime,
				startTime + (Time.HOUR * 10), false, null, 0, null, 0, null,
				serviceContext);

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		calendarBooking = CalendarBookingLocalServiceUtil.updateCalendarBooking(
			_user.getUserId(), calendarBooking.getCalendarBookingId(),
			calendar.getCalendarId(), new long[0],
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomString(), startTime,
			startTime + (Time.HOUR * 10), false, null, 0, null, 0, null,
			RandomTestUtil.randomInt(), serviceContext);

		Assert.assertEquals(
			WorkflowConstants.STATUS_DRAFT, calendarBooking.getStatus());
	}

	@Test
	public void testUpdateCalendarBookingWorkflowActionSaveDraftAfterPublish()
		throws PortalException {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(_user.getCompanyId());

		CalendarResource calendarResource =
			CalendarResourceUtil.getUserCalendarResource(
				_user.getUserId(), serviceContext);
		Calendar calendar = calendarResource.getDefaultCalendar();

		long startDate = DateUtil.newTime();

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		CalendarBooking calendarBooking =
			CalendarBookingLocalServiceUtil.addCalendarBooking(
				_user.getUserId(), calendar.getCalendarId(), new long[0],
				CalendarBookingConstants.PARENT_CALENDAR_BOOKING_ID_DEFAULT,
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomString(), startDate, startDate + 36000000,
				false, null, 0, null, 0, null, serviceContext);

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		calendarBooking = CalendarBookingLocalServiceUtil.updateCalendarBooking(
			_user.getUserId(), calendarBooking.getCalendarBookingId(),
			calendar.getCalendarId(), new long[0],
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomString(), startDate, startDate + 36000000,
			false, null, 0, null, 0, null, RandomTestUtil.randomInt(),
			serviceContext);

		calendarBooking = CalendarBookingLocalServiceUtil.fetchCalendarBooking(
			calendarBooking.getCalendarBookingId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_DRAFT, calendarBooking.getStatus());
	}

	protected ServiceContext createServiceContext() {
		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(_user.getCompanyId());

		return serviceContext;
	}

	private User _user;

}
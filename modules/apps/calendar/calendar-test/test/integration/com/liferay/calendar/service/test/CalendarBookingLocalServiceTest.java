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
import com.liferay.calendar.service.CalendarLocalServiceUtil;
import com.liferay.calendar.util.CalendarResourceUtil;
import com.liferay.calendar.workflow.CalendarBookingWorkflowConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
public class CalendarBookingLocalServiceTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_user = UserTestUtil.addUser();
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

	@Test
	public void testInviteToDraftCalendarBookingResultsInMasterPendingChild()
		throws PortalException {

		ServiceContext serviceContext = createServiceContext();

		CalendarResource calendarResource =
			CalendarResourceUtil.getUserCalendarResource(
				_user.getUserId(), serviceContext);

		Calendar calendar = calendarResource.getDefaultCalendar();

		Calendar invitedCalendar = CalendarLocalServiceUtil.addCalendar(
			_user.getUserId(), _user.getGroupId(),
			calendarResource.getCalendarResourceId(),
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(),
			calendarResource.getTimeZoneId(), RandomTestUtil.randomInt(), false,
			false, false, serviceContext);

		long startDate = DateUtil.newTime();

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		CalendarBooking calendarBooking =
			CalendarBookingLocalServiceUtil.addCalendarBooking(
				_user.getUserId(), calendar.getCalendarId(),
				new long[] { invitedCalendar.getCalendarId() },
				CalendarBookingConstants.PARENT_CALENDAR_BOOKING_ID_DEFAULT,
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomString(), startDate, startDate + 36000000,
				false, null, 0, null, 0, null, serviceContext);

		CalendarBooking childCalendarBooking = getChildCalendarBooking(
			calendarBooking);

		Assert.assertEquals(
			CalendarBookingWorkflowConstants.STATUS_MASTER_PENDING,
			childCalendarBooking.getStatus());
	}

	@Test
	public void testInviteToPublishedCalendarBookingResultsInPendingChild()
		throws PortalException {

		ServiceContext serviceContext = createServiceContext();

		CalendarResource calendarResource =
			CalendarResourceUtil.getUserCalendarResource(
				_user.getUserId(), serviceContext);

		Calendar calendar = calendarResource.getDefaultCalendar();

		Calendar invitedCalendar = CalendarLocalServiceUtil.addCalendar(
			_user.getUserId(), _user.getGroupId(),
			calendarResource.getCalendarResourceId(),
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(),
			calendarResource.getTimeZoneId(), RandomTestUtil.randomInt(), false,
			false, false, serviceContext);

		long startDate = DateUtil.newTime();

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		CalendarBooking calendarBooking =
			CalendarBookingLocalServiceUtil.addCalendarBooking(
				_user.getUserId(), calendar.getCalendarId(),
				new long[] { invitedCalendar.getCalendarId() },
				CalendarBookingConstants.PARENT_CALENDAR_BOOKING_ID_DEFAULT,
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomString(), startDate, startDate + 36000000,
				false, null, 0, null, 0, null, serviceContext);

		CalendarBooking childCalendarBooking = getChildCalendarBooking(
			calendarBooking);

		Assert.assertEquals(
			WorkflowConstants.STATUS_PENDING, childCalendarBooking.getStatus());
	}

	@Test
	public void testMoveToTrashCalendarBookingShouldMoveItsChildrenToTrash()
		throws PortalException {

		ServiceContext serviceContext = createServiceContext();

		CalendarResource calendarResource =
			CalendarResourceUtil.getUserCalendarResource(
				_user.getUserId(), serviceContext);

		Calendar calendar = calendarResource.getDefaultCalendar();

		Calendar invitedCalendar = CalendarLocalServiceUtil.addCalendar(
			_user.getUserId(), _user.getGroupId(),
			calendarResource.getCalendarResourceId(),
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(),
			calendarResource.getTimeZoneId(), RandomTestUtil.randomInt(), false,
			false, false, serviceContext);

		long startDate = DateUtil.newTime();

		CalendarBooking calendarBooking =
			CalendarBookingLocalServiceUtil.addCalendarBooking(
				_user.getUserId(), calendar.getCalendarId(),
				new long[] { invitedCalendar.getCalendarId() },
				CalendarBookingConstants.PARENT_CALENDAR_BOOKING_ID_DEFAULT,
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomString(), startDate, startDate + 36000000,
				false, null, 0, null, 0, null, serviceContext);

		CalendarBooking childCalendarBooking = getChildCalendarBooking(
			calendarBooking);

		Assert.assertEquals(
			WorkflowConstants.STATUS_PENDING, childCalendarBooking.getStatus());

		CalendarBookingLocalServiceUtil.moveCalendarBookingToTrash(
			_user.getUserId(), calendarBooking);

		childCalendarBooking = getChildCalendarBooking(calendarBooking);

		Assert.assertEquals(
			WorkflowConstants.STATUS_IN_TRASH,
			childCalendarBooking.getStatus());
	}

	@Test
	public void testPublishCalendarBooking() throws PortalException {
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

		calendarBooking = CalendarBookingLocalServiceUtil.fetchCalendarBooking(
			calendarBooking.getCalendarBookingId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, calendarBooking.getStatus());
	}

	@Test
	public void testPublishDraftCalendarBooking() throws PortalException {
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
			serviceContext);

		calendarBooking = CalendarBookingLocalServiceUtil.fetchCalendarBooking(
			calendarBooking.getCalendarBookingId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, calendarBooking.getStatus());
	}

	@Test
	public void testPublishDraftCalendarBookingShouldResultInPendingChild()
		throws PortalException {

		ServiceContext serviceContext = createServiceContext();

		CalendarResource calendarResource =
			CalendarResourceUtil.getUserCalendarResource(
				_user.getUserId(), serviceContext);

		Calendar calendar = calendarResource.getDefaultCalendar();

		Calendar invitedCalendar = CalendarLocalServiceUtil.addCalendar(
			_user.getUserId(), _user.getGroupId(),
			calendarResource.getCalendarResourceId(),
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(),
			calendarResource.getTimeZoneId(), RandomTestUtil.randomInt(), false,
			false, false, serviceContext);

		long startDate = DateUtil.newTime();

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		CalendarBooking calendarBooking =
			CalendarBookingLocalServiceUtil.addCalendarBooking(
				_user.getUserId(), calendar.getCalendarId(),
				new long[] { invitedCalendar.getCalendarId() },
				CalendarBookingConstants.PARENT_CALENDAR_BOOKING_ID_DEFAULT,
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomString(), startDate, startDate + 36000000,
				false, null, 0, null, 0, null, serviceContext);

		CalendarBooking childCalendarBooking = getChildCalendarBooking(
			calendarBooking);

		Assert.assertEquals(
			CalendarBookingWorkflowConstants.STATUS_MASTER_PENDING,
			childCalendarBooking.getStatus());

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		calendarBooking = CalendarBookingLocalServiceUtil.updateCalendarBooking(
			_user.getUserId(), calendarBooking.getCalendarBookingId(),
			calendar.getCalendarId(),
			new long[] { invitedCalendar.getCalendarId() },
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomString(), startDate, startDate + 36000000,
			false, null, 0, null, 0, null, serviceContext);

		childCalendarBooking = getChildCalendarBooking(calendarBooking);

		Assert.assertEquals(
			CalendarBookingWorkflowConstants.STATUS_PENDING,
			childCalendarBooking.getStatus());
	}

	@Test
	public void testRestoredFromTrashEventResultsInRestoredFromTrashChildren()
		throws PortalException {

		ServiceContext serviceContext = createServiceContext();

		CalendarResource calendarResource =
			CalendarResourceUtil.getUserCalendarResource(
				_user.getUserId(), serviceContext);

		Calendar calendar = calendarResource.getDefaultCalendar();

		Calendar invitedCalendar = CalendarLocalServiceUtil.addCalendar(
			_user.getUserId(), _user.getGroupId(),
			calendarResource.getCalendarResourceId(),
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(),
			calendarResource.getTimeZoneId(), RandomTestUtil.randomInt(), false,
			false, false, serviceContext);

		long startDate = DateUtil.newTime();

		CalendarBooking calendarBooking =
			CalendarBookingLocalServiceUtil.addCalendarBooking(
				_user.getUserId(), calendar.getCalendarId(),
				new long[] { invitedCalendar.getCalendarId() },
				CalendarBookingConstants.PARENT_CALENDAR_BOOKING_ID_DEFAULT,
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomString(), startDate, startDate + 36000000,
				false, null, 0, null, 0, null, serviceContext);

		CalendarBooking childCalendarBooking = getChildCalendarBooking(
			calendarBooking);

		Assert.assertEquals(
			WorkflowConstants.STATUS_PENDING, childCalendarBooking.getStatus());

		CalendarBookingLocalServiceUtil.moveCalendarBookingToTrash(
			_user.getUserId(), calendarBooking);

		childCalendarBooking = getChildCalendarBooking(calendarBooking);

		Assert.assertEquals(
			WorkflowConstants.STATUS_IN_TRASH,
			childCalendarBooking.getStatus());

		CalendarBookingLocalServiceUtil.restoreCalendarBookingFromTrash(
			_user.getUserId(), calendarBooking.getCalendarBookingId());

		childCalendarBooking = getChildCalendarBooking(calendarBooking);

		Assert.assertEquals(
			WorkflowConstants.STATUS_PENDING, childCalendarBooking.getStatus());
	}

	@Test
	public void testSaveAsDraftCalendarBooking() throws PortalException {
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

		calendarBooking = CalendarBookingLocalServiceUtil.fetchCalendarBooking(
			calendarBooking.getCalendarBookingId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_DRAFT, calendarBooking.getStatus());
	}

	@Test
	public void testSaveAsDraftDraftCalendarBooking() throws PortalException {
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
			serviceContext);

		calendarBooking = CalendarBookingLocalServiceUtil.fetchCalendarBooking(
			calendarBooking.getCalendarBookingId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_DRAFT, calendarBooking.getStatus());
	}

	@Test
	public void testSaveAsDraftPublishedCalendarBooking()
		throws PortalException {

		ServiceContext serviceContext = createServiceContext();

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
			false, null, 0, null, 0, null, serviceContext);

		calendarBooking = CalendarBookingLocalServiceUtil.fetchCalendarBooking(
			calendarBooking.getCalendarBookingId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_DRAFT, calendarBooking.getStatus());
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

	protected ServiceContext createServiceContext() {
		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(_user.getCompanyId());

		return serviceContext;
	}

	protected CalendarBooking getChildCalendarBooking(
		CalendarBooking calendarBooking) {

		List<CalendarBooking> childCalendarBookings =
			calendarBooking.getChildCalendarBookings();

		CalendarBooking childCalendarBooking = childCalendarBookings.get(0);

		if (childCalendarBooking.isMasterBooking()) {
			childCalendarBooking = childCalendarBookings.get(1);
		}

		return childCalendarBooking;
	}

	@DeleteAfterTestRun
	private User _user;

}
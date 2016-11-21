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
import com.liferay.calendar.notification.NotificationType;
import com.liferay.calendar.recurrence.Frequency;
import com.liferay.calendar.recurrence.PositionalWeekday;
import com.liferay.calendar.recurrence.Recurrence;
import com.liferay.calendar.recurrence.RecurrenceSerializer;
import com.liferay.calendar.service.CalendarBookingLocalService;
import com.liferay.calendar.service.CalendarBookingLocalServiceUtil;
import com.liferay.calendar.service.CalendarLocalServiceUtil;
import com.liferay.calendar.util.CalendarResourceUtil;
import com.liferay.calendar.workflow.CalendarBookingWorkflowConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.SynchronousMailTestRule;
import com.liferay.portal.util.test.MailServiceTestUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/**
 * @author Adam Brandizzi
 */
@RunWith(Arquillian.class)
@Sync
public class CalendarBookingLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), SynchronousMailTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_user = UserTestUtil.addUser();

		setUpCheckBookingMessageListener();
	}

	@After
	public void tearDown() {
		tearDownCheckBookingMessageListener();
	}

	@Test
	public void testAddCalendarBooking() throws Exception {
		ServiceContext serviceContext = createServiceContext();

		Calendar calendar = addCalendar(_user, serviceContext);

		long startTime = System.currentTimeMillis();

		serviceContext.setLanguageId("fr_FR");

		CalendarBooking calendarBooking = addRegularCalendarBooking(
			_user, calendar, startTime, startTime + (Time.HOUR * 10),
			serviceContext);

		Assert.assertEquals(
			"fr_FR",
			LocalizationUtil.getDefaultLanguageId(calendarBooking.getTitle()));
	}

	@Test
	public void testAddCalendarBookingDoesNotNotifyCreatorTwice()
		throws Exception {

		ServiceContext serviceContext = createServiceContext();

		_invitingUser = UserTestUtil.addUser();

		Calendar calendar = addCalendar(_invitingUser, serviceContext);

		Calendar invitedCalendar = addCalendar(_user, serviceContext);

		long startTime = System.currentTimeMillis() + Time.MINUTE;

		long endTime = startTime + Time.HOUR;

		long firstReminder = Time.MINUTE;

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		CalendarBooking calendarBooking = addCalendarBooking(
			_invitingUser, calendar,
			new long[] {invitedCalendar.getCalendarId()},
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(), startTime, endTime, null,
			(int)firstReminder, NotificationType.EMAIL, 0,
			NotificationType.EMAIL, serviceContext);

		CalendarBooking childCalendarBooking = getChildCalendarBooking(
			calendarBooking);

		CalendarBookingLocalServiceUtil.updateStatus(
			_user.getUserId(), childCalendarBooking,
			CalendarBookingWorkflowConstants.STATUS_APPROVED, serviceContext);

		CalendarBookingLocalServiceUtil.checkCalendarBookings();

		String mailMessageSubject =
			"Calendar: Event Reminder for " + StringPool.QUOTE +
				calendarBooking.getTitle(LocaleUtil.getDefault()) +
					StringPool.QUOTE;

		List<com.dumbster.smtp.MailMessage> mailMessages =
			MailServiceTestUtil.getMailMessages("Subject", mailMessageSubject);

		Assert.assertEquals(2, mailMessages.size());
	}

	@Test
	public void testDeleteCalendarBooking() throws Exception {
		ServiceContext serviceContext = createServiceContext();

		Calendar calendar = addCalendar(_user, serviceContext);

		long startTime = System.currentTimeMillis();

		Recurrence recurrence = new Recurrence();

		recurrence.setFrequency(Frequency.DAILY);
		recurrence.setPositionalWeekdays(new ArrayList<PositionalWeekday>());

		CalendarBooking calendarBooking = addRecurringCalendarBooking(
			_user, calendar, startTime, startTime + (Time.HOUR * 10),
			recurrence, serviceContext);

		long instanceStartTime = startTime + Time.DAY * 2;

		Map<Locale, String> titleMap = RandomTestUtil.randomLocaleStringMap();

		CalendarBooking calendarBookingInstance =
			CalendarBookingLocalServiceUtil.updateCalendarBookingInstance(
				_user.getUserId(), calendarBooking.getCalendarBookingId(), 2,
				calendar.getCalendarId(), titleMap,
				calendarBooking.getDescriptionMap(),
				calendarBooking.getLocation(), instanceStartTime,
				instanceStartTime + (Time.HOUR * 10), false, null, false, 0,
				null, 0, null, serviceContext);

		CalendarBookingLocalServiceUtil.deleteCalendarBooking(calendarBooking);

		calendarBookingInstance =
			CalendarBookingLocalServiceUtil.fetchCalendarBooking(
				calendarBookingInstance.getCalendarBookingId());

		Assert.assertEquals(titleMap, calendarBookingInstance.getTitleMap());
	}

	@Test
	public void testDeleteCalendarBookingWithAllFollowingInstances()
		throws Exception {

		ServiceContext serviceContext = createServiceContext();

		Calendar calendar = addCalendar(_user, serviceContext);

		long startTime = System.currentTimeMillis();

		Recurrence recurrence = new Recurrence();

		recurrence.setFrequency(Frequency.DAILY);
		recurrence.setPositionalWeekdays(new ArrayList<PositionalWeekday>());

		CalendarBooking calendarBooking = addRecurringCalendarBooking(
			_user, calendar, startTime, startTime + (Time.HOUR * 10),
			recurrence, serviceContext);

		long instanceStartTime = startTime + Time.DAY * 2;

		Map<Locale, String> titleMap = RandomTestUtil.randomLocaleStringMap();

		CalendarBooking calendarBookingInstance =
			CalendarBookingLocalServiceUtil.updateCalendarBookingInstance(
				_user.getUserId(), calendarBooking.getCalendarBookingId(), 2,
				calendar.getCalendarId(), titleMap,
				calendarBooking.getDescriptionMap(),
				calendarBooking.getLocation(), instanceStartTime,
				instanceStartTime + (Time.HOUR * 10), false, null, false, 0,
				null, 0, null, serviceContext);

		CalendarBookingLocalServiceUtil.deleteCalendarBookingInstance(
			calendarBooking, 1, true, true);

		calendarBookingInstance =
			CalendarBookingLocalServiceUtil.fetchCalendarBooking(
				calendarBookingInstance.getCalendarBookingId());

		Assert.assertNull(calendarBookingInstance);

		calendarBookingInstance =
			CalendarBookingLocalServiceUtil.getCalendarBookingInstance(
				calendarBooking.getCalendarBookingId(), 0);

		Assert.assertNotNull(calendarBookingInstance);

		calendarBookingInstance =
			CalendarBookingLocalServiceUtil.getCalendarBookingInstance(
				calendarBooking.getCalendarBookingId(), 1);

		Assert.assertNull(calendarBookingInstance);
	}

	@Test
	public void testDeleteCalendarBookingWithAllRecurringInstances()
		throws Exception {

		ServiceContext serviceContext = createServiceContext();

		Calendar calendar = addCalendar(_user, serviceContext);

		long startTime = System.currentTimeMillis();

		Recurrence recurrence = new Recurrence();

		recurrence.setFrequency(Frequency.DAILY);
		recurrence.setPositionalWeekdays(new ArrayList<PositionalWeekday>());

		CalendarBooking calendarBooking = addRecurringCalendarBooking(
			_user, calendar, startTime, startTime + (Time.HOUR * 10),
			recurrence, serviceContext);

		long instanceStartTime = startTime + Time.DAY * 2;

		CalendarBooking calendarBookingInstance =
			CalendarBookingLocalServiceUtil.updateCalendarBookingInstance(
				_user.getUserId(), calendarBooking.getCalendarBookingId(), 2,
				calendar.getCalendarId(),
				RandomTestUtil.randomLocaleStringMap(),
				calendarBooking.getDescriptionMap(),
				calendarBooking.getLocation(), instanceStartTime,
				instanceStartTime + (Time.HOUR * 10), false, null, false, 0,
				null, 0, null, serviceContext);

		CalendarBookingLocalServiceUtil.deleteCalendarBooking(
			calendarBooking, true);

		calendarBookingInstance =
			CalendarBookingLocalServiceUtil.fetchCalendarBooking(
				calendarBookingInstance.getCalendarBookingId());

		Assert.assertNull(calendarBookingInstance);
	}

	@Test
	public void testDeleteCalendarBookingWithoutAllRecurringInstances()
		throws Exception {

		ServiceContext serviceContext = createServiceContext();

		Calendar calendar = addCalendar(_user, serviceContext);

		long startTime = System.currentTimeMillis();

		Recurrence recurrence = new Recurrence();

		recurrence.setFrequency(Frequency.DAILY);
		recurrence.setPositionalWeekdays(new ArrayList<PositionalWeekday>());

		CalendarBooking calendarBooking = addRecurringCalendarBooking(
			_user, calendar, startTime, startTime + (Time.HOUR * 10),
			recurrence, serviceContext);

		long instanceStartTime = startTime + Time.DAY * 2;

		Map<Locale, String> titleMap = RandomTestUtil.randomLocaleStringMap();

		CalendarBooking calendarBookingInstance =
			CalendarBookingLocalServiceUtil.updateCalendarBookingInstance(
				_user.getUserId(), calendarBooking.getCalendarBookingId(), 2,
				calendar.getCalendarId(), titleMap,
				calendarBooking.getDescriptionMap(),
				calendarBooking.getLocation(), instanceStartTime,
				instanceStartTime + (Time.HOUR * 10), false, null, false, 0,
				null, 0, null, serviceContext);

		CalendarBookingLocalServiceUtil.deleteCalendarBooking(
			calendarBooking, false);

		calendarBookingInstance =
			CalendarBookingLocalServiceUtil.fetchCalendarBooking(
				calendarBookingInstance.getCalendarBookingId());

		Assert.assertEquals(titleMap, calendarBookingInstance.getTitleMap());
	}

	@Test
	public void testDeleteLastCalendarBookingInstanceDeletesCalendarBooking()
		throws Exception {

		ServiceContext serviceContext = createServiceContext();

		Calendar calendar = addCalendar(_user, serviceContext);

		long startTime = System.currentTimeMillis();

		Recurrence recurrence = new Recurrence();

		recurrence.setCount(2);
		recurrence.setFrequency(Frequency.DAILY);
		recurrence.setPositionalWeekdays(new ArrayList<PositionalWeekday>());

		CalendarBooking calendarBooking = addRecurringCalendarBooking(
			_user, calendar, startTime, startTime + (Time.HOUR * 10),
			recurrence, serviceContext);

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
	public void testGetRecurringCalendarBookings() throws Exception {
		ServiceContext serviceContext = createServiceContext();

		Calendar calendar = addCalendar(_user, serviceContext);

		long startTime = System.currentTimeMillis();

		Recurrence recurrence = new Recurrence();

		recurrence.setFrequency(Frequency.DAILY);
		recurrence.setPositionalWeekdays(new ArrayList<PositionalWeekday>());

		CalendarBooking calendarBooking = addRecurringCalendarBooking(
			_user, calendar, startTime, startTime + (Time.HOUR * 10),
			recurrence, serviceContext);

		Map<Locale, String> instanceTitleMap =
			RandomTestUtil.randomLocaleStringMap();

		int instanceIndex = 2;

		long instanceStartTime = startTime + Time.DAY * instanceIndex;

		CalendarBookingLocalServiceUtil.updateCalendarBookingInstance(
			_user.getUserId(), calendarBooking.getCalendarBookingId(),
			instanceIndex, calendar.getCalendarId(), instanceTitleMap,
			calendarBooking.getDescriptionMap(), calendarBooking.getLocation(),
			instanceStartTime, instanceStartTime + (Time.HOUR * 10), false,
			null, false, 0, null, 0, null, serviceContext);

		instanceIndex = 4;

		instanceStartTime = instanceStartTime + Time.DAY * instanceStartTime;

		CalendarBookingLocalServiceUtil.updateCalendarBookingInstance(
			_user.getUserId(), calendarBooking.getCalendarBookingId(),
			instanceIndex, calendar.getCalendarId(), instanceTitleMap,
			calendarBooking.getDescriptionMap(), calendarBooking.getLocation(),
			instanceStartTime, instanceStartTime + (Time.HOUR * 10), false,
			null, false, 0, null, 0, null, serviceContext);

		List<CalendarBooking> instances =
			CalendarBookingLocalServiceUtil.getRecurringCalendarBookings(
				calendarBooking);

		Assert.assertEquals(3, instances.size());

		for (CalendarBooking instance : instances) {
			if (instance.getCalendarBookingId() ==
					calendarBooking.getCalendarBookingId()) {

				continue;
			}

			Assert.assertEquals(instanceTitleMap, instance.getTitleMap());
		}
	}

	@Test
	public void testGetRecurringCalendarBookingsSkipPastEvents()
		throws Exception {

		ServiceContext serviceContext = createServiceContext();

		Calendar calendar = addCalendar(_user, serviceContext);

		long startTime = System.currentTimeMillis();

		Recurrence recurrence = new Recurrence();

		recurrence.setFrequency(Frequency.DAILY);
		recurrence.setPositionalWeekdays(new ArrayList<PositionalWeekday>());

		CalendarBooking calendarBooking = addRecurringCalendarBooking(
			_user, calendar, startTime, startTime + (Time.HOUR * 10),
			recurrence, serviceContext);

		Map<Locale, String> instanceTitleMap =
			RandomTestUtil.randomLocaleStringMap();

		int instanceIndex = 2;

		long firstInstancStartTime = startTime + Time.DAY * instanceIndex;

		CalendarBookingLocalServiceUtil.updateCalendarBookingInstance(
			_user.getUserId(), calendarBooking.getCalendarBookingId(),
			instanceIndex, calendar.getCalendarId(), instanceTitleMap,
			calendarBooking.getDescriptionMap(), calendarBooking.getLocation(),
			firstInstancStartTime, firstInstancStartTime + (Time.HOUR * 10),
			false, null, false, 0, null, 0, null, serviceContext);

		instanceIndex = 4;

		long secondInstancStartTime =
			firstInstancStartTime + Time.DAY * instanceIndex;

		CalendarBookingLocalServiceUtil.updateCalendarBookingInstance(
			_user.getUserId(), calendarBooking.getCalendarBookingId(),
			instanceIndex, calendar.getCalendarId(), instanceTitleMap,
			calendarBooking.getDescriptionMap(), calendarBooking.getLocation(),
			secondInstancStartTime, secondInstancStartTime + (Time.HOUR * 10),
			false, null, false, 0, null, 0, null, serviceContext);

		List<CalendarBooking> instances =
			CalendarBookingLocalServiceUtil.getRecurringCalendarBookings(
				calendarBooking, firstInstancStartTime + 1);

		Assert.assertEquals(1, instances.size());

		CalendarBooking instance = instances.get(0);

		Assert.assertEquals(secondInstancStartTime, instance.getStartTime());
	}

	@Test
	public void testInviteToDraftCalendarBookingResultsInMasterPendingChild()
		throws Exception {

		ServiceContext serviceContext = createServiceContext();

		Calendar calendar = addCalendar(_user, serviceContext);

		Calendar invitedCalendar = addCalendar(
			calendar.getCalendarResource(), serviceContext);

		long startTime = System.currentTimeMillis();

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		CalendarBooking calendarBooking = addMasterCalendarBooking(
			_user, calendar, new long[] {invitedCalendar.getCalendarId()},
			startTime, startTime + (Time.HOUR * 10), serviceContext);

		CalendarBooking childCalendarBooking = getChildCalendarBooking(
			calendarBooking);

		Assert.assertEquals(
			CalendarBookingWorkflowConstants.STATUS_MASTER_PENDING,
			childCalendarBooking.getStatus());
	}

	@Test
	public void testInviteToPublishedCalendarBookingResultsInPendingChild()
		throws Exception {

		ServiceContext serviceContext = createServiceContext();

		Calendar calendar = addCalendar(_user, serviceContext);

		Calendar invitedCalendar = addCalendar(
			calendar.getCalendarResource(), serviceContext);

		long startTime = System.currentTimeMillis();

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		CalendarBooking calendarBooking = addMasterCalendarBooking(
			_user, calendar, new long[] {invitedCalendar.getCalendarId()},
			startTime, startTime + (Time.HOUR * 10), serviceContext);

		CalendarBooking childCalendarBooking = getChildCalendarBooking(
			calendarBooking);

		Assert.assertEquals(
			WorkflowConstants.STATUS_PENDING, childCalendarBooking.getStatus());
	}

	@Test
	public void testMoveToTrashCalendarBookingShouldMoveItsChildrenToTrash()
		throws Exception {

		ServiceContext serviceContext = createServiceContext();

		Calendar calendar = addCalendar(_user, serviceContext);

		Calendar invitedCalendar = addCalendar(
			calendar.getCalendarResource(), serviceContext);

		long startTime = System.currentTimeMillis();

		CalendarBooking calendarBooking = addMasterCalendarBooking(
			_user, calendar, new long[] {invitedCalendar.getCalendarId()},
			startTime, startTime + (Time.HOUR * 10), serviceContext);

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
	public void testPublishCalendarBooking() throws Exception {
		ServiceContext serviceContext = createServiceContext();

		Calendar calendar = addCalendar(_user, serviceContext);

		long startTime = System.currentTimeMillis();

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		CalendarBooking calendarBooking = addRegularCalendarBooking(
			_user, calendar, startTime, startTime + (Time.HOUR * 10),
			serviceContext);

		calendarBooking = CalendarBookingLocalServiceUtil.fetchCalendarBooking(
			calendarBooking.getCalendarBookingId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, calendarBooking.getStatus());
	}

	@Test
	public void testPublishDraftCalendarBooking() throws Exception {
		ServiceContext serviceContext = createServiceContext();

		Calendar calendar = addCalendar(_user, serviceContext);

		long startTime = System.currentTimeMillis();

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		CalendarBooking calendarBooking = addRegularCalendarBooking(
			_user, calendar, startTime, startTime + (Time.HOUR * 10),
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
	public void testPublishDraftCalendarBookingResultsInPendingChild()
		throws Exception {

		ServiceContext serviceContext = createServiceContext();

		Calendar calendar = addCalendar(_user, serviceContext);

		Calendar invitedCalendar = addCalendar(
			calendar.getCalendarResource(), serviceContext);

		long startTime = System.currentTimeMillis();

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		CalendarBooking calendarBooking = addMasterCalendarBooking(
			_user, calendar, new long[] {invitedCalendar.getCalendarId()},
			startTime, startTime + (Time.HOUR * 10), serviceContext);

		CalendarBooking childCalendarBooking = getChildCalendarBooking(
			calendarBooking);

		Assert.assertEquals(
			CalendarBookingWorkflowConstants.STATUS_MASTER_PENDING,
			childCalendarBooking.getStatus());

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		calendarBooking = CalendarBookingLocalServiceUtil.updateCalendarBooking(
			_user.getUserId(), calendarBooking.getCalendarBookingId(),
			calendar.getCalendarId(),
			new long[] {invitedCalendar.getCalendarId()},
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomString(), startTime,
			startTime + (Time.HOUR * 10), false, null, 0, null, 0, null,
			serviceContext);

		childCalendarBooking = getChildCalendarBooking(calendarBooking);

		Assert.assertEquals(
			CalendarBookingWorkflowConstants.STATUS_PENDING,
			childCalendarBooking.getStatus());
	}

	@Test
	public void testRestoredFromTrashEventResultsInRestoredFromTrashChildren()
		throws Exception {

		ServiceContext serviceContext = createServiceContext();

		Calendar calendar = addCalendar(_user, serviceContext);

		Calendar invitedCalendar = addCalendar(
			calendar.getCalendarResource(), serviceContext);

		long startTime = System.currentTimeMillis();

		CalendarBooking calendarBooking = addMasterCalendarBooking(
			_user, calendar, new long[] {invitedCalendar.getCalendarId()},
			startTime, startTime + (Time.HOUR * 10), serviceContext);

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
	public void testSaveAsDraftCalendarBooking() throws Exception {
		ServiceContext serviceContext = createServiceContext();

		Calendar calendar = addCalendar(_user, serviceContext);

		long startTime = System.currentTimeMillis();

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		CalendarBooking calendarBooking = addRegularCalendarBooking(
			_user, calendar, startTime, startTime + (Time.HOUR * 10),
			serviceContext);

		calendarBooking = CalendarBookingLocalServiceUtil.fetchCalendarBooking(
			calendarBooking.getCalendarBookingId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_DRAFT, calendarBooking.getStatus());
	}

	@Test
	public void testSaveAsDraftDraftCalendarBooking() throws Exception {
		ServiceContext serviceContext = createServiceContext();

		Calendar calendar = addCalendar(_user, serviceContext);

		long startTime = System.currentTimeMillis();

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		CalendarBooking calendarBooking = addRegularCalendarBooking(
			_user, calendar, startTime, startTime + (Time.HOUR * 10),
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
	public void testSaveAsDraftPublishedCalendarBooking() throws Exception {
		ServiceContext serviceContext = createServiceContext();

		Calendar calendar = addCalendar(_user, serviceContext);

		long startTime = System.currentTimeMillis();

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		CalendarBooking calendarBooking = addRegularCalendarBooking(
			_user, calendar, startTime, startTime + (Time.HOUR * 10),
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

	@Test(expected = CalendarBookingRecurrenceException.class)
	public void testStartDateBeforeUntilDateThrowsRecurrenceException()
		throws Exception {

		ServiceContext serviceContext = createServiceContext();

		Calendar calendar = addCalendar(_user, serviceContext);

		long startTime = System.currentTimeMillis();

		java.util.Calendar untilJCalendar = CalendarFactoryUtil.getCalendar(
			startTime);

		untilJCalendar.add(java.util.Calendar.DAY_OF_MONTH, -2);

		Recurrence recurrence = new Recurrence();

		recurrence.setFrequency(Frequency.DAILY);
		recurrence.setUntilJCalendar(untilJCalendar);
		recurrence.setPositionalWeekdays(
			Collections.<PositionalWeekday>emptyList());

		addRecurringCalendarBooking(
			_user, calendar, startTime, startTime + (Time.HOUR * 10),
			recurrence, serviceContext);
	}

	@Test
	public void testUpdateCalendarBookingAndAllRecurringInstancesStatus()
		throws Exception {

		ServiceContext serviceContext = createServiceContext();

		Calendar calendar = addCalendar(_user, serviceContext);

		long startTime = System.currentTimeMillis();

		long endTime = startTime + (Time.HOUR * 10);

		Recurrence recurrence = new Recurrence();

		recurrence.setFrequency(Frequency.DAILY);
		recurrence.setPositionalWeekdays(new ArrayList<PositionalWeekday>());

		CalendarBooking calendarBooking = addRecurringCalendarBooking(
			_user, calendar, startTime, endTime, recurrence, serviceContext);

		long instanceStartTime = startTime + Time.DAY * 2;

		long instanceEndTime = instanceStartTime + (Time.HOUR * 10);

		Map<Locale, String> titleMap = RandomTestUtil.randomLocaleStringMap();

		CalendarBooking calendarBookingInstance =
			CalendarBookingLocalServiceUtil.updateCalendarBookingInstance(
				_user.getUserId(), calendarBooking.getCalendarBookingId(), 2,
				calendar.getCalendarId(), titleMap,
				calendarBooking.getDescriptionMap(),
				calendarBooking.getLocation(), instanceStartTime,
				instanceEndTime, false, null, false, 0, null, 0, null,
				serviceContext);

		CalendarBookingLocalServiceUtil.invokeTransition(
			_user.getUserId(), calendarBooking, startTime + Time.DAY,
			CalendarBookingWorkflowConstants.STATUS_MAYBE, true, true,
			serviceContext);

		calendarBookingInstance =
			CalendarBookingLocalServiceUtil.fetchCalendarBooking(
				calendarBookingInstance.getCalendarBookingId());

		Assert.assertEquals(
			CalendarBookingWorkflowConstants.STATUS_MAYBE,
			calendarBookingInstance.getStatus());
	}

	@Test
	public void testUpdateCalendarBookingInstance() throws Exception {
		ServiceContext serviceContext = createServiceContext();

		Calendar calendar = addCalendar(_user, serviceContext);

		long startTime = System.currentTimeMillis();

		Recurrence recurrence = new Recurrence();

		recurrence.setFrequency(Frequency.DAILY);
		recurrence.setPositionalWeekdays(new ArrayList<PositionalWeekday>());

		CalendarBooking calendarBooking = addRecurringCalendarBooking(
			_user, calendar, startTime, startTime + (Time.HOUR * 10),
			recurrence, serviceContext);

		Map<Locale, String> instanceTitleMap =
			RandomTestUtil.randomLocaleStringMap();

		long instanceStartTime = startTime + Time.DAY * 2;

		CalendarBooking calendarBookingInstance =
			CalendarBookingLocalServiceUtil.updateCalendarBookingInstance(
				_user.getUserId(), calendarBooking.getCalendarBookingId(), 2,
				calendar.getCalendarId(), instanceTitleMap,
				calendarBooking.getDescriptionMap(),
				calendarBooking.getLocation(), instanceStartTime,
				instanceStartTime + (Time.HOUR * 10), false, null, false, 0,
				null, 0, null, serviceContext);

		Assert.assertEquals(
			instanceTitleMap, calendarBookingInstance.getTitleMap());

		Map<Locale, String> newTitleMap =
			RandomTestUtil.randomLocaleStringMap();

		CalendarBookingLocalServiceUtil.updateCalendarBookingInstance(
			_user.getUserId(), calendarBooking.getCalendarBookingId(), 1,
			calendar.getCalendarId(), newTitleMap,
			calendarBooking.getDescriptionMap(), calendarBooking.getLocation(),
			startTime, startTime + (Time.HOUR * 10), false,
			calendarBooking.getRecurrence(), true, 0, null, 0, null,
			serviceContext);

		calendarBookingInstance =
			CalendarBookingLocalServiceUtil.fetchCalendarBooking(
				calendarBookingInstance.getCalendarBookingId());

		Assert.assertEquals(newTitleMap, calendarBookingInstance.getTitleMap());
	}

	@Test
	public void testUpdateCalendarBookingPreservesChildReminders()
		throws Exception {

		ServiceContext serviceContext = createServiceContext();

		Calendar calendar = addCalendar(_user, serviceContext);

		Calendar invitedCalendar = addCalendar(
			calendar.getCalendarResource(), serviceContext);

		long startTime = System.currentTimeMillis();

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		CalendarBooking calendarBooking = addCalendarBooking(
			_user, calendar, new long[] {invitedCalendar.getCalendarId()},
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(), startTime,
			startTime + (Time.HOUR * 10), null, RandomTestUtil.randomInt(),
			NotificationType.EMAIL, RandomTestUtil.randomInt(),
			NotificationType.EMAIL, serviceContext);

		CalendarBooking childCalendarBooking = getChildCalendarBooking(
			calendarBooking);

		childCalendarBooking = CalendarBookingLocalServiceUtil.updateStatus(
			_user.getUserId(), childCalendarBooking,
			CalendarBookingWorkflowConstants.STATUS_MAYBE, serviceContext);

		int firstReminder = RandomTestUtil.randomInt();

		int secondReminder = RandomTestUtil.randomInt(1, firstReminder);

		childCalendarBooking =
			CalendarBookingLocalServiceUtil.updateCalendarBooking(
				_user.getUserId(), childCalendarBooking.getCalendarBookingId(),
				childCalendarBooking.getCalendarId(), new long[0],
				childCalendarBooking.getTitleMap(),
				childCalendarBooking.getDescriptionMap(),
				childCalendarBooking.getLocation(), startTime,
				startTime + (Time.HOUR * 10), childCalendarBooking.getAllDay(),
				childCalendarBooking.getRecurrence(), firstReminder,
				NotificationType.EMAIL.getValue(), secondReminder,
				NotificationType.EMAIL.getValue(), serviceContext);

		calendarBooking = CalendarBookingLocalServiceUtil.updateCalendarBooking(
			_user.getUserId(), calendarBooking.getCalendarBookingId(),
			calendarBooking.getCalendarId(),
			new long[] {invitedCalendar.getCalendarId()},
			calendarBooking.getTitleMap(), calendarBooking.getDescriptionMap(),
			calendarBooking.getLocation(), startTime,
			startTime + (Time.HOUR * 11), calendarBooking.getAllDay(),
			calendarBooking.getRecurrence(), RandomTestUtil.randomInt(),
			calendarBooking.getFirstReminderType(), RandomTestUtil.randomInt(),
			calendarBooking.getSecondReminderType(), serviceContext);

		childCalendarBooking = getChildCalendarBooking(calendarBooking);

		Assert.assertNotEquals(
			calendarBooking.getFirstReminder(),
			childCalendarBooking.getFirstReminder());
		Assert.assertNotEquals(
			calendarBooking.getSecondReminder(),
			childCalendarBooking.getSecondReminder());

		Assert.assertEquals(
			firstReminder, childCalendarBooking.getFirstReminder());
		Assert.assertEquals(
			secondReminder, childCalendarBooking.getSecondReminder());
	}

	@Test
	public void testUpdateCalendarBookingPreservesChildStatus()
		throws Exception {

		ServiceContext serviceContext = createServiceContext();

		Calendar calendar = addCalendar(_user, serviceContext);

		Calendar invitedCalendar = addCalendar(
			calendar.getCalendarResource(), serviceContext);

		long startTime = System.currentTimeMillis();

		long endTime = startTime + (Time.HOUR * 10);

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		CalendarBooking calendarBooking = addMasterCalendarBooking(
			_user, calendar, new long[] {invitedCalendar.getCalendarId()},
			startTime, endTime, serviceContext);

		CalendarBooking childCalendarBooking = getChildCalendarBooking(
			calendarBooking);

		Assert.assertEquals(
			WorkflowConstants.STATUS_PENDING, childCalendarBooking.getStatus());

		CalendarBookingLocalServiceUtil.updateStatus(
			_user.getUserId(), childCalendarBooking,
			CalendarBookingWorkflowConstants.STATUS_MAYBE, serviceContext);

		calendarBooking = CalendarBookingLocalServiceUtil.updateCalendarBooking(
			_user.getUserId(), calendarBooking.getCalendarBookingId(),
			calendarBooking.getCalendarId(),
			new long[] {invitedCalendar.getCalendarId()},
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomString(), startTime, endTime,
			calendarBooking.getAllDay(), calendarBooking.getRecurrence(),
			calendarBooking.getFirstReminder(),
			calendarBooking.getFirstReminderType(),
			calendarBooking.getSecondReminder(),
			calendarBooking.getSecondReminderType(), serviceContext);

		childCalendarBooking = getChildCalendarBooking(calendarBooking);

		Assert.assertEquals(
			CalendarBookingWorkflowConstants.STATUS_MAYBE,
			childCalendarBooking.getStatus());

		long newEndTime = endTime + 1000000;

		calendarBooking = CalendarBookingLocalServiceUtil.updateCalendarBooking(
			_user.getUserId(), calendarBooking.getCalendarBookingId(),
			calendarBooking.getCalendarId(),
			new long[] {invitedCalendar.getCalendarId()},
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomString(), startTime, newEndTime,
			calendarBooking.getAllDay(), calendarBooking.getRecurrence(),
			calendarBooking.getFirstReminder(),
			calendarBooking.getFirstReminderType(),
			calendarBooking.getSecondReminder(),
			calendarBooking.getSecondReminderType(), serviceContext);

		childCalendarBooking = getChildCalendarBooking(calendarBooking);

		Assert.assertNotEquals(
			CalendarBookingWorkflowConstants.STATUS_MAYBE,
			childCalendarBooking.getStatus());
	}

	@Test
	public void testUpdateCalendarBookingPreservesDescriptionTranslations()
		throws Exception {

		ServiceContext serviceContext = createServiceContext();

		Calendar calendar = addCalendar(_user, serviceContext);

		Map<Locale, String> oldDescriptionMap = new HashMap<>();

		oldDescriptionMap.put(LocaleUtil.BRAZIL, RandomTestUtil.randomString());
		oldDescriptionMap.put(
			LocaleUtil.GERMANY, RandomTestUtil.randomString());
		oldDescriptionMap.put(LocaleUtil.SPAIN, RandomTestUtil.randomString());
		oldDescriptionMap.put(LocaleUtil.US, RandomTestUtil.randomString());

		long startTime = System.currentTimeMillis();

		CalendarBooking calendarBooking =
			addRegularCalendarBookingWithTitleAndDescription(
				_user, calendar, RandomTestUtil.randomLocaleStringMap(),
				oldDescriptionMap, startTime, startTime + (Time.HOUR * 10),
				serviceContext);

		Map<Locale, String> newDescriptionMap = new HashMap<>();

		newDescriptionMap.put(LocaleUtil.GERMANY, "");
		newDescriptionMap.put(LocaleUtil.SPAIN, RandomTestUtil.randomString());
		newDescriptionMap.put(
			LocaleUtil.US, oldDescriptionMap.get(LocaleUtil.US));

		calendarBooking = CalendarBookingLocalServiceUtil.updateCalendarBooking(
			_user.getUserId(), calendarBooking.getCalendarBookingId(),
			calendar.getCalendarId(), new long[0],
			RandomTestUtil.randomLocaleStringMap(), newDescriptionMap,
			RandomTestUtil.randomString(), startTime,
			startTime + (Time.HOUR * 10), false, null, 0, null, 0, null,
			serviceContext);

		calendarBooking = CalendarBookingLocalServiceUtil.fetchCalendarBooking(
			calendarBooking.getCalendarBookingId());

		Assert.assertEquals(
			oldDescriptionMap.get(LocaleUtil.BRAZIL),
			calendarBooking.getDescription(LocaleUtil.BRAZIL));
		Assert.assertEquals(
			newDescriptionMap.get(LocaleUtil.SPAIN),
			calendarBooking.getDescription(LocaleUtil.SPAIN));
		Assert.assertEquals(
			newDescriptionMap.get(LocaleUtil.US),
			calendarBooking.getDescription(LocaleUtil.US));

		Map<Locale, String> descriptionMap =
			calendarBooking.getDescriptionMap();

		Assert.assertFalse(descriptionMap.containsKey(LocaleUtil.GERMANY));
	}

	@Test
	public void testUpdateCalendarBookingPreservesTitleTranslations()
		throws Exception {

		ServiceContext serviceContext = createServiceContext();

		Calendar calendar = addCalendar(_user, serviceContext);

		Map<Locale, String> oldTitleMap = new HashMap<>();

		oldTitleMap.put(LocaleUtil.BRAZIL, RandomTestUtil.randomString());
		oldTitleMap.put(LocaleUtil.GERMANY, RandomTestUtil.randomString());
		oldTitleMap.put(LocaleUtil.SPAIN, RandomTestUtil.randomString());
		oldTitleMap.put(LocaleUtil.US, RandomTestUtil.randomString());

		long startTime = System.currentTimeMillis();

		CalendarBooking calendarBooking =
			addRegularCalendarBookingWithTitleAndDescription(
				_user, calendar, oldTitleMap,
				RandomTestUtil.randomLocaleStringMap(), startTime,
				startTime + (Time.HOUR * 10), serviceContext);

		Map<Locale, String> newTitleMap = new HashMap<>();

		newTitleMap.put(LocaleUtil.GERMANY, "");
		newTitleMap.put(LocaleUtil.SPAIN, RandomTestUtil.randomString());
		newTitleMap.put(LocaleUtil.US, oldTitleMap.get(LocaleUtil.US));

		calendarBooking = CalendarBookingLocalServiceUtil.updateCalendarBooking(
			_user.getUserId(), calendarBooking.getCalendarBookingId(),
			calendar.getCalendarId(), new long[0], newTitleMap,
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomString(), startTime,
			startTime + (Time.HOUR * 10), false, null, 0, null, 0, null,
			serviceContext);

		calendarBooking = CalendarBookingLocalServiceUtil.fetchCalendarBooking(
			calendarBooking.getCalendarBookingId());

		Assert.assertEquals(
			oldTitleMap.get(LocaleUtil.BRAZIL),
			calendarBooking.getTitle(LocaleUtil.BRAZIL));
		Assert.assertEquals(
			newTitleMap.get(LocaleUtil.SPAIN),
			calendarBooking.getTitle(LocaleUtil.SPAIN));
		Assert.assertEquals(
			oldTitleMap.get(LocaleUtil.US),
			calendarBooking.getTitle(LocaleUtil.US));

		Map<Locale, String> titleMap = calendarBooking.getTitleMap();

		Assert.assertFalse(titleMap.containsKey(LocaleUtil.GERMANY));
	}

	@Test
	public void testUpdateChildCalendarBookingPreservesStatus()
		throws Exception {

		ServiceContext serviceContext = createServiceContext();

		Calendar calendar = addCalendar(_user, serviceContext);

		Calendar invitedCalendar = addCalendar(
			calendar.getCalendarResource(), serviceContext);

		long startTime = System.currentTimeMillis();

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		CalendarBooking calendarBooking = addMasterCalendarBooking(
			_user, calendar, new long[] {invitedCalendar.getCalendarId()},
			startTime, startTime + (Time.HOUR * 10), serviceContext);

		CalendarBooking childCalendarBooking = getChildCalendarBooking(
			calendarBooking);

		Assert.assertEquals(
			WorkflowConstants.STATUS_PENDING, childCalendarBooking.getStatus());

		childCalendarBooking = CalendarBookingLocalServiceUtil.updateStatus(
			_user.getUserId(), childCalendarBooking,
			CalendarBookingWorkflowConstants.STATUS_MAYBE, serviceContext);

		CalendarBookingLocalServiceUtil.updateCalendarBooking(
			_user.getUserId(), childCalendarBooking.getCalendarBookingId(),
			childCalendarBooking.getCalendarId(),
			new long[] {invitedCalendar.getCalendarId()},
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomString(), startTime,
			startTime + (Time.HOUR * 10), childCalendarBooking.getAllDay(),
			childCalendarBooking.getRecurrence(),
			childCalendarBooking.getFirstReminder(),
			childCalendarBooking.getFirstReminderType(),
			childCalendarBooking.getSecondReminder(),
			childCalendarBooking.getSecondReminderType(), serviceContext);

		childCalendarBooking = getChildCalendarBooking(calendarBooking);

		Assert.assertEquals(
			CalendarBookingWorkflowConstants.STATUS_MAYBE,
			childCalendarBooking.getStatus());
	}

	@Test
	public void testUpdateRecurringCalendarBooking() throws Exception {
		ServiceContext serviceContext = createServiceContext();

		Calendar calendar = addCalendar(_user, serviceContext);

		long startTime = System.currentTimeMillis();

		Recurrence recurrence = new Recurrence();

		recurrence.setFrequency(Frequency.DAILY);
		recurrence.setPositionalWeekdays(new ArrayList<PositionalWeekday>());

		CalendarBooking calendarBooking = addRecurringCalendarBooking(
			_user, calendar, startTime, startTime + (Time.HOUR * 10),
			recurrence, serviceContext);

		Map<Locale, String> instanceTitleMap =
			RandomTestUtil.randomLocaleStringMap();

		long instanceStartTime = startTime + Time.DAY * 2;

		CalendarBooking calendarBookingInstance =
			CalendarBookingLocalServiceUtil.updateCalendarBookingInstance(
				_user.getUserId(), calendarBooking.getCalendarBookingId(), 2,
				calendar.getCalendarId(), instanceTitleMap,
				calendarBooking.getDescriptionMap(),
				calendarBooking.getLocation(), instanceStartTime,
				instanceStartTime + (Time.HOUR * 10), false, null, false, 0,
				null, 0, null, serviceContext);

		Assert.assertEquals(
			instanceTitleMap, calendarBookingInstance.getTitleMap());

		Map<Locale, String> newTitleMap =
			RandomTestUtil.randomLocaleStringMap();

		CalendarBookingLocalServiceUtil.updateRecurringCalendarBooking(
			_user.getUserId(), calendarBooking.getCalendarBookingId(),
			calendar.getCalendarId(), new long[0], newTitleMap,
			calendarBooking.getDescriptionMap(), calendarBooking.getLocation(),
			startTime, startTime + (Time.HOUR * 10), false, 0, null, 0, null,
			serviceContext);

		calendarBookingInstance =
			CalendarBookingLocalServiceUtil.fetchCalendarBooking(
				calendarBookingInstance.getCalendarBookingId());

		Assert.assertEquals(newTitleMap, calendarBookingInstance.getTitleMap());
	}

	protected Calendar addCalendar(
			CalendarResource calendarResource, ServiceContext serviceContext)
		throws PortalException {

		return CalendarLocalServiceUtil.addCalendar(
			_user.getUserId(), _user.getGroupId(),
			calendarResource.getCalendarResourceId(),
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(),
			calendarResource.getTimeZoneId(), RandomTestUtil.randomInt(), false,
			false, false, serviceContext);
	}

	protected Calendar addCalendar(User user, ServiceContext serviceContext)
		throws PortalException {

		CalendarResource calendarResource =
			CalendarResourceUtil.getUserCalendarResource(
				user.getUserId(), serviceContext);

		return calendarResource.getDefaultCalendar();
	}

	protected CalendarBooking addCalendarBooking(
			User user, Calendar calendar, long[] childCalendarBookingIds,
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
			long startTime, long endTime, Recurrence recurrence,
			int firstReminder, NotificationType firstReminderType,
			int secondReminder, NotificationType secondReminderType,
			ServiceContext serviceContext)
		throws PortalException {

		String firstReminderTypeString = null;

		if (firstReminderType != null) {
			firstReminderTypeString = firstReminderType.getValue();
		}

		String secondReminderTypeString = null;

		if (secondReminderType != null) {
			secondReminderTypeString = secondReminderType.getValue();
		}

		CalendarBooking calendarBooking =
			CalendarBookingLocalServiceUtil.addCalendarBooking(
				user.getUserId(), calendar.getCalendarId(),
				childCalendarBookingIds,
				CalendarBookingConstants.PARENT_CALENDAR_BOOKING_ID_DEFAULT,
				CalendarBookingConstants.RECURRING_CALENDAR_BOOKING_ID_DEFAULT,
				titleMap, descriptionMap, RandomTestUtil.randomString(),
				startTime, endTime, false,
				RecurrenceSerializer.serialize(recurrence), firstReminder,
				firstReminderTypeString, secondReminder,
				secondReminderTypeString, serviceContext);

		return calendarBooking;
	}

	protected CalendarBooking addMasterCalendarBooking(
			User user, Calendar calendar, long[] childCalendarBookingIds,
			long startTime, long endTime, ServiceContext serviceContext)
		throws PortalException {

		return addCalendarBooking(
			user, calendar, childCalendarBookingIds,
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(), startTime, endTime, null, 0,
			null, 0, null, serviceContext);
	}

	protected CalendarBooking addRecurringCalendarBooking(
			User user, Calendar calendar, long startTime, long endTime,
			Recurrence recurrence, ServiceContext serviceContext)
		throws PortalException {

		return addCalendarBooking(
			user, calendar, new long[0], RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(), startTime, endTime,
			recurrence, 0, null, 0, null, serviceContext);
	}

	protected CalendarBooking addRegularCalendarBooking(
			User user, Calendar calendar, long startTime, long endTime,
			ServiceContext serviceContext)
		throws PortalException {

		return addCalendarBooking(
			user, calendar, new long[0], RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(), startTime, endTime, null, 0,
			null, 0, null, serviceContext);
	}

	protected CalendarBooking addRegularCalendarBookingWithTitleAndDescription(
			User user, Calendar calendar, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, long startTime, long endTime,
			ServiceContext serviceContext)
		throws PortalException {

		return addCalendarBooking(
			user, calendar, new long[0], titleMap, descriptionMap, startTime,
			endTime, null, 0, null, 0, null, serviceContext);
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

	protected void setUpCheckBookingMessageListener() {
		Bundle bundle = FrameworkUtil.getBundle(
			CalendarBookingLocalServiceTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		ServiceReference<?> serviceReference =
			bundleContext.getServiceReference(
				"com.liferay.calendar.web.internal.messaging." +
					"CheckBookingsMessageListener");

		_checkBookingMessageListener = bundleContext.getService(
			serviceReference);

		ReflectionTestUtil.setFieldValue(
			_checkBookingMessageListener, "_calendarBookingLocalService",
			ProxyUtil.newProxyInstance(
				CalendarBookingLocalService.class.getClassLoader(),
				new Class<?>[] {CalendarBookingLocalService.class},
				new InvocationHandler() {

					@Override
					public Object invoke(
							Object proxy, Method method, Object[] args)
						throws Throwable {

						if ("checkCalendarBookings".equals(method.getName())) {
							return null;
						}

						return method.invoke(
							CalendarBookingLocalServiceUtil.getService(), args);
					}

				}));
	}

	protected void tearDownCheckBookingMessageListener() {
		ReflectionTestUtil.setFieldValue(
			_checkBookingMessageListener, "_calendarBookingLocalService",
			CalendarBookingLocalServiceUtil.getService());
	}

	private Object _checkBookingMessageListener;

	@DeleteAfterTestRun
	private User _invitingUser;

	@DeleteAfterTestRun
	private User _user;

}
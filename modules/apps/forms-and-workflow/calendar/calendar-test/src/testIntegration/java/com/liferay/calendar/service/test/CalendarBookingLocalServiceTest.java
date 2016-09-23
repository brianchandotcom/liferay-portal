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

	@After
	public void tearDown() {
		ReflectionTestUtil.setFieldValue(
			_checkBookingMessageListener, "_calendarBookingLocalService",
			CalendarBookingLocalServiceUtil.getService());
	}

	@Test
	public void testAddCalendarBooking() throws Exception {
		ServiceContext serviceContext = createServiceContext();

		CalendarResource calendarResource =
			CalendarResourceUtil.getUserCalendarResource(
				_user.getUserId(), serviceContext);

		Calendar calendar = calendarResource.getDefaultCalendar();

		long startTime = System.currentTimeMillis();

		serviceContext.setLanguageId("fr_FR");

		CalendarBooking calendarBooking =
			CalendarBookingLocalServiceUtil.addCalendarBooking(
				_user.getUserId(), calendar.getCalendarId(), new long[0],
				CalendarBookingConstants.PARENT_CALENDAR_BOOKING_ID_DEFAULT,
				CalendarBookingConstants.RECURRING_CALENDAR_BOOKING_ID_DEFAULT,
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomString(), startTime,
				startTime + (Time.HOUR * 10), false, null, 0, null, 0, null,
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

		CalendarResource calendarResource =
			CalendarResourceUtil.getUserCalendarResource(
				_invitingUser.getUserId(), serviceContext);

		Calendar calendar = calendarResource.getDefaultCalendar();

		CalendarResource invitedCalendarResource =
			CalendarResourceUtil.getUserCalendarResource(
				_user.getUserId(), serviceContext);

		Calendar invitedCalendar = invitedCalendarResource.getDefaultCalendar();

		long startTime = System.currentTimeMillis() + Time.MINUTE;

		long endTime = startTime + Time.HOUR;

		long firstReminder = Time.MINUTE;

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		CalendarBooking calendarBooking =
			CalendarBookingLocalServiceUtil.addCalendarBooking(
				_invitingUser.getUserId(), calendar.getCalendarId(),
				new long[] {invitedCalendar.getCalendarId()},
				CalendarBookingConstants.PARENT_CALENDAR_BOOKING_ID_DEFAULT,
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomString(), startTime, endTime, false, null,
				firstReminder, NotificationType.EMAIL.getValue(), 0,
				NotificationType.EMAIL.getValue(), serviceContext);

		CalendarBooking childCalendarBooking = getChildCalendarBooking(
			calendarBooking);

		childCalendarBooking = CalendarBookingLocalServiceUtil.updateStatus(
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

		CalendarResource calendarResource =
			CalendarResourceUtil.getUserCalendarResource(
				_user.getUserId(), serviceContext);

		Calendar calendar = calendarResource.getDefaultCalendar();

		long startTime = System.currentTimeMillis();

		Recurrence recurrence = new Recurrence();

		recurrence.setFrequency(Frequency.DAILY);
		recurrence.setPositionalWeekdays(new ArrayList<PositionalWeekday>());

		CalendarBooking calendarBooking =
			CalendarBookingLocalServiceUtil.addCalendarBooking(
				_user.getUserId(), calendar.getCalendarId(), new long[0],
				CalendarBookingConstants.PARENT_CALENDAR_BOOKING_ID_DEFAULT,
				CalendarBookingConstants.RECURRING_CALENDAR_BOOKING_ID_DEFAULT,
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomString(), startTime, startTime + 36000000,
				false, RecurrenceSerializer.serialize(recurrence), 0, null, 0,
				null, serviceContext);

		long instanceStartTime = startTime + Time.DAY * 2;

		Map<Locale, String> titleMap = RandomTestUtil.randomLocaleStringMap();

		CalendarBooking calendarBookingInstance =
			CalendarBookingLocalServiceUtil.updateCalendarBookingInstance(
				_user.getUserId(), calendarBooking.getCalendarBookingId(), 2,
				calendar.getCalendarId(), titleMap,
				calendarBooking.getDescriptionMap(),
				calendarBooking.getLocation(), instanceStartTime,
				instanceStartTime + 36000000, false, null, false, 0, null, 0,
				null, serviceContext);

		CalendarBookingLocalServiceUtil.deleteCalendarBooking(calendarBooking);

		calendarBookingInstance =
			CalendarBookingLocalServiceUtil.fetchCalendarBooking(
				calendarBookingInstance.getCalendarBookingId());

		Assert.assertEquals(titleMap, calendarBookingInstance.getTitleMap());
	}

	@Test
	public void testDeleteCalendarBookingWithAllRecurringInstances()
		throws Exception {

		ServiceContext serviceContext = createServiceContext();

		CalendarResource calendarResource =
			CalendarResourceUtil.getUserCalendarResource(
				_user.getUserId(), serviceContext);

		Calendar calendar = calendarResource.getDefaultCalendar();

		long startTime = System.currentTimeMillis();

		Recurrence recurrence = new Recurrence();

		recurrence.setFrequency(Frequency.DAILY);
		recurrence.setPositionalWeekdays(new ArrayList<PositionalWeekday>());

		CalendarBooking calendarBooking =
			CalendarBookingLocalServiceUtil.addCalendarBooking(
				_user.getUserId(), calendar.getCalendarId(), new long[0],
				CalendarBookingConstants.PARENT_CALENDAR_BOOKING_ID_DEFAULT,
				CalendarBookingConstants.RECURRING_CALENDAR_BOOKING_ID_DEFAULT,
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomString(), startTime, startTime + 36000000,
				false, RecurrenceSerializer.serialize(recurrence), 0, null, 0,
				null, serviceContext);

		long instanceStartTime = startTime + Time.DAY * 2;

		CalendarBooking calendarBookingInstance =
			CalendarBookingLocalServiceUtil.updateCalendarBookingInstance(
				_user.getUserId(), calendarBooking.getCalendarBookingId(), 2,
				calendar.getCalendarId(),
				RandomTestUtil.randomLocaleStringMap(),
				calendarBooking.getDescriptionMap(),
				calendarBooking.getLocation(), instanceStartTime,
				instanceStartTime + 36000000, false, null, false, 0, null, 0,
				null, serviceContext);

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

		CalendarResource calendarResource =
			CalendarResourceUtil.getUserCalendarResource(
				_user.getUserId(), serviceContext);

		Calendar calendar = calendarResource.getDefaultCalendar();

		long startTime = System.currentTimeMillis();

		Recurrence recurrence = new Recurrence();

		recurrence.setFrequency(Frequency.DAILY);
		recurrence.setPositionalWeekdays(new ArrayList<PositionalWeekday>());

		CalendarBooking calendarBooking =
			CalendarBookingLocalServiceUtil.addCalendarBooking(
				_user.getUserId(), calendar.getCalendarId(), new long[0],
				CalendarBookingConstants.PARENT_CALENDAR_BOOKING_ID_DEFAULT,
				CalendarBookingConstants.RECURRING_CALENDAR_BOOKING_ID_DEFAULT,
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomString(), startTime, startTime + 36000000,
				false, RecurrenceSerializer.serialize(recurrence), 0, null, 0,
				null, serviceContext);

		long instanceStartTime = startTime + Time.DAY * 2;

		Map<Locale, String> titleMap = RandomTestUtil.randomLocaleStringMap();

		CalendarBooking calendarBookingInstance =
			CalendarBookingLocalServiceUtil.updateCalendarBookingInstance(
				_user.getUserId(), calendarBooking.getCalendarBookingId(), 2,
				calendar.getCalendarId(), titleMap,
				calendarBooking.getDescriptionMap(),
				calendarBooking.getLocation(), instanceStartTime,
				instanceStartTime + 36000000, false, null, false, 0, null, 0,
				null, serviceContext);

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

		CalendarResource calendarResource =
			CalendarResourceUtil.getUserCalendarResource(
				_user.getUserId(), serviceContext);

		Calendar calendar = calendarResource.getDefaultCalendar();

		long startTime = System.currentTimeMillis();

		Recurrence recurrence = new Recurrence();

		recurrence.setCount(2);
		recurrence.setFrequency(Frequency.DAILY);
		recurrence.setPositionalWeekdays(new ArrayList<PositionalWeekday>());

		CalendarBooking calendarBooking =
			CalendarBookingLocalServiceUtil.addCalendarBooking(
				_user.getUserId(), calendar.getCalendarId(), new long[0],
				CalendarBookingConstants.PARENT_CALENDAR_BOOKING_ID_DEFAULT,
				CalendarBookingConstants.RECURRING_CALENDAR_BOOKING_ID_DEFAULT,
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
	public void testGetRecurringCalendarBookings() throws Exception {
		ServiceContext serviceContext = createServiceContext();

		CalendarResource calendarResource =
			CalendarResourceUtil.getUserCalendarResource(
				_user.getUserId(), serviceContext);

		Calendar calendar = calendarResource.getDefaultCalendar();

		long startTime = System.currentTimeMillis();

		Recurrence recurrence = new Recurrence();

		recurrence.setFrequency(Frequency.DAILY);
		recurrence.setPositionalWeekdays(new ArrayList<PositionalWeekday>());

		CalendarBooking calendarBooking =
			CalendarBookingLocalServiceUtil.addCalendarBooking(
				_user.getUserId(), calendar.getCalendarId(), new long[0],
				CalendarBookingConstants.PARENT_CALENDAR_BOOKING_ID_DEFAULT,
				CalendarBookingConstants.RECURRING_CALENDAR_BOOKING_ID_DEFAULT,
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomString(), startTime, startTime + 36000000,
				false, RecurrenceSerializer.serialize(recurrence), 0, null, 0,
				null, serviceContext);

		Map<Locale, String> instanceTitleMap =
			RandomTestUtil.randomLocaleStringMap();

		int instanceIndex = 2;

		long instanceStartTime = startTime + Time.DAY * instanceIndex;

		CalendarBookingLocalServiceUtil.updateCalendarBookingInstance(
			_user.getUserId(), calendarBooking.getCalendarBookingId(),
			instanceIndex, calendar.getCalendarId(), instanceTitleMap,
			calendarBooking.getDescriptionMap(), calendarBooking.getLocation(),
			instanceStartTime, instanceStartTime + 36000000, false, null, false,
			0, null, 0, null, serviceContext);

		instanceIndex = 4;

		instanceStartTime = instanceStartTime + Time.DAY * instanceStartTime;

		CalendarBookingLocalServiceUtil.updateCalendarBookingInstance(
			_user.getUserId(), calendarBooking.getCalendarBookingId(),
			instanceIndex, calendar.getCalendarId(), instanceTitleMap,
			calendarBooking.getDescriptionMap(), calendarBooking.getLocation(),
			instanceStartTime, instanceStartTime + 36000000, false, null, false,
			0, null, 0, null, serviceContext);

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

		CalendarResource calendarResource =
			CalendarResourceUtil.getUserCalendarResource(
				_user.getUserId(), serviceContext);

		Calendar calendar = calendarResource.getDefaultCalendar();

		long startTime = System.currentTimeMillis();

		Recurrence recurrence = new Recurrence();

		recurrence.setFrequency(Frequency.DAILY);
		recurrence.setPositionalWeekdays(new ArrayList<PositionalWeekday>());

		CalendarBooking calendarBooking =
			CalendarBookingLocalServiceUtil.addCalendarBooking(
				_user.getUserId(), calendar.getCalendarId(), new long[0],
				CalendarBookingConstants.PARENT_CALENDAR_BOOKING_ID_DEFAULT,
				CalendarBookingConstants.RECURRING_CALENDAR_BOOKING_ID_DEFAULT,
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomString(), startTime, startTime + 36000000,
				false, RecurrenceSerializer.serialize(recurrence), 0, null, 0,
				null, serviceContext);

		Map<Locale, String> instanceTitleMap =
			RandomTestUtil.randomLocaleStringMap();

		int instanceIndex = 2;

		long firstInstancStartTime = startTime + Time.DAY * instanceIndex;

		CalendarBookingLocalServiceUtil.updateCalendarBookingInstance(
			_user.getUserId(), calendarBooking.getCalendarBookingId(),
			instanceIndex, calendar.getCalendarId(), instanceTitleMap,
			calendarBooking.getDescriptionMap(), calendarBooking.getLocation(),
			firstInstancStartTime, firstInstancStartTime + 36000000, false,
			null, false, 0, null, 0, null, serviceContext);

		instanceIndex = 4;

		long secondInstancStartTime =
			firstInstancStartTime + Time.DAY * instanceIndex;

		CalendarBookingLocalServiceUtil.updateCalendarBookingInstance(
			_user.getUserId(), calendarBooking.getCalendarBookingId(),
			instanceIndex, calendar.getCalendarId(), instanceTitleMap,
			calendarBooking.getDescriptionMap(), calendarBooking.getLocation(),
			secondInstancStartTime, secondInstancStartTime + 36000000, false,
			null, false, 0, null, 0, null, serviceContext);

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

		long startTime = System.currentTimeMillis();

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		CalendarBooking calendarBooking =
			CalendarBookingLocalServiceUtil.addCalendarBooking(
				_user.getUserId(), calendar.getCalendarId(),
				new long[] {invitedCalendar.getCalendarId()},
				CalendarBookingConstants.PARENT_CALENDAR_BOOKING_ID_DEFAULT,
				CalendarBookingConstants.RECURRING_CALENDAR_BOOKING_ID_DEFAULT,
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomString(), startTime, startTime + 36000000,
				false, null, 0, null, 0, null, serviceContext);

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

		long startTime = System.currentTimeMillis();

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		CalendarBooking calendarBooking =
			CalendarBookingLocalServiceUtil.addCalendarBooking(
				_user.getUserId(), calendar.getCalendarId(),
				new long[] {invitedCalendar.getCalendarId()},
				CalendarBookingConstants.PARENT_CALENDAR_BOOKING_ID_DEFAULT,
				CalendarBookingConstants.RECURRING_CALENDAR_BOOKING_ID_DEFAULT,
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomString(), startTime, startTime + 36000000,
				false, null, 0, null, 0, null, serviceContext);

		CalendarBooking childCalendarBooking = getChildCalendarBooking(
			calendarBooking);

		Assert.assertEquals(
			WorkflowConstants.STATUS_PENDING, childCalendarBooking.getStatus());
	}

	@Test
	public void testMoveToTrashCalendarBookingShouldMoveItsChildrenToTrash()
		throws Exception {

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

		long startTime = System.currentTimeMillis();

		CalendarBooking calendarBooking =
			CalendarBookingLocalServiceUtil.addCalendarBooking(
				_user.getUserId(), calendar.getCalendarId(),
				new long[] {invitedCalendar.getCalendarId()},
				CalendarBookingConstants.PARENT_CALENDAR_BOOKING_ID_DEFAULT,
				CalendarBookingConstants.RECURRING_CALENDAR_BOOKING_ID_DEFAULT,
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomString(), startTime, startTime + 36000000,
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
	public void testPublishCalendarBooking() throws Exception {
		ServiceContext serviceContext = createServiceContext();

		CalendarResource calendarResource =
			CalendarResourceUtil.getUserCalendarResource(
				_user.getUserId(), serviceContext);

		Calendar calendar = calendarResource.getDefaultCalendar();

		long startTime = System.currentTimeMillis();

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		CalendarBooking calendarBooking =
			CalendarBookingLocalServiceUtil.addCalendarBooking(
				_user.getUserId(), calendar.getCalendarId(), new long[0],
				CalendarBookingConstants.PARENT_CALENDAR_BOOKING_ID_DEFAULT,
				CalendarBookingConstants.RECURRING_CALENDAR_BOOKING_ID_DEFAULT,
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
	public void testPublishDraftCalendarBooking() throws Exception {
		ServiceContext serviceContext = createServiceContext();

		CalendarResource calendarResource =
			CalendarResourceUtil.getUserCalendarResource(
				_user.getUserId(), serviceContext);

		Calendar calendar = calendarResource.getDefaultCalendar();

		long startTime = System.currentTimeMillis();

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		CalendarBooking calendarBooking =
			CalendarBookingLocalServiceUtil.addCalendarBooking(
				_user.getUserId(), calendar.getCalendarId(), new long[0],
				CalendarBookingConstants.PARENT_CALENDAR_BOOKING_ID_DEFAULT,
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
	public void testPublishDraftCalendarBookingResultsInPendingChild()
		throws Exception {

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

		long startTime = System.currentTimeMillis();

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		CalendarBooking calendarBooking =
			CalendarBookingLocalServiceUtil.addCalendarBooking(
				_user.getUserId(), calendar.getCalendarId(),
				new long[] {invitedCalendar.getCalendarId()},
				CalendarBookingConstants.PARENT_CALENDAR_BOOKING_ID_DEFAULT,
				CalendarBookingConstants.RECURRING_CALENDAR_BOOKING_ID_DEFAULT,
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomString(), startTime, startTime + 36000000,
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
			new long[] {invitedCalendar.getCalendarId()},
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomString(), startTime, startTime + 36000000,
			false, null, 0, null, 0, null, serviceContext);

		childCalendarBooking = getChildCalendarBooking(calendarBooking);

		Assert.assertEquals(
			CalendarBookingWorkflowConstants.STATUS_PENDING,
			childCalendarBooking.getStatus());
	}

	@Test
	public void testRestoredFromTrashEventResultsInRestoredFromTrashChildren()
		throws Exception {

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

		long startTime = System.currentTimeMillis();

		CalendarBooking calendarBooking =
			CalendarBookingLocalServiceUtil.addCalendarBooking(
				_user.getUserId(), calendar.getCalendarId(),
				new long[] {invitedCalendar.getCalendarId()},
				CalendarBookingConstants.PARENT_CALENDAR_BOOKING_ID_DEFAULT,
				CalendarBookingConstants.RECURRING_CALENDAR_BOOKING_ID_DEFAULT,
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomString(), startTime, startTime + 36000000,
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
	public void testSaveAsDraftCalendarBooking() throws Exception {
		ServiceContext serviceContext = createServiceContext();

		CalendarResource calendarResource =
			CalendarResourceUtil.getUserCalendarResource(
				_user.getUserId(), serviceContext);

		Calendar calendar = calendarResource.getDefaultCalendar();

		long startTime = System.currentTimeMillis();

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		CalendarBooking calendarBooking =
			CalendarBookingLocalServiceUtil.addCalendarBooking(
				_user.getUserId(), calendar.getCalendarId(), new long[0],
				CalendarBookingConstants.PARENT_CALENDAR_BOOKING_ID_DEFAULT,
				CalendarBookingConstants.RECURRING_CALENDAR_BOOKING_ID_DEFAULT,
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
	public void testSaveAsDraftDraftCalendarBooking() throws Exception {
		ServiceContext serviceContext = createServiceContext();

		CalendarResource calendarResource =
			CalendarResourceUtil.getUserCalendarResource(
				_user.getUserId(), serviceContext);

		Calendar calendar = calendarResource.getDefaultCalendar();

		long startTime = System.currentTimeMillis();

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		CalendarBooking calendarBooking =
			CalendarBookingLocalServiceUtil.addCalendarBooking(
				_user.getUserId(), calendar.getCalendarId(), new long[0],
				CalendarBookingConstants.PARENT_CALENDAR_BOOKING_ID_DEFAULT,
				CalendarBookingConstants.RECURRING_CALENDAR_BOOKING_ID_DEFAULT,
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
	public void testSaveAsDraftPublishedCalendarBooking() throws Exception {
		ServiceContext serviceContext = createServiceContext();

		CalendarResource calendarResource =
			CalendarResourceUtil.getUserCalendarResource(
				_user.getUserId(), serviceContext);

		Calendar calendar = calendarResource.getDefaultCalendar();

		long startTime = System.currentTimeMillis();

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		CalendarBooking calendarBooking =
			CalendarBookingLocalServiceUtil.addCalendarBooking(
				_user.getUserId(), calendar.getCalendarId(), new long[0],
				CalendarBookingConstants.PARENT_CALENDAR_BOOKING_ID_DEFAULT,
				CalendarBookingConstants.RECURRING_CALENDAR_BOOKING_ID_DEFAULT,
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomString(), startTime, startTime + 36000000,
				false, null, 0, null, 0, null, serviceContext);

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		calendarBooking = CalendarBookingLocalServiceUtil.updateCalendarBooking(
			_user.getUserId(), calendarBooking.getCalendarBookingId(),
			calendar.getCalendarId(), new long[0],
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomString(), startTime, startTime + 36000000,
			false, null, 0, null, 0, null, serviceContext);

		calendarBooking = CalendarBookingLocalServiceUtil.fetchCalendarBooking(
			calendarBooking.getCalendarBookingId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_DRAFT, calendarBooking.getStatus());
	}

	@Test(expected = CalendarBookingRecurrenceException.class)
	public void testStartDateBeforeUntilDateThrowsRecurrenceException()
		throws Exception {

		ServiceContext serviceContext = createServiceContext();

		CalendarResource calendarResource =
			CalendarResourceUtil.getUserCalendarResource(
				_user.getUserId(), serviceContext);

		Calendar calendar = calendarResource.getDefaultCalendar();

		long startTime = System.currentTimeMillis();

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
			CalendarBookingConstants.RECURRING_CALENDAR_BOOKING_ID_DEFAULT,
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomString(), startTime,
			startTime + (Time.HOUR * 10), false,
			RecurrenceSerializer.serialize(recurrence), 0, null, 0, null,
			serviceContext);
	}

	@Test
	public void testUpdateCalendarBookingInstance() throws Exception {
		ServiceContext serviceContext = createServiceContext();

		CalendarResource calendarResource =
			CalendarResourceUtil.getUserCalendarResource(
				_user.getUserId(), serviceContext);

		Calendar calendar = calendarResource.getDefaultCalendar();

		long startTime = System.currentTimeMillis();

		Recurrence recurrence = new Recurrence();

		recurrence.setFrequency(Frequency.DAILY);
		recurrence.setPositionalWeekdays(new ArrayList<PositionalWeekday>());

		CalendarBooking calendarBooking =
			CalendarBookingLocalServiceUtil.addCalendarBooking(
				_user.getUserId(), calendar.getCalendarId(), new long[0],
				CalendarBookingConstants.PARENT_CALENDAR_BOOKING_ID_DEFAULT,
				CalendarBookingConstants.RECURRING_CALENDAR_BOOKING_ID_DEFAULT,
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomString(), startTime, startTime + 36000000,
				false, RecurrenceSerializer.serialize(recurrence), 0, null, 0,
				null, serviceContext);

		Map<Locale, String> instanceTitleMap =
			RandomTestUtil.randomLocaleStringMap();

		long instanceStartTime = startTime + Time.DAY * 2;

		CalendarBooking calendarBookingInstance =
			CalendarBookingLocalServiceUtil.updateCalendarBookingInstance(
				_user.getUserId(), calendarBooking.getCalendarBookingId(), 2,
				calendar.getCalendarId(), instanceTitleMap,
				calendarBooking.getDescriptionMap(),
				calendarBooking.getLocation(), instanceStartTime,
				instanceStartTime + 36000000, false, null, false, 0, null, 0,
				null, serviceContext);

		Assert.assertEquals(
			instanceTitleMap, calendarBookingInstance.getTitleMap());

		Map<Locale, String> newTitleMap =
			RandomTestUtil.randomLocaleStringMap();

		CalendarBookingLocalServiceUtil.updateCalendarBookingInstance(
			_user.getUserId(), calendarBooking.getCalendarBookingId(), 1,
			calendar.getCalendarId(), newTitleMap,
			calendarBooking.getDescriptionMap(), calendarBooking.getLocation(),
			startTime, startTime + 36000000, false,
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

		long startTime = System.currentTimeMillis();

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		CalendarBooking calendarBooking =
			CalendarBookingLocalServiceUtil.addCalendarBooking(
				_user.getUserId(), calendar.getCalendarId(),
				new long[] {invitedCalendar.getCalendarId()},
				CalendarBookingConstants.PARENT_CALENDAR_BOOKING_ID_DEFAULT,
				CalendarBookingConstants.RECURRING_CALENDAR_BOOKING_ID_DEFAULT,
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomString(), startTime, startTime + 36000000,
				false, null, RandomTestUtil.randomInt(),
				NotificationType.EMAIL.getValue(), RandomTestUtil.randomInt(),
				NotificationType.EMAIL.getValue(), serviceContext);

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
				startTime + 36000000, childCalendarBooking.getAllDay(),
				childCalendarBooking.getRecurrence(), firstReminder,
				NotificationType.EMAIL.getValue(), secondReminder,
				NotificationType.EMAIL.getValue(), serviceContext);

		calendarBooking = CalendarBookingLocalServiceUtil.updateCalendarBooking(
			_user.getUserId(), calendarBooking.getCalendarBookingId(),
			calendarBooking.getCalendarId(),
			new long[] {invitedCalendar.getCalendarId()},
			calendarBooking.getTitleMap(), calendarBooking.getDescriptionMap(),
			calendarBooking.getLocation(), startTime, startTime + 37000000,
			calendarBooking.getAllDay(), calendarBooking.getRecurrence(),
			RandomTestUtil.randomInt(), calendarBooking.getFirstReminderType(),
			RandomTestUtil.randomInt(), calendarBooking.getSecondReminderType(),
			serviceContext);

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

		long startTime = System.currentTimeMillis();

		long endTime = startTime + 36000000;

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		CalendarBooking calendarBooking =
			CalendarBookingLocalServiceUtil.addCalendarBooking(
				_user.getUserId(), calendar.getCalendarId(),
				new long[] {invitedCalendar.getCalendarId()},
				CalendarBookingConstants.PARENT_CALENDAR_BOOKING_ID_DEFAULT,
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomString(), startTime, endTime, false, null,
				0, null, 0, null, serviceContext);

		CalendarBooking childCalendarBooking = getChildCalendarBooking(
			calendarBooking);

		Assert.assertEquals(
			WorkflowConstants.STATUS_PENDING, childCalendarBooking.getStatus());

		childCalendarBooking = CalendarBookingLocalServiceUtil.updateStatus(
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

		CalendarResource calendarResource =
			CalendarResourceUtil.getUserCalendarResource(
				_user.getUserId(), serviceContext);

		Calendar calendar = calendarResource.getDefaultCalendar();

		Map<Locale, String> oldDescriptionMap = new HashMap<>();

		oldDescriptionMap.put(LocaleUtil.BRAZIL, RandomTestUtil.randomString());
		oldDescriptionMap.put(
			LocaleUtil.GERMANY, RandomTestUtil.randomString());
		oldDescriptionMap.put(LocaleUtil.SPAIN, RandomTestUtil.randomString());
		oldDescriptionMap.put(LocaleUtil.US, RandomTestUtil.randomString());

		long startTime = System.currentTimeMillis();

		CalendarBooking calendarBooking =
			CalendarBookingLocalServiceUtil.addCalendarBooking(
				_user.getUserId(), calendar.getCalendarId(), new long[0],
				CalendarBookingConstants.PARENT_CALENDAR_BOOKING_ID_DEFAULT,
				CalendarBookingConstants.RECURRING_CALENDAR_BOOKING_ID_DEFAULT,
				RandomTestUtil.randomLocaleStringMap(), oldDescriptionMap,
				RandomTestUtil.randomString(), startTime, startTime + 36000000,
				false, null, 0, null, 0, null, serviceContext);

		Map<Locale, String> newDescriptionMap = new HashMap<>();

		newDescriptionMap.put(LocaleUtil.GERMANY, "");
		newDescriptionMap.put(LocaleUtil.SPAIN, RandomTestUtil.randomString());
		newDescriptionMap.put(
			LocaleUtil.US, oldDescriptionMap.get(LocaleUtil.US));

		calendarBooking = CalendarBookingLocalServiceUtil.updateCalendarBooking(
			_user.getUserId(), calendarBooking.getCalendarBookingId(),
			calendar.getCalendarId(), new long[0],
			RandomTestUtil.randomLocaleStringMap(), newDescriptionMap,
			RandomTestUtil.randomString(), startTime, startTime + 36000000,
			false, null, 0, null, 0, null, serviceContext);

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

		CalendarResource calendarResource =
			CalendarResourceUtil.getUserCalendarResource(
				_user.getUserId(), serviceContext);

		Calendar calendar = calendarResource.getDefaultCalendar();

		Map<Locale, String> oldTitleMap = new HashMap<>();

		oldTitleMap.put(LocaleUtil.BRAZIL, RandomTestUtil.randomString());
		oldTitleMap.put(LocaleUtil.GERMANY, RandomTestUtil.randomString());
		oldTitleMap.put(LocaleUtil.SPAIN, RandomTestUtil.randomString());
		oldTitleMap.put(LocaleUtil.US, RandomTestUtil.randomString());

		long startTime = System.currentTimeMillis();

		CalendarBooking calendarBooking =
			CalendarBookingLocalServiceUtil.addCalendarBooking(
				_user.getUserId(), calendar.getCalendarId(), new long[0],
				CalendarBookingConstants.PARENT_CALENDAR_BOOKING_ID_DEFAULT,
				CalendarBookingConstants.RECURRING_CALENDAR_BOOKING_ID_DEFAULT,
				oldTitleMap, RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomString(), startTime, startTime + 36000000,
				false, null, 0, null, 0, null, serviceContext);

		Map<Locale, String> newTitleMap = new HashMap<>();

		newTitleMap.put(LocaleUtil.GERMANY, "");
		newTitleMap.put(LocaleUtil.SPAIN, RandomTestUtil.randomString());
		newTitleMap.put(LocaleUtil.US, oldTitleMap.get(LocaleUtil.US));

		calendarBooking = CalendarBookingLocalServiceUtil.updateCalendarBooking(
			_user.getUserId(), calendarBooking.getCalendarBookingId(),
			calendar.getCalendarId(), new long[0], newTitleMap,
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomString(), startTime, startTime + 36000000,
			false, null, 0, null, 0, null, serviceContext);

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

		long startTime = System.currentTimeMillis();

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		CalendarBooking calendarBooking =
			CalendarBookingLocalServiceUtil.addCalendarBooking(
				_user.getUserId(), calendar.getCalendarId(),
				new long[] {invitedCalendar.getCalendarId()},
				CalendarBookingConstants.PARENT_CALENDAR_BOOKING_ID_DEFAULT,
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomString(), startTime, startTime + 36000000,
				false, null, 0, null, 0, null, serviceContext);

		CalendarBooking childCalendarBooking = getChildCalendarBooking(
			calendarBooking);

		Assert.assertEquals(
			WorkflowConstants.STATUS_PENDING, childCalendarBooking.getStatus());

		childCalendarBooking = CalendarBookingLocalServiceUtil.updateStatus(
			_user.getUserId(), childCalendarBooking,
			CalendarBookingWorkflowConstants.STATUS_MAYBE, serviceContext);

		childCalendarBooking =
			CalendarBookingLocalServiceUtil.updateCalendarBooking(
				_user.getUserId(), childCalendarBooking.getCalendarBookingId(),
				childCalendarBooking.getCalendarId(),
				new long[] {invitedCalendar.getCalendarId()},
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomString(), startTime, startTime + 36000000,
				childCalendarBooking.getAllDay(),
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

		CalendarResource calendarResource =
			CalendarResourceUtil.getUserCalendarResource(
				_user.getUserId(), serviceContext);

		Calendar calendar = calendarResource.getDefaultCalendar();

		long startTime = System.currentTimeMillis();

		Recurrence recurrence = new Recurrence();

		recurrence.setFrequency(Frequency.DAILY);
		recurrence.setPositionalWeekdays(new ArrayList<PositionalWeekday>());

		CalendarBooking calendarBooking =
			CalendarBookingLocalServiceUtil.addCalendarBooking(
				_user.getUserId(), calendar.getCalendarId(), new long[0],
				CalendarBookingConstants.PARENT_CALENDAR_BOOKING_ID_DEFAULT,
				CalendarBookingConstants.RECURRING_CALENDAR_BOOKING_ID_DEFAULT,
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomString(), startTime, startTime + 36000000,
				false, RecurrenceSerializer.serialize(recurrence), 0, null, 0,
				null, serviceContext);

		Map<Locale, String> instanceTitleMap =
			RandomTestUtil.randomLocaleStringMap();

		long instanceStartTime = startTime + Time.DAY * 2;

		CalendarBooking calendarBookingInstance =
			CalendarBookingLocalServiceUtil.updateCalendarBookingInstance(
				_user.getUserId(), calendarBooking.getCalendarBookingId(), 2,
				calendar.getCalendarId(), instanceTitleMap,
				calendarBooking.getDescriptionMap(),
				calendarBooking.getLocation(), instanceStartTime,
				instanceStartTime + 36000000, false, null, false, 0, null, 0,
				null, serviceContext);

		Assert.assertEquals(
			instanceTitleMap, calendarBookingInstance.getTitleMap());

		Map<Locale, String> newTitleMap =
			RandomTestUtil.randomLocaleStringMap();

		CalendarBookingLocalServiceUtil.updateRecurringCalendarBooking(
			_user.getUserId(), calendarBooking.getCalendarBookingId(),
			calendar.getCalendarId(), new long[0], newTitleMap,
			calendarBooking.getDescriptionMap(), calendarBooking.getLocation(),
			startTime, startTime + 36000000, false, 0, null, 0, null,
			serviceContext);

		calendarBookingInstance =
			CalendarBookingLocalServiceUtil.fetchCalendarBooking(
				calendarBookingInstance.getCalendarBookingId());

		Assert.assertEquals(newTitleMap, calendarBookingInstance.getTitleMap());
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

	private Object _checkBookingMessageListener;

	@DeleteAfterTestRun
	private User _invitingUser;

	@DeleteAfterTestRun
	private User _user;

}
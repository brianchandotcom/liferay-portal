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
import com.liferay.calendar.notification.NotificationType;
import com.liferay.calendar.recurrence.Recurrence;
import com.liferay.calendar.recurrence.RecurrenceSerializer;
import com.liferay.calendar.service.CalendarBookingLocalService;
import com.liferay.calendar.service.CalendarBookingLocalServiceUtil;
import com.liferay.calendar.test.util.CalendarBookingTestUtil;
import com.liferay.calendar.test.util.CalendarTestUtil;
import com.liferay.calendar.test.util.RecurrenceTestUtil;
import com.liferay.calendar.util.JCalendarUtil;
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
import com.liferay.portal.kernel.util.TimeZoneUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.SynchronousMailTestRule;
import com.liferay.portal.util.test.MailServiceTestUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

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
	public void testAddAllDayCalendarBooking() throws Exception {
		ServiceContext serviceContext = createServiceContext();

		Calendar calendar = CalendarTestUtil.addCalendar(
			_user, _losAngelesTimeZone, serviceContext);

		java.util.Calendar nowJCalendar = JCalendarUtil.getJCalendar(
			2017, java.util.Calendar.JANUARY, 5, 22, 0, 0, 0, _utcTimeZone);

		CalendarBooking calendarBooking =
			CalendarBookingTestUtil.addAllDayCalendarBooking(
				_user, calendar, nowJCalendar.getTimeInMillis(),
				nowJCalendar.getTimeInMillis(), serviceContext);

		java.util.Calendar startTimeJCalendar = JCalendarUtil.getJCalendar(
			calendarBooking.getStartTime(), calendarBooking.getTimeZone());

		java.util.Calendar endTimeJCalendar = JCalendarUtil.getJCalendar(
			calendarBooking.getEndTime(), calendarBooking.getTimeZone());

		assertSameDay(nowJCalendar, startTimeJCalendar);

		assertSameDay(nowJCalendar, endTimeJCalendar);

		assertEqualsTime(0, 0, startTimeJCalendar);

		assertEqualsTime(23, 59, endTimeJCalendar);
	}

	@Test
	public void testAddCalendarBooking() throws Exception {
		ServiceContext serviceContext = createServiceContext();

		Calendar calendar = CalendarTestUtil.addCalendar(_user, serviceContext);

		long startTime = System.currentTimeMillis();

		serviceContext.setLanguageId("fr_FR");

		CalendarBooking calendarBooking =
			CalendarBookingTestUtil.addRegularCalendarBooking(
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

		Calendar calendar = CalendarTestUtil.addCalendar(
			_invitingUser, serviceContext);

		Calendar invitedCalendar = CalendarTestUtil.addCalendar(
			_user, serviceContext);

		long startTime = System.currentTimeMillis() + Time.MINUTE;

		long endTime = startTime + Time.HOUR;

		long firstReminder = Time.MINUTE;

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		CalendarBooking calendarBooking =
			CalendarBookingTestUtil.addCalendarBooking(
				_invitingUser, calendar,
				new long[] {invitedCalendar.getCalendarId()},
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomLocaleStringMap(), startTime, endTime,
				null, (int)firstReminder, NotificationType.EMAIL, 0,
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

		Assert.assertEquals(mailMessages.toString(), 2, mailMessages.size());
	}

	@Test
	public void testAddCalendarBookingResourceRequested()
		throws PortalException {

		Calendar calendar = CalendarTestUtil.addCalendar(_user);

		Calendar resourceCalendar =
			CalendarTestUtil.addCalendarResourceCalendar(_user);

		CalendarBooking firstChildCalendarBooking =
			CalendarBookingTestUtil.addChildCalendarBooking(
				calendar, resourceCalendar);

		Assert.assertTrue(firstChildCalendarBooking.isPending());

		CalendarBooking secondChildCalendarBooking =
			CalendarBookingTestUtil.addChildCalendarBooking(
				calendar, resourceCalendar);

		Assert.assertTrue(secondChildCalendarBooking.isDenied());
	}

	@Test
	public void testAddCalendarBookingResourceRequestedEndOverlapsStart()
		throws PortalException {

		Calendar calendar = CalendarTestUtil.addCalendar(_user);

		Calendar resourceCalendar =
			CalendarTestUtil.addCalendarResourceCalendar(_user);

		CalendarBooking firstChildCalendarBooking =
			CalendarBookingTestUtil.addChildCalendarBooking(
				calendar, resourceCalendar);

		Assert.assertTrue(firstChildCalendarBooking.isPending());

		long startTime = firstChildCalendarBooking.getEndTime();

		long endTime = startTime + Time.HOUR;

		CalendarBooking secondChildCalendarBooking =
			CalendarBookingTestUtil.addChildCalendarBooking(
				calendar, resourceCalendar, startTime, endTime);

		Assert.assertTrue(secondChildCalendarBooking.isPending());
	}

	@Test
	public void testAddCalendarBookingResourceRequestedNotifiesDenial()
		throws Exception {

		Calendar calendar = CalendarTestUtil.addCalendar(_user);

		Calendar resourceCalendar =
			CalendarTestUtil.addCalendarResourceCalendar(_user);

		CalendarBooking firstChildCalendarBooking =
			CalendarBookingTestUtil.addChildCalendarBooking(
				calendar, resourceCalendar);

		CalendarBookingTestUtil.addChildCalendarBooking(
			calendar, resourceCalendar,
			firstChildCalendarBooking.getStartTime(),
			firstChildCalendarBooking.getEndTime());

		String messageBodySnippet = "has declined this invitation";

		Assert.assertTrue(
			MailServiceTestUtil.lastMailMessageContains(messageBodySnippet));
	}

	@Test
	public void testAddCalendarBookingResourceRequestedNotifiesInvitees()
		throws Exception {

		_invitingUser = UserTestUtil.addUser();

		Calendar invintingCalendar = CalendarTestUtil.addCalendar(
			_invitingUser);

		Calendar invitedCalendar = CalendarTestUtil.addCalendar(_user);

		_resourceUser = UserTestUtil.addUser();

		Calendar resourceCalendar =
			CalendarTestUtil.addCalendarResourceCalendar(_resourceUser);

		CalendarBooking firstChildCalendarBooking =
			CalendarBookingTestUtil.addChildCalendarBooking(
				invintingCalendar, resourceCalendar);

		long[] childCalendarIds = new long[] {
			invitedCalendar.getCalendarId(), resourceCalendar.getCalendarId()
		};

		CalendarBookingTestUtil.addMasterCalendarBooking(
			_user, invintingCalendar, childCalendarIds,
			firstChildCalendarBooking.getStartTime(),
			firstChildCalendarBooking.getEndTime(), createServiceContext());

		assertSentEmail(_user.getEmailAddress());

		assertSentEmail(_invitingUser.getEmailAddress());
	}

	@Test
	public void testAddCalendarBookingResourceRequestedOverlappingStart()
		throws PortalException {

		Calendar calendar = CalendarTestUtil.addCalendar(_user);

		Calendar resourceCalendar =
			CalendarTestUtil.addCalendarResourceCalendar(_user);

		CalendarBooking firstChildCalendarBooking =
			CalendarBookingTestUtil.addChildCalendarBooking(
				calendar, resourceCalendar);

		Assert.assertTrue(firstChildCalendarBooking.isPending());

		long endTime =
			firstChildCalendarBooking.getEndTime() +
				firstChildCalendarBooking.getDuration() / 2;

		long startTime =
			firstChildCalendarBooking.getStartTime() +
				firstChildCalendarBooking.getDuration() / 2;

		CalendarBooking secondChildCalendarBooking =
			CalendarBookingTestUtil.addChildCalendarBooking(
				calendar, resourceCalendar, startTime, endTime);

		Assert.assertTrue(secondChildCalendarBooking.isDenied());
	}

	@Test
	public void testAddCalendarBookingResourceRequestedStartOverlapsEnd()
		throws PortalException {

		Calendar calendar = CalendarTestUtil.addCalendar(_user);

		Calendar resourceCalendar =
			CalendarTestUtil.addCalendarResourceCalendar(_user);

		CalendarBooking firstChildCalendarBooking =
			CalendarBookingTestUtil.addChildCalendarBooking(
				calendar, resourceCalendar);

		Assert.assertTrue(firstChildCalendarBooking.isPending());

		long endTime = firstChildCalendarBooking.getStartTime();

		long startTime = endTime - Time.HOUR;

		CalendarBooking secondChildCalendarBooking =
			CalendarBookingTestUtil.addChildCalendarBooking(
				calendar, resourceCalendar, startTime, endTime);

		Assert.assertTrue(secondChildCalendarBooking.isPending());
	}

	@Test
	public void testAddCalendarBookingResourceReserved()
		throws PortalException {

		Calendar resourceCalendar =
			CalendarTestUtil.addCalendarResourceCalendar(_user);

		CalendarBooking firstCalendarBooking =
			CalendarBookingTestUtil.addRegularCalendarBooking(resourceCalendar);

		Assert.assertTrue(firstCalendarBooking.isApproved());

		Calendar calendar = CalendarTestUtil.addCalendar(_user);

		CalendarBooking secondChildCalendarBooking =
			CalendarBookingTestUtil.addChildCalendarBooking(
				calendar, resourceCalendar, firstCalendarBooking.getStartTime(),
				firstCalendarBooking.getEndTime());

		Assert.assertTrue(secondChildCalendarBooking.isDenied());
	}

	@Test
	public void testAddCalendarBookingResourceReservedEndOverlapsStart()
		throws PortalException {

		Calendar resourceCalendar =
			CalendarTestUtil.addCalendarResourceCalendar(_user);

		CalendarBooking firstCalendarBooking =
			CalendarBookingTestUtil.addRegularCalendarBooking(resourceCalendar);

		Assert.assertTrue(firstCalendarBooking.isApproved());

		long endTime = firstCalendarBooking.getStartTime();

		long startTime = endTime - Time.HOUR;

		Calendar calendar = CalendarTestUtil.addCalendar(_user);

		CalendarBooking secondChildCalendarBooking =
			CalendarBookingTestUtil.addChildCalendarBooking(
				calendar, resourceCalendar, startTime, endTime);

		Assert.assertTrue(secondChildCalendarBooking.isPending());
	}

	@Test
	public void testAddCalendarBookingResourceReservedOverlappingEnd()
		throws PortalException {

		Calendar resourceCalendar =
			CalendarTestUtil.addCalendarResourceCalendar(_user);

		CalendarBooking firstCalendarBooking =
			CalendarBookingTestUtil.addRegularCalendarBooking(resourceCalendar);

		Assert.assertTrue(firstCalendarBooking.isApproved());

		long endTime = firstCalendarBooking.getEndTime() - Time.HOUR / 2;

		long startTime = firstCalendarBooking.getStartTime() - Time.HOUR / 2;

		Calendar calendar = CalendarTestUtil.addCalendar(_user);

		CalendarBooking secondChildCalendarBooking =
			CalendarBookingTestUtil.addChildCalendarBooking(
				calendar, resourceCalendar, startTime, endTime);

		Assert.assertTrue(secondChildCalendarBooking.isDenied());
	}

	@Test
	public void testAddCalendarBookingResourceReservedOverlappingStart()
		throws PortalException {

		Calendar resourceCalendar =
			CalendarTestUtil.addCalendarResourceCalendar(_user);

		CalendarBooking firstCalendarBooking =
			CalendarBookingTestUtil.addRegularCalendarBooking(resourceCalendar);

		Assert.assertTrue(firstCalendarBooking.isApproved());

		long endTime = firstCalendarBooking.getEndTime() + Time.HOUR / 2;

		long startTime = firstCalendarBooking.getStartTime() + Time.HOUR / 2;

		Calendar calendar = CalendarTestUtil.addCalendar(_user);

		CalendarBooking secondChildCalendarBooking =
			CalendarBookingTestUtil.addChildCalendarBooking(
				calendar, resourceCalendar, startTime, endTime);

		Assert.assertTrue(secondChildCalendarBooking.isDenied());
	}

	@Test
	public void testAddCalendarBookingResourceReservedStartOverlapsEnd()
		throws PortalException {

		Calendar resourceCalendar =
			CalendarTestUtil.addCalendarResourceCalendar(_user);

		CalendarBooking firstCalendarBooking =
			CalendarBookingTestUtil.addRegularCalendarBooking(resourceCalendar);

		Assert.assertTrue(firstCalendarBooking.isApproved());

		long startTime = firstCalendarBooking.getEndTime();

		long endTime = startTime + Time.HOUR;

		Calendar calendar = CalendarTestUtil.addCalendar(_user);

		CalendarBooking secondChildCalendarBooking =
			CalendarBookingTestUtil.addChildCalendarBooking(
				calendar, resourceCalendar, startTime, endTime);

		Assert.assertTrue(secondChildCalendarBooking.isPending());
	}

	@Test
	public void testAddCalendarBookingResourseRequestedOverlappingEnd()
		throws PortalException {

		Calendar calendar = CalendarTestUtil.addCalendar(_user);

		Calendar resourceCalendar =
			CalendarTestUtil.addCalendarResourceCalendar(_user);

		CalendarBooking firstChildCalendarBooking =
			CalendarBookingTestUtil.addChildCalendarBooking(
				calendar, resourceCalendar);

		Assert.assertTrue(firstChildCalendarBooking.isPending());

		long endTime =
			firstChildCalendarBooking.getEndTime() -
				firstChildCalendarBooking.getDuration() / 2;

		long startTime =
			firstChildCalendarBooking.getStartTime() -
				firstChildCalendarBooking.getDuration() / 2;

		CalendarBooking secondChildCalendarBooking =
			CalendarBookingTestUtil.addChildCalendarBooking(
				calendar, resourceCalendar, startTime, endTime);

		Assert.assertTrue(secondChildCalendarBooking.isDenied());
	}

	@Test
	public void testAddRecurringCalendarBookingUntilStartTime()
		throws Exception {

		ServiceContext serviceContext = createServiceContext();

		Calendar calendar = CalendarTestUtil.addCalendar(
			_user, _losAngelesTimeZone, serviceContext);

		java.util.Calendar startTimeJCalendar = CalendarFactoryUtil.getCalendar(
			2017, java.util.Calendar.JANUARY, 1, 20, 0, 0, 0,
			_losAngelesTimeZone);

		java.util.Calendar untilJCalendar =
			(java.util.Calendar)startTimeJCalendar.clone();

		Recurrence recurrence = RecurrenceTestUtil.getDailyRecurrence(
			_losAngelesTimeZone, untilJCalendar);

		long startTime = startTimeJCalendar.getTimeInMillis();

		CalendarBooking calendarBooking =
			CalendarBookingTestUtil.addRecurringCalendarBooking(
				_user, calendar, startTime, startTime + (Time.HOUR * 10),
				recurrence, serviceContext);

		long calendarBookingId = calendarBooking.getCalendarBookingId();

		assertCalendarBookingInstancesCount(calendarBookingId, 1);
	}

	@Test
	public void testDeleteCalendarBooking() throws Exception {
		ServiceContext serviceContext = createServiceContext();

		CalendarBooking calendarBooking =
			CalendarBookingTestUtil.addDailyRecurringCalendarBooking(
				_user, serviceContext);

		Map<Locale, String> titleMap = RandomTestUtil.randomLocaleStringMap();

		CalendarBooking calendarBookingInstance =
			CalendarBookingTestUtil.updateCalendarBookingInstance(
				calendarBooking, 2, titleMap, serviceContext);

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

		CalendarBooking calendarBooking =
			CalendarBookingTestUtil.addDailyRecurringCalendarBooking(
				_user, serviceContext);

		Map<Locale, String> titleMap = RandomTestUtil.randomLocaleStringMap();

		CalendarBooking calendarBookingInstance =
			CalendarBookingTestUtil.updateCalendarBookingInstance(
				calendarBooking, 2, titleMap, serviceContext);

		CalendarBookingLocalServiceUtil.deleteCalendarBookingInstance(
			calendarBooking, 1, true, true);

		calendarBookingInstance =
			CalendarBookingLocalServiceUtil.fetchCalendarBooking(
				calendarBookingInstance.getCalendarBookingId());

		Assert.assertNull(calendarBookingInstance);

		assertCalendarBookingInstancesCount(
			calendarBooking.getCalendarBookingId(), 1);
	}

	@Test
	public void testDeleteCalendarBookingWithAllRecurringInstances()
		throws Exception {

		ServiceContext serviceContext = createServiceContext();

		CalendarBooking calendarBooking =
			CalendarBookingTestUtil.addDailyRecurringCalendarBooking(
				_user, serviceContext);

		Map<Locale, String> titleMap = RandomTestUtil.randomLocaleStringMap();

		CalendarBooking calendarBookingInstance =
			CalendarBookingTestUtil.updateCalendarBookingInstance(
				calendarBooking, 2, titleMap, serviceContext);

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

		CalendarBooking calendarBooking =
			CalendarBookingTestUtil.addDailyRecurringCalendarBooking(
				_user, serviceContext);

		Map<Locale, String> titleMap = RandomTestUtil.randomLocaleStringMap();

		CalendarBooking calendarBookingInstance =
			CalendarBookingTestUtil.updateCalendarBookingInstance(
				calendarBooking, 2, titleMap, serviceContext);

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

		CalendarBooking calendarBooking =
			CalendarBookingTestUtil.addRecurringCalendarBooking(
				_user, RecurrenceTestUtil.getDailyRecurrence(2),
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

		CalendarBooking calendarBooking =
			CalendarBookingTestUtil.addDailyRecurringCalendarBooking(
				_user, serviceContext);

		Map<Locale, String> titleMap = RandomTestUtil.randomLocaleStringMap();

		CalendarBookingTestUtil.updateCalendarBookingInstance(
			calendarBooking, 2, titleMap, serviceContext);

		CalendarBookingTestUtil.updateCalendarBookingInstance(
			calendarBooking, 4, titleMap, serviceContext);

		List<CalendarBooking> instances =
			CalendarBookingLocalServiceUtil.getRecurringCalendarBookings(
				calendarBooking);

		Assert.assertEquals(instances.toString(), 3, instances.size());

		for (CalendarBooking instance : instances) {
			if (instance.getCalendarBookingId() ==
					calendarBooking.getCalendarBookingId()) {

				continue;
			}

			Assert.assertEquals(titleMap, instance.getTitleMap());
		}
	}

	@Test
	public void testGetRecurringCalendarBookingsSkipPastEvents()
		throws Exception {

		ServiceContext serviceContext = createServiceContext();

		CalendarBooking calendarBooking =
			CalendarBookingTestUtil.addDailyRecurringCalendarBooking(
				_user, serviceContext);

		Map<Locale, String> titleMap = RandomTestUtil.randomLocaleStringMap();

		CalendarBooking firstCalendarBookingInstance =
			CalendarBookingTestUtil.updateCalendarBookingInstance(
				calendarBooking, 2, titleMap, serviceContext);

		CalendarBooking secondCalendarBookingInstance =
			CalendarBookingTestUtil.updateCalendarBookingInstance(
				calendarBooking, 4, titleMap, serviceContext);

		List<CalendarBooking> instances =
			CalendarBookingLocalServiceUtil.getRecurringCalendarBookings(
				calendarBooking,
				firstCalendarBookingInstance.getStartTime() + 1);

		Assert.assertEquals(instances.toString(), 1, instances.size());

		CalendarBooking instance = instances.get(0);

		Assert.assertEquals(
			secondCalendarBookingInstance.getStartTime(),
			instance.getStartTime());
	}

	@Test
	public void testInviteToDraftCalendarBookingResultsInMasterPendingChild()
		throws Exception {

		ServiceContext serviceContext = createServiceContext();

		Calendar calendar = CalendarTestUtil.addCalendar(_user, serviceContext);

		Calendar invitedCalendar = CalendarTestUtil.addCalendar(
			calendar.getCalendarResource(), serviceContext);

		long startTime = System.currentTimeMillis();

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		CalendarBooking calendarBooking =
			CalendarBookingTestUtil.addMasterCalendarBooking(
				_user, calendar, new long[] {invitedCalendar.getCalendarId()},
				startTime, startTime + (Time.HOUR * 10), serviceContext);

		CalendarBooking childCalendarBooking = getChildCalendarBooking(
			calendarBooking);

		Assert.assertEquals(
			CalendarBookingWorkflowConstants.STATUS_MASTER_PENDING,
			childCalendarBooking.getStatus());
	}

	@Test
	public void testInviteToPublishedCalendarBookingNotifiesInvitee()
		throws Exception {

		_invitingUser = UserTestUtil.addUser();

		Calendar invitingcalendar = CalendarTestUtil.addCalendar(_invitingUser);

		Calendar invitedCalendar = CalendarTestUtil.addCalendar(_user);

		CalendarBooking calendarBooking =
			CalendarBookingTestUtil.addMasterCalendarBookingWithWorkflow(
				invitingcalendar, invitedCalendar,
				WorkflowConstants.ACTION_PUBLISH);

		String mailMessageSubject =
			"Calendar: Event Notification for " + StringPool.QUOTE +
				calendarBooking.getTitle(LocaleUtil.getDefault()) +
					StringPool.QUOTE;

		List<com.dumbster.smtp.MailMessage> mailMessages =
			MailServiceTestUtil.getMailMessages("Subject", mailMessageSubject);

		Assert.assertEquals(mailMessages.toString(), 1, mailMessages.size());
	}

	@Test
	public void testInviteToPublishedCalendarBookingResultsInPendingChild()
		throws Exception {

		ServiceContext serviceContext = createServiceContext();

		Calendar calendar = CalendarTestUtil.addCalendar(_user, serviceContext);

		Calendar invitedCalendar = CalendarTestUtil.addCalendar(
			calendar.getCalendarResource(), serviceContext);

		long startTime = System.currentTimeMillis();

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		CalendarBooking calendarBooking =
			CalendarBookingTestUtil.addMasterCalendarBooking(
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

		Calendar calendar = CalendarTestUtil.addCalendar(_user, serviceContext);

		Calendar invitedCalendar = CalendarTestUtil.addCalendar(
			calendar.getCalendarResource(), serviceContext);

		long startTime = System.currentTimeMillis();

		CalendarBooking calendarBooking =
			CalendarBookingTestUtil.addMasterCalendarBooking(
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

		Calendar calendar = CalendarTestUtil.addCalendar(_user, serviceContext);

		long startTime = System.currentTimeMillis();

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		CalendarBooking calendarBooking =
			CalendarBookingTestUtil.addRegularCalendarBooking(
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

		Calendar calendar = CalendarTestUtil.addCalendar(_user, serviceContext);

		long startTime = System.currentTimeMillis();

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		CalendarBooking calendarBooking =
			CalendarBookingTestUtil.addRegularCalendarBooking(
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

		Calendar calendar = CalendarTestUtil.addCalendar(_user, serviceContext);

		Calendar invitedCalendar = CalendarTestUtil.addCalendar(
			calendar.getCalendarResource(), serviceContext);

		long startTime = System.currentTimeMillis();

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		CalendarBooking calendarBooking =
			CalendarBookingTestUtil.addMasterCalendarBooking(
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

		Calendar calendar = CalendarTestUtil.addCalendar(_user, serviceContext);

		Calendar invitedCalendar = CalendarTestUtil.addCalendar(
			calendar.getCalendarResource(), serviceContext);

		long startTime = System.currentTimeMillis();

		CalendarBooking calendarBooking =
			CalendarBookingTestUtil.addMasterCalendarBooking(
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

		Calendar calendar = CalendarTestUtil.addCalendar(_user, serviceContext);

		long startTime = System.currentTimeMillis();

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		CalendarBooking calendarBooking =
			CalendarBookingTestUtil.addRegularCalendarBooking(
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

		Calendar calendar = CalendarTestUtil.addCalendar(_user, serviceContext);

		long startTime = System.currentTimeMillis();

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		CalendarBooking calendarBooking =
			CalendarBookingTestUtil.addRegularCalendarBooking(
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

		Calendar calendar = CalendarTestUtil.addCalendar(_user, serviceContext);

		long startTime = System.currentTimeMillis();

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		CalendarBooking calendarBooking =
			CalendarBookingTestUtil.addRegularCalendarBooking(
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

		Calendar calendar = CalendarTestUtil.addCalendar(_user, serviceContext);

		long startTime = System.currentTimeMillis();

		java.util.Calendar untilJCalendar = CalendarFactoryUtil.getCalendar(
			startTime);

		untilJCalendar.add(java.util.Calendar.DAY_OF_MONTH, -2);

		Recurrence recurrence = RecurrenceTestUtil.getDailyRecurrence(
			untilJCalendar);

		CalendarBookingTestUtil.addRecurringCalendarBooking(
			_user, calendar, startTime, startTime + (Time.HOUR * 10),
			recurrence, serviceContext);
	}

	@Test
	public void testUpdateAllFollowingFromSingleInstance() throws Exception {
		ServiceContext serviceContext = createServiceContext();

		CalendarBooking calendarBooking =
			CalendarBookingTestUtil.addDailyRecurringCalendarBooking(
				_user, serviceContext);

		Map<Locale, String> earlierDescriptionMap = new HashMap<>(
			calendarBooking.getDescriptionMap());

		Map<Locale, String> laterDescriptionMap =
			RandomTestUtil.randomLocaleStringMap();

		CalendarBooking calendarBookingInstance =
			CalendarBookingTestUtil.updateCalendarBookingInstance(
				calendarBooking, 2, RandomTestUtil.randomLocaleStringMap(),
				serviceContext);

		long instanceStartTime = calendarBookingInstance.getStartTime();
		long instanceEndTime = calendarBookingInstance.getEndTime();

		CalendarBookingLocalServiceUtil.updateCalendarBookingInstance(
			_user.getUserId(), calendarBookingInstance.getCalendarBookingId(),
			0, calendarBookingInstance.getCalendarId(),
			calendarBookingInstance.getTitleMap(), laterDescriptionMap,
			calendarBookingInstance.getLocation(), instanceStartTime,
			instanceEndTime, false, null, true, 0, null, 0, null,
			serviceContext);

		List<CalendarBooking> recurringCalendarBookings =
			CalendarBookingLocalServiceUtil.getRecurringCalendarBookings(
				calendarBooking);

		List<CalendarBooking> earlierCalendarBookings = new ArrayList<>();
		List<CalendarBooking> laterCalendarBookings = new ArrayList<>();

		partitionCalendarBookingListByStartTime(
			recurringCalendarBookings, instanceStartTime,
			earlierCalendarBookings, laterCalendarBookings);

		Assert.assertEquals(
			earlierCalendarBookings.toString(), 1,
			earlierCalendarBookings.size());
		Assert.assertEquals(
			laterCalendarBookings.toString(), 2, laterCalendarBookings.size());

		for (CalendarBooking earlierCalendarBooking : earlierCalendarBookings) {
			Assert.assertEquals(
				earlierDescriptionMap,
				earlierCalendarBooking.getDescriptionMap());
		}

		for (CalendarBooking laterCalendarBooking : laterCalendarBookings) {
			Assert.assertEquals(
				laterDescriptionMap, laterCalendarBooking.getDescriptionMap());
		}
	}

	@Test
	public void testUpdateAllFollowingFromSingleInstancePreservesDayTime()
		throws Exception {

		ServiceContext serviceContext = createServiceContext();

		CalendarBooking calendarBooking =
			CalendarBookingTestUtil.addDailyRecurringCalendarBooking(
				_user, serviceContext);

		Map<Locale, String> titleMap = RandomTestUtil.randomLocaleStringMap();

		long instanceStartTime = calendarBooking.getStartTime() + Time.DAY * 2;

		long instanceEndTime = instanceStartTime + (Time.HOUR * 10);

		CalendarBooking calendarBookingInstance =
			CalendarBookingTestUtil.updateCalendarBookingInstance(
				_user, calendarBooking, 2, titleMap,
				calendarBooking.getDescriptionMap(), instanceStartTime,
				instanceEndTime, serviceContext);

		Map<Locale, String> laterDescriptionMap =
			RandomTestUtil.randomLocaleStringMap();

		calendarBookingInstance =
			CalendarBookingTestUtil.
				updateCalendarBookingInstanceAndAllFollowing(
					_user, calendarBookingInstance, 0, titleMap,
					laterDescriptionMap, instanceStartTime, instanceEndTime,
					serviceContext);

		List<CalendarBooking> recurringCalendarBookings =
			CalendarBookingLocalServiceUtil.getRecurringCalendarBookings(
				calendarBooking, calendarBooking.getEndTime());

		Assert.assertEquals(
			recurringCalendarBookings.toString(), 2,
			recurringCalendarBookings.size());

		java.util.Calendar expectedEndTimeJCalendar =
			JCalendarUtil.getJCalendar(
				instanceEndTime, calendarBookingInstance.getTimeZone());

		java.util.Calendar expectedStartTimeJCalendar =
			JCalendarUtil.getJCalendar(
				instanceStartTime, calendarBookingInstance.getTimeZone());

		assertCalendarBookingPeriod(
			recurringCalendarBookings.get(0), expectedStartTimeJCalendar,
			expectedEndTimeJCalendar);

		expectedEndTimeJCalendar = JCalendarUtil.getJCalendar(
			instanceEndTime, calendarBookingInstance.getTimeZone());

		expectedEndTimeJCalendar.add(java.util.Calendar.DAY_OF_MONTH, 1);

		expectedStartTimeJCalendar = JCalendarUtil.getJCalendar(
			instanceStartTime, calendarBookingInstance.getTimeZone());

		expectedStartTimeJCalendar.add(java.util.Calendar.DAY_OF_MONTH, 1);

		assertCalendarBookingPeriod(
			recurringCalendarBookings.get(1), expectedStartTimeJCalendar,
			expectedEndTimeJCalendar);
	}

	@Test
	public void testUpdateAllFollowingFromSingleInstancePreservesDayTimeAcrossDays()
		throws Exception {

		ServiceContext serviceContext = createServiceContext();

		Calendar calendar = CalendarTestUtil.addCalendar(_user, serviceContext);

		long startTime = System.currentTimeMillis();

		long endTime = startTime + Time.DAY;

		Recurrence recurrence = RecurrenceTestUtil.getDailyRecurrence();

		CalendarBooking calendarBooking =
			CalendarBookingTestUtil.addRecurringCalendarBooking(
				_user, calendar, startTime, endTime, recurrence,
				serviceContext);

		Map<Locale, String> titleMap = RandomTestUtil.randomLocaleStringMap();

		Map<Locale, String> laterDescriptionMap =
			RandomTestUtil.randomLocaleStringMap();

		CalendarBooking calendarBookingInstance =
			CalendarBookingTestUtil.updateCalendarBookingInstance(
				calendarBooking, 2, titleMap, serviceContext);

		long instanceEndTime = calendarBookingInstance.getEndTime();
		long instanceStartTime = calendarBookingInstance.getStartTime();

		calendarBookingInstance =
			CalendarBookingTestUtil.
				updateCalendarBookingInstanceAndAllFollowing(
					_user, calendarBookingInstance, 0, titleMap,
					laterDescriptionMap, instanceStartTime, instanceEndTime,
					serviceContext);

		List<CalendarBooking> recurringCalendarBookings =
			CalendarBookingLocalServiceUtil.getRecurringCalendarBookings(
				calendarBooking, calendarBooking.getEndTime());

		Assert.assertEquals(
			recurringCalendarBookings.toString(), 2,
			recurringCalendarBookings.size());

		java.util.Calendar expectedEndTimeJCalendar =
			JCalendarUtil.getJCalendar(
				instanceEndTime, calendarBookingInstance.getTimeZone());

		java.util.Calendar expectedStartTimeJCalendar =
			JCalendarUtil.getJCalendar(
				instanceStartTime, calendarBookingInstance.getTimeZone());

		assertCalendarBookingPeriod(
			recurringCalendarBookings.get(0), expectedStartTimeJCalendar,
			expectedEndTimeJCalendar);

		expectedEndTimeJCalendar = JCalendarUtil.getJCalendar(
			instanceEndTime, calendarBookingInstance.getTimeZone());

		expectedEndTimeJCalendar.add(java.util.Calendar.DAY_OF_MONTH, 1);

		expectedStartTimeJCalendar = JCalendarUtil.getJCalendar(
			instanceStartTime, calendarBookingInstance.getTimeZone());

		expectedStartTimeJCalendar.add(java.util.Calendar.DAY_OF_MONTH, 1);

		assertCalendarBookingPeriod(
			recurringCalendarBookings.get(1), expectedStartTimeJCalendar,
			expectedEndTimeJCalendar);
	}

	@Test
	public void testUpdateAllFollowingWhenRecurrenceIsInSpecificTimeZone()
		throws Exception {

		ServiceContext serviceContext = createServiceContext();

		Calendar calendar = CalendarTestUtil.addCalendar(_user, serviceContext);

		java.util.Calendar startTimeJCalendar = new GregorianCalendar(
			_losAngelesTimeZone);

		startTimeJCalendar.set(java.util.Calendar.YEAR, 2017);
		startTimeJCalendar.set(
			java.util.Calendar.MONTH, java.util.Calendar.MAY);
		startTimeJCalendar.set(
			java.util.Calendar.DAY_OF_WEEK, java.util.Calendar.SUNDAY);
		startTimeJCalendar.set(java.util.Calendar.HOUR_OF_DAY, 20);
		startTimeJCalendar.set(java.util.Calendar.MINUTE, 0);

		long startTime = startTimeJCalendar.getTimeInMillis();

		long endTime = startTime + (Time.HOUR * 1);

		Recurrence recurrence = RecurrenceTestUtil.getDailyRecurrence(
			_losAngelesTimeZone);

		recurrence.setInterval(1);

		CalendarBooking calendarBooking =
			CalendarBookingTestUtil.addRecurringCalendarBooking(
				_user, calendar, startTime, endTime, recurrence,
				serviceContext);

		int instanceIndex = 2;

		Map<Locale, String> titleMap = RandomTestUtil.randomLocaleStringMap();

		long instanceStartTime = startTime + (Time.DAY * 2) + (Time.HOUR * 1);

		long instanceEndTime = instanceStartTime + (Time.HOUR * 1);

		CalendarBookingTestUtil.updateCalendarBookingInstance(
			_user, calendarBooking, instanceIndex, titleMap,
			calendarBooking.getDescriptionMap(), instanceStartTime,
			instanceEndTime, serviceContext);

		instanceIndex = 1;

		instanceStartTime = startTime + (Time.DAY * 1) + (Time.HOUR * 1);

		instanceEndTime = instanceStartTime + (Time.HOUR * 1);

		calendarBooking = CalendarBookingLocalServiceUtil.fetchCalendarBooking(
			calendarBooking.getCalendarBookingId());

		CalendarBooking expectedCalendarBookingInstance =
			CalendarBookingTestUtil.
				updateRecurringCalendarBookingInstanceAndAllFollowing(
					_user, calendarBooking, instanceIndex,
					calendarBooking.getTitleMap(),
					calendarBooking.getDescriptionMap(), instanceStartTime,
					instanceEndTime, calendarBooking.getRecurrence(),
					serviceContext);

		java.util.Calendar expectedJCalendar = new GregorianCalendar(
			_losAngelesTimeZone);

		expectedJCalendar.setTimeInMillis(
			expectedCalendarBookingInstance.getStartTime());

		Assert.assertEquals(
			expectedJCalendar.get(java.util.Calendar.DAY_OF_WEEK),
			java.util.Calendar.MONDAY);

		Assert.assertEquals(
			expectedJCalendar.get(java.util.Calendar.HOUR_OF_DAY),
			startTimeJCalendar.get(java.util.Calendar.HOUR_OF_DAY) + 1);

		expectedJCalendar.setTimeInMillis(
			expectedCalendarBookingInstance.getEndTime());

		Assert.assertEquals(
			expectedJCalendar.get(java.util.Calendar.DAY_OF_WEEK),
			java.util.Calendar.MONDAY);

		Assert.assertEquals(
			expectedJCalendar.get(java.util.Calendar.HOUR_OF_DAY),
			startTimeJCalendar.get(java.util.Calendar.HOUR_OF_DAY) + 2);
	}

	@Test
	public void testUpdateCalendarBookingAndAllRecurringInstancesStatus()
		throws Exception {

		ServiceContext serviceContext = createServiceContext();

		CalendarBooking calendarBooking =
			CalendarBookingTestUtil.addDailyRecurringCalendarBooking(
				_user, serviceContext);

		Map<Locale, String> titleMap = RandomTestUtil.randomLocaleStringMap();

		CalendarBooking thirdCalendarBookingInstance =
			CalendarBookingTestUtil.updateCalendarBookingInstance(
				calendarBooking, 2, titleMap, serviceContext);

		CalendarBooking secondCalendarBookingInstance =
			CalendarBookingLocalServiceUtil.getCalendarBookingInstance(
				calendarBooking.getCalendarBookingId(), 1);

		CalendarBookingLocalServiceUtil.invokeTransition(
			_user.getUserId(), calendarBooking,
			secondCalendarBookingInstance.getStartTime(),
			CalendarBookingWorkflowConstants.STATUS_MAYBE, true, true,
			serviceContext);

		thirdCalendarBookingInstance =
			CalendarBookingLocalServiceUtil.fetchCalendarBooking(
				thirdCalendarBookingInstance.getCalendarBookingId());

		Assert.assertEquals(
			CalendarBookingWorkflowConstants.STATUS_MAYBE,
			thirdCalendarBookingInstance.getStatus());
	}

	@Test
	public void testUpdateCalendarBookingInstance() throws Exception {
		ServiceContext serviceContext = createServiceContext();

		CalendarBooking calendarBooking =
			CalendarBookingTestUtil.addDailyRecurringCalendarBooking(
				_user, serviceContext);

		Map<Locale, String> instanceTitleMap =
			RandomTestUtil.randomLocaleStringMap();

		CalendarBooking calendarBookingInstance =
			CalendarBookingTestUtil.updateCalendarBookingInstance(
				calendarBooking, 2, instanceTitleMap, serviceContext);

		Assert.assertEquals(
			instanceTitleMap, calendarBookingInstance.getTitleMap());

		Map<Locale, String> newTitleMap =
			RandomTestUtil.randomLocaleStringMap();

		CalendarBookingLocalServiceUtil.updateCalendarBookingInstance(
			_user.getUserId(), calendarBooking.getCalendarBookingId(), 1,
			calendarBooking.getCalendarId(), newTitleMap,
			calendarBooking.getDescriptionMap(), calendarBooking.getLocation(),
			calendarBooking.getStartTime(), calendarBooking.getEndTime(), false,
			calendarBooking.getRecurrence(), true, 0, null, 0, null,
			serviceContext);

		calendarBookingInstance =
			CalendarBookingLocalServiceUtil.fetchCalendarBooking(
				calendarBookingInstance.getCalendarBookingId());

		Assert.assertEquals(newTitleMap, calendarBookingInstance.getTitleMap());
	}

	@Test
	public void testUpdateCalendarBookingInstanceWithoutRecurrence()
		throws Exception {

		ServiceContext serviceContext = createServiceContext();

		CalendarBooking calendarBooking =
			CalendarBookingTestUtil.addDailyRecurringCalendarBooking(
				_user, serviceContext);

		Map<Locale, String> instanceTitleMap =
			RandomTestUtil.randomLocaleStringMap();

		CalendarBooking calendarBookingInstance =
			CalendarBookingTestUtil.updateCalendarBookingInstance(
				calendarBooking, 2, instanceTitleMap, serviceContext);

		Assert.assertEquals(
			instanceTitleMap, calendarBookingInstance.getTitleMap());

		Map<Locale, String> newTitleMap =
			RandomTestUtil.randomLocaleStringMap();

		CalendarBookingLocalServiceUtil.updateCalendarBookingInstance(
			_user.getUserId(), calendarBooking.getCalendarBookingId(), 1,
			calendarBooking.getCalendarId(), newTitleMap,
			calendarBooking.getDescriptionMap(), calendarBooking.getLocation(),
			calendarBooking.getStartTime(), calendarBooking.getEndTime(), false,
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

		Calendar calendar = CalendarTestUtil.addCalendar(_user, serviceContext);

		Calendar invitedCalendar = CalendarTestUtil.addCalendar(
			calendar.getCalendarResource(), serviceContext);

		long startTime = System.currentTimeMillis();

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		CalendarBooking calendarBooking =
			CalendarBookingTestUtil.addCalendarBooking(
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

		Calendar calendar = CalendarTestUtil.addCalendar(_user, serviceContext);

		Calendar invitedCalendar = CalendarTestUtil.addCalendar(
			calendar.getCalendarResource(), serviceContext);

		long startTime = System.currentTimeMillis();

		long endTime = startTime + (Time.HOUR * 10);

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		CalendarBooking calendarBooking =
			CalendarBookingTestUtil.addMasterCalendarBooking(
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

		Calendar calendar = CalendarTestUtil.addCalendar(_user, serviceContext);

		Map<Locale, String> oldDescriptionMap = new HashMap<>();

		oldDescriptionMap.put(LocaleUtil.BRAZIL, RandomTestUtil.randomString());
		oldDescriptionMap.put(
			LocaleUtil.GERMANY, RandomTestUtil.randomString());
		oldDescriptionMap.put(LocaleUtil.SPAIN, RandomTestUtil.randomString());
		oldDescriptionMap.put(LocaleUtil.US, RandomTestUtil.randomString());

		long startTime = System.currentTimeMillis();

		CalendarBooking calendarBooking =
			CalendarBookingTestUtil.
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

		Calendar calendar = CalendarTestUtil.addCalendar(_user, serviceContext);

		Map<Locale, String> oldTitleMap = new HashMap<>();

		oldTitleMap.put(LocaleUtil.BRAZIL, RandomTestUtil.randomString());
		oldTitleMap.put(LocaleUtil.GERMANY, RandomTestUtil.randomString());
		oldTitleMap.put(LocaleUtil.SPAIN, RandomTestUtil.randomString());
		oldTitleMap.put(LocaleUtil.US, RandomTestUtil.randomString());

		long startTime = System.currentTimeMillis();

		CalendarBooking calendarBooking =
			CalendarBookingTestUtil.
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

		Calendar calendar = CalendarTestUtil.addCalendar(_user, serviceContext);

		Calendar invitedCalendar = CalendarTestUtil.addCalendar(
			calendar.getCalendarResource(), serviceContext);

		long startTime = System.currentTimeMillis();

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		CalendarBooking calendarBooking =
			CalendarBookingTestUtil.addMasterCalendarBooking(
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
	public void testUpdateLastInstanceCalendarBookingRecurrence()
		throws Exception {

		ServiceContext serviceContext = createServiceContext();

		CalendarBooking calendarBooking =
			CalendarBookingTestUtil.addDailyRecurringCalendarBooking(
				_user, serviceContext);

		CalendarBooking calendarBookingInstance =
			CalendarBookingTestUtil.updateCalendarBookingInstance(
				calendarBooking, 2, RandomTestUtil.randomLocaleStringMap(),
				serviceContext);

		java.util.Calendar untilJCalendar = CalendarFactoryUtil.getCalendar(
			calendarBooking.getStartTime());

		untilJCalendar.add(java.util.Calendar.DAY_OF_MONTH, 10);

		Recurrence recurrence = RecurrenceTestUtil.getDailyRecurrence(
			untilJCalendar);

		CalendarBookingLocalServiceUtil.
			updateLastInstanceCalendarBookingRecurrence(
				calendarBookingInstance,
				RecurrenceSerializer.serialize(recurrence));

		assertDoesNotRepeat(calendarBookingInstance);

		assertRepeatsUntil(calendarBooking, untilJCalendar);
	}

	@Test
	public void testUpdateLastInstanceCalendarBookingRecurrenceFromSingleInstance()
		throws Exception {

		ServiceContext serviceContext = createServiceContext();

		CalendarBooking calendarBooking =
			CalendarBookingTestUtil.addDailyRecurringCalendarBooking(
				_user, serviceContext);

		CalendarBooking calendarBookingInstance =
			CalendarBookingTestUtil.updateCalendarBookingInstance(
				calendarBooking, 2, RandomTestUtil.randomLocaleStringMap(),
				serviceContext);

		java.util.Calendar untilJCalendar = CalendarFactoryUtil.getCalendar(
			calendarBooking.getStartTime());

		untilJCalendar.add(java.util.Calendar.DAY_OF_MONTH, 10);

		Recurrence recurrence = RecurrenceTestUtil.getDailyRecurrence(
			untilJCalendar);

		CalendarBookingLocalServiceUtil.
			updateLastInstanceCalendarBookingRecurrence(
				calendarBookingInstance,
				RecurrenceSerializer.serialize(recurrence));

		assertDoesNotRepeat(calendarBookingInstance);

		assertRepeatsUntil(calendarBooking, untilJCalendar);
	}

	@Test
	public void testUpdateLastInstanceCalendarBookingRecurrencePreservesExceptionDate()
		throws Exception {

		ServiceContext serviceContext = createServiceContext();

		CalendarBooking calendarBooking =
			CalendarBookingTestUtil.addDailyRecurringCalendarBooking(
				_user, serviceContext);

		CalendarBooking calendarBookingInstance =
			CalendarBookingTestUtil.updateCalendarBookingInstance(
				calendarBooking, 2, RandomTestUtil.randomLocaleStringMap(),
				serviceContext);

		java.util.Calendar untilJCalendar = CalendarFactoryUtil.getCalendar(
			calendarBooking.getStartTime());

		untilJCalendar.add(java.util.Calendar.DAY_OF_MONTH, 10);

		Recurrence recurrence = RecurrenceTestUtil.getDailyRecurrence(
			untilJCalendar);

		CalendarBookingLocalServiceUtil.
			updateLastInstanceCalendarBookingRecurrence(
				calendarBookingInstance,
				RecurrenceSerializer.serialize(recurrence));

		java.util.Calendar instanceStartTimeJCalendar =
			CalendarFactoryUtil.getCalendar(
				calendarBookingInstance.getStartTime(), _utcTimeZone);

		assertRepeatsExceptFor(calendarBooking, instanceStartTimeJCalendar);
	}

	@Test
	public void testUpdateRecurringCalendarBooking() throws Exception {
		ServiceContext serviceContext = createServiceContext();

		Calendar calendar = CalendarTestUtil.addCalendar(_user, serviceContext);

		long startTime = System.currentTimeMillis();

		Recurrence recurrence = RecurrenceTestUtil.getDailyRecurrence();

		CalendarBooking calendarBooking =
			CalendarBookingTestUtil.addRecurringCalendarBooking(
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

	@Test
	public void testUpdateRecurringCalendarBookingLastInstance()
		throws Exception {

		ServiceContext serviceContext = createServiceContext();

		Calendar calendar = CalendarTestUtil.addCalendar(
			_user, _losAngelesTimeZone, serviceContext);

		java.util.Calendar startTimeJCalendar = CalendarFactoryUtil.getCalendar(
			2017, java.util.Calendar.JANUARY, 1, 20, 0, 0, 0,
			_losAngelesTimeZone);

		Recurrence recurrence = RecurrenceTestUtil.getDailyRecurrence(
			3, _losAngelesTimeZone);

		long startTime = startTimeJCalendar.getTimeInMillis();

		long endTime = startTime + (Time.HOUR * 10);

		CalendarBooking calendarBooking =
			CalendarBookingTestUtil.addRecurringCalendarBooking(
				_user, calendar, startTime, endTime, recurrence,
				serviceContext);

		long calendarBookingId = calendarBooking.getCalendarBookingId();

		assertCalendarBookingInstancesCount(calendarBookingId, 3);

		recurrence = RecurrenceTestUtil.getDailyRecurrence(
			_losAngelesTimeZone, startTimeJCalendar);

		CalendarBookingLocalServiceUtil.updateCalendarBooking(
			calendarBooking.getUserId(), calendarBookingId,
			calendar.getCalendarId(), calendarBooking.getTitleMap(),
			calendarBooking.getDescriptionMap(), calendarBooking.getLocation(),
			startTime, endTime, false,
			RecurrenceSerializer.serialize(recurrence),
			calendarBooking.getFirstReminder(),
			calendarBooking.getFirstReminderType(),
			calendarBooking.getSecondReminder(),
			calendarBooking.getSecondReminderType(), serviceContext);

		assertCalendarBookingInstancesCount(calendarBookingId, 1);
	}

	protected void assertCalendarBookingInstancesCount(
			long calendarBookingId, int count)
		throws PortalException {

		CalendarBooking calendarBookingInstance = null;

		for (int i = 0; i < count; i++) {
			calendarBookingInstance =
				CalendarBookingLocalServiceUtil.getCalendarBookingInstance(
					calendarBookingId, i);

			Assert.assertNotNull(calendarBookingInstance);
		}

		calendarBookingInstance =
			CalendarBookingLocalServiceUtil.getCalendarBookingInstance(
				calendarBookingId, count);

		Assert.assertNull(calendarBookingInstance);
	}

	protected void assertCalendarBookingPeriod(
		CalendarBooking calendarBooking, java.util.Calendar startTimeJCalendar,
		java.util.Calendar endTimeJCalendar) {

		java.util.Calendar instanceStartTimeJCalendar =
			JCalendarUtil.getJCalendar(
				calendarBooking.getStartTime(), calendarBooking.getTimeZone());

		assertSameDay(startTimeJCalendar, instanceStartTimeJCalendar);

		assertSameTime(startTimeJCalendar, instanceStartTimeJCalendar);

		java.util.Calendar instanceEndTimeJCalendar =
			JCalendarUtil.getJCalendar(
				calendarBooking.getEndTime(), calendarBooking.getTimeZone());

		assertSameDay(endTimeJCalendar, instanceEndTimeJCalendar);

		assertSameTime(endTimeJCalendar, instanceEndTimeJCalendar);
	}

	protected void assertDoesNotRepeat(CalendarBooking calendarBooking) {
		calendarBooking = CalendarBookingLocalServiceUtil.fetchCalendarBooking(
			calendarBooking.getCalendarBookingId());

		Assert.assertNull(calendarBooking.getRecurrenceObj());
	}

	protected void assertEqualsTime(
		int hour, int minute, java.util.Calendar jCalendar) {

		Assert.assertEquals(
			hour, jCalendar.get(java.util.Calendar.HOUR_OF_DAY));

		Assert.assertEquals(minute, jCalendar.get(java.util.Calendar.MINUTE));
	}

	protected void assertRepeatsExceptFor(
		CalendarBooking calendarBooking,
		java.util.Calendar exceptionJCalendar) {

		calendarBooking = CalendarBookingLocalServiceUtil.fetchCalendarBooking(
			calendarBooking.getCalendarBookingId());

		Recurrence recurrence = calendarBooking.getRecurrenceObj();

		List<java.util.Calendar> exceptionJCalendars =
			recurrence.getExceptionJCalendars();

		Assert.assertEquals(
			exceptionJCalendars.toString(), 1, exceptionJCalendars.size());

		assertSameDay(exceptionJCalendar, exceptionJCalendars.get(0));
	}

	protected void assertRepeatsUntil(
		CalendarBooking calendarBooking, java.util.Calendar untilJCalendar) {

		calendarBooking = CalendarBookingLocalServiceUtil.fetchCalendarBooking(
			calendarBooking.getCalendarBookingId());

		Recurrence recurrence = calendarBooking.getRecurrenceObj();

		assertSameDay(untilJCalendar, recurrence.getUntilJCalendar());
	}

	protected void assertSameDay(
		java.util.Calendar expectedJCalendar,
		java.util.Calendar actualJCalendar) {

		Assert.assertNotNull(expectedJCalendar);
		Assert.assertNotNull(actualJCalendar);

		Assert.assertEquals(
			expectedJCalendar.get(java.util.Calendar.YEAR),
			actualJCalendar.get(java.util.Calendar.YEAR));

		Assert.assertEquals(
			expectedJCalendar.get(java.util.Calendar.MONTH),
			actualJCalendar.get(java.util.Calendar.MONTH));

		Assert.assertEquals(
			expectedJCalendar.get(java.util.Calendar.DAY_OF_MONTH),
			actualJCalendar.get(java.util.Calendar.DAY_OF_MONTH));
	}

	protected void assertSameTime(
		java.util.Calendar expectedJCalendar,
		java.util.Calendar actualJCalendar) {

		Assert.assertNotNull(expectedJCalendar);
		Assert.assertNotNull(actualJCalendar);

		Assert.assertEquals(
			expectedJCalendar.get(java.util.Calendar.HOUR),
			actualJCalendar.get(java.util.Calendar.HOUR));

		Assert.assertEquals(
			expectedJCalendar.get(java.util.Calendar.MINUTE),
			actualJCalendar.get(java.util.Calendar.MINUTE));
	}

	protected void assertSentEmail(String to) {
		List<com.dumbster.smtp.MailMessage> mailMessages =
			MailServiceTestUtil.getMailMessages("To", to);

		Assert.assertFalse(mailMessages.toString(), mailMessages.isEmpty());
	}

	protected ServiceContext createServiceContext() {
		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(_user.getCompanyId());

		serviceContext.setUserId(_user.getUserId());

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

	protected void partitionCalendarBookingListByStartTime(
		List<CalendarBooking> calendarBookings, long startTime,
		List<CalendarBooking> earlierCalendarBookings,
		List<CalendarBooking> laterCalendarBookings) {

		for (CalendarBooking calendarBooking : calendarBookings) {
			if (calendarBooking.getStartTime() >= startTime) {
				laterCalendarBookings.add(calendarBooking);
			}
			else {
				earlierCalendarBookings.add(calendarBooking);
			}
		}
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

	private static final TimeZone _losAngelesTimeZone = TimeZone.getTimeZone(
		"America/Los_Angeles");
	private static final TimeZone _utcTimeZone = TimeZoneUtil.getTimeZone(
		StringPool.UTC);

	private Object _checkBookingMessageListener;

	@DeleteAfterTestRun
	private User _invitingUser;

	@DeleteAfterTestRun
	private User _resourceUser;

	@DeleteAfterTestRun
	private User _user;

}
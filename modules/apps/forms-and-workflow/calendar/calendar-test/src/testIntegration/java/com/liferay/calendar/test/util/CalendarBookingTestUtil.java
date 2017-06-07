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

package com.liferay.calendar.test.util;

import com.liferay.calendar.model.Calendar;
import com.liferay.calendar.model.CalendarBooking;
import com.liferay.calendar.model.CalendarBookingConstants;
import com.liferay.calendar.model.CalendarResource;
import com.liferay.calendar.notification.NotificationType;
import com.liferay.calendar.recurrence.Recurrence;
import com.liferay.calendar.recurrence.RecurrenceSerializer;
import com.liferay.calendar.service.CalendarBookingLocalServiceUtil;
import com.liferay.calendar.util.CalendarResourceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowThreadLocal;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Adam Brandizzi
 */
public class CalendarBookingTestUtil {

	public static CalendarBooking addAllDayCalendarBooking(
			User user, Calendar calendar, long startTime, long endTime,
			ServiceContext serviceContext)
		throws PortalException {

		return addCalendarBooking(
			user, calendar, new long[0], RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(), startTime, endTime, true,
			null, 0, null, 0, null, serviceContext);
	}

	public static CalendarBooking addCalendarBooking(
			User user, Calendar calendar, long[] childCalendarBookingIds,
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
			long startTime, long endTime, boolean allDay, Recurrence recurrence,
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
				startTime, endTime, allDay,
				RecurrenceSerializer.serialize(recurrence), firstReminder,
				firstReminderTypeString, secondReminder,
				secondReminderTypeString, serviceContext);

		return calendarBooking;
	}

	public static CalendarBooking addCalendarBooking(
			User user, Calendar calendar, long[] childCalendarBookingIds,
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
			long startTime, long endTime, Recurrence recurrence,
			int firstReminder, NotificationType firstReminderType,
			int secondReminder, NotificationType secondReminderType,
			ServiceContext serviceContext)
		throws PortalException {

		return addCalendarBooking(
			user, calendar, childCalendarBookingIds, titleMap, descriptionMap,
			startTime, endTime, false, recurrence, firstReminder,
			firstReminderType, secondReminder, secondReminderType,
			serviceContext);
	}

	public static CalendarBooking addCalendarBookingWithAction(
			User user, int action)
		throws PortalException {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(user.getCompanyId());
		serviceContext.setWorkflowAction(action);

		CalendarResource calendarResource =
			CalendarResourceUtil.getUserCalendarResource(
				user.getUserId(), serviceContext);

		Calendar calendar = calendarResource.getDefaultCalendar();

		long startTime = System.currentTimeMillis();

		return addRegularCalendarBooking(
			user, calendar, startTime, startTime + (Time.HOUR * 10),
			serviceContext);
	}

	public static CalendarBooking addChildCalendarBooking(
			Calendar invitingCalendar, Calendar invitedCalendar)
		throws PortalException {

		User user = UserLocalServiceUtil.fetchUser(
			invitingCalendar.getUserId());

		long startTime = System.currentTimeMillis();

		long endTime = startTime + Time.HOUR;

		CalendarBooking masterCalendarBooking = addMasterCalendarBooking(
			user, invitingCalendar,
			new long[] {invitedCalendar.getCalendarId()}, startTime, endTime,
			createServiceContext(user));

		return getChildCalendarBooking(masterCalendarBooking);
	}

	public static CalendarBooking addChildCalendarBooking(
			Calendar invitingCalendar, Calendar invitedCalendar, long startTime,
			long endTime)
		throws PortalException {

		User user = UserLocalServiceUtil.fetchUser(
			invitingCalendar.getUserId());

		CalendarBooking masterCalendarBooking = addMasterCalendarBooking(
			user, invitingCalendar,
			new long[] {invitedCalendar.getCalendarId()}, startTime, endTime,
			createServiceContext(user));

		return getChildCalendarBooking(masterCalendarBooking);
	}

	public static CalendarBooking addDailyRecurringCalendarBooking(
			User user, ServiceContext serviceContext)
		throws PortalException {

		Calendar calendar = CalendarTestUtil.addCalendar(user, serviceContext);

		Recurrence recurrence = RecurrenceTestUtil.getDailyRecurrence();

		long startTime = System.currentTimeMillis();

		return addRecurringCalendarBooking(
			user, calendar, startTime, startTime + (Time.HOUR * 10), recurrence,
			serviceContext);
	}

	public static CalendarBooking addDraftCalendarBooking(User user)
		throws PortalException {

		return addCalendarBookingWithAction(
			user, WorkflowConstants.ACTION_SAVE_DRAFT);
	}

	public static CalendarBooking addMasterCalendarBooking(
			User user, Calendar calendar, long[] childCalendarBookingIds,
			long startTime, long endTime, ServiceContext serviceContext)
		throws PortalException {

		return addCalendarBooking(
			user, calendar, childCalendarBookingIds,
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(), startTime, endTime, null, 0,
			null, 0, null, serviceContext);
	}

	public static CalendarBooking addMasterCalendarBooking(
			User user, Calendar calendar, long[] childCalendarIds,
			ServiceContext serviceContext)
		throws PortalException {

		long startTime = System.currentTimeMillis();

		long endTime = startTime + Time.HOUR * 10;

		return addMasterCalendarBooking(
			user, calendar, childCalendarIds, startTime, endTime,
			serviceContext);
	}

	public static CalendarBooking addMasterCalendarBookingWithWorkflow(
			Calendar invitingCalendar, Calendar invitedCalendar,
			int actionPublish)
		throws PortalException {

		long startTime = System.currentTimeMillis();

		User user = UserLocalServiceUtil.fetchUser(invitedCalendar.getUserId());

		ServiceContext serviceContext = createServiceContext(user);

		serviceContext.setWorkflowAction(actionPublish);

		CalendarBooking calendarBooking = addMasterCalendarBooking(
			user, invitingCalendar,
			new long[] {invitedCalendar.getCalendarId()}, startTime,
			startTime + (Time.HOUR * 10), serviceContext);

		return calendarBooking;
	}

	public static CalendarBooking addPublishedCalendarBooking(User user)
		throws PortalException {

		return addCalendarBookingWithAction(
			user, WorkflowConstants.ACTION_PUBLISH);
	}

	public static CalendarBooking addRecurringCalendarBooking(
			User user, Calendar calendar, long startTime, long endTime,
			Recurrence recurrence, ServiceContext serviceContext)
		throws PortalException {

		return addCalendarBooking(
			user, calendar, new long[0], RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(), startTime, endTime,
			recurrence, 0, null, 0, null, serviceContext);
	}

	public static CalendarBooking addRecurringCalendarBooking(
			User user, Calendar calendar, Recurrence recurrence,
			ServiceContext serviceContext)
		throws PortalException {

		long startTime = System.currentTimeMillis();

		long endTime = startTime + Time.HOUR;

		return addCalendarBooking(
			user, calendar, new long[0], RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(), startTime, endTime,
			recurrence, 0, null, 0, null, serviceContext);
	}

	public static CalendarBooking addRecurringCalendarBooking(
			User user, Recurrence recurrence, ServiceContext serviceContext)
		throws PortalException {

		Calendar calendar = CalendarTestUtil.addCalendar(user, serviceContext);

		long startTime = System.currentTimeMillis();

		return addRecurringCalendarBooking(
			user, calendar, startTime, startTime + (Time.HOUR * 10), recurrence,
			serviceContext);
	}

	public static CalendarBooking addRegularCalendarBooking(Calendar calendar)
		throws PortalException {

		User user = UserLocalServiceUtil.fetchUser(calendar.getUserId());

		return addRegularCalendarBooking(
			user, calendar, createServiceContext(user));
	}

	public static CalendarBooking addRegularCalendarBooking(
			User user, Calendar calendar, long startTime, long endTime,
			ServiceContext serviceContext)
		throws PortalException {

		return addCalendarBooking(
			user, calendar, new long[0], RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(), startTime, endTime, null, 0,
			null, 0, null, serviceContext);
	}

	public static CalendarBooking addRegularCalendarBooking(
			User user, Calendar calendar, ServiceContext serviceContext)
		throws PortalException {

		long startTime = System.currentTimeMillis();

		long endTime = startTime + Time.HOUR;

		return addCalendarBooking(
			user, calendar, new long[0], RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(), startTime, endTime, null, 0,
			null, 0, null, serviceContext);
	}

	public static CalendarBooking
			addRegularCalendarBookingWithTitleAndDescription(
				User user, Calendar calendar, Map<Locale, String> titleMap,
				Map<Locale, String> descriptionMap, long startTime,
				long endTime, ServiceContext serviceContext)
		throws PortalException {

		return addCalendarBooking(
			user, calendar, new long[0], titleMap, descriptionMap, startTime,
			endTime, null, 0, null, 0, null, serviceContext);
	}

	public static CalendarBooking addRegularCalendarBookingWithWorkflow(
			User user, Calendar calendar, ServiceContext serviceContext)
		throws PortalException {

		boolean workflowEnabled = WorkflowThreadLocal.isEnabled();

		try {
			WorkflowThreadLocal.setEnabled(true);

			serviceContext = (ServiceContext)serviceContext.clone();

			serviceContext.setWorkflowAction(
				WorkflowConstants.ACTION_SAVE_DRAFT);

			return addRegularCalendarBooking(user, calendar, serviceContext);
		}
		finally {
			WorkflowThreadLocal.setEnabled(workflowEnabled);
		}
	}

	public static ServiceContext createServiceContext(User user) {
		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(user.getCompanyId());

		serviceContext.setUserId(user.getUserId());

		return serviceContext;
	}

	public static CalendarBooking getChildCalendarBooking(
		CalendarBooking calendarBooking) {

		List<CalendarBooking> childCalendarBookings =
			calendarBooking.getChildCalendarBookings();

		CalendarBooking childCalendarBooking = childCalendarBookings.get(0);

		if (childCalendarBooking.isMasterBooking()) {
			childCalendarBooking = childCalendarBookings.get(1);
		}

		return childCalendarBooking;
	}

	public static CalendarBooking updateCalendarBookingInstance(
			CalendarBooking calendarBooking, int instanceIndex,
			Map<Locale, String> titleMap, ServiceContext serviceContext)
		throws PortalException {

		long endTime = calendarBooking.getEndTime() + Time.DAY * instanceIndex;

		long startTime =
			calendarBooking.getStartTime() + Time.DAY * instanceIndex;

		return CalendarBookingLocalServiceUtil.updateCalendarBookingInstance(
			calendarBooking.getUserId(), calendarBooking.getCalendarBookingId(),
			instanceIndex, calendarBooking.getCalendarId(), titleMap,
			calendarBooking.getDescriptionMap(), calendarBooking.getLocation(),
			startTime, endTime, false, null, false, 0, null, 0, null,
			serviceContext);
	}

	public static CalendarBooking updateCalendarBookingInstance(
			User user, CalendarBooking calendarBooking, int instanceIndex,
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
			long instanceStartTime, long instanceEndTime,
			ServiceContext serviceContext)
		throws PortalException {

		return CalendarBookingLocalServiceUtil.updateCalendarBookingInstance(
			user.getUserId(), calendarBooking.getCalendarBookingId(),
			instanceIndex, calendarBooking.getCalendarId(), titleMap,
			descriptionMap, calendarBooking.getLocation(), instanceStartTime,
			instanceEndTime, false, null, false, 0, null, 0, null,
			serviceContext);
	}

	public static CalendarBooking updateCalendarBookingInstanceAndAllFollowing(
			CalendarBooking calendarBooking, int instanceIndex,
			Map<Locale, String> titleMap, ServiceContext serviceContext)
		throws PortalException {

		long endTime = calendarBooking.getEndTime() + Time.DAY * instanceIndex;

		long startTime =
			calendarBooking.getStartTime() + Time.DAY * instanceIndex;

		return CalendarBookingLocalServiceUtil.updateCalendarBookingInstance(
			calendarBooking.getUserId(), calendarBooking.getCalendarBookingId(),
			instanceIndex, calendarBooking.getCalendarId(), titleMap,
			calendarBooking.getDescriptionMap(), calendarBooking.getLocation(),
			startTime, endTime, false, null, true, 0, null, 0, null,
			serviceContext);
	}

	public static CalendarBooking updateCalendarBookingInstanceAndAllFollowing(
			User user, CalendarBooking calendarBooking, int instanceIndex,
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
			long instanceStartTime, long instanceEndTime,
			ServiceContext serviceContext)
		throws PortalException {

		return CalendarBookingLocalServiceUtil.updateCalendarBookingInstance(
			user.getUserId(), calendarBooking.getCalendarBookingId(),
			instanceIndex, calendarBooking.getCalendarId(), titleMap,
			descriptionMap, calendarBooking.getLocation(), instanceStartTime,
			instanceEndTime, false, null, true, 0, null, 0, null,
			serviceContext);
	}

	public static CalendarBooking
			updateRecurringCalendarBookingInstanceAndAllFollowing(
				User user, CalendarBooking calendarBooking, int instanceIndex,
				Map<Locale, String> titleMap,
				Map<Locale, String> descriptionMap, long instanceStartTime,
				long instanceEndTime, String recurrence,
				ServiceContext serviceContext)
		throws PortalException {

		return CalendarBookingLocalServiceUtil.updateCalendarBookingInstance(
			user.getUserId(), calendarBooking.getCalendarBookingId(),
			instanceIndex, calendarBooking.getCalendarId(), titleMap,
			descriptionMap, calendarBooking.getLocation(), instanceStartTime,
			instanceEndTime, false, recurrence, true, 0, null, 0, null,
			serviceContext);
	}

}
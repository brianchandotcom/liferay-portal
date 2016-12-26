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
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Locale;
import java.util.Map;

/**
 * @author Adam Brandizzi
 */
public class CalendarBookingTestUtil {

	public static CalendarBooking addCalendarBooking(
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

	public static CalendarBooking addRegularCalendarBooking(
			User user, Calendar calendar, long startTime, long endTime,
			ServiceContext serviceContext)
		throws PortalException {

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

}
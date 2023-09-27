/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.calendar.service;

import com.liferay.calendar.model.CalendarBooking;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;
import java.util.Map;

/**
 * Provides the remote service utility for CalendarBooking. This utility wraps
 * <code>com.liferay.calendar.service.impl.CalendarBookingServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Eduardo Lundgren
 * @see CalendarBookingService
 * @generated
 */
public class CalendarBookingServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.calendar.service.impl.CalendarBookingServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static CalendarBooking addCalendarBooking(
			long calendarId, long[] childCalendarIds,
			long parentCalendarBookingId, long recurringCalendarBookingId,
			Map<java.util.Locale, String> titleMap,
			Map<java.util.Locale, String> descriptionMap, String location,
			int startTimeYear, int startTimeMonth, int startTimeDay,
			int startTimeHour, int startTimeMinute, int endTimeYear,
			int endTimeMonth, int endTimeDay, int endTimeHour,
			int endTimeMinute, String timeZoneId, boolean allDay,
			String recurrence, long firstReminder, String firstReminderType,
			long secondReminder, String secondReminderType,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addCalendarBooking(
			calendarId, childCalendarIds, parentCalendarBookingId,
			recurringCalendarBookingId, titleMap, descriptionMap, location,
			startTimeYear, startTimeMonth, startTimeDay, startTimeHour,
			startTimeMinute, endTimeYear, endTimeMonth, endTimeDay, endTimeHour,
			endTimeMinute, timeZoneId, allDay, recurrence, firstReminder,
			firstReminderType, secondReminder, secondReminderType,
			serviceContext);
	}

	public static CalendarBooking addCalendarBooking(
			long calendarId, long[] childCalendarIds,
			long parentCalendarBookingId, long recurringCalendarBookingId,
			Map<java.util.Locale, String> titleMap,
			Map<java.util.Locale, String> descriptionMap, String location,
			long startTime, long endTime, boolean allDay, String recurrence,
			long firstReminder, String firstReminderType, long secondReminder,
			String secondReminderType,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addCalendarBooking(
			calendarId, childCalendarIds, parentCalendarBookingId,
			recurringCalendarBookingId, titleMap, descriptionMap, location,
			startTime, endTime, allDay, recurrence, firstReminder,
			firstReminderType, secondReminder, secondReminderType,
			serviceContext);
	}

	public static CalendarBooking deleteCalendarBooking(long calendarBookingId)
		throws PortalException {

		return getService().deleteCalendarBooking(calendarBookingId);
	}

	public static void deleteCalendarBookingInstance(
			long calendarBookingId, int instanceIndex, boolean allFollowing)
		throws PortalException {

		getService().deleteCalendarBookingInstance(
			calendarBookingId, instanceIndex, allFollowing);
	}

	public static void deleteCalendarBookingInstance(
			long calendarBookingId, int instanceIndex, boolean allFollowing,
			boolean deleteRecurringCalendarBookings)
		throws PortalException {

		getService().deleteCalendarBookingInstance(
			calendarBookingId, instanceIndex, allFollowing,
			deleteRecurringCalendarBookings);
	}

	public static void deleteCalendarBookingInstance(
			long calendarBookingId, long startTime, boolean allFollowing)
		throws PortalException {

		getService().deleteCalendarBookingInstance(
			calendarBookingId, startTime, allFollowing);
	}

	public static String exportCalendarBooking(
			long calendarBookingId, String type)
		throws Exception {

		return getService().exportCalendarBooking(calendarBookingId, type);
	}

	public static CalendarBooking fetchCalendarBooking(long calendarBookingId)
		throws PortalException {

		return getService().fetchCalendarBooking(calendarBookingId);
	}

	public static CalendarBooking getCalendarBooking(long calendarBookingId)
		throws PortalException {

		return getService().getCalendarBooking(calendarBookingId);
	}

	public static CalendarBooking getCalendarBooking(
			long calendarId, long parentCalendarBookingId)
		throws PortalException {

		return getService().getCalendarBooking(
			calendarId, parentCalendarBookingId);
	}

	public static CalendarBooking getCalendarBookingInstance(
			long calendarBookingId, int instanceIndex)
		throws PortalException {

		return getService().getCalendarBookingInstance(
			calendarBookingId, instanceIndex);
	}

	public static List<CalendarBooking> getCalendarBookings(
			long calendarId, int[] statuses)
		throws PortalException {

		return getService().getCalendarBookings(calendarId, statuses);
	}

	public static List<CalendarBooking> getCalendarBookings(
			long calendarId, long startTime, long endTime)
		throws PortalException {

		return getService().getCalendarBookings(calendarId, startTime, endTime);
	}

	public static List<CalendarBooking> getCalendarBookings(
			long calendarId, long startTime, long endTime, int max)
		throws PortalException {

		return getService().getCalendarBookings(
			calendarId, startTime, endTime, max);
	}

	public static String getCalendarBookingsRSS(
			long calendarId, long startTime, long endTime, int max, String type,
			double version, String displayStyle,
			com.liferay.portal.kernel.theme.ThemeDisplay themeDisplay)
		throws PortalException {

		return getService().getCalendarBookingsRSS(
			calendarId, startTime, endTime, max, type, version, displayStyle,
			themeDisplay);
	}

	public static List<CalendarBooking> getChildCalendarBookings(
			long parentCalendarBookingId)
		throws PortalException {

		return getService().getChildCalendarBookings(parentCalendarBookingId);
	}

	public static List<CalendarBooking> getChildCalendarBookings(
			long parentCalendarBookingId,
			boolean includeStagingCalendarBookings)
		throws PortalException {

		return getService().getChildCalendarBookings(
			parentCalendarBookingId, includeStagingCalendarBookings);
	}

	public static List<CalendarBooking> getChildCalendarBookings(
			long parentCalendarBookingId, int status)
		throws PortalException {

		return getService().getChildCalendarBookings(
			parentCalendarBookingId, status);
	}

	public static CalendarBooking getLastInstanceCalendarBooking(
			long calendarBookingId)
		throws PortalException {

		return getService().getLastInstanceCalendarBooking(calendarBookingId);
	}

	public static CalendarBooking getNewStartTimeAndDurationCalendarBooking(
			long calendarBookingId, long offset, long duration)
		throws PortalException {

		return getService().getNewStartTimeAndDurationCalendarBooking(
			calendarBookingId, offset, duration);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static boolean hasChildCalendarBookings(
		long parentCalendarBookingId) {

		return getService().hasChildCalendarBookings(parentCalendarBookingId);
	}

	public static CalendarBooking invokeTransition(
			long calendarBookingId, int instanceIndex, int status,
			boolean updateInstance, boolean allFollowing,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().invokeTransition(
			calendarBookingId, instanceIndex, status, updateInstance,
			allFollowing, serviceContext);
	}

	public static CalendarBooking invokeTransition(
			long calendarBookingId, long startTime, int status,
			boolean updateInstance, boolean allFollowing,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().invokeTransition(
			calendarBookingId, startTime, status, updateInstance, allFollowing,
			serviceContext);
	}

	public static CalendarBooking moveCalendarBookingToTrash(
			long calendarBookingId)
		throws PortalException {

		return getService().moveCalendarBookingToTrash(calendarBookingId);
	}

	public static CalendarBooking restoreCalendarBookingFromTrash(
			long calendarBookingId)
		throws PortalException {

		return getService().restoreCalendarBookingFromTrash(calendarBookingId);
	}

	public static List<CalendarBooking> search(
			long companyId, long[] groupIds, long[] calendarIds,
			long[] calendarResourceIds, long parentCalendarBookingId,
			String keywords, long startTime, long endTime,
			java.util.TimeZone displayTimeZone, boolean recurring,
			int[] statuses, int start, int end,
			OrderByComparator<CalendarBooking> orderByComparator)
		throws PortalException {

		return getService().search(
			companyId, groupIds, calendarIds, calendarResourceIds,
			parentCalendarBookingId, keywords, startTime, endTime,
			displayTimeZone, recurring, statuses, start, end,
			orderByComparator);
	}

	public static List<CalendarBooking> search(
			long companyId, long[] groupIds, long[] calendarIds,
			long[] calendarResourceIds, long parentCalendarBookingId,
			String title, String description, String location, long startTime,
			long endTime, boolean recurring, int[] statuses,
			boolean andOperator, int start, int end,
			OrderByComparator<CalendarBooking> orderByComparator)
		throws PortalException {

		return getService().search(
			companyId, groupIds, calendarIds, calendarResourceIds,
			parentCalendarBookingId, title, description, location, startTime,
			endTime, recurring, statuses, andOperator, start, end,
			orderByComparator);
	}

	public static int searchCount(
			long companyId, long[] groupIds, long[] calendarIds,
			long[] calendarResourceIds, long parentCalendarBookingId,
			String keywords, long startTime, long endTime, boolean recurring,
			int[] statuses)
		throws PortalException {

		return getService().searchCount(
			companyId, groupIds, calendarIds, calendarResourceIds,
			parentCalendarBookingId, keywords, startTime, endTime, recurring,
			statuses);
	}

	public static int searchCount(
			long companyId, long[] groupIds, long[] calendarIds,
			long[] calendarResourceIds, long parentCalendarBookingId,
			String title, String description, String location, long startTime,
			long endTime, boolean recurring, int[] statuses,
			boolean andOperator)
		throws PortalException {

		return getService().searchCount(
			companyId, groupIds, calendarIds, calendarResourceIds,
			parentCalendarBookingId, title, description, location, startTime,
			endTime, recurring, statuses, andOperator);
	}

	public static CalendarBooking updateCalendarBooking(
			long calendarBookingId, long calendarId, long[] childCalendarIds,
			Map<java.util.Locale, String> titleMap,
			Map<java.util.Locale, String> descriptionMap, String location,
			long startTime, long endTime, boolean allDay, String recurrence,
			long firstReminder, String firstReminderType, long secondReminder,
			String secondReminderType,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateCalendarBooking(
			calendarBookingId, calendarId, childCalendarIds, titleMap,
			descriptionMap, location, startTime, endTime, allDay, recurrence,
			firstReminder, firstReminderType, secondReminder,
			secondReminderType, serviceContext);
	}

	public static CalendarBooking updateCalendarBooking(
			long calendarBookingId, long calendarId,
			Map<java.util.Locale, String> titleMap,
			Map<java.util.Locale, String> descriptionMap, String location,
			long startTime, long endTime, boolean allDay, String recurrence,
			long firstReminder, String firstReminderType, long secondReminder,
			String secondReminderType,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateCalendarBooking(
			calendarBookingId, calendarId, titleMap, descriptionMap, location,
			startTime, endTime, allDay, recurrence, firstReminder,
			firstReminderType, secondReminder, secondReminderType,
			serviceContext);
	}

	public static CalendarBooking updateCalendarBookingInstance(
			long calendarBookingId, int instanceIndex, long calendarId,
			long[] childCalendarIds, Map<java.util.Locale, String> titleMap,
			Map<java.util.Locale, String> descriptionMap, String location,
			long startTime, long endTime, boolean allDay, boolean allFollowing,
			long firstReminder, String firstReminderType, long secondReminder,
			String secondReminderType,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateCalendarBookingInstance(
			calendarBookingId, instanceIndex, calendarId, childCalendarIds,
			titleMap, descriptionMap, location, startTime, endTime, allDay,
			allFollowing, firstReminder, firstReminderType, secondReminder,
			secondReminderType, serviceContext);
	}

	public static CalendarBooking updateCalendarBookingInstance(
			long calendarBookingId, int instanceIndex, long calendarId,
			long[] childCalendarIds, Map<java.util.Locale, String> titleMap,
			Map<java.util.Locale, String> descriptionMap, String location,
			long startTime, long endTime, boolean allDay, String recurrence,
			boolean allFollowing, long firstReminder, String firstReminderType,
			long secondReminder, String secondReminderType,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateCalendarBookingInstance(
			calendarBookingId, instanceIndex, calendarId, childCalendarIds,
			titleMap, descriptionMap, location, startTime, endTime, allDay,
			recurrence, allFollowing, firstReminder, firstReminderType,
			secondReminder, secondReminderType, serviceContext);
	}

	public static CalendarBooking updateCalendarBookingInstance(
			long calendarBookingId, int instanceIndex, long calendarId,
			Map<java.util.Locale, String> titleMap,
			Map<java.util.Locale, String> descriptionMap, String location,
			int startTimeYear, int startTimeMonth, int startTimeDay,
			int startTimeHour, int startTimeMinute, int endTimeYear,
			int endTimeMonth, int endTimeDay, int endTimeHour,
			int endTimeMinute, String timeZoneId, boolean allDay,
			String recurrence, boolean allFollowing, long firstReminder,
			String firstReminderType, long secondReminder,
			String secondReminderType,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateCalendarBookingInstance(
			calendarBookingId, instanceIndex, calendarId, titleMap,
			descriptionMap, location, startTimeYear, startTimeMonth,
			startTimeDay, startTimeHour, startTimeMinute, endTimeYear,
			endTimeMonth, endTimeDay, endTimeHour, endTimeMinute, timeZoneId,
			allDay, recurrence, allFollowing, firstReminder, firstReminderType,
			secondReminder, secondReminderType, serviceContext);
	}

	public static CalendarBooking updateCalendarBookingInstance(
			long calendarBookingId, int instanceIndex, long calendarId,
			Map<java.util.Locale, String> titleMap,
			Map<java.util.Locale, String> descriptionMap, String location,
			long startTime, long endTime, boolean allDay, String recurrence,
			boolean allFollowing, long firstReminder, String firstReminderType,
			long secondReminder, String secondReminderType,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateCalendarBookingInstance(
			calendarBookingId, instanceIndex, calendarId, titleMap,
			descriptionMap, location, startTime, endTime, allDay, recurrence,
			allFollowing, firstReminder, firstReminderType, secondReminder,
			secondReminderType, serviceContext);
	}

	public static void updateLastInstanceCalendarBookingRecurrence(
			long calendarBookingId, String recurrence)
		throws PortalException {

		getService().updateLastInstanceCalendarBookingRecurrence(
			calendarBookingId, recurrence);
	}

	public static CalendarBooking updateOffsetAndDuration(
			long calendarBookingId, long calendarId, long[] childCalendarIds,
			Map<java.util.Locale, String> titleMap,
			Map<java.util.Locale, String> descriptionMap, String location,
			long offset, long duration, boolean allDay, String recurrence,
			long firstReminder, String firstReminderType, long secondReminder,
			String secondReminderType,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateOffsetAndDuration(
			calendarBookingId, calendarId, childCalendarIds, titleMap,
			descriptionMap, location, offset, duration, allDay, recurrence,
			firstReminder, firstReminderType, secondReminder,
			secondReminderType, serviceContext);
	}

	public static CalendarBooking updateOffsetAndDuration(
			long calendarBookingId, long calendarId,
			Map<java.util.Locale, String> titleMap,
			Map<java.util.Locale, String> descriptionMap, String location,
			long offset, long duration, boolean allDay, String recurrence,
			long firstReminder, String firstReminderType, long secondReminder,
			String secondReminderType,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateOffsetAndDuration(
			calendarBookingId, calendarId, titleMap, descriptionMap, location,
			offset, duration, allDay, recurrence, firstReminder,
			firstReminderType, secondReminder, secondReminderType,
			serviceContext);
	}

	public static CalendarBooking updateRecurringCalendarBooking(
			long calendarBookingId, long calendarId, long[] childCalendarIds,
			Map<java.util.Locale, String> titleMap,
			Map<java.util.Locale, String> descriptionMap, String location,
			long startTime, long endTime, boolean allDay, long firstReminder,
			String firstReminderType, long secondReminder,
			String secondReminderType,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateRecurringCalendarBooking(
			calendarBookingId, calendarId, childCalendarIds, titleMap,
			descriptionMap, location, startTime, endTime, allDay, firstReminder,
			firstReminderType, secondReminder, secondReminderType,
			serviceContext);
	}

	public static CalendarBookingService getService() {
		return _service;
	}

	public static void setService(CalendarBookingService service) {
		_service = service;
	}

	private static volatile CalendarBookingService _service;

}
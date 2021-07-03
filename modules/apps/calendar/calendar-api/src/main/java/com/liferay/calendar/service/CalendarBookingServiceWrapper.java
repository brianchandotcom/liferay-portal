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

package com.liferay.calendar.service;

import com.liferay.calendar.model.CalendarBooking;
import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CalendarBookingService}.
 *
 * @author Eduardo Lundgren
 * @see CalendarBookingService
 * @generated
 */
public class CalendarBookingServiceWrapper
	implements CalendarBookingService, ServiceWrapper<CalendarBookingService> {

	public CalendarBookingServiceWrapper(
		CalendarBookingService calendarBookingService) {

		_calendarBookingService = calendarBookingService;
	}

	@Override
	public CalendarBooking addCalendarBooking(
			long calendarId, long[] childCalendarIds,
			long parentCalendarBookingId, long recurringCalendarBookingId,
			java.util.Map<java.util.Locale, String> titleMap,
			java.util.Map<java.util.Locale, String> descriptionMap,
			String location, int startTimeYear, int startTimeMonth,
			int startTimeDay, int startTimeHour, int startTimeMinute,
			int endTimeYear, int endTimeMonth, int endTimeDay, int endTimeHour,
			int endTimeMinute, String timeZoneId, boolean allDay,
			String recurrence, long firstReminder, String firstReminderType,
			long secondReminder, String secondReminderType,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _calendarBookingService.addCalendarBooking(
			calendarId, childCalendarIds, parentCalendarBookingId,
			recurringCalendarBookingId, titleMap, descriptionMap, location,
			startTimeYear, startTimeMonth, startTimeDay, startTimeHour,
			startTimeMinute, endTimeYear, endTimeMonth, endTimeDay, endTimeHour,
			endTimeMinute, timeZoneId, allDay, recurrence, firstReminder,
			firstReminderType, secondReminder, secondReminderType,
			serviceContext);
	}

	@Override
	public CalendarBooking addCalendarBooking(
			long calendarId, long[] childCalendarIds,
			long parentCalendarBookingId, long recurringCalendarBookingId,
			java.util.Map<java.util.Locale, String> titleMap,
			java.util.Map<java.util.Locale, String> descriptionMap,
			String location, long startTime, long endTime, boolean allDay,
			String recurrence, long firstReminder, String firstReminderType,
			long secondReminder, String secondReminderType,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _calendarBookingService.addCalendarBooking(
			calendarId, childCalendarIds, parentCalendarBookingId,
			recurringCalendarBookingId, titleMap, descriptionMap, location,
			startTime, endTime, allDay, recurrence, firstReminder,
			firstReminderType, secondReminder, secondReminderType,
			serviceContext);
	}

	@Override
	public CalendarBooking deleteCalendarBooking(long calendarBookingId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _calendarBookingService.deleteCalendarBooking(calendarBookingId);
	}

	@Override
	public void deleteCalendarBookingInstance(
			long calendarBookingId, int instanceIndex, boolean allFollowing)
		throws com.liferay.portal.kernel.exception.PortalException {

		_calendarBookingService.deleteCalendarBookingInstance(
			calendarBookingId, instanceIndex, allFollowing);
	}

	@Override
	public void deleteCalendarBookingInstance(
			long calendarBookingId, int instanceIndex, boolean allFollowing,
			boolean deleteRecurringCalendarBookings)
		throws com.liferay.portal.kernel.exception.PortalException {

		_calendarBookingService.deleteCalendarBookingInstance(
			calendarBookingId, instanceIndex, allFollowing,
			deleteRecurringCalendarBookings);
	}

	@Override
	public void deleteCalendarBookingInstance(
			long calendarBookingId, long startTime, boolean allFollowing)
		throws com.liferay.portal.kernel.exception.PortalException {

		_calendarBookingService.deleteCalendarBookingInstance(
			calendarBookingId, startTime, allFollowing);
	}

	@Override
	public String exportCalendarBooking(long calendarBookingId, String type)
		throws Exception {

		return _calendarBookingService.exportCalendarBooking(
			calendarBookingId, type);
	}

	@Override
	public CalendarBooking fetchCalendarBooking(long calendarBookingId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _calendarBookingService.fetchCalendarBooking(calendarBookingId);
	}

	@Override
	public CalendarBooking getCalendarBooking(long calendarBookingId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _calendarBookingService.getCalendarBooking(calendarBookingId);
	}

	@Override
	public CalendarBooking getCalendarBooking(
			long calendarId, long parentCalendarBookingId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _calendarBookingService.getCalendarBooking(
			calendarId, parentCalendarBookingId);
	}

	@Override
	public CalendarBooking getCalendarBookingInstance(
			long calendarBookingId, int instanceIndex)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _calendarBookingService.getCalendarBookingInstance(
			calendarBookingId, instanceIndex);
	}

	@Override
	public java.util.List<CalendarBooking> getCalendarBookings(
			long calendarId, int[] statuses)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _calendarBookingService.getCalendarBookings(
			calendarId, statuses);
	}

	@Override
	public java.util.List<CalendarBooking> getCalendarBookings(
			long calendarId, long startTime, long endTime)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _calendarBookingService.getCalendarBookings(
			calendarId, startTime, endTime);
	}

	@Override
	public java.util.List<CalendarBooking> getCalendarBookings(
			long calendarId, long startTime, long endTime, int max)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _calendarBookingService.getCalendarBookings(
			calendarId, startTime, endTime, max);
	}

	@Override
	public String getCalendarBookingsRSS(
			long calendarId, long startTime, long endTime, int max, String type,
			double version, String displayStyle,
			com.liferay.portal.kernel.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _calendarBookingService.getCalendarBookingsRSS(
			calendarId, startTime, endTime, max, type, version, displayStyle,
			themeDisplay);
	}

	@Override
	public java.util.List<CalendarBooking> getChildCalendarBookings(
			long parentCalendarBookingId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _calendarBookingService.getChildCalendarBookings(
			parentCalendarBookingId);
	}

	@Override
	public java.util.List<CalendarBooking> getChildCalendarBookings(
			long parentCalendarBookingId,
			boolean includeStagingCalendarBookings)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _calendarBookingService.getChildCalendarBookings(
			parentCalendarBookingId, includeStagingCalendarBookings);
	}

	@Override
	public java.util.List<CalendarBooking> getChildCalendarBookings(
			long parentCalendarBookingId, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _calendarBookingService.getChildCalendarBookings(
			parentCalendarBookingId, status);
	}

	@Override
	public CalendarBooking getLastInstanceCalendarBooking(
			long calendarBookingId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _calendarBookingService.getLastInstanceCalendarBooking(
			calendarBookingId);
	}

	@Override
	public CalendarBooking getNewStartTimeAndDurationCalendarBooking(
			long calendarBookingId, long offset, long duration)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _calendarBookingService.
			getNewStartTimeAndDurationCalendarBooking(
				calendarBookingId, offset, duration);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _calendarBookingService.getOSGiServiceIdentifier();
	}

	@Override
	public boolean hasChildCalendarBookings(long parentCalendarBookingId) {
		return _calendarBookingService.hasChildCalendarBookings(
			parentCalendarBookingId);
	}

	@Override
	public CalendarBooking invokeTransition(
			long calendarBookingId, int instanceIndex, int status,
			boolean updateInstance, boolean allFollowing,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _calendarBookingService.invokeTransition(
			calendarBookingId, instanceIndex, status, updateInstance,
			allFollowing, serviceContext);
	}

	@Override
	public CalendarBooking invokeTransition(
			long calendarBookingId, long startTime, int status,
			boolean updateInstance, boolean allFollowing,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _calendarBookingService.invokeTransition(
			calendarBookingId, startTime, status, updateInstance, allFollowing,
			serviceContext);
	}

	@Override
	public CalendarBooking moveCalendarBookingToTrash(long calendarBookingId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _calendarBookingService.moveCalendarBookingToTrash(
			calendarBookingId);
	}

	@Override
	public CalendarBooking restoreCalendarBookingFromTrash(
			long calendarBookingId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _calendarBookingService.restoreCalendarBookingFromTrash(
			calendarBookingId);
	}

	@Override
	public java.util.List<CalendarBooking> search(
			long companyId, long[] groupIds, long[] calendarIds,
			long[] calendarResourceIds, long parentCalendarBookingId,
			String keywords, long startTime, long endTime, boolean recurring,
			int[] statuses, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator<CalendarBooking>
				orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _calendarBookingService.search(
			companyId, groupIds, calendarIds, calendarResourceIds,
			parentCalendarBookingId, keywords, startTime, endTime, recurring,
			statuses, start, end, orderByComparator);
	}

	@Override
	public java.util.List<CalendarBooking> search(
			long companyId, long[] groupIds, long[] calendarIds,
			long[] calendarResourceIds, long parentCalendarBookingId,
			String title, String description, String location, long startTime,
			long endTime, boolean recurring, int[] statuses,
			boolean andOperator, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator<CalendarBooking>
				orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _calendarBookingService.search(
			companyId, groupIds, calendarIds, calendarResourceIds,
			parentCalendarBookingId, title, description, location, startTime,
			endTime, recurring, statuses, andOperator, start, end,
			orderByComparator);
	}

	@Override
	public int searchCount(
			long companyId, long[] groupIds, long[] calendarIds,
			long[] calendarResourceIds, long parentCalendarBookingId,
			String keywords, long startTime, long endTime, boolean recurring,
			int[] statuses)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _calendarBookingService.searchCount(
			companyId, groupIds, calendarIds, calendarResourceIds,
			parentCalendarBookingId, keywords, startTime, endTime, recurring,
			statuses);
	}

	@Override
	public int searchCount(
			long companyId, long[] groupIds, long[] calendarIds,
			long[] calendarResourceIds, long parentCalendarBookingId,
			String title, String description, String location, long startTime,
			long endTime, boolean recurring, int[] statuses,
			boolean andOperator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _calendarBookingService.searchCount(
			companyId, groupIds, calendarIds, calendarResourceIds,
			parentCalendarBookingId, title, description, location, startTime,
			endTime, recurring, statuses, andOperator);
	}

	@Override
	public CalendarBooking updateCalendarBooking(
			long calendarBookingId, long calendarId, long[] childCalendarIds,
			java.util.Map<java.util.Locale, String> titleMap,
			java.util.Map<java.util.Locale, String> descriptionMap,
			String location, long startTime, long endTime, boolean allDay,
			String recurrence, long firstReminder, String firstReminderType,
			long secondReminder, String secondReminderType,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _calendarBookingService.updateCalendarBooking(
			calendarBookingId, calendarId, childCalendarIds, titleMap,
			descriptionMap, location, startTime, endTime, allDay, recurrence,
			firstReminder, firstReminderType, secondReminder,
			secondReminderType, serviceContext);
	}

	@Override
	public CalendarBooking updateCalendarBooking(
			long calendarBookingId, long calendarId,
			java.util.Map<java.util.Locale, String> titleMap,
			java.util.Map<java.util.Locale, String> descriptionMap,
			String location, long startTime, long endTime, boolean allDay,
			String recurrence, long firstReminder, String firstReminderType,
			long secondReminder, String secondReminderType,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _calendarBookingService.updateCalendarBooking(
			calendarBookingId, calendarId, titleMap, descriptionMap, location,
			startTime, endTime, allDay, recurrence, firstReminder,
			firstReminderType, secondReminder, secondReminderType,
			serviceContext);
	}

	@Override
	public CalendarBooking updateCalendarBookingInstance(
			long calendarBookingId, int instanceIndex, long calendarId,
			long[] childCalendarIds,
			java.util.Map<java.util.Locale, String> titleMap,
			java.util.Map<java.util.Locale, String> descriptionMap,
			String location, long startTime, long endTime, boolean allDay,
			boolean allFollowing, long firstReminder, String firstReminderType,
			long secondReminder, String secondReminderType,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _calendarBookingService.updateCalendarBookingInstance(
			calendarBookingId, instanceIndex, calendarId, childCalendarIds,
			titleMap, descriptionMap, location, startTime, endTime, allDay,
			allFollowing, firstReminder, firstReminderType, secondReminder,
			secondReminderType, serviceContext);
	}

	@Override
	public CalendarBooking updateCalendarBookingInstance(
			long calendarBookingId, int instanceIndex, long calendarId,
			long[] childCalendarIds,
			java.util.Map<java.util.Locale, String> titleMap,
			java.util.Map<java.util.Locale, String> descriptionMap,
			String location, long startTime, long endTime, boolean allDay,
			String recurrence, boolean allFollowing, long firstReminder,
			String firstReminderType, long secondReminder,
			String secondReminderType,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _calendarBookingService.updateCalendarBookingInstance(
			calendarBookingId, instanceIndex, calendarId, childCalendarIds,
			titleMap, descriptionMap, location, startTime, endTime, allDay,
			recurrence, allFollowing, firstReminder, firstReminderType,
			secondReminder, secondReminderType, serviceContext);
	}

	@Override
	public CalendarBooking updateCalendarBookingInstance(
			long calendarBookingId, int instanceIndex, long calendarId,
			java.util.Map<java.util.Locale, String> titleMap,
			java.util.Map<java.util.Locale, String> descriptionMap,
			String location, int startTimeYear, int startTimeMonth,
			int startTimeDay, int startTimeHour, int startTimeMinute,
			int endTimeYear, int endTimeMonth, int endTimeDay, int endTimeHour,
			int endTimeMinute, String timeZoneId, boolean allDay,
			String recurrence, boolean allFollowing, long firstReminder,
			String firstReminderType, long secondReminder,
			String secondReminderType,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _calendarBookingService.updateCalendarBookingInstance(
			calendarBookingId, instanceIndex, calendarId, titleMap,
			descriptionMap, location, startTimeYear, startTimeMonth,
			startTimeDay, startTimeHour, startTimeMinute, endTimeYear,
			endTimeMonth, endTimeDay, endTimeHour, endTimeMinute, timeZoneId,
			allDay, recurrence, allFollowing, firstReminder, firstReminderType,
			secondReminder, secondReminderType, serviceContext);
	}

	@Override
	public CalendarBooking updateCalendarBookingInstance(
			long calendarBookingId, int instanceIndex, long calendarId,
			java.util.Map<java.util.Locale, String> titleMap,
			java.util.Map<java.util.Locale, String> descriptionMap,
			String location, long startTime, long endTime, boolean allDay,
			String recurrence, boolean allFollowing, long firstReminder,
			String firstReminderType, long secondReminder,
			String secondReminderType,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _calendarBookingService.updateCalendarBookingInstance(
			calendarBookingId, instanceIndex, calendarId, titleMap,
			descriptionMap, location, startTime, endTime, allDay, recurrence,
			allFollowing, firstReminder, firstReminderType, secondReminder,
			secondReminderType, serviceContext);
	}

	@Override
	public void updateLastInstanceCalendarBookingRecurrence(
			long calendarBookingId, String recurrence)
		throws com.liferay.portal.kernel.exception.PortalException {

		_calendarBookingService.updateLastInstanceCalendarBookingRecurrence(
			calendarBookingId, recurrence);
	}

	@Override
	public CalendarBooking updateOffsetAndDuration(
			long calendarBookingId, long calendarId, long[] childCalendarIds,
			java.util.Map<java.util.Locale, String> titleMap,
			java.util.Map<java.util.Locale, String> descriptionMap,
			String location, long offset, long duration, boolean allDay,
			String recurrence, long firstReminder, String firstReminderType,
			long secondReminder, String secondReminderType,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _calendarBookingService.updateOffsetAndDuration(
			calendarBookingId, calendarId, childCalendarIds, titleMap,
			descriptionMap, location, offset, duration, allDay, recurrence,
			firstReminder, firstReminderType, secondReminder,
			secondReminderType, serviceContext);
	}

	@Override
	public CalendarBooking updateOffsetAndDuration(
			long calendarBookingId, long calendarId,
			java.util.Map<java.util.Locale, String> titleMap,
			java.util.Map<java.util.Locale, String> descriptionMap,
			String location, long offset, long duration, boolean allDay,
			String recurrence, long firstReminder, String firstReminderType,
			long secondReminder, String secondReminderType,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _calendarBookingService.updateOffsetAndDuration(
			calendarBookingId, calendarId, titleMap, descriptionMap, location,
			offset, duration, allDay, recurrence, firstReminder,
			firstReminderType, secondReminder, secondReminderType,
			serviceContext);
	}

	@Override
	public CalendarBooking updateRecurringCalendarBooking(
			long calendarBookingId, long calendarId, long[] childCalendarIds,
			java.util.Map<java.util.Locale, String> titleMap,
			java.util.Map<java.util.Locale, String> descriptionMap,
			String location, long startTime, long endTime, boolean allDay,
			long firstReminder, String firstReminderType, long secondReminder,
			String secondReminderType,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _calendarBookingService.updateRecurringCalendarBooking(
			calendarBookingId, calendarId, childCalendarIds, titleMap,
			descriptionMap, location, startTime, endTime, allDay, firstReminder,
			firstReminderType, secondReminder, secondReminderType,
			serviceContext);
	}

	@Override
	public CalendarBookingService getWrappedService() {
		return _calendarBookingService;
	}

	@Override
	public void setWrappedService(
		CalendarBookingService calendarBookingService) {

		_calendarBookingService = calendarBookingService;
	}

	private CalendarBookingService _calendarBookingService;

}
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

package com.liferay.calendar.verify;

import com.liferay.calendar.model.CalendarBooking;
import com.liferay.calendar.notification.NotificationType;
import com.liferay.calendar.service.CalendarBookingLocalService;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.verify.VerifyProcess;

import java.sql.Timestamp;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adam Brandizzi
 */
@Component(
	immediate = true,
	property = {"verify.process.name=com.liferay.calendar.service"},
	service = VerifyProcess.class
)
public class CalendarVerifyProcess extends VerifyProcess {

	protected CalendarBooking addCalendarBooking(
		String uuid, long calendarBookingId, long companyId, long groupId,
		long userId, String userName, Timestamp createDate,
		Timestamp modifiedDate, long calendarId, long calendarResourceId,
		String title, String description, String location, long startTime,
		long endTime, boolean allDay, String recurrence, int firstReminder,
		NotificationType firstReminderType, int secondReminder,
		NotificationType secondReminderType) {

		CalendarBooking calendarBooking =
			_calendarBookingLocalService.createCalendarBooking(
				calendarBookingId);

		calendarBooking.setUuid(uuid);
		calendarBooking.setCompanyId(companyId);
		calendarBooking.setGroupId(groupId);
		calendarBooking.setUserId(userId);
		calendarBooking.setUserName(userName);
		calendarBooking.setCreateDate(createDate);
		calendarBooking.setModifiedDate(modifiedDate);
		calendarBooking.setCalendarId(calendarId);
		calendarBooking.setCalendarResourceId(calendarResourceId);
		calendarBooking.setParentCalendarBookingId(calendarBookingId);
		calendarBooking.setVEventUid(uuid);
		calendarBooking.setTitle(title);
		calendarBooking.setDescription(description);
		calendarBooking.setLocation(location);
		calendarBooking.setStartTime(startTime);
		calendarBooking.setEndTime(endTime);
		calendarBooking.setAllDay(allDay);
		calendarBooking.setRecurrence(recurrence);
		calendarBooking.setFirstReminder(firstReminder);
		calendarBooking.setFirstReminderType(firstReminderType.toString());
		calendarBooking.setSecondReminder(secondReminder);
		calendarBooking.setSecondReminderType(secondReminderType.toString());
		calendarBooking.setStatus(WorkflowConstants.STATUS_APPROVED);
		calendarBooking.setStatusByUserId(userId);
		calendarBooking.setStatusByUserName(userName);
		calendarBooking.setStatusDate(createDate);

		return _calendarBookingLocalService.updateCalendarBooking(
			calendarBooking);
	}

	@Override
	protected void doVerify() throws Exception {
		verifyCalEvent();
	}

	@Reference(unbind = "-")
	protected void setCalendarBookingLocalService(
		CalendarBookingLocalService calendarBookingLocalService) {

		_calendarBookingLocalService = calendarBookingLocalService;
	}

	protected void verifyCalEvent() throws Exception {
	}

	private CalendarBookingLocalService _calendarBookingLocalService;

}
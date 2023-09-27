/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.calendar.model.impl;

import com.liferay.calendar.model.Calendar;
import com.liferay.calendar.model.CalendarBooking;
import com.liferay.calendar.model.CalendarResource;
import com.liferay.calendar.notification.NotificationType;
import com.liferay.calendar.recurrence.Recurrence;
import com.liferay.calendar.recurrence.RecurrenceSerializer;
import com.liferay.calendar.service.CalendarBookingLocalServiceUtil;
import com.liferay.calendar.service.CalendarLocalServiceUtil;
import com.liferay.calendar.service.CalendarResourceLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSON;
import com.liferay.portal.kernel.util.TimeZoneUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;
import java.util.TimeZone;

/**
 * @author Eduardo Lundgren
 */
public class CalendarBookingImpl extends CalendarBookingBaseImpl {

	@Override
	public Calendar getCalendar() throws PortalException {
		return CalendarLocalServiceUtil.getCalendar(getCalendarId());
	}

	@Override
	public CalendarResource getCalendarResource() throws PortalException {
		return CalendarResourceLocalServiceUtil.getCalendarResource(
			getCalendarResourceId());
	}

	@Override
	public List<CalendarBooking> getChildCalendarBookings() {
		return CalendarBookingLocalServiceUtil.getChildCalendarBookings(
			getCalendarBookingId());
	}

	@Override
	public long getDuration() {
		return getEndTime() - getStartTime();
	}

	@Override
	public NotificationType getFirstReminderNotificationType() {
		return NotificationType.parse(getFirstReminderType());
	}

	@JSON
	@Override
	public int getInstanceIndex() {
		return _instanceIndex;
	}

	@Override
	public CalendarBooking getParentCalendarBooking() throws PortalException {
		return CalendarBookingLocalServiceUtil.getCalendarBooking(
			getParentCalendarBookingId());
	}

	@Override
	public Recurrence getRecurrenceObj() {
		if ((_recurrenceObj == null) && Validator.isNotNull(getRecurrence())) {
			_recurrenceObj = RecurrenceSerializer.deserialize(
				getRecurrence(), getTimeZone());
		}

		return _recurrenceObj;
	}

	@Override
	public NotificationType getSecondReminderNotificationType() {
		return NotificationType.parse(getSecondReminderType());
	}

	@Override
	public TimeZone getTimeZone() {
		if (isAllDay()) {
			return TimeZoneUtil.getTimeZone(StringPool.UTC);
		}

		try {
			CalendarBooking parentCalendarBooking = getParentCalendarBooking();

			Calendar calendar = parentCalendarBooking.getCalendar();

			return calendar.getTimeZone();
		}
		catch (PortalException portalException) {
			throw new SystemException(portalException);
		}
	}

	@Override
	public boolean isMasterBooking() {
		if (getParentCalendarBookingId() == getCalendarBookingId()) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isMasterRecurringBooking() {
		if (getRecurringCalendarBookingId() == getCalendarBookingId()) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isRecurring() {
		if (Validator.isNotNull(getRecurrence()) ||
			(getCalendarBookingId() != getRecurringCalendarBookingId())) {

			return true;
		}

		return false;
	}

	@JSON
	@Override
	public void setInstanceIndex(int instanceIndex) {
		_instanceIndex = instanceIndex;
	}

	private int _instanceIndex;
	private Recurrence _recurrenceObj;

}
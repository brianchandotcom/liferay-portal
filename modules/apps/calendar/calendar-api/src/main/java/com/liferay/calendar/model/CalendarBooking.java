/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.calendar.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the CalendarBooking service. Represents a row in the &quot;CalendarBooking&quot; database table, with each column mapped to a property of this class.
 *
 * @author Eduardo Lundgren
 * @see CalendarBookingModel
 * @generated
 */
@ImplementationClassName("com.liferay.calendar.model.impl.CalendarBookingImpl")
@ProviderType
public interface CalendarBooking extends CalendarBookingModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.calendar.model.impl.CalendarBookingImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CalendarBooking, Long>
		CALENDAR_BOOKING_ID_ACCESSOR = new Accessor<CalendarBooking, Long>() {

			@Override
			public Long get(CalendarBooking calendarBooking) {
				return calendarBooking.getCalendarBookingId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<CalendarBooking> getTypeClass() {
				return CalendarBooking.class;
			}

		};

	public Calendar getCalendar()
		throws com.liferay.portal.kernel.exception.PortalException;

	public CalendarResource getCalendarResource()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.util.List<CalendarBooking> getChildCalendarBookings();

	public long getDuration();

	public com.liferay.calendar.notification.NotificationType
		getFirstReminderNotificationType();

	@com.liferay.portal.kernel.json.JSON
	public int getInstanceIndex();

	public CalendarBooking getParentCalendarBooking()
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.calendar.recurrence.Recurrence getRecurrenceObj();

	public com.liferay.calendar.notification.NotificationType
		getSecondReminderNotificationType();

	public java.util.TimeZone getTimeZone();

	public boolean isMasterBooking();

	public boolean isMasterRecurringBooking();

	public boolean isRecurring();

	@com.liferay.portal.kernel.json.JSON
	public void setInstanceIndex(int instanceIndex);

}
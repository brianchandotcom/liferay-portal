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
 * The extended model interface for the CalendarResource service. Represents a row in the &quot;CalendarResource&quot; database table, with each column mapped to a property of this class.
 *
 * @author Eduardo Lundgren
 * @see CalendarResourceModel
 * @generated
 */
@ImplementationClassName("com.liferay.calendar.model.impl.CalendarResourceImpl")
@ProviderType
public interface CalendarResource
	extends CalendarResourceModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.calendar.model.impl.CalendarResourceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CalendarResource, Long>
		CALENDAR_RESOURCE_ID_ACCESSOR = new Accessor<CalendarResource, Long>() {

			@Override
			public Long get(CalendarResource calendarResource) {
				return calendarResource.getCalendarResourceId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<CalendarResource> getTypeClass() {
				return CalendarResource.class;
			}

		};

	public java.util.List<Calendar> getCalendars();

	public Calendar getDefaultCalendar();

	public long getDefaultCalendarId();

	public java.util.TimeZone getTimeZone()
		throws com.liferay.portal.kernel.exception.PortalException;

	public String getTimeZoneId()
		throws com.liferay.portal.kernel.exception.PortalException;

	public boolean isGroup();

	public boolean isUser();

}
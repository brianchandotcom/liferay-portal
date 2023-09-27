/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.calendar.internal.security.permission;

import com.liferay.calendar.model.CalendarBooking;
import com.liferay.calendar.service.CalendarBookingLocalService;
import com.liferay.portal.kernel.security.permission.PermissionUpdateHandler;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gergely Mathe
 */
@Component(
	property = "model.class.name=com.liferay.calendar.model.CalendarBooking",
	service = PermissionUpdateHandler.class
)
public class CalendarBookingPermissionUpdateHandler
	implements PermissionUpdateHandler {

	@Override
	public void updatedPermission(String primKey) {
		CalendarBooking calendarBooking =
			_calendarBookingLocalService.fetchCalendarBooking(
				GetterUtil.getLong(primKey));

		if (calendarBooking == null) {
			return;
		}

		calendarBooking.setModifiedDate(new Date());

		_calendarBookingLocalService.updateCalendarBooking(calendarBooking);
	}

	@Reference
	private CalendarBookingLocalService _calendarBookingLocalService;

}
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

package com.liferay.calendar.service.permission;

import com.liferay.calendar.model.Calendar;
import com.liferay.calendar.service.CalendarLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.security.permission.PermissionUpdateHandler;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gergely Mathe
 */
@Component(
	property = {"model.class.name=com.liferay.calendar.model.Calendar"},
	service = PermissionUpdateHandler.class
)
public class CalendarPermissionUpdateHandler
	implements PermissionUpdateHandler {

	@Override
	public void updatedPermission(String primKey) {
		Calendar calendar = _calendarLocalService.fetchCalendar(
			GetterUtil.getLong(primKey));

		if (calendar == null) {
			return;
		}

		calendar.setModifiedDate(new Date());

		_calendarLocalService.updateCalendar(calendar);
	}

	@Reference(unbind = "-")
	protected void setCalendarSetLocalService(
		CalendarLocalService calendarLocalService) {

		_calendarLocalService = calendarLocalService;
	}

	private volatile CalendarLocalService _calendarLocalService;

}
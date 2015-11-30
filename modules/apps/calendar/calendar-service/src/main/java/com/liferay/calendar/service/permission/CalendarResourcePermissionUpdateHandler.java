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

import com.liferay.calendar.model.CalendarResource;
import com.liferay.calendar.service.CalendarResourceLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.security.permission.PermissionUpdateHandler;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gergely Mathe
 */
@Component(
	property = {"model.class.name=com.liferay.calendar.model.CalendarResource"},
	service = PermissionUpdateHandler.class
)
public class CalendarResourcePermissionUpdateHandler
	implements PermissionUpdateHandler {

	@Override
	public void updatedPermission(String primKey) {
		CalendarResource calendarResource =
			_calendarResourceLocalService.fetchCalendarResource(
				GetterUtil.getLong(primKey));

		if (calendarResource == null) {
			return;
		}

		calendarResource.setModifiedDate(new Date());

		_calendarResourceLocalService.updateCalendarResource(calendarResource);
	}

	@Reference(unbind = "-")
	protected void setCalendarResourceSetLocalService(
		CalendarResourceLocalService calendarResourceLocalService) {

		_calendarResourceLocalService = calendarResourceLocalService;
	}

	private volatile CalendarResourceLocalService _calendarResourceLocalService;

}
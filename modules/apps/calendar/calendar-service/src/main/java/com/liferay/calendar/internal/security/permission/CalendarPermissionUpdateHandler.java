/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.calendar.internal.security.permission;

import com.liferay.calendar.model.Calendar;
import com.liferay.calendar.service.CalendarLocalService;
import com.liferay.portal.kernel.security.permission.PermissionUpdateHandler;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gergely Mathe
 */
@Component(
	property = "model.class.name=com.liferay.calendar.model.Calendar",
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

	@Reference
	private CalendarLocalService _calendarLocalService;

}
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.calendar.model.impl;

import com.liferay.calendar.model.Calendar;
import com.liferay.calendar.model.CalendarResource;
import com.liferay.calendar.service.CalendarLocalServiceUtil;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.TimeZoneUtil;

import java.util.List;
import java.util.TimeZone;

/**
 * @author Eduardo Lundgren
 * @author Marcellus Tavares
 */
public class CalendarResourceImpl extends CalendarResourceBaseImpl {

	@Override
	public List<Calendar> getCalendars() {
		return CalendarLocalServiceUtil.getCalendarResourceCalendars(
			getGroupId(), getCalendarResourceId());
	}

	@Override
	public Calendar getDefaultCalendar() {
		List<Calendar> calendars =
			CalendarLocalServiceUtil.getCalendarResourceCalendars(
				getGroupId(), getCalendarResourceId(), true);

		if (!calendars.isEmpty()) {
			return calendars.get(0);
		}

		return null;
	}

	@Override
	public long getDefaultCalendarId() {
		Calendar calendar = getDefaultCalendar();

		if (calendar != null) {
			return calendar.getCalendarId();
		}

		return 0;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return new StagedModelType(
			PortalUtil.getClassNameId(CalendarResource.class.getName()));
	}

	@Override
	public TimeZone getTimeZone() throws PortalException {
		if (isUser()) {
			User user = UserLocalServiceUtil.getUser(getClassPK());

			return user.getTimeZone();
		}

		return TimeZoneUtil.getDefault();
	}

	@Override
	public String getTimeZoneId() throws PortalException {
		TimeZone timeZone = getTimeZone();

		return timeZone.getID();
	}

	@Override
	public boolean isGroup() {
		long groupClassNameId = PortalUtil.getClassNameId(Group.class);

		if (groupClassNameId == getClassNameId()) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isUser() {
		long userClassNameId = PortalUtil.getClassNameId(User.class);

		if (userClassNameId == getClassNameId()) {
			return true;
		}

		return false;
	}

}
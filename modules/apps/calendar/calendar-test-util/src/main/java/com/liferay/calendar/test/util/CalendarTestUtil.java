/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.calendar.test.util;

import com.liferay.calendar.model.Calendar;
import com.liferay.calendar.model.CalendarResource;
import com.liferay.calendar.service.CalendarLocalServiceUtil;
import com.liferay.calendar.service.CalendarResourceLocalServiceUtil;
import com.liferay.calendar.util.CalendarResourceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ClassNameLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.TimeZoneUtil;

import java.util.TimeZone;

/**
 * @author Adam Brandizzi
 */
public class CalendarTestUtil {

	public static Calendar addCalendar(
			CalendarResource calendarResource, ServiceContext serviceContext)
		throws PortalException {

		return CalendarLocalServiceUtil.addCalendar(
			calendarResource.getUserId(), calendarResource.getGroupId(),
			calendarResource.getCalendarResourceId(),
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(),
			calendarResource.getTimeZoneId(), RandomTestUtil.randomInt(), false,
			false, false, serviceContext);
	}

	public static Calendar addCalendar(Group group) throws PortalException {
		return addCalendar(
			group,
			ServiceContextTestUtil.getServiceContext(group.getGroupId()));
	}

	public static Calendar addCalendar(
			Group group, ServiceContext serviceContext)
		throws PortalException {

		return addCalendar(group, null, serviceContext);
	}

	public static Calendar addCalendar(
			Group group, TimeZone timeZone, ServiceContext serviceContext)
		throws PortalException {

		CalendarResource calendarResource =
			CalendarResourceUtil.getGroupCalendarResource(
				group.getGroupId(), serviceContext);

		if (timeZone == null) {
			timeZone = TimeZoneUtil.getDefault();
		}

		return CalendarLocalServiceUtil.addCalendar(
			group.getCreatorUserId(), group.getGroupId(),
			calendarResource.getCalendarResourceId(),
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(), timeZone.getID(),
			RandomTestUtil.randomInt(), false, false, false, serviceContext);
	}

	public static Calendar addCalendar(User user) throws PortalException {
		return addCalendar(user, null, createServiceContext(user));
	}

	public static Calendar addCalendar(User user, ServiceContext serviceContext)
		throws PortalException {

		return addCalendar(user, null, serviceContext);
	}

	public static Calendar addCalendar(
			User user, TimeZone timeZone, ServiceContext serviceContext)
		throws PortalException {

		CalendarResource calendarResource =
			CalendarResourceUtil.getUserCalendarResource(
				user.getUserId(), serviceContext);

		Calendar calendar = calendarResource.getDefaultCalendar();

		if (timeZone != null) {
			calendar.setTimeZoneId(timeZone.getID());

			calendar = CalendarLocalServiceUtil.updateCalendar(calendar);
		}

		return calendar;
	}

	public static Calendar addCalendarResourceCalendar(Group group)
		throws PortalException {

		ServiceContext createServiceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		CalendarResource calendarResource =
			CalendarResourceLocalServiceUtil.addCalendarResource(
				group.getCreatorUserId(), group.getGroupId(),
				ClassNameLocalServiceUtil.getClassNameId(
					CalendarResource.class),
				0, null, null, RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomLocaleStringMap(), true,
				createServiceContext);

		return calendarResource.getDefaultCalendar();
	}

	public static Calendar addCalendarResourceCalendar(User user)
		throws PortalException {

		CalendarResource calendarResource =
			CalendarResourceLocalServiceUtil.addCalendarResource(
				user.getUserId(), user.getGroupId(),
				ClassNameLocalServiceUtil.getClassNameId(
					CalendarResource.class),
				0, null, null, RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomLocaleStringMap(), true,
				createServiceContext(user));

		return calendarResource.getDefaultCalendar();
	}

	public static ServiceContext createServiceContext(User user) {
		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(user.getCompanyId());
		serviceContext.setUserId(user.getUserId());

		return serviceContext;
	}

	public static Calendar getDefaultCalendar(Group group)
		throws PortalException {

		CalendarResource calendarResource =
			CalendarResourceUtil.getGroupCalendarResource(
				group.getGroupId(),
				ServiceContextTestUtil.getServiceContext(group.getGroupId()));

		return calendarResource.getDefaultCalendar();
	}

}
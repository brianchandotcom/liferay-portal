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

package com.liferay.calendar.service;

import com.liferay.calendar.model.Calendar;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;
import java.util.Map;

/**
 * Provides the remote service utility for Calendar. This utility wraps
 * <code>com.liferay.calendar.service.impl.CalendarServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Eduardo Lundgren
 * @see CalendarService
 * @generated
 */
public class CalendarServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.calendar.service.impl.CalendarServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static Calendar addCalendar(
			long groupId, long calendarResourceId,
			Map<java.util.Locale, String> nameMap,
			Map<java.util.Locale, String> descriptionMap, String timeZoneId,
			int color, boolean defaultCalendar, boolean enableComments,
			boolean enableRatings,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addCalendar(
			groupId, calendarResourceId, nameMap, descriptionMap, timeZoneId,
			color, defaultCalendar, enableComments, enableRatings,
			serviceContext);
	}

	public static Calendar deleteCalendar(long calendarId)
		throws PortalException {

		return getService().deleteCalendar(calendarId);
	}

	public static String exportCalendar(long calendarId, String type)
		throws Exception {

		return getService().exportCalendar(calendarId, type);
	}

	public static Calendar fetchCalendar(long calendarId)
		throws PortalException {

		return getService().fetchCalendar(calendarId);
	}

	public static Calendar getCalendar(long calendarId) throws PortalException {
		return getService().getCalendar(calendarId);
	}

	public static List<Calendar> getCalendarResourceCalendars(
			long groupId, long calendarResourceId)
		throws PortalException {

		return getService().getCalendarResourceCalendars(
			groupId, calendarResourceId);
	}

	public static List<Calendar> getCalendarResourceCalendars(
			long groupId, long calendarResourceId, boolean defaultCalendar)
		throws PortalException {

		return getService().getCalendarResourceCalendars(
			groupId, calendarResourceId, defaultCalendar);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static void importCalendar(long calendarId, String data, String type)
		throws Exception {

		getService().importCalendar(calendarId, data, type);
	}

	public static boolean isManageableFromGroup(long calendarId, long groupId)
		throws PortalException {

		return getService().isManageableFromGroup(calendarId, groupId);
	}

	public static List<Calendar> search(
			long companyId, long[] groupIds, long[] calendarResourceIds,
			String keywords, boolean andOperator, int start, int end,
			OrderByComparator<Calendar> orderByComparator)
		throws PortalException {

		return getService().search(
			companyId, groupIds, calendarResourceIds, keywords, andOperator,
			start, end, orderByComparator);
	}

	public static List<Calendar> search(
			long companyId, long[] groupIds, long[] calendarResourceIds,
			String keywords, boolean andOperator, int start, int end,
			OrderByComparator<Calendar> orderByComparator, String actionId)
		throws PortalException {

		return getService().search(
			companyId, groupIds, calendarResourceIds, keywords, andOperator,
			start, end, orderByComparator, actionId);
	}

	public static List<Calendar> search(
			long companyId, long[] groupIds, long[] calendarResourceIds,
			String name, String description, boolean andOperator, int start,
			int end, OrderByComparator<Calendar> orderByComparator)
		throws PortalException {

		return getService().search(
			companyId, groupIds, calendarResourceIds, name, description,
			andOperator, start, end, orderByComparator);
	}

	public static List<Calendar> search(
			long companyId, long[] groupIds, long[] calendarResourceIds,
			String name, String description, boolean andOperator, int start,
			int end, OrderByComparator<Calendar> orderByComparator,
			String actionId)
		throws PortalException {

		return getService().search(
			companyId, groupIds, calendarResourceIds, name, description,
			andOperator, start, end, orderByComparator, actionId);
	}

	public static int searchCount(
			long companyId, long[] groupIds, long[] calendarResourceIds,
			String keywords, boolean andOperator)
		throws PortalException {

		return getService().searchCount(
			companyId, groupIds, calendarResourceIds, keywords, andOperator);
	}

	public static int searchCount(
			long companyId, long[] groupIds, long[] calendarResourceIds,
			String keywords, boolean andOperator, String actionId)
		throws PortalException {

		return getService().searchCount(
			companyId, groupIds, calendarResourceIds, keywords, andOperator,
			actionId);
	}

	public static int searchCount(
			long companyId, long[] groupIds, long[] calendarResourceIds,
			String name, String description, boolean andOperator)
		throws PortalException {

		return getService().searchCount(
			companyId, groupIds, calendarResourceIds, name, description,
			andOperator);
	}

	public static int searchCount(
			long companyId, long[] groupIds, long[] calendarResourceIds,
			String name, String description, boolean andOperator,
			String actionId)
		throws PortalException {

		return getService().searchCount(
			companyId, groupIds, calendarResourceIds, name, description,
			andOperator, actionId);
	}

	public static Calendar updateCalendar(
			long calendarId, Map<java.util.Locale, String> nameMap,
			Map<java.util.Locale, String> descriptionMap, int color,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateCalendar(
			calendarId, nameMap, descriptionMap, color, serviceContext);
	}

	public static Calendar updateCalendar(
			long calendarId, Map<java.util.Locale, String> nameMap,
			Map<java.util.Locale, String> descriptionMap, String timeZoneId,
			int color, boolean defaultCalendar, boolean enableComments,
			boolean enableRatings,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateCalendar(
			calendarId, nameMap, descriptionMap, timeZoneId, color,
			defaultCalendar, enableComments, enableRatings, serviceContext);
	}

	public static Calendar updateColor(
			long calendarId, int color,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateColor(calendarId, color, serviceContext);
	}

	public static CalendarService getService() {
		return _service;
	}

	public static void setService(CalendarService service) {
		_service = service;
	}

	private static volatile CalendarService _service;

}
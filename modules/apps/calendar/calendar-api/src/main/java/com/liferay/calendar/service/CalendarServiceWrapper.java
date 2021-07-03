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
import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CalendarService}.
 *
 * @author Eduardo Lundgren
 * @see CalendarService
 * @generated
 */
public class CalendarServiceWrapper
	implements CalendarService, ServiceWrapper<CalendarService> {

	public CalendarServiceWrapper(CalendarService calendarService) {
		_calendarService = calendarService;
	}

	@Override
	public Calendar addCalendar(
			long groupId, long calendarResourceId,
			java.util.Map<java.util.Locale, String> nameMap,
			java.util.Map<java.util.Locale, String> descriptionMap,
			String timeZoneId, int color, boolean defaultCalendar,
			boolean enableComments, boolean enableRatings,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _calendarService.addCalendar(
			groupId, calendarResourceId, nameMap, descriptionMap, timeZoneId,
			color, defaultCalendar, enableComments, enableRatings,
			serviceContext);
	}

	@Override
	public Calendar deleteCalendar(long calendarId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _calendarService.deleteCalendar(calendarId);
	}

	@Override
	public String exportCalendar(long calendarId, String type)
		throws Exception {

		return _calendarService.exportCalendar(calendarId, type);
	}

	@Override
	public Calendar fetchCalendar(long calendarId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _calendarService.fetchCalendar(calendarId);
	}

	@Override
	public Calendar getCalendar(long calendarId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _calendarService.getCalendar(calendarId);
	}

	@Override
	public java.util.List<Calendar> getCalendarResourceCalendars(
			long groupId, long calendarResourceId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _calendarService.getCalendarResourceCalendars(
			groupId, calendarResourceId);
	}

	@Override
	public java.util.List<Calendar> getCalendarResourceCalendars(
			long groupId, long calendarResourceId, boolean defaultCalendar)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _calendarService.getCalendarResourceCalendars(
			groupId, calendarResourceId, defaultCalendar);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _calendarService.getOSGiServiceIdentifier();
	}

	@Override
	public void importCalendar(long calendarId, String data, String type)
		throws Exception {

		_calendarService.importCalendar(calendarId, data, type);
	}

	@Override
	public boolean isManageableFromGroup(long calendarId, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _calendarService.isManageableFromGroup(calendarId, groupId);
	}

	@Override
	public java.util.List<Calendar> search(
			long companyId, long[] groupIds, long[] calendarResourceIds,
			String keywords, boolean andOperator, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator<Calendar>
				orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _calendarService.search(
			companyId, groupIds, calendarResourceIds, keywords, andOperator,
			start, end, orderByComparator);
	}

	@Override
	public java.util.List<Calendar> search(
			long companyId, long[] groupIds, long[] calendarResourceIds,
			String keywords, boolean andOperator, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator<Calendar>
				orderByComparator,
			String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _calendarService.search(
			companyId, groupIds, calendarResourceIds, keywords, andOperator,
			start, end, orderByComparator, actionId);
	}

	@Override
	public java.util.List<Calendar> search(
			long companyId, long[] groupIds, long[] calendarResourceIds,
			String name, String description, boolean andOperator, int start,
			int end,
			com.liferay.portal.kernel.util.OrderByComparator<Calendar>
				orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _calendarService.search(
			companyId, groupIds, calendarResourceIds, name, description,
			andOperator, start, end, orderByComparator);
	}

	@Override
	public java.util.List<Calendar> search(
			long companyId, long[] groupIds, long[] calendarResourceIds,
			String name, String description, boolean andOperator, int start,
			int end,
			com.liferay.portal.kernel.util.OrderByComparator<Calendar>
				orderByComparator,
			String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _calendarService.search(
			companyId, groupIds, calendarResourceIds, name, description,
			andOperator, start, end, orderByComparator, actionId);
	}

	@Override
	public int searchCount(
			long companyId, long[] groupIds, long[] calendarResourceIds,
			String keywords, boolean andOperator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _calendarService.searchCount(
			companyId, groupIds, calendarResourceIds, keywords, andOperator);
	}

	@Override
	public int searchCount(
			long companyId, long[] groupIds, long[] calendarResourceIds,
			String keywords, boolean andOperator, String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _calendarService.searchCount(
			companyId, groupIds, calendarResourceIds, keywords, andOperator,
			actionId);
	}

	@Override
	public int searchCount(
			long companyId, long[] groupIds, long[] calendarResourceIds,
			String name, String description, boolean andOperator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _calendarService.searchCount(
			companyId, groupIds, calendarResourceIds, name, description,
			andOperator);
	}

	@Override
	public int searchCount(
			long companyId, long[] groupIds, long[] calendarResourceIds,
			String name, String description, boolean andOperator,
			String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _calendarService.searchCount(
			companyId, groupIds, calendarResourceIds, name, description,
			andOperator, actionId);
	}

	@Override
	public Calendar updateCalendar(
			long calendarId, java.util.Map<java.util.Locale, String> nameMap,
			java.util.Map<java.util.Locale, String> descriptionMap, int color,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _calendarService.updateCalendar(
			calendarId, nameMap, descriptionMap, color, serviceContext);
	}

	@Override
	public Calendar updateCalendar(
			long calendarId, java.util.Map<java.util.Locale, String> nameMap,
			java.util.Map<java.util.Locale, String> descriptionMap,
			String timeZoneId, int color, boolean defaultCalendar,
			boolean enableComments, boolean enableRatings,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _calendarService.updateCalendar(
			calendarId, nameMap, descriptionMap, timeZoneId, color,
			defaultCalendar, enableComments, enableRatings, serviceContext);
	}

	@Override
	public Calendar updateColor(
			long calendarId, int color,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _calendarService.updateColor(calendarId, color, serviceContext);
	}

	@Override
	public CalendarService getWrappedService() {
		return _calendarService;
	}

	@Override
	public void setWrappedService(CalendarService calendarService) {
		_calendarService = calendarService;
	}

	private CalendarService _calendarService;

}
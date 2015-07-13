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

package com.liferay.calendar.service.http.jsonws;

import aQute.bnd.annotation.ProviderType;

import com.liferay.calendar.service.CalendarService;

import com.liferay.portal.kernel.jsonwebservice.JSONWebService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the JSONWS remote service utility for Calendar. This utility wraps
 * {@link com.liferay.calendar.service.impl.CalendarServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Eduardo Lundgren
 * @see CalendarService
 * @generated
 */
@Component(immediate = true, property =  {
	"json.web.service.context.name=calendar", "json.web.service.context.path=Calendar"}, service = CalendarJsonService.class)
@JSONWebService
@ProviderType
public class CalendarJsonService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.calendar.service.impl.CalendarServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public com.liferay.calendar.model.Calendar addCalendar(long groupId,
		long calendarResourceId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String timeZoneId, int color, boolean defaultCalendar,
		boolean enableComments, boolean enableRatings,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.addCalendar(groupId, calendarResourceId, nameMap,
			descriptionMap, timeZoneId, color, defaultCalendar, enableComments,
			enableRatings, serviceContext);
	}

	public com.liferay.calendar.model.Calendar deleteCalendar(long calendarId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.deleteCalendar(calendarId);
	}

	public java.lang.String exportCalendar(long calendarId,
		java.lang.String type) throws java.lang.Exception {
		return _service.exportCalendar(calendarId, type);
	}

	public com.liferay.calendar.model.Calendar fetchCalendar(long calendarId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.fetchCalendar(calendarId);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _service.getBeanIdentifier();
	}

	public com.liferay.calendar.model.Calendar getCalendar(long calendarId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.getCalendar(calendarId);
	}

	public java.util.List<com.liferay.calendar.model.Calendar> getCalendarResourceCalendars(
		long groupId, long calendarResourceId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.getCalendarResourceCalendars(groupId, calendarResourceId);
	}

	public java.util.List<com.liferay.calendar.model.Calendar> getCalendarResourceCalendars(
		long groupId, long calendarResourceId, boolean defaultCalendar)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.getCalendarResourceCalendars(groupId,
			calendarResourceId, defaultCalendar);
	}

	public void importCalendar(long calendarId, java.lang.String data,
		java.lang.String type) throws java.lang.Exception {
		_service.importCalendar(calendarId, data, type);
	}

	public java.util.List<com.liferay.calendar.model.Calendar> search(
		long companyId, long[] groupIds, long[] calendarResourceIds,
		java.lang.String keywords, boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.calendar.model.Calendar> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.search(companyId, groupIds, calendarResourceIds,
			keywords, andOperator, start, end, orderByComparator);
	}

	public java.util.List<com.liferay.calendar.model.Calendar> search(
		long companyId, long[] groupIds, long[] calendarResourceIds,
		java.lang.String keywords, boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.calendar.model.Calendar> orderByComparator,
		java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.search(companyId, groupIds, calendarResourceIds,
			keywords, andOperator, start, end, orderByComparator, actionId);
	}

	public java.util.List<com.liferay.calendar.model.Calendar> search(
		long companyId, long[] groupIds, long[] calendarResourceIds,
		java.lang.String name, java.lang.String description,
		boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.calendar.model.Calendar> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.search(companyId, groupIds, calendarResourceIds, name,
			description, andOperator, start, end, orderByComparator);
	}

	public java.util.List<com.liferay.calendar.model.Calendar> search(
		long companyId, long[] groupIds, long[] calendarResourceIds,
		java.lang.String name, java.lang.String description,
		boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.calendar.model.Calendar> orderByComparator,
		java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.search(companyId, groupIds, calendarResourceIds, name,
			description, andOperator, start, end, orderByComparator, actionId);
	}

	public int searchCount(long companyId, long[] groupIds,
		long[] calendarResourceIds, java.lang.String keywords,
		boolean andOperator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.searchCount(companyId, groupIds, calendarResourceIds,
			keywords, andOperator);
	}

	public int searchCount(long companyId, long[] groupIds,
		long[] calendarResourceIds, java.lang.String keywords,
		boolean andOperator, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.searchCount(companyId, groupIds, calendarResourceIds,
			keywords, andOperator, actionId);
	}

	public int searchCount(long companyId, long[] groupIds,
		long[] calendarResourceIds, java.lang.String name,
		java.lang.String description, boolean andOperator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.searchCount(companyId, groupIds, calendarResourceIds,
			name, description, andOperator);
	}

	public int searchCount(long companyId, long[] groupIds,
		long[] calendarResourceIds, java.lang.String name,
		java.lang.String description, boolean andOperator,
		java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.searchCount(companyId, groupIds, calendarResourceIds,
			name, description, andOperator, actionId);
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_service.setBeanIdentifier(beanIdentifier);
	}

	public com.liferay.calendar.model.Calendar updateCalendar(long calendarId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		int color, com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.updateCalendar(calendarId, nameMap, descriptionMap,
			color, serviceContext);
	}

	public com.liferay.calendar.model.Calendar updateCalendar(long calendarId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String timeZoneId, int color, boolean defaultCalendar,
		boolean enableComments, boolean enableRatings,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.updateCalendar(calendarId, nameMap, descriptionMap,
			timeZoneId, color, defaultCalendar, enableComments, enableRatings,
			serviceContext);
	}

	public com.liferay.calendar.model.Calendar updateColor(long calendarId,
		int color, com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.updateColor(calendarId, color, serviceContext);
	}

	@Reference
	protected void setService(CalendarService service) {
		_service = service;
	}

	private CalendarService _service;
}
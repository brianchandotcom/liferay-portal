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

import com.liferay.calendar.service.CalendarResourceService;

import com.liferay.portal.kernel.jsonwebservice.JSONWebService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the JSONWS remote service utility for CalendarResource. This utility wraps
 * {@link com.liferay.calendar.service.impl.CalendarResourceServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Eduardo Lundgren
 * @see CalendarResourceService
 * @generated
 */
@Component(immediate = true, property =  {
	"json.web.service.context.name=calendar", "json.web.service.context.path=CalendarResource"}, service = CalendarResourceJsonService.class)
@JSONWebService
@ProviderType
public class CalendarResourceJsonService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.calendar.service.impl.CalendarResourceServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public com.liferay.calendar.model.CalendarResource addCalendarResource(
		long groupId, long classNameId, long classPK,
		java.lang.String classUuid, java.lang.String code,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		boolean active, com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.addCalendarResource(groupId, classNameId, classPK,
			classUuid, code, nameMap, descriptionMap, active, serviceContext);
	}

	public com.liferay.calendar.model.CalendarResource deleteCalendarResource(
		long calendarResourceId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.deleteCalendarResource(calendarResourceId);
	}

	public com.liferay.calendar.model.CalendarResource fetchCalendarResource(
		long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.fetchCalendarResource(classNameId, classPK);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _service.getBeanIdentifier();
	}

	public com.liferay.calendar.model.CalendarResource getCalendarResource(
		long calendarResourceId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.getCalendarResource(calendarResourceId);
	}

	public java.util.List<com.liferay.calendar.model.CalendarResource> search(
		long companyId, long[] groupIds, long[] classNameIds,
		java.lang.String code, java.lang.String name,
		java.lang.String description, boolean active, boolean andOperator,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.calendar.model.CalendarResource> orderByComparator) {
		return _service.search(companyId, groupIds, classNameIds, code, name,
			description, active, andOperator, start, end, orderByComparator);
	}

	public java.util.List<com.liferay.calendar.model.CalendarResource> search(
		long companyId, long[] groupIds, long[] classNameIds,
		java.lang.String keywords, boolean active, boolean andOperator,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.calendar.model.CalendarResource> orderByComparator) {
		return _service.search(companyId, groupIds, classNameIds, keywords,
			active, andOperator, start, end, orderByComparator);
	}

	public int searchCount(long companyId, long[] groupIds,
		long[] classNameIds, java.lang.String code, java.lang.String name,
		java.lang.String description, boolean active, boolean andOperator) {
		return _service.searchCount(companyId, groupIds, classNameIds, code,
			name, description, active, andOperator);
	}

	public int searchCount(long companyId, long[] groupIds,
		long[] classNameIds, java.lang.String keywords, boolean active) {
		return _service.searchCount(companyId, groupIds, classNameIds,
			keywords, active);
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_service.setBeanIdentifier(beanIdentifier);
	}

	public com.liferay.calendar.model.CalendarResource updateCalendarResource(
		long calendarResourceId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		boolean active, com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _service.updateCalendarResource(calendarResourceId, nameMap,
			descriptionMap, active, serviceContext);
	}

	@Reference
	protected void setService(CalendarResourceService service) {
		_service = service;
	}

	private CalendarResourceService _service;
}
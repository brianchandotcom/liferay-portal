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

import com.liferay.calendar.model.CalendarResource;
import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CalendarResourceService}.
 *
 * @author Eduardo Lundgren
 * @see CalendarResourceService
 * @generated
 */
public class CalendarResourceServiceWrapper
	implements CalendarResourceService,
			   ServiceWrapper<CalendarResourceService> {

	public CalendarResourceServiceWrapper(
		CalendarResourceService calendarResourceService) {

		_calendarResourceService = calendarResourceService;
	}

	@Override
	public CalendarResource addCalendarResource(
			long groupId, long classNameId, long classPK, String classUuid,
			String code, java.util.Map<java.util.Locale, String> nameMap,
			java.util.Map<java.util.Locale, String> descriptionMap,
			boolean active,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _calendarResourceService.addCalendarResource(
			groupId, classNameId, classPK, classUuid, code, nameMap,
			descriptionMap, active, serviceContext);
	}

	@Override
	public CalendarResource deleteCalendarResource(long calendarResourceId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _calendarResourceService.deleteCalendarResource(
			calendarResourceId);
	}

	@Override
	public CalendarResource fetchCalendarResource(
			long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _calendarResourceService.fetchCalendarResource(
			classNameId, classPK);
	}

	@Override
	public CalendarResource getCalendarResource(long calendarResourceId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _calendarResourceService.getCalendarResource(calendarResourceId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _calendarResourceService.getOSGiServiceIdentifier();
	}

	@Override
	public java.util.List<CalendarResource> search(
		long companyId, long[] groupIds, long[] classNameIds, String keywords,
		boolean active, boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CalendarResource>
			orderByComparator) {

		return _calendarResourceService.search(
			companyId, groupIds, classNameIds, keywords, active, andOperator,
			start, end, orderByComparator);
	}

	@Override
	public java.util.List<CalendarResource> search(
		long companyId, long[] groupIds, long[] classNameIds, String code,
		String name, String description, boolean active, boolean andOperator,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CalendarResource>
			orderByComparator) {

		return _calendarResourceService.search(
			companyId, groupIds, classNameIds, code, name, description, active,
			andOperator, start, end, orderByComparator);
	}

	@Override
	public int searchCount(
		long companyId, long[] groupIds, long[] classNameIds, String keywords,
		boolean active) {

		return _calendarResourceService.searchCount(
			companyId, groupIds, classNameIds, keywords, active);
	}

	@Override
	public int searchCount(
		long companyId, long[] groupIds, long[] classNameIds, String code,
		String name, String description, boolean active, boolean andOperator) {

		return _calendarResourceService.searchCount(
			companyId, groupIds, classNameIds, code, name, description, active,
			andOperator);
	}

	@Override
	public CalendarResource updateCalendarResource(
			long calendarResourceId,
			java.util.Map<java.util.Locale, String> nameMap,
			java.util.Map<java.util.Locale, String> descriptionMap,
			boolean active,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _calendarResourceService.updateCalendarResource(
			calendarResourceId, nameMap, descriptionMap, active,
			serviceContext);
	}

	@Override
	public CalendarResourceService getWrappedService() {
		return _calendarResourceService;
	}

	@Override
	public void setWrappedService(
		CalendarResourceService calendarResourceService) {

		_calendarResourceService = calendarResourceService;
	}

	private CalendarResourceService _calendarResourceService;

}
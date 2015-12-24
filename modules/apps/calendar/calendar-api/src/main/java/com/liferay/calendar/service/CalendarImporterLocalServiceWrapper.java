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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CalendarImporterLocalService}.
 *
 * @author Eduardo Lundgren
 * @see CalendarImporterLocalService
 * @generated
 */
@ProviderType
public class CalendarImporterLocalServiceWrapper
	implements CalendarImporterLocalService,
		ServiceWrapper<CalendarImporterLocalService> {
	public CalendarImporterLocalServiceWrapper(
		CalendarImporterLocalService calendarImporterLocalService) {
		_calendarImporterLocalService = calendarImporterLocalService;
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _calendarImporterLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public void importCalEvent(
		com.liferay.portlet.calendar.model.CalEvent calEvent)
		throws com.liferay.portal.kernel.exception.PortalException {
		_calendarImporterLocalService.importCalEvent(calEvent);
	}

	@Override
	public void importCalEvents()
		throws com.liferay.portal.kernel.exception.PortalException {
		_calendarImporterLocalService.importCalEvents();
	}

	@Override
	public void importRolePermissions() {
		_calendarImporterLocalService.importRolePermissions();
	}

	@Override
	public CalendarImporterLocalService getWrappedService() {
		return _calendarImporterLocalService;
	}

	@Override
	public void setWrappedService(
		CalendarImporterLocalService calendarImporterLocalService) {
		_calendarImporterLocalService = calendarImporterLocalService;
	}

	private CalendarImporterLocalService _calendarImporterLocalService;
}
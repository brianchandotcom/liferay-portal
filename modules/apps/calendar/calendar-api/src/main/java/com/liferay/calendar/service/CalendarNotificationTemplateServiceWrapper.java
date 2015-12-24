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
 * Provides a wrapper for {@link CalendarNotificationTemplateService}.
 *
 * @author Eduardo Lundgren
 * @see CalendarNotificationTemplateService
 * @generated
 */
@ProviderType
public class CalendarNotificationTemplateServiceWrapper
	implements CalendarNotificationTemplateService,
		ServiceWrapper<CalendarNotificationTemplateService> {
	public CalendarNotificationTemplateServiceWrapper(
		CalendarNotificationTemplateService calendarNotificationTemplateService) {
		_calendarNotificationTemplateService = calendarNotificationTemplateService;
	}

	@Override
	public com.liferay.calendar.model.CalendarNotificationTemplate addCalendarNotificationTemplate(
		long calendarId,
		com.liferay.calendar.notification.NotificationType notificationType,
		java.lang.String notificationTypeSettings,
		com.liferay.calendar.notification.NotificationTemplateType notificationTemplateType,
		java.lang.String subject, java.lang.String body,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _calendarNotificationTemplateService.addCalendarNotificationTemplate(calendarId,
			notificationType, notificationTypeSettings,
			notificationTemplateType, subject, body, serviceContext);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _calendarNotificationTemplateService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.calendar.model.CalendarNotificationTemplate updateCalendarNotificationTemplate(
		long calendarNotificationTemplateId,
		java.lang.String notificationTypeSettings, java.lang.String subject,
		java.lang.String body,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _calendarNotificationTemplateService.updateCalendarNotificationTemplate(calendarNotificationTemplateId,
			notificationTypeSettings, subject, body, serviceContext);
	}

	@Override
	public CalendarNotificationTemplateService getWrappedService() {
		return _calendarNotificationTemplateService;
	}

	@Override
	public void setWrappedService(
		CalendarNotificationTemplateService calendarNotificationTemplateService) {
		_calendarNotificationTemplateService = calendarNotificationTemplateService;
	}

	private CalendarNotificationTemplateService _calendarNotificationTemplateService;
}
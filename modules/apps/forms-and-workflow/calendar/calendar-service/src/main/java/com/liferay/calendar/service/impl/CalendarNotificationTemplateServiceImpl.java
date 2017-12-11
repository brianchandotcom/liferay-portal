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

package com.liferay.calendar.service.impl;

import com.liferay.calendar.model.Calendar;
import com.liferay.calendar.model.CalendarNotificationTemplate;
import com.liferay.calendar.notification.NotificationTemplateType;
import com.liferay.calendar.notification.NotificationType;
import com.liferay.calendar.service.base.CalendarNotificationTemplateServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.service.ServiceContext;

/**
 * @author Marcellus Tavares
 */
public class CalendarNotificationTemplateServiceImpl
	extends CalendarNotificationTemplateServiceBaseImpl {

	@Override
	public CalendarNotificationTemplate addCalendarNotificationTemplate(
			long calendarId, NotificationType notificationType,
			String notificationTypeSettings,
			NotificationTemplateType notificationTemplateType, String subject,
			String body, ServiceContext serviceContext)
		throws PortalException {

		_calendarModelResourcePermission.check(
			getPermissionChecker(), calendarId, ActionKeys.UPDATE);

		return calendarNotificationTemplateLocalService.
			addCalendarNotificationTemplate(
				getUserId(), calendarId, notificationType,
				notificationTypeSettings, notificationTemplateType, subject,
				body, serviceContext);
	}

	@Override
	public CalendarNotificationTemplate updateCalendarNotificationTemplate(
			long calendarNotificationTemplateId,
			String notificationTypeSettings, String subject, String body,
			ServiceContext serviceContext)
		throws PortalException {

		CalendarNotificationTemplate calendarNotificationTemplate =
			calendarNotificationTemplatePersistence.findByPrimaryKey(
				calendarNotificationTemplateId);

		_calendarModelResourcePermission.check(
			getPermissionChecker(),
			calendarNotificationTemplate.getCalendarId(), ActionKeys.UPDATE);

		return calendarNotificationTemplateLocalService.
			updateCalendarNotificationTemplate(
				calendarNotificationTemplateId, notificationTypeSettings,
				subject, body, serviceContext);
	}

	private static volatile ModelResourcePermission<Calendar>
		_calendarModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				CalendarNotificationTemplateServiceImpl.class,
				"_calendarModelResourcePermission", Calendar.class);

}
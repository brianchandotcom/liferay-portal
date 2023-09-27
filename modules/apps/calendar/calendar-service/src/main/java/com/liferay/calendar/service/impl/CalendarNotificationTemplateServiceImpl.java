/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.calendar.service.impl;

import com.liferay.calendar.model.Calendar;
import com.liferay.calendar.model.CalendarNotificationTemplate;
import com.liferay.calendar.notification.NotificationTemplateType;
import com.liferay.calendar.notification.NotificationType;
import com.liferay.calendar.service.base.CalendarNotificationTemplateServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.ServiceContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(
	property = {
		"json.web.service.context.name=calendar",
		"json.web.service.context.path=CalendarNotificationTemplate"
	},
	service = AopService.class
)
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

	@Reference(
		target = "(model.class.name=com.liferay.calendar.model.Calendar)"
	)
	private ModelResourcePermission<Calendar> _calendarModelResourcePermission;

}
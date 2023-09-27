/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.calendar.service;

import com.liferay.calendar.model.CalendarNotificationTemplate;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * Provides the remote service utility for CalendarNotificationTemplate. This utility wraps
 * <code>com.liferay.calendar.service.impl.CalendarNotificationTemplateServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Eduardo Lundgren
 * @see CalendarNotificationTemplateService
 * @generated
 */
public class CalendarNotificationTemplateServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.calendar.service.impl.CalendarNotificationTemplateServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static CalendarNotificationTemplate addCalendarNotificationTemplate(
			long calendarId,
			com.liferay.calendar.notification.NotificationType notificationType,
			String notificationTypeSettings,
			com.liferay.calendar.notification.NotificationTemplateType
				notificationTemplateType,
			String subject, String body,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addCalendarNotificationTemplate(
			calendarId, notificationType, notificationTypeSettings,
			notificationTemplateType, subject, body, serviceContext);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static CalendarNotificationTemplate
			updateCalendarNotificationTemplate(
				long calendarNotificationTemplateId,
				String notificationTypeSettings, String subject, String body,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateCalendarNotificationTemplate(
			calendarNotificationTemplateId, notificationTypeSettings, subject,
			body, serviceContext);
	}

	public static CalendarNotificationTemplateService getService() {
		return _service;
	}

	public static void setService(CalendarNotificationTemplateService service) {
		_service = service;
	}

	private static volatile CalendarNotificationTemplateService _service;

}
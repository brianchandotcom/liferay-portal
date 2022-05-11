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

package com.liferay.notification.service;

import com.liferay.notification.model.NotificationTemplate;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;
import java.util.Map;

/**
 * Provides the remote service utility for NotificationTemplate. This utility wraps
 * <code>com.liferay.notification.service.impl.NotificationTemplateServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Gabriel Albuquerque
 * @see NotificationTemplateService
 * @generated
 */
public class NotificationTemplateServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.notification.service.impl.NotificationTemplateServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static NotificationTemplate addNotificationTemplate(
			long userId, String name, String description, String from,
			Map<java.util.Locale, String> fromNameMap, String to, String cc,
			String bcc, boolean enabled,
			Map<java.util.Locale, String> subjectMap,
			Map<java.util.Locale, String> bodyMap)
		throws PortalException {

		return getService().addNotificationTemplate(
			userId, name, description, from, fromNameMap, to, cc, bcc, enabled,
			subjectMap, bodyMap);
	}

	public static NotificationTemplate deleteNotificationTemplate(
			long notificationTemplateId)
		throws PortalException {

		return getService().deleteNotificationTemplate(notificationTemplateId);
	}

	public static NotificationTemplate deleteNotificationTemplate(
			NotificationTemplate notificationTemplate)
		throws PortalException {

		return getService().deleteNotificationTemplate(notificationTemplate);
	}

	public static NotificationTemplate getNotificationTemplate(
			long notificationTemplateId)
		throws PortalException {

		return getService().getNotificationTemplate(notificationTemplateId);
	}

	public static List<NotificationTemplate> getNotificationTemplates(
		int start, int end) {

		return getService().getNotificationTemplates(start, end);
	}

	public static int getNotificationTemplatesCount() {
		return getService().getNotificationTemplatesCount();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static NotificationTemplate updateNotificationTemplate(
			long notificationTemplateId, String name, String description,
			String from, Map<java.util.Locale, String> fromNameMap, String to,
			String cc, String bcc, boolean enabled,
			Map<java.util.Locale, String> subjectMap,
			Map<java.util.Locale, String> bodyMap)
		throws PortalException {

		return getService().updateNotificationTemplate(
			notificationTemplateId, name, description, from, fromNameMap, to,
			cc, bcc, enabled, subjectMap, bodyMap);
	}

	public static NotificationTemplateService getService() {
		return _service;
	}

	private static volatile NotificationTemplateService _service;

}
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
			long userId, long objectDefinitionId, String bcc,
			Map<java.util.Locale, String> bodyMap, String cc,
			String description, String from,
			Map<java.util.Locale, String> fromNameMap, String name,
			Map<java.util.Locale, String> subjectMap,
			Map<java.util.Locale, String> toMap,
			List<Long> attachmentObjectFieldIds)
		throws PortalException {

		return getService().addNotificationTemplate(
			userId, objectDefinitionId, bcc, bodyMap, cc, description, from,
			fromNameMap, name, subjectMap, toMap, attachmentObjectFieldIds);
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

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static NotificationTemplate updateNotificationTemplate(
			long notificationTemplateId, long objectDefinitionId, String bcc,
			Map<java.util.Locale, String> bodyMap, String cc,
			String description, String from,
			Map<java.util.Locale, String> fromNameMap, String name,
			Map<java.util.Locale, String> subjectMap,
			Map<java.util.Locale, String> toMap,
			List<Long> attachmentObjectFieldIds)
		throws PortalException {

		return getService().updateNotificationTemplate(
			notificationTemplateId, objectDefinitionId, bcc, bodyMap, cc,
			description, from, fromNameMap, name, subjectMap, toMap,
			attachmentObjectFieldIds);
	}

	public static NotificationTemplateService getService() {
		return _service;
	}

	private static volatile NotificationTemplateService _service;

}
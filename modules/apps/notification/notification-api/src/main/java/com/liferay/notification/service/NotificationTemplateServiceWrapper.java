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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link NotificationTemplateService}.
 *
 * @author Gabriel Albuquerque
 * @see NotificationTemplateService
 * @generated
 */
public class NotificationTemplateServiceWrapper
	implements NotificationTemplateService,
			   ServiceWrapper<NotificationTemplateService> {

	public NotificationTemplateServiceWrapper() {
		this(null);
	}

	public NotificationTemplateServiceWrapper(
		NotificationTemplateService notificationTemplateService) {

		_notificationTemplateService = notificationTemplateService;
	}

	@Override
	public com.liferay.notification.model.NotificationTemplate
			addNotificationTemplate(
				long userId, String bcc,
				java.util.Map<java.util.Locale, String> bodyMap, String cc,
				String description, boolean enabled, String from,
				java.util.Map<java.util.Locale, String> fromNameMap,
				String name, java.util.Map<java.util.Locale, String> subjectMap,
				String to)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _notificationTemplateService.addNotificationTemplate(
			userId, bcc, bodyMap, cc, description, enabled, from, fromNameMap,
			name, subjectMap, to);
	}

	@Override
	public com.liferay.notification.model.NotificationTemplate
			deleteNotificationTemplate(long notificationTemplateId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _notificationTemplateService.deleteNotificationTemplate(
			notificationTemplateId);
	}

	@Override
	public com.liferay.notification.model.NotificationTemplate
			deleteNotificationTemplate(
				com.liferay.notification.model.NotificationTemplate
					notificationTemplate)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _notificationTemplateService.deleteNotificationTemplate(
			notificationTemplate);
	}

	@Override
	public com.liferay.notification.model.NotificationTemplate
			getNotificationTemplate(long notificationTemplateId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _notificationTemplateService.getNotificationTemplate(
			notificationTemplateId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _notificationTemplateService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.notification.model.NotificationTemplate
			updateNotificationTemplate(
				long notificationTemplateId, String bcc,
				java.util.Map<java.util.Locale, String> bodyMap, String cc,
				String description, boolean enabled, String from,
				java.util.Map<java.util.Locale, String> fromNameMap,
				String name, java.util.Map<java.util.Locale, String> subjectMap,
				String to)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _notificationTemplateService.updateNotificationTemplate(
			notificationTemplateId, bcc, bodyMap, cc, description, enabled,
			from, fromNameMap, name, subjectMap, to);
	}

	@Override
	public NotificationTemplateService getWrappedService() {
		return _notificationTemplateService;
	}

	@Override
	public void setWrappedService(
		NotificationTemplateService notificationTemplateService) {

		_notificationTemplateService = notificationTemplateService;
	}

	private NotificationTemplateService _notificationTemplateService;

}
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

package com.liferay.notification.service.impl;

import com.liferay.notification.constants.NotificationConstants;
import com.liferay.notification.constants.NotificationTemplateActionKeys;
import com.liferay.notification.model.NotificationTemplate;
import com.liferay.notification.service.NotificationTemplateLocalService;
import com.liferay.notification.service.base.NotificationTemplateServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gabriel Albuquerque
 * @author Gustavo Lima
 */
@Component(
	property = {
		"json.web.service.context.name=notification",
		"json.web.service.context.path=NotificationTemplate"
	},
	service = AopService.class
)
public class NotificationTemplateServiceImpl
	extends NotificationTemplateServiceBaseImpl {

	@Override
	public NotificationTemplate addNotificationTemplate(
			long userId, String name, String description, String from,
			Map<Locale, String> fromNameMap, String to, String cc, String bcc,
			boolean enabled, Map<Locale, String> subjectMap,
			Map<Locale, String> bodyMap)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null,
			NotificationTemplateActionKeys.ADD_NOTIFICATION_TEMPLATE);

		return _notificationTemplateLocalService.addNotificationTemplate(
			userId, name, description, from, fromNameMap, to, cc, bcc, enabled,
			subjectMap, bodyMap);
	}

	@Override
	public NotificationTemplate deleteNotificationTemplate(
			long notificationTemplateId)
		throws PortalException {

		_notificationTemplateModelResourcePermission.check(
			getPermissionChecker(), notificationTemplateId, ActionKeys.DELETE);

		return _notificationTemplateLocalService.deleteNotificationTemplate(
			notificationTemplateId);
	}

	@Override
	public NotificationTemplate deleteNotificationTemplate(
			NotificationTemplate notificationTemplate)
		throws PortalException {

		_notificationTemplateModelResourcePermission.check(
			getPermissionChecker(),
			notificationTemplate.getNotificationTemplateId(),
			ActionKeys.DELETE);

		return _notificationTemplateLocalService.deleteNotificationTemplate(
			notificationTemplate);
	}

	@Override
	public NotificationTemplate getNotificationTemplate(
			long notificationTemplateId)
		throws PortalException {

		_notificationTemplateModelResourcePermission.check(
			getPermissionChecker(), notificationTemplateId, ActionKeys.VIEW);

		return _notificationTemplateLocalService.getNotificationTemplate(
			notificationTemplateId);
	}

	@Override
	public List<NotificationTemplate> getNotificationTemplates(
		int start, int end) {

		return _notificationTemplateLocalService.getNotificationTemplates(
			start, end);
	}

	@Override
	public int getNotificationTemplatesCount() {
		return _notificationTemplateLocalService.
			getNotificationTemplatesCount();
	}

	@Override
	public NotificationTemplate updateNotificationTemplate(
			long notificationTemplateId, String name, String description,
			String from, Map<Locale, String> fromNameMap, String to, String cc,
			String bcc, boolean enabled, Map<Locale, String> subjectMap,
			Map<Locale, String> bodyMap)
		throws PortalException {

		_notificationTemplateModelResourcePermission.check(
			getPermissionChecker(), notificationTemplateId, ActionKeys.UPDATE);

		return _notificationTemplateLocalService.updateNotificationTemplate(
			notificationTemplateId, name, description, from, fromNameMap, to,
			cc, bcc, enabled, subjectMap, bodyMap);
	}

	@Reference
	private NotificationTemplateLocalService _notificationTemplateLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.notification.model.NotificationTemplate)"
	)
	private ModelResourcePermission<NotificationTemplate>
		_notificationTemplateModelResourcePermission;

	@Reference(
		target = "(resource.name=" + NotificationConstants.RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

}
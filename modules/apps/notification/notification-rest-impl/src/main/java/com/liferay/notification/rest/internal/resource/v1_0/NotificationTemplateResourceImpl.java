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

package com.liferay.notification.rest.internal.resource.v1_0;

import com.liferay.notification.rest.dto.v1_0.NotificationTemplate;
import com.liferay.notification.rest.resource.v1_0.NotificationTemplateResource;
import com.liferay.notification.service.NotificationTemplateService;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Gabriel Albuquerque
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/notification-template.properties",
	scope = ServiceScope.PROTOTYPE, service = NotificationTemplateResource.class
)
public class NotificationTemplateResourceImpl
	extends BaseNotificationTemplateResourceImpl {

	@Override
	public void deleteNotificationTemplate(Long notificationTemplateId)
		throws Exception {

		_notificationTemplateService.deleteNotificationTemplate(
			notificationTemplateId);
	}

	@Override
	public NotificationTemplate getNotificationTemplate(
			Long notificationTemplateId)
		throws Exception {

		return _toNotificationTemplate(
			_notificationTemplateService.getNotificationTemplate(
				notificationTemplateId));
	}

	@Override
	public NotificationTemplate postNotificationTemplate(
			NotificationTemplate notificationTemplate)
		throws Exception {

		return _toNotificationTemplate(
			_notificationTemplateService.addNotificationTemplate(
				contextUser.getUserId(), notificationTemplate.getBcc(),
				LocalizedMapUtil.getLocalizedMap(
					notificationTemplate.getBodyMap()),
				notificationTemplate.getCc(),
				notificationTemplate.getDescription(),
				notificationTemplate.getEnable(),
				notificationTemplate.getFrom(),
				LocalizedMapUtil.getLocalizedMap(
					notificationTemplate.getFromNameMap()),
				notificationTemplate.getName(),
				LocalizedMapUtil.getLocalizedMap(
					notificationTemplate.getSubjectMap()),
				notificationTemplate.getTo()));
	}

	@Override
	public NotificationTemplate putNotificationTemplate(
			Long notificationTemplateId,
			NotificationTemplate notificationTemplate)
		throws Exception {

		return _toNotificationTemplate(
			_notificationTemplateService.updateNotificationTemplate(
				notificationTemplateId, notificationTemplate.getBcc(),
				LocalizedMapUtil.getLocalizedMap(
					notificationTemplate.getBodyMap()),
				notificationTemplate.getCc(),
				notificationTemplate.getDescription(),
				notificationTemplate.getEnable(),
				notificationTemplate.getFrom(),
				LocalizedMapUtil.getLocalizedMap(
					notificationTemplate.getFromNameMap()),
				notificationTemplate.getName(),
				LocalizedMapUtil.getLocalizedMap(
					notificationTemplate.getSubjectMap()),
				notificationTemplate.getTo()));
	}

	private NotificationTemplate _toNotificationTemplate(
		com.liferay.notification.model.NotificationTemplate
			serviceBuilderNotificationTemplate) {

		return new NotificationTemplate() {
			{
				actions = HashMapBuilder.put(
					"delete",
					addAction(
						ActionKeys.DELETE, "deleteNotificationTemplate",
						com.liferay.notification.model.NotificationTemplate.
							class.getName(),
						serviceBuilderNotificationTemplate.
							getNotificationTemplateId())
				).put(
					"get",
					addAction(
						ActionKeys.VIEW, "getNotificationTemplate",
						com.liferay.notification.model.NotificationTemplate.
							class.getName(),
						serviceBuilderNotificationTemplate.
							getNotificationTemplateId())
				).put(
					"permissions",
					addAction(
						ActionKeys.PERMISSIONS, "patchNotificationTemplate",
						com.liferay.notification.model.NotificationTemplate.
							class.getName(),
						serviceBuilderNotificationTemplate.
							getNotificationTemplateId())
				).put(
					"update",
					addAction(
						ActionKeys.UPDATE, "putNotificationTemplate",
						com.liferay.notification.model.NotificationTemplate.
							class.getName(),
						serviceBuilderNotificationTemplate.
							getNotificationTemplateId())
				).build();
				bcc = serviceBuilderNotificationTemplate.getBcc();
				bodyMap = LocalizedMapUtil.getI18nMap(
					serviceBuilderNotificationTemplate.getBodyMap());
				cc = serviceBuilderNotificationTemplate.getCc();
				dateCreated =
					serviceBuilderNotificationTemplate.getCreateDate();
				dateModified =
					serviceBuilderNotificationTemplate.getModifiedDate();
				description =
					serviceBuilderNotificationTemplate.getDescription();
				enable = serviceBuilderNotificationTemplate.getEnabled();
				from = serviceBuilderNotificationTemplate.getFrom();
				fromNameMap = LocalizedMapUtil.getI18nMap(
					serviceBuilderNotificationTemplate.getFromNameMap());
				id =
					serviceBuilderNotificationTemplate.
						getNotificationTemplateId();
				name = serviceBuilderNotificationTemplate.getName();
				name_i18n = LocalizedMapUtil.getI18nMap(
					serviceBuilderNotificationTemplate.getNameMap());
				subjectMap = LocalizedMapUtil.getI18nMap(
					serviceBuilderNotificationTemplate.getSubjectMap());
			}
		};
	}

	@Reference
	private NotificationTemplateService _notificationTemplateService;

}
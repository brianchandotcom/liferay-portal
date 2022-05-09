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

package com.liferay.notification.admin.service.impl;

import com.liferay.notification.admin.exception.NotificationTemplateFromException;
import com.liferay.notification.admin.exception.NotificationTemplateNameException;
import com.liferay.notification.admin.model.NotificationTemplate;
import com.liferay.notification.admin.service.NotificationQueueEntryLocalService;
import com.liferay.notification.admin.service.base.NotificationTemplateLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gabriel Albuquerque
 * @author Gustavo Lima
 */
@Component(
	property = "model.class.name=com.liferay.notification.admin.model.NotificationTemplate",
	service = AopService.class
)
public class NotificationTemplateLocalServiceImpl
	extends NotificationTemplateLocalServiceBaseImpl {

	@Override
	public NotificationTemplate addNotificationTemplate(
			long userId, String name, String description, String from,
			Map<Locale, String> fromNameMap, String to, String cc, String bcc,
			boolean enabled, Map<Locale, String> subjectMap,
			Map<Locale, String> bodyMap)
		throws PortalException {

		User user = _userLocalService.getUser(userId);

		validate(name, from);

		NotificationTemplate notificationTemplate =
			notificationTemplatePersistence.create(
				counterLocalService.increment());

		notificationTemplate.setCompanyId(user.getCompanyId());
		notificationTemplate.setUserId(user.getUserId());
		notificationTemplate.setUserName(user.getFullName());
		notificationTemplate.setName(name);
		notificationTemplate.setDescription(description);
		notificationTemplate.setFrom(from);
		notificationTemplate.setFromNameMap(fromNameMap);
		notificationTemplate.setTo(to);
		notificationTemplate.setCc(cc);
		notificationTemplate.setBcc(bcc);
		notificationTemplate.setEnabled(enabled);
		notificationTemplate.setSubjectMap(subjectMap);
		notificationTemplate.setBodyMap(bodyMap);

		return notificationTemplatePersistence.update(notificationTemplate);
	}

	@Override
	public NotificationTemplate deleteNotificationTemplate(
			long notificationTemplateId)
		throws PortalException {

		NotificationTemplate notificationTemplate =
			notificationTemplatePersistence.findByPrimaryKey(
				notificationTemplateId);

		return deleteNotificationTemplate(notificationTemplate);
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public NotificationTemplate deleteNotificationTemplate(
		NotificationTemplate notificationTemplate) {

		_notificationQueueEntryLocalService.
			updateNotificationQueueEntriesTemplateIds(
				notificationTemplate.getNotificationTemplateId());

		notificationTemplatePersistence.remove(notificationTemplate);

		return notificationTemplate;
	}

	@Override
	public NotificationTemplate updateNotificationTemplate(
			long notificationTemplateId, String name, String description,
			String from, Map<Locale, String> fromNameMap, String to, String cc,
			String bcc, boolean enabled, Map<Locale, String> subjectMap,
			Map<Locale, String> bodyMap)
		throws PortalException {

		NotificationTemplate notificationTemplate =
			notificationTemplatePersistence.findByPrimaryKey(
				notificationTemplateId);

		validate(name, from);

		notificationTemplate.setName(name);
		notificationTemplate.setDescription(description);
		notificationTemplate.setFrom(from);
		notificationTemplate.setFromNameMap(fromNameMap);
		notificationTemplate.setTo(to);
		notificationTemplate.setCc(cc);
		notificationTemplate.setBcc(bcc);
		notificationTemplate.setEnabled(enabled);
		notificationTemplate.setSubjectMap(subjectMap);
		notificationTemplate.setBodyMap(bodyMap);

		return notificationTemplatePersistence.update(notificationTemplate);
	}

	protected void validate(String name, String from) throws PortalException {
		if (Validator.isNull(name)) {
			throw new NotificationTemplateNameException("Name is null");
		}

		if (Validator.isNull(from)) {
			throw new NotificationTemplateFromException("From is null");
		}
	}

	@Reference
	private NotificationQueueEntryLocalService
		_notificationQueueEntryLocalService;

	@Reference
	private UserLocalService _userLocalService;

}
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
import com.liferay.notification.model.NotificationQueueEntry;
import com.liferay.notification.model.NotificationTemplate;
import com.liferay.notification.model.NotificationTemplateAttachment;
import com.liferay.notification.service.NotificationTemplateAttachmentLocalService;
import com.liferay.notification.service.base.NotificationTemplateLocalServiceBaseImpl;
import com.liferay.notification.service.persistence.NotificationQueueEntryPersistence;
import com.liferay.notification.service.persistence.NotificationTemplateAttachmentPersistence;
import com.liferay.notification.type.NotificationContext;
import com.liferay.notification.type.NotificationType;
import com.liferay.notification.type.NotificationTypeServiceTracker;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gabriel Albuquerque
 * @author Gustavo Lima
 */
@Component(
	property = "model.class.name=com.liferay.notification.model.NotificationTemplate",
	service = AopService.class
)
public class NotificationTemplateLocalServiceImpl
	extends NotificationTemplateLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public NotificationTemplate addNotificationTemplate(
			long userId, long objectDefinitionId, String bcc,
			Map<Locale, String> bodyMap, String cc, String description,
			String from, Map<Locale, String> fromNameMap, String name,
			String recipientType, Map<Locale, String> subjectMap,
			Map<Locale, String> toMap, String type,
			List<Long> attachmentObjectFieldIds)
		throws PortalException {

		if (Validator.isNull(type)) {
			type = NotificationConstants.TYPE_EMAIL;
		}

		_validate(
			objectDefinitionId, from, name, type, attachmentObjectFieldIds);

		NotificationTemplate notificationTemplate =
			notificationTemplatePersistence.create(
				counterLocalService.increment());

		User user = _userLocalService.getUser(userId);

		notificationTemplate.setCompanyId(user.getCompanyId());
		notificationTemplate.setUserId(user.getUserId());
		notificationTemplate.setUserName(user.getFullName());

		notificationTemplate.setObjectDefinitionId(objectDefinitionId);
		notificationTemplate.setBcc(bcc);
		notificationTemplate.setBodyMap(bodyMap);
		notificationTemplate.setCc(cc);
		notificationTemplate.setDescription(description);
		notificationTemplate.setFrom(from);
		notificationTemplate.setFromNameMap(fromNameMap);
		notificationTemplate.setName(name);
		notificationTemplate.setRecipientType(recipientType);
		notificationTemplate.setSubjectMap(subjectMap);
		notificationTemplate.setToMap(toMap);
		notificationTemplate.setType(type);

		notificationTemplate = notificationTemplatePersistence.update(
			notificationTemplate);

		_resourceLocalService.addResources(
			notificationTemplate.getCompanyId(), 0,
			notificationTemplate.getUserId(),
			NotificationTemplate.class.getName(),
			notificationTemplate.getNotificationTemplateId(), false, true,
			true);

		for (long attachmentObjectFieldId : attachmentObjectFieldIds) {
			_notificationTemplateAttachmentLocalService.
				addNotificationTemplateAttachment(
					notificationTemplate.getCompanyId(),
					notificationTemplate.getNotificationTemplateId(),
					attachmentObjectFieldId);
		}

		return notificationTemplate;
	}

	@Override
	public NotificationTemplate deleteNotificationTemplate(
			long notificationTemplateId)
		throws PortalException {

		NotificationTemplate notificationTemplate =
			notificationTemplatePersistence.findByPrimaryKey(
				notificationTemplateId);

		return notificationTemplateLocalService.deleteNotificationTemplate(
			notificationTemplate);
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public NotificationTemplate deleteNotificationTemplate(
			NotificationTemplate notificationTemplate)
		throws PortalException {

		notificationTemplate = notificationTemplatePersistence.remove(
			notificationTemplate);

		_resourceLocalService.deleteResource(
			notificationTemplate, ResourceConstants.SCOPE_INDIVIDUAL);

		List<NotificationQueueEntry> notificationQueueEntries =
			_notificationQueueEntryPersistence.findByNotificationTemplateId(
				notificationTemplate.getNotificationTemplateId());

		for (NotificationQueueEntry notificationQueueEntry :
				notificationQueueEntries) {

			notificationQueueEntry.setNotificationTemplateId(0);

			_notificationQueueEntryPersistence.update(notificationQueueEntry);
		}

		_notificationTemplateAttachmentPersistence.
			removeByNotificationTemplateId(
				notificationTemplate.getNotificationTemplateId());

		return notificationTemplate;
	}

	@Override
	public NotificationTemplate getNotificationTemplate(
			long notificationTemplateId)
		throws PortalException {

		return notificationTemplatePersistence.findByPrimaryKey(
			notificationTemplateId);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public NotificationTemplate updateNotificationTemplate(
			long notificationTemplateId, long objectDefinitionId, String bcc,
			Map<Locale, String> bodyMap, String cc, String description,
			String from, Map<Locale, String> fromNameMap, String name,
			String recipientType, Map<Locale, String> subjectMap,
			Map<Locale, String> toMap, String type,
			List<Long> attachmentObjectFieldIds)
		throws PortalException {

		_validate(
			objectDefinitionId, from, name, type, attachmentObjectFieldIds);

		NotificationTemplate notificationTemplate =
			notificationTemplatePersistence.findByPrimaryKey(
				notificationTemplateId);

		notificationTemplate.setObjectDefinitionId(objectDefinitionId);
		notificationTemplate.setBcc(bcc);
		notificationTemplate.setBodyMap(bodyMap);
		notificationTemplate.setCc(cc);
		notificationTemplate.setDescription(description);
		notificationTemplate.setFrom(from);
		notificationTemplate.setFromNameMap(fromNameMap);
		notificationTemplate.setName(name);
		notificationTemplate.setRecipientType(recipientType);
		notificationTemplate.setSubjectMap(subjectMap);
		notificationTemplate.setToMap(toMap);

		notificationTemplate = notificationTemplatePersistence.update(
			notificationTemplate);

		List<Long> oldAttachmentObjectFieldIds = new ArrayList<>();

		for (NotificationTemplateAttachment notificationTemplateAttachment :
				_notificationTemplateAttachmentPersistence.
					findByNotificationTemplateId(
						notificationTemplate.getNotificationTemplateId())) {

			if (ListUtil.exists(
					attachmentObjectFieldIds,
					attachmentObjectFieldId -> Objects.equals(
						attachmentObjectFieldId,
						notificationTemplateAttachment.getObjectFieldId()))) {

				oldAttachmentObjectFieldIds.add(
					notificationTemplateAttachment.getObjectFieldId());

				continue;
			}

			_notificationTemplateAttachmentPersistence.remove(
				notificationTemplateAttachment);
		}

		for (long attachmentObjectFieldId :
				ListUtil.remove(
					attachmentObjectFieldIds, oldAttachmentObjectFieldIds)) {

			_notificationTemplateAttachmentLocalService.
				addNotificationTemplateAttachment(
					notificationTemplate.getCompanyId(),
					notificationTemplate.getNotificationTemplateId(),
					attachmentObjectFieldId);
		}

		return notificationTemplate;
	}

	private void _validate(
			long objectDefinitionId, String from, String name, String type,
			List<Long> attachmentObjectFieldIds)
		throws PortalException {

		NotificationType notificationType =
			_notificationTypeServiceTracker.getNotificationType(type);

		if (notificationType != null) {
			NotificationContext notificationContext = new NotificationContext();

			notificationContext.setObjectDefinitionId(objectDefinitionId);
			notificationContext.setAttachmentObjectFieldIds(
				attachmentObjectFieldIds);
			notificationContext.setAttributes(
				HashMapBuilder.<String, Serializable>put(
					"from", from
				).build());
			notificationContext.setNotificationTemplateName(name);

			notificationType.validateNotificationTemplate(notificationContext);
		}
	}

	@Reference
	private NotificationQueueEntryPersistence
		_notificationQueueEntryPersistence;

	@Reference
	private NotificationTemplateAttachmentLocalService
		_notificationTemplateAttachmentLocalService;

	@Reference
	private NotificationTemplateAttachmentPersistence
		_notificationTemplateAttachmentPersistence;

	@Reference
	private NotificationTypeServiceTracker _notificationTypeServiceTracker;

	@Reference
	private ResourceLocalService _resourceLocalService;

	@Reference
	private UserLocalService _userLocalService;

}
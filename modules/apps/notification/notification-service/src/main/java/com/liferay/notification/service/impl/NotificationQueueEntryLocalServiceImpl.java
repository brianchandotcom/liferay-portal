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

import com.liferay.notification.constants.NotificationQueueEntryConstants;
import com.liferay.notification.model.NotificationQueueEntry;
import com.liferay.notification.service.NotificationQueueEntryAttachmentLocalService;
import com.liferay.notification.service.base.NotificationQueueEntryLocalServiceBaseImpl;
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

import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gabriel Albuquerque
 * @author Gustavo Lima
 */
@Component(
	property = "model.class.name=com.liferay.notification.model.NotificationQueueEntry",
	service = AopService.class
)
public class NotificationQueueEntryLocalServiceImpl
	extends NotificationQueueEntryLocalServiceBaseImpl {

	@Override
	public NotificationQueueEntry addNotificationQueueEntry(
			long userId, long notificationTemplateId, String bcc, String body,
			String cc, String className, long classPK, String from,
			String fromName, double priority, String subject, String to,
			String toName, String type, List<Long> fileEntryIds)
		throws PortalException {

		NotificationQueueEntry notificationQueueEntry =
			notificationQueueEntryPersistence.create(
				counterLocalService.increment());

		User user = _userLocalService.getUser(userId);

		notificationQueueEntry.setCompanyId(user.getCompanyId());
		notificationQueueEntry.setUserId(user.getUserId());
		notificationQueueEntry.setUserName(user.getFullName());

		notificationQueueEntry.setNotificationTemplateId(
			notificationTemplateId);
		notificationQueueEntry.setBcc(bcc);
		notificationQueueEntry.setBody(body);
		notificationQueueEntry.setCc(cc);
		notificationQueueEntry.setClassName(className);
		notificationQueueEntry.setClassPK(classPK);
		notificationQueueEntry.setFrom(from);
		notificationQueueEntry.setFromName(fromName);
		notificationQueueEntry.setPriority(priority);
		notificationQueueEntry.setSubject(subject);
		notificationQueueEntry.setTo(to);
		notificationQueueEntry.setToName(toName);
		notificationQueueEntry.setType(type);
		notificationQueueEntry.setStatus(
			NotificationQueueEntryConstants.STATUS_UNSENT);

		notificationQueueEntry = notificationQueueEntryPersistence.update(
			notificationQueueEntry);

		_resourceLocalService.addResources(
			notificationQueueEntry.getCompanyId(), 0,
			notificationQueueEntry.getUserId(),
			NotificationQueueEntry.class.getName(),
			notificationQueueEntry.getNotificationQueueEntryId(), false, true,
			true);

		for (long fileEntryId : fileEntryIds) {
			_notificationQueueEntryAttachmentLocalService.
				addNotificationQueueEntryAttachment(
					notificationQueueEntry.getCompanyId(), fileEntryId,
					notificationQueueEntry.getNotificationQueueEntryId());
		}

		return notificationQueueEntry;
	}

	@Override
	public void deleteNotificationQueueEntries(Date sentDate)
		throws PortalException {

		for (NotificationQueueEntry notificationQueueEntry :
				notificationQueueEntryPersistence.findByLtSentDate(sentDate)) {

			notificationQueueEntryPersistence.remove(notificationQueueEntry);

			_notificationQueueEntryAttachmentLocalService.
				deleteNotificationQueueEntryAttachments(
					notificationQueueEntry.getNotificationQueueEntryId());
		}
	}

	@Override
	public NotificationQueueEntry deleteNotificationQueueEntry(
			long notificationQueueEntryId)
		throws PortalException {

		NotificationQueueEntry notificationQueueEntry =
			notificationQueueEntryPersistence.findByPrimaryKey(
				notificationQueueEntryId);

		return notificationQueueEntryLocalService.deleteNotificationQueueEntry(
			notificationQueueEntry);
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public NotificationQueueEntry deleteNotificationQueueEntry(
			NotificationQueueEntry notificationQueueEntry)
		throws PortalException {

		notificationQueueEntry = notificationQueueEntryPersistence.remove(
			notificationQueueEntry);

		_resourceLocalService.deleteResource(
			notificationQueueEntry, ResourceConstants.SCOPE_INDIVIDUAL);

		_notificationQueueEntryAttachmentLocalService.
			deleteNotificationQueueEntryAttachments(
				notificationQueueEntry.getNotificationQueueEntryId());

		return notificationQueueEntry;
	}

	@Override
	public NotificationQueueEntry resendNotificationQueueEntry(
			long notificationQueueEntryId)
		throws PortalException {

		return notificationQueueEntryLocalService.updateStatus(
			notificationQueueEntryId,
			NotificationQueueEntryConstants.STATUS_UNSENT);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public NotificationQueueEntry updateStatus(
			long notificationQueueEntryId, int status)
		throws PortalException {

		NotificationQueueEntry notificationQueueEntry =
			notificationQueueEntryPersistence.findByPrimaryKey(
				notificationQueueEntryId);

		if (status == NotificationQueueEntryConstants.STATUS_SENT) {
			notificationQueueEntry.setSentDate(new Date());
		}
		else {
			notificationQueueEntry.setSentDate(null);
		}

		notificationQueueEntry.setStatus(status);

		return notificationQueueEntryPersistence.update(notificationQueueEntry);
	}

	@Reference
	private NotificationQueueEntryAttachmentLocalService
		_notificationQueueEntryAttachmentLocalService;

	@Reference
	private ResourceLocalService _resourceLocalService;

	@Reference
	private UserLocalService _userLocalService;

}
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

import com.liferay.notification.model.NotificationQueueEntry;
import com.liferay.notification.service.base.NotificationQueueEntryLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.systemevent.SystemEvent;

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
			long userId, String className, long classPK,
			long notificationTemplateId, String from, String fromName,
			String to, String toName, String cc, String bcc, String subject,
			String body, double priority)
		throws PortalException {

		User user = _userLocalService.getUser(userId);

		long notificationQueueEntryId = counterLocalService.increment();

		NotificationQueueEntry notificationQueueEntry =
			notificationQueueEntryPersistence.create(notificationQueueEntryId);

		notificationQueueEntry.setCompanyId(user.getCompanyId());
		notificationQueueEntry.setUserId(user.getUserId());
		notificationQueueEntry.setUserName(user.getFullName());
		notificationQueueEntry.setClassName(className);
		notificationQueueEntry.setClassPK(classPK);
		notificationQueueEntry.setNotificationTemplateId(
			notificationTemplateId);
		notificationQueueEntry.setFrom(from);
		notificationQueueEntry.setFromName(fromName);
		notificationQueueEntry.setTo(to);
		notificationQueueEntry.setToName(toName);
		notificationQueueEntry.setCc(cc);
		notificationQueueEntry.setBcc(bcc);
		notificationQueueEntry.setSubject(subject);
		notificationQueueEntry.setBody(body);
		notificationQueueEntry.setPriority(priority);

		return notificationQueueEntryPersistence.update(notificationQueueEntry);
	}

	@Override
	public NotificationQueueEntry deleteNotificationQueueEntry(
			long notificationQueueEntryId)
		throws PortalException {

		NotificationQueueEntry notificationQueueEntry =
			notificationQueueEntryPersistence.findByPrimaryKey(
				notificationQueueEntryId);

		return deleteNotificationQueueEntry(notificationQueueEntry);
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public NotificationQueueEntry deleteNotificationQueueEntry(
		NotificationQueueEntry notificationQueueEntry) {

		notificationQueueEntryPersistence.remove(notificationQueueEntry);

		return notificationQueueEntry;
	}

	@Override
	public void updateNotificationQueueEntriesTemplateIds(
		long notificationTemplateId) {

		List<NotificationQueueEntry> notificationQueueEntries =
			notificationQueueEntryPersistence.findByNotificationTemplateId(
				notificationTemplateId);

		for (NotificationQueueEntry notificationQueueEntry :
				notificationQueueEntries) {

			notificationQueueEntry.setNotificationTemplateId(0);

			notificationQueueEntryPersistence.update(notificationQueueEntry);
		}
	}

	@Reference
	private UserLocalService _userLocalService;

}
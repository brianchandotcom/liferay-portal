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

import com.liferay.notification.model.NotificationQueueEntryAttachment;
import com.liferay.notification.service.base.NotificationQueueEntryAttachmentLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepository;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carolina Barbosa
 */
@Component(
	property = "model.class.name=com.liferay.notification.model.NotificationQueueEntryAttachment",
	service = AopService.class
)
public class NotificationQueueEntryAttachmentLocalServiceImpl
	extends NotificationQueueEntryAttachmentLocalServiceBaseImpl {

	@Override
	public NotificationQueueEntryAttachment addNotificationQueueEntryAttachment(
			long companyId, long fileEntryId, long notificationQueueEntryId)
		throws PortalException {

		NotificationQueueEntryAttachment notificationQueueEntryAttachment =
			notificationQueueEntryAttachmentPersistence.create(
				counterLocalService.increment());

		notificationQueueEntryAttachment.setCompanyId(companyId);
		notificationQueueEntryAttachment.setFileEntryId(fileEntryId);
		notificationQueueEntryAttachment.setNotificationQueueEntryId(
			notificationQueueEntryId);

		return notificationQueueEntryAttachmentPersistence.update(
			notificationQueueEntryAttachment);
	}

	@Override
	public void deleteNotificationQueueEntryAttachments(
			long notificationQueueEntryId)
		throws PortalException {

		for (NotificationQueueEntryAttachment notificationQueueEntryAttachment :
				notificationQueueEntryAttachmentPersistence.
					findByNotificationQueueEntryId(notificationQueueEntryId)) {

			notificationQueueEntryAttachmentPersistence.remove(
				notificationQueueEntryAttachment.
					getNotificationQueueEntryAttachmentId());

			_portletFileRepository.deletePortletFileEntry(
				notificationQueueEntryAttachment.getFileEntryId());
		}
	}

	@Override
	public List<NotificationQueueEntryAttachment>
		getNotificationQueueEntryNotificationQueueEntryAttachments(
			long notificationQueueEntryId) {

		return notificationQueueEntryAttachmentPersistence.
			findByNotificationQueueEntryId(notificationQueueEntryId);
	}

	@Reference
	private PortletFileRepository _portletFileRepository;

}
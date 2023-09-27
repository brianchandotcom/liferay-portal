/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
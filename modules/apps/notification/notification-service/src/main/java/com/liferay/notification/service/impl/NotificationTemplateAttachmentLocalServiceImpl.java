/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.notification.service.impl;

import com.liferay.notification.model.NotificationTemplateAttachment;
import com.liferay.notification.service.base.NotificationTemplateAttachmentLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Carolina Barbosa
 */
@Component(
	property = "model.class.name=com.liferay.notification.model.NotificationTemplateAttachment",
	service = AopService.class
)
public class NotificationTemplateAttachmentLocalServiceImpl
	extends NotificationTemplateAttachmentLocalServiceBaseImpl {

	@Override
	public NotificationTemplateAttachment addNotificationTemplateAttachment(
			long companyId, long notificationTemplateId, long objectFieldId)
		throws PortalException {

		NotificationTemplateAttachment notificationTemplateAttachment =
			notificationTemplateAttachmentPersistence.create(
				counterLocalService.increment());

		notificationTemplateAttachment.setCompanyId(companyId);
		notificationTemplateAttachment.setNotificationTemplateId(
			notificationTemplateId);
		notificationTemplateAttachment.setObjectFieldId(objectFieldId);

		return notificationTemplateAttachmentPersistence.update(
			notificationTemplateAttachment);
	}

	@Override
	public List<NotificationTemplateAttachment>
		getNotificationTemplateAttachments(long notificationTemplateId) {

		return notificationTemplateAttachmentPersistence.
			findByNotificationTemplateId(notificationTemplateId);
	}

}
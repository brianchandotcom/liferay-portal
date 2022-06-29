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
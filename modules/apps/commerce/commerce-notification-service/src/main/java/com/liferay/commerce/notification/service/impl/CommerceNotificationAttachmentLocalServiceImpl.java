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

package com.liferay.commerce.notification.service.impl;

import com.liferay.commerce.notification.model.CommerceNotificationAttachment;
import com.liferay.commerce.notification.service.base.CommerceNotificationAttachmentLocalServiceBaseImpl;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	property = "model.class.name=com.liferay.commerce.notification.model.CommerceNotificationAttachment",
	service = AopService.class
)
public class CommerceNotificationAttachmentLocalServiceImpl
	extends CommerceNotificationAttachmentLocalServiceBaseImpl {

	@Override
	public CommerceNotificationAttachment addCommerceNotificationAttachment(
			long commerceNotificationQueueEntryId, long fileEntryId,
			boolean deleteOnSend, ServiceContext serviceContext)
		throws PortalException {

		User user = _userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();

		FileEntry fileEntry = _dlAppLocalService.getFileEntry(fileEntryId);

		long commerceNotificationAttachmentId = counterLocalService.increment();

		CommerceNotificationAttachment commerceNotificationAttachment =
			commerceNotificationAttachmentPersistence.create(
				commerceNotificationAttachmentId);

		commerceNotificationAttachment.setGroupId(groupId);
		commerceNotificationAttachment.setCompanyId(user.getCompanyId());
		commerceNotificationAttachment.setUserId(user.getUserId());
		commerceNotificationAttachment.setUserName(user.getFullName());
		commerceNotificationAttachment.setCommerceNotificationQueueEntryId(
			commerceNotificationQueueEntryId);
		commerceNotificationAttachment.setFileEntryId(
			fileEntry.getFileEntryId());
		commerceNotificationAttachment.setDeleteOnSend(deleteOnSend);

		return commerceNotificationAttachmentPersistence.update(
			commerceNotificationAttachment);
	}

	@Override
	public void deleteCommerceNotificationAttachments(
		long commerceNotificationQueueEntryId) {

		commerceNotificationAttachmentPersistence.
			removeByCommerceNotificationQueueEntryId(
				commerceNotificationQueueEntryId);
	}

	@Override
	public List<CommerceNotificationAttachment>
		getCommerceNotificationAttachments(
			long commerceNotificationQueueEntryId, int start, int end,
			OrderByComparator<CommerceNotificationAttachment>
				orderByComparator) {

		return commerceNotificationAttachmentPersistence.
			findByCommerceNotificationQueueEntryId(
				commerceNotificationQueueEntryId, start, end,
				orderByComparator);
	}

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private UserLocalService _userLocalService;

}
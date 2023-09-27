/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.service;

import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.model.DLFileEntryTypeConstants;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalServiceWrapper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.subscription.service.SubscriptionLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(service = ServiceWrapper.class)
public class SubscriptionDLAppLocalServiceWrapper
	extends DLAppLocalServiceWrapper {

	@Override
	public void subscribeFileEntryType(
			long userId, long groupId, long fileEntryTypeId)
		throws PortalException {

		super.subscribeFileEntryType(userId, groupId, fileEntryTypeId);

		if (fileEntryTypeId ==
				DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT) {

			fileEntryTypeId = groupId;
		}

		_subscriptionLocalService.addSubscription(
			userId, groupId, DLFileEntryType.class.getName(), fileEntryTypeId);
	}

	@Override
	public void subscribeFolder(long userId, long groupId, long folderId)
		throws PortalException {

		super.subscribeFolder(userId, groupId, folderId);

		if (folderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			folderId = groupId;
		}

		_subscriptionLocalService.addSubscription(
			userId, groupId, DLFolder.class.getName(), folderId);
	}

	@Override
	public void unsubscribeFileEntryType(
			long userId, long groupId, long fileEntryTypeId)
		throws PortalException {

		super.unsubscribeFileEntryType(userId, groupId, fileEntryTypeId);

		if (fileEntryTypeId ==
				DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT) {

			fileEntryTypeId = groupId;
		}

		_subscriptionLocalService.deleteSubscription(
			userId, DLFileEntryType.class.getName(), fileEntryTypeId);
	}

	@Override
	public void unsubscribeFolder(long userId, long groupId, long folderId)
		throws PortalException {

		super.unsubscribeFolder(userId, groupId, folderId);

		if (folderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			folderId = groupId;
		}

		_subscriptionLocalService.deleteSubscription(
			userId, DLFolder.class.getName(), folderId);
	}

	@Reference
	private SubscriptionLocalService _subscriptionLocalService;

}
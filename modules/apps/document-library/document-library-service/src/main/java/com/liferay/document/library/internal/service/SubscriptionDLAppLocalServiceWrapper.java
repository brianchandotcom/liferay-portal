/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.service;

import com.liferay.asset.display.page.constants.AssetDisplayPageConstants;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.model.DLFileEntryTypeConstants;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.model.DLVersionNumberIncrease;
import com.liferay.document.library.kernel.service.DLAppLocalServiceWrapper;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.subscription.service.SubscriptionLocalService;

import java.io.File;
import java.io.InputStream;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(service = ServiceWrapper.class)
public class SubscriptionDLAppLocalServiceWrapper
	extends DLAppLocalServiceWrapper {

	@Override
	public FileEntry addFileEntry(
			String externalReferenceCode, long userId, long repositoryId,
			long folderId, String sourceFileName, String mimeType, byte[] bytes,
			Date expirationDate, Date reviewDate, ServiceContext serviceContext)
		throws PortalException {

		serviceContext.setAttribute(
			"hasAssetDisplayPage", _hasAssetDisplayPage(serviceContext));

		return super.addFileEntry(
			externalReferenceCode, userId, repositoryId, folderId,
			sourceFileName, mimeType, bytes, expirationDate, reviewDate,
			serviceContext);
	}

	@Override
	public FileEntry addFileEntry(
			String externalReferenceCode, long userId, long repositoryId,
			long folderId, String sourceFileName, String mimeType, String title,
			String urlTitle, String description, String changeLog, byte[] bytes,
			Date expirationDate, Date reviewDate, ServiceContext serviceContext)
		throws PortalException {

		serviceContext.setAttribute(
			"hasAssetDisplayPage", _hasAssetDisplayPage(serviceContext));

		return super.addFileEntry(
			externalReferenceCode, userId, repositoryId, folderId,
			sourceFileName, mimeType, title, urlTitle, description, changeLog,
			bytes, expirationDate, reviewDate, serviceContext);
	}

	@Override
	public FileEntry addFileEntry(
			String externalReferenceCode, long userId, long repositoryId,
			long folderId, String sourceFileName, String mimeType, String title,
			String urlTitle, String description, String changeLog, File file,
			Date expirationDate, Date reviewDate, ServiceContext serviceContext)
		throws PortalException {

		serviceContext.setAttribute(
			"hasAssetDisplayPage", _hasAssetDisplayPage(serviceContext));

		return super.addFileEntry(
			externalReferenceCode, userId, repositoryId, folderId,
			sourceFileName, mimeType, title, urlTitle, description, changeLog,
			file, expirationDate, reviewDate, serviceContext);
	}

	@Override
	public FileEntry addFileEntry(
			String externalReferenceCode, long userId, long repositoryId,
			long folderId, String sourceFileName, String mimeType, String title,
			String urlTitle, String description, String changeLog,
			InputStream inputStream, long size, Date expirationDate,
			Date reviewDate, ServiceContext serviceContext)
		throws PortalException {

		serviceContext.setAttribute(
			"hasAssetDisplayPage", _hasAssetDisplayPage(serviceContext));

		return super.addFileEntry(
			externalReferenceCode, userId, repositoryId, folderId,
			sourceFileName, mimeType, title, urlTitle, description, changeLog,
			inputStream, size, expirationDate, reviewDate, serviceContext);
	}

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

	@Override
	public FileEntry updateFileEntry(
			long userId, long fileEntryId, String sourceFileName,
			String mimeType, String title, String urlTitle, String description,
			String changeLog, DLVersionNumberIncrease dlVersionNumberIncrease,
			byte[] bytes, Date expirationDate, Date reviewDate,
			ServiceContext serviceContext)
		throws PortalException {

		serviceContext.setAttribute(
			"hasAssetDisplayPage", _hasAssetDisplayPage(serviceContext));

		return super.updateFileEntry(
			userId, fileEntryId, sourceFileName, mimeType, title, urlTitle,
			description, changeLog, dlVersionNumberIncrease, bytes,
			expirationDate, reviewDate, serviceContext);
	}

	@Override
	public FileEntry updateFileEntry(
			long userId, long fileEntryId, String sourceFileName,
			String mimeType, String title, String urlTitle, String description,
			String changeLog, DLVersionNumberIncrease dlVersionNumberIncrease,
			File file, Date expirationDate, Date reviewDate,
			ServiceContext serviceContext)
		throws PortalException {

		serviceContext.setAttribute(
			"hasAssetDisplayPage", _hasAssetDisplayPage(serviceContext));

		return super.updateFileEntry(
			userId, fileEntryId, sourceFileName, mimeType, title, urlTitle,
			description, changeLog, dlVersionNumberIncrease, file,
			expirationDate, reviewDate, serviceContext);
	}

	@Override
	public FileEntry updateFileEntry(
			long userId, long fileEntryId, String sourceFileName,
			String mimeType, String title, String urlTitle, String description,
			String changeLog, DLVersionNumberIncrease dlVersionNumberIncrease,
			InputStream inputStream, long size, Date expirationDate,
			Date reviewDate, ServiceContext serviceContext)
		throws PortalException {

		serviceContext.setAttribute(
			"hasAssetDisplayPage", _hasAssetDisplayPage(serviceContext));

		return super.updateFileEntry(
			userId, fileEntryId, sourceFileName, mimeType, title, urlTitle,
			description, changeLog, dlVersionNumberIncrease, inputStream, size,
			expirationDate, reviewDate, serviceContext);
	}

	private boolean _hasAssetDisplayPage(ServiceContext serviceContext) {
		int displayPageType = ParamUtil.getInteger(
			serviceContext, "displayPageType",
			AssetDisplayPageConstants.TYPE_DEFAULT);

		if (displayPageType == AssetDisplayPageConstants.TYPE_DEFAULT) {
			long fileEntryTypeId = ParamUtil.getLong(
				serviceContext, "fileEntryTypeId",
				DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT);

			LayoutPageTemplateEntry layoutPageTemplateEntry =
				_layoutPageTemplateEntryService.
					fetchDefaultLayoutPageTemplateEntry(
						serviceContext.getScopeGroupId(),
						_portal.getClassNameId(FileEntry.class),
						fileEntryTypeId);

			if (layoutPageTemplateEntry == null) {
				return false;
			}
		}
		else if (displayPageType == AssetDisplayPageConstants.TYPE_NONE) {
			return false;
		}
		else if (displayPageType == AssetDisplayPageConstants.TYPE_SPECIFIC) {
			if (ParamUtil.getLong(serviceContext, "assetDisplayPageId") == 0) {
				return false;
			}
		}

		return true;
	}

	@Reference
	private LayoutPageTemplateEntryService _layoutPageTemplateEntryService;

	@Reference
	private Portal _portal;

	@Reference
	private SubscriptionLocalService _subscriptionLocalService;

}
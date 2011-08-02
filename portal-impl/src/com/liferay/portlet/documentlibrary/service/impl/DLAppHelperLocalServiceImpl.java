/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.asset.NoSuchEntryException;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetLink;
import com.liferay.portlet.asset.model.AssetLinkConstants;
import com.liferay.portlet.documentlibrary.model.DLFileEntryConstants;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.model.DLSyncConstants;
import com.liferay.portlet.documentlibrary.service.base.DLAppHelperLocalServiceBaseImpl;
import com.liferay.portlet.documentlibrary.social.DLActivityKeys;

import java.util.List;

/**
 * @author Alexander Chow
 */
public class DLAppHelperLocalServiceImpl
	extends DLAppHelperLocalServiceBaseImpl {

	public void addFileEntry(
			FileEntry fileEntry, FileVersion fileVersion,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Sync

		dlSyncLocalService.addSync(
			fileEntry.getUuid(), fileEntry.getCompanyId(),
			fileEntry.getRepositoryId(), DLSyncConstants.TYPE_FILE);

		// Message boards

		if (PropsValues.DL_FILE_ENTRY_COMMENTS_ENABLED) {
			mbMessageLocalService.addDiscussionMessage(
				fileEntry.getUserId(), fileEntry.getUserName(),
				fileEntry.getGroupId(), DLFileEntryConstants.getClassName(),
				fileEntry.getFileEntryId(), WorkflowConstants.ACTION_PUBLISH);
		}
	}

	public void addFolder(Folder folder, ServiceContext serviceContext)
		throws SystemException {

		dlSyncLocalService.addSync(
			folder.getUuid(), folder.getCompanyId(),
			folder.getRepositoryId(), DLSyncConstants.TYPE_FOLDER);
	}

	public void deleteFileEntry(FileEntry fileEntry)
		throws PortalException, SystemException {

		// File ranks

		dlFileRankLocalService.deleteFileRanksByFileEntryId(
			fileEntry.getFileEntryId());

		// File shortcuts

		dlFileShortcutLocalService.deleteFileShortcuts(
			fileEntry.getFileEntryId());

		// Sync

		dlSyncLocalService.updateSync(
			fileEntry.getUuid(), DLSyncConstants.EVENT_DELETE);

		// Asset

		assetEntryLocalService.deleteEntry(
			DLFileEntryConstants.getClassName(), fileEntry.getFileEntryId());

		// Message boards

		mbMessageLocalService.deleteDiscussionMessages(
			DLFileEntryConstants.getClassName(), fileEntry.getFileEntryId());

		// Ratings

		ratingsStatsLocalService.deleteStats(
			DLFileEntryConstants.getClassName(), fileEntry.getFileEntryId());

		// Social

		socialActivityLocalService.deleteActivities(
			DLFileEntryConstants.getClassName(), fileEntry.getFileEntryId());
	}

	public void deleteFolder(Folder folder)
		throws PortalException, SystemException {

		dlSyncLocalService.updateSync(
			folder.getUuid(), DLSyncConstants.EVENT_DELETE);
	}

	public void getFileAsStream(
			long userId, FileEntry fileEntry, boolean incrementCounter)
		throws PortalException, SystemException {

		// File rank

		if (userId > 0 && incrementCounter) {
			dlFileRankLocalService.updateFileRank(
				fileEntry.getGroupId(), fileEntry.getCompanyId(), userId,
				fileEntry.getFileEntryId(), new ServiceContext());
		}

		// File read count

		if (PropsValues.DL_FILE_ENTRY_READ_COUNT_ENABLED && incrementCounter) {
			assetEntryLocalService.incrementViewCounter(
				userId, DLFileEntryConstants.getClassName(),
				fileEntry.getFileEntryId());

			List<DLFileShortcut> fileShortcuts =
				dlFileShortcutPersistence.findByToFileEntryId(
				fileEntry.getFileEntryId());

			for (DLFileShortcut fileShortcut : fileShortcuts) {
				assetEntryLocalService.incrementViewCounter(
					userId, DLFileShortcut.class.getName(),
					fileShortcut.getFileShortcutId());
			}
		}
	}

	public List<DLFileShortcut> getFileShortcuts(
			long groupId, long folderId, int status)
		throws SystemException {

		return dlFileShortcutPersistence.findByG_F_S(groupId, folderId, status);
	}

	public int getFileShortcutsCount(
			long groupId, long folderId, int status)
		throws SystemException {

		return dlFileShortcutPersistence.countByG_F_S(
			groupId, folderId, status);
	}

	public List<FileEntry> getNoAssetFileEntries() {
		return null;
	}

	public AssetEntry updateAsset(
			long userId, FileEntry fileEntry, FileVersion fileVersion,
			long[] assetCategoryIds, String[] assetTagNames,
			long[] assetLinkEntryIds, String mimeType,
			boolean addDraftAssetEntry, boolean visible)
		throws PortalException, SystemException {

		AssetEntry assetEntry = null;

		if (addDraftAssetEntry) {
			assetEntry = assetEntryLocalService.updateEntry(
				userId, fileEntry.getGroupId(),
				DLFileEntryConstants.getClassName(),
				fileVersion.getFileVersionId(), fileEntry.getUuid(),
				assetCategoryIds, assetTagNames, false, null, null, null, null,
				mimeType, fileEntry.getTitle(), fileEntry.getDescription(),
				null, null, null, 0, 0, null, false);
		}
		else {
			assetEntry = assetEntryLocalService.updateEntry(
				userId, fileEntry.getGroupId(),
				DLFileEntryConstants.getClassName(),
				fileEntry.getFileEntryId(), fileEntry.getUuid(),
				assetCategoryIds, assetTagNames, visible, null, null, null,
				null, mimeType, fileEntry.getTitle(),
				fileEntry.getDescription(), null, null, null, 0, 0, null,
				false);

			List<DLFileShortcut> fileShortcuts =
				dlFileShortcutPersistence.findByToFileEntryId(
					fileEntry.getFileEntryId());

			for (DLFileShortcut fileShortcut : fileShortcuts) {
				assetEntryLocalService.updateEntry(
					userId, fileShortcut.getGroupId(),
					DLFileShortcut.class.getName(),
					fileShortcut.getFileShortcutId(), fileShortcut.getUuid(),
					assetCategoryIds, assetTagNames, true, null, null, null,
					null, mimeType, fileEntry.getTitle(),
					fileEntry.getDescription(), null, null, null, 0, 0, null,
					false);
			}
		}

		assetLinkLocalService.updateLinks(
			userId, assetEntry.getEntryId(), assetLinkEntryIds,
			AssetLinkConstants.TYPE_RELATED);

		return assetEntry;
	}

	public void updateFileEntry(
			FileEntry fileEntry, FileVersion fileVersion,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		dlSyncLocalService.updateSync(
			fileEntry.getUuid(), DLSyncConstants.EVENT_UPDATE);
	}

	public void updateFolder(Folder folder, ServiceContext serviceContext)
		throws PortalException, SystemException {

		dlSyncLocalService.updateSync(
			folder.getUuid(), DLSyncConstants.EVENT_UPDATE);
	}

	public void updateStatus(
			long userId, FileEntry fileEntry, FileVersion latestFileVersion,
			int status)
		throws PortalException, SystemException {

		if (status == WorkflowConstants.STATUS_APPROVED) {

			// Asset

			String latestFileVersionVersion = latestFileVersion.getVersion();

			if (latestFileVersionVersion.equals(fileEntry.getVersion())) {
				if (!latestFileVersionVersion.equals(
						DLFileEntryConstants.DEFAULT_VERSION)) {

					AssetEntry draftAssetEntry = null;

					try {
						draftAssetEntry = assetEntryLocalService.getEntry(
							DLFileEntryConstants.getClassName(),
							latestFileVersion.getPrimaryKey());

						long[] assetCategoryIds =
							draftAssetEntry.getCategoryIds();
						String[] assetTagNames = draftAssetEntry.getTagNames();

						List<AssetLink> assetLinks =
							assetLinkLocalService.getDirectLinks(
								draftAssetEntry.getEntryId(),
								AssetLinkConstants.TYPE_RELATED);

						long[] assetLinkEntryIds = StringUtil.split(
							ListUtil.toString(
								assetLinks, AssetLink.ENTRY_ID2_ACCESSOR), 0L);

						AssetEntry assetEntry =
							assetEntryLocalService.updateEntry(
								userId, fileEntry.getGroupId(),
								DLFileEntryConstants.getClassName(),
								fileEntry.getFileEntryId(), fileEntry.getUuid(),
								assetCategoryIds, assetTagNames, true, null,
								null, null, null, draftAssetEntry.getMimeType(),
								fileEntry.getTitle(),
								fileEntry.getDescription(), null, null, null, 0,
								0, null, false);

						assetLinkLocalService.updateLinks(
							userId, assetEntry.getEntryId(),
							assetLinkEntryIds, AssetLinkConstants.TYPE_RELATED);

						assetEntryLocalService.deleteEntry(
							draftAssetEntry.getEntryId());
					}
					catch (NoSuchEntryException nsee) {
					}
				}

				assetEntryLocalService.updateVisible(
					DLFileEntryConstants.getClassName(),
					fileEntry.getFileEntryId(), true);
			}

			// Social

			int activityType = DLActivityKeys.UPDATE_FILE_ENTRY;

			if (latestFileVersionVersion.equals(
					DLFileEntryConstants.DEFAULT_VERSION)) {

				activityType = DLActivityKeys.ADD_FILE_ENTRY;
			}

			socialActivityLocalService.addUniqueActivity(
				latestFileVersion.getStatusByUserId(),
				fileEntry.getGroupId(), latestFileVersion.getCreateDate(),
				DLFileEntryConstants.getClassName(), fileEntry.getFileEntryId(),
				activityType, StringPool.BLANK, 0);
		}
		else {

			// Asset

			if (Validator.isNull(fileEntry.getVersion())) {
				assetEntryLocalService.updateVisible(
					DLFileEntryConstants.getClassName(),
					fileEntry.getFileEntryId(), false);
			}
		}
	}

}
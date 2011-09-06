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
import com.liferay.portal.model.Group;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileEntry;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.asset.NoSuchEntryException;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetLink;
import com.liferay.portlet.asset.model.AssetLinkConstants;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryConstants;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.model.DLSyncConstants;
import com.liferay.portlet.documentlibrary.service.base.DLAppHelperLocalServiceBaseImpl;
import com.liferay.portlet.documentlibrary.social.DLActivityKeys;
import com.liferay.portlet.documentlibrary.util.DLPreviewableProcessor;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

/**
 * @author Alexander Chow
 */
public class DLAppHelperLocalServiceImpl
	extends DLAppHelperLocalServiceBaseImpl {

	public void addFileEntry(
			FileEntry fileEntry, FileVersion fileVersion,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		if (PropsValues.DL_FILE_ENTRY_COMMENTS_ENABLED) {
			mbMessageLocalService.addDiscussionMessage(
				fileEntry.getUserId(), fileEntry.getUserName(),
				fileEntry.getGroupId(), DLFileEntryConstants.getClassName(),
				fileEntry.getFileEntryId(), WorkflowConstants.ACTION_PUBLISH);
		}
	}

	public void addFolder(Folder folder, ServiceContext serviceContext)
		throws SystemException {

		if (!isStagingGroup(folder.getGroupId())) {
			dlSyncLocalService.addSync(
				folder.getFolderId(), folder.getCompanyId(),
				folder.getRepositoryId(), folder.getParentFolderId(),
				DLSyncConstants.TYPE_FOLDER);
		}
	}

	public void deleteFileEntry(FileEntry fileEntry)
		throws PortalException, SystemException {

		// File previews

		DLPreviewableProcessor.deleteFiles(fileEntry);

		// File ranks

		dlFileRankLocalService.deleteFileRanksByFileEntryId(
			fileEntry.getFileEntryId());

		// File shortcuts

		dlFileShortcutLocalService.deleteFileShortcuts(
			fileEntry.getFileEntryId());

		// Sync

		if (!isStagingGroup(fileEntry.getGroupId())) {
			dlSyncLocalService.updateSync(
				fileEntry.getFileEntryId(), fileEntry.getFolderId(),
				DLSyncConstants.EVENT_DELETE);
		}

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

		if (!isStagingGroup(folder.getGroupId())) {
			dlSyncLocalService.updateSync(
				folder.getFolderId(), folder.getParentFolderId(),
				DLSyncConstants.EVENT_DELETE);
		}
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
				fileEntry.getFileEntryId(), 1);

			List<DLFileShortcut> fileShortcuts =
				dlFileShortcutPersistence.findByToFileEntryId(
				fileEntry.getFileEntryId());

			for (DLFileShortcut fileShortcut : fileShortcuts) {
				assetEntryLocalService.incrementViewCounter(
					userId, DLFileShortcut.class.getName(),
					fileShortcut.getFileShortcutId(), 1);
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
			boolean addDraftAssetEntry, boolean visible, int height, int width)
		throws PortalException, SystemException {

		AssetEntry assetEntry = null;

		long fileEntryTypeId = 0;

		LiferayFileEntry liferayFileEntry =
			(LiferayFileEntry)fileEntry.getLatestFileVersion().getFileEntry();

		if (liferayFileEntry != null) {
			DLFileEntry dlFileEntry = liferayFileEntry.getDLFileEntry();
			fileEntryTypeId = dlFileEntry.getFileEntryTypeId();
		}

		if (addDraftAssetEntry) {
			assetEntry = assetEntryLocalService.updateEntry(
				userId, fileEntry.getGroupId(),
				DLFileEntryConstants.getClassName(),
				fileVersion.getFileVersionId(), fileEntry.getUuid(),
				fileEntryTypeId, assetCategoryIds, assetTagNames, false, null,
				null, null, null, mimeType, fileEntry.getTitle(),
				fileEntry.getDescription(), null, null, null, height, width,
				null, false);
		}
		else {
			assetEntry = assetEntryLocalService.updateEntry(
				userId, fileEntry.getGroupId(),
				DLFileEntryConstants.getClassName(),
				fileEntry.getFileEntryId(), fileEntry.getUuid(),
				fileEntryTypeId, assetCategoryIds, assetTagNames, visible, null,
				null, null, null, mimeType, fileEntry.getTitle(),
				fileEntry.getDescription(), null, null, null, height, width,
				null, false);

			List<DLFileShortcut> fileShortcuts =
				dlFileShortcutPersistence.findByToFileEntryId(
					fileEntry.getFileEntryId());

			for (DLFileShortcut fileShortcut : fileShortcuts) {
				assetEntryLocalService.updateEntry(
					userId, fileShortcut.getGroupId(),
					DLFileShortcut.class.getName(),
					fileShortcut.getFileShortcutId(), fileShortcut.getUuid(),
					fileEntryTypeId, assetCategoryIds, assetTagNames, true,
					null, null, null, null, mimeType, fileEntry.getTitle(),
					fileEntry.getDescription(), null, null, null, height, width,
					null, false);
			}
		}

		assetLinkLocalService.updateLinks(
			userId, assetEntry.getEntryId(), assetLinkEntryIds,
			AssetLinkConstants.TYPE_RELATED);

		return assetEntry;
	}

	@SuppressWarnings("unused")
	public void updateFileEntry(
			FileEntry fileEntry, FileVersion fileVersion,
			ServiceContext serviceContext)
		throws PortalException, SystemException {
	}

	public void updateFolder(Folder folder, ServiceContext serviceContext)
		throws PortalException, SystemException {

		if (!isStagingGroup(folder.getGroupId())) {
			dlSyncLocalService.updateSync(
				folder.getFolderId(), folder.getParentFolderId(),
				DLSyncConstants.EVENT_UPDATE);
		}
	}

	public void updateStatus(
			long userId, FileEntry fileEntry, FileVersion latestFileVersion,
			int status, Map<String, Serializable> workflowContext)
		throws PortalException, SystemException {

		if (status == WorkflowConstants.STATUS_APPROVED) {

			// Asset

			String latestFileVersionVersion = latestFileVersion.getVersion();

			if (latestFileVersionVersion.equals(fileEntry.getVersion())) {
				if (!latestFileVersionVersion.equals(
						DLFileEntryConstants.VERSION_DEFAULT)) {

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

						long fileEntryTypeId = 0;

						LiferayFileEntry liferayFileEntry = (LiferayFileEntry)
							fileEntry.getLatestFileVersion().getFileEntry();

						if (liferayFileEntry != null) {
							DLFileEntry dlFileEntry =
								liferayFileEntry.getDLFileEntry();
							fileEntryTypeId = dlFileEntry.getFileEntryTypeId();
						}

						AssetEntry assetEntry =
							assetEntryLocalService.updateEntry(
								userId, fileEntry.getGroupId(),
								DLFileEntryConstants.getClassName(),
								fileEntry.getFileEntryId(), fileEntry.getUuid(),
								fileEntryTypeId, assetCategoryIds,
								assetTagNames, true, null, null, null, null,
								draftAssetEntry.getMimeType(),
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

			// Sync

			String event = (String)workflowContext.get("event");

			if (!isStagingGroup(fileEntry.getGroupId()) &&
				Validator.isNotNull(event)) {

				if (event.equals(DLSyncConstants.EVENT_ADD)) {
					dlSyncLocalService.addSync(
						fileEntry.getFileEntryId(), fileEntry.getCompanyId(),
						fileEntry.getRepositoryId(), fileEntry.getFolderId(),
						DLSyncConstants.TYPE_FILE);
				}
				else if (event.equals(DLSyncConstants.EVENT_UPDATE)) {
					dlSyncLocalService.updateSync(
						fileEntry.getFileEntryId(), fileEntry.getFolderId(),
						DLSyncConstants.EVENT_UPDATE);
				}
			}

			// Social

			int activityType = DLActivityKeys.UPDATE_FILE_ENTRY;

			if (latestFileVersionVersion.equals(
					DLFileEntryConstants.VERSION_DEFAULT)) {

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

	protected boolean isStagingGroup(long groupId) {
		try {
			Group group = groupLocalService.getGroup(groupId);

			return group.isStagingGroup();
		}
		catch (Exception e) {
			return false;
		}
	}

}
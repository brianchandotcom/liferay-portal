/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileEntry;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileVersion;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.spring.transaction.TransactionCommitCallbackUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.asset.NoSuchEntryException;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetLink;
import com.liferay.portlet.asset.model.AssetLinkConstants;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryConstants;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.DLSyncConstants;
import com.liferay.portlet.documentlibrary.service.base.DLAppHelperLocalServiceBaseImpl;
import com.liferay.portlet.documentlibrary.social.DLActivityKeys;
import com.liferay.portlet.documentlibrary.util.DLProcessorRegistryUtil;

import java.io.Serializable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * @author Alexander Chow
 */
public class DLAppHelperLocalServiceImpl
	extends DLAppHelperLocalServiceBaseImpl {

	public void addFileEntry(
			long userId, FileEntry fileEntry, FileVersion fileVersion,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		updateAsset(
			userId, fileEntry, fileVersion,
			serviceContext.getAssetCategoryIds(),
			serviceContext.getAssetTagNames(),
			serviceContext.getAssetLinkEntryIds());

		if (PropsValues.DL_FILE_ENTRY_COMMENTS_ENABLED) {
			mbMessageLocalService.addDiscussionMessage(
				fileEntry.getUserId(), fileEntry.getUserName(),
				fileEntry.getGroupId(), DLFileEntryConstants.getClassName(),
				fileEntry.getFileEntryId(), WorkflowConstants.ACTION_PUBLISH);
		}

		if (fileVersion instanceof LiferayFileVersion) {
			DLFileVersion dlFileVersion = (DLFileVersion)fileVersion.getModel();

			Map<String, Serializable> workflowContext =
				new HashMap<String, Serializable>();

			workflowContext.put("event", DLSyncConstants.EVENT_ADD);

			WorkflowHandlerRegistryUtil.startWorkflowInstance(
				dlFileVersion.getCompanyId(), dlFileVersion.getGroupId(),
				userId, DLFileEntryConstants.getClassName(),
				dlFileVersion.getFileVersionId(), dlFileVersion, serviceContext,
				workflowContext);
		}

		registerDLProcessorCallback(fileEntry);
	}

	public void addFolder(Folder folder, ServiceContext serviceContext)
		throws PortalException, SystemException {

		if (!isStagingGroup(folder.getGroupId())) {
			dlSyncLocalService.addSync(
				folder.getFolderId(), folder.getUuid(), folder.getCompanyId(),
				folder.getRepositoryId(), folder.getParentFolderId(),
				folder.getName(), folder.getDescription(),
				DLSyncConstants.TYPE_FOLDER, "-1");
		}
	}

	public void checkAssetEntry(
			long userId, FileEntry fileEntry, FileVersion fileVersion)
		throws PortalException, SystemException {

		AssetEntry fileEntryAssetEntry = assetEntryLocalService.fetchEntry(
			DLFileEntryConstants.getClassName(), fileEntry.getFileEntryId());

		long[] assetCategoryIds = new long[0];
		String[] assetTagNames = new String[0];

		long fileEntryTypeId = getFileEntryTypeId(fileEntry);

		if (fileEntryAssetEntry == null) {
			fileEntryAssetEntry = assetEntryLocalService.updateEntry(
				userId, fileEntry.getGroupId(),
				DLFileEntryConstants.getClassName(), fileEntry.getFileEntryId(),
				fileEntry.getUuid(), fileEntryTypeId, assetCategoryIds,
				assetTagNames, false, null, null, null, null,
				fileEntry.getMimeType(), fileEntry.getTitle(),
				fileEntry.getDescription(), null, null, null, 0, 0, null,
				false);
		}

		AssetEntry fileVersionAssetEntry = assetEntryLocalService.fetchEntry(
			DLFileEntryConstants.getClassName(),
			fileVersion.getFileVersionId());

		if ((fileVersionAssetEntry == null) && !fileVersion.isApproved() &&
			!fileVersion.getVersion().equals(
				DLFileEntryConstants.VERSION_DEFAULT)) {

			assetCategoryIds = assetCategoryLocalService.getCategoryIds(
				DLFileEntryConstants.getClassName(),
				fileEntry.getFileEntryId());
			assetTagNames = assetTagLocalService.getTagNames(
				DLFileEntryConstants.getClassName(),
				fileEntry.getFileEntryId());

			fileVersionAssetEntry = assetEntryLocalService.updateEntry(
				userId, fileEntry.getGroupId(),
				DLFileEntryConstants.getClassName(),
				fileVersion.getFileVersionId(), fileEntry.getUuid(),
				fileEntryTypeId, assetCategoryIds, assetTagNames, false, null,
				null, null, null, fileEntry.getMimeType(), fileEntry.getTitle(),
				fileEntry.getDescription(), null, null, null, 0, 0, null,
				false);

			List<AssetLink> assetLinks = assetLinkLocalService.getDirectLinks(
				fileEntryAssetEntry.getEntryId());

			long[] assetLinkIds = StringUtil.split(
				ListUtil.toString(assetLinks, AssetLink.ENTRY_ID2_ACCESSOR),
				0L);

			assetLinkLocalService.updateLinks(
				userId, fileVersionAssetEntry.getEntryId(), assetLinkIds,
				AssetLinkConstants.TYPE_RELATED);
		}
	}

	public void deleteFileEntry(FileEntry fileEntry)
		throws PortalException, SystemException {

		// Subscriptions

		subscriptionLocalService.deleteSubscriptions(
			fileEntry.getCompanyId(), DLFileEntryConstants.getClassName(),
			fileEntry.getFileEntryId());

		// File previews

		DLProcessorRegistryUtil.cleanUp(fileEntry);

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
				fileEntry.getTitle(), fileEntry.getDescription(),
				DLSyncConstants.EVENT_DELETE, fileEntry.getVersion());
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
	}

	public void deleteFolder(Folder folder)
		throws PortalException, SystemException {

		if (!isStagingGroup(folder.getGroupId())) {
			dlSyncLocalService.updateSync(
				folder.getFolderId(), folder.getParentFolderId(),
				folder.getName(), folder.getDescription(),
				DLSyncConstants.EVENT_DELETE, "-1");
		}
	}

	public void getFileAsStream(
			long userId, FileEntry fileEntry, boolean incrementCounter)
		throws SystemException {

		// File rank

		if ((userId > 0) && incrementCounter) {
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

	public int getFileShortcutsCount(long groupId, long folderId, int status)
		throws SystemException {

		return dlFileShortcutPersistence.countByG_F_S(
			groupId, folderId, status);
	}

	public List<FileEntry> getNoAssetFileEntries() {
		return null;
	}

	public void moveFileEntry(FileEntry fileEntry)
		throws PortalException, SystemException {

		if (!isStagingGroup(fileEntry.getGroupId())) {
			dlSyncLocalService.updateSync(
				fileEntry.getFileEntryId(), fileEntry.getFolderId(),
				fileEntry.getTitle(), fileEntry.getDescription(),
				DLSyncConstants.EVENT_UPDATE, fileEntry.getVersion());
		}
	}

	public void moveFolder(Folder folder)
		throws PortalException, SystemException {

		if (!isStagingGroup(folder.getGroupId())) {
			dlSyncLocalService.updateSync(
			folder.getFolderId(), folder.getParentFolderId(), folder.getName(),
			folder.getDescription(), DLSyncConstants.EVENT_UPDATE, "-1");
		}
	}

	public AssetEntry updateAsset(
			long userId, FileEntry fileEntry, FileVersion fileVersion,
			long assetClassPk)
		throws PortalException, SystemException {

		long[] assetCategoryIds = assetCategoryLocalService.getCategoryIds(
			DLFileEntryConstants.getClassName(), assetClassPk);
		String[] assetTagNames = assetTagLocalService.getTagNames(
			DLFileEntryConstants.getClassName(), assetClassPk);

		AssetEntry assetEntry = assetEntryLocalService.getEntry(
			DLFileEntryConstants.getClassName(), assetClassPk);

		List<AssetLink> assetLinks = assetLinkLocalService.getDirectLinks(
			assetEntry.getEntryId());

		long[] assetLinkIds = StringUtil.split(
			ListUtil.toString(assetLinks, AssetLink.ENTRY_ID2_ACCESSOR), 0L);

		return updateAsset(
			userId, fileEntry, fileVersion, assetCategoryIds, assetTagNames,
			assetLinkIds);
	}

	public AssetEntry updateAsset(
			long userId, FileEntry fileEntry, FileVersion fileVersion,
			long[] assetCategoryIds, String[] assetTagNames,
			long[] assetLinkEntryIds)
		throws PortalException, SystemException {

		AssetEntry assetEntry = null;

		boolean visible = false;

		boolean addDraftAssetEntry = false;

		if (fileEntry instanceof LiferayFileEntry) {
			DLFileVersion dlFileVersion = (DLFileVersion)fileVersion.getModel();

			if (dlFileVersion.isApproved()) {
				visible = true;
			}
			else {
				String version = dlFileVersion.getVersion();

				if (!version.equals(DLFileEntryConstants.VERSION_DEFAULT)) {
					addDraftAssetEntry = true;
				}
			}
		}
		else {
			visible = true;
		}

		long fileEntryTypeId = getFileEntryTypeId(fileEntry);

		if (addDraftAssetEntry) {
			assetEntry = assetEntryLocalService.updateEntry(
				userId, fileEntry.getGroupId(),
				DLFileEntryConstants.getClassName(),
				fileVersion.getFileVersionId(), fileEntry.getUuid(),
				fileEntryTypeId, assetCategoryIds, assetTagNames, false, null,
				null, null, null, fileEntry.getMimeType(), fileEntry.getTitle(),
				fileEntry.getDescription(), null, null, null, 0, 0, null,
				false);
		}
		else {
			assetEntry = assetEntryLocalService.updateEntry(
				userId, fileEntry.getGroupId(),
				DLFileEntryConstants.getClassName(), fileEntry.getFileEntryId(),
				fileEntry.getUuid(), fileEntryTypeId, assetCategoryIds,
				assetTagNames, visible, null, null, null, null,
				fileEntry.getMimeType(), fileEntry.getTitle(),
				fileEntry.getDescription(), null, null, null, 0, 0, null,
				false);

			List<DLFileShortcut> dlFileShortcuts =
				dlFileShortcutPersistence.findByToFileEntryId(
					fileEntry.getFileEntryId());

			for (DLFileShortcut dlFileShortcut : dlFileShortcuts) {
				assetEntryLocalService.updateEntry(
					userId, dlFileShortcut.getGroupId(),
					DLFileShortcut.class.getName(),
					dlFileShortcut.getFileShortcutId(),
					dlFileShortcut.getUuid(), fileEntryTypeId, assetCategoryIds,
					assetTagNames, true, null, null, null, null,
					fileEntry.getMimeType(), fileEntry.getTitle(),
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
			long userId, FileEntry fileEntry, FileVersion fileVersion,
			long assetClassPk)
		throws PortalException, SystemException {

		boolean updateAsset = true;

		if (fileEntry instanceof LiferayFileEntry &&
			fileEntry.getVersion().equals(fileVersion.getVersion())) {

			updateAsset = false;
		}

		if (updateAsset) {
			updateAsset(userId, fileEntry, fileVersion, assetClassPk);
		}

		registerDLProcessorCallback(fileEntry);
	}

	public void updateFileEntry(
			long userId, FileEntry fileEntry, FileVersion fileVersion,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		updateAsset(
			userId, fileEntry, fileVersion,
			serviceContext.getAssetCategoryIds(),
			serviceContext.getAssetTagNames(),
			serviceContext.getAssetLinkEntryIds());

		registerDLProcessorCallback(fileEntry);
	}

	public void updateFolder(Folder folder, ServiceContext serviceContext)
		throws PortalException, SystemException {

		if (!isStagingGroup(folder.getGroupId())) {
			dlSyncLocalService.updateSync(
				folder.getFolderId(), folder.getParentFolderId(),
				folder.getName(), folder.getDescription(),
				DLSyncConstants.EVENT_UPDATE, "-1");
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
						long fileEntryTypeId = getFileEntryTypeId(fileEntry);

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
								fileEntryTypeId, assetCategoryIds,
								assetTagNames, true, null, null, null, null,
								draftAssetEntry.getMimeType(),
								fileEntry.getTitle(),
								fileEntry.getDescription(), null, null, null, 0,
								0, null, false);

						assetLinkLocalService.updateLinks(
							userId, assetEntry.getEntryId(), assetLinkEntryIds,
							AssetLinkConstants.TYPE_RELATED);

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
						fileEntry.getFileEntryId(), fileEntry.getUuid(),
						fileEntry.getCompanyId(), fileEntry.getRepositoryId(),
						fileEntry.getFolderId(), fileEntry.getTitle(),
						fileEntry.getDescription(), DLSyncConstants.TYPE_FILE,
						fileEntry.getVersion());
				}
				else if (event.equals(DLSyncConstants.EVENT_UPDATE)) {
					dlSyncLocalService.updateSync(
						fileEntry.getFileEntryId(), fileEntry.getFolderId(),
						fileEntry.getTitle(), fileEntry.getDescription(),
						DLSyncConstants.EVENT_UPDATE, fileEntry.getVersion());
				}
			}

			// Social

			int activityType = DLActivityKeys.UPDATE_FILE_ENTRY;

			if (latestFileVersionVersion.equals(
					DLFileEntryConstants.VERSION_DEFAULT)) {

				activityType = DLActivityKeys.ADD_FILE_ENTRY;
			}

			socialActivityLocalService.addUniqueActivity(
				latestFileVersion.getStatusByUserId(), fileEntry.getGroupId(),
				latestFileVersion.getCreateDate(),
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

	protected long getFileEntryTypeId(FileEntry fileEntry) {
		if (fileEntry instanceof LiferayFileEntry) {
			DLFileEntry dlFileEntry = (DLFileEntry)fileEntry.getModel();

			return dlFileEntry.getFileEntryTypeId();
		}
		else {
			return 0;
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

	protected void registerDLProcessorCallback(final FileEntry fileEntry) {
		TransactionCommitCallbackUtil.registerCallback(
			new Callable<Void>() {

				public Void call() throws Exception {
					DLProcessorRegistryUtil.trigger(fileEntry);

					return null;
				}

			});
	}

}
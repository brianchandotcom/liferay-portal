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

import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackRegistryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.kernel.workflow.WorkflowThreadLocal;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileEntry;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileVersion;
import com.liferay.portal.repository.liferayrepository.model.LiferayFolder;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.asset.NoSuchEntryException;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetLink;
import com.liferay.portlet.asset.model.AssetLinkConstants;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryConstants;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.model.DLSyncConstants;
import com.liferay.portlet.documentlibrary.service.base.DLAppHelperLocalServiceBaseImpl;
import com.liferay.portlet.documentlibrary.social.DLActivityKeys;
import com.liferay.portlet.documentlibrary.util.DLAppHelperThreadLocal;
import com.liferay.portlet.documentlibrary.util.DLProcessorRegistryUtil;
import com.liferay.portlet.documentlibrary.util.comparator.FileVersionVersionComparator;
import com.liferay.portlet.social.model.SocialActivityConstants;
import com.liferay.portlet.trash.model.TrashEntry;
import com.liferay.portlet.trash.model.TrashVersion;
import com.liferay.portlet.trash.util.TrashUtil;

import java.io.Serializable;

import java.util.Collections;
import java.util.Date;
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

		if (DLAppHelperThreadLocal.isEnabled()) {
			updateAsset(
				userId, fileEntry, fileVersion,
				serviceContext.getAssetCategoryIds(),
				serviceContext.getAssetTagNames(),
				serviceContext.getAssetLinkEntryIds());

			if (PropsValues.DL_FILE_ENTRY_COMMENTS_ENABLED) {
				mbMessageLocalService.addDiscussionMessage(
					fileEntry.getUserId(), fileEntry.getUserName(),
					fileEntry.getGroupId(), DLFileEntryConstants.getClassName(),
					fileEntry.getFileEntryId(),
					WorkflowConstants.ACTION_PUBLISH);
			}
		}

		boolean previousEnabled = WorkflowThreadLocal.isEnabled();

		if (!DLAppHelperThreadLocal.isEnabled()) {
			WorkflowThreadLocal.setEnabled(false);
		}

		try {
			if (fileVersion instanceof LiferayFileVersion) {
				DLFileVersion dlFileVersion =
					(DLFileVersion)fileVersion.getModel();

				Map<String, Serializable> workflowContext =
					new HashMap<String, Serializable>();

				workflowContext.put("event", DLSyncConstants.EVENT_ADD);

				WorkflowHandlerRegistryUtil.startWorkflowInstance(
					dlFileVersion.getCompanyId(), dlFileVersion.getGroupId(),
					userId, DLFileEntryConstants.getClassName(),
					dlFileVersion.getFileVersionId(), dlFileVersion,
					serviceContext, workflowContext);
			}
		}
		finally {
			if (!DLAppHelperThreadLocal.isEnabled()) {
				WorkflowThreadLocal.setEnabled(previousEnabled);
			}
		}

		if (DLAppHelperThreadLocal.isEnabled()) {
			registerDLProcessorCallback(fileEntry, null);
		}
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

	public void cancelCheckOut(
			long userId, FileEntry fileEntry, FileVersion sourceFileVersion,
			FileVersion destinationFileVersion, FileVersion draftFileVersion,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		updateFileEntry(
			userId, fileEntry, sourceFileVersion, destinationFileVersion,
			serviceContext);

		if (draftFileVersion == null) {
			return;
		}

		AssetEntry draftAssetEntry = null;

		try {
			draftAssetEntry = assetEntryLocalService.getEntry(
				DLFileEntryConstants.getClassName(),
				draftFileVersion.getPrimaryKey());

			assetEntryLocalService.deleteEntry(draftAssetEntry.getEntryId());
		}
		catch (NoSuchEntryException nsee) {
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
				userId, fileEntry.getGroupId(), fileEntry.getCreateDate(),
				fileEntry.getModifiedDate(),
				DLFileEntryConstants.getClassName(), fileEntry.getFileEntryId(),
				fileEntry.getUuid(), fileEntryTypeId, assetCategoryIds,
				assetTagNames, false, null, null, null, fileEntry.getMimeType(),
				fileEntry.getTitle(), fileEntry.getDescription(), null, null,
				null, 0, 0, null, false);
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
				userId, fileEntry.getGroupId(), fileEntry.getCreateDate(),
				fileEntry.getModifiedDate(),
				DLFileEntryConstants.getClassName(),
				fileVersion.getFileVersionId(), fileEntry.getUuid(),
				fileEntryTypeId, assetCategoryIds, assetTagNames, false, null,
				null, null, fileEntry.getMimeType(), fileEntry.getTitle(),
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

		if (DLAppHelperThreadLocal.isEnabled()) {

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
				DLFileEntryConstants.getClassName(),
				fileEntry.getFileEntryId());

			// Message boards

			mbMessageLocalService.deleteDiscussionMessages(
				DLFileEntryConstants.getClassName(),
				fileEntry.getFileEntryId());

			// Ratings

			ratingsStatsLocalService.deleteStats(
				DLFileEntryConstants.getClassName(),
				fileEntry.getFileEntryId());
		}

		// Trash

		if (fileEntry.getModel() instanceof DLFileEntry) {
			trashEntryLocalService.deleteEntry(
				DLFileEntryConstants.getClassName(),
				fileEntry.getFileEntryId());
		}
	}

	public void deleteFolder(Folder folder)
		throws PortalException, SystemException {

		// Sync

		if (!isStagingGroup(folder.getGroupId())) {
			dlSyncLocalService.updateSync(
				folder.getFolderId(), folder.getParentFolderId(),
				folder.getName(), folder.getDescription(),
				DLSyncConstants.EVENT_DELETE, "-1");
		}

		// Trash

		if (folder.getModel() instanceof DLFolder) {
			trashEntryLocalService.deleteEntry(
				DLFolderConstants.getClassName(), folder.getFolderId());
		}
	}

	public void getFileAsStream(
			long userId, FileEntry fileEntry, boolean incrementCounter)
		throws SystemException {

		if (!incrementCounter) {
			return;
		}

		// File rank

		if (userId > 0) {
			dlFileRankLocalService.updateFileRank(
				fileEntry.getGroupId(), fileEntry.getCompanyId(), userId,
				fileEntry.getFileEntryId(), new ServiceContext());
		}

		// File read count

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

	public List<DLFileShortcut> getFileShortcuts(
			long groupId, long folderId, boolean active, int status)
		throws SystemException {

		return dlFileShortcutPersistence.findByG_F_A_S(
			groupId, folderId, active, status);
	}

	/**
	 * @deprecated {@link #getFileShortcuts(long, long, boolean, int)}
	 */
	public List<DLFileShortcut> getFileShortcuts(
			long groupId, long folderId, int status)
		throws SystemException {

		return getFileShortcuts(groupId, folderId, true, status);
	}

	public int getFileShortcutsCount(
			long groupId, long folderId, boolean active, int status)
		throws SystemException {

		return dlFileShortcutPersistence.countByG_F_A_S(
			groupId, folderId, active, status);
	}

	/**
	 * @deprecated {@link #getFileShortcutsCount(long, long, boolean, int)}
	 */
	public int getFileShortcutsCount(long groupId, long folderId, int status)
		throws SystemException {

		return getFileShortcutsCount(groupId, folderId, true, status);
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

	public FileEntry moveFileEntryFromTrash(
			long userId, FileEntry fileEntry, long newFolderId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		// File entry

		List<DLFileVersion> dlFileVersions =
			dlFileVersionLocalService.getFileVersions(
				fileEntry.getFileEntryId(), WorkflowConstants.STATUS_ANY);

		dlFileVersions = ListUtil.sort(
			dlFileVersions, new FileVersionVersionComparator());

		FileVersion fileVersion = new LiferayFileVersion(dlFileVersions.get(0));

		if (fileVersion.isInTrash()) {
			restoreFileEntryFromTrash(userId, fileEntry);

			DLFileEntry dlFileEntry = dlFileEntryLocalService.moveFileEntry(
				userId, fileEntry.getFileEntryId(), newFolderId,
				serviceContext);

			if (DLAppHelperThreadLocal.isEnabled()) {
				dlFileRankLocalService.enableFileRanks(
					fileEntry.getFileEntryId());
			}

			return new LiferayFileEntry(dlFileEntry);
		}
		else {
			dlFileEntryLocalService.updateStatus(
				userId, fileVersion.getFileVersionId(), fileVersion.getStatus(),
				new HashMap<String, Serializable>(), serviceContext);

			if (DLAppHelperThreadLocal.isEnabled()) {

				// File rank

				dlFileRankLocalService.enableFileRanks(
					fileEntry.getFileEntryId());

				// File shortcut

				dlFileShortcutLocalService.enableFileShortcuts(
					fileEntry.getFileEntryId());
			}

			// App helper

			fileEntry = dlAppService.moveFileEntry(
				fileEntry.getFileEntryId(), newFolderId, serviceContext);

			// Social

			socialActivityCounterLocalService.enableActivityCounters(
				DLFileEntryConstants.getClassName(),
				fileEntry.getFileEntryId());

			socialActivityLocalService.addActivity(
				userId, fileEntry.getGroupId(),
				DLFileEntryConstants.getClassName(), fileEntry.getFileEntryId(),
				SocialActivityConstants.TYPE_RESTORE_FROM_TRASH,
				StringPool.BLANK, 0);

			return fileEntry;
		}
	}

	public FileEntry moveFileEntryToTrash(long userId, FileEntry fileEntry)
		throws PortalException, SystemException {

		// File entry

		DLFileEntry dlFileEntry = (DLFileEntry)fileEntry.getModel();

		dlFileEntry.setTitle(
			TrashUtil.appendTrashNamespace(dlFileEntry.getTitle()));

		dlFileEntryPersistence.update(dlFileEntry);

		List<DLFileVersion> dlFileVersions =
			dlFileVersionLocalService.getFileVersions(
				fileEntry.getFileEntryId(), WorkflowConstants.STATUS_ANY);

		dlFileVersions = ListUtil.sort(
			dlFileVersions, new FileVersionVersionComparator());

		FileVersion fileVersion = new LiferayFileVersion(dlFileVersions.get(0));

		Map<String, Serializable> workflowContext =
			new HashMap<String, Serializable>();

		workflowContext.put("dlFileVersions", (Serializable)dlFileVersions);

		int oldStatus = fileVersion.getStatus();

		// File version

		dlFileEntryLocalService.updateStatus(
			userId, fileVersion.getFileVersionId(),
			WorkflowConstants.STATUS_IN_TRASH, workflowContext,
			new ServiceContext());

		if (!DLAppHelperThreadLocal.isEnabled()) {
			return fileEntry;
		}

		// File shortcut

		dlFileShortcutLocalService.disableFileShortcuts(
			fileEntry.getFileEntryId());

		// File rank

		dlFileRankLocalService.disableFileRanks(fileEntry.getFileEntryId());

		// Social

		socialActivityCounterLocalService.disableActivityCounters(
			DLFileEntryConstants.getClassName(), fileEntry.getFileEntryId());

		socialActivityLocalService.addActivity(
			userId, fileEntry.getGroupId(), DLFileEntryConstants.getClassName(),
			fileEntry.getFileEntryId(),
			SocialActivityConstants.TYPE_MOVE_TO_TRASH, StringPool.BLANK, 0);

		// Workflow

		if (oldStatus == WorkflowConstants.STATUS_PENDING) {
			workflowInstanceLinkLocalService.deleteWorkflowInstanceLink(
				fileVersion.getCompanyId(), fileVersion.getGroupId(),
				DLFileEntryConstants.getClassName(),
				fileVersion.getFileVersionId());
		}

		return fileEntry;
	}

	public DLFileShortcut moveFileShortcutFromTrash(
			long userId, DLFileShortcut dlFileShortcut, long newFolderId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		if (dlFileShortcut.isInTrash()) {
			restoreFileShortcutFromTrash(userId, dlFileShortcut);
		}

		return dlAppService.updateFileShortcut(
			dlFileShortcut.getFileShortcutId(), newFolderId,
			dlFileShortcut.getToFileEntryId(), serviceContext);
	}

	public DLFileShortcut moveFileShortcutToTrash(
			long userId, DLFileShortcut dlFileShortcut)
		throws PortalException, SystemException {

		// File shortcut

		int oldStatus = dlFileShortcut.getStatus();

		dlFileShortcutLocalService.updateStatus(
			userId, dlFileShortcut.getFileShortcutId(),
			WorkflowConstants.STATUS_IN_TRASH, new ServiceContext());

		// Social

		socialActivityLocalService.addActivity(
			userId, dlFileShortcut.getGroupId(), DLFileShortcut.class.getName(),
			dlFileShortcut.getFileShortcutId(),
			SocialActivityConstants.TYPE_MOVE_TO_TRASH, StringPool.BLANK, 0);

		// Trash

		trashEntryLocalService.addTrashEntry(
			userId, dlFileShortcut.getGroupId(), DLFileShortcut.class.getName(),
			dlFileShortcut.getFileShortcutId(), oldStatus, null, null);

		return dlFileShortcut;
	}

	public void moveFolder(Folder folder)
		throws PortalException, SystemException {

		if (!isStagingGroup(folder.getGroupId())) {
			dlSyncLocalService.updateSync(
				folder.getFolderId(), folder.getParentFolderId(),
				folder.getName(), folder.getDescription(),
				DLSyncConstants.EVENT_UPDATE, "-1");
		}
	}

	public Folder moveFolderFromTrash(
			long userId, Folder folder, long parentFolderId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		DLFolder dlFolder = (DLFolder)folder.getModel();

		if (dlFolder.isInTrash()) {
			restoreFolderFromTrash(userId, folder);
		}
		else {

			// Folder

			dlFolderLocalService.updateStatus(
				userId, folder.getFolderId(), WorkflowConstants.STATUS_APPROVED,
				new HashMap<String, Serializable>(), new ServiceContext());

			// File rank

			dlFileRankLocalService.enableFileRanksByFolderId(
				folder.getFolderId());
		}

		return dlAppService.moveFolder(
			folder.getFolderId(), parentFolderId, serviceContext);
	}

	public Folder moveFolderToTrash(long userId, Folder folder)
		throws PortalException, SystemException {

		// Folder

		DLFolder dlFolder = dlFolderLocalService.updateStatus(
			userId, folder.getFolderId(), WorkflowConstants.STATUS_IN_TRASH,
			new HashMap<String, Serializable>(), new ServiceContext());

		dlFolder.setName(TrashUtil.appendTrashNamespace(dlFolder.getName()));

		dlFolderPersistence.update(dlFolder);

		// File rank

		dlFileRankLocalService.disableFileRanksByFolderId(folder.getFolderId());

		return new LiferayFolder(dlFolder);
	}

	public void restoreFileEntryFromTrash(long userId, FileEntry fileEntry)
		throws PortalException, SystemException {

		// File entry

		DLFileEntry dlFileEntry = (DLFileEntry)fileEntry.getModel();

		dlFileEntry.setTitle(
			TrashUtil.stripTrashNamespace(dlFileEntry.getTitle()));

		dlFileEntryPersistence.update(dlFileEntry);

		FileVersion fileVersion = new LiferayFileVersion(
			dlFileEntry.getLatestFileVersion(true));

		TrashEntry trashEntry = trashEntryLocalService.getEntry(
			DLFileEntryConstants.getClassName(), fileEntry.getFileEntryId());

		// File version

		Map<String, Serializable> workflowContext =
			new HashMap<String, Serializable>();

		List<TrashVersion> trashVersions = trashEntryLocalService.getVersions(
			trashEntry.getEntryId());

		workflowContext.put("trashVersions", (Serializable)trashVersions);

		dlFileEntryLocalService.updateStatus(
			userId, fileVersion.getFileVersionId(), trashEntry.getStatus(),
			workflowContext, new ServiceContext());

		if (!DLAppHelperThreadLocal.isEnabled()) {
			return;
		}

		// File shortcut

		dlFileShortcutLocalService.enableFileShortcuts(
			fileEntry.getFileEntryId());

		// File rank

		dlFileRankLocalService.enableFileRanks(fileEntry.getFileEntryId());

		// Social

		socialActivityCounterLocalService.enableActivityCounters(
			DLFileEntryConstants.getClassName(), fileEntry.getFileEntryId());

		socialActivityLocalService.addActivity(
			userId, fileEntry.getGroupId(), DLFileEntryConstants.getClassName(),
			fileEntry.getFileEntryId(),
			SocialActivityConstants.TYPE_RESTORE_FROM_TRASH, StringPool.BLANK,
			0);
	}

	public void restoreFileShortcutFromTrash(
			long userId, DLFileShortcut dlFileShortcut)
		throws PortalException, SystemException {

		// File shortcut

		TrashEntry trashEntry = trashEntryLocalService.getEntry(
			DLFileShortcut.class.getName(), dlFileShortcut.getFileShortcutId());

		dlFileShortcutLocalService.updateStatus(
			userId, dlFileShortcut.getFileShortcutId(), trashEntry.getStatus(),
			new ServiceContext());

		// Social

		socialActivityCounterLocalService.enableActivityCounters(
			DLFileShortcut.class.getName(), dlFileShortcut.getFileShortcutId());

		socialActivityLocalService.addActivity(
			userId, dlFileShortcut.getGroupId(), DLFileShortcut.class.getName(),
			dlFileShortcut.getFileShortcutId(),
			SocialActivityConstants.TYPE_RESTORE_FROM_TRASH, StringPool.BLANK,
			0);

		// Trash

		trashEntryLocalService.deleteEntry(trashEntry.getEntryId());
	}

	public void restoreFolderFromTrash(long userId, Folder folder)
		throws PortalException, SystemException {

		// Folder

		TrashEntry trashEntry = trashEntryLocalService.getEntry(
			DLFolderConstants.getClassName(), folder.getFolderId());

		dlFolderLocalService.updateStatus(
			userId, folder.getFolderId(), WorkflowConstants.STATUS_APPROVED,
			new HashMap<String, Serializable>(), new ServiceContext());

		// File rank

		dlFileRankLocalService.enableFileRanksByFolderId(folder.getFolderId());

		// Trash

		trashEntryLocalService.deleteEntry(trashEntry.getEntryId());
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
				userId, fileEntry.getGroupId(), fileEntry.getCreateDate(),
				fileEntry.getModifiedDate(),
				DLFileEntryConstants.getClassName(),
				fileVersion.getFileVersionId(), fileEntry.getUuid(),
				fileEntryTypeId, assetCategoryIds, assetTagNames, false, null,
				null, null, fileEntry.getMimeType(), fileEntry.getTitle(),
				fileEntry.getDescription(), null, null, null, 0, 0, null,
				false);
		}
		else {
			assetEntry = assetEntryLocalService.updateEntry(
				userId, fileEntry.getGroupId(), fileEntry.getCreateDate(),
				fileEntry.getModifiedDate(),
				DLFileEntryConstants.getClassName(), fileEntry.getFileEntryId(),
				fileEntry.getUuid(), fileEntryTypeId, assetCategoryIds,
				assetTagNames, visible, null, null, null,
				fileEntry.getMimeType(), fileEntry.getTitle(),
				fileEntry.getDescription(), null, null, null, 0, 0, null,
				false);

			List<DLFileShortcut> dlFileShortcuts =
				dlFileShortcutPersistence.findByToFileEntryId(
					fileEntry.getFileEntryId());

			for (DLFileShortcut dlFileShortcut : dlFileShortcuts) {
				assetEntryLocalService.updateEntry(
					userId, dlFileShortcut.getGroupId(),
					dlFileShortcut.getCreateDate(),
					dlFileShortcut.getModifiedDate(),
					DLFileShortcut.class.getName(),
					dlFileShortcut.getFileShortcutId(),
					dlFileShortcut.getUuid(), fileEntryTypeId, assetCategoryIds,
					assetTagNames, true, null, null, null,
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
			long userId, FileEntry fileEntry, FileVersion sourceFileVersion,
			FileVersion destinationFileVersion, long assetClassPk)
		throws PortalException, SystemException {

		if (!DLAppHelperThreadLocal.isEnabled()) {
			return;
		}

		boolean updateAsset = true;

		if (fileEntry instanceof LiferayFileEntry &&
			fileEntry.getVersion().equals(
				destinationFileVersion.getVersion())) {

			updateAsset = false;
		}

		if (updateAsset) {
			updateAsset(
				userId, fileEntry, destinationFileVersion, assetClassPk);
		}

		registerDLProcessorCallback(fileEntry, sourceFileVersion);
	}

	public void updateFileEntry(
			long userId, FileEntry fileEntry, FileVersion sourceFileVersion,
			FileVersion destinationFileVersion, ServiceContext serviceContext)
		throws PortalException, SystemException {

		if (!DLAppHelperThreadLocal.isEnabled()) {
			return;
		}

		updateAsset(
			userId, fileEntry, destinationFileVersion,
			serviceContext.getAssetCategoryIds(),
			serviceContext.getAssetTagNames(),
			serviceContext.getAssetLinkEntryIds());

		registerDLProcessorCallback(fileEntry, sourceFileVersion);
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
			int oldStatus, int newStatus,
			Map<String, Serializable> workflowContext)
		throws PortalException, SystemException {

		if (!DLAppHelperThreadLocal.isEnabled()) {
			return;
		}

		if (newStatus == WorkflowConstants.STATUS_APPROVED) {

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
								fileEntry.getCreateDate(),
								fileEntry.getModifiedDate(),
								DLFileEntryConstants.getClassName(),
								fileEntry.getFileEntryId(), fileEntry.getUuid(),
								fileEntryTypeId, assetCategoryIds,
								assetTagNames, true, null, null, null,
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

			if ((oldStatus != WorkflowConstants.STATUS_IN_TRASH) &&
				!latestFileVersion.isInTrashFolder()) {

				Date activityDate = latestFileVersion.getModifiedDate();

				int activityType = DLActivityKeys.UPDATE_FILE_ENTRY;

				if (latestFileVersionVersion.equals(
						DLFileEntryConstants.VERSION_DEFAULT)) {

					activityDate = latestFileVersion.getCreateDate();

					activityType = DLActivityKeys.ADD_FILE_ENTRY;
				}

				socialActivityLocalService.addUniqueActivity(
					latestFileVersion.getStatusByUserId(),
					fileEntry.getGroupId(), activityDate,
					DLFileEntryConstants.getClassName(),
					fileEntry.getFileEntryId(), activityType, StringPool.BLANK,
					0);
			}
		}
		else {

			// Asset

			boolean visible = false;

			if (newStatus != WorkflowConstants.STATUS_IN_TRASH) {
				List<DLFileVersion> approvedFileVersions =
					dlFileVersionPersistence.findByF_S(
						fileEntry.getFileEntryId(),
						WorkflowConstants.STATUS_APPROVED);

				if (!approvedFileVersions.isEmpty()) {
					visible = true;
				}
			}

			assetEntryLocalService.updateVisible(
				DLFileEntryConstants.getClassName(), fileEntry.getFileEntryId(),
				visible);
		}
	}

	public void updateStatuses(
			User user, List<Object> dlFileEntriesAndDLFolders, int status)
		throws PortalException, SystemException {

		for (Object object : dlFileEntriesAndDLFolders) {
			if (object instanceof DLFileEntry) {
				DLFileEntry dlFileEntry = (DLFileEntry)object;

				List<DLFileVersion> dlFileVersions =
					dlFileVersionLocalService.getFileVersions(
						dlFileEntry.getFileEntryId(),
						WorkflowConstants.STATUS_ANY);

				dlFileVersions = ListUtil.copy(dlFileVersions);

				Collections.sort(
					dlFileVersions, new FileVersionVersionComparator());

				DLFileVersion latestDlFileVersion = dlFileVersions.get(0);

				if ((status == WorkflowConstants.STATUS_APPROVED) &&
					(latestDlFileVersion.getStatus() ==
						WorkflowConstants.STATUS_IN_TRASH)) {

					continue;
				}

				// File shortcut

				if (status == WorkflowConstants.STATUS_APPROVED) {
					dlFileShortcutLocalService.enableFileShortcuts(
						dlFileEntry.getFileEntryId());
				}
				else {
					dlFileShortcutLocalService.disableFileShortcuts(
						dlFileEntry.getFileEntryId());
				}

				// Asset

				if (status == WorkflowConstants.STATUS_APPROVED) {
					if (latestDlFileVersion.isApproved()) {
						assetEntryLocalService.updateVisible(
							DLFileEntryConstants.getClassName(),
							dlFileEntry.getFileEntryId(), true);
					}
				}
				else {
					assetEntryLocalService.updateVisible(
						DLFileEntryConstants.getClassName(),
						dlFileEntry.getFileEntryId(), false);
				}

				// Social

				JSONObject extraDataJSONObject =
					JSONFactoryUtil.createJSONObject();

				extraDataJSONObject.put("title", dlFileEntry.getTitle());

				if (status == WorkflowConstants.STATUS_APPROVED) {
					socialActivityCounterLocalService.enableActivityCounters(
						DLFileEntryConstants.getClassName(),
						dlFileEntry.getFileEntryId());

					socialActivityLocalService.addActivity(
						user.getUserId(), dlFileEntry.getGroupId(),
						DLFileEntryConstants.getClassName(),
						dlFileEntry.getFileEntryId(),
						SocialActivityConstants.TYPE_RESTORE_FROM_TRASH,
						extraDataJSONObject.toString(), 0);
				}
				else if (latestDlFileVersion.getStatus() ==
							WorkflowConstants.STATUS_APPROVED) {

					socialActivityCounterLocalService.disableActivityCounters(
						DLFileEntryConstants.getClassName(),
						dlFileEntry.getFileEntryId());

					socialActivityLocalService.addActivity(
						user.getUserId(), dlFileEntry.getGroupId(),
						DLFileEntryConstants.getClassName(),
						dlFileEntry.getFileEntryId(),
						SocialActivityConstants.TYPE_MOVE_TO_TRASH,
						extraDataJSONObject.toString(), 0);
				}

				// Index

				Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(
					DLFileEntry.class);

				indexer.reindex(dlFileEntry);

				// Workflow

				if (status != WorkflowConstants.STATUS_APPROVED) {
					for (DLFileVersion dlFileVersion : dlFileVersions) {
						if (!dlFileVersion.isPending()) {
							continue;
						}

						dlFileVersion.setStatus(WorkflowConstants.STATUS_DRAFT);

						dlFileVersionPersistence.update(dlFileVersion);

						workflowInstanceLinkLocalService.
							deleteWorkflowInstanceLink(
								dlFileVersion.getCompanyId(),
								dlFileVersion.getGroupId(),
								DLFileEntryConstants.getClassName(),
								dlFileVersion.getFileVersionId());
					}
				}
			}
			else if (object instanceof DLFolder) {
				DLFolder dlFolder = (DLFolder)object;

				if (dlFolder.isInTrash()) {
					continue;
				}

				QueryDefinition queryDefinition = new QueryDefinition(
					WorkflowConstants.STATUS_ANY);

				List<Object> foldersAndFileEntriesAndFileShortcuts =
					dlFolderLocalService.
						getFoldersAndFileEntriesAndFileShortcuts(
							dlFolder.getGroupId(), dlFolder.getFolderId(), null,
							false, queryDefinition);

				updateStatuses(
					user, foldersAndFileEntriesAndFileShortcuts, status);
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

	protected void registerDLProcessorCallback(
		final FileEntry fileEntry, final FileVersion fileVersion) {

		TransactionCommitCallbackRegistryUtil.registerCallback(
			new Callable<Void>() {

				public Void call() throws Exception {
					DLProcessorRegistryUtil.trigger(fileEntry, fileVersion);

					return null;
				}

			});
	}

}
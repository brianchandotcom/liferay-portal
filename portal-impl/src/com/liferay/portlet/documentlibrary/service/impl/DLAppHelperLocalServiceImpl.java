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

package com.liferay.portlet.documentlibrary.service.impl;

import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.notifications.UserNotificationDefinition;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackRegistryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowThreadLocal;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.Lock;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileEntry;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileVersion;
import com.liferay.portal.repository.liferayrepository.model.LiferayFolder;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.SubscriptionSender;
import com.liferay.portlet.PortletURLFactoryUtil;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetLink;
import com.liferay.portlet.asset.model.AssetLinkConstants;
import com.liferay.portlet.documentlibrary.DLSettings;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryConstants;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.model.DLFileEntryTypeConstants;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.model.DLSyncConstants;
import com.liferay.portlet.documentlibrary.model.DLSyncEvent;
import com.liferay.portlet.documentlibrary.service.base.DLAppHelperLocalServiceBaseImpl;
import com.liferay.portlet.documentlibrary.social.DLActivityKeys;
import com.liferay.portlet.documentlibrary.util.DLAppHelperThreadLocal;
import com.liferay.portlet.documentlibrary.util.DLProcessorRegistryUtil;
import com.liferay.portlet.documentlibrary.util.DLUtil;
import com.liferay.portlet.documentlibrary.util.comparator.FileVersionVersionComparator;
import com.liferay.portlet.social.model.SocialActivityConstants;
import com.liferay.portlet.trash.model.TrashEntry;
import com.liferay.portlet.trash.model.TrashVersion;
import com.liferay.portlet.trash.util.TrashUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Callable;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * Provides the local service helper for the document library application.
 *
 * @author Alexander Chow
 */
public class DLAppHelperLocalServiceImpl
	extends DLAppHelperLocalServiceBaseImpl {

	@Override
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
				DLUtil.startWorkflowInstance(
					userId, (DLFileVersion)fileVersion.getModel(),
					DLSyncConstants.EVENT_ADD, serviceContext);
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

	@Override
	public void addFolder(
			long userId, Folder folder, ServiceContext serviceContext)
		throws PortalException, SystemException {

		if (!DLAppHelperThreadLocal.isEnabled()) {
			return;
		}

		updateAsset(
			userId, folder, serviceContext.getAssetCategoryIds(),
			serviceContext.getAssetTagNames(),
			serviceContext.getAssetLinkEntryIds());

		registerDLSyncEventCallback(DLSyncConstants.EVENT_ADD, folder);
	}

	@Override
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

		AssetEntry draftAssetEntry = assetEntryLocalService.fetchEntry(
			DLFileEntryConstants.getClassName(),
			draftFileVersion.getPrimaryKey());

		if (draftAssetEntry != null) {
			assetEntryLocalService.deleteEntry(draftAssetEntry);
		}
	}

	@Override
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

	@Override
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

			registerDLSyncEventCallback(
				DLSyncConstants.EVENT_DELETE, fileEntry);

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
			DLFileEntry dlFileEntry = (DLFileEntry)fileEntry.getModel();

			if (dlFileEntry.isInTrashExplicitly()) {
				trashEntryLocalService.deleteEntry(
					DLFileEntryConstants.getClassName(),
					fileEntry.getFileEntryId());
			}
			else {
				List<DLFileVersion> dlFileVersions =
					dlFileEntry.getFileVersions(WorkflowConstants.STATUS_ANY);

				for (DLFileVersion dlFileVersion : dlFileVersions) {
					trashVersionLocalService.deleteTrashVersion(
						DLFileVersion.class.getName(),
						dlFileVersion.getFileVersionId());
				}
			}
		}
	}

	@Override
	public void deleteFolder(Folder folder)
		throws PortalException, SystemException {

		if (!DLAppHelperThreadLocal.isEnabled()) {
			return;
		}

		// Sync

		registerDLSyncEventCallback(DLSyncConstants.EVENT_DELETE, folder);

		// Asset

		assetEntryLocalService.deleteEntry(
			DLFolderConstants.getClassName(), folder.getFolderId());

		// Trash

		if (folder.getModel() instanceof DLFolder) {
			DLFolder dlFolder = (DLFolder)folder.getModel();

			if (dlFolder.isInTrashExplicitly()) {
				trashEntryLocalService.deleteEntry(
					DLFolderConstants.getClassName(), dlFolder.getFolderId());
			}
			else {
				trashVersionLocalService.deleteTrashVersion(
					DLFolderConstants.getClassName(), dlFolder.getFolderId());
			}
		}
	}

	@Override
	public void deleteRepositoryFileEntries(long repositoryId)
		throws PortalException, SystemException {

		LocalRepository localRepository =
			repositoryLocalService.getLocalRepositoryImpl(repositoryId);

		List<FileEntry> fileEntries = localRepository.getRepositoryFileEntries(
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);

		for (FileEntry fileEntry : fileEntries) {
			deleteFileEntry(fileEntry);
		}
	}

	@Override
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

	@Override
	public List<DLFileShortcut> getFileShortcuts(
			long groupId, long folderId, boolean active, int status)
		throws SystemException {

		return dlFileShortcutPersistence.findByG_F_A_S(
			groupId, folderId, active, status);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #getFileShortcuts(long, long,
	 *             boolean, int)}
	 */
	@Deprecated
	@Override
	public List<DLFileShortcut> getFileShortcuts(
			long groupId, long folderId, int status)
		throws SystemException {

		return getFileShortcuts(groupId, folderId, true, status);
	}

	@Override
	public int getFileShortcutsCount(
			long groupId, long folderId, boolean active, int status)
		throws SystemException {

		return dlFileShortcutPersistence.countByG_F_A_S(
			groupId, folderId, active, status);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #getFileShortcutsCount(long,
	 *             long, boolean, int)}
	 */
	@Deprecated
	@Override
	public int getFileShortcutsCount(long groupId, long folderId, int status)
		throws SystemException {

		return getFileShortcutsCount(groupId, folderId, true, status);
	}

	@Override
	public List<FileEntry> getNoAssetFileEntries() {
		return null;
	}

	@Override
	public void moveDependentsToTrash(
			List<Object> dlFileEntriesAndDLFolders, long trashEntryId)
		throws PortalException, SystemException {

		for (Object object : dlFileEntriesAndDLFolders) {
			if (object instanceof DLFileEntry) {

				// File entry

				DLFileEntry dlFileEntry = (DLFileEntry)object;

				if (dlFileEntry.isInTrashExplicitly()) {
					continue;
				}

				// File shortcut

				dlFileShortcutLocalService.disableFileShortcuts(
					dlFileEntry.getFileEntryId());

				// File versions

				List<DLFileVersion> dlFileVersions =
					dlFileVersionLocalService.getFileVersions(
						dlFileEntry.getFileEntryId(),
						WorkflowConstants.STATUS_ANY);

				for (DLFileVersion dlFileVersion : dlFileVersions) {

					// File version

					int oldStatus = dlFileVersion.getStatus();

					dlFileVersion.setStatus(WorkflowConstants.STATUS_IN_TRASH);

					dlFileVersionPersistence.update(dlFileVersion);

					// Trash

					int status = oldStatus;

					if (oldStatus == WorkflowConstants.STATUS_PENDING) {
						status = WorkflowConstants.STATUS_DRAFT;
					}

					if (oldStatus != WorkflowConstants.STATUS_APPROVED) {
						trashVersionLocalService.addTrashVersion(
							trashEntryId, DLFileVersion.class.getName(),
							dlFileVersion.getFileVersionId(), status, null);
					}

					// Workflow

					if (oldStatus == WorkflowConstants.STATUS_PENDING) {
						workflowInstanceLinkLocalService.
							deleteWorkflowInstanceLink(
								dlFileVersion.getCompanyId(),
								dlFileVersion.getGroupId(),
								DLFileEntryConstants.getClassName(),
								dlFileVersion.getFileVersionId());
					}
				}

				// Asset

				assetEntryLocalService.updateVisible(
					DLFileEntryConstants.getClassName(),
					dlFileEntry.getFileEntryId(), false);

				// Index

				Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(
					DLFileEntry.class);

				indexer.reindex(dlFileEntry);
			}
			else if (object instanceof DLFileShortcut) {

				// File shortcut

				DLFileShortcut dlFileShortcut = (DLFileShortcut)object;

				if (dlFileShortcut.isInTrash()) {
					continue;
				}

				int oldStatus = dlFileShortcut.getStatus();

				dlFileShortcut.setStatus(WorkflowConstants.STATUS_IN_TRASH);

				dlFileShortcutPersistence.update(dlFileShortcut);

				// Trash

				if (oldStatus != WorkflowConstants.STATUS_APPROVED) {
					trashVersionLocalService.addTrashVersion(
						trashEntryId, DLFileShortcut.class.getName(),
						dlFileShortcut.getFileShortcutId(), oldStatus, null);
				}
			}
			else if (object instanceof DLFolder) {

				// Folder

				DLFolder dlFolder = (DLFolder)object;

				if (dlFolder.isInTrashExplicitly()) {
					continue;
				}

				int oldStatus = dlFolder.getStatus();

				dlFolder.setStatus(WorkflowConstants.STATUS_IN_TRASH);

				dlFolderPersistence.update(dlFolder);

				// Trash

				if (oldStatus != WorkflowConstants.STATUS_APPROVED) {
					trashVersionLocalService.addTrashVersion(
						trashEntryId, DLFolder.class.getName(),
						dlFolder.getFolderId(), oldStatus, null);
				}

				// Folders, file entries, and file shortcuts

				QueryDefinition queryDefinition = new QueryDefinition(
					WorkflowConstants.STATUS_ANY);

				List<Object> foldersAndFileEntriesAndFileShortcuts =
					dlFolderLocalService.
						getFoldersAndFileEntriesAndFileShortcuts(
							dlFolder.getGroupId(), dlFolder.getFolderId(), null,
							false, queryDefinition);

				moveDependentsToTrash(
					foldersAndFileEntriesAndFileShortcuts, trashEntryId);

				// Asset

				assetEntryLocalService.updateVisible(
					DLFolderConstants.getClassName(), dlFolder.getFolderId(),
					false);

				// Index

				Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(
					DLFolder.class);

				indexer.reindex(dlFolder);
			}
		}
	}

	@Override
	public void moveFileEntry(FileEntry fileEntry)
		throws PortalException, SystemException {

		registerDLSyncEventCallback(DLSyncConstants.EVENT_MOVE, fileEntry);
	}

	@Override
	public FileEntry moveFileEntryFromTrash(
			long userId, FileEntry fileEntry, long newFolderId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		boolean hasLock = dlFileEntryLocalService.hasFileEntryLock(
			userId, fileEntry.getFileEntryId());

		if (!hasLock) {
			dlFileEntryLocalService.lockFileEntry(
				userId, fileEntry.getFileEntryId());
		}

		try {
			return doMoveFileEntryFromTrash(
				userId, fileEntry, newFolderId, serviceContext);
		}
		finally {
			if (!hasLock) {
				dlFileEntryLocalService.unlockFileEntry(
					fileEntry.getFileEntryId());
			}
		}
	}

	/**
	 * Moves the file entry to the recycle bin.
	 *
	 * @param  userId the primary key of the user moving the file entry
	 * @param  fileEntry the file entry to be moved
	 * @return the moved file entry
	 * @throws PortalException if a user with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public FileEntry moveFileEntryToTrash(long userId, FileEntry fileEntry)
		throws PortalException, SystemException {

		boolean hasLock = dlFileEntryLocalService.hasFileEntryLock(
			userId, fileEntry.getFileEntryId());

		if (!hasLock) {
			dlFileEntryLocalService.lockFileEntry(
				userId, fileEntry.getFileEntryId());
		}

		try {
			return doMoveFileEntryToTrash(userId, fileEntry);
		}
		finally {
			if (!hasLock) {
				dlFileEntryLocalService.unlockFileEntry(
					fileEntry.getFileEntryId());
			}
		}
	}

	@Override
	public DLFileShortcut moveFileShortcutFromTrash(
			long userId, DLFileShortcut dlFileShortcut, long newFolderId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		if (dlFileShortcut.isInTrashExplicitly()) {
			restoreFileShortcutFromTrash(userId, dlFileShortcut);
		}
		else {

			// File shortcut

			TrashEntry trashEntry = dlFileShortcut.getTrashEntry();

			TrashVersion trashVersion =
				trashVersionLocalService.fetchVersion(
					trashEntry.getEntryId(), DLFileShortcut.class.getName(),
					dlFileShortcut.getFileShortcutId());

			int status = WorkflowConstants.STATUS_APPROVED;

			if (trashVersion != null) {
				status = trashVersion.getStatus();
			}

			dlFileShortcutLocalService.updateStatus(
				userId, dlFileShortcut.getFileShortcutId(), status,
				new ServiceContext());

			// Trash

			if (trashVersion != null) {
				trashVersionLocalService.deleteTrashVersion(trashVersion);
			}

			// Social

			JSONObject extraDataJSONObject = JSONFactoryUtil.createJSONObject();

			extraDataJSONObject.put("title", dlFileShortcut.getToTitle());

			socialActivityLocalService.addActivity(
				userId, dlFileShortcut.getGroupId(),
				DLFileShortcut.class.getName(),
				dlFileShortcut.getFileShortcutId(),
				SocialActivityConstants.TYPE_RESTORE_FROM_TRASH,
				extraDataJSONObject.toString(), 0);
		}

		return dlAppService.updateFileShortcut(
			dlFileShortcut.getFileShortcutId(), newFolderId,
			dlFileShortcut.getToFileEntryId(), serviceContext);
	}

	/**
	 * Moves the file shortcut to the recycle bin.
	 *
	 * @param  userId the primary key of the user moving the file shortcut
	 * @param  dlFileShortcut the file shortcut to be moved
	 * @return the moved file shortcut
	 * @throws PortalException if a user with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DLFileShortcut moveFileShortcutToTrash(
			long userId, DLFileShortcut dlFileShortcut)
		throws PortalException, SystemException {

		// File shortcut

		int oldStatus = dlFileShortcut.getStatus();

		dlFileShortcutLocalService.updateStatus(
			userId, dlFileShortcut.getFileShortcutId(),
			WorkflowConstants.STATUS_IN_TRASH, new ServiceContext());

		// Social

		JSONObject extraDataJSONObject = JSONFactoryUtil.createJSONObject();

		extraDataJSONObject.put(
			"title", TrashUtil.getOriginalTitle(dlFileShortcut.getToTitle()));

		socialActivityLocalService.addActivity(
			userId, dlFileShortcut.getGroupId(), DLFileShortcut.class.getName(),
			dlFileShortcut.getFileShortcutId(),
			SocialActivityConstants.TYPE_MOVE_TO_TRASH,
			extraDataJSONObject.toString(), 0);

		// Trash

		trashEntryLocalService.addTrashEntry(
			userId, dlFileShortcut.getGroupId(), DLFileShortcut.class.getName(),
			dlFileShortcut.getFileShortcutId(), dlFileShortcut.getUuid(), null,
			oldStatus, null, null);

		return dlFileShortcut;
	}

	@Override
	public void moveFolder(Folder folder) throws SystemException {
		registerDLSyncEventCallback(DLSyncConstants.EVENT_MOVE, folder);
	}

	@Override
	public Folder moveFolderFromTrash(
			long userId, Folder folder, long parentFolderId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		boolean hasLock = dlFolderLocalService.hasFolderLock(
			userId, folder.getFolderId());

		Lock lock = null;

		if (!hasLock) {
			lock = dlFolderLocalService.lockFolder(
				userId, folder.getFolderId());
		}

		try {
			return doMoveFolderFromTrash(
				userId, folder, parentFolderId, serviceContext);
		}
		finally {
			if (!hasLock) {
				dlFolderLocalService.unlockFolder(
					folder.getFolderId(), lock.getUuid());
			}
		}
	}

	/**
	 * Moves the folder to the recycle bin.
	 *
	 * @param  userId the primary key of the user moving the folder
	 * @param  folder the folder to be moved
	 * @return the moved folder
	 * @throws PortalException if a user with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Folder moveFolderToTrash(long userId, Folder folder)
		throws PortalException, SystemException {

		boolean hasLock = dlFolderLocalService.hasFolderLock(
			userId, folder.getFolderId());

		Lock lock = null;

		if (!hasLock) {
			lock = dlFolderLocalService.lockFolder(
				userId, folder.getFolderId());
		}

		try {
			return doMoveFolderToTrash(userId, folder);
		}
		finally {
			if (!hasLock) {
				dlFolderLocalService.unlockFolder(
					folder.getFolderId(), lock.getUuid());
			}
		}
	}

	@Override
	public void registerDLSyncEventCallback(String event, FileEntry fileEntry)
		throws PortalException, SystemException {

		if (isStagingGroup(fileEntry.getGroupId()) ||
			!(fileEntry instanceof LiferayFileEntry)) {

			return;
		}

		if (!event.equals(DLSyncConstants.EVENT_DELETE) &&
			!event.equals(DLSyncConstants.EVENT_TRASH)) {

			FileVersion fileVersion = fileEntry.getFileVersion();

			if (!fileVersion.isApproved()) {
				return;
			}
		}

		registerDLSyncEventCallback(
			event, DLSyncConstants.TYPE_FILE, fileEntry.getFileEntryId());
	}

	@Override
	public void registerDLSyncEventCallback(String event, Folder folder)
		throws SystemException {

		if (isStagingGroup(folder.getGroupId()) ||
			!(folder instanceof LiferayFolder)) {

			return;
		}

		registerDLSyncEventCallback(
			event, DLSyncConstants.TYPE_FOLDER, folder.getFolderId());
	}

	@Override
	public void restoreDependentsFromTrash(
			List<Object> dlFileEntriesAndDLFolders, long trashEntryId)
		throws PortalException, SystemException {

		for (Object object : dlFileEntriesAndDLFolders) {
			if (object instanceof DLFileEntry) {

				// File entry

				DLFileEntry dlFileEntry = (DLFileEntry)object;

				TrashEntry trashEntry = trashEntryLocalService.fetchEntry(
					DLFileEntry.class.getName(), dlFileEntry.getFileEntryId());

				if (trashEntry != null) {
					continue;
				}

				// File shortcut

				dlFileShortcutLocalService.enableFileShortcuts(
					dlFileEntry.getFileEntryId());

				// File versions

				List<DLFileVersion> dlFileVersions =
					dlFileVersionLocalService.getFileVersions(
						dlFileEntry.getFileEntryId(),
						WorkflowConstants.STATUS_IN_TRASH);

				for (DLFileVersion dlFileVersion : dlFileVersions) {

					// File version

					TrashVersion trashVersion =
						trashVersionLocalService.fetchVersion(
							trashEntryId, DLFileVersion.class.getName(),
							dlFileVersion.getFileVersionId());

					int oldStatus = WorkflowConstants.STATUS_APPROVED;

					if (trashVersion != null) {
						oldStatus = trashVersion.getStatus();
					}

					dlFileVersion.setStatus(oldStatus);

					dlFileVersionPersistence.update(dlFileVersion);

					// Trash

					if (trashVersion != null) {
						trashVersionLocalService.deleteTrashVersion(
							trashVersion);
					}
				}

				// Asset

				DLFileVersion latestDlFileVersion =
					dlFileEntry.getLatestFileVersion(false);

				if (latestDlFileVersion.isApproved()) {
					assetEntryLocalService.updateVisible(
						DLFileEntryConstants.getClassName(),
						dlFileEntry.getFileEntryId(), true);
				}

				// Index

				Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(
					DLFileEntry.class);

				indexer.reindex(dlFileEntry);
			}
			else if (object instanceof DLFileShortcut) {

				// Folder

				DLFileShortcut dlFileShortcut = (DLFileShortcut)object;

				TrashEntry trashEntry = trashEntryLocalService.fetchEntry(
					DLFileShortcut.class.getName(),
					dlFileShortcut.getFileShortcutId());

				if (trashEntry != null) {
					continue;
				}

				TrashVersion trashVersion =
					trashVersionLocalService.fetchVersion(
						trashEntryId, DLFileShortcut.class.getName(),
						dlFileShortcut.getFileShortcutId());

				int oldStatus = WorkflowConstants.STATUS_APPROVED;

				if (trashVersion != null) {
					oldStatus = trashVersion.getStatus();
				}

				dlFileShortcut.setStatus(oldStatus);

				dlFileShortcutPersistence.update(dlFileShortcut);

				if (trashVersion != null) {
					trashVersionLocalService.deleteTrashVersion(trashVersion);
				}
			}
			else if (object instanceof DLFolder) {

				// Folder

				DLFolder dlFolder = (DLFolder)object;

				TrashEntry trashEntry = trashEntryLocalService.fetchEntry(
					DLFolder.class.getName(), dlFolder.getFolderId());

				if (trashEntry != null) {
					continue;
				}

				TrashVersion trashVersion =
					trashVersionLocalService.fetchVersion(
						trashEntryId, DLFolder.class.getName(),
						dlFolder.getFolderId());

				int oldStatus = WorkflowConstants.STATUS_APPROVED;

				if (trashVersion != null) {
					oldStatus = trashVersion.getStatus();
				}

				dlFolder.setStatus(oldStatus);

				dlFolderPersistence.update(dlFolder);

				// Folders, file entries, and file shortcuts

				QueryDefinition queryDefinition = new QueryDefinition(
					WorkflowConstants.STATUS_IN_TRASH);

				List<Object> foldersAndFileEntriesAndFileShortcuts =
					dlFolderLocalService.
						getFoldersAndFileEntriesAndFileShortcuts(
							dlFolder.getGroupId(), dlFolder.getFolderId(), null,
							false, queryDefinition);

				restoreDependentsFromTrash(
					foldersAndFileEntriesAndFileShortcuts, trashEntryId);

				// Trash

				if (trashVersion != null) {
					trashVersionLocalService.deleteTrashVersion(trashVersion);
				}

				// Asset

				assetEntryLocalService.updateVisible(
					DLFolderConstants.getClassName(), dlFolder.getFolderId(),
					true);

				// Index

				Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(
					DLFolder.class);

				indexer.reindex(dlFolder);
			}
		}
	}

	@Override
	public void restoreFileEntryFromTrash(long userId, FileEntry fileEntry)
		throws PortalException, SystemException {

		// File entry

		DLFileEntry dlFileEntry = (DLFileEntry)fileEntry.getModel();

		dlFileEntry.setTitle(
			TrashUtil.getOriginalTitle(dlFileEntry.getTitle()));

		dlFileEntryPersistence.update(dlFileEntry);

		FileVersion fileVersion = fileEntry.getFileVersion();

		TrashEntry trashEntry = trashEntryLocalService.getEntry(
			DLFileEntryConstants.getClassName(), fileEntry.getFileEntryId());

		dlFileEntryLocalService.updateStatus(
			userId, fileVersion.getFileVersionId(), trashEntry.getStatus(),
			new ServiceContext(), new HashMap<String, Serializable>());

		if (DLAppHelperThreadLocal.isEnabled()) {

			// File rank

			dlFileRankLocalService.enableFileRanks(fileEntry.getFileEntryId());

			// File shortcut

			dlFileShortcutLocalService.enableFileShortcuts(
				fileEntry.getFileEntryId());

			// Sync

			registerDLSyncEventCallback(
				DLSyncConstants.EVENT_RESTORE, fileEntry);
		}

		// Trash

		List<TrashVersion> trashVersions = trashVersionLocalService.getVersions(
			trashEntry.getEntryId());

		for (TrashVersion trashVersion : trashVersions) {
			DLFileVersion trashDLFileVersion =
				dlFileVersionPersistence.findByPrimaryKey(
					trashVersion.getClassPK());

			trashDLFileVersion.setStatus(trashVersion.getStatus());

			dlFileVersionPersistence.update(trashDLFileVersion);
		}

		trashEntryLocalService.deleteEntry(trashEntry.getEntryId());

		if (!DLAppHelperThreadLocal.isEnabled()) {
			return;
		}

		// Social

		JSONObject extraDataJSONObject = JSONFactoryUtil.createJSONObject();

		extraDataJSONObject.put("title", fileEntry.getTitle());

		socialActivityLocalService.addActivity(
			userId, fileEntry.getGroupId(), DLFileEntryConstants.getClassName(),
			fileEntry.getFileEntryId(),
			SocialActivityConstants.TYPE_RESTORE_FROM_TRASH,
			extraDataJSONObject.toString(), 0);
	}

	@Override
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

		JSONObject extraDataJSONObject = JSONFactoryUtil.createJSONObject();

		extraDataJSONObject.put("title", dlFileShortcut.getToTitle());

		socialActivityLocalService.addActivity(
			userId, dlFileShortcut.getGroupId(), DLFileShortcut.class.getName(),
			dlFileShortcut.getFileShortcutId(),
			SocialActivityConstants.TYPE_RESTORE_FROM_TRASH,
			extraDataJSONObject.toString(), 0);

		// Trash

		trashEntryLocalService.deleteEntry(trashEntry.getEntryId());
	}

	@Override
	public void restoreFolderFromTrash(long userId, Folder folder)
		throws PortalException, SystemException {

		// Folder

		DLFolder dlFolder = (DLFolder)folder.getModel();

		dlFolder.setName(TrashUtil.getOriginalTitle(dlFolder.getName()));

		dlFolderPersistence.update(dlFolder);

		TrashEntry trashEntry = trashEntryLocalService.getEntry(
			DLFolder.class.getName(), dlFolder.getFolderId());

		dlFolderLocalService.updateStatus(
			userId, folder.getFolderId(), trashEntry.getStatus(),
			new HashMap<String, Serializable>(), new ServiceContext());

		// File rank

		dlFileRankLocalService.enableFileRanksByFolderId(folder.getFolderId());

		// Folders, file entries, and file shortcuts

		QueryDefinition queryDefinition = new QueryDefinition(
			WorkflowConstants.STATUS_IN_TRASH);

		List<Object> foldersAndFileEntriesAndFileShortcuts =
			dlFolderLocalService.getFoldersAndFileEntriesAndFileShortcuts(
				dlFolder.getGroupId(), dlFolder.getFolderId(), null, false,
				queryDefinition);

		dlAppHelperLocalService.restoreDependentsFromTrash(
			foldersAndFileEntriesAndFileShortcuts, trashEntry.getEntryId());

		// Sync

		registerDLSyncEventCallback(DLSyncConstants.EVENT_RESTORE, folder);

		// Trash

		trashEntryLocalService.deleteEntry(trashEntry.getEntryId());

		// Social

		JSONObject extraDataJSONObject = JSONFactoryUtil.createJSONObject();

		extraDataJSONObject.put("title", folder.getName());

		socialActivityLocalService.addActivity(
			userId, folder.getGroupId(), DLFolderConstants.getClassName(),
			folder.getFolderId(),
			SocialActivityConstants.TYPE_RESTORE_FROM_TRASH,
			extraDataJSONObject.toString(), 0);
	}

	@Override
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

	@Override
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
			if (assetCategoryIds == null) {
				assetCategoryIds = assetCategoryLocalService.getCategoryIds(
					DLFileEntryConstants.getClassName(),
					fileEntry.getFileEntryId());
			}

			if (assetTagNames == null) {
				assetTagNames = assetTagLocalService.getTagNames(
					DLFileEntryConstants.getClassName(),
					fileEntry.getFileEntryId());
			}

			if (assetLinkEntryIds == null) {
				AssetEntry previousAssetEntry = assetEntryLocalService.getEntry(
					DLFileEntryConstants.getClassName(),
					fileEntry.getFileEntryId());

				List<AssetLink> assetLinks =
					assetLinkLocalService.getDirectLinks(
						previousAssetEntry.getEntryId(),
						AssetLinkConstants.TYPE_RELATED);

				assetLinkEntryIds = StringUtil.split(
					ListUtil.toString(
						assetLinks, AssetLink.ENTRY_ID2_ACCESSOR), 0L);
			}

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

	@Override
	public AssetEntry updateAsset(
			long userId, Folder folder, long[] assetCategoryIds,
			String[] assetTagNames, long[] assetLinkEntryIds)
		throws PortalException, SystemException {

		AssetEntry assetEntry = null;

		boolean visible = false;

		if (folder instanceof LiferayFolder) {
			DLFolder dlFolder = (DLFolder)folder.getModel();

			if (dlFolder.isApproved() && !dlFolder.isHidden() &&
				!dlFolder.isInHiddenFolder()) {

				visible = true;
			}
		}
		else {
			visible = true;
		}

		assetEntry = assetEntryLocalService.updateEntry(
			userId, folder.getGroupId(), folder.getCreateDate(),
			folder.getModifiedDate(), DLFolderConstants.getClassName(),
			folder.getFolderId(), folder.getUuid(), 0, assetCategoryIds,
			assetTagNames, visible, null, null, null, null, folder.getName(),
			folder.getDescription(), null, null, null, 0, 0, null, false);

		assetLinkLocalService.updateLinks(
			userId, assetEntry.getEntryId(), assetLinkEntryIds,
			AssetLinkConstants.TYPE_RELATED);

		return assetEntry;
	}

	@Override
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

		registerDLSyncEventCallback(DLSyncConstants.EVENT_UPDATE, fileEntry);
	}

	@Override
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

		registerDLSyncEventCallback(DLSyncConstants.EVENT_UPDATE, fileEntry);
	}

	@Override
	public void updateFolder(
			long userId, Folder folder, ServiceContext serviceContext)
		throws PortalException, SystemException {

		updateAsset(
			userId, folder, serviceContext.getAssetCategoryIds(),
			serviceContext.getAssetTagNames(),
			serviceContext.getAssetLinkEntryIds());

		registerDLSyncEventCallback(DLSyncConstants.EVENT_UPDATE, folder);
	}

	@Override
	public void updateStatus(
			long userId, FileEntry fileEntry, FileVersion latestFileVersion,
			int oldStatus, int newStatus, ServiceContext serviceContext,
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

					AssetEntry draftAssetEntry =
						assetEntryLocalService.fetchEntry(
							DLFileEntryConstants.getClassName(),
							latestFileVersion.getPrimaryKey());

					if (draftAssetEntry != null) {
						long fileEntryTypeId = getFileEntryTypeId(fileEntry);

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

						assetEntryLocalService.deleteEntry(draftAssetEntry);
					}
				}

				assetEntryLocalService.updateVisible(
					DLFileEntryConstants.getClassName(),
					fileEntry.getFileEntryId(), true);
			}

			// Sync

			String event = (String)workflowContext.get("event");

			if (Validator.isNotNull(event)) {
				registerDLSyncEventCallback(event, fileEntry);
			}

			if ((oldStatus != WorkflowConstants.STATUS_IN_TRASH) &&
				!fileEntry.isInTrash()) {

				// Social

				Date activityCreateDate = latestFileVersion.getModifiedDate();
				int activityType = DLActivityKeys.UPDATE_FILE_ENTRY;

				if (event.equals(DLSyncConstants.EVENT_ADD)) {
					activityCreateDate = latestFileVersion.getCreateDate();
					activityType = DLActivityKeys.ADD_FILE_ENTRY;
				}

				JSONObject extraDataJSONObject =
					JSONFactoryUtil.createJSONObject();

				extraDataJSONObject.put("title", fileEntry.getTitle());

				socialActivityLocalService.addUniqueActivity(
					latestFileVersion.getStatusByUserId(),
					fileEntry.getGroupId(), activityCreateDate,
					DLFileEntryConstants.getClassName(),
					fileEntry.getFileEntryId(), activityType,
					extraDataJSONObject.toString(), 0);

				// Subscriptions

				notifySubscribers(
					latestFileVersion,
					(String)workflowContext.get(WorkflowConstants.CONTEXT_URL),
					serviceContext);
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

	protected FileEntry doMoveFileEntryFromTrash(
			long userId, FileEntry fileEntry, long newFolderId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		// File entry

		DLFileEntry dlFileEntry = (DLFileEntry)fileEntry.getModel();

		if (dlFileEntry.isInTrashExplicitly()) {
			restoreFileEntryFromTrash(userId, fileEntry);

			fileEntry = dlAppLocalService.moveFileEntry(
				userId, fileEntry.getFileEntryId(), newFolderId,
				serviceContext);

			if (DLAppHelperThreadLocal.isEnabled()) {
				dlFileRankLocalService.enableFileRanks(
					fileEntry.getFileEntryId());
			}

			return fileEntry;
		}

		TrashEntry trashEntry = dlFileEntry.getTrashEntry();

		List<DLFileVersion> dlFileVersions =
			dlFileVersionLocalService.getFileVersions(
				fileEntry.getFileEntryId(), WorkflowConstants.STATUS_IN_TRASH);

		dlFileVersions = ListUtil.sort(
			dlFileVersions, new FileVersionVersionComparator());

		FileVersion fileVersion = new LiferayFileVersion(dlFileVersions.get(0));

		TrashVersion trashVersion = trashVersionLocalService.fetchVersion(
			trashEntry.getEntryId(), DLFileVersion.class.getName(),
			fileVersion.getFileVersionId());

		int oldStatus = WorkflowConstants.STATUS_APPROVED;

		if (trashVersion != null) {
			oldStatus = trashVersion.getStatus();
		}

		dlFileEntryLocalService.updateStatus(
			userId, fileVersion.getFileVersionId(), oldStatus, serviceContext,
			new HashMap<String, Serializable>());

		// File versions

		for (DLFileVersion dlFileVersion : dlFileVersions) {

			// File version

			trashVersion = trashVersionLocalService.fetchVersion(
				trashEntry.getEntryId(), DLFileVersion.class.getName(),
				dlFileVersion.getFileVersionId());

			oldStatus = WorkflowConstants.STATUS_APPROVED;

			if (trashVersion != null) {
				oldStatus = trashVersion.getStatus();
			}

			dlFileVersion.setStatus(oldStatus);

			dlFileVersionPersistence.update(dlFileVersion);

			// Trash

			if (trashVersion != null) {
				trashVersionLocalService.deleteTrashVersion(trashVersion);
			}
		}

		if (DLAppHelperThreadLocal.isEnabled()) {

			// File rank

			dlFileRankLocalService.enableFileRanks(fileEntry.getFileEntryId());

			// File shortcut

			dlFileShortcutLocalService.enableFileShortcuts(
				fileEntry.getFileEntryId());
		}

		// App helper

		fileEntry = dlAppService.moveFileEntry(
			fileEntry.getFileEntryId(), newFolderId, serviceContext);

		// Sync

		registerDLSyncEventCallback(DLSyncConstants.EVENT_RESTORE, fileEntry);

		// Social

		JSONObject extraDataJSONObject = JSONFactoryUtil.createJSONObject();

		extraDataJSONObject.put("title", fileEntry.getTitle());

		socialActivityLocalService.addActivity(
			userId, fileEntry.getGroupId(), DLFileEntryConstants.getClassName(),
			fileEntry.getFileEntryId(),
			SocialActivityConstants.TYPE_RESTORE_FROM_TRASH,
			extraDataJSONObject.toString(), 0);

		return fileEntry;
	}

	protected FileEntry doMoveFileEntryToTrash(long userId, FileEntry fileEntry)
		throws PortalException, SystemException {

		// File versions

		List<DLFileVersion> dlFileVersions =
			dlFileVersionLocalService.getFileVersions(
				fileEntry.getFileEntryId(), WorkflowConstants.STATUS_ANY);

		dlFileVersions = ListUtil.sort(
			dlFileVersions, new FileVersionVersionComparator());

		FileVersion fileVersion = fileEntry.getFileVersion();

		int oldStatus = fileVersion.getStatus();

		dlFileEntryLocalService.updateStatus(
			userId, fileVersion.getFileVersionId(),
			WorkflowConstants.STATUS_IN_TRASH, new ServiceContext(),
			new HashMap<String, Serializable>());

		if (DLAppHelperThreadLocal.isEnabled()) {

			// File shortcut

			dlFileShortcutLocalService.disableFileShortcuts(
				fileEntry.getFileEntryId());

			// File rank

			dlFileRankLocalService.disableFileRanks(fileEntry.getFileEntryId());

			// Sync

			registerDLSyncEventCallback(DLSyncConstants.EVENT_TRASH, fileEntry);
		}

		// Trash

		int oldDLFileVersionStatus = WorkflowConstants.STATUS_ANY;

		List<ObjectValuePair<Long, Integer>> dlFileVersionStatusOVPs =
			new ArrayList<ObjectValuePair<Long, Integer>>();

		DLFileVersion oldDLFileVersion = (DLFileVersion)fileVersion.getModel();

		oldDLFileVersionStatus = oldDLFileVersion.getStatus();

		if ((dlFileVersions != null) && !dlFileVersions.isEmpty()) {
			dlFileVersionStatusOVPs = getDlFileVersionStatuses(dlFileVersions);
		}

		for (DLFileVersion curDLFileVersion : dlFileVersions) {
			curDLFileVersion.setStatus(WorkflowConstants.STATUS_IN_TRASH);

			dlFileVersionPersistence.update(curDLFileVersion);
		}

		DLFileEntry dlFileEntry = (DLFileEntry)fileEntry.getModel();

		UnicodeProperties typeSettingsProperties = new UnicodeProperties();

		typeSettingsProperties.put("title", dlFileEntry.getTitle());

		TrashEntry trashEntry = trashEntryLocalService.addTrashEntry(
			userId, dlFileEntry.getGroupId(),
			DLFileEntryConstants.getClassName(), dlFileEntry.getFileEntryId(),
			dlFileEntry.getUuid(), dlFileEntry.getClassName(),
			oldDLFileVersionStatus, dlFileVersionStatusOVPs,
			typeSettingsProperties);

		String trashTitle = TrashUtil.getTrashTitle(trashEntry.getEntryId());

		dlFileEntry.setTitle(trashTitle);

		dlFileEntryPersistence.update(dlFileEntry);

		if (!DLAppHelperThreadLocal.isEnabled()) {
			return fileEntry;
		}

		// Social

		JSONObject extraDataJSONObject = JSONFactoryUtil.createJSONObject();

		extraDataJSONObject.put(
			"title", TrashUtil.getOriginalTitle(fileEntry.getTitle()));

		socialActivityLocalService.addActivity(
			userId, fileEntry.getGroupId(), DLFileEntryConstants.getClassName(),
			fileEntry.getFileEntryId(),
			SocialActivityConstants.TYPE_MOVE_TO_TRASH,
			extraDataJSONObject.toString(), 0);

		// Workflow

		if (oldStatus == WorkflowConstants.STATUS_PENDING) {
			workflowInstanceLinkLocalService.deleteWorkflowInstanceLink(
				fileVersion.getCompanyId(), fileVersion.getGroupId(),
				DLFileEntryConstants.getClassName(),
				fileVersion.getFileVersionId());
		}

		return fileEntry;
	}

	protected Folder doMoveFolderFromTrash(
			long userId, Folder folder, long parentFolderId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		DLFolder dlFolder = (DLFolder)folder.getModel();

		if (dlFolder.isInTrashExplicitly()) {
			restoreFolderFromTrash(userId, folder);
		}
		else {

			// Folder

			TrashEntry trashEntry = dlFolder.getTrashEntry();

			TrashVersion trashVersion =
				trashVersionLocalService.fetchVersion(
					trashEntry.getEntryId(), DLFolder.class.getName(),
					dlFolder.getFolderId());

			int status = WorkflowConstants.STATUS_APPROVED;

			if (trashVersion != null) {
				status = trashVersion.getStatus();
			}

			dlFolderLocalService.updateStatus(
				userId, folder.getFolderId(), status,
				new HashMap<String, Serializable>(), new ServiceContext());

			// File rank

			dlFileRankLocalService.enableFileRanksByFolderId(
				folder.getFolderId());

			// Trash

			if (trashVersion != null) {
				trashVersionLocalService.deleteTrashVersion(trashVersion);
			}

			// Folders, file entries, and file shortcuts

			QueryDefinition queryDefinition = new QueryDefinition(
				WorkflowConstants.STATUS_IN_TRASH);

			List<Object> foldersAndFileEntriesAndFileShortcuts =
				dlFolderLocalService.getFoldersAndFileEntriesAndFileShortcuts(
					dlFolder.getGroupId(), dlFolder.getFolderId(), null, false,
					queryDefinition);

			dlAppHelperLocalService.restoreDependentsFromTrash(
				foldersAndFileEntriesAndFileShortcuts, trashEntry.getEntryId());

			// Sync

			registerDLSyncEventCallback(DLSyncConstants.EVENT_RESTORE, folder);

			// Social

			JSONObject extraDataJSONObject = JSONFactoryUtil.createJSONObject();

			extraDataJSONObject.put("title", folder.getName());

			socialActivityLocalService.addActivity(
				userId, folder.getGroupId(), DLFolderConstants.class.getName(),
				folder.getFolderId(),
				SocialActivityConstants.TYPE_RESTORE_FROM_TRASH,
				extraDataJSONObject.toString(), 0);
		}

		return dlAppLocalService.moveFolder(
			userId, folder.getFolderId(), parentFolderId, serviceContext);
	}

	protected Folder doMoveFolderToTrash(long userId, Folder folder)
		throws PortalException, SystemException {

		// Folder

		DLFolder dlFolder = dlFolderLocalService.updateStatus(
			userId, folder.getFolderId(), WorkflowConstants.STATUS_IN_TRASH,
			new HashMap<String, Serializable>(), new ServiceContext());

		// File rank

		dlFileRankLocalService.disableFileRanksByFolderId(folder.getFolderId());

		// Trash

		UnicodeProperties typeSettingsProperties = new UnicodeProperties();

		typeSettingsProperties.put("title", dlFolder.getName());

		TrashEntry trashEntry = trashEntryLocalService.addTrashEntry(
			userId, dlFolder.getGroupId(), DLFolderConstants.getClassName(),
			dlFolder.getFolderId(), dlFolder.getUuid(), null,
			WorkflowConstants.STATUS_APPROVED, null, typeSettingsProperties);

		dlFolder.setName(TrashUtil.getTrashTitle(trashEntry.getEntryId()));

		dlFolderPersistence.update(dlFolder);

		// Folders, file entries, and file shortcuts

		QueryDefinition queryDefinition = new QueryDefinition(
			WorkflowConstants.STATUS_ANY);

		List<Object> foldersAndFileEntriesAndFileShortcuts =
			dlFolderLocalService.getFoldersAndFileEntriesAndFileShortcuts(
				dlFolder.getGroupId(), dlFolder.getFolderId(), null, false,
				queryDefinition);

		dlAppHelperLocalService.moveDependentsToTrash(
			foldersAndFileEntriesAndFileShortcuts, trashEntry.getEntryId());

		// Sync

		registerDLSyncEventCallback(DLSyncConstants.EVENT_TRASH, folder);

		// Social

		JSONObject extraDataJSONObject = JSONFactoryUtil.createJSONObject();

		extraDataJSONObject.put("title", folder.getName());

		socialActivityLocalService.addActivity(
			userId, folder.getGroupId(), DLFolderConstants.getClassName(),
			folder.getFolderId(), SocialActivityConstants.TYPE_MOVE_TO_TRASH,
			extraDataJSONObject.toString(), 0);

		return new LiferayFolder(dlFolder);
	}

	protected List<ObjectValuePair<Long, Integer>> getDlFileVersionStatuses(
		List<DLFileVersion> dlFileVersions) {

		List<ObjectValuePair<Long, Integer>> dlFileVersionStatusOVPs =
			new ArrayList<ObjectValuePair<Long, Integer>>(
				dlFileVersions.size());

		for (DLFileVersion dlFileVersion : dlFileVersions) {
			int status = dlFileVersion.getStatus();

			if (status == WorkflowConstants.STATUS_PENDING) {
				status = WorkflowConstants.STATUS_DRAFT;
			}

			ObjectValuePair<Long, Integer> dlFileVersionStatusOVP =
				new ObjectValuePair<Long, Integer>(
					dlFileVersion.getFileVersionId(), status);

			dlFileVersionStatusOVPs.add(dlFileVersionStatusOVP);
		}

		return dlFileVersionStatusOVPs;
	}

	protected String getEntryURL(
			long groupId, long fileEntryId, ServiceContext serviceContext)
		throws PortalException, SystemException {

		HttpServletRequest request = serviceContext.getRequest();

		if (request == null) {
			return StringPool.BLANK;
		}

		String portletId = PortletKeys.DOCUMENT_LIBRARY;

		long plid = serviceContext.getPlid();

		long controlPanelPlid = PortalUtil.getControlPanelPlid(
			serviceContext.getCompanyId());

		if (plid == controlPanelPlid) {
			plid = PortalUtil.getPlidFromPortletId(
				groupId, PortletKeys.DOCUMENT_LIBRARY);
		}

		if (plid == LayoutConstants.DEFAULT_PLID) {
			portletId = PortletKeys.DOCUMENT_LIBRARY_ADMIN;
			plid = controlPanelPlid;
		}

		PortletURL portletURL = PortletURLFactoryUtil.create(
			request, portletId, plid, PortletRequest.RENDER_PHASE);

		portletURL.setParameter(
			"struts_action", "/document_library/view_file_entry");
		portletURL.setParameter("fileEntryId", String.valueOf(fileEntryId));

		return portletURL.toString();
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

	protected void notifySubscribers(
			FileVersion fileVersion, String entryURL,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		if (!fileVersion.isApproved() || Validator.isNull(entryURL)) {
			return;
		}

		DLSettings dlSettings = DLUtil.getDLSettings(fileVersion.getGroupId());

		if (serviceContext.isCommandAdd() &&
			dlSettings.getEmailFileEntryAddedEnabled()) {
		}
		else if (serviceContext.isCommandUpdate() &&
				 dlSettings.getEmailFileEntryUpdatedEnabled()) {
		}
		else {
			return;
		}

		String entryTitle = fileVersion.getTitle();

		String fromName = dlSettings.getEmailFromName();
		String fromAddress = dlSettings.getEmailFromAddress();

		Map<Locale, String> localizedSubjectMap = null;
		Map<Locale, String> localizedBodyMap = null;

		if (serviceContext.isCommandUpdate()) {
			localizedSubjectMap = dlSettings.getEmailFileEntryUpdatedSubject();
			localizedBodyMap = dlSettings.getEmailFileEntryUpdatedBody();
		}
		else {
			localizedSubjectMap = dlSettings.getEmailFileEntryAddedSubject();
			localizedBodyMap = dlSettings.getEmailFileEntryAddedBody();
		}

		FileEntry fileEntry = fileVersion.getFileEntry();

		Folder folder = null;

		long folderId = fileEntry.getFolderId();

		if (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			folder = dlAppLocalService.getFolder(folderId);
		}

		String folderName = LanguageUtil.get(
			serviceContext.getLocale(), "home");

		if (folder != null) {
			folderName = folder.getName();
		}

		SubscriptionSender subscriptionSender = new SubscriptionSender();

		DLFileEntry dlFileEntry = (DLFileEntry)fileEntry.getModel();

		DLFileEntryType dlFileEntryType =
			dlFileEntryTypeLocalService.getDLFileEntryType(
				dlFileEntry.getFileEntryTypeId());

		subscriptionSender.setClassPK(fileVersion.getFileEntryId());
		subscriptionSender.setClassName(DLFileEntryConstants.getClassName());
		subscriptionSender.setCompanyId(fileVersion.getCompanyId());
		subscriptionSender.setContextAttributes(
			"[$DOCUMENT_STATUS_BY_USER_NAME$]",
			fileVersion.getStatusByUserName(), "[$DOCUMENT_TITLE$]", entryTitle,
			"[$DOCUMENT_TYPE$]",
			dlFileEntryType.getName(serviceContext.getLocale()),
			"[$DOCUMENT_URL$]", entryURL, "[$FOLDER_NAME$]", folderName);
		subscriptionSender.setContextUserPrefix("DOCUMENT");
		subscriptionSender.setEntryTitle(entryTitle);
		subscriptionSender.setEntryURL(entryURL);
		subscriptionSender.setFrom(fromAddress, fromName);
		subscriptionSender.setHtmlFormat(true);
		subscriptionSender.setLocalizedBodyMap(localizedBodyMap);
		subscriptionSender.setLocalizedSubjectMap(localizedSubjectMap);
		subscriptionSender.setMailId(
			"file_entry", fileVersion.getFileEntryId());

		int notificationType =
			UserNotificationDefinition.NOTIFICATION_TYPE_ADD_ENTRY;

		if (serviceContext.isCommandUpdate()) {
			notificationType =
				UserNotificationDefinition.NOTIFICATION_TYPE_UPDATE_ENTRY;
		}

		subscriptionSender.setNotificationType(notificationType);

		subscriptionSender.setPortletId(PortletKeys.DOCUMENT_LIBRARY);
		subscriptionSender.setReplyToAddress(fromAddress);
		subscriptionSender.setScopeGroupId(fileVersion.getGroupId());
		subscriptionSender.setServiceContext(serviceContext);
		subscriptionSender.setUserId(fileVersion.getUserId());

		subscriptionSender.addPersistedSubscribers(
			Folder.class.getName(), fileVersion.getGroupId());

		if (folder != null) {
			subscriptionSender.addPersistedSubscribers(
				Folder.class.getName(), folder.getFolderId());

			for (Long ancestorFolderId : folder.getAncestorFolderIds()) {
				subscriptionSender.addPersistedSubscribers(
					Folder.class.getName(), ancestorFolderId);
			}
		}

		if (dlFileEntryType.getFileEntryTypeId() ==
				DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT) {

			subscriptionSender.addPersistedSubscribers(
				DLFileEntryType.class.getName(), fileVersion.getGroupId());
		}
		else {
			subscriptionSender.addPersistedSubscribers(
				DLFileEntryType.class.getName(),
				dlFileEntryType.getFileEntryTypeId());
		}

		subscriptionSender.addPersistedSubscribers(
			DLFileEntry.class.getName(), fileEntry.getFileEntryId());

		subscriptionSender.flushNotificationsAsync();
	}

	protected void registerDLProcessorCallback(
		final FileEntry fileEntry, final FileVersion fileVersion) {

		TransactionCommitCallbackRegistryUtil.registerCallback(
			new Callable<Void>() {

				@Override
				public Void call() throws Exception {
					DLProcessorRegistryUtil.trigger(
						fileEntry, fileVersion, true);

					return null;
				}

			});
	}

	protected void registerDLSyncEventCallback(
			final String event, final String type, final long typePK)
		throws SystemException {

		DLSyncEvent dlSyncEvent = dlSyncEventLocalService.addDLSyncEvent(
			event, type, typePK);

		final long modifiedTime = dlSyncEvent.getModifiedTime();

		TransactionCommitCallbackRegistryUtil.registerCallback(
			new Callable<Void>() {

				@Override
				public Void call() throws Exception {
					Message message = new Message();

					Map<String, Object> values = new HashMap<String, Object>(4);

					values.put("event", event);
					values.put("modifiedTime", modifiedTime);
					values.put("type", type);
					values.put("typePK", typePK);

					message.setValues(values);

					MessageBusUtil.sendMessage(
						DestinationNames.DOCUMENT_LIBRARY_SYNC_EVENT_PROCESSOR,
						message);

					return null;
				}

			}
		);
	}

}
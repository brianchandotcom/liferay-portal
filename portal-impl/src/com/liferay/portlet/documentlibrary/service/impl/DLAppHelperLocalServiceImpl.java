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

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.WildcardMode;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.lock.Lock;
import com.liferay.portal.kernel.notifications.UserNotificationDefinition;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.Repository;
import com.liferay.portal.kernel.repository.RepositoryProviderUtil;
import com.liferay.portal.kernel.repository.capabilities.RepositoryEventTriggerCapability;
import com.liferay.portal.kernel.repository.event.RepositoryEventType;
import com.liferay.portal.kernel.repository.event.TrashRepositoryEventType;
import com.liferay.portal.kernel.repository.event.WorkflowRepositoryEventType;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.repository.model.RepositoryModel;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;
import com.liferay.portal.kernel.social.SocialActivityManagerUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.UserConstants;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileEntry;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileVersion;
import com.liferay.portal.repository.liferayrepository.model.LiferayFolder;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.GroupSubscriptionCheckSubscriptionSender;
import com.liferay.portal.util.SubscriptionSender;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetLink;
import com.liferay.portlet.asset.model.AssetLinkConstants;
import com.liferay.portlet.documentlibrary.DLGroupServiceSettings;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryConstants;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.model.DLFileEntryTypeConstants;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.model.DLFileShortcutConstants;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.model.DLSyncConstants;
import com.liferay.portlet.documentlibrary.service.DLAppService;
import com.liferay.portlet.documentlibrary.service.base.DLAppHelperLocalServiceBaseImpl;
import com.liferay.portlet.documentlibrary.service.permission.DLPermission;
import com.liferay.portlet.documentlibrary.social.DLActivityKeys;
import com.liferay.portlet.documentlibrary.util.DLAppHelperThreadLocal;
import com.liferay.portlet.documentlibrary.util.comparator.DLFileVersionVersionComparator;
import com.liferay.portlet.social.model.SocialActivityConstants;
import com.liferay.trash.kernel.model.TrashEntry;
import com.liferay.trash.kernel.model.TrashVersion;
import com.liferay.trash.kernel.util.TrashUtil;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Provides the local service helper for the document library application.
 *
 * @author Alexander Chow
 */
public class DLAppHelperLocalServiceImpl
	extends DLAppHelperLocalServiceBaseImpl {

	@Override
	public void addFolder(
			long userId, Folder folder, ServiceContext serviceContext)
		throws PortalException {

		if (!DLAppHelperThreadLocal.isEnabled()) {
			return;
		}

		updateAsset(
			userId, folder, serviceContext.getAssetCategoryIds(),
			serviceContext.getAssetTagNames(),
			serviceContext.getAssetLinkEntryIds());
	}

	@Override
	public void cancelCheckOut(
			long userId, FileEntry fileEntry, FileVersion sourceFileVersion,
			FileVersion destinationFileVersion, FileVersion draftFileVersion,
			ServiceContext serviceContext)
		throws PortalException {

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
		throws PortalException {

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
				null, 0, 0, null);
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
				fileEntry.getDescription(), null, null, null, 0, 0, null);

			List<AssetLink> assetLinks = assetLinkLocalService.getDirectLinks(
				fileEntryAssetEntry.getEntryId());

			long[] assetLinkIds = ListUtil.toLongArray(
				assetLinks, AssetLink.ENTRY_ID2_ACCESSOR);

			assetLinkLocalService.updateLinks(
				userId, fileVersionAssetEntry.getEntryId(), assetLinkIds,
				AssetLinkConstants.TYPE_RELATED);
		}
	}

	@Override
	public void deleteFileEntry(FileEntry fileEntry) throws PortalException {
		if (!DLAppHelperThreadLocal.isEnabled()) {
			return;
		}

		// Subscriptions

		subscriptionLocalService.deleteSubscriptions(
			fileEntry.getCompanyId(), DLFileEntryConstants.getClassName(),
			fileEntry.getFileEntryId());

		// File ranks

		dlFileRankLocalService.deleteFileRanksByFileEntryId(
			fileEntry.getFileEntryId());

		// File shortcuts

		dlFileShortcutLocalService.deleteFileShortcuts(
			fileEntry.getFileEntryId());

		// Asset

		assetEntryLocalService.deleteEntry(
			DLFileEntryConstants.getClassName(), fileEntry.getFileEntryId());

		// Ratings

		ratingsStatsLocalService.deleteStats(
			DLFileEntryConstants.getClassName(), fileEntry.getFileEntryId());
	}

	@Override
	public void deleteFolder(Folder folder) throws PortalException {
		if (!DLAppHelperThreadLocal.isEnabled()) {
			return;
		}

		// Asset

		assetEntryLocalService.deleteEntry(
			DLFolderConstants.getClassName(), folder.getFolderId());
	}

	@Override
	public void deleteRepositoryFileEntries(long repositoryId)
		throws PortalException {

		LocalRepository localRepository =
			RepositoryProviderUtil.getLocalRepository(repositoryId);

		List<FileEntry> fileEntries = localRepository.getRepositoryFileEntries(
			UserConstants.USER_ID_DEFAULT,
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);

		for (FileEntry fileEntry : fileEntries) {
			deleteFileEntry(fileEntry);
		}
	}

	@Override
	public void getFileAsStream(
		long userId, FileEntry fileEntry, boolean incrementCounter) {

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
				userId, DLFileShortcutConstants.getClassName(),
				fileShortcut.getFileShortcutId(), 1);
		}
	}

	@Override
	public List<DLFileShortcut> getFileShortcuts(
		long groupId, long folderId, boolean active, int status) {

		return dlFileShortcutPersistence.findByG_F_A_S(
			groupId, folderId, active, status);
	}

	@Override
	public int getFileShortcutsCount(
		long groupId, long folderId, boolean active, int status) {

		return dlFileShortcutPersistence.countByG_F_A_S(
			groupId, folderId, active, status);
	}

	@Override
	public List<FileEntry> getNoAssetFileEntries() {
		return null;
	}

	@Override
	public void moveDependentsToTrash(DLFolder dlFolder)
		throws PortalException {

		trashOrRestoreFolder(dlFolder, true);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #moveDependentsToTrash(DLFolder)}
	 */
	@Deprecated
	@Override
	public void moveDependentsToTrash(
			List<Object> dlFileEntriesAndDLFolders, long trashEntryId)
		throws PortalException {

		if (dlFileEntriesAndDLFolders.isEmpty()) {
			return;
		}

		Object object = dlFileEntriesAndDLFolders.get(0);

		long folderId = 0;

		if (object instanceof DLFileEntry) {
			DLFileEntry dlFileEntry = (DLFileEntry)object;

			folderId = dlFileEntry.getFolderId();
		}
		else if (object instanceof DLFileShortcut) {
			DLFileShortcut dlFileShortcut = (DLFileShortcut)object;

			folderId = dlFileShortcut.getFolderId();
		}
		else if (object instanceof DLFolder) {
			DLFolder dlFolder = (DLFolder)object;

			folderId = dlFolder.getFolderId();
		}

		DLFolder dlFolder = dlFolderLocalService.getDLFolder(folderId);

		moveDependentsToTrash(dlFolder);
	}

	@Override
	public FileEntry moveFileEntryFromTrash(
			long userId, FileEntry fileEntry, long newFolderId,
			ServiceContext serviceContext)
		throws PortalException {

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
	 */
	@Override
	public FileEntry moveFileEntryToTrash(long userId, FileEntry fileEntry)
		throws PortalException {

		boolean hasLock = dlFileEntryLocalService.hasFileEntryLock(
			userId, fileEntry.getFileEntryId());

		if (!hasLock) {
			dlFileEntryLocalService.lockFileEntry(
				userId, fileEntry.getFileEntryId());
		}

		try {
			if (fileEntry.isCheckedOut()) {
				dlFileEntryLocalService.cancelCheckOut(
					userId, fileEntry.getFileEntryId());
			}

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
	public FileShortcut moveFileShortcutFromTrash(
			long userId, FileShortcut fileShortcut, long newFolderId,
			ServiceContext serviceContext)
		throws PortalException {

		DLFileShortcut dlFileShortcut = (DLFileShortcut)fileShortcut.getModel();

		if (dlFileShortcut.isInTrashExplicitly()) {
			restoreFileShortcutFromTrash(userId, fileShortcut);
		}
		else {

			// File shortcut

			TrashVersion trashVersion = trashVersionLocalService.fetchVersion(
				DLFileShortcutConstants.getClassName(),
				fileShortcut.getFileShortcutId());

			int status = WorkflowConstants.STATUS_APPROVED;

			if (trashVersion != null) {
				status = trashVersion.getStatus();
			}

			dlFileShortcutLocalService.updateStatus(
				userId, fileShortcut.getFileShortcutId(), status,
				new ServiceContext());

			// Trash

			if (trashVersion != null) {
				trashVersionLocalService.deleteTrashVersion(trashVersion);
			}

			// Social

			JSONObject extraDataJSONObject = JSONFactoryUtil.createJSONObject();

			extraDataJSONObject.put("title", fileShortcut.getToTitle());

			SocialActivityManagerUtil.addActivity(
				userId, fileShortcut,
				SocialActivityConstants.TYPE_RESTORE_FROM_TRASH,
				extraDataJSONObject.toString(), 0);
		}

		return dlAppService.updateFileShortcut(
			fileShortcut.getFileShortcutId(), newFolderId,
			fileShortcut.getToFileEntryId(), serviceContext);
	}

	/**
	 * Moves the file shortcut to the recycle bin.
	 *
	 * @param  userId the primary key of the user moving the file shortcut
	 * @param  fileShortcut the file shortcut to be moved
	 * @return the moved file shortcut
	 */
	@Override
	public FileShortcut moveFileShortcutToTrash(
			long userId, FileShortcut fileShortcut)
		throws PortalException {

		// File shortcut

		DLFileShortcut dlFileShortcut = (DLFileShortcut)fileShortcut.getModel();

		int oldStatus = dlFileShortcut.getStatus();

		dlFileShortcutLocalService.updateStatus(
			userId, fileShortcut.getFileShortcutId(),
			WorkflowConstants.STATUS_IN_TRASH, new ServiceContext());

		// Social

		JSONObject extraDataJSONObject = JSONFactoryUtil.createJSONObject();

		extraDataJSONObject.put(
			"title", TrashUtil.getOriginalTitle(fileShortcut.getToTitle()));

		SocialActivityManagerUtil.addActivity(
			userId, fileShortcut, SocialActivityConstants.TYPE_MOVE_TO_TRASH,
			extraDataJSONObject.toString(), 0);

		// Trash

		trashEntryLocalService.addTrashEntry(
			userId, fileShortcut.getGroupId(),
			DLFileShortcutConstants.getClassName(),
			fileShortcut.getFileShortcutId(), fileShortcut.getUuid(), null,
			oldStatus, null, null);

		return fileShortcut;
	}

	@Override
	public Folder moveFolderFromTrash(
			long userId, Folder folder, long parentFolderId,
			ServiceContext serviceContext)
		throws PortalException {

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
	 */
	@Override
	public Folder moveFolderToTrash(long userId, Folder folder)
		throws PortalException {

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
	public void restoreDependentsFromTrash(DLFolder dlFolder)
		throws PortalException {

		trashOrRestoreFolder(dlFolder, false);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #restoreDependentsFromTrash(DLFolder)}
	 */
	@Deprecated
	@Override
	public void restoreDependentsFromTrash(
			List<Object> dlFileEntriesAndDLFolders)
		throws PortalException {

		if (dlFileEntriesAndDLFolders.isEmpty()) {
			return;
		}

		Object object = dlFileEntriesAndDLFolders.get(0);

		long folderId = 0;

		if (object instanceof DLFileEntry) {
			DLFileEntry dlFileEntry = (DLFileEntry)object;

			folderId = dlFileEntry.getFolderId();
		}
		else if (object instanceof DLFileShortcut) {
			DLFileShortcut dlFileShortcut = (DLFileShortcut)object;

			folderId = dlFileShortcut.getFolderId();
		}
		else if (object instanceof DLFolder) {
			DLFolder dlFolder = (DLFolder)object;

			folderId = dlFolder.getFolderId();
		}

		DLFolder dlFolder = dlFolderLocalService.getDLFolder(folderId);

		restoreDependentsFromTrash(dlFolder);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #restoreDependentsFromTrash(List)}
	 */
	@Deprecated
	@Override
	public void restoreDependentsFromTrash(
			List<Object> dlFileEntriesAndDLFolders, long trashEntryId)
		throws PortalException {

		restoreDependentsFromTrash(dlFileEntriesAndDLFolders);
	}

	@Override
	public void restoreFileEntryFromTrash(long userId, FileEntry fileEntry)
		throws PortalException {

		// File entry

		DLFileEntry dlFileEntry = (DLFileEntry)fileEntry.getModel();

		dlFileEntry.setFileName(
			TrashUtil.getOriginalTitle(dlFileEntry.getTitle(), "fileName"));
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

			triggerRepositoryEvent(
				fileEntry.getRepositoryId(),
				TrashRepositoryEventType.EntryRestored.class, FileEntry.class,
				fileEntry);
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

		SocialActivityManagerUtil.addActivity(
			userId, fileEntry, SocialActivityConstants.TYPE_RESTORE_FROM_TRASH,
			extraDataJSONObject.toString(), 0);
	}

	@Override
	public void restoreFileShortcutFromTrash(
			long userId, FileShortcut fileShortcut)
		throws PortalException {

		// File shortcut

		TrashEntry trashEntry = trashEntryLocalService.getEntry(
			DLFileShortcutConstants.getClassName(),
			fileShortcut.getFileShortcutId());

		dlFileShortcutLocalService.updateStatus(
			userId, fileShortcut.getFileShortcutId(), trashEntry.getStatus(),
			new ServiceContext());

		// Social

		JSONObject extraDataJSONObject = JSONFactoryUtil.createJSONObject();

		extraDataJSONObject.put("title", fileShortcut.getToTitle());

		SocialActivityManagerUtil.addActivity(
			userId, fileShortcut,
			SocialActivityConstants.TYPE_RESTORE_FROM_TRASH,
			extraDataJSONObject.toString(), 0);

		// Trash

		trashEntryLocalService.deleteEntry(trashEntry.getEntryId());
	}

	@Override
	public void restoreFolderFromTrash(long userId, Folder folder)
		throws PortalException {

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

		restoreDependentsFromTrash(dlFolder);

		// Sync

		triggerRepositoryEvent(
			folder.getRepositoryId(),
			TrashRepositoryEventType.EntryRestored.class, Folder.class, folder);

		// Trash

		trashEntryLocalService.deleteEntry(trashEntry.getEntryId());

		// Social

		JSONObject extraDataJSONObject = JSONFactoryUtil.createJSONObject();

		extraDataJSONObject.put("title", folder.getName());

		SocialActivityManagerUtil.addActivity(
			userId, folder, SocialActivityConstants.TYPE_RESTORE_FROM_TRASH,
			extraDataJSONObject.toString(), 0);
	}

	@Override
	public AssetEntry updateAsset(
			long userId, FileEntry fileEntry, FileVersion fileVersion,
			long assetClassPk)
		throws PortalException {

		long[] assetCategoryIds = assetCategoryLocalService.getCategoryIds(
			DLFileEntryConstants.getClassName(), assetClassPk);
		String[] assetTagNames = assetTagLocalService.getTagNames(
			DLFileEntryConstants.getClassName(), assetClassPk);

		AssetEntry assetEntry = assetEntryLocalService.getEntry(
			DLFileEntryConstants.getClassName(), assetClassPk);

		List<AssetLink> assetLinks = assetLinkLocalService.getDirectLinks(
			assetEntry.getEntryId());

		long[] assetLinkIds = ListUtil.toLongArray(
			assetLinks, AssetLink.ENTRY_ID2_ACCESSOR);

		return updateAsset(
			userId, fileEntry, fileVersion, assetCategoryIds, assetTagNames,
			assetLinkIds);
	}

	@Override
	public AssetEntry updateAsset(
			long userId, FileEntry fileEntry, FileVersion fileVersion,
			long[] assetCategoryIds, String[] assetTagNames,
			long[] assetLinkEntryIds)
		throws PortalException {

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

				assetLinkEntryIds = ListUtil.toLongArray(
					assetLinks, AssetLink.ENTRY_ID2_ACCESSOR);
			}

			assetEntry = assetEntryLocalService.updateEntry(
				userId, fileEntry.getGroupId(), fileEntry.getCreateDate(),
				fileEntry.getModifiedDate(),
				DLFileEntryConstants.getClassName(),
				fileVersion.getFileVersionId(), fileEntry.getUuid(),
				fileEntryTypeId, assetCategoryIds, assetTagNames, false, null,
				null, null, fileEntry.getMimeType(), fileEntry.getTitle(),
				fileEntry.getDescription(), null, null, null, 0, 0, null);
		}
		else {
			assetEntry = assetEntryLocalService.updateEntry(
				userId, fileEntry.getGroupId(), fileEntry.getCreateDate(),
				fileEntry.getModifiedDate(),
				DLFileEntryConstants.getClassName(), fileEntry.getFileEntryId(),
				fileEntry.getUuid(), fileEntryTypeId, assetCategoryIds,
				assetTagNames, visible, null, null, null,
				fileEntry.getMimeType(), fileEntry.getTitle(),
				fileEntry.getDescription(), null, null, null, 0, 0, null);

			List<DLFileShortcut> dlFileShortcuts =
				dlFileShortcutPersistence.findByToFileEntryId(
					fileEntry.getFileEntryId());

			for (DLFileShortcut dlFileShortcut : dlFileShortcuts) {
				assetEntryLocalService.updateEntry(
					userId, dlFileShortcut.getGroupId(),
					dlFileShortcut.getCreateDate(),
					dlFileShortcut.getModifiedDate(),
					DLFileShortcutConstants.getClassName(),
					dlFileShortcut.getFileShortcutId(),
					dlFileShortcut.getUuid(), fileEntryTypeId, assetCategoryIds,
					assetTagNames, true, null, null, null,
					fileEntry.getMimeType(), fileEntry.getTitle(),
					fileEntry.getDescription(), null, null, null, 0, 0, null);
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
		throws PortalException {

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
			folder.getDescription(), null, null, null, 0, 0, null);

		assetLinkLocalService.updateLinks(
			userId, assetEntry.getEntryId(), assetLinkEntryIds,
			AssetLinkConstants.TYPE_RELATED);

		return assetEntry;
	}

	@Override
	public void updateFileEntry(
			long userId, FileEntry fileEntry, FileVersion sourceFileVersion,
			FileVersion destinationFileVersion, long assetClassPk)
		throws PortalException {

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
	}

	@Override
	public void updateFileEntry(
			long userId, FileEntry fileEntry, FileVersion sourceFileVersion,
			FileVersion destinationFileVersion, ServiceContext serviceContext)
		throws PortalException {

		if (!DLAppHelperThreadLocal.isEnabled()) {
			return;
		}

		updateAsset(
			userId, fileEntry, destinationFileVersion,
			serviceContext.getAssetCategoryIds(),
			serviceContext.getAssetTagNames(),
			serviceContext.getAssetLinkEntryIds());
	}

	@Override
	public void updateFolder(
			long userId, Folder folder, ServiceContext serviceContext)
		throws PortalException {

		updateAsset(
			userId, folder, serviceContext.getAssetCategoryIds(),
			serviceContext.getAssetTagNames(),
			serviceContext.getAssetLinkEntryIds());
	}

	@Override
	public void updateStatus(
			long userId, FileEntry fileEntry, FileVersion latestFileVersion,
			int oldStatus, int newStatus, ServiceContext serviceContext,
			Map<String, Serializable> workflowContext)
		throws PortalException {

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

						long[] assetLinkEntryIds = ListUtil.toLongArray(
							assetLinks, AssetLink.ENTRY_ID2_ACCESSOR);

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
								0, null);

						assetLinkLocalService.updateLinks(
							userId, assetEntry.getEntryId(), assetLinkEntryIds,
							AssetLinkConstants.TYPE_RELATED);

						assetEntryLocalService.deleteEntry(draftAssetEntry);
					}
				}

				AssetEntry assetEntry = assetEntryLocalService.fetchEntry(
					DLFileEntryConstants.getClassName(),
					fileEntry.getFileEntryId());

				if (assetEntry != null) {
					assetEntryLocalService.updateVisible(
						DLFileEntryConstants.getClassName(),
						fileEntry.getFileEntryId(), true);
				}
			}

			// Sync

			String event = GetterUtil.getString(workflowContext.get("event"));

			if (Validator.isNotNull(event)) {
				triggerRepositoryEvent(
					fileEntry.getRepositoryId(),
					getWorkflowRepositoryEventTypeClass(event), FileEntry.class,
					fileEntry);
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

				SocialActivityManagerUtil.addUniqueActivity(
					latestFileVersion.getStatusByUserId(), activityCreateDate,
					fileEntry, activityType, extraDataJSONObject.toString(), 0);

				// Subscriptions

				notifySubscribers(
					userId, latestFileVersion,
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
		throws PortalException {

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

		List<DLFileVersion> dlFileVersions =
			dlFileVersionLocalService.getFileVersions(
				fileEntry.getFileEntryId(), WorkflowConstants.STATUS_IN_TRASH);

		dlFileVersions = ListUtil.sort(
			dlFileVersions, new DLFileVersionVersionComparator());

		FileVersion fileVersion = new LiferayFileVersion(dlFileVersions.get(0));

		TrashVersion trashVersion = trashVersionLocalService.fetchVersion(
			DLFileVersion.class.getName(), fileVersion.getFileVersionId());

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
				DLFileVersion.class.getName(),
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

		triggerRepositoryEvent(
			fileEntry.getRepositoryId(),
			TrashRepositoryEventType.EntryRestored.class, FileEntry.class,
			fileEntry);

		// Social

		JSONObject extraDataJSONObject = JSONFactoryUtil.createJSONObject();

		extraDataJSONObject.put("title", fileEntry.getTitle());

		SocialActivityManagerUtil.addActivity(
			userId, fileEntry, SocialActivityConstants.TYPE_RESTORE_FROM_TRASH,
			extraDataJSONObject.toString(), 0);

		return fileEntry;
	}

	protected FileEntry doMoveFileEntryToTrash(long userId, FileEntry fileEntry)
		throws PortalException {

		// File versions

		List<DLFileVersion> dlFileVersions =
			dlFileVersionLocalService.getFileVersions(
				fileEntry.getFileEntryId(), WorkflowConstants.STATUS_ANY);

		dlFileVersions = ListUtil.sort(
			dlFileVersions, new DLFileVersionVersionComparator());

		List<ObjectValuePair<Long, Integer>> dlFileVersionStatusOVPs =
			new ArrayList<>();

		if ((dlFileVersions != null) && !dlFileVersions.isEmpty()) {
			dlFileVersionStatusOVPs = getDlFileVersionStatuses(dlFileVersions);
		}

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

			triggerRepositoryEvent(
				fileEntry.getRepositoryId(),
				TrashRepositoryEventType.EntryTrashed.class, FileEntry.class,
				fileEntry);
		}

		// Trash

		DLFileVersion oldDLFileVersion = (DLFileVersion)fileVersion.getModel();

		int oldDLFileVersionStatus = oldDLFileVersion.getStatus();

		for (DLFileVersion curDLFileVersion : dlFileVersions) {
			curDLFileVersion.setStatus(WorkflowConstants.STATUS_IN_TRASH);

			dlFileVersionPersistence.update(curDLFileVersion);
		}

		DLFileEntry dlFileEntry = (DLFileEntry)fileEntry.getModel();

		UnicodeProperties typeSettingsProperties = new UnicodeProperties();

		typeSettingsProperties.put("fileName", dlFileEntry.getFileName());
		typeSettingsProperties.put("title", dlFileEntry.getTitle());

		TrashEntry trashEntry = trashEntryLocalService.addTrashEntry(
			userId, dlFileEntry.getGroupId(),
			DLFileEntryConstants.getClassName(), dlFileEntry.getFileEntryId(),
			dlFileEntry.getUuid(), dlFileEntry.getClassName(),
			oldDLFileVersionStatus, dlFileVersionStatusOVPs,
			typeSettingsProperties);

		String trashTitle = TrashUtil.getTrashTitle(trashEntry.getEntryId());

		dlFileEntry.setFileName(trashTitle);
		dlFileEntry.setTitle(trashTitle);

		dlFileEntryPersistence.update(dlFileEntry);

		if (!DLAppHelperThreadLocal.isEnabled()) {
			return fileEntry;
		}

		// Social

		JSONObject extraDataJSONObject = JSONFactoryUtil.createJSONObject();

		extraDataJSONObject.put(
			"title", TrashUtil.getOriginalTitle(fileEntry.getTitle()));

		SocialActivityManagerUtil.addActivity(
			userId, fileEntry, SocialActivityConstants.TYPE_MOVE_TO_TRASH,
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
		throws PortalException {

		DLFolder dlFolder = (DLFolder)folder.getModel();

		if (dlFolder.isInTrashExplicitly()) {
			restoreFolderFromTrash(userId, folder);
		}
		else {

			// Folder

			TrashVersion trashVersion = trashVersionLocalService.fetchVersion(
				DLFolder.class.getName(), dlFolder.getFolderId());

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

			restoreDependentsFromTrash(dlFolder);

			// Sync

			triggerRepositoryEvent(
				folder.getRepositoryId(),
				TrashRepositoryEventType.EntryRestored.class, Folder.class,
				folder);

			// Social

			JSONObject extraDataJSONObject = JSONFactoryUtil.createJSONObject();

			extraDataJSONObject.put("title", folder.getName());

			SocialActivityManagerUtil.addActivity(
				userId, folder, SocialActivityConstants.TYPE_RESTORE_FROM_TRASH,
				extraDataJSONObject.toString(), 0);
		}

		return dlAppLocalService.moveFolder(
			userId, folder.getFolderId(), parentFolderId, serviceContext);
	}

	protected Folder doMoveFolderToTrash(long userId, Folder folder)
		throws PortalException {

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

		moveDependentsToTrash(dlFolder);

		// Sync

		triggerRepositoryEvent(
			folder.getRepositoryId(),
			TrashRepositoryEventType.EntryTrashed.class, Folder.class, folder);

		// Social

		JSONObject extraDataJSONObject = JSONFactoryUtil.createJSONObject();

		extraDataJSONObject.put("title", folder.getName());

		SocialActivityManagerUtil.addActivity(
			userId, folder, SocialActivityConstants.TYPE_MOVE_TO_TRASH,
			extraDataJSONObject.toString(), 0);

		return new LiferayFolder(dlFolder);
	}

	protected List<ObjectValuePair<Long, Integer>> getDlFileVersionStatuses(
		List<DLFileVersion> dlFileVersions) {

		List<ObjectValuePair<Long, Integer>> dlFileVersionStatusOVPs =
			new ArrayList<>(dlFileVersions.size());

		for (DLFileVersion dlFileVersion : dlFileVersions) {
			int status = dlFileVersion.getStatus();

			if (status == WorkflowConstants.STATUS_PENDING) {
				status = WorkflowConstants.STATUS_DRAFT;
			}

			ObjectValuePair<Long, Integer> dlFileVersionStatusOVP =
				new ObjectValuePair<>(dlFileVersion.getFileVersionId(), status);

			dlFileVersionStatusOVPs.add(dlFileVersionStatusOVP);
		}

		return dlFileVersionStatusOVPs;
	}

	protected long getFileEntryTypeId(FileEntry fileEntry) {
		if (fileEntry instanceof LiferayFileEntry) {
			DLFileEntry dlFileEntry = (DLFileEntry)fileEntry.getModel();

			return dlFileEntry.getFileEntryTypeId();
		}

		return 0;
	}

	protected Class<? extends WorkflowRepositoryEventType>
		getWorkflowRepositoryEventTypeClass(
			String syncEvent) {

		if (syncEvent.equals(DLSyncConstants.EVENT_ADD)) {
			return WorkflowRepositoryEventType.Add.class;
		}
		else if (syncEvent.equals(DLSyncConstants.EVENT_UPDATE)) {
			return WorkflowRepositoryEventType.Update.class;
		}
		else {
			throw new IllegalArgumentException(
				String.format("Unsupported sync event %s", syncEvent));
		}
	}

	protected void notifySubscribers(
			long userId, FileVersion fileVersion, String entryURL,
			ServiceContext serviceContext)
		throws PortalException {

		if (!fileVersion.isApproved() || Validator.isNull(entryURL)) {
			return;
		}

		DLGroupServiceSettings dlGroupServiceSettings =
			DLGroupServiceSettings.getInstance(fileVersion.getGroupId());

		boolean commandUpdate = false;

		if (serviceContext.isCommandUpdate() ||
			Constants.CHECKIN.equals(serviceContext.getCommand())) {

			commandUpdate = true;
		}

		if (serviceContext.isCommandAdd() &&
			dlGroupServiceSettings.isEmailFileEntryAddedEnabled()) {
		}
		else if (commandUpdate &&
				 dlGroupServiceSettings.isEmailFileEntryUpdatedEnabled()) {
		}
		else {
			return;
		}

		String entryTitle = fileVersion.getTitle();

		String fromName = dlGroupServiceSettings.getEmailFromName();
		String fromAddress = dlGroupServiceSettings.getEmailFromAddress();

		LocalizedValuesMap subjectLocalizedValuesMap = null;
		LocalizedValuesMap bodyLocalizedValuesMap = null;

		if (commandUpdate) {
			subjectLocalizedValuesMap =
				dlGroupServiceSettings.getEmailFileEntryUpdatedSubject();
			bodyLocalizedValuesMap =
				dlGroupServiceSettings.getEmailFileEntryUpdatedBody();
		}
		else {
			subjectLocalizedValuesMap =
				dlGroupServiceSettings.getEmailFileEntryAddedSubject();
			bodyLocalizedValuesMap =
				dlGroupServiceSettings.getEmailFileEntryAddedBody();
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

		SubscriptionSender subscriptionSender =
			new GroupSubscriptionCheckSubscriptionSender(
				DLPermission.RESOURCE_NAME);

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
		subscriptionSender.setContextCreatorUserPrefix("DOCUMENT");
		subscriptionSender.setCreatorUserId(fileVersion.getUserId());
		subscriptionSender.setCurrentUserId(userId);
		subscriptionSender.setEntryTitle(entryTitle);
		subscriptionSender.setEntryURL(entryURL);
		subscriptionSender.setFrom(fromAddress, fromName);
		subscriptionSender.setHtmlFormat(true);
		subscriptionSender.setLocalizedBodyMap(
			LocalizationUtil.getMap(bodyLocalizedValuesMap));
		subscriptionSender.setLocalizedSubjectMap(
			LocalizationUtil.getMap(subjectLocalizedValuesMap));
		subscriptionSender.setMailId(
			"file_entry", fileVersion.getFileEntryId());

		int notificationType =
			UserNotificationDefinition.NOTIFICATION_TYPE_ADD_ENTRY;

		if (commandUpdate) {
			notificationType =
				UserNotificationDefinition.NOTIFICATION_TYPE_UPDATE_ENTRY;
		}

		subscriptionSender.setNotificationType(notificationType);

		String portletId = PortletProviderUtil.getPortletId(
			FileEntry.class.getName(), PortletProvider.Action.VIEW);

		subscriptionSender.setPortletId(portletId);

		subscriptionSender.setReplyToAddress(fromAddress);
		subscriptionSender.setScopeGroupId(fileVersion.getGroupId());
		subscriptionSender.setServiceContext(serviceContext);

		subscriptionSender.addPersistedSubscribers(
			DLFolder.class.getName(), fileVersion.getGroupId());

		if (folder != null) {
			subscriptionSender.addPersistedSubscribers(
				DLFolder.class.getName(), folder.getFolderId());

			for (Long ancestorFolderId : folder.getAncestorFolderIds()) {
				subscriptionSender.addPersistedSubscribers(
					DLFolder.class.getName(), ancestorFolderId);
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

	protected void trashOrRestoreFolder(DLFolder dlFolder, boolean moveToTrash)
		throws PortalException {

		TrashEntry trashEntry = null;

		if (moveToTrash) {
			trashEntry = trashEntryLocalService.getEntry(
				DLFolderConstants.getClassName(), dlFolder.getFolderId());
		}

		long dlFileEntryClassNameId = classNameLocalService.getClassNameId(
			DLFileEntry.class);

		List<AssetEntry> dlFileEntryAssetEntries =
			assetEntryFinder.findByDLFileEntryC_T(
				dlFileEntryClassNameId, dlFolder.getTreePath());

		for (AssetEntry dlFileEntryAssetEntry : dlFileEntryAssetEntries) {
			assetEntryLocalService.updateVisible(
				dlFileEntryAssetEntry, !moveToTrash);
		}

		long dlFolderClassNameId = classNameLocalService.getClassNameId(
			DLFolder.class);

		List<AssetEntry> dlFolderAssetEntries =
			assetEntryFinder.findByDLFolderC_T(
				dlFolderClassNameId, dlFolder.getTreePath());

		for (AssetEntry dlFolderAssetEntry : dlFolderAssetEntries) {
			assetEntryLocalService.updateVisible(
				dlFolderAssetEntry, !moveToTrash);
		}

		List<DLFolder> dlFolders = dlFolderPersistence.findByG_M_T_H(
			dlFolder.getGroupId(), false,
			CustomSQLUtil.keywords(
				dlFolder.getTreePath(), WildcardMode.TRAILING)[0],
			false);

		for (DLFolder childDLFolder : dlFolders) {
			trashOrRestoreFolder(
				dlFolder, childDLFolder, moveToTrash, trashEntry);
		}
	}

	protected void trashOrRestoreFolder(
			DLFolder dlFolder, DLFolder childDLFolder, boolean moveToTrash,
			TrashEntry trashEntry)
		throws PortalException {

		List<DLFileEntry> dlFileEntries =
			dlFileEntryLocalService.getFileEntries(
				childDLFolder.getGroupId(), childDLFolder.getFolderId());

		for (DLFileEntry dlFileEntry : dlFileEntries) {
			if (moveToTrash) {
				if (dlFileEntry.isInTrashExplicitly()) {
					continue;
				}
			}
			else if (!dlFileEntry.isInTrashImplicitly()) {
				continue;
			}

			// File shortcut

			dlFileShortcutLocalService.updateFileShortcutsActive(
				dlFileEntry.getFileEntryId(), !moveToTrash);

			// File versions

			List<DLFileVersion> dlFileVersions = null;

			if (moveToTrash) {
				dlFileVersions = dlFileVersionLocalService.getFileVersions(
					dlFileEntry.getFileEntryId(), WorkflowConstants.STATUS_ANY);
			}
			else {
				dlFileVersions = dlFileVersionLocalService.getFileVersions(
					dlFileEntry.getFileEntryId(),
					WorkflowConstants.STATUS_IN_TRASH);
			}

			for (DLFileVersion dlFileVersion : dlFileVersions) {

				// File version

				if (moveToTrash) {
					int oldStatus = dlFileVersion.getStatus();

					dlFileVersion.setStatus(WorkflowConstants.STATUS_IN_TRASH);

					dlFileVersionPersistence.update(dlFileVersion);

					// Trash

					if (oldStatus != WorkflowConstants.STATUS_APPROVED) {
						int newStatus = oldStatus;

						if (oldStatus == WorkflowConstants.STATUS_PENDING) {
							newStatus = WorkflowConstants.STATUS_DRAFT;
						}

						trashVersionLocalService.addTrashVersion(
							trashEntry.getEntryId(),
							DLFileVersion.class.getName(),
							dlFileVersion.getFileVersionId(), newStatus, null);
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
				else {
					TrashVersion trashVersion =
						trashVersionLocalService.fetchVersion(
							DLFileVersion.class.getName(),
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
			}

			// Index

			Indexer<DLFileEntry> indexer =
				IndexerRegistryUtil.nullSafeGetIndexer(DLFileEntry.class);

			indexer.reindex(dlFileEntry);
		}

		List<DLFileShortcut> dlFileShortcuts =
			dlFileShortcutPersistence.findByG_F(
				childDLFolder.getGroupId(), childDLFolder.getFolderId());

		for (DLFileShortcut dlFileShortcut : dlFileShortcuts) {
			if (moveToTrash) {
				if (dlFileShortcut.isInTrashExplicitly()) {
					continue;
				}

				int oldStatus = dlFileShortcut.getStatus();

				dlFileShortcut.setStatus(WorkflowConstants.STATUS_IN_TRASH);

				dlFileShortcutPersistence.update(dlFileShortcut);

				// Trash

				if (oldStatus != WorkflowConstants.STATUS_APPROVED) {
					trashVersionLocalService.addTrashVersion(
						trashEntry.getEntryId(),
						DLFileShortcutConstants.getClassName(),
						dlFileShortcut.getFileShortcutId(), oldStatus, null);
				}
			}
			else {
				if (!dlFileShortcut.isInTrashImplicitly()) {
					continue;
				}

				TrashVersion trashVersion =
					trashVersionLocalService.fetchVersion(
						DLFileShortcutConstants.getClassName(),
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
		}

		if (childDLFolder.equals(dlFolder)) {
			return;
		}

		if (moveToTrash) {
			if (childDLFolder.isInTrashExplicitly()) {
				return;
			}

			int oldStatus = childDLFolder.getStatus();

			childDLFolder.setStatus(WorkflowConstants.STATUS_IN_TRASH);

			dlFolderPersistence.update(childDLFolder);

			// Trash

			if (oldStatus != WorkflowConstants.STATUS_APPROVED) {
				trashVersionLocalService.addTrashVersion(
					trashEntry.getEntryId(), DLFolder.class.getName(),
					childDLFolder.getFolderId(), oldStatus, null);
			}
		}
		else {
			if (!childDLFolder.isInTrashImplicitly()) {
				return;
			}

			TrashVersion trashVersion = trashVersionLocalService.fetchVersion(
				DLFolder.class.getName(), childDLFolder.getFolderId());

			int oldStatus = WorkflowConstants.STATUS_APPROVED;

			if (trashVersion != null) {
				oldStatus = trashVersion.getStatus();
			}

			childDLFolder.setStatus(oldStatus);

			dlFolderPersistence.update(childDLFolder);

			// Trash

			if (trashVersion != null) {
				trashVersionLocalService.deleteTrashVersion(trashVersion);
			}
		}

		// Index

		Indexer<DLFolder> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			DLFolder.class);

		indexer.reindex(childDLFolder);
	}

	protected <T extends RepositoryModel<T>> void triggerRepositoryEvent(
			long repositoryId,
			Class<? extends RepositoryEventType> repositoryEventType,
			Class<T> modelClass, T target)
		throws PortalException {

		Repository repository = RepositoryProviderUtil.getRepository(
			repositoryId);

		if (repository.isCapabilityProvided(
				RepositoryEventTriggerCapability.class)) {

			RepositoryEventTriggerCapability repositoryEventTriggerCapability =
				repository.getCapability(
					RepositoryEventTriggerCapability.class);

			repositoryEventTriggerCapability.trigger(
				repositoryEventType, modelClass, target);
		}
	}

	@BeanReference(type = DLAppService.class)
	protected DLAppService dlAppService;

}
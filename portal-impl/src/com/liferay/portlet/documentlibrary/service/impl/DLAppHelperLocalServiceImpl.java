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

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetLink;
import com.liferay.asset.kernel.model.AssetLinkConstants;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.document.library.kernel.model.DLFileShortcut;
import com.liferay.document.library.kernel.model.DLFileShortcutConstants;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.kernel.util.DLAppHelperThreadLocal;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.document.library.kernel.util.comparator.DLFileVersionVersionComparator;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.dao.orm.WildcardMode;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.interval.IntervalActionProcessor;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.lock.Lock;
import com.liferay.portal.kernel.messaging.async.Async;
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
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.social.SocialActivityManagerUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileEntry;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileShortcut;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileVersion;
import com.liferay.portal.repository.liferayrepository.model.LiferayFolder;
import com.liferay.portlet.documentlibrary.service.base.DLAppHelperLocalServiceBaseImpl;
import com.liferay.portlet.documentlibrary.social.DLActivityKeys;
import com.liferay.portlet.documentlibrary.util.DLAppUtil;
import com.liferay.social.kernel.model.SocialActivityConstants;
import com.liferay.trash.kernel.exception.RestoreEntryException;
import com.liferay.trash.kernel.exception.TrashEntryException;
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
import java.util.Objects;

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
	public void cancelCheckOuts(long groupId) throws PortalException {
		ActionableDynamicQuery actionableDynamicQuery =
			getCancelCheckOutsActionableDynamicQuery(groupId);

		actionableDynamicQuery.performActions();
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
				assetTagNames, true, false, null, null, null, null,
				fileEntry.getMimeType(), fileEntry.getTitle(),
				fileEntry.getDescription(), null, null, null, 0, 0, null);
		}

		AssetEntry fileVersionAssetEntry = assetEntryLocalService.fetchEntry(
			DLFileEntryConstants.getClassName(),
			fileVersion.getFileVersionId());

		if ((fileVersionAssetEntry != null) || fileVersion.isApproved()) {
			return;
		}

		String version = fileVersion.getVersion();

		if (version.equals(DLFileEntryConstants.VERSION_DEFAULT)) {
			return;
		}

		assetCategoryIds = assetCategoryLocalService.getCategoryIds(
			DLFileEntryConstants.getClassName(), fileEntry.getFileEntryId());
		assetTagNames = assetTagLocalService.getTagNames(
			DLFileEntryConstants.getClassName(), fileEntry.getFileEntryId());

		fileVersionAssetEntry = assetEntryLocalService.updateEntry(
			userId, fileEntry.getGroupId(), fileEntry.getCreateDate(),
			fileEntry.getModifiedDate(), DLFileEntryConstants.getClassName(),
			fileVersion.getFileVersionId(), fileEntry.getUuid(),
			fileEntryTypeId, assetCategoryIds, assetTagNames, true, false, null,
			null, null, null, fileEntry.getMimeType(), fileEntry.getTitle(),
			fileEntry.getDescription(), null, null, null, 0, 0, null);

		List<AssetLink> assetLinks = assetLinkLocalService.getDirectLinks(
			fileEntryAssetEntry.getEntryId(), false);

		long[] assetLinkIds = ListUtil.toLongArray(
			assetLinks, AssetLink.ENTRY_ID2_ACCESSOR);

		assetLinkLocalService.updateLinks(
			userId, fileVersionAssetEntry.getEntryId(), assetLinkIds,
			AssetLinkConstants.TYPE_RELATED);
	}

	@Override
	public void deleteFileEntry(FileEntry fileEntry) throws PortalException {
		if (!DLAppHelperThreadLocal.isEnabled()) {
			return;
		}

		_deleteFileEntry(fileEntry.getFileEntryId());
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

		ActionableDynamicQuery actionableDynamicQuery =
			dlFileEntryLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> dynamicQuery.add(
				RestrictionsFactoryUtil.eq("repositoryId", repositoryId)));
		actionableDynamicQuery.setPerformActionMethod(
			(DLFileEntry dlFileEntry) -> _deleteFileEntry(
				dlFileEntry.getFileEntryId()));
	}

	@Override
	public long getCheckedOutFileEntriesCount(long groupId)
		throws PortalException {

		ActionableDynamicQuery actionableDynamicQuery =
			getCancelCheckOutsActionableDynamicQuery(groupId);

		return actionableDynamicQuery.performCount();
	}

	@Override
	public void getFileAsStream(
		long userId, FileEntry fileEntry, boolean incrementCounter) {

		if (!incrementCounter) {
			return;
		}

		// File read count

		assetEntryLocalService.incrementViewCounter(
			fileEntry.getCompanyId(), userId,
			DLFileEntryConstants.getClassName(), fileEntry.getFileEntryId(), 1);

		List<DLFileShortcut> fileShortcuts =
			dlFileShortcutPersistence.findByToFileEntryId(
				fileEntry.getFileEntryId());

		for (DLFileShortcut fileShortcut : fileShortcuts) {
			assetEntryLocalService.incrementViewCounter(
				fileEntry.getCompanyId(), userId,
				DLFileShortcutConstants.getClassName(),
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

	@Override
	public FileEntry moveFileEntryFromTrash(
			long userId, FileEntry fileEntry, long newFolderId,
			ServiceContext serviceContext)
		throws PortalException {

		boolean hasLock = dlFileEntryLocalService.hasFileEntryLock(
			userId, fileEntry.getFileEntryId(), fileEntry.getFolderId());

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
			userId, fileEntry.getFileEntryId(), fileEntry.getFolderId());

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

		if (!dlFileShortcut.isInTrash()) {
			throw new RestoreEntryException(
				RestoreEntryException.INVALID_STATUS);
		}

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

			JSONObject extraDataJSONObject = JSONUtil.put(
				"title", fileShortcut.getToTitle());

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

		if (dlFileShortcut.isInTrash()) {
			throw new TrashEntryException();
		}

		int oldStatus = dlFileShortcut.getStatus();

		dlFileShortcut = dlFileShortcutLocalService.updateStatus(
			userId, fileShortcut.getFileShortcutId(),
			WorkflowConstants.STATUS_IN_TRASH, new ServiceContext());

		// Social

		JSONObject extraDataJSONObject = JSONUtil.put(
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

		return new LiferayFileShortcut(dlFileShortcut);
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

	@Async
	@Override
	public void reindex(long companyId, List<Long> dlFileEntryIds)
		throws PortalException {

		IntervalActionProcessor<Void> intervalActionProcessor =
			new IntervalActionProcessor<>(dlFileEntryIds.size());

		intervalActionProcessor.setPerformIntervalActionMethod(
			(start, end) -> {
				List<Long> sublist = dlFileEntryIds.subList(start, end);

				Indexer<DLFileEntry> indexer =
					IndexerRegistryUtil.nullSafeGetIndexer(DLFileEntry.class);

				IndexableActionableDynamicQuery
					indexableActionableDynamicQuery =
						dlFileEntryLocalService.
							getIndexableActionableDynamicQuery();

				indexableActionableDynamicQuery.setAddCriteriaMethod(
					dynamicQuery -> {
						Property dlFileEntryId = PropertyFactoryUtil.forName(
							"fileEntryId");

						dynamicQuery.add(dlFileEntryId.in(sublist));
					});
				indexableActionableDynamicQuery.setCompanyId(companyId);
				indexableActionableDynamicQuery.setPerformActionMethod(
					(DLFileEntry dlFileEntry) ->
						indexableActionableDynamicQuery.addDocuments(
							indexer.getDocument(dlFileEntry)));
				indexableActionableDynamicQuery.setSearchEngineId(
					indexer.getSearchEngineId());

				indexableActionableDynamicQuery.performActions();

				intervalActionProcessor.incrementStart(sublist.size());

				return null;
			});

		intervalActionProcessor.performIntervalActions();
	}

	@Override
	public void restoreDependentsFromTrash(DLFolder dlFolder)
		throws PortalException {

		trashOrRestoreFolder(dlFolder, false);
	}

	@Override
	public void restoreFileEntryFromTrash(long userId, FileEntry fileEntry)
		throws PortalException {

		restoreFileEntryFromTrash(userId, fileEntry.getFolderId(), fileEntry);
	}

	@Override
	public void restoreFileEntryFromTrash(
			long userId, long newFolderId, FileEntry fileEntry)
		throws PortalException {

		// File entry

		DLFileEntry dlFileEntry = (DLFileEntry)fileEntry.getModel();

		if (!dlFileEntry.isInTrash()) {
			throw new RestoreEntryException(
				RestoreEntryException.INVALID_STATUS);
		}

		FileVersion fileVersion = fileEntry.getFileVersion();

		if (!DLAppHelperThreadLocal.isEnabled()) {
			dlFileEntryLocalService.updateStatus(
				userId, fileVersion.getFileVersionId(),
				WorkflowConstants.STATUS_APPROVED, new ServiceContext(),
				new HashMap<String, Serializable>());

			return;
		}

		String originalTitle = TrashUtil.getOriginalTitle(
			dlFileEntry.getTitle());

		String title = dlFileEntryLocalService.getUniqueTitle(
			dlFileEntry.getGroupId(), newFolderId, dlFileEntry.getFileEntryId(),
			originalTitle, dlFileEntry.getExtension());

		String originalFileName = TrashUtil.getOriginalTitle(
			dlFileEntry.getTitle(), "fileName");

		String fileName = originalFileName;

		if (!StringUtil.equals(title, originalTitle)) {
			String extension = DLAppUtil.getExtension(title, originalFileName);

			fileName = DLUtil.getSanitizedFileName(title, extension);
		}

		dlFileEntry.setFileName(fileName);
		dlFileEntry.setTitle(title);

		dlFileEntry = dlFileEntryPersistence.update(dlFileEntry);

		DLFileVersion dlFileVersion = (DLFileVersion)fileVersion.getModel();

		dlFileVersion.setFileName(fileName);
		dlFileVersion.setTitle(title);

		dlFileVersion = dlFileVersionPersistence.update(dlFileVersion);

		TrashEntry trashEntry = trashEntryLocalService.getEntry(
			DLFileEntryConstants.getClassName(), fileEntry.getFileEntryId());

		dlFileEntryLocalService.updateStatus(
			userId, dlFileEntry, dlFileVersion, trashEntry.getStatus(),
			new ServiceContext(), new HashMap<>());

		// File shortcut

		dlFileShortcutLocalService.enableFileShortcuts(
			fileEntry.getFileEntryId());

		// Sync

		triggerRepositoryEvent(
			fileEntry.getRepositoryId(),
			TrashRepositoryEventType.EntryRestored.class, FileEntry.class,
			fileEntry);

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

		// Social

		JSONObject extraDataJSONObject = JSONUtil.put(
			"title", fileEntry.getTitle());

		SocialActivityManagerUtil.addActivity(
			userId, fileEntry, SocialActivityConstants.TYPE_RESTORE_FROM_TRASH,
			extraDataJSONObject.toString(), 0);
	}

	@Override
	public void restoreFileShortcutFromTrash(
			long userId, FileShortcut fileShortcut)
		throws PortalException {

		DLFileShortcut dlFileShortcut = (DLFileShortcut)fileShortcut.getModel();

		if (!dlFileShortcut.isInTrash()) {
			throw new RestoreEntryException(
				RestoreEntryException.INVALID_STATUS);
		}

		// File shortcut

		TrashEntry trashEntry = trashEntryLocalService.getEntry(
			DLFileShortcutConstants.getClassName(),
			fileShortcut.getFileShortcutId());

		dlFileShortcutLocalService.updateStatus(
			userId, fileShortcut.getFileShortcutId(), trashEntry.getStatus(),
			new ServiceContext());

		// Social

		JSONObject extraDataJSONObject = JSONUtil.put(
			"title", fileShortcut.getToTitle());

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

		if (!dlFolder.isInTrash()) {
			throw new RestoreEntryException(
				RestoreEntryException.INVALID_STATUS);
		}

		String originalName = TrashUtil.getOriginalTitle(dlFolder.getName());

		dlFolder.setName(
			dlFolderLocalService.getUniqueFolderName(
				folder.getUuid(), folder.getGroupId(),
				folder.getParentFolderId(), originalName, 2));

		dlFolder = dlFolderPersistence.update(dlFolder);

		TrashEntry trashEntry = trashEntryLocalService.getEntry(
			DLFolder.class.getName(), dlFolder.getFolderId());

		dlFolderLocalService.updateStatus(
			userId, folder.getFolderId(), trashEntry.getStatus(),
			new HashMap<String, Serializable>(), new ServiceContext());

		// Folders, file entries, and file shortcuts

		restoreDependentsFromTrash(dlFolder);

		// Sync

		triggerRepositoryEvent(
			folder.getRepositoryId(),
			TrashRepositoryEventType.EntryRestored.class, Folder.class, folder);

		// Trash

		trashEntryLocalService.deleteEntry(trashEntry.getEntryId());

		// Social

		JSONObject extraDataJSONObject = JSONUtil.put(
			"title", folder.getName());

		SocialActivityManagerUtil.addActivity(
			userId, folder, SocialActivityConstants.TYPE_RESTORE_FROM_TRASH,
			extraDataJSONObject.toString(), 0);
	}

	@Override
	public AssetEntry updateAsset(
			long userId, FileEntry fileEntry, FileVersion fileVersion,
			long assetClassPK)
		throws PortalException {

		long[] assetCategoryIds = assetCategoryLocalService.getCategoryIds(
			DLFileEntryConstants.getClassName(), assetClassPK);
		String[] assetTagNames = assetTagLocalService.getTagNames(
			DLFileEntryConstants.getClassName(), assetClassPK);

		AssetEntry assetEntry = assetEntryLocalService.fetchEntry(
			DLFileEntryConstants.getClassName(), assetClassPK);

		List<AssetLink> assetLinks = null;

		if (assetEntry != null) {
			assetLinks = assetLinkLocalService.getDirectLinks(
				assetEntry.getEntryId(), false);
		}

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
						AssetLinkConstants.TYPE_RELATED, false);

				assetLinkEntryIds = ListUtil.toLongArray(
					assetLinks, AssetLink.ENTRY_ID2_ACCESSOR);
			}

			assetEntry = assetEntryLocalService.updateEntry(
				userId, fileEntry.getGroupId(), fileEntry.getCreateDate(),
				fileEntry.getModifiedDate(),
				DLFileEntryConstants.getClassName(),
				fileVersion.getFileVersionId(), fileEntry.getUuid(),
				fileEntryTypeId, assetCategoryIds, assetTagNames, true, false,
				null, null, null, null, fileEntry.getMimeType(),
				fileEntry.getTitle(), fileEntry.getDescription(), null, null,
				null, 0, 0, null);
		}
		else {
			Date publishDate = null;

			if (visible) {
				publishDate = fileEntry.getCreateDate();
			}

			assetEntry = assetEntryLocalService.updateEntry(
				userId, fileEntry.getGroupId(), fileEntry.getCreateDate(),
				fileEntry.getModifiedDate(),
				DLFileEntryConstants.getClassName(), fileEntry.getFileEntryId(),
				fileEntry.getUuid(), fileEntryTypeId, assetCategoryIds,
				assetTagNames, true, visible, null, null, publishDate, null,
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
					assetTagNames, true, true, null, null,
					dlFileShortcut.getCreateDate(), null,
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

		Date publishDate = null;

		if (visible) {
			publishDate = folder.getCreateDate();
		}

		assetEntry = assetEntryLocalService.updateEntry(
			userId, folder.getGroupId(), folder.getCreateDate(),
			folder.getModifiedDate(), DLFolderConstants.getClassName(),
			folder.getFolderId(), folder.getUuid(), 0, assetCategoryIds,
			assetTagNames, true, visible, null, null, publishDate, null, null,
			folder.getName(), folder.getDescription(), null, null, null, 0, 0,
			null);

		assetLinkLocalService.updateLinks(
			userId, assetEntry.getEntryId(), assetLinkEntryIds,
			AssetLinkConstants.TYPE_RELATED);

		return assetEntry;
	}

	@Override
	public void updateFileEntry(
			long userId, FileEntry fileEntry, FileVersion sourceFileVersion,
			FileVersion destinationFileVersion, long assetClassPK)
		throws PortalException {

		if (!DLAppHelperThreadLocal.isEnabled()) {
			return;
		}

		boolean updateAsset = true;

		if (fileEntry instanceof LiferayFileEntry) {
			String version = fileEntry.getVersion();

			if (version.equals(destinationFileVersion.getVersion())) {
				updateAsset = false;
			}
		}

		if (updateAsset) {
			updateAsset(
				userId, fileEntry, destinationFileVersion, assetClassPK);
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

		if (Objects.equals(serviceContext.getCommand(), Constants.REVERT)) {
			List<AssetCategory> assetCategories =
				assetCategoryLocalService.getCategories(
					DLFileEntryConstants.getClassName(),
					fileEntry.getFileEntryId());

			List<Long> assetCategoryIds = ListUtil.toList(
				assetCategories, AssetCategory.CATEGORY_ID_ACCESSOR);

			serviceContext.setAssetCategoryIds(
				ArrayUtil.toLongArray(assetCategoryIds));
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
								AssetLinkConstants.TYPE_RELATED, false);

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
								assetTagNames, true, true, null, null,
								fileEntry.getCreateDate(), null,
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
					assetEntryLocalService.updateEntry(
						assetEntry.getClassName(), assetEntry.getClassPK(),
						assetEntry.getCreateDate(),
						assetEntry.getExpirationDate(), assetEntry.isListable(),
						true);
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

				JSONObject extraDataJSONObject = JSONUtil.put(
					"title", fileEntry.getTitle());

				SocialActivityManagerUtil.addUniqueActivity(
					latestFileVersion.getStatusByUserId(), activityCreateDate,
					fileEntry, activityType, extraDataJSONObject.toString(), 0);
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

		if (!dlFileEntry.isInTrash()) {
			throw new RestoreEntryException(
				RestoreEntryException.INVALID_STATUS);
		}

		if (dlFileEntry.isInTrashExplicitly()) {
			restoreFileEntryFromTrash(userId, newFolderId, fileEntry);

			fileEntry = dlAppLocalService.moveFileEntry(
				userId, fileEntry.getFileEntryId(), newFolderId,
				serviceContext);

			// Indexer

			Indexer<DLFileEntry> indexer =
				IndexerRegistryUtil.nullSafeGetIndexer(DLFileEntry.class);

			indexer.reindex((DLFileEntry)fileEntry.getModel());

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
			userId, dlFileEntry, dlFileVersions.get(0), oldStatus,
			serviceContext, new HashMap<>());

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

			// File shortcut

			dlFileShortcutLocalService.enableFileShortcuts(
				fileEntry.getFileEntryId());
		}

		// App helper

		fileEntry = dlAppLocalService.moveFileEntry(
			userId, fileEntry.getFileEntryId(), newFolderId, serviceContext);

		// Sync

		triggerRepositoryEvent(
			fileEntry.getRepositoryId(),
			TrashRepositoryEventType.EntryRestored.class, FileEntry.class,
			fileEntry);

		// Social

		JSONObject extraDataJSONObject = JSONUtil.put(
			"title", fileEntry.getTitle());

		SocialActivityManagerUtil.addActivity(
			userId, fileEntry, SocialActivityConstants.TYPE_RESTORE_FROM_TRASH,
			extraDataJSONObject.toString(), 0);

		// Indexer

		Indexer<DLFileEntry> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			DLFileEntry.class);

		indexer.reindex((DLFileEntry)fileEntry.getModel());

		return fileEntry;
	}

	protected FileEntry doMoveFileEntryToTrash(long userId, FileEntry fileEntry)
		throws PortalException {

		if (fileEntry.isInTrash()) {
			throw new TrashEntryException();
		}

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

		FileVersion fileVersion = fileEntry.getLatestFileVersion(true);

		dlFileEntryLocalService.updateStatus(
			userId, fileVersion.getFileVersionId(),
			WorkflowConstants.STATUS_IN_TRASH, new ServiceContext(),
			new HashMap<String, Serializable>());

		if (DLAppHelperThreadLocal.isEnabled()) {

			// File shortcut

			dlFileShortcutLocalService.disableFileShortcuts(
				fileEntry.getFileEntryId());

			// Sync

			triggerRepositoryEvent(
				fileEntry.getRepositoryId(),
				TrashRepositoryEventType.EntryTrashed.class, FileEntry.class,
				fileEntry);
		}

		// Trash

		dlFileVersions = dlFileVersionLocalService.getFileVersions(
			fileEntry.getFileEntryId(), WorkflowConstants.STATUS_ANY);

		for (DLFileVersion curDLFileVersion : dlFileVersions) {
			curDLFileVersion.setStatus(WorkflowConstants.STATUS_IN_TRASH);

			dlFileVersionPersistence.update(curDLFileVersion);
		}

		DLFileEntry dlFileEntry = (DLFileEntry)fileEntry.getModel();

		if (!DLAppHelperThreadLocal.isEnabled()) {
			return fileEntry;
		}

		DLFileVersion oldDLFileVersion = (DLFileVersion)fileVersion.getModel();

		int oldDLFileVersionStatus = oldDLFileVersion.getStatus();

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

		dlFileEntry = dlFileEntryPersistence.update(dlFileEntry);

		// Indexer

		Indexer<DLFileEntry> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			DLFileEntry.class);

		indexer.reindex(dlFileEntry);

		// Social

		JSONObject extraDataJSONObject = JSONUtil.put(
			"title", TrashUtil.getOriginalTitle(fileEntry.getTitle()));

		SocialActivityManagerUtil.addActivity(
			userId, fileEntry, SocialActivityConstants.TYPE_MOVE_TO_TRASH,
			extraDataJSONObject.toString(), 0);

		// Workflow

		int oldStatus = fileVersion.getStatus();

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

		if (!dlFolder.isInTrash()) {
			throw new RestoreEntryException(
				RestoreEntryException.INVALID_STATUS);
		}

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

			JSONObject extraDataJSONObject = JSONUtil.put(
				"title", folder.getName());

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

		DLFolder dlFolder = (DLFolder)folder.getModel();

		if (dlFolder.isInTrash()) {
			throw new TrashEntryException();
		}

		dlFolder = dlFolderLocalService.updateStatus(
			userId, folder.getFolderId(), WorkflowConstants.STATUS_IN_TRASH,
			new HashMap<String, Serializable>(), new ServiceContext());

		// Trash

		UnicodeProperties typeSettingsProperties = new UnicodeProperties();

		typeSettingsProperties.put("title", dlFolder.getName());

		TrashEntry trashEntry = trashEntryLocalService.addTrashEntry(
			userId, dlFolder.getGroupId(), DLFolderConstants.getClassName(),
			dlFolder.getFolderId(), dlFolder.getUuid(), null,
			WorkflowConstants.STATUS_APPROVED, null, typeSettingsProperties);

		dlFolder.setName(TrashUtil.getTrashTitle(trashEntry.getEntryId()));

		dlFolder = dlFolderPersistence.update(dlFolder);

		// Folders, file entries, and file shortcuts

		moveDependentsToTrash(dlFolder);

		// Sync

		triggerRepositoryEvent(
			folder.getRepositoryId(),
			TrashRepositoryEventType.EntryTrashed.class, Folder.class, folder);

		// Social

		JSONObject extraDataJSONObject = JSONUtil.put(
			"title", folder.getName());

		SocialActivityManagerUtil.addActivity(
			userId, folder, SocialActivityConstants.TYPE_MOVE_TO_TRASH,
			extraDataJSONObject.toString(), 0);

		// Indexer

		Indexer<DLFolder> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			DLFolder.class);

		indexer.reindex(dlFolder);

		return new LiferayFolder(dlFolder);
	}

	protected ActionableDynamicQuery getCancelCheckOutsActionableDynamicQuery(
		long groupId) {

		ActionableDynamicQuery fileEntryActionableDynamicQuery =
			dlFileEntryLocalService.getActionableDynamicQuery();

		fileEntryActionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				Property fileEntryIdProperty = PropertyFactoryUtil.forName(
					"fileEntryId");

				DynamicQuery fileVersionDynamicQuery =
					DynamicQueryFactoryUtil.forClass(
						DLFileVersion.class, "dlFileVersion",
						PortalClassLoaderUtil.getClassLoader());

				fileVersionDynamicQuery.setProjection(
					ProjectionFactoryUtil.property("fileEntryId"));

				fileVersionDynamicQuery.add(
					RestrictionsFactoryUtil.eqProperty(
						"dlFileVersion.fileEntryId", "this.fileEntryId"));

				Property versionProperty = PropertyFactoryUtil.forName(
					"version");

				fileVersionDynamicQuery.add(
					versionProperty.eq(
						DLFileEntryConstants.PRIVATE_WORKING_COPY_VERSION));

				dynamicQuery.add(
					fileEntryIdProperty.in(fileVersionDynamicQuery));
			});
		fileEntryActionableDynamicQuery.setGroupId(groupId);
		fileEntryActionableDynamicQuery.setPerformActionMethod(
			(ActionableDynamicQuery.PerformActionMethod<DLFileEntry>)
				dlFileEntry -> dlAppService.cancelCheckOut(
					dlFileEntry.getFileEntryId()));

		return fileEntryActionableDynamicQuery;
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
		getWorkflowRepositoryEventTypeClass(String syncEvent) {

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

	protected void trashOrRestoreFolder(DLFolder dlFolder, boolean moveToTrash)
		throws PortalException {

		TrashEntry trashEntry = null;

		if (moveToTrash) {
			trashEntry = trashEntryLocalService.getEntry(
				DLFolderConstants.getClassName(), dlFolder.getFolderId());
		}

		long dlFileEntryClassNameId = classNameLocalService.getClassNameId(
			DLFileEntry.class);

		List<DLFileEntry> dlFileEntries = dlFileEntryFinder.findByC_T(
			dlFileEntryClassNameId, dlFolder.getTreePath());

		for (DLFileEntry dlFileEntry : dlFileEntries) {
			assetEntryLocalService.updateVisible(
				DLFileEntry.class.getName(), dlFileEntry.getFileEntryId(),
				!moveToTrash);
		}

		long dlFolderClassNameId = classNameLocalService.getClassNameId(
			DLFolder.class);

		List<DLFolder> dlFolders = dlFolderFinder.findF_ByC_T(
			dlFolderClassNameId, dlFolder.getTreePath());

		for (DLFolder curDLFolder : dlFolders) {
			assetEntryLocalService.updateVisible(
				DLFolder.class.getName(), curDLFolder.getFolderId(),
				!moveToTrash);
		}

		dlFolders = dlFolderPersistence.findByG_M_T_H(
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

		List<Long> dlFileEntryIds = new ArrayList<>();

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

					dlFileVersion = dlFileVersionPersistence.update(
						dlFileVersion);

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

			dlFileEntryIds.add(dlFileEntry.getFileEntryId());
		}

		if (!dlFileEntryIds.isEmpty()) {
			dlAppHelperLocalService.reindex(
				dlFolder.getCompanyId(), dlFileEntryIds);
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

				dlFileShortcut = dlFileShortcutPersistence.update(
					dlFileShortcut);

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

			childDLFolder = dlFolderPersistence.update(childDLFolder);

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

			childDLFolder = dlFolderPersistence.update(childDLFolder);

			// Trash

			if (trashVersion != null) {
				trashVersionLocalService.deleteTrashVersion(trashVersion);
			}
		}

		// Indexer

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

	private void _deleteFileEntry(long fileEntryId) throws PortalException {

		// File shortcuts

		dlFileShortcutLocalService.deleteFileShortcuts(fileEntryId);

		// Asset

		assetEntryLocalService.deleteEntry(
			DLFileEntryConstants.getClassName(), fileEntryId);

		// Ratings

		ratingsStatsLocalService.deleteStats(
			DLFileEntryConstants.getClassName(), fileEntryId);
	}

	/**
	 * @see com.liferay.document.library.sync.constants.DLSyncConstants
	 */
	private class DLSyncConstants {

		public static final String EVENT_ADD = "add";

		public static final String EVENT_UPDATE = "update";

	}

}
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

package com.liferay.portal.repository.liferayrepository;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.Repository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.SortedArrayList;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Lock;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileEntry;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileVersion;
import com.liferay.portal.repository.liferayrepository.model.LiferayFolder;
import com.liferay.portal.service.RepositoryLocalService;
import com.liferay.portal.service.RepositoryService;
import com.liferay.portal.service.ResourceLocalService;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.DLAppHelperLocalService;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalService;
import com.liferay.portlet.documentlibrary.service.DLFileEntryService;
import com.liferay.portlet.documentlibrary.service.DLFileEntryTypeLocalService;
import com.liferay.portlet.documentlibrary.service.DLFileVersionLocalService;
import com.liferay.portlet.documentlibrary.service.DLFileVersionService;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalService;
import com.liferay.portlet.documentlibrary.service.DLFolderService;
import com.liferay.portlet.documentlibrary.util.DLSearcher;
import com.liferay.portlet.documentlibrary.util.RepositoryModelUtil;
import com.liferay.portlet.documentlibrary.util.comparator.DLFileEntryOrderByComparator;
import com.liferay.portlet.documentlibrary.util.comparator.DLFolderOrderByComparator;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;

import java.io.File;
import java.io.InputStream;

import java.util.List;
import java.util.Map;

/**
 * @author Alexander Chow
 */
public class LiferayRepository
	extends LiferayRepositoryBase implements Repository {

	public LiferayRepository(
		RepositoryLocalService repositoryLocalService,
		RepositoryService repositoryService,
		DLAppHelperLocalService dlAppHelperLocalService,
		DLFileEntryLocalService dlFileEntryLocalService,
		DLFileEntryService dlFileEntryService,
		DLFileEntryTypeLocalService dlFileEntryTypeLocalService,
		DLFileVersionLocalService dlFileVersionLocalService,
		DLFileVersionService dlFileVersionService,
		DLFolderLocalService dlFolderLocalService,
		DLFolderService dlFolderService,
		ResourceLocalService resourceLocalService, long groupId,
		long repositoryId, long dlFolderId) {

		super(
			repositoryLocalService, repositoryService, dlAppHelperLocalService,
			dlFileEntryLocalService, dlFileEntryService,
			dlFileEntryTypeLocalService, dlFileVersionLocalService,
			dlFileVersionService, dlFolderLocalService, dlFolderService,
			resourceLocalService, groupId, repositoryId, dlFolderId);
	}

	@Override
	public FileEntry addFileEntry(
			long userId, long folderId, String sourceFileName, String mimeType,
			String title, String description, String changeLog, File file,
			ServiceContext serviceContext)
		throws PortalException {

		long fileEntryTypeId = ParamUtil.getLong(
			serviceContext, "fileEntryTypeId",
			getDefaultFileEntryTypeId(serviceContext, folderId));

		Map<String, Fields> fieldsMap = getFieldsMap(
			serviceContext, fileEntryTypeId);

		long size = 0;

		if (file != null) {
			size = file.length();
		}

		DLFileEntry dlFileEntry = dlFileEntryService.addFileEntry(
			getGroupId(), getRepositoryId(), toFolderId(folderId),
			sourceFileName, mimeType, title, description, changeLog,
			fileEntryTypeId, fieldsMap, file, null, size, serviceContext);

		addFileEntryResources(dlFileEntry, serviceContext);

		return new LiferayFileEntry(dlFileEntry);
	}

	@Override
	public FileEntry addFileEntry(
			long userId, long folderId, String sourceFileName, String mimeType,
			String title, String description, String changeLog, InputStream is,
			long size, ServiceContext serviceContext)
		throws PortalException {

		long fileEntryTypeId = ParamUtil.getLong(
			serviceContext, "fileEntryTypeId",
			getDefaultFileEntryTypeId(serviceContext, folderId));

		Map<String, Fields> fieldsMap = getFieldsMap(
			serviceContext, fileEntryTypeId);

		DLFileEntry dlFileEntry = dlFileEntryService.addFileEntry(
			getGroupId(), getRepositoryId(), toFolderId(folderId),
			sourceFileName, mimeType, title, description, changeLog,
			fileEntryTypeId, fieldsMap, null, is, size, serviceContext);

		addFileEntryResources(dlFileEntry, serviceContext);

		return new LiferayFileEntry(dlFileEntry);
	}

	/**
	 * @deprecated As of 7.0.0, see {@link #addFileEntry(long, long, String,
	 *             String, String, String, String, File, ServiceContext)}
	 */
	@Deprecated
	@Override
	public FileEntry addFileEntry(
			long folderId, String sourceFileName, String mimeType, String title,
			String description, String changeLog, File file,
			ServiceContext serviceContext)
		throws PortalException {

		return addFileEntry(
			com.liferay.portal.kernel.repository.util.RepositoryUserUtil.
				getUserId(),
			folderId, sourceFileName, mimeType, title, description, changeLog,
			file, serviceContext);
	}

	/**
	 * @deprecated As of 7.0.0, see {@link #addFileEntry(long, long, String,
	 *             String, String, String, String, InputStream, long,
	 *             ServiceContext)}
	 */
	@Deprecated
	@Override
	public FileEntry addFileEntry(
			long folderId, String sourceFileName, String mimeType, String title,
			String description, String changeLog, InputStream is, long size,
			ServiceContext serviceContext)
		throws PortalException {

		return addFileEntry(
			com.liferay.portal.kernel.repository.util.RepositoryUserUtil.
				getUserId(),
			folderId, sourceFileName, mimeType, title, description, changeLog,
			is, size, serviceContext);
	}

	@Override
	public Folder addFolder(
			long parentFolderId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException {

		boolean mountPoint = ParamUtil.getBoolean(serviceContext, "mountPoint");

		DLFolder dlFolder = dlFolderService.addFolder(
			getGroupId(), getRepositoryId(), mountPoint,
			toFolderId(parentFolderId), name, description, serviceContext);

		return new LiferayFolder(dlFolder);
	}

	@Override
	public FileVersion cancelCheckOut(long fileEntryId) throws PortalException {
		DLFileVersion dlFileVersion = dlFileEntryService.cancelCheckOut(
			fileEntryId);

		if (dlFileVersion != null) {
			return new LiferayFileVersion(dlFileVersion);
		}

		return null;
	}

	@Override
	public void checkInFileEntry(
			long fileEntryId, boolean major, String changeLog,
			ServiceContext serviceContext)
		throws PortalException {

		dlFileEntryService.checkInFileEntry(
			fileEntryId, major, changeLog, serviceContext);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #checkInFileEntry(long,
	 *             String, ServiceContext)}
	 */
	@Deprecated
	@Override
	public void checkInFileEntry(long fileEntryId, String lockUuid)
		throws PortalException {

		checkInFileEntry(fileEntryId, lockUuid, new ServiceContext());
	}

	@Override
	public void checkInFileEntry(
			long fileEntryId, String lockUuid, ServiceContext serviceContext)
		throws PortalException {

		dlFileEntryService.checkInFileEntry(
			fileEntryId, lockUuid, serviceContext);
	}

	@Override
	public FileEntry checkOutFileEntry(
			long fileEntryId, ServiceContext serviceContext)
		throws PortalException {

		DLFileEntry dlFileEntry = dlFileEntryService.checkOutFileEntry(
			fileEntryId, serviceContext);

		return new LiferayFileEntry(dlFileEntry);
	}

	@Override
	public FileEntry checkOutFileEntry(
			long fileEntryId, String owner, long expirationTime,
			ServiceContext serviceContext)
		throws PortalException {

		DLFileEntry dlFileEntry = dlFileEntryService.checkOutFileEntry(
			fileEntryId, owner, expirationTime, serviceContext);

		return new LiferayFileEntry(dlFileEntry);
	}

	@Override
	public FileEntry copyFileEntry(
			long userId, long groupId, long fileEntryId, long destFolderId,
			ServiceContext serviceContext)
		throws PortalException {

		DLFileEntry dlFileEntry = dlFileEntryService.copyFileEntry(
			groupId, getRepositoryId(), fileEntryId, destFolderId,
			serviceContext);

		return new LiferayFileEntry(dlFileEntry);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #copyFileEntry(long, long,
	 *             long, long, ServiceContext)}
	 */
	@Deprecated
	@Override
	public FileEntry copyFileEntry(
			long groupId, long fileEntryId, long destFolderId,
			ServiceContext serviceContext)
		throws PortalException {

		return copyFileEntry(
			com.liferay.portal.kernel.repository.util.RepositoryUserUtil.
				getUserId(),
			groupId, fileEntryId, destFolderId, serviceContext);
	}

	@Override
	public void deleteFileEntry(long fileEntryId) throws PortalException {
		dlFileEntryService.deleteFileEntry(fileEntryId);
	}

	@Override
	public void deleteFileEntry(long folderId, String title)
		throws PortalException {

		dlFileEntryService.deleteFileEntry(
			getGroupId(), toFolderId(folderId), title);
	}

	@Override
	public void deleteFileVersion(long fileEntryId, String version)
		throws PortalException {

		dlFileEntryService.deleteFileVersion(fileEntryId, version);
	}

	@Override
	public void deleteFolder(long folderId) throws PortalException {
		dlFolderService.deleteFolder(folderId);
	}

	@Override
	public void deleteFolder(long parentFolderId, String name)
		throws PortalException {

		dlFolderService.deleteFolder(
			getGroupId(), toFolderId(parentFolderId), name);
	}

	@Override
	public List<FileEntry> getFileEntries(
			long folderId, int start, int end, OrderByComparator<FileEntry> obc)
		throws PortalException {

		List<DLFileEntry> dlFileEntries = dlFileEntryService.getFileEntries(
			getGroupId(), toFolderId(folderId), start, end,
			DLFileEntryOrderByComparator.getOrderByComparator(obc));

		return RepositoryModelUtil.toFileEntries(dlFileEntries);
	}

	@Override
	public List<FileEntry> getFileEntries(
			long folderId, long fileEntryTypeId, int start, int end,
			OrderByComparator<FileEntry> obc)
		throws PortalException {

		List<DLFileEntry> dlFileEntries = dlFileEntryService.getFileEntries(
			getGroupId(), toFolderId(folderId), fileEntryTypeId, start, end,
			DLFileEntryOrderByComparator.getOrderByComparator(obc));

		return RepositoryModelUtil.toFileEntries(dlFileEntries);
	}

	@Override
	public List<FileEntry> getFileEntries(
			long folderId, String[] mimeTypes, int start, int end,
			OrderByComparator<FileEntry> obc)
		throws PortalException {

		List<DLFileEntry> dlFileEntries = dlFileEntryService.getFileEntries(
			getGroupId(), toFolderId(folderId), mimeTypes, start, end,
			DLFileEntryOrderByComparator.getOrderByComparator(obc));

		return RepositoryModelUtil.toFileEntries(dlFileEntries);
	}

	@Override
	public List<Object> getFileEntriesAndFileShortcuts(
			long folderId, int status, int start, int end)
		throws PortalException {

		List<Object> dlFileEntriesAndFileShortcuts =
			dlFolderService.getFileEntriesAndFileShortcuts(
				getGroupId(), toFolderId(folderId), status, start, end);

		return RepositoryModelUtil.toFileEntriesAndFolders(
			dlFileEntriesAndFileShortcuts);
	}

	@Override
	public int getFileEntriesAndFileShortcutsCount(long folderId, int status)
		throws PortalException {

		return dlFolderService.getFileEntriesAndFileShortcutsCount(
			getGroupId(), toFolderId(folderId), status);
	}

	@Override
	public int getFileEntriesAndFileShortcutsCount(
			long folderId, int status, String[] mimeTypes)
		throws PortalException {

		return dlFolderService.getFileEntriesAndFileShortcutsCount(
			getGroupId(), toFolderId(folderId), status, mimeTypes);
	}

	@Override
	public int getFileEntriesCount(long folderId) {
		return dlFileEntryService.getFileEntriesCount(
			getGroupId(), toFolderId(folderId));
	}

	@Override
	public int getFileEntriesCount(long folderId, long fileEntryTypeId) {
		return dlFileEntryService.getFileEntriesCount(
			getGroupId(), toFolderId(folderId), fileEntryTypeId);
	}

	@Override
	public int getFileEntriesCount(long folderId, String[] mimeTypes) {
		return dlFileEntryService.getFileEntriesCount(
			getGroupId(), folderId, mimeTypes);
	}

	@Override
	public FileEntry getFileEntry(long fileEntryId) throws PortalException {
		DLFileEntry dlFileEntry = dlFileEntryService.getFileEntry(fileEntryId);

		return new LiferayFileEntry(dlFileEntry);
	}

	@Override
	public FileEntry getFileEntry(long folderId, String title)
		throws PortalException {

		DLFileEntry dlFileEntry = dlFileEntryService.getFileEntry(
			getGroupId(), toFolderId(folderId), title);

		return new LiferayFileEntry(dlFileEntry);
	}

	@Override
	public FileEntry getFileEntryByUuid(String uuid) throws PortalException {
		DLFileEntry dlFileEntry =
			dlFileEntryService.getFileEntryByUuidAndGroupId(uuid, getGroupId());

		return new LiferayFileEntry(dlFileEntry);
	}

	public Lock getFileEntryLock(long fileEntryId) {
		return dlFileEntryService.getFileEntryLock(fileEntryId);
	}

	@Override
	public FileVersion getFileVersion(long fileVersionId)
		throws PortalException {

		DLFileVersion dlFileVersion = dlFileVersionService.getFileVersion(
			fileVersionId);

		return new LiferayFileVersion(dlFileVersion);
	}

	@Override
	public Folder getFolder(long folderId) throws PortalException {
		DLFolder dlFolder = dlFolderService.getFolder(toFolderId(folderId));

		return new LiferayFolder(dlFolder);
	}

	@Override
	public Folder getFolder(long parentFolderId, String name)
		throws PortalException {

		DLFolder dlFolder = dlFolderService.getFolder(
			getGroupId(), toFolderId(parentFolderId), name);

		return new LiferayFolder(dlFolder);
	}

	@Override
	public List<Folder> getFolders(
			long parentFolderId, boolean includeMountfolders, int start,
			int end, OrderByComparator<Folder> obc)
		throws PortalException {

		return getFolders(
			parentFolderId, WorkflowConstants.STATUS_APPROVED,
			includeMountfolders, start, end, obc);
	}

	@Override
	public List<Folder> getFolders(
			long parentFolderId, int status, boolean includeMountfolders,
			int start, int end, OrderByComparator<Folder> obc)
		throws PortalException {

		List<DLFolder> dlFolders = dlFolderService.getFolders(
			getGroupId(), toFolderId(parentFolderId), status,
			includeMountfolders, start, end,
			DLFolderOrderByComparator.getOrderByComparator(obc));

		return RepositoryModelUtil.toFolders(dlFolders);
	}

	@Override
	public List<Object> getFoldersAndFileEntriesAndFileShortcuts(
			long folderId, int status, boolean includeMountFolders, int start,
			int end, OrderByComparator<?> obc)
		throws PortalException {

		List<Object> dlFoldersAndFileEntriesAndFileShortcuts =
			dlFolderService.getFoldersAndFileEntriesAndFileShortcuts(
				getGroupId(), toFolderId(folderId), status, includeMountFolders,
				start, end, obc);

		return RepositoryModelUtil.toFileEntriesAndFolders(
			dlFoldersAndFileEntriesAndFileShortcuts);
	}

	@Override
	public List<Object> getFoldersAndFileEntriesAndFileShortcuts(
			long folderId, int status, String[] mimeTypes,
			boolean includeMountFolders, int start, int end,
			OrderByComparator<?> obc)
		throws PortalException {

		List<Object> dlFoldersAndFileEntriesAndFileShortcuts =
			dlFolderService.getFoldersAndFileEntriesAndFileShortcuts(
				getGroupId(), toFolderId(folderId), status, mimeTypes,
				includeMountFolders, start, end, obc);

		return RepositoryModelUtil.toFileEntriesAndFolders(
			dlFoldersAndFileEntriesAndFileShortcuts);
	}

	@Override
	public int getFoldersAndFileEntriesAndFileShortcutsCount(
			long folderId, int status, boolean includeMountFolders)
		throws PortalException {

		return dlFolderService.getFoldersAndFileEntriesAndFileShortcutsCount(
			getGroupId(), toFolderId(folderId), status, includeMountFolders);
	}

	@Override
	public int getFoldersAndFileEntriesAndFileShortcutsCount(
			long folderId, int status, String[] mimeTypes,
			boolean includeMountFolders)
		throws PortalException {

		return dlFolderService.getFoldersAndFileEntriesAndFileShortcutsCount(
			getGroupId(), toFolderId(folderId), status, mimeTypes,
			includeMountFolders);
	}

	@Override
	public int getFoldersCount(long parentFolderId, boolean includeMountfolders)
		throws PortalException {

		return getFoldersCount(
			parentFolderId, WorkflowConstants.STATUS_APPROVED,
			includeMountfolders);
	}

	@Override
	public int getFoldersCount(
			long parentFolderId, int status, boolean includeMountfolders)
		throws PortalException {

		return dlFolderService.getFoldersCount(
			getGroupId(), toFolderId(parentFolderId), status,
			includeMountfolders);
	}

	@Override
	public int getFoldersFileEntriesCount(List<Long> folderIds, int status) {
		return dlFileEntryService.getFoldersFileEntriesCount(
			getGroupId(), toFolderIds(folderIds), status);
	}

	@Override
	public List<Folder> getMountFolders(
			long parentFolderId, int start, int end,
			OrderByComparator<Folder> obc)
		throws PortalException {

		List<DLFolder> dlFolders = dlFolderService.getMountFolders(
			getGroupId(), toFolderId(parentFolderId), start, end,
			DLFolderOrderByComparator.getOrderByComparator(obc));

		return RepositoryModelUtil.toFolders(dlFolders);
	}

	@Override
	public int getMountFoldersCount(long parentFolderId)
		throws PortalException {

		return dlFolderService.getMountFoldersCount(
			getGroupId(), toFolderId(parentFolderId));
	}

	@Override
	public List<FileEntry> getRepositoryFileEntries(
			long userId, long rootFolderId, int start, int end,
			OrderByComparator<FileEntry> obc)
		throws PortalException {

		List<DLFileEntry> dlFileEntries =
			dlFileEntryService.getGroupFileEntries(
				getGroupId(), userId, toFolderId(rootFolderId), start, end,
				DLFileEntryOrderByComparator.getOrderByComparator(obc));

		return RepositoryModelUtil.toFileEntries(dlFileEntries);
	}

	@Override
	public List<FileEntry> getRepositoryFileEntries(
			long userId, long rootFolderId, String[] mimeTypes, int status,
			int start, int end, OrderByComparator<FileEntry> obc)
		throws PortalException {

		List<DLFileEntry> dlFileEntries =
			dlFileEntryService.getGroupFileEntries(
				getGroupId(), userId, getRepositoryId(),
				toFolderId(rootFolderId), mimeTypes, status, start, end,
				DLFileEntryOrderByComparator.getOrderByComparator(obc));

		return RepositoryModelUtil.toFileEntries(dlFileEntries);
	}

	@Override
	public int getRepositoryFileEntriesCount(long userId, long rootFolderId)
		throws PortalException {

		return dlFileEntryService.getGroupFileEntriesCount(
			getGroupId(), userId, toFolderId(rootFolderId));
	}

	@Override
	public int getRepositoryFileEntriesCount(
			long userId, long rootFolderId, String[] mimeTypes, int status)
		throws PortalException {

		return dlFileEntryService.getGroupFileEntriesCount(
			getGroupId(), userId, getRepositoryId(), toFolderId(rootFolderId),
			mimeTypes, status);
	}

	@Override
	public void getSubfolderIds(List<Long> folderIds, long folderId)
		throws PortalException {

		dlFolderService.getSubfolderIds(
			folderIds, getGroupId(), toFolderId(folderId), true);
	}

	@Override
	public List<Long> getSubfolderIds(long folderId, boolean recurse)
		throws PortalException {

		return dlFolderService.getSubfolderIds(
			getGroupId(), toFolderId(folderId), recurse);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #checkOutFileEntry(long,
	 *             ServiceContext)}
	 */
	@Deprecated
	@Override
	public Lock lockFileEntry(long fileEntryId) throws PortalException {
		FileEntry fileEntry = checkOutFileEntry(
			fileEntryId, new ServiceContext());

		return fileEntry.getLock();
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #checkOutFileEntry(long,
	 *             String, long, ServiceContext)}
	 */
	@Deprecated
	@Override
	public Lock lockFileEntry(
			long fileEntryId, String owner, long expirationTime)
		throws PortalException {

		FileEntry fileEntry = checkOutFileEntry(
			fileEntryId, owner, expirationTime, new ServiceContext());

		return fileEntry.getLock();
	}

	@Override
	public Lock lockFolder(long folderId) throws PortalException {
		return dlFolderService.lockFolder(toFolderId(folderId));
	}

	@Override
	public Lock lockFolder(
			long folderId, String owner, boolean inheritable,
			long expirationTime)
		throws PortalException {

		return dlFolderService.lockFolder(
			toFolderId(folderId), owner, inheritable, expirationTime);
	}

	@Override
	public FileEntry moveFileEntry(
			long fileEntryId, long newFolderId, ServiceContext serviceContext)
		throws PortalException {

		DLFileEntry dlFileEntry = dlFileEntryService.moveFileEntry(
			fileEntryId, toFolderId(newFolderId), serviceContext);

		return new LiferayFileEntry(dlFileEntry);
	}

	@Override
	public Folder moveFolder(
			long folderId, long parentFolderId, ServiceContext serviceContext)
		throws PortalException {

		DLFolder dlFolder = dlFolderService.moveFolder(
			toFolderId(folderId), toFolderId(parentFolderId), serviceContext);

		return new LiferayFolder(dlFolder);
	}

	@Override
	public Lock refreshFileEntryLock(
			String lockUuid, long companyId, long expirationTime)
		throws PortalException {

		return dlFileEntryService.refreshFileEntryLock(
			lockUuid, companyId, expirationTime);
	}

	@Override
	public Lock refreshFolderLock(
			String lockUuid, long companyId, long expirationTime)
		throws PortalException {

		return dlFolderService.refreshFolderLock(
			lockUuid, companyId, expirationTime);
	}

	@Override
	public void revertFileEntry(
			long fileEntryId, String version, ServiceContext serviceContext)
		throws PortalException {

		dlFileEntryService.revertFileEntry(
			fileEntryId, version, serviceContext);
	}

	@Override
	public Hits search(long creatorUserId, int status, int start, int end)
		throws PortalException {

		return dlFileEntryService.search(
			getGroupId(), creatorUserId, status, start, end);
	}

	@Override
	public Hits search(
			long creatorUserId, long folderId, String[] mimeTypes, int status,
			int start, int end)
		throws PortalException {

		return dlFileEntryService.search(
			getGroupId(), creatorUserId, folderId, mimeTypes, status, start,
			end);
	}

	@Override
	public Hits search(SearchContext searchContext) throws SearchException {
		Indexer indexer = null;

		if (searchContext.isIncludeFolders()) {
			indexer = DLSearcher.getInstance();
		}
		else {
			indexer = IndexerRegistryUtil.getIndexer(DLFileEntry.class);
		}

		searchContext.setSearchEngineId(indexer.getSearchEngineId());

		return indexer.search(searchContext);
	}

	@Override
	public Hits search(SearchContext searchContext, Query query)
		throws SearchException {

		return SearchEngineUtil.search(searchContext, query);
	}

	@Override
	public void unlockFolder(long folderId, String lockUuid)
		throws PortalException {

		dlFolderService.unlockFolder(toFolderId(folderId), lockUuid);
	}

	@Override
	public void unlockFolder(long parentFolderId, String name, String lockUuid)
		throws PortalException {

		dlFolderService.unlockFolder(
			getGroupId(), toFolderId(parentFolderId), name, lockUuid);
	}

	@Override
	public FileEntry updateFileEntry(
			long userId, long fileEntryId, String sourceFileName,
			String mimeType, String title, String description, String changeLog,
			boolean majorVersion, File file, ServiceContext serviceContext)
		throws PortalException {

		long fileEntryTypeId = ParamUtil.getLong(
			serviceContext, "fileEntryTypeId", -1L);

		Map<String, Fields> fieldsMap = getFieldsMap(
			serviceContext, fileEntryTypeId);

		long size = 0;

		if (file != null) {
			size = file.length();
		}

		DLFileEntry dlFileEntry = dlFileEntryService.updateFileEntry(
			fileEntryId, sourceFileName, mimeType, title, description,
			changeLog, majorVersion, fileEntryTypeId, fieldsMap, file, null,
			size, serviceContext);

		return new LiferayFileEntry(dlFileEntry);
	}

	@Override
	public FileEntry updateFileEntry(
			long userId, long fileEntryId, String sourceFileName,
			String mimeType, String title, String description, String changeLog,
			boolean majorVersion, InputStream is, long size,
			ServiceContext serviceContext)
		throws PortalException {

		long fileEntryTypeId = ParamUtil.getLong(
			serviceContext, "fileEntryTypeId", -1L);

		Map<String, Fields> fieldsMap = getFieldsMap(
			serviceContext, fileEntryTypeId);

		DLFileEntry dlFileEntry = dlFileEntryService.updateFileEntry(
			fileEntryId, sourceFileName, mimeType, title, description,
			changeLog, majorVersion, fileEntryTypeId, fieldsMap, null, is, size,
			serviceContext);

		return new LiferayFileEntry(dlFileEntry);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #updateFileEntry(long, long,
	 *             String, String, String, String, String, boolean, File,
	 *             ServiceContext)}
	 */
	@Deprecated
	@Override
	public FileEntry updateFileEntry(
			long fileEntryId, String sourceFileName, String mimeType,
			String title, String description, String changeLog,
			boolean majorVersion, File file, ServiceContext serviceContext)
		throws PortalException {

		return updateFileEntry(
			com.liferay.portal.kernel.repository.util.RepositoryUserUtil.
				getUserId(),
			fileEntryId, sourceFileName, mimeType, title, description,
			changeLog, majorVersion, file, serviceContext);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #updateFileEntry(long, long,
	 *             String, String, String, String, String, boolean,
	 *             InputStream, long, ServiceContext)}
	 */
	@Deprecated
	@Override
	public FileEntry updateFileEntry(
			long fileEntryId, String sourceFileName, String mimeType,
			String title, String description, String changeLog,
			boolean majorVersion, InputStream is, long size,
			ServiceContext serviceContext)
		throws PortalException {

		return updateFileEntry(
			com.liferay.portal.kernel.repository.util.RepositoryUserUtil.
				getUserId(),
			fileEntryId, sourceFileName, mimeType, title, description,
			changeLog, majorVersion, is, size, serviceContext);
	}

	@Override
	public Folder updateFolder(
			long folderId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException {

		long defaultFileEntryTypeId = ParamUtil.getLong(
			serviceContext, "defaultFileEntryTypeId");
		SortedArrayList<Long> fileEntryTypeIds = getLongList(
			serviceContext, "dlFileEntryTypesSearchContainerPrimaryKeys");
		boolean overrideFileEntryTypes = ParamUtil.getBoolean(
			serviceContext, "overrideFileEntryTypes");

		DLFolder dlFolder = dlFolderService.updateFolder(
			toFolderId(folderId), name, description, defaultFileEntryTypeId,
			fileEntryTypeIds, overrideFileEntryTypes, serviceContext);

		return new LiferayFolder(dlFolder);
	}

	@Override
	public boolean verifyFileEntryCheckOut(long fileEntryId, String lockUuid)
		throws PortalException {

		return dlFileEntryService.verifyFileEntryCheckOut(
			fileEntryId, lockUuid);
	}

	@Override
	public boolean verifyFileEntryLock(long fileEntryId, String lockUuid)
		throws PortalException {

		return dlFileEntryService.verifyFileEntryLock(fileEntryId, lockUuid);
	}

	@Override
	public boolean verifyInheritableLock(long folderId, String lockUuid)
		throws PortalException {

		return dlFolderService.verifyInheritableLock(
			toFolderId(folderId), lockUuid);
	}

}
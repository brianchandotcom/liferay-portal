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

package com.liferay.portal.repository.liferayrepository;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.Repository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.SortedArrayList;
import com.liferay.portal.model.Lock;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileEntry;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileVersion;
import com.liferay.portal.repository.liferayrepository.model.LiferayFolder;
import com.liferay.portal.service.RepositoryService;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryConstants;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.DLAppHelperLocalService;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalService;
import com.liferay.portlet.documentlibrary.service.DLFileEntryService;
import com.liferay.portlet.documentlibrary.service.DLFileVersionLocalService;
import com.liferay.portlet.documentlibrary.service.DLFileVersionService;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalService;
import com.liferay.portlet.documentlibrary.service.DLFolderService;
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
		RepositoryService repositoryService,
		DLAppHelperLocalService dlAppHelperLocalService,
		DLFileEntryLocalService dlFileEntryLocalService,
		DLFileEntryService dlFileEntryService,
		DLFileVersionLocalService dlFileVersionLocalService,
		DLFileVersionService dlFileVersionService,
		DLFolderLocalService dlFolderLocalService,
		DLFolderService dlFolderService, long repositoryId) {

		super(
			repositoryService, dlAppHelperLocalService, dlFileEntryLocalService,
			dlFileEntryService, dlFileVersionLocalService, dlFileVersionService,
			dlFolderLocalService, dlFolderService, repositoryId);
	}

	public LiferayRepository(
		RepositoryService repositoryService,
		DLAppHelperLocalService dlAppHelperLocalService,
		DLFileEntryLocalService dlFileEntryLocalService,
		DLFileEntryService dlFileEntryService,
		DLFileVersionLocalService dlFileVersionLocalService,
		DLFileVersionService dlFileVersionService,
		DLFolderLocalService dlFolderLocalService,
		DLFolderService dlFolderService, long folderId,
		long fileEntryId, long fileVersionId) {

		super(
			repositoryService, dlAppHelperLocalService, dlFileEntryLocalService,
			dlFileEntryService, dlFileVersionLocalService, dlFileVersionService,
			dlFolderLocalService, dlFolderService, folderId, fileEntryId,
			fileVersionId);
	}

	public FileEntry addFileEntry(
			long folderId, String sourceFileName, String mimeType, String title,
			String description, String changeLog, File file,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		long fileEntryTypeId = ParamUtil.getLong(
			serviceContext, "fileEntryTypeId", -1L);
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

	public FileEntry addFileEntry(
			long folderId, String sourceFileName, String mimeType, String title,
			String description, String changeLog, InputStream is, long size,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		long fileEntryTypeId = ParamUtil.getLong(
			serviceContext, "fileEntryTypeId", -1L);
		Map<String, Fields> fieldsMap = getFieldsMap(
			serviceContext, fileEntryTypeId);

		DLFileEntry dlFileEntry = dlFileEntryService.addFileEntry(
			getGroupId(), getRepositoryId(), toFolderId(folderId),
			sourceFileName, mimeType, title, description, changeLog,
			fileEntryTypeId, fieldsMap, null, is, size, serviceContext);

		addFileEntryResources(dlFileEntry, serviceContext);

		return new LiferayFileEntry(dlFileEntry);
	}

	public Folder addFolder(
			long parentFolderId, String title, String description,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		boolean mountPoint = ParamUtil.getBoolean(serviceContext, "mountPoint");

		DLFolder dlFolder = dlFolderService.addFolder(
			getGroupId(), getRepositoryId(), mountPoint,
			toFolderId(parentFolderId), title, description, serviceContext);

		return new LiferayFolder(dlFolder);
	}

	public void cancelCheckOut(long fileEntryId)
		throws PortalException, SystemException {

		dlFileEntryService.cancelCheckOut(fileEntryId);
	}

	public void checkInFileEntry(
			long fileEntryId, boolean major, String changeLog,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		dlFileEntryService.checkInFileEntry(
			fileEntryId, major, changeLog, serviceContext);
	}

	public void checkInFileEntry(long fileEntryId, String lockUuid)
		throws PortalException, SystemException {

		dlFileEntryService.checkInFileEntry(fileEntryId, lockUuid);
	}

	public FileEntry checkOutFileEntry(long fileEntryId)
		throws PortalException, SystemException {

		DLFileEntry dlFileEntry = dlFileEntryService.checkOutFileEntry(
			fileEntryId);

		return new LiferayFileEntry(dlFileEntry);
	}

	public FileEntry checkOutFileEntry(
			long fileEntryId, String owner, long expirationTime)
		throws PortalException, SystemException {

		DLFileEntry dlFileEntry = dlFileEntryService.checkOutFileEntry(
			fileEntryId, owner, expirationTime);

		return new LiferayFileEntry(dlFileEntry);
	}

	public FileEntry copyFileEntry(
			long groupId, long fileEntryId, long destFolderId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		DLFileEntry dlFileEntry = dlFileEntryService.copyFileEntry(
			groupId, getRepositoryId(), fileEntryId, destFolderId,
			serviceContext);

		return new LiferayFileEntry(dlFileEntry);
	}

	public void deleteFileEntry(long fileEntryId)
		throws PortalException, SystemException {

		dlFileEntryService.deleteFileEntry(fileEntryId);
	}

	public void deleteFileEntry(long folderId, String title)
		throws PortalException, SystemException {

		dlFileEntryService.deleteFileEntry(
			getGroupId(), toFolderId(folderId), title);
	}

	public void deleteFolder(long folderId)
		throws PortalException, SystemException {

		dlFolderService.deleteFolder(folderId);
	}

	public void deleteFolder(long parentFolderId, String title)
		throws PortalException, SystemException {

		dlFolderService.deleteFolder(
			getGroupId(), toFolderId(parentFolderId), title);
	}

	public List<FileEntry> getFileEntries(
			long folderId, int start, int end, OrderByComparator obc)
		throws SystemException {

		List<DLFileEntry> dlFileEntries = dlFileEntryService.getFileEntries(
			getGroupId(), toFolderId(folderId), start, end, obc);

		return toFileEntries(dlFileEntries);
	}

	public List<FileEntry> getFileEntries(
			long folderId, long fileEntryTypeId, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		List<DLFileEntry> dlFileEntries = dlFileEntryService.getFileEntries(
			getGroupId(), toFolderId(folderId), fileEntryTypeId, start, end,
			obc);

		return toFileEntries(dlFileEntries);
	}

	public List<Object> getFileEntriesAndFileShortcuts(
			long folderId, int status, int start, int end)
		throws SystemException {

		List<Object> dlFileEntriesAndFileShortcuts =
			dlFolderService.getFileEntriesAndFileShortcuts(
				getGroupId(), toFolderId(folderId), status, start, end);

		return toFileEntriesAndFolders(dlFileEntriesAndFileShortcuts);
	}

	public int getFileEntriesAndFileShortcutsCount(long folderId, int status)
		throws SystemException {

		return dlFolderService.getFileEntriesAndFileShortcutsCount(
			getGroupId(), toFolderId(folderId), status);
	}

	public FileEntry[] getFileEntriesPrevAndNext(
			long fileEntryId, OrderByComparator obc)
		throws PortalException, SystemException {

		DLFileEntry[] dlFileEntries =
			dlFileEntryService.getFileEntriesPrevAndNext(fileEntryId, obc);

		List<FileEntry> fileEntries =
			toFileEntries(ListUtil.fromArray(dlFileEntries));

		return fileEntries.toArray(new FileEntry[fileEntries.size()]);
	}

	public int getFileEntriesCount(long folderId) throws SystemException {
		return dlFileEntryService.getFileEntriesCount(
			getGroupId(), toFolderId(folderId));
	}

	public int getFileEntriesCount(long folderId, long fileEntryTypeId)
		throws SystemException {

		return dlFileEntryService.getFileEntriesCount(
			getGroupId(), toFolderId(folderId), fileEntryTypeId);
	}

	public FileEntry getFileEntry(long fileEntryId)
		throws PortalException, SystemException {

		DLFileEntry dlFileEntry = dlFileEntryService.getFileEntry(
			fileEntryId);

		return new LiferayFileEntry(dlFileEntry);
	}

	public FileEntry getFileEntry(long folderId, String title)
		throws PortalException, SystemException {

		DLFileEntry dlFileEntry = dlFileEntryService.getFileEntry(
			getGroupId(), toFolderId(folderId), title);

		return new LiferayFileEntry(dlFileEntry);
	}

	public FileEntry getFileEntryByUuid(String uuid)
		throws PortalException, SystemException {

		DLFileEntry dlFileEntry =
			dlFileEntryService.getFileEntryByUuidAndGroupId(uuid, getGroupId());

		return new LiferayFileEntry(dlFileEntry);
	}

	public Lock getFileEntryLock(long fileEntryId) {
		return dlFileEntryService.getFileEntryLock(fileEntryId);
	}

	public FileVersion getFileVersion(long fileVersionId)
		throws PortalException, SystemException {

		DLFileVersion dlFileVersion = dlFileVersionService.getFileVersion(
			fileVersionId);

		return new LiferayFileVersion(dlFileVersion);
	}

	public Folder getFolder(long folderId)
		throws PortalException, SystemException {

		DLFolder dlFolder = dlFolderService.getFolder(toFolderId(folderId));

		return new LiferayFolder(dlFolder);
	}

	public Folder getFolder(long parentFolderId, String title)
		throws PortalException, SystemException {

		DLFolder dlFolder = dlFolderService.getFolder(
			getGroupId(), toFolderId(parentFolderId), title);

		return new LiferayFolder(dlFolder);
	}

	public List<Folder> getFolders(
			long parentFolderId, boolean includeMountfolders, int start,
			int end, OrderByComparator obc)
		throws SystemException {

		List<DLFolder> dlFolders = dlFolderService.getFolders(
			getGroupId(), toFolderId(parentFolderId), includeMountfolders,
			start, end, obc);

		return toFolders(dlFolders);
	}

	public List<Object> getFoldersAndFileEntriesAndFileShortcuts(
			long folderId, int status, boolean includeMountFolders, int start,
			int end, OrderByComparator obc)
		throws SystemException {

		List<Object> dlFoldersAndFileEntriesAndFileShortcuts =
			dlFolderService.getFoldersAndFileEntriesAndFileShortcuts(
				getGroupId(), toFolderId(folderId), status, includeMountFolders,
				start, end, obc);

		return toFileEntriesAndFolders(dlFoldersAndFileEntriesAndFileShortcuts);
	}

	public List<Object> getFoldersAndFileEntriesAndFileShortcuts(
			long folderId, int status, String[] mimeTypes,
			boolean includeMountFolders, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		List<Object> dlFoldersAndFileEntriesAndFileShortcuts =
			dlFolderService.getFoldersAndFileEntriesAndFileShortcuts(
				getGroupId(), toFolderId(folderId), status, mimeTypes,
				includeMountFolders, start, end, obc);

		return toFileEntriesAndFolders(dlFoldersAndFileEntriesAndFileShortcuts);
	}

	public int getFoldersAndFileEntriesAndFileShortcutsCount(
			long folderId, int status, boolean includeMountFolders)
		throws SystemException {

		return dlFolderService.getFoldersAndFileEntriesAndFileShortcutsCount(
			getGroupId(), toFolderId(folderId), status, includeMountFolders);
	}

	public int getFoldersAndFileEntriesAndFileShortcutsCount(
			long folderId, int status, String[] mimeTypes,
			boolean includeMountFolders)
		throws SystemException {

		return dlFolderService.getFoldersAndFileEntriesAndFileShortcutsCount(
			getGroupId(), toFolderId(folderId), status, mimeTypes,
			includeMountFolders);
	}

	public int getFoldersCount(long parentFolderId) throws SystemException {
		return getFoldersCount(parentFolderId, true);
	}

	public int getFoldersCount(long parentFolderId, boolean includeMountfolders)
		throws SystemException {

		return dlFolderService.getFoldersCount(
			getGroupId(), toFolderId(parentFolderId), includeMountfolders);
	}

	public int getFoldersFileEntriesCount(List<Long> folderIds, int status)
		throws SystemException {

		return dlFileEntryService.getFoldersFileEntriesCount(
			getGroupId(), toFolderIds(folderIds), status);
	}

	public List<Folder> getMountFolders(
			long parentFolderId, int start, int end, OrderByComparator obc)
		throws SystemException {

		List<DLFolder> dlFolders = dlFolderService.getMountFolders(
			getGroupId(), toFolderId(parentFolderId), start, end, obc);

		return toFolders(dlFolders);
	}

	public int getMountFoldersCount(long parentFolderId)
		throws SystemException {

		return dlFolderService.getMountFoldersCount(
			getGroupId(), toFolderId(parentFolderId));
	}

	public List<FileEntry> getRepositoryFileEntries(
			long userId, long rootFolderId, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		List<DLFileEntry> dlFileEntries =
			dlFileEntryService.getGroupFileEntries(
				getGroupId(), userId, toFolderId(rootFolderId), start, end,
				obc);

		return toFileEntries(dlFileEntries);
	}

	public int getRepositoryFileEntriesCount(long userId, long rootFolderId)
		throws SystemException {

		return dlFileEntryService.getGroupFileEntriesCount(
			getGroupId(), userId, toFolderId(rootFolderId));
	}

	public void getSubfolderIds(List<Long> folderIds, long folderId)
		throws SystemException {

		dlFolderService.getSubfolderIds(
			folderIds, getGroupId(), toFolderId(folderId));
	}

	public List<Long> getSubfolderIds(long folderId, boolean recurse)
		throws SystemException {

		return dlFolderService.getSubfolderIds(
			getGroupId(), toFolderId(folderId), recurse);
	}

	public Lock lockFolder(long folderId)
		throws PortalException, SystemException {

		return dlFolderService.lockFolder(toFolderId(folderId));
	}

	public Lock lockFolder(
			long folderId, String owner, boolean inheritable,
			long expirationTime)
		throws PortalException, SystemException {

		return dlFolderService.lockFolder(
			toFolderId(folderId), owner, inheritable, expirationTime);
	}

	public FileEntry moveFileEntry(
			long fileEntryId, long newFolderId, ServiceContext serviceContext)
		throws PortalException, SystemException {

		DLFileEntry dlFileEntry = dlFileEntryService.moveFileEntry(
			fileEntryId, toFolderId(newFolderId), serviceContext);

		return new LiferayFileEntry(dlFileEntry);
	}

	public Folder moveFolder(
			long folderId, long parentFolderId, ServiceContext serviceContext)
		throws PortalException, SystemException {

		DLFolder dlFolder = dlFolderService.moveFolder(
			toFolderId(folderId), toFolderId(parentFolderId), serviceContext);

		return new LiferayFolder(dlFolder);
	}

	public Lock refreshFileEntryLock(String lockUuid, long expirationTime)
		throws PortalException, SystemException {

		return dlFileEntryService.refreshFileEntryLock(
			lockUuid, expirationTime);
	}

	public Lock refreshFolderLock(String lockUuid, long expirationTime)
		throws PortalException, SystemException {

		return dlFolderService.refreshFolderLock(lockUuid, expirationTime);
	}

	public void revertFileEntry(
			long fileEntryId, String version, ServiceContext serviceContext)
		throws PortalException, SystemException {

		dlFileEntryService.revertFileEntry(
			fileEntryId, version, serviceContext);
	}

	public Hits search(SearchContext searchContext) throws SearchException {
		Indexer indexer = IndexerRegistryUtil.getIndexer(
			DLFileEntryConstants.getClassName());

		BooleanQuery fullQuery = indexer.getFullQuery(searchContext);

		return SearchEngineUtil.search(searchContext, fullQuery);
	}

	public Hits search(SearchContext searchContext, Query query)
		throws SearchException {

		return SearchEngineUtil.search(searchContext, query);
	}

	public void unlockFolder(long folderId, String lockUuid)
		throws PortalException, SystemException {

		dlFolderService.unlockFolder(
			getGroupId(), toFolderId(folderId), lockUuid);
	}

	public void unlockFolder(long parentFolderId, String title, String lockUuid)
		throws PortalException, SystemException {

		dlFolderService.unlockFolder(
			getGroupId(), toFolderId(parentFolderId), title, lockUuid);
	}

	public FileEntry updateFileEntry(
			long fileEntryId, String sourceFileName, String mimeType,
			String title, String description, String changeLog,
			boolean majorVersion, File file, ServiceContext serviceContext)
		throws PortalException, SystemException {

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

	public FileEntry updateFileEntry(
			long fileEntryId, String sourceFileName, String mimeType,
			String title, String description, String changeLog,
			boolean majorVersion, InputStream is, long size,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

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

	public Folder updateFolder(
			long folderId, String title, String description,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		long defaultFileEntryTypeId = ParamUtil.getLong(
			serviceContext, "defaultFileEntryTypeId");
		SortedArrayList<Long> fileEntryTypeIds = getLongList(
			serviceContext, "fileEntryTypeSearchContainerPrimaryKeys");
		boolean overrideFileEntryTypes = ParamUtil.getBoolean(
			serviceContext, "overrideFileEntryTypes");

		DLFolder dlFolder = dlFolderService.updateFolder(
			toFolderId(folderId), title, description, defaultFileEntryTypeId,
			fileEntryTypeIds, overrideFileEntryTypes, serviceContext);

		return new LiferayFolder(dlFolder);
	}

	public boolean verifyFileEntryCheckOut(long fileEntryId, String lockUuid)
		throws PortalException, SystemException {

		return dlFileEntryService.verifyFileEntryCheckOut(
			fileEntryId, lockUuid);
	}

	public boolean verifyInheritableLock(long folderId, String lockUuid)
		throws PortalException, SystemException {

		return dlFolderService.verifyInheritableLock(
			toFolderId(folderId), lockUuid);
	}

}
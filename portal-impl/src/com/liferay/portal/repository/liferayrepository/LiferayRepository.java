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
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
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
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.NoSuchFileVersionException;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
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
		ResourceLocalService resourceLocalService, long repositoryId) {

		super(
			repositoryLocalService, repositoryService, dlAppHelperLocalService,
			dlFileEntryLocalService, dlFileEntryService,
			dlFileEntryTypeLocalService, dlFileVersionLocalService,
			dlFileVersionService, dlFolderLocalService, dlFolderService,
			resourceLocalService, repositoryId);
	}

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
		ResourceLocalService resourceLocalService, long folderId,
		long fileEntryId, long fileVersionId) {

		super(
			repositoryLocalService, repositoryService, dlAppHelperLocalService,
			dlFileEntryLocalService, dlFileEntryService,
			dlFileEntryTypeLocalService, dlFileVersionLocalService,
			dlFileVersionService, dlFolderLocalService, dlFolderService,
			resourceLocalService, folderId, fileEntryId, fileVersionId);
	}

	@Override
	public FileEntry addFileEntry(
			long folderId, String sourceFileName, String mimeType, String title,
			String description, String changeLog, File file,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

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
			long folderId, String sourceFileName, String mimeType, String title,
			String description, String changeLog, InputStream is, long size,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

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

	@Override
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

	@Override
	public FileVersion cancelCheckOut(long fileEntryId)
		throws PortalException, SystemException {

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
		throws PortalException, SystemException {

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
		throws PortalException, SystemException {

		checkInFileEntry(fileEntryId, lockUuid, new ServiceContext());
	}

	@Override
	public void checkInFileEntry(
			long fileEntryId, String lockUuid, ServiceContext serviceContext)
		throws PortalException, SystemException {

		dlFileEntryService.checkInFileEntry(
			fileEntryId, lockUuid, serviceContext);
	}

	@Override
	public FileEntry checkOutFileEntry(
			long fileEntryId, ServiceContext serviceContext)
		throws PortalException, SystemException {

		DLFileEntry dlFileEntry = dlFileEntryService.checkOutFileEntry(
			fileEntryId, serviceContext);

		return new LiferayFileEntry(dlFileEntry);
	}

	@Override
	public FileEntry checkOutFileEntry(
			long fileEntryId, String owner, long expirationTime,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		DLFileEntry dlFileEntry = dlFileEntryService.checkOutFileEntry(
			fileEntryId, owner, expirationTime, serviceContext);

		return new LiferayFileEntry(dlFileEntry);
	}

	@Override
	public FileEntry copyFileEntry(
			long groupId, long fileEntryId, long destFolderId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		DLFileEntry dlFileEntry = dlFileEntryService.copyFileEntry(
			groupId, getRepositoryId(), fileEntryId, destFolderId,
			serviceContext);

		return new LiferayFileEntry(dlFileEntry);
	}

	@Override
	public void deleteFileEntry(long fileEntryId)
		throws PortalException, SystemException {

		dlFileEntryService.deleteFileEntry(fileEntryId);
	}

	@Override
	public void deleteFileEntry(long folderId, String title)
		throws PortalException, SystemException {

		dlFileEntryService.deleteFileEntry(
			getGroupId(), toFolderId(folderId), title);
	}

	@Override
	public void deleteFileVersion(long fileEntryId, String version)
		throws PortalException, SystemException {

		dlFileEntryService.deleteFileVersion(fileEntryId, version);
	}

	@Override
	public void deleteFolder(long folderId)
		throws PortalException, SystemException {

		dlFolderService.deleteFolder(folderId);
	}

	@Override
	public void deleteFolder(long parentFolderId, String title)
		throws PortalException, SystemException {

		dlFolderService.deleteFolder(
			getGroupId(), toFolderId(parentFolderId), title);
	}

	@Override
	public List<FileEntry> getFileEntries(
			long folderId, int start, int end, OrderByComparator obc)
		throws PortalException, SystemException {

		List<DLFileEntry> dlFileEntries = dlFileEntryService.getFileEntries(
			getGroupId(), toFolderId(folderId), start, end, obc);

		return RepositoryModelUtil.toFileEntries(dlFileEntries);
	}

	@Override
	public List<FileEntry> getFileEntries(
			long folderId, long fileEntryTypeId, int start, int end,
			OrderByComparator obc)
		throws PortalException, SystemException {

		List<DLFileEntry> dlFileEntries = dlFileEntryService.getFileEntries(
			getGroupId(), toFolderId(folderId), fileEntryTypeId, start, end,
			obc);

		return RepositoryModelUtil.toFileEntries(dlFileEntries);
	}

	@Override
	public List<FileEntry> getFileEntries(
			long folderId, String[] mimeTypes, int start, int end,
			OrderByComparator obc)
		throws PortalException, SystemException {

		List<DLFileEntry> dlFileEntries = dlFileEntryService.getFileEntries(
			getGroupId(), toFolderId(folderId), mimeTypes, start, end, obc);

		return RepositoryModelUtil.toFileEntries(dlFileEntries);
	}

	@Override
	public List<Object> getFileEntriesAndFileShortcuts(
			long folderId, int status, int start, int end)
		throws PortalException, SystemException {

		List<Object> dlFileEntriesAndFileShortcuts =
			dlFolderService.getFileEntriesAndFileShortcuts(
				getGroupId(), toFolderId(folderId), status, start, end);

		return RepositoryModelUtil.toFileEntriesAndFolders(
			dlFileEntriesAndFileShortcuts);
	}

	@Override
	public int getFileEntriesAndFileShortcutsCount(long folderId, int status)
		throws PortalException, SystemException {

		return dlFolderService.getFileEntriesAndFileShortcutsCount(
			getGroupId(), toFolderId(folderId), status);
	}

	@Override
	public int getFileEntriesAndFileShortcutsCount(
			long folderId, int status, String[] mimeTypes)
		throws PortalException, SystemException {

		return dlFolderService.getFileEntriesAndFileShortcutsCount(
			getGroupId(), toFolderId(folderId), status, mimeTypes);
	}

	@Override
	public int getFileEntriesCount(long folderId) throws SystemException {
		return dlFileEntryService.getFileEntriesCount(
			getGroupId(), toFolderId(folderId));
	}

	@Override
	public int getFileEntriesCount(long folderId, long fileEntryTypeId)
		throws SystemException {

		return dlFileEntryService.getFileEntriesCount(
			getGroupId(), toFolderId(folderId), fileEntryTypeId);
	}

	@Override
	public int getFileEntriesCount(long folderId, String[] mimeTypes)
		throws SystemException {

		return dlFileEntryService.getFileEntriesCount(
			getGroupId(), folderId, mimeTypes);
	}

	@Override
	public FileEntry getFileEntry(long fileEntryId)
		throws PortalException, SystemException {

		DLFileEntry dlFileEntry = dlFileEntryService.getFileEntry(fileEntryId);

		return new LiferayFileEntry(dlFileEntry);
	}

	@Override
	public FileEntry getFileEntry(long folderId, String title)
		throws PortalException, SystemException {

		DLFileEntry dlFileEntry = dlFileEntryService.getFileEntry(
			getGroupId(), toFolderId(folderId), title);

		return new LiferayFileEntry(dlFileEntry);
	}

	@Override
	public FileEntry getFileEntryByUuid(String uuid)
		throws PortalException, SystemException {

		DLFileEntry dlFileEntry =
			dlFileEntryService.getFileEntryByUuidAndGroupId(uuid, getGroupId());

		return new LiferayFileEntry(dlFileEntry);
	}

	public Lock getFileEntryLock(long fileEntryId) {
		return dlFileEntryService.getFileEntryLock(fileEntryId);
	}

	@Override
	public FileVersion getFileVersion(long fileVersionId)
		throws PortalException, SystemException {

		DLFileVersion dlFileVersion = dlFileVersionService.getFileVersion(
			fileVersionId);

		return new LiferayFileVersion(dlFileVersion);
	}

	@Override
	public Folder getFolder(long folderId)
		throws PortalException, SystemException {

		DLFolder dlFolder = dlFolderService.getFolder(toFolderId(folderId));

		return new LiferayFolder(dlFolder);
	}

	@Override
	public Folder getFolder(long parentFolderId, String title)
		throws PortalException, SystemException {

		DLFolder dlFolder = dlFolderService.getFolder(
			getGroupId(), toFolderId(parentFolderId), title);

		return new LiferayFolder(dlFolder);
	}

	@Override
	public List<Folder> getFolders(
			long parentFolderId, boolean includeMountfolders, int start,
			int end, OrderByComparator obc)
		throws PortalException, SystemException {

		return getFolders(
			parentFolderId, WorkflowConstants.STATUS_APPROVED,
			includeMountfolders, start, end, obc);
	}

	@Override
	public List<Folder> getFolders(
			long parentFolderId, int status, boolean includeMountfolders,
			int start, int end, OrderByComparator obc)
		throws PortalException, SystemException {

		List<DLFolder> dlFolders = dlFolderService.getFolders(
			getGroupId(), toFolderId(parentFolderId), status,
			includeMountfolders, start, end, obc);

		return RepositoryModelUtil.toFolders(dlFolders);
	}

	@Override
	public List<Object> getFoldersAndFileEntriesAndFileShortcuts(
			long folderId, int status, boolean includeMountFolders, int start,
			int end, OrderByComparator obc)
		throws PortalException, SystemException {

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
			OrderByComparator obc)
		throws PortalException, SystemException {

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
		throws PortalException, SystemException {

		return dlFolderService.getFoldersAndFileEntriesAndFileShortcutsCount(
			getGroupId(), toFolderId(folderId), status, includeMountFolders);
	}

	@Override
	public int getFoldersAndFileEntriesAndFileShortcutsCount(
			long folderId, int status, String[] mimeTypes,
			boolean includeMountFolders)
		throws PortalException, SystemException {

		return dlFolderService.getFoldersAndFileEntriesAndFileShortcutsCount(
			getGroupId(), toFolderId(folderId), status, mimeTypes,
			includeMountFolders);
	}

	@Override
	public int getFoldersCount(long parentFolderId, boolean includeMountfolders)
		throws PortalException, SystemException {

		return getFoldersCount(
			parentFolderId, WorkflowConstants.STATUS_APPROVED,
			includeMountfolders);
	}

	@Override
	public int getFoldersCount(
			long parentFolderId, int status, boolean includeMountfolders)
		throws PortalException, SystemException {

		return dlFolderService.getFoldersCount(
			getGroupId(), toFolderId(parentFolderId), status,
			includeMountfolders);
	}

	@Override
	public int getFoldersFileEntriesCount(List<Long> folderIds, int status)
		throws SystemException {

		return dlFileEntryService.getFoldersFileEntriesCount(
			getGroupId(), toFolderIds(folderIds), status);
	}

	@Override
	public List<Folder> getMountFolders(
			long parentFolderId, int start, int end, OrderByComparator obc)
		throws PortalException, SystemException {

		List<DLFolder> dlFolders = dlFolderService.getMountFolders(
			getGroupId(), toFolderId(parentFolderId), start, end, obc);

		return RepositoryModelUtil.toFolders(dlFolders);
	}

	@Override
	public int getMountFoldersCount(long parentFolderId)
		throws PortalException, SystemException {

		return dlFolderService.getMountFoldersCount(
			getGroupId(), toFolderId(parentFolderId));
	}

	@Override
	public List<FileEntry> getRepositoryFileEntries(
			long userId, long rootFolderId, int start, int end,
			OrderByComparator obc)
		throws PortalException, SystemException {

		List<DLFileEntry> dlFileEntries =
			dlFileEntryService.getGroupFileEntries(
				getGroupId(), userId, toFolderId(rootFolderId), start, end,
				obc);

		return RepositoryModelUtil.toFileEntries(dlFileEntries);
	}

	@Override
	public List<FileEntry> getRepositoryFileEntries(
			long userId, long rootFolderId, String[] mimeTypes, int status,
			int start, int end, OrderByComparator obc)
		throws PortalException, SystemException {

		List<DLFileEntry> dlFileEntries =
			dlFileEntryService.getGroupFileEntries(
				getGroupId(), userId, getRepositoryId(),
				toFolderId(rootFolderId), mimeTypes, status, start, end, obc);

		return RepositoryModelUtil.toFileEntries(dlFileEntries);
	}

	@Override
	public int getRepositoryFileEntriesCount(long userId, long rootFolderId)
		throws PortalException, SystemException {

		return dlFileEntryService.getGroupFileEntriesCount(
			getGroupId(), userId, toFolderId(rootFolderId));
	}

	@Override
	public int getRepositoryFileEntriesCount(
			long userId, long rootFolderId, String[] mimeTypes, int status)
		throws PortalException, SystemException {

		return dlFileEntryService.getGroupFileEntriesCount(
			getGroupId(), userId, getRepositoryId(), toFolderId(rootFolderId),
			mimeTypes, status);
	}

	@Override
	public void getSubfolderIds(List<Long> folderIds, long folderId)
		throws PortalException, SystemException {

		dlFolderService.getSubfolderIds(
			folderIds, getGroupId(), toFolderId(folderId), true);
	}

	@Override
	public List<Long> getSubfolderIds(long folderId, boolean recurse)
		throws PortalException, SystemException {

		return dlFolderService.getSubfolderIds(
			getGroupId(), toFolderId(folderId), recurse);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #checkOutFileEntry(long,
	 *             ServiceContext)}
	 */
	@Deprecated
	@Override
	public Lock lockFileEntry(long fileEntryId)
		throws PortalException, SystemException {

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
		throws PortalException, SystemException {

		FileEntry fileEntry = checkOutFileEntry(
			fileEntryId, owner, expirationTime, new ServiceContext());

		return fileEntry.getLock();
	}

	@Override
	public Lock lockFolder(long folderId)
		throws PortalException, SystemException {

		return dlFolderService.lockFolder(toFolderId(folderId));
	}

	@Override
	public Lock lockFolder(
			long folderId, String owner, boolean inheritable,
			long expirationTime)
		throws PortalException, SystemException {

		return dlFolderService.lockFolder(
			toFolderId(folderId), owner, inheritable, expirationTime);
	}

	@Override
	public FileEntry moveFileEntry(
			long fileEntryId, long newFolderId, ServiceContext serviceContext)
		throws PortalException, SystemException {

		DLFileEntry dlFileEntry = dlFileEntryService.moveFileEntry(
			fileEntryId, toFolderId(newFolderId), serviceContext);

		return new LiferayFileEntry(dlFileEntry);
	}

	@Override
	public Folder moveFolder(
			long folderId, long parentFolderId, ServiceContext serviceContext)
		throws PortalException, SystemException {

		DLFolder dlFolder = dlFolderService.moveFolder(
			toFolderId(folderId), toFolderId(parentFolderId), serviceContext);

		return new LiferayFolder(dlFolder);
	}

	@Override
	public Lock refreshFileEntryLock(
			String lockUuid, long companyId, long expirationTime)
		throws PortalException, SystemException {

		return dlFileEntryService.refreshFileEntryLock(
			lockUuid, companyId, expirationTime);
	}

	@Override
	public Lock refreshFolderLock(
			String lockUuid, long companyId, long expirationTime)
		throws PortalException, SystemException {

		return dlFolderService.refreshFolderLock(
			lockUuid, companyId, expirationTime);
	}

	@Override
	public void revertFileEntry(
			long fileEntryId, String version, ServiceContext serviceContext)
		throws PortalException, SystemException {

		dlFileEntryService.revertFileEntry(
			fileEntryId, version, serviceContext);
	}

	@Override
	public Hits search(long creatorUserId, int status, int start, int end)
		throws PortalException, SystemException {

		return dlFileEntryService.search(
			getGroupId(), creatorUserId, status, start, end);
	}

	@Override
	public Hits search(
			long creatorUserId, long folderId, String[] mimeTypes, int status,
			int start, int end)
		throws PortalException, SystemException {

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
		throws PortalException, SystemException {

		dlFolderService.unlockFolder(toFolderId(folderId), lockUuid);
	}

	@Override
	public void unlockFolder(long parentFolderId, String title, String lockUuid)
		throws PortalException, SystemException {

		dlFolderService.unlockFolder(
			getGroupId(), toFolderId(parentFolderId), title, lockUuid);
	}

	@Override
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

	@Override
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

	@Override
	public Folder updateFolder(
			long folderId, String title, String description,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		long defaultFileEntryTypeId = ParamUtil.getLong(
			serviceContext, "defaultFileEntryTypeId");
		SortedArrayList<Long> fileEntryTypeIds = getLongList(
			serviceContext, "dlFileEntryTypesSearchContainerPrimaryKeys");
		boolean overrideFileEntryTypes = ParamUtil.getBoolean(
			serviceContext, "overrideFileEntryTypes");

		DLFolder dlFolder = dlFolderService.updateFolder(
			toFolderId(folderId), title, description, defaultFileEntryTypeId,
			fileEntryTypeIds, overrideFileEntryTypes, serviceContext);

		return new LiferayFolder(dlFolder);
	}

	@Override
	public boolean verifyFileEntryCheckOut(long fileEntryId, String lockUuid)
		throws PortalException, SystemException {

		return dlFileEntryService.verifyFileEntryCheckOut(
			fileEntryId, lockUuid);
	}

	@Override
	public boolean verifyFileEntryLock(long fileEntryId, String lockUuid)
		throws PortalException, SystemException {

		return dlFileEntryService.verifyFileEntryLock(fileEntryId, lockUuid);
	}

	@Override
	public boolean verifyInheritableLock(long folderId, String lockUuid)
		throws PortalException, SystemException {

		return dlFolderService.verifyInheritableLock(
			toFolderId(folderId), lockUuid);
	}

	@Override
	protected void initByFileEntryId(long fileEntryId) {
		try {
			DLFileEntry dlFileEntry = dlFileEntryService.getFileEntry(
				fileEntryId);

			initByRepositoryId(dlFileEntry.getRepositoryId());
		}
		catch (Exception e) {
			if (_log.isTraceEnabled()) {
				if (e instanceof NoSuchFileEntryException) {
					_log.trace(e.getMessage());
				}
				else {
					_log.trace(e, e);
				}
			}
		}
	}

	@Override
	protected void initByFileVersionId(long fileVersionId) {
		try {
			DLFileVersion dlFileVersion = dlFileVersionService.getFileVersion(
				fileVersionId);

			initByRepositoryId(dlFileVersion.getRepositoryId());
		}
		catch (Exception e) {
			if (_log.isTraceEnabled()) {
				if (e instanceof NoSuchFileVersionException) {
					_log.trace(e.getMessage());
				}
				else {
					_log.trace(e, e);
				}
			}
		}
	}

	@Override
	protected void initByFolderId(long folderId) {
		try {
			DLFolder dlFolder = dlFolderService.getFolder(folderId);

			initByRepositoryId(dlFolder.getRepositoryId());
		}
		catch (Exception e) {
			if (_log.isTraceEnabled()) {
				if (e instanceof NoSuchFolderException) {
					_log.trace(e.getMessage());
				}
				else {
					_log.trace(e, e);
				}
			}
		}
	}

	@Override
	protected void initByRepositoryId(long repositoryId) {
		setGroupId(repositoryId);
		setRepositoryId(repositoryId);

		try {
			com.liferay.portal.model.Repository repository =
				repositoryService.getRepository(repositoryId);

			setDlFolderId(repository.getDlFolderId());
			setGroupId(repository.getGroupId());
			setRepositoryId(repository.getRepositoryId());
		}
		catch (Exception e) {
		}
	}

	private static Log _log = LogFactoryUtil.getLog(LiferayRepository.class);

}
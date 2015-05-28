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

package com.liferay.portal.repository;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.Repository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.repository.model.RepositoryEntry;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.model.Lock;
import com.liferay.portal.service.ServiceContext;

import java.io.File;
import java.io.InputStream;

import java.util.List;

/**
 * @author Iván Zaera
 */
public class InitializedRepository
	extends InitializedDocumentRepository<Repository> implements Repository {

	@Deprecated
	@Override
	public FileEntry addFileEntry(
			long folderId, String sourceFileName, String mimeType, String title,
			String description, String changeLog, File file,
			ServiceContext serviceContext)
		throws PortalException {

		checkDocumentRepository();

		return getDocumentRepository().addFileEntry(
			folderId, sourceFileName, mimeType, title, description, changeLog,
			file, serviceContext);
	}

	@Deprecated
	@Override
	public FileEntry addFileEntry(
			long folderId, String sourceFileName, String mimeType, String title,
			String description, String changeLog, InputStream is, long size,
			ServiceContext serviceContext)
		throws PortalException {

		checkDocumentRepository();

		return getDocumentRepository().addFileEntry(
			folderId, sourceFileName, mimeType, title, description, changeLog,
			is, size, serviceContext);
	}

	@Deprecated
	@Override
	public Folder addFolder(
			long parentFolderId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException {

		checkDocumentRepository();

		return getDocumentRepository().addFolder(
			parentFolderId, name, description, serviceContext);
	}

	@Override
	public FileVersion cancelCheckOut(long fileEntryId) throws PortalException {
		checkDocumentRepository();

		return getDocumentRepository().cancelCheckOut(fileEntryId);
	}

	@Deprecated
	@Override
	public void checkInFileEntry(
			long fileEntryId, boolean major, String changeLog,
			ServiceContext serviceContext)
		throws PortalException {

		checkDocumentRepository();

		getDocumentRepository().checkInFileEntry(
			fileEntryId, major, changeLog, serviceContext);
	}

	@Deprecated
	@Override
	public void checkInFileEntry(long fileEntryId, String lockUuid)
		throws PortalException {

		checkDocumentRepository();

		getDocumentRepository().checkInFileEntry(fileEntryId, lockUuid);
	}

	@Deprecated
	@Override
	public void checkInFileEntry(
			long fileEntryId, String lockUuid, ServiceContext serviceContext)
		throws PortalException {

		checkDocumentRepository();

		getDocumentRepository().checkInFileEntry(
			fileEntryId, lockUuid, serviceContext);
	}

	@Override
	public FileEntry checkOutFileEntry(
			long fileEntryId, ServiceContext serviceContext)
		throws PortalException {

		checkDocumentRepository();

		return getDocumentRepository().checkOutFileEntry(
			fileEntryId, serviceContext);
	}

	@Override
	public FileEntry checkOutFileEntry(
			long fileEntryId, String owner, long expirationTime,
			ServiceContext serviceContext)
		throws PortalException {

		checkDocumentRepository();

		return getDocumentRepository().checkOutFileEntry(
			fileEntryId, owner, expirationTime, serviceContext);
	}

	@Deprecated
	@Override
	public FileEntry copyFileEntry(
			long groupId, long fileEntryId, long destFolderId,
			ServiceContext serviceContext)
		throws PortalException {

		checkDocumentRepository();

		return getDocumentRepository().copyFileEntry(
			groupId, fileEntryId, destFolderId, serviceContext);
	}

	@Override
	public void deleteFileEntry(long folderId, String title)
		throws PortalException {

		checkDocumentRepository();

		getDocumentRepository().deleteFileEntry(folderId, title);
	}

	@Override
	public void deleteFileVersion(long fileEntryId, String version)
		throws PortalException {

		checkDocumentRepository();

		getDocumentRepository().deleteFileVersion(fileEntryId, version);
	}

	@Override
	public void deleteFolder(long parentFolderId, String name)
		throws PortalException {

		checkDocumentRepository();

		getDocumentRepository().deleteFolder(parentFolderId, name);
	}

	@Override
	public List<FileEntry> getFileEntries(
			long folderId, long fileEntryTypeId, int start, int end,
			OrderByComparator<FileEntry> obc)
		throws PortalException {

		checkDocumentRepository();

		return getDocumentRepository().getFileEntries(
			folderId, fileEntryTypeId, start, end, obc);
	}

	@Override
	public List<FileEntry> getFileEntries(
			long folderId, String[] mimeTypes, int start, int end,
			OrderByComparator<FileEntry> obc)
		throws PortalException {

		checkDocumentRepository();

		return getDocumentRepository().getFileEntries(
			folderId, mimeTypes, start, end, obc);
	}

	@Override
	public List<RepositoryEntry> getFileEntriesAndFileShortcuts(
			long folderId, int status, int start, int end)
		throws PortalException {

		checkDocumentRepository();

		return getDocumentRepository().getFileEntriesAndFileShortcuts(
			folderId, status, start, end);
	}

	@Override
	public int getFileEntriesAndFileShortcutsCount(long folderId, int status)
		throws PortalException {

		checkDocumentRepository();

		return getDocumentRepository().getFileEntriesAndFileShortcutsCount(
			folderId, status);
	}

	@Override
	public int getFileEntriesAndFileShortcutsCount(
			long folderId, int status, String[] mimeTypes)
		throws PortalException {

		checkDocumentRepository();

		return getDocumentRepository().getFileEntriesAndFileShortcutsCount(
			folderId, status, mimeTypes);
	}

	@Override
	public int getFileEntriesCount(long folderId, long fileEntryTypeId)
		throws PortalException {

		checkDocumentRepository();

		return getDocumentRepository().getFileEntriesCount(
			folderId, fileEntryTypeId);
	}

	@Override
	public int getFileEntriesCount(long folderId, String[] mimeTypes)
		throws PortalException {

		checkDocumentRepository();

		return getDocumentRepository().getFileEntriesCount(folderId, mimeTypes);
	}

	@Override
	public List<Folder> getFolders(
			long parentFolderId, boolean includeMountFolders, int start,
			int end, OrderByComparator<Folder> obc)
		throws PortalException {

		checkDocumentRepository();

		return getDocumentRepository().getFolders(
			parentFolderId, includeMountFolders, start, end, obc);
	}

	@Override
	public List<Folder> getFolders(
			long parentFolderId, int status, boolean includeMountFolders,
			int start, int end, OrderByComparator<Folder> obc)
		throws PortalException {

		checkDocumentRepository();

		return getDocumentRepository().getFolders(
			parentFolderId, status, includeMountFolders, start, end, obc);
	}

	@Override
	public List<RepositoryEntry> getFoldersAndFileEntriesAndFileShortcuts(
			long folderId, int status, boolean includeMountFolders, int start,
			int end, OrderByComparator<?> obc)
		throws PortalException {

		checkDocumentRepository();

		return getDocumentRepository().getFoldersAndFileEntriesAndFileShortcuts(
			folderId, status, includeMountFolders, start, end, obc);
	}

	@Override
	public List<RepositoryEntry> getFoldersAndFileEntriesAndFileShortcuts(
			long folderId, int status, String[] mimetypes,
			boolean includeMountFolders, int start, int end,
			OrderByComparator<?> obc)
		throws PortalException {

		checkDocumentRepository();

		return getDocumentRepository().getFoldersAndFileEntriesAndFileShortcuts(
			folderId, status, mimetypes, includeMountFolders, start, end, obc);
	}

	@Override
	public int getFoldersAndFileEntriesAndFileShortcutsCount(
			long folderId, int status, boolean includeMountFolders)
		throws PortalException {

		checkDocumentRepository();

		return getDocumentRepository().
			getFoldersAndFileEntriesAndFileShortcutsCount(
				folderId, status, includeMountFolders);
	}

	@Override
	public int getFoldersAndFileEntriesAndFileShortcutsCount(
			long folderId, int status, String[] mimetypes,
			boolean includeMountFolders)
		throws PortalException {

		checkDocumentRepository();

		return getDocumentRepository().
			getFoldersAndFileEntriesAndFileShortcutsCount(
				folderId, status, mimetypes, includeMountFolders);
	}

	@Override
	public int getFoldersCount(long parentFolderId, boolean includeMountfolders)
		throws PortalException {

		checkDocumentRepository();

		return getDocumentRepository().getFoldersCount(
			parentFolderId, includeMountfolders);
	}

	@Override
	public int getFoldersCount(
			long parentFolderId, int status, boolean includeMountfolders)
		throws PortalException {

		checkDocumentRepository();

		return getDocumentRepository().getFoldersCount(
			parentFolderId, status, includeMountfolders);
	}

	@Override
	public int getFoldersFileEntriesCount(List<Long> folderIds, int status)
		throws PortalException {

		checkDocumentRepository();

		return getDocumentRepository().getFoldersFileEntriesCount(
			folderIds, status);
	}

	@Override
	public List<Folder> getMountFolders(
			long parentFolderId, int start, int end,
			OrderByComparator<Folder> obc)
		throws PortalException {

		checkDocumentRepository();

		return getDocumentRepository().getMountFolders(
			parentFolderId, start, end, obc);
	}

	@Override
	public int getMountFoldersCount(long parentFolderId)
		throws PortalException {

		checkDocumentRepository();

		return getDocumentRepository().getMountFoldersCount(parentFolderId);
	}

	@Override
	public List<FileEntry> getRepositoryFileEntries(
			long userId, long rootFolderId, String[] mimeTypes, int status,
			int start, int end, OrderByComparator<FileEntry> obc)
		throws PortalException {

		checkDocumentRepository();

		return getDocumentRepository().getRepositoryFileEntries(
			userId, rootFolderId, mimeTypes, status, start, end, obc);
	}

	@Override
	public int getRepositoryFileEntriesCount(long userId, long rootFolderId)
		throws PortalException {

		checkDocumentRepository();

		return getDocumentRepository().getRepositoryFileEntriesCount(
			userId, rootFolderId);
	}

	@Override
	public int getRepositoryFileEntriesCount(
			long userId, long rootFolderId, String[] mimeTypes, int status)
		throws PortalException {

		checkDocumentRepository();

		return getDocumentRepository().getRepositoryFileEntriesCount(
			userId, rootFolderId, mimeTypes, status);
	}

	@Override
	public void getSubfolderIds(List<Long> folderIds, long folderId)
		throws PortalException {

		checkDocumentRepository();

		getDocumentRepository().getSubfolderIds(folderIds, folderId);
	}

	@Override
	public List<Long> getSubfolderIds(long folderId, boolean recurse)
		throws PortalException {

		checkDocumentRepository();

		return getDocumentRepository().getSubfolderIds(folderId, recurse);
	}

	@Deprecated
	@Override
	public Lock lockFileEntry(long fileEntryId) throws PortalException {
		checkDocumentRepository();

		return getDocumentRepository().lockFileEntry(fileEntryId);
	}

	@Deprecated
	@Override
	public Lock lockFileEntry(
			long fileEntryId, String owner, long expirationTime)
		throws PortalException {

		checkDocumentRepository();

		return getDocumentRepository().lockFileEntry(
			fileEntryId, owner, expirationTime);
	}

	@Override
	public Lock lockFolder(long folderId) throws PortalException {
		checkDocumentRepository();

		return getDocumentRepository().lockFolder(folderId);
	}

	@Override
	public Lock lockFolder(
			long folderId, String owner, boolean inheritable,
			long expirationTime)
		throws PortalException {

		checkDocumentRepository();

		return getDocumentRepository().lockFolder(
			folderId, owner, inheritable, expirationTime);
	}

	@Deprecated
	@Override
	public FileEntry moveFileEntry(
			long fileEntryId, long newFolderId, ServiceContext serviceContext)
		throws PortalException {

		checkDocumentRepository();

		return getDocumentRepository().moveFileEntry(
			fileEntryId, newFolderId, serviceContext);
	}

	@Deprecated
	@Override
	public Folder moveFolder(
			long folderId, long newParentFolderId,
			ServiceContext serviceContext)
		throws PortalException {

		checkDocumentRepository();

		return getDocumentRepository().moveFolder(
			folderId, newParentFolderId, serviceContext);
	}

	@Override
	public Lock refreshFileEntryLock(
			String lockUuid, long companyId, long expirationTime)
		throws PortalException {

		checkDocumentRepository();

		return getDocumentRepository().refreshFileEntryLock(
			lockUuid, companyId, expirationTime);
	}

	@Override
	public Lock refreshFolderLock(
			String lockUuid, long companyId, long expirationTime)
		throws PortalException {

		checkDocumentRepository();

		return getDocumentRepository().refreshFolderLock(
			lockUuid, companyId, expirationTime);
	}

	@Deprecated
	@Override
	public void revertFileEntry(
			long fileEntryId, String version, ServiceContext serviceContext)
		throws PortalException {

		checkDocumentRepository();

		getDocumentRepository().revertFileEntry(
			fileEntryId, version, serviceContext);
	}

	@Override
	public Hits search(long creatorUserId, int status, int start, int end)
		throws PortalException {

		checkDocumentRepository();

		return getDocumentRepository().search(
			creatorUserId, status, start, end);
	}

	@Override
	public Hits search(
			long creatorUserId, long folderId, String[] mimeTypes, int status,
			int start, int end)
		throws PortalException {

		checkDocumentRepository();

		return getDocumentRepository().search(
			creatorUserId, folderId, mimeTypes, status, start, end);
	}

	@Override
	public Hits search(SearchContext searchContext) throws SearchException {
		checkDocumentRepository();

		return getDocumentRepository().search(searchContext);
	}

	@Override
	public Hits search(SearchContext searchContext, Query query)
		throws SearchException {

		checkDocumentRepository();

		return getDocumentRepository().search(searchContext, query);
	}

	@Override
	public void unlockFolder(long folderId, String lockUuid)
		throws PortalException {

		checkDocumentRepository();

		getDocumentRepository().unlockFolder(folderId, lockUuid);
	}

	@Override
	public void unlockFolder(long parentFolderId, String name, String lockUuid)
		throws PortalException {

		checkDocumentRepository();

		getDocumentRepository().unlockFolder(parentFolderId, name, lockUuid);
	}

	@Deprecated
	@Override
	public FileEntry updateFileEntry(
			long fileEntryId, String sourceFileName, String mimeType,
			String title, String description, String changeLog,
			boolean majorVersion, File file, ServiceContext serviceContext)
		throws PortalException {

		checkDocumentRepository();

		return getDocumentRepository().updateFileEntry(
			fileEntryId, sourceFileName, mimeType, title, description,
			changeLog, majorVersion, file, serviceContext);
	}

	@Deprecated
	@Override
	public FileEntry updateFileEntry(
			long fileEntryId, String sourceFileName, String mimeType,
			String title, String description, String changeLog,
			boolean majorVersion, InputStream is, long size,
			ServiceContext serviceContext)
		throws PortalException {

		checkDocumentRepository();

		return getDocumentRepository().updateFileEntry(
			fileEntryId, sourceFileName, mimeType, title, description,
			changeLog, majorVersion, is, size, serviceContext);
	}

	@Override
	public Folder updateFolder(
			long folderId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException {

		checkDocumentRepository();

		return getDocumentRepository().updateFolder(
			folderId, name, description, serviceContext);
	}

	@Override
	public boolean verifyFileEntryCheckOut(long fileEntryId, String lockUuid)
		throws PortalException {

		checkDocumentRepository();

		return getDocumentRepository().verifyFileEntryCheckOut(
			fileEntryId, lockUuid);
	}

	@Override
	public boolean verifyFileEntryLock(long fileEntryId, String lockUuid)
		throws PortalException {

		checkDocumentRepository();

		return getDocumentRepository().verifyFileEntryLock(
			fileEntryId, lockUuid);
	}

	@Override
	public boolean verifyInheritableLock(long folderId, String lockUuid)
		throws PortalException {

		checkDocumentRepository();

		return getDocumentRepository().verifyInheritableLock(
			folderId, lockUuid);
	}

}
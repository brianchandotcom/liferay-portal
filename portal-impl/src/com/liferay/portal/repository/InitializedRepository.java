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
import com.liferay.portal.kernel.lock.Lock;
import com.liferay.portal.kernel.repository.Repository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.repository.model.RepositoryEntry;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.File;
import java.io.InputStream;

import java.util.List;

/**
 * @author Iván Zaera
 */
public class InitializedRepository
	extends InitializedDocumentRepository<Repository> implements Repository {

	/**
	 * @deprecated As of 7.0.0
	 */
	@Deprecated
	@Override
	public FileEntry addFileEntry(
			long folderId, String sourceFileName, String mimeType, String title,
			String description, String changeLog, File file,
			ServiceContext serviceContext)
		throws PortalException {

		checkDocumentRepository();

		return documentRepository.addFileEntry(
			folderId, sourceFileName, mimeType, title, description, changeLog,
			file, serviceContext);
	}

	/**
	 * @deprecated As of 7.0.0
	 */
	@Deprecated
	@Override
	public FileEntry addFileEntry(
			long folderId, String sourceFileName, String mimeType, String title,
			String description, String changeLog, InputStream is, long size,
			ServiceContext serviceContext)
		throws PortalException {

		checkDocumentRepository();

		return documentRepository.addFileEntry(
			folderId, sourceFileName, mimeType, title, description, changeLog,
			is, size, serviceContext);
	}

	/**
	 * @deprecated As of 7.0.0
	 */
	@Deprecated
	@Override
	public Folder addFolder(
			long parentFolderId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException {

		checkDocumentRepository();

		return documentRepository.addFolder(
			parentFolderId, name, description, serviceContext);
	}

	@Override
	public FileVersion cancelCheckOut(long fileEntryId) throws PortalException {
		checkDocumentRepository();

		return documentRepository.cancelCheckOut(fileEntryId);
	}

	/**
	 * @deprecated As of 7.0.0
	 */
	@Deprecated
	@Override
	public void checkInFileEntry(
			long fileEntryId, boolean major, String changeLog,
			ServiceContext serviceContext)
		throws PortalException {

		checkDocumentRepository();

		documentRepository.checkInFileEntry(
			fileEntryId, major, changeLog, serviceContext);
	}

	/**
	 * @deprecated As of 7.0.0
	 */
	@Deprecated
	@Override
	public void checkInFileEntry(
			long fileEntryId, String lockUuid, ServiceContext serviceContext)
		throws PortalException {

		checkDocumentRepository();

		documentRepository.checkInFileEntry(
			fileEntryId, lockUuid, serviceContext);
	}

	@Override
	public FileEntry checkOutFileEntry(
			long fileEntryId, ServiceContext serviceContext)
		throws PortalException {

		checkDocumentRepository();

		return documentRepository.checkOutFileEntry(
			fileEntryId, serviceContext);
	}

	@Override
	public FileEntry checkOutFileEntry(
			long fileEntryId, String owner, long expirationTime,
			ServiceContext serviceContext)
		throws PortalException {

		checkDocumentRepository();

		return documentRepository.checkOutFileEntry(
			fileEntryId, owner, expirationTime, serviceContext);
	}

	/**
	 * @deprecated As of 7.0.0
	 */
	@Deprecated
	@Override
	public FileEntry copyFileEntry(
			long groupId, long fileEntryId, long destFolderId,
			ServiceContext serviceContext)
		throws PortalException {

		checkDocumentRepository();

		return documentRepository.copyFileEntry(
			groupId, fileEntryId, destFolderId, serviceContext);
	}

	@Override
	public void deleteFileEntry(long folderId, String title)
		throws PortalException {

		checkDocumentRepository();

		documentRepository.deleteFileEntry(folderId, title);
	}

	@Override
	public void deleteFileVersion(long fileEntryId, String version)
		throws PortalException {

		checkDocumentRepository();

		documentRepository.deleteFileVersion(fileEntryId, version);
	}

	@Override
	public void deleteFolder(long parentFolderId, String name)
		throws PortalException {

		checkDocumentRepository();

		documentRepository.deleteFolder(parentFolderId, name);
	}

	@Override
	public List<FileEntry> getFileEntries(
			long folderId, long fileEntryTypeId, int start, int end,
			OrderByComparator<FileEntry> obc)
		throws PortalException {

		checkDocumentRepository();

		return documentRepository.getFileEntries(
			folderId, fileEntryTypeId, start, end, obc);
	}

	@Override
	public List<FileEntry> getFileEntries(
			long folderId, String[] mimeTypes, int status, int start, int end,
			OrderByComparator<FileEntry> obc)
		throws PortalException {

		return documentRepository.getFileEntries(
			folderId, mimeTypes, status, start, end, obc);
	}

	@Override
	public List<FileEntry> getFileEntries(
			long folderId, String[] mimeTypes, int start, int end,
			OrderByComparator<FileEntry> obc)
		throws PortalException {

		checkDocumentRepository();

		return documentRepository.getFileEntries(
			folderId, mimeTypes, start, end, obc);
	}

	@Override
	public List<RepositoryEntry> getFileEntriesAndFileShortcuts(
			long folderId, int status, int start, int end)
		throws PortalException {

		checkDocumentRepository();

		return documentRepository.getFileEntriesAndFileShortcuts(
			folderId, status, start, end);
	}

	@Override
	public int getFileEntriesAndFileShortcutsCount(long folderId, int status)
		throws PortalException {

		checkDocumentRepository();

		return documentRepository.getFileEntriesAndFileShortcutsCount(
			folderId, status);
	}

	@Override
	public int getFileEntriesAndFileShortcutsCount(
			long folderId, int status, String[] mimeTypes)
		throws PortalException {

		checkDocumentRepository();

		return documentRepository.getFileEntriesAndFileShortcutsCount(
			folderId, status, mimeTypes);
	}

	@Override
	public int getFileEntriesCount(long folderId, long fileEntryTypeId)
		throws PortalException {

		checkDocumentRepository();

		return documentRepository.getFileEntriesCount(
			folderId, fileEntryTypeId);
	}

	@Override
	public int getFileEntriesCount(long folderId, String[] mimeTypes)
		throws PortalException {

		checkDocumentRepository();

		return documentRepository.getFileEntriesCount(folderId, mimeTypes);
	}

	@Override
	public int getFileEntriesCount(
			long folderId, String[] mimeTypes, int status)
		throws PortalException {

		return documentRepository.getFileEntriesCount(
			folderId, mimeTypes, status);
	}

	@Override
	public List<Folder> getFolders(
			long parentFolderId, boolean includeMountFolders, int start,
			int end, OrderByComparator<Folder> obc)
		throws PortalException {

		checkDocumentRepository();

		return documentRepository.getFolders(
			parentFolderId, includeMountFolders, start, end, obc);
	}

	@Override
	public List<Folder> getFolders(
			long parentFolderId, int status, boolean includeMountFolders,
			int start, int end, OrderByComparator<Folder> obc)
		throws PortalException {

		checkDocumentRepository();

		return documentRepository.getFolders(
			parentFolderId, status, includeMountFolders, start, end, obc);
	}

	@Override
	public List<RepositoryEntry> getFoldersAndFileEntriesAndFileShortcuts(
			long folderId, int status, boolean includeMountFolders, int start,
			int end, OrderByComparator<?> obc)
		throws PortalException {

		checkDocumentRepository();

		return documentRepository.getFoldersAndFileEntriesAndFileShortcuts(
			folderId, status, includeMountFolders, start, end, obc);
	}

	@Override
	public List<RepositoryEntry> getFoldersAndFileEntriesAndFileShortcuts(
			long folderId, int status, String[] mimetypes,
			boolean includeMountFolders, int start, int end,
			OrderByComparator<?> obc)
		throws PortalException {

		checkDocumentRepository();

		return documentRepository.getFoldersAndFileEntriesAndFileShortcuts(
			folderId, status, mimetypes, includeMountFolders, start, end, obc);
	}

	@Override
	public int getFoldersAndFileEntriesAndFileShortcutsCount(
			long folderId, int status, boolean includeMountFolders)
		throws PortalException {

		checkDocumentRepository();

		return documentRepository.getFoldersAndFileEntriesAndFileShortcutsCount(
			folderId, status, includeMountFolders);
	}

	@Override
	public int getFoldersAndFileEntriesAndFileShortcutsCount(
			long folderId, int status, String[] mimetypes,
			boolean includeMountFolders)
		throws PortalException {

		checkDocumentRepository();

		return documentRepository.getFoldersAndFileEntriesAndFileShortcutsCount(
			folderId, status, mimetypes, includeMountFolders);
	}

	@Override
	public int getFoldersCount(long parentFolderId, boolean includeMountfolders)
		throws PortalException {

		checkDocumentRepository();

		return documentRepository.getFoldersCount(
			parentFolderId, includeMountfolders);
	}

	@Override
	public int getFoldersCount(
			long parentFolderId, int status, boolean includeMountfolders)
		throws PortalException {

		checkDocumentRepository();

		return documentRepository.getFoldersCount(
			parentFolderId, status, includeMountfolders);
	}

	@Override
	public int getFoldersFileEntriesCount(List<Long> folderIds, int status)
		throws PortalException {

		checkDocumentRepository();

		return documentRepository.getFoldersFileEntriesCount(folderIds, status);
	}

	@Override
	public List<Folder> getMountFolders(
			long parentFolderId, int start, int end,
			OrderByComparator<Folder> obc)
		throws PortalException {

		checkDocumentRepository();

		return documentRepository.getMountFolders(
			parentFolderId, start, end, obc);
	}

	@Override
	public int getMountFoldersCount(long parentFolderId)
		throws PortalException {

		checkDocumentRepository();

		return documentRepository.getMountFoldersCount(parentFolderId);
	}

	@Override
	public List<FileEntry> getRepositoryFileEntries(
			long userId, long rootFolderId, String[] mimeTypes, int status,
			int start, int end, OrderByComparator<FileEntry> obc)
		throws PortalException {

		checkDocumentRepository();

		return documentRepository.getRepositoryFileEntries(
			userId, rootFolderId, mimeTypes, status, start, end, obc);
	}

	@Override
	public int getRepositoryFileEntriesCount(long userId, long rootFolderId)
		throws PortalException {

		checkDocumentRepository();

		return documentRepository.getRepositoryFileEntriesCount(
			userId, rootFolderId);
	}

	@Override
	public int getRepositoryFileEntriesCount(
			long userId, long rootFolderId, String[] mimeTypes, int status)
		throws PortalException {

		checkDocumentRepository();

		return documentRepository.getRepositoryFileEntriesCount(
			userId, rootFolderId, mimeTypes, status);
	}

	@Override
	public void getSubfolderIds(List<Long> folderIds, long folderId)
		throws PortalException {

		checkDocumentRepository();

		documentRepository.getSubfolderIds(folderIds, folderId);
	}

	@Override
	public List<Long> getSubfolderIds(long folderId, boolean recurse)
		throws PortalException {

		checkDocumentRepository();

		return documentRepository.getSubfolderIds(folderId, recurse);
	}

	@Override
	public Lock lockFolder(long folderId) throws PortalException {
		checkDocumentRepository();

		return documentRepository.lockFolder(folderId);
	}

	@Override
	public Lock lockFolder(
			long folderId, String owner, boolean inheritable,
			long expirationTime)
		throws PortalException {

		checkDocumentRepository();

		return documentRepository.lockFolder(
			folderId, owner, inheritable, expirationTime);
	}

	/**
	 * @deprecated As of 7.0.0
	 */
	@Deprecated
	@Override
	public FileEntry moveFileEntry(
			long fileEntryId, long newFolderId, ServiceContext serviceContext)
		throws PortalException {

		checkDocumentRepository();

		return documentRepository.moveFileEntry(
			fileEntryId, newFolderId, serviceContext);
	}

	/**
	 * @deprecated As of 7.0.0
	 */
	@Deprecated
	@Override
	public Folder moveFolder(
			long folderId, long newParentFolderId,
			ServiceContext serviceContext)
		throws PortalException {

		checkDocumentRepository();

		return documentRepository.moveFolder(
			folderId, newParentFolderId, serviceContext);
	}

	@Override
	public Lock refreshFileEntryLock(
			String lockUuid, long companyId, long expirationTime)
		throws PortalException {

		checkDocumentRepository();

		return documentRepository.refreshFileEntryLock(
			lockUuid, companyId, expirationTime);
	}

	@Override
	public Lock refreshFolderLock(
			String lockUuid, long companyId, long expirationTime)
		throws PortalException {

		checkDocumentRepository();

		return documentRepository.refreshFolderLock(
			lockUuid, companyId, expirationTime);
	}

	/**
	 * @deprecated As of 7.0.0
	 */
	@Deprecated
	@Override
	public void revertFileEntry(
			long fileEntryId, String version, ServiceContext serviceContext)
		throws PortalException {

		checkDocumentRepository();

		documentRepository.revertFileEntry(
			fileEntryId, version, serviceContext);
	}

	@Override
	public Hits search(long creatorUserId, int status, int start, int end)
		throws PortalException {

		checkDocumentRepository();

		return documentRepository.search(creatorUserId, status, start, end);
	}

	@Override
	public Hits search(
			long creatorUserId, long folderId, String[] mimeTypes, int status,
			int start, int end)
		throws PortalException {

		checkDocumentRepository();

		return documentRepository.search(
			creatorUserId, folderId, mimeTypes, status, start, end);
	}

	@Override
	public Hits search(SearchContext searchContext) throws SearchException {
		checkDocumentRepository();

		return documentRepository.search(searchContext);
	}

	@Override
	public Hits search(SearchContext searchContext, Query query)
		throws SearchException {

		checkDocumentRepository();

		return documentRepository.search(searchContext, query);
	}

	@Override
	public void unlockFolder(long folderId, String lockUuid)
		throws PortalException {

		checkDocumentRepository();

		documentRepository.unlockFolder(folderId, lockUuid);
	}

	@Override
	public void unlockFolder(long parentFolderId, String name, String lockUuid)
		throws PortalException {

		checkDocumentRepository();

		documentRepository.unlockFolder(parentFolderId, name, lockUuid);
	}

	/**
	 * @deprecated As of 7.0.0
	 */
	@Deprecated
	@Override
	public FileEntry updateFileEntry(
			long fileEntryId, String sourceFileName, String mimeType,
			String title, String description, String changeLog,
			boolean majorVersion, File file, ServiceContext serviceContext)
		throws PortalException {

		checkDocumentRepository();

		return documentRepository.updateFileEntry(
			fileEntryId, sourceFileName, mimeType, title, description,
			changeLog, majorVersion, file, serviceContext);
	}

	/**
	 * @deprecated As of 7.0.0
	 */
	@Deprecated
	@Override
	public FileEntry updateFileEntry(
			long fileEntryId, String sourceFileName, String mimeType,
			String title, String description, String changeLog,
			boolean majorVersion, InputStream is, long size,
			ServiceContext serviceContext)
		throws PortalException {

		checkDocumentRepository();

		return documentRepository.updateFileEntry(
			fileEntryId, sourceFileName, mimeType, title, description,
			changeLog, majorVersion, is, size, serviceContext);
	}

	@Override
	public Folder updateFolder(
			long folderId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException {

		checkDocumentRepository();

		return documentRepository.updateFolder(
			folderId, name, description, serviceContext);
	}

	@Override
	public boolean verifyFileEntryCheckOut(long fileEntryId, String lockUuid)
		throws PortalException {

		checkDocumentRepository();

		return documentRepository.verifyFileEntryCheckOut(
			fileEntryId, lockUuid);
	}

	@Override
	public boolean verifyFileEntryLock(long fileEntryId, String lockUuid)
		throws PortalException {

		checkDocumentRepository();

		return documentRepository.verifyFileEntryLock(fileEntryId, lockUuid);
	}

	@Override
	public boolean verifyInheritableLock(long folderId, String lockUuid)
		throws PortalException {

		checkDocumentRepository();

		return documentRepository.verifyInheritableLock(folderId, lockUuid);
	}

}
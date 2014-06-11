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

package com.liferay.portal.kernel.repository;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.capabilities.CapabilityProvider;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
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
 * @author Alexander Chow
 */
public interface Repository extends CapabilityProvider {

	public FileEntry addFileEntry(
			long folderId, String sourceFileName, String mimeType, String title,
			String description, String changeLog, File file,
			ServiceContext serviceContext)
		throws PortalException, SystemException;

	public FileEntry addFileEntry(
			long folderId, String sourceFileName, String mimeType, String title,
			String description, String changeLog, InputStream is, long size,
			ServiceContext serviceContext)
		throws PortalException, SystemException;

	public Folder addFolder(
			long parentFolderId, String title, String description,
			ServiceContext serviceContext)
		throws PortalException, SystemException;

	public FileVersion cancelCheckOut(long fileEntryId)
		throws PortalException, SystemException;

	public void checkInFileEntry(
			long fileEntryId, boolean major, String changeLog,
			ServiceContext serviceContext)
		throws PortalException, SystemException;

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #checkInFileEntry(long,
	 *             String, ServiceContext)}
	 */
	@Deprecated
	public void checkInFileEntry(long fileEntryId, String lockUuid)
		throws PortalException, SystemException;

	public void checkInFileEntry(
			long fileEntryId, String lockUuid, ServiceContext serviceContext)
		throws PortalException, SystemException;

	public FileEntry checkOutFileEntry(
			long fileEntryId, ServiceContext serviceContext)
		throws PortalException, SystemException;

	public FileEntry checkOutFileEntry(
			long fileEntryId, String owner, long expirationTime,
			ServiceContext serviceContext)
		throws PortalException, SystemException;

	public FileEntry copyFileEntry(
			long groupId, long fileEntryId, long destFolderId,
			ServiceContext serviceContext)
		throws PortalException, SystemException;

	public void deleteFileEntry(long fileEntryId)
		throws PortalException, SystemException;

	public void deleteFileEntry(long folderId, String title)
		throws PortalException, SystemException;

	public void deleteFileVersion(long fileEntryId, String version)
		throws PortalException, SystemException;

	public void deleteFolder(long folderId)
		throws PortalException, SystemException;

	public void deleteFolder(long parentFolderId, String title)
		throws PortalException, SystemException;

	public List<FileEntry> getFileEntries(
			long folderId, int start, int end, OrderByComparator obc)
		throws PortalException, SystemException;

	public List<FileEntry> getFileEntries(
			long folderId, long fileEntryTypeId, int start, int end,
			OrderByComparator obc)
		throws PortalException, SystemException;

	public List<FileEntry> getFileEntries(
			long folderId, String[] mimeTypes, int start, int end,
			OrderByComparator obc)
		throws PortalException, SystemException;

	public List<Object> getFileEntriesAndFileShortcuts(
			long folderId, int status, int start, int end)
		throws PortalException, SystemException;

	public int getFileEntriesAndFileShortcutsCount(long folderId, int status)
		throws PortalException, SystemException;

	public int getFileEntriesAndFileShortcutsCount(
			long folderId, int status, String[] mimeTypes)
		throws PortalException, SystemException;

	public int getFileEntriesCount(long folderId)
		throws PortalException, SystemException;

	public int getFileEntriesCount(long folderId, long fileEntryTypeId)
		throws PortalException, SystemException;

	public int getFileEntriesCount(long folderId, String[] mimeTypes)
		throws PortalException, SystemException;

	public FileEntry getFileEntry(long fileEntryId)
		throws PortalException, SystemException;

	public FileEntry getFileEntry(long folderId, String title)
		throws PortalException, SystemException;

	public FileEntry getFileEntryByUuid(String uuid)
		throws PortalException, SystemException;

	public FileVersion getFileVersion(long fileVersionId)
		throws PortalException, SystemException;

	public Folder getFolder(long folderId)
		throws PortalException, SystemException;

	public Folder getFolder(long parentFolderId, String title)
		throws PortalException, SystemException;

	public List<Folder> getFolders(
			long parentFolderId, boolean includeMountFolders, int start,
			int end, OrderByComparator obc)
		throws PortalException, SystemException;

	public List<Folder> getFolders(
			long parentFolderId, int status, boolean includeMountFolders,
			int start, int end, OrderByComparator obc)
		throws PortalException, SystemException;

	public List<Object> getFoldersAndFileEntriesAndFileShortcuts(
			long folderId, int status, boolean includeMountFolders, int start,
			int end, OrderByComparator obc)
		throws PortalException, SystemException;

	public List<Object> getFoldersAndFileEntriesAndFileShortcuts(
			long folderId, int status, String[] mimetypes,
			boolean includeMountFolders, int start, int end,
			OrderByComparator obc)
		throws PortalException, SystemException;

	public int getFoldersAndFileEntriesAndFileShortcutsCount(
			long folderId, int status, boolean includeMountFolders)
		throws PortalException, SystemException;

	public int getFoldersAndFileEntriesAndFileShortcutsCount(
			long folderId, int status, String[] mimetypes,
			boolean includeMountFolders)
		throws PortalException, SystemException;

	public int getFoldersCount(long parentFolderId, boolean includeMountfolders)
		throws PortalException, SystemException;

	public int getFoldersCount(
			long parentFolderId, int status, boolean includeMountfolders)
		throws PortalException, SystemException;

	public int getFoldersFileEntriesCount(List<Long> folderIds, int status)
		throws PortalException, SystemException;

	public List<Folder> getMountFolders(
			long parentFolderId, int start, int end, OrderByComparator obc)
		throws PortalException, SystemException;

	public int getMountFoldersCount(long parentFolderId)
		throws PortalException, SystemException;

	public List<FileEntry> getRepositoryFileEntries(
			long userId, long rootFolderId, int start, int end,
			OrderByComparator obc)
		throws PortalException, SystemException;

	public List<FileEntry> getRepositoryFileEntries(
			long userId, long rootFolderId, String[] mimeTypes, int status,
			int start, int end, OrderByComparator obc)
		throws PortalException, SystemException;

	public int getRepositoryFileEntriesCount(long userId, long rootFolderId)
		throws PortalException, SystemException;

	public int getRepositoryFileEntriesCount(
			long userId, long rootFolderId, String[] mimeTypes, int status)
		throws PortalException, SystemException;

	public long getRepositoryId();

	public void getSubfolderIds(List<Long> folderIds, long folderId)
		throws PortalException, SystemException;

	public List<Long> getSubfolderIds(long folderId, boolean recurse)
		throws PortalException, SystemException;

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #checkOutFileEntry(long,
	 *             ServiceContext)}
	 */
	@Deprecated
	public Lock lockFileEntry(long fileEntryId)
		throws PortalException, SystemException;

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #checkOutFileEntry(long,
	 *             String, long, ServiceContext)}
	 */
	@Deprecated
	public Lock lockFileEntry(
			long fileEntryId, String owner, long expirationTime)
		throws PortalException, SystemException;

	public Lock lockFolder(long folderId)
		throws PortalException, SystemException;

	public Lock lockFolder(
			long folderId, String owner, boolean inheritable,
			long expirationTime)
		throws PortalException, SystemException;

	public FileEntry moveFileEntry(
			long fileEntryId, long newFolderId, ServiceContext serviceContext)
		throws PortalException, SystemException;

	public Folder moveFolder(
			long folderId, long newParentFolderId,
			ServiceContext serviceContext)
		throws PortalException, SystemException;

	public Lock refreshFileEntryLock(
			String lockUuid, long companyId, long expirationTime)
		throws PortalException, SystemException;

	public Lock refreshFolderLock(
			String lockUuid, long companyId, long expirationTime)
		throws PortalException, SystemException;

	public void revertFileEntry(
			long fileEntryId, String version, ServiceContext serviceContext)
		throws PortalException, SystemException;

	public Hits search(long creatorUserId, int status, int start, int end)
		throws PortalException, SystemException;

	public Hits search(
			long creatorUserId, long folderId, String[] mimeTypes, int status,
			int start, int end)
		throws PortalException, SystemException;

	public Hits search(SearchContext searchContext) throws SearchException;

	public Hits search(SearchContext searchContext, Query query)
		throws SearchException;

	public void unlockFolder(long folderId, String lockUuid)
		throws PortalException, SystemException;

	public void unlockFolder(long parentFolderId, String title, String lockUuid)
		throws PortalException, SystemException;

	public FileEntry updateFileEntry(
			long fileEntryId, String sourceFileName, String mimeType,
			String title, String description, String changeLog,
			boolean majorVersion, File file, ServiceContext serviceContext)
		throws PortalException, SystemException;

	public FileEntry updateFileEntry(
			long fileEntryId, String sourceFileName, String mimeType,
			String title, String description, String changeLog,
			boolean majorVersion, InputStream is, long size,
			ServiceContext serviceContext)
		throws PortalException, SystemException;

	public Folder updateFolder(
			long folderId, String title, String description,
			ServiceContext serviceContext)
		throws PortalException, SystemException;

	public boolean verifyFileEntryCheckOut(long fileEntryId, String lockUuid)
		throws PortalException, SystemException;

	public boolean verifyFileEntryLock(long fileEntryId, String lockUuid)
		throws PortalException, SystemException;

	public boolean verifyInheritableLock(long folderId, String lockUuid)
		throws PortalException, SystemException;

}
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
import com.liferay.portal.kernel.repository.capabilities.CapabilityProvider;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.repository.model.RepositoryEntry;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.service.ServiceContext;

import java.io.File;
import java.io.InputStream;

import java.util.List;

/**
 * @author Iván Zaera
 */
public interface DocumentRepository extends CapabilityProvider {

	public FileEntry addFileEntry(
			long userId, long folderId, String sourceFileName, String mimeType,
			String title, String description, String changeLog, File file,
			ServiceContext serviceContext)
		throws PortalException;

	public FileEntry addFileEntry(
			long userId, long folderId, String sourceFileName, String mimeType,
			String title, String description, String changeLog, InputStream is,
			long size, ServiceContext serviceContext)
		throws PortalException;

	public FileShortcut addFileShortcut(
			long userId, long folderId, long toFileEntryId,
			ServiceContext serviceContext)
		throws PortalException;

	public Folder addFolder(
			long userId, long parentFolderId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException;

	public void checkInFileEntry(
			long userId, long fileEntryId, boolean major, String changeLog,
			ServiceContext serviceContext)
		throws PortalException;

	public void checkInFileEntry(
			long userId, long fileEntryId, String lockUuid,
			ServiceContext serviceContext)
		throws PortalException;

	public FileEntry copyFileEntry(
			long userId, long groupId, long fileEntryId, long destFolderId,
			ServiceContext serviceContext)
		throws PortalException;

	public void deleteAll() throws PortalException;

	public void deleteFileEntry(long fileEntryId) throws PortalException;

	public void deleteFileShortcut(long fileShortcutId) throws PortalException;

	public void deleteFileShortcuts(long toFileEntryId) throws PortalException;

	public void deleteFolder(long folderId) throws PortalException;

	public List<FileEntry> getFileEntries(
			long folderId, int status, int start, int end,
			OrderByComparator<FileEntry> obc)
		throws PortalException;

	public List<FileEntry> getFileEntries(
			long folderId, int start, int end, OrderByComparator<FileEntry> obc)
		throws PortalException;

	public List<RepositoryEntry> getFileEntriesAndFileShortcuts(
			long folderId, int status, int start, int end)
		throws PortalException;

	public int getFileEntriesAndFileShortcutsCount(long folderId, int status)
		throws PortalException;

	public int getFileEntriesCount(long folderId) throws PortalException;

	public int getFileEntriesCount(long folderId, int status)
		throws PortalException;

	public FileEntry getFileEntry(long fileEntryId) throws PortalException;

	public FileEntry getFileEntry(long folderId, String title)
		throws PortalException;

	public FileEntry getFileEntryByUuid(String uuid) throws PortalException;

	public FileShortcut getFileShortcut(long fileShortcutId)
		throws PortalException;

	public FileVersion getFileVersion(long fileVersionId)
		throws PortalException;

	public Folder getFolder(long folderId) throws PortalException;

	public Folder getFolder(long parentFolderId, String name)
		throws PortalException;

	public List<Folder> getFolders(
			long parentFolderId, boolean includeMountFolders, int start,
			int end, OrderByComparator<Folder> obc)
		throws PortalException;

	public List<Folder> getFolders(
			long parentFolderId, int status, boolean includeMountFolders,
			int start, int end, OrderByComparator<Folder> obc)
		throws PortalException;

	public List<RepositoryEntry> getFoldersAndFileEntriesAndFileShortcuts(
			long folderId, int status, boolean includeMountFolders, int start,
			int end, OrderByComparator<?> obc)
		throws PortalException;

	public int getFoldersCount(long parentFolderId, boolean includeMountfolders)
		throws PortalException;

	public int getFoldersCount(
			long parentFolderId, int status, boolean includeMountfolders)
		throws PortalException;

	public List<FileEntry> getRepositoryFileEntries(
			long userId, long rootFolderId, int start, int end,
			OrderByComparator<FileEntry> obc)
		throws PortalException;

	public long getRepositoryId();

	public FileEntry moveFileEntry(
			long userId, long fileEntryId, long newFolderId,
			ServiceContext serviceContext)
		throws PortalException;

	public Folder moveFolder(
			long userId, long folderId, long parentFolderId,
			ServiceContext serviceContext)
		throws PortalException;

	public void revertFileEntry(
			long userId, long fileEntryId, String version,
			ServiceContext serviceContext)
		throws PortalException;

	public FileEntry updateFileEntry(
			long userId, long fileEntryId, String sourceFileName,
			String mimeType, String title, String description, String changeLog,
			boolean majorVersion, File file, ServiceContext serviceContext)
		throws PortalException;

	public FileEntry updateFileEntry(
			long userId, long fileEntryId, String sourceFileName,
			String mimeType, String title, String description, String changeLog,
			boolean majorVersion, InputStream is, long size,
			ServiceContext serviceContext)
		throws PortalException;

	public FileShortcut updateFileShortcut(
			long userId, long fileShortcutId, long folderId, long toFileEntryId,
			ServiceContext serviceContext)
		throws PortalException;

	public void updateFileShortcuts(
			long oldToFileEntryId, long newToFileEntryId)
		throws PortalException;

	public Folder updateFolder(
			long folderId, long parentFolderId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException;

}
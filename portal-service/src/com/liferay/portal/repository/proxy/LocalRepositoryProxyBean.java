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

package com.liferay.portal.repository.proxy;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.service.ServiceContext;

import java.io.File;
import java.io.InputStream;

import java.util.List;

/**
 * @author Mika Koivisto
 */
public class LocalRepositoryProxyBean
	extends RepositoryModelProxyBean implements LocalRepository {

	public LocalRepositoryProxyBean(
		LocalRepository localRepository, ClassLoader classLoader) {

		super(classLoader);

		_localRepository = localRepository;
	}

	public FileEntry addFileEntry(
			long userId, long folderId, String sourceFileName, String mimeType,
			String title, String description, String changeLog, File file,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		FileEntry fileEntry = _localRepository.addFileEntry(
			userId, folderId, sourceFileName, mimeType, title, description,
			changeLog, file, serviceContext);

		return newFileEntryProxyBean(fileEntry);
	}

	public FileEntry addFileEntry(
			long userId, long folderId, String sourceFileName, String mimeType,
			String title, String description, String changeLog, InputStream is,
			long size, ServiceContext serviceContext)
		throws PortalException, SystemException {

		FileEntry fileEntry = _localRepository.addFileEntry(
			userId, folderId, sourceFileName, mimeType, title, description,
			changeLog, is, size, serviceContext);

		return newFileEntryProxyBean(fileEntry);
	}

	public Folder addFolder(
			long userId, long parentFolderId, String title, String description,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		Folder folder = _localRepository.addFolder(
			userId, parentFolderId, title, description, serviceContext);

		return newFolderProxyBean(folder);
	}

	public void deleteAll() throws PortalException, SystemException {
		_localRepository.deleteAll();
	}

	public void deleteFileEntry(long fileEntryId)
		throws PortalException, SystemException {

		_localRepository.deleteFileEntry(fileEntryId);
	}

	public void deleteFolder(long folderId)
		throws PortalException, SystemException {

		_localRepository.deleteFolder(folderId);
	}

	public List<FileEntry> getFileEntries(
			long folderId, int start, int end, OrderByComparator obc)
		throws SystemException {

		List<FileEntry> fileEntries = _localRepository.getFileEntries(
			folderId, start, end, obc);

		return toFileEntryProxyBeans(fileEntries);
	}

	public List<Object> getFileEntriesAndFileShortcuts(
			long folderId, int status, int start, int end)
		throws SystemException {

		List<Object> objects = _localRepository.getFileEntriesAndFileShortcuts(
			folderId, status, start, end);

		return toObjectProxyBeans(objects);
	}

	public int getFileEntriesAndFileShortcutsCount(long folderId, int status)
		throws SystemException {

		return _localRepository.getFileEntriesAndFileShortcutsCount(
			folderId, status);
	}

	public int getFileEntriesCount(long folderId) throws SystemException {
		return _localRepository.getFileEntriesCount(folderId);
	}

	public FileEntry getFileEntry(long fileEntryId)
		throws PortalException, SystemException {

		FileEntry fileEntry = _localRepository.getFileEntry(fileEntryId);

		return newFileEntryProxyBean(fileEntry);
	}

	public FileEntry getFileEntry(long folderId, String title)
		throws PortalException, SystemException {

		FileEntry fileEntry = _localRepository.getFileEntry(folderId, title);

		return newFileEntryProxyBean(fileEntry);
	}

	public FileEntry getFileEntryByUuid(String uuid)
		throws PortalException, SystemException {

		FileEntry fileEntry = _localRepository.getFileEntryByUuid(uuid);

		return newFileEntryProxyBean(fileEntry);
	}

	public FileVersion getFileVersion(long fileVersionId)
		throws PortalException, SystemException {

		FileVersion fileVersion = _localRepository.getFileVersion(
			fileVersionId);

		return newFileVersionProxyBean(fileVersion);
	}

	public Folder getFolder(long folderId)
		throws PortalException, SystemException {

		Folder folder = _localRepository.getFolder(folderId);

		return newFolderProxyBean(folder);
	}

	public Folder getFolder(long parentFolderId, String title)
		throws PortalException, SystemException {

		return _localRepository.getFolder(parentFolderId, title);
	}

	public List<Folder> getFolders(
			long parentFolderId, boolean includeMountfolders, int start,
			int end, OrderByComparator obc)
		throws SystemException {

		List<Folder> folderList = _localRepository.getFolders(
			parentFolderId, includeMountfolders, start, end, obc);

		return toFolderProxyBeans(folderList);
	}

	public List<Object> getFoldersAndFileEntriesAndFileShortcuts(
			long folderId, int status, boolean includeMountFolders, int start,
			int end, OrderByComparator obc)
		throws SystemException {

		List<Object> objects =
			_localRepository.getFoldersAndFileEntriesAndFileShortcuts(
				folderId, status, includeMountFolders, start, end, obc);

		return toObjectProxyBeans(objects);
	}

	public List<Object> getFoldersAndFileEntriesAndFileShortcuts(
			long folderId, int status, String[] mimeTypes,
			boolean includeMountFolders, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		List<Object> objects =
			_localRepository.getFoldersAndFileEntriesAndFileShortcuts(
				folderId, status, mimeTypes, includeMountFolders, start, end,
				obc);

		return toObjectProxyBeans(objects);
	}

	public int getFoldersAndFileEntriesAndFileShortcutsCount(
			long folderId, int status, boolean includeMountFolders)
		throws SystemException {

		return _localRepository.getFoldersAndFileEntriesAndFileShortcutsCount(
			folderId, status, includeMountFolders);
	}

	public int getFoldersAndFileEntriesAndFileShortcutsCount(
			long folderId, int status, String[] mimeTypes,
			boolean includeMountFolders)
		throws SystemException {

		return _localRepository.getFoldersAndFileEntriesAndFileShortcutsCount(
			folderId, status, mimeTypes, includeMountFolders);
	}

	public int getFoldersCount(long parentFolderId, boolean includeMountFolders)
		throws SystemException {

		return _localRepository.getFoldersCount(
			parentFolderId, includeMountFolders);
	}

	public int getFoldersFileEntriesCount(List<Long> folderIds, int status)
		throws SystemException {

		return _localRepository.getFoldersFileEntriesCount(folderIds, status);
	}

	public List<Folder> getMountFolders(
			long parentFolderId, int start, int end, OrderByComparator obc)
		throws SystemException {

		List<Folder> folderList = _localRepository.getMountFolders(
			parentFolderId, start, end, obc);

		return toFolderProxyBeans(folderList);
	}

	public int getMountFoldersCount(long parentFolderId)
		throws SystemException {

		return _localRepository.getMountFoldersCount(parentFolderId);
	}

	public long getRepositoryId() {
		return _localRepository.getRepositoryId();
	}

	public FileEntry moveFileEntry(
			long userId, long fileEntryId, long newFolderId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		FileEntry fileEntry = _localRepository.moveFileEntry(
			userId, fileEntryId, newFolderId, serviceContext);

		return newFileEntryProxyBean(fileEntry);
	}

	public void updateAsset(
			long userId, FileEntry fileEntry, FileVersion fileVersion,
			long[] assetCategoryIds, String[] assetTagNames,
			long[] assetLinkEntryIds)
		throws PortalException, SystemException {

		_localRepository.updateAsset(
			userId, fileEntry, fileVersion, assetCategoryIds, assetTagNames,
			assetLinkEntryIds);
	}

	public FileEntry updateFileEntry(
			long userId, long fileEntryId, String sourceFileName,
			String mimeType, String title, String description, String changeLog,
			boolean majorVersion, File file, ServiceContext serviceContext)
		throws PortalException, SystemException {

		FileEntry fileEntry = _localRepository.updateFileEntry(
			userId, fileEntryId, sourceFileName, mimeType, title, description,
			changeLog, majorVersion, file, serviceContext);

		return newFileEntryProxyBean(fileEntry);
	}

	public FileEntry updateFileEntry(
			long userId, long fileEntryId, String sourceFileName,
			String mimeType, String title, String description, String changeLog,
			boolean majorVersion, InputStream is, long size,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		FileEntry fileEntry = _localRepository.updateFileEntry(
			userId, fileEntryId, sourceFileName, mimeType, title, description,
			changeLog, majorVersion, is, size, serviceContext);

		return newFileEntryProxyBean(fileEntry);
	}

	public Folder updateFolder(
			long folderId, long parentFolderId, String title,
			String description, ServiceContext serviceContext)
		throws PortalException, SystemException {

		return _localRepository.updateFolder(
			folderId, parentFolderId, title, description, serviceContext);
	}

	private LocalRepository _localRepository;

}
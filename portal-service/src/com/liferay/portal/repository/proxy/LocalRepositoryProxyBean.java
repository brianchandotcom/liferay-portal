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

package com.liferay.portal.repository.proxy;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.capabilities.Capability;
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
 * @author Mika Koivisto
 */
public class LocalRepositoryProxyBean
	extends RepositoryModelProxyBean implements LocalRepository {

	public LocalRepositoryProxyBean(
		LocalRepository localRepository, ClassLoader classLoader) {

		super(classLoader);

		_localRepository = localRepository;
	}

	@Override
	public FileEntry addFileEntry(
			long userId, long folderId, String sourceFileName, String mimeType,
			String title, String description, String changeLog, File file,
			ServiceContext serviceContext)
		throws PortalException {

		FileEntry fileEntry = _localRepository.addFileEntry(
			userId, folderId, sourceFileName, mimeType, title, description,
			changeLog, file, serviceContext);

		return newFileEntryProxyBean(fileEntry);
	}

	@Override
	public FileEntry addFileEntry(
			long userId, long folderId, String sourceFileName, String mimeType,
			String title, String description, String changeLog, InputStream is,
			long size, ServiceContext serviceContext)
		throws PortalException {

		FileEntry fileEntry = _localRepository.addFileEntry(
			userId, folderId, sourceFileName, mimeType, title, description,
			changeLog, is, size, serviceContext);

		return newFileEntryProxyBean(fileEntry);
	}

	@Override
	public FileShortcut addFileShortcut(
			long userId, long folderId, long toFileEntryId,
			ServiceContext serviceContext)
		throws PortalException {

		FileShortcut fileShortcut = _localRepository.addFileShortcut(
			userId, folderId, toFileEntryId, serviceContext);

		return newFileShortcutProxyBean(fileShortcut);
	}

	@Override
	public Folder addFolder(
			long userId, long parentFolderId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException {

		Folder folder = _localRepository.addFolder(
			userId, parentFolderId, name, description, serviceContext);

		return newFolderProxyBean(folder);
	}

	@Override
	public void checkInFileEntry(
			long userId, long fileEntryId, boolean major, String changeLog,
			ServiceContext serviceContext)
		throws PortalException {

		_localRepository.checkInFileEntry(
			userId, fileEntryId, major, changeLog, serviceContext);
	}

	@Override
	public void checkInFileEntry(
			long userId, long fileEntryId, String lockUuid,
			ServiceContext serviceContext)
		throws PortalException {

		_localRepository.checkInFileEntry(
			userId, fileEntryId, lockUuid, serviceContext);
	}

	@Override
	public FileEntry copyFileEntry(
			long userId, long groupId, long fileEntryId, long destFolderId,
			ServiceContext serviceContext)
		throws PortalException {

		FileEntry fileEntry = _localRepository.copyFileEntry(
			userId, groupId, fileEntryId, destFolderId, serviceContext);

		return newFileEntryProxyBean(fileEntry);
	}

	@Override
	public void deleteAll() throws PortalException {
		_localRepository.deleteAll();
	}

	@Override
	public void deleteFileEntry(long fileEntryId) throws PortalException {
		_localRepository.deleteFileEntry(fileEntryId);
	}

	@Override
	public void deleteFileShortcut(long fileShortcutId) throws PortalException {
		_localRepository.deleteFileShortcut(fileShortcutId);
	}

	@Override
	public void deleteFileShortcuts(long toFileEntryId) throws PortalException {
		_localRepository.deleteFileShortcuts(toFileEntryId);
	}

	@Override
	public void deleteFolder(long folderId) throws PortalException {
		_localRepository.deleteFolder(folderId);
	}

	@Override
	public <T extends Capability> T getCapability(Class<T> capabilityClass) {
		return _localRepository.getCapability(capabilityClass);
	}

	@Override
	public List<FileEntry> getFileEntries(
			long folderId, int status, int start, int end,
			OrderByComparator<FileEntry> obc)
		throws PortalException {

		return _localRepository.getFileEntries(
			folderId, status, start, end, obc);
	}

	@Override
	public List<FileEntry> getFileEntries(
			long folderId, int start, int end, OrderByComparator<FileEntry> obc)
		throws PortalException {

		return _localRepository.getFileEntries(folderId, start, end, obc);
	}

	@Override
	public List<RepositoryEntry> getFileEntriesAndFileShortcuts(
			long folderId, int status, int start, int end)
		throws PortalException {

		return _localRepository.getFileEntriesAndFileShortcuts(
			folderId, status, start, end);
	}

	@Override
	public int getFileEntriesAndFileShortcutsCount(long folderId, int status)
		throws PortalException {

		return _localRepository.getFileEntriesAndFileShortcutsCount(
			folderId, status);
	}

	@Override
	public int getFileEntriesCount(long folderId) throws PortalException {
		return _localRepository.getFileEntriesCount(folderId);
	}

	@Override
	public int getFileEntriesCount(long folderId, int status)
		throws PortalException {

		return _localRepository.getFileEntriesCount(folderId, status);
	}

	@Override
	public FileEntry getFileEntry(long fileEntryId) throws PortalException {
		FileEntry fileEntry = _localRepository.getFileEntry(fileEntryId);

		return newFileEntryProxyBean(fileEntry);
	}

	@Override
	public FileEntry getFileEntry(long folderId, String title)
		throws PortalException {

		FileEntry fileEntry = _localRepository.getFileEntry(folderId, title);

		return newFileEntryProxyBean(fileEntry);
	}

	@Override
	public FileEntry getFileEntryByUuid(String uuid) throws PortalException {
		FileEntry fileEntry = _localRepository.getFileEntryByUuid(uuid);

		return newFileEntryProxyBean(fileEntry);
	}

	@Override
	public FileShortcut getFileShortcut(long fileShortcutId)
		throws PortalException {

		FileShortcut fileShortcut = _localRepository.getFileShortcut(
			fileShortcutId);

		return newFileShortcutProxyBean(fileShortcut);
	}

	@Override
	public FileVersion getFileVersion(long fileVersionId)
		throws PortalException {

		FileVersion fileVersion = _localRepository.getFileVersion(
			fileVersionId);

		return newFileVersionProxyBean(fileVersion);
	}

	@Override
	public Folder getFolder(long folderId) throws PortalException {
		Folder folder = _localRepository.getFolder(folderId);

		return newFolderProxyBean(folder);
	}

	@Override
	public Folder getFolder(long parentFolderId, String name)
		throws PortalException {

		return _localRepository.getFolder(parentFolderId, name);
	}

	@Override
	public List<Folder> getFolders(
			long parentFolderId, boolean includeMountFolders, int start,
			int end, OrderByComparator<Folder> obc)
		throws PortalException {

		return _localRepository.getFolders(
			parentFolderId, includeMountFolders, start, end, obc);
	}

	@Override
	public List<Folder> getFolders(
			long parentFolderId, int status, boolean includeMountFolders,
			int start, int end, OrderByComparator<Folder> obc)
		throws PortalException {

		return _localRepository.getFolders(
			parentFolderId, status, includeMountFolders, start, end, obc);
	}

	@Override
	public List<RepositoryEntry> getFoldersAndFileEntriesAndFileShortcuts(
			long folderId, int status, boolean includeMountFolders, int start,
			int end, OrderByComparator<?> obc)
		throws PortalException {

		return _localRepository.getFoldersAndFileEntriesAndFileShortcuts(
			folderId, status, includeMountFolders, start, end, obc);
	}

	@Override
	public int getFoldersCount(long parentFolderId, boolean includeMountfolders)
		throws PortalException {

		return _localRepository.getFoldersCount(
			parentFolderId, includeMountfolders);
	}

	@Override
	public int getFoldersCount(
			long parentFolderId, int status, boolean includeMountfolders)
		throws PortalException {

		return _localRepository.getFoldersCount(
			parentFolderId, status, includeMountfolders);
	}

	@Override
	public List<FileEntry> getRepositoryFileEntries(
			long userId, long rootFolderId, int start, int end,
			OrderByComparator<FileEntry> obc)
		throws PortalException {

		return _localRepository.getRepositoryFileEntries(
			userId, rootFolderId, start, end, obc);
	}

	@Override
	public long getRepositoryId() {
		return _localRepository.getRepositoryId();
	}

	@Override
	public <T extends Capability> boolean isCapabilityProvided(
		Class<T> capabilityClass) {

		return _localRepository.isCapabilityProvided(capabilityClass);
	}

	@Override
	public FileEntry moveFileEntry(
			long userId, long fileEntryId, long newFolderId,
			ServiceContext serviceContext)
		throws PortalException {

		FileEntry fileEntry = _localRepository.moveFileEntry(
			userId, fileEntryId, newFolderId, serviceContext);

		return newFileEntryProxyBean(fileEntry);
	}

	@Override
	public Folder moveFolder(
			long userId, long folderId, long parentFolderId,
			ServiceContext serviceContext)
		throws PortalException {

		Folder folder = _localRepository.moveFolder(
			userId, folderId, parentFolderId, serviceContext);

		return newFolderProxyBean(folder);
	}

	@Override
	public void revertFileEntry(
			long userId, long fileEntryId, String version,
			ServiceContext serviceContext)
		throws PortalException {

		_localRepository.revertFileEntry(
			userId, fileEntryId, version, serviceContext);
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	@Override
	public void updateAsset(
			long userId, FileEntry fileEntry, FileVersion fileVersion,
			long[] assetCategoryIds, String[] assetTagNames,
			long[] assetLinkEntryIds)
		throws PortalException {

		_localRepository.updateAsset(
			userId, fileEntry, fileVersion, assetCategoryIds, assetTagNames,
			assetLinkEntryIds);
	}

	@Override
	public FileEntry updateFileEntry(
			long userId, long fileEntryId, String sourceFileName,
			String mimeType, String title, String description, String changeLog,
			boolean majorVersion, File file, ServiceContext serviceContext)
		throws PortalException {

		FileEntry fileEntry = _localRepository.updateFileEntry(
			userId, fileEntryId, sourceFileName, mimeType, title, description,
			changeLog, majorVersion, file, serviceContext);

		return newFileEntryProxyBean(fileEntry);
	}

	@Override
	public FileEntry updateFileEntry(
			long userId, long fileEntryId, String sourceFileName,
			String mimeType, String title, String description, String changeLog,
			boolean majorVersion, InputStream is, long size,
			ServiceContext serviceContext)
		throws PortalException {

		FileEntry fileEntry = _localRepository.updateFileEntry(
			userId, fileEntryId, sourceFileName, mimeType, title, description,
			changeLog, majorVersion, is, size, serviceContext);

		return newFileEntryProxyBean(fileEntry);
	}

	@Override
	public FileShortcut updateFileShortcut(
			long userId, long fileShortcutId, long folderId, long toFileEntryId,
			ServiceContext serviceContext)
		throws PortalException {

		FileShortcut fileShortcut = _localRepository.updateFileShortcut(
			userId, fileShortcutId, folderId, toFileEntryId, serviceContext);

		return newFileShortcutProxyBean(fileShortcut);
	}

	@Override
	public void updateFileShortcuts(
			long oldToFileEntryId, long newToFileEntryId)
		throws PortalException {

		_localRepository.updateFileShortcuts(
			oldToFileEntryId, newToFileEntryId);
	}

	@Override
	public Folder updateFolder(
			long folderId, long parentFolderId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException {

		return _localRepository.updateFolder(
			folderId, parentFolderId, name, description, serviceContext);
	}

	private final LocalRepository _localRepository;

}
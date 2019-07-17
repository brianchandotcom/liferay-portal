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

package com.liferay.document.library.repository.cmis.internal;

import com.liferay.document.library.kernel.model.DLVersionNumberIncrease;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.lock.Lock;
import com.liferay.portal.kernel.repository.Repository;
import com.liferay.portal.kernel.repository.capabilities.Capability;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.repository.model.RepositoryEntry;
import com.liferay.portal.kernel.repository.proxy.RepositoryModelProxyBean;
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
 * @author Adolfo Pérez
 */
public class RepositoryProxyBean
	extends RepositoryModelProxyBean implements Repository {

	public RepositoryProxyBean(Repository repository, ClassLoader classLoader) {
		super(classLoader);

		_repository = repository;
		_classLoader = classLoader;
	}

	@Override
	public FileEntry addFileEntry(
			long userId, long folderId, String sourceFileName, String mimeType,
			String title, String description, String changeLog, File file,
			ServiceContext serviceContext)
		throws PortalException {

		try (ContextClassLoaderSetter contextClassLoaderSetter =
				new ContextClassLoaderSetter(_classLoader)) {

			FileEntry fileEntry = _repository.addFileEntry(
				userId, folderId, sourceFileName, mimeType, title, description,
				changeLog, file, serviceContext);

			return newFileEntryProxyBean(fileEntry);
		}
	}

	@Override
	public FileEntry addFileEntry(
			long userId, long folderId, String sourceFileName, String mimeType,
			String title, String description, String changeLog, InputStream is,
			long size, ServiceContext serviceContext)
		throws PortalException {

		try (ContextClassLoaderSetter contextClassLoaderSetter =
				new ContextClassLoaderSetter(_classLoader)) {

			FileEntry fileEntry = _repository.addFileEntry(
				userId, folderId, sourceFileName, mimeType, title, description,
				changeLog, is, size, serviceContext);

			return newFileEntryProxyBean(fileEntry);
		}
	}

	@Override
	public FileShortcut addFileShortcut(
			long userId, long folderId, long toFileEntryId,
			ServiceContext serviceContext)
		throws PortalException {

		try (ContextClassLoaderSetter contextClassLoaderSetter =
				new ContextClassLoaderSetter(_classLoader)) {

			FileShortcut fileShortcut = _repository.addFileShortcut(
				userId, folderId, toFileEntryId, serviceContext);

			return newFileShortcutProxyBean(fileShortcut);
		}
	}

	@Override
	public Folder addFolder(
			long userId, long parentFolderId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException {

		try (ContextClassLoaderSetter contextClassLoaderSetter =
				new ContextClassLoaderSetter(_classLoader)) {

			Folder folder = _repository.addFolder(
				userId, parentFolderId, name, description, serviceContext);

			return newFolderProxyBean(folder);
		}
	}

	@Override
	public FileVersion cancelCheckOut(long fileEntryId) throws PortalException {
		try (ContextClassLoaderSetter contextClassLoaderSetter =
				new ContextClassLoaderSetter(_classLoader)) {

			FileVersion fileVersion = _repository.cancelCheckOut(fileEntryId);

			return newFileVersionProxyBean(fileVersion);
		}
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #checkInFileEntry(long, long, DLVersionNumberIncrease,
	 *             String, ServiceContext)}
	 */
	@Deprecated
	@Override
	public void checkInFileEntry(
			long userId, long fileEntryId, boolean majorVersion,
			String changeLog, ServiceContext serviceContext)
		throws PortalException {

		checkInFileEntry(
			userId, fileEntryId,
			DLVersionNumberIncrease.fromMajorVersion(majorVersion), changeLog,
			serviceContext);
	}

	@Override
	public void checkInFileEntry(
			long userId, long fileEntryId,
			DLVersionNumberIncrease dlVersionNumberIncrease, String changeLog,
			ServiceContext serviceContext)
		throws PortalException {

		try (ContextClassLoaderSetter contextClassLoaderSetter =
				new ContextClassLoaderSetter(_classLoader)) {

			_repository.checkInFileEntry(
				userId, fileEntryId, dlVersionNumberIncrease, changeLog,
				serviceContext);
		}
	}

	@Override
	public void checkInFileEntry(
			long userId, long fileEntryId, String lockUuid,
			ServiceContext serviceContext)
		throws PortalException {

		try (ContextClassLoaderSetter contextClassLoaderSetter =
				new ContextClassLoaderSetter(_classLoader)) {

			_repository.checkInFileEntry(
				userId, fileEntryId, lockUuid, serviceContext);
		}
	}

	@Override
	public FileEntry checkOutFileEntry(
			long fileEntryId, ServiceContext serviceContext)
		throws PortalException {

		try (ContextClassLoaderSetter contextClassLoaderSetter =
				new ContextClassLoaderSetter(_classLoader)) {

			FileEntry fileEntry = _repository.checkOutFileEntry(
				fileEntryId, serviceContext);

			return newFileEntryProxyBean(fileEntry);
		}
	}

	@Override
	public FileEntry checkOutFileEntry(
			long fileEntryId, String owner, long expirationTime,
			ServiceContext serviceContext)
		throws PortalException {

		try (ContextClassLoaderSetter contextClassLoaderSetter =
				new ContextClassLoaderSetter(_classLoader)) {

			FileEntry fileEntry = _repository.checkOutFileEntry(
				fileEntryId, owner, expirationTime, serviceContext);

			return newFileEntryProxyBean(fileEntry);
		}
	}

	@Override
	public FileEntry copyFileEntry(
			long userId, long groupId, long fileEntryId, long destFolderId,
			ServiceContext serviceContext)
		throws PortalException {

		try (ContextClassLoaderSetter contextClassLoaderSetter =
				new ContextClassLoaderSetter(_classLoader)) {

			FileEntry fileEntry = _repository.copyFileEntry(
				userId, groupId, fileEntryId, destFolderId, serviceContext);

			return newFileEntryProxyBean(fileEntry);
		}
	}

	@Override
	public void deleteAll() throws PortalException {
		try (ContextClassLoaderSetter contextClassLoaderSetter =
				new ContextClassLoaderSetter(_classLoader)) {

			_repository.deleteAll();
		}
	}

	@Override
	public void deleteFileEntry(long fileEntryId) throws PortalException {
		try (ContextClassLoaderSetter contextClassLoaderSetter =
				new ContextClassLoaderSetter(_classLoader)) {

			_repository.deleteFileEntry(fileEntryId);
		}
	}

	@Override
	public void deleteFileEntry(long folderId, String title)
		throws PortalException {

		try (ContextClassLoaderSetter contextClassLoaderSetter =
				new ContextClassLoaderSetter(_classLoader)) {

			_repository.deleteFileEntry(folderId, title);
		}
	}

	@Override
	public void deleteFileShortcut(long fileShortcutId) throws PortalException {
		try (ContextClassLoaderSetter contextClassLoaderSetter =
				new ContextClassLoaderSetter(_classLoader)) {

			_repository.deleteFileShortcut(fileShortcutId);
		}
	}

	@Override
	public void deleteFileShortcuts(long toFileEntryId) throws PortalException {
		try (ContextClassLoaderSetter contextClassLoaderSetter =
				new ContextClassLoaderSetter(_classLoader)) {

			_repository.deleteFileShortcuts(toFileEntryId);
		}
	}

	@Override
	public void deleteFileVersion(long fileVersionId) throws PortalException {
		try (ContextClassLoaderSetter contextClassLoaderSetter =
				new ContextClassLoaderSetter(_classLoader)) {

			_repository.deleteFileVersion(fileVersionId);
		}
	}

	@Override
	public void deleteFileVersion(long fileEntryId, String version)
		throws PortalException {

		try (ContextClassLoaderSetter contextClassLoaderSetter =
				new ContextClassLoaderSetter(_classLoader)) {

			_repository.deleteFileVersion(fileEntryId, version);
		}
	}

	@Override
	public void deleteFolder(long folderId) throws PortalException {
		try (ContextClassLoaderSetter contextClassLoaderSetter =
				new ContextClassLoaderSetter(_classLoader)) {

			_repository.deleteFolder(folderId);
		}
	}

	@Override
	public void deleteFolder(long parentFolderId, String name)
		throws PortalException {

		try (ContextClassLoaderSetter contextClassLoaderSetter =
				new ContextClassLoaderSetter(_classLoader)) {

			_repository.deleteFolder(parentFolderId, name);
		}
	}

	@Override
	public <T extends Capability> T getCapability(Class<T> capabilityClass) {
		try (ContextClassLoaderSetter contextClassLoaderSetter =
				new ContextClassLoaderSetter(_classLoader)) {

			return _repository.getCapability(capabilityClass);
		}
	}

	@Override
	public List<FileEntry> getFileEntries(
			long folderId, int status, int start, int end,
			OrderByComparator<FileEntry> obc)
		throws PortalException {

		try (ContextClassLoaderSetter contextClassLoaderSetter =
				new ContextClassLoaderSetter(_classLoader)) {

			List<FileEntry> fileEntries = _repository.getFileEntries(
				folderId, status, start, end, obc);

			return toFileEntryProxyBeans(fileEntries);
		}
	}

	@Override
	public List<FileEntry> getFileEntries(
			long folderId, int start, int end, OrderByComparator<FileEntry> obc)
		throws PortalException {

		try (ContextClassLoaderSetter contextClassLoaderSetter =
				new ContextClassLoaderSetter(_classLoader)) {

			List<FileEntry> fileEntries = _repository.getFileEntries(
				folderId, start, end, obc);

			return toFileEntryProxyBeans(fileEntries);
		}
	}

	@Override
	public List<FileEntry> getFileEntries(
			long folderId, long fileEntryTypeId, int start, int end,
			OrderByComparator<FileEntry> obc)
		throws PortalException {

		try (ContextClassLoaderSetter contextClassLoaderSetter =
				new ContextClassLoaderSetter(_classLoader)) {

			List<FileEntry> fileEntries = _repository.getFileEntries(
				folderId, fileEntryTypeId, start, end, obc);

			return toFileEntryProxyBeans(fileEntries);
		}
	}

	@Override
	public List<FileEntry> getFileEntries(
			long folderId, String[] mimeTypes, int status, int start, int end,
			OrderByComparator<FileEntry> obc)
		throws PortalException {

		try (ContextClassLoaderSetter contextClassLoaderSetter =
				new ContextClassLoaderSetter(_classLoader)) {

			List<FileEntry> fileEntries = _repository.getFileEntries(
				folderId, mimeTypes, status, start, end, obc);

			return toFileEntryProxyBeans(fileEntries);
		}
	}

	@Override
	public List<FileEntry> getFileEntries(
			long folderId, String[] mimeTypes, int start, int end,
			OrderByComparator<FileEntry> obc)
		throws PortalException {

		try (ContextClassLoaderSetter contextClassLoaderSetter =
				new ContextClassLoaderSetter(_classLoader)) {

			List<FileEntry> fileEntries = _repository.getFileEntries(
				folderId, mimeTypes, start, end, obc);

			return toFileEntryProxyBeans(fileEntries);
		}
	}

	@Override
	public List<RepositoryEntry> getFileEntriesAndFileShortcuts(
			long folderId, int status, int start, int end)
		throws PortalException {

		try (ContextClassLoaderSetter contextClassLoaderSetter =
				new ContextClassLoaderSetter(_classLoader)) {

			List<RepositoryEntry> fileEntriesAndFileShortcuts =
				_repository.getFileEntriesAndFileShortcuts(
					folderId, status, start, end);

			return toObjectProxyBeans(fileEntriesAndFileShortcuts);
		}
	}

	@Override
	public int getFileEntriesAndFileShortcutsCount(long folderId, int status)
		throws PortalException {

		try (ContextClassLoaderSetter contextClassLoaderSetter =
				new ContextClassLoaderSetter(_classLoader)) {

			return _repository.getFileEntriesAndFileShortcutsCount(
				folderId, status);
		}
	}

	@Override
	public int getFileEntriesAndFileShortcutsCount(
			long folderId, int status, String[] mimeTypes)
		throws PortalException {

		try (ContextClassLoaderSetter contextClassLoaderSetter =
				new ContextClassLoaderSetter(_classLoader)) {

			return _repository.getFileEntriesAndFileShortcutsCount(
				folderId, status, mimeTypes);
		}
	}

	@Override
	public int getFileEntriesCount(long folderId) throws PortalException {
		try (ContextClassLoaderSetter contextClassLoaderSetter =
				new ContextClassLoaderSetter(_classLoader)) {

			return _repository.getFileEntriesCount(folderId);
		}
	}

	@Override
	public int getFileEntriesCount(long folderId, int status)
		throws PortalException {

		try (ContextClassLoaderSetter contextClassLoaderSetter =
				new ContextClassLoaderSetter(_classLoader)) {

			return _repository.getFileEntriesCount(folderId, status);
		}
	}

	@Override
	public int getFileEntriesCount(long folderId, long fileEntryTypeId)
		throws PortalException {

		try (ContextClassLoaderSetter contextClassLoaderSetter =
				new ContextClassLoaderSetter(_classLoader)) {

			return _repository.getFileEntriesCount(folderId, fileEntryTypeId);
		}
	}

	@Override
	public int getFileEntriesCount(long folderId, String[] mimeTypes)
		throws PortalException {

		try (ContextClassLoaderSetter contextClassLoaderSetter =
				new ContextClassLoaderSetter(_classLoader)) {

			return _repository.getFileEntriesCount(folderId, mimeTypes);
		}
	}

	@Override
	public int getFileEntriesCount(
			long folderId, String[] mimeTypes, int status)
		throws PortalException {

		try (ContextClassLoaderSetter contextClassLoaderSetter =
				new ContextClassLoaderSetter(_classLoader)) {

			return _repository.getFileEntriesCount(folderId, mimeTypes, status);
		}
	}

	@Override
	public FileEntry getFileEntry(long fileEntryId) throws PortalException {
		try (ContextClassLoaderSetter contextClassLoaderSetter =
				new ContextClassLoaderSetter(_classLoader)) {

			return newFileEntryProxyBean(_repository.getFileEntry(fileEntryId));
		}
	}

	@Override
	public FileEntry getFileEntry(long folderId, String title)
		throws PortalException {

		try (ContextClassLoaderSetter contextClassLoaderSetter =
				new ContextClassLoaderSetter(_classLoader)) {

			FileEntry fileEntry = _repository.getFileEntry(folderId, title);

			return newFileEntryProxyBean(fileEntry);
		}
	}

	@Override
	public FileEntry getFileEntryByUuid(String uuid) throws PortalException {
		try (ContextClassLoaderSetter contextClassLoaderSetter =
				new ContextClassLoaderSetter(_classLoader)) {

			return newFileEntryProxyBean(_repository.getFileEntryByUuid(uuid));
		}
	}

	@Override
	public FileShortcut getFileShortcut(long fileShortcutId)
		throws PortalException {

		try (ContextClassLoaderSetter contextClassLoaderSetter =
				new ContextClassLoaderSetter(_classLoader)) {

			return newFileShortcutProxyBean(
				_repository.getFileShortcut(fileShortcutId));
		}
	}

	@Override
	public FileVersion getFileVersion(long fileVersionId)
		throws PortalException {

		try (ContextClassLoaderSetter contextClassLoaderSetter =
				new ContextClassLoaderSetter(_classLoader)) {

			return newFileVersionProxyBean(
				_repository.getFileVersion(fileVersionId));
		}
	}

	@Override
	public Folder getFolder(long folderId) throws PortalException {
		try (ContextClassLoaderSetter contextClassLoaderSetter =
				new ContextClassLoaderSetter(_classLoader)) {

			return newFolderProxyBean(_repository.getFolder(folderId));
		}
	}

	@Override
	public Folder getFolder(long parentFolderId, String name)
		throws PortalException {

		try (ContextClassLoaderSetter contextClassLoaderSetter =
				new ContextClassLoaderSetter(_classLoader)) {

			Folder folder = _repository.getFolder(parentFolderId, name);

			return newFolderProxyBean(folder);
		}
	}

	@Override
	public List<Folder> getFolders(
			long parentFolderId, boolean includeMountFolders, int start,
			int end, OrderByComparator<Folder> obc)
		throws PortalException {

		try (ContextClassLoaderSetter contextClassLoaderSetter =
				new ContextClassLoaderSetter(_classLoader)) {

			List<Folder> folders = _repository.getFolders(
				parentFolderId, includeMountFolders, start, end, obc);

			return toFolderProxyBeans(folders);
		}
	}

	@Override
	public List<Folder> getFolders(
			long parentFolderId, int status, boolean includeMountFolders,
			int start, int end, OrderByComparator<Folder> obc)
		throws PortalException {

		try (ContextClassLoaderSetter contextClassLoaderSetter =
				new ContextClassLoaderSetter(_classLoader)) {

			List<Folder> folders = _repository.getFolders(
				parentFolderId, status, includeMountFolders, start, end, obc);

			return toFolderProxyBeans(folders);
		}
	}

	@Override
	public List<RepositoryEntry> getFoldersAndFileEntriesAndFileShortcuts(
			long folderId, int status, boolean includeMountFolders, int start,
			int end, OrderByComparator<?> obc)
		throws PortalException {

		try (ContextClassLoaderSetter contextClassLoaderSetter =
				new ContextClassLoaderSetter(_classLoader)) {

			List<RepositoryEntry> foldersAndFileEntriesAndFileShortcuts =
				_repository.getFoldersAndFileEntriesAndFileShortcuts(
					folderId, status, includeMountFolders, start, end, obc);

			return toObjectProxyBeans(foldersAndFileEntriesAndFileShortcuts);
		}
	}

	@Override
	public List<RepositoryEntry> getFoldersAndFileEntriesAndFileShortcuts(
			long folderId, int status, String[] mimetypes,
			boolean includeMountFolders, int start, int end,
			OrderByComparator<?> obc)
		throws PortalException {

		try (ContextClassLoaderSetter contextClassLoaderSetter =
				new ContextClassLoaderSetter(_classLoader)) {

			List<RepositoryEntry> foldersAndFileEntriesAndFileShortcuts =
				_repository.getFoldersAndFileEntriesAndFileShortcuts(
					folderId, status, mimetypes, includeMountFolders, start,
					end, obc);

			return toObjectProxyBeans(foldersAndFileEntriesAndFileShortcuts);
		}
	}

	@Override
	public int getFoldersAndFileEntriesAndFileShortcutsCount(
			long folderId, int status, boolean includeMountFolders)
		throws PortalException {

		try (ContextClassLoaderSetter contextClassLoaderSetter =
				new ContextClassLoaderSetter(_classLoader)) {

			return _repository.getFoldersAndFileEntriesAndFileShortcutsCount(
				folderId, status, includeMountFolders);
		}
	}

	@Override
	public int getFoldersAndFileEntriesAndFileShortcutsCount(
			long folderId, int status, String[] mimetypes,
			boolean includeMountFolders)
		throws PortalException {

		try (ContextClassLoaderSetter contextClassLoaderSetter =
				new ContextClassLoaderSetter(_classLoader)) {

			return _repository.getFoldersAndFileEntriesAndFileShortcutsCount(
				folderId, status, mimetypes, includeMountFolders);
		}
	}

	@Override
	public int getFoldersCount(long parentFolderId, boolean includeMountfolders)
		throws PortalException {

		try (ContextClassLoaderSetter contextClassLoaderSetter =
				new ContextClassLoaderSetter(_classLoader)) {

			return _repository.getFoldersCount(
				parentFolderId, includeMountfolders);
		}
	}

	@Override
	public int getFoldersCount(
			long parentFolderId, int status, boolean includeMountfolders)
		throws PortalException {

		try (ContextClassLoaderSetter contextClassLoaderSetter =
				new ContextClassLoaderSetter(_classLoader)) {

			return _repository.getFoldersCount(
				parentFolderId, status, includeMountfolders);
		}
	}

	@Override
	public int getFoldersFileEntriesCount(List<Long> folderIds, int status)
		throws PortalException {

		try (ContextClassLoaderSetter contextClassLoaderSetter =
				new ContextClassLoaderSetter(_classLoader)) {

			return _repository.getFoldersFileEntriesCount(folderIds, status);
		}
	}

	@Override
	public List<Folder> getMountFolders(
			long parentFolderId, int start, int end,
			OrderByComparator<Folder> obc)
		throws PortalException {

		try (ContextClassLoaderSetter contextClassLoaderSetter =
				new ContextClassLoaderSetter(_classLoader)) {

			List<Folder> mountFolders = _repository.getMountFolders(
				parentFolderId, start, end, obc);

			return toFolderProxyBeans(mountFolders);
		}
	}

	@Override
	public int getMountFoldersCount(long parentFolderId)
		throws PortalException {

		try (ContextClassLoaderSetter contextClassLoaderSetter =
				new ContextClassLoaderSetter(_classLoader)) {

			return _repository.getMountFoldersCount(parentFolderId);
		}
	}

	@Override
	public List<FileEntry> getRepositoryFileEntries(
			long userId, long rootFolderId, int start, int end,
			OrderByComparator<FileEntry> obc)
		throws PortalException {

		try (ContextClassLoaderSetter contextClassLoaderSetter =
				new ContextClassLoaderSetter(_classLoader)) {

			List<FileEntry> repositoryFileEntries =
				_repository.getRepositoryFileEntries(
					userId, rootFolderId, start, end, obc);

			return toFileEntryProxyBeans(repositoryFileEntries);
		}
	}

	@Override
	public List<FileEntry> getRepositoryFileEntries(
			long userId, long rootFolderId, String[] mimeTypes, int status,
			int start, int end, OrderByComparator<FileEntry> obc)
		throws PortalException {

		try (ContextClassLoaderSetter contextClassLoaderSetter =
				new ContextClassLoaderSetter(_classLoader)) {

			List<FileEntry> repositoryFileEntries =
				_repository.getRepositoryFileEntries(
					userId, rootFolderId, mimeTypes, status, start, end, obc);

			return toFileEntryProxyBeans(repositoryFileEntries);
		}
	}

	@Override
	public int getRepositoryFileEntriesCount(long userId, long rootFolderId)
		throws PortalException {

		try (ContextClassLoaderSetter contextClassLoaderSetter =
				new ContextClassLoaderSetter(_classLoader)) {

			return _repository.getRepositoryFileEntriesCount(
				userId, rootFolderId);
		}
	}

	@Override
	public int getRepositoryFileEntriesCount(
			long userId, long rootFolderId, String[] mimeTypes, int status)
		throws PortalException {

		try (ContextClassLoaderSetter contextClassLoaderSetter =
				new ContextClassLoaderSetter(_classLoader)) {

			return _repository.getRepositoryFileEntriesCount(
				userId, rootFolderId, mimeTypes, status);
		}
	}

	@Override
	public long getRepositoryId() {
		try (ContextClassLoaderSetter contextClassLoaderSetter =
				new ContextClassLoaderSetter(_classLoader)) {

			return _repository.getRepositoryId();
		}
	}

	@Override
	public void getSubfolderIds(List<Long> folderIds, long folderId)
		throws PortalException {

		try (ContextClassLoaderSetter contextClassLoaderSetter =
				new ContextClassLoaderSetter(_classLoader)) {

			_repository.getSubfolderIds(folderIds, folderId);
		}
	}

	@Override
	public List<Long> getSubfolderIds(long folderId, boolean recurse)
		throws PortalException {

		try (ContextClassLoaderSetter contextClassLoaderSetter =
				new ContextClassLoaderSetter(_classLoader)) {

			return _repository.getSubfolderIds(folderId, recurse);
		}
	}

	@Override
	public <T extends Capability> boolean isCapabilityProvided(
		Class<T> capabilityClass) {

		try (ContextClassLoaderSetter contextClassLoaderSetter =
				new ContextClassLoaderSetter(_classLoader)) {

			return _repository.isCapabilityProvided(capabilityClass);
		}
	}

	@Override
	public Lock lockFolder(long folderId) throws PortalException {
		try (ContextClassLoaderSetter contextClassLoaderSetter =
				new ContextClassLoaderSetter(_classLoader)) {

			return _repository.lockFolder(folderId);
		}
	}

	@Override
	public Lock lockFolder(
			long folderId, String owner, boolean inheritable,
			long expirationTime)
		throws PortalException {

		try (ContextClassLoaderSetter contextClassLoaderSetter =
				new ContextClassLoaderSetter(_classLoader)) {

			return _repository.lockFolder(
				folderId, owner, inheritable, expirationTime);
		}
	}

	@Override
	public FileEntry moveFileEntry(
			long userId, long fileEntryId, long newFolderId,
			ServiceContext serviceContext)
		throws PortalException {

		try (ContextClassLoaderSetter contextClassLoaderSetter =
				new ContextClassLoaderSetter(_classLoader)) {

			FileEntry fileEntry = _repository.moveFileEntry(
				userId, fileEntryId, newFolderId, serviceContext);

			return newFileEntryProxyBean(fileEntry);
		}
	}

	@Override
	public Folder moveFolder(
			long userId, long folderId, long parentFolderId,
			ServiceContext serviceContext)
		throws PortalException {

		try (ContextClassLoaderSetter contextClassLoaderSetter =
				new ContextClassLoaderSetter(_classLoader)) {

			Folder folder = _repository.moveFolder(
				userId, folderId, parentFolderId, serviceContext);

			return newFolderProxyBean(folder);
		}
	}

	@Override
	public Lock refreshFileEntryLock(
			String lockUuid, long companyId, long expirationTime)
		throws PortalException {

		try (ContextClassLoaderSetter contextClassLoaderSetter =
				new ContextClassLoaderSetter(_classLoader)) {

			return _repository.refreshFileEntryLock(
				lockUuid, companyId, expirationTime);
		}
	}

	@Override
	public Lock refreshFolderLock(
			String lockUuid, long companyId, long expirationTime)
		throws PortalException {

		try (ContextClassLoaderSetter contextClassLoaderSetter =
				new ContextClassLoaderSetter(_classLoader)) {

			return _repository.refreshFolderLock(
				lockUuid, companyId, expirationTime);
		}
	}

	@Override
	public void revertFileEntry(
			long userId, long fileEntryId, String version,
			ServiceContext serviceContext)
		throws PortalException {

		try (ContextClassLoaderSetter contextClassLoaderSetter =
				new ContextClassLoaderSetter(_classLoader)) {

			_repository.revertFileEntry(
				userId, fileEntryId, version, serviceContext);
		}
	}

	@Override
	public Hits search(long creatorUserId, int status, int start, int end)
		throws PortalException {

		try (ContextClassLoaderSetter contextClassLoaderSetter =
				new ContextClassLoaderSetter(_classLoader)) {

			return _repository.search(creatorUserId, status, start, end);
		}
	}

	@Override
	public Hits search(
			long creatorUserId, long folderId, String[] mimeTypes, int status,
			int start, int end)
		throws PortalException {

		try (ContextClassLoaderSetter contextClassLoaderSetter =
				new ContextClassLoaderSetter(_classLoader)) {

			return _repository.search(
				creatorUserId, folderId, mimeTypes, status, start, end);
		}
	}

	@Override
	public Hits search(SearchContext searchContext) throws SearchException {
		try (ContextClassLoaderSetter contextClassLoaderSetter =
				new ContextClassLoaderSetter(_classLoader)) {

			return _repository.search(searchContext);
		}
	}

	@Override
	public Hits search(SearchContext searchContext, Query query)
		throws SearchException {

		try (ContextClassLoaderSetter contextClassLoaderSetter =
				new ContextClassLoaderSetter(_classLoader)) {

			return _repository.search(searchContext, query);
		}
	}

	@Override
	public void unlockFolder(long folderId, String lockUuid)
		throws PortalException {

		try (ContextClassLoaderSetter contextClassLoaderSetter =
				new ContextClassLoaderSetter(_classLoader)) {

			_repository.unlockFolder(folderId, lockUuid);
		}
	}

	@Override
	public void unlockFolder(long parentFolderId, String name, String lockUuid)
		throws PortalException {

		try (ContextClassLoaderSetter contextClassLoaderSetter =
				new ContextClassLoaderSetter(_classLoader)) {

			_repository.unlockFolder(parentFolderId, name, lockUuid);
		}
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #updateFileEntry(long, long, String, String, String, String,
	 *             String, DLVersionNumberIncrease, File, ServiceContext)}
	 */
	@Deprecated
	@Override
	public FileEntry updateFileEntry(
			long userId, long fileEntryId, String sourceFileName,
			String mimeType, String title, String description, String changeLog,
			boolean majorVersion, File file, ServiceContext serviceContext)
		throws PortalException {

		return updateFileEntry(
			userId, fileEntryId, sourceFileName, mimeType, title, description,
			changeLog, DLVersionNumberIncrease.fromMajorVersion(majorVersion),
			file, serviceContext);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #updateFileEntry(long, long, String, String, String, String,
	 *             String, DLVersionNumberIncrease, InputStream, long,
	 *             ServiceContext)}
	 */
	@Deprecated
	@Override
	public FileEntry updateFileEntry(
			long userId, long fileEntryId, String sourceFileName,
			String mimeType, String title, String description, String changeLog,
			boolean majorVersion, InputStream is, long size,
			ServiceContext serviceContext)
		throws PortalException {

		return updateFileEntry(
			userId, fileEntryId, sourceFileName, mimeType, title, description,
			changeLog, DLVersionNumberIncrease.fromMajorVersion(majorVersion),
			is, size, serviceContext);
	}

	@Override
	public FileEntry updateFileEntry(
			long userId, long fileEntryId, String sourceFileName,
			String mimeType, String title, String description, String changeLog,
			DLVersionNumberIncrease dlVersionNumberIncrease, File file,
			ServiceContext serviceContext)
		throws PortalException {

		try (ContextClassLoaderSetter contextClassLoaderSetter =
				new ContextClassLoaderSetter(_classLoader)) {

			FileEntry fileEntry = _repository.updateFileEntry(
				userId, fileEntryId, sourceFileName, mimeType, title,
				description, changeLog, dlVersionNumberIncrease, file,
				serviceContext);

			return newFileEntryProxyBean(fileEntry);
		}
	}

	@Override
	public FileEntry updateFileEntry(
			long userId, long fileEntryId, String sourceFileName,
			String mimeType, String title, String description, String changeLog,
			DLVersionNumberIncrease dlVersionNumberIncrease, InputStream is,
			long size, ServiceContext serviceContext)
		throws PortalException {

		try (ContextClassLoaderSetter contextClassLoaderSetter =
				new ContextClassLoaderSetter(_classLoader)) {

			FileEntry fileEntry = _repository.updateFileEntry(
				userId, fileEntryId, sourceFileName, mimeType, title,
				description, changeLog, dlVersionNumberIncrease, is, size,
				serviceContext);

			return newFileEntryProxyBean(fileEntry);
		}
	}

	@Override
	public FileShortcut updateFileShortcut(
			long userId, long fileShortcutId, long folderId, long toFileEntryId,
			ServiceContext serviceContext)
		throws PortalException {

		try (ContextClassLoaderSetter contextClassLoaderSetter =
				new ContextClassLoaderSetter(_classLoader)) {

			FileShortcut fileShortcut = _repository.updateFileShortcut(
				userId, fileShortcutId, folderId, toFileEntryId,
				serviceContext);

			return newFileShortcutProxyBean(fileShortcut);
		}
	}

	@Override
	public void updateFileShortcuts(
			long oldToFileEntryId, long newToFileEntryId)
		throws PortalException {

		try (ContextClassLoaderSetter contextClassLoaderSetter =
				new ContextClassLoaderSetter(_classLoader)) {

			_repository.updateFileShortcuts(oldToFileEntryId, newToFileEntryId);
		}
	}

	@Override
	public Folder updateFolder(
			long folderId, long parentFolderId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException {

		try (ContextClassLoaderSetter contextClassLoaderSetter =
				new ContextClassLoaderSetter(_classLoader)) {

			Folder folder = _repository.updateFolder(
				folderId, parentFolderId, name, description, serviceContext);

			return newFolderProxyBean(folder);
		}
	}

	@Override
	public Folder updateFolder(
			long folderId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException {

		try (ContextClassLoaderSetter contextClassLoaderSetter =
				new ContextClassLoaderSetter(_classLoader)) {

			Folder folder = _repository.updateFolder(
				folderId, name, description, serviceContext);

			return newFolderProxyBean(folder);
		}
	}

	@Override
	public boolean verifyFileEntryCheckOut(long fileEntryId, String lockUuid)
		throws PortalException {

		try (ContextClassLoaderSetter contextClassLoaderSetter =
				new ContextClassLoaderSetter(_classLoader)) {

			return _repository.verifyFileEntryCheckOut(fileEntryId, lockUuid);
		}
	}

	@Override
	public boolean verifyFileEntryLock(long fileEntryId, String lockUuid)
		throws PortalException {

		try (ContextClassLoaderSetter contextClassLoaderSetter =
				new ContextClassLoaderSetter(_classLoader)) {

			return _repository.verifyFileEntryLock(fileEntryId, lockUuid);
		}
	}

	@Override
	public boolean verifyInheritableLock(long folderId, String lockUuid)
		throws PortalException {

		try (ContextClassLoaderSetter contextClassLoaderSetter =
				new ContextClassLoaderSetter(_classLoader)) {

			return _repository.verifyInheritableLock(folderId, lockUuid);
		}
	}

	private final ClassLoader _classLoader;
	private final Repository _repository;

}
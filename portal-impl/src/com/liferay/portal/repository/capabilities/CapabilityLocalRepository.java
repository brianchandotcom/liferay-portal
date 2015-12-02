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

package com.liferay.portal.repository.capabilities;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.capabilities.CapabilityProvider;
import com.liferay.portal.kernel.repository.event.RepositoryEventTrigger;
import com.liferay.portal.kernel.repository.event.RepositoryEventType;
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
 * @author Adolfo Pérez
 */
public class CapabilityLocalRepository
	extends BaseCapabilityRepository<LocalRepository>
	implements LocalRepository {

	public CapabilityLocalRepository(
		LocalRepository localRepository, CapabilityProvider capabilityProvider,
		RepositoryEventTrigger repositoryEventTrigger) {

		super(localRepository, capabilityProvider);

		_repositoryEventTrigger = repositoryEventTrigger;
	}

	@Override
	public FileEntry addFileEntry(
			long userId, long folderId, String sourceFileName, String mimeType,
			String title, String description, String changeLog, File file,
			ServiceContext serviceContext)
		throws PortalException {

		LocalRepository localRepository = getRepository();

		FileEntry fileEntry = localRepository.addFileEntry(
			userId, folderId, sourceFileName, mimeType, title, description,
			changeLog, file, serviceContext);

		_repositoryEventTrigger.trigger(
			RepositoryEventType.Add.class, FileEntry.class, fileEntry);

		return fileEntry;
	}

	@Override
	public FileEntry addFileEntry(
			long userId, long folderId, String sourceFileName, String mimeType,
			String title, String description, String changeLog, InputStream is,
			long size, ServiceContext serviceContext)
		throws PortalException {

		LocalRepository localRepository = getRepository();

		FileEntry fileEntry = localRepository.addFileEntry(
			userId, folderId, sourceFileName, mimeType, title, description,
			changeLog, is, size, serviceContext);

		_repositoryEventTrigger.trigger(
			RepositoryEventType.Add.class, FileEntry.class, fileEntry);

		return fileEntry;
	}

	@Override
	public FileShortcut addFileShortcut(
			long userId, long folderId, long toFileEntryId,
			ServiceContext serviceContext)
		throws PortalException {

		LocalRepository localRepository = getRepository();

		FileShortcut fileShortcut = localRepository.addFileShortcut(
			userId, folderId, toFileEntryId, serviceContext);

		_repositoryEventTrigger.trigger(
			RepositoryEventType.Add.class, FileShortcut.class, fileShortcut);

		return fileShortcut;
	}

	@Override
	public Folder addFolder(
			long userId, long parentFolderId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException {

		LocalRepository localRepository = getRepository();

		Folder folder = localRepository.addFolder(
			userId, parentFolderId, name, description, serviceContext);

		_repositoryEventTrigger.trigger(
			RepositoryEventType.Add.class, Folder.class, folder);

		return folder;
	}

	@Override
	public void checkInFileEntry(
			long userId, long fileEntryId, boolean major, String changeLog,
			ServiceContext serviceContext)
		throws PortalException {

		LocalRepository localRepository = getRepository();

		localRepository.checkInFileEntry(
			userId, fileEntryId, major, changeLog, serviceContext);

		FileEntry fileEntry = localRepository.getFileEntry(fileEntryId);

		_repositoryEventTrigger.trigger(
			RepositoryEventType.Update.class, FileEntry.class, fileEntry);
	}

	@Override
	public void checkInFileEntry(
			long userId, long fileEntryId, String lockUuid,
			ServiceContext serviceContext)
		throws PortalException {

		LocalRepository localRepository = getRepository();

		localRepository.checkInFileEntry(
			userId, fileEntryId, lockUuid, serviceContext);

		FileEntry fileEntry = localRepository.getFileEntry(fileEntryId);

		_repositoryEventTrigger.trigger(
			RepositoryEventType.Update.class, FileEntry.class, fileEntry);
	}

	@Override
	public FileEntry copyFileEntry(
			long userId, long groupId, long fileEntryId, long destFolderId,
			ServiceContext serviceContext)
		throws PortalException {

		LocalRepository localRepository = getRepository();

		FileEntry fileEntry = localRepository.copyFileEntry(
			userId, groupId, fileEntryId, destFolderId, serviceContext);

		_repositoryEventTrigger.trigger(
			RepositoryEventType.Add.class, FileEntry.class, fileEntry);

		return fileEntry;
	}

	@Override
	public void deleteAll() throws PortalException {
		LocalRepository localRepository = getRepository();

		_repositoryEventTrigger.trigger(
			RepositoryEventType.Delete.class, LocalRepository.class,
			localRepository);

		localRepository.deleteAll();
	}

	@Override
	public void deleteFileEntry(long fileEntryId) throws PortalException {
		LocalRepository localRepository = getRepository();

		FileEntry fileEntry = localRepository.getFileEntry(fileEntryId);

		_repositoryEventTrigger.trigger(
			RepositoryEventType.Delete.class, FileEntry.class, fileEntry);

		localRepository.deleteFileEntry(fileEntryId);
	}

	@Override
	public void deleteFileShortcut(long fileShortcutId) throws PortalException {
		LocalRepository localRepository = getRepository();

		FileShortcut fileShortcut = localRepository.getFileShortcut(
			fileShortcutId);

		_repositoryEventTrigger.trigger(
			RepositoryEventType.Delete.class, FileShortcut.class, fileShortcut);

		localRepository.deleteFileShortcut(fileShortcutId);
	}

	@Override
	public void deleteFileShortcuts(long toFileEntryId) throws PortalException {
		LocalRepository localRepository = getRepository();

		FileEntry fileEntry = localRepository.getFileEntry(toFileEntryId);

		List<FileShortcut> fileShortcuts = fileEntry.getFileShortcuts();

		for (FileShortcut fileShortcut : fileShortcuts) {
			_repositoryEventTrigger.trigger(
				RepositoryEventType.Delete.class, FileShortcut.class,
				fileShortcut);
		}

		localRepository.deleteFileShortcuts(toFileEntryId);
	}

	@Override
	public void deleteFolder(long folderId) throws PortalException {
		LocalRepository localRepository = getRepository();

		Folder folder = localRepository.getFolder(folderId);

		_repositoryEventTrigger.trigger(
			RepositoryEventType.Delete.class, Folder.class, folder);

		localRepository.deleteFolder(folderId);
	}

	@Override
	public List<FileEntry> getFileEntries(
			long folderId, int status, int start, int end,
			OrderByComparator<FileEntry> obc)
		throws PortalException {

		return getRepository().getFileEntries(
			folderId, status, start, end, obc);
	}

	@Override
	public List<FileEntry> getFileEntries(
			long folderId, int start, int end, OrderByComparator<FileEntry> obc)
		throws PortalException {

		return getRepository().getFileEntries(folderId, start, end, obc);
	}

	@Override
	public List<RepositoryEntry> getFileEntriesAndFileShortcuts(
			long folderId, int status, int start, int end)
		throws PortalException {

		return getRepository().getFileEntriesAndFileShortcuts(
			folderId, status, start, end);
	}

	@Override
	public int getFileEntriesAndFileShortcutsCount(long folderId, int status)
		throws PortalException {

		return getRepository().getFileEntriesAndFileShortcutsCount(
			folderId, status);
	}

	@Override
	public int getFileEntriesCount(long folderId) throws PortalException {
		return getRepository().getFileEntriesCount(folderId);
	}

	@Override
	public int getFileEntriesCount(long folderId, int status)
		throws PortalException {

		return getRepository().getFileEntriesCount(folderId, status);
	}

	@Override
	public FileEntry getFileEntry(long fileEntryId) throws PortalException {
		return getRepository().getFileEntry(fileEntryId);
	}

	@Override
	public FileEntry getFileEntry(long folderId, String title)
		throws PortalException {

		return getRepository().getFileEntry(folderId, title);
	}

	@Override
	public FileEntry getFileEntryByUuid(String uuid) throws PortalException {
		return getRepository().getFileEntryByUuid(uuid);
	}

	@Override
	public FileShortcut getFileShortcut(long fileShortcutId)
		throws PortalException {

		return getRepository().getFileShortcut(fileShortcutId);
	}

	@Override
	public FileVersion getFileVersion(long fileVersionId)
		throws PortalException {

		return getRepository().getFileVersion(fileVersionId);
	}

	@Override
	public Folder getFolder(long folderId) throws PortalException {
		return getRepository().getFolder(folderId);
	}

	@Override
	public Folder getFolder(long parentFolderId, String name)
		throws PortalException {

		return getRepository().getFolder(parentFolderId, name);
	}

	@Override
	public List<Folder> getFolders(
			long parentFolderId, boolean includeMountFolders, int start,
			int end, OrderByComparator<Folder> obc)
		throws PortalException {

		return getRepository().getFolders(
			parentFolderId, includeMountFolders, start, end, obc);
	}

	@Override
	public List<Folder> getFolders(
			long parentFolderId, int status, boolean includeMountFolders,
			int start, int end, OrderByComparator<Folder> obc)
		throws PortalException {

		return getRepository().getFolders(
			parentFolderId, status, includeMountFolders, start, end, obc);
	}

	@Override
	public List<RepositoryEntry> getFoldersAndFileEntriesAndFileShortcuts(
			long folderId, int status, boolean includeMountFolders, int start,
			int end, OrderByComparator<?> obc)
		throws PortalException {

		return getRepository().getFoldersAndFileEntriesAndFileShortcuts(
			folderId, status, includeMountFolders, start, end, obc);
	}

	@Override
	public int getFoldersCount(long parentFolderId, boolean includeMountfolders)
		throws PortalException {

		return getRepository().getFoldersCount(
			parentFolderId, includeMountfolders);
	}

	@Override
	public int getFoldersCount(
			long parentFolderId, int status, boolean includeMountfolders)
		throws PortalException {

		return getRepository().getFoldersCount(
			parentFolderId, status, includeMountfolders);
	}

	@Override
	public List<FileEntry> getRepositoryFileEntries(
			long userId, long rootFolderId, int start, int end,
			OrderByComparator<FileEntry> obc)
		throws PortalException {

		return getRepository().getRepositoryFileEntries(
			userId, rootFolderId, start, end, obc);
	}

	@Override
	public long getRepositoryId() {
		return getRepository().getRepositoryId();
	}

	@Override
	public FileEntry moveFileEntry(
			long userId, long fileEntryId, long newFolderId,
			ServiceContext serviceContext)
		throws PortalException {

		LocalRepository localRepository = getRepository();

		FileEntry fileEntry = localRepository.moveFileEntry(
			userId, fileEntryId, newFolderId, serviceContext);

		_repositoryEventTrigger.trigger(
			RepositoryEventType.Move.class, FileEntry.class, fileEntry);

		return fileEntry;
	}

	@Override
	public Folder moveFolder(
			long userId, long folderId, long parentFolderId,
			ServiceContext serviceContext)
		throws PortalException {

		LocalRepository localRepository = getRepository();

		Folder folder = localRepository.moveFolder(
			userId, folderId, parentFolderId, serviceContext);

		_repositoryEventTrigger.trigger(
			RepositoryEventType.Move.class, Folder.class, folder);

		return folder;
	}

	@Override
	public void revertFileEntry(
			long userId, long fileEntryId, String version,
			ServiceContext serviceContext)
		throws PortalException {

		LocalRepository localRepository = getRepository();

		localRepository.revertFileEntry(
			userId, fileEntryId, version, serviceContext);

		FileEntry fileEntry = getFileEntry(fileEntryId);

		_repositoryEventTrigger.trigger(
			RepositoryEventType.Update.class, FileEntry.class, fileEntry);
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

		getRepository().updateAsset(
			userId, fileEntry, fileVersion, assetCategoryIds, assetTagNames,
			assetLinkEntryIds);
	}

	@Override
	public FileEntry updateFileEntry(
			long userId, long fileEntryId, String sourceFileName,
			String mimeType, String title, String description, String changeLog,
			boolean majorVersion, File file, ServiceContext serviceContext)
		throws PortalException {

		LocalRepository localRepository = getRepository();

		FileEntry fileEntry = localRepository.updateFileEntry(
			userId, fileEntryId, sourceFileName, mimeType, title, description,
			changeLog, majorVersion, file, serviceContext);

		_repositoryEventTrigger.trigger(
			RepositoryEventType.Update.class, FileEntry.class, fileEntry);

		return fileEntry;
	}

	@Override
	public FileEntry updateFileEntry(
			long userId, long fileEntryId, String sourceFileName,
			String mimeType, String title, String description, String changeLog,
			boolean majorVersion, InputStream is, long size,
			ServiceContext serviceContext)
		throws PortalException {

		LocalRepository localRepository = getRepository();

		FileEntry fileEntry = localRepository.updateFileEntry(
			userId, fileEntryId, sourceFileName, mimeType, title, description,
			changeLog, majorVersion, is, size, serviceContext);

		_repositoryEventTrigger.trigger(
			RepositoryEventType.Update.class, FileEntry.class, fileEntry);

		return fileEntry;
	}

	@Override
	public FileShortcut updateFileShortcut(
			long userId, long fileShortcutId, long folderId, long toFileEntryId,
			ServiceContext serviceContext)
		throws PortalException {

		LocalRepository localRepository = getRepository();

		FileShortcut fileShortcut = localRepository.updateFileShortcut(
			userId, fileShortcutId, folderId, toFileEntryId, serviceContext);

		_repositoryEventTrigger.trigger(
			RepositoryEventType.Update.class, FileShortcut.class, fileShortcut);

		return fileShortcut;
	}

	@Override
	public void updateFileShortcuts(
			long oldToFileEntryId, long newToFileEntryId)
		throws PortalException {

		LocalRepository localRepository = getRepository();

		FileEntry fileEntry = localRepository.getFileEntry(oldToFileEntryId);

		fileEntry.getFileShortcuts();

		localRepository.updateFileShortcuts(oldToFileEntryId, newToFileEntryId);
	}

	@Override
	public Folder updateFolder(
			long folderId, long parentFolderId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException {

		LocalRepository localRepository = getRepository();

		Folder folder = localRepository.updateFolder(
			folderId, parentFolderId, name, description, serviceContext);

		_repositoryEventTrigger.trigger(
			RepositoryEventType.Update.class, Folder.class, folder);

		return folder;
	}

	private final RepositoryEventTrigger _repositoryEventTrigger;

}
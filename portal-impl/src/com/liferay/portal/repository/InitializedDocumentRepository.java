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

import com.liferay.document.library.kernel.model.DLVersionNumberIncrease;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.DocumentRepository;
import com.liferay.portal.kernel.repository.capabilities.Capability;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.repository.model.RepositoryEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.File;
import java.io.InputStream;

import java.util.List;

/**
 * @author Adolfo Pérez
 */
public abstract class InitializedDocumentRepository
	<T extends DocumentRepository>
		implements DocumentRepository {

	@Override
	public FileEntry addFileEntry(
			long userId, long folderId, String sourceFileName, String mimeType,
			String title, String description, String changeLog, File file,
			ServiceContext serviceContext)
		throws PortalException {

		checkDocumentRepository();

		return documentRepository.addFileEntry(
			userId, folderId, sourceFileName, mimeType, title, description,
			changeLog, file, serviceContext);
	}

	@Override
	public FileEntry addFileEntry(
			long userId, long folderId, String sourceFileName, String mimeType,
			String title, String description, String changeLog, InputStream is,
			long size, ServiceContext serviceContext)
		throws PortalException {

		checkDocumentRepository();

		return documentRepository.addFileEntry(
			userId, folderId, sourceFileName, mimeType, title, description,
			changeLog, is, size, serviceContext);
	}

	@Override
	public FileShortcut addFileShortcut(
			long userId, long folderId, long toFileEntryId,
			ServiceContext serviceContext)
		throws PortalException {

		checkDocumentRepository();

		return documentRepository.addFileShortcut(
			userId, folderId, toFileEntryId, serviceContext);
	}

	@Override
	public Folder addFolder(
			long userId, long parentFolderId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException {

		checkDocumentRepository();

		return documentRepository.addFolder(
			userId, parentFolderId, name, description, serviceContext);
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

		checkDocumentRepository();

		documentRepository.checkInFileEntry(
			userId, fileEntryId, dlVersionNumberIncrease, changeLog,
			serviceContext);
	}

	@Override
	public void checkInFileEntry(
			long userId, long fileEntryId, String lockUuid,
			ServiceContext serviceContext)
		throws PortalException {

		checkDocumentRepository();

		documentRepository.checkInFileEntry(
			userId, fileEntryId, lockUuid, serviceContext);
	}

	@Override
	public FileEntry copyFileEntry(
			long userId, long groupId, long fileEntryId, long destFolderId,
			ServiceContext serviceContext)
		throws PortalException {

		checkDocumentRepository();

		return documentRepository.copyFileEntry(
			userId, groupId, fileEntryId, destFolderId, serviceContext);
	}

	@Override
	public void deleteAll() throws PortalException {
		checkDocumentRepository();

		documentRepository.deleteAll();
	}

	@Override
	public void deleteFileEntry(long fileEntryId) throws PortalException {
		checkDocumentRepository();

		documentRepository.deleteFileEntry(fileEntryId);
	}

	@Override
	public void deleteFileShortcut(long fileShortcutId) throws PortalException {
		checkDocumentRepository();

		documentRepository.deleteFileShortcut(fileShortcutId);
	}

	@Override
	public void deleteFileShortcuts(long toFileEntryId) throws PortalException {
		checkDocumentRepository();

		documentRepository.deleteFileShortcuts(toFileEntryId);
	}

	@Override
	public void deleteFileVersion(long fileVersionId) throws PortalException {
		checkDocumentRepository();

		documentRepository.deleteFileVersion(fileVersionId);
	}

	@Override
	public void deleteFolder(long folderId) throws PortalException {
		checkDocumentRepository();

		documentRepository.deleteFolder(folderId);
	}

	@Override
	public <C extends Capability> C getCapability(Class<C> capabilityClass) {
		checkDocumentRepository();

		return documentRepository.getCapability(capabilityClass);
	}

	@Override
	public List<FileEntry> getFileEntries(
			long folderId, int status, int start, int end,
			OrderByComparator<FileEntry> obc)
		throws PortalException {

		checkDocumentRepository();

		return documentRepository.getFileEntries(
			folderId, status, start, end, obc);
	}

	@Override
	public List<FileEntry> getFileEntries(
			long folderId, int start, int end, OrderByComparator<FileEntry> obc)
		throws PortalException {

		checkDocumentRepository();

		return documentRepository.getFileEntries(folderId, start, end, obc);
	}

	@Override
	public List<RepositoryEntry> getFileEntriesAndFileShortcuts(
			long folderId, int status, int start, int end)
		throws PortalException {

		return documentRepository.getFileEntriesAndFileShortcuts(
			folderId, status, start, end);
	}

	@Override
	public int getFileEntriesAndFileShortcutsCount(long folderId, int status)
		throws PortalException {

		return documentRepository.getFileEntriesAndFileShortcutsCount(
			folderId, status);
	}

	@Override
	public int getFileEntriesCount(long folderId) throws PortalException {
		checkDocumentRepository();

		return documentRepository.getFileEntriesCount(folderId);
	}

	@Override
	public int getFileEntriesCount(long folderId, int status)
		throws PortalException {

		checkDocumentRepository();

		return documentRepository.getFileEntriesCount(folderId, status);
	}

	@Override
	public FileEntry getFileEntry(long fileEntryId) throws PortalException {
		checkDocumentRepository();

		return documentRepository.getFileEntry(fileEntryId);
	}

	@Override
	public FileEntry getFileEntry(long folderId, String title)
		throws PortalException {

		checkDocumentRepository();

		return documentRepository.getFileEntry(folderId, title);
	}

	@Override
	public FileEntry getFileEntryByUuid(String uuid) throws PortalException {
		checkDocumentRepository();

		return documentRepository.getFileEntryByUuid(uuid);
	}

	@Override
	public FileShortcut getFileShortcut(long fileShortcutId)
		throws PortalException {

		checkDocumentRepository();

		return documentRepository.getFileShortcut(fileShortcutId);
	}

	@Override
	public FileVersion getFileVersion(long fileVersionId)
		throws PortalException {

		checkDocumentRepository();

		return documentRepository.getFileVersion(fileVersionId);
	}

	@Override
	public Folder getFolder(long folderId) throws PortalException {
		checkDocumentRepository();

		return documentRepository.getFolder(folderId);
	}

	@Override
	public Folder getFolder(long parentFolderId, String name)
		throws PortalException {

		checkDocumentRepository();

		return documentRepository.getFolder(parentFolderId, name);
	}

	@Override
	public List<Folder> getFolders(
			long parentFolderId, boolean includeMountFolders, int start,
			int end, OrderByComparator<Folder> obc)
		throws PortalException {

		return documentRepository.getFolders(
			parentFolderId, includeMountFolders, start, end, obc);
	}

	@Override
	public List<Folder> getFolders(
			long parentFolderId, int status, boolean includeMountFolders,
			int start, int end, OrderByComparator<Folder> obc)
		throws PortalException {

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
	public int getFoldersAndFileEntriesAndFileShortcutsCount(
			long folderId, int status, boolean includeMountFolders)
		throws PortalException {

		checkDocumentRepository();

		return documentRepository.getFoldersAndFileEntriesAndFileShortcutsCount(
			folderId, status, includeMountFolders);
	}

	@Override
	public int getFoldersCount(long parentFolderId, boolean includeMountfolders)
		throws PortalException {

		return documentRepository.getFoldersCount(
			parentFolderId, includeMountfolders);
	}

	@Override
	public int getFoldersCount(
			long parentFolderId, int status, boolean includeMountfolders)
		throws PortalException {

		return documentRepository.getFoldersCount(
			parentFolderId, status, includeMountfolders);
	}

	@Override
	public List<FileEntry> getRepositoryFileEntries(
			long userId, long rootFolderId, int start, int end,
			OrderByComparator<FileEntry> obc)
		throws PortalException {

		checkDocumentRepository();

		return documentRepository.getRepositoryFileEntries(
			userId, rootFolderId, start, end, obc);
	}

	@Override
	public long getRepositoryId() {
		checkDocumentRepository();

		return documentRepository.getRepositoryId();
	}

	@Override
	public <C extends Capability> boolean isCapabilityProvided(
		Class<C> capabilityClass) {

		checkDocumentRepository();

		return documentRepository.isCapabilityProvided(capabilityClass);
	}

	@Override
	public FileEntry moveFileEntry(
			long userId, long fileEntryId, long newFolderId,
			ServiceContext serviceContext)
		throws PortalException {

		checkDocumentRepository();

		return documentRepository.moveFileEntry(
			userId, fileEntryId, newFolderId, serviceContext);
	}

	@Override
	public Folder moveFolder(
			long userId, long folderId, long parentFolderId,
			ServiceContext serviceContext)
		throws PortalException {

		checkDocumentRepository();

		return documentRepository.moveFolder(
			userId, folderId, parentFolderId, serviceContext);
	}

	@Override
	public void revertFileEntry(
			long userId, long fileEntryId, String version,
			ServiceContext serviceContext)
		throws PortalException {

		checkDocumentRepository();

		documentRepository.revertFileEntry(
			userId, fileEntryId, version, serviceContext);
	}

	public void setDocumentRepository(T documentRepository) {
		if (this.documentRepository != null) {
			throw new IllegalStateException(
				"Unable to initialize an initialized document repository");
		}

		this.documentRepository = documentRepository;
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

		checkDocumentRepository();

		return documentRepository.updateFileEntry(
			userId, fileEntryId, sourceFileName, mimeType, title, description,
			changeLog, dlVersionNumberIncrease, file, serviceContext);
	}

	@Override
	public FileEntry updateFileEntry(
			long userId, long fileEntryId, String sourceFileName,
			String mimeType, String title, String description, String changeLog,
			DLVersionNumberIncrease dlVersionNumberIncrease, InputStream is,
			long size, ServiceContext serviceContext)
		throws PortalException {

		checkDocumentRepository();

		return documentRepository.updateFileEntry(
			userId, fileEntryId, sourceFileName, mimeType, title, description,
			changeLog, dlVersionNumberIncrease, is, size, serviceContext);
	}

	@Override
	public FileShortcut updateFileShortcut(
			long userId, long fileShortcutId, long folderId, long toFileEntryId,
			ServiceContext serviceContext)
		throws PortalException {

		checkDocumentRepository();

		return documentRepository.updateFileShortcut(
			userId, fileShortcutId, folderId, toFileEntryId, serviceContext);
	}

	@Override
	public void updateFileShortcuts(
			long oldToFileEntryId, long newToFileEntryId)
		throws PortalException {

		checkDocumentRepository();

		documentRepository.updateFileShortcuts(
			oldToFileEntryId, newToFileEntryId);
	}

	@Override
	public Folder updateFolder(
			long folderId, long parentFolderId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException {

		checkDocumentRepository();

		return documentRepository.updateFolder(
			folderId, parentFolderId, name, description, serviceContext);
	}

	protected void checkDocumentRepository() {
		if (documentRepository == null) {
			throw new IllegalStateException(
				"Document repositry is not initialized");
		}
	}

	protected T documentRepository;

}
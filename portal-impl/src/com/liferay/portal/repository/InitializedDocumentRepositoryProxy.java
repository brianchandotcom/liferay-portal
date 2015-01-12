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
import com.liferay.portal.kernel.repository.DocumentRepository;
import com.liferay.portal.kernel.repository.capabilities.Capability;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.service.ServiceContext;

import java.io.File;
import java.io.InputStream;

import java.util.List;

/**
 * @author Adolfo Pérez
 */
public class InitializedDocumentRepositoryProxy implements DocumentRepository {

	@Override
	public FileEntry addFileEntry(
			long userId, long folderId, String sourceFileName, String mimeType,
			String title, String description, String changeLog, File file,
			ServiceContext serviceContext)
		throws PortalException {

		_checkDocumentRepositoryInitialized();

		return _documentRepository.addFileEntry(
			userId, folderId, sourceFileName, mimeType, title, description,
			changeLog, file, serviceContext);
	}

	@Override
	public FileEntry addFileEntry(
			long userId, long folderId, String sourceFileName, String mimeType,
			String title, String description, String changeLog, InputStream is,
			long size, ServiceContext serviceContext)
		throws PortalException {

		_checkDocumentRepositoryInitialized();

		return _documentRepository.addFileEntry(
			userId, folderId, sourceFileName, mimeType, title, description,
			changeLog, is, size, serviceContext);
	}

	@Override
	public Folder addFolder(
			long userId, long parentFolderId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException {

		_checkDocumentRepositoryInitialized();

		return _documentRepository.addFolder(
			userId, parentFolderId, name, description, serviceContext);
	}

	@Override
	public void checkInFileEntry(
			long userId, long fileEntryId, boolean major, String changeLog,
			ServiceContext serviceContext)
		throws PortalException {

		_checkDocumentRepositoryInitialized();

		_documentRepository.checkInFileEntry(
			userId, fileEntryId, major, changeLog, serviceContext);
	}

	@Override
	public void checkInFileEntry(
			long userId, long fileEntryId, String lockUuid,
			ServiceContext serviceContext)
		throws PortalException {

		_checkDocumentRepositoryInitialized();

		_documentRepository.checkInFileEntry(
			userId, fileEntryId, lockUuid, serviceContext);
	}

	@Override
	public FileEntry copyFileEntry(
			long userId, long groupId, long fileEntryId, long destFolderId,
			ServiceContext serviceContext)
		throws PortalException {

		_checkDocumentRepositoryInitialized();

		return _documentRepository.copyFileEntry(
			userId, groupId, fileEntryId, destFolderId, serviceContext);
	}

	@Override
	public void deleteAll() throws PortalException {
		_checkDocumentRepositoryInitialized();

		_documentRepository.deleteAll();
	}

	@Override
	public void deleteFileEntry(long fileEntryId) throws PortalException {
		_checkDocumentRepositoryInitialized();

		_documentRepository.deleteFileEntry(fileEntryId);
	}

	@Override
	public void deleteFolder(long folderId) throws PortalException {
		_checkDocumentRepositoryInitialized();

		_documentRepository.deleteFolder(folderId);
	}

	@Override
	public <T extends Capability> T getCapability(Class<T> capabilityClass) {
		_checkDocumentRepositoryInitialized();

		return _documentRepository.getCapability(capabilityClass);
	}

	@Override
	public FileEntry getFileEntry(long fileEntryId) throws PortalException {
		_checkDocumentRepositoryInitialized();

		return _documentRepository.getFileEntry(fileEntryId);
	}

	@Override
	public FileEntry getFileEntry(long folderId, String title)
		throws PortalException {

		_checkDocumentRepositoryInitialized();

		return _documentRepository.getFileEntry(folderId, title);
	}

	@Override
	public FileEntry getFileEntryByUuid(String uuid) throws PortalException {
		_checkDocumentRepositoryInitialized();

		return _documentRepository.getFileEntryByUuid(uuid);
	}

	@Override
	public FileVersion getFileVersion(long fileVersionId)
		throws PortalException {

		_checkDocumentRepositoryInitialized();

		return _documentRepository.getFileVersion(fileVersionId);
	}

	@Override
	public Folder getFolder(long folderId) throws PortalException {
		_checkDocumentRepositoryInitialized();

		return _documentRepository.getFolder(folderId);
	}

	@Override
	public Folder getFolder(long parentFolderId, String name)
		throws PortalException {

		_checkDocumentRepositoryInitialized();

		return _documentRepository.getFolder(parentFolderId, name);
	}

	@Override
	public List<FileEntry> getRepositoryFileEntries(
			long userId, long rootFolderId, int start, int end,
			OrderByComparator<FileEntry> obc)
		throws PortalException {

		_checkDocumentRepositoryInitialized();

		return _documentRepository.getRepositoryFileEntries(
			userId, rootFolderId, start, end, obc);
	}

	@Override
	public long getRepositoryId() {
		_checkDocumentRepositoryInitialized();

		return _documentRepository.getRepositoryId();
	}

	@Override
	public <T extends Capability> boolean isCapabilityProvided(
		Class<T> capabilityClass) {

		_checkDocumentRepositoryInitialized();

		return _documentRepository.isCapabilityProvided(capabilityClass);
	}

	@Override
	public FileEntry moveFileEntry(
			long userId, long fileEntryId, long newFolderId,
			ServiceContext serviceContext)
		throws PortalException {

		_checkDocumentRepositoryInitialized();

		return _documentRepository.moveFileEntry(
			userId, fileEntryId, newFolderId, serviceContext);
	}

	@Override
	public Folder moveFolder(
			long userId, long folderId, long parentFolderId,
			ServiceContext serviceContext)
		throws PortalException {

		_checkDocumentRepositoryInitialized();

		return _documentRepository.moveFolder(
			userId, folderId, parentFolderId, serviceContext);
	}

	@Override
	public void revertFileEntry(
			long userId, long fileEntryId, String version,
			ServiceContext serviceContext)
		throws PortalException {

		_checkDocumentRepositoryInitialized();

		_documentRepository.revertFileEntry(
			userId, fileEntryId, version, serviceContext);
	}

	public void setDocumentRepository(DocumentRepository documentRepository) {
		if (_documentRepository != null) {
			throw new IllegalStateException(
				"Tried to initialize an already initialized repository proxy");
		}

		_documentRepository = documentRepository;
	}

	@Override
	public FileEntry updateFileEntry(
			long userId, long fileEntryId, String sourceFileName,
			String mimeType, String title, String description, String changeLog,
			boolean majorVersion, File file, ServiceContext serviceContext)
		throws PortalException {

		_checkDocumentRepositoryInitialized();

		return _documentRepository.updateFileEntry(
			userId, fileEntryId, sourceFileName, mimeType, title, description,
			changeLog, majorVersion, file, serviceContext);
	}

	@Override
	public FileEntry updateFileEntry(
			long userId, long fileEntryId, String sourceFileName,
			String mimeType, String title, String description, String changeLog,
			boolean majorVersion, InputStream is, long size,
			ServiceContext serviceContext)
		throws PortalException {

		_checkDocumentRepositoryInitialized();

		return _documentRepository.updateFileEntry(
			userId, fileEntryId, sourceFileName, mimeType, title, description,
			changeLog, majorVersion, is, size, serviceContext);
	}

	@Override
	public Folder updateFolder(
			long folderId, long parentFolderId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException {

		_checkDocumentRepositoryInitialized();

		return _documentRepository.updateFolder(
			folderId, parentFolderId, name, description, serviceContext);
	}

	private void _checkDocumentRepositoryInitialized() {
		if (_documentRepository == null) {
			throw new IllegalStateException(
				"Tried to use the repository proxy before it was fully " +
					"initialized");
		}
	}

	private DocumentRepository _documentRepository;

}
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
import com.liferay.portal.kernel.repository.capabilities.Capability;
import com.liferay.portal.kernel.repository.capabilities.TrashCapability;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.service.ServiceContext;

import java.io.File;
import java.io.InputStream;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Adolfo Pérez
 */
public class CapabilityLocalRepository
	extends BaseCapabilityRepository<LocalRepository>
	implements LocalRepository {

	public CapabilityLocalRepository(
		LocalRepository localRepository,
		Map<Class<? extends Capability>, Capability> supportedCapabilities,
		Set<Class<? extends Capability>> exportedCapabilityClasses) {

		super(
			localRepository, supportedCapabilities, exportedCapabilityClasses);
	}

	@Override
	public FileEntry addFileEntry(
			long userId, long folderId, String sourceFileName, String mimeType,
			String title, String description, String changeLog, File file,
			ServiceContext serviceContext)
		throws PortalException {

		return getRepository().addFileEntry(
			userId, folderId, sourceFileName, mimeType, title, description,
			changeLog, file, serviceContext);
	}

	@Override
	public FileEntry addFileEntry(
			long userId, long folderId, String sourceFileName, String mimeType,
			String title, String description, String changeLog, InputStream is,
			long size, ServiceContext serviceContext)
		throws PortalException {

		return getRepository().addFileEntry(
			userId, folderId, sourceFileName, mimeType, title, description,
			changeLog, is, size, serviceContext);
	}

	@Override
	public Folder addFolder(
			long userId, long parentFolderId, String title, String description,
			ServiceContext serviceContext)
		throws PortalException {

		return getRepository().addFolder(
			userId, parentFolderId, title, description, serviceContext);
	}

	@Override
	public void deleteAll() throws PortalException {
		getRepository().deleteAll();
	}

	@Override
	public void deleteFileEntry(long fileEntryId) throws PortalException {
		LocalRepository localRepository = getRepository();

		if (isCapabilityProvided(TrashCapability.class)) {
			TrashCapability trashCapability = getCapability(
				TrashCapability.class);

			FileEntry fileEntry = localRepository.getFileEntry(fileEntryId);

			trashCapability.deleteTrashEntry(fileEntry);
		}

		localRepository.deleteFileEntry(fileEntryId);
	}

	@Override
	public void deleteFolder(long folderId) throws PortalException {
		LocalRepository localRepository = getRepository();

		if (isCapabilityProvided(TrashCapability.class)) {
			TrashCapability trashCapability = getCapability(
				TrashCapability.class);

			Folder folder = localRepository.getFolder(folderId);

			trashCapability.deleteTrashEntry(folder);
		}

		localRepository.deleteFolder(folderId);
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
	public FileVersion getFileVersion(long fileVersionId)
		throws PortalException {

		return getRepository().getFileVersion(fileVersionId);
	}

	@Override
	public Folder getFolder(long folderId) throws PortalException {
		return getRepository().getFolder(folderId);
	}

	@Override
	public Folder getFolder(long parentFolderId, String title)
		throws PortalException {

		return getRepository().getFolder(parentFolderId, title);
	}

	@Override
	public List<FileEntry> getRepositoryFileEntries(
			long rootFolderId, int start, int end,
			OrderByComparator<FileEntry> obc)
		throws PortalException {

		return getRepository().getRepositoryFileEntries(
			rootFolderId, start, end, obc);
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

		return getRepository().moveFileEntry(
			userId, fileEntryId, newFolderId, serviceContext);
	}

	@Override
	public Folder moveFolder(
			long userId, long folderId, long parentFolderId,
			ServiceContext serviceContext)
		throws PortalException {

		return getRepository().moveFolder(
			userId, folderId, parentFolderId, serviceContext);
	}

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

		return getRepository().updateFileEntry(
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

		return getRepository().updateFileEntry(
			userId, fileEntryId, sourceFileName, mimeType, title, description,
			changeLog, majorVersion, is, size, serviceContext);
	}

	@Override
	public Folder updateFolder(
			long folderId, long parentFolderId, String title,
			String description, ServiceContext serviceContext)
		throws PortalException {

		return getRepository().updateFolder(
			folderId, parentFolderId, title, description, serviceContext);
	}

}
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
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.capabilities.Capability;
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
public class CapabilityLocalRepository implements LocalRepository {

	public CapabilityLocalRepository(
		LocalRepository localRepository,
		Map<Class<? extends Capability>, Capability> capabilityMap,
		Set<Class<? extends Capability>> exportedCapabilities) {

		Set<Class<? extends Capability>> supportedCababilities =
			capabilityMap.keySet();

		if (!supportedCababilities.containsAll(exportedCapabilities)) {
			throw new IllegalArgumentException(
				String.format(
					"Exporting capabilities not explicitly supported is not " +
					"allowed. Repository supports %s, but tried to export %s",
					capabilityMap.keySet(), exportedCapabilities));
		}

		_localRepository = localRepository;
		_capabilityMap = capabilityMap;
		_exportedCapabilities = exportedCapabilities;
	}

	@Override
	public FileEntry addFileEntry(
			long userId, long folderId, String sourceFileName, String mimeType,
			String title, String description, String changeLog, File file,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		return _localRepository.addFileEntry(
			userId, folderId, sourceFileName, mimeType, title, description,
			changeLog, file, serviceContext);
	}

	@Override
	public FileEntry addFileEntry(
			long userId, long folderId, String sourceFileName, String mimeType,
			String title, String description, String changeLog, InputStream is,
			long size, ServiceContext serviceContext)
		throws PortalException, SystemException {

		return _localRepository.addFileEntry(
			userId, folderId, sourceFileName, mimeType, title, description,
			changeLog, is, size, serviceContext);
	}

	@Override
	public Folder addFolder(
			long userId, long parentFolderId, String title, String description,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		return _localRepository.addFolder(
			userId, parentFolderId, title, description, serviceContext);
	}

	@Override
	public void deleteAll() throws PortalException, SystemException {
		_localRepository.deleteAll();
	}

	@Override
	public void deleteFileEntry(long fileEntryId)
		throws PortalException, SystemException {

		_localRepository.deleteFileEntry(fileEntryId);
	}

	@Override
	public void deleteFolder(long folderId)
		throws PortalException, SystemException {

		_localRepository.deleteFolder(folderId);
	}

	@Override
	public FileEntry getFileEntry(long fileEntryId)
		throws PortalException, SystemException {

		return _localRepository.getFileEntry(fileEntryId);
	}

	@Override
	public FileEntry getFileEntry(long folderId, String title)
		throws PortalException, SystemException {

		return _localRepository.getFileEntry(folderId, title);
	}

	@Override
	public FileEntry getFileEntryByUuid(String uuid)
		throws PortalException, SystemException {

		return _localRepository.getFileEntryByUuid(uuid);
	}

	@Override
	public FileVersion getFileVersion(long fileVersionId)
		throws PortalException, SystemException {

		return _localRepository.getFileVersion(fileVersionId);
	}

	@Override
	public Folder getFolder(long folderId)
		throws PortalException, SystemException {

		return _localRepository.getFolder(folderId);
	}

	@Override
	public Folder getFolder(long parentFolderId, String title)
		throws PortalException, SystemException {

		return _localRepository.getFolder(parentFolderId, title);
	}

	@Override
	public List<FileEntry> getRepositoryFileEntries(
			long rootFolderId, int start, int end, OrderByComparator obc)
		throws PortalException, SystemException {

		return _localRepository.getRepositoryFileEntries(
			rootFolderId, start, end, obc);
	}

	@Override
	public long getRepositoryId() {
		return _localRepository.getRepositoryId();
	}

	@Override
	public FileEntry moveFileEntry(
			long userId, long fileEntryId, long newFolderId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		return _localRepository.moveFileEntry(
			userId, fileEntryId, newFolderId, serviceContext);
	}

	@Override
	public Folder moveFolder(
			long userId, long folderId, long parentFolderId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		return _localRepository.moveFolder(
			userId, folderId, parentFolderId, serviceContext);
	}

	@Override
	public void updateAsset(
			long userId, FileEntry fileEntry, FileVersion fileVersion,
			long[] assetCategoryIds, String[] assetTagNames,
			long[] assetLinkEntryIds)
		throws PortalException, SystemException {

		_localRepository.updateAsset(
			userId, fileEntry, fileVersion, assetCategoryIds, assetTagNames,
			assetLinkEntryIds);
	}

	@Override
	public FileEntry updateFileEntry(
			long userId, long fileEntryId, String sourceFileName,
			String mimeType, String title, String description, String changeLog,
			boolean majorVersion, File file, ServiceContext serviceContext)
		throws PortalException, SystemException {

		return _localRepository.updateFileEntry(
			userId, fileEntryId, sourceFileName, mimeType, title, description,
			changeLog, majorVersion, file, serviceContext);
	}

	@Override
	public FileEntry updateFileEntry(
			long userId, long fileEntryId, String sourceFileName,
			String mimeType, String title, String description, String changeLog,
			boolean majorVersion, InputStream is, long size,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		return _localRepository.updateFileEntry(
			userId, fileEntryId, sourceFileName, mimeType, title, description,
			changeLog, majorVersion, is, size, serviceContext);
	}

	@Override
	public Folder updateFolder(
			long folderId, long parentFolderId, String title,
			String description, ServiceContext serviceContext)
		throws PortalException, SystemException {

		return _localRepository.updateFolder(
			folderId, parentFolderId, title, description, serviceContext);
	}

	protected <T extends Capability<T>> T getCapability(
		Class<T> capabilityClass) {

		Capability<?> capability = _capabilityMap.get(capabilityClass);

		if (capability == null) {
			throw new IllegalArgumentException(
				String.format(
					"Capability %s not supported by repository %d",
					capabilityClass.getName(), getRepositoryId()));
		}

		return (T)capability;
	}

	private final Map<Class<? extends Capability>, Capability>
		_capabilityMap;
	private final Set<Class<? extends Capability>> _exportedCapabilities;
	private final LocalRepository _localRepository;

}
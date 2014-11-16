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
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.service.ServiceContext;

import java.util.List;

/**
 * @author Alexander Chow
 */
public interface LocalRepository extends DocumentRepository {

	public Folder addFolder(
			long userId, long parentFolderId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException;

	public void deleteAll() throws PortalException;

	public void deleteFileEntry(long fileEntryId) throws PortalException;

	public void deleteFolder(long folderId) throws PortalException;

	public FileEntry getFileEntry(long fileEntryId) throws PortalException;

	public FileEntry getFileEntry(long folderId, String title)
		throws PortalException;

	public FileEntry getFileEntryByUuid(String uuid) throws PortalException;

	public FileVersion getFileVersion(long fileVersionId)
		throws PortalException;

	public Folder getFolder(long folderId) throws PortalException;

	public Folder getFolder(long parentFolderId, String name)
		throws PortalException;

	public List<FileEntry> getRepositoryFileEntries(
			long rootFolderId, int start, int end,
			OrderByComparator<FileEntry> obc)
		throws PortalException;

	public FileEntry moveFileEntry(
			long userId, long fileEntryId, long newFolderId,
			ServiceContext serviceContext)
		throws PortalException;

	public Folder moveFolder(
			long userId, long folderId, long parentFolderId,
			ServiceContext serviceContext)
		throws PortalException;

	public void updateAsset(
			long userId, FileEntry fileEntry, FileVersion fileVersion,
			long[] assetCategoryIds, String[] assetTagNames,
			long[] assetLinkEntryIds)
		throws PortalException;

	public Folder updateFolder(
			long folderId, long parentFolderId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException;

}
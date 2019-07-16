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

package com.liferay.portlet.documentlibrary.service.impl;

import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.RepositoryProviderUtil;
import com.liferay.portal.kernel.repository.capabilities.TrashCapability;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portlet.documentlibrary.service.base.DLTrashLocalServiceBaseImpl;

/**
 * @author Adolfo Pérez
 */
public class DLTrashLocalServiceImpl extends DLTrashLocalServiceBaseImpl {

	@Override
	public FileEntry moveFileEntryFromTrash(
			long userId, long repositoryId, long fileEntryId, long newFolderId,
			ServiceContext serviceContext)
		throws PortalException {

		LocalRepository localRepository =
			RepositoryProviderUtil.getLocalRepository(repositoryId);

		TrashCapability trashCapability = localRepository.getCapability(
			TrashCapability.class);

		FileEntry fileEntry = localRepository.getFileEntry(fileEntryId);

		Folder newFolder = null;

		if (newFolderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			newFolder = localRepository.getFolder(newFolderId);
		}

		return trashCapability.moveFileEntryFromTrash(
			userId, fileEntry, newFolder, serviceContext);
	}

	@Override
	public FileEntry moveFileEntryToTrash(
			long userId, long repositoryId, long fileEntryId)
		throws PortalException {

		LocalRepository localRepository =
			RepositoryProviderUtil.getLocalRepository(repositoryId);

		TrashCapability trashCapability = localRepository.getCapability(
			TrashCapability.class);

		return trashCapability.moveFileEntryToTrash(
			userId, localRepository.getFileEntry(fileEntryId));
	}

	@Override
	public void restoreFileEntryFromTrash(
			long userId, long repositoryId, long fileEntryId)
		throws PortalException {

		LocalRepository localRepository =
			RepositoryProviderUtil.getLocalRepository(repositoryId);

		TrashCapability trashCapability = localRepository.getCapability(
			TrashCapability.class);

		trashCapability.restoreFileEntryFromTrash(
			userId, localRepository.getFileEntry(fileEntryId));
	}

}
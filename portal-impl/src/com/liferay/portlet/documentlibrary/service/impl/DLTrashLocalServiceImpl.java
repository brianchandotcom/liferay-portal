/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

		Folder newFolder = null;

		if (newFolderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			newFolder = localRepository.getFolder(newFolderId);
		}

		return trashCapability.moveFileEntryFromTrash(
			userId, localRepository.getFileEntry(fileEntryId), newFolder,
			serviceContext);
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
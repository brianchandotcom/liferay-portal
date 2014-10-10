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

package com.liferay.portal.repository.util;

import com.liferay.portal.InvalidRepositoryException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.capabilities.TrashCapability;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.repository.util.RepositoryTrash;
import com.liferay.portal.service.RepositoryLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;

/**
 * @author Adolfo Pérez
 */
public class RepositoryTrashImpl implements RepositoryTrash {

	@Override
	public FileEntry moveFileEntryFromTrash(
			long userId, long repositoryId, long fileEntryId, long newFolderId,
			ServiceContext serviceContext)
		throws PortalException {

		LocalRepository localRepository =
			RepositoryLocalServiceUtil.getLocalRepositoryImpl(repositoryId);

		if (!localRepository.isCapabilityProvided(TrashCapability.class)) {
			throw new InvalidRepositoryException(
				"Repository " + localRepository.getRepositoryId() +
					" does not support trash operations");
		}

		TrashCapability trashCapability = localRepository.getCapability(
			TrashCapability.class);

		FileEntry fileEntry = localRepository.getFileEntry(fileEntryId);
		Folder destinationFolder = localRepository.getFolder(newFolderId);

		return trashCapability.moveFileEntryFromTrash(
			userId, fileEntry, destinationFolder, serviceContext);
	}

}
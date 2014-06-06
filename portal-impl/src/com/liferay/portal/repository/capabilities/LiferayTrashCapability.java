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
import com.liferay.portal.kernel.repository.capabilities.TrashCapability;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.service.ServiceContext;

/**
 * @author Adolfo Pérez
 */
public class LiferayTrashCapability implements TrashCapability {

	@Override
	public void deleteFileEntry(FileEntry fileEntry) throws PortalException {
		throw new UnsupportedOperationException("Not Implemented");
	}

	@Override
	public void deleteFolder(Folder folder) throws PortalException {
		throw new UnsupportedOperationException("Not Implemented");
	}

	@Override
	public FileEntry moveFileEntryFromTrash(
			long userId, FileEntry fileEntry, Folder destinationFolder,
			ServiceContext serviceContext)
		throws PortalException {

		throw new UnsupportedOperationException("Not Implemented");
	}

	@Override
	public FileEntry moveFileEntryToTrash(long userId, FileEntry fileEntry)
		throws PortalException {

		throw new UnsupportedOperationException("Not implemented");
	}

	@Override
	public Folder moveFolderFromTrash(
			long userId, Folder folder, Folder destinationFolder,
			ServiceContext serviceContext)
		throws PortalException {

		throw new UnsupportedOperationException("Not implemented");
	}

	@Override
	public Folder moveFolderToTrash(long userId, Folder folder)
		throws PortalException {

		throw new UnsupportedOperationException("Not implemented");
	}

	@Override
	public void restoreFileEntryFromTrash(long userId, FileEntry fileEntry)
		throws PortalException {

		throw new UnsupportedOperationException("Not implemented");
	}

	@Override
	public void restoreFolderFromTrash(long userId, Folder folder)
		throws PortalException {

		throw new UnsupportedOperationException("Not implemented");
	}

}
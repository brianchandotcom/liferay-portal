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

package com.liferay.portal.kernel.repository.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.service.ServiceContext;

/**
 * @author Adolfo Pérez
 */
public class RepositoryTrashUtil {

	public static RepositoryTrash getRepositoryTrash() {
		PortalRuntimePermission.checkGetBeanProperty(RepositoryTrashUtil.class);

		return _repositoryTrash;
	}

	public static FileEntry moveFileEntryFromTrash(
			long userId, long repositoryId, long fileEntryId, long newFolderId,
			ServiceContext serviceContext)
		throws PortalException {

		return getRepositoryTrash().moveFileEntryFromTrash(
			userId, repositoryId, fileEntryId, newFolderId, serviceContext);
	}

	public static FileEntry moveFileEntryToTrash(
			long userId, long repositoryId, long fileEntryId)
		throws PortalException {

		return getRepositoryTrash().moveFileEntryToTrash(
			userId, repositoryId, fileEntryId);
	}

	public static void restoreFileEntryFromTrash(
			long userId, long repositoryId, long fileEntryId)
		throws PortalException {

		getRepositoryTrash().restoreFileEntryFromTrash(
			userId, repositoryId, fileEntryId);
	}

	public void setRepositoryTrash(RepositoryTrash repositoryTrash) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_repositoryTrash = repositoryTrash;
	}

	private static RepositoryTrash _repositoryTrash;

}
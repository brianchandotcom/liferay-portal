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

package com.liferay.portlet.documentlibrary.lar;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.repository.liferayrepository.model.LiferayFolder;
import com.liferay.portal.repository.liferayrepository.util.LiferayBase;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.persistence.DLFolderUtil;
import com.liferay.portlet.documentlibrary.util.RepositoryModelUtil;

import java.util.List;

/**
 * @author Alexander Chow
 */
public class FolderUtil extends LiferayBase {

	public static Folder fetchByR_P_N(
			long groupId, long parentFolderId, String name)
		throws SystemException {

		DLFolder dlFolder = DLFolderUtil.fetchByG_P_N(
			groupId, parentFolderId, name);

		if (dlFolder == null) {
			return null;
		}

		return new LiferayFolder(dlFolder);
	}

	public static Folder fetchByUUID_R(String uuid, long repositoryId)
		throws SystemException {

		DLFolder dlFolder = DLFolderUtil.fetchByUUID_G(uuid, repositoryId);

		if (dlFolder == null) {
			return null;
		}

		return new LiferayFolder(dlFolder);
	}

	public static Folder findByPrimaryKey(long folderId)
		throws NoSuchFolderException, SystemException {

		DLFolder dlFolder = DLFolderUtil.findByPrimaryKey(folderId);

		return new LiferayFolder(dlFolder);
	}

	public static List<Folder> findByR_P(long repositoryId, long parentFolderId)
		throws SystemException {

		List<DLFolder> dlFolders = DLFolderUtil.findByG_P(
			repositoryId, parentFolderId);

		return RepositoryModelUtil.toFolders(dlFolders);
	}

	public static List<Folder> findByRepositoryId(long repositoryId)
		throws SystemException {

		List<DLFolder> dlFolders = DLFolderUtil.findByGroupId(repositoryId);

		return RepositoryModelUtil.toFolders(dlFolders);
	}

}
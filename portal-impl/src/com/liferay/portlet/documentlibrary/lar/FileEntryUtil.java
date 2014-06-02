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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileEntry;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileEntryUtil;
import com.liferay.portlet.documentlibrary.store.DLStoreUtil;
import com.liferay.portlet.documentlibrary.util.RepositoryModelUtil;

import java.io.InputStream;

import java.util.List;

/**
 * @author Alexander Chow
 */
public class FileEntryUtil {

	public static FileEntry fetchByPrimaryKey(long fileEntryId)
		throws SystemException {

		DLFileEntry dlFileEntry = DLFileEntryUtil.fetchByPrimaryKey(
			fileEntryId);

		if (dlFileEntry == null) {
			return null;
		}

		return new LiferayFileEntry(dlFileEntry);
	}

	public static FileEntry fetchByR_F_T(
			long repositoryId, long folderId, String title)
		throws SystemException {

		DLFileEntry dlFileEntry = DLFileEntryUtil.fetchByG_F_T(
			repositoryId, folderId, title);

		if (dlFileEntry == null) {
			return null;
		}

		return new LiferayFileEntry(dlFileEntry);
	}

	public static FileEntry fetchByUUID_R(String uuid, long repositoryId)
		throws SystemException {

		DLFileEntry dlFileEntry = DLFileEntryUtil.fetchByUUID_G(
			uuid, repositoryId);

		if (dlFileEntry == null) {
			return null;
		}

		return new LiferayFileEntry(dlFileEntry);
	}

	/**
	 * @deprecated As of 7.0.0
	 */
	@Deprecated
	public static List<FileEntry> findByR_F(long repositoryId, long folderId)
		throws SystemException {

		List<DLFileEntry> dlFileEntries = DLFileEntryUtil.findByG_F(
			repositoryId, folderId);

		return RepositoryModelUtil.toFileEntries(dlFileEntries);
	}

	/**
	 * @deprecated As of 7.0.0
	 */
	@Deprecated
	public static FileEntry findByR_F_T(
			long repositoryId, long folderId, String title)
		throws NoSuchFileEntryException, SystemException {

		DLFileEntry dlFileEntry = DLFileEntryUtil.findByG_F_T(
			repositoryId, folderId, title);

		return new LiferayFileEntry(dlFileEntry);
	}

	public static InputStream getContentStream(FileEntry fileEntry)
		throws PortalException, SystemException {

		long repositoryId = DLFolderConstants.getDataRepositoryId(
			fileEntry.getRepositoryId(), fileEntry.getFolderId());

		String name = ((DLFileEntry)fileEntry.getModel()).getName();

		InputStream is = DLStoreUtil.getFileAsStream(
			fileEntry.getCompanyId(), repositoryId, name,
			fileEntry.getVersion());

		if (is == null) {
			is = new UnsyncByteArrayInputStream(new byte[0]);
		}

		return is;
	}

}
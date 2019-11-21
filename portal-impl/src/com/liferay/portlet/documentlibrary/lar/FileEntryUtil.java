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

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLFileEntryLocalServiceUtil;
import com.liferay.document.library.kernel.store.DLStoreUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileEntry;

import java.io.InputStream;

/**
 * @author Alexander Chow
 */
public class FileEntryUtil {

	public static FileEntry fetchByPrimaryKey(long fileEntryId) {
		DLFileEntry dlFileEntry = DLFileEntryLocalServiceUtil.fetchDLFileEntry(
			fileEntryId);

		if (dlFileEntry == null) {
			return null;
		}

		return new LiferayFileEntry(dlFileEntry);
	}

	public static FileEntry fetchByR_F_FN(
		long repositoryId, long folderId, String fileName) {

		DLFileEntry dlFileEntry =
			DLFileEntryLocalServiceUtil.fetchFileEntryByFileName(
				repositoryId, folderId, fileName);

		if (dlFileEntry == null) {
			return null;
		}

		return new LiferayFileEntry(dlFileEntry);
	}

	public static FileEntry fetchByR_F_T(
		long repositoryId, long folderId, String title) {

		DLFileEntry dlFileEntry = DLFileEntryLocalServiceUtil.fetchFileEntry(
			repositoryId, folderId, title);

		if (dlFileEntry == null) {
			return null;
		}

		return new LiferayFileEntry(dlFileEntry);
	}

	public static FileEntry fetchByUUID_R(String uuid, long repositoryId) {
		DLFileEntry dlFileEntry = DLFileEntryLocalServiceUtil.fetchFileEntry(
			uuid, repositoryId);

		if (dlFileEntry == null) {
			return null;
		}

		return new LiferayFileEntry(dlFileEntry);
	}

	public static InputStream getContentStream(FileEntry fileEntry)
		throws PortalException {

		long repositoryId = DLFolderConstants.getDataRepositoryId(
			fileEntry.getRepositoryId(), fileEntry.getFolderId());

		DLFileEntry dlFileEntry = (DLFileEntry)fileEntry.getModel();

		InputStream is = DLStoreUtil.getFileAsStream(
			fileEntry.getCompanyId(), repositoryId, dlFileEntry.getName(),
			fileEntry.getVersion());

		if (is == null) {
			is = new UnsyncByteArrayInputStream(new byte[0]);
		}

		return is;
	}

}
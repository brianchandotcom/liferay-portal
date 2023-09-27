/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portlet.documentlibrary.lar;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileVersion;
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

		DLFileVersion dlFileVersion = dlFileEntry.getFileVersion();

		InputStream inputStream = DLStoreUtil.getFileAsStream(
			fileEntry.getCompanyId(), repositoryId, dlFileEntry.getName(),
			dlFileVersion.getStoreFileName());

		if (inputStream == null) {
			inputStream = new UnsyncByteArrayInputStream(new byte[0]);
		}

		return inputStream;
	}

}
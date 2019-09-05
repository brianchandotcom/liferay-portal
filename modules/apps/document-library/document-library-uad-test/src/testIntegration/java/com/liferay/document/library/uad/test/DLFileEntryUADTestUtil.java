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

package com.liferay.document.library.uad.test;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.document.library.kernel.service.DLFolderLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestDataConstants;
import com.liferay.portal.kernel.util.ContentTypes;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import java.util.List;

/**
 * @author William Newbury
 */
public class DLFileEntryUADTestUtil {

	public static DLFileEntry addDLFileEntry(
			DLAppLocalService dlAppLocalService,
			DLFileEntryLocalService dlFileEntryLocalService,
			DLFolderLocalService dlFolderLocalService, long userId,
			long groupId)
		throws Exception {

		DLFolder dlFolder = dlFolderLocalService.addFolder(
			userId, groupId, groupId, false, 0L, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), false,
			ServiceContextTestUtil.getServiceContext());

		return addDLFileEntry(
			dlAppLocalService, dlFileEntryLocalService, dlFolder.getFolderId(),
			userId, groupId);
	}

	public static DLFileEntry addDLFileEntry(
			DLAppLocalService dlAppLocalService,
			DLFileEntryLocalService dlFileEntryLocalService, long dlFolderId,
			long userId, long groupId)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		byte[] bytes = TestDataConstants.TEST_BYTE_ARRAY;

		InputStream is = new ByteArrayInputStream(bytes);

		FileEntry fileEntry = dlAppLocalService.addFileEntry(
			userId, groupId, dlFolderId, RandomTestUtil.randomString(),
			ContentTypes.TEXT_PLAIN, RandomTestUtil.randomString(),
			StringPool.BLANK, StringPool.BLANK, is, bytes.length,
			serviceContext);

		return dlFileEntryLocalService.getFileEntry(fileEntry.getFileEntryId());
	}

	public static void cleanUpDependencies(
			DLAppLocalService dlAppLocalService,
			DLFileEntryLocalService dlFileEntryLocalService,
			DLFolderLocalService dlFolderLocalService,
			List<DLFileEntry> dlFileEntries)
		throws Exception {

		for (DLFileEntry dlFileEntry : dlFileEntries) {
			DLFileEntry existingDLFileEntry =
				dlFileEntryLocalService.fetchDLFileEntry(
					dlFileEntry.getFileEntryId());

			if (existingDLFileEntry != null) {
				dlAppLocalService.deleteFileEntry(dlFileEntry.getFileEntryId());
			}

			long dlFolderId = dlFileEntry.getFolderId();

			if (dlFolderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
				dlFolderLocalService.deleteFolder(dlFileEntry.getFolderId());
			}
		}
	}

}
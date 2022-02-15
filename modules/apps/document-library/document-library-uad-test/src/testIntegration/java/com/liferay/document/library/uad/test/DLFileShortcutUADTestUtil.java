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
import com.liferay.document.library.kernel.model.DLFileEntryTypeConstants;
import com.liferay.document.library.kernel.model.DLFileShortcut;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.document.library.kernel.service.DLFileShortcutLocalService;
import com.liferay.document.library.kernel.service.DLFolderLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.constants.TestDataConstants;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class DLFileShortcutUADTestUtil {

	public static DLFileShortcut addDLFileShortcut(
			DLFileEntryLocalService dlFileEntryLocalService,
			DLFileShortcutLocalService dlFileShortcutLocalService,
			DLFolderLocalService dlFolderLocalService, long userId,
			long groupId)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		DLFolder dlFolder = dlFolderLocalService.addFolder(
			userId, groupId, groupId, false, 0L, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), false, serviceContext);

		byte[] bytes = TestDataConstants.TEST_BYTE_ARRAY;

		InputStream inputStream = new ByteArrayInputStream(bytes);

		DLFileEntry dlFileEntry = dlFileEntryLocalService.addFileEntry(
			null, userId, dlFolder.getGroupId(), dlFolder.getRepositoryId(),
			dlFolder.getFolderId(), RandomTestUtil.randomString(),
			ContentTypes.TEXT_PLAIN, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), StringPool.BLANK, StringPool.BLANK,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT, null,
			null, inputStream, bytes.length, null, null, serviceContext);

		return dlFileShortcutLocalService.addFileShortcut(
			userId, groupId, groupId, dlFolder.getFolderId(),
			dlFileEntry.getFileEntryId(), serviceContext);
	}

	public static DLFileShortcut addDLFileShortcutWithStatusByUserId(
			DLFileEntryLocalService dlFileEntryLocalService,
			DLFileShortcutLocalService dlFileShortcutLocalService,
			DLFolderLocalService dlFolderLocalService, long userId,
			long groupId, long statusByUserId)
		throws Exception {

		DLFileShortcut dlFileShortcut = addDLFileShortcut(
			dlFileEntryLocalService, dlFileShortcutLocalService,
			dlFolderLocalService, userId, groupId);

		return dlFileShortcutLocalService.updateStatus(
			statusByUserId, dlFileShortcut.getFileShortcutId(),
			WorkflowConstants.STATUS_DRAFT,
			ServiceContextTestUtil.getServiceContext());
	}

	public static void cleanUpDependencies(
			DLFileEntryLocalService dlFileEntryLocalService,
			DLFolderLocalService dlFolderLocalService,
			List<DLFileShortcut> dlFileShortcuts)
		throws Exception {

		for (DLFileShortcut dlFileShortcut : dlFileShortcuts) {
			dlFileEntryLocalService.deleteFileEntry(
				dlFileShortcut.getToFileEntryId());

			dlFolderLocalService.deleteFolder(dlFileShortcut.getFolderId());
		}
	}

}
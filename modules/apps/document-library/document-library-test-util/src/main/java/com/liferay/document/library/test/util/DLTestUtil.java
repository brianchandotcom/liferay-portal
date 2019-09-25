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

package com.liferay.document.library.test.util;

import com.liferay.document.library.kernel.exception.NoSuchFolderException;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryTypeConstants;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLFileEntryLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLFolderLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestDataConstants;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;

import java.io.ByteArrayInputStream;

/**
 * @author Adolfo Pérez
 */
public class DLTestUtil {

	public static DLFileEntry addDLFileEntry(long dlFolderId) throws Exception {
		DLFolder dlFolder = DLFolderLocalServiceUtil.fetchDLFolder(dlFolderId);

		byte[] bytes = TestDataConstants.TEST_BYTE_ARRAY;

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(dlFolder.getGroupId());

		return DLFileEntryLocalServiceUtil.addFileEntry(
			TestPropsValues.getUserId(), dlFolder.getGroupId(),
			dlFolder.getRepositoryId(), dlFolder.getFolderId(),
			RandomTestUtil.randomString(), ContentTypes.TEXT_PLAIN,
			RandomTestUtil.randomString(), StringPool.BLANK, StringPool.BLANK,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT, null,
			null, new ByteArrayInputStream(bytes), bytes.length,
			serviceContext);
	}

	public static DLFolder addDLFolder(long groupId) throws Exception {
		return addDLFolder(
			groupId, ServiceContextTestUtil.getServiceContext(groupId));
	}

	public static DLFolder addDLFolder(
			long groupId, boolean deleteExisting, ServiceContext serviceContext)
		throws Exception {

		return addDLFolder(
			groupId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, deleteExisting,
			serviceContext);
	}

	public static DLFolder addDLFolder(
			long groupId, long parentFolderId, boolean deleteExisting,
			ServiceContext serviceContext)
		throws Exception {

		String name = RandomTestUtil.randomString();

		if (deleteExisting) {
			try {
				DLFolder folder = DLFolderLocalServiceUtil.getFolder(
					groupId, parentFolderId, name);

				DLFolderLocalServiceUtil.deleteFolder(folder.getFolderId());
			}
			catch (NoSuchFolderException nsfe) {
			}
		}

		return DLFolderLocalServiceUtil.addFolder(
			TestPropsValues.getUserId(), groupId, groupId, false,
			parentFolderId, name, StringPool.BLANK, false, serviceContext);
	}

	public static DLFolder addDLFolder(
			long groupId, ServiceContext serviceContext)
		throws Exception {

		return addDLFolder(groupId, true, serviceContext);
	}

}
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

package com.liferay.portlet.documentlibrary.util.test;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.test.RandomTestUtil;
import com.liferay.portal.util.test.ServiceContextTestUtil;
import com.liferay.portal.util.test.TestPropsValues;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryTypeConstants;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * @author Adolfo Pérez
 */
public class DLTestUtil {

	public static DLFileEntry addFileEntry(DLFolder folder) throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(folder.getGroupId());

		byte[] bytes = RandomTestUtil.randomBytes();

		InputStream is = new ByteArrayInputStream(bytes);

		return DLFileEntryLocalServiceUtil.addFileEntry(
			TestPropsValues.getUserId(), folder.getGroupId(),
			folder.getRepositoryId(), folder.getFolderId(),
			RandomTestUtil.randomString(), "text/plain",
			RandomTestUtil.randomString(), StringPool.BLANK, StringPool.BLANK,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT, null,
			null, is, bytes.length, serviceContext);
	}

	public static DLFolder addFolder(long groupId) throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		return addFolder(groupId, serviceContext);
	}

	public static DLFolder addFolder(
			long groupId, ServiceContext serviceContext)
		throws Exception {

		String name = RandomTestUtil.randomString();

		try {
			DLFolder folder = DLFolderLocalServiceUtil.getFolder(
				groupId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, name);

			DLFolderLocalServiceUtil.deleteFolder(folder.getFolderId());
		}
		catch (NoSuchFolderException nsfe) {
		}

		return DLFolderLocalServiceUtil.addFolder(
			TestPropsValues.getUserId(), groupId, groupId, false,
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, name, StringPool.BLANK,
			false, serviceContext);
	}

}
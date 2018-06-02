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

package com.liferay.portal.search.test.documentlibrary.util;

import com.liferay.document.library.kernel.model.DLFileEntryTypeConstants;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.document.library.kernel.service.DLFolderLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

import java.io.ByteArrayInputStream;

import java.util.ArrayList;
import java.util.List;

/**
 * @author André de Oliveira
 */
public class DLFolderSearchFixture {

	public DLFolderSearchFixture(
		DLFolderLocalService dlFolderLocalService1,
		DLFileEntryLocalService dlFileEntryLocalService1) {

		dlFolderLocalService = dlFolderLocalService1;
		dlFileEntryLocalService = dlFileEntryLocalService1;
	}

	public DLFolder addDLFolderAndDLFileEntry(
			Group group, User user, String keywords, String content,
			ServiceContext serviceContext)
		throws Exception {

		DLFolder dlFolder = dlFolderLocalService.addFolder(
			user.getUserId(), group.getGroupId(), group.getGroupId(), false,
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, keywords, keywords,
			false, serviceContext);

		dlFileEntryLocalService.addFileEntry(
			user.getUserId(), group.getGroupId(), group.getGroupId(),
			dlFolder.getFolderId(), keywords, null,
			RandomTestUtil.randomString(), keywords, StringPool.BLANK,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT, null,
			null, new ByteArrayInputStream(content.getBytes()), 0,
			serviceContext);

		_dlFolders.add(dlFolder);

		return dlFolder;
	}

	public List<DLFolder> getDLFolders() {
		return _dlFolders;
	}

	public void setUp() {
	}

	public void tearDown() throws Exception {
		for (DLFolder folder : _dlFolders) {
			dlFolderLocalService.deleteAllByRepository(
				folder.getRepositoryId());
		}
	}

	protected DLFileEntryLocalService dlFileEntryLocalService;
	protected DLFolderLocalService dlFolderLocalService;

	private final List<DLFolder> _dlFolders = new ArrayList<>();

}
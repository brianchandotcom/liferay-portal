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
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.document.library.kernel.service.DLFolderLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestDataConstants;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author William Newbury
 */
@Component(immediate = true, service = DLFileEntryUADTestHelper.class)
public class DLFileEntryUADTestHelper {

	public DLFileEntry addDLFileEntry(long userId) throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		DLFolder dlFolder = _dlFolderLocalService.addFolder(
			userId, TestPropsValues.getGroupId(), TestPropsValues.getGroupId(),
			false, 0L, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), false, serviceContext);

		byte[] bytes = TestDataConstants.TEST_BYTE_ARRAY;
		long folderId = dlFolder.getFolderId();
		long repositoryId = dlFolder.getRepositoryId();
		String changeLog = StringPool.BLANK;
		String description = StringPool.BLANK;
		String mimeType = ContentTypes.TEXT_PLAIN;
		String sourceFileName = RandomTestUtil.randomString();
		String title = RandomTestUtil.randomString();

		InputStream is = new ByteArrayInputStream(bytes);

		FileEntry fileEntry = _dlAppLocalService.addFileEntry(
			userId, repositoryId, folderId, sourceFileName, mimeType, title,
			description, changeLog, is, bytes.length, serviceContext);

		return _dlFileEntryLocalService.getFileEntry(
			fileEntry.getFileEntryId());
	}

	public void cleanUpDependencies(List<DLFileEntry> dlFileEntries)
		throws Exception {

		for (DLFileEntry dlFileEntry : dlFileEntries) {
			if (_dlFileEntryLocalService.fetchDLFileEntry(
					dlFileEntry.getFileEntryId()) != null) {

				_dlAppLocalService.deleteFileEntry(
					dlFileEntry.getFileEntryId());
			}

			_dlFolderLocalService.deleteFolder(dlFileEntry.getFolderId());
		}
	}

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private DLFileEntryLocalService _dlFileEntryLocalService;

	@Reference
	private DLFolderLocalService _dlFolderLocalService;

}
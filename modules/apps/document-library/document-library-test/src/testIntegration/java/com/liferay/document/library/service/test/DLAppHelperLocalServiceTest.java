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

package com.liferay.document.library.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppHelperLocalService;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.File;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Adolfo Pérez
 */
@RunWith(Arquillian.class)
public class DLAppHelperLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testMoveFileEntryFromTrashRestoresFileEntryContent()
		throws Exception {

		String content = StringUtil.randomString();

		FileEntry fileEntry = _dlAppLocalService.addFileEntry(
			null, TestPropsValues.getUserId(), TestPropsValues.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString() + ".txt", ContentTypes.TEXT_PLAIN,
			content.getBytes(), null, null,
			ServiceContextTestUtil.getServiceContext());

		_dlAppHelperLocalService.moveFileEntryToTrash(
			TestPropsValues.getUserId(),
			_dlAppLocalService.getFileEntry(fileEntry.getFileEntryId()));

		_dlAppHelperLocalService.moveFileEntryFromTrash(
			TestPropsValues.getUserId(),
			_dlAppLocalService.getFileEntry(fileEntry.getFileEntryId()),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			ServiceContextTestUtil.getServiceContext());

		FileEntry restoredFileEntry = _dlAppLocalService.getFileEntry(
			fileEntry.getFileEntryId());

		Assert.assertArrayEquals(
			content.getBytes(),
			_file.getBytes(restoredFileEntry.getContentStream()));
	}

	@Test
	public void testRestoreFileEntryFromTrashRestoresFileEntryContent()
		throws Exception {

		String content = StringUtil.randomString();

		FileEntry fileEntry = _dlAppLocalService.addFileEntry(
			null, TestPropsValues.getUserId(), TestPropsValues.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString() + ".txt", ContentTypes.TEXT_PLAIN,
			content.getBytes(), null, null,
			ServiceContextTestUtil.getServiceContext());

		_dlAppHelperLocalService.moveFileEntryToTrash(
			TestPropsValues.getUserId(),
			_dlAppLocalService.getFileEntry(fileEntry.getFileEntryId()));

		_dlAppHelperLocalService.restoreFileEntryFromTrash(
			TestPropsValues.getUserId(),
			_dlAppLocalService.getFileEntry(fileEntry.getFileEntryId()));

		FileEntry restoredFileEntry = _dlAppLocalService.getFileEntry(
			fileEntry.getFileEntryId());

		Assert.assertArrayEquals(
			content.getBytes(),
			_file.getBytes(restoredFileEntry.getContentStream()));
	}

	@Inject
	private DLAppHelperLocalService _dlAppHelperLocalService;

	@Inject
	private DLAppLocalService _dlAppLocalService;

	@Inject
	private File _file;

}
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
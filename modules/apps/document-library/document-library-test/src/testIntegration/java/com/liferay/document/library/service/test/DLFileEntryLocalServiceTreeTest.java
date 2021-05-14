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
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.document.library.kernel.service.DLFileEntryLocalServiceUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.constants.TestDataConstants;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Shinn Lok
 * @author Sergio González
 */
@RunWith(Arquillian.class)
public class DLFileEntryLocalServiceTreeTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testFileEntryTreePathWhenMovingSubfolderWithFileEntry()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		Folder folderA = DLAppServiceUtil.addFolder(
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			"Folder A", RandomTestUtil.randomString(), serviceContext);

		Folder folderAA = DLAppServiceUtil.addFolder(
			_group.getGroupId(), folderA.getFolderId(), "Folder AA",
			RandomTestUtil.randomString(), serviceContext);

		FileEntry fileEntry = addFileEntry(folderAA.getFolderId(), "Entry.txt");

		DLAppLocalServiceUtil.moveFolder(
			TestPropsValues.getUserId(), folderAA.getFolderId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, serviceContext);

		DLFileEntry dlFileEntry = DLFileEntryLocalServiceUtil.getDLFileEntry(
			fileEntry.getFileEntryId());

		Assert.assertEquals(
			dlFileEntry.buildTreePath(), dlFileEntry.getTreePath());
	}

	@Test
	public void testRebuildTree() throws Exception {
		List<FileEntry> fileEntries = createTree();

		for (FileEntry fileEntry : fileEntries) {
			DLFileEntry dlFileEntry = DLFileEntryLocalServiceUtil.getFileEntry(
				fileEntry.getFileEntryId());

			dlFileEntry.setTreePath("/0/");

			DLFileEntryLocalServiceUtil.updateDLFileEntry(dlFileEntry);
		}

		DLFileEntryLocalServiceUtil.rebuildTree(TestPropsValues.getCompanyId());

		for (FileEntry fileEntry : fileEntries) {
			DLFileEntry dlFileEntry = DLFileEntryLocalServiceUtil.getFileEntry(
				fileEntry.getFileEntryId());

			Assert.assertEquals(
				dlFileEntry.buildTreePath(), dlFileEntry.getTreePath());
		}
	}

	protected FileEntry addFileEntry(long folderId, String sourceFileName)
		throws Exception {

		return DLAppLocalServiceUtil.addFileEntry(
			null, TestPropsValues.getUserId(), _group.getGroupId(), folderId,
			sourceFileName, ContentTypes.TEXT_PLAIN,
			TestDataConstants.TEST_BYTE_ARRAY, null, null,
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId()));
	}

	protected List<FileEntry> createTree() throws Exception {
		List<FileEntry> fileEntries = new ArrayList<>();

		FileEntry fileEntryA = addFileEntry(
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, "Entry A.txt");

		fileEntries.add(fileEntryA);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		Folder folder = DLAppServiceUtil.addFolder(
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			"Folder A", RandomTestUtil.randomString(), serviceContext);

		FileEntry fileEntryAA = addFileEntry(
			folder.getFolderId(), "Entry AA.txt");

		fileEntries.add(fileEntryAA);

		return fileEntries;
	}

	@DeleteAfterTestRun
	private Group _group;

}
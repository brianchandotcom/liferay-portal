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

package com.liferay.portlet.documentlibrary.service;

import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portlet.documentlibrary.model.DLFileEntryConstants;
import com.liferay.portlet.documentlibrary.model.DLFileEntryTypeConstants;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.util.test.DLAppTestUtil;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Shinn Lok
 */
public class DLFileVersionLocalServiceTreeTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testFileVersionTreePathWhenMovingSubfolderWithFileVersion()
		throws Exception {

		Folder folderA = DLAppTestUtil.addFolder(
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			"Folder A");

		Folder folderAA = DLAppTestUtil.addFolder(
			_group.getGroupId(), folderA.getFolderId(), "Folder AA");

		FileEntry fileEntry = addFileEntry(
			folderAA.getFolderId(), "Entry.txt");

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		DLAppLocalServiceUtil.moveFolder(
			TestPropsValues.getUserId(), folderAA.getFolderId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, serviceContext);

		DLFileVersion dlFileVersion =
			DLFileVersionLocalServiceUtil.getFileVersion(
				fileEntry.getFileEntryId(),
				DLFileEntryConstants.VERSION_DEFAULT);

		Assert.assertEquals(
			dlFileVersion.buildTreePath(), dlFileVersion.getTreePath());
	}

	@Test
	public void testRebuildTree() throws Exception {
		createTree();

		for (FileEntry fileEntry : _fileEntries) {
			DLFileVersion dlFileVersion =
				DLFileVersionLocalServiceUtil.getFileVersion(
					fileEntry.getFileEntryId(),
					DLFileEntryConstants.VERSION_DEFAULT);

			dlFileVersion.setTreePath(null);

			DLFileVersionLocalServiceUtil.updateDLFileVersion(dlFileVersion);
		}

		DLFileVersionLocalServiceUtil.rebuildTree(
			TestPropsValues.getCompanyId());

		for (FileEntry fileEntry : _fileEntries) {
			DLFileVersion dlFileVersion =
				DLFileVersionLocalServiceUtil.getFileVersion(
					fileEntry.getFileEntryId(),
					DLFileEntryConstants.VERSION_DEFAULT);

			Assert.assertEquals(
				dlFileVersion.buildTreePath(), dlFileVersion.getTreePath());
		}
	}

	protected FileEntry addFileEntry(
			long folderId, String sourceFileName)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		DLAppTestUtil.populateServiceContext(
			serviceContext, Constants.ADD,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_ALL, true);

		return DLAppLocalServiceUtil.addFileEntry(
			TestPropsValues.getUserId(), _group.getGroupId(), folderId,
			sourceFileName, ContentTypes.TEXT_PLAIN,
			RandomTestUtil.randomString().getBytes(), serviceContext);
	}

	protected void createTree() throws Exception {
		FileEntry fileEntryA = addFileEntry(
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, "Entry A.txt");

		_fileEntries.add(fileEntryA);

		_folder = DLAppTestUtil.addFolder(
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			"Folder A");

		FileEntry fileEntryAA = addFileEntry(
			 _folder.getFolderId(), "Entry AA.txt");

		_fileEntries.add(fileEntryAA);
	}

	private final List<FileEntry> _fileEntries = new ArrayList<>();
	private Folder _folder;

	@DeleteAfterTestRun
	private Group _group;

}
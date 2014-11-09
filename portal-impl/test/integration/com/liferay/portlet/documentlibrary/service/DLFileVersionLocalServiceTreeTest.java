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
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.DeleteAfterTestRun;
import com.liferay.portal.test.listeners.MainServletExecutionTestListener;
import com.liferay.portal.test.runners.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.util.test.GroupTestUtil;
import com.liferay.portal.util.test.ServiceContextTestUtil;
import com.liferay.portal.util.test.TestPropsValues;
import com.liferay.portlet.documentlibrary.model.DLFileEntryConstants;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.util.test.DLAppTestUtil;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Shinn Lok
 */
@ExecutionTestListeners(listeners = {MainServletExecutionTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class DLFileVersionLocalServiceTreeTest {

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

		FileEntry fileEntry = DLAppTestUtil.addFileEntry(
			_group.getGroupId(), folderAA.getFolderId(), "Entry.txt");

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

	protected void createTree() throws Exception {
		FileEntry fileEntryA = DLAppTestUtil.addFileEntry(
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			"Entry A.txt");

		_fileEntries.add(fileEntryA);

		_folder = DLAppTestUtil.addFolder(
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			"Folder A");

		FileEntry fileEntryAA = DLAppTestUtil.addFileEntry(
			_group.getGroupId(), _folder.getFolderId(), "Entry AA.txt");

		_fileEntries.add(fileEntryAA);
	}

	private final List<FileEntry> _fileEntries = new ArrayList<FileEntry>();
	private Folder _folder;

	@DeleteAfterTestRun
	private Group _group;

}
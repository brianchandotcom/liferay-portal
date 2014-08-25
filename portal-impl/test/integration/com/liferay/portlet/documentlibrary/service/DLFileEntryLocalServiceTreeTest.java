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
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.util.test.DLAppTestUtil;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.testng.Assert;

/**
 * @author Shinn Lok
 * @author Sergio González
 */
@ExecutionTestListeners(listeners = {MainServletExecutionTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class DLFileEntryLocalServiceTreeTest {

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testFileEntryTreepathWhenMovingSubfolderWithFileEntry()
		throws Exception {

		Folder folder = DLAppTestUtil.addFolder(
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			"Folder 1");

		Folder subfolder = DLAppTestUtil.addFolder(
			_group.getGroupId(), folder.getFolderId(), "Folder 1.1");

		FileEntry fileEntry = DLAppTestUtil.addFileEntry(
			_group.getGroupId(), subfolder.getFolderId(), "Entry.txt");

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		DLAppLocalServiceUtil.moveFolder(
			TestPropsValues.getUserId(), subfolder.getFolderId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, serviceContext);

		DLFileEntry dlFileEntry = DLFileEntryLocalServiceUtil.getDLFileEntry(
			fileEntry.getFileEntryId());

		Assert.assertEquals(
			dlFileEntry.buildTreePath(), dlFileEntry.getTreePath());
	}

	@Test
	public void testFolderTreepathWhenMovingFolderWithSubfolder()
		throws Exception {

		List<Folder> folders = new ArrayList<Folder>();

		Folder folder = DLAppTestUtil.addFolder(
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			"Folder 1");

		folders.add(folder);

		Folder subfolder = DLAppTestUtil.addFolder(
			_group.getGroupId(), folder.getFolderId(), "Folder 1.1");

		folders.add(subfolder);

		Folder subsubfolder = DLAppTestUtil.addFolder(
			_group.getGroupId(), subfolder.getFolderId(), "Folder 1.1.1");

		folders.add(subsubfolder);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		DLAppLocalServiceUtil.moveFolder(
			TestPropsValues.getUserId(), subfolder.getFolderId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, serviceContext);

		for (Folder curFolder : folders) {
			DLFolder dlFolder = DLFolderLocalServiceUtil.getFolder(
				curFolder.getFolderId());

			Assert.assertEquals(
				dlFolder.buildTreePath(), dlFolder.getTreePath());
		}
	}

	@Test
	public void testRebuildTree() throws Exception {
		List<FileEntry> fileEntries = createTree();

		for (FileEntry fileEntry : fileEntries) {
			DLFileEntry dlFileEntry = DLFileEntryLocalServiceUtil.getFileEntry(
				fileEntry.getFileEntryId());

			dlFileEntry.setTreePath(null);

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

	protected List<FileEntry> createTree() throws Exception {
		List<FileEntry> fileEntries = new ArrayList<FileEntry>();

		FileEntry fileEntryA = DLAppTestUtil.addFileEntry(
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			"Entry A.txt");

		fileEntries.add(fileEntryA);

		Folder folder = DLAppTestUtil.addFolder(
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			"Folder A");

		FileEntry fileEntryAA = DLAppTestUtil.addFileEntry(
			_group.getGroupId(), folder.getFolderId(), "Entry A.txt");

		fileEntries.add(fileEntryAA);

		return fileEntries;
	}

	@DeleteAfterTestRun
	private Group _group;

}
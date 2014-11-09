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
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
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
public class DLFileShortcutLocalServiceTreeTest {

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testFileShortcutTreePathWhenMovingSubfolderWithFileShortcut()
		throws Exception {

		Folder folderA = DLAppTestUtil.addFolder(
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			"Folder A");

		Folder folderAA = DLAppTestUtil.addFolder(
			_group.getGroupId(), folderA.getFolderId(), "Folder AA");

		FileEntry fileEntry = DLAppTestUtil.addFileEntry(
			_group.getGroupId(), folderA.getFolderId(), "Entry.txt");

		DLFileShortcut dlFileShortcut = DLAppTestUtil.addDLFileShortcut(
			fileEntry, TestPropsValues.getGroupId(), folderAA.getFolderId());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		DLAppLocalServiceUtil.moveFolder(
			TestPropsValues.getUserId(), folderAA.getFolderId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, serviceContext);

		dlFileShortcut = DLFileShortcutLocalServiceUtil.getDLFileShortcut(
			dlFileShortcut.getFileShortcutId());

		Assert.assertEquals(
			dlFileShortcut.buildTreePath(), dlFileShortcut.getTreePath());
	}

	@Test
	public void testRebuildTree() throws Exception {
		createTree();

		for (DLFileShortcut dlFileShortcut : _dlFileShortcuts) {
			dlFileShortcut.setTreePath(null);

			DLFileShortcutLocalServiceUtil.updateDLFileShortcut(dlFileShortcut);
		}

		DLFileShortcutLocalServiceUtil.rebuildTree(
			TestPropsValues.getCompanyId());

		for (DLFileShortcut dlFileShortcut : _dlFileShortcuts) {
			dlFileShortcut = DLFileShortcutLocalServiceUtil.getDLFileShortcut(
				dlFileShortcut.getFileShortcutId());

			Assert.assertEquals(
				dlFileShortcut.buildTreePath(), dlFileShortcut.getTreePath());
		}
	}

	protected void createTree() throws Exception {
		_fileEntry = DLAppTestUtil.addFileEntry(
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			"Entry A.txt");

		DLFileShortcut dlFileShortcutA = DLAppTestUtil.addDLFileShortcut(
			_fileEntry, TestPropsValues.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		_dlFileShortcuts.add(dlFileShortcutA);

		_folder = DLAppTestUtil.addFolder(
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			"Folder A");

		DLFileShortcut dlFileShortcutAA = DLAppTestUtil.addDLFileShortcut(
			_fileEntry, TestPropsValues.getGroupId(), _folder.getFolderId());

		_dlFileShortcuts.add(dlFileShortcutAA);
	}

	private final List<DLFileShortcut> _dlFileShortcuts =
		new ArrayList<DLFileShortcut>();
	private FileEntry _fileEntry;
	private Folder _folder;

	@DeleteAfterTestRun
	private Group _group;

}
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.util.DLAppTestUtil;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.testng.Assert;

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

	@After
	public void tearDown() throws Exception {
		GroupLocalServiceUtil.deleteGroup(_group);
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
			TestPropsValues.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, "Entry A.txt");

		DLFileShortcut dlFileShortcutA = DLAppTestUtil.addDLFileShortcut(
			_fileEntry, TestPropsValues.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		_dlFileShortcuts.add(dlFileShortcutA);

		_folder = DLAppTestUtil.addFolder(
			TestPropsValues.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, "Folder A");

		DLFileShortcut dlFileShortcutAA = DLAppTestUtil.addDLFileShortcut(
			_fileEntry, TestPropsValues.getGroupId(), _folder.getFolderId());

		_dlFileShortcuts.add(dlFileShortcutAA);
	}

	private List<DLFileShortcut> _dlFileShortcuts =
		new ArrayList<DLFileShortcut>();
	private FileEntry _fileEntry;
	private Folder _folder;
	private Group _group;

}
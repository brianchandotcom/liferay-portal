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
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
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
public class DLFileShortcutLocalServiceTreeTest {

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
	public void testFileShortcutTreePathWhenMovingSubfolderWithFileShortcut()
		throws Exception {

		Folder folderA = DLAppTestUtil.addFolder(
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			"Folder A");

		Folder folderAA = DLAppTestUtil.addFolder(
			_group.getGroupId(), folderA.getFolderId(), "Folder AA");

		FileEntry fileEntry = DLAppTestUtil.addFileEntry(
			_group.getGroupId(), folderA.getFolderId(), "Entry.txt");

		DLFileShortcut dlFileShortcut = addDLFileShortcut(
			fileEntry, TestPropsValues.getGroupId(), folderAA.getFolderId());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

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

	protected DLFileShortcut addDLFileShortcut(
			FileEntry fileEntry, long groupId, long folderId)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				groupId, TestPropsValues.getUserId());

		return DLAppLocalServiceUtil.addFileShortcut(
			TestPropsValues.getUserId(), groupId, folderId,
			fileEntry.getFileEntryId(), serviceContext);
	}

	protected void createTree() throws Exception {
		_fileEntry = DLAppTestUtil.addFileEntry(
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			"Entry A.txt");

		DLFileShortcut dlFileShortcutA = addDLFileShortcut(
			_fileEntry, TestPropsValues.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		_dlFileShortcuts.add(dlFileShortcutA);

		_folder = DLAppTestUtil.addFolder(
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			"Folder A");

		DLFileShortcut dlFileShortcutAA = addDLFileShortcut(
			_fileEntry, TestPropsValues.getGroupId(), _folder.getFolderId());

		_dlFileShortcuts.add(dlFileShortcutAA);
	}

	private final List<DLFileShortcut> _dlFileShortcuts = new ArrayList<>();
	private FileEntry _fileEntry;
	private Folder _folder;

	@DeleteAfterTestRun
	private Group _group;

}
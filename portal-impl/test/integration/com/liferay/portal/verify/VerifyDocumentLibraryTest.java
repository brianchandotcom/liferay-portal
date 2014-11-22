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

package com.liferay.portal.verify;

import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.model.Group;
import com.liferay.portal.test.DeleteAfterTestRun;
import com.liferay.portal.test.MainServletTestRule;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousDestinationExecutionTestListener;
import com.liferay.portal.test.runners.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.util.test.GroupTestUtil;
import com.liferay.portal.util.test.RandomTestUtil;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;
import com.liferay.portlet.documentlibrary.util.test.DLAppTestUtil;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Manuel de la Peña
 * @author Eudaldo Alonso
 * @author Sergio González
 */
@ExecutionTestListeners(
	listeners = {
		SynchronousDestinationExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Sync
public class VerifyDocumentLibraryTest extends BaseVerifyProcessTestCase {

	@ClassRule
	public static final MainServletTestRule mainServletTestRule =
		MainServletTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testDLFileEntryTreePathWithDLFileEntryInTrash()
		throws Exception {

		Folder parentFolder = DLAppTestUtil.addFolder(
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		FileEntry fileEntry = DLAppTestUtil.addFileEntry(
			_group.getGroupId(), parentFolder.getFolderId(),
			RandomTestUtil.randomString());

		DLAppServiceUtil.moveFileEntryToTrash(fileEntry.getFileEntryId());

		DLFolderLocalServiceUtil.deleteFolder(
			parentFolder.getFolderId(), false);

		doVerify();
	}

	@Test
	public void testDLFileEntryTreePathWithParentDLFolderInTrash()
		throws Exception {

		Folder grandparentFolder = DLAppTestUtil.addFolder(
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		Folder parentFolder = DLAppTestUtil.addFolder(
			_group.getGroupId(), grandparentFolder.getFolderId());

		DLAppTestUtil.addFileEntry(
			_group.getGroupId(), parentFolder.getFolderId(),
			RandomTestUtil.randomString());

		DLAppServiceUtil.moveFolderToTrash(parentFolder.getFolderId());

		DLFolderLocalServiceUtil.deleteFolder(
			grandparentFolder.getFolderId(), false);

		doVerify();
	}

	@Test
	public void testDLFileShortcutTreePathWithDLFileShortcutInTrash()
		throws Exception {

		Folder parentFolder = DLAppTestUtil.addFolder(
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		FileEntry fileEntry = DLAppTestUtil.addFileEntry(
			_group.getGroupId(), parentFolder.getFolderId(),
			RandomTestUtil.randomString());

		DLFileShortcut dlFileShortcut = DLAppTestUtil.addDLFileShortcut(
			fileEntry, _group.getGroupId(), parentFolder.getFolderId());

		DLAppServiceUtil.moveFileShortcutToTrash(
			dlFileShortcut.getFileShortcutId());

		DLFolderLocalServiceUtil.deleteFolder(
			parentFolder.getFolderId(), false);

		doVerify();
	}

	@Test
	public void testDLFileShortcutTreePathWithParentDLFolderInTrash()
		throws Exception {

		Folder grandparentFolder = DLAppTestUtil.addFolder(
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		Folder parentFolder = DLAppTestUtil.addFolder(
			_group.getGroupId(), grandparentFolder.getFolderId());

		DLAppTestUtil.addFileEntry(
			_group.getGroupId(), parentFolder.getFolderId(),
			RandomTestUtil.randomString());

		DLAppServiceUtil.moveFolderToTrash(parentFolder.getFolderId());

		DLFolderLocalServiceUtil.deleteFolder(
			grandparentFolder.getFolderId(), false);

		doVerify();
	}

	@Test
	public void testDLFolderTreePathWithDLFolderInTrash() throws Exception {
		Folder parentFolder = DLAppTestUtil.addFolder(
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		Folder folder = DLAppTestUtil.addFolder(
			_group.getGroupId(), parentFolder.getFolderId());

		DLAppServiceUtil.moveFolderToTrash(folder.getFolderId());

		DLFolderLocalServiceUtil.deleteFolder(
			parentFolder.getFolderId(), false);

		doVerify();
	}

	@Test
	public void testDLFolderTreePathWithParentDLFolderInTrash()
		throws Exception {

		Folder grandparentFolder = DLAppTestUtil.addFolder(
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		Folder parentFolder = DLAppTestUtil.addFolder(
			_group.getGroupId(), grandparentFolder.getFolderId());

		DLAppTestUtil.addFolder(
			_group.getGroupId(), parentFolder.getFolderId());

		DLAppServiceUtil.moveFolderToTrash(parentFolder.getFolderId());

		DLFolderLocalServiceUtil.deleteFolder(
			grandparentFolder.getFolderId(), false);

		doVerify();
	}

	@Override
	protected VerifyProcess getVerifyProcess() {
		return new VerifyDocumentLibrary();
	}

	@DeleteAfterTestRun
	private Group _group;

}
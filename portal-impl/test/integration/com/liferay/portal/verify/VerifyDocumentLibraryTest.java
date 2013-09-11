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

package com.liferay.portal.verify;

import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.model.Group;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;
import com.liferay.portlet.documentlibrary.util.DLAppTestUtil;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Manuel de la Peña
 */
@ExecutionTestListeners(listeners = {MainServletExecutionTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class VerifyDocumentLibraryTest extends BaseVerifyTestCase {

	@Test
	@Transactional
	public void testTreePathWithDLFileEntryInTrash() throws Exception {
		Group group = GroupTestUtil.addGroup();

		Folder parentFolder = DLAppTestUtil.addFolder(
			group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			"parentFolder");

		FileEntry fileEntry = DLAppTestUtil.addFileEntry(
			group.getGroupId(), parentFolder.getFolderId(), false, "fileName");

		DLAppServiceUtil.moveFileEntryToTrash(fileEntry.getFileEntryId());

		DLFolderLocalServiceUtil.deleteFolder(
			parentFolder.getFolderId(), false);

		doVerify();
	}

	@Test
	@Transactional
	public void testTreePathWithDLFileShortcutInTrash() throws Exception {
		Group group = GroupTestUtil.addGroup();

		Folder parentFolder = DLAppTestUtil.addFolder(
			group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			"parentFolder");

		FileEntry fileEntry = DLAppTestUtil.addFileEntry(
			group.getGroupId(), parentFolder.getFolderId(), false, "fileName");

		DLFileShortcut dlFileShortcut = DLAppTestUtil.addDLFileShortcut(
			fileEntry, group.getGroupId(), parentFolder.getFolderId());

		DLAppServiceUtil.moveFileShortcutToTrash(
			dlFileShortcut.getFileShortcutId());

		DLFolderLocalServiceUtil.deleteFolder(
			parentFolder.getFolderId(), false);

		doVerify();
	}

	@Test
	@Transactional
	public void testTreePathWithDLFolderInTrash() throws Exception {
		Group group = GroupTestUtil.addGroup();

		Folder parentFolder = DLAppTestUtil.addFolder(
			group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			"parentFolder");

		Folder childFolder = DLAppTestUtil.addFolder(
			group.getGroupId(), parentFolder.getFolderId(), "parentFolder");

		DLAppServiceUtil.moveFolderToTrash(childFolder.getFolderId());

		DLFolderLocalServiceUtil.deleteFolder(
			parentFolder.getFolderId(), false);

		doVerify();
	}

	@Override
	protected VerifyProcess getVerifyProcess() {
		return new VerifyDocumentLibrary();
	}

}
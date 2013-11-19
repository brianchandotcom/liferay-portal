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

package com.liferay.portlet.documentlibrary.service.permission;

import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.permission.BasePermissionTestCase;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.util.DLAppTestUtil;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eric Chin
 * @author Shinn Lok
 */
@ExecutionTestListeners(listeners = {EnvironmentExecutionTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class DLFileEntryPermissionTest extends BasePermissionTestCase {

	@Test
	public void testContains() throws Exception {
		Assert.assertTrue(
			DLFileEntryPermission.contains(
				permissionChecker, _fileEntry, ActionKeys.VIEW));
		Assert.assertTrue(
			DLFileEntryPermission.contains(
				permissionChecker, _subfileEntry, ActionKeys.VIEW));

		removePortletModelViewPermission();

		Assert.assertFalse(
			DLFileEntryPermission.contains(
				permissionChecker, _fileEntry, ActionKeys.VIEW));
		Assert.assertFalse(
			DLFileEntryPermission.contains(
				permissionChecker, _subfileEntry, ActionKeys.VIEW));
	}

	@Override
	protected void doSetUp() throws Exception {
		_fileEntry = DLAppTestUtil.addFileEntry(
			group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			true, ServiceTestUtil.randomString());

		Folder folder = DLAppTestUtil.addFolder(
			group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			ServiceTestUtil.randomString(), true);

		_subfileEntry = DLAppTestUtil.addFileEntry(
			group.getGroupId(), folder.getFolderId(), false,
			ServiceTestUtil.randomString());
	}

	@Override
	protected String getResourceName() {
		return DLPermission.RESOURCE_NAME;
	}

	private FileEntry _fileEntry;
	private FileEntry _subfileEntry;

}
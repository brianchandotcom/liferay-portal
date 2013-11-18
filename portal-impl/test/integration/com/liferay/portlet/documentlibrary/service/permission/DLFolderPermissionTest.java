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

import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portal.util.RoleTestUtil;
import com.liferay.portal.util.UserTestUtil;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.util.DLAppTestUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eric Chin
 */
@ExecutionTestListeners(
	listeners = {
		EnvironmentExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class DLFolderPermissionTest {

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_folder = DLAppTestUtil.addFolder(
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			"Test Folder Permissions", true);

		_subfolder = DLAppTestUtil.addFolder(
			_group.getGroupId(), _folder.getFolderId(),
			"Test SubFolder Permissions", true);

		RoleTestUtil.addResourcePermission(
			RoleConstants.POWER_USER, DLPermission.RESOURCE_NAME,
			ResourceConstants.SCOPE_GROUP, String.valueOf(_group.getGroupId()),
			ActionKeys.VIEW);
	}

	@After
	public void tearDown() throws Exception {
		RoleTestUtil.removeResourcePermission(
			RoleConstants.POWER_USER, DLPermission.RESOURCE_NAME,
			ResourceConstants.SCOPE_GROUP, String.valueOf(_group.getGroupId()),
			ActionKeys.VIEW);
	}

	@Test
	public void testGetFolderWithoutRootPermission() throws Exception {
		checkFolderRootPermission(false);
	}

	@Test
	public void testGetFolderWithRootPermission() throws Exception {
		checkFolderRootPermission(true);
	}

	protected void checkFolderRootPermission(boolean hasRootPermission)
		throws Exception {

		User user = UserTestUtil.addUser();

		PermissionChecker permissionChecker = _getPermissionChecker(user);

		if (!hasRootPermission) {
			RoleTestUtil.removeResourcePermission(
				RoleConstants.POWER_USER, DLPermission.RESOURCE_NAME,
				ResourceConstants.SCOPE_GROUP,
				String.valueOf(_group.getGroupId()), ActionKeys.VIEW);
		}

		boolean hasViewPermission = DLFolderPermission.contains(
			permissionChecker, _folder, ActionKeys.VIEW);

		boolean hasSubfolderViewPermission = DLFolderPermission.contains(
			permissionChecker, _subfolder, ActionKeys.VIEW);

		if (!hasRootPermission) {
			Assert.assertFalse(hasViewPermission);
			Assert.assertFalse(hasSubfolderViewPermission);
		}
		else {
			Assert.assertTrue(hasViewPermission);
			Assert.assertTrue(hasSubfolderViewPermission);
		}

		if (!hasRootPermission) {
			RoleTestUtil.addResourcePermission(
				RoleConstants.POWER_USER, DLPermission.RESOURCE_NAME,
				ResourceConstants.SCOPE_GROUP,
				String.valueOf(_group.getGroupId()), ActionKeys.VIEW);
		}
	}

	private PermissionChecker _getPermissionChecker(User user)
		throws Exception {

		return PermissionCheckerFactoryUtil.create(user);
	}

	private Folder _folder;
	private Group _group;
	private Folder _subfolder;

}
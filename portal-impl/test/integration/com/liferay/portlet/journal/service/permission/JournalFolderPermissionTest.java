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

package com.liferay.portlet.journal.service.permission;

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
import com.liferay.portlet.journal.model.JournalFolder;
import com.liferay.portlet.journal.model.JournalFolderConstants;
import com.liferay.portlet.journal.util.JournalTestUtil;

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
public class JournalFolderPermissionTest {

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_folder = JournalTestUtil.addFolder(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, "Test Folder");

		_subfolder = JournalTestUtil.addFolder(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, "Test SubFolder");

		RoleTestUtil.addResourcePermission(
			RoleConstants.POWER_USER, JournalPermission.RESOURCE_NAME,
			ResourceConstants.SCOPE_GROUP, String.valueOf(_group.getGroupId()),
			ActionKeys.VIEW);
	}

	@After
	public void tearDown() throws Exception {
		RoleTestUtil.removeResourcePermission(
			RoleConstants.POWER_USER, JournalPermission.RESOURCE_NAME,
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
				RoleConstants.POWER_USER, JournalPermission.RESOURCE_NAME,
				ResourceConstants.SCOPE_GROUP,
				String.valueOf(_group.getGroupId()), ActionKeys.VIEW);
		}

		boolean hasViewPermission = JournalFolderPermission.contains(
			permissionChecker, _folder, ActionKeys.VIEW);

		boolean hasSubfolderViewPermission = JournalFolderPermission.contains(
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
				RoleConstants.POWER_USER, JournalPermission.RESOURCE_NAME,
				ResourceConstants.SCOPE_GROUP,
				String.valueOf(_group.getGroupId()), ActionKeys.VIEW);
		}
	}

	private PermissionChecker _getPermissionChecker(User user)
		throws Exception {

		return PermissionCheckerFactoryUtil.create(user);
	}

	private JournalFolder _folder;
	private Group _group;
	private JournalFolder _subfolder;

}
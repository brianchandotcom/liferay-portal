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
import com.liferay.portal.model.Group;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.service.ResourceBlockLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portal.util.RoleTestUtil;
import com.liferay.portal.util.UserTestUtil;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.util.DLAppTestUtil;

import java.util.ArrayList;

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
public class DLFileEntryPermissionTest {

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_folder = DLAppTestUtil.addFolder(
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			"Test Folder", true);

		_fileEntry = DLAppTestUtil.addFileEntry(
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			true, "TestFileEntry.txt");

		_subFileEntry = DLAppTestUtil.addFileEntry(
			_group.getGroupId(), _folder.getFolderId(), false,
			"TestSubFolderFileEntry.txt");

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
	public void testGetFileEntryWithoutFolderPermission() throws Exception {
		User user = UserTestUtil.addUser();

		PermissionChecker permissionChecker = _getPermissionChecker(user);

		Folder folder = DLAppTestUtil.addFolder(
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			"Test Folder Permissions", true);

		FileEntry fileEntry = DLAppTestUtil.addFileEntry(
			_group.getGroupId(), folder.getFolderId(), false,
			"TestFileEntry.txt");

		deleteGuestPermission(folder);
		deletePowerUserPermission(folder);

		boolean hasViewPermission = DLFileEntryPermission.contains(
			permissionChecker, fileEntry, ActionKeys.VIEW);

		Assert.assertFalse(hasViewPermission);
	}

	@Test
	public void testGetFileEntryWithoutRootPermission() throws Exception {
		checkFileEntryRootPermissions(false);
	}

	@Test
	public void testGetFileEntryWithRootPermission() throws Exception {
		checkFileEntryRootPermissions(true);
	}

	protected void checkFileEntryRootPermissions(boolean hasRootPermission)
		throws Exception {

		User user = UserTestUtil.addUser();

		PermissionChecker permissionChecker = _getPermissionChecker(user);

		if (!hasRootPermission) {
			RoleTestUtil.removeResourcePermission(
				RoleConstants.POWER_USER, DLPermission.RESOURCE_NAME,
				ResourceConstants.SCOPE_GROUP,
				String.valueOf(_group.getGroupId()), ActionKeys.VIEW);
		}

		boolean hasViewPermission = DLFileEntryPermission.contains(
			permissionChecker, _fileEntry, ActionKeys.VIEW);

		boolean hasSubFileEntryViewPermission = DLFileEntryPermission.contains(
			permissionChecker, _subFileEntry, ActionKeys.VIEW);

		if (!hasRootPermission) {
			Assert.assertFalse(hasViewPermission);
			Assert.assertFalse(hasSubFileEntryViewPermission);
		}
		else {
			Assert.assertTrue(hasViewPermission);
			Assert.assertTrue(hasSubFileEntryViewPermission);
		}

		if (!hasRootPermission) {
			RoleTestUtil.addResourcePermission(
				RoleConstants.POWER_USER, DLPermission.RESOURCE_NAME,
				ResourceConstants.SCOPE_GROUP,
				String.valueOf(_group.getGroupId()), ActionKeys.VIEW);
		}
	}

	protected void deleteGuestPermission(Folder folder) throws Exception {
		Role role = RoleLocalServiceUtil.getRole(
			_group.getCompanyId(), RoleConstants.GUEST);

		deletePermission(folder, role);
	}

	protected void deletePermission(Folder folder, Role role) throws Exception {
		if (ResourceBlockLocalServiceUtil.isSupported(
				DLFolder.class.getName())) {

			ResourceBlockLocalServiceUtil.setIndividualScopePermissions(
				_group.getCompanyId(), _group.getGroupId(),
				DLFolder.class.getName(), folder.getFolderId(),
				role.getRoleId(), new ArrayList<String>());
		}
		else {
			ResourcePermissionLocalServiceUtil.setResourcePermissions(
				_group.getCompanyId(), DLFolder.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(folder.getFolderId()), role.getRoleId(),
				new String[0]);
		}
	}

	protected void deletePowerUserPermission(Folder folder) throws Exception {
		Role role = RoleLocalServiceUtil.getRole(
			_group.getCompanyId(), RoleConstants.POWER_USER);

		deletePermission(folder, role);
	}

	private PermissionChecker _getPermissionChecker(User user)
		throws Exception {

		return PermissionCheckerFactoryUtil.create(user);
	}

	private FileEntry _fileEntry;
	private Folder _folder;
	private Group _group;
	private FileEntry _subFileEntry;

}
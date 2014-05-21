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

package com.liferay.portlet.documentlibrary.service.permission;

import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.permission.BasePermissionTestCase;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.test.portal.util.RandomTestUtil;
import com.liferay.test.portal.util.RoleTestUtil;
import com.liferay.test.portlet.documentlibrary.util.DLAppTestUtil;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eric Chin
 * @author Shinn Lok
 */
@ExecutionTestListeners(listeners = {MainServletExecutionTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class DLFolderPermissionTest extends BasePermissionTestCase {

	@Test
	public void testContains() throws Exception {
		Assert.assertTrue(
			DLFolderPermission.contains(
				permissionChecker, _folder, ActionKeys.VIEW));
		Assert.assertTrue(
			DLFolderPermission.contains(
				permissionChecker, _subfolder, ActionKeys.VIEW));

		removePortletModelViewPermission();

		Assert.assertFalse(
			DLFolderPermission.contains(
				permissionChecker, _folder, ActionKeys.VIEW));
		Assert.assertFalse(
			DLFolderPermission.contains(
				permissionChecker, _subfolder, ActionKeys.VIEW));
	}

	@Override
	protected void doSetUp() throws Exception {
		_folder = DLAppTestUtil.addFolder(
			group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), true);

		_subfolder = DLAppTestUtil.addFolder(
			group.getGroupId(), _folder.getFolderId(),
			RandomTestUtil.randomString(), true);
	}

	@Override
	protected String getResourceName() {
		return DLPermission.RESOURCE_NAME;
	}

	@Override
	protected void removePortletModelViewPermission() throws Exception {
		super.removePortletModelViewPermission();

		RoleTestUtil.removeResourcePermission(
			RoleConstants.GUEST, getResourceName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			String.valueOf(group.getGroupId()), ActionKeys.VIEW);
	}

	private Folder _folder;
	private Folder _subfolder;

}
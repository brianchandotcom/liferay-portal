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

package com.liferay.document.library.permission.test;

import com.liferay.document.library.kernel.exception.NoSuchFolderException;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.service.permission.test.BasePermissionTestCase;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portlet.documentlibrary.service.permission.DLFolderPermission;
import com.liferay.portlet.documentlibrary.service.permission.DLPermission;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Eric Chin
 * @author Shinn Lok
 */
public class DLFolderPermissionCheckerTest extends BasePermissionTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

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
		String name = RandomTestUtil.randomString();

		try {
			DLAppServiceUtil.deleteFolder(
				group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				name);
		}
		catch (NoSuchFolderException nsfe) {
		}

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), TestPropsValues.getUserId());

		_folder = DLAppServiceUtil.addFolder(
			group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			name, RandomTestUtil.randomString(), serviceContext);

		name = RandomTestUtil.randomString();

		try {
			DLAppServiceUtil.deleteFolder(
				group.getGroupId(), _folder.getFolderId(), name);
		}
		catch (NoSuchFolderException nsfe) {
		}

		_subfolder = DLAppServiceUtil.addFolder(
			group.getGroupId(), _folder.getFolderId(), name,
			RandomTestUtil.randomString(), serviceContext);
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
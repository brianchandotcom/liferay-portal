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

import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.permission.test.BasePermissionTestCase;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.util.test.DLAppTestUtil;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Eric Chin
 * @author Shinn Lok
 */
public class DLFileEntryPermissionTest extends BasePermissionTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE);

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
			RandomTestUtil.randomString());

		Folder folder = DLAppTestUtil.addFolder(
			group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), true);

		_subfileEntry = DLAppTestUtil.addFileEntry(
			group.getGroupId(), folder.getFolderId(),
			RandomTestUtil.randomString());
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

	private FileEntry _fileEntry;
	private FileEntry _subfileEntry;

}
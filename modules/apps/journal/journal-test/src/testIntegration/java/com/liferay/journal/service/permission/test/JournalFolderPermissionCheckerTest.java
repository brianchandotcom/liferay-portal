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

package com.liferay.journal.service.permission.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.journal.constants.JournalConstants;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.security.permission.test.util.BasePermissionTestCase;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eric Chin
 * @author Shinn Lok
 */
@RunWith(Arquillian.class)
public class JournalFolderPermissionCheckerTest extends BasePermissionTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testContains() throws Exception {
		Assert.assertTrue(
			_journalFolderModelResourcePermission.contains(
				permissionChecker, _folder, ActionKeys.VIEW));
		Assert.assertTrue(
			_journalFolderModelResourcePermission.contains(
				permissionChecker, _subfolder, ActionKeys.VIEW));

		removePortletModelViewPermission();

		Assert.assertFalse(
			_journalFolderModelResourcePermission.contains(
				permissionChecker, _folder, ActionKeys.VIEW));
		Assert.assertFalse(
			_journalFolderModelResourcePermission.contains(
				permissionChecker, _subfolder, ActionKeys.VIEW));
	}

	@Override
	protected void doSetUp() throws Exception {
		_folder = JournalTestUtil.addFolder(
			group.getGroupId(), RandomTestUtil.randomString());

		_subfolder = JournalTestUtil.addFolder(
			group.getGroupId(), _folder.getFolderId(),
			RandomTestUtil.randomString());
	}

	@Override
	protected String getResourceName() {
		return JournalConstants.RESOURCE_NAME;
	}

	@Inject(filter = "model.class.name=com.liferay.journal.model.JournalFolder")
	private static ModelResourcePermission<JournalFolder>
		_journalFolderModelResourcePermission;

	private JournalFolder _folder;
	private JournalFolder _subfolder;

}
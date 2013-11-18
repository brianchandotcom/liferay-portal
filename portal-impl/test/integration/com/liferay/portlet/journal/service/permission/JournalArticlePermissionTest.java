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
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portal.util.RoleTestUtil;
import com.liferay.portal.util.UserTestUtil;
import com.liferay.portlet.journal.model.JournalArticle;
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
public class JournalArticlePermissionTest {

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_folder = JournalTestUtil.addFolder(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			"Test Folder Permission");

		_article = JournalTestUtil.addArticle(
			_group.getGroupId(), "Test Permission Article",
			"Test Permission Article");

		_subarticle = JournalTestUtil.addArticle(
			_group.getGroupId(), _folder.getFolderId(), "Test sub article",
			"Test sub article");

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
	public void testGetGroupArticleWithoutRootPermission() throws Exception {
		checkGroupArticleRootPermission(false);
	}

	@Test
	public void testGetGroupArticleWithRootPermission() throws Exception {
		checkGroupArticleRootPermission(true);
	}

	protected void checkGroupArticleRootPermission(boolean hasRootPermission)
		throws Exception {

		User user = UserTestUtil.addUser();

		UserLocalServiceUtil.addGroupUser(_group.getGroupId(), user);

		PermissionChecker permissionChecker = _getPermissionChecker(user);

		if (!hasRootPermission) {
			RoleTestUtil.removeResourcePermission(
				RoleConstants.POWER_USER, JournalPermission.RESOURCE_NAME,
				ResourceConstants.SCOPE_GROUP,
				String.valueOf(_group.getGroupId()), ActionKeys.VIEW);
		}

		long groupId = _group.getGroupId();

		int status = WorkflowConstants.STATUS_ANY;

		boolean hasViewPermission = JournalArticlePermission.contains(
			permissionChecker, groupId, _article.getArticleId(), status,
			ActionKeys.VIEW);

		boolean hasSubarticleViewPermission = JournalArticlePermission.contains(
			permissionChecker, groupId, _subarticle.getArticleId(), status,
			ActionKeys.VIEW);

		if (!hasRootPermission) {
			Assert.assertFalse(hasViewPermission);
			Assert.assertFalse(hasSubarticleViewPermission);
		}
		else {
			Assert.assertTrue(hasViewPermission);
			Assert.assertTrue(hasSubarticleViewPermission);
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

	private JournalArticle _article;
	private JournalFolder _folder;
	private Group _group;
	private JournalArticle _subarticle;

}
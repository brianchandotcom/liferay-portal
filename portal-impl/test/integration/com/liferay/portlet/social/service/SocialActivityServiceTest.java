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

package com.liferay.portlet.social.service;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ResourceBlockLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portal.util.RoleTestUtil;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portal.util.UserTestUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.permission.DLPermission;
import com.liferay.portlet.documentlibrary.util.DLAppTestUtil;
import com.liferay.portlet.social.model.SocialActivity;
import com.liferay.portlet.social.util.SocialActivityHierarchyEntryThreadLocal;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Zsolt Berentey
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class SocialActivityServiceTest {

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
		_user = UserTestUtil.addUser();

		RoleTestUtil.addResourcePermission(
			RoleConstants.GUEST, DLPermission.RESOURCE_NAME,
			ResourceConstants.SCOPE_GROUP, String.valueOf(_group.getGroupId()),
			ActionKeys.VIEW);
	}

	@After
	public void tearDown() throws Exception {
		GroupLocalServiceUtil.deleteGroup(_group);
		UserLocalServiceUtil.deleteUser(_user);

		RoleTestUtil.removeResourcePermission(
			RoleConstants.GUEST, DLPermission.RESOURCE_NAME,
			ResourceConstants.SCOPE_GROUP, String.valueOf(_group.getGroupId()),
			ActionKeys.VIEW);

		SocialActivityHierarchyEntryThreadLocal.clear();
	}

	@Test
	public void testFilterActivities() throws Exception {
		FileEntry fileEntry = DLAppTestUtil.addFileEntry(
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			ServiceTestUtil.randomString() + ".txt",
			ServiceTestUtil.randomString(), true);

		deleteGuestPermission(fileEntry);

		List<SocialActivity> activities =
			SocialActivityLocalServiceUtil.getGroupActivities(
				_group.getGroupId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(1, activities.size());

		ServiceTestUtil.setUser(_user);

		activities = SocialActivityServiceUtil.getGroupActivities(
			_group.getGroupId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(0, activities.size());

		ServiceTestUtil.setUser(TestPropsValues.getUser());
	}

	@Test
	public void testPagingFilterActivities() throws Exception {
		for (int i = 0; i < 4; i++) {
			DLAppTestUtil.addFileEntry(
				_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				ServiceTestUtil.randomString() + ".txt", String.valueOf(i),
				true);

			FileEntry fileEntry = DLAppTestUtil.addFileEntry(
				_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				ServiceTestUtil.randomString() + ".txt",
				ServiceTestUtil.randomString(), true);

			deleteGuestPermission(fileEntry);
		}

		ServiceTestUtil.setUser(_user);

		Assert.assertEquals(
			8,
			SocialActivityServiceUtil.getGroupActivitiesCount(
				_group.getGroupId()));

		List<SocialActivity> activities =
			SocialActivityServiceUtil.getGroupActivities(
				_group.getGroupId(), 0, 2);

		Assert.assertEquals(2, activities.size());

		int index = 3;

		for (SocialActivity activity : activities) {
			String title = String.valueOf(index);

			Assert.assertEquals(title, activity.getExtraDataValue("title"));

			index--;
		}

		activities = SocialActivityServiceUtil.getGroupActivities(
			_group.getGroupId(), 2, 4);

		Assert.assertEquals(2, activities.size());

		for (SocialActivity activity : activities) {
			String title = String.valueOf(index);

			Assert.assertEquals(title, activity.getExtraDataValue("title"));

			index--;
		}
	}

	protected void deleteGuestPermission(FileEntry fileEntry) throws Exception {
		Role role = RoleLocalServiceUtil.getRole(
			_group.getCompanyId(), RoleConstants.GUEST);

		if (ResourceBlockLocalServiceUtil.isSupported(
				DLFileEntry.class.getName())) {

			ResourceBlockLocalServiceUtil.setIndividualScopePermissions(
				_group.getCompanyId(), _group.getGroupId(),
				DLFileEntry.class.getName(), fileEntry.getFileEntryId(),
				role.getRoleId(), new ArrayList<String>());
		}
		else {
			ResourcePermissionLocalServiceUtil.setResourcePermissions(
				_group.getCompanyId(), DLFileEntry.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(fileEntry.getFileEntryId()), role.getRoleId(),
				new String[0]);
		}
	}

	private Group _group;
	private User _user;

}
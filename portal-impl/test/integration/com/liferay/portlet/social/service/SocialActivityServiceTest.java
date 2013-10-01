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
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ResourceBlockLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portal.util.UserTestUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
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
		MainServletExecutionTestListener.class,
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class SocialActivityServiceTest {

	@Before
	public void setUp() throws Exception {
		group = GroupTestUtil.addGroup();

		user = UserTestUtil.addUser();
	}

	@After
	public void tearDown() throws Exception {
		SocialActivityHierarchyEntryThreadLocal.clear();

		GroupLocalServiceUtil.deleteGroup(group);

		UserLocalServiceUtil.deleteUser(user);
	}

	@Test
	public void testFilterActivities() throws Exception {
		FileEntry fileEntry = DLAppTestUtil.addFileEntry(
			group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			ServiceTestUtil.randomString() + ".txt",
			ServiceTestUtil.randomString(), true);

		removeGuestPermission(fileEntry);

		List<SocialActivity> activities =
			SocialActivityLocalServiceUtil.getGroupActivities(
				group.getGroupId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(1, activities.size());

		ServiceTestUtil.setUser(user);

		activities = SocialActivityServiceUtil.getGroupActivities(
			group.getGroupId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(0, activities.size());

		ServiceTestUtil.setUser(TestPropsValues.getUser());
	}

	@Test
	public void testPagingFilterActivities() throws Exception {
		for (int i = 0; i < 4; i++) {
			DLAppTestUtil.addFileEntry(
				group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				ServiceTestUtil.randomString() + ".txt", String.valueOf(i),
				true);

			FileEntry fileEntry = DLAppTestUtil.addFileEntry(
				group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				ServiceTestUtil.randomString() + ".txt",
				ServiceTestUtil.randomString(), true);

			removeGuestPermission(fileEntry);
		}

		ServiceTestUtil.setUser(user);

		Assert.assertEquals(
			8,
			SocialActivityServiceUtil.getGroupActivitiesCount(
				group.getGroupId()));

		List<SocialActivity> activities =
			SocialActivityServiceUtil.getGroupActivities(
				group.getGroupId(), 0, 2);

		Assert.assertEquals(2, activities.size());

		int index = 3;

		for (SocialActivity activity : activities) {
			String title = String.valueOf(index);

			Assert.assertEquals(title, activity.getExtraDataValue("title"));

			index--;
		}

		activities = SocialActivityServiceUtil.getGroupActivities(
			group.getGroupId(), 2, 4);

		Assert.assertEquals(2, activities.size());

		for (SocialActivity activity : activities) {
			String title = String.valueOf(index);

			Assert.assertEquals(title, activity.getExtraDataValue("title"));

			index--;
		}
	}

	protected void removeGuestPermission(FileEntry fileEntry) throws Exception {
		Role role = RoleLocalServiceUtil.getRole(
			group.getCompanyId(), RoleConstants.GUEST);

		String resourceName = DLFileEntry.class.getName();

		if (ResourceBlockLocalServiceUtil.isSupported(resourceName)) {
			ResourceBlockLocalServiceUtil.setIndividualScopePermissions(
				group.getCompanyId(), group.getGroupId(), resourceName,
				fileEntry.getFileEntryId(), role.getRoleId(),
				new ArrayList<String>());
		}
		else {
			ResourcePermissionLocalServiceUtil.setResourcePermissions(
				group.getCompanyId(), DLFileEntry.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(fileEntry.getFileEntryId()), role.getRoleId(),
				new String[0]);
		}
	}

	protected Group group;
	protected User user;

}
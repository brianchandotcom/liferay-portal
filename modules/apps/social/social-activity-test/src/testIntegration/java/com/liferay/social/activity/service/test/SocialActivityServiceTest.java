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

package com.liferay.social.activity.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.constants.TestDataConstants;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portlet.documentlibrary.constants.DLConstants;
import com.liferay.portlet.social.util.SocialActivityHierarchyEntryThreadLocal;
import com.liferay.social.kernel.model.SocialActivity;
import com.liferay.social.kernel.service.SocialActivityLocalServiceUtil;
import com.liferay.social.kernel.service.SocialActivityServiceUtil;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Zsolt Berentey
 */
@RunWith(Arquillian.class)
public class SocialActivityServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
		_user = UserTestUtil.addUser();

		RoleTestUtil.addResourcePermission(
			RoleConstants.GUEST, DLConstants.RESOURCE_NAME,
			ResourceConstants.SCOPE_GROUP, String.valueOf(_group.getGroupId()),
			ActionKeys.VIEW);
	}

	@After
	public void tearDown() throws Exception {
		RoleTestUtil.removeResourcePermission(
			RoleConstants.GUEST, DLConstants.RESOURCE_NAME,
			ResourceConstants.SCOPE_GROUP, String.valueOf(_group.getGroupId()),
			ActionKeys.VIEW);

		SocialActivityHierarchyEntryThreadLocal.clear();
	}

	@Test
	public void testFilterActivities() throws Exception {
		FileEntry fileEntry = addFileEntry(
			RandomTestUtil.randomString() + ".txt",
			RandomTestUtil.randomString());

		deleteGuestPermission(fileEntry);

		List<SocialActivity> activities =
			SocialActivityLocalServiceUtil.getGroupActivities(
				_group.getGroupId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(activities.toString(), 1, activities.size());

		UserTestUtil.setUser(_user);

		activities = SocialActivityServiceUtil.getGroupActivities(
			_group.getGroupId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(activities.toString(), 0, activities.size());

		UserTestUtil.setUser(TestPropsValues.getUser());
	}

	@Test
	public void testPagingFilterActivities() throws Exception {
		for (int i = 0; i < 4; i++) {
			addFileEntry(
				RandomTestUtil.randomString() + ".txt", String.valueOf(i));

			FileEntry fileEntry = addFileEntry(
				RandomTestUtil.randomString() + ".txt",
				RandomTestUtil.randomString());

			deleteGuestPermission(fileEntry);
		}

		long userId = PrincipalThreadLocal.getUserId();

		UserTestUtil.setUser(_user);

		try {
			Assert.assertEquals(
				8,
				SocialActivityServiceUtil.getGroupActivitiesCount(
					_group.getGroupId()));

			List<SocialActivity> activities =
				SocialActivityServiceUtil.getGroupActivities(
					_group.getGroupId(), 0, 2);

			Assert.assertEquals(activities.toString(), 2, activities.size());

			int index = 3;

			for (SocialActivity activity : activities) {
				String title = String.valueOf(index);

				Assert.assertEquals(title, activity.getExtraDataValue("title"));

				index--;
			}

			activities = SocialActivityServiceUtil.getGroupActivities(
				_group.getGroupId(), 2, 4);

			Assert.assertEquals(activities.toString(), 2, activities.size());

			for (SocialActivity activity : activities) {
				String title = String.valueOf(index);

				Assert.assertEquals(title, activity.getExtraDataValue("title"));

				index--;
			}
		}
		finally {
			UserTestUtil.setUser(UserLocalServiceUtil.getUser(userId));
		}
	}

	protected FileEntry addFileEntry(String fileName, String title)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		return DLAppLocalServiceUtil.addFileEntry(
			null, TestPropsValues.getUserId(), _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, fileName,
			ContentTypes.TEXT_PLAIN, title, StringPool.BLANK, StringPool.BLANK,
			StringPool.BLANK, TestDataConstants.TEST_BYTE_ARRAY, null, null,
			serviceContext);
	}

	protected void deleteGuestPermission(FileEntry fileEntry) throws Exception {
		Role role = RoleLocalServiceUtil.getRole(
			_group.getCompanyId(), RoleConstants.GUEST);

		ResourcePermissionLocalServiceUtil.setResourcePermissions(
			_group.getCompanyId(), DLFileEntry.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			String.valueOf(fileEntry.getFileEntryId()), role.getRoleId(),
			new String[0]);
	}

	@DeleteAfterTestRun
	private Group _group;

	@DeleteAfterTestRun
	private User _user;

}
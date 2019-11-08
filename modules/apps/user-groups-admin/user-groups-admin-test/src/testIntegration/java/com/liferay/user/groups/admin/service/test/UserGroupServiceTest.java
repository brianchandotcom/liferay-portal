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

package com.liferay.user.groups.admin.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.service.UserGroupService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserGroupTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.service.persistence.constants.UserGroupFinderConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.users.admin.kernel.util.UsersAdminUtil;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Drew Brokke
 */
@RunWith(Arquillian.class)
public class UserGroupServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testDatabaseSearchPermissionCheck() throws Exception {
		User user = UserTestUtil.addUser();

		try {
			PermissionThreadLocal.setPermissionChecker(
				_permissionCheckerFactory.create(user));

			UserGroup userGroup = _addUserGroup();

			_userGroupLocalService.addUserUserGroup(
				user.getUserId(), userGroup);

			_assertSearch(user, 0);

			Role adminRole = _roleLocalService.getRole(
				user.getCompanyId(), RoleConstants.ADMINISTRATOR);

			RoleLocalServiceUtil.addUserRole(user.getUserId(), adminRole);

			_assertSearch(user, 1);
		}
		finally {
			_userLocalService.deleteUser(user);
		}
	}

	@Test
	public void testGetGtUserGroups() throws Exception {
		for (int i = 0; i < 10; i++) {
			_userGroups.add(UserGroupTestUtil.addUserGroup());
		}

		long parentUserGroupId = 0;
		int size = 5;

		List<UserGroup> userGroups1 = _userGroupService.getGtUserGroups(
			0, TestPropsValues.getCompanyId(), parentUserGroupId, size);

		Assert.assertEquals(userGroups1.toString(), size, userGroups1.size());

		UserGroup lastUserGroup = userGroups1.get(userGroups1.size() - 1);

		List<UserGroup> userGroups2 = _userGroupService.getGtUserGroups(
			lastUserGroup.getUserGroupId(), TestPropsValues.getCompanyId(),
			parentUserGroupId, size);

		Assert.assertEquals(userGroups2.toString(), size, userGroups2.size());

		long previousUserGroupId = 0;

		for (UserGroup userGroup : userGroups2) {
			long userGroupId = userGroup.getUserGroupId();

			Assert.assertTrue(userGroupId > lastUserGroup.getUserGroupId());
			Assert.assertTrue(userGroupId > previousUserGroupId);

			previousUserGroupId = userGroupId;
		}
	}

	@Test
	public void testGetUserGroupsLikeName() throws Exception {
		List<UserGroup> allUserGroups = new ArrayList<>();

		allUserGroups.addAll(
			_userGroupLocalService.getUserGroups(
				TestPropsValues.getCompanyId()));

		List<UserGroup> likeNameUserGroups = new ArrayList<>();

		String name = RandomTestUtil.randomString(50);

		for (int i = 0; i < 10; i++) {
			UserGroup userGroup = _addUserGroup();

			userGroup.setName(name + i);

			userGroup = _userGroupLocalService.updateUserGroup(userGroup);

			allUserGroups.add(userGroup);
			likeNameUserGroups.add(userGroup);
		}

		allUserGroups.add(_addUserGroup());
		allUserGroups.add(_addUserGroup());
		allUserGroups.add(_addUserGroup());

		_assertExpectedUserGroups(likeNameUserGroups, name + "%");
		_assertExpectedUserGroups(
			likeNameUserGroups, StringUtil.toLowerCase(name) + "%");
		_assertExpectedUserGroups(
			likeNameUserGroups, StringUtil.toUpperCase(name) + "%");
		_assertExpectedUserGroups(allUserGroups, null);
		_assertExpectedUserGroups(allUserGroups, "");
	}

	private UserGroup _addUserGroup() throws Exception {
		UserGroup userGroup = UserGroupTestUtil.addUserGroup();

		_userGroups.add(userGroup);

		return userGroup;
	}

	private void _assertExpectedUserGroups(
			List<UserGroup> expectedUserGroups, String nameSearch)
		throws Exception {

		List<UserGroup> actualUserGroups = _userGroupService.getUserGroups(
			TestPropsValues.getCompanyId(), nameSearch, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		Assert.assertEquals(
			actualUserGroups.toString(), expectedUserGroups.size(),
			actualUserGroups.size());
		Assert.assertTrue(
			actualUserGroups.toString(),
			actualUserGroups.containsAll(expectedUserGroups));

		Assert.assertEquals(
			expectedUserGroups.size(),
			_userGroupService.getUserGroupsCount(
				TestPropsValues.getCompanyId(), nameSearch));
	}

	private void _assertSearch(User user, int expected) throws Exception {
		LinkedHashMap<String, Object> userGroupParams =
			LinkedHashMapBuilder.<String, Object>put(
				UserGroupFinderConstants.PARAM_KEY_USER_GROUPS_USERS,
				Long.valueOf(user.getUserId()));

		List<UserGroup> userGroups = _userGroupService.search(
			user.getCompanyId(), null, userGroupParams, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS,
			UsersAdminUtil.getUserGroupOrderByComparator("name", "asc"));

		Assert.assertEquals(userGroups.toString(), expected, userGroups.size());
	}

	@Inject
	private PermissionCheckerFactory _permissionCheckerFactory;

	@Inject
	private ResourceActionLocalService _resourceActionLocalService;

	@Inject
	private RoleLocalService _roleLocalService;

	@Inject
	private UserGroupLocalService _userGroupLocalService;

	@DeleteAfterTestRun
	private final List<UserGroup> _userGroups = new ArrayList<>();

	@Inject
	private UserGroupService _userGroupService;

	@Inject
	private UserLocalService _userLocalService;

}
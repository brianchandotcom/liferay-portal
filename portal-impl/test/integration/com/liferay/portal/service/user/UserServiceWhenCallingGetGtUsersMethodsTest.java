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

package com.liferay.portal.service.user;

import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.service.UserServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.OrganizationTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserGroupTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Brian Wing Shun Chan
 * @author José Manuel Navarro
 * @author Drew Brokke
 */
public class UserServiceWhenCallingGetGtUsersMethodsTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testGetGtCompanyUsers() throws Exception {
		for (int i = 0; i < 10; i++) {
			_users.add(UserTestUtil.addUser());
		}

		int size = 5;

		_assert(
			size,
			gtUserId -> UserServiceUtil.getGtCompanyUsers(
				gtUserId, TestPropsValues.getCompanyId(), size));
	}

	@Test
	public void testGetGtOrganizationUsers() throws Exception {
		_organization = OrganizationTestUtil.addOrganization();

		for (int i = 0; i < 10; i++) {
			_users.add(
				UserTestUtil.addOrganizationUser(
					_organization, RoleConstants.ORGANIZATION_USER));
		}

		int size = 5;

		_assert(
			size,
			gtUserId -> UserServiceUtil.getGtOrganizationUsers(
				gtUserId, _organization.getOrganizationId(), size));
	}

	@Test
	public void testGetGtUserGroupUsers() throws Exception {
		_userGroup = UserGroupTestUtil.addUserGroup();

		long[] userIds = new long[10];

		for (int i = 0; i < userIds.length; i++) {
			User user = UserTestUtil.addUser();

			_users.add(user);

			userIds[i] = user.getUserId();
		}

		UserLocalServiceUtil.setUserGroupUsers(
			_userGroup.getUserGroupId(), userIds);

		int size = 5;

		_assert(
			size,
			gtUserId -> UserServiceUtil.getGtUserGroupUsers(
				gtUserId, _userGroup.getUserGroupId(), size));
	}

	private void _assert(
			int size,
			UnsafeFunction<Long, List<User>, Exception> unsafeFunction)
		throws Exception {

		List<User> users = unsafeFunction.apply(0L);

		Assert.assertFalse(users.isEmpty());
		Assert.assertEquals(users.toString(), size, users.size());

		User lastUser = users.get(users.size() - 1);

		users = unsafeFunction.apply(lastUser.getUserId());

		Assert.assertFalse(users.isEmpty());
		Assert.assertEquals(users.toString(), size, users.size());

		long previousUserId = 0;

		for (User user : users) {
			long userId = user.getUserId();

			Assert.assertTrue(userId > lastUser.getUserId());
			Assert.assertTrue(userId > previousUserId);

			previousUserId = userId;
		}
	}

	@DeleteAfterTestRun
	private Organization _organization;

	@DeleteAfterTestRun
	private UserGroup _userGroup;

	@DeleteAfterTestRun
	private final List<User> _users = new ArrayList<>();

}
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

package com.liferay.users.admin.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.OrganizationTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserGroupTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerTestRule;

import java.util.ArrayList;
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
public class UserServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerTestRule.INSTANCE);

	@Test
	public void testGetGtCompanyUsers() throws Exception {
		int numberOfUsers = 10;
		int size = 5;

		for (int i = 0; i < numberOfUsers; i++) {
			_users.add(UserTestUtil.addUser());
		}

		assertGtUserIdCall(
			size,
			gtUserId -> _userService.getGtCompanyUsers(
				gtUserId, TestPropsValues.getCompanyId(), size));
	}

	@Test
	public void testGetGtOrganizationUsers() throws Exception {
		_organization = OrganizationTestUtil.addOrganization();

		int numberOfUsers = 10;
		int size = 5;

		for (int i = 0; i < numberOfUsers; i++) {
			_users.add(
				UserTestUtil.addOrganizationUser(
					_organization, RoleConstants.ORGANIZATION_USER));
		}

		assertGtUserIdCall(
			size,
			gtUserId -> _userService.getGtOrganizationUsers(
				gtUserId, _organization.getOrganizationId(), size));
	}

	@Test
	public void testGetGtUserGroupUsers() throws Exception {
		_userGroup = UserGroupTestUtil.addUserGroup();

		int numberOfUsers = 10;
		int size = 5;

		long[] userIds = new long[numberOfUsers];

		for (int i = 0; i < numberOfUsers; i++) {
			User user = UserTestUtil.addUser();

			_users.add(user);

			userIds[i] = user.getUserId();
		}

		_userLocalService.setUserGroupUsers(
			_userGroup.getUserGroupId(), userIds);

		assertGtUserIdCall(
			size,
			gtUserId -> _userService.getGtUserGroupUsers(
				gtUserId, _userGroup.getUserGroupId(), size));
	}

	protected void assertGtUserIdCall(
			int size,
			UnsafeFunction<Long, List<User>, Exception> gtUserIdFunction)
		throws Exception {

		List<User> retrievedUsers = gtUserIdFunction.apply(0L);

		Assert.assertFalse("It should return users", retrievedUsers.isEmpty());

		Assert.assertEquals(
			"It should return the correct number of users", size,
			retrievedUsers.size());

		User lastUser = retrievedUsers.get(retrievedUsers.size() - 1);

		retrievedUsers = gtUserIdFunction.apply(lastUser.getUserId());

		Assert.assertFalse("It should return users", retrievedUsers.isEmpty());

		Assert.assertEquals(
			"It should return the correct number of users", size,
			retrievedUsers.size());

		long previousUserId = 0;

		for (User user : retrievedUsers) {
			long userId = user.getUserId();

			Assert.assertTrue(
				"The returned userId " + userId +
					" should be greater than the given gtUserId: " +
						lastUser.getUserId(),
				userId > lastUser.getUserId());

			Assert.assertTrue(
				"The userId " + userId +
					" should be greater than the previous userId " +
						previousUserId,
				userId > previousUserId);

			previousUserId = userId;
		}
	}

	@Inject
	private static UserLocalService _userLocalService;

	@Inject
	private static UserService _userService;

	@DeleteAfterTestRun
	private Organization _organization;

	@DeleteAfterTestRun
	private UserGroup _userGroup;

	@DeleteAfterTestRun
	private final List<User> _users = new ArrayList<>();

}
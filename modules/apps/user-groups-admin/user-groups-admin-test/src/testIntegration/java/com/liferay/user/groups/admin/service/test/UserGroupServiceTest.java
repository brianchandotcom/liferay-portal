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
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.UserGroupService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserGroupTestUtil;
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
public class UserGroupServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerTestRule.INSTANCE);

	@Test
	public void testGetGtUserGroups() throws Exception {
		long parentUserGroupId = 0;

		int numberOfUserGroups = 10;
		int size = 5;

		for (int i = 0; i < numberOfUserGroups; i++) {
			_userGroups.add(UserGroupTestUtil.addUserGroup());
		}

		List<UserGroup> retrievedUserGroups = _userGroupService.getGtUserGroups(
			0, TestPropsValues.getCompanyId(), parentUserGroupId, size);

		Assert.assertFalse(
			"It should return user groups", retrievedUserGroups.isEmpty());

		Assert.assertEquals(
			"It should return the correct number of user groups", size,
			retrievedUserGroups.size());

		UserGroup lastUserGroup = retrievedUserGroups.get(
			retrievedUserGroups.size() - 1);

		retrievedUserGroups = _userGroupService.getGtUserGroups(
			lastUserGroup.getUserGroupId(), TestPropsValues.getCompanyId(),
			parentUserGroupId, size);

		Assert.assertFalse(
			"It should return user groups", retrievedUserGroups.isEmpty());

		Assert.assertEquals(
			"It should return the correct number of user groups", size,
			retrievedUserGroups.size());

		long previousUserGroupId = 0;

		for (UserGroup userGroup : retrievedUserGroups) {
			long userGroupId = userGroup.getUserGroupId();

			Assert.assertTrue(
				"The returned userGroupId " + userGroupId +
					" should be greater than the given gtUserGroupId: " +
						lastUserGroup.getUserGroupId(),
				userGroupId > lastUserGroup.getUserGroupId());

			Assert.assertTrue(
				"The userGroupId " + userGroupId +
					" should be greater than the previous userGroupId " +
						previousUserGroupId,
				userGroupId > previousUserGroupId);

			previousUserGroupId = userGroupId;
		}
	}

	@Inject
	private static UserGroupService _userGroupService;

	@DeleteAfterTestRun
	private final List<UserGroup> _userGroups = new ArrayList<>();

}
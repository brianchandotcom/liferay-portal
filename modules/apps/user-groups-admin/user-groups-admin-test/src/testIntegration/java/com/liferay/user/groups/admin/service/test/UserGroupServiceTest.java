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
		for (int i = 0; i < 10; i++) {
			_userGroups.add(UserGroupTestUtil.addUserGroup());
		}

		long parentUserGroupId = 0;
		int size = 5;

		List<UserGroup> userGroups = _userGroupService.getGtUserGroups(
			0, TestPropsValues.getCompanyId(), parentUserGroupId, size);

		Assert.assertFalse(userGroups.isEmpty());
		Assert.assertEquals(userGroups.toString(), size, userGroups.size());

		UserGroup lastUserGroup = userGroups.get(userGroups.size() - 1);

		userGroups = _userGroupService.getGtUserGroups(
			lastUserGroup.getUserGroupId(), TestPropsValues.getCompanyId(),
			parentUserGroupId, size);

		Assert.assertFalse(userGroups.isEmpty());
		Assert.assertEquals(userGroups.toString(), size, userGroups.size());

		long previousUserGroupId = 0;

		for (UserGroup userGroup : userGroups) {
			long userGroupId = userGroup.getUserGroupId();

			Assert.assertTrue(userGroupId > lastUserGroup.getUserGroupId());
			Assert.assertTrue(userGroupId > previousUserGroupId);

			previousUserGroupId = userGroupId;
		}
	}

	@Inject
	private static UserGroupService _userGroupService;

	@DeleteAfterTestRun
	private final List<UserGroup> _userGroups = new ArrayList<>();

}
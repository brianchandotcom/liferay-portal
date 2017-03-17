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

package com.liferay.portal.service.persistence;

import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.model.UserGroupGroupRole;
import com.liferay.portal.kernel.service.UserGroupGroupRoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.service.persistence.UserGroupGroupRoleFinderUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.UserGroupTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Preston Crary
 */
public class UserGroupGroupRoleFinderTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), TransactionalTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
		_role = RoleTestUtil.addRole(RoleConstants.TYPE_SITE);
		_user = UserTestUtil.addUser();
		_userGroup = UserGroupTestUtil.addUserGroup();

		UserLocalServiceUtil.addUserGroupUser(
			_userGroup.getUserGroupId(), _user.getUserId());

		long[] roleIds = new long[] {_role.getRoleId()};

		UserGroupGroupRoleLocalServiceUtil.addUserGroupGroupRoles(
			_userGroup.getUserGroupId(), _group.getGroupId(), roleIds);
	}

	@Test
	public void testFindByUserGroupsUsers() {
		List<UserGroupGroupRole> userGroupGroupRoles =
			UserGroupGroupRoleFinderUtil.findByUserGroupsUsers(
				_user.getUserId(), _group.getGroupId());

		Assert.assertEquals(
			userGroupGroupRoles.toString(), 1, userGroupGroupRoles.size());

		UserGroupGroupRole userGroupGroupRole = userGroupGroupRoles.get(0);

		Assert.assertEquals(
			_userGroup.getUserGroupId(), userGroupGroupRole.getUserGroupId());
		Assert.assertEquals(
			_group.getGroupId(), userGroupGroupRole.getGroupId());
		Assert.assertEquals(_role.getRoleId(), userGroupGroupRole.getRoleId());
	}

	@DeleteAfterTestRun
	private Group _group;

	@DeleteAfterTestRun
	private Role _role;

	@DeleteAfterTestRun
	private User _user;

	@DeleteAfterTestRun
	private UserGroup _userGroup;

}
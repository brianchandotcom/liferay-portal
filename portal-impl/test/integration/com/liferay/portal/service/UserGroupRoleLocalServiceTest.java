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

package com.liferay.portal.service;

import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroupRole;
import com.liferay.portal.service.persistence.UserGroupRolePK;
import com.liferay.portal.service.persistence.UserGroupRoleUtil;
import com.liferay.portal.test.DeleteAfterTestRun;
import com.liferay.portal.test.TransactionalTestRule;
import com.liferay.portal.test.listeners.MainServletExecutionTestListener;
import com.liferay.portal.test.runners.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.util.test.GroupTestUtil;
import com.liferay.portal.util.test.TestPropsValues;
import com.liferay.portal.util.test.UserTestUtil;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Pei-Jung Lan
 */
@ExecutionTestListeners(listeners = {MainServletExecutionTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class UserGroupRoleLocalServiceTest {

	@ClassRule
	public static TransactionalTestRule transactionalTestRule =
		new TransactionalTestRule();

	@Test
	public void testAddUserGroupRole() throws Throwable {
		_group = GroupTestUtil.addGroup();

		_user = UserTestUtil.addUser(null, _group.getGroupId());

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.SITE_ADMINISTRATOR);

		UserGroupRolePK userGroupRolePK = new UserGroupRolePK(
			_user.getUserId(), _group.getGroupId(), role.getRoleId());

		UserGroupRole userGroupRole = addUserGroupRole(userGroupRolePK);

		EntityCacheUtil.clearLocalCache();

		Assert.assertEquals(
			userGroupRole,
			UserGroupRoleUtil.fetchByPrimaryKey(userGroupRolePK));
	}

	protected UserGroupRole addUserGroupRole(UserGroupRolePK userGroupRolePK) {
		UserGroupRole userGroupRole = UserGroupRoleUtil.fetchByPrimaryKey(
			userGroupRolePK);

		Assert.assertNull(userGroupRole);

		userGroupRole = UserGroupRoleUtil.create(userGroupRolePK);

		userGroupRole = UserGroupRoleUtil.update(userGroupRole);

		return userGroupRole;
	}

	@DeleteAfterTestRun
	private Group _group;

	@DeleteAfterTestRun
	private User _user;

}
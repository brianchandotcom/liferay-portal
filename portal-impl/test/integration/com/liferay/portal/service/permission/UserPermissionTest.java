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

package com.liferay.portal.service.permission;

import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.service.permission.UserPermissionUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Pei-Jung Lan
 */
public class UserPermissionTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_user = UserTestUtil.addUser();
	}

	@Test
	public void testViewUserPermission() throws Exception {
		_role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		RoleTestUtil.addResourcePermission(
			_role, User.class.getName(), ResourceConstants.SCOPE_COMPANY,
			String.valueOf(_user.getCompanyId()), ActionKeys.VIEW);

		UserLocalServiceUtil.addRoleUser(_role.getRoleId(), _user);

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_user);

		Assert.assertTrue(
			UserPermissionUtil.contains(
				permissionChecker, ResourceConstants.PRIMKEY_DNE,
				ActionKeys.VIEW));
	}

	@DeleteAfterTestRun
	private Role _role;

	@DeleteAfterTestRun
	private User _user;

}
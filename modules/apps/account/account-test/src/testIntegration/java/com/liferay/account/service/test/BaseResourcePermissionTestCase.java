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

package com.liferay.account.service.test;

import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;

import org.junit.After;
import org.junit.Before;

/**
 * @author Pei-Jung Lan
 */
public abstract class BaseResourcePermissionTestCase {

	@Before
	public void setUp() throws Exception {
		user = UserTestUtil.addUser();

		UserTestUtil.setUser(user);

		permissionChecker = PermissionThreadLocal.getPermissionChecker();
	}

	@After
	public void tearDown() throws Exception {
		UserTestUtil.setUser(TestPropsValues.getUser());
	}

	protected void addResourcePermission(String resourceName, String actionId)
		throws Exception {

		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		RoleTestUtil.addResourcePermission(
			role, resourceName, ResourceConstants.SCOPE_COMPANY,
			String.valueOf(TestPropsValues.getCompanyId()), actionId);

		UserLocalServiceUtil.addRoleUser(role.getRoleId(), user);
	}

	protected PermissionChecker permissionChecker;
	protected User user;

}
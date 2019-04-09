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

package com.liferay.headless.foundation.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.foundation.dto.v1_0.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.UserTestUtil;

import java.util.Objects;

import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@RunWith(Arquillian.class)
public class RoleResourceTest extends BaseRoleResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_user = UserTestUtil.addGroupAdminUser(testGroup);
	}

	@Override
	protected void assertValid(Role role) {
		boolean valid = false;

		if ((role.getId() != null) && (role.getName() != null)) {
			valid = true;
		}

		Assert.assertTrue(valid);
	}

	@Override
	protected boolean equals(Role role1, Role role2) {
		if (Objects.equals(role1.getName(), role2.getName())) {
			return true;
		}

		return false;
	}

	@Override
	protected Role testGetMyUserAccountRolesPage_addRole(
			Long userAccountId, Role role)
		throws Exception {

		return _addRole(role);
	}

	@Override
	protected Long testGetMyUserAccountRolesPage_getUserAccountId()
		throws Exception {

		return _user.getUserId();
	}

	@Override
	protected Role testGetRole_addRole() throws Exception {
		return _addRole(randomRole());
	}

	@Override
	protected Role testGetUserAccountRolesPage_addRole(
			Long userAccountId, Role role)
		throws Exception {

		return _addRole(role);
	}

	@Override
	protected Long testGetUserAccountRolesPage_getUserAccountId()
		throws Exception {

		return _user.getUserId();
	}

	private Role _addRole(Role role) throws Exception {
		RoleLocalServiceUtil.deleteUserRole(
			_user.getUserId(),
			RoleLocalServiceUtil.getRole(
				testGroup.getCompanyId(), RoleConstants.USER));

		com.liferay.portal.kernel.model.Role serviceBuilderRole =
			RoleLocalServiceUtil.addRole(
				_user.getUserId(), null, 0, role.getName(), null, null, 0, null,
				new ServiceContext());

		RoleLocalServiceUtil.addUserRole(_user.getUserId(), serviceBuilderRole);

		return _toRole(serviceBuilderRole);
	}

	private Role _toRole(com.liferay.portal.kernel.model.Role role) {
		return new Role() {
			{
				description = role.getDescription();
				id = role.getRoleId();
				name = role.getName();
			}
		};
	}

	@DeleteAfterTestRun
	private User _user;

}
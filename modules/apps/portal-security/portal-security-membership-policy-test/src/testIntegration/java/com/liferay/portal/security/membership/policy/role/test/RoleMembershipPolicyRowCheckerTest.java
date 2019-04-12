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

package com.liferay.portal.security.membership.policy.role.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.security.membership.policy.role.BaseRoleMembershipPolicyTestCase;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portlet.RenderResponseFactory;
import com.liferay.portlet.rolesadmin.search.SetUserRoleChecker;
import com.liferay.portlet.rolesadmin.search.UnsetUserRoleChecker;

import javax.portlet.RenderResponse;
import javax.portlet.filter.RenderResponseWrapper;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Roberto Díaz
 */
@RunWith(Arquillian.class)
public class RoleMembershipPolicyRowCheckerTest
	extends BaseRoleMembershipPolicyTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testIsCheckerDisabledWhenSettingForbiddenRoleToUser()
		throws Exception {

		long forbiddenRoleId = addForbiddenRoles()[0];

		Role forbiddenRole = RoleLocalServiceUtil.getRole(forbiddenRoleId);

		SetUserRoleChecker setUserRoleChecker = new SetUserRoleChecker(
			_renderResponse, forbiddenRole);

		User user = UserTestUtil.addUser();

		Assert.assertTrue(setUserRoleChecker.isDisabled(user));
	}

	@Test
	public void testIsCheckerDisabledWhenSettingRequiredRoleToUser()
		throws Exception {

		long requiredRoleId = addRequiredRoles()[0];

		Role requiredRole = RoleLocalServiceUtil.getRole(requiredRoleId);

		SetUserRoleChecker setUserRoleChecker = new SetUserRoleChecker(
			_renderResponse, requiredRole);

		User user = UserTestUtil.addUser();

		Assert.assertFalse(setUserRoleChecker.isDisabled(user));
	}

	@Test
	public void testIsCheckerDisabledWhenUnsettingForbiddenRoleToUser()
		throws Exception {

		long forbiddenRoleId = addForbiddenRoles()[0];

		Role forbiddenRole = RoleLocalServiceUtil.getRole(forbiddenRoleId);

		UnsetUserRoleChecker unsetUserRoleChecker = new UnsetUserRoleChecker(
			_renderResponse, forbiddenRole);

		User user = UserTestUtil.addUser();

		RoleLocalServiceUtil.addUserRole(user.getUserId(), forbiddenRoleId);

		Assert.assertFalse(unsetUserRoleChecker.isDisabled(user));
	}

	@Test
	public void testIsCheckerDisabledWhenUnsettingRequiredRoleToUser()
		throws Exception {

		long requiredRoleId = addRequiredRoles()[0];

		Role requiredRole = RoleLocalServiceUtil.getRole(requiredRoleId);

		UnsetUserRoleChecker unsetUserRoleChecker = new UnsetUserRoleChecker(
			_renderResponse, requiredRole);

		User user = UserTestUtil.addUser();

		RoleLocalServiceUtil.addUserRole(user.getUserId(), requiredRoleId);

		Assert.assertTrue(unsetUserRoleChecker.isDisabled(user));
	}

	private static final RenderResponse _renderResponse =
		new RenderResponseWrapper(RenderResponseFactory.create()) {

			@Override
			public String getNamespace() {
				return RandomTestUtil.randomString();
			}

		};

}
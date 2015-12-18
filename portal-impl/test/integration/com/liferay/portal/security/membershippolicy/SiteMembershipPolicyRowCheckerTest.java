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

package com.liferay.portal.security.membershippolicy;

import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.UserGroupRoleLocalServiceUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portlet.sites.search.UserGroupRoleRoleChecker;
import com.liferay.portlet.sites.search.UserGroupRoleUserChecker;
import com.liferay.portlet.sitesadmin.search.SiteMembershipChecker;

import javax.portlet.RenderResponse;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.powermock.api.mockito.PowerMockito;

/**
 * @author Roberto Díaz
 */
public class SiteMembershipPolicyRowCheckerTest
	extends BaseSiteMembershipPolicyTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testIsCheckerDisabledWhenSettingForbiddenGroupToUser()
		throws Exception {

		RenderResponse renderResponse = PowerMockito.mock(RenderResponse.class);

		long forbiddenGroupId = addForbiddenGroups()[0];

		Group forbiddenGroup = GroupLocalServiceUtil.getGroup(forbiddenGroupId);

		SiteMembershipChecker siteMembershipChecker = new SiteMembershipChecker(
			renderResponse, forbiddenGroup);

		User user = UserTestUtil.addUser();

		Assert.assertTrue(siteMembershipChecker.isDisabled(user));
	}

	@Test
	public void testIsCheckerDisabledWhenSettingForbiddenRoleToUser()
		throws Exception {

		RenderResponse renderResponse = PowerMockito.mock(RenderResponse.class);

		long forbiddenRoleId = addForbiddenRoles()[0];

		Role role = RoleLocalServiceUtil.getRole(forbiddenRoleId);

		UserGroupRoleUserChecker userGroupRoleUserChecker =
			new UserGroupRoleUserChecker(renderResponse, group, role);

		User user = UserTestUtil.addUser();

		Assert.assertTrue(userGroupRoleUserChecker.isDisabled(user));
	}

	@Test
	public void testIsCheckerDisabledWhenSettingRequiredGroupToUser()
		throws Exception {

		RenderResponse renderResponse = PowerMockito.mock(RenderResponse.class);

		long requiredGroupId = addRequiredGroups()[0];

		Group requiredGroup = GroupLocalServiceUtil.getGroup(requiredGroupId);

		SiteMembershipChecker siteMembershipChecker = new SiteMembershipChecker(
			renderResponse, requiredGroup);

		User user = UserTestUtil.addUser();

		Assert.assertFalse(siteMembershipChecker.isDisabled(user));
	}

	@Test
	public void testIsCheckerDisabledWhenSettingRequiredRoleToUser()
		throws Exception {

		RenderResponse renderResponse = PowerMockito.mock(RenderResponse.class);

		long requiredRoleId = addRequiredRoles()[0];

		Role role = RoleLocalServiceUtil.getRole(requiredRoleId);

		UserGroupRoleUserChecker userGroupRoleUserChecker =
			new UserGroupRoleUserChecker(renderResponse, group, role);

		User user = UserTestUtil.addUser();

		Assert.assertFalse(userGroupRoleUserChecker.isDisabled(user));
	}

	@Test
	public void testIsCheckerDisabledWhenSettingUserToForbiddenRole()
		throws Exception {

		RenderResponse renderResponse = PowerMockito.mock(RenderResponse.class);

		User user = UserTestUtil.addUser();

		UserGroupRoleRoleChecker userGroupRoleRoleChecker =
			new UserGroupRoleRoleChecker(renderResponse, user, group);

		Role role = RoleLocalServiceUtil.getRole(addForbiddenRoles()[0]);

		Assert.assertTrue(userGroupRoleRoleChecker.isDisabled(role));
	}

	@Test
	public void testIsCheckerDisabledWhenSettingUserToRequiredRole()
		throws Exception {

		RenderResponse renderResponse = PowerMockito.mock(RenderResponse.class);

		User user = UserTestUtil.addUser();

		UserGroupRoleRoleChecker userGroupRoleRoleChecker =
			new UserGroupRoleRoleChecker(renderResponse, user, group);

		Role role = RoleLocalServiceUtil.getRole(addRequiredRoles()[0]);

		Assert.assertFalse(userGroupRoleRoleChecker.isDisabled(role));
	}

	@Test
	public void testIsCheckerDisabledWhenUnsettingForbiddenGroupFromUser()
		throws Exception {

		RenderResponse renderResponse = PowerMockito.mock(RenderResponse.class);

		long forbiddenGroupId = addForbiddenGroups()[0];

		Group forbiddenGroup = GroupLocalServiceUtil.getGroup(forbiddenGroupId);

		SiteMembershipChecker siteMembershipChecker = new SiteMembershipChecker(
			renderResponse, forbiddenGroup);

		User user = UserTestUtil.addUser(forbiddenGroupId);

		Assert.assertFalse(siteMembershipChecker.isDisabled(user));
	}

	@Test
	public void testIsCheckerDisabledWhenUnsettingForbiddenRoleFromUser()
		throws Exception {

		RenderResponse renderResponse = PowerMockito.mock(RenderResponse.class);

		long forbiddenRoleId = addForbiddenRoles()[0];

		Role role = RoleLocalServiceUtil.getRole(forbiddenRoleId);

		UserGroupRoleUserChecker userGroupRoleUserChecker =
			new UserGroupRoleUserChecker(renderResponse, group, role);

		User user = UserTestUtil.addUser();

		UserGroupRoleLocalServiceUtil.addUserGroupRoles(
			user.getUserId(), group.getGroupId(), new long[] {forbiddenRoleId});

		Assert.assertFalse(userGroupRoleUserChecker.isDisabled(user));
	}

	@Test
	public void testIsCheckerDisabledWhenUnsettingRequiredGroupFromUser()
		throws Exception {

		RenderResponse renderResponse = PowerMockito.mock(RenderResponse.class);

		long requiredGroupId = addRequiredGroups()[0];

		Group requiredGroup = GroupLocalServiceUtil.getGroup(requiredGroupId);

		SiteMembershipChecker siteMembershipChecker = new SiteMembershipChecker(
			renderResponse, requiredGroup);

		User user = UserTestUtil.addUser(requiredGroupId);

		Assert.assertTrue(siteMembershipChecker.isDisabled(user));
	}

	@Test
	public void testIsCheckerDisabledWhenUnsettingRequiredRoleFromUser()
		throws Exception {

		RenderResponse renderResponse = PowerMockito.mock(RenderResponse.class);

		long requiredRoleId = addRequiredRoles()[0];

		Role role = RoleLocalServiceUtil.getRole(requiredRoleId);

		UserGroupRoleUserChecker userGroupRoleUserChecker =
			new UserGroupRoleUserChecker(renderResponse, group, role);

		User user = UserTestUtil.addUser();

		UserGroupRoleLocalServiceUtil.addUserGroupRoles(
			user.getUserId(), group.getGroupId(), new long[] {requiredRoleId});

		Assert.assertTrue(userGroupRoleUserChecker.isDisabled(user));
	}

	@Test
	public void testIsCheckerDisabledWhenUnsettingUserFromForbiddenRole()
		throws Exception {

		RenderResponse renderResponse = PowerMockito.mock(RenderResponse.class);

		User user = UserTestUtil.addUser();

		UserGroupRoleRoleChecker userGroupRoleRoleChecker =
			new UserGroupRoleRoleChecker(renderResponse, user, group);

		long forbiddenRoleId = addForbiddenRoles()[0];

		Role role = RoleLocalServiceUtil.getRole(forbiddenRoleId);

		UserGroupRoleLocalServiceUtil.addUserGroupRoles(
			user.getUserId(), group.getGroupId(), new long[] {forbiddenRoleId});

		Assert.assertFalse(userGroupRoleRoleChecker.isDisabled(role));
	}

	@Test
	public void testIsCheckerDisabledWhenUnsettingUserFromRequiredRole()
		throws Exception {

		RenderResponse renderResponse = PowerMockito.mock(RenderResponse.class);

		User user = UserTestUtil.addUser();

		UserGroupRoleRoleChecker userGroupRoleRoleChecker =
			new UserGroupRoleRoleChecker(renderResponse, user, group);

		long requiredRoleId = addRequiredRoles()[0];

		Role role = RoleLocalServiceUtil.getRole(requiredRoleId);

		UserGroupRoleLocalServiceUtil.addUserGroupRoles(
			user.getUserId(), group.getGroupId(), new long[] {requiredRoleId});

		Assert.assertTrue(userGroupRoleRoleChecker.isDisabled(role));
	}

}
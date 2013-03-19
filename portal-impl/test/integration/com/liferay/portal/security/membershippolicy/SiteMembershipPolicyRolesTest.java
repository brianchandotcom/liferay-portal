/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroupRole;
import com.liferay.portal.security.membershippolicy.util.MembershipPolicyTestUtil;
import com.liferay.portal.service.UserGroupRoleLocalServiceUtil;
import com.liferay.portal.service.UserGroupRoleServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.persistence.UserGroupRolePK;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Roberto Díaz
 */
public class SiteMembershipPolicyRolesTest
	extends BaseSiteMembershipPolicyTestCase {

	@Test(expected = MembershipPolicyException.class)
	public void testAssignUsersToForbiddenRole() throws Exception {

		long[] forbiddenRoleIds = addForbiddenRoles();

		Group group = getGroup();

		UserGroupRoleServiceUtil.addUserGroupRoles(
			addUsers(), group.getGroupId(), forbiddenRoleIds[0]);
	}

	@Test(expected = MembershipPolicyException.class)
	public void testAssignUserToForbiddenRole() throws Exception {
		long[] userIds = addUsers();
		long[] forbiddenRoleIds = addForbiddenRoles();

		Group group = getGroup();

		UserGroupRolePK userGroupRolePK = new UserGroupRolePK(
			userIds[0], group.getGroupId(), forbiddenRoleIds[0]);

		List<UserGroupRole> userGroupRoleList = new ArrayList<UserGroupRole>();

		UserGroupRole userGroupRole =
			UserGroupRoleLocalServiceUtil.createUserGroupRole(userGroupRolePK);

		userGroupRoleList.add(userGroupRole);

		User user = UserLocalServiceUtil.getUser(userIds[0]);

		MembershipPolicyTestUtil.updateUser(
			user, null, null, null, null, userGroupRoleList);
	}

	@Test(expected = MembershipPolicyException.class)
	public void testAssignUserToForbiddenRoles() throws Exception {

		long[] userIds = addUsers();

		Group group = getGroup();

		UserGroupRoleServiceUtil.addUserGroupRoles(
			userIds[0], group.getGroupId(), addForbiddenRoles());

	}

	@Test
	public void testPropagateWhenAssigningRolesToUser() throws Exception {
		long[] userIds = addUsers();
		long[] standardRoleIds = addStandardRoles();

		Group group = getGroup();

		UserGroupRolePK userGroupRolePK = new UserGroupRolePK(
			userIds[0], group.getGroupId(), standardRoleIds[0]);

		List<UserGroupRole> userGroupRoleList = new ArrayList<UserGroupRole>();

		UserGroupRole userGroupRole =
			UserGroupRoleLocalServiceUtil.createUserGroupRole(userGroupRolePK);

		userGroupRoleList.add(userGroupRole);

		User user = UserLocalServiceUtil.getUser(userIds[0]);

		MembershipPolicyTestUtil.updateUser(
			user, null, null, null, null, userGroupRoleList);

		Assert.assertTrue(getPropagateRolesMethodFlag());
	}

	@Test
	public void testPropagateWhenAssigningUsersToRole() throws Exception {
		long[] standardRoleIds = addStandardRoles();

		Group group = getGroup();

		UserGroupRoleServiceUtil.addUserGroupRoles(
			addUsers(), group.getGroupId(), standardRoleIds[0]);

		Assert.assertTrue(getPropagateRolesMethodFlag());
	}

	@Test
	public void testPropagateWhenAssigningUserToRoles() throws Exception {
		long[] userIds = addUsers();

		Group group = getGroup();

		UserGroupRoleServiceUtil.addUserGroupRoles(
			userIds[0], group.getGroupId(), addStandardRoles());

		Assert.assertTrue(getPropagateRolesMethodFlag());
	}

	@Test
	public void testPropagateWhenUnassigningRolesFromUser() throws Exception {
		long[] userIds = addUsers();

		Group group = getGroup();
		User user = UserLocalServiceUtil.getUser(userIds[0]);

		UserGroupRoleServiceUtil.addUserGroupRoles(
			user.getUserId(), group.getGroupId(), addStandardRoles());

		MembershipPolicyTestUtil.updateUser(
			user, null, null, null, null,
			Collections.<UserGroupRole>emptyList());

		Assert.assertTrue(getPropagateRolesMethodFlag());
	}

	@Test
	public void testPropagateWhenUnassigningUserFromRoles() throws Exception {
		long[] userIds = addUsers();

		Group group = getGroup();

		UserGroupRoleServiceUtil.deleteUserGroupRoles(
			userIds[0], group.getGroupId(), addStandardRoles());

		Assert.assertTrue(getPropagateRolesMethodFlag());
	}

	@Test
	public void testUnassignRequiredRolesFromUser() throws Exception {
		long[] userIds = addUsers();

		Group group = getGroup();

		UserGroupRoleServiceUtil.addUserGroupRoles(
			userIds[0], group.getGroupId(), addRequiredRoles());

		User user = UserLocalServiceUtil.getUser(userIds[0]);

		List<UserGroupRole> initialUserGroupRoles =
			UserGroupRoleLocalServiceUtil.getUserGroupRoles(user.getUserId());

		List<UserGroupRole> emptyNonAbstractList =
			new ArrayList<UserGroupRole>();

		MembershipPolicyTestUtil.updateUser(
			user, null, null, null, null, emptyNonAbstractList);

		List<UserGroupRole> currentUserGroupRoles =
			UserGroupRoleLocalServiceUtil.getUserGroupRoles(user.getUserId());

		Assert.assertEquals(
			initialUserGroupRoles.size(), currentUserGroupRoles.size());
	}

	@Test(expected = MembershipPolicyException.class)
	public void testUnassignUserFromRequiredRoles() throws Exception {
		long[] userIds = addUsers();

		Group group = getGroup();

		UserGroupRoleServiceUtil.deleteUserGroupRoles(
			userIds[0], group.getGroupId(), addRequiredRoles());
	}

	@Test(expected = MembershipPolicyException.class)
	public void testUnassignUsersFromRequiredRole() throws Exception {
		long[] requiredRoleIds = addRequiredRoles();

		Group group = getGroup();

		UserGroupRoleServiceUtil.deleteUserGroupRoles(
			addUsers(), group.getGroupId(), requiredRoleIds[0]);
	}

	@Test
	public void testUnassignUsersFromRole() throws Exception {
		long[] standardRoleIds = addStandardRoles();

		Group group = getGroup();

		UserGroupRoleServiceUtil.deleteUserGroupRoles(
			addUsers(), group.getGroupId(), standardRoleIds[0]);

		Assert.assertTrue(getPropagateRolesMethodFlag());
	}

}
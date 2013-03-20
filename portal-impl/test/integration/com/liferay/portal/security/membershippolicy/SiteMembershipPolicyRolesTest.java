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

import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroupRole;
import com.liferay.portal.security.membershippolicy.util.MembershipPolicyTestUtil;
import com.liferay.portal.service.UserGroupRoleLocalServiceUtil;
import com.liferay.portal.service.UserGroupRoleServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.persistence.UserGroupRolePK;
import com.liferay.portal.util.GroupTestUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Roberto Díaz
 */
public class SiteMembershipPolicyRolesTest
	extends BaseSiteMembershipPolicyTestCase {

	@Before
	public void setUp() throws Exception {
		FinderCacheUtil.clearCache();

		_group = GroupTestUtil.addGroup();
	}

	@Test(expected = MembershipPolicyException.class)
	public void testAssignUsersToForbiddenRole() throws Exception {

		long[] forbiddenRoleIds = addForbiddenRoles();

		UserGroupRoleServiceUtil.addUserGroupRoles(
			addUsers(), _group.getGroupId(), forbiddenRoleIds[0]);
	}

	@Test(expected = MembershipPolicyException.class)
	public void testAssignUserToForbiddenRole() throws Exception {
		long[] userIds = addUsers();
		long[] forbiddenRoleIds = addForbiddenRoles();

		UserGroupRolePK userGroupRolePK = new UserGroupRolePK(
			userIds[0], _group.getGroupId(), forbiddenRoleIds[0]);

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

		UserGroupRoleServiceUtil.addUserGroupRoles(
			userIds[0], _group.getGroupId(), addForbiddenRoles());

	}

	@Test
	public void testPropagateWhenAssigningRolesToUser() throws Exception {
		long[] userIds = addUsers();
		long[] standardRoleIds = addStandardRoles();

		UserGroupRolePK userGroupRolePK = new UserGroupRolePK(
			userIds[0], _group.getGroupId(), standardRoleIds[0]);

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

		UserGroupRoleServiceUtil.addUserGroupRoles(
			addUsers(), _group.getGroupId(), standardRoleIds[0]);

		Assert.assertTrue(getPropagateRolesMethodFlag());
	}

	@Test
	public void testPropagateWhenAssigningUserToRoles() throws Exception {
		long[] userIds = addUsers();

		UserGroupRoleServiceUtil.addUserGroupRoles(
			userIds[0], _group.getGroupId(), addStandardRoles());

		Assert.assertTrue(getPropagateRolesMethodFlag());
	}

	@Test
	public void testPropagateWhenUnassigningRolesFromUser() throws Exception {
		long[] userIds = addUsers();

		User user = UserLocalServiceUtil.getUser(userIds[0]);

		UserGroupRoleServiceUtil.addUserGroupRoles(
			user.getUserId(), _group.getGroupId(), addStandardRoles());

		MembershipPolicyTestUtil.updateUser(
			user, null, null, null, null,
			Collections.<UserGroupRole>emptyList());

		Assert.assertTrue(getPropagateRolesMethodFlag());
	}

	@Test
	public void testPropagateWhenUnassigningUserFromRoles() throws Exception {
		long[] userIds = addUsers();

		UserGroupRoleServiceUtil.deleteUserGroupRoles(
			userIds[0], _group.getGroupId(), addStandardRoles());

		Assert.assertTrue(getPropagateRolesMethodFlag());
	}

	@Test
	public void testUnassignRequiredRolesFromUser() throws Exception {
		long[] userIds = addUsers();

		UserGroupRoleServiceUtil.addUserGroupRoles(
			userIds[0], _group.getGroupId(), addRequiredRoles());

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

		UserGroupRoleServiceUtil.deleteUserGroupRoles(
			userIds[0], _group.getGroupId(), addRequiredRoles());
	}

	@Test(expected = MembershipPolicyException.class)
	public void testUnassignUsersFromRequiredRole() throws Exception {
		long[] requiredRoleIds = addRequiredRoles();

		UserGroupRoleServiceUtil.deleteUserGroupRoles(
			addUsers(), _group.getGroupId(), requiredRoleIds[0]);
	}

	@Test
	public void testUnassignUsersFromRole() throws Exception {
		long[] standardRoleIds = addStandardRoles();

		UserGroupRoleServiceUtil.deleteUserGroupRoles(
			addUsers(), _group.getGroupId(), standardRoleIds[0]);

		Assert.assertTrue(getPropagateRolesMethodFlag());
	}

	private Group _group = null;

}
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

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroupRole;
import com.liferay.portal.security.membershippolicy.util.MembershipPolicyTestUtil;
import com.liferay.portal.service.GroupServiceUtil;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.UserServiceUtil;

import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Roberto Díaz
 */
public class SiteMembershipPolicyMembershipsTest
	extends BaseSiteMembershipPolicyTestCase {

	@Test(expected = MembershipPolicyException.class)
	public void testAddUserToForbiddenSites() throws Exception {
		MembershipPolicyTestUtil.addUser(null, null, addForbiddenSites(), null);
	}

	public void testAddUserToRequiredSites() throws Exception {
		long[] requiredSiteIds = addRequiredSites();

		int initialUserGroupCount = UserLocalServiceUtil.getGroupUsersCount(
			requiredSiteIds[0]);

		MembershipPolicyTestUtil.addUser(
			null, null, new long[] {requiredSiteIds[0]}, null);

		Assert.assertEquals(
			initialUserGroupCount + 1,
			UserLocalServiceUtil.getGroupUsersCount(requiredSiteIds[0]));
	}

	@Test(expected = MembershipPolicyException.class)
	public void testAssignUsersToForbiddenSite() throws Exception {
		long[] forbiddenSiteIds = addForbiddenSites();

		UserServiceUtil.addGroupUsers(
			forbiddenSiteIds[0], addUsers(),
			ServiceTestUtil.getServiceContext());
	}

	@Test
	public void testAssignUsersToRequiredSite() throws Exception {
		long[] requiredSiteIds = addRequiredSites();

		int initialUserGroupCountInRequiredSite =
			UserLocalServiceUtil.getGroupUsersCount(requiredSiteIds[0]);

		UserServiceUtil.addGroupUsers(
			requiredSiteIds[0], addUsers(),
			ServiceTestUtil.getServiceContext());

		Assert.assertEquals(
			initialUserGroupCountInRequiredSite + 2,
			UserLocalServiceUtil.getGroupUsersCount(requiredSiteIds[0]));
		Assert.assertTrue(getPropagateMembershipMethodFlag());
	}

	@Test(expected = MembershipPolicyException.class)
	public void testAssignUserToForbiddenSites() throws Exception {
		long[] userIds = addUsers();

		User user = UserLocalServiceUtil.getUser(userIds[0]);

		MembershipPolicyTestUtil.updateUser(
			user, null, null, addForbiddenSites(), null,
			Collections.<UserGroupRole>emptyList());
	}

	@Test
	public void testAssignUserToRequiredSites() throws Exception {
		long[] userIds = addUsers();
		long[] requiredSiteIds = addRequiredSites();

		int initialUserGroupCount = UserLocalServiceUtil.getGroupUsersCount(
			requiredSiteIds[0]);

		User user = UserLocalServiceUtil.getUser(userIds[0]);

		MembershipPolicyTestUtil.updateUser(
			user, null, null, new long[]{requiredSiteIds[0]}, null,
			Collections.<UserGroupRole>emptyList());

		Assert.assertEquals(
			initialUserGroupCount + 1,
			UserLocalServiceUtil.getGroupUsersCount(requiredSiteIds[0]));
		Assert.assertTrue(getPropagateMembershipMethodFlag());
	}

	@Test
	public void testPropagateWhenAddingUserToRequiredSites() throws Exception {
		MembershipPolicyTestUtil.addUser(null, null, addRequiredSites(), null);

		Assert.assertTrue(getPropagateMembershipMethodFlag());
	}

	@Test
	public void testPropagateWhenAssigningUsersToRequiredSite()
		throws Exception {

		long[] requiredSiteIds = addRequiredSites();

		UserServiceUtil.addGroupUsers(
			requiredSiteIds[0], addUsers(),
			ServiceTestUtil.getServiceContext());

		Assert.assertTrue(getPropagateMembershipMethodFlag());
	}

	@Test
	public void testPropagateWhenAssigningUserToRequiredSites()
		throws Exception {

		long[] userIds = addUsers();

		User user = UserLocalServiceUtil.getUser(userIds[0]);

		MembershipPolicyTestUtil.updateUser(
			user, null, null, addRequiredSites(), null,
			Collections.<UserGroupRole>emptyList());

		Assert.assertTrue(getPropagateMembershipMethodFlag());
	}

	@Test
	public void testUnassignUserFromRequiredSites() throws Exception {
		long[] userIds = addUsers();
		long[] standardSiteIds = addStandardSites();
		long[] requiredSiteIds = addRequiredSites();

		User user = UserLocalServiceUtil.getUser(userIds[0]);

		// Users always have the personal site

		List<Group> groups =  user.getGroups();

		Assert.assertEquals(1, groups.size());

		long[] userGroupIds = ArrayUtil.append(
			standardSiteIds, requiredSiteIds, new long[] {user.getGroupId()});

		MembershipPolicyTestUtil.updateUser(
			user, null, null, userGroupIds, null,
			Collections.<UserGroupRole>emptyList());

		Assert.assertEquals(userGroupIds.length, user.getGroups().size());

		MembershipPolicyTestUtil.updateUser(
			user, null, null, requiredSiteIds, null,
			Collections.<UserGroupRole>emptyList());

		Assert.assertEquals(requiredSiteIds.length, user.getGroups().size());
	}

	@Test
	public void testUnassignUserFromSites() throws Exception {
		long[] userIds = addUsers();
		long[] standardSiteIds = addStandardSites();
		long[] requiredSiteIds = addRequiredSites();

		User user = UserLocalServiceUtil.getUser(userIds[0]);

		// Users always have the personal site

		List<Group> groups =  user.getGroups();

		Assert.assertEquals(1, groups.size());

		long[] userGroupIds = ArrayUtil.append(
			standardSiteIds, requiredSiteIds, new long[] {user.getGroupId()});

		MembershipPolicyTestUtil.updateUser(
			user, null, null, userGroupIds, null,
			Collections.<UserGroupRole>emptyList());

		groups =  user.getGroups();

		Assert.assertEquals(userGroupIds.length, groups.size());

		MembershipPolicyTestUtil.updateUser(
			user, null, null, standardSiteIds, null,
			Collections.<UserGroupRole>emptyList());

		// We have removed the user from her personal site but the required
		// sites are kept

		groups =  user.getGroups();

		Assert.assertEquals(userGroupIds.length - 1, groups.size());
	}

	@Test(expected = MembershipPolicyException.class)
	public void testUnassignUsersFromRequiredSite() throws Exception {
		long[] requiredSiteIds = addRequiredSites();

		User user = MembershipPolicyTestUtil.addUser(
			null, null, requiredSiteIds, null);

		UserServiceUtil.unsetGroupUsers(
			requiredSiteIds[0], new long[]{user.getUserId()},
			ServiceTestUtil.getServiceContext());
	}

	@Test
	public void testUnassignUsersFromSite() throws Exception {
		long[] standardSiteIds = addStandardSites();

		User user = MembershipPolicyTestUtil.addUser(
			null, null, standardSiteIds, null);

		int initialUserGroupCount = UserLocalServiceUtil.getGroupUsersCount(
			standardSiteIds[0]);

		UserServiceUtil.unsetGroupUsers(
			standardSiteIds[0], new long[]{user.getUserId()},
			ServiceTestUtil.getServiceContext());

		Assert.assertEquals(
			initialUserGroupCount - 1,
			UserLocalServiceUtil.getGroupUsersCount(standardSiteIds[0]));
		Assert.assertTrue(getPropagateMembershipMethodFlag());
	}

	@Test
	public void testVerifyWhenAddingGroup() throws Exception {
		MembershipPolicyTestUtil.addGroup();

		Assert.assertTrue(getVerifyMethodFlag());
	}

	@Test
	public void testVerifyWhenUpdatingGroup() throws Exception {
		Group group = MembershipPolicyTestUtil.addGroup();

		GroupServiceUtil.updateGroup(
			group.getGroupId(), group.getParentGroupId(),
			ServiceTestUtil.randomString(), group.getDescription(),
			group.getType(), group.getFriendlyURL(), group.isActive(),
			ServiceTestUtil.getServiceContext());

		Assert.assertTrue(getVerifyMethodFlag());
	}

	@Test
	public void testVerifyWhenUpdatingGroupTypeSettings() throws Exception {
		Group group = MembershipPolicyTestUtil.addGroup();

		String typeSettings = ServiceTestUtil.randomString(50);

		GroupServiceUtil.updateGroup(group.getGroupId(), typeSettings);

		Assert.assertTrue(getVerifyMethodFlag());
	}

}
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

import com.liferay.announcements.kernel.service.AnnouncementsDeliveryLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.RequiredRoleException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.service.UserServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.OrganizationTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserGroupTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Michael C. Han
 */
public class UserLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testGetCompanyUsers() throws Exception {
		_company = CompanyTestUtil.addCompany();

		List<User> companyUsers = UserLocalServiceUtil.getCompanyUsers(
			_company.getCompanyId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(companyUsers.toString(), 1, companyUsers.size());

		User user = companyUsers.get(0);

		Assert.assertFalse(user.isDefaultUser());
	}

	@Test
	public void testGetGroupUsers() throws Exception {
		Group group = GroupTestUtil.addGroup();

		_addUsers(20);

		UserLocalServiceUtil.addGroupUsers(group.getGroupId(), _users);

		List<User> allGroupUsers = UserLocalServiceUtil.getGroupUsers(
			group.getGroupId());

		Assert.assertEquals(
			allGroupUsers.toString(), _users.size() + 1, allGroupUsers.size());
		Assert.assertTrue(allGroupUsers.containsAll(_users));

		int start = 5;
		int delta = 5;

		List<User> partialGroupUsers = UserLocalServiceUtil.getGroupUsers(
			group.getGroupId(), WorkflowConstants.STATUS_APPROVED, start,
			start + delta, null);

		Assert.assertEquals(
			partialGroupUsers.toString(), delta, partialGroupUsers.size());
		Assert.assertTrue(allGroupUsers.containsAll(partialGroupUsers));
	}

	@Test
	public void testGetNoAnnouncementsDeliveries() throws Exception {
		User user1 = UserTestUtil.addUser();

		_users.add(user1);

		User user2 = UserTestUtil.addUser();

		_users.add(user2);

		AnnouncementsDeliveryLocalServiceUtil.addUserDelivery(
			user1.getUserId(), "general");

		List<User> users = UserLocalServiceUtil.getNoAnnouncementsDeliveries(
			"general");

		Assert.assertFalse(users.toString(), users.contains(user1));
		Assert.assertTrue(users.toString(), users.contains(user2));
	}

	@Test
	public void testGetNoGroups() throws Exception {
		User user = UserTestUtil.addUser();

		_users.add(user);

		GroupLocalServiceUtil.deleteGroup(user.getGroupId());

		List<User> users = UserLocalServiceUtil.getNoGroups();

		Assert.assertTrue(users.toString(), users.contains(user));
	}

	@Test
	public void testGetOrganizationsAndUserGroupsUsersCount() throws Exception {
		long[] commonUserIds = _addUsers(5);

		int organizationIterations = 4;
		int uniqueOrganizationUsersCount = 0;

		long[] organizationIds = new long[organizationIterations];

		for (int i = 0; i < organizationIterations; i++) {
			long[] uniqueUserIds = _addUsers(organizationIterations);

			Organization organization = OrganizationTestUtil.addOrganization();

			_organizations.add(organization);

			UserServiceUtil.addOrganizationUsers(
				organization.getOrganizationId(), commonUserIds);
			UserServiceUtil.addOrganizationUsers(
				organization.getOrganizationId(), uniqueUserIds);

			organizationIds[i] = organization.getOrganizationId();

			uniqueOrganizationUsersCount += uniqueUserIds.length;
		}

		int uniqueUserGroupUsersCount = 0;

		int userGroupIterations = 3;

		long[] userGroupIds = new long[userGroupIterations];

		for (int i = 0; i < userGroupIterations; i++) {
			long[] uniqueUserIds = _addUsers(userGroupIterations);

			UserGroup userGroup = UserGroupTestUtil.addUserGroup();

			_userGroups.add(userGroup);

			UserServiceUtil.addUserGroupUsers(
				userGroup.getUserGroupId(), commonUserIds);
			UserServiceUtil.addUserGroupUsers(
				userGroup.getUserGroupId(), uniqueUserIds);

			userGroupIds[i] = userGroup.getUserGroupId();

			uniqueUserGroupUsersCount += uniqueUserIds.length;
		}

		long[] emptyLongArray = new long[0];

		Assert.assertEquals(
			0,
			UserLocalServiceUtil.getOrganizationsAndUserGroupsUsersCount(
				null, null));
		Assert.assertEquals(
			0,
			UserLocalServiceUtil.getOrganizationsAndUserGroupsUsersCount(
				emptyLongArray.clone(), null));
		Assert.assertEquals(
			0,
			UserLocalServiceUtil.getOrganizationsAndUserGroupsUsersCount(
				null, emptyLongArray.clone()));
		Assert.assertEquals(
			0,
			UserLocalServiceUtil.getOrganizationsAndUserGroupsUsersCount(
				emptyLongArray.clone(), emptyLongArray.clone()));

		int commonUsersCount = commonUserIds.length;

		Assert.assertEquals(
			commonUsersCount + uniqueOrganizationUsersCount +
				uniqueUserGroupUsersCount,
			UserLocalServiceUtil.getOrganizationsAndUserGroupsUsersCount(
				organizationIds, userGroupIds));

		Assert.assertEquals(
			commonUsersCount + uniqueOrganizationUsersCount,
			UserLocalServiceUtil.getOrganizationsAndUserGroupsUsersCount(
				organizationIds, null));
		Assert.assertEquals(
			commonUsersCount + uniqueOrganizationUsersCount,
			UserLocalServiceUtil.getOrganizationsAndUserGroupsUsersCount(
				organizationIds, emptyLongArray.clone()));
		Assert.assertEquals(
			commonUsersCount + uniqueUserGroupUsersCount,
			UserLocalServiceUtil.getOrganizationsAndUserGroupsUsersCount(
				null, userGroupIds));
		Assert.assertEquals(
			commonUsersCount + uniqueUserGroupUsersCount,
			UserLocalServiceUtil.getOrganizationsAndUserGroupsUsersCount(
				emptyLongArray.clone(), userGroupIds));
	}

	@Test
	public void testGetOrganizationUsers() throws Exception {
		Organization organization = OrganizationTestUtil.addOrganization();

		_addUsers(20);

		UserLocalServiceUtil.addOrganizationUsers(
			organization.getOrganizationId(), _users);

		List<User> organizationUsers =
			UserLocalServiceUtil.getOrganizationUsers(
				organization.getOrganizationId());

		Assert.assertEquals(
			organizationUsers.toString(), _users.size(),
			organizationUsers.size());
		Assert.assertTrue(organizationUsers.containsAll(_users));

		int start = 5;
		int delta = 5;

		organizationUsers = UserLocalServiceUtil.getOrganizationUsers(
			organization.getOrganizationId(), WorkflowConstants.STATUS_APPROVED,
			start, start + delta, null);

		Assert.assertEquals(
			organizationUsers.toString(), delta, organizationUsers.size());
		Assert.assertTrue(_users.containsAll(organizationUsers));
	}

	@Test
	public void testGetUserGroupUsers() throws Exception {
		UserGroup userGroup = UserGroupTestUtil.addUserGroup();

		_addUsers(20);

		UserLocalServiceUtil.addUserGroupUsers(
			userGroup.getUserGroupId(), _users);

		List<User> userGroupUsers = UserLocalServiceUtil.getUserGroupUsers(
			userGroup.getUserGroupId());

		Assert.assertEquals(
			userGroupUsers.toString(), _users.size(), userGroupUsers.size());
		Assert.assertTrue(userGroupUsers.containsAll(_users));

		int start = 5;
		int delta = 5;

		userGroupUsers = UserLocalServiceUtil.getUserGroupUsers(
			userGroup.getUserGroupId(), start, start + delta);

		Assert.assertEquals(
			userGroupUsers.toString(), delta, userGroupUsers.size());
		Assert.assertTrue(_users.containsAll(userGroupUsers));
	}

	@Test
	public void testSetRoleUsers() throws Exception {
		User user = UserTestUtil.addUser();

		long roleId = RoleTestUtil.addGroupRole(user.getGroupId());

		UserLocalServiceUtil.addRoleUser(roleId, user);

		user = UserLocalServiceUtil.getUser(user.getUserId());

		Assert.assertTrue(ArrayUtil.contains(user.getRoleIds(), roleId));
	}

	@Test
	public void testUnsetRoleUsers() throws Exception {
		User user = UserTestUtil.addUser();

		long roleId = RoleTestUtil.addGroupRole(user.getGroupId());

		UserLocalServiceUtil.addRoleUser(roleId, user);

		UserLocalServiceUtil.unsetRoleUsers(
			roleId, new long[] {user.getUserId()});

		Assert.assertFalse(ArrayUtil.contains(user.getRoleIds(), roleId));
	}

	@Test(expected = RequiredRoleException.MustNotRemoveLastAdministator.class)
	public void testUnsetRoleUsersLastAdministratorRole() throws Exception {
		Group group = GroupTestUtil.addGroup();

		UserTestUtil.addUser(group.getGroupId());

		List<User> groupUsers = UserLocalServiceUtil.getGroupUsers(
			group.getGroupId());

		Role role = RoleLocalServiceUtil.getRole(
			group.getCompanyId(), RoleConstants.ADMINISTRATOR);

		UserLocalServiceUtil.unsetRoleUsers(role.getRoleId(), groupUsers);
	}

	@Test(expected = RequiredRoleException.MustNotRemoveUserRole.class)
	public void testUnsetRoleUsersUserRole() throws Exception {
		Group group = GroupTestUtil.addGroup();

		User user = UserTestUtil.addUser(group.getGroupId());

		Role role = RoleLocalServiceUtil.getRole(
			group.getCompanyId(), RoleConstants.USER);

		UserLocalServiceUtil.unsetRoleUsers(
			role.getRoleId(), new long[] {user.getUserId()});
	}

	@Test
	public void testUpdateUser() throws Exception {
		User user = UserTestUtil.addUser();

		_users.add(user);

		TransactionConfig transactionConfig = TransactionConfig.Factory.create(
			Propagation.REQUIRED, new Class<?>[] {Exception.class});

		// Update user twice in same transaction (with email address change)

		try {
			TransactionInvokerUtil.invoke(
				transactionConfig,
				() -> {
					UserLocalServiceUtil.updateUser(user);

					ServiceContext serviceContext =
						ServiceContextTestUtil.getServiceContext(
							user.getGroupId(), user.getUserId());

					return UserLocalServiceUtil.updateUser(
						user.getUserId(), StringPool.BLANK, StringPool.BLANK,
						StringPool.BLANK, false, StringPool.BLANK,
						StringPool.BLANK,
						"TestUser" + RandomTestUtil.nextLong(),
						"UserServiceTest." + RandomTestUtil.nextLong() +
							"@liferay.com",
						0, StringPool.BLANK, false, null, StringPool.BLANK,
						StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
						"UserServiceTest", StringPool.BLANK, "UserServiceTest",
						0, 0, true, Calendar.JANUARY, 1, 1970, StringPool.BLANK,
						StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
						StringPool.BLANK, StringPool.BLANK, null, null, null,
						null, null, serviceContext);
				});
		}
		catch (Throwable throwable) {
			throw new Exception(throwable);
		}
	}

	private long[] _addUsers(int numberOfUsers) throws Exception {
		long[] userIds = new long[numberOfUsers];

		for (int i = 0; i < numberOfUsers; i++) {
			User user = UserTestUtil.addUser();

			_users.add(user);

			userIds[i] = user.getUserId();
		}

		return userIds;
	}

	@DeleteAfterTestRun
	private Company _company;

	@DeleteAfterTestRun
	private final List<Organization> _organizations = new ArrayList<>();

	@DeleteAfterTestRun
	private final List<UserGroup> _userGroups = new ArrayList<>();

	@DeleteAfterTestRun
	private final List<User> _users = new ArrayList<>();

}
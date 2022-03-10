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

package com.liferay.user.service.test;

import com.liferay.announcements.kernel.service.AnnouncementsDeliveryLocalService;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.RequiredRoleException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.PortalPreferences;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.Ticket;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.PortalPreferencesLocalService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.TicketLocalService;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.UserNotificationEventLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.randomizerbumpers.UniqueStringRandomizerBumper;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.OrganizationTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserGroupTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PropsValues;

import java.lang.reflect.Field;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.LongStream;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Michael C. Han
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class UserLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testAddDefaultAdminUser() throws Exception {
		String defaultAdminPassword = PropsValues.DEFAULT_ADMIN_PASSWORD;

		try {
			ReflectionTestUtil.setFieldValue(
				PropsValues.class, "DEFAULT_ADMIN_PASSWORD", StringPool.BLANK);

			User user = _userLocalService.addDefaultAdminUser(
				TestPropsValues.getCompanyId(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString() + "@liferay.com",
				LocaleUtil.getDefault(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString());

			Assert.assertTrue(user.isPasswordReset());
		}
		finally {
			ReflectionTestUtil.setFieldValue(
				PropsValues.class, "DEFAULT_ADMIN_PASSWORD",
				defaultAdminPassword);
		}
	}

	@Test
	public void testDeleteUserDeletesNotificationEvents() throws Exception {
		User user = UserTestUtil.addUser();

		_userNotificationEventLocalService.sendUserNotificationEvents(
			user.getUserId(), null, 0, false, false,
			JSONFactoryUtil.createJSONObject());

		_userLocalService.deleteUser(user);

		int count =
			_userNotificationEventLocalService.getUserNotificationEventsCount(
				user.getUserId());

		Assert.assertEquals(0, count);
	}

	@Test
	public void testDeleteUserDeletesPreferences() throws Exception {
		User user = UserTestUtil.addUser();

		_portalPreferencesLocalService.addPortalPreferences(
			user.getUserId(), PortletKeys.PREFS_OWNER_TYPE_USER, null);
		_portletPreferencesLocalService.addPortletPreferences(
			user.getCompanyId(), user.getUserId(),
			PortletKeys.PREFS_OWNER_TYPE_USER, 0, null, null, null);

		_userLocalService.deleteUser(user);

		PortalPreferences portalPreferences =
			_portalPreferencesLocalService.fetchPortalPreferences(
				user.getUserId(), PortletKeys.PREFS_OWNER_TYPE_USER);

		Assert.assertNull(portalPreferences);

		PortletPreferences portletPreferences =
			_portletPreferencesLocalService.fetchPortletPreferences(
				user.getUserId(), PortletKeys.PREFS_OWNER_TYPE_USER, 0, null);

		Assert.assertNull(portletPreferences);
	}

	@Test
	public void testDeleteUserDeletesTickets() throws Exception {
		User user = UserTestUtil.addUser();

		_userLocalService.deleteUser(user);

		List<Ticket> tickets = _ticketLocalService.getTickets(
			user.getCompanyId(), User.class.getName(), user.getUserId());

		Assert.assertEquals(tickets.toString(), 0, tickets.size());
	}

	@Test
	public void testGetCompanyUsers() throws Exception {
		Company company = CompanyTestUtil.addCompany();

		List<User> companyUsers = _userLocalService.getCompanyUsers(
			company.getCompanyId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(companyUsers.toString(), 1, companyUsers.size());

		User user = companyUsers.get(0);

		Assert.assertFalse(user.isDefaultUser());
	}

	@Test
	public void testGetGroupUsers() throws Exception {
		Group group = GroupTestUtil.addGroup();

		long[] userIds = _addUsers(20);

		_userLocalService.addGroupUsers(group.getGroupId(), userIds);

		long[] allGroupUserIds = _userLocalService.getGroupUserIds(
			group.getGroupId());

		Assert.assertEquals(
			allGroupUserIds.toString(), userIds.length + 1,
			allGroupUserIds.length);
		Assert.assertTrue(ArrayUtil.containsAll(allGroupUserIds, userIds));

		int start = 5;
		int delta = 5;

		List<User> partialGroupUsers = _userLocalService.getGroupUsers(
			group.getGroupId(), WorkflowConstants.STATUS_APPROVED, start,
			start + delta, null);

		Assert.assertEquals(
			partialGroupUsers.toString(), delta, partialGroupUsers.size());
		Assert.assertTrue(
			ArrayUtil.containsAll(
				allGroupUserIds,
				ListUtil.toLongArray(
					partialGroupUsers, User.USER_ID_ACCESSOR)));
	}

	@Test
	public void testGetNoAnnouncementsDeliveries() throws Exception {
		User user1 = UserTestUtil.addUser();
		User user2 = UserTestUtil.addUser();

		_announcementsDeliveryLocalService.addUserDelivery(
			user1.getUserId(), "general");

		List<User> users = _userLocalService.getNoAnnouncementsDeliveries(
			"general");

		Assert.assertFalse(users.toString(), users.contains(user1));
		Assert.assertTrue(users.toString(), users.contains(user2));
	}

	@Test
	public void testGetNoGroups() throws Exception {
		User user = UserTestUtil.addUser();

		_groupLocalService.deleteGroup(user.getGroupId());

		List<User> users = _userLocalService.getNoGroups();

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

			_userLocalService.addOrganizationUsers(
				organization.getOrganizationId(), commonUserIds);
			_userLocalService.addOrganizationUsers(
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

			_userLocalService.addUserGroupUsers(
				userGroup.getUserGroupId(), commonUserIds);
			_userLocalService.addUserGroupUsers(
				userGroup.getUserGroupId(), uniqueUserIds);

			userGroupIds[i] = userGroup.getUserGroupId();

			uniqueUserGroupUsersCount += uniqueUserIds.length;
		}

		long[] emptyLongArray = new long[0];

		Assert.assertEquals(
			0,
			_userLocalService.getOrganizationsAndUserGroupsUsersCount(
				null, null));
		Assert.assertEquals(
			0,
			_userLocalService.getOrganizationsAndUserGroupsUsersCount(
				emptyLongArray.clone(), null));
		Assert.assertEquals(
			0,
			_userLocalService.getOrganizationsAndUserGroupsUsersCount(
				null, emptyLongArray.clone()));
		Assert.assertEquals(
			0,
			_userLocalService.getOrganizationsAndUserGroupsUsersCount(
				emptyLongArray.clone(), emptyLongArray.clone()));

		int commonUsersCount = commonUserIds.length;

		Assert.assertEquals(
			commonUsersCount + uniqueOrganizationUsersCount +
				uniqueUserGroupUsersCount,
			_userLocalService.getOrganizationsAndUserGroupsUsersCount(
				organizationIds, userGroupIds));

		Assert.assertEquals(
			commonUsersCount + uniqueOrganizationUsersCount,
			_userLocalService.getOrganizationsAndUserGroupsUsersCount(
				organizationIds, null));
		Assert.assertEquals(
			commonUsersCount + uniqueOrganizationUsersCount,
			_userLocalService.getOrganizationsAndUserGroupsUsersCount(
				organizationIds, emptyLongArray.clone()));
		Assert.assertEquals(
			commonUsersCount + uniqueUserGroupUsersCount,
			_userLocalService.getOrganizationsAndUserGroupsUsersCount(
				null, userGroupIds));
		Assert.assertEquals(
			commonUsersCount + uniqueUserGroupUsersCount,
			_userLocalService.getOrganizationsAndUserGroupsUsersCount(
				emptyLongArray.clone(), userGroupIds));
	}

	@Test
	public void testGetOrganizationUsers() throws Exception {
		Organization organization = OrganizationTestUtil.addOrganization();

		long[] userIds = _addUsers(20);

		_userLocalService.addOrganizationUsers(
			organization.getOrganizationId(), userIds);

		long[] organizationUserIds = _userLocalService.getOrganizationUserIds(
			organization.getOrganizationId());

		Assert.assertEquals(
			organizationUserIds.toString(), userIds.length,
			organizationUserIds.length);
		Assert.assertTrue(ArrayUtil.containsAll(organizationUserIds, userIds));

		int start = 5;
		int delta = 5;

		List<User> organizationUsers = _userLocalService.getOrganizationUsers(
			organization.getOrganizationId(), WorkflowConstants.STATUS_APPROVED,
			start, start + delta, null);

		Assert.assertEquals(
			organizationUsers.toString(), delta, organizationUsers.size());
		Assert.assertTrue(
			ArrayUtil.containsAll(
				userIds,
				ListUtil.toLongArray(
					organizationUsers, User.USER_ID_ACCESSOR)));
	}

	@Test
	public void testGetUserGroupUsers() throws Exception {
		UserGroup userGroup = UserGroupTestUtil.addUserGroup();

		long[] userIds = _addUsers(20);

		_userLocalService.addUserGroupUsers(
			userGroup.getUserGroupId(), userIds);

		List<User> userGroupUsers = _userLocalService.getUserGroupUsers(
			userGroup.getUserGroupId());

		Assert.assertEquals(
			userGroupUsers.toString(), userIds.length, userGroupUsers.size());
		Assert.assertTrue(
			ArrayUtil.containsAll(
				ListUtil.toLongArray(userGroupUsers, User.USER_ID_ACCESSOR),
				userIds));

		int start = 5;
		int delta = 5;

		userGroupUsers = _userLocalService.getUserGroupUsers(
			userGroup.getUserGroupId(), start, start + delta);

		Assert.assertEquals(
			userGroupUsers.toString(), delta, userGroupUsers.size());
		Assert.assertTrue(
			ArrayUtil.containsAll(
				userIds,
				ListUtil.toLongArray(userGroupUsers, User.USER_ID_ACCESSOR)));
	}

	@Test
	public void testSearchCounts() throws Exception {

		// LPS-119805

		_userLocalService.searchCounts(
			TestPropsValues.getCompanyId(), WorkflowConstants.STATUS_APPROVED,
			LongStream.rangeClosed(
				1000, 3000
			).toArray());
	}

	@Test
	public void testSearchCountsUserRole() throws Exception {
		Group group = GroupTestUtil.addGroup();

		PermissionChecker oldPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(UserTestUtil.addUser()));

		try {
			Map<Long, Integer> counts = _userLocalService.searchCounts(
				TestPropsValues.getCompanyId(),
				WorkflowConstants.STATUS_APPROVED,
				new long[] {group.getGroupId()});

			Integer count = counts.get(group.getGroupId());

			Assert.assertNotNull(count);

			Assert.assertEquals(1, count.intValue());
		}
		finally {
			PermissionThreadLocal.setPermissionChecker(oldPermissionChecker);
		}
	}

	@Test
	public void testSearchUserGroupUserInOrganizationSite() throws Exception {
		Organization organization = OrganizationTestUtil.addOrganization(true);

		Group organizationSite = _groupLocalService.getOrganizationGroup(
			TestPropsValues.getCompanyId(), organization.getOrganizationId());

		organizationSite.setManualMembership(true);

		User user = UserTestUtil.addUser();

		UserGroup userGroup = UserGroupTestUtil.addUserGroup();

		_userGroupLocalService.addUserUserGroup(user.getUserId(), userGroup);

		_groupLocalService.addUserGroupGroup(
			userGroup.getUserGroupId(), organizationSite);

		List<User> users = _userLocalService.search(
			TestPropsValues.getCompanyId(), user.getFirstName(),
			WorkflowConstants.STATUS_APPROVED,
			LinkedHashMapBuilder.<String, Object>put(
				"inherit", Boolean.TRUE
			).put(
				"usersGroups", organizationSite.getGroupId()
			).build(),
			QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			(OrderByComparator<User>)null);

		Assert.assertEquals(users.toString(), 1, users.size());
		Assert.assertTrue(users.contains(user));
	}

	@Test
	public void testSearchUsersFromDatabase() throws Exception {
		Field propsValuesField = ReflectionUtil.getDeclaredField(
			PropsValues.class, "USERS_SEARCH_WITH_INDEX");

		boolean propsValuesFieldValue = (boolean)propsValuesField.get(null);

		try {
			propsValuesField.set(null, false);

			_userLocalService.searchCount(
				TestPropsValues.getCompanyId(), null,
				WorkflowConstants.STATUS_APPROVED,
				LinkedHashMapBuilder.<String, Object>put(
					com.liferay.portal.kernel.search.Field.GROUP_ID,
					TestPropsValues.getGroupId()
				).build());
		}
		finally {
			propsValuesField.set(null, propsValuesFieldValue);
		}
	}

	@Test
	public void testSetRoleUsers() throws Exception {
		User user = UserTestUtil.addUser();

		long roleId = RoleTestUtil.addGroupRole(user.getGroupId());

		_userLocalService.addRoleUser(roleId, user);

		user = _userLocalService.getUser(user.getUserId());

		Assert.assertTrue(ArrayUtil.contains(user.getRoleIds(), roleId));
	}

	@Test
	public void testUnsetRoleUsers() throws Exception {
		User user = UserTestUtil.addUser();

		long roleId = RoleTestUtil.addGroupRole(user.getGroupId());

		_userLocalService.addRoleUser(roleId, user);

		_userLocalService.unsetRoleUsers(roleId, new long[] {user.getUserId()});

		Assert.assertFalse(ArrayUtil.contains(user.getRoleIds(), roleId));
	}

	@Test(expected = RequiredRoleException.MustNotRemoveLastAdministator.class)
	public void testUnsetRoleUsersLastAdministratorRole() throws Exception {
		Group group = GroupTestUtil.addGroup();

		UserTestUtil.addUser(group.getGroupId());

		List<User> groupUsers = _userLocalService.getGroupUsers(
			group.getGroupId());

		Role role = _roleLocalService.getRole(
			group.getCompanyId(), RoleConstants.ADMINISTRATOR);

		_userLocalService.unsetRoleUsers(role.getRoleId(), groupUsers);
	}

	@Test(expected = RequiredRoleException.MustNotRemoveUserRole.class)
	public void testUnsetRoleUsersUserRole() throws Exception {
		Group group = GroupTestUtil.addGroup();

		User user = UserTestUtil.addUser(group.getGroupId());

		Role role = _roleLocalService.getRole(
			group.getCompanyId(), RoleConstants.USER);

		_userLocalService.unsetRoleUsers(
			role.getRoleId(), new long[] {user.getUserId()});
	}

	@Test
	public void testUpdatePassword() throws Exception {
		User user = UserTestUtil.addUser();
		String password = RandomTestUtil.randomString(
			UniqueStringRandomizerBumper.INSTANCE);

		Date oldPasswordModifiedDate = user.getPasswordModifiedDate();

		_userLocalService.updatePassword(
			user.getUserId(), password, password, false, true);

		user = _userLocalService.getUser(user.getUserId());

		Date newPasswordModifiedDate = user.getPasswordModifiedDate();

		Assert.assertTrue(
			newPasswordModifiedDate.after(oldPasswordModifiedDate));
	}

	@Test
	public void testUpdateUser() throws Exception {
		User user = UserTestUtil.addUser();

		TransactionConfig transactionConfig = TransactionConfig.Factory.create(
			Propagation.REQUIRED, new Class<?>[] {Exception.class});

		// Update user twice in same transaction (with email address change)

		try {
			TransactionInvokerUtil.invoke(
				transactionConfig,
				() -> {
					_userLocalService.updateUser(user);

					ServiceContext serviceContext =
						ServiceContextTestUtil.getServiceContext(
							user.getGroupId(), user.getUserId());

					return _userLocalService.updateUser(
						user.getUserId(), StringPool.BLANK, StringPool.BLANK,
						StringPool.BLANK, false, StringPool.BLANK,
						StringPool.BLANK,
						"TestUser" + RandomTestUtil.nextLong(),
						"UserServiceTest." + RandomTestUtil.nextLong() +
							"@liferay.com",
						false, null, StringPool.BLANK, StringPool.BLANK,
						StringPool.BLANK, StringPool.BLANK, "UserServiceTest",
						StringPool.BLANK, "UserServiceTest", 0, 0, true,
						Calendar.JANUARY, 1, 1970, StringPool.BLANK,
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

			userIds[i] = user.getUserId();
		}

		return userIds;
	}

	@Inject
	private AnnouncementsDeliveryLocalService
		_announcementsDeliveryLocalService;

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private PortalPreferencesLocalService _portalPreferencesLocalService;

	@Inject
	private PortletPreferencesLocalService _portletPreferencesLocalService;

	@Inject
	private RoleLocalService _roleLocalService;

	@Inject
	private TicketLocalService _ticketLocalService;

	@Inject
	private UserGroupLocalService _userGroupLocalService;

	@Inject
	private UserLocalService _userLocalService;

	@Inject
	private UserNotificationEventLocalService
		_userNotificationEventLocalService;

}
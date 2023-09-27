/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.internal.security.permission.resource.test;

import com.liferay.account.constants.AccountActionKeys;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryOrganizationRelLocalService;
import com.liferay.account.service.test.util.AccountEntryArgs;
import com.liferay.account.service.test.util.AccountEntryTestUtil;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.OrganizationTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Pei-Jung Lan
 */
@RunWith(Arquillian.class)
public class AccountEntryModelResourcePermissionTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testManageAvailableAccountsPermissions() throws Exception {
		AccountEntry accountEntry = AccountEntryTestUtil.addAccountEntry();
		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);
		User user = UserTestUtil.addUser();

		RoleTestUtil.addResourcePermission(
			role, Organization.class.getName(), ResourceConstants.SCOPE_COMPANY,
			String.valueOf(TestPropsValues.getCompanyId()),
			AccountActionKeys.MANAGE_AVAILABLE_ACCOUNTS);

		_userLocalService.addRoleUser(role.getRoleId(), user.getUserId());

		_assertDoesNotContain(user, accountEntry, _ACTION_IDS);

		Organization organization1 = OrganizationTestUtil.addOrganization();

		_userLocalService.addOrganizationUser(
			organization1.getOrganizationId(), user.getUserId());

		_assertDoesNotContain(user, accountEntry, _ACTION_IDS);

		Organization organization2 = OrganizationTestUtil.addOrganization();

		_accountEntryOrganizationRelLocalService.addAccountEntryOrganizationRel(
			accountEntry.getAccountEntryId(),
			organization2.getOrganizationId());

		_assertDoesNotContain(user, accountEntry, _ACTION_IDS);

		Organization organization3 = OrganizationTestUtil.addOrganization();

		_userLocalService.addOrganizationUser(
			organization3.getOrganizationId(), user.getUserId());
		_accountEntryOrganizationRelLocalService.addAccountEntryOrganizationRel(
			accountEntry.getAccountEntryId(),
			organization3.getOrganizationId());

		_assertContains(user, accountEntry, _ACTION_IDS);
		_assertDoesNotContain(
			user, accountEntry, AccountActionKeys.MANAGE_ORGANIZATIONS);

		_accountEntryOrganizationRelLocalService.
			deleteAccountEntryOrganizationRel(
				accountEntry.getAccountEntryId(),
				organization3.getOrganizationId());

		Organization parentOrganization =
			OrganizationTestUtil.addOrganization();

		_userLocalService.addOrganizationUser(
			parentOrganization.getOrganizationId(), user.getUserId());

		Organization childOrganization = OrganizationTestUtil.addOrganization(
			parentOrganization.getOrganizationId(),
			RandomTestUtil.randomString(), false);

		_accountEntryOrganizationRelLocalService.addAccountEntryOrganizationRel(
			accountEntry.getAccountEntryId(),
			childOrganization.getOrganizationId());

		_assertContains(user, accountEntry, _ACTION_IDS);
		_assertDoesNotContain(
			user, accountEntry, AccountActionKeys.MANAGE_ORGANIZATIONS);
	}

	@Test
	public void testManageSuborganizationsAccountsPermissions()
		throws Exception {

		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_ORGANIZATION);

		for (String actionId : _ACTION_IDS) {
			RoleTestUtil.addResourcePermission(
				role, AccountEntry.class.getName(),
				ResourceConstants.SCOPE_GROUP_TEMPLATE,
				String.valueOf(GroupConstants.DEFAULT_PARENT_GROUP_ID),
				actionId);
		}

		User user = UserTestUtil.addUser();

		Organization parentOrganization =
			OrganizationTestUtil.addOrganization();

		AccountEntry accountEntry1 = AccountEntryTestUtil.addAccountEntry(
			AccountEntryArgs.withOrganizations(parentOrganization));

		_assertDoesNotContain(user, accountEntry1, _ACTION_IDS);

		_userGroupRoleLocalService.addUserGroupRole(
			user.getUserId(), parentOrganization.getGroupId(),
			role.getRoleId());

		_userLocalService.addOrganizationUser(
			parentOrganization.getOrganizationId(), user.getUserId());

		_assertContains(user, accountEntry1, _ACTION_IDS);

		Organization childOrganization = OrganizationTestUtil.addOrganization(
			parentOrganization.getOrganizationId(),
			RandomTestUtil.randomString(), false);

		AccountEntry accountEntry2 = AccountEntryTestUtil.addAccountEntry(
			AccountEntryArgs.withOrganizations(childOrganization));

		_assertDoesNotContain(user, accountEntry2, _ACTION_IDS);

		RoleTestUtil.addResourcePermission(
			role, Organization.class.getName(),
			ResourceConstants.SCOPE_GROUP_TEMPLATE,
			String.valueOf(GroupConstants.DEFAULT_PARENT_GROUP_ID),
			AccountActionKeys.MANAGE_SUBORGANIZATIONS_ACCOUNTS);
		RoleTestUtil.removeResourcePermission(
			role.getName(), AccountEntry.class.getName(),
			ResourceConstants.SCOPE_GROUP_TEMPLATE,
			String.valueOf(GroupConstants.DEFAULT_PARENT_GROUP_ID),
			ActionKeys.VIEW);

		_assertContains(user, accountEntry2, _ACTION_IDS);
	}

	@Test
	public void testOwnerPermissions() throws Exception {
		User user = UserTestUtil.addUser();

		AccountEntry accountEntry = AccountEntryTestUtil.addAccountEntry(
			AccountEntryArgs.withOwner(user));

		_assertContains(user, accountEntry, _ACTION_IDS);
	}

	private void _assertContains(
			User user, AccountEntry accountEntry, String... actionIds)
		throws Exception {

		PermissionChecker originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		try {
			PermissionThreadLocal.setPermissionChecker(
				PermissionCheckerFactoryUtil.create(user));

			for (String actionId : actionIds) {
				Assert.assertTrue(
					actionId,
					_accountEntryModelResourcePermission.contains(
						PermissionThreadLocal.getPermissionChecker(),
						accountEntry, actionId));
			}
		}
		finally {
			PermissionThreadLocal.setPermissionChecker(
				originalPermissionChecker);
		}
	}

	private void _assertDoesNotContain(
			User user, AccountEntry accountEntry, String... actionIds)
		throws Exception {

		PermissionChecker originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		try {
			PermissionThreadLocal.setPermissionChecker(
				PermissionCheckerFactoryUtil.create(user));

			for (String actionId : actionIds) {
				Assert.assertFalse(
					actionId,
					_accountEntryModelResourcePermission.contains(
						PermissionThreadLocal.getPermissionChecker(),
						accountEntry, actionId));
			}
		}
		finally {
			PermissionThreadLocal.setPermissionChecker(
				originalPermissionChecker);
		}
	}

	private static final String[] _ACTION_IDS = {
		AccountActionKeys.VIEW_USERS, ActionKeys.DELETE, ActionKeys.VIEW,
		ActionKeys.UPDATE
	};

	@Inject(filter = "model.class.name=com.liferay.account.model.AccountEntry")
	private volatile ModelResourcePermission<AccountEntry>
		_accountEntryModelResourcePermission;

	@Inject
	private AccountEntryOrganizationRelLocalService
		_accountEntryOrganizationRelLocalService;

	@Inject
	private UserGroupRoleLocalService _userGroupRoleLocalService;

	@Inject
	private UserLocalService _userLocalService;

}
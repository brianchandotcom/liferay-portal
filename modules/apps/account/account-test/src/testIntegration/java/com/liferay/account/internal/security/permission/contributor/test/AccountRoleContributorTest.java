/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.internal.security.permission.contributor.test;

import com.liferay.account.constants.AccountRoleConstants;
import com.liferay.account.manager.CurrentAccountEntryManager;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountRole;
import com.liferay.account.service.AccountRoleLocalService;
import com.liferay.account.service.test.util.AccountEntryArgs;
import com.liferay.account.service.test.util.AccountEntryTestUtil;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.blogs.test.util.BlogsTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupedModel;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
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
public class AccountRoleContributorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testAccountMemberRoleAssignment() throws Exception {
		User user = UserTestUtil.addUser();

		AccountEntry accountEntry = AccountEntryTestUtil.addAccountEntry(
			AccountEntryArgs.withUsers(user));

		PermissionChecker permissionChecker = _permissionCheckerFactory.create(
			user);

		long[] roleIds = permissionChecker.getRoleIds(
			user.getUserId(), accountEntry.getAccountEntryGroupId());

		Role role = _roleLocalService.getRole(
			accountEntry.getCompanyId(),
			AccountRoleConstants.REQUIRED_ROLE_NAME_ACCOUNT_MEMBER);

		Assert.assertTrue(ArrayUtil.contains(roleIds, role.getRoleId()));
	}

	@Test
	public void testNoRolesAssigned() throws Exception {
		User user = UserTestUtil.addUser();

		PermissionChecker permissionChecker = _permissionCheckerFactory.create(
			user);

		Assert.assertNotSame(
			PermissionChecker.DEFAULT_ROLE_IDS,
			permissionChecker.getRoleIds(
				user.getUserId(), TestPropsValues.getGroupId()));
	}

	@Test
	public void testSelectedAccountPermission() throws Exception {
		User user = UserTestUtil.addUser();

		AccountEntry accountEntry1 = AccountEntryTestUtil.addAccountEntry(
			AccountEntryArgs.withUsers(user));

		AccountRole accountRole = _accountRoleLocalService.addAccountRole(
			TestPropsValues.getUserId(), accountEntry1.getAccountEntryId(),
			RandomTestUtil.randomString(), null, null);

		Group group = GroupTestUtil.addGroup();

		GroupedModel groupedModel = BlogsTestUtil.addEntryWithWorkflow(
			TestPropsValues.getUserId(), RandomTestUtil.randomString(), true,
			ServiceContextTestUtil.getServiceContext(group.getGroupId()));

		_accountRoleLocalService.associateUser(
			accountEntry1.getAccountEntryId(), accountRole.getAccountRoleId(),
			user.getUserId());

		AccountEntry accountEntry2 = AccountEntryTestUtil.addAccountEntry(
			AccountEntryArgs.withUsers(user));

		_currentAccountEntryManager.setCurrentAccountEntry(
			accountEntry1.getAccountEntryId(), group.getGroupId(),
			user.getUserId());

		_testSelectedAccountPermission(
			groupedModel, user, ActionKeys.UPDATE, false);

		_resourcePermissionLocalService.setResourcePermissions(
			groupedModel.getCompanyId(), groupedModel.getModelClassName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			String.valueOf(groupedModel.getPrimaryKeyObj()),
			accountRole.getRoleId(), new String[] {ActionKeys.UPDATE});

		_testSelectedAccountPermission(
			groupedModel, user, ActionKeys.UPDATE, true);

		_currentAccountEntryManager.setCurrentAccountEntry(
			accountEntry2.getAccountEntryId(), group.getGroupId(),
			user.getUserId());

		_testSelectedAccountPermission(
			groupedModel, user, ActionKeys.UPDATE, false);

		_currentAccountEntryManager.setCurrentAccountEntry(
			accountEntry1.getAccountEntryId(), group.getGroupId(),
			user.getUserId());

		_testSelectedAccountPermission(
			groupedModel, user, ActionKeys.DELETE, false);

		_resourcePermissionLocalService.addResourcePermission(
			groupedModel.getCompanyId(), groupedModel.getModelClassName(),
			ResourceConstants.SCOPE_GROUP,
			String.valueOf(groupedModel.getGroupId()), accountRole.getRoleId(),
			ActionKeys.DELETE);

		_testSelectedAccountPermission(
			groupedModel, user, ActionKeys.DELETE, true);

		_currentAccountEntryManager.setCurrentAccountEntry(
			accountEntry2.getAccountEntryId(), group.getGroupId(),
			user.getUserId());

		_testSelectedAccountPermission(
			groupedModel, user, ActionKeys.DELETE, false);
	}

	private void _testSelectedAccountPermission(
			GroupedModel groupedModel, User user, String actionKey,
			boolean hasPermission)
		throws Exception {

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		Assert.assertEquals(
			hasPermission,
			permissionChecker.hasPermission(
				groupedModel.getGroupId(), groupedModel.getModelClassName(),
				String.valueOf(groupedModel.getPrimaryKeyObj()), actionKey));
	}

	@Inject
	private AccountRoleLocalService _accountRoleLocalService;

	@Inject
	private CurrentAccountEntryManager _currentAccountEntryManager;

	@Inject
	private PermissionCheckerFactory _permissionCheckerFactory;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Inject
	private RoleLocalService _roleLocalService;

}
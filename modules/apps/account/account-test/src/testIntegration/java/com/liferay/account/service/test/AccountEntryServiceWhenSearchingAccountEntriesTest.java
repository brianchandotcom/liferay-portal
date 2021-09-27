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

package com.liferay.account.service.test;

import com.liferay.account.constants.AccountActionKeys;
import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountEntryOrganizationRelLocalService;
import com.liferay.account.service.AccountEntryService;
import com.liferay.account.service.AccountEntryUserRelLocalService;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.OrganizationConstants;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.search.test.util.SearchTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Pei-Jung Lan
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class AccountEntryServiceWhenSearchingAccountEntriesTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_rootOrganization = _organizationLocalService.addOrganization(
			TestPropsValues.getUserId(),
			OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID,
			RandomTestUtil.randomString(), false);

		_organizationAccountEntries.put(
			_rootOrganization,
			_addAccountEntryWithOrganization(_rootOrganization));

		_organization = _organizationLocalService.addOrganization(
			TestPropsValues.getUserId(), _rootOrganization.getOrganizationId(),
			RandomTestUtil.randomString(), false);

		_organizationAccountEntries.put(
			_organization, _addAccountEntryWithOrganization(_organization));

		_suborganization = _organizationLocalService.addOrganization(
			TestPropsValues.getUserId(), _organization.getOrganizationId(),
			RandomTestUtil.randomString(), false);

		_organizationAccountEntries.put(
			_suborganization,
			_addAccountEntryWithOrganization(_suborganization));

		_user = UserTestUtil.addUser();

		_originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(_user));
	}

	@After
	public void tearDown() throws Exception {
		PermissionThreadLocal.setPermissionChecker(_originalPermissionChecker);
	}

	@Test
	public void testShouldReturnAllAccountEntriesAsAdminUser()
		throws Exception {

		Role role = _roleLocalService.getRole(
			_user.getCompanyId(), RoleConstants.ADMINISTRATOR);

		_userLocalService.addRoleUser(role.getRoleId(), _user);

		_assertSearch(_getAllAccountEntries());
	}

	@Test
	public void testShouldReturnAllAccountEntriesWithCompanyViewPermission()
		throws Exception {

		_assertSearch(Collections.emptyList());

		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		RoleTestUtil.addResourcePermission(
			role, AccountEntry.class.getName(), ResourceConstants.SCOPE_COMPANY,
			String.valueOf(TestPropsValues.getCompanyId()), ActionKeys.VIEW);

		_userLocalService.addRoleUser(role.getRoleId(), _user.getUserId());

		_assertSearch(_getAllAccountEntries());
	}

	@Test
	public void testShouldReturnDirectMembershipAccountEntries()
		throws Exception {

		_assertSearch(Collections.emptyList());

		AccountEntry accountEntry = _organizationAccountEntries.get(
			_rootOrganization);

		_accountEntryUserRelLocalService.addAccountEntryUserRel(
			accountEntry.getAccountEntryId(), _user.getUserId());

		_assertSearch(Collections.singletonList(accountEntry));
	}

	@Test
	public void testShouldReturnNoAccountEntriesWithoutManageAccountsPermission()
		throws Exception {

		for (Organization organization : _organizationAccountEntries.keySet()) {
			_userLocalService.addOrganizationUser(
				organization.getOrganizationId(), _user);
		}

		_assertSearch(Collections.emptyList());
	}

	@Test
	public void testShouldReturnOrganizationAccountEntriesWithManageAccountsPermission()
		throws Exception {

		_userLocalService.addOrganizationUser(
			_rootOrganization.getOrganizationId(), _user);

		Role role = _addOrganizationRole();

		RoleTestUtil.addResourcePermission(
			role, Organization.class.getName(),
			ResourceConstants.SCOPE_GROUP_TEMPLATE, "0",
			AccountActionKeys.MANAGE_ACCOUNTS);

		_userGroupRoleLocalService.addUserGroupRole(
			_user.getUserId(), _rootOrganization.getGroupId(),
			role.getRoleId());

		_assertSearch(
			ListUtil.toList(
				_organizationAccountEntries.get(_rootOrganization)));
	}

	@Test
	public void testShouldReturnSuborganizationsAccountEntries()
		throws Exception {

		_userLocalService.addOrganizationUser(
			_organization.getOrganizationId(), _user);

		Role role = _addOrganizationRole();

		RoleTestUtil.addResourcePermission(
			role, Organization.class.getName(),
			ResourceConstants.SCOPE_GROUP_TEMPLATE, "0",
			AccountActionKeys.MANAGE_ACCOUNTS);

		_userGroupRoleLocalService.addUserGroupRole(
			_user.getUserId(), _organization.getGroupId(), role.getRoleId());

		AccountEntry accountEntry = _organizationAccountEntries.get(
			_organization);

		_assertSearch(ListUtil.toList(accountEntry));

		RoleTestUtil.addResourcePermission(
			role, Organization.class.getName(),
			ResourceConstants.SCOPE_GROUP_TEMPLATE, "0",
			AccountActionKeys.MANAGE_SUBORGANIZATIONS_ACCOUNTS);

		AccountEntry suborgAccountEntry = _organizationAccountEntries.get(
			_suborganization);

		_assertSearch(Arrays.asList(accountEntry, suborgAccountEntry));

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_user);

		Assert.assertFalse(
			_hasPermission(permissionChecker, accountEntry, ActionKeys.UPDATE));
		Assert.assertFalse(
			_hasPermission(
				permissionChecker, suborgAccountEntry, ActionKeys.UPDATE));

		RoleTestUtil.addResourcePermission(
			role, AccountEntry.class.getName(),
			ResourceConstants.SCOPE_GROUP_TEMPLATE, "0", ActionKeys.UPDATE);

		Assert.assertTrue(
			_hasPermission(permissionChecker, accountEntry, ActionKeys.UPDATE));
		Assert.assertTrue(
			_hasPermission(
				permissionChecker, suborgAccountEntry, ActionKeys.UPDATE));
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	private AccountEntry _addAccountEntryWithOrganization(
			Organization organization)
		throws Exception {

		AccountEntry accountEntry = _accountEntryLocalService.addAccountEntry(
			TestPropsValues.getUserId(),
			AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), null,
			null, WorkflowConstants.STATUS_APPROVED,
			ServiceContextTestUtil.getServiceContext());

		_accountEntryOrganizationRelLocalService.addAccountEntryOrganizationRel(
			accountEntry.getAccountEntryId(), organization.getOrganizationId());

		return accountEntry;
	}

	private Role _addOrganizationRole() throws Exception {
		return _roleLocalService.addRole(
			TestPropsValues.getUserId(), null, 0, RandomTestUtil.randomString(),
			null, null, RoleConstants.TYPE_ORGANIZATION, null, null);
	}

	private void _assertSearch(List<AccountEntry> expectedAccountEntries)
		throws Exception {

		BaseModelSearchResult<AccountEntry> baseModelSearchResult =
			_accountEntryService.searchAccountEntries(
				null, null, 0, 10, "name", false);

		Assert.assertEquals(
			expectedAccountEntries.size(), baseModelSearchResult.getLength());
		Assert.assertEquals(
			ListUtil.sort(
				expectedAccountEntries,
				Comparator.comparing(
					AccountEntry::getName, String::compareToIgnoreCase)),
			baseModelSearchResult.getBaseModels());
	}

	private List<AccountEntry> _getAllAccountEntries() throws Exception {
		return _accountEntryLocalService.getAccountEntries(
			TestPropsValues.getCompanyId(), WorkflowConstants.STATUS_APPROVED,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	private boolean _hasPermission(
			PermissionChecker permissionChecker, AccountEntry accountEntry,
			String actionId)
		throws Exception {

		for (Organization organization : _user.getOrganizations(true)) {
			if (permissionChecker.hasPermission(
					organization.getGroupId(), AccountEntry.class.getName(),
					accountEntry.getAccountEntryId(), actionId)) {

				return true;
			}
		}

		return false;
	}

	@Inject
	private AccountEntryLocalService _accountEntryLocalService;

	@Inject
	private AccountEntryOrganizationRelLocalService
		_accountEntryOrganizationRelLocalService;

	@Inject
	private AccountEntryService _accountEntryService;

	@Inject
	private AccountEntryUserRelLocalService _accountEntryUserRelLocalService;

	private Organization _organization;
	private final Map<Organization, AccountEntry> _organizationAccountEntries =
		new LinkedHashMap<>();

	@Inject
	private OrganizationLocalService _organizationLocalService;

	private PermissionChecker _originalPermissionChecker;

	@Inject
	private RoleLocalService _roleLocalService;

	private Organization _rootOrganization;
	private Organization _suborganization;
	private User _user;

	@Inject
	private UserGroupRoleLocalService _userGroupRoleLocalService;

	@Inject
	private UserLocalService _userLocalService;

}
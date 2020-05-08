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
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountEntryOrganizationRelLocalService;
import com.liferay.account.service.AccountEntryService;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.OrganizationTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.search.test.util.SearchTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
		Organization rootOrganization = OrganizationTestUtil.addOrganization();

		Organization organization = OrganizationTestUtil.addOrganization(
			rootOrganization.getOrganizationId(), RandomTestUtil.randomString(),
			false);

		Organization suborganization = OrganizationTestUtil.addOrganization(
			organization.getOrganizationId(), RandomTestUtil.randomString(),
			false);

		_organizations.add(suborganization);

		_organizations.add(organization);

		_organizations.add(rootOrganization);

		for (Organization curOrganization : _organizations) {
			_addAccountEntryWithOrganization(curOrganization);
		}

		_user = UserTestUtil.addUser();

		_originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(_user);

		PermissionThreadLocal.setPermissionChecker(permissionChecker);
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

		_assertSearch(_accountEntries);
	}

	@Test
	public void testShouldReturnNoAccountEntriesWithoutManageAccountsPermission()
		throws Exception {

		for (Organization organization : _organizations) {
			_userLocalService.addOrganizationUser(
				organization.getOrganizationId(), _user);
		}

		_assertSearch(Collections.emptyList());
	}

	@Test
	public void testShouldReturnOrganizationAccountEntriesWithManageAccountsPermission()
		throws Exception {

		Organization rootOrganization = _organizations.get(
			_organizations.size() - 1);

		_userLocalService.addOrganizationUser(
			rootOrganization.getOrganizationId(), _user);

		Role role = RoleTestUtil.addRole(
			RandomTestUtil.randomString(), RoleConstants.TYPE_ORGANIZATION,
			Organization.class.getName(),
			ResourceConstants.SCOPE_GROUP_TEMPLATE, "0",
			AccountActionKeys.MANAGE_ACCOUNTS);

		UserTestUtil.addUserGroupRole(
			_user.getUserId(), rootOrganization.getGroupId(), role.getName());

		_assertSearch(
			ListUtil.toList(_accountEntries.get(_accountEntries.size() - 1)));

		_resourcePermissionLocalService.addResourcePermission(
			_user.getCompanyId(), Organization.class.getName(),
			ResourceConstants.SCOPE_GROUP_TEMPLATE, "0", role.getRoleId(),
			ActionKeys.MANAGE_SUBORGANIZATIONS);

		_assertSearch(_accountEntries);
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	private AccountEntry _addAccountEntryWithOrganization(
			Organization organization)
		throws Exception {

		AccountEntry accountEntry = AccountEntryTestUtil.addAccountEntry(
			_accountEntryLocalService);

		_accountEntries.add(accountEntry);

		_accountEntryOrganizationRelLocalService.addAccountEntryOrganizationRel(
			accountEntry.getAccountEntryId(), organization.getOrganizationId());

		return accountEntry;
	}

	private void _assertSearch(List<AccountEntry> expectedAccountEntries)
		throws Exception {

		BaseModelSearchResult<AccountEntry> baseModelSearchResult =
			_accountEntryService.search(null, null, 0, 10, "name", false);

		Assert.assertEquals(
			expectedAccountEntries.size(), baseModelSearchResult.getLength());
		Assert.assertEquals(
			ListUtil.sort(
				expectedAccountEntries,
				Comparator.comparing(
					AccountEntry::getName, String::compareToIgnoreCase)),
			baseModelSearchResult.getBaseModels());
	}

	private final List<AccountEntry> _accountEntries = new ArrayList<>();

	@Inject
	private AccountEntryLocalService _accountEntryLocalService;

	@Inject
	private AccountEntryOrganizationRelLocalService
		_accountEntryOrganizationRelLocalService;

	@Inject
	private AccountEntryService _accountEntryService;

	private final List<Organization> _organizations = new ArrayList<>();
	private PermissionChecker _originalPermissionChecker;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Inject
	private RoleLocalService _roleLocalService;

	private User _user;

	@Inject
	private UserLocalService _userLocalService;

}
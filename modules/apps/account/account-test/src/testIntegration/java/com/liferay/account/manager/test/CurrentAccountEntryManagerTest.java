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

package com.liferay.account.manager.test;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.exception.AccountEntryTypeException;
import com.liferay.account.manager.CurrentAccountEntryManager;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountEntryOrganizationRelLocalService;
import com.liferay.account.service.AccountEntryUserRelLocalService;
import com.liferay.account.service.test.util.AccountEntryTestUtil;
import com.liferay.account.settings.AccountEntryGroupSettings;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.configuration.test.util.ConfigurationTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.OrganizationTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Pei-Jung Lan
 * @author Drew Brokke
 */
@RunWith(Arquillian.class)
public class CurrentAccountEntryManagerTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_user = UserTestUtil.addUser();
	}

	@Test
	public void testGetCurrentAccountEntry() throws Exception {
		AccountEntry accountEntry1 = AccountEntryTestUtil.addAccountEntry(
			_accountEntryLocalService);

		_accountEntryUserRelLocalService.addAccountEntryUserRel(
			accountEntry1.getAccountEntryId(), _user.getUserId());

		AccountEntry accountEntry2 = AccountEntryTestUtil.addAccountEntry(
			_accountEntryLocalService);

		_accountEntryUserRelLocalService.addAccountEntryUserRel(
			accountEntry2.getAccountEntryId(), _user.getUserId());

		List<AccountEntry> accountEntries =
			_accountEntryLocalService.getUserAccountEntries(
				_user.getUserId(),
				AccountConstants.PARENT_ACCOUNT_ENTRY_ID_DEFAULT, null,
				new String[] {
					AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS,
					AccountConstants.ACCOUNT_ENTRY_TYPE_PERSON
				},
				0, 1);

		Assert.assertNotNull(accountEntries);
		Assert.assertEquals(
			accountEntries.get(0),
			_currentAccountEntryManager.getCurrentAccountEntry(
				TestPropsValues.getGroupId(), _user.getUserId()));
	}

	@Test
	public void testGetCurrentAccountEntryDefault() throws Exception {
		_addAccountEntry(
			"aInactive", false, AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS);
		_addAccountEntry(
			"bInvalidType", true, AccountConstants.ACCOUNT_ENTRY_TYPE_PERSON);

		_setAllowedTypes(
			TestPropsValues.getGroupId(),
			new String[] {AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS});

		AccountEntry noPermissionAccountEntry = _addAccountEntry(
			"cNoPermission", true,
			AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS);

		_accountEntryUserRelLocalService.deleteAccountEntryUserRels(
			noPermissionAccountEntry.getAccountEntryId(),
			new long[] {_user.getUserId()});

		Organization organization = OrganizationTestUtil.addOrganization();

		_organizationLocalService.addUserOrganization(
			_user.getUserId(), organization.getOrganizationId());
		_accountEntryOrganizationRelLocalService.addAccountEntryOrganizationRel(
			noPermissionAccountEntry.getAccountEntryId(),
			organization.getOrganizationId());

		AccountEntry expectedAccountEntry = _addAccountEntry(
			"dHasPermission", true,
			AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS);

		AccountEntry currentAccountEntry =
			_currentAccountEntryManager.getCurrentAccountEntry(
				TestPropsValues.getGroupId(), _user.getUserId());

		Assert.assertNotNull(currentAccountEntry);
		Assert.assertEquals(expectedAccountEntry, currentAccountEntry);
	}

	@Test
	public void testGetCurrentAccountEntryForGroupWithRestrictedTypes()
		throws Exception {

		AccountEntry accountEntry = null;

		String[] allowedTypes = {AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS};

		List<AccountEntry> accountEntries =
			_accountEntryLocalService.getUserAccountEntries(
				TestPropsValues.getUserId(),
				AccountConstants.PARENT_ACCOUNT_ENTRY_ID_DEFAULT, null,
				allowedTypes, 0, 1);

		if (!accountEntries.isEmpty()) {
			accountEntry = accountEntries.get(0);
		}

		Group group = GroupTestUtil.addGroup();

		_setAllowedTypes(group.getGroupId(), allowedTypes);

		AccountEntry personAccountEntry =
			AccountEntryTestUtil.addPersonAccountEntry(
				_accountEntryLocalService);

		Assert.assertEquals(
			accountEntry,
			_currentAccountEntryManager.getCurrentAccountEntry(
				group.getGroupId(), TestPropsValues.getUserId()));

		group = GroupTestUtil.addGroup();

		_currentAccountEntryManager.setCurrentAccountEntry(
			personAccountEntry.getAccountEntryId(), group.getGroupId(),
			TestPropsValues.getUserId());

		_setAllowedTypes(group.getGroupId(), allowedTypes);

		Assert.assertEquals(
			accountEntry,
			_currentAccountEntryManager.getCurrentAccountEntry(
				group.getGroupId(), TestPropsValues.getUserId()));
	}

	@Test
	public void testGetCurrentAccountEntryForGuestUser() throws Exception {
		Assert.assertEquals(
			_accountEntryLocalService.getGuestAccountEntry(
				TestPropsValues.getCompanyId()),
			_currentAccountEntryManager.getCurrentAccountEntry(
				TestPropsValues.getGroupId(), UserConstants.USER_ID_DEFAULT));
	}

	@Test
	public void testGetCurrentAccountEntryForUserWithNoAccountEntries()
		throws Exception {

		Assert.assertNull(
			_currentAccountEntryManager.getCurrentAccountEntry(
				TestPropsValues.getGroupId(), _user.getUserId()));
	}

	@Test
	public void testGetCurrentAccountEntryWithNoViewPermission()
		throws Exception {

		AccountEntry accountEntry = AccountEntryTestUtil.addAccountEntry(
			_accountEntryLocalService);

		_currentAccountEntryManager.setCurrentAccountEntry(
			accountEntry.getAccountEntryId(), TestPropsValues.getGroupId(),
			_user.getUserId());

		Assert.assertNull(
			_currentAccountEntryManager.getCurrentAccountEntry(
				TestPropsValues.getGroupId(), _user.getUserId()));
	}

	@Test
	public void testGetCurrentAccountEntryWithViewPermission()
		throws Exception {

		AccountEntry accountEntry = AccountEntryTestUtil.addAccountEntry(
			_accountEntryLocalService);

		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		UserLocalServiceUtil.addRoleUser(role.getRoleId(), _user.getUserId());

		_resourcePermissionLocalService.setResourcePermissions(
			TestPropsValues.getCompanyId(), AccountEntry.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			String.valueOf(accountEntry.getAccountEntryId()), role.getRoleId(),
			new String[] {ActionKeys.VIEW});

		_currentAccountEntryManager.setCurrentAccountEntry(
			accountEntry.getAccountEntryId(), TestPropsValues.getGroupId(),
			_user.getUserId());

		Assert.assertEquals(
			accountEntry,
			_currentAccountEntryManager.getCurrentAccountEntry(
				TestPropsValues.getGroupId(), _user.getUserId()));
	}

	@Test
	public void testSetCurrentAccountEntry() throws Exception {
		AccountEntry accountEntry = AccountEntryTestUtil.addAccountEntry(
			_accountEntryLocalService);

		_currentAccountEntryManager.setCurrentAccountEntry(
			accountEntry.getAccountEntryId(), TestPropsValues.getGroupId(),
			TestPropsValues.getUserId());

		Assert.assertEquals(
			accountEntry,
			_currentAccountEntryManager.getCurrentAccountEntry(
				TestPropsValues.getGroupId(), TestPropsValues.getUserId()));
	}

	@Test
	public void testSetCurrentAccountEntryForGroupWithRestrictedTypes()
		throws Exception {

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.account.internal.manager." +
					"CurrentAccountEntryManagerImpl",
				LoggerTestUtil.WARN)) {

			Group group = GroupTestUtil.addGroup();

			_setAllowedTypes(
				group.getGroupId(),
				new String[] {AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS});

			AccountEntry personAccountEntry =
				AccountEntryTestUtil.addPersonAccountEntry(
					_accountEntryLocalService);

			_currentAccountEntryManager.setCurrentAccountEntry(
				personAccountEntry.getAccountEntryId(), group.getGroupId(),
				TestPropsValues.getUserId());

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Assert.assertEquals(LoggerTestUtil.WARN, logEntry.getPriority());

			Throwable throwable = logEntry.getThrowable();

			Assert.assertEquals(
				AccountEntryTypeException.class, throwable.getClass());

			Assert.assertEquals(
				"Cannot set a current account entry of a disallowed type: " +
					"person",
				throwable.getMessage());
		}
	}

	private AccountEntry _addAccountEntry(
			String name, boolean active, String type)
		throws Exception {

		AccountEntry accountEntry = AccountEntryTestUtil.addAccountEntry(
			_accountEntryLocalService);

		accountEntry.setName(name);

		if (!active) {
			accountEntry.setStatus(WorkflowConstants.STATUS_INACTIVE);
		}

		accountEntry.setType(type);

		accountEntry = _accountEntryLocalService.updateAccountEntry(
			accountEntry);

		_accountEntryUserRelLocalService.addAccountEntryUserRel(
			accountEntry.getAccountEntryId(), _user.getUserId());

		return accountEntry;
	}

	private void _setAllowedTypes(long groupId, String[] allowedTypes)
		throws Exception {

		_accountEntryGroupSettings.setAllowedTypes(groupId, allowedTypes);

		// Force async operations to complete before returning

		ConfigurationTestUtil.saveConfiguration(
			RandomTestUtil.randomString(), null);
	}

	@Inject
	private AccountEntryGroupSettings _accountEntryGroupSettings;

	@Inject
	private AccountEntryLocalService _accountEntryLocalService;

	@Inject
	private AccountEntryOrganizationRelLocalService
		_accountEntryOrganizationRelLocalService;

	@Inject
	private AccountEntryUserRelLocalService _accountEntryUserRelLocalService;

	@Inject
	private CurrentAccountEntryManager _currentAccountEntryManager;

	@Inject
	private OrganizationLocalService _organizationLocalService;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	private User _user;

}
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
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.OrganizationTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
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
		_group = GroupTestUtil.addGroup();
		_user = UserTestUtil.addUser();
	}

	@Test
	public void testGetCurrentAccountEntry() throws Exception {
		AccountEntry accountEntry1 = _addAccountEntry(
			"aaa", true, AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS);

		_addAccountEntry(
			"bbb", true, AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS);

		Assert.assertEquals(
			accountEntry1,
			_currentAccountEntryManager.getCurrentAccountEntry(
				_group.getGroupId(), _user.getUserId()));
	}

	@Test
	public void testGetCurrentAccountEntryDefault() throws Exception {
		_addAccountEntry(
			"aInactive", false, AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS);
		_addAccountEntry(
			"bInvalidType", true, AccountConstants.ACCOUNT_ENTRY_TYPE_PERSON);

		_setAllowedTypes(
			_group.getGroupId(),
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
				_group.getGroupId(), _user.getUserId());

		Assert.assertNotNull(currentAccountEntry);
		Assert.assertEquals(expectedAccountEntry, currentAccountEntry);
	}

	@Test
	public void testGetCurrentAccountEntryForGroupWithRestrictedTypes()
		throws Exception {

		Group group = GroupTestUtil.addGroup();
		AccountEntry personAccountEntry = _addAccountEntry(
			RandomTestUtil.randomString(), true,
			AccountConstants.ACCOUNT_ENTRY_TYPE_PERSON);

		_currentAccountEntryManager.setCurrentAccountEntry(
			personAccountEntry.getAccountEntryId(), group.getGroupId(),
			_user.getUserId());

		Assert.assertEquals(
			personAccountEntry,
			_currentAccountEntryManager.getCurrentAccountEntry(
				group.getGroupId(), _user.getUserId()));

		_setAllowedTypes(
			group.getGroupId(),
			new String[] {AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS});

		Assert.assertNull(
			_currentAccountEntryManager.getCurrentAccountEntry(
				group.getGroupId(), _user.getUserId()));
	}

	@Test
	public void testGetCurrentAccountEntryForGuestUser() throws Exception {
		Assert.assertEquals(
			_accountEntryLocalService.getGuestAccountEntry(
				TestPropsValues.getCompanyId()),
			_currentAccountEntryManager.getCurrentAccountEntry(
				_group.getGroupId(), UserConstants.USER_ID_DEFAULT));
	}

	@Test
	public void testGetCurrentAccountEntryForUserWithNoAccountEntries()
		throws Exception {

		Assert.assertNull(
			_currentAccountEntryManager.getCurrentAccountEntry(
				_group.getGroupId(), _user.getUserId()));
	}

	@Test
	public void testGetCurrentAccountEntryWithNoViewPermission()
		throws Exception {

		AccountEntry accountEntry = AccountEntryTestUtil.addAccountEntry(
			_accountEntryLocalService);

		_currentAccountEntryManager.setCurrentAccountEntry(
			accountEntry.getAccountEntryId(), _group.getGroupId(),
			_user.getUserId());

		Assert.assertNull(
			_currentAccountEntryManager.getCurrentAccountEntry(
				_group.getGroupId(), _user.getUserId()));
	}

	@Test
	public void testGetCurrentAccountEntryWithViewPermission()
		throws Exception {

		AccountEntry accountEntry = AccountEntryTestUtil.addAccountEntry(
			_accountEntryLocalService);

		_accountEntryUserRelLocalService.addAccountEntryUserRel(
			accountEntry.getAccountEntryId(), _user.getUserId());

		_currentAccountEntryManager.setCurrentAccountEntry(
			accountEntry.getAccountEntryId(), _group.getGroupId(),
			_user.getUserId());

		Assert.assertEquals(
			accountEntry,
			_currentAccountEntryManager.getCurrentAccountEntry(
				_group.getGroupId(), _user.getUserId()));
	}

	@Test
	public void testSetCurrentAccountEntry() throws Exception {
		AccountEntry accountEntry = _addAccountEntry(
			RandomTestUtil.randomString(), true,
			AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS);

		_currentAccountEntryManager.setCurrentAccountEntry(
			accountEntry.getAccountEntryId(), _group.getGroupId(),
			_user.getUserId());

		Assert.assertEquals(
			accountEntry,
			_currentAccountEntryManager.getCurrentAccountEntry(
				_group.getGroupId(), _user.getUserId()));
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
				_user.getUserId());

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

	private Group _group;

	@Inject
	private OrganizationLocalService _organizationLocalService;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	private User _user;

}
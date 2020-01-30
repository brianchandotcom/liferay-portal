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

package com.liferay.account.internal.model.listener.test;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountRole;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountEntryOrganizationRelLocalService;
import com.liferay.account.service.AccountEntryUserRelLocalService;
import com.liferay.account.service.AccountRoleLocalService;
import com.liferay.account.service.test.AccountEntryTestUtil;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.OrganizationTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Pei-Jung Lan
 */
@RunWith(Arquillian.class)
public class AccountEntryModelListenerWhenDeletingAccountEntryTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_accountEntry = AccountEntryTestUtil.addAccountEntry(
			_accountEntryLocalService);
	}

	@Test
	public void testAccountEntryOrganizationRelDeleted() throws Exception {
		_organization = OrganizationTestUtil.addOrganization();

		_accountEntryOrganizationRelLocalService.addAccountEntryOrganizationRel(
			_accountEntry.getAccountEntryId(),
			_organization.getOrganizationId());

		Assert.assertTrue(
			_accountEntryOrganizationRelLocalService.
				hasAccountEntryOrganizationRel(
					_accountEntry.getAccountEntryId(),
					_organization.getOrganizationId()));

		_accountEntryLocalService.deleteAccountEntry(_accountEntry);

		Assert.assertNull(
			_accountEntryLocalService.fetchAccountEntry(
				_accountEntry.getAccountEntryId()));

		Assert.assertFalse(
			_accountEntryOrganizationRelLocalService.
				hasAccountEntryOrganizationRel(
					_accountEntry.getAccountEntryId(),
					_organization.getOrganizationId()));
	}

	@Test
	public void testAccountEntryUserRelDeleted() throws Exception {
		_user = UserTestUtil.addUser();

		_accountEntryUserRelLocalService.addAccountEntryUserRel(
			_accountEntry.getAccountEntryId(), _user.getUserId());

		Assert.assertTrue(
			_accountEntryUserRelLocalService.hasAccountEntryUserRel(
				_accountEntry.getAccountEntryId(), _user.getUserId()));

		_accountEntryLocalService.deleteAccountEntry(_accountEntry);

		Assert.assertNull(
			_accountEntryLocalService.fetchAccountEntry(
				_accountEntry.getAccountEntryId()));

		Assert.assertFalse(
			_accountEntryUserRelLocalService.hasAccountEntryUserRel(
				_accountEntry.getAccountEntryId(), _user.getUserId()));
	}

	@Test
	public void testAccountRoleDeleted() throws Exception {
		AccountRole accountRole = _accountRoleLocalService.addAccountRole(
			TestPropsValues.getUserId(), _accountEntry.getAccountEntryId(),
			RandomTestUtil.randomString(), null, null);

		Assert.assertEquals(
			_accountEntry.getAccountEntryId(), accountRole.getAccountEntryId());

		_accountEntryLocalService.deleteAccountEntry(_accountEntry);

		Assert.assertNull(
			_accountRoleLocalService.fetchAccountRole(
				accountRole.getAccountRoleId()));
	}

	private AccountEntry _accountEntry;

	@Inject
	private AccountEntryLocalService _accountEntryLocalService;

	@Inject
	private AccountEntryOrganizationRelLocalService
		_accountEntryOrganizationRelLocalService;

	@Inject
	private AccountEntryUserRelLocalService _accountEntryUserRelLocalService;

	@Inject
	private AccountRoleLocalService _accountRoleLocalService;

	@DeleteAfterTestRun
	private Organization _organization;

	@Inject
	private RoleLocalService _roleLocalService;

	@DeleteAfterTestRun
	private User _user;

}
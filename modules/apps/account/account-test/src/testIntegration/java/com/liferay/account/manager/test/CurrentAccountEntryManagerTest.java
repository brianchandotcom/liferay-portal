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
import com.liferay.account.manager.CurrentAccountEntryManager;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountEntryUserRelLocalService;
import com.liferay.account.service.test.util.AccountEntryTestUtil;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Pei-Jung Lan
 */
@RunWith(Arquillian.class)
public class CurrentAccountEntryManagerTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testGetCurrentAccountEntry() throws Exception {
		User user = UserTestUtil.addUser();

		AccountEntry accountEntry1 = AccountEntryTestUtil.addAccountEntry(
			_accountEntryLocalService);

		_accountEntryUserRelLocalService.addAccountEntryUserRel(
			accountEntry1.getAccountEntryId(), user.getUserId());

		AccountEntry accountEntry2 = AccountEntryTestUtil.addAccountEntry(
			_accountEntryLocalService);

		_accountEntryUserRelLocalService.addAccountEntryUserRel(
			accountEntry2.getAccountEntryId(), user.getUserId());

		List<AccountEntry> accountEntries =
			_accountEntryLocalService.getUserAccountEntries(
				user.getUserId(),
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
				TestPropsValues.getGroupId(), user.getUserId()));
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

		User user = UserTestUtil.addUser();

		Assert.assertNull(
			_currentAccountEntryManager.getCurrentAccountEntry(
				TestPropsValues.getGroupId(), user.getUserId()));
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

	@Inject
	private AccountEntryLocalService _accountEntryLocalService;

	@Inject
	private AccountEntryUserRelLocalService _accountEntryUserRelLocalService;

	@Inject
	private CurrentAccountEntryManager _currentAccountEntryManager;

}
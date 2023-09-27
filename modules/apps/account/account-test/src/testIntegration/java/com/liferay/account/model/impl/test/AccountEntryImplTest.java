/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.model.impl.test;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.test.util.AccountEntryArgs;
import com.liferay.account.service.test.util.AccountEntryTestUtil;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.AddressLocalService;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.OrganizationTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Drew Brokke
 */
@RunWith(Arquillian.class)
public class AccountEntryImplTest {

	@Test
	public void testFetchOrganizations() throws Exception {
		OrganizationTestUtil.addOrganization();

		AccountEntry accountEntry = AccountEntryTestUtil.addAccountEntry();

		_testFetchOrganizations(accountEntry);

		Organization organization = OrganizationTestUtil.addOrganization();

		accountEntry = AccountEntryTestUtil.addAccountEntry(
			AccountEntryArgs.withOrganizations(organization));

		_testFetchOrganizations(accountEntry, organization);
	}

	@Test
	public void testFetchUsers() throws Exception {
		UserTestUtil.addUser();

		AccountEntry accountEntry = AccountEntryTestUtil.addAccountEntry();

		_testFetchUsers(accountEntry);

		User user = UserTestUtil.addUser();

		accountEntry = AccountEntryTestUtil.addAccountEntry(
			AccountEntryArgs.withUsers(user));

		_testFetchUsers(accountEntry, user);
	}

	@Test
	public void testGetAccountEntryGroup() throws Exception {
		AccountEntry accountEntry = AccountEntryTestUtil.addAccountEntry();

		Assert.assertEquals(
			_getAccountEntryGroup(accountEntry),
			accountEntry.getAccountEntryGroup());

		accountEntry = _accountEntryLocalService.createAccountEntry(0);

		Assert.assertNull(accountEntry.getAccountEntryGroup());
	}

	@Test
	public void testGetAccountEntryGroupId() throws Exception {
		AccountEntry accountEntry = AccountEntryTestUtil.addAccountEntry();

		Group group = _getAccountEntryGroup(accountEntry);

		Assert.assertEquals(
			group.getGroupId(), accountEntry.getAccountEntryGroupId());

		accountEntry = _accountEntryLocalService.createAccountEntry(0);

		Assert.assertEquals(
			GroupConstants.DEFAULT_LIVE_GROUP_ID,
			accountEntry.getAccountEntryGroupId());
	}

	@Test
	public void testGetDefaultBillingAddress() throws Exception {
		AccountEntry accountEntry = AccountEntryTestUtil.addAccountEntry();

		Assert.assertNull(accountEntry.getDefaultBillingAddress());

		Address address = _addAddress();

		accountEntry = _accountEntryLocalService.updateDefaultBillingAddressId(
			accountEntry.getAccountEntryId(), address.getAddressId());

		Assert.assertEquals(address, accountEntry.getDefaultBillingAddress());
	}

	@Test
	public void testGetDefaultShippingAddress() throws Exception {
		AccountEntry accountEntry = AccountEntryTestUtil.addAccountEntry();

		Assert.assertNull(accountEntry.getDefaultShippingAddress());

		Address address = _addAddress();

		accountEntry = _accountEntryLocalService.updateDefaultShippingAddressId(
			accountEntry.getAccountEntryId(), address.getAddressId());

		Assert.assertEquals(address, accountEntry.getDefaultShippingAddress());
	}

	@Rule
	public final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	private Address _addAddress() throws Exception {
		User user = TestPropsValues.getUser();

		return _addressLocalService.addAddress(
			null, user.getUserId(), null, user.getContactId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), null, null,
			RandomTestUtil.randomString(), null, 0L, 0L,
			RandomTestUtil.randomLong(), false, false, "1234567890",
			ServiceContextTestUtil.getServiceContext());
	}

	private Group _getAccountEntryGroup(AccountEntry accountEntry) {
		return _groupLocalService.fetchGroup(
			accountEntry.getCompanyId(),
			_classNameLocalService.getClassNameId(AccountEntry.class),
			accountEntry.getAccountEntryId());
	}

	private void _testFetchOrganizations(
		AccountEntry accountEntry, Organization... expectedOrganizations) {

		List<Organization> organizations = accountEntry.fetchOrganizations();

		Assert.assertEquals(
			organizations.toString(), expectedOrganizations.length,
			organizations.size());
		Assert.assertTrue(
			organizations.containsAll(Arrays.asList(expectedOrganizations)));
	}

	private void _testFetchUsers(
		AccountEntry accountEntry, User... expectedUsers) {

		List<User> users = accountEntry.fetchUsers();

		Assert.assertEquals(
			users.toString(), expectedUsers.length, users.size());
		Assert.assertTrue(users.containsAll(Arrays.asList(expectedUsers)));
	}

	@Inject
	private AccountEntryLocalService _accountEntryLocalService;

	@Inject
	private AddressLocalService _addressLocalService;

	@Inject
	private ClassNameLocalService _classNameLocalService;

	@Inject
	private GroupLocalService _groupLocalService;

}
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

import com.liferay.account.exception.DuplicateAccountEntryOrganizationRelException;
import com.liferay.account.exception.NoSuchEntryException;
import com.liferay.account.exception.NoSuchEntryOrganizationRelException;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountEntryOrganizationRel;
import com.liferay.account.model.AccountEntryOrganizationRelModel;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountEntryOrganizationRelLocalService;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.exception.NoSuchOrganizationException;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.OrganizationTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.List;

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
public class AccountEntryOrganizationRelLocalServiceTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_accountEntry = AccountEntryTestUtil.addAccountEntry(
			_accountEntryLocalService);

		_organization = OrganizationTestUtil.addOrganization();
	}

	@Test
	public void testAddAccountEntryOrganizationRel() throws Exception {
		AccountEntryOrganizationRel accountEntryOrganizationRel =
			_accountEntryOrganizationRelLocalService.
				addAccountEntryOrganizationRel(
					_accountEntry.getAccountEntryId(),
					_organization.getOrganizationId());

		_accountEntryOrganizationRels.add(accountEntryOrganizationRel);

		Assert.assertNotNull(accountEntryOrganizationRel);
		Assert.assertNotNull(
			_accountEntryOrganizationRelLocalService.
				fetchAccountEntryOrganizationRel(
					accountEntryOrganizationRel.getPrimaryKey()));
	}

	@Test
	public void testAddAccountEntryOrganizationRels() throws Exception {
		_organizations.add(OrganizationTestUtil.addOrganization());
		_organizations.add(OrganizationTestUtil.addOrganization());

		long[] organizationIds = ListUtil.toLongArray(
			_organizations, Organization.ORGANIZATION_ID_ACCESSOR);

		_accountEntryOrganizationRelLocalService.
			addAccountEntryOrganizationRels(
				_accountEntry.getAccountEntryId(), organizationIds);

		Assert.assertEquals(
			2,
			_accountEntryOrganizationRelLocalService.
				getAccountEntryOrganizationRelsCount(
					_accountEntry.getAccountEntryId()));

		List<AccountEntryOrganizationRel> accountEntryOrganizationRels =
			_accountEntryOrganizationRelLocalService.
				getAccountEntryOrganizationRels(
					_accountEntry.getAccountEntryId());

		long[] accountEntryOrganizationIds = ListUtil.toLongArray(
			accountEntryOrganizationRels,
			AccountEntryOrganizationRelModel::getOrganizationId);

		Assert.assertTrue(
			ArrayUtil.containsAll(
				organizationIds, accountEntryOrganizationIds));
		Assert.assertTrue(
			ArrayUtil.containsAll(
				accountEntryOrganizationIds, organizationIds));
	}

	@Test(expected = DuplicateAccountEntryOrganizationRelException.class)
	public void testAddAccountEntryOrganizationRelThrowsDuplicateAccountEntryOrganizationRelException()
		throws Exception {

		_accountEntryOrganizationRels.add(
			_accountEntryOrganizationRelLocalService.
				addAccountEntryOrganizationRel(
					_accountEntry.getAccountEntryId(),
					_organization.getOrganizationId()));

		_accountEntryOrganizationRelLocalService.addAccountEntryOrganizationRel(
			_accountEntry.getAccountEntryId(),
			_organization.getOrganizationId());
	}

	@Test(expected = NoSuchEntryException.class)
	public void testAddAccountEntryOrganizationRelThrowsNoSuchEntryException()
		throws Exception {

		_accountEntryOrganizationRelLocalService.addAccountEntryOrganizationRel(
			RandomTestUtil.randomLong(), _organization.getOrganizationId());
	}

	@Test(expected = NoSuchOrganizationException.class)
	public void testAddAccountEntryOrganizationRelThrowsNoSuchOrganizationException()
		throws Exception {

		_accountEntryOrganizationRelLocalService.addAccountEntryOrganizationRel(
			_accountEntry.getAccountEntryId(), RandomTestUtil.randomLong());
	}

	@Test
	public void testDeleteAccountEntryOrganizationRel() throws Exception {
		_accountEntryOrganizationRelLocalService.addAccountEntryOrganizationRel(
			_accountEntry.getAccountEntryId(),
			_organization.getOrganizationId());

		Assert.assertTrue(
			_accountEntryOrganizationRelLocalService.
				hasAccountEntryOrganizationRel(
					_accountEntry.getAccountEntryId(),
					_organization.getOrganizationId()));

		_accountEntryOrganizationRelLocalService.
			deleteAccountEntryOrganizationRel(
				_accountEntry.getAccountEntryId(),
				_organization.getOrganizationId());

		Assert.assertFalse(
			_accountEntryOrganizationRelLocalService.
				hasAccountEntryOrganizationRel(
					_accountEntry.getAccountEntryId(),
					_organization.getOrganizationId()));
	}

	@Test
	public void testDeleteAccountEntryOrganizationRels() throws Exception {
		_organizations.add(OrganizationTestUtil.addOrganization());
		_organizations.add(OrganizationTestUtil.addOrganization());

		long[] organizationIds = ListUtil.toLongArray(
			_organizations, Organization.ORGANIZATION_ID_ACCESSOR);

		_accountEntryOrganizationRelLocalService.
			addAccountEntryOrganizationRels(
				_accountEntry.getAccountEntryId(), organizationIds);

		Assert.assertEquals(
			2,
			_accountEntryOrganizationRelLocalService.
				getAccountEntryOrganizationRelsCount(
					_accountEntry.getAccountEntryId()));

		_accountEntryOrganizationRelLocalService.
			deleteAccountEntryOrganizationRels(
				_accountEntry.getAccountEntryId(), organizationIds);

		Assert.assertEquals(
			0,
			_accountEntryOrganizationRelLocalService.
				getAccountEntryOrganizationRelsCount(
					_accountEntry.getAccountEntryId()));
	}

	@Test(expected = NoSuchEntryOrganizationRelException.class)
	public void testDeleteAccountEntryUserRelThrowsNoSuchEntryOrganizationRelException()
		throws Exception {

		_accountEntryOrganizationRelLocalService.
			deleteAccountEntryOrganizationRel(
				_accountEntry.getAccountEntryId(),
				_organization.getOrganizationId());
	}

	@Test
	public void testGetAccountEntryOrganizationRelsByOrganizationId()
		throws Exception {

		_accountEntries.add(
			AccountEntryTestUtil.addAccountEntry(_accountEntryLocalService));
		_accountEntries.add(
			AccountEntryTestUtil.addAccountEntry(_accountEntryLocalService));

		long[] expectedAccountEntryIds = ListUtil.toLongArray(
			_accountEntries, AccountEntry.ACCOUNT_ENTRY_ID_ACCESSOR);

		for (long accountEntryId : expectedAccountEntryIds) {
			_accountEntryOrganizationRelLocalService.
				addAccountEntryOrganizationRel(
					accountEntryId, _organization.getOrganizationId());
		}

		List<AccountEntryOrganizationRel> accountEntryOrganizationRels =
			_accountEntryOrganizationRelLocalService.
				getAccountEntryOrganizationRelsByOrganizationId(
					_organization.getOrganizationId());

		long[] accountEntryIds = ListUtil.toLongArray(
			accountEntryOrganizationRels,
			AccountEntryOrganizationRelModel::getAccountEntryId);

		Assert.assertTrue(
			ArrayUtil.containsAll(expectedAccountEntryIds, accountEntryIds));
		Assert.assertTrue(
			ArrayUtil.containsAll(accountEntryIds, expectedAccountEntryIds));
	}

	@DeleteAfterTestRun
	private final List<AccountEntry> _accountEntries = new ArrayList<>();

	@DeleteAfterTestRun
	private AccountEntry _accountEntry;

	@Inject
	private AccountEntryLocalService _accountEntryLocalService;

	@Inject
	private AccountEntryOrganizationRelLocalService
		_accountEntryOrganizationRelLocalService;

	@DeleteAfterTestRun
	private final List<AccountEntryOrganizationRel>
		_accountEntryOrganizationRels = new ArrayList<>();

	@DeleteAfterTestRun
	private Organization _organization;

	@DeleteAfterTestRun
	private final List<Organization> _organizations = new ArrayList<>();

}
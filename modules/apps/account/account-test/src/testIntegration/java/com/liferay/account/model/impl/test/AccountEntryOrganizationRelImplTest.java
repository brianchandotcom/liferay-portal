/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.model.impl.test;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountEntryOrganizationRel;
import com.liferay.account.service.AccountEntryOrganizationRelLocalService;
import com.liferay.account.service.test.util.AccountEntryTestUtil;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.OrganizationTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Drew Brokke
 */
@RunWith(Arquillian.class)
public class AccountEntryOrganizationRelImplTest {

	@Before
	public void setUp() throws Exception {
		_accountEntry = AccountEntryTestUtil.addAccountEntry();
		_organization = OrganizationTestUtil.addOrganization();

		_accountEntryOrganizationRel =
			_accountEntryOrganizationRelLocalService.
				addAccountEntryOrganizationRel(
					_accountEntry.getAccountEntryId(),
					_organization.getOrganizationId());
	}

	@Test
	public void testFetchAccountEntry() {
		Assert.assertEquals(
			_accountEntry, _accountEntryOrganizationRel.fetchAccountEntry());
	}

	@Test
	public void testFetchOrganization() {
		Assert.assertEquals(
			_organization, _accountEntryOrganizationRel.fetchOrganization());
	}

	@Test
	public void testGetAccountEntry() throws Exception {
		Assert.assertEquals(
			_accountEntry, _accountEntryOrganizationRel.getAccountEntry());
	}

	@Test
	public void testGetOrganization() throws Exception {
		Assert.assertEquals(
			_organization, _accountEntryOrganizationRel.getOrganization());
	}

	@Rule
	public final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	private AccountEntry _accountEntry;
	private AccountEntryOrganizationRel _accountEntryOrganizationRel;

	@Inject
	private AccountEntryOrganizationRelLocalService
		_accountEntryOrganizationRelLocalService;

	private Organization _organization;

}
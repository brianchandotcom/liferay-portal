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

package com.liferay.account.model.impl.test;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountEntryOrganizationRel;
import com.liferay.account.service.AccountEntryLocalService;
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
		_accountEntry = AccountEntryTestUtil.addAccountEntry(
			_accountEntryLocalService);
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

	@Inject
	private AccountEntryLocalService _accountEntryLocalService;

	private AccountEntryOrganizationRel _accountEntryOrganizationRel;

	@Inject
	private AccountEntryOrganizationRelLocalService
		_accountEntryOrganizationRelLocalService;

	private Organization _organization;

}
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

package com.liferay.commerce.organization.service.impl.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.organization.service.CommerceOrganizationLocalService;
import com.liferay.commerce.organization.service.impl.test.util.CommerceOrganizationTestUtil;
import com.liferay.commerce.organization.service.impl.test.util.OrganizationTypeSetUp;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.service.OrganizationLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.OrganizationTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.frutilla.FrutillaRule;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Luca Pellizzon
 */
@RunWith(Arquillian.class)
public class CommerceOrganizationLocalServiceImplTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_organizationTypeSetUp = new OrganizationTypeSetUp(_configurationAdmin);

		_organizationTypeSetUp.setUpEnvironment();
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		_organizationTypeSetUp.tearDownEnvironment();
	}

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_parentOrganization = OrganizationTestUtil.addOrganization();
	}

	@After
	public void tearDown() throws Exception {
		OrganizationLocalServiceUtil.deleteOrganization(_organization);
		OrganizationLocalServiceUtil.deleteOrganization(_parentOrganization);
	}

	@Test
	public void testGetAccountOrganization() throws Exception {
		frutillaRule.scenario(
			"Get the parent organization account starting from a given " +
				"organization"
		).given(
			"An organization and its parent organiation"
		).when(
			"Getting the Account Organization"
		).then(
			"I get the parent organization"
		);

		_organization = CommerceOrganizationTestUtil.addOrganization(
			_group.getGroupId(), _parentOrganization.getOrganizationId());

		Organization actualOrganization =
			_commerceOrganizationLocalService.getAccountOrganization(
				_organization.getOrganizationId());

		Assert.assertEquals(
			_organization.getOrganizationId(),
			actualOrganization.getOrganizationId());
	}

	@Test
	public void testGetOrganizationPrimaryAddress() throws Exception {
		frutillaRule.scenario(
			"Get the primary address for an organization"
		).given(
			"An organization"
		).when(
			"Getting the primary address of the organization"
		).then(
			"I get the primary address of the organization"
		);

		_organization = CommerceOrganizationTestUtil.addOrganization(
			_group.getGroupId(), _parentOrganization.getOrganizationId());

		Address expectedAddress =
			CommerceOrganizationTestUtil.addOrganizationPrimaryAddress(
				_group.getGroupId(), _organization.getOrganizationId());

		Address actualAddress =
			_commerceOrganizationLocalService.getOrganizationPrimaryAddress(
				_organization.getOrganizationId());

		Assert.assertEquals(
			expectedAddress.getAddressId(), actualAddress.getAddressId());
	}

	@Rule
	public FrutillaRule frutillaRule = new FrutillaRule();

	@Inject
	private static ConfigurationAdmin _configurationAdmin;

	private static OrganizationTypeSetUp _organizationTypeSetUp;

	@Inject
	private CommerceOrganizationLocalService _commerceOrganizationLocalService;

	@DeleteAfterTestRun
	private Group _group;

	private Organization _organization;
	private Organization _parentOrganization;

}
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

package com.liferay.portal.service;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.OrganizationConstants;
import com.liferay.portal.kernel.service.OrganizationLocalServiceUtil;
import com.liferay.portal.kernel.service.OrganizationServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.OrganizationTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Drew Brokke
 */
public class OrganizationServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testGetGtOrganizations() throws Exception {
		for (int i = 0; i < 10; i++) {
			_organizations.add(OrganizationTestUtil.addOrganization());
		}

		long parentOrganizationId = 0;
		int size = 5;

		List<Organization> organizations =
			OrganizationServiceUtil.getGtOrganizations(
				0, TestPropsValues.getCompanyId(), parentOrganizationId, size);

		Assert.assertFalse(organizations.isEmpty());
		Assert.assertEquals(
			organizations.toString(), size, organizations.size());

		Organization lastOrganization = organizations.get(
			organizations.size() - 1);

		organizations = OrganizationServiceUtil.getGtOrganizations(
			lastOrganization.getOrganizationId(),
			TestPropsValues.getCompanyId(), parentOrganizationId, size);

		Assert.assertFalse(organizations.isEmpty());
		Assert.assertEquals(
			organizations.toString(), size, organizations.size());

		long previousOrganizationId = 0;

		for (Organization organization : organizations) {
			long organizationId = organization.getOrganizationId();

			Assert.assertTrue(
				organizationId > lastOrganization.getOrganizationId());
			Assert.assertTrue(organizationId > previousOrganizationId);

			previousOrganizationId = organizationId;
		}
	}

	@Test
	public void testGetOrganizationsLikeName() throws Exception {
		List<Organization> allChildOrganizations = new ArrayList<>();
		Organization parentOrganziation =
			OrganizationTestUtil.addOrganization();

		List<Organization> allOrganizations = new ArrayList<>(
			OrganizationLocalServiceUtil.getOrganizations(
				TestPropsValues.getCompanyId(),
				OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID));

		try {
			String name = RandomTestUtil.randomString(10);

			long parentOrganizationId = parentOrganziation.getOrganizationId();

			List<Organization> likeNameChildOrganizations = new ArrayList<>();

			for (int i = 0; i < 10; i++) {
				Organization organization =
					OrganizationTestUtil.addOrganization(
						parentOrganizationId, name + i, false);

				likeNameChildOrganizations.add(organization);
			}

			allChildOrganizations.addAll(likeNameChildOrganizations);
			allChildOrganizations.add(
				OrganizationTestUtil.addOrganization(
					parentOrganizationId, RandomTestUtil.randomString(10),
					false));
			allChildOrganizations.add(
				OrganizationTestUtil.addOrganization(
					parentOrganizationId, RandomTestUtil.randomString(10),
					false));
			allChildOrganizations.add(
				OrganizationTestUtil.addOrganization(
					parentOrganizationId, RandomTestUtil.randomString(10),
					false));

			allOrganizations.addAll(allChildOrganizations);

			assertExpectedOrganizations(
				likeNameChildOrganizations, parentOrganizationId, name + "%");
			assertExpectedOrganizations(
				likeNameChildOrganizations, parentOrganizationId,
				StringUtil.toLowerCase(name) + "%");
			assertExpectedOrganizations(
				likeNameChildOrganizations, parentOrganizationId,
				StringUtil.toUpperCase(name) + "%");
			assertExpectedOrganizations(
				likeNameChildOrganizations,
				OrganizationConstants.ANY_PARENT_ORGANIZATION_ID,
				StringUtil.toUpperCase(name) + "%");
			assertExpectedOrganizations(
				allChildOrganizations, parentOrganizationId, null);
			assertExpectedOrganizations(
				allChildOrganizations, parentOrganizationId, "");
			assertExpectedOrganizations(
				allOrganizations,
				OrganizationConstants.ANY_PARENT_ORGANIZATION_ID, "");
		}
		finally {
			for (Organization childOrganization : allChildOrganizations) {
				OrganizationLocalServiceUtil.deleteOrganization(
					childOrganization);
			}

			OrganizationLocalServiceUtil.deleteOrganization(parentOrganziation);
		}
	}

	protected void assertExpectedOrganizations(
			List<Organization> expectedOrganizations, long parentOrganizationId,
			String nameSearch)
		throws Exception {

		List<Organization> actualOrganizations =
			OrganizationServiceUtil.getOrganizations(
				TestPropsValues.getCompanyId(), parentOrganizationId,
				nameSearch, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(
			actualOrganizations.toString(), expectedOrganizations.size(),
			actualOrganizations.size());
		Assert.assertTrue(
			actualOrganizations.toString(),
			actualOrganizations.containsAll(expectedOrganizations));

		Assert.assertEquals(
			expectedOrganizations.size(),
			OrganizationServiceUtil.getOrganizationsCount(
				TestPropsValues.getCompanyId(), parentOrganizationId,
				nameSearch));
	}

	@DeleteAfterTestRun
	private final List<Organization> _organizations = new ArrayList<>();

}
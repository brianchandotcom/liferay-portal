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

package com.liferay.organizations.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.service.OrganizationService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.OrganizationTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerTestRule;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Drew Brokke
 */
@RunWith(Arquillian.class)
public class OrganizationServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerTestRule.INSTANCE);

	@Test
	public void testGetGtOrganizations() throws Exception {
		long parentOrganizationId = 0;

		int numberOfOrganizations = 10;
		int size = 5;

		for (int i = 0; i < numberOfOrganizations; i++) {
			_organizations.add(OrganizationTestUtil.addOrganization());
		}

		List<Organization> retrievedOrganizations =
			_organizationService.getGtOrganizations(
				0, TestPropsValues.getCompanyId(), parentOrganizationId, size);

		Assert.assertFalse(
			"It should return organizations", retrievedOrganizations.isEmpty());

		Assert.assertEquals(
			"It should return the correct number of organizations", size,
			retrievedOrganizations.size());

		Organization lastOrganization = retrievedOrganizations.get(
			retrievedOrganizations.size() - 1);

		retrievedOrganizations = _organizationService.getGtOrganizations(
			lastOrganization.getOrganizationId(),
			TestPropsValues.getCompanyId(), parentOrganizationId, size);

		Assert.assertFalse(
			"It should return organizations", retrievedOrganizations.isEmpty());

		Assert.assertEquals(
			"It should return the correct number of organizations", size,
			retrievedOrganizations.size());

		long previousOrganizationId = 0;

		for (Organization organization : retrievedOrganizations) {
			long organizationId = organization.getOrganizationId();

			Assert.assertTrue(
				"The returned organizationId " + organizationId +
					" should be greater than the given gtOrganizationId: " +
						lastOrganization.getOrganizationId(),
				organizationId > lastOrganization.getOrganizationId());

			Assert.assertTrue(
				"The organizationId " + organizationId +
					" should be greater than the previous organizationId " +
						previousOrganizationId,
				organizationId > previousOrganizationId);

			previousOrganizationId = organizationId;
		}
	}

	@Inject
	private static OrganizationService _organizationService;

	@DeleteAfterTestRun
	private final List<Organization> _organizations = new ArrayList<>();

}
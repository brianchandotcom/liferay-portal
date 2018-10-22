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

package com.liferay.segments.odata.retriever.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.OrganizationTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerTestRule;
import com.liferay.segments.odata.retriever.ODataRetriever;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author David Arques
 */
@RunWith(Arquillian.class)
public class OrganizationODataRetrieverTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerTestRule.INSTANCE);

	@Before
	public void setUp() {
		_organizations = new ArrayList<>();
	}

	@Test
	public void testGetResultsFilterByName() throws Exception {
		Organization organization = OrganizationTestUtil.addOrganization();

		_organizations.add(organization);

		_organizations.add(OrganizationTestUtil.addOrganization());

		List<Organization> organizations =
			_organizationODataRetriever.getResults(
				TestPropsValues.getCompanyId(),
				"(name eq '" + organization.getName() + "')",
				LocaleUtil.getDefault(), 0, 2);

		Assert.assertEquals(organizations.toString(), 1, organizations.size());
		Assert.assertEquals(organization, organizations.get(0));
	}

	@Inject(
		filter = "model.class.name=com.liferay.portal.kernel.model.Organization"
	)
	private ODataRetriever<Organization> _organizationODataRetriever;

	@DeleteAfterTestRun
	private List<Organization> _organizations;

}
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

import com.fasterxml.jackson.databind.util.ISO8601Utils;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.OrganizationTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.segments.odata.retriever.ODataRetriever;

import java.time.Instant;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
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
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testGetOrganizationsFilterByCompanyId() throws Exception {
		Organization parentOrganization =
			OrganizationTestUtil.addOrganization();

		Organization organization = OrganizationTestUtil.addOrganization(
			parentOrganization.getOrganizationId(),
			RandomTestUtil.randomString(), false);

		_organizations.add(organization);

		_organizations.add(parentOrganization);

		String filterString = String.format(
			"(companyId eq '%s') and (parentOrganizationId eq '%s')",
			organization.getCompanyId(),
			parentOrganization.getOrganizationId());

		int count = _oDataRetriever.getResultsCount(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault());

		Assert.assertEquals(1, count);

		List<Organization> organizations = _oDataRetriever.getResults(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault(), 0, 2);

		Assert.assertEquals(organization, organizations.get(0));
	}

	@Test
	public void testGetOrganizationsFilterByDateModifiedEquals()
		throws Exception {

		Organization parentOrganization =
			OrganizationTestUtil.addOrganization();

		Organization organization1 = OrganizationTestUtil.addOrganization(
			parentOrganization.getOrganizationId(),
			RandomTestUtil.randomString(), false);
		Organization organization2 = OrganizationTestUtil.addOrganization(
			parentOrganization.getOrganizationId(),
			RandomTestUtil.randomString(), false);

		_organizations.add(organization1);

		_organizations.add(organization2);

		_organizations.add(parentOrganization);

		Date modifiedDate = organization1.getModifiedDate();

		Instant instant = modifiedDate.toInstant();

		organization2.setModifiedDate(Date.from(instant.plusSeconds(1)));

		_organizationLocalService.updateOrganization(organization2);

		String filterString = String.format(
			"(dateModified eq %s) and (parentOrganizationId eq '%s')",
			ISO8601Utils.format(organization1.getModifiedDate()),
			parentOrganization.getOrganizationId());

		int count = _oDataRetriever.getResultsCount(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault());

		Assert.assertEquals(1, count);

		List<Organization> organizations = _oDataRetriever.getResults(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault(), 0, 2);

		Assert.assertEquals(organization1, organizations.get(0));
	}

	@Test
	public void testGetOrganizationsFilterByDateModifiedGreater()
		throws Exception {

		Organization parentOrganization =
			OrganizationTestUtil.addOrganization();

		Organization organization1 = OrganizationTestUtil.addOrganization(
			parentOrganization.getOrganizationId(),
			RandomTestUtil.randomString(), false);
		Organization organization2 = OrganizationTestUtil.addOrganization(
			parentOrganization.getOrganizationId(),
			RandomTestUtil.randomString(), false);

		_organizations.add(organization1);

		_organizations.add(organization2);

		_organizations.add(parentOrganization);

		Date modifiedDate = organization1.getModifiedDate();

		Instant instant = modifiedDate.toInstant();

		organization2.setModifiedDate(Date.from(instant.plusSeconds(1)));

		_organizationLocalService.updateOrganization(organization2);

		String filterString = String.format(
			"(dateModified gt %s) and (parentOrganizationId eq '%s')",
			ISO8601Utils.format(organization1.getModifiedDate()),
			parentOrganization.getOrganizationId());

		int count = _oDataRetriever.getResultsCount(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault());

		Assert.assertEquals(1, count);

		List<Organization> organizations = _oDataRetriever.getResults(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault(), 0, 2);

		Assert.assertEquals(organization2, organizations.get(0));
	}

	@Test
	public void testGetOrganizationsFilterByDateModifiedGreaterOrEquals()
		throws Exception {

		Organization parentOrganization =
			OrganizationTestUtil.addOrganization();

		Organization organization1 = OrganizationTestUtil.addOrganization(
			parentOrganization.getOrganizationId(),
			RandomTestUtil.randomString(), false);
		Organization organization2 = OrganizationTestUtil.addOrganization(
			parentOrganization.getOrganizationId(),
			RandomTestUtil.randomString(), false);

		_organizations.add(organization1);

		_organizations.add(organization2);

		_organizations.add(parentOrganization);

		Date modifiedDate = organization1.getModifiedDate();

		Instant instant = modifiedDate.toInstant();

		organization2.setModifiedDate(Date.from(instant.plusSeconds(1)));

		_organizationLocalService.updateOrganization(organization2);

		String filterString = String.format(
			"(dateModified ge %s) and (parentOrganizationId eq '%s')",
			ISO8601Utils.format(organization2.getModifiedDate()),
			parentOrganization.getOrganizationId());

		int count = _oDataRetriever.getResultsCount(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault());

		Assert.assertEquals(1, count);

		List<Organization> organizations = _oDataRetriever.getResults(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault(), 0, 2);

		Assert.assertEquals(organization2, organizations.get(0));
	}

	@Test
	public void testGetOrganizationsFilterByDateModifiedLower()
		throws Exception {

		Organization parentOrganization =
			OrganizationTestUtil.addOrganization();

		Organization organization1 = OrganizationTestUtil.addOrganization(
			parentOrganization.getOrganizationId(),
			RandomTestUtil.randomString(), false);
		Organization organization2 = OrganizationTestUtil.addOrganization(
			parentOrganization.getOrganizationId(),
			RandomTestUtil.randomString(), false);

		_organizations.add(organization1);

		_organizations.add(organization2);

		_organizations.add(parentOrganization);

		Date modifiedDate = organization1.getModifiedDate();

		Instant instant = modifiedDate.toInstant();

		organization2.setModifiedDate(Date.from(instant.plusSeconds(1)));

		_organizationLocalService.updateOrganization(organization2);

		String filterString = String.format(
			"(dateModified lt %s) and (parentOrganizationId eq '%s')",
			ISO8601Utils.format(organization2.getModifiedDate()),
			parentOrganization.getOrganizationId());

		int count = _oDataRetriever.getResultsCount(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault());

		Assert.assertEquals(1, count);

		List<Organization> organizations = _oDataRetriever.getResults(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault(), 0, 2);

		Assert.assertEquals(organization1, organizations.get(0));
	}

	@Test
	public void testGetOrganizationsFilterByDateModifiedLowerOrEquals()
		throws Exception {

		Organization parentOrganization =
			OrganizationTestUtil.addOrganization();

		Organization organization1 = OrganizationTestUtil.addOrganization(
			parentOrganization.getOrganizationId(),
			RandomTestUtil.randomString(), false);
		Organization organization2 = OrganizationTestUtil.addOrganization(
			parentOrganization.getOrganizationId(),
			RandomTestUtil.randomString(), false);

		_organizations.add(organization1);

		_organizations.add(organization2);

		_organizations.add(parentOrganization);

		Date modifiedDate = organization1.getModifiedDate();

		Instant instant = modifiedDate.toInstant();

		organization2.setModifiedDate(Date.from(instant.plusSeconds(1)));

		_organizationLocalService.updateOrganization(organization2);

		String filterString = String.format(
			"(dateModified le %s) and (parentOrganizationId eq '%s')",
			ISO8601Utils.format(modifiedDate),
			parentOrganization.getOrganizationId());

		int count = _oDataRetriever.getResultsCount(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault());

		Assert.assertEquals(1, count);

		List<Organization> organizations = _oDataRetriever.getResults(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault(), 0, 2);

		Assert.assertEquals(organization1, organizations.get(0));
	}

	@Test
	public void testGetOrganizationsFilterByName() throws Exception {
		Organization organization1 = OrganizationTestUtil.addOrganization();
		Organization organization2 = OrganizationTestUtil.addOrganization();

		_organizations.add(organization1);

		_organizations.add(organization2);

		String filterString = "(name eq '" + organization1.getName() + "')";

		int count = _oDataRetriever.getResultsCount(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault());

		Assert.assertEquals(1, count);

		List<Organization> organizations = _oDataRetriever.getResults(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault(), 0, 2);

		Assert.assertEquals(organization1, organizations.get(0));
	}

	@Test
	public void testGetOrganizationsFilterByNameTreePath() throws Exception {
		Organization parentOrganization = OrganizationTestUtil.addOrganization(
			0, "A", false);

		Organization organization = OrganizationTestUtil.addOrganization(
			parentOrganization.getOrganizationId(), "B", false);

		_organizations.add(organization);

		_organizations.add(parentOrganization);

		String filterString = "(nameTreePath eq 'A > B')";

		int count = _oDataRetriever.getResultsCount(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault());

		Assert.assertEquals(1, count);

		List<Organization> organizations = _oDataRetriever.getResults(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault(), 0, 2);

		Assert.assertEquals(organization, organizations.get(0));
	}

	@Test
	public void testGetOrganizationsFilterByNameTreePathContains()
		throws Exception {

		Organization parentOrganization = OrganizationTestUtil.addOrganization(
			0, RandomTestUtil.randomString(), false);

		Organization organization = OrganizationTestUtil.addOrganization(
			parentOrganization.getOrganizationId(),
			RandomTestUtil.randomString(), false);

		_organizations.add(organization);

		_organizations.add(parentOrganization);

		String filterString =
			"contains(nameTreePath, '" + organization.getName() + "')";

		int count = _oDataRetriever.getResultsCount(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault());

		Assert.assertEquals(1, count);

		List<Organization> organizations = _oDataRetriever.getResults(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault(), 0, 2);

		Assert.assertEquals(organization, organizations.get(0));
	}

	@Test
	public void testGetOrganizationsFilterByOrganizationId() throws Exception {
		Organization organization1 = OrganizationTestUtil.addOrganization();
		Organization organization2 = OrganizationTestUtil.addOrganization();

		_organizations.add(organization1);

		_organizations.add(organization2);

		String filterString =
			"(organizationId eq '" + organization1.getOrganizationId() + "')";

		int count = _oDataRetriever.getResultsCount(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault());

		Assert.assertEquals(1, count);

		List<Organization> organizations = _oDataRetriever.getResults(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault(), 0, 2);

		Assert.assertEquals(organization1, organizations.get(0));
	}

	@Test
	public void testGetOrganizationsFilterByParentOrganizationId()
		throws Exception {

		Organization parentOrganization =
			OrganizationTestUtil.addOrganization();

		Organization organization = OrganizationTestUtil.addOrganization(
			parentOrganization.getOrganizationId(),
			RandomTestUtil.randomString(), false);

		_organizations.add(organization);

		_organizations.add(parentOrganization);

		String filterString =
			"(parentOrganizationId eq '" +
				parentOrganization.getOrganizationId() + "')";

		int count = _oDataRetriever.getResultsCount(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault());

		Assert.assertEquals(1, count);

		List<Organization> organizations = _oDataRetriever.getResults(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault(), 0, 2);

		Assert.assertEquals(organization, organizations.get(0));
	}

	@Test
	public void testGetOrganizationsFilterByParentOrganizationIdAndNotOrganizationId()
		throws Exception {

		Organization parentOrganization =
			OrganizationTestUtil.addOrganization();

		Organization organization1 = OrganizationTestUtil.addOrganization(
			parentOrganization.getOrganizationId(),
			RandomTestUtil.randomString(), false);

		Organization organization2 = OrganizationTestUtil.addOrganization(
			parentOrganization.getOrganizationId(),
			RandomTestUtil.randomString(), false);

		_organizations.add(organization1);

		_organizations.add(organization2);

		_organizations.add(parentOrganization);

		String filterString = String.format(
			"(parentOrganizationId eq '%s') and (not (organizationId eq '%s'))",
			parentOrganization.getOrganizationId(),
			organization2.getOrganizationId());

		int count = _oDataRetriever.getResultsCount(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault());

		Assert.assertEquals(1, count);

		List<Organization> organizations = _oDataRetriever.getResults(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault(), 0, 1);

		Assert.assertEquals(organization1, organizations.get(0));
	}

	@Test
	public void testGetOrganizationsFilterByType() throws Exception {
		Organization parentOrganization =
			OrganizationTestUtil.addOrganization();

		Organization organization = OrganizationTestUtil.addOrganization(
			parentOrganization.getOrganizationId(),
			RandomTestUtil.randomString(), false);

		_organizations.add(organization);

		_organizations.add(parentOrganization);

		String filterString = String.format(
			" (parentOrganizationId eq '%s') and (type eq '%s')",
			parentOrganization.getOrganizationId(), organization.getType());

		int count = _oDataRetriever.getResultsCount(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault());

		Assert.assertEquals(1, count);

		List<Organization> organizations = _oDataRetriever.getResults(
			TestPropsValues.getCompanyId(), filterString,
			LocaleUtil.getDefault(), 0, 2);

		Assert.assertEquals(organization, organizations.get(0));
	}

	@Inject(
		filter = "model.class.name=com.liferay.portal.kernel.model.Organization"
	)
	private ODataRetriever<Organization> _oDataRetriever;

	@Inject
	private OrganizationLocalService _organizationLocalService;

	@DeleteAfterTestRun
	private final List<Organization> _organizations = new ArrayList<>();

}
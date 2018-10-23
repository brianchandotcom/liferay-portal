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
import com.liferay.portal.test.rule.PermissionCheckerTestRule;
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
			PermissionCheckerTestRule.INSTANCE);

	@Test
	public void testGetResultsFilterByDateModifiedEquals() throws Exception {
		Organization organization = OrganizationTestUtil.addOrganization();

		Organization suborganization1 = OrganizationTestUtil.addOrganization(
			organization.getOrganizationId(), RandomTestUtil.randomString(),
			false);
		Organization suborganization2 = OrganizationTestUtil.addOrganization(
			organization.getOrganizationId(), RandomTestUtil.randomString(),
			false);

		_organizations.add(suborganization1);
		_organizations.add(suborganization2);
		_organizations.add(organization);

		Date modifiedDate = suborganization1.getModifiedDate();

		Instant instant = modifiedDate.toInstant();

		suborganization2.setModifiedDate(Date.from(instant.plusSeconds(1)));

		_organizationLocalService.updateOrganization(suborganization2);

		List<Organization> organizations = _oDataRetriever.getResults(
			TestPropsValues.getCompanyId(),
			String.format(
				"(dateModified eq %s) and (parentOrganizationId eq '%s')",
				ISO8601Utils.format(suborganization1.getModifiedDate()),
				organization.getOrganizationId()),
			LocaleUtil.getDefault(), 0, 2);

		Assert.assertEquals(organizations.toString(), 1, organizations.size());
		Assert.assertEquals(suborganization1, organizations.get(0));
	}

	@Test
	public void testGetResultsFilterByDateModifiedGreater() throws Exception {
		Organization organization = OrganizationTestUtil.addOrganization();

		Organization suborganization1 = OrganizationTestUtil.addOrganization(
			organization.getOrganizationId(), RandomTestUtil.randomString(),
			false);
		Organization suborganization2 = OrganizationTestUtil.addOrganization(
			organization.getOrganizationId(), RandomTestUtil.randomString(),
			false);

		_organizations.add(suborganization1);
		_organizations.add(suborganization2);
		_organizations.add(organization);

		Date modifiedDate = suborganization1.getModifiedDate();

		Instant instant = modifiedDate.toInstant();

		suborganization2.setModifiedDate(Date.from(instant.plusSeconds(1)));

		_organizationLocalService.updateOrganization(suborganization2);

		List<Organization> organizations = _oDataRetriever.getResults(
			TestPropsValues.getCompanyId(),
			String.format(
				"(dateModified gt %s) and (parentOrganizationId eq '%s')",
				ISO8601Utils.format(suborganization1.getModifiedDate()),
				organization.getOrganizationId()),
			LocaleUtil.getDefault(), 0, 2);

		Assert.assertEquals(organizations.toString(), 1, organizations.size());
		Assert.assertEquals(suborganization2, organizations.get(0));
	}

	@Test
	public void testGetResultsFilterByName() throws Exception {
		Organization organization1 = OrganizationTestUtil.addOrganization();
		Organization organization2 = OrganizationTestUtil.addOrganization();

		_organizations.add(organization1);
		_organizations.add(organization2);

		List<Organization> organizations = _oDataRetriever.getResults(
			TestPropsValues.getCompanyId(),
			"(name eq '" + organization1.getName() + "')",
			LocaleUtil.getDefault(), 0, 2);

		Assert.assertEquals(organizations.toString(), 1, organizations.size());
		Assert.assertEquals(organization1, organizations.get(0));
	}

	@Test
	public void testGetResultsFilterByOrganizationId() throws Exception {
		Organization organization1 = OrganizationTestUtil.addOrganization();
		Organization organization2 = OrganizationTestUtil.addOrganization();

		_organizations.add(organization1);
		_organizations.add(organization2);

		List<Organization> organizations = _oDataRetriever.getResults(
			TestPropsValues.getCompanyId(),
			"(organizationId eq '" + organization1.getOrganizationId() + "')",
			LocaleUtil.getDefault(), 0, 2);

		Assert.assertEquals(organizations.toString(), 1, organizations.size());
		Assert.assertEquals(organization1, organizations.get(0));
	}

	@Test
	public void testGetResultsFilterByParentOrganizationId() throws Exception {
		Organization organization = OrganizationTestUtil.addOrganization();

		Organization suborganization = OrganizationTestUtil.addOrganization(
			organization.getOrganizationId(), RandomTestUtil.randomString(),
			false);

		_organizations.add(suborganization);

		_organizations.add(organization);

		List<Organization> organizations = _oDataRetriever.getResults(
			TestPropsValues.getCompanyId(),
			"(parentOrganizationId eq '" + organization.getOrganizationId() +
				"')",
			LocaleUtil.getDefault(), 0, 2);

		Assert.assertEquals(organizations.toString(), 1, organizations.size());
		Assert.assertEquals(suborganization, organizations.get(0));
	}

	@Test
	public void testGetResultsFilterByTreePath() throws Exception {
		Organization organization1 = OrganizationTestUtil.addOrganization();
		Organization organization2 = OrganizationTestUtil.addOrganization();

		_organizations.add(organization1);
		_organizations.add(organization2);

		List<Organization> organizations = _oDataRetriever.getResults(
			TestPropsValues.getCompanyId(),
			"(treePath eq '" + organization1.getTreePath() + "')",
			LocaleUtil.getDefault(), 0, 2);

		Assert.assertEquals(organizations.toString(), 1, organizations.size());
		Assert.assertEquals(organization1, organizations.get(0));
	}

	@Test
	public void testGetUsersFilterByDateModifiedGreaterOrEquals()
		throws Exception {

		Organization organization = OrganizationTestUtil.addOrganization();

		Organization suborganization1 = OrganizationTestUtil.addOrganization(
			organization.getOrganizationId(), RandomTestUtil.randomString(),
			false);
		Organization suborganization2 = OrganizationTestUtil.addOrganization(
			organization.getOrganizationId(), RandomTestUtil.randomString(),
			false);

		_organizations.add(suborganization1);
		_organizations.add(suborganization2);
		_organizations.add(organization);

		Date modifiedDate = suborganization1.getModifiedDate();

		Instant instant = modifiedDate.toInstant();

		suborganization2.setModifiedDate(Date.from(instant.plusSeconds(1)));

		_organizationLocalService.updateOrganization(suborganization2);

		List<Organization> organizations = _oDataRetriever.getResults(
			TestPropsValues.getCompanyId(),
			String.format(
				"(dateModified ge %s) and (parentOrganizationId eq '%s')",
				ISO8601Utils.format(suborganization2.getModifiedDate()),
				organization.getOrganizationId()),
			LocaleUtil.getDefault(), 0, 2);

		Assert.assertEquals(organizations.toString(), 1, organizations.size());
		Assert.assertEquals(suborganization2, organizations.get(0));
	}

	@Test
	public void testGetUsersFilterByDateModifiedLower() throws Exception {
		Organization organization = OrganizationTestUtil.addOrganization();

		Organization suborganization1 = OrganizationTestUtil.addOrganization(
			organization.getOrganizationId(), RandomTestUtil.randomString(),
			false);
		Organization suborganization2 = OrganizationTestUtil.addOrganization(
			organization.getOrganizationId(), RandomTestUtil.randomString(),
			false);

		_organizations.add(suborganization1);
		_organizations.add(suborganization2);
		_organizations.add(organization);

		Date modifiedDate = suborganization1.getModifiedDate();

		Instant instant = modifiedDate.toInstant();

		suborganization2.setModifiedDate(Date.from(instant.plusSeconds(1)));

		_organizationLocalService.updateOrganization(suborganization2);

		List<Organization> organizations = _oDataRetriever.getResults(
			TestPropsValues.getCompanyId(),
			String.format(
				"(dateModified lt %s) and (parentOrganizationId eq '%s')",
				ISO8601Utils.format(suborganization2.getModifiedDate()),
				organization.getOrganizationId()),
			LocaleUtil.getDefault(), 0, 2);

		Assert.assertEquals(organizations.toString(), 1, organizations.size());
		Assert.assertEquals(suborganization1, organizations.get(0));
	}

	@Test
	public void testGetUsersFilterByDateModifiedLowerOrEquals()
		throws Exception {

		Organization organization = OrganizationTestUtil.addOrganization();

		Organization suborganization1 = OrganizationTestUtil.addOrganization(
			organization.getOrganizationId(), RandomTestUtil.randomString(),
			false);
		Organization suborganization2 = OrganizationTestUtil.addOrganization(
			organization.getOrganizationId(), RandomTestUtil.randomString(),
			false);

		_organizations.add(suborganization1);
		_organizations.add(suborganization2);
		_organizations.add(organization);

		Date modifiedDate = suborganization1.getModifiedDate();

		Instant instant = modifiedDate.toInstant();

		suborganization2.setModifiedDate(Date.from(instant.plusSeconds(1)));

		_organizationLocalService.updateOrganization(suborganization2);

		List<Organization> organizations = _oDataRetriever.getResults(
			TestPropsValues.getCompanyId(),
			String.format(
				"(dateModified le %s) and (parentOrganizationId eq '%s')",
				ISO8601Utils.format(modifiedDate),
				organization.getOrganizationId()),
			LocaleUtil.getDefault(), 0, 2);

		Assert.assertEquals(organizations.toString(), 1, organizations.size());
		Assert.assertEquals(suborganization1, organizations.get(0));
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
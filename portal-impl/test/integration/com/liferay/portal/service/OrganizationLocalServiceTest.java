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

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.OrganizationParentException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.ListTypeConstants;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.OrganizationConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.service.OrganizationLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.OrganizationTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Jorge Ferrer
 * @author Sergio González
 */
@Sync
public class OrganizationLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Test
	public void testAddOrganization() throws Exception {
		User user = TestPropsValues.getUser();

		Organization organization =
			OrganizationLocalServiceUtil.addOrganization(
				user.getUserId(),
				OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID,
				"Organization", false);

		_organizations.add(organization);

		List<Organization> organizations = user.getOrganizations(true);

		Assert.assertTrue(organizations.contains(organization));

		Assert.assertFalse(
			OrganizationLocalServiceUtil.hasUserOrganization(
				user.getUserId(), organization.getOrganizationId()));
	}

	@Test
	public void testAddOrganizationWithoutSiteToParentOrganizationWithoutSite()
		throws Exception {

		Organization organizationA = OrganizationTestUtil.addOrganization(
			OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID,
			"Organization A", false);

		Organization organizationB = OrganizationTestUtil.addOrganization(
			organizationA.getOrganizationId(), "Organization B", false);

		_organizations.add(organizationB);

		_organizations.add(organizationA);

		Assert.assertEquals(
			organizationA.getOrganizationId(),
			organizationB.getParentOrganizationId());

		Group groupB = organizationB.getGroup();

		Assert.assertEquals(
			GroupConstants.DEFAULT_PARENT_GROUP_ID, groupB.getParentGroupId());
	}

	@Test
	public void testAddOrganizationWithoutSiteToParentOrganizationWithSite()
		throws Exception {

		Organization organizationA = OrganizationTestUtil.addOrganization(
			OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID,
			"Organization A", true);

		Organization organizationB = OrganizationTestUtil.addOrganization(
			organizationA.getOrganizationId(), "Organization B", false);

		_organizations.add(organizationB);

		_organizations.add(organizationA);

		Assert.assertEquals(
			organizationA.getOrganizationId(),
			organizationB.getParentOrganizationId());

		Group groupB = organizationB.getGroup();

		Assert.assertEquals(
			GroupConstants.DEFAULT_PARENT_GROUP_ID, groupB.getParentGroupId());
	}

	@Test
	public void testAddOrganizationWithSiteToParentOrganizationWithoutSite()
		throws Exception {

		Organization organizationA = OrganizationTestUtil.addOrganization(
			OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID,
			"Organization A", false);

		Organization organizationB = OrganizationTestUtil.addOrganization(
			organizationA.getOrganizationId(), "Organization B", true);

		_organizations.add(organizationB);

		_organizations.add(organizationA);

		Assert.assertEquals(
			organizationA.getOrganizationId(),
			organizationB.getParentOrganizationId());

		Group groupB = organizationB.getGroup();

		Assert.assertEquals(
			GroupConstants.DEFAULT_PARENT_GROUP_ID, groupB.getParentGroupId());
	}

	@Test
	public void testAddOrganizationWithSiteToParentOrganizationWithSite()
		throws Exception {

		Organization organizationA = OrganizationTestUtil.addOrganization(
			OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID,
			"Organization A", true);

		Organization organizationB = OrganizationTestUtil.addOrganization(
			organizationA.getOrganizationId(), "Organization B", true);

		_organizations.add(organizationB);

		_organizations.add(organizationA);

		Assert.assertEquals(
			organizationA.getOrganizationId(),
			organizationB.getParentOrganizationId());

		Group groupB = organizationB.getGroup();

		Assert.assertEquals(
			organizationA.getGroupId(), groupB.getParentGroupId());
	}

	@Test
	public void testAddSiteToOrganizationWithChildOrganizationWithoutSite()
		throws Exception {

		Organization organizationA = OrganizationTestUtil.addOrganization(
			OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID,
			"Organization A", false);

		Organization organizationB = OrganizationTestUtil.addOrganization(
			organizationA.getOrganizationId(), "Organization B", false);

		_organizations.add(organizationB);

		_organizations.add(organizationA);

		organizationA = OrganizationTestUtil.addSite(organizationA);

		Group groupB = organizationB.getGroup();

		Assert.assertEquals(
			GroupConstants.DEFAULT_PARENT_GROUP_ID, groupB.getParentGroupId());
	}

	@Test
	public void testAddSiteToOrganizationWithChildOrganizationWithSite()
		throws Exception {

		Organization organizationA = OrganizationTestUtil.addOrganization(
			OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID,
			"Organization A", false);

		Organization organizationB = OrganizationTestUtil.addOrganization(
			organizationA.getOrganizationId(), "Organization B", true);

		_organizations.add(organizationB);

		_organizations.add(organizationA);

		organizationA = OrganizationTestUtil.addSite(organizationA);

		Group groupA = organizationA.getGroup();

		Group groupB = organizationB.getGroup();

		Assert.assertEquals(groupA.getGroupId(), groupB.getParentGroupId());
	}

	@Test
	public void testAddSiteToOrganizationWithParentOrganizationWithoutSite()
		throws Exception {

		Organization organizationA = OrganizationTestUtil.addOrganization(
			OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID,
			"Organization A", false);

		Organization organizationB = OrganizationTestUtil.addOrganization(
			organizationA.getOrganizationId(), "Organization B", false);

		_organizations.add(organizationB);

		_organizations.add(organizationA);

		organizationB = OrganizationTestUtil.addSite(organizationB);

		Group groupB = organizationB.getGroup();

		Assert.assertEquals(
			GroupConstants.DEFAULT_PARENT_GROUP_ID, groupB.getParentGroupId());
	}

	@Test
	public void testAddSiteToOrganizationWithParentOrganizationWithSite()
		throws Exception {

		Organization organizationA = OrganizationTestUtil.addOrganization(
			OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID,
			"Organization A", true);

		Organization organizationB = OrganizationTestUtil.addOrganization(
			organizationA.getOrganizationId(), "Organization B", false);

		_organizations.add(organizationB);

		_organizations.add(organizationA);

		organizationB = OrganizationTestUtil.addSite(organizationB);

		Group groupA = organizationA.getGroup();
		Group groupB = organizationB.getGroup();

		Assert.assertEquals(groupA.getGroupId(), groupB.getParentGroupId());
	}

	@Test
	public void testGetNoAssetOrganizations() throws Exception {
		for (Organization organization :
				OrganizationLocalServiceUtil.getNoAssetOrganizations()) {

			OrganizationLocalServiceUtil.deleteOrganization(organization);
		}

		Organization organizationA =
			OrganizationLocalServiceUtil.addOrganization(
				TestPropsValues.getUserId(),
				OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID,
				RandomTestUtil.randomString(),
				OrganizationConstants.TYPE_ORGANIZATION, 0, 0,
				ListTypeConstants.ORGANIZATION_STATUS_DEFAULT, StringPool.BLANK,
				false, new ServiceContext());

		Organization organizationB =
			OrganizationLocalServiceUtil.addOrganization(
				TestPropsValues.getUserId(),
				OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID, "Test2",
				OrganizationConstants.TYPE_ORGANIZATION, 0, 0,
				ListTypeConstants.ORGANIZATION_STATUS_DEFAULT, StringPool.BLANK,
				false, new ServiceContext());

		_organizations.add(organizationB);

		_organizations.add(organizationA);

		AssetEntry assetEntry = AssetEntryLocalServiceUtil.fetchEntry(
			Organization.class.getName(), organizationB.getOrganizationId());

		Assert.assertNotNull(assetEntry);

		AssetEntryLocalServiceUtil.deleteAssetEntry(assetEntry);

		List<Organization> organizations =
			OrganizationLocalServiceUtil.getNoAssetOrganizations();

		Assert.assertEquals(organizations.toString(), 1, organizations.size());
		Assert.assertEquals(organizationB, organizations.get(0));
	}

	@Test
	public void testGetOrganizationsAndUsers() throws Exception {
		Organization organization = OrganizationTestUtil.addOrganization();

		Organization suborganization = OrganizationTestUtil.addOrganization(
			organization.getOrganizationId(), RandomTestUtil.randomString(),
			false);

		_organizations.add(suborganization);

		_organizations.add(organization);

		UserLocalServiceUtil.addOrganizationUser(
			organization.getOrganizationId(), TestPropsValues.getUserId());

		Assert.assertEquals(2, getOrganizationsAndUsersCount(organization));

		List<Object> results = getOrganizationsAndUsers(organization);

		Assert.assertEquals(results.toString(), 2, results.size());
		Assert.assertTrue(results.contains(suborganization));
		Assert.assertTrue(results.contains(TestPropsValues.getUser()));

		UserLocalServiceUtil.deleteOrganizationUser(
			organization.getOrganizationId(), TestPropsValues.getUser());
	}

	@Test
	public void testGetOrganizationsAndUsersWithNoSuborganizations()
		throws Exception {

		Organization organization = OrganizationTestUtil.addOrganization();

		_organizations.add(organization);

		UserLocalServiceUtil.addOrganizationUser(
			organization.getOrganizationId(), TestPropsValues.getUserId());

		Assert.assertEquals(1, getOrganizationsAndUsersCount(organization));

		List<Object> results = getOrganizationsAndUsers(organization);

		Assert.assertEquals(results.toString(), 1, results.size());
		Assert.assertTrue(results.contains(TestPropsValues.getUser()));

		UserLocalServiceUtil.deleteOrganizationUser(
			organization.getOrganizationId(), TestPropsValues.getUser());
	}

	@Test
	public void testGetOrganizationsAndUsersWithNoUsers() throws Exception {
		Organization organization = OrganizationTestUtil.addOrganization();

		Organization suborganization = OrganizationTestUtil.addOrganization(
			organization.getOrganizationId(), RandomTestUtil.randomString(),
			false);

		_organizations.add(suborganization);

		_organizations.add(organization);

		Assert.assertEquals(1, getOrganizationsAndUsersCount(organization));

		List<Object> results = getOrganizationsAndUsers(organization);

		Assert.assertEquals(results.toString(), 1, results.size());
		Assert.assertTrue(results.contains(suborganization));
	}

	@Test
	public void testGetOrganizationsAndUsersWithRootOrganization()
		throws Exception {

		Organization organization = OrganizationTestUtil.addOrganization();

		_organizations.add(organization);

		Assert.assertEquals(0, getOrganizationsAndUsersCount(organization));

		List<Object> results = getOrganizationsAndUsers(organization);

		Assert.assertTrue(results.isEmpty());
	}

	@Test
	public void testHasUserOrganization1() throws Exception {
		Organization organizationA = OrganizationTestUtil.addOrganization(
			OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID,
			"Organization A", false);

		Organization organizationB = OrganizationTestUtil.addOrganization(
			OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID,
			"Organization B", false);

		_organizations.add(organizationA);
		_organizations.add(organizationB);

		UserLocalServiceUtil.addOrganizationUser(
			organizationA.getOrganizationId(), TestPropsValues.getUserId());

		Assert.assertTrue(
			OrganizationLocalServiceUtil.hasUserOrganization(
				TestPropsValues.getUserId(), organizationA.getOrganizationId(),
				false, false));
		Assert.assertFalse(
			OrganizationLocalServiceUtil.hasUserOrganization(
				TestPropsValues.getUserId(), organizationB.getOrganizationId(),
				false, false));

		UserLocalServiceUtil.deleteOrganizationUser(
			organizationA.getOrganizationId(), TestPropsValues.getUser());
	}

	@Test
	public void testHasUserOrganization2() throws Exception {
		Organization organizationA = OrganizationTestUtil.addOrganization(
			OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID,
			"Organization A", false);

		Organization organizationAA = OrganizationTestUtil.addOrganization(
			organizationA.getOrganizationId(), "Organization AA", false);

		_organizations.add(organizationAA);

		_organizations.add(organizationA);

		UserLocalServiceUtil.addOrganizationUser(
			organizationAA.getOrganizationId(), TestPropsValues.getUserId());

		Assert.assertTrue(
			OrganizationLocalServiceUtil.hasUserOrganization(
				TestPropsValues.getUserId(), organizationA.getOrganizationId(),
				true, false));
		Assert.assertTrue(
			OrganizationLocalServiceUtil.hasUserOrganization(
				TestPropsValues.getUserId(), organizationA.getOrganizationId(),
				true, true));

		UserLocalServiceUtil.deleteOrganizationUser(
			organizationAA.getOrganizationId(), TestPropsValues.getUser());
	}

	@Test
	public void testMoveOrganizationWithoutSiteToParentOrganizationWithoutSite()
		throws Exception {

		Organization organizationA = OrganizationTestUtil.addOrganization(
			OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID,
			"Organization A", false);

		Organization organizationAA = OrganizationTestUtil.addOrganization(
			organizationA.getOrganizationId(), "Organization AA", true);

		Organization organizationB = OrganizationTestUtil.addOrganization(
			OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID,
			"Organization B", false);

		organizationAA = OrganizationLocalServiceUtil.updateOrganization(
			organizationAA.getCompanyId(), organizationAA.getOrganizationId(),
			organizationB.getOrganizationId(), organizationAA.getName(),
			organizationAA.getType(), organizationAA.getRegionId(),
			organizationAA.getCountryId(), organizationAA.getStatusId(),
			organizationAA.getComments(), false, null, true, null);

		_organizations.add(organizationAA);

		_organizations.add(organizationB);
		_organizations.add(organizationA);

		Assert.assertEquals(
			organizationB.getOrganizationId(),
			organizationAA.getParentOrganizationId());

		Group groupAA = organizationAA.getGroup();

		Assert.assertEquals(
			GroupConstants.DEFAULT_PARENT_GROUP_ID, groupAA.getParentGroupId());
	}

	@Test
	public void testMoveOrganizationWithoutSiteToParentOrganizationWithSite()
		throws Exception {

		Organization organizationA = OrganizationTestUtil.addOrganization(
			OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID,
			"Organization A", false);

		Organization organizationAA = OrganizationTestUtil.addOrganization(
			organizationA.getOrganizationId(), "Organization AA", true);

		Organization organizationB = OrganizationTestUtil.addOrganization(
			OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID,
			"Organization B", true);

		organizationAA = OrganizationLocalServiceUtil.updateOrganization(
			organizationAA.getCompanyId(), organizationAA.getOrganizationId(),
			organizationB.getOrganizationId(), organizationAA.getName(),
			organizationAA.getType(), organizationAA.getRegionId(),
			organizationAA.getCountryId(), organizationAA.getStatusId(),
			organizationAA.getComments(), false, null, true, null);

		_organizations.add(organizationAA);

		_organizations.add(organizationB);
		_organizations.add(organizationA);

		Assert.assertEquals(
			organizationB.getOrganizationId(),
			organizationAA.getParentOrganizationId());

		Group groupAA = organizationAA.getGroup();

		Assert.assertEquals(
			GroupConstants.DEFAULT_PARENT_GROUP_ID, groupAA.getParentGroupId());
	}

	@Test
	public void testMoveOrganizationWithSiteToParentOrganizationWithoutSite()
		throws Exception {

		Organization organizationA = OrganizationTestUtil.addOrganization(
			OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID,
			"Organization A", true);

		Organization organizationAA = OrganizationTestUtil.addOrganization(
			organizationA.getOrganizationId(), "Organization AA", true);

		Organization organizationB = OrganizationTestUtil.addOrganization(
			OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID,
			"Organization B", false);

		organizationAA = OrganizationLocalServiceUtil.updateOrganization(
			organizationAA.getCompanyId(), organizationAA.getOrganizationId(),
			organizationB.getOrganizationId(), organizationAA.getName(),
			organizationAA.getType(), organizationAA.getRegionId(),
			organizationAA.getCountryId(), organizationAA.getStatusId(),
			organizationAA.getComments(), false, null, true, null);

		_organizations.add(organizationAA);

		_organizations.add(organizationB);
		_organizations.add(organizationA);

		Assert.assertEquals(
			organizationB.getOrganizationId(),
			organizationAA.getParentOrganizationId());

		Group groupAA = organizationAA.getGroup();

		Assert.assertEquals(
			GroupConstants.DEFAULT_PARENT_GROUP_ID, groupAA.getParentGroupId());
	}

	@Test
	public void testMoveOrganizationWithSiteToParentOrganizationWithSite()
		throws Exception {

		Organization organizationA = OrganizationTestUtil.addOrganization(
			OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID,
			"Organization A", true);

		Organization organizationAA = OrganizationTestUtil.addOrganization(
			organizationA.getOrganizationId(), "Organization AA", true);

		Organization organizationB = OrganizationTestUtil.addOrganization(
			OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID,
			"Organization B", true);

		organizationAA = OrganizationLocalServiceUtil.updateOrganization(
			organizationAA.getCompanyId(), organizationAA.getOrganizationId(),
			organizationB.getOrganizationId(), organizationAA.getName(),
			organizationAA.getType(), organizationAA.getRegionId(),
			organizationAA.getCountryId(), organizationAA.getStatusId(),
			organizationAA.getComments(), false, null, true, null);

		_organizations.add(organizationAA);

		_organizations.add(organizationB);
		_organizations.add(organizationA);

		Assert.assertEquals(
			organizationB.getOrganizationId(),
			organizationAA.getParentOrganizationId());

		Group groupAA = organizationAA.getGroup();

		Assert.assertEquals(
			organizationB.getGroupId(), groupAA.getParentGroupId());
	}

	@Test(expected = OrganizationParentException.class)
	public void testNoCircularParentOrganization() throws Exception {
		Organization organizationA = OrganizationTestUtil.addOrganization(
			OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID,
			"Organization A", false);

		Organization organizationAA = OrganizationTestUtil.addOrganization(
			organizationA.getOrganizationId(), "Organization AA", false);

		Organization organizationAAA = OrganizationTestUtil.addOrganization(
			organizationAA.getOrganizationId(), "Organization AAA", false);

		Organization organizationAAAA = OrganizationTestUtil.addOrganization(
			organizationAAA.getOrganizationId(), "Organization AAAA", false);

		_organizations.add(organizationAAAA);

		_organizations.add(organizationAAA);
		_organizations.add(organizationAA);
		_organizations.add(organizationA);

		organizationA.setParentOrganizationId(
			organizationAAAA.getOrganizationId());

		_updateOrganization(organizationA);
	}

	@Test
	public void testParentOrganizationUpdatesAllTreePaths() throws Exception {
		Organization organizationA = OrganizationTestUtil.addOrganization(
			OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID,
			"Organization A", false);

		Organization organizationB = OrganizationTestUtil.addOrganization(
			OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID,
			"Organization B", false);

		Organization organizationBB = OrganizationTestUtil.addOrganization(
			organizationB.getOrganizationId(), "Organization BB", false);

		Organization organizationBBB = OrganizationTestUtil.addOrganization(
			organizationBB.getOrganizationId(), "Organization BBB", false);

		_organizations.add(organizationBBB);

		_organizations.add(organizationBB);
		_organizations.add(organizationB);
		_organizations.add(organizationA);

		String shallowTreePath = _getTreePath(
			new Organization[] {
				organizationB, organizationBB, organizationBBB
			});

		Assert.assertEquals(shallowTreePath, organizationBBB.getTreePath());

		organizationB.setParentOrganizationId(
			organizationA.getOrganizationId());

		_updateOrganization(organizationB);

		organizationBBB = OrganizationLocalServiceUtil.fetchOrganization(
			organizationBBB.getOrganizationId());

		String deepTreePath = _getTreePath(
			new Organization[] {
				organizationA, organizationB, organizationBB, organizationBBB
			});

		Assert.assertEquals(deepTreePath, organizationBBB.getTreePath());

		organizationB.setParentOrganizationId(
			OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID);

		_updateOrganization(organizationB);

		organizationBBB = OrganizationLocalServiceUtil.fetchOrganization(
			organizationBBB.getOrganizationId());

		Assert.assertEquals(shallowTreePath, organizationBBB.getTreePath());
	}

	@Test
	public void testSearchOrganizationsAndUsers() throws Exception {
		Organization organization = OrganizationTestUtil.addOrganization();

		Organization suborganization = OrganizationTestUtil.addOrganization(
			organization.getOrganizationId(), "Organization1", false);

		_organizations.add(suborganization);

		_organizations.add(organization);

		_user = UserTestUtil.addUser("user1", TestPropsValues.getGroupId());

		UserLocalServiceUtil.addOrganizationUsers(
			organization.getOrganizationId(), new long[] {_user.getUserId()});

		Hits hits = searchOrganizationsAndUsers(organization, null);

		Assert.assertEquals(hits.toString(), 2, hits.getLength());

		hits = searchOrganizationsAndUsers(organization, "Organization1");

		Document document = hits.doc(0);

		Assert.assertEquals(
			document.toString(),
			String.valueOf(suborganization.getOrganizationId()),
			document.get(Field.ORGANIZATION_ID));

		hits = searchOrganizationsAndUsers(organization, "user1");

		document = hits.doc(0);

		Assert.assertEquals(
			document.toString(), String.valueOf(_user.getUserId()),
			document.get(Field.USER_ID));

		UserLocalServiceUtil.deleteOrganizationUser(
			organization.getOrganizationId(), _user);
	}

	@Test
	public void testSearchOrganizationsAndUsersWhenNoOrganizations()
		throws Exception {

		Organization organization = OrganizationTestUtil.addOrganization();

		_organizations.add(organization);

		_user = UserTestUtil.addUser("user1", TestPropsValues.getGroupId());

		UserLocalServiceUtil.addOrganizationUsers(
			organization.getOrganizationId(), new long[] {_user.getUserId()});

		Hits hits = searchOrganizationsAndUsers(organization, null);

		Assert.assertEquals(hits.toString(), 1, hits.getLength());

		Document document = hits.doc(0);

		Assert.assertEquals(
			document.toString(), String.valueOf(_user.getUserId()),
			document.get(Field.USER_ID));

		hits = searchOrganizationsAndUsers(organization, "Organization1");

		Assert.assertEquals(hits.toString(), 0, hits.getLength());

		hits = searchOrganizationsAndUsers(organization, "user1");

		document = hits.doc(0);

		Assert.assertEquals(
			document.toString(), String.valueOf(_user.getUserId()),
			document.get(Field.USER_ID));

		UserLocalServiceUtil.deleteOrganizationUser(
			organization.getOrganizationId(), _user);
	}

	@Test
	public void testSearchOrganizationsAndUsersWhenNoUsers() throws Exception {
		Organization organization = OrganizationTestUtil.addOrganization();

		Organization suborganization = OrganizationTestUtil.addOrganization(
			organization.getOrganizationId(), "Organization1", false);

		_organizations.add(suborganization);

		_organizations.add(organization);

		Hits hits = searchOrganizationsAndUsers(organization, null);

		Assert.assertEquals(hits.toString(), 1, hits.getLength());

		Document document = hits.doc(0);

		Assert.assertEquals(
			document.toString(),
			String.valueOf(suborganization.getOrganizationId()),
			document.get(Field.ORGANIZATION_ID));

		hits = searchOrganizationsAndUsers(organization, "Organization1");

		document = hits.doc(0);

		Assert.assertEquals(
			document.toString(),
			String.valueOf(suborganization.getOrganizationId()),
			document.get(Field.ORGANIZATION_ID));

		hits = searchOrganizationsAndUsers(organization, "user1");

		Assert.assertEquals(hits.toString(), 0, hits.getLength());
	}

	@Test
	public void testSearchOrganizationsAndUsersWithRootOrganization()
		throws Exception {

		Organization organization = OrganizationTestUtil.addOrganization();

		_organizations.add(organization);

		Hits hits = searchOrganizationsAndUsers(organization, null);

		Assert.assertEquals(hits.toString(), 0, hits.getLength());

		hits = searchOrganizationsAndUsers(organization, "Organization1");

		Assert.assertEquals(hits.toString(), 0, hits.getLength());

		hits = searchOrganizationsAndUsers(organization, "user1");

		Assert.assertEquals(hits.toString(), 0, hits.getLength());
	}

	protected List<Object> getOrganizationsAndUsers(Organization organization) {
		return OrganizationLocalServiceUtil.getOrganizationsAndUsers(
			organization.getCompanyId(), organization.getOrganizationId(),
			WorkflowConstants.STATUS_ANY, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	protected int getOrganizationsAndUsersCount(Organization organization) {
		return OrganizationLocalServiceUtil.getOrganizationsAndUsersCount(
			organization.getCompanyId(), organization.getOrganizationId(),
			WorkflowConstants.STATUS_ANY);
	}

	protected Hits searchOrganizationsAndUsers(
			Organization parentOrganization, String keywords)
		throws Exception {

		return OrganizationLocalServiceUtil.searchOrganizationsAndUsers(
			parentOrganization.getCompanyId(),
			parentOrganization.getOrganizationId(), keywords,
			WorkflowConstants.STATUS_ANY, null, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	private String _getTreePath(Organization[] organizations) {
		StringBundler sb = new StringBundler();

		sb.append(StringPool.FORWARD_SLASH);

		for (Organization organization : organizations) {
			sb.append(organization.getOrganizationId());
			sb.append(StringPool.FORWARD_SLASH);
		}

		return sb.toString();
	}

	private Organization _updateOrganization(Organization organization)
		throws Exception {

		Group organizationGroup = organization.getGroup();

		return OrganizationLocalServiceUtil.updateOrganization(
			organization.getCompanyId(), organization.getOrganizationId(),
			organization.getParentOrganizationId(), organization.getName(),
			organization.getType(), organization.getRegionId(),
			organization.getCountryId(), organization.getStatusId(),
			organization.getComments(), false, null, organizationGroup.isSite(),
			null);
	}

	@DeleteAfterTestRun
	private final List<Organization> _organizations = new ArrayList<>();

	@DeleteAfterTestRun
	private User _user;

}
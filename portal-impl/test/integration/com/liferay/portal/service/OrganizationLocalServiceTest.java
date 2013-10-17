/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.OrganizationConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portal.util.OrganizationTestUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.TestPropsValues;

import java.lang.reflect.Field;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jorge Ferrer
 * @author Sergio González
 */
@ExecutionTestListeners(
	listeners = {
		EnvironmentExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Transactional
public class OrganizationLocalServiceTest {

	@After
	public void tearDown() throws Exception {
		for (int i = _organizations.size() - 1; i >= 0; i--) {
			OrganizationLocalServiceUtil.deleteOrganization(
				_organizations.get(i));
		}
	}

	@Test
	public void testAddOrganization() throws Exception {
		User user = TestPropsValues.getUser();

		Organization organization =
			OrganizationLocalServiceUtil.addOrganization(
				user.getUserId(),
				OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID,
				"Organization", false);

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

		Assert.assertEquals(
			organizationA.getOrganizationId(),
			organizationB.getParentOrganizationId());

		Group groupB = organizationB.getGroup();

		Assert.assertEquals(
			organizationA.getGroupId(), groupB.getParentGroupId());
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
			organizationAA.getComments(), true, null);

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
			organizationAA.getComments(), true, null);

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
			organizationAA.getComments(), true, null);

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
			organizationAA.getComments(), true, null);

		Assert.assertEquals(
			organizationB.getOrganizationId(),
			organizationAA.getParentOrganizationId());

		Group groupAA = organizationAA.getGroup();

		Assert.assertEquals(
			organizationB.getGroupId(), groupAA.getParentGroupId());
	}

	@Test
	public void testRebuildTree() throws Exception {
		createOrganizationTree();

		Field field = ReflectionUtil.getDeclaredField(
			PropsValues.class, "MODEL_TREE_REBUILD_QUERY_RESULTS_BATCH_SIZE");

		int oldSize = field.getInt(null);

		field.setInt(null, 3);

		try {
			OrganizationLocalServiceUtil.rebuildTree(
				TestPropsValues.getCompanyId());

			for (Organization organization : _organizations) {
				Assert.assertEquals(
					organization.buildTreePath(), organization.getTreePath());
			}
		}
		finally {
			field.setInt(null, oldSize);
		}
	}

	protected void createOrganizationTree() throws Exception {

		/**
		 * Tree 1
		 *
		 * /A--->/A--->/A
		 *  |     |--->/B
		 *  |     |--->/C
		 *  |     |--->/D
		 *  |
		 *  |--->/B--->/A
		 *  |     |--->/B
		 *  |     |--->/C
		 *  |
		 *  |--->/C--->/A
		 *        |--->/B
		 */

		Organization organizationA = OrganizationTestUtil.addOrganization(
			OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID,
			"Organization A", false);

		_organizations.add(organizationA);

		Organization organizationAA = OrganizationTestUtil.addOrganization(
			organizationA.getOrganizationId(), "Organization AA", false);

		_organizations.add(organizationAA);

		Organization organizationAAA = OrganizationTestUtil.addOrganization(
			organizationAA.getOrganizationId(), "Organization AAA", false);

		_organizations.add(organizationAAA);

		Organization organizationAAB = OrganizationTestUtil.addOrganization(
			organizationAA.getOrganizationId(), "Organization AAB", false);

		_organizations.add(organizationAAB);

		Organization organizationAAC = OrganizationTestUtil.addOrganization(
			organizationAA.getOrganizationId(), "Organization AAC", false);

		_organizations.add(organizationAAC);

		Organization organizationAAD = OrganizationTestUtil.addOrganization(
			organizationAA.getOrganizationId(), "Organization AAD", false);

		_organizations.add(organizationAAD);

		Organization organizationAB = OrganizationTestUtil.addOrganization(
			organizationA.getOrganizationId(), "Organization AB", false);

		_organizations.add(organizationAB);

		Organization organizationABA = OrganizationTestUtil.addOrganization(
			organizationAB.getOrganizationId(), "Organization ABA", false);

		_organizations.add(organizationABA);

		Organization organizationABB = OrganizationTestUtil.addOrganization(
			organizationAB.getOrganizationId(), "Organization ABB", false);

		_organizations.add(organizationABB);

		Organization organizationABC = OrganizationTestUtil.addOrganization(
			organizationAB.getOrganizationId(), "Organization ABC", false);

		_organizations.add(organizationABC);

		Organization organizationAC = OrganizationTestUtil.addOrganization(
			organizationA.getOrganizationId(), "Organization AC", false);

		_organizations.add(organizationAC);

		Organization organizationACA = OrganizationTestUtil.addOrganization(
			organizationAC.getOrganizationId(), "Organization ACA", false);

		_organizations.add(organizationACA);

		Organization organizationACB = OrganizationTestUtil.addOrganization(
			organizationAC.getOrganizationId(), "Organization ACB", false);

		_organizations.add(organizationACB);

		/**
		 * Tree 2
		 *
		 * /B--->/A--->/A
		 *  |     |--->/B
		 *  |
		 *  |--->/B--->/A
		 *  |     |--->/B
		 *  |     |--->/C
		 *  |
		 *  |--->/C--->/A
		 *        |--->/B
		 *        |--->/C
		 *        |--->/D
		 */

		Organization organizationB = OrganizationTestUtil.addOrganization(
			OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID,
			"Organization B", false);

		_organizations.add(organizationB);

		Organization organizationBA = OrganizationTestUtil.addOrganization(
			organizationB.getOrganizationId(), "Organization BA", false);

		_organizations.add(organizationBA);

		Organization organizationBAA = OrganizationTestUtil.addOrganization(
			organizationBA.getOrganizationId(), "Organization BAA", false);

		_organizations.add(organizationBAA);

		Organization organizationBAB = OrganizationTestUtil.addOrganization(
			organizationBA.getOrganizationId(), "Organization BAB", false);

		_organizations.add(organizationBAB);

		Organization organizationBB = OrganizationTestUtil.addOrganization(
			organizationB.getOrganizationId(), "Organization BB", false);

		_organizations.add(organizationBB);

		Organization organizationBBA = OrganizationTestUtil.addOrganization(
			organizationBB.getOrganizationId(), "Organization BBA", false);

		_organizations.add(organizationBBA);

		Organization organizationBBB = OrganizationTestUtil.addOrganization(
			organizationBB.getOrganizationId(), "Organization BBB", false);

		_organizations.add(organizationBBB);

		Organization organizationBBC = OrganizationTestUtil.addOrganization(
			organizationBB.getOrganizationId(), "Organization BBC", false);

		_organizations.add(organizationBBC);

		Organization organizationBC = OrganizationTestUtil.addOrganization(
			organizationB.getOrganizationId(), "Organization BC", false);

		_organizations.add(organizationBC);

		Organization organizationBCA = OrganizationTestUtil.addOrganization(
			organizationBC.getOrganizationId(), "Organization BCA", false);

		_organizations.add(organizationBCA);

		Organization organizationBCB = OrganizationTestUtil.addOrganization(
			organizationBC.getOrganizationId(), "Organization BCB", false);

		_organizations.add(organizationBCB);

		Organization organizationBCC = OrganizationTestUtil.addOrganization(
			organizationBC.getOrganizationId(), "Organization BCC", false);

		_organizations.add(organizationBCC);

		Organization organizationBCD = OrganizationTestUtil.addOrganization(
			organizationBC.getOrganizationId(), "Organization BCD", false);

		_organizations.add(organizationBCD);
	}

	private List<Organization> _organizations = new ArrayList<Organization>();

}
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

package com.liferay.organizations.model.impl.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.OrganizationTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Shinn Lok
 */
@RunWith(Arquillian.class)
public class OrganizationImplTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_organization1 = OrganizationTestUtil.addOrganization();

		_organization2 = OrganizationTestUtil.addOrganization(
			_organization1.getOrganizationId(), RandomTestUtil.randomString(),
			false);

		_organization3 = OrganizationTestUtil.addOrganization(
			_organization2.getOrganizationId(), RandomTestUtil.randomString(),
			false);

		_organization4 = OrganizationTestUtil.addOrganization(
			_organization3.getOrganizationId(), RandomTestUtil.randomString(),
			false);

		_organizations.add(_organization4);

		_organizations.add(_organization3);
		_organizations.add(_organization2);
		_organizations.add(_organization1);
	}

	@Test
	public void testGetAncestorOrganizationIds() throws Exception {
		Assert.assertArrayEquals(
			new long[] {
				_organization1.getOrganizationId(),
				_organization2.getOrganizationId(),
				_organization3.getOrganizationId()
			},
			_organization4.getAncestorOrganizationIds());
	}

	@Test
	public void testGetAncestorOrganizationIdsWithNullTreePath()
		throws Exception {

		_organization4.setTreePath(null);

		Assert.assertArrayEquals(
			new long[] {
				_organization1.getOrganizationId(),
				_organization2.getOrganizationId(),
				_organization3.getOrganizationId()
			},
			_organization4.getAncestorOrganizationIds());
	}

	@Test
	public void testGetDescendants() {
		List<Organization> organizations1 = _organization1.getDescendants();

		Assert.assertEquals(
			organizations1.toString(), 3, organizations1.size());

		List<Organization> organizations2 = _organization2.getDescendants();

		Assert.assertEquals(
			organizations2.toString(), 2, organizations2.size());

		List<Organization> organizations3 = _organization3.getDescendants();

		Assert.assertEquals(
			organizations3.toString(), 1, organizations3.size());

		List<Organization> organizations4 = _organization4.getDescendants();

		Assert.assertEquals(
			organizations4.toString(), 0, organizations4.size());

		Assert.assertTrue(
			organizations1.toString(), organizations1.contains(_organization2));
		Assert.assertTrue(
			organizations1.toString(), organizations1.contains(_organization3));
		Assert.assertTrue(
			organizations1.toString(), organizations1.contains(_organization4));
		Assert.assertTrue(!organizations1.contains(_organization1));
	}

	private Organization _organization1;
	private Organization _organization2;
	private Organization _organization3;
	private Organization _organization4;

	@DeleteAfterTestRun
	private final List<Organization> _organizations = new ArrayList<>();

}
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

package com.liferay.site.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
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
 * @author Miguel Pastor
 */
@RunWith(Arquillian.class)
public class GroupLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group1 = GroupTestUtil.addGroup();

		_group2 = GroupTestUtil.addGroup(_group1.getGroupId());

		_group3 = GroupTestUtil.addGroup(_group2.getGroupId());

		_group4 = GroupTestUtil.addGroup(_group3.getGroupId());

		_groups.add(_group4);

		_groups.add(_group3);
		_groups.add(_group2);
		_groups.add(_group1);
	}

	@Test
	public void testGetDescendants() throws Exception {
		Assert.assertEquals(3, _group1.getDescendants(true).size());
		Assert.assertEquals(2, _group2.getDescendants(true).size());
		Assert.assertEquals(1, _group3.getDescendants(true).size());
		Assert.assertEquals(0, _group4.getDescendants(true).size());

		List<Group> groups = _group1.getDescendants(true);

		Assert.assertTrue(groups.contains(_group2));
		Assert.assertTrue(groups.contains(_group3));
		Assert.assertTrue(groups.contains(_group4));
		Assert.assertTrue(!groups.contains(_group1));
	}

	@Test
	public void testGetStagedSites() {
		List<Group> groups = GroupLocalServiceUtil.getStagedSites();

		Assert.assertTrue(groups.isEmpty());
	}

	private Group _group1;
	private Group _group2;
	private Group _group3;
	private Group _group4;

	@DeleteAfterTestRun
	private final List<Group> _groups = new ArrayList<>();

}
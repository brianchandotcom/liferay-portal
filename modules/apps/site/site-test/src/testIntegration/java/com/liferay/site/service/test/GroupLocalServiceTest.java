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

package com.liferay.site.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
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

	@Test
	public void testGetDescendantGroups() throws Exception {
		Group group1 = GroupTestUtil.addGroup();

		_groups.addFirst(group1);

		Group group2 = GroupTestUtil.addGroup(group1.getGroupId());

		_groups.addFirst(group2);

		Group group3 = GroupTestUtil.addGroup(group2.getGroupId());

		_groups.addFirst(group3);

		Group group4 = GroupTestUtil.addGroup(group1.getGroupId());

		_groups.addFirst(group4);

		_assertDescendantGroups(group1, group2, group3, group4);

		_assertDescendantGroups(group2, group3);

		_assertDescendantGroups(group3);

		_assertDescendantGroups(group4);
	}

	@Test
	public void testGetStagedSites() {
		List<Group> groups = _groupLocalService.getStagedSites();

		Assert.assertTrue(groups.toString(), groups.isEmpty());
	}

	private void _assertDescendantGroups(
		Group parentGroup, Group... expectedDescendantGroups) {

		List<Group> actualDescendantGroups = parentGroup.getDescendants(true);

		Assert.assertEquals(
			actualDescendantGroups.toString(), expectedDescendantGroups.length,
			actualDescendantGroups.size());

		for (Group expectedDescendantGroup : expectedDescendantGroups) {
			Assert.assertTrue(
				"Missing descendant: " + expectedDescendantGroup.toString(),
				actualDescendantGroups.contains(expectedDescendantGroup));
		}
	}

	@Inject
	private GroupLocalService _groupLocalService;

	@DeleteAfterTestRun
	private final LinkedList<Group> _groups = new LinkedList<>();

}
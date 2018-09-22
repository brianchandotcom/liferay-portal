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
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.GroupServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerTestRule;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Drew Brokke
 */
public class GroupServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerTestRule.INSTANCE);

	@Test
	public void testGetGroupsLikeName() throws Exception {
		String baseName = RandomTestUtil.randomString(10);
		List<Group> expectedGroups = new ArrayList<>();

		for (int i = 0; i < 10; i++) {
			String uniqueName = baseName + String.valueOf(i);

			Group group = GroupTestUtil.addGroup(
				GroupConstants.DEFAULT_PARENT_GROUP_ID);

			group.setName(uniqueName);

			group = GroupLocalServiceUtil.updateGroup(group);

			expectedGroups.add(group);
		}

		_groups.addAll(expectedGroups);
		_groups.add(GroupTestUtil.addGroup());
		_groups.add(GroupTestUtil.addGroup());
		_groups.add(GroupTestUtil.addGroup());

		String searchString = baseName + "%";

		List<Group> actualGroups = GroupServiceUtil.getGroups(
			TestPropsValues.getCompanyId(),
			GroupConstants.DEFAULT_PARENT_GROUP_ID, searchString, true,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(
			actualGroups.toString(), expectedGroups.size(),
			actualGroups.size());

		Assert.assertTrue(
			actualGroups.toString(), actualGroups.containsAll(expectedGroups));

		Assert.assertEquals(
			expectedGroups.size(),
			GroupServiceUtil.getGroupsCount(
				TestPropsValues.getCompanyId(),
				GroupConstants.DEFAULT_PARENT_GROUP_ID, searchString, true));
	}

	@Test
	public void testGetGtGroups() throws Exception {
		for (int i = 0; i < 10; i++) {
			_groups.add(GroupTestUtil.addGroup());
		}

		long parentGroupId = 0;
		int size = 5;

		List<Group> groups = GroupServiceUtil.getGtGroups(
			0, TestPropsValues.getCompanyId(), parentGroupId, true, size);

		Assert.assertFalse(groups.isEmpty());
		Assert.assertEquals(groups.toString(), size, groups.size());

		Group lastGroup = groups.get(groups.size() - 1);

		groups = GroupServiceUtil.getGtGroups(
			lastGroup.getGroupId(), TestPropsValues.getCompanyId(),
			parentGroupId, true, size);

		Assert.assertFalse(groups.isEmpty());
		Assert.assertEquals(groups.toString(), size, groups.size());

		long previousGroupId = 0;

		for (Group group : groups) {
			long groupId = group.getGroupId();

			Assert.assertTrue(groupId > lastGroup.getGroupId());
			Assert.assertTrue(groupId > previousGroupId);

			previousGroupId = groupId;
		}
	}

	@DeleteAfterTestRun
	private final List<Group> _groups = new ArrayList<>();

}
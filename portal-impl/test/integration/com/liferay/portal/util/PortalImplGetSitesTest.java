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

package com.liferay.portal.util;

import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.User;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.util.test.LayoutTestUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Eduardo Garcia
 */
public class PortalImplGetSitesTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_groups.add(_group);

		_user = UserTestUtil.addGroupAdminUser(_group);

		_users.add(_user);
	}

	@Test
	public void testGetSharedContentSiteGroupIdsFromAncestors()
		throws Exception {

		Group grandparentGroup = GroupTestUtil.addGroup();

		Group parentGroup = GroupTestUtil.addGroup(
			grandparentGroup.getGroupId());

		_group = GroupTestUtil.addGroup(parentGroup.getGroupId());

		_groups.add(_group);
		_groups.add(parentGroup);
		_groups.add(grandparentGroup);

		_user = UserTestUtil.addGroupAdminUser(_group);

		_users.add(_user);

		long[] groupIds = getSharedContentSiteGroupIds();

		Assert.assertTrue(
			ArrayUtil.contains(groupIds, grandparentGroup.getGroupId()));
		Assert.assertTrue(
			ArrayUtil.contains(groupIds, parentGroup.getGroupId()));
	}

	@Test
	public void testGetSharedContentSiteGroupIdsFromCompany() throws Exception {
		Company company = CompanyLocalServiceUtil.getCompany(
			_group.getCompanyId());

		Assert.assertTrue(
			ArrayUtil.contains(
				getSharedContentSiteGroupIds(), company.getGroupId()));
	}

	@Test
	public void testGetSharedContentSiteGroupIdsFromDescendants()
		throws Exception {

		Group childGroup = GroupTestUtil.addGroup(_group.getGroupId());

		_groups.add(0, childGroup);

		Group grandchildGroup = GroupTestUtil.addGroup(childGroup.getGroupId());

		_groups.add(0, grandchildGroup);

		long[] groupIds = getSharedContentSiteGroupIds();

		Assert.assertTrue(
			ArrayUtil.contains(groupIds, childGroup.getGroupId()));
		Assert.assertTrue(
			ArrayUtil.contains(groupIds, grandchildGroup.getGroupId()));
	}

	@Test
	public void testGetSharedContentSiteGroupIdsFromGroup() throws Exception {
		Group group = GroupTestUtil.addGroup();

		_groups.add(group);

		_user = UserTestUtil.addGroupAdminUser(group);

		_users.add(_user);

		Assert.assertTrue(
			ArrayUtil.contains(
				getSharedContentSiteGroupIds(), group.getGroupId()));
	}

	@Test
	public void testGetSharedContentSiteGroupIdsFromLayout() throws Exception {
		Layout layout = LayoutTestUtil.addLayout(_group);

		Group group = GroupTestUtil.addGroup(_user.getUserId(), layout);

		Assert.assertTrue(
			ArrayUtil.contains(
				getSharedContentSiteGroupIds(), group.getGroupId()));
	}

	@Test
	public void testGetSharedContentSiteGroupIdsReturnsUniqueGroupIds()
		throws Exception {

		long[] groupIds = getSharedContentSiteGroupIds();

		Set<Long> set = new HashSet<>(ListUtil.toList(groupIds));

		Assert.assertFalse(set.size() < groupIds.length);
	}

	protected long[] getSharedContentSiteGroupIds() throws Exception {
		return PortalUtil.getSharedContentSiteGroupIds(
			_group.getCompanyId(), _group.getGroupId(), _user.getUserId());
	}

	private Group _group;

	@DeleteAfterTestRun
	private final List<Group> _groups = new ArrayList<>();

	private User _user;

	@DeleteAfterTestRun
	private final List<User> _users = new ArrayList<>();

}
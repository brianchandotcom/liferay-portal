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

package com.liferay.portal.util.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eduardo García
 */
@RunWith(Arquillian.class)
public class PortalImplGetSitesTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

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

		long[] groupIds = _getSharedContentSiteGroupIds();

		Assert.assertTrue(
			ArrayUtil.contains(groupIds, grandparentGroup.getGroupId()));
		Assert.assertTrue(
			ArrayUtil.contains(groupIds, parentGroup.getGroupId()));
	}

	@Test
	public void testGetSharedContentSiteGroupIdsFromCompany() throws Exception {
		Company company = _companyLocalService.getCompany(
			_group.getCompanyId());

		Assert.assertTrue(
			ArrayUtil.contains(
				_getSharedContentSiteGroupIds(), company.getGroupId()));
	}

	@Test
	public void testGetSharedContentSiteGroupIdsFromDescendants()
		throws Exception {

		Group childGroup = GroupTestUtil.addGroup(_group.getGroupId());

		_groups.add(0, childGroup);

		Group grandchildGroup = GroupTestUtil.addGroup(childGroup.getGroupId());

		_groups.add(0, grandchildGroup);

		long[] groupIds = _getSharedContentSiteGroupIds();

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
				_getSharedContentSiteGroupIds(), group.getGroupId()));
	}

	@Test
	public void testGetSharedContentSiteGroupIdsFromLayout() throws Exception {
		Layout layout = LayoutTestUtil.addTypePortletLayout(_group);

		Group group = GroupTestUtil.addGroup(_user.getUserId(), layout);

		Assert.assertTrue(
			ArrayUtil.contains(
				_getSharedContentSiteGroupIds(), group.getGroupId()));
	}

	@Test
	public void testGetSharedContentSiteGroupIdsReturnsUniqueGroupIds()
		throws Exception {

		long[] groupIds = _getSharedContentSiteGroupIds();

		Set<Long> set = new HashSet<>(ListUtil.fromArray(groupIds));

		Assert.assertFalse(set.size() < groupIds.length);
	}

	private long[] _getSharedContentSiteGroupIds() throws Exception {
		return _portal.getSharedContentSiteGroupIds(
			_group.getCompanyId(), _group.getGroupId(), _user.getUserId());
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	private Group _group;

	@DeleteAfterTestRun
	private final List<Group> _groups = new ArrayList<>();

	@Inject
	private Portal _portal;

	private User _user;

	@DeleteAfterTestRun
	private final List<User> _users = new ArrayList<>();

}
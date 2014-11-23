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

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.User;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.GroupServiceUtil;
import com.liferay.portal.test.MainServletTestRule;
import com.liferay.portal.test.listeners.ResetDatabaseExecutionTestListener;
import com.liferay.portal.test.runners.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.util.test.GroupTestUtil;
import com.liferay.portal.util.test.LayoutTestUtil;
import com.liferay.portal.util.test.UserTestUtil;
import com.liferay.portlet.sites.util.Sites;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eduardo Garcia
 */
@ExecutionTestListeners(
	listeners = {
		ResetDatabaseExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class PortalImplGetSitesTest {

	@ClassRule
	public static final MainServletTestRule mainServletTestRule =
		MainServletTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_user = UserTestUtil.addGroupAdminUser(_group);
	}

	@Test
	public void testGetSharedContentSiteGroupIdsFromAncestors()
		throws Exception {

		testGetSharedContentSiteGroupIdsFromAncestors(true);
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

		Group childGroup = GroupTestUtil.addGroup(
			_group.getGroupId(), StringUtil.randomString());

		Group grandchildGroup = GroupTestUtil.addGroup(
			childGroup.getGroupId(), StringUtil.randomString());

		long[] groupIds = getSharedContentSiteGroupIds();

		Assert.assertTrue(
			ArrayUtil.contains(groupIds, childGroup.getGroupId()));
		Assert.assertTrue(
			ArrayUtil.contains(groupIds, grandchildGroup.getGroupId()));
	}

	@Test
	public void testGetSharedContentSiteGroupIdsFromGroup() throws Exception {
		Group group = GroupTestUtil.addGroup();

		_user = UserTestUtil.addGroupAdminUser(group);

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

		Set<Long> set = new HashSet<Long>(ListUtil.toList(groupIds));

		Assert.assertFalse(set.size() < groupIds.length);
	}

	protected long[] getSharedContentSiteGroupIds() throws Exception {
		return PortalUtil.getSharedContentSiteGroupIds(
			_group.getCompanyId(), _group.getGroupId(), _user.getUserId());
	}

	protected void setContentSharingWithChildrenEnabled(
			Group group, int contentSharingWithChildrenEnabled)
		throws Exception {

		UnicodeProperties typeSettingsProperties =
			group.getTypeSettingsProperties();

		typeSettingsProperties.setProperty(
			"contentSharingWithChildrenEnabled",
			String.valueOf(contentSharingWithChildrenEnabled));

		GroupServiceUtil.updateGroup(
			group.getGroupId(), typeSettingsProperties.toString());
	}

	protected void testGetSharedContentSiteGroupIdsFromAncestors(
			boolean contentSharingWithChildrenEnabled)
		throws Exception {

		Group grandparentGroup = GroupTestUtil.addGroup();

		Group parentGroup = GroupTestUtil.addGroup(
			grandparentGroup.getGroupId(), StringUtil.randomString());

		_group = GroupTestUtil.addGroup(
			parentGroup.getGroupId(), StringUtil.randomString());

		_user = UserTestUtil.addGroupAdminUser(_group);

		if (!contentSharingWithChildrenEnabled) {
			setContentSharingWithChildrenEnabled(
				grandparentGroup, Sites.CONTENT_SHARING_WITH_CHILDREN_DISABLED);
			setContentSharingWithChildrenEnabled(
				parentGroup, Sites.CONTENT_SHARING_WITH_CHILDREN_DISABLED);
		}

		long[] groupIds = getSharedContentSiteGroupIds();

		Assert.assertEquals(
			contentSharingWithChildrenEnabled,
			ArrayUtil.contains(groupIds, grandparentGroup.getGroupId()));
		Assert.assertEquals(
			contentSharingWithChildrenEnabled,
			ArrayUtil.contains(groupIds, parentGroup.getGroupId()));
	}

	private Group _group;
	private User _user;

}
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

package com.liferay.portal.util;

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.User;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.GroupServiceUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portlet.sites.util.Sites;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eduardo Garcia
 */
@ExecutionTestListeners(
	listeners = {
		EnvironmentExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Transactional
public class PortalImplGetSitesTest {

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_groupAdminUser = UserTestUtil.addGroupAdminUser(_group);
	}

	@Test
	public void testGetSharedContentSiteGroupIdsFromAdministered()
		throws Exception {

		Group administeredGroup = GroupTestUtil.addGroup();

		_groupAdminUser = UserTestUtil.addGroupAdminUser(administeredGroup);

		Assert.assertTrue(
			ArrayUtil.contains(
				_getSharedContentSiteGroupIds(),
				administeredGroup.getGroupId()));
	}

	@Test
	public void testGetSharedContentSiteGroupIdsFromAncestors()
		throws Exception {

		doTestGetSharedContentSiteGroupIdsFromAncestors(true);
	}

	@Test
	public void testGetSharedContentSiteGroupIdsFromDescendants()
		throws Exception {

		Group childGroup = GroupTestUtil.addGroup(
			_group.getGroupId(), StringUtil.randomString());

		Group grandChildGroup = GroupTestUtil.addGroup(
			childGroup.getGroupId(), StringUtil.randomString());

		long[] groupIds = _getSharedContentSiteGroupIds();

		Assert.assertTrue(
			ArrayUtil.contains(groupIds, childGroup.getGroupId()));

		Assert.assertTrue(
			ArrayUtil.contains(groupIds, grandChildGroup.getGroupId()));
	}

	@Test
	public void testGetSharedContentSiteGroupIdsFromGlobal() throws Exception {
		Company company = CompanyLocalServiceUtil.getCompany(
			_group.getCompanyId());

		Assert.assertTrue(
			ArrayUtil.contains(
				_getSharedContentSiteGroupIds(), company.getGroupId()));
	}

	@Test
	public void testGetSharedContentSiteGroupIdsFromLayoutScope()
		throws Exception {

		Layout layout = LayoutTestUtil.addLayout(_group);

		Group layoutScopeGroup = GroupTestUtil.addGroup(
			_groupAdminUser.getUserId(), layout);

		Assert.assertTrue(
			ArrayUtil.contains(
				_getSharedContentSiteGroupIds(),
				layoutScopeGroup.getGroupId()));
	}

	protected void doTestGetSharedContentSiteGroupIdsFromAncestors(
			boolean contentSharingWithChildrenEnabled)
		throws Exception {

		Group grandParentGroup = GroupTestUtil.addGroup();

		Group parentGroup = GroupTestUtil.addGroup(
			grandParentGroup.getGroupId(), StringUtil.randomString());

		_group = GroupTestUtil.addGroup(
			parentGroup.getGroupId(), StringUtil.randomString());

		_groupAdminUser = UserTestUtil.addGroupAdminUser(_group);

		if (!contentSharingWithChildrenEnabled) {
			_setContentSharingWithChildrenEnabled(
				grandParentGroup, Sites.CONTENT_SHARING_WITH_CHILDREN_DISABLED);

			_setContentSharingWithChildrenEnabled(
				parentGroup, Sites.CONTENT_SHARING_WITH_CHILDREN_DISABLED);
		}

		long[] groupIds = _getSharedContentSiteGroupIds();

		Assert.assertEquals(
			contentSharingWithChildrenEnabled,
			ArrayUtil.contains(groupIds, grandParentGroup.getGroupId()));

		Assert.assertEquals(
			contentSharingWithChildrenEnabled,
			ArrayUtil.contains(groupIds, parentGroup.getGroupId()));
	}

	private long[] _getSharedContentSiteGroupIds() throws Exception {
		return PortalUtil.getSharedContentSiteGroupIds(
			_group.getCompanyId(), _group.getGroupId(),
			_groupAdminUser.getUserId());
	}

	private void _setContentSharingWithChildrenEnabled(
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

	private Group _group;
	private User _groupAdminUser;

}
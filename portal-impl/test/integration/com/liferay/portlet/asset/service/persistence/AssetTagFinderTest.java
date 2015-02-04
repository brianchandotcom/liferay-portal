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

package com.liferay.portlet.asset.service.persistence;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.TransactionalTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.test.LayoutTestUtil;
import com.liferay.portlet.asset.model.AssetTag;
import com.liferay.portlet.asset.service.AssetTagLocalServiceUtil;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.util.test.BlogsTestUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Sergio González
 */
public class AssetTagFinderTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			TransactionalTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_scopeGroup = addScopeGroup();
	}

	@Test
	public void testFilterCountByG_C_N() throws Exception {
		long classNameId = PortalUtil.getClassNameId(BlogsEntry.class);
		String assetTagName = RandomTestUtil.randomString();

		int initialScopeGroupAssetTagsCount =
			AssetTagFinderUtil.filterCountByG_C_N(
				_scopeGroup.getGroupId(), classNameId, assetTagName);
		int initialSiteGroupAssetTagsCount =
			AssetTagFinderUtil.filterCountByG_C_N(
				_scopeGroup.getParentGroupId(), classNameId, assetTagName);

		addBlogsEntry(_scopeGroup.getGroupId(), assetTagName);

		User user = UserTestUtil.addUser(null, 0);

		PermissionChecker originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		try {
			PermissionChecker permissionChecker =
				PermissionCheckerFactoryUtil.create(user);

			PermissionThreadLocal.setPermissionChecker(permissionChecker);

			int scopeGroupAssetTagsCount =
				AssetTagFinderUtil.filterCountByG_C_N(
					_scopeGroup.getGroupId(), classNameId, assetTagName);

			Assert.assertEquals(
				initialScopeGroupAssetTagsCount + 1, scopeGroupAssetTagsCount);

			int siteGroupAssetTagsCount = AssetTagFinderUtil.filterCountByG_C_N(
				_scopeGroup.getParentGroupId(), classNameId, assetTagName);

			Assert.assertEquals(
				initialSiteGroupAssetTagsCount, siteGroupAssetTagsCount);
		}
		finally {
			PermissionThreadLocal.setPermissionChecker(
				originalPermissionChecker);
		}
	}

	@Test
	public void testFilterCountByG_N() throws Exception {
		String assetTagName = RandomTestUtil.randomString();

		int initialScopeGroupAssetTagsCount =
			AssetTagFinderUtil.filterCountByG_N(
				_scopeGroup.getGroupId(), assetTagName);
		int initialTagsCountSiteGroup = AssetTagFinderUtil.filterCountByG_N(
			_scopeGroup.getParentGroupId(), assetTagName);

		addBlogsEntry(_scopeGroup.getGroupId(), assetTagName);

		User user = UserTestUtil.addUser(null, 0);

		PermissionChecker originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		try {
			PermissionChecker permissionChecker =
				PermissionCheckerFactoryUtil.create(user);

			PermissionThreadLocal.setPermissionChecker(permissionChecker);

			int scopeGroupAssetTagsCount = AssetTagFinderUtil.filterCountByG_N(
				_scopeGroup.getGroupId(), assetTagName);

			Assert.assertEquals(
				initialScopeGroupAssetTagsCount + 1, scopeGroupAssetTagsCount);

			int siteGroupAssetTagsCount = AssetTagFinderUtil.filterCountByG_N(
				_scopeGroup.getParentGroupId(), assetTagName);

			Assert.assertEquals(
				initialTagsCountSiteGroup, siteGroupAssetTagsCount);
		}
		finally {
			PermissionThreadLocal.setPermissionChecker(
				originalPermissionChecker);
		}
	}

	@Test
	public void testFilterFindByG_C_N() throws Exception {
		long classNameId = PortalUtil.getClassNameId(BlogsEntry.class);
		String assetTagName = RandomTestUtil.randomString();

		List<AssetTag> initialScopeGroupAssetTags =
			AssetTagFinderUtil.filterFindByG_C_N(
				_scopeGroup.getGroupId(), classNameId, assetTagName,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
		List<AssetTag> initialSiteGroupAssetTags =
			AssetTagFinderUtil.filterFindByG_C_N(
				_scopeGroup.getParentGroupId(), classNameId, assetTagName,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		addBlogsEntry(_scopeGroup.getGroupId(), assetTagName);

		User user = UserTestUtil.addUser(null, 0);

		PermissionChecker originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		try {
			PermissionChecker permissionChecker =
				PermissionCheckerFactoryUtil.create(user);

			PermissionThreadLocal.setPermissionChecker(permissionChecker);

			List<AssetTag> scopeGroupAssetTags =
				AssetTagFinderUtil.filterFindByG_C_N(
					_scopeGroup.getGroupId(), classNameId, assetTagName,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

			Assert.assertEquals(
				initialScopeGroupAssetTags.size() + 1,
				scopeGroupAssetTags.size());

			List<AssetTag> siteGroupAssetTags =
				AssetTagFinderUtil.filterFindByG_C_N(
					_scopeGroup.getParentGroupId(), classNameId, assetTagName,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

			Assert.assertEquals(
				initialSiteGroupAssetTags.size(), siteGroupAssetTags.size());
		}
		finally {
			PermissionThreadLocal.setPermissionChecker(
				originalPermissionChecker);
		}
	}

	protected void addAssetTag(long groupId, String name) throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		AssetTagLocalServiceUtil.addTag(
			TestPropsValues.getUserId(), name, serviceContext);
	}

	protected void addBlogsEntry(long groupId, String assetTagName)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		serviceContext.setAssetTagNames(new String[] {assetTagName});

		BlogsTestUtil.addEntry(
			TestPropsValues.getUserId(), RandomTestUtil.randomString(), true,
			serviceContext);
	}

	protected Group addScopeGroup() throws Exception {
		Group group = GroupTestUtil.addGroup();

		Layout layout = LayoutTestUtil.addLayout(group);

		Map<Locale, String> nameMap = new HashMap<>();

		String name = RandomTestUtil.randomString();

		nameMap.put(LocaleUtil.getDefault(), name);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		Group scopeGroup = GroupLocalServiceUtil.addGroup(
			TestPropsValues.getUserId(), group.getParentGroupId(),
			Layout.class.getName(), layout.getPlid(),
			GroupConstants.DEFAULT_LIVE_GROUP_ID, nameMap,
			RandomTestUtil.randomLocaleStringMap(),
			GroupConstants.TYPE_SITE_OPEN, true,
			GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION,
			StringPool.SLASH + FriendlyURLNormalizerUtil.normalize(name), false,
			true, serviceContext);

		return scopeGroup;
	}

	@DeleteAfterTestRun
	private Group _scopeGroup;

}
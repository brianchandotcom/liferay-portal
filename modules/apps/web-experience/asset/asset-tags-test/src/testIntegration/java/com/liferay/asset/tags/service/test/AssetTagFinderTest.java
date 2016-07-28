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

package com.liferay.asset.tags.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetTagLocalServiceUtil;
import com.liferay.asset.kernel.service.persistence.AssetTagFinderUtil;
import com.liferay.blogs.kernel.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.TransactionalTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.test.LayoutTestUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Sergio González
 */
@RunWith(Arquillian.class)
public class AssetTagFinderTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), TransactionalTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_scopeGroup = addScopeGroup();
	}

	@Test
	public void testCountByG_C_N() throws Exception {
		long classNameId = PortalUtil.getClassNameId(BlogsEntry.class);
		String assetTagName = RandomTestUtil.randomString();

		int initialScopeGroupAssetTagsCount = AssetTagFinderUtil.countByG_C_N(
			_scopeGroup.getGroupId(), classNameId, assetTagName);
		int initialSiteGroupAssetTagsCount = AssetTagFinderUtil.countByG_C_N(
			_scopeGroup.getParentGroupId(), classNameId, assetTagName);

		addBlogsEntry(_scopeGroup.getGroupId(), assetTagName);

		int scopeGroupAssetTagsCount = AssetTagFinderUtil.countByG_C_N(
			_scopeGroup.getGroupId(), classNameId, assetTagName);

		Assert.assertEquals(
			initialScopeGroupAssetTagsCount + 1, scopeGroupAssetTagsCount);

		int siteGroupAssetTagsCount = AssetTagFinderUtil.countByG_C_N(
			_scopeGroup.getParentGroupId(), classNameId, assetTagName);

		Assert.assertEquals(
			initialSiteGroupAssetTagsCount, siteGroupAssetTagsCount);
	}

	@Test
	public void testCountByG_N() throws Exception {
		String assetTagName = RandomTestUtil.randomString();

		int initialScopeGroupAssetTagsCount = AssetTagFinderUtil.countByG_N(
			_scopeGroup.getGroupId(), assetTagName);
		int initialTagsCountSiteGroup = AssetTagFinderUtil.countByG_N(
			_scopeGroup.getParentGroupId(), assetTagName);

		addBlogsEntry(_scopeGroup.getGroupId(), assetTagName);

		int scopeGroupAssetTagsCount = AssetTagFinderUtil.countByG_N(
			_scopeGroup.getGroupId(), assetTagName);

		Assert.assertEquals(
			initialScopeGroupAssetTagsCount + 1, scopeGroupAssetTagsCount);

		int siteGroupAssetTagsCount = AssetTagFinderUtil.countByG_N(
			_scopeGroup.getParentGroupId(), assetTagName);

		Assert.assertEquals(initialTagsCountSiteGroup, siteGroupAssetTagsCount);
	}

	@Test
	public void testFindByG_C_N() throws Exception {
		long classNameId = PortalUtil.getClassNameId(BlogsEntry.class);
		String assetTagName = RandomTestUtil.randomString();

		List<AssetTag> initialScopeGroupAssetTags =
			AssetTagFinderUtil.findByG_C_N(
				_scopeGroup.getGroupId(), classNameId, assetTagName,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
		List<AssetTag> initialSiteGroupAssetTags =
			AssetTagFinderUtil.findByG_C_N(
				_scopeGroup.getParentGroupId(), classNameId, assetTagName,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		addBlogsEntry(_scopeGroup.getGroupId(), assetTagName);

		List<AssetTag> scopeGroupAssetTags = AssetTagFinderUtil.findByG_C_N(
			_scopeGroup.getGroupId(), classNameId, assetTagName,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		Assert.assertEquals(
			initialScopeGroupAssetTags.size() + 1, scopeGroupAssetTags.size());

		List<AssetTag> siteGroupAssetTags = AssetTagFinderUtil.findByG_C_N(
			_scopeGroup.getParentGroupId(), classNameId, assetTagName,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		Assert.assertEquals(
			initialSiteGroupAssetTags.size(), siteGroupAssetTags.size());
	}

	protected void addAssetTag(long groupId, String name) throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		AssetTagLocalServiceUtil.addTag(
			TestPropsValues.getUserId(), groupId, name, serviceContext);
	}

	protected void addBlogsEntry(long groupId, String assetTagName)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				groupId, TestPropsValues.getUserId());

		serviceContext.setAssetTagNames(new String[] {assetTagName});

		BlogsEntryLocalServiceUtil.addEntry(
			TestPropsValues.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), serviceContext);
	}

	protected Group addScopeGroup() throws Exception {
		Group group = GroupTestUtil.addGroup();

		Layout layout = LayoutTestUtil.addLayout(group);

		Map<Locale, String> nameMap = new HashMap<>();

		String name = RandomTestUtil.randomString();

		nameMap.put(LocaleUtil.getDefault(), name);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		return GroupLocalServiceUtil.addGroup(
			TestPropsValues.getUserId(), group.getParentGroupId(),
			Layout.class.getName(), layout.getPlid(),
			GroupConstants.DEFAULT_LIVE_GROUP_ID, nameMap,
			RandomTestUtil.randomLocaleStringMap(),
			GroupConstants.TYPE_SITE_OPEN, true,
			GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION,
			StringPool.SLASH + FriendlyURLNormalizerUtil.normalize(name), false,
			true, serviceContext);
	}

	@DeleteAfterTestRun
	private Group _scopeGroup;

}
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

package com.liferay.asset.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetTagLocalServiceUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.message.boards.constants.MBCategoryConstants;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.test.util.MBTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.After;
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
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		Layout layout = LayoutTestUtil.addLayout(_group);

		String name = RandomTestUtil.randomString();

		Map<Locale, String> nameMap = HashMapBuilder.<Locale, String>put(
			LocaleUtil.getDefault(), name
		).build();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		_scopeGroup = GroupLocalServiceUtil.addGroup(
			TestPropsValues.getUserId(), _group.getParentGroupId(),
			Layout.class.getName(), layout.getPlid(),
			GroupConstants.DEFAULT_LIVE_GROUP_ID, nameMap,
			RandomTestUtil.randomLocaleStringMap(),
			GroupConstants.TYPE_SITE_OPEN, true,
			GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION,
			StringPool.SLASH + FriendlyURLNormalizerUtil.normalize(name), false,
			true, serviceContext);
	}

	@After
	public void tearDown() throws Exception {
		GroupLocalServiceUtil.deleteGroup(_scopeGroup);

		GroupLocalServiceUtil.deleteGroup(_group);
	}

	@Test
	public void testCountByG_C_N() throws Exception {
		long classNameId = PortalUtil.getClassNameId(MBMessage.class);
		String assetTagName = RandomTestUtil.randomString();

		int initialScopeGroupAssetTagsCount =
			AssetTagLocalServiceUtil.getTagsSize(
				_scopeGroup.getGroupId(), classNameId, assetTagName);
		int initialSiteGroupAssetTagsCount =
			AssetTagLocalServiceUtil.getTagsSize(
				_scopeGroup.getParentGroupId(), classNameId, assetTagName);

		addMBMessage(_scopeGroup.getGroupId(), assetTagName);

		int scopeGroupAssetTagsCount = AssetTagLocalServiceUtil.getTagsSize(
			_scopeGroup.getGroupId(), classNameId, assetTagName);

		Assert.assertEquals(
			initialScopeGroupAssetTagsCount + 1, scopeGroupAssetTagsCount);

		int siteGroupAssetTagsCount = AssetTagLocalServiceUtil.getTagsSize(
			_scopeGroup.getParentGroupId(), classNameId, assetTagName);

		Assert.assertEquals(
			initialSiteGroupAssetTagsCount, siteGroupAssetTagsCount);
	}

	@Test
	public void testCountByG_N() throws Exception {
		String assetTagName = RandomTestUtil.randomString();

		int initialScopeGroupAssetTagsCount =
			AssetTagLocalServiceUtil.getTagsSize(
				_scopeGroup.getGroupId(), assetTagName);
		int initialTagsCountSiteGroup = AssetTagLocalServiceUtil.getTagsSize(
			_scopeGroup.getParentGroupId(), assetTagName);

		addMBMessage(_scopeGroup.getGroupId(), assetTagName);

		int scopeGroupAssetTagsCount = AssetTagLocalServiceUtil.getTagsSize(
			_scopeGroup.getGroupId(), assetTagName);

		Assert.assertEquals(
			initialScopeGroupAssetTagsCount + 1, scopeGroupAssetTagsCount);

		int siteGroupAssetTagsCount = AssetTagLocalServiceUtil.getTagsSize(
			_scopeGroup.getParentGroupId(), assetTagName);

		Assert.assertEquals(initialTagsCountSiteGroup, siteGroupAssetTagsCount);
	}

	@Test
	public void testFindByG_C_N() throws Exception {
		long classNameId = PortalUtil.getClassNameId(MBMessage.class);
		String assetTagName = RandomTestUtil.randomString();

		List<AssetTag> initialScopeGroupAssetTags =
			AssetTagLocalServiceUtil.getTags(
				_scopeGroup.getGroupId(), classNameId, assetTagName,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);
		List<AssetTag> initialSiteGroupAssetTags =
			AssetTagLocalServiceUtil.getTags(
				_scopeGroup.getParentGroupId(), classNameId, assetTagName,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		addMBMessage(_scopeGroup.getGroupId(), assetTagName);

		List<AssetTag> scopeGroupAssetTags = AssetTagLocalServiceUtil.getTags(
			_scopeGroup.getGroupId(), classNameId, assetTagName,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(
			scopeGroupAssetTags.toString(),
			initialScopeGroupAssetTags.size() + 1, scopeGroupAssetTags.size());

		List<AssetTag> siteGroupAssetTags = AssetTagLocalServiceUtil.getTags(
			_scopeGroup.getParentGroupId(), classNameId, assetTagName,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(
			siteGroupAssetTags.toString(), initialSiteGroupAssetTags.size(),
			siteGroupAssetTags.size());
	}

	protected void addMBMessage(long groupId, String assetTagName)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				groupId, TestPropsValues.getUserId());

		serviceContext.setAssetTagNames(new String[] {assetTagName});

		MBTestUtil.addMessageWithWorkflow(
			groupId, MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), true,
			serviceContext);
	}

	private Group _group;
	private Group _scopeGroup;

}
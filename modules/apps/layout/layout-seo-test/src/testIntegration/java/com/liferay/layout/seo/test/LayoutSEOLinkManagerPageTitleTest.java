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

package com.liferay.layout.seo.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.seo.kernel.LayoutSEOLinkManager;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ListMergeable;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alicia García
 */
@RunWith(Arquillian.class)
public class LayoutSEOLinkManagerPageTitleTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_layout = _layoutLocalService.getLayout(TestPropsValues.getPlid());
		_group = GroupTestUtil.addGroup();

		_layout.setGroupId(_group.getGroupId());
	}

	@Test
	public void testGetPageTitleLayoutTitle() throws Exception {
		String siteName = RandomTestUtil.randomString();

		_group.setName(siteName);

		_groupLocalService.updateGroup(_group);

		String layoutTitle = RandomTestUtil.randomString();

		_layout.setTitle(layoutTitle);

		String companyName = RandomTestUtil.randomString();

		Assert.assertEquals(
			StringBundler.concat(
				layoutTitle, " - ", siteName, " - ", companyName),
			_layoutSEOLinkManager.getPageTitle(
				_layout, null, null, null, null, companyName,
				LocaleUtil.getDefault()));
	}

	@Test
	public void testGetPageTitleLayoutTitleTitleList() throws Exception {
		String siteName = RandomTestUtil.randomString();

		_group.setName(siteName);

		_groupLocalService.updateGroup(_group);

		String layoutTitle = RandomTestUtil.randomString();

		_layout.setTitle(layoutTitle);

		String companyName = RandomTestUtil.randomString();

		ListMergeable<String> titleListMergeable = new ListMergeable<>();

		titleListMergeable.add(RandomTestUtil.randomString());
		titleListMergeable.add(RandomTestUtil.randomString());

		Assert.assertEquals(
			StringBundler.concat(
				titleListMergeable.mergeToString(StringPool.SPACE), " - ",
				siteName, " - ", companyName),
			_layoutSEOLinkManager.getPageTitle(
				_layout, null, null, titleListMergeable, null, companyName,
				LocaleUtil.getDefault()));
	}

	@Test
	public void testGetPageTitleLayoutWithSubtitleList() throws Exception {
		String siteName = RandomTestUtil.randomString();

		_group.setName(siteName);

		_groupLocalService.updateGroup(_group);

		String layoutTitle = RandomTestUtil.randomString();

		_layout.setTitle(layoutTitle);

		String companyName = RandomTestUtil.randomString();

		ListMergeable<String> subtitleListMergeable = new ListMergeable<>();

		subtitleListMergeable.add(RandomTestUtil.randomString());
		subtitleListMergeable.add(RandomTestUtil.randomString());

		Assert.assertEquals(
			StringBundler.concat(
				subtitleListMergeable.mergeToString(StringPool.SPACE), " - ",
				layoutTitle, " - ", siteName, " - ", companyName),
			_layoutSEOLinkManager.getPageTitle(
				_layout, null, null, null, subtitleListMergeable, companyName,
				LocaleUtil.getDefault()));
	}

	@Test
	public void testGetPageTitleLayoutWithTitleListSubtitleList()
		throws Exception {

		String siteName = RandomTestUtil.randomString();

		_group.setName(siteName);

		_groupLocalService.updateGroup(_group);

		String layoutTitle = RandomTestUtil.randomString();

		_layout.setTitle(layoutTitle);

		String companyName = RandomTestUtil.randomString();

		ListMergeable<String> titleListMergeable = new ListMergeable<>();

		titleListMergeable.add(RandomTestUtil.randomString());
		titleListMergeable.add(RandomTestUtil.randomString());

		ListMergeable<String> subtitleListMergeable = new ListMergeable<>();

		subtitleListMergeable.add(RandomTestUtil.randomString());
		subtitleListMergeable.add(RandomTestUtil.randomString());

		Assert.assertEquals(
			StringBundler.concat(
				subtitleListMergeable.mergeToString(StringPool.SPACE), " - ",
				titleListMergeable.mergeToString(StringPool.SPACE), " - ",
				siteName, " - ", companyName),
			_layoutSEOLinkManager.getPageTitle(
				_layout, null, null, titleListMergeable, subtitleListMergeable,
				companyName, LocaleUtil.getDefault()));
	}

	@Test
	public void testGetPageTitleSiteCompanySiteNameEquals() throws Exception {
		String companyName = RandomTestUtil.randomString();

		_group.setName(companyName, LocaleUtil.getDefault());

		_groupLocalService.updateGroup(_group);

		String title = RandomTestUtil.randomString();

		_layout.setTitle(title);

		Assert.assertEquals(
			title + " - " + companyName,
			_layoutSEOLinkManager.getPageTitle(
				_layout, null, null, null, null, companyName,
				LocaleUtil.getDefault()));
	}

	@Test
	public void testGetPageTitleTilesTitle() throws Exception {
		String tilesTitle = RandomTestUtil.randomString();

		String siteName = RandomTestUtil.randomString();

		_group.setName(siteName);

		_groupLocalService.updateGroup(_group);

		String companyName = RandomTestUtil.randomString();

		Assert.assertEquals(
			StringBundler.concat(
				tilesTitle, " - ", siteName, " - ", companyName),
			_layoutSEOLinkManager.getPageTitle(
				_layout, null, tilesTitle, null, null, companyName,
				LocaleUtil.getDefault()));
	}

	@Test
	public void testGetPageTitleTilesTitleCompanySiteNameEquals()
		throws Exception {

		String tilesTitle = RandomTestUtil.randomString();

		String siteName = RandomTestUtil.randomString();

		_group.setName(siteName);

		_groupLocalService.updateGroup(_group);

		Assert.assertEquals(
			tilesTitle + " - " + siteName,
			_layoutSEOLinkManager.getPageTitle(
				_layout, null, tilesTitle, null, null, siteName,
				LocaleUtil.getDefault()));
	}

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private GroupLocalService _groupLocalService;

	private Layout _layout;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private LayoutSEOLinkManager _layoutSEOLinkManager;

}
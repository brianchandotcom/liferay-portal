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
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutPrototype;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ListMergeable;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortletKeys;
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
		_layout = _addLayout();

		_group = _addGroup();

		_layout.setGroupId(_group.getGroupId());
	}

	@Test
	public void testGetFullPageTitleUsesLayoutPrototypeTitle()
		throws Exception {

		String layoutPrototypeTitle = RandomTestUtil.randomString();

		LayoutPrototype layoutPrototype = LayoutTestUtil.addLayoutPrototype(
			layoutPrototypeTitle);

		_prototypeGroup = layoutPrototype.getGroup();

		_layout.setGroupId(_prototypeGroup.getGroupId());

		ListMergeable<String> subtitleListMergeable = new ListMergeable<>();

		subtitleListMergeable.add(RandomTestUtil.randomString());
		subtitleListMergeable.add(RandomTestUtil.randomString());

		String companyName = RandomTestUtil.randomString();

		Assert.assertEquals(
			StringBundler.concat(
				subtitleListMergeable.mergeToString(StringPool.SPACE), " - ",
				layoutPrototypeTitle, " - ", companyName),
			_layoutSEOLinkManager.getFullPageTitle(
				_layout, null, null, null, subtitleListMergeable, companyName,
				LocaleUtil.getDefault()));
	}

	@Test
	public void testGetFullPageTitleUsesLayoutTitle() throws Exception {
		String companyName = RandomTestUtil.randomString();

		Assert.assertEquals(
			StringBundler.concat(
				_layout.getTitle(), " - ", _group.getName(), " - ",
				companyName),
			_layoutSEOLinkManager.getFullPageTitle(
				_layout, null, null, null, null, companyName,
				LocaleUtil.getDefault()));
	}

	@Test
	public void testGetFullPageTitleUsesLayoutTitleAndSubtitleList()
		throws Exception {

		ListMergeable<String> subtitleListMergeable = new ListMergeable<>();

		subtitleListMergeable.add(RandomTestUtil.randomString());
		subtitleListMergeable.add(RandomTestUtil.randomString());

		String companyName = RandomTestUtil.randomString();

		Assert.assertEquals(
			StringBundler.concat(
				subtitleListMergeable.mergeToString(StringPool.SPACE), " - ",
				_layout.getTitle(), " - ", _group.getName(), " - ",
				companyName),
			_layoutSEOLinkManager.getFullPageTitle(
				_layout, null, null, null, subtitleListMergeable, companyName,
				LocaleUtil.getDefault()));
	}

	@Test
	public void testGetFullPageTitleUsesLayoutTitleAndTitleList()
		throws Exception {

		ListMergeable<String> titleListMergeable = new ListMergeable<>();

		titleListMergeable.add(RandomTestUtil.randomString());
		titleListMergeable.add(RandomTestUtil.randomString());

		String companyName = RandomTestUtil.randomString();

		Assert.assertEquals(
			StringBundler.concat(
				titleListMergeable.mergeToString(StringPool.SPACE), " - ",
				_group.getName(), " - ", companyName),
			_layoutSEOLinkManager.getFullPageTitle(
				_layout, null, null, titleListMergeable, null, companyName,
				LocaleUtil.getDefault()));
	}

	@Test
	public void testGetFullPageTitleUsesLayoutTitleAndTitleListAndSubtitleList()
		throws Exception {

		ListMergeable<String> titleListMergeable = new ListMergeable<>();

		titleListMergeable.add(RandomTestUtil.randomString());
		titleListMergeable.add(RandomTestUtil.randomString());

		ListMergeable<String> subtitleListMergeable = new ListMergeable<>();

		subtitleListMergeable.add(RandomTestUtil.randomString());
		subtitleListMergeable.add(RandomTestUtil.randomString());

		String companyName = RandomTestUtil.randomString();

		Assert.assertEquals(
			StringBundler.concat(
				subtitleListMergeable.mergeToString(StringPool.SPACE), " - ",
				titleListMergeable.mergeToString(StringPool.SPACE), " - ",
				_group.getName(), " - ", companyName),
			_layoutSEOLinkManager.getFullPageTitle(
				_layout, null, null, titleListMergeable, subtitleListMergeable,
				companyName, LocaleUtil.getDefault()));
	}

	@Test
	public void testGetFullPageTitleUsesPortletTitle() throws Exception {
		_modifyLayoutToSystemManage();

		Portlet portlet = _portletLocalService.getPortletById(
			TestPropsValues.getCompanyId(), PortletKeys.BLOGS_ADMIN);

		String companyName = RandomTestUtil.randomString();

		ListMergeable<String> titleListMergeable = new ListMergeable<>();

		titleListMergeable.add(RandomTestUtil.randomString());
		titleListMergeable.add(RandomTestUtil.randomString());

		ListMergeable<String> subtitleListMergeable = new ListMergeable<>();

		subtitleListMergeable.add(RandomTestUtil.randomString());
		subtitleListMergeable.add(RandomTestUtil.randomString());

		Assert.assertEquals(
			StringBundler.concat(
				_portal.getPortletTitle(
					portlet.getRootPortletId(), LocaleUtil.getDefault()),
				" - ", _group.getName(), " - ", companyName),
			_layoutSEOLinkManager.getFullPageTitle(
				_layout, portlet.getRootPortletId(), null, titleListMergeable,
				subtitleListMergeable, companyName, LocaleUtil.getDefault()));
	}

	@Test
	public void testGetFullPageTitleUsesTilesTitle() throws Exception {
		String tilesTitle = RandomTestUtil.randomString();
		String companyName = RandomTestUtil.randomString();

		Assert.assertEquals(
			StringBundler.concat(
				tilesTitle, " - ", _group.getName(), " - ", companyName),
			_layoutSEOLinkManager.getFullPageTitle(
				_layout, null, tilesTitle, null, null, companyName,
				LocaleUtil.getDefault()));
	}

	@Test
	public void testGetFullPageTitleUsesTilesTitleAndCompanyName()
		throws Exception {

		String tilesTitle = RandomTestUtil.randomString();
		String companyName = _group.getName();

		Assert.assertEquals(
			tilesTitle + " - " + companyName,
			_layoutSEOLinkManager.getFullPageTitle(
				_layout, null, tilesTitle, null, null, companyName,
				LocaleUtil.getDefault()));
	}

	@Test
	public void testGetFullPageUsesLayoutTitleAndCompanyName()
		throws Exception {

		String companyName = _group.getName();

		Assert.assertEquals(
			_layout.getTitle() + " - " + companyName,
			_layoutSEOLinkManager.getFullPageTitle(
				_layout, null, null, null, null, companyName,
				LocaleUtil.getDefault()));
	}

	@Test
	public void testGetPageTitleSuffixCompanyName() throws Exception {
		String companyName = _group.getName();

		Assert.assertEquals(
			companyName,
			_layoutSEOLinkManager.getPageTitleSuffix(_layout, companyName));
	}

	@Test
	public void testGetPageTitleSuffixGroupNameCompanyName() throws Exception {
		String companyName = RandomTestUtil.randomString();

		Assert.assertEquals(
			_group.getName() + " - " + companyName,
			_layoutSEOLinkManager.getPageTitleSuffix(_layout, companyName));
	}

	@Test
	public void testGetPageTitleUsesLayoutPrototypeTitle() throws Exception {
		String layoutPrototypeTitle = RandomTestUtil.randomString();

		LayoutPrototype layoutPrototype = LayoutTestUtil.addLayoutPrototype(
			layoutPrototypeTitle);

		_prototypeGroup = layoutPrototype.getGroup();

		_layout.setGroupId(_prototypeGroup.getGroupId());

		String actual = _layoutSEOLinkManager.getPageTitle(
			_layout, null, null, null, null, LocaleUtil.getDefault());

		Assert.assertEquals(layoutPrototypeTitle, actual);
	}

	@Test
	public void testGetPageTitleUsesPortletTitle() throws Exception {
		_modifyLayoutToSystemManage();

		Portlet portlet = _portletLocalService.getPortletById(
			TestPropsValues.getCompanyId(), PortletKeys.BLOGS_ADMIN);

		Assert.assertEquals(
			_portal.getPortletTitle(
				portlet.getRootPortletId(), LocaleUtil.getDefault()),
			_layoutSEOLinkManager.getPageTitle(
				_layout, portlet.getRootPortletId(), null, null, null,
				LocaleUtil.getDefault()));
	}

	private Group _addGroup() throws Exception {
		Group group = GroupTestUtil.addGroup();

		group.setName(RandomTestUtil.randomString());

		return _groupLocalService.updateGroup(group);
	}

	private Layout _addLayout() throws PortalException {
		Layout layout = _layoutLocalService.getLayout(
			TestPropsValues.getPlid());

		layout.setTitle(RandomTestUtil.randomString());

		return layout;
	}

	private void _modifyLayoutToSystemManage() {
		_layout.setSystem(true);
		_layout.setFriendlyURL("/manage");
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

	@Inject
	private Portal _portal;

	@Inject
	private PortletLocalService _portletLocalService;

	@DeleteAfterTestRun
	private Group _prototypeGroup;

}
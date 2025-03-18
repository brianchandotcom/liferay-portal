/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.style.book.util.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.style.book.model.StyleBookEntry;
import com.liferay.style.book.service.StyleBookEntryLocalService;
import com.liferay.style.book.util.DefaultStyleBookEntryUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Evan Thibodeau
 */
@RunWith(Arquillian.class)
public class DefaultStyleBookEntryUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		LayoutSet layoutSet = _group.getPublicLayoutSet();

		layoutSet.setThemeId(_THEME_ID);

		_layout = LayoutTestUtil.addTypeContentLayout(_group);
	}

	@Test
	public void testGetStyleBookEntryNameWithDefaultStyleBookEntry()
		throws Exception {

		_styleBookEntryLocalService.addStyleBookEntry(
			RandomTestUtil.randomString(), TestPropsValues.getUserId(),
			_group.getGroupId(), true, null, RandomTestUtil.randomString(),
			null, _THEME_ID, null);

		Assert.assertEquals(
			"styles-by-default",
			DefaultStyleBookEntryUtil.getStyleBookEntryName(
				_layout, null, null));
	}

	@Test
	public void testGetStyleBookEntryNameWithDefaultStyleBookEntryAndWithMasterLayoutWithoutStyleBookEntry()
		throws Exception {

		Layout masterLayoutBasedLayout = _getMasterLayoutBasedLayout();

		_styleBookEntryLocalService.addStyleBookEntry(
			RandomTestUtil.randomString(), TestPropsValues.getUserId(),
			_group.getGroupId(), true, null, RandomTestUtil.randomString(),
			null, _THEME_ID, null);

		Assert.assertEquals(
			"styles-by-default",
			DefaultStyleBookEntryUtil.getStyleBookEntryName(
				masterLayoutBasedLayout, null, null));
	}

	@Test
	public void testGetStyleBookEntryNameWithMasterLayoutWithoutStyleBookEntry()
		throws Exception {

		Assert.assertEquals(
			"styles-from-theme",
			DefaultStyleBookEntryUtil.getStyleBookEntryName(
				_getMasterLayoutBasedLayout(), null, null));
	}

	@Test
	public void testGetStyleBookEntryNameWithMasterLayoutWithStyleBookEntry()
		throws Exception {

		Layout masterLayoutBasedLayout = _getMasterLayoutBasedLayout();

		Layout masterLayout = _layoutLocalService.getLayout(
			masterLayoutBasedLayout.getMasterLayoutPlid());

		StyleBookEntry styleBookEntry =
			_styleBookEntryLocalService.addStyleBookEntry(
				RandomTestUtil.randomString(), TestPropsValues.getUserId(),
				_group.getGroupId(), false, null, RandomTestUtil.randomString(),
				null, _THEME_ID, null);

		masterLayout.setStyleBookEntryId(styleBookEntry.getStyleBookEntryId());

		_layoutLocalService.updateLayout(masterLayout);

		Assert.assertEquals(
			"styles-from-master",
			DefaultStyleBookEntryUtil.getStyleBookEntryName(
				masterLayoutBasedLayout, null, null));
	}

	@Test
	public void testGetStyleBookEntryNameWithoutDefaultStyleBookEntryAndWithMasterLayoutWithoutStyleBookEntry1()
		throws Exception {

		_testGetStyleBookEntryNameWithoutDefaultStyleBookEntryAndWithMasterLayoutWithoutStyleBookEntry(
			"styles-from-theme");
	}

	@FeatureFlags("LPD-30204")
	@Test
	public void testGetStyleBookEntryNameWithoutDefaultStyleBookEntryAndWithMasterLayoutWithoutStyleBookEntry2()
		throws Exception {

		_testGetStyleBookEntryNameWithoutDefaultStyleBookEntryAndWithMasterLayoutWithoutStyleBookEntry(
			"styles-from-x");
	}

	@Test
	public void testGetStyleBookEntryNameWithoutMasterLayout1() {
		Assert.assertEquals(
			"styles-from-theme",
			DefaultStyleBookEntryUtil.getStyleBookEntryName(
				_layout, null, null));
	}

	@FeatureFlags("LPD-30204")
	@Test
	public void testGetStyleBookEntryNameWithoutMasterLayout2() {
		Assert.assertEquals(
			"styles-from-x",
			DefaultStyleBookEntryUtil.getStyleBookEntryName(
				_layout, null, null));
	}

	@Test
	public void testGetStyleBookEntryNameWithStyleBookEntry() throws Exception {
		String name = RandomTestUtil.randomString();

		StyleBookEntry styleBookEntry =
			_styleBookEntryLocalService.addStyleBookEntry(
				RandomTestUtil.randomString(), TestPropsValues.getUserId(),
				_group.getGroupId(), false, null, name, null, _THEME_ID, null);

		Assert.assertEquals(
			name,
			DefaultStyleBookEntryUtil.getStyleBookEntryName(
				_layout, null, styleBookEntry));
	}

	private Layout _getMasterLayoutBasedLayout() throws Exception {
		Layout masterLayoutBasedLayout = LayoutTestUtil.addTypeContentLayout(
			_group);

		LayoutPageTemplateEntry masterLayoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
				null, TestPropsValues.getUserId(), _group.getGroupId(), 0, null,
				RandomTestUtil.randomString(),
				LayoutPageTemplateEntryTypeConstants.MASTER_LAYOUT, 0,
				WorkflowConstants.STATUS_APPROVED,
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		masterLayoutBasedLayout.setMasterLayoutPlid(
			masterLayoutPageTemplateEntry.getPlid());

		return _layoutLocalService.updateLayout(masterLayoutBasedLayout);
	}

	private void
			_testGetStyleBookEntryNameWithoutDefaultStyleBookEntryAndWithMasterLayoutWithoutStyleBookEntry(
				String expectedMessage)
		throws Exception {

		Layout layoutWithMasterLayout = _getLayoutBasedOnMasterLayout();

		_styleBookEntryLocalService.addStyleBookEntry(
			RandomTestUtil.randomString(), TestPropsValues.getUserId(),
			_group.getGroupId(), false, null, RandomTestUtil.randomString(),
			null, _THEME_ID, null);

		Assert.assertEquals(
			expectedMessage,
			DefaultStyleBookEntryUtil.getStyleBookEntryName(
				layoutWithMasterLayout, null, null));
	}

	private static final String _THEME_ID = RandomTestUtil.randomString();

	private Group _group;
	private Layout _layout;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Inject
	private StyleBookEntryLocalService _styleBookEntryLocalService;

}
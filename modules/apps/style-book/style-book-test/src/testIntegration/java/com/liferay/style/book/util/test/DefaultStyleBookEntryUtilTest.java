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

		StyleBookEntry styleBookEntry =
			_styleBookEntryLocalService.addStyleBookEntry(
				RandomTestUtil.randomString(), TestPropsValues.getUserId(),
				_group.getGroupId(), true, null, RandomTestUtil.randomString(),
				null, _THEME_ID, null);

		Assert.assertEquals(
			"styles-by-default",
			DefaultStyleBookEntryUtil.getStyleBookEntryName(
				_layout, null, null));

		_styleBookEntryLocalService.deleteStyleBookEntry(
			styleBookEntry.getStyleBookEntryId());
	}

	@Test
	public void testGetStyleBookEntryNameWithDefaultStyleBookEntryAndWithMasterLayoutWithoutStyleBookEntry()
		throws Exception {

		Layout layoutWithMasterLayout = _getLayoutWithMasterLayout();

		StyleBookEntry styleBookEntry =
			_styleBookEntryLocalService.addStyleBookEntry(
				RandomTestUtil.randomString(), TestPropsValues.getUserId(),
				_group.getGroupId(), true, null, RandomTestUtil.randomString(),
				null, _THEME_ID, null);

		Assert.assertEquals(
			"styles-by-default",
			DefaultStyleBookEntryUtil.getStyleBookEntryName(
				layoutWithMasterLayout, null, null));

		_styleBookEntryLocalService.deleteStyleBookEntry(
			styleBookEntry.getStyleBookEntryId());
	}

	@Test
	public void testGetStyleBookEntryNameWithMasterLayoutWithoutStyleBookEntry()
		throws Exception {

		Assert.assertEquals(
			"styles-from-theme",
			DefaultStyleBookEntryUtil.getStyleBookEntryName(
				_getLayoutWithMasterLayout(), null, null));
	}

	@Test
	public void testGetStyleBookEntryNameWithMasterLayoutWithStyleBookEntry()
		throws Exception {

		Layout layoutWithMasterLayout = _getLayoutWithMasterLayout();

		Layout masterPageTemplateLayout = _layoutLocalService.getLayout(
			layoutWithMasterLayout.getMasterLayoutPlid());

		StyleBookEntry styleBookEntry =
			_styleBookEntryLocalService.addStyleBookEntry(
				RandomTestUtil.randomString(), TestPropsValues.getUserId(),
				_group.getGroupId(), false, null, RandomTestUtil.randomString(),
				null, _THEME_ID, null);

		masterPageTemplateLayout.setStyleBookEntryId(
			styleBookEntry.getStyleBookEntryId());

		_layoutLocalService.updateLayout(masterPageTemplateLayout);

		Assert.assertEquals(
			"styles-from-master",
			DefaultStyleBookEntryUtil.getStyleBookEntryName(
				layoutWithMasterLayout, null, null));
	}

	@FeatureFlags(enable = false, value = "LPD-30204")
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

		_styleBookEntryLocalService.deleteStyleBookEntry(
			styleBookEntry.getStyleBookEntryId());
	}

	private Layout _getLayoutWithMasterLayout() throws Exception {
		Layout layoutWithMasterLayout = LayoutTestUtil.addTypeContentLayout(
			_group);

		LayoutPageTemplateEntry masterLayoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
				null, TestPropsValues.getUserId(), _group.getGroupId(), 0, null,
				RandomTestUtil.randomString(),
				LayoutPageTemplateEntryTypeConstants.MASTER_LAYOUT, 0,
				WorkflowConstants.STATUS_APPROVED,
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		layoutWithMasterLayout.setMasterLayoutPlid(
			masterLayoutPageTemplateEntry.getPlid());

		return _layoutLocalService.updateLayout(layoutWithMasterLayout);
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
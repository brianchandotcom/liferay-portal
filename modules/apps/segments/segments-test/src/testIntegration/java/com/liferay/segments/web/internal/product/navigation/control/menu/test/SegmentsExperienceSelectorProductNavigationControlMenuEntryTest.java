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

package com.liferay.segments.web.internal.product.navigation.control.menu.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.content.page.editor.constants.ContentPageEditorWebKeys;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.product.navigation.control.menu.ProductNavigationControlMenuEntry;
import com.liferay.segments.test.util.SegmentsTestUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
@RunWith(Arquillian.class)
public class SegmentsExperienceSelectorProductNavigationControlMenuEntryTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testIsShow() throws Exception {
		Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

		SegmentsTestUtil.addSegmentsExperience(
			_group.getGroupId(), _portal.getClassNameId(Layout.class),
			layout.getPlid());

		Assert.assertFalse(
			_productNavigationControlMenuEntry.isShow(
				_getMockHttpServletRequest(layout)));
	}

	@Test
	public void testIsShowInEditMode() throws Exception {
		Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

		MockHttpServletRequest mockHttpServletRequest =
			_getMockHttpServletRequest(layout);

		mockHttpServletRequest.addParameter("p_l_mode", Constants.EDIT);

		Assert.assertFalse(
			_productNavigationControlMenuEntry.isShow(mockHttpServletRequest));
	}

	@Test
	public void testIsShowWithContentLayoutUpdateable() throws Exception {
		Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

		layout.setLayoutPrototypeUuid(RandomTestUtil.randomString());
		layout.setSourcePrototypeLayoutUuid(RandomTestUtil.randomString());

		SegmentsTestUtil.addSegmentsExperience(
			_group.getGroupId(), _portal.getClassNameId(Layout.class),
			layout.getPlid());

		Assert.assertFalse(
			_productNavigationControlMenuEntry.isShow(
				_getMockHttpServletRequest(layout)));
	}

	@Test
	public void testIsShowWithFullPageApplication() throws Exception {
		Layout layout = _layoutLocalService.addLayout(
			TestPropsValues.getUserId(), _group.getGroupId(), false,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, "Full Page Application",
			null, null, "full_page_application", false,
			StringPool.SLASH +
				FriendlyURLNormalizerUtil.normalize("Full Page Application"),
			ServiceContextTestUtil.getServiceContext());

		Assert.assertFalse(
			_productNavigationControlMenuEntry.isShow(
				_getMockHttpServletRequest(layout)));
	}

	@Test
	public void testIsShowWithLayoutPageTemplateEntry() throws Exception {
		Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

		SegmentsTestUtil.addSegmentsExperience(
			_group.getGroupId(), _portal.getClassNameId(Layout.class),
			layout.getPlid());

		MockHttpServletRequest mockHttpServletRequest =
			_getMockHttpServletRequest(layout);

		mockHttpServletRequest.setAttribute(
			ContentPageEditorWebKeys.CLASS_NAME,
			LayoutPageTemplateEntry.class.getName());

		Assert.assertFalse(
			_productNavigationControlMenuEntry.isShow(mockHttpServletRequest));
	}

	@Test
	public void testIsShowWithOnlyOneSegmentExperience() throws Exception {
		Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

		Assert.assertFalse(
			_productNavigationControlMenuEntry.isShow(
				_getMockHttpServletRequest(layout)));
	}

	@Test
	public void testIsShowWithPortletLayout() throws Exception {
		Layout layout = LayoutTestUtil.addTypePortletLayout(_group);

		Assert.assertFalse(
			_productNavigationControlMenuEntry.isShow(
				_getMockHttpServletRequest(layout)));
	}

	private MockHttpServletRequest _getMockHttpServletRequest(Layout layout)
		throws Exception {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _getThemeDisplay(layout));

		return mockHttpServletRequest;
	}

	private ThemeDisplay _getThemeDisplay(Layout layout) throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(
			_companyLocalService.getCompany(TestPropsValues.getCompanyId()));
		themeDisplay.setLayout(layout);
		themeDisplay.setLayoutTypePortlet(
			(LayoutTypePortlet)layout.getLayoutType());
		themeDisplay.setUser(TestPropsValues.getUser());

		return themeDisplay;
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private Portal _portal;

	@Inject(
		filter = "component.name=com.liferay.segments.web.internal.product.navigation.control.menu.SegmentsExperienceSelectorProductNavigationControlMenuEntry"
	)
	private ProductNavigationControlMenuEntry
		_productNavigationControlMenuEntry;

}
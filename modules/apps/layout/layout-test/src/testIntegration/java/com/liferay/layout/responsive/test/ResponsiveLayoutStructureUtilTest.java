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

package com.liferay.layout.responsive.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkService;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.responsive.ResponsiveLayoutStructureUtil;
import com.liferay.layout.responsive.ViewportSize;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.layout.util.structure.CommonStylesUtil;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.layout.util.structure.StyledLayoutStructureItem;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.servlet.HttpMethods;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.segments.constants.SegmentsExperienceConstants;

import java.util.List;

import org.hamcrest.CoreMatchers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Pavel Savinov
 */
@RunWith(Arquillian.class)
public class ResponsiveLayoutStructureUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		_layout = LayoutTestUtil.addTypeContentLayout(_group);

		FragmentEntry fragmentEntry =
			_fragmentEntryLocalService.addFragmentEntry(
				TestPropsValues.getUserId(), _group.getGroupId(), 0,
				StringUtil.randomString(), StringUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), false, "{fieldSets: []}", null,
				0, FragmentConstants.TYPE_COMPONENT,
				WorkflowConstants.STATUS_APPROVED, serviceContext);

		_fragmentEntryLink = _fragmentEntryLinkService.addFragmentEntryLink(
			_group.getGroupId(), 0, fragmentEntry.getFragmentEntryId(),
			SegmentsExperienceConstants.ID_DEFAULT, _layout.getPlid(),
			fragmentEntry.getCss(), fragmentEntry.getHtml(),
			fragmentEntry.getJs(), fragmentEntry.getConfiguration(), null,
			StringPool.BLANK, 0, null, serviceContext);

		_themeDisplay = new ThemeDisplay();

		_themeDisplay.setCompany(
			_companyLocalService.getCompany(TestPropsValues.getCompanyId()));
		_themeDisplay.setLanguageId(
			LanguageUtil.getLanguageId(LocaleUtil.getDefault()));
		_themeDisplay.setLayout(_layout);
		_themeDisplay.setLayoutSet(_group.getPublicLayoutSet());
		_themeDisplay.setLayoutTypePortlet(
			(LayoutTypePortlet)_layout.getLayoutType());
		_themeDisplay.setLocale(LocaleUtil.US);
		_themeDisplay.setLookAndFeel(
			_layout.getTheme(), _layout.getColorScheme());
		_themeDisplay.setPermissionChecker(
			PermissionThreadLocal.getPermissionChecker());
		_themeDisplay.setPlid(_layout.getPlid());
		_themeDisplay.setRealUser(TestPropsValues.getUser());
		_themeDisplay.setScopeGroupId(_group.getGroupId());
		_themeDisplay.setSiteGroupId(_group.getGroupId());
		_themeDisplay.setUser(TestPropsValues.getUser());
	}

	@Test
	public void testResponsiveLayoutStructureClassesExist() throws Exception {
		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					_layout.getGroupId(), _layout.getPlid());

		LayoutStructure layoutStructure = LayoutStructure.of(
			layoutPageTemplateStructure.getData(
				SegmentsExperienceConstants.ID_DEFAULT));

		LayoutStructureItem rowStyledLayoutStructureItem =
			layoutStructure.addRowStyledLayoutStructureItem(
				layoutStructure.getMainItemId(), 0, 1);

		LayoutStructureItem columnLayoutStructureItem =
			layoutStructure.addColumnLayoutStructureItem(
				rowStyledLayoutStructureItem.getItemId(), 0);

		layoutStructure.addFragmentStyledLayoutStructureItem(
			_fragmentEntryLink.getFragmentEntryLinkId(),
			columnLayoutStructureItem.getItemId(), 0);

		_layoutPageTemplateStructureLocalService.
			updateLayoutPageTemplateStructureData(
				_layout.getGroupId(), _layout.getPlid(),
				SegmentsExperienceConstants.ID_DEFAULT,
				layoutStructure.toString());

		MockHttpServletRequest httpServletRequest =
			new MockHttpServletRequest();

		httpServletRequest.setAttribute(WebKeys.THEME_DISPLAY, _themeDisplay);

		httpServletRequest.setMethod(HttpMethods.GET);

		_layout.includeLayoutContent(
			httpServletRequest, new MockHttpServletResponse());

		String content = String.valueOf(
			(StringBundler)httpServletRequest.getAttribute(
				WebKeys.LAYOUT_CONTENT));

		for (ViewportSize viewportSize : ViewportSize.values()) {
			if (viewportSize.equals(ViewportSize.DESKTOP)) {
				continue;
			}

			String flexClassName =
				"flex" + viewportSize.getCssClassPrefix() + "row";

			Assert.assertThat(
				content, CoreMatchers.containsString(flexClassName));

			String colClassName =
				"col" + viewportSize.getCssClassPrefix() + "12";

			Assert.assertThat(
				content, CoreMatchers.containsString(colClassName));
		}
	}

	@Test
	public void testResponsiveLayoutStructureCommonStylesClassesExist()
		throws Exception {

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					_layout.getGroupId(), _layout.getPlid());

		LayoutStructure layoutStructure = LayoutStructure.of(
			layoutPageTemplateStructure.getData(
				SegmentsExperienceConstants.ID_DEFAULT));

		StyledLayoutStructureItem rowStyledLayoutStructureItem =
			(StyledLayoutStructureItem)
				layoutStructure.addRowStyledLayoutStructureItem(
					layoutStructure.getMainItemId(), 0, 1);

		for (ViewportSize viewportSize : ViewportSize.values()) {
			if (viewportSize.equals(ViewportSize.DESKTOP)) {
				continue;
			}

			JSONObject viewportStylesJSONObject = JSONUtil.put(
				viewportSize.getViewportSizeId(),
				JSONUtil.put("styles", _getResponsiveStylesJSONObject()));

			rowStyledLayoutStructureItem.updateItemConfig(
				viewportStylesJSONObject);
		}

		List<String> commonStyleNames =
			CommonStylesUtil.getAvailableStyleNames();

		for (String styleName : commonStyleNames) {
			if (!CommonStylesUtil.isResponsive(styleName)) {
				continue;
			}

			String actualCssClass =
				ResponsiveLayoutStructureUtil.getResponsiveCssClassValues(
					rowStyledLayoutStructureItem);

			for (ViewportSize viewportSize : ViewportSize.values()) {
				if (viewportSize.equals(ViewportSize.DESKTOP)) {
					continue;
				}

				String expectedCssClass = StringUtil.replace(
					CommonStylesUtil.getResponsiveTemplate(styleName),
					StringPool.OPEN_CURLY_BRACE, StringPool.CLOSE_CURLY_BRACE,
					HashMapBuilder.put(
						"value", "2"
					).put(
						"viewport", viewportSize.getCssClassPrefix()
					).build());

				Assert.assertThat(
					actualCssClass,
					CoreMatchers.containsString(expectedCssClass));
			}
		}
	}

	private JSONObject _getResponsiveStylesJSONObject() throws Exception {
		List<String> commonStyleNames =
			CommonStylesUtil.getAvailableStyleNames();

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		for (String styleName : commonStyleNames) {
			if (!CommonStylesUtil.isResponsive(styleName)) {
				continue;
			}

			jsonObject.put(styleName, "2");
		}

		return jsonObject;
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	private FragmentEntryLink _fragmentEntryLink;

	@Inject
	private FragmentEntryLinkService _fragmentEntryLinkService;

	@Inject
	private FragmentEntryLocalService _fragmentEntryLocalService;

	@DeleteAfterTestRun
	private Group _group;

	private Layout _layout;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	private ThemeDisplay _themeDisplay;

}
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

package com.liferay.layout.content.page.editor.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkService;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.layout.content.page.editor.constants.ContentPageEditorPortletKeys;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalServiceUtil;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.PortletConfigFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.servlet.HttpMethods;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.portlet.MockLiferayResourceRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayResourceResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.segments.constants.SegmentsExperienceConstants;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

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
 * @author Víctor Galán
 */
@RunWith(Arquillian.class)
@Sync
public class GetPagePreviewMVCResourceCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_serviceContext = new ServiceContext();

		_serviceContext.setScopeGroupId(_group.getGroupId());
		_serviceContext.setUserId(TestPropsValues.getUserId());

		ServiceContextThreadLocal.pushServiceContext(_serviceContext);

		_themeDisplay = new ThemeDisplay();

		_themeDisplay.setCompany(
			_companyLocalService.getCompany(TestPropsValues.getCompanyId()));
		_themeDisplay.setLanguageId(
			LanguageUtil.getLanguageId(LocaleUtil.getDefault()));
		_themeDisplay.setPermissionChecker(
			PermissionThreadLocal.getPermissionChecker());
		_themeDisplay.setRealUser(TestPropsValues.getUser());
		_themeDisplay.setScopeGroupId(_group.getGroupId());
		_themeDisplay.setSiteGroupId(_group.getGroupId());
		_themeDisplay.setUser(TestPropsValues.getUser());
	}

	@Test
	public void testGetPagePreviewAssetDisplayPage() throws Exception {
		_serviceContext.setAttribute(
			"layout.instanceable.allowed", Boolean.TRUE);

		_addLayout(LayoutConstants.TYPE_ASSET_DISPLAY, false);

		_assertContainsContent();
	}

	@Test
	public void testGetPagePreviewContentPage() throws Exception {
		_addLayout(LayoutConstants.TYPE_CONTENT, false);

		_assertContainsContent();
	}

	@Test
	public void testGetPagePreviewPageTemplate() throws Exception {
		_addLayout(LayoutConstants.TYPE_CONTENT, true);

		_assertContainsContent();
	}

	private void _addLayout(String type, boolean pageTemplate)
		throws Exception {

		Layout layout = _layoutLocalService.addLayout(
			TestPropsValues.getUserId(), _group.getGroupId(), pageTemplate,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			StringPool.BLANK, type, false, pageTemplate, StringPool.BLANK,
			_serviceContext);

		FragmentEntry fragmentEntry =
			_fragmentEntryLocalService.addFragmentEntry(
				TestPropsValues.getUserId(), _group.getGroupId(), 0,
				StringUtil.randomString(), StringUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), false, "{fieldSets: []}", null,
				0, FragmentConstants.TYPE_COMPONENT,
				WorkflowConstants.STATUS_APPROVED, _serviceContext);

		_fragmentEntryLink = _fragmentEntryLinkService.addFragmentEntryLink(
			_group.getGroupId(), 0, fragmentEntry.getFragmentEntryId(),
			SegmentsExperienceConstants.ID_DEFAULT, layout.getPlid(),
			fragmentEntry.getCss(), fragmentEntry.getHtml(),
			fragmentEntry.getJs(), fragmentEntry.getConfiguration(), null,
			StringPool.BLANK, 0, null, _serviceContext);

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			LayoutPageTemplateStructureLocalServiceUtil.
				fetchLayoutPageTemplateStructure(
					_group.getGroupId(), layout.getPlid());

		String data = layoutPageTemplateStructure.getData(
			SegmentsExperienceConstants.ID_DEFAULT);

		LayoutStructure layoutStructure = LayoutStructure.of(data);

		layoutStructure.addFragmentStyledLayoutStructureItem(
			_fragmentEntryLink.getFragmentEntryLinkId(),
			layoutStructure.getMainItemId(), 0);

		_layoutPageTemplateStructureLocalService.
			updateLayoutPageTemplateStructureData(
				_group.getGroupId(), layout.getPlid(),
				SegmentsExperienceConstants.ID_DEFAULT,
				layoutStructure.toString());

		_themeDisplay.setLayout(layout);
		_themeDisplay.setLayoutSet(layout.getLayoutSet());
		_themeDisplay.setLayoutTypePortlet(
			(LayoutTypePortlet)layout.getLayoutType());
		_themeDisplay.setLookAndFeel(
			layout.getTheme(), layout.getColorScheme());
		_themeDisplay.setPlid(layout.getPlid());
	}

	private void _assertContainsContent() throws Exception {
		MockLiferayResourceRequest mockLiferayResourceRequest =
			new MockLiferayResourceRequest();

		Portlet portlet = _portletLocalService.getPortletById(
			ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET);

		mockLiferayResourceRequest.setAttribute(
			JavaConstants.JAVAX_PORTLET_CONFIG,
			PortletConfigFactoryUtil.create(portlet, null));

		mockLiferayResourceRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _themeDisplay);

		MockHttpServletRequest httpServletRequest =
			(MockHttpServletRequest)
				mockLiferayResourceRequest.getHttpServletRequest();

		httpServletRequest.setAttribute(WebKeys.THEME_DISPLAY, _themeDisplay);
		httpServletRequest.setMethod(HttpMethods.GET);

		_serviceContext.setRequest(httpServletRequest);

		MockLiferayResourceResponse mockLiferayResourceResponse =
			new MockLiferayResourceResponse();

		ReflectionTestUtil.invoke(
			_mvcResourceCommand, "doServeResource",
			new Class<?>[] {ResourceRequest.class, ResourceResponse.class},
			mockLiferayResourceRequest, mockLiferayResourceResponse);

		MockHttpServletResponse mockHttpServletResponse =
			(MockHttpServletResponse)
				mockLiferayResourceResponse.getHttpServletResponse();

		String content = mockHttpServletResponse.getContentAsString();

		Assert.assertThat(
			content, CoreMatchers.containsString(_fragmentEntryLink.getCss()));
		Assert.assertThat(
			content, CoreMatchers.containsString(_fragmentEntryLink.getHtml()));
		Assert.assertThat(
			content, CoreMatchers.containsString(_fragmentEntryLink.getJs()));
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

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Inject(
		filter = "mvc.command.name=/layout_content_page_editor/get_page_preview"
	)
	private MVCResourceCommand _mvcResourceCommand;

	@Inject
	private PortletLocalService _portletLocalService;

	private ServiceContext _serviceContext;
	private ThemeDisplay _themeDisplay;

}
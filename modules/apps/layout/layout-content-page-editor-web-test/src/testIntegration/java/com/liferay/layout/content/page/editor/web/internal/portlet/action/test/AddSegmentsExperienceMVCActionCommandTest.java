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
import com.liferay.layout.content.page.editor.web.internal.util.SegmentsExperienceTestUtil;
import com.liferay.layout.page.template.importer.LayoutPageTemplatesImporter;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.segments.constants.SegmentsEntryConstants;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.service.SegmentsExperienceLocalService;
import com.liferay.segments.service.SegmentsExperienceService;
import com.liferay.segments.test.util.SegmentsTestUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Sarai Díaz
 */
@RunWith(Arquillian.class)
public class AddSegmentsExperienceMVCActionCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_company = _companyLocalService.getCompany(_group.getCompanyId());

		_layout = LayoutTestUtil.addTypeContentPublishedLayout(
			_group, "Test layout", WorkflowConstants.STATUS_APPROVED);

		_draftLayout = _layout.fetchDraftLayout();

		ServiceContextThreadLocal.pushServiceContext(new ServiceContext());
	}

	@After
	public void tearDown() {
		ServiceContextThreadLocal.popServiceContext();
	}

	@Test
	public void testAddSegmentsExperiment() throws Exception {
		String name = RandomTestUtil.randomString(10);

		SegmentsEntry segmentsEntry = SegmentsTestUtil.addSegmentsEntry(
			_group.getGroupId());

		SegmentsExperienceTestUtil.addSegmentsExperienceData(
			"fragment_composition_with_a_card.json", _draftLayout,
			_layoutPageTemplatesImporter, SegmentsEntryConstants.ID_DEFAULT);

		SegmentsExperienceTestUtil.addSegmentsExperienceData(
			"fragment_composition_with_a_button.json", _layout,
			_layoutPageTemplatesImporter, SegmentsEntryConstants.ID_DEFAULT);

		MockLiferayPortletActionRequest mockActionRequest =
			_getMockLiferayPortletActionRequest(
				name, segmentsEntry.getSegmentsEntryId());

		JSONObject responseJSONObject = ReflectionTestUtil.invoke(
			_mvcActionCommand, "addSegmentsExperience",
			new Class<?>[] {ActionRequest.class, ActionResponse.class},
			mockActionRequest, new MockLiferayPortletActionResponse());

		JSONObject segmentsExperienceJSONObject =
			responseJSONObject.getJSONObject("segmentsExperience");

		Assert.assertEquals(name, segmentsExperienceJSONObject.get("name"));
		Assert.assertEquals(
			segmentsEntry.getSegmentsEntryId(),
			GetterUtil.getLong(
				segmentsExperienceJSONObject.get("segmentsEntryId")));

		long segmentsExperienceId = GetterUtil.getLong(
			segmentsExperienceJSONObject.get("segmentsExperienceId"));

		SegmentsExperience segmentsExperience =
			_segmentsExperienceService.getSegmentsExperience(
				segmentsExperienceId);

		Assert.assertTrue(segmentsExperience.isActive());
		Assert.assertEquals(
			name, segmentsExperience.getName(LocaleUtil.getDefault()));
		Assert.assertEquals(
			segmentsEntry.getSegmentsEntryId(),
			segmentsExperience.getSegmentsEntryId());
		Assert.assertEquals(
			segmentsExperienceId, segmentsExperience.getSegmentsExperienceId());

		long defaultSegmentsExperienceId =
			_segmentsExperienceLocalService.fetchDefaultSegmentsExperienceId(
				_draftLayout.getPlid());

		SegmentsExperienceTestUtil.checkSegmentsExperiences(
			_draftLayout, defaultSegmentsExperienceId, segmentsExperienceId);

		SegmentsExperienceTestUtil.checkSegmentsExperiences(
			_layout, defaultSegmentsExperienceId, segmentsExperienceId);
	}

	private MockHttpServletRequest _getMockHttpServletRequest(
		ThemeDisplay themeDisplay) {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		return mockHttpServletRequest;
	}

	private MockLiferayPortletActionRequest _getMockLiferayPortletActionRequest(
			String name, long segmentsEntryId)
		throws Exception {

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			new MockLiferayPortletActionRequest();

		mockLiferayPortletActionRequest.addParameter("name", name);
		mockLiferayPortletActionRequest.addParameter(
			"segmentsEntryId", String.valueOf(segmentsEntryId));
		mockLiferayPortletActionRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _getThemeDisplay());

		return mockLiferayPortletActionRequest;
	}

	private ThemeDisplay _getThemeDisplay() throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(_company);
		themeDisplay.setLayout(_draftLayout);
		themeDisplay.setLayoutSet(_draftLayout.getLayoutSet());
		themeDisplay.setLocale(LocaleUtil.getDefault());
		themeDisplay.setLookAndFeel(
			_draftLayout.getTheme(), _draftLayout.getColorScheme());
		themeDisplay.setPermissionChecker(
			PermissionThreadLocal.getPermissionChecker());
		themeDisplay.setPlid(_draftLayout.getPlid());
		themeDisplay.setRealUser(TestPropsValues.getUser());
		themeDisplay.setRequest(_getMockHttpServletRequest(themeDisplay));
		themeDisplay.setScopeGroupId(_group.getGroupId());
		themeDisplay.setServerName("localhost");
		themeDisplay.setServerPort(8080);
		themeDisplay.setSiteGroupId(_group.getGroupId());
		themeDisplay.setUser(TestPropsValues.getUser());

		return themeDisplay;
	}

	private Company _company;

	@Inject
	private CompanyLocalService _companyLocalService;

	private Layout _draftLayout;

	@DeleteAfterTestRun
	private Group _group;

	private Layout _layout;

	@Inject
	private LayoutPageTemplatesImporter _layoutPageTemplatesImporter;

	@Inject(
		filter = "mvc.command.name=/layout_content_page_editor/add_segments_experience"
	)
	private MVCActionCommand _mvcActionCommand;

	@Inject
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

	@Inject
	private SegmentsExperienceService _segmentsExperienceService;

}
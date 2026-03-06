/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.content.page.editor.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.servlet.PortletServlet;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.service.SegmentsExperienceService;
import com.liferay.segments.test.util.SegmentsTestUtil;

import jakarta.servlet.http.HttpServletRequest;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Georgel Pop
 */
@RunWith(Arquillian.class)
public class UpdateSegmentsExperienceMVCActionCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = _groupLocalService.getGroup(TestPropsValues.getGroupId());

		_layout = LayoutTestUtil.addTypeContentLayout(_group);

		_draftLayout = _layout.fetchDraftLayout();

		ServiceContextThreadLocal.pushServiceContext(
			ServiceContextTestUtil.getServiceContext(
				_group, TestPropsValues.getUserId()));
	}

	@After
	public void tearDown() {
		ServiceContextThreadLocal.popServiceContext();
	}

	@Test
	@TestInfo("LPD-73850")
	public void testUpdateSegmentsExperience() throws Exception {
		SegmentsEntry segmentsEntry1 = SegmentsTestUtil.addSegmentsEntry(
			_group.getGroupId());

		SegmentsExperience segmentsExperience =
			_segmentsExperienceService.addSegmentsExperience(
				null, _group.getGroupId(),
				segmentsEntry1.getExternalReferenceCode(), null,
				_draftLayout.getPlid(), RandomTestUtil.randomLocaleStringMap(),
				true, new UnicodeProperties(),
				ServiceContextTestUtil.getServiceContext(
					_group, TestPropsValues.getUserId()));

		String newName = RandomTestUtil.randomString(10);

		Group companyGroup = _groupLocalService.getCompanyGroup(
			TestPropsValues.getCompanyId());

		SegmentsEntry segmentsEntry2 = SegmentsTestUtil.addSegmentsEntry(
			companyGroup.getGroupId());

		JSONObject responseJSONObject = _updateSegmentsExperience(
			segmentsExperience.getSegmentsExperienceId(), newName,
			segmentsEntry2.getExternalReferenceCode(),
			companyGroup.getExternalReferenceCode());

		JSONObject segmentsExperienceJSONObject = _jsonFactory.createJSONObject(
			responseJSONObject.getString("segmentsExperience"));

		segmentsExperience = _segmentsExperienceService.getSegmentsExperience(
			segmentsExperienceJSONObject.getLong("segmentsExperienceId"));

		Assert.assertEquals(
			newName, segmentsExperience.getName(LocaleUtil.getDefault()));
		Assert.assertEquals(
			segmentsEntry2.getExternalReferenceCode(),
			segmentsExperience.getSegmentsEntryERC());
		Assert.assertEquals(
			companyGroup.getExternalReferenceCode(),
			segmentsExperience.getSegmentsEntryScopeERC());
	}

	private MockLiferayPortletActionRequest _getMockLiferayPortletActionRequest(
			long segmentsExperienceId, String name, String segmentsEntryERC,
			String segmentsEntryScopeERC)
		throws Exception {

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			new MockLiferayPortletActionRequest();

		mockLiferayPortletActionRequest.addParameter(
			"groupId", String.valueOf(_group.getGroupId()));

		if (name != null) {
			mockLiferayPortletActionRequest.addParameter("name", name);
		}

		mockLiferayPortletActionRequest.addParameter(
			"plid", String.valueOf(_draftLayout.getPlid()));

		if (segmentsEntryERC != null) {
			mockLiferayPortletActionRequest.addParameter(
				"segmentsEntryERC", segmentsEntryERC);
		}

		if (segmentsEntryScopeERC != null) {
			mockLiferayPortletActionRequest.addParameter(
				"segmentsEntryScopeERC", segmentsEntryScopeERC);
		}

		mockLiferayPortletActionRequest.addParameter(
			"segmentsExperienceId", String.valueOf(segmentsExperienceId));
		mockLiferayPortletActionRequest.setAttribute(
			JavaConstants.JAKARTA_PORTLET_CONFIG, null);

		HttpServletRequest httpServletRequest = new MockHttpServletRequest();

		ThemeDisplay themeDisplay = _getThemeDisplay();

		httpServletRequest.setAttribute(WebKeys.THEME_DISPLAY, themeDisplay);

		httpServletRequest.setAttribute(
			WebKeys.USER_ID, TestPropsValues.getUserId());

		mockLiferayPortletActionRequest.setAttribute(
			PortletServlet.PORTLET_SERVLET_REQUEST, httpServletRequest);

		mockLiferayPortletActionRequest.setAttribute(
			WebKeys.LAYOUT, _draftLayout);
		mockLiferayPortletActionRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		return mockLiferayPortletActionRequest;
	}

	private ThemeDisplay _getThemeDisplay() throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(
			_companyLocalService.getCompany(_group.getCompanyId()));
		themeDisplay.setLayout(_draftLayout);
		themeDisplay.setLayoutSet(_draftLayout.getLayoutSet());
		themeDisplay.setLayoutTypePortlet(
			(LayoutTypePortlet)_draftLayout.getLayoutType());
		themeDisplay.setLocale(LocaleUtil.getDefault());
		themeDisplay.setPermissionChecker(
			PermissionThreadLocal.getPermissionChecker());
		themeDisplay.setPlid(_draftLayout.getPlid());
		themeDisplay.setRequest(new MockHttpServletRequest());
		themeDisplay.setScopeGroupId(_group.getGroupId());
		themeDisplay.setServerName("localhost");
		themeDisplay.setServerPort(8080);
		themeDisplay.setSiteGroupId(_group.getGroupId());
		themeDisplay.setUser(TestPropsValues.getUser());

		return themeDisplay;
	}

	private JSONObject _updateSegmentsExperience(
			long segmentsExperienceId, String name, String segmentsEntryERC,
			String segmentsEntryScopeERC)
		throws Exception {

		MockLiferayPortletActionResponse mockLiferayPortletActionResponse =
			new MockLiferayPortletActionResponse();

		_mvcActionCommand.processAction(
			_getMockLiferayPortletActionRequest(
				segmentsExperienceId, name, segmentsEntryERC,
				segmentsEntryScopeERC),
			mockLiferayPortletActionResponse);

		MockHttpServletResponse mockHttpServletResponse =
			(MockHttpServletResponse)
				mockLiferayPortletActionResponse.getHttpServletResponse();

		return _jsonFactory.createJSONObject(
			mockHttpServletResponse.getContentAsString());
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	private Layout _draftLayout;
	private Group _group;

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private JSONFactory _jsonFactory;

	private Layout _layout;

	@Inject(
		filter = "mvc.command.name=/layout_content_page_editor/update_segments_experience"
	)
	private MVCActionCommand _mvcActionCommand;

	@Inject
	private SegmentsExperienceService _segmentsExperienceService;

}
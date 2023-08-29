/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.reports.web.internal.struts.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.reports.web.internal.util.LayoutReportsTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.service.SegmentsExperienceLocalService;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Mikel Lorza
 */
@FeatureFlags("LPS-187284")
@RunWith(Arquillian.class)
public class GetLayoutReportsDataStrutsActionTest {

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
	public void testGetLayoutReportsDataStrutsActionWithContentLayout()
		throws Exception {

		Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

		JSONObject jsonObject = _serveResource(layout);

		JSONObject segmentsExperienceSelectorDataJSONObject =
			jsonObject.getJSONObject("segmentsExperienceSelectorData");

		Assert.assertNotNull(segmentsExperienceSelectorDataJSONObject);

		JSONObject selectedSegmentsExperienceJSONObject =
			segmentsExperienceSelectorDataJSONObject.getJSONObject(
				"selectedSegmentsExperience");

		_assertSelectedSegmentsExperienceJSONObject(
			layout, selectedSegmentsExperienceJSONObject);

		JSONArray segmentsExperiencesJSONArray =
			segmentsExperienceSelectorDataJSONObject.getJSONArray(
				"segmentsExperiences");

		Assert.assertNotNull(segmentsExperiencesJSONArray);
		Assert.assertEquals(1, segmentsExperiencesJSONArray.length());

		JSONArray tabsDataJSONArray = jsonObject.getJSONArray("tabsData");

		Assert.assertNotNull(tabsDataJSONArray);
		Assert.assertEquals(2, tabsDataJSONArray.length());
		_assertRenderTimesTabJSONObject(
			layout, tabsDataJSONArray.getJSONObject(0));
		_assertGooglePageSpeedInsightsTabJSONObject(
			layout, tabsDataJSONArray.getJSONObject(1));
	}

	@Test
	public void testGetLayoutReportsDataStrutsActionWithContentLayoutAndGooglePageSpeedDisabled()
		throws Exception {

		LayoutReportsTestUtil.
			withLayoutReportsGooglePageSpeedGroupConfiguration(
				RandomTestUtil.randomString(), false, _group.getGroupId(),
				() -> {
					Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

					JSONObject jsonObject = _serveResource(layout);

					JSONArray tabsDataJSONArray = jsonObject.getJSONArray(
						"tabsData");

					Assert.assertNotNull(tabsDataJSONArray);
					Assert.assertEquals(1, tabsDataJSONArray.length());
					_assertRenderTimesTabJSONObject(
						layout, tabsDataJSONArray.getJSONObject(0));
				});
	}

	@Test
	public void testGetLayoutReportsDataStrutsActionWithContentLayoutAndSomeExperiences()
		throws Exception {

		Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

		SegmentsExperience segmentsExperience =
			_segmentsExperienceLocalService.addSegmentsExperience(
				TestPropsValues.getUserId(), _group.getGroupId(),
				RandomTestUtil.randomLong(), layout.getPlid(),
				RandomTestUtil.randomLocaleStringMap(), true,
				new UnicodeProperties(true),
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		JSONObject jsonObject = _serveResource(layout);

		JSONObject segmentsExperienceSelectorDataJSONObject =
			jsonObject.getJSONObject("segmentsExperienceSelectorData");

		Assert.assertNotNull(segmentsExperienceSelectorDataJSONObject);

		JSONObject selectedSegmentsExperienceJSONObject =
			segmentsExperienceSelectorDataJSONObject.getJSONObject(
				"selectedSegmentsExperience");

		_assertSelectedSegmentsExperienceJSONObject(
			layout, selectedSegmentsExperienceJSONObject);

		JSONArray segmentsExperiencesJSONArray =
			segmentsExperienceSelectorDataJSONObject.getJSONArray(
				"segmentsExperiences");

		Assert.assertNotNull(segmentsExperiencesJSONArray);

		Assert.assertEquals(2, segmentsExperiencesJSONArray.length());

		JSONObject segmentsExperienceJSONObject =
			segmentsExperiencesJSONArray.getJSONObject(1);

		Assert.assertNotNull(segmentsExperienceJSONObject);

		Assert.assertFalse(segmentsExperienceJSONObject.getBoolean("active"));
		Assert.assertEquals(
			segmentsExperience.getSegmentsEntryId(),
			segmentsExperienceJSONObject.getLong("segmentsEntryId"));
		Assert.assertEquals(
			"Anyone",
			segmentsExperienceJSONObject.getString("segmentsEntryName"));
		Assert.assertEquals(
			segmentsExperience.getSegmentsExperienceId(),
			segmentsExperienceJSONObject.getLong("segmentsExperienceId"));
		Assert.assertEquals(
			segmentsExperience.getName(_group.getDefaultLanguageId()),
			segmentsExperienceJSONObject.getString("segmentsExperienceName"));
		Assert.assertEquals(
			"Inactive", segmentsExperienceJSONObject.getString("statusLabel"));
	}

	@Test
	public void testGetLayoutReportsDataStrutsActionWithPortletLayout()
		throws Exception {

		Layout layout = LayoutTestUtil.addTypePortletLayout(_group);

		JSONObject jsonObject = _serveResource(layout);

		JSONObject segmentsExperienceSelectorDataJSONObject =
			jsonObject.getJSONObject("segmentsExperienceSelectorData");

		Assert.assertNotNull(segmentsExperienceSelectorDataJSONObject);
		Assert.assertEquals(
			0,
			segmentsExperienceSelectorDataJSONObject.getJSONArray(
				"segmentsExperiences"
			).length());

		JSONArray tabsDataJSONArray = jsonObject.getJSONArray("tabsData");

		Assert.assertNotNull(tabsDataJSONArray);
		Assert.assertEquals(1, tabsDataJSONArray.length());
		_assertGooglePageSpeedInsightsTabJSONObject(
			layout, tabsDataJSONArray.getJSONObject(0));
	}

	@Test
	public void testGetLayoutReportsDataStrutsActionWithPortletLayoutAndGooglePageSpeedDisabled()
		throws Exception {

		LayoutReportsTestUtil.
			withLayoutReportsGooglePageSpeedGroupConfiguration(
				RandomTestUtil.randomString(), false, _group.getGroupId(),
				() -> {
					Layout layout = LayoutTestUtil.addTypePortletLayout(_group);

					JSONObject jsonObject = _serveResource(layout);

					JSONArray tabsDataJSONArray = jsonObject.getJSONArray(
						"tabsData");

					Assert.assertNotNull(tabsDataJSONArray);
					Assert.assertEquals(0, tabsDataJSONArray.length());
				});
	}

	private void _assertGooglePageSpeedInsightsTabJSONObject(
		Layout layout, JSONObject jsonObject) {

		Assert.assertNotNull(jsonObject);
		Assert.assertEquals("page-speed-insights", jsonObject.getString("id"));
		Assert.assertEquals("PageSpeed Insights", jsonObject.getString("name"));
		Assert.assertEquals(
			"http://localhost:8080/layout_reports" +
				"/get_google_page_speed_data?p_l_id=" +
					String.valueOf(layout.getPlid()),
			jsonObject.getString("url"));
	}

	private void _assertRenderTimesTabJSONObject(
		Layout layout, JSONObject jsonObject) {

		Assert.assertEquals("performance", jsonObject.getString("id"));
		Assert.assertEquals("Performance", jsonObject.getString("name"));
		Assert.assertEquals(
			"http://localhost:8080/layout_reports" +
				"/get_layout_item_data?p_l_id=" +
					String.valueOf(layout.getPlid()),
			jsonObject.getString("url"));
	}

	private void _assertSelectedSegmentsExperienceJSONObject(
		Layout layout, JSONObject selectedSegmentsExperienceJSONObject) {

		Assert.assertNotNull(selectedSegmentsExperienceJSONObject);
		Assert.assertTrue(
			selectedSegmentsExperienceJSONObject.getBoolean("active"));
		Assert.assertEquals(
			0, selectedSegmentsExperienceJSONObject.getLong("segmentsEntryId"));
		Assert.assertEquals(
			"Anyone",
			selectedSegmentsExperienceJSONObject.getString(
				"segmentsEntryName"));
		Assert.assertEquals(
			_segmentsExperienceLocalService.fetchDefaultSegmentsExperienceId(
				layout.getPlid()),
			selectedSegmentsExperienceJSONObject.getLong(
				"segmentsExperienceId"));
		Assert.assertEquals(
			"Default",
			selectedSegmentsExperienceJSONObject.getString(
				"segmentsExperienceName"));
		Assert.assertEquals(
			"Active",
			selectedSegmentsExperienceJSONObject.getString("statusLabel"));
	}

	private JSONObject _serveResource(Layout layout) throws Exception {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setLayout(layout);
		themeDisplay.setLayoutSet(layout.getLayoutSet());
		themeDisplay.setLocale(
			LocaleUtil.fromLanguageId(_group.getDefaultLanguageId()));
		themeDisplay.setPlid(layout.getPlid());
		themeDisplay.setPortalURL("http://localhost:8080");
		themeDisplay.setScopeGroupId(_group.getGroupId());
		themeDisplay.setSiteGroupId(_group.getGroupId());

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		_getLayoutReportsDataStrutsAction.execute(
			mockHttpServletRequest, mockHttpServletResponse);

		return JSONFactoryUtil.createJSONObject(
			mockHttpServletResponse.getContentAsString());
	}

	@Inject(
		filter = "component.name=com.liferay.layout.reports.web.internal.struts.GetLayoutReportsDataStrutsAction"
	)
	private StrutsAction _getLayoutReportsDataStrutsAction;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

}
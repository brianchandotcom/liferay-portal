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

package com.liferay.segments.web.internal.display.context.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.product.navigation.control.menu.ProductNavigationControlMenuEntry;
import com.liferay.segments.constants.SegmentsEntryConstants;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.service.SegmentsExperienceLocalService;
import com.liferay.segments.test.util.SegmentsTestUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Eudaldo Alonso
 */
@RunWith(Arquillian.class)
public class SegmentsExperienceSelectorDisplayContextTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_layout = LayoutTestUtil.addTypeContentLayout(_group);
	}

	@Test
	public void testActiveSegmentsExperiences() throws Exception {
		SegmentsExperience segmentsExperience1 =
			SegmentsTestUtil.addSegmentsExperience(
				_group.getGroupId(), SegmentsEntryConstants.ID_DEFAULT,
				_portal.getClassNameId(Layout.class), _layout.getPlid());
		SegmentsExperience segmentsExperience2 =
			SegmentsTestUtil.addSegmentsExperience(
				_group.getGroupId(), _portal.getClassNameId(Layout.class),
				_layout.getPlid());
		SegmentsExperience segmentsExperience3 =
			SegmentsTestUtil.addSegmentsExperience(
				_group.getGroupId(), _portal.getClassNameId(Layout.class),
				_layout.getPlid());

		JSONArray segmentsExperiencesJSONArray =
			_getSegmentsExperiencesJSONArray();

		JSONObject segmentsExperiencesJSONObject0 =
			segmentsExperiencesJSONArray.getJSONObject(0);

		Assert.assertTrue(segmentsExperiencesJSONObject0.getBoolean("active"));
		Assert.assertEquals(
			_segmentsExperienceLocalService.fetchDefaultSegmentsExperienceId(
				_layout.getPlid()),
			segmentsExperiencesJSONObject0.getLong("segmentsExperienceId"));

		JSONObject segmentsExperiencesJSONObject1 =
			segmentsExperiencesJSONArray.getJSONObject(1);

		Assert.assertFalse(segmentsExperiencesJSONObject1.getBoolean("active"));
		Assert.assertEquals(
			segmentsExperience1.getSegmentsExperienceId(),
			segmentsExperiencesJSONObject1.getLong("segmentsExperienceId"));

		JSONObject segmentsExperiencesJSONObject2 =
			segmentsExperiencesJSONArray.getJSONObject(2);

		Assert.assertFalse(segmentsExperiencesJSONObject2.getBoolean("active"));
		Assert.assertEquals(
			segmentsExperience2.getSegmentsExperienceId(),
			segmentsExperiencesJSONObject2.getLong("segmentsExperienceId"));

		JSONObject segmentsExperiencesJSONObject3 =
			segmentsExperiencesJSONArray.getJSONObject(3);

		Assert.assertFalse(segmentsExperiencesJSONObject3.getBoolean("active"));
		Assert.assertEquals(
			segmentsExperience3.getSegmentsExperienceId(),
			segmentsExperiencesJSONObject3.getLong("segmentsExperienceId"));

		_segmentsExperienceLocalService.updateSegmentsExperiencePriority(
			segmentsExperience1.getSegmentsExperienceId(), 0);
		_segmentsExperienceLocalService.updateSegmentsExperiencePriority(
			segmentsExperience2.getSegmentsExperienceId(), 0);
		_segmentsExperienceLocalService.updateSegmentsExperiencePriority(
			segmentsExperience2.getSegmentsExperienceId(), 2);

		segmentsExperiencesJSONArray = _getSegmentsExperiencesJSONArray();

		segmentsExperiencesJSONObject0 =
			segmentsExperiencesJSONArray.getJSONObject(0);

		Assert.assertTrue(segmentsExperiencesJSONObject0.getBoolean("active"));
		Assert.assertEquals(
			segmentsExperience2.getSegmentsExperienceId(),
			segmentsExperiencesJSONObject0.getLong("segmentsExperienceId"));

		segmentsExperiencesJSONObject1 =
			segmentsExperiencesJSONArray.getJSONObject(1);

		Assert.assertTrue(segmentsExperiencesJSONObject1.getBoolean("active"));
		Assert.assertEquals(
			segmentsExperience1.getSegmentsExperienceId(),
			segmentsExperiencesJSONObject1.getLong("segmentsExperienceId"));

		segmentsExperiencesJSONObject2 =
			segmentsExperiencesJSONArray.getJSONObject(2);

		Assert.assertFalse(segmentsExperiencesJSONObject2.getBoolean("active"));
		Assert.assertEquals(
			_segmentsExperienceLocalService.fetchDefaultSegmentsExperienceId(
				_layout.getPlid()),
			segmentsExperiencesJSONObject2.getLong("segmentsExperienceId"));

		segmentsExperiencesJSONObject3 =
			segmentsExperiencesJSONArray.getJSONObject(3);

		Assert.assertFalse(segmentsExperiencesJSONObject3.getBoolean("active"));
		Assert.assertEquals(
			segmentsExperience3.getSegmentsExperienceId(),
			segmentsExperiencesJSONObject3.getLong("segmentsExperienceId"));
	}

	private MockHttpServletRequest _getMockHttpServletRequest()
		throws Exception {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(
			WebKeys.CURRENT_URL, "http://www.liferay.com");
		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _getThemeDisplay());

		return mockHttpServletRequest;
	}

	private JSONArray _getSegmentsExperiencesJSONArray() throws Exception {
		MockHttpServletRequest mockHttpServletRequest =
			_getMockHttpServletRequest();

		_productNavigationControlMenuEntry.includeIcon(
			mockHttpServletRequest, new MockHttpServletResponse());

		Object segmentsExperienceSelectorDisplayContext =
			mockHttpServletRequest.getAttribute(
				"com.liferay.segments.web.internal.display.context." +
					"SegmentsExperienceSelectorDisplayContext");

		return ReflectionTestUtil.invoke(
			segmentsExperienceSelectorDisplayContext,
			"getSegmentsExperiencesJSONArray", new Class<?>[0], null);
	}

	private ThemeDisplay _getThemeDisplay() throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setLocale(LocaleUtil.getSiteDefault());
		themeDisplay.setPlid(_layout.getPlid());
		themeDisplay.setScopeGroupId(_group.getGroupId());
		themeDisplay.setUser(TestPropsValues.getUser());

		return themeDisplay;
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	@DeleteAfterTestRun
	private Group _group;

	private Layout _layout;

	@Inject
	private Portal _portal;

	@Inject(
		filter = "component.name=com.liferay.segments.web.internal.product.navigation.control.menu.SegmentsExperienceSelectorProductNavigationControlMenuEntry"
	)
	private ProductNavigationControlMenuEntry
		_productNavigationControlMenuEntry;

	@Inject
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

}
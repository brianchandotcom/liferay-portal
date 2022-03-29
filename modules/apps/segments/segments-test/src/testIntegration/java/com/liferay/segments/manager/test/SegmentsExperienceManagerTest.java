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

package com.liferay.segments.manager.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.segments.constants.SegmentsWebKeys;
import com.liferay.segments.manager.SegmentsExperienceManager;
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

/**
 * @author Eudaldo Alonso
 */
@RunWith(Arquillian.class)
public class SegmentsExperienceManagerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_layout = LayoutTestUtil.addTypeContentLayout(_group);

		_segmentsExperienceManager = new SegmentsExperienceManager(
			_segmentsExperienceLocalService);
	}

	@Test
	public void testGetSegmentsExperienceIdWithoutSegmentsExperienceIds() {
		Assert.assertEquals(
			_segmentsExperienceLocalService.fetchDefaultSegmentsExperienceId(
				_layout.getPlid()),
			_segmentsExperienceManager.getSegmentsExperienceId(
				_getMockHttpServletRequest()));
	}

	@Test
	public void testGetSegmentsExperienceIdWithSegmentsExperienceIds()
		throws PortalException {

		MockHttpServletRequest mockHttpServletRequest =
			_getMockHttpServletRequest();

		SegmentsExperience segmentsExperience =
			SegmentsTestUtil.addSegmentsExperience(
				_group.getGroupId(),
				PortalUtil.getClassNameId(Layout.class.getName()),
				_layout.getPlid());

		mockHttpServletRequest.setAttribute(
			SegmentsWebKeys.SEGMENTS_EXPERIENCE_IDS,
			new long[] {segmentsExperience.getSegmentsExperienceId()});

		Assert.assertEquals(
			segmentsExperience.getSegmentsExperienceId(),
			_segmentsExperienceManager.getSegmentsExperienceId(
				mockHttpServletRequest));
	}

	private MockHttpServletRequest _getMockHttpServletRequest() {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setPlid(_layout.getPlid());

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		return mockHttpServletRequest;
	}

	@DeleteAfterTestRun
	private Group _group;

	private Layout _layout;

	@Inject
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

	private SegmentsExperienceManager _segmentsExperienceManager;

}
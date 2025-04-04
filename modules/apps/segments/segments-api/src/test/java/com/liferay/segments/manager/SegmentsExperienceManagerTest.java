/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.manager;

import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.segments.service.SegmentsExperienceLocalService;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Anderson Luiz
 */
public class SegmentsExperienceManagerTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetSegmentsExperienceId() {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(WebKeys.THEME_DISPLAY, null);

		_segmentsExperienceManager.getSegmentsExperienceId(
			mockHttpServletRequest);

		Mockito.verify(
			_segmentsExperienceLocalService
		).fetchDefaultSegmentsExperienceId(
			Mockito.anyLong()
		);
	}

	@Mock
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

	@InjectMocks
	private SegmentsExperienceManager _segmentsExperienceManager;

}
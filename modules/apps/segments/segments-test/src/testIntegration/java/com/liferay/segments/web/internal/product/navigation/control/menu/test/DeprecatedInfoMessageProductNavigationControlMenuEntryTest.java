/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.web.internal.product.navigation.control.menu.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.product.navigation.control.menu.ProductNavigationControlMenuEntry;
import com.liferay.segments.constants.SegmentsPortletKeys;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Bárbara Cabrera
 */
@RunWith(Arquillian.class)
public class DeprecatedInfoMessageProductNavigationControlMenuEntryTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testIsShow() throws Exception {
		Assert.assertTrue(
			_productNavigationControlMenuEntry.isShow(
				_getMockHttpServletRequest(SegmentsPortletKeys.SEGMENTS)));
		Assert.assertFalse(
			_productNavigationControlMenuEntry.isShow(
				_getMockHttpServletRequest(RandomTestUtil.randomString())));
	}

	private MockHttpServletRequest _getMockHttpServletRequest(
		String portletName) {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		ThemeDisplay themeDisplay = new ThemeDisplay();

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		portletDisplay.setPortletName(portletName);

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		return mockHttpServletRequest;
	}

	@Inject(
		filter = "component.name=com.liferay.segments.web.internal.product.navigation.control.menu.DeprecatedInfoMessageProductNavigationControlMenuEntry"
	)
	private ProductNavigationControlMenuEntry
		_productNavigationControlMenuEntry;

}
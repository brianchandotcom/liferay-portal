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

package com.liferay.portal.util;

import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.theme.ThemeDisplay;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Vilmos Papp
 * @author Akos Thurzo
 */
public class PortalImplLayoutURLTest extends PortalImplBaseURLTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE);

	@Test
	public void testFromControlPanel() throws Exception {
		ThemeDisplay themeDisplay = initThemeDisplay(
			company, group, layout, VIRTUAL_HOSTNAME);

		String virtualHostnameFriendlyURL = PortalUtil.getLayoutURL(
			layout, themeDisplay, true);

		themeDisplay = initThemeDisplay(
			company, group, controlPanelLayout, VIRTUAL_HOSTNAME);

		String controlPanelFriendlyURL = PortalUtil.getLayoutURL(
			layout, themeDisplay, true);

		Assert.assertEquals(
			virtualHostnameFriendlyURL, controlPanelFriendlyURL);
	}

	@Test
	public void testNotPreserveParameters() throws Exception {
		ThemeDisplay themeDisplay = initThemeDisplay(
			company, group, layout, VIRTUAL_HOSTNAME);

		themeDisplay.setDoAsUserId("impersonated");

		String virtualHostnameFriendlyURL = PortalUtil.getLayoutURL(
			layout, themeDisplay, false);

		Assert.assertEquals(
			StringPool.BLANK,
			HttpUtil.getParameter(virtualHostnameFriendlyURL, "doAsUserId"));
	}

	@Test
	public void testPreserveParameters() throws Exception {
		ThemeDisplay themeDisplay = initThemeDisplay(
			company, group, layout, VIRTUAL_HOSTNAME);

		themeDisplay.setDoAsUserId("impersonated");

		String virtualHostnameFriendlyURL = PortalUtil.getLayoutURL(
			layout, themeDisplay, true);

		Assert.assertEquals(
			"impersonated",
			HttpUtil.getParameter(virtualHostnameFriendlyURL, "doAsUserId"));
	}

	@Test
	public void testUsingLocalhost() throws Exception {
		ThemeDisplay themeDisplay = initThemeDisplay(
			company, group, layout, VIRTUAL_HOSTNAME);

		String virtualHostnameFriendlyURL = PortalUtil.getLayoutURL(
			layout, themeDisplay, true);

		themeDisplay.setServerName(LOCALHOST);

		String localhostFriendlyURL = PortalUtil.getLayoutURL(
			layout, themeDisplay, true);

		Assert.assertEquals(localhostFriendlyURL, virtualHostnameFriendlyURL);
	}

	@Test
	public void testUsingLocalhostFromControlPanel() throws Exception {
		ThemeDisplay themeDisplay = initThemeDisplay(
			company, group, controlPanelLayout, VIRTUAL_HOSTNAME);

		String virtualHostnameFriendlyURL = PortalUtil.getLayoutURL(
			layout, themeDisplay, true);

		themeDisplay.setServerName(LOCALHOST);

		String localhostFriendlyURL = PortalUtil.getLayoutURL(
			layout, themeDisplay, true);

		Assert.assertEquals(localhostFriendlyURL, virtualHostnameFriendlyURL);
	}

	@Test
	public void testUsingLocalhostFromControlPanelOnly() throws Exception {
		ThemeDisplay themeDisplay = initThemeDisplay(
			company, group, layout, VIRTUAL_HOSTNAME);

		String virtualHostnameFriendlyURL = PortalUtil.getLayoutURL(
			layout, themeDisplay, true);

		themeDisplay = initThemeDisplay(
			company, group, controlPanelLayout, VIRTUAL_HOSTNAME, LOCALHOST);

		String controlPanelFriendlyURL = PortalUtil.getLayoutURL(
			layout, themeDisplay, true);

		Assert.assertEquals(
			virtualHostnameFriendlyURL, controlPanelFriendlyURL);
	}

}
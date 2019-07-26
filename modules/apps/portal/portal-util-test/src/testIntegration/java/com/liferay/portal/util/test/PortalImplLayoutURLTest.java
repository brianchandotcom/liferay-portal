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

package com.liferay.portal.util.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.admin.kernel.model.LayoutTypePortletConstants;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.impl.VirtualLayout;
import com.liferay.portal.kernel.service.VirtualHostLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PropsValues;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Vilmos Papp
 * @author Akos Thurzo
 */
@RunWith(Arquillian.class)
public class PortalImplLayoutURLTest extends BasePortalImplURLTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testFromControlPanel() throws Exception {
		Assert.assertEquals(
			portal.getLayoutURL(
				publicLayout,
				initThemeDisplay(
					company, group, publicLayout, VIRTUAL_HOSTNAME),
				true),
			portal.getLayoutURL(
				publicLayout,
				initThemeDisplay(
					company, group, controlPanelLayout, VIRTUAL_HOSTNAME),
				true));
	}

	@Test
	public void testNotPreserveParameters() throws Exception {
		ThemeDisplay themeDisplay = initThemeDisplay(
			company, group, publicLayout, VIRTUAL_HOSTNAME);

		themeDisplay.setDoAsUserId("impersonated");

		Assert.assertEquals(
			StringPool.BLANK,
			_http.getParameter(
				portal.getLayoutURL(publicLayout, themeDisplay, false),
				"doAsUserId"));
	}

	@Test
	public void testNotPreserveParametersForLayoutTypeURL() throws Exception {
		ThemeDisplay themeDisplay = initThemeDisplay(
			company, group, publicLayout, VIRTUAL_HOSTNAME);

		themeDisplay.setDoAsUserId("impersonated");

		Layout layout = LayoutTestUtil.addLayout(group);

		layout.setType(LayoutConstants.TYPE_URL);

		layoutLocalService.updateLayout(layout);

		String virtualHostnameFriendlyURL = portal.getLayoutURL(
			layout, themeDisplay, true);

		if (Validator.isNotNull(
				layout.getTypeSettingsProperty(
					LayoutTypePortletConstants.URL)) &&
			!virtualHostnameFriendlyURL.startsWith(StringPool.SLASH) &&
			!virtualHostnameFriendlyURL.startsWith(
				portal.getPortalURL(layout, themeDisplay))) {

			Assert.assertEquals(
				StringPool.BLANK,
				_http.getParameter(virtualHostnameFriendlyURL, "doAsUserId"));
		}

		Assert.assertEquals(
			StringPool.BLANK,
			_http.getParameter(
				portal.getLayoutURL(layout, themeDisplay, false),
				"doAsUserId"));
	}

	@Test
	public void testPreserveParameters() throws Exception {
		ThemeDisplay themeDisplay = initThemeDisplay(
			company, group, publicLayout, VIRTUAL_HOSTNAME);

		themeDisplay.setDoAsUserId("impersonated");

		String virtualHostnameFriendlyURL = portal.getLayoutURL(
			publicLayout, themeDisplay, true);

		if (virtualHostnameFriendlyURL.startsWith(StringPool.SLASH) ||
			virtualHostnameFriendlyURL.startsWith(
				portal.getPortalURL(themeDisplay))) {

			Assert.assertEquals(
				"impersonated",
				_http.getParameter(virtualHostnameFriendlyURL, "doAsUserId"));
		}
	}

	@Test
	public void testUsingLocalhost() throws Exception {
		ThemeDisplay themeDisplay = initThemeDisplay(
			company, group, publicLayout, VIRTUAL_HOSTNAME);

		String virtualHostnameFriendlyURL = portal.getLayoutURL(
			publicLayout, themeDisplay, true);

		themeDisplay.setServerName(LOCALHOST);

		String localhostFriendlyURL = portal.getLayoutURL(
			publicLayout, themeDisplay, true);

		Assert.assertEquals(localhostFriendlyURL, virtualHostnameFriendlyURL);
	}

	@Test
	public void testUsingLocalhostFromControlPanel() throws Exception {
		ThemeDisplay themeDisplay = initThemeDisplay(
			company, group, controlPanelLayout, VIRTUAL_HOSTNAME);

		String virtualHostnameFriendlyURL = portal.getLayoutURL(
			publicLayout, themeDisplay, true);

		themeDisplay.setServerName(LOCALHOST);

		String localhostFriendlyURL = portal.getLayoutURL(
			publicLayout, themeDisplay, true);

		Assert.assertEquals(localhostFriendlyURL, virtualHostnameFriendlyURL);
	}

	@Test
	public void testUsingLocalhostFromControlPanelOnly() throws Exception {
		Assert.assertEquals(
			portal.getLayoutURL(
				publicLayout,
				initThemeDisplay(
					company, group, publicLayout, VIRTUAL_HOSTNAME),
				true),
			portal.getLayoutURL(
				publicLayout,
				initThemeDisplay(
					company, group, controlPanelLayout, VIRTUAL_HOSTNAME,
					LOCALHOST),
				true));
	}

	@Test
	public void testUsingPublicLayoutSetWithVirtualHostToControlPanel()
		throws Exception {

		// Set virtual hostname for public layout Set

		LayoutSet publicLayoutSet = publicLayout.getLayoutSet();

		_virtualHostLocalService.updateVirtualHost(
			company.getCompanyId(), publicLayoutSet.getLayoutSetId(),
			_PUBLIC_LAYOUT_SET_VIRTUAL_HOSTNAME);

		// Test generated layout URL for Control Panel navigating from the
		// public layout

		ThemeDisplay themeDisplay = initThemeDisplay(
			company, group, publicLayout, VIRTUAL_HOSTNAME,
			_PUBLIC_LAYOUT_SET_VIRTUAL_HOSTNAME);

		Group controlPanelGroup = controlPanelLayout.getGroup();

		String expectedControlPanelFriendlyURL =
			portal.getPortalURL(themeDisplay) +
				PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_GROUP_SERVLET_MAPPING +
					controlPanelGroup.getFriendlyURL() +
						controlPanelLayout.getFriendlyURL();

		Assert.assertEquals(
			expectedControlPanelFriendlyURL,
			portal.getLayoutURL(controlPanelLayout, themeDisplay, true));
	}

	@Test
	public void testUsingPublicLayoutSetWithVirtualHostToScopedControlPanel()
		throws Exception {

		// Set virtual hostname for public layout set

		LayoutSet publicLayoutSet = publicLayout.getLayoutSet();

		_virtualHostLocalService.updateVirtualHost(
			company.getCompanyId(), publicLayoutSet.getLayoutSetId(),
			_PUBLIC_LAYOUT_SET_VIRTUAL_HOSTNAME);

		// Create group for public layout (will be used as scope group)

		Group scopeGroup = GroupTestUtil.addGroup(
			TestPropsValues.getUserId(), publicLayout);

		// Test generated layout URL for scoped Control Panel navigating from
		// the public layout

		Layout scopedControlPanelLayout = new VirtualLayout(
			controlPanelLayout, scopeGroup);

		ThemeDisplay themeDisplay = initThemeDisplay(
			company, group, publicLayout, VIRTUAL_HOSTNAME,
			_PUBLIC_LAYOUT_SET_VIRTUAL_HOSTNAME);

		themeDisplay.setScopeGroupId(scopedControlPanelLayout.getGroupId());

		Assert.assertEquals(
			portal.getPortalURL(themeDisplay) +
				PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_GROUP_SERVLET_MAPPING +
					scopeGroup.getFriendlyURL() +
						scopedControlPanelLayout.getFriendlyURL(),
			portal.getLayoutURL(scopedControlPanelLayout, themeDisplay, true));

		GroupTestUtil.deleteGroup(scopeGroup);
	}

	private static final String _PUBLIC_LAYOUT_SET_VIRTUAL_HOSTNAME =
		"test-public-layout.com";

	@Inject
	private Http _http;

	@Inject
	private VirtualHostLocalService _virtualHostLocalService;

}
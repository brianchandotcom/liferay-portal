/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.service.VirtualHostLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Akos Thurzo
 */
public class PortalImplLayoutRelativeURLTest extends PortalImplBaseURLTestCase {

	@Before
	public void setUp() throws Exception {
		super.setUp();

		LayoutSet publicLayoutSet = publicLayout.getLayoutSet();

		VirtualHostLocalServiceUtil.updateVirtualHost(
			company.getCompanyId(), publicLayoutSet.getLayoutSetId(),
			VIRTUAL_HOSTNAME);

		privateLayoutRelativeURL =
			PRIVATE_GROUP_SERVLET_MAPPING + group.getFriendlyURL() +
				privateLayout.getFriendlyURL();

		publicLayoutRelativeURL =
			PUBLIC_SERVLET_MAPPING + group.getFriendlyURL() +
				publicLayout.getFriendlyURL();
	}

	@Test
	public void testPrivateLayoutFromCompanyVirtualHost() throws Exception {
		ThemeDisplay themeDisplay = initThemeDisplay(
			company, group, privateLayout, LOCALHOST);

		String relativeURL = PortalUtil.getLayoutRelativeURL(
			privateLayout, themeDisplay);

		Assert.assertEquals(privateLayoutRelativeURL, relativeURL);
	}

	@Test
	public void testPrivateLayoutURLFromPublicLayoutSetVirtualHost()
		throws Exception {

		ThemeDisplay themeDisplay = initThemeDisplay(
			company, group, privateLayout, LOCALHOST, VIRTUAL_HOSTNAME);

		String relativeURL = PortalUtil.getLayoutRelativeURL(
			privateLayout, themeDisplay);

		Assert.assertEquals(privateLayoutRelativeURL, relativeURL);
	}

	@Test
	public void testPublicLayoutFromCompanyVirtualHost() throws Exception {
		ThemeDisplay themeDisplay = initThemeDisplay(
			company, group, publicLayout, LOCALHOST);

		String relativeURL = PortalUtil.getLayoutRelativeURL(
			publicLayout, themeDisplay);

		Assert.assertEquals(publicLayoutRelativeURL, relativeURL);
	}

	@Test
	public void testPublicLayoutURLFromPublicLayoutSetVirtualHost()
		throws Exception {

		ThemeDisplay themeDisplay = initThemeDisplay(
			company, group, publicLayout, LOCALHOST, VIRTUAL_HOSTNAME);

		String relativeURL = PortalUtil.getLayoutRelativeURL(
			publicLayout, themeDisplay);

		String publicLayoutFriendlyURL = publicLayout.getFriendlyURL();

		Assert.assertTrue(
			publicLayoutRelativeURL.equals(relativeURL) ||
			publicLayoutFriendlyURL.equals(relativeURL));
	}

	protected String privateLayoutRelativeURL;
	protected String publicLayoutRelativeURL;

}
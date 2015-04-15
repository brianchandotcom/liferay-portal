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

import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.service.VirtualHostLocalServiceUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.test.LayoutTestUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Akos Thurzo
 * @author Manuel de la Peña
 */
public class PortalImplLayoutRelativeURLTest extends PortalImplBaseURLTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE);

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		LayoutSet layoutSet = layout.getLayoutSet();

		VirtualHostLocalServiceUtil.updateVirtualHost(
			company.getCompanyId(), layoutSet.getLayoutSetId(),
			VIRTUAL_HOSTNAME);

		layoutRelativeURL =
			PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING +
				group.getFriendlyURL() + layout.getFriendlyURL();
	}

	@Test
	public void testGetLayoutRelativeURL() throws Exception {
		testGetLayoutRelativeURL(
			initThemeDisplay(company, group, layout, LOCALHOST), layout,
			layoutRelativeURL);
		testGetLayoutRelativeURL(
			initThemeDisplay(
				company, group, layout, LOCALHOST, VIRTUAL_HOSTNAME),
			layout, layoutRelativeURL);
		testGetLayoutRelativeURL(
			initThemeDisplay(company, group, layout, LOCALHOST), layout,
			layoutRelativeURL);

		String curLayoutFriendlyURL = layout.getFriendlyURL();
		String curLayoutRelativeURL = PortalUtil.getLayoutRelativeURL(
			layout,
			initThemeDisplay(
				company, group, layout, LOCALHOST, VIRTUAL_HOSTNAME));

		Assert.assertTrue(
			curLayoutFriendlyURL.equals(layoutRelativeURL) ||
			curLayoutRelativeURL.equals(layoutRelativeURL));
	}

	protected void testGetLayoutRelativeURL(
			ThemeDisplay themeDisplay, Layout layout, String layoutRelativeURL)
		throws Exception {

		Assert.assertEquals(
			layoutRelativeURL,
			PortalUtil.getLayoutRelativeURL(layout, themeDisplay));

		Layout childLayout = LayoutTestUtil.addLayout(group);

		themeDisplay.setRefererPlid(childLayout.getPlid());

		Assert.assertEquals(
			layoutRelativeURL,
			PortalUtil.getLayoutRelativeURL(layout, themeDisplay));

		themeDisplay.setRefererPlid(1);

		try {
			PortalUtil.getLayoutRelativeURL(layout, themeDisplay);

			Assert.fail();
		}
		catch (NoSuchLayoutException nsle) {
		}
	}

	protected String layoutRelativeURL;

}
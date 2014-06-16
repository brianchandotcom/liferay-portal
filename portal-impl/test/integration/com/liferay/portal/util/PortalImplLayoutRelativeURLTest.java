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
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.service.VirtualHostLocalServiceUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.ResetDatabaseExecutionTestListener;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.test.LayoutTestUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Akos Thurzo
 * @author Manuel de la Peña
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		ResetDatabaseExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class PortalImplLayoutRelativeURLTest extends PortalImplBaseURLTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		LayoutSet publicLayoutSet = publicLayout.getLayoutSet();

		VirtualHostLocalServiceUtil.updateVirtualHost(
			company.getCompanyId(), publicLayoutSet.getLayoutSetId(),
			VIRTUAL_HOSTNAME);

		privateLayoutRelativeURL =
			PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_GROUP_SERVLET_MAPPING +
				group.getFriendlyURL() + privateLayout.getFriendlyURL();
		publicLayoutRelativeURL =
			PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING +
				group.getFriendlyURL() + publicLayout.getFriendlyURL();
	}

	@Test
	public void testGetLayoutRelativeURL() throws Exception {
		testGetLayoutRelativeURL(
			initThemeDisplay(company, group, privateLayout, LOCALHOST),
			privateLayout, privateLayoutRelativeURL);
		testGetLayoutRelativeURL(
			initThemeDisplay(
				company, group, privateLayout, LOCALHOST, VIRTUAL_HOSTNAME),
			privateLayout, privateLayoutRelativeURL);
		testGetLayoutRelativeURL(
			initThemeDisplay(company, group, publicLayout, LOCALHOST),
			publicLayout, publicLayoutRelativeURL);

		String publicLayoutFriendlyURL = publicLayout.getFriendlyURL();
		String layoutRelativeURL = PortalUtil.getLayoutRelativeURL(
			publicLayout,
			initThemeDisplay(
				company, group, publicLayout, LOCALHOST, VIRTUAL_HOSTNAME));

		Assert.assertTrue(
			publicLayoutFriendlyURL.equals(layoutRelativeURL) ||
			publicLayoutRelativeURL.equals(layoutRelativeURL));
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
			PortalUtil.getLayoutRelativeURL(privateLayout, themeDisplay);

			Assert.fail();
		}
		catch (NoSuchLayoutException nsle) {
		}
	}

	protected String privateLayoutRelativeURL;
	protected String publicLayoutRelativeURL;

}
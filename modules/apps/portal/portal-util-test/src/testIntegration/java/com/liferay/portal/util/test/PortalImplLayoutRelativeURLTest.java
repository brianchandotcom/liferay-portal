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
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.exception.NoSuchLayoutException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.service.VirtualHostLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PropsValues;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Akos Thurzo
 * @author Manuel de la Peña
 */
@RunWith(Arquillian.class)
public class PortalImplLayoutRelativeURLTest extends BasePortalImplURLTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		LayoutSet publicLayoutSet = publicLayout.getLayoutSet();

		_virtualHostLocalService.updateVirtualHost(
			company.getCompanyId(), publicLayoutSet.getLayoutSetId(),
			VIRTUAL_HOSTNAME);

		_privateLayoutRelativeURL =
			PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_GROUP_SERVLET_MAPPING +
				group.getFriendlyURL() + privateLayout.getFriendlyURL();
		_publicLayoutRelativeURL =
			PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING +
				group.getFriendlyURL() + publicLayout.getFriendlyURL();
	}

	@Test
	public void testGetLayoutRelativeURL() throws Exception {
		_testGetLayoutRelativeURL(
			initThemeDisplay(company, group, privateLayout, LOCALHOST),
			privateLayout, _privateLayoutRelativeURL);
		_testGetLayoutRelativeURL(
			initThemeDisplay(
				company, group, privateLayout, LOCALHOST, VIRTUAL_HOSTNAME),
			privateLayout, _privateLayoutRelativeURL);
		_testGetLayoutRelativeURL(
			initThemeDisplay(company, group, publicLayout, LOCALHOST),
			publicLayout, _publicLayoutRelativeURL);

		String publicLayoutFriendlyURL = publicLayout.getFriendlyURL();
		String layoutRelativeURL = portal.getLayoutRelativeURL(
			publicLayout,
			initThemeDisplay(
				company, group, publicLayout, LOCALHOST, VIRTUAL_HOSTNAME));

		Assert.assertTrue(
			publicLayoutFriendlyURL.equals(layoutRelativeURL) ||
			_publicLayoutRelativeURL.equals(layoutRelativeURL));
	}

	private void _testGetLayoutRelativeURL(
			ThemeDisplay themeDisplay, Layout layout, String layoutRelativeURL)
		throws Exception {

		Assert.assertEquals(
			layoutRelativeURL,
			portal.getLayoutRelativeURL(layout, themeDisplay));

		Layout childLayout = LayoutTestUtil.addLayout(group);

		themeDisplay.setRefererPlid(childLayout.getPlid());

		Assert.assertEquals(
			layoutRelativeURL,
			portal.getLayoutRelativeURL(layout, themeDisplay));

		themeDisplay.setRefererPlid(Long.MAX_VALUE);

		try {
			portal.getLayoutRelativeURL(privateLayout, themeDisplay);

			Assert.fail();
		}
		catch (NoSuchLayoutException nsle) {
		}
	}

	private String _privateLayoutRelativeURL;
	private String _publicLayoutRelativeURL;

	@Inject
	private VirtualHostLocalService _virtualHostLocalService;

}
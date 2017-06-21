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

import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.VirtualHostLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Michael Bowerman
 */
public class PortalImplLayoutFriendlyURLTest extends BasePortalImplURLTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		newCompany = CompanyTestUtil.addCompany();

		newCompanyVirtualHostname = newCompany.getVirtualHostname();

		newGroup = GroupLocalServiceUtil.fetchGroup(
			newCompany.getCompanyId(), VIRTUAL_HOSTS_DEFAULT_SITE_NAME);

		newLayout = LayoutLocalServiceUtil.fetchLayout(
			newGroup.getGroupId(), false, 1);
	}

	@Test
	public void testUsingCompanyDefaultSiteVirtualHost() throws Exception {
		testLayoutFriendlyURL(
			newCompanyVirtualHostname, newLayout.getFriendlyURL());
	}

	@Test
	public void testUsingCompanyDefaultSiteVirtualHostWhenLayoutSetHasVirtualHost()
		throws Exception {

		assignNewPublicLayoutSetVirtualHost();

		String expectedURL =
			PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING +
				newGroup.getFriendlyURL() + newLayout.getFriendlyURL();

		testLayoutFriendlyURL(newCompanyVirtualHostname, expectedURL);
	}

	@Test
	public void testUsingLayoutSetVirtualHost() throws Exception {
		assignNewPublicLayoutSetVirtualHost();

		testLayoutFriendlyURL(
			newPublicLayoutSetVirtualHostname, newLayout.getFriendlyURL());
	}

	@Test
	public void testUsingLocalhost() throws Exception {
		String expectedURL =
			PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING +
				newGroup.getFriendlyURL() + newLayout.getFriendlyURL();

		testLayoutFriendlyURL(LOCALHOST, expectedURL);
	}

	@Test
	public void testUsingLocalhostWhenLayoutSetHasVirtualHost()
		throws Exception {

		assignNewPublicLayoutSetVirtualHost();

		String expectedURL =
			PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING +
				newGroup.getFriendlyURL() + newLayout.getFriendlyURL();

		testLayoutFriendlyURL(LOCALHOST, expectedURL);
	}

	protected void assignNewPublicLayoutSetVirtualHost() {
		LayoutSet newPublicLayoutSet = newGroup.getPublicLayoutSet();

		newPublicLayoutSetVirtualHostname =
			RandomTestUtil.randomString() + "." +
				RandomTestUtil.randomString(3);

		VirtualHostLocalServiceUtil.updateVirtualHost(
			newCompany.getCompanyId(), newPublicLayoutSet.getLayoutSetId(),
			newPublicLayoutSetVirtualHostname);
	}

	protected void testLayoutFriendlyURL(
			String virtualHostname, String expectedURL)
		throws Exception {

		ThemeDisplay themeDisplay = initThemeDisplay(
			newCompany, newGroup, newLayout, virtualHostname);

		themeDisplay.setPortalDomain(virtualHostname);

		Assert.assertEquals(
			expectedURL, PortalUtil.getLayoutFriendlyURL(themeDisplay));
	}

	@DeleteAfterTestRun
	protected Company newCompany;

	protected String newCompanyVirtualHostname;
	protected Group newGroup;
	protected Layout newLayout;
	protected String newPublicLayoutSetVirtualHostname;

}
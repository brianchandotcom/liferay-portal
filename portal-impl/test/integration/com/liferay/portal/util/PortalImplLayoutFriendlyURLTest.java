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

		_company = CompanyTestUtil.addCompany();

		_companyVirtualHostname = _company.getVirtualHostname();

		_group = GroupLocalServiceUtil.fetchGroup(
			_company.getCompanyId(), VIRTUAL_HOSTS_DEFAULT_SITE_NAME);

		_layout = LayoutLocalServiceUtil.fetchLayout(
			_group.getGroupId(), false, 1);
	}

	@Test
	public void testUsingCompanyDefaultSiteVirtualHost() throws Exception {
		testLayoutFriendlyURL(
			_companyVirtualHostname, _layout.getFriendlyURL());
	}

	@Test
	public void testUsingCompanyDefaultSiteVirtualHostWhenLayoutSetHasVirtualHost()
		throws Exception {

		assignNewPublicLayoutSetVirtualHost();

		String expectedURL =
			PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING +
				_group.getFriendlyURL() + _layout.getFriendlyURL();

		testLayoutFriendlyURL(_companyVirtualHostname, expectedURL);
	}

	@Test
	public void testUsingLayoutSetVirtualHost() throws Exception {
		assignNewPublicLayoutSetVirtualHost();

		testLayoutFriendlyURL(
			_publicLayoutSetVirtualHostname, _layout.getFriendlyURL());
	}

	@Test
	public void testUsingLocalhost() throws Exception {
		String expectedURL =
			PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING +
				_group.getFriendlyURL() + _layout.getFriendlyURL();

		testLayoutFriendlyURL(LOCALHOST, expectedURL);
	}

	@Test
	public void testUsingLocalhostWhenLayoutSetHasVirtualHost()
		throws Exception {

		assignNewPublicLayoutSetVirtualHost();

		String expectedURL =
			PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING +
				_group.getFriendlyURL() + _layout.getFriendlyURL();

		testLayoutFriendlyURL(LOCALHOST, expectedURL);
	}

	protected void assignNewPublicLayoutSetVirtualHost() {
		LayoutSet newPublicLayoutSet = _group.getPublicLayoutSet();

		_publicLayoutSetVirtualHostname =
			RandomTestUtil.randomString() + "." +
				RandomTestUtil.randomString(3);

		VirtualHostLocalServiceUtil.updateVirtualHost(
			_company.getCompanyId(), newPublicLayoutSet.getLayoutSetId(),
			_publicLayoutSetVirtualHostname);
	}

	protected void testLayoutFriendlyURL(
			String virtualHostname, String expectedURL)
		throws Exception {

		ThemeDisplay themeDisplay = initThemeDisplay(
			_company, _group, _layout, virtualHostname);

		themeDisplay.setPortalDomain(virtualHostname);

		Assert.assertEquals(
			expectedURL, PortalUtil.getLayoutFriendlyURL(themeDisplay));
	}

	@DeleteAfterTestRun
	private Company _company;

	private String _companyVirtualHostname;
	private Group _group;
	private Layout _layout;
	private String _publicLayoutSetVirtualHostname;

}
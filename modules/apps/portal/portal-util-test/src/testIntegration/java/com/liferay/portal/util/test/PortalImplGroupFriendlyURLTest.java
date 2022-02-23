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
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.VirtualHostLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.PropsValuesTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.TreeMapBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PropsValues;

import java.util.TreeMap;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Christopher Kian
 */
@RunWith(Arquillian.class)
public class PortalImplGroupFriendlyURLTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_originalVirtualHostDefaultSiteName =
			ReflectionTestUtil.getAndSetFieldValue(
				PropsValues.class, "VIRTUAL_HOSTS_DEFAULT_SITE_NAME",
				GroupConstants.GUEST);

		_company = CompanyTestUtil.addCompany();

		_group = _groupLocalService.fetchGroup(
			_company.getCompanyId(),
			PropsValues.VIRTUAL_HOSTS_DEFAULT_SITE_NAME);

		if (_group == null) {
			User user = UserTestUtil.getAdminUser(_company.getCompanyId());

			_group = GroupTestUtil.addGroup(
				_company.getCompanyId(), user.getUserId(), 0);

			_group.setGroupKey(PropsValues.VIRTUAL_HOSTS_DEFAULT_SITE_NAME);

			_groupLocalService.updateGroup(_group);
		}

		LayoutTestUtil.addLayout(_group, true);
	}

	@AfterClass
	public static void tearDownClass() throws PortalException {
		ReflectionTestUtil.setFieldValue(
			PropsValues.class, "VIRTUAL_HOSTS_DEFAULT_SITE_NAME",
			_originalVirtualHostDefaultSiteName);

		_companyLocalService.deleteCompany(_company);
	}

	@Test
	public void testGetGroupFriendlyURLFromPrivateLayout() throws Exception {
		Layout layout = _layoutLocalService.fetchDefaultLayout(
			_group.getGroupId(), true);

		_updateLayoutSetVirtualHostname(layout, _PRIVATE_LAYOUT_HOSTNAME);

		// Tests for LPS-70980

		String expectedURL =
			PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_GROUP_SERVLET_MAPPING +
				_group.getFriendlyURL();

		_testGroupFriendlyURL(
			_PRIVATE_LAYOUT_HOSTNAME, expectedURL, _group, layout);
	}

	@Test
	public void testGetGroupFriendlyURLFromPublicLayout() throws Exception {
		Layout layout = _layoutLocalService.fetchDefaultLayout(
			_group.getGroupId(), false);

		_updateLayoutSetVirtualHostname(layout, _PUBLIC_LAYOUT_HOSTNAME);

		String expectedURL = StringPool.BLANK;

		_testGroupFriendlyURL(
			_PUBLIC_LAYOUT_HOSTNAME, expectedURL, _group, layout);
	}

	@Test
	public void testGetGroupFriendlyURLFromPublicLayoutDefaultSite()
		throws Exception {

		Layout defaultSiteLayout = _layoutLocalService.fetchDefaultLayout(
			_group.getGroupId(), false);

		_updateLayoutSetVirtualHostname(defaultSiteLayout, StringPool.BLANK);

		_testGroupFriendlyURL(
			_company.getVirtualHostname(), StringPool.BLANK, _group,
			defaultSiteLayout);

		_updateLayoutSetVirtualHostname(
			defaultSiteLayout, _PUBLIC_LAYOUT_HOSTNAME);

		_testGroupFriendlyURL(
			_company.getVirtualHostname(), StringPool.BLANK, _group,
			defaultSiteLayout);

		_testGroupFriendlyURL(
			_PUBLIC_LAYOUT_HOSTNAME, StringPool.BLANK, _group,
			defaultSiteLayout);

		User user = UserTestUtil.getAdminUser(_company.getCompanyId());

		Group group = GroupTestUtil.addGroup(
			_company.getCompanyId(), user.getUserId(), 0);

		Layout nondefaultSiteLayout = LayoutTestUtil.addLayout(group);

		_testGroupFriendlyURL(
			_company.getVirtualHostname(),
			PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING +
				group.getFriendlyURL(),
			group, nondefaultSiteLayout);

		String hostName =
			RandomTestUtil.randomString(6) + StringPool.PERIOD +
				RandomTestUtil.randomString(3);

		_updateLayoutSetVirtualHostname(nondefaultSiteLayout, hostName);

		_testGroupFriendlyURL(
			hostName, StringPool.BLANK, group, nondefaultSiteLayout);
	}

	@Test
	public void testGetGroupFriendlyURLFromPublicLayoutNoVirtualHostDefaultSite()
		throws Exception {

		try (SafeCloseable safeCloseable =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"VIRTUAL_HOSTS_DEFAULT_SITE_NAME", StringPool.BLANK)) {

			Layout defaultSiteLayout = _layoutLocalService.fetchDefaultLayout(
				_group.getGroupId(), false);

			_updateLayoutSetVirtualHostname(
				defaultSiteLayout, StringPool.BLANK);

			_testGroupFriendlyURL(
				_company.getVirtualHostname(),
				PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING +
					_group.getFriendlyURL(),
				_group, defaultSiteLayout);

			_updateLayoutSetVirtualHostname(
				defaultSiteLayout, _PUBLIC_LAYOUT_HOSTNAME);

			_testGroupFriendlyURL(
				_company.getVirtualHostname(),
				PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING +
					_group.getFriendlyURL(),
				_group, defaultSiteLayout);

			_testGroupFriendlyURL(
				_PUBLIC_LAYOUT_HOSTNAME, StringPool.BLANK, _group,
				defaultSiteLayout);

			User user = UserTestUtil.getAdminUser(_company.getCompanyId());

			Group group = GroupTestUtil.addGroup(
				_company.getCompanyId(), user.getUserId(), 0);

			Layout nondefaultSiteLayout = LayoutTestUtil.addLayout(group);

			_testGroupFriendlyURL(
				_company.getVirtualHostname(),
				PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING +
					group.getFriendlyURL(),
				group, nondefaultSiteLayout);

			String hostName =
				RandomTestUtil.randomString(6) + StringPool.PERIOD +
					RandomTestUtil.randomString(3);

			_updateLayoutSetVirtualHostname(nondefaultSiteLayout, hostName);

			_testGroupFriendlyURL(
				hostName, StringPool.BLANK, group, nondefaultSiteLayout);
		}
	}

	private void _testGroupFriendlyURL(
			String virtualHostname, String expectedURL, Group group,
			Layout layout)
		throws Exception {

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(_company);
		themeDisplay.setLayout(layout);
		themeDisplay.setLayoutSet(layout.getLayoutSet());
		themeDisplay.setPortalDomain(virtualHostname);
		themeDisplay.setServerName(virtualHostname);
		themeDisplay.setSiteGroupId(group.getGroupId());
		themeDisplay.setUser(TestPropsValues.getUser());

		Assert.assertEquals(
			expectedURL,
			_portal.getGroupFriendlyURL(
				layout.getLayoutSet(), themeDisplay, false, true));
	}

	private void _updateLayoutSetVirtualHostname(
		Layout layout, String layoutHostname) {

		LayoutSet layoutSet = layout.getLayoutSet();

		TreeMap<String, String> virtualHostnames = TreeMapBuilder.put(
			layoutHostname, StringPool.BLANK
		).build();

		_virtualHostLocalService.updateVirtualHosts(
			_company.getCompanyId(), layoutSet.getLayoutSetId(),
			virtualHostnames);

		layoutSet.setVirtualHostnames(virtualHostnames);

		layout.setLayoutSet(layoutSet);
	}

	private static final String _PRIVATE_LAYOUT_HOSTNAME =
		"privateLayoutHostname";

	private static final String _PUBLIC_LAYOUT_HOSTNAME =
		"publicLayoutHostname";

	private static Company _company;

	@Inject
	private static CompanyLocalService _companyLocalService;

	private static Group _group;

	@Inject
	private static GroupLocalService _groupLocalService;

	private static String _originalVirtualHostDefaultSiteName;

	@Inject
	private static VirtualHostLocalService _virtualHostLocalService;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private Portal _portal;

}
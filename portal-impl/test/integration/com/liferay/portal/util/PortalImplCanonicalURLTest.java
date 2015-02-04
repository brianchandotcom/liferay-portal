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
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.test.LayoutTestUtil;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Sergio González
 */
public class PortalImplCanonicalURLTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() throws Exception {
		_defaultLocale = LocaleUtil.getDefault();

		LocaleUtil.setDefault(
			LocaleUtil.US.getLanguage(), LocaleUtil.US.getCountry(),
			LocaleUtil.US.getVariant());
	}

	@AfterClass
	public static void tearDownClass() {
		LocaleUtil.setDefault(
			_defaultLocale.getLanguage(), _defaultLocale.getCountry(),
			_defaultLocale.getVariant());
	}

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		Map<Locale, String> nameMap = new HashMap<>();

		nameMap.put(LocaleUtil.GERMANY, "Zuhause1");
		nameMap.put(LocaleUtil.SPAIN, "Casa1");
		nameMap.put(LocaleUtil.US, "Home1");

		Map<Locale, String> friendlyURLMap = new HashMap<>();

		friendlyURLMap.put(LocaleUtil.GERMANY, "/zuhause1");
		friendlyURLMap.put(LocaleUtil.SPAIN, "/casa1");
		friendlyURLMap.put(LocaleUtil.US, "/home1");

		_layout1 = LayoutTestUtil.addLayout(
			_group.getGroupId(), false, nameMap, friendlyURLMap);

		nameMap = new HashMap<>();

		nameMap.put(LocaleUtil.GERMANY, "Zuhause2");
		nameMap.put(LocaleUtil.SPAIN, "Casa2");
		nameMap.put(LocaleUtil.US, "Home2");

		friendlyURLMap = new HashMap<>();

		friendlyURLMap.put(LocaleUtil.GERMANY, "/zuhause2");
		friendlyURLMap.put(LocaleUtil.SPAIN, "/casa2");
		friendlyURLMap.put(LocaleUtil.US, "/home2");

		_layout2 = LayoutTestUtil.addLayout(
			_group.getGroupId(), false, nameMap, friendlyURLMap);

		if (_defaultGroup == null) {
			_defaultGroup = GroupLocalServiceUtil.getGroup(
				TestPropsValues.getCompanyId(),
				PropsValues.VIRTUAL_HOSTS_DEFAULT_SITE_NAME);

			_defaultGrouplayout1 = LayoutLocalServiceUtil.fetchFirstLayout(
				_defaultGroup.getGroupId(), false,
				LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

			if (_defaultGrouplayout1 == null) {
				_defaultGrouplayout1 = LayoutTestUtil.addLayout(_defaultGroup);
			}

			_defaultGrouplayout2 = LayoutTestUtil.addLayout(
				_defaultGroup.getGroupId());
		}
	}

	@Test
	public void testCustomPortalLocaleCanonicalURLFirstLayout()
		throws Exception {

		testCanonicalURL(
			"localhost", "localhost", _group, _layout1, null, null, "/es",
			StringPool.BLANK, false);
	}

	@Test
	public void testCustomPortalLocaleCanonicalURLForceLayoutFriendlyURL()
		throws Exception {

		testCanonicalURL(
			"localhost", "localhost", _group, _layout1, null, null, "/es",
			"/home1", true);
	}

	@Test
	public void testCustomPortalLocaleCanonicalURLSecondLayout()
		throws Exception {

		testCanonicalURL(
			"localhost", "localhost", _group, _layout2, null, null, "/es",
			"/home2", false);
	}

	@Test
	public void testDefaultPortalLocaleCanonicalURLFirstLayout()
		throws Exception {

		testCanonicalURL(
			"localhost", "localhost", _group, _layout1, null, null, "/en",
			StringPool.BLANK, false);
	}

	@Test
	public void testDefaultPortalLocaleCanonicalURLForceLayoutFriendlyURL()
		throws Exception {

		testCanonicalURL(
			"localhost", "localhost", _group, _layout1, null, null, "/en",
			"/home1", true);
	}

	@Test
	public void testDefaultPortalLocaleCanonicalURLSecondLayout()
		throws Exception {

		testCanonicalURL(
			"localhost", "localhost", _group, _layout2, null, null, "/en",
			"/home2", false);
	}

	@Test
	public void testDefaultSiteFirstPage() throws Exception {
		testCanonicalURL(
			"localhost", "localhost", _defaultGroup, _defaultGrouplayout1, null,
			null, "/en", StringPool.BLANK, false);
	}

	@Test
	public void testDefaultSiteFirstPageWithCustomPortalLocale()
		throws Exception {

		testCanonicalURL(
			"localhost", "localhost", _defaultGroup, _defaultGrouplayout1, null,
			null, "/es", StringPool.BLANK, false);
	}

	@Test
	public void testDefaultSiteSecondPage() throws Exception {
		testCanonicalURL(
			"localhost", "localhost", _defaultGroup, _defaultGrouplayout2, null,
			null, "/en", _defaultGrouplayout2.getFriendlyURL(), false);
	}

	@Test
	public void testDefaultSiteSecondPageWithCustomPortalLocale()
		throws Exception {

		testCanonicalURL(
			"localhost", "localhost", _defaultGroup, _defaultGrouplayout2, null,
			null, "/es", _defaultGrouplayout2.getFriendlyURL(), false);
	}

	@Test
	public void
			testDomainCustomPortalLocaleCanonicalURLFirstLayoutFromLocalhost()
		throws Exception {

		testCanonicalURL(
			"liferay.com", "localhost", _group, _layout1, null, null, "/es",
			StringPool.BLANK, false);
	}

	@Test
	public void testDomainDefaultSiteFirstPageFromLocalhost() throws Exception {
		testCanonicalURL(
			"liferay.com", "localhost", _defaultGroup, _defaultGrouplayout1,
			null, null, "/en", StringPool.BLANK, false);
	}

	@Test
	public void testLocalizedSiteCustomSiteLocaleCanonicalURLFirstLayout()
		throws Exception {

		testCanonicalURL(
			"localhost", "localhost", _group, _layout1,
			new Locale[] {LocaleUtil.GERMANY, LocaleUtil.SPAIN, LocaleUtil.US},
			LocaleUtil.SPAIN, "/en", StringPool.BLANK, false);
	}

	@Test
	public void
			testLocalizedSiteCustomSiteLocaleCanonicalURLForceLayoutFriendlyURL()
		throws Exception {

		testCanonicalURL(
			"localhost", "localhost", _group, _layout1,
			new Locale[] {LocaleUtil.GERMANY, LocaleUtil.SPAIN, LocaleUtil.US},
			LocaleUtil.SPAIN, "/en", "/casa1", true);
	}

	@Test
	public void testLocalizedSiteCustomSiteLocaleCanonicalURLSecondLayout()
		throws Exception {

		testCanonicalURL(
			"localhost", "localhost", _group, _layout2,
			new Locale[] {LocaleUtil.GERMANY, LocaleUtil.SPAIN, LocaleUtil.US},
			LocaleUtil.SPAIN, "/en", "/casa2", false);
	}

	@Test
	public void testLocalizedSiteDefaultSiteLocaleCanonicalURLFirstLayout()
		throws Exception {

		testCanonicalURL(
			"localhost", "localhost", _group, _layout1, new Locale[] {
				LocaleUtil.GERMANY, LocaleUtil.SPAIN, LocaleUtil.US},
			LocaleUtil.SPAIN, "/es", StringPool.BLANK, false);
	}

	@Test
	public void
			testLocalizedSiteDefaultSiteLocaleCanonicalURLForceLayoutFriendlyURL()
		throws Exception {

		testCanonicalURL(
			"localhost", "localhost", _group, _layout1,
			new Locale[] {LocaleUtil.GERMANY, LocaleUtil.SPAIN, LocaleUtil.US},
			LocaleUtil.SPAIN, "/es", "/casa1", true);
	}

	@Test
	public void testLocalizedSiteDefaultSiteLocaleCanonicalURLSecondLayout()
		throws Exception {

		testCanonicalURL(
			"localhost", "localhost", _group, _layout2,
			new Locale[] {LocaleUtil.GERMANY, LocaleUtil.SPAIN, LocaleUtil.US},
			LocaleUtil.SPAIN, "/es", "/casa2", false);
	}

	@Test
	public void testNonLocalhostDefaultSiteFirstPage() throws Exception {
		testCanonicalURL(
			"localhost", "liferay.com", _defaultGroup, _defaultGrouplayout1,
			null, null, "/en", StringPool.BLANK, false);
	}

	@Test
	public void testNonLocalhostDefaultSiteSecondPage() throws Exception {
		testCanonicalURL(
			"localhost", "liferay.com", _defaultGroup, _defaultGrouplayout2,
			null, null, "/en", _defaultGrouplayout2.getFriendlyURL(), false);
	}

	@Test
	public void testNonLocalhostPortalDomainFirstLayout() throws Exception {
		testCanonicalURL(
			"localhost", "liferay.com", _group, _layout1, null, null, "/en",
			StringPool.BLANK, false);
	}

	@Test
	public void testNonLocalhostPortalDomainForceLayoutFriendlyURL()
		throws Exception {

		testCanonicalURL(
			"localhost", "liferay.com", _group, _layout1, null, null, "/en",
			"/home1", true);
	}

	@Test
	public void testNonLocalhostPortalDomainSecondLayout() throws Exception {
		testCanonicalURL(
			"localhost", "liferay.com", _group, _layout2, null, null, "/en",
			"/home2", false);
	}

	protected String generateURL(
		String portalDomain, String languageId, String groupFriendlyURL,
		String layoutFriendlyURL) {

		StringBundler sb = new StringBundler(6);

		sb.append("http://");
		sb.append(portalDomain);
		sb.append(languageId);

		if (Validator.isNotNull(groupFriendlyURL)) {
			sb.append(PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING);
			sb.append(groupFriendlyURL);
		}

		sb.append(layoutFriendlyURL);

		return sb.toString();
	}

	protected ThemeDisplay getThemeDisplay(Group group) throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		Company company = CompanyLocalServiceUtil.getCompany(
			TestPropsValues.getCompanyId());

		themeDisplay.setCompany(company);

		themeDisplay.setLayoutSet(group.getPublicLayoutSet());
		themeDisplay.setServerPort(80);
		themeDisplay.setSiteGroupId(group.getGroupId());

		return themeDisplay;
	}

	protected void setVirtualHost(long companyId, String virtualHostname)
		throws Exception {

		if (Validator.isNull(virtualHostname)) {
			return;
		}

		Company company = CompanyLocalServiceUtil.getCompany(companyId);

		CompanyLocalServiceUtil.updateCompany(
			company.getCompanyId(), virtualHostname, company.getMx(),
			company.getMaxUsers(), company.isActive());
	}

	protected void testCanonicalURL(
			String virtualHostname, String portalDomain, Group group,
			Layout layout, Locale[] groupAvailableLocales,
			Locale groupDefaultLocale, String i18nPath,
			String expectedLayoutFriendlyURL, boolean forceLayoutFriendlyURL)
		throws Exception {

		if (!group.isGuest()) {
			group = GroupTestUtil.updateDisplaySettings(
				group.getGroupId(), groupAvailableLocales, groupDefaultLocale);
		}

		String completeURL = generateURL(
			portalDomain, i18nPath, group.getFriendlyURL(),
			layout.getFriendlyURL());

		setVirtualHost(layout.getCompanyId(), virtualHostname);

		ThemeDisplay themeDisplay = getThemeDisplay(group);

		themeDisplay.setPortalURL("http://" + portalDomain + ":8080/");

		String actualCanonicalURL = PortalUtil.getCanonicalURL(
			completeURL, themeDisplay, layout, forceLayoutFriendlyURL);

		String expectedGroupFriendlyURL = StringPool.BLANK;

		if (!group.isGuest()) {
			expectedGroupFriendlyURL = group.getFriendlyURL();
		}

		String expectedPortalDomain = virtualHostname;

		if (StringUtil.equalsIgnoreCase(virtualHostname, "localhost") &&
			!StringUtil.equalsIgnoreCase(portalDomain, "localhost")) {

			expectedPortalDomain = portalDomain;
		}

		String expectedCanonicalURL = generateURL(
			expectedPortalDomain, StringPool.BLANK, expectedGroupFriendlyURL,
			expectedLayoutFriendlyURL);

		Assert.assertEquals(expectedCanonicalURL, actualCanonicalURL);
	}

	private static Locale _defaultLocale;

	private Group _defaultGroup;
	private Layout _defaultGrouplayout1;
	private Layout _defaultGrouplayout2;

	@DeleteAfterTestRun
	private Group _group;

	private Layout _layout1;
	private Layout _layout2;

}
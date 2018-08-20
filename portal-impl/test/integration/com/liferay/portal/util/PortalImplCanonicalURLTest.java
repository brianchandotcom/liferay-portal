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

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.test.LayoutTestUtil;

import java.util.Arrays;
import java.util.Collection;
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
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_defaultLocale = LocaleUtil.getDefault();
		_defaultPrependStyle = PropsValues.LOCALE_PREPEND_FRIENDLY_URL_STYLE;

		LocaleUtil.setDefault(
			LocaleUtil.US.getLanguage(), LocaleUtil.US.getCountry(),
			LocaleUtil.US.getVariant());
	}

	@AfterClass
	public static void tearDownClass() {
		LocaleUtil.setDefault(
			_defaultLocale.getLanguage(), _defaultLocale.getCountry(),
			_defaultLocale.getVariant());

		TestPropsUtil.set(
			PropsKeys.LOCALE_PREPEND_FRIENDLY_URL_STYLE,
			GetterUtil.getString(_defaultPrependStyle));
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
	public void testCanonicalURLWithoutQueryString() throws Exception {
		String portalDomain = "localhost";

		String completeURL = generateURL(
			portalDomain, "8080", "/en", _group.getFriendlyURL(),
			_layout1.getFriendlyURL(), false);

		completeURL = HttpUtil.addParameter(
			completeURL, "_ga", "2.237928582.786466685.1515402734-1365236376");

		ThemeDisplay themeDisplay = createThemeDisplay(
			portalDomain, _group, 8080, false);

		String canonicalURL = PortalUtil.getCanonicalURL(
			completeURL, themeDisplay, _layout1, true, true);

		String expectedCanonicalURL = HttpUtil.removeParameter(
			canonicalURL, "_ga");

		String actualCanonicalURL = PortalUtil.getCanonicalURL(
			completeURL, themeDisplay, _layout1, true, false);

		Assert.assertEquals(expectedCanonicalURL, actualCanonicalURL);
	}

	@Test
	public void testCustomPortalLocaleCanonicalURLFirstLayout()
		throws Exception {

		testCanonicalURL(
			"localhost", "localhost", _group, _layout1, null, null, "/es",
			StringPool.BLANK, false, false);
	}

	@Test
	public void testCustomPortalLocaleCanonicalURLForceLayoutFriendlyURL()
		throws Exception {

		testCanonicalURL(
			"localhost", "localhost", _group, _layout1, null, null, "/es",
			"/home1", true, false);
	}

	@Test
	public void testCustomPortalLocaleCanonicalURLSecondLayout()
		throws Exception {

		testCanonicalURL(
			"localhost", "localhost", _group, _layout2, null, null, "/es",
			"/home2", false, false);
	}

	@Test
	public void testDefaultPortalLocaleCanonicalURLFirstLayout()
		throws Exception {

		testCanonicalURL(
			"localhost", "localhost", _group, _layout1, null, null, "/en",
			StringPool.BLANK, false, false);
	}

	@Test
	public void testDefaultPortalLocaleCanonicalURLForceLayoutFriendlyURL()
		throws Exception {

		testCanonicalURL(
			"localhost", "localhost", _group, _layout1, null, null, "/en",
			"/home1", true, false);
	}

	@Test
	public void testDefaultPortalLocaleCanonicalURLSecondLayout()
		throws Exception {

		testCanonicalURL(
			"localhost", "localhost", _group, _layout2, null, null, "/en",
			"/home2", false, false);
	}

	@Test
	public void testDefaultSiteFirstPage() throws Exception {
		testCanonicalURL(
			"localhost", "localhost", _defaultGroup, _defaultGrouplayout1, null,
			null, "/en", StringPool.BLANK, false, false);
	}

	@Test
	public void testDefaultSiteFirstPageWithCustomPortalLocale()
		throws Exception {

		testCanonicalURL(
			"localhost", "localhost", _defaultGroup, _defaultGrouplayout1, null,
			null, "/es", StringPool.BLANK, false, false);
	}

	@Test
	public void testDefaultSiteSecondPage() throws Exception {
		testCanonicalURL(
			"localhost", "localhost", _defaultGroup, _defaultGrouplayout2, null,
			null, "/en", _defaultGrouplayout2.getFriendlyURL(), false, false);
	}

	@Test
	public void testDefaultSiteSecondPageWithCustomPortalLocale()
		throws Exception {

		testCanonicalURL(
			"localhost", "localhost", _defaultGroup, _defaultGrouplayout2, null,
			null, "/es", _defaultGrouplayout2.getFriendlyURL(), false, false);
	}

	@Test
	public void testDomainCustomPortalLocaleCanonicalURLFirstLayoutFromLocalhost()
		throws Exception {

		testCanonicalURL(
			"liferay.com", "localhost", _group, _layout1, null, null, "/es",
			StringPool.BLANK, false, false);
	}

	@Test
	public void testDomainDefaultSiteFirstPageFromLocalhost() throws Exception {
		testCanonicalURL(
			"liferay.com", "localhost", _defaultGroup, _defaultGrouplayout1,
			null, null, "/en", StringPool.BLANK, false, false);
	}

	@Test
	public void testDomainDefaultSiteFirstPageFromLocalhostWithPort()
		throws Exception {

		testCanonicalURL(
			"liferay.com", "localhost:8080", _defaultGroup,
			_defaultGrouplayout1, null, null, "/en", StringPool.BLANK, false,
			false);
	}

	@Test
	public void testDomainDefaultSiteFirstPageFromLocalhostWithPortSecure()
		throws Exception {

		testCanonicalURL(
			"liferay.com", "localhost:8080", _defaultGroup,
			_defaultGrouplayout1, null, null, "/en", StringPool.BLANK, false,
			true);
	}

	@Test
	public void testLocalizedSiteCustomSiteLocaleCanonicalURLFirstLayout()
		throws Exception {

		testCanonicalURL(
			"localhost", "localhost", _group, _layout1,
			Arrays.asList(LocaleUtil.GERMANY, LocaleUtil.SPAIN, LocaleUtil.US),
			LocaleUtil.SPAIN, "/en", StringPool.BLANK, false, false);
	}

	@Test
	public void testLocalizedSiteCustomSiteLocaleCanonicalURLForceLayoutFriendlyURL()
		throws Exception {

		testCanonicalURL(
			"localhost", "localhost", _group, _layout1,
			Arrays.asList(LocaleUtil.GERMANY, LocaleUtil.SPAIN, LocaleUtil.US),
			LocaleUtil.SPAIN, "/en", "/casa1", true, false);
	}

	@Test
	public void testLocalizedSiteCustomSiteLocaleCanonicalURLSecondLayout()
		throws Exception {

		testCanonicalURL(
			"localhost", "localhost", _group, _layout2,
			Arrays.asList(LocaleUtil.GERMANY, LocaleUtil.SPAIN, LocaleUtil.US),
			LocaleUtil.SPAIN, "/en", "/casa2", false, false);
	}

	@Test
	public void testLocalizedSiteDefaultSiteLocaleCanonicalURLFirstLayout()
		throws Exception {

		testCanonicalURL(
			"localhost", "localhost", _group, _layout1,
			Arrays.asList(LocaleUtil.GERMANY, LocaleUtil.SPAIN, LocaleUtil.US),
			LocaleUtil.SPAIN, "/es", StringPool.BLANK, false, false);
	}

	@Test
	public void testLocalizedSiteDefaultSiteLocaleCanonicalURLForceLayoutFriendlyURL()
		throws Exception {

		testCanonicalURL(
			"localhost", "localhost", _group, _layout1,
			Arrays.asList(LocaleUtil.GERMANY, LocaleUtil.SPAIN, LocaleUtil.US),
			LocaleUtil.SPAIN, "/es", "/casa1", true, false);
	}

	@Test
	public void testLocalizedSiteDefaultSiteLocaleCanonicalURLSecondLayout()
		throws Exception {

		testCanonicalURL(
			"localhost", "localhost", _group, _layout2,
			Arrays.asList(LocaleUtil.GERMANY, LocaleUtil.SPAIN, LocaleUtil.US),
			LocaleUtil.SPAIN, "/es", "/casa2", false, false);
	}

	@Test
	public void testNonlocalhostDefaultSiteFirstPage() throws Exception {
		testCanonicalURL(
			"localhost", "liferay.com", _defaultGroup, _defaultGrouplayout1,
			null, null, "/en", StringPool.BLANK, false, false);
	}

	@Test
	public void testNonlocalhostDefaultSiteSecondPage() throws Exception {
		testCanonicalURL(
			"localhost", "liferay.com", _defaultGroup, _defaultGrouplayout2,
			null, null, "/en", _defaultGrouplayout2.getFriendlyURL(), false,
			false);
	}

	@Test
	public void testNonlocalhostPortalDomainFirstLayout() throws Exception {
		testCanonicalURL(
			"localhost", "liferay.com", _group, _layout1, null, null, "/en",
			StringPool.BLANK, false, false);
	}

	@Test
	public void testNonlocalhostPortalDomainForceLayoutFriendlyURL()
		throws Exception {

		testCanonicalURL(
			"localhost", "liferay.com", _group, _layout1, null, null, "/en",
			"/home1", true, false);
	}

	@Test
	public void testNonlocalhostPortalDomainSecondLayout() throws Exception {
		testCanonicalURL(
			"localhost", "liferay.com", _group, _layout2, null, null, "/en",
			"/home2", false, false);
	}

	protected ThemeDisplay createThemeDisplay(
			String portalDomain, Group group, int serverPort, boolean secure)
		throws PortalException {

		ThemeDisplay themeDisplay = new ThemeDisplay();

		Company company = CompanyLocalServiceUtil.getCompany(
			TestPropsValues.getCompanyId());

		themeDisplay.setCompany(company);

		themeDisplay.setLayoutSet(group.getPublicLayoutSet());
		themeDisplay.setPortalDomain(portalDomain);

		if (secure) {
			themeDisplay.setPortalURL(Http.HTTPS_WITH_SLASH + portalDomain);
		}
		else {
			themeDisplay.setPortalURL(Http.HTTP_WITH_SLASH + portalDomain);
		}

		themeDisplay.setSecure(secure);

		int index = portalDomain.indexOf(CharPool.COLON);

		if (index != -1) {
			serverPort = GetterUtil.getIntegerStrict(
				portalDomain.substring(index + 1));
		}

		themeDisplay.setServerPort(serverPort);
		themeDisplay.setSiteGroupId(group.getGroupId());

		return themeDisplay;
	}

	protected String generateURL(
		String portalDomain, String port, String i18nPath,
		String groupFriendlyURL, String layoutFriendlyURL, boolean secure) {

		StringBundler sb = new StringBundler(8);

		if (secure) {
			sb.append(Http.HTTPS_WITH_SLASH);
		}
		else {
			sb.append(Http.HTTP_WITH_SLASH);
		}

		sb.append(portalDomain);

		if (port != null) {
			sb.append(StringPool.COLON);
			sb.append(port);
		}

		sb.append(i18nPath);

		if (Validator.isNotNull(groupFriendlyURL)) {
			sb.append(PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING);
			sb.append(groupFriendlyURL);
		}

		sb.append(layoutFriendlyURL);

		return sb.toString();
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
			Layout layout, Collection<Locale> groupAvailableLocales,
			Locale groupDefaultLocale, String i18nPath,
			String expectedLayoutFriendlyURL, boolean forceLayoutFriendlyURL,
			boolean secure)
		throws Exception {

		if (!group.isGuest()) {
			group = GroupTestUtil.updateDisplaySettings(
				group.getGroupId(), groupAvailableLocales, groupDefaultLocale);
		}

		String port = null;

		int index = portalDomain.indexOf(CharPool.COLON);

		if (index != -1) {
			port = portalDomain.substring(index + 1);
		}

		String completeURL = generateURL(
			portalDomain, port, i18nPath, group.getFriendlyURL(),
			layout.getFriendlyURL(), secure);

		setVirtualHost(layout.getCompanyId(), virtualHostname);

		ThemeDisplay themeDisplay = createThemeDisplay(
			portalDomain, group, Http.HTTP_PORT, secure);

		String expectedGroupFriendlyURL = StringPool.BLANK;

		if (!group.isGuest()) {
			expectedGroupFriendlyURL = group.getFriendlyURL();
		}

		String expectedPortalDomain = virtualHostname;

		if (virtualHostname.startsWith("localhost") &&
			!portalDomain.startsWith("localhost")) {

			expectedPortalDomain = portalDomain;
		}

		String expectedCanonicalURL = generateURL(
			expectedPortalDomain, port, StringPool.BLANK,
			expectedGroupFriendlyURL, expectedLayoutFriendlyURL, secure);

		TestPropsUtil.set(PropsKeys.LOCALE_PREPEND_FRIENDLY_URL_STYLE, "2");

		String actualCanonicalURL2 = PortalUtil.getCanonicalURL(
			completeURL, themeDisplay, layout, forceLayoutFriendlyURL);

		Assert.assertEquals(expectedCanonicalURL, actualCanonicalURL2);
	}

	private static Locale _defaultLocale;
	private static int _defaultPrependStyle;

	private Group _defaultGroup;
	private Layout _defaultGrouplayout1;
	private Layout _defaultGrouplayout2;

	@DeleteAfterTestRun
	private Group _group;

	private Layout _layout1;
	private Layout _layout2;

}
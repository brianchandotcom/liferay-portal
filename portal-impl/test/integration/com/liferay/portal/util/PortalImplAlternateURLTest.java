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
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.test.LayoutTestUtil;

import java.util.Locale;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Sergio González
 */
public class PortalImplAlternateURLTest {

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

	@Test
	public void testCustomPortalLocaleAlternateURL() throws Exception {
		testAlternateURL("localhost", null, null, LocaleUtil.SPAIN, "/es");
	}

	@Test
	public void testDefaultPortalLocaleAlternateURL() throws Exception {
		testAlternateURL(
			"localhost", null, null, LocaleUtil.US, StringPool.BLANK);
	}

	@Test
	public void testLocalizedSiteCustomSiteLocaleAlternateURL()
		throws Exception {

		testAlternateURL(
			"localhost",
			new Locale[] {LocaleUtil.US, LocaleUtil.SPAIN, LocaleUtil.GERMANY},
			LocaleUtil.SPAIN, LocaleUtil.US, "/en");
	}

	@Test
	public void testLocalizedSiteDefaultSiteLocaleAlternateURL()
		throws Exception {

		testAlternateURL(
			"localhost",
			new Locale[] {LocaleUtil.US, LocaleUtil.SPAIN, LocaleUtil.GERMANY},
			LocaleUtil.SPAIN, LocaleUtil.SPAIN, StringPool.BLANK);
	}

	@Test
	public void testNonlocalhostCustomPortalLocaleAlternateURL()
		throws Exception {

		testAlternateURL("liferay.com", null, null, LocaleUtil.SPAIN, "/es");
	}

	@Test
	public void testNonlocalhostDefaultPortalLocaleAlternateURL()
		throws Exception {

		testAlternateURL(
			"liferay.com", null, null, LocaleUtil.US, StringPool.BLANK);
	}

	@Test
	public void testNonlocalhostLocalizedSiteCustomSiteLocaleAlternateURL()
		throws Exception {

		testAlternateURL(
			"liferay.com", new Locale[] {LocaleUtil.US, LocaleUtil.SPAIN,
			LocaleUtil.GERMANY}, LocaleUtil.SPAIN, LocaleUtil.US, "/en");
	}

	@Test
	public void testNonlocalhostLocalizedSiteDefaultSiteLocaleAlternateURL()
		throws Exception {

		testAlternateURL(
			"liferay.com", new Locale[] {LocaleUtil.US, LocaleUtil.SPAIN,
			LocaleUtil.GERMANY}, LocaleUtil.SPAIN, LocaleUtil.SPAIN,
			StringPool.BLANK);
	}

	protected String generateURL(
		String portalDomain, String languageId, String groupFriendlyURL,
		String layoutFriendlyURL) {

		StringBundler sb = new StringBundler(6);

		sb.append("http://");
		sb.append(portalDomain);
		sb.append(languageId);
		sb.append(PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING);
		sb.append(groupFriendlyURL);
		sb.append(layoutFriendlyURL);

		return sb.toString();
	}

	protected ThemeDisplay getThemeDisplay(Group group, String portalURL)
		throws Exception {

		ThemeDisplay themeDisplay = new ThemeDisplay();

		Company company = CompanyLocalServiceUtil.getCompany(
			TestPropsValues.getCompanyId());

		themeDisplay.setCompany(company);

		themeDisplay.setLayoutSet(group.getPublicLayoutSet());
		themeDisplay.setPortalURL(portalURL);

		return themeDisplay;
	}

	protected void testAlternateURL(
			String portalDomain, Locale[] groupAvailableLocales,
			Locale groupDefaultLocale, Locale alternateLocale,
			String expectedI18nPath)
		throws Exception {

		_group = GroupTestUtil.addGroup();

		_group = GroupTestUtil.updateDisplaySettings(
			_group.getGroupId(), groupAvailableLocales, groupDefaultLocale);

		Layout layout = LayoutTestUtil.addLayout(
			_group.getGroupId(), "welcome", false);

		String canonicalURL = generateURL(
			portalDomain, StringPool.BLANK, _group.getFriendlyURL(),
			layout.getFriendlyURL());

		String actualAlternateURL = PortalUtil.getAlternateURL(
			canonicalURL, getThemeDisplay(_group, canonicalURL),
			alternateLocale, layout);

		String expectedAlternateURL = generateURL(
			portalDomain, expectedI18nPath, _group.getFriendlyURL(),
			layout.getFriendlyURL());

		Assert.assertEquals(expectedAlternateURL, actualAlternateURL);
	}

	private static Locale _defaultLocale;

	@DeleteAfterTestRun
	private Group _group;

}
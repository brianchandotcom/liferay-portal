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

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portal.theme.ThemeDisplay;

import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Sergio González
 */
@ExecutionTestListeners(
	listeners = {
		EnvironmentExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Transactional
public class PortalImplAlternateURLTest {

	@Test
	public void testCustomPortalLocaleAlternateURL() throws Exception {
		testAlternateURL(null, null, LocaleUtil.SPAIN, "/es");
	}

	@Test
	public void testDefaultPortalLocaleAlternateURL() throws Exception {
		testAlternateURL(null, null, LocaleUtil.US, StringPool.BLANK);
	}

	@Test
	public void testLocalizedSiteCustomSiteLocaleAlternateURL()
		throws Exception {

		testAlternateURL(
			new Locale[] {LocaleUtil.US, LocaleUtil.SPAIN, LocaleUtil.GERMANY},
			LocaleUtil.SPAIN, LocaleUtil.US, "/en");
	}

	@Test
	public void testLocalizedSiteDefaultSiteLocaleAlternateURL()
		throws Exception {

		testAlternateURL(
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
		String languageId, String groupFriendlyURL, String layoutFriendlyURL) {

		return generateURL(
			"localhost", languageId, groupFriendlyURL, layoutFriendlyURL);
	}

	protected String generateURL(
		String portalDomain, String languageId, String groupFriendlyURL,
		String layoutFriendlyURL) {

		StringBundler sb = new StringBundler(5);

		sb.append("http://");
		sb.append(portalDomain);
		sb.append(languageId);
		sb.append(PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING);
		sb.append(groupFriendlyURL);
		sb.append(layoutFriendlyURL);

		return sb.toString();
	}

	protected ThemeDisplay getThemeDisplay(Group group) throws Exception {
		return getThemeDisplay(group, null);
	}

	protected ThemeDisplay getThemeDisplay(Group group, String portalURL)
		throws Exception {

		ThemeDisplay themeDisplay = new ThemeDisplay();

		Company company = CompanyLocalServiceUtil.getCompany(
			TestPropsValues.getCompanyId());

		themeDisplay.setCompany(company);

		themeDisplay.setLayoutSet(group.getPublicLayoutSet());

		if (Validator.isNotNull(portalURL)) {
			themeDisplay.setPortalURL(portalURL);
		}

		return themeDisplay;
	}

	protected void testAlternateURL(
			Locale[] groupAvailableLocales, Locale groupDefaultLocale,
			Locale alternateLocale, String expectedI18nPath)
		throws Exception {

		testAlternateURL(
			"localhost", groupAvailableLocales, groupDefaultLocale,
			alternateLocale, expectedI18nPath);
	}

	protected void testAlternateURL(
			String portalDomain, Locale[] groupAvailableLocales,
			Locale groupDefaultLocale, Locale alternateLocale,
			String expectedI18nPath)
		throws Exception {

		Group group = GroupTestUtil.addGroup();

		group = GroupTestUtil.updateDisplaySettings(
			group.getGroupId(), groupAvailableLocales, groupDefaultLocale);

		Layout layout = LayoutTestUtil.addLayout(
			group.getGroupId(), "welcome", false);

		String canonicalURL = generateURL(
			portalDomain, StringPool.BLANK, group.getFriendlyURL(),
			layout.getFriendlyURL());

		String actualAlternateURL = PortalUtil.getAlternateURL(
			canonicalURL, getThemeDisplay(group, canonicalURL), alternateLocale,
			layout);

		String expectedAlternateURL = generateURL(
			portalDomain, expectedI18nPath, group.getFriendlyURL(),
			layout.getFriendlyURL());

		Assert.assertEquals(expectedAlternateURL, actualAlternateURL);
	}

}
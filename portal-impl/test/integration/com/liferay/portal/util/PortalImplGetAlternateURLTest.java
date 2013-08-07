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
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
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
public class PortalImplGetAlternateURLTest {

	@Test
	public void testCustomPortalLocaleAlternateURL() throws Exception {
		Locale esLocale = new Locale("es", "ES");

		testAlternateURL(null, null, esLocale, "/es");
	}

	@Test
	public void testDefaultPortalLocaleAlternateURL() throws Exception {
		testAlternateURL(null, null, Locale.US, StringPool.BLANK);
	}

	@Test
	public void testLocalizedSiteCustomSiteLocaleAlternateURL()
		throws Exception {

		Locale enLocale = Locale.US;
		Locale esLocale = new Locale("es", "ES");
		Locale deLocale = new Locale("de", "DE");

		testAlternateURL(
			new Locale[] {enLocale, esLocale, deLocale}, esLocale, enLocale,
			"/en");
	}

	@Test
	public void testLocalizedSiteDefaultSiteLocaleAlternateURL()
		throws Exception {

		Locale enLocale = Locale.US;
		Locale esLocale = new Locale("es", "ES");
		Locale deLocale = new Locale("de", "DE");

		testAlternateURL(
			new Locale[] {enLocale, esLocale, deLocale}, esLocale, esLocale,
			StringPool.BLANK);
	}

	protected String generateURL(
		String languageId, String groupFriendlyURL, String layoutFriendlyURL) {

		StringBundler sb = new StringBundler(6);

		sb.append("http://localhost");
		sb.append(languageId);
		sb.append(PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING);
		sb.append(groupFriendlyURL);
		sb.append(layoutFriendlyURL);

		return sb.toString();
	}

	protected ThemeDisplay initThemeDisplay(Group group) throws Exception {
		Company company = CompanyLocalServiceUtil.getCompany(
			TestPropsValues.getCompanyId());

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setLayoutSet(group.getPublicLayoutSet());
		themeDisplay.setCompany(company);

		return themeDisplay;
	}

	protected void testAlternateURL(
			Locale[] groupAvailableLocales, Locale groupDefaultLocale,
			Locale alternateLocale, String expectedI18nPath)
		throws Exception {

		Group group = GroupTestUtil.addGroup();

		group = GroupTestUtil.updateDisplaySettings(
			group.getGroupId(), groupAvailableLocales, groupDefaultLocale);

		Layout layout = LayoutTestUtil.addLayout(
			group.getGroupId(), "welcome", false);

		String canonicalURL = generateURL(
			StringPool.BLANK, group.getFriendlyURL(), layout.getFriendlyURL());

		ThemeDisplay themeDisplay = initThemeDisplay(group);

		String alternateURL = PortalUtil.getAlternateURL(
			canonicalURL, themeDisplay, alternateLocale, layout);

		String expectedURL = generateURL(
			expectedI18nPath, group.getFriendlyURL(), layout.getFriendlyURL());

		Assert.assertEquals(expectedURL, alternateURL);
	}

}

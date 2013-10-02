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

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

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
public class PortalImplCanonicalURLTest {

	@Test
	public void testCustomPortalLocaleCanonicalURL() throws Exception {
		testCanonicalURL(null, _LOCALHOST, null, null, "/es", "/home");
	}

	@Test
	public void testDefaultPortalLocaleCanonicalURL() throws Exception {
		testCanonicalURL(null, _LOCALHOST, null, null, "/en", "/home");
	}

	@Test
	public void testLocalizedSiteCustomSiteLocaleCanonicalURL()
		throws Exception {

		testCanonicalURL(
			null, _LOCALHOST, new Locale[] {
				LocaleUtil.GERMANY, LocaleUtil.SPAIN, LocaleUtil.US},
			LocaleUtil.SPAIN, "/en", "/casa");
	}

	@Test
	public void testLocalizedSiteDefaultSiteLocaleCanonicalURL()
		throws Exception {

		testCanonicalURL(
			null, _LOCALHOST, new Locale[] {
				LocaleUtil.GERMANY, LocaleUtil.SPAIN, LocaleUtil.US},
			LocaleUtil.SPAIN, "/es", "/casa");
	}

	@Test
	public void testNonLocalhostPortalDomain() throws Exception {
		testCanonicalURL(
			_LOCALHOST, _LIFERAY_DOT_COM, null, null, "/en", "/home");
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

	protected void setVirtualHost(Layout layout, String virtualHostname)
		throws Exception {

		if (Validator.isNull(virtualHostname)) {
			return;
		}

		Company company = CompanyLocalServiceUtil.getCompany(
			layout.getCompanyId());

		CompanyLocalServiceUtil.updateCompany(
			company.getCompanyId(), virtualHostname, company.getMx(),
			company.getMaxUsers(), company.isActive());
	}

	protected void testCanonicalURL(
			String virtualHostname, String portalDomain,
			Locale[] groupAvailableLocales, Locale groupDefaultLocale,
			String i18nPath, String expectedFriendlyURL)
		throws Exception {

		Group group = GroupTestUtil.addGroup();

		group = GroupTestUtil.updateDisplaySettings(
			group.getGroupId(), groupAvailableLocales, groupDefaultLocale);

		Map<Locale, String> nameMap = new HashMap<Locale, String>();

		nameMap.put(LocaleUtil.GERMANY, "Zuhause");
		nameMap.put(LocaleUtil.SPAIN, "Casa");
		nameMap.put(LocaleUtil.US, "Home");

		Map<Locale, String> friendlyURLMap = new HashMap<Locale, String>();

		friendlyURLMap.put(LocaleUtil.GERMANY, "/zuhause");
		friendlyURLMap.put(LocaleUtil.SPAIN, "/casa");
		friendlyURLMap.put(LocaleUtil.US, "/home");

		Layout layout = LayoutTestUtil.addLayout(
			group.getGroupId(), false, nameMap, friendlyURLMap);

		String completeURL = generateURL(
			portalDomain, i18nPath, group.getFriendlyURL(),
			layout.getFriendlyURL());

		setVirtualHost(layout, virtualHostname);

		ThemeDisplay themeDisplay = getThemeDisplay(group);

		themeDisplay.setPortalURL("http://" + portalDomain + ":8080/");

		String actualCanonicalURL = PortalUtil.getCanonicalURL(
			completeURL, themeDisplay, layout, true);

		String expectedCanonicalURL = generateURL(
			portalDomain, StringPool.BLANK, group.getFriendlyURL(),
			expectedFriendlyURL);

		Assert.assertEquals(expectedCanonicalURL, actualCanonicalURL);
	}

	private static final String _LIFERAY_DOT_COM = "liferay.com";

	private static final String _LOCALHOST = "localhost";

}
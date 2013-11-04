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
import org.junit.Before;
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

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		Map<Locale, String> nameMap = new HashMap<Locale, String>();

		nameMap.put(LocaleUtil.GERMANY, "Zuhause1");
		nameMap.put(LocaleUtil.SPAIN, "Casa1");
		nameMap.put(LocaleUtil.US, "Home1");

		Map<Locale, String> friendlyURLMap = new HashMap<Locale, String>();

		friendlyURLMap.put(LocaleUtil.GERMANY, "/zuhause1");
		friendlyURLMap.put(LocaleUtil.SPAIN, "/casa1");
		friendlyURLMap.put(LocaleUtil.US, "/home1");

		_layout1 = LayoutTestUtil.addLayout(
			_group.getGroupId(), false, nameMap, friendlyURLMap);

		nameMap = new HashMap<Locale, String>();

		nameMap.put(LocaleUtil.GERMANY, "Zuhause2");
		nameMap.put(LocaleUtil.SPAIN, "Casa2");
		nameMap.put(LocaleUtil.US, "Home2");

		friendlyURLMap = new HashMap<Locale, String>();

		friendlyURLMap.put(LocaleUtil.GERMANY, "/zuhause2");
		friendlyURLMap.put(LocaleUtil.SPAIN, "/casa2");
		friendlyURLMap.put(LocaleUtil.US, "/home2");

		_layout2 = LayoutTestUtil.addLayout(
			_group.getGroupId(), false, nameMap, friendlyURLMap);
	}

	@Test
	public void testCustomPortalLocaleCanonicalURLFirstLayout()
		throws Exception {

		testCanonicalURL(
			null, "localhost", _layout1, null, null, "/es", StringPool.BLANK,
			false);
	}

	@Test
	public void testCustomPortalLocaleCanonicalURLForceLayoutFriendlyURL()
		throws Exception {

		testCanonicalURL(
			null, "localhost", _layout1, null, null, "/es", "/home1", true);
	}

	@Test
	public void testCustomPortalLocaleCanonicalURLSecondLayout() throws Exception {
		testCanonicalURL(
			null, "localhost", _layout2, null, null, "/es", "/home2", false);
	}

	@Test
	public void testDefaultPortalLocaleCanonicalURLFirstLayout()
		throws Exception {

		testCanonicalURL(
			null, "localhost", _layout1, null, null, "/en", StringPool.BLANK,
			false);
	}

	@Test
	public void testDefaultPortalLocaleCanonicalURLForceLayoutFriendlyURL()
		throws Exception {

		testCanonicalURL(
			null, "localhost", _layout1, null, null, "/en", "/home1", true);
	}

	@Test
	public void testDefaultPortalLocaleCanonicalURLSecondLayout()
		throws Exception {

		testCanonicalURL(
			null, "localhost", _layout2, null, null, "/en", "/home2", false);
	}

	@Test
	public void testLocalizedSiteCustomSiteLocaleCanonicalURLFirstLayout()
		throws Exception {

		testCanonicalURL(
			null, "localhost", _layout1, new Locale[] {
				LocaleUtil.GERMANY, LocaleUtil.SPAIN, LocaleUtil.US},
			LocaleUtil.SPAIN, "/en", StringPool.BLANK, false);
	}

	@Test
	public void testLocalizedSiteCustomSiteLocaleCanonicalURLForceLayoutFriendlyURL()
		throws Exception {

		testCanonicalURL(
			null, "localhost", _layout1, new Locale[] {
				LocaleUtil.GERMANY, LocaleUtil.SPAIN, LocaleUtil.US},
			LocaleUtil.SPAIN, "/en", "/casa1", true);
	}

	@Test
	public void testLocalizedSiteCustomSiteLocaleCanonicalURLSecondLayout()
		throws Exception {

		testCanonicalURL(
			null, "localhost", _layout2, new Locale[] {
				LocaleUtil.GERMANY, LocaleUtil.SPAIN, LocaleUtil.US},
			LocaleUtil.SPAIN, "/en", "/casa2", false);
	}

	@Test
	public void testLocalizedSiteDefaultSiteLocaleCanonicalURLFirstLayout()
		throws Exception {

		testCanonicalURL(
			null, "localhost", _layout1, new Locale[] {
				LocaleUtil.GERMANY, LocaleUtil.SPAIN, LocaleUtil.US},
			LocaleUtil.SPAIN, "/es", StringPool.BLANK, false);
	}

	@Test
	public void testLocalizedSiteDefaultSiteLocaleCanonicalURLForceLayoutFriendlyURL()
		throws Exception {

		testCanonicalURL(
			null, "localhost", _layout1, new Locale[] {
				LocaleUtil.GERMANY, LocaleUtil.SPAIN, LocaleUtil.US},
			LocaleUtil.SPAIN, "/es", "/casa1", true);
	}

	@Test
	public void testLocalizedSiteDefaultSiteLocaleCanonicalURLSecondLayout()
		throws Exception {

		testCanonicalURL(
			null, "localhost", _layout2, new Locale[] {
				LocaleUtil.GERMANY, LocaleUtil.SPAIN, LocaleUtil.US},
			LocaleUtil.SPAIN, "/es", "/casa2", false);
	}

	@Test
	public void testNonLocalhostPortalDomainFirstLayout() throws Exception {
		testCanonicalURL(
			"localhost", "liferay.com", _layout1, null, null, "/en",
			StringPool.BLANK, false);
	}

	@Test
	public void testNonLocalhostPortalDomainForceLayoutFriendlyURL() throws Exception {
		testCanonicalURL(
			"localhost", "liferay.com", _layout1, null, null, "/en", "/home1",
			true);
	}

	@Test
	public void testNonLocalhostPortalDomainSecondLayout() throws Exception {
		testCanonicalURL(
			"localhost", "liferay.com", _layout2, null, null, "/en", "/home2",
			false);
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
			String virtualHostname, String portalDomain, Layout layout,
			Locale[] groupAvailableLocales, Locale groupDefaultLocale,
			String i18nPath, String expectedFriendlyURL,
			boolean forceLayoutFriendlyURL)
		throws Exception {

		_group = GroupTestUtil.updateDisplaySettings(
			_group.getGroupId(), groupAvailableLocales, groupDefaultLocale);

		String completeURL = generateURL(
			portalDomain, i18nPath, _group.getFriendlyURL(),
			layout.getFriendlyURL());

		setVirtualHost(layout.getCompanyId(), virtualHostname);

		ThemeDisplay themeDisplay = getThemeDisplay(_group);

		themeDisplay.setPortalURL("http://" + portalDomain + ":8080/");

		String actualCanonicalURL = PortalUtil.getCanonicalURL(
			completeURL, themeDisplay, layout, forceLayoutFriendlyURL);

		String expectedCanonicalURL = generateURL(
			portalDomain, StringPool.BLANK, _group.getFriendlyURL(),
			expectedFriendlyURL);

		Assert.assertEquals(expectedCanonicalURL, actualCanonicalURL);
	}

	private Group _group = null;
	private Layout _layout1 = null;
	private Layout _layout2 = null;

}
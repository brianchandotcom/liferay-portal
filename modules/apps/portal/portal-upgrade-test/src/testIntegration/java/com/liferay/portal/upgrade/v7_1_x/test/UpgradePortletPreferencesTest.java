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

package com.liferay.portal.upgrade.v7_1_x.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.OrganizationConstants;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.PortalPreferencesLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.upgrade.util.UpgradeProcessUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.v7_1_x.UpgradePortalPreferences;

import java.util.Locale;
import java.util.Set;

import javax.portlet.PortletPreferences;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Christopher Kian
 */
@RunWith(Arquillian.class)
public class UpgradePortletPreferencesTest extends UpgradePortalPreferences {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_availableLocales = _language.getAvailableLocales();

		String defaultLanguageId = UpgradeProcessUtil.getDefaultLanguageId(
			CompanyThreadLocal.getCompanyId());

		_defaultLocale = _language.getLocale(defaultLanguageId);

		CompanyTestUtil.resetCompanyLocales(
			CompanyThreadLocal.getCompanyId(), "en_US,es_ES", "en_US");
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		CompanyTestUtil.resetCompanyLocales(
			CompanyThreadLocal.getCompanyId(), _availableLocales,
			_defaultLocale);
	}

	@Before
	public void setUp() throws Exception {
		_organization = _organizationLocalService.addOrganization(
			_userLocalService.getDefaultUserId(
				CompanyThreadLocal.getCompanyId()),
			OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID,
			RandomTestUtil.randomString(), false);
	}

	@Test
	public void testMissingDefaultLocale() throws Exception {
		StringBundler sb = new StringBundler(4);

		sb.append("<portlet-preferences><preference><name>reminderQueries");
		sb.append("</name><value>defaultValue</value></preference>");
		sb.append("<preference><name>reminderQueries_es_ES</name><value>");
		sb.append("spanishValue</value></preference></portlet-preferences>");

		PortletPreferences portletPreferences = _upgradePreferences(
			sb.toString());

		Assert.assertEquals(
			"defaultValue",
			portletPreferences.getValue("reminderQueries", null));
		Assert.assertEquals(
			"defaultValue",
			portletPreferences.getValue("reminderQueries_en_US", null));
		Assert.assertEquals(
			"spanishValue",
			portletPreferences.getValue("reminderQueries_es_ES", null));
	}

	@Test
	public void testPresentDefaultLocale() throws Exception {
		StringBundler sb = new StringBundler(6);

		sb.append("<portlet-preferences><preference><name>reminderQueries");
		sb.append("</name><value>defaultValue</value></preference>");
		sb.append("<preference><name>reminderQueries_en_US</name><value>");
		sb.append("englishValue</value></preference><preference><name>");
		sb.append("reminderQueries_es_ES</name><value>spanishValue</value>");
		sb.append("</preference></portlet-preferences>");

		PortletPreferences portletPreferences = _upgradePreferences(
			sb.toString());

		Assert.assertEquals(
			"defaultValue",
			portletPreferences.getValue("reminderQueries", null));

		Assert.assertEquals(
			"englishValue",
			portletPreferences.getValue("reminderQueries_en_US", null));

		Assert.assertEquals(
			"spanishValue",
			portletPreferences.getValue("reminderQueries_es_ES", null));
	}

	private PortletPreferences _upgradePreferences(String preferences)
		throws Exception {

		_portalPreferencesLocalService.addPortalPreferences(
			_organization.getOrganizationId(),
			PortletKeys.PREFS_OWNER_TYPE_ORGANIZATION, preferences);

		upgrade();

		CacheRegistryUtil.clear();

		return _organization.getPreferences();
	}

	private static Set<Locale> _availableLocales;
	private static Locale _defaultLocale;

	@Inject
	private static Language _language;

	@DeleteAfterTestRun
	private Organization _organization;

	@Inject
	private OrganizationLocalService _organizationLocalService;

	@Inject
	private PortalPreferencesLocalService _portalPreferencesLocalService;

	@Inject
	private UserLocalService _userLocalService;

}
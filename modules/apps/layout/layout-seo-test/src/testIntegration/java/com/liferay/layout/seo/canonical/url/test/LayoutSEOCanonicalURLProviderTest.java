/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.seo.canonical.url.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.seo.canonical.url.LayoutSEOCanonicalURLProvider;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.Collections;
import java.util.Locale;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
@RunWith(Arquillian.class)
public class LayoutSEOCanonicalURLProviderTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_availableLocales = LanguageUtil.getAvailableLocales(
			TestPropsValues.getCompanyId());
		_defaultLocale = LocaleUtil.getDefault();

		_group = GroupTestUtil.addGroup();

		_layout = LayoutTestUtil.addTypeContentLayout(_group);

		_initThemeDisplay();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		serviceContext.setRequest(new MockHttpServletRequest());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);
	}

	@After
	public void tearDown() throws Exception {
		CompanyTestUtil.resetCompanyLocales(
			TestPropsValues.getCompanyId(), _availableLocales, _defaultLocale);

		ServiceContextThreadLocal.popServiceContext();
	}

	@Test
	public void testGetCanonicalURL() throws Exception {
		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					"com.liferay.layout.seo.internal.configuration." +
						"LayoutSEOCompanyConfiguration",
					HashMapDictionaryBuilder.<String, Object>put(
						"canonicalURL", "localized-url"
					).build())) {

			CompanyTestUtil.resetCompanyLocales(
				_group.getCompanyId(),
				Collections.singletonList(LocaleUtil.GERMAN),
				LocaleUtil.GERMAN);

			Assert.assertEquals(
				"http://localhost:8080/web" + _group.getFriendlyURL(),
				_layoutSEOCanonicalURLProvider.getCanonicalURL(
					_layout, LocaleUtil.US,
					_portal.getCanonicalURL(
						_portal.getLayoutFullURL(_layout, _themeDisplay),
						_themeDisplay, _layout),
					_themeDisplay));
		}
	}

	private void _initThemeDisplay() throws Exception {
		_themeDisplay = new ThemeDisplay();

		_themeDisplay.setCompany(
			CompanyLocalServiceUtil.fetchCompany(_group.getCompanyId()));
		_themeDisplay.setLayoutSet(_layout.getLayoutSet());
		_themeDisplay.setServerPort(8080);
		_themeDisplay.setSiteGroupId(_group.getGroupId());
	}

	private static Set<Locale> _availableLocales;
	private static Locale _defaultLocale;

	@DeleteAfterTestRun
	private Group _group;

	private Layout _layout;

	@Inject
	private LayoutSEOCanonicalURLProvider _layoutSEOCanonicalURLProvider;

	@Inject
	private Portal _portal;

	private ThemeDisplay _themeDisplay;

}
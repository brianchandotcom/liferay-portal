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

package com.liferay.layout.seo.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.seo.kernel.LayoutSEOLink;
import com.liferay.layout.seo.kernel.LayoutSEOLinkManager;
import com.liferay.layout.seo.service.LayoutSEOEntryLocalService;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alicia Garcia
 */
@RunWith(Arquillian.class)
public class LayoutSEOLinkManagerCanonicalLayoutSEOLinkTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_layout = _layoutLocalService.getLayout(TestPropsValues.getPlid());

		_group = GroupTestUtil.addGroup();

		_layout.setGroupId(_group.getGroupId());

		_themeDisplay = _getThemeDisplay();
	}

	@Test
	public void testGetCanonicalLayoutURL() throws Exception {
		String canonicalURL = _portal.getCanonicalURL(
			RandomTestUtil.randomString(), _themeDisplay, _layout, false,
			false);

		Map<Locale, String> alternateURLs = _portal.getAlternateURLs(
			canonicalURL, _themeDisplay, _layout);

		LayoutSEOLink canonicalLayoutSEOLink =
			_layoutSEOLinkManager.getCanonicalLayoutSEOLink(
				_layout, LocaleUtil.getDefault(), canonicalURL, alternateURLs);

		Assert.assertEquals(canonicalURL, canonicalLayoutSEOLink.getHref());
	}

	@Test
	public void testGetCanonicalLayoutURLCustomCanonicalURLDisabled()
		throws Exception {

		String canonicalURL = _portal.getCanonicalURL(
			RandomTestUtil.randomString(), _themeDisplay, _layout, false,
			false);

		Map<Locale, String> alternateURLs = _portal.getAlternateURLs(
			canonicalURL, _themeDisplay, _layout);

		_layoutSEOEntryLocalService.updateLayoutSEOEntry(
			TestPropsValues.getUserId(), _group.getGroupId(), false,
			_layout.getLayoutId(), false,
			Collections.singletonMap(
				LocaleUtil.getDefault(), "http://example.com"),
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		LayoutSEOLink canonicalLayoutSEOLink =
			_layoutSEOLinkManager.getCanonicalLayoutSEOLink(
				_layout, LocaleUtil.getDefault(), canonicalURL, alternateURLs);

		Assert.assertEquals(canonicalURL, canonicalLayoutSEOLink.getHref());
	}

	@Test
	public void testGetCanonicalLayoutURLCustomCanonicalURLEnabled()
		throws Exception {

		_layoutSEOEntryLocalService.updateLayoutSEOEntry(
			TestPropsValues.getUserId(), _group.getGroupId(), false,
			_layout.getLayoutId(), true,
			Collections.singletonMap(
				LocaleUtil.getDefault(), "http://example.com"),
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		String canonicalURL = _portal.getCanonicalURL(
			RandomTestUtil.randomString(), _themeDisplay, _layout, true, false);

		Map<Locale, String> alternateURLs = _portal.getAlternateURLs(
			canonicalURL, _themeDisplay, _layout);

		LayoutSEOLink canonicalLayoutSEOLink =
			_layoutSEOLinkManager.getCanonicalLayoutSEOLink(
				_layout, LocaleUtil.getDefault(), canonicalURL, alternateURLs);

		Assert.assertEquals(
			"http://example.com", canonicalLayoutSEOLink.getHref());
	}

	@Test
	public void testGetCanonicalLayoutURLLocalizedURL() throws Exception {
		String canonicalURL = _portal.getCanonicalURL(
			RandomTestUtil.randomString(), _themeDisplay, _layout, false,
			false);

		Map<Locale, String> alternateURLs = _portal.getAlternateURLs(
			canonicalURL, _themeDisplay, _layout);

		_testWithLayoutSEOCompanyConfiguration(
			() -> {
				LayoutSEOLink canonicalLayoutSEOLink =
					_layoutSEOLinkManager.getCanonicalLayoutSEOLink(
						_layout, LocaleUtil.CHINA, canonicalURL, alternateURLs);

				Assert.assertEquals(
					alternateURLs.getOrDefault(LocaleUtil.CHINA, canonicalURL),
					canonicalLayoutSEOLink.getHref());
			});
	}

	private ThemeDisplay _getThemeDisplay() throws PortalException {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		Company company = _companyLocalService.getCompany(
			_group.getCompanyId());

		company.setVirtualHostname(_VIRTUAL_HOSTNAME);

		themeDisplay.setCompany(company);

		themeDisplay.setLayoutSet(_layout.getLayoutSet());
		themeDisplay.setSecure(false);
		themeDisplay.setServerName(_VIRTUAL_HOSTNAME);
		themeDisplay.setServerPort(_SERVER_PORT);
		themeDisplay.setSiteGroupId(_group.getGroupId());
		themeDisplay.setScopeGroupId(_group.getGroupId());
		themeDisplay.setPermissionChecker(
			PermissionThreadLocal.getPermissionChecker());
		themeDisplay.setPlid(_layout.getPlid());

		return themeDisplay;
	}

	private void _testWithLayoutSEOCompanyConfiguration(
			UnsafeRunnable<Exception> unsafeRunnable)
		throws Exception {

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					_LAYOUT_SEO_CONFIGURATION_PID,
					new HashMapDictionary<String, Object>() {
						{
							put("canonicalURL", "localized-url");
						}
					})) {

			unsafeRunnable.run();
		}
	}

	private static final String _LAYOUT_SEO_CONFIGURATION_PID =
		"com.liferay.layout.seo.internal.configuration." +
			"LayoutSEOCompanyConfiguration";

	private static final int _SERVER_PORT = 8080;

	private static final String _VIRTUAL_HOSTNAME = "test.com";

	@Inject
	private CompanyLocalService _companyLocalService;

	@DeleteAfterTestRun
	private Group _group;

	private Layout _layout;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private LayoutSEOEntryLocalService _layoutSEOEntryLocalService;

	@Inject
	private LayoutSEOLinkManager _layoutSEOLinkManager;

	@Inject
	private Portal _portal;

	private ThemeDisplay _themeDisplay;

}
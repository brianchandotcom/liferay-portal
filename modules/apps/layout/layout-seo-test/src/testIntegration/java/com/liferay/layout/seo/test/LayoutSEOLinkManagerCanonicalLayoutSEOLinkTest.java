/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.seo.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.seo.kernel.LayoutSEOLink;
import com.liferay.layout.seo.kernel.LayoutSEOLinkManager;
import com.liferay.layout.seo.service.LayoutSEOEntryLocalService;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
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

import org.springframework.mock.web.MockHttpServletRequest;

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
		_group = GroupTestUtil.addGroup();

		LayoutTestUtil.addTypePortletLayout(_group);

		_layout = LayoutTestUtil.addTypePortletLayout(_group);

		ServiceContext serviceContext = new ServiceContext();

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		_themeDisplay = _getThemeDisplay();

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _themeDisplay);

		_themeDisplay.setRequest(mockHttpServletRequest);

		serviceContext.setRequest(mockHttpServletRequest);

		ServiceContextThreadLocal.pushServiceContext(serviceContext);
	}

	@Test
	public void testGetCanonicalLayoutURL() throws Exception {
		String canonicalURL = _portal.getCanonicalURL(
			RandomTestUtil.randomString(), _themeDisplay, _layout, false,
			false);

		LayoutSEOLink canonicalLayoutSEOLink =
			_layoutSEOLinkManager.getCanonicalLayoutSEOLink(
				_layout, LocaleUtil.getDefault(), canonicalURL, _themeDisplay);

		Assert.assertEquals(canonicalURL, canonicalLayoutSEOLink.getHref());
	}

	@Test
	public void testGetCanonicalLayoutURLCustomCanonicalURLDisabled()
		throws Exception {

		String canonicalURL = _portal.getCanonicalURL(
			RandomTestUtil.randomString(), _themeDisplay, _layout, false,
			false);

		_layoutSEOEntryLocalService.updateLayoutSEOEntry(
			TestPropsValues.getUserId(), _group.getGroupId(), false,
			_layout.getLayoutId(), false,
			Collections.singletonMap(
				LocaleUtil.getDefault(), "http://example.com"),
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		LayoutSEOLink canonicalLayoutSEOLink =
			_layoutSEOLinkManager.getCanonicalLayoutSEOLink(
				_layout, LocaleUtil.getDefault(), canonicalURL, _themeDisplay);

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

		LayoutSEOLink canonicalLayoutSEOLink =
			_layoutSEOLinkManager.getCanonicalLayoutSEOLink(
				_layout, LocaleUtil.getDefault(), canonicalURL, _themeDisplay);

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
						_layout, LocaleUtil.CHINA, canonicalURL, _themeDisplay);

				Assert.assertEquals(
					alternateURLs.getOrDefault(LocaleUtil.CHINA, canonicalURL),
					canonicalLayoutSEOLink.getHref());
			});
	}

	private ThemeDisplay _getThemeDisplay() throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		Company company = _companyLocalService.getCompany(
			_group.getCompanyId());

		company.setVirtualHostname(_VIRTUAL_HOSTNAME);

		themeDisplay.setCompany(company);

		themeDisplay.setLayoutSet(_layout.getLayoutSet());
		themeDisplay.setPermissionChecker(
			PermissionThreadLocal.getPermissionChecker());
		themeDisplay.setPlid(_layout.getPlid());
		themeDisplay.setScopeGroupId(_group.getGroupId());
		themeDisplay.setSecure(false);
		themeDisplay.setServerName(_VIRTUAL_HOSTNAME);
		themeDisplay.setServerPort(_SERVER_PORT);
		themeDisplay.setSiteGroupId(_group.getGroupId());

		return themeDisplay;
	}

	private void _testWithLayoutSEOCompanyConfiguration(
			UnsafeRunnable<Exception> unsafeRunnable)
		throws Exception {

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					_LAYOUT_SEO_CONFIGURATION_PID,
					HashMapDictionaryBuilder.<String, Object>put(
						"canonicalURL", "localized-url"
					).build())) {

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
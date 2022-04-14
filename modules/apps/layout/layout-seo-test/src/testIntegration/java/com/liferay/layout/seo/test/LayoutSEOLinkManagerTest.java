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
import com.liferay.layout.test.util.LayoutFriendlyURLRandomizerBumper;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletRenderResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Cristina González
 */
@RunWith(Arquillian.class)
public class LayoutSEOLinkManagerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		LayoutTestUtil.addTypePortletLayout(_group);
	}

	@Test
	public void testGetClassicLocalizedLayoutSEOLinksWithDefaultLocale()
		throws Exception {

		_setupForTestingPageLocalizedLayoutSEOLinks();

		_testWithLayoutSEOCompanyConfiguration(
			"default-language-url",
			() -> _testWithSiteDefaultLanguage(
				_layout.getGroupId(), LocaleUtil.US,
				() -> _assertPageLocalizedLayoutSEOLinks(
					LocaleUtil.US, "default-language-url")));
	}

	@Test
	public void testGetClassicLocalizedLayoutSEOLinksWithNoDefaultLocale()
		throws Exception {

		_setupForTestingPageLocalizedLayoutSEOLinks();

		_testWithLayoutSEOCompanyConfiguration(
			"default-language-url",
			() -> _testWithSiteDefaultLanguage(
				_layout.getGroupId(), LocaleUtil.US,
				() -> _assertPageLocalizedLayoutSEOLinks(
					LocaleUtil.SPAIN, "default-language-url")));
	}

	@Test
	public void testGetDefaultLocalizedLayoutSEOLinksWithDefaultLocale()
		throws Exception {

		_setupForTestingPageLocalizedLayoutSEOLinks();

		_testWithLayoutSEOCompanyConfiguration(
			"localized-url",
			() -> _testWithSiteDefaultLanguage(
				_layout.getGroupId(), LocaleUtil.US,
				() -> _assertPageLocalizedLayoutSEOLinks(
					LocaleUtil.US, "localized-url")));
	}

	@Test
	public void testGetDefaultLocalizedLayoutSEOLinksWithNoDefaultLocale()
		throws Exception {

		_setupForTestingPageLocalizedLayoutSEOLinks();

		_testWithLayoutSEOCompanyConfiguration(
			"localized-url",
			() -> _testWithSiteDefaultLanguage(
				_layout.getGroupId(), LocaleUtil.US,
				() -> _assertPageLocalizedLayoutSEOLinks(
					LocaleUtil.SPAIN, "localized-url")));
	}

	private void _assertAlternateLayoutSEOLink(
		Locale locale, List<LayoutSEOLink> layoutSEOLinks, String urlPrefix) {

		LayoutSEOLink layoutSEOLink = _getAlternateLayoutSEOLink(
			locale, layoutSEOLinks);

		Assert.assertNotNull(layoutSEOLink);

		Assert.assertEquals(
			_getExpectedAlternateURL(locale, urlPrefix),
			layoutSEOLink.getHref());
		Assert.assertEquals(
			LocaleUtil.toW3cLanguageId(locale), layoutSEOLink.getHrefLang());
		Assert.assertEquals(
			layoutSEOLink.getRelationship(),
			LayoutSEOLink.Relationship.ALTERNATE);
	}

	private void _assertAlternateLayoutSEOLinks(
		List<LayoutSEOLink> layoutSEOLinks, String urlPrefix) {

		for (Locale locale : _expectedFriendlyURLs.keySet()) {
			_assertAlternateLayoutSEOLink(locale, layoutSEOLinks, urlPrefix);
		}

		_assertXDefaultAlternateLayoutSEOLink(layoutSEOLinks);
	}

	private void _assertCanonicalLayoutSEOLink(
		List<LayoutSEOLink> layoutSEOLinks, Locale locale,
		String canonicalURLConfiguration, String urlPrefix) {

		String canonicalURL = _getExpectedCanonicalURL(
			locale, canonicalURLConfiguration, urlPrefix);

		LayoutSEOLink layoutSEOLink = _getCanonicalLayoutSEOLink(
			layoutSEOLinks);

		Assert.assertNotNull(canonicalURL);
		Assert.assertEquals(canonicalURL, layoutSEOLink.getHref());
		Assert.assertNull(layoutSEOLink.getHrefLang());
		Assert.assertEquals(
			layoutSEOLink.getRelationship(),
			LayoutSEOLink.Relationship.CANONICAL);
	}

	private void _assertLocalizedLayoutSEOLinks(
			Locale locale, String canonicalURLConfiguration, String urlPrefix)
		throws PortalException {

		List<LayoutSEOLink> layoutSEOLinks =
			_layoutSEOLinkManager.getLocalizedLayoutSEOLinks(
				_layout, locale, _canonicalURL, _expectedFriendlyURLs.keySet());

		Assert.assertEquals(
			layoutSEOLinks.toString(), _expectedFriendlyURLs.size() + 2,
			layoutSEOLinks.size());

		_assertAlternateLayoutSEOLinks(layoutSEOLinks, urlPrefix);

		_assertCanonicalLayoutSEOLink(
			layoutSEOLinks, locale, canonicalURLConfiguration, urlPrefix);
	}

	private void _assertPageLocalizedLayoutSEOLinks(
			Locale locale, String canonicalURLConfiguration)
		throws PortalException {

		_assertLocalizedLayoutSEOLinks(
			locale, canonicalURLConfiguration, StringPool.SLASH);
	}

	private void _assertXDefaultAlternateLayoutSEOLink(
		List<LayoutSEOLink> layoutSEOLinks) {

		LayoutSEOLink layoutSEOLink = _getXDefaultAlternateLayoutSEOLink(
			layoutSEOLinks);

		Assert.assertNotNull(layoutSEOLink);
		Assert.assertEquals(_canonicalURL, layoutSEOLink.getHref());
		Assert.assertEquals("x-default", layoutSEOLink.getHrefLang());
		Assert.assertEquals(
			layoutSEOLink.getRelationship(),
			LayoutSEOLink.Relationship.ALTERNATE);
	}

	private LayoutSEOLink _getAlternateLayoutSEOLink(
		Locale locale, List<LayoutSEOLink> layoutSEOLinks) {

		for (LayoutSEOLink layoutSEOLink : layoutSEOLinks) {
			String hrefLang = layoutSEOLink.getHrefLang();

			if ((layoutSEOLink.getRelationship() ==
					LayoutSEOLink.Relationship.ALTERNATE) &&
				(hrefLang != null) &&
				Objects.equals(hrefLang, LocaleUtil.toW3cLanguageId(locale))) {

				return layoutSEOLink;
			}
		}

		return null;
	}

	private LayoutSEOLink _getCanonicalLayoutSEOLink(
		List<LayoutSEOLink> layoutSEOLinks) {

		for (LayoutSEOLink layoutSEOLink : layoutSEOLinks) {
			if (layoutSEOLink.getRelationship() ==
					LayoutSEOLink.Relationship.CANONICAL) {

				return layoutSEOLink;
			}
		}

		return null;
	}

	private String _getExpectedAlternateURL(Locale locale, String urlPrefix) {
		String expectedLanguagePath = StringPool.BLANK;

		if (!Objects.equals(LocaleUtil.US, locale)) {
			expectedLanguagePath = StringPool.SLASH + locale.getLanguage();
		}

		return StringBundler.concat(
			_PORTAL_URL, expectedLanguagePath, _groupFriendlyURL, urlPrefix,
			_expectedFriendlyURLs.get(locale));
	}

	private String _getExpectedCanonicalURL(
		Locale locale, String canonicalURLConfiguration, String urlPrefix) {

		Locale canonicalLocale = locale;

		if (Objects.equals(canonicalURLConfiguration, "default-language-url")) {
			canonicalLocale = LocaleUtil.getDefault();
		}

		return _getExpectedAlternateURL(canonicalLocale, urlPrefix);
	}

	private HttpServletRequest _getHttpServletRequest(Layout layout)
		throws Exception {

		MockHttpServletRequest httpServletRequest =
			new MockHttpServletRequest();

		httpServletRequest.setAttribute(
			JavaConstants.JAVAX_PORTLET_RESPONSE,
			new MockLiferayPortletRenderResponse());

		_themeDisplay = new ThemeDisplay();

		_themeDisplay.setCompany(
			_companyLocalService.getCompany(layout.getCompanyId()));
		_themeDisplay.setLayout(layout);

		LayoutSet layoutSet = _group.getPublicLayoutSet();

		_themeDisplay.setLayoutSet(layoutSet);
		_themeDisplay.setLookAndFeel(
			layoutSet.getTheme(), layoutSet.getColorScheme());

		_themeDisplay.setPortalDomain("localhost");
		_themeDisplay.setRealUser(TestPropsValues.getUser());
		_themeDisplay.setRequest(httpServletRequest);
		_themeDisplay.setResponse(new MockHttpServletResponse());
		_themeDisplay.setScopeGroupId(_group.getGroupId());
		_themeDisplay.setServerName("localhost");
		_themeDisplay.setSiteGroupId(_group.getGroupId());
		_themeDisplay.setUser(TestPropsValues.getUser());

		httpServletRequest.setAttribute(WebKeys.THEME_DISPLAY, _themeDisplay);

		return httpServletRequest;
	}

	private LayoutSEOLink _getXDefaultAlternateLayoutSEOLink(
		List<LayoutSEOLink> layoutSEOLinks) {

		for (LayoutSEOLink layoutSEOLink : layoutSEOLinks) {
			String hrefLang = layoutSEOLink.getHrefLang();

			if ((hrefLang != null) && Objects.equals(hrefLang, "x-default")) {
				return layoutSEOLink;
			}
		}

		return null;
	}

	private void _setupForTestingPageLocalizedLayoutSEOLinks()
		throws Exception {

		_layout = LayoutTestUtil.addTypePortletLayout(_group);

		for (Map.Entry<Locale, String> entry :
				_expectedFriendlyURLs.entrySet()) {

			_layout = _layoutLocalService.updateFriendlyURL(
				TestPropsValues.getUserId(), _layout.getPlid(),
				StringPool.SLASH + entry.getValue(),
				LocaleUtil.toLanguageId(entry.getKey()));
		}

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setRequest(_getHttpServletRequest(_layout));

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		_groupFriendlyURL = _portal.getGroupFriendlyURL(
			_group.getPublicLayoutSet(), _themeDisplay, false, false);

		_canonicalURL = StringBundler.concat(
			_PORTAL_URL, _groupFriendlyURL, StringPool.SLASH,
			_expectedFriendlyURLs.get(LocaleUtil.US));
	}

	private void _testWithLayoutSEOCompanyConfiguration(
			String canonicalURL, UnsafeRunnable<Exception> unsafeRunnable)
		throws Exception {

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					_LAYOUT_SEO_CONFIGURATION_PID,
					HashMapDictionaryBuilder.<String, Object>put(
						"canonicalURL", canonicalURL
					).build())) {

			unsafeRunnable.run();
		}
	}

	private void _testWithSiteDefaultLanguage(
			long groupId, Locale locale,
			UnsafeRunnable<Exception> unsafeRunnable)
		throws Exception {

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		UnicodeProperties unicodeProperties = group.getTypeSettingsProperties();

		String oldLanguageId = unicodeProperties.getProperty("languageId");

		try {
			unicodeProperties.setProperty(
				"languageId", LocaleUtil.toLanguageId(locale));

			GroupLocalServiceUtil.updateGroup(
				groupId, unicodeProperties.toString());

			unsafeRunnable.run();
		}
		finally {
			unicodeProperties.setProperty("languageId", oldLanguageId);

			GroupLocalServiceUtil.updateGroup(
				groupId, unicodeProperties.toString());
		}
	}

	private static final String _LAYOUT_SEO_CONFIGURATION_PID =
		"com.liferay.layout.seo.internal.configuration." +
			"LayoutSEOCompanyConfiguration";

	private static final String _PORTAL_URL = "http://localhost:8080";

	private String _canonicalURL;

	@Inject
	private CompanyLocalService _companyLocalService;

	private final Map<Locale, String> _expectedFriendlyURLs =
		HashMapBuilder.put(
			LocaleUtil.GERMANY,
			FriendlyURLNormalizerUtil.normalize(
				RandomTestUtil.randomString(
					LayoutFriendlyURLRandomizerBumper.INSTANCE))
		).put(
			LocaleUtil.SPAIN,
			FriendlyURLNormalizerUtil.normalize(
				RandomTestUtil.randomString(
					LayoutFriendlyURLRandomizerBumper.INSTANCE))
		).put(
			LocaleUtil.US,
			FriendlyURLNormalizerUtil.normalize(
				RandomTestUtil.randomString(
					LayoutFriendlyURLRandomizerBumper.INSTANCE))
		).build();
	private Group _group;
	private String _groupFriendlyURL;
	private Layout _layout;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private LayoutSEOLinkManager _layoutSEOLinkManager;

	@Inject
	private Portal _portal;

	private ThemeDisplay _themeDisplay;

}
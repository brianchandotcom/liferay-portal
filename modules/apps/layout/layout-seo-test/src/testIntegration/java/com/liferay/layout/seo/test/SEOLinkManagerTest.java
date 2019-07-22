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
import com.liferay.layout.seo.kernel.SEOLink;
import com.liferay.layout.seo.kernel.SEOLinkManager;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Cristina González
 */
@RunWith(Arquillian.class)
public class SEOLinkManagerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_layout = _layoutLocalService.getLayout(TestPropsValues.getPlid());
	}

	@Test
	public void testGetClassicLocalizedSEOLinksWithDefaultLocale()
		throws Exception {

		_testWithSEOCompanyConfiguration(
			"default-language-url",
			() -> {
				List<SEOLink> seoLinks = _seoLinkManager.getLocalizedSEOLinks(
					_layout, LocaleUtil.US, _CANONICAL_URL, _alternateURLs);

				Assert.assertEquals(
					seoLinks.toString(), _alternateURLs.size() + 2,
					seoLinks.size());

				_assertCanonicalSEOLink(seoLinks, _CANONICAL_URL);

				_assertAlternateSEOLinks(seoLinks, _alternateURLs);
			});
	}

	@Test
	public void testGetClassicLocalizedSEOLinksWithNoDefaultLocale()
		throws Exception {

		_testWithSEOCompanyConfiguration(
			"default-language-url",
			() -> {
				List<SEOLink> seoLinks = _seoLinkManager.getLocalizedSEOLinks(
					_layout, LocaleUtil.SPAIN, _CANONICAL_URL, _alternateURLs);

				Assert.assertEquals(
					seoLinks.toString(), _alternateURLs.size() + 2,
					seoLinks.size());

				_assertCanonicalSEOLink(seoLinks, _CANONICAL_URL);

				_assertAlternateSEOLinks(seoLinks, _alternateURLs);
			});
	}

	@Test
	public void testGetDefaultLocalizedSEOLinksWithDefaultLocale()
		throws Exception {

		_testWithSEOCompanyConfiguration(
			"localized-url",
			() -> {
				List<SEOLink> seoLinks = _seoLinkManager.getLocalizedSEOLinks(
					_layout, LocaleUtil.US, _CANONICAL_URL, _alternateURLs);

				Assert.assertEquals(
					seoLinks.toString(), _alternateURLs.size() + 2,
					seoLinks.size());

				_assertAlternateSEOLinks(seoLinks, _alternateURLs);
				_assertCanonicalSEOLink(seoLinks, _CANONICAL_URL);
			});
	}

	@Test
	public void testGetDefaultLocalizedSEOLinksWithNoDefaultLocale()
		throws Exception {

		_testWithSEOCompanyConfiguration(
			"localized-url",
			() -> {
				List<SEOLink> seoLinks = _seoLinkManager.getLocalizedSEOLinks(
					_layout, LocaleUtil.SPAIN, _CANONICAL_URL, _alternateURLs);

				Assert.assertEquals(
					seoLinks.toString(), _alternateURLs.size() + 2,
					seoLinks.size());

				_assertAlternateSEOLinks(seoLinks, _alternateURLs);
				_assertCanonicalSEOLink(
					seoLinks, _alternateURLs.get(LocaleUtil.SPAIN));
			});
	}

	private void _assertAlternateSEOLink(
		Locale locale, List<SEOLink> seoLinks,
		Map<Locale, String> alternateURLs) {

		SEOLink seoLink = _getAlternateSEOLink(locale, seoLinks);

		Assert.assertNotNull(seoLink);
		Assert.assertEquals(alternateURLs.get(locale), seoLink.getHref());
		Assert.assertEquals(
			LocaleUtil.toW3cLanguageId(locale), seoLink.getHrefLang());
		Assert.assertEquals(
			seoLink.getRelationship(), SEOLink.Relationship.ALTERNATE);
	}

	private void _assertAlternateSEOLinks(
		List<SEOLink> seoLinks, Map<Locale, String> alternateURLs) {

		for (Locale locale : alternateURLs.keySet()) {
			_assertAlternateSEOLink(locale, seoLinks, alternateURLs);
		}

		_assertXDefaultAlternateSEOLink(seoLinks, alternateURLs);
	}

	private void _assertCanonicalSEOLink(
		List<SEOLink> seoLinks, String canonicalURL) {

		SEOLink seoLink = _getCanonicalSEOLink(seoLinks);

		Assert.assertNotNull(canonicalURL);
		Assert.assertEquals(canonicalURL, seoLink.getHref());
		Assert.assertNull(seoLink.getHrefLang());
		Assert.assertEquals(
			seoLink.getRelationship(), SEOLink.Relationship.CANONICAL);
	}

	private void _assertXDefaultAlternateSEOLink(
		List<SEOLink> seoLinks, Map<Locale, String> alternateURLs) {

		SEOLink seoLink = _getXDefaultAlternateSEOLink(seoLinks);

		Assert.assertNotNull(seoLink);
		Assert.assertEquals(
			alternateURLs.get(LocaleUtil.getDefault()), seoLink.getHref());
		Assert.assertEquals("x-default", seoLink.getHrefLang());
		Assert.assertEquals(
			seoLink.getRelationship(), SEOLink.Relationship.ALTERNATE);
	}

	private SEOLink _getAlternateSEOLink(
		Locale locale, List<SEOLink> seoLinks) {

		for (SEOLink seoLink : seoLinks) {
			String hrefLang = seoLink.getHrefLang();

			if ((seoLink.getRelationship() == SEOLink.Relationship.ALTERNATE) &&
				(hrefLang != null) &&
				Objects.equals(hrefLang, LocaleUtil.toW3cLanguageId(locale))) {

				return seoLink;
			}
		}

		return null;
	}

	private SEOLink _getCanonicalSEOLink(List<SEOLink> seoLinks) {
		for (SEOLink seoLink : seoLinks) {
			if (seoLink.getRelationship() == SEOLink.Relationship.CANONICAL) {
				return seoLink;
			}
		}

		return null;
	}

	private SEOLink _getXDefaultAlternateSEOLink(List<SEOLink> seoLinks) {
		for (SEOLink seoLink : seoLinks) {
			String hrefLang = seoLink.getHrefLang();

			if ((hrefLang != null) && Objects.equals(hrefLang, "x-default")) {
				return seoLink;
			}
		}

		return null;
	}

	private void _testWithSEOCompanyConfiguration(
			String canonicalURL, UnsafeRunnable<Exception> unsafeRunnable)
		throws Exception {

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					_SEO_CONFIGURATION_PID,
					new HashMapDictionary<String, Object>() {
						{
							put("canonicalURL", canonicalURL);
						}
					})) {

			unsafeRunnable.run();
		}
	}

	private static final String _CANONICAL_URL = "canonicalURL";

	private static final String _SEO_CONFIGURATION_PID =
		"com.liferay.layout.seo.internal.configuration.SEOCompanyConfiguration";

	private final Map<Locale, String> _alternateURLs =
		new HashMap<Locale, String>() {
			{
				put(LocaleUtil.GERMAN, "germanURL");
				put(LocaleUtil.SPAIN, "spanishURL");
				put(LocaleUtil.US, _CANONICAL_URL);
			}
		};
	private Layout _layout;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private SEOLinkManager _seoLinkManager;

}
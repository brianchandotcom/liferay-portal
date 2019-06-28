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

package com.liferay.seo.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.seo.SEO;
import com.liferay.portal.kernel.seo.SEOLink;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Cristina González
 */
@RunWith(Arquillian.class)
public class SEOTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testGetLocalizedSEOLinks() throws Exception {
		List<SEOLink> seoLinks = _seo.getLocalizedSEOLinks(
			TestPropsValues.getCompanyId(), _CANONICAL_URL, _alternateURLs);

		Assert.assertEquals(
			seoLinks.toString(), _alternateURLs.size() + 2, seoLinks.size());

		_assertCanonicalSEOLink(seoLinks, _CANONICAL_URL);

		_assertAlternateSEOLinks(seoLinks, _alternateURLs);
	}

	private void _assertAlternateSEOLink(
		Locale locale, List<SEOLink> seoLinks,
		Map<Locale, String> alternateURLs) {

		SEOLink.AlternateSEOLink spainAlternateSEOLink = _getAlternateSEOLink(
			locale, seoLinks);

		Assert.assertNotNull(spainAlternateSEOLink);

		Assert.assertEquals(
			alternateURLs.get(locale), spainAlternateSEOLink.getHref());
		Assert.assertEquals(
			spainAlternateSEOLink.getSEOLinkDataSennaTrack(),
			SEOLink.SEOLinkDataSennaTrack.TEMPORARY);
		Assert.assertEquals(
			Optional.of(LocaleUtil.toW3cLanguageId(locale)),
			spainAlternateSEOLink.getHrefLang());
		Assert.assertEquals(
			spainAlternateSEOLink.getSeoLinkRel(),
			SEOLink.SEOLinkRel.ALTERNATE);
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

		SEOLink.CanonicalSEOLink canonicalSEOLink = _getCanonicalSEOLink(
			seoLinks);

		Assert.assertNotNull(canonicalURL);

		Assert.assertEquals(canonicalURL, canonicalSEOLink.getHref());
		Assert.assertEquals(
			canonicalSEOLink.getSEOLinkDataSennaTrack(),
			SEOLink.SEOLinkDataSennaTrack.TEMPORARY);
		Assert.assertEquals(canonicalSEOLink.getHrefLang(), Optional.empty());
		Assert.assertEquals(
			canonicalSEOLink.getSeoLinkRel(), SEOLink.SEOLinkRel.CANONICAL);
	}

	private void _assertXDefaultAlternateSEOLink(
		List<SEOLink> seoLinks, Map<Locale, String> alternateURLs) {

		SEOLink.XDefaultAlternateSEOLink xDefaultAlternateSEOLink =
			_getXDefaultAlternateSEOLink(seoLinks);

		Assert.assertNotNull(xDefaultAlternateSEOLink);

		Assert.assertEquals(
			alternateURLs.get(LocaleUtil.getDefault()),
			xDefaultAlternateSEOLink.getHref());
		Assert.assertEquals(
			xDefaultAlternateSEOLink.getSEOLinkDataSennaTrack(),
			SEOLink.SEOLinkDataSennaTrack.TEMPORARY);
		Assert.assertEquals(
			xDefaultAlternateSEOLink.getHrefLang(), Optional.of("x-default"));
		Assert.assertEquals(
			xDefaultAlternateSEOLink.getSeoLinkRel(),
			SEOLink.SEOLinkRel.ALTERNATE);
	}

	private SEOLink.AlternateSEOLink _getAlternateSEOLink(
		Locale locale, List<SEOLink> seoLinks) {

		for (SEOLink seoLink : seoLinks) {
			Optional<String> hrefLangOptional = seoLink.getHrefLang();

			if (!hrefLangOptional.isPresent()) {
				continue;
			}

			if (seoLink.getSeoLinkRel() != SEOLink.SEOLinkRel.ALTERNATE) {
				continue;
			}

			if (Objects.equals(
					hrefLangOptional.get(),
					LocaleUtil.toW3cLanguageId(locale))) {

				return (SEOLink.AlternateSEOLink)seoLink;
			}
		}

		return null;
	}

	private SEOLink.CanonicalSEOLink _getCanonicalSEOLink(
		List<SEOLink> seoLinks) {

		for (SEOLink seoLink : seoLinks) {
			if (seoLink.getSeoLinkRel() == SEOLink.SEOLinkRel.CANONICAL) {
				return (SEOLink.CanonicalSEOLink)seoLink;
			}
		}

		return null;
	}

	private SEOLink.XDefaultAlternateSEOLink _getXDefaultAlternateSEOLink(
		List<SEOLink> seoLinks) {

		for (SEOLink seoLink : seoLinks) {
			if (seoLink instanceof SEOLink.XDefaultAlternateSEOLink) {
				return (SEOLink.XDefaultAlternateSEOLink)seoLink;
			}
		}

		return null;
	}

	private static final String _CANONICAL_URL = "canonicalURL";

	private final Map<Locale, String> _alternateURLs =
		new HashMap<Locale, String>() {
			{
				put(LocaleUtil.GERMAN, "germanURL");
				put(LocaleUtil.SPAIN, "spanishURL");
				put(LocaleUtil.US, _CANONICAL_URL);
			}
		};

	@Inject
	private SEO _seo;

}
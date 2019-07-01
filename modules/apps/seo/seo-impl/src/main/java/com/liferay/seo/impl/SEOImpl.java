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

package com.liferay.seo.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.seo.SEO;
import com.liferay.portal.kernel.seo.SEOLink;
import com.liferay.portal.kernel.util.Html;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.seo.impl.configuration.SEOCompanyConfiguration;
import com.liferay.seo.impl.configuration.SEOConfigurationConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.annotation.versioning.ProviderType;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(service = SEO.class)
@ProviderType
public class SEOImpl implements SEO {

	@Override
	public List<SEOLink> getLocalizedSEOLinks(
			long companyId, Locale locale, String canonicalURL,
			Map<Locale, String> alternateURLs)
		throws PortalException {

		SEOCompanyConfiguration seoCompanyConfiguration =
			_configurationProvider.getCompanyConfiguration(
				SEOCompanyConfiguration.class, companyId);

		if (Objects.equals(
				seoCompanyConfiguration.configuration(),
				SEOConfigurationConstants.CLASSIC)) {

			return _getClassicLocalizedSEOLinks(canonicalURL, alternateURLs);
		}

		return _getDefaultLocalizedSEOLinks(locale, canonicalURL, alternateURLs);
	}

	private List<SEOLink> _getClassicLocalizedSEOLinks(
		String canonicalURL, Map<Locale, String> alternateURLs) {

		List<SEOLink> seoLinks = new ArrayList<>();

		seoLinks.add(
			new SEOLinkImpl(
				SEOLink.SEOLinkDataSennaTrack.TEMPORARY,
				_html.escapeAttribute(canonicalURL), null,
				SEOLink.SEOLinkRel.CANONICAL));

		_addAlternateSEOLinks(alternateURLs, seoLinks::add);

		return seoLinks;
	}

	private List<SEOLink> _getDefaultLocalizedSEOLinks(
		Locale locale, String canonicalURL, Map<Locale, String> alternateURLs) {

		String localizedCanonicalURL = alternateURLs.getOrDefault(
			locale, canonicalURL);

		List<SEOLink> seoLinks = new ArrayList<>();

		seoLinks.add(
			new SEOLinkImpl(
				SEOLink.SEOLinkDataSennaTrack.TEMPORARY,
				_html.escapeAttribute(localizedCanonicalURL), null,
				SEOLink.SEOLinkRel.CANONICAL));

		_addAlternateSEOLinks(alternateURLs, seoLinks::add);

		return seoLinks;
	}

	private void _addAlternateSEOLinks(
		Map<Locale, String> alternateURLs, Consumer<SEOLink> consumer) {

		alternateURLs.forEach(
			(locale, url) ->
				consumer.accept(
					new SEOLinkImpl(
						SEOLink.SEOLinkDataSennaTrack.TEMPORARY,
						_html.escapeAttribute(url),
						LocaleUtil.toW3cLanguageId(locale),
						SEOLink.SEOLinkRel.ALTERNATE)));

		String defaultLocaleUrl = alternateURLs.get(LocaleUtil.getDefault());

		if (defaultLocaleUrl == null) {
			return;
		}

		consumer.accept(
			new SEOLinkImpl(
				SEOLink.SEOLinkDataSennaTrack.TEMPORARY,
				_html.escapeAttribute(defaultLocaleUrl), "x-default",
				SEOLink.SEOLinkRel.ALTERNATE));
	}

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private Html _html;

}
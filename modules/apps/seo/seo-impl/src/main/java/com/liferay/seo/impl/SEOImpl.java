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

			return getClassicLocalizedSEOLinks(canonicalURL, alternateURLs);
		}

		return getDefaultLocalizedSEOLinks(locale, canonicalURL, alternateURLs);
	}

	protected List<SEOLink> getClassicLocalizedSEOLinks(
		String canonicalURL, Map<Locale, String> alternateURLs) {

		return new ArrayList() {
			{
				add(
					new SEOLink.CanonicalSEOLink(
						_html.escapeAttribute(canonicalURL)));
				addAll(_getAlternateSEOLinks(alternateURLs));
			}
		};
	}

	protected List<SEOLink> getDefaultLocalizedSEOLinks(
		Locale locale, String canonicalURL, Map<Locale, String> alternateURLs) {

		String localizedCanonicalURL = alternateURLs.getOrDefault(
			locale, canonicalURL);

		return new ArrayList() {
			{
				add(
					new SEOLink.CanonicalSEOLink(
						_html.escapeAttribute(localizedCanonicalURL)));
				addAll(_getAlternateSEOLinks(alternateURLs));
			}
		};
	}

	private List<SEOLink> _getAlternateSEOLinks(
		Map<Locale, String> alternateURLs) {

		Set<Map.Entry<Locale, String>> entries = alternateURLs.entrySet();

		Stream<Map.Entry<Locale, String>> stream = entries.stream();

		return Stream.concat(
			stream.map(
				entry -> new SEOLink.AlternateSEOLink(
					_html.escapeAttribute(entry.getValue()),
					LocaleUtil.toW3cLanguageId(entry.getKey()))),
			Stream.of(
				Optional.ofNullable(
					alternateURLs.get(LocaleUtil.getDefault())
				).map(
					alternateXDefaultURL ->
						new SEOLink.XDefaultAlternateSEOLink(
							_html.escapeAttribute(alternateXDefaultURL))
				)
			).filter(
				Optional::isPresent
			).map(
				Optional::get
			)
		).collect(
			Collectors.toList()
		);
	}

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private Html _html;

}
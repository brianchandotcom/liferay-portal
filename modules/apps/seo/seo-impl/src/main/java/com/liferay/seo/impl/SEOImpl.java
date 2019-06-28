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

import com.liferay.portal.kernel.seo.SEO;
import com.liferay.portal.kernel.seo.SEOLink;
import com.liferay.portal.kernel.util.Html;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
		long companyId, String canonicalURL,
		Map<Locale, String> alternateURLs) {

		List<SEOLink> seoLinks = new ArrayList<>();

		seoLinks.add(
			new SEOLink.CanonicalSEOLink(_html.escapeAttribute(canonicalURL)));

		Locale defaultLocale = LocaleUtil.getDefault();

		for (Map.Entry<Locale, String> entry : alternateURLs.entrySet()) {
			Locale availableLocale = entry.getKey();
			String alternateURL = entry.getValue();

			if (availableLocale.equals(defaultLocale)) {
				seoLinks.add(
					new SEOLink.XDefaultAlternateSEOLink(
						_html.escapeAttribute(canonicalURL)));
			}

			seoLinks.add(
				new SEOLink.AlternateSEOLink(
					_html.escapeAttribute(alternateURL),
					LocaleUtil.toW3cLanguageId(availableLocale)));
		}

		return seoLinks;
	}

	@Reference
	private Html _html;

}
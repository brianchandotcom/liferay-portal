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

package com.liferay.questions.web.internal.seo.canonical.url;

import com.liferay.layout.seo.canonical.url.LayoutSEOCanonicalURLProvider;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Javier Gamarra
 */
@Component(
	property = "service.ranking=100",
	service = LayoutSEOCanonicalURLProvider.class
)
public class QuestionsLayoutSEOCanonicalURLProvider
	implements LayoutSEOCanonicalURLProvider {

	@Override
	public String getCanonicalURL(
			Layout layout, Locale locale, String canonicalURL,
			Map<Locale, String> alternateURLs)
		throws PortalException {

		alternateURLs.replaceAll((key, value) -> _addQuestionsURL(value));

		return _addQuestionsURL(
			_layoutSEOCanonicalURLProvider.getCanonicalURL(
				layout, locale, canonicalURL, alternateURLs));
	}

	@Override
	public Map<Locale, String> getCanonicalURLMap(
			Layout layout, ThemeDisplay themeDisplay)
		throws PortalException {

		return _layoutSEOCanonicalURLProvider.getCanonicalURLMap(
			layout, themeDisplay);
	}

	@Override
	public String getDefaultCanonicalURL(
			Layout layout, ThemeDisplay themeDisplay)
		throws PortalException {

		return _layoutSEOCanonicalURLProvider.getDefaultCanonicalURL(
			layout, themeDisplay);
	}

	private String _addQuestionsURL(String canonicalURL) {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		String currentURL = serviceContext.getCurrentURL();

		if (currentURL.contains("questions")) {
			if (canonicalURL.endsWith("/")) {
				canonicalURL = canonicalURL.substring(
					0, canonicalURL.length() - 1);
			}

			return canonicalURL + currentURL;
		}

		return canonicalURL;
	}

	@Reference(
		target = "(component.name=com.liferay.layout.seo.internal.canonical.url.LayoutSEOCanonicalURLProviderImpl)"
	)
	private LayoutSEOCanonicalURLProvider _layoutSEOCanonicalURLProvider;

}
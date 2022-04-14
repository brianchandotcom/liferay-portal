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

package com.liferay.layout.seo.internal.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * @author Lourdes Fernández Besada
 */
public class AlternateURLProvider {

	public AlternateURLProvider(Portal portal) {
		_portal = portal;
	}

	public String getAlternateURL(
			String canonicalURL, ThemeDisplay themeDisplay, Locale locale,
			Layout layout,
			FriendlyURLMapperProvider.FriendlyURLMapper friendlyURLMapper)
		throws PortalException {

		return _portal.getAlternateURL(
			friendlyURLMapper.getMappedFriendlyURL(canonicalURL, locale),
			themeDisplay, locale, layout);
	}

	public Map<Locale, String> getAlternateURLs(
			String canonicalURL, ThemeDisplay themeDisplay, Layout layout,
			Set<Locale> locales,
			FriendlyURLMapperProvider.FriendlyURLMapper friendlyURLMapper)
		throws PortalException {

		Map<Locale, String> alternateURLs = new HashMap<>();

		for (Locale locale : locales) {
			alternateURLs.put(
				locale,
				getAlternateURL(
					canonicalURL, themeDisplay, locale, layout,
					friendlyURLMapper));
		}

		return alternateURLs;
	}

	private final Portal _portal;

}
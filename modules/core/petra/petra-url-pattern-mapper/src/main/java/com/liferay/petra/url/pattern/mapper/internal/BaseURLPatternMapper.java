/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.url.pattern.mapper.internal;

import com.liferay.petra.url.pattern.mapper.URLPatternMapper;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author Carlos Sierra Andrés
 */
public abstract class BaseURLPatternMapper<T> implements URLPatternMapper<T> {

	protected boolean isExtensionURLPattern(String urlPattern) {

		// Servlet 4 spec 12.1.3
		// Servlet 4 spec 12.2

		if ((urlPattern.length() < 3) || (urlPattern.charAt(0) != '*') ||
			(urlPattern.charAt(1) != '.')) {

			return false;
		}

		for (int i = 2; i < urlPattern.length(); ++i) {
			if ((urlPattern.charAt(i) == '/') ||
				(urlPattern.charAt(i) == '.')) {

				return false;
			}
		}

		return true;
	}

	protected boolean isWildcardURLPattern(String urlPattern) {

		// Servlet 4 spec 12.2

		if ((urlPattern.length() < 2) || (urlPattern.charAt(0) != '/') ||
			(urlPattern.charAt(urlPattern.length() - 1) != '*') ||
			(urlPattern.charAt(urlPattern.length() - 2) != '/')) {

			return false;
		}

		// RFC 3986 3.3

		try {
			String urlPath = urlPattern.substring(0, urlPattern.length() - 1);

			URI uri = new URI("https://test" + urlPath);

			if (!urlPath.contentEquals(uri.getPath())) {
				return false;
			}
		}
		catch (URISyntaxException uriSyntaxException) {
			return false;
		}

		return true;
	}

	protected abstract void put(String urlPattern, T value)
		throws IllegalArgumentException;

}
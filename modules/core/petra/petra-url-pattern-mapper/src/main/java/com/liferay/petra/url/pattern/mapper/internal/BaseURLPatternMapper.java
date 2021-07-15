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
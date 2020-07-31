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

package com.liferay.portal.remote.cors.internal.url.pattern.matcher;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author Arthur Chan
 * @author Carlos Sierra Andrés
 */
public interface URLPatternMatcher<T> {

	/**
	 *  https://download.oracle.com/otndocs/jcp/servlet-4-final-eval-spec/index.html#12.1.3
	 *  https://download.oracle.com/otndocs/jcp/servlet-4-final-eval-spec/index.html#12.2
	 *
	 * @param urlPattern the given urlPattern
	 * @return a boolean value indicating if the urlPattern an extensionURLPattern
	 */
	public static boolean isExtensionURLPattern(String urlPattern) {
		if ((urlPattern.length() < 3) || (urlPattern.charAt(0) != '*') ||
			(urlPattern.charAt(1) != '.')) {

			return false;
		}

		for (int i = 2; i < urlPattern.length(); ++i) {
			if (urlPattern.charAt(i) == '/') {
				return false;
			}

			if (urlPattern.charAt(i) == '.') {
				return false;
			}
		}

		return true;
	}

	/**
	 *  https://tools.ietf.org/html/rfc3986#section-3.3
	 *  https://download.oracle.com/otndocs/jcp/servlet-4-final-eval-spec/index.html#12.2
	 *
	 * @param urlPattern the given urlPattern
	 * @return a boolean value indicating if the urlPattern a wildCardURLPattern
	 */
	public static boolean isWildcardURLPattern(String urlPattern) {
		if ((urlPattern.length() < 2) || (urlPattern.charAt(0) != '/') ||
			(urlPattern.charAt(urlPattern.length() - 1) != '*') ||
			(urlPattern.charAt(urlPattern.length() - 2) != '/')) {

			return false;
		}

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

	/**
	 * https://download.oracle.com/otndocs/jcp/servlet-4-final-eval-spec/index.html#12.1
	 *
	 * @param urlPath a legal urlPath from a URL
	 * @return the matched pattern
	 */
	public T getValue(String urlPath);

	/**
	 * https://download.oracle.com/otndocs/jcp/servlet-4-final-eval-spec/index.html#12.2
	 *
	 * @param urlPattern the URL pattern, used for URL pattern matching
	 * @param value an non null object associated with urlPattern
	 */
	public void putValue(String urlPattern, T value)
		throws IllegalArgumentException;

}
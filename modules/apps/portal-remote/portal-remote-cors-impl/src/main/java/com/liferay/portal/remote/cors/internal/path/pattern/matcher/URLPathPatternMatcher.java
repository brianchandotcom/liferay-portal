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

package com.liferay.portal.remote.cors.internal.path.pattern.matcher;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author Arthur Chan
 * @author Carlos Sierra Andrés
 */
public interface URLPathPatternMatcher<T> {

	public static final byte ASCII_CHARACTER_RANGE = 96;

	public static final byte ASCII_PRINTABLE_OFFSET = 32;

	/**
	 *  https://download.oracle.com/otndocs/jcp/servlet-4-final-eval-spec/index.html#12.1.3
	 *  https://download.oracle.com/otndocs/jcp/servlet-4-final-eval-spec/index.html#12.2
	 *
	 * @param urlPathPattern the given urlPathPattern
	 * @return a boolean value indicating if the urlPathPattern a valid extensionPattern
	 */
	public static boolean isValidExtensionPattern(String urlPathPattern) {
		if ((urlPathPattern.length() < 3) ||
			(urlPathPattern.charAt(0) != '*') ||
			(urlPathPattern.charAt(1) != '.')) {

			return false;
		}

		for (int i = 2; i < urlPathPattern.length(); ++i) {
			if (urlPathPattern.charAt(i) == '/') {
				return false;
			}

			if (urlPathPattern.charAt(i) == '.') {
				return false;
			}
		}

		return true;
	}

	/**
	 *  https://tools.ietf.org/html/rfc3986#section-3.3
	 *  https://download.oracle.com/otndocs/jcp/servlet-4-final-eval-spec/index.html#12.2
	 *
	 * @param urlPathPattern the given urlPathPattern
	 * @return a boolean value indicating if the urlPathPattern a valid wildCardPattern
	 */
	public static boolean isValidWildCardPattern(String urlPathPattern) {
		if ((urlPathPattern.length() < 2) ||
			(urlPathPattern.charAt(0) != '/') ||
			(urlPathPattern.charAt(urlPathPattern.length() - 1) != '*') ||
			(urlPathPattern.charAt(urlPathPattern.length() - 2) != '/')) {

			return false;
		}

		try {
			String urlPath = urlPathPattern.substring(
				0, urlPathPattern.length() - 1);

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
	 * @param urlPathPattern the pattern of urlPath, used for pattern matching
	 * @param value an non null object associated with urlPathPattern
	 */
	public void putValue(String urlPathPattern, T value)
		throws IllegalArgumentException;

}
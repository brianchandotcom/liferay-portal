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

import java.util.List;

/**
 * @author Arthur Chan
 * @author Carlos Sierra Andrés
 */
public abstract class PathPatternMatcher<T> {

	/**
	 * https://download.oracle.com/otndocs/jcp/servlet-4-final-eval-spec/index.html#12.1
	 *
	 * Get the matching pattern of the given path, following the order of:
	 * 1. Exact matching pattern
	 * 2. Wild card matching the longest pattern
	 * 3. Extension pattern
	 * 4. Default pattern
	 *
	 * @param path a legal path from a URL
	 * @return the matched pattern
	 */
	public abstract PatternTuple<T> getPatternTuple(String path);

	/**
	 * https://download.oracle.com/otndocs/jcp/servlet-4-final-eval-spec/index.html#12.1
	 *
	 * Get all matching patterns of the given path, including:
	 * 1. Exact matching pattern
	 * 2. Wild card matching patterns
	 * 3. Extension pattern
	 *
	 * @param path a legal path from a URL
	 * @return all the matched patterns
	 */
	public abstract List<PatternTuple<T>> getPatternTuples(String path);

	/**
	 * https://download.oracle.com/otndocs/jcp/servlet-4-final-eval-spec/index.html#12.2
	 *
	 * In the Web application deployment descriptor, the following syntax is
	 * used to define mappings:
	 * 1. Wild Card path pattern 1:
	 *        A string beginning with a ' / ' character and ending with a ' /* '
	 *        suffix is used for path mapping.
	 * 2. Wild Card path pattern 2, aka extension matching:
	 *        A string beginning with a ' *. ' prefix is used as an extension
	 *        mapping.
	 * 3. Special path pattern 1:
	 *        The empty string ("") is a special URL pattern that exactly maps
	 *        to the application's context root, i.e., requests of the form
	 *        http://host:port/'<'context-root'>'/. In this case the path info is
	 *        ' / ' and the servlet path and context path is empty string ("").
	 * 4. Special path pattern 2:
	 *        A string containing only the ' / ' character indicates the
	 *        "default" servlet of the application. In this case the servlet
	 *        path is the request URI minus the context path and the path info
	 *        is null.
	 * 5. Exact path pattern:
	 *        All other strings are used for exact matches only.
	 *
	 * @param pathPattern the pattern of path, used for pattern matching
	 * @param value an non null object associated with pathPattern
	 */
	public abstract void insert(String pathPattern, T value)
		throws IllegalArgumentException;

	/**
	 * A valid ExtensionPattern:
	 * 1. Without the leading '*', it abides by the format of a segment of path
	 *         of URI specification: https://tools.ietf.org/html/rfc3986#section-3.3
	 * 2. It also abides by:
	 *         https://download.oracle.com/otndocs/jcp/servlet-4-final-eval-spec/index.html#12.1.3, and
	 *         https://download.oracle.com/otndocs/jcp/servlet-4-final-eval-spec/index.html#12.2
	 *
	 * @param pathPattern the given pathPattern
	 * @return a boolean value indicating if the pathPattern a valid extensionPattern
	 */
	protected boolean isValidExtensionPattern(String pathPattern) {
		if ((pathPattern.length() < 3) || (pathPattern.charAt(0) != '*') ||
			(pathPattern.charAt(1) != '.')) {

			return false;
		}

		for (int i = 2; i < pathPattern.length(); ++i) {
			if (pathPattern.charAt(i) == '/') {
				return false;
			}

			if (pathPattern.charAt(i) == '.') {
				return false;
			}
		}

		return true;
	}

	/**
	 * A valid WildCardPattern:
	 * 1. Without the trailing '*', it abides by the format of path of URI specification:
	 *         https://tools.ietf.org/html/rfc3986#section-3.3
	 * 2. It also abides by:
	 *         https://download.oracle.com/otndocs/jcp/servlet-4-final-eval-spec/index.html#12.2
	 *
	 * @param pathPattern the given pathPattern
	 * @return a boolean value indicating if the pathPattern a valid wildCardPattern
	 */
	protected boolean isValidWildCardPattern(String pathPattern) {
		if ((pathPattern.length() < 2) || (pathPattern.charAt(0) != '/') ||
			(pathPattern.charAt(pathPattern.length() - 1) != '*') ||
			(pathPattern.charAt(pathPattern.length() - 2) != '/')) {

			return false;
		}

		try {
			String path = pathPattern.substring(0, pathPattern.length() - 1);

			URI uri = new URI("https://test" + path);

			if (!path.contentEquals(uri.getPath())) {
				return false;
			}
		}
		catch (URISyntaxException uriSyntaxException) {
			return false;
		}

		return true;
	}

	/**
	 * Total number of printable ASCII characters
	 * Index from 32(space) to 127(delete)
	 */
	protected static final byte ASCII_CHARACTER_RANGE = 96;

	/**
	 * Total number of printable ASCII characters
	 * Index from 32(space) to 127(delete)
	 */
	protected static final byte ASCII_PRINTABLE_OFFSET = 32;

}
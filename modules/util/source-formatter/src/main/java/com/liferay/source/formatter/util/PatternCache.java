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

package com.liferay.source.formatter.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

/**
 * @author Peter Shin
 */
public class PatternCache {

	public static Pattern get(String regex) {
		return get(regex, null);
	}

	public static Pattern get(String regex, Integer flags) {
		String key = _getKey(regex, flags);

		Pattern pattern = _patterns.get(key);

		if (pattern != null) {
			return pattern;
		}

		if (flags != null) {
			pattern = Pattern.compile(regex, flags);
		}
		else {
			pattern = Pattern.compile(regex);
		}

		_patterns.put(key, pattern);

		return pattern;
	}

	private static String _getKey(String regex, Integer flags) {
		if (flags == null) {
			return regex;
		}

		return regex.concat("[[").concat(String.valueOf(flags));
	}

	private static final Map<String, Pattern> _patterns =
		new ConcurrentHashMap<>();

}
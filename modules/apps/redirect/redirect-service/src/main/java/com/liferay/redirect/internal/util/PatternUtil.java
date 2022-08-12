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

package com.liferay.redirect.internal.util;

import com.liferay.petra.string.StringPool;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author Adolfo Pérez
 */
public class PatternUtil {

	public static Map<Pattern, String> parsePatterns(String[] patterns) {
		Map<Pattern, String> parsedPatterns = new LinkedHashMap<>();

		for (String pattern : patterns) {
			String[] patternReplacementArray = pattern.split("\\s+", 2);

			if ((patternReplacementArray.length != 2) ||
				patternReplacementArray[0].isEmpty() ||
				patternReplacementArray[1].isEmpty()) {

				continue;
			}

			parsedPatterns.put(
				Pattern.compile(_normalizePattern(patternReplacementArray[0])),
				patternReplacementArray[1]);
		}

		return parsedPatterns;
	}

	private static String _normalizePattern(String pattern) {
		if (pattern.startsWith(StringPool.CARET)) {
			return pattern;
		}

		return StringPool.CARET + pattern;
	}

}
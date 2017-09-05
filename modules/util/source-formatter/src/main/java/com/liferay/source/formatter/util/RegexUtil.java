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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Peter Shin
 */
public class RegexUtil {

	public static Matcher getMatcher(CharSequence input, String regex) {
		return getMatcher(input, regex, null);
	}

	public static Matcher getMatcher(
		CharSequence input, String regex, Integer flags) {

		Pattern pattern = getPattern(regex, flags);

		return pattern.matcher(input);
	}

	public static Pattern getPattern(String regex) {
		return getPattern(regex, null);
	}

	public static Pattern getPattern(String regex, Integer flags) {
		return PatternCache.get(regex, flags);
	}

	public static boolean matches(CharSequence input, String regex) {
		return matches(input, regex, null);
	}

	public static boolean matches(
		CharSequence input, String regex, Integer flags) {

		Matcher matcher = getMatcher(input, regex, flags);

		return matcher.matches();
	}

}
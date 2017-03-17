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

package com.liferay.source.formatter.checks.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JSPSourceUtil {

	public static boolean isJavaSource(String content, int pos) {
		String s = content.substring(pos);

		Matcher matcher = _javaEndTagPattern.matcher(s);

		if (!matcher.find()) {
			return false;
		}

		s = s.substring(0, matcher.start());

		matcher = _javaStartTagPattern.matcher(s);

		if (!matcher.find()) {
			return true;
		}

		return false;
	}

	private static final Pattern _javaEndTagPattern = Pattern.compile(
		"[\n\t]%>(\n|\\Z)");
	private static final Pattern _javaStartTagPattern = Pattern.compile(
		"[\n\t]<%\\!?\n");

}
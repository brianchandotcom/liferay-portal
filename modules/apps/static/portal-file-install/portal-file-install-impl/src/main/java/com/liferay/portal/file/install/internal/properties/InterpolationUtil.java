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

package com.liferay.portal.file.install.internal.properties;

import com.liferay.petra.string.CharPool;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Matthew Tambara
 */
public class InterpolationUtil {

	public static void performSubstitution(Map<String, String> properties) {
		for (Map.Entry<String, String> entry : properties.entrySet()) {
			String name = entry.getKey();

			properties.put(name, substVars(entry.getValue()));
		}
	}

	public static String substVars(String value)
		throws IllegalArgumentException {

		return _unescape(_substVars(value));
	}

	private static int _indexOf(String value, int fromIndex) {
		Matcher escapedOpeningCurlyMatcher = _escapedOpeningCurly.matcher(
			value);

		Matcher escapedClosingCurlyMatcher = _escapedClosingCurly.matcher(
			value);

		int escapedOpeningCurlyMatcherIndex = Integer.MAX_VALUE;

		if (escapedOpeningCurlyMatcher.find(fromIndex)) {
			escapedOpeningCurlyMatcherIndex =
				escapedOpeningCurlyMatcher.start();
		}

		int escapedClosingCurlyMatcherIndex = Integer.MAX_VALUE;

		if (escapedClosingCurlyMatcher.find(fromIndex)) {
			escapedClosingCurlyMatcherIndex =
				escapedClosingCurlyMatcher.start();
		}

		int index = Math.min(
			escapedOpeningCurlyMatcherIndex, escapedClosingCurlyMatcherIndex);

		if (index == Integer.MAX_VALUE) {
			return -1;
		}

		return index;
	}

	private static String _substVars(String value)
		throws IllegalArgumentException {

		// Assume we have a value that is something like: "leading ${foo.${bar}}
		// middle ${baz} trailing". Find the first ending "}" variable
		// delimiter which will correspond to the first deepest nested variable
		// placeholder.

		int startDelim = value.indexOf(_DELIM_START);
		int stopDelim = value.indexOf(_DELIM_STOP);

		while ((startDelim >= 0) && (stopDelim >= 0)) {
			while ((stopDelim > 0) &&
				   (value.charAt(stopDelim - 1) == _ESCAPE_CHAR)) {

				stopDelim = value.indexOf(_DELIM_STOP, stopDelim + 1);
			}

			// Find the matching starting "${" variable delimiter by looping
			// until we find a start delimiter that is greater than the stop
			// delimiter we have found

			while (stopDelim >= 0) {
				int index = value.indexOf(
					_DELIM_START, startDelim + _DELIM_START.length());

				if ((index < 0) || (index > stopDelim)) {
					break;
				}
				else if (index < stopDelim) {
					startDelim = index;
				}
			}

			if (startDelim < stopDelim) {
				break;
			}

			stopDelim = value.indexOf(_DELIM_STOP, stopDelim + 1);
			startDelim = value.indexOf(_DELIM_START);
		}

		// If we do not have a start or stop delimiter, then just return the
		// existing value

		if ((startDelim < 0) || (stopDelim < 0)) {
			return value;
		}

		// At this point, we have found a variable placeholder, so we must
		// perform a variable substitution on it. Using the start and stop
		// delimiter indices, extract the first, deepest nested variable
		// placeholder.

		String variable = value.substring(
			startDelim + _DELIM_START.length(), stopDelim);

		String substValue = null;

		// Get the value of the deepest nested variable placeholder. Try the
		// configuration properties first.

		if ((substValue == null) && (variable.length() > 0)) {
			substValue = System.getProperty(variable);
		}

		if (substValue == null) {
			substValue = "";
		}

		// Append the leading characters, the substituted value of the variable,
		// and the trailing characters to get the new value

		value =
			value.substring(0, startDelim) + substValue +
				value.substring(stopDelim + _DELIM_STOP.length());

		// Perform the substitution again since there could still be
		// substitutions to make

		value = _substVars(value);

		// Return the value

		return value;
	}

	private static String _unescape(String value) {
		value = value.replaceAll("\\" + _MARKER, "\\$");

		Matcher existingSubstVarMatcher = _existingSubstVar.matcher(value);

		if (!existingSubstVarMatcher.matches()) {
			return value;
		}

		int escape = _indexOf(value, 0);

		while ((escape >= 0) && (escape < (value.length() - 1))) {
			char c = value.charAt(escape + 1);

			if ((c == CharPool.OPEN_CURLY_BRACE) ||
				(c == CharPool.CLOSE_CURLY_BRACE) || (c == _ESCAPE_CHAR)) {

				value =
					value.substring(0, escape) + value.substring(escape + 1);
			}

			escape = _indexOf(value, escape + 1);
		}

		return value;
	}

	private InterpolationUtil() {
	}

	private static final String _DELIM_START = "${";

	private static final String _DELIM_STOP = "}";

	private static final char _ESCAPE_CHAR = '\\';

	private static final String _MARKER = "$__";

	private static final Pattern _escapedClosingCurly = Pattern.compile(
		"\\\\+\\}");
	private static final Pattern _escapedOpeningCurly = Pattern.compile(
		"\\\\+\\{");
	private static final Pattern _existingSubstVar = Pattern.compile(
		".*\\$\\\\*\\{.*\\}.*");

}
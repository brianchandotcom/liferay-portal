/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.file.install.properties;

/**
 * @author Matthew Tambara
 */
public class InterpolationUtil {

	public static String substVars(String value) {

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

			if ((substValue == null) && variable.startsWith(_LIFERAY_PREFIX)) {
				substValue = System.getenv(variable);
			}
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

		value = substVars(value);

		// Return the value

		return value;
	}

	private static final String _DELIM_START = "${";

	private static final String _DELIM_STOP = "}";

	private static final char _ESCAPE_CHAR = '\\';

	private static final String _LIFERAY_PREFIX = "LIFERAY_";

}
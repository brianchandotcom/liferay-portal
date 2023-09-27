/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.redirect.internal.util;

import com.google.re2j.Pattern;

import com.liferay.petra.string.StringPool;
import com.liferay.redirect.model.RedirectPatternEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Adolfo Pérez
 */
public class PatternUtil {

	public static List<RedirectPatternEntry> parse(String[] patternStrings) {
		List<RedirectPatternEntry> redirectPatternEntries = new ArrayList<>();

		for (String patternString : patternStrings) {
			String[] parts = patternString.split("\\s+", 3);

			if ((parts.length < 3) || parts[0].isEmpty() ||
				parts[1].isEmpty() || parts[2].isEmpty()) {

				continue;
			}

			redirectPatternEntries.add(
				new RedirectPatternEntry(
					Pattern.compile(_normalize(parts[0])), parts[1], parts[2]));
		}

		return redirectPatternEntries;
	}

	private static String _normalize(String patternString) {
		if (patternString.startsWith(StringPool.CARET)) {
			patternString = patternString.substring(1);
		}

		if (patternString.startsWith(StringPool.SLASH)) {
			patternString = patternString.substring(1);
		}

		return StringPool.CARET + patternString;
	}

}
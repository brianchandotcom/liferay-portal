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

import com.google.re2j.Pattern;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.redirect.constants.RedirectConstants;
import com.liferay.redirect.model.RedirectPatternEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Adolfo Pérez
 */
public class PatternUtil {

	public static List<RedirectPatternEntry> parse(String[] patternStrings) {
		List<RedirectPatternEntry> redirectPatternEntries = new ArrayList<>();

		int limit = 2;

		if (FeatureFlagManagerUtil.isEnabled("LPS-175850")) {
			limit = 3;
		}

		for (String patternString : patternStrings) {
			String[] parts = patternString.split("\\s+", 3);

			if ((parts.length < limit) || parts[0].isEmpty() ||
				parts[1].isEmpty()) {

				continue;
			}

			String userAgent = RedirectConstants.USER_AGENT_ALL;

			if (FeatureFlagManagerUtil.isEnabled("LPS-175850")) {
				if (parts[2].isEmpty()) {
					continue;
				}

				userAgent = parts[2];
			}

			redirectPatternEntries.add(
				new RedirectPatternEntry(
					Pattern.compile(_normalize(parts[0])), parts[1],
					userAgent));
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
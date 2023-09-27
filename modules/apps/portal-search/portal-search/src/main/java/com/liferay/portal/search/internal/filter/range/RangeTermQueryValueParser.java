/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.filter.range;

import com.liferay.petra.string.StringPool;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Adam Brandizzi
 */
public class RangeTermQueryValueParser {

	public RangeTermQueryValue parse(String value) {
		Matcher matcher = _pattern.matcher(value);

		if (!matcher.matches()) {
			return null;
		}

		RangeTermQueryValue.Builder rangeTermQueryValueBuilder =
			new RangeTermQueryValue.Builder();

		rangeTermQueryValueBuilder.includesLower(isIncludesLower(matcher));
		rangeTermQueryValueBuilder.includesUpper(isIncludesUpper(matcher));
		rangeTermQueryValueBuilder.lowerBound(matcher.group("lowerBound"));
		rangeTermQueryValueBuilder.upperBound(matcher.group("upperBound"));

		return rangeTermQueryValueBuilder.build();
	}

	protected boolean isIncludesLower(Matcher matcher) {
		String lowerBracket = matcher.group("lowerBracket");

		if (lowerBracket.equals(StringPool.OPEN_BRACKET)) {
			return true;
		}

		return false;
	}

	protected boolean isIncludesUpper(Matcher matcher) {
		String upperBracket = matcher.group("upperBracket");

		if (upperBracket.equals(StringPool.CLOSE_BRACKET)) {
			return true;
		}

		return false;
	}

	private static final Pattern _pattern = Pattern.compile(
		"\\s*(?<lowerBracket>[\\]\\[])(?<lowerBound>\\S+)\\s+" +
			"(?<upperBound>\\S+)(?<upperBracket>[\\]\\[])\\s*");

}
/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check.comparator;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.NaturalOrderStringComparator;
import com.liferay.source.formatter.check.util.SourceUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Alan Huang
 */
public class ParameterNameComparator extends NaturalOrderStringComparator {

	@Override
	public int compare(String parameterName1, String parameterName2) {
		Matcher matcher = _multipleLineConstantPattern.matcher(parameterName1);

		parameterName1 = matcher.replaceAll(".");

		matcher = _multipleLineConstantPattern.matcher(parameterName2);

		parameterName2 = matcher.replaceAll(".");

		String strippedParameterName1 = SourceUtil.stripQuotes(parameterName1);
		String strippedParameterName2 = SourceUtil.stripQuotes(parameterName2);

		if (strippedParameterName1.contains(StringPool.OPEN_PARENTHESIS) ||
			strippedParameterName2.contains(StringPool.OPEN_PARENTHESIS)) {

			return 0;
		}

		matcher = _multipleLineParameterNamePattern.matcher(parameterName1);

		if (matcher.find()) {
			parameterName1 = matcher.replaceAll(StringPool.BLANK);
		}

		matcher = _multipleLineParameterNamePattern.matcher(parameterName2);

		if (matcher.find()) {
			parameterName2 = matcher.replaceAll(StringPool.BLANK);
		}

		if (parameterName1.matches("\".*\"") &&
			parameterName2.matches("\".*\"")) {

			String strippedQuotes1 = parameterName1.substring(
				1, parameterName1.length() - 1);
			String strippedQuotes2 = parameterName2.substring(
				1, parameterName2.length() - 1);

			return super.compare(strippedQuotes1, strippedQuotes2);
		}

		int value = super.compare(parameterName1, parameterName2);

		if (parameterName1.startsWith(StringPool.QUOTE) ^
			parameterName2.startsWith(StringPool.QUOTE)) {

			return -value;
		}

		return value;
	}

	private static final Pattern _multipleLineConstantPattern = Pattern.compile(
		"\\.\n\t+");
	private static final Pattern _multipleLineParameterNamePattern =
		Pattern.compile("\" \\+\n\t+\"");

}
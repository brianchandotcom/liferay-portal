/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Alan Huang
 */
public class GradleEmptyLinesCheck extends BaseEmptyLinesCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		Matcher matcher = _missingEmptyLineBetweenDependenciesPattern1.matcher(
			content);

		if (matcher.find()) {
			return StringUtil.replaceFirst(
				content, "\n", "\n\n", matcher.start());
		}

		matcher = _missingEmptyLineBetweenDependenciesPattern2.matcher(content);

		if (matcher.find()) {
			return StringUtil.replaceFirst(
				content, "\n", "\n\n", matcher.start());
		}

		return content;
	}

	private static final Pattern _missingEmptyLineBetweenDependenciesPattern1 =
		Pattern.compile(" group: \".+\"\n\t+.+\\{\n");
	private static final Pattern _missingEmptyLineBetweenDependenciesPattern2 =
		Pattern.compile("\t+\\}\n\t+\\w+ group: \".+\"\n");

}
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JavaDiamondOperatorCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		return _applyDiamondOperator(content);
	}

	private String _applyDiamondOperator(String content) {
		Matcher matcher = _diamondOperatorPattern.matcher(content);

		while (matcher.find()) {
			String match = matcher.group();

			if (match.contains("{\n")) {
				continue;
			}

			String className = matcher.group(3);
			String parameterType = matcher.group(5);

			// LPS-70579

			if ((className.equals("AutoResetThreadLocal") ||
				 className.equals("InitialThreadLocal")) &&
				(parameterType.startsWith("Map<") ||
				 parameterType.startsWith("Set<"))) {

				continue;
			}

			String whitespace = matcher.group(4);

			String replacement = StringUtil.replaceFirst(
				match,
				StringBundler.concat(whitespace, "<", parameterType, ">"),
				"<>");

			content = StringUtil.replace(content, match, replacement);
		}

		return content;
	}

	private static final Pattern _diamondOperatorPattern = Pattern.compile(
		"(return|=)\n?(\t+| )new ([A-Za-z]+)(\\s*)<([^>][^;]*?)>" +
			"\\(\n*\t*.*?\\);\n",
		Pattern.DOTALL);

}
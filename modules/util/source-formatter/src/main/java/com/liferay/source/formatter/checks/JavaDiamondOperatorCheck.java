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

package com.liferay.source.formatter.checks;

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
				match, whitespace + "<" + parameterType + ">", "<>");

			content = StringUtil.replace(content, match, replacement);
		}

		return content;
	}

	private final Pattern _diamondOperatorPattern = Pattern.compile(
		"(return|=)\n?(\t+| )new ([A-Za-z]+)(\\s*)<([^>][^;]*?)>" +
			"\\(\n*\t*.*?\\);\n",
		Pattern.DOTALL);

}
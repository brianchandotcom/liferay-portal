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

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.source.formatter.SourceFormatterMessage;
import com.liferay.source.formatter.checks.util.JavaSourceUtil;

import java.lang.reflect.Field;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class StringUtilCheck extends BaseFileCheck {

	@Override
	public Tuple process(String fileName, String absolutePath, String content)
		throws Exception {

		if (absolutePath.contains("poshi") ||
			fileName.endsWith("StringUtilTest.java")) {

			return new Tuple(content, Collections.emptySet());
		}

		Set<SourceFormatterMessage> sourceFormatterMessages = new HashSet<>();

		_checkReplaceCalls(sourceFormatterMessages, fileName, content);

		return new Tuple(content, sourceFormatterMessages);
	}

	private void _checkReplaceCalls(
			Set<SourceFormatterMessage> sourceFormatterMessages,
			String fileName, String content)
		throws Exception {

		Matcher matcher = _stringUtilReplacePattern.matcher(content);

		while (matcher.find()) {
			if (ToolsUtil.isInsideQuotes(content, matcher.start())) {
				continue;
			}

			List<String> parametersList = JavaSourceUtil.getParameterList(
				matcher.group());

			if (parametersList.size() != 3) {
				return;
			}

			String secondParameter = parametersList.get(1);

			Matcher singleLengthMatcher = _singleLengthStringPattern.matcher(
				secondParameter);

			if (!singleLengthMatcher.find()) {
				continue;
			}

			String fieldName = singleLengthMatcher.group(2);

			if (fieldName != null) {
				Field field = StringPool.class.getDeclaredField(fieldName);

				String value = (String)field.get(null);

				if (value.length() != 1) {
					continue;
				}
			}

			String method = matcher.group(1);

			StringBundler sb = new StringBundler(5);

			sb.append("Use StringUtil.");
			sb.append(method);
			sb.append("(String, char, char) or StringUtil.");
			sb.append(method);
			sb.append("(String, char, String) instead");

			addMessage(
				sourceFormatterMessages, fileName, sb.toString(),
				getLineCount(content, matcher.start()));
		}
	}

	private final Pattern _singleLengthStringPattern = Pattern.compile(
		"^(\".\"|StringPool\\.([A-Z_]+))$");
	private final Pattern _stringUtilReplacePattern = Pattern.compile(
		"StringUtil\\.(replace(First|Last)?)\\((.*?)\\);\n", Pattern.DOTALL);

}
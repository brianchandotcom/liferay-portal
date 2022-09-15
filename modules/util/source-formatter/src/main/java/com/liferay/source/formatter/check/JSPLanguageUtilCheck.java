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

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.check.util.JavaSourceUtil;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JSPLanguageUtilCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		Matcher matcher = _languageUtilPattern.matcher(content);

		while (matcher.find()) {
			if (isJavaSource(content, matcher.start(), true)) {
				return StringUtil.replaceFirst(
					content, "LanguageUtil.get(locale,",
					"LanguageUtil.get(request,");
			}
		}

		return _replaceLiferayTag(content);
	}

	private String _replaceLiferayTag(String content) {
		Matcher matcher = _javaSourcePattern.matcher(content);

		StringBuffer sb = new StringBuffer();

		while (matcher.find()) {
			String expression = matcher.group();

			expression = expression.replaceAll("\n\t*", StringPool.BLANK);

			Matcher expMatcher = _languageUtilGetPattern.matcher(expression);

			if (expMatcher.find()) {
				String javaSource = expMatcher.group(1);

				List<String> parameters = JavaSourceUtil.getParameterList(
					javaSource);

				if (parameters.size() != 2) {
					continue;
				}

				String secondParameter = parameters.get(1);

				if (!secondParameter.startsWith("\"") ||
					!secondParameter.endsWith("\"") ||
					(StringUtil.count(secondParameter, "\"") > 2)) {

					continue;
				}

				matcher.appendReplacement(
					sb, "<liferay-ui:message key=" + secondParameter + " />");
			}
		}

		if (sb.length() > 0) {
			matcher.appendTail(sb);

			return sb.toString();
		}

		return content;
	}

	private static final Pattern _javaSourcePattern = Pattern.compile(
		"(?<!['\"])<%=((?!%>)[\\s\\S])*%>");
	private static final Pattern _languageUtilGetPattern = Pattern.compile(
		"<%= ?(LanguageUtil\\.get\\(request, ((?!\\)).)+\\)) ?%>");
	private static final Pattern _languageUtilPattern = Pattern.compile(
		"LanguageUtil\\.get\\(locale,");

}
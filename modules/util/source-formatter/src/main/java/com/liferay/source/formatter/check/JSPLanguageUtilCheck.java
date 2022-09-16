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

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.ToolsUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JSPLanguageUtilCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		Matcher matcher = _languageUtilPattern1.matcher(content);

		while (matcher.find()) {
			if (isJavaSource(content, matcher.start(), true)) {
				return StringUtil.replaceFirst(
					content, "LanguageUtil.get(locale,",
					"LanguageUtil.get(request,");
			}
		}

		matcher = _languageUtilPattern2.matcher(content);

		while (matcher.find()) {
			if (!ToolsUtil.isInsideQuotes(content, matcher.start())) {
				addMessage(
					fileName,
					"Use <liferay-ui:message> tag instead of LanguageUtil.get",
					getLineNumber(content, matcher.start()));
			}
		}

		return content;
	}

	private static final Pattern _languageUtilPattern1 = Pattern.compile(
		"LanguageUtil\\.get\\(locale,");
	private static final Pattern _languageUtilPattern2 = Pattern.compile(
		"<%= LanguageUtil\\.get\\(");

}
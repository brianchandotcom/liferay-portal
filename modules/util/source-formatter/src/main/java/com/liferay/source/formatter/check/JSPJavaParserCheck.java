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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.tools.java.parser.JavaParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JSPJavaParserCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		Matcher matcher = _javaSourcePattern.matcher(content);

		while (matcher.find()) {
			try {
				String indent = matcher.group(1);

				if (Validator.isNotNull(matcher.group(2))) {
					indent += "\t";
				}

				String match = matcher.group(3);

				String replacement = JavaParser.parseSnippet(match, indent);

				if (!match.equals(replacement)) {
					return StringUtil.replaceFirst(
						content, match, replacement, matcher.start());
				}
			}
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					_log.debug(exception);
				}
			}
		}

		return content;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JSPJavaParserCheck.class);

	private static final Pattern _javaSourcePattern = Pattern.compile(
		"\n(\t*)(.*)<%=?\n(((?!%>)[\\s\\S])*)\n\t*%>");

}
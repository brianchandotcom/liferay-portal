/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.tools.java.parser.JavaParser;
import com.liferay.source.formatter.SourceFormatterArgs;
import com.liferay.source.formatter.processor.SourceProcessor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JSPJavaParserCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		SourceProcessor sourceProcessor = getSourceProcessor();

		SourceFormatterArgs sourceFormatterArgs =
			sourceProcessor.getSourceFormatterArgs();

		if (!sourceFormatterArgs.isJavaParserEnabled()) {
			return content;
		}

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
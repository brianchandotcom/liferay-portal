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

import com.liferay.portal.kernel.util.Tuple;
import com.liferay.source.formatter.SourceFormatterMessage;
import com.liferay.source.formatter.checks.util.JSPSourceUtil;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JSPIfStatementCheck extends IfStatementCheck {

	@Override
	public Tuple process(String fileName, String absolutePath, String content)
		throws Exception {

		Set<SourceFormatterMessage> sourceFormatterMessages = new HashSet<>();

		Matcher matcher = _ifStatementPattern.matcher(content);

		while (matcher.find()) {
			if (JSPSourceUtil.isJavaSource(content, matcher.start())) {
				checkIfClauseParentheses(
					sourceFormatterMessages, matcher.group(), fileName,
					getLineCount(content, matcher.start(1)));
			}
		}

		matcher = _testTagPattern.matcher(content);

		while (matcher.find()) {
			if (!JSPSourceUtil.isJavaSource(content, matcher.start())) {
				String ifClause = "if (" + matcher.group(2) + ") {";

				checkIfClauseParentheses(
					sourceFormatterMessages, ifClause, fileName,
					getLineCount(content, matcher.start(2)));
			}
		}

		return new Tuple(content, sourceFormatterMessages);
	}

	private final Pattern _ifStatementPattern = Pattern.compile(
		"[\t\n]((else )?if|while) .*\\) \\{\n");
	private final Pattern _testTagPattern = Pattern.compile(
		"[\t\n]<c:(if|when) test=['\"]<%= (.+) %>['\"]>\n");

}
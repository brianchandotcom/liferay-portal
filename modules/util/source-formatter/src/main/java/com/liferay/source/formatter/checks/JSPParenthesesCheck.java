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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.source.formatter.checks.util.JSPSourceUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JSPParenthesesCheck extends IfStatementCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		Matcher matcher = _ifStatementPattern.matcher(content);

		while (matcher.find()) {
			if (JSPSourceUtil.isJavaSource(content, matcher.start())) {
				int index = checkIfClauseParentheses(
					matcher.group(), fileName,
					getLineNumber(content, matcher.start(1)));

				if (index != -1) {
					return _fixClause(
						content, matcher.group(), index, matcher.start());
				}
			}
		}

		matcher = _tagPattern.matcher(content);

		while (matcher.find()) {
			if (!JSPSourceUtil.isJavaSource(content, matcher.start())) {
				String ifClause = "if (" + matcher.group(1) + ") {";

				int index = checkIfClauseParentheses(
					ifClause, fileName,
					getLineNumber(content, matcher.start()));

				if (index != -1) {
					return _fixClause(
						content, matcher.group(), index - 1,
						matcher.start() - 1);
				}
			}
		}

		return content;
	}

	private String _fixClause(
		String content, String clause, int index, int start) {

		int x = -1;

		for (int i = 0; i < index; i++) {
			while (true) {
				x = clause.indexOf(StringPool.OPEN_PARENTHESIS, x + 1);

				if (!ToolsUtil.isInsideQuotes(clause, x)) {
					break;
				}
			}
		}

		int y = x;

		while (true) {
			y = clause.indexOf(StringPool.CLOSE_PARENTHESIS, y + 1);

			if (y == -1) {
				return content;
			}

			if (ToolsUtil.isInsideQuotes(clause, y)) {
				continue;
			}

			String s = clause.substring(x, y + 1);

			if (getLevel(s) != 0) {
				continue;
			}

			String replacement = StringUtil.replaceFirst(
				clause, StringPool.CLOSE_PARENTHESIS, StringPool.BLANK, y);

			replacement = StringUtil.replaceFirst(
				replacement, StringPool.OPEN_PARENTHESIS, StringPool.BLANK, x);

			return StringUtil.replaceFirst(content, clause, replacement, start);
		}
	}

	private final Pattern _ifStatementPattern = Pattern.compile(
		"[\t\n]((else )?if|while) .*\\) \\{\n");
	private final Pattern _tagPattern = Pattern.compile("<%= (.+?) %>");

}
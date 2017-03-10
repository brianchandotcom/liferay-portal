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

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.SourceFormatterMessage;

import java.util.Set;

/**
 * @author Hugo Huijser
 */
public abstract class IfStatementCheck extends BaseFileCheck {

	protected void checkIfClauseParentheses(
		Set<SourceFormatterMessage> sourceFormatterMessages, String ifClause,
		String fileName, int lineCount) {

		ifClause = stripQuotes(ifClause);

		if (ifClause.matches(
				"[^()]*\\((\\(?\\w+ instanceof \\w+\\)?( \\|\\| )?)+" +
					"\\)[^()]*") &&
			!ifClause.matches("[^()]*\\([^()]*\\)[^()]*")) {

			addMessage(
				sourceFormatterMessages, fileName,
				"Redundant parentheses in if-statement", lineCount);

			return;
		}

		if (ifClause.contains(StringPool.DOUBLE_SLASH) ||
			ifClause.contains("/*") || ifClause.contains("*/")) {

			return;
		}

		if (_hasRedundantParentheses(ifClause, "||", "&&") ||
			_hasRedundantParentheses(ifClause, "&&", "||")) {

			addMessage(
				sourceFormatterMessages, fileName,
				"Redundant parentheses in if-statement", lineCount);

			return;
		}

		int previousParenthesisPos = -1;

		for (int i = 0; i < ifClause.length(); i++) {
			char c = ifClause.charAt(i);

			if ((c != CharPool.OPEN_PARENTHESIS) &&
				(c != CharPool.CLOSE_PARENTHESIS)) {

				continue;
			}

			if (previousParenthesisPos != -1) {
				String s = ifClause.substring(previousParenthesisPos + 1, i);

				if (_hasMissingParentheses(s)) {
					addMessage(
						sourceFormatterMessages, fileName,
						"Missing parentheses in if-statement", lineCount);

					return;
				}
			}

			if (previousParenthesisPos == -1) {
				previousParenthesisPos = i;

				continue;
			}

			previousParenthesisPos = i;

			if (c != CharPool.OPEN_PARENTHESIS) {
				continue;
			}

			char previousChar = ifClause.charAt(i - 1);

			if ((previousChar != CharPool.OPEN_PARENTHESIS) &&
				(previousChar != CharPool.SPACE)) {

				continue;
			}

			int j = i;

			while (true) {
				j = ifClause.indexOf(StringPool.CLOSE_PARENTHESIS, j + 1);

				String s = ifClause.substring(i + 1, j);

				if (getLevel(s) == 0) {
					char nextChar = ifClause.charAt(j + 1);

					if (((previousChar == CharPool.OPEN_PARENTHESIS) &&
						 (nextChar == CharPool.CLOSE_PARENTHESIS)) ||
						(((nextChar == CharPool.CLOSE_PARENTHESIS) ||
						  (nextChar == CharPool.SPACE)) &&
						 _hasRedundantParentheses(s))) {

						addMessage(
							sourceFormatterMessages, fileName,
							"Redundant parentheses in if-statement", lineCount);

						return;
					}

					break;
				}
			}
		}
	}

	private boolean _hasMissingParentheses(String s) {
		if (Validator.isNull(s)) {
			return false;
		}

		boolean containsAndOperator = s.contains("&&");
		boolean containsOrOperator = s.contains("||");

		if (containsAndOperator && containsOrOperator) {
			return true;
		}

		boolean containsCompareOperator = false;

		if (s.contains(" == ") || s.contains(" != ") || s.contains(" < ") ||
			s.contains(" > ") || s.contains(" =< ") || s.contains(" => ") ||
			s.contains(" <= ") || s.contains(" >= ")) {

			containsCompareOperator = true;
		}

		boolean containsMathOperator = false;

		if (s.contains(" = ") || s.contains(" - ") || s.contains(" + ") ||
			s.contains(" & ") || s.contains(" % ") || s.contains(" * ") ||
			s.contains(" / ")) {

			containsMathOperator = true;
		}

		if (containsCompareOperator &&
			(containsAndOperator || containsOrOperator ||
			 (containsMathOperator && !s.contains(StringPool.OPEN_BRACKET)))) {

			return true;
		}

		return false;
	}

	private boolean _hasRedundantParentheses(String s) {
		//if (s.matches("\\w+ instanceof \\w+")) {
		//	return true;
		//}

		int x = -1;

		while (true) {
			x = s.indexOf(StringPool.SPACE, x + 1);

			if (x == -1) {
				break;
			}

			if (getLevel(s.substring(0, x)) == 0) {
				return false;
			}
		}

		return true;
	}

	private boolean _hasRedundantParentheses(
		String s, String operator1, String operator2) {

		while (true) {
			int x = s.indexOf("!(");

			if (x == -1) {
				break;
			}

			int y = x;

			while (true) {
				y = s.indexOf(")", y + 1);

				String linePart = s.substring(x, y + 1);

				if (getLevel(linePart) == 0) {
					break;
				}
			}

			s = StringUtil.replaceFirst(s, ")", StringPool.BLANK, y);
			s = StringUtil.replaceFirst(s, "!(", StringPool.BLANK, x);
		}

		String[] parts = StringUtil.split(s, operator1);

		if (parts.length < 3) {
			return false;
		}

		for (int i = 1; i < (parts.length - 1); i++) {
			String part = parts[i];

			if (part.contains(operator2)) {
				continue;
			}

			if (Math.abs(getLevel(part)) == 1) {
				return true;
			}
		}

		return false;
	}

}
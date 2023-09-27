/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.tools.ToolsUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Qi Zhang
 */
public class JSPExpressionTagCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		StringBuffer sb = new StringBuffer();

		Matcher matcher = _jspExpressionTagPattern.matcher(content);

		while (matcher.find()) {
			String jspExpressionTag = matcher.group();

			String replacement = jspExpressionTag.replaceFirst(
				"<%= \"([^\"]+?)\" \\+(.+)", "$1<%=$2");

			if (!jspExpressionTag.equals(replacement)) {
				return StringUtil.replaceFirst(
					content, matcher.group(), replacement);
			}

			if (getLevel(jspExpressionTag) != 0) {
				continue;
			}

			matcher.appendReplacement(
				sb,
				Matcher.quoteReplacement(
					_formatJspExpressionTag(matcher.group(1))));
		}

		if (sb.length() > 0) {
			matcher.appendTail(sb);

			return sb.toString();
		}

		return content;
	}

	private String _formatJspExpressionTag(String expression) {
		List<String> operandList = new ArrayList<>();

		int startPosition = 0;

		int x = -1;

		while (true) {
			x = expression.indexOf("+", x + 1);

			if (x == -1) {
				break;
			}

			if (ToolsUtil.isInsideQuotes(expression, x)) {
				continue;
			}

			if (x < (expression.length() - 1)) {
				char c = expression.charAt(x + 1);

				if (c == CharPool.PLUS) {
					continue;
				}
			}

			if (x > 0) {
				char c = expression.charAt(x - 1);

				if (c == CharPool.PLUS) {
					continue;
				}
			}

			String operand = expression.substring(startPosition, x);

			if (getLevel(operand) != 0) {
				continue;
			}

			String trimmedOperand = operand.trim();

			if ((operand.contains("?") || operand.contains(":")) &&
				(!trimmedOperand.startsWith("(") ||
				 !trimmedOperand.endsWith(")") ||
				 (_getMatchedCloseParenthesisPosition(trimmedOperand) !=
					 (trimmedOperand.length() - 1)))) {

				continue;
			}

			operandList.add(trimmedOperand);

			startPosition = x + 1;
		}

		operandList.add(StringUtil.trim(expression.substring(startPosition)));

		StringBundler sb = new StringBundler();

		String previousOperand = null;

		for (String operand : operandList) {
			if (operand.startsWith(StringPool.QUOTE)) {
				if (Validator.isNotNull(previousOperand) &&
					!previousOperand.startsWith(StringPool.QUOTE)) {

					sb.append(" %>");
				}

				sb.append(StringUtil.unquote(operand));
			}
			else {
				if (Validator.isNotNull(previousOperand)) {
					if (!previousOperand.startsWith(StringPool.QUOTE)) {
						sb.append(" + ");
					}
					else {
						sb.append("<%= ");
					}
				}
				else {
					sb.append("<%= ");
				}

				sb.append(operand);
			}

			previousOperand = operand;
		}

		if (!previousOperand.startsWith(StringPool.QUOTE)) {
			sb.append(" %>");
		}

		return sb.toString();
	}

	private int _getMatchedCloseParenthesisPosition(String content) {
		int x = -1;

		while (true) {
			x = content.indexOf(")", x + 1);

			if ((x == -1) ||
				(!ToolsUtil.isInsideQuotes(content, x) &&
				 (getLevel(content.substring(0, x + 1)) == 0))) {

				return x;
			}
		}
	}

	private static final Pattern _jspExpressionTagPattern = Pattern.compile(
		"(?<!')<%=(.+?)%>");

}
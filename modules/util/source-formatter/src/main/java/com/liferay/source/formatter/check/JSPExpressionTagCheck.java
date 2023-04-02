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

			if (getLevel(jspExpressionTag, "(", ")") != 0) {
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

		int x = startPosition;

		while (true) {
			x = expression.indexOf("+", x);

			if (x == -1) {
				break;
			}

			if (ToolsUtil.isInsideQuotes(expression, x)) {
				x = x + 1;

				continue;
			}

			if (x < (expression.length() - 1)) {
				char c = expression.charAt(x + 1);

				if (c == CharPool.PLUS) {
					x = x + 1;

					continue;
				}
			}

			if (x > 0) {
				char c = expression.charAt(x - 1);

				if (c == CharPool.PLUS) {
					x = x + 1;

					continue;
				}
			}

			String operand = expression.substring(startPosition, x);

			if ((getLevel(operand, "(", ")") != 0) || operand.contains("?") ||
				operand.contains(":")) {

				x = x + 1;

				continue;
			}

			operandList.add(operand.trim());

			x = x + 1;

			startPosition = x;
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

	private static final Pattern _jspExpressionTagPattern = Pattern.compile(
		"(?<!')<%=(.+?)%>");

}
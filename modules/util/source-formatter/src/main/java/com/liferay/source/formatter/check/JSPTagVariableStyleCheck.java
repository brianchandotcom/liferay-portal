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
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Qi Zhang
 */
public class JSPTagVariableStyleCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		return _fixIncorrectVariableStyle(content);
	}

	private String _fixIncorrectVariableStyle(String content) {
		Matcher matcher = _javaSourceParameterPattern.matcher(content);

		outLoop:
		while (matcher.find()) {
			String parameter = matcher.group(2);

			String[] parameters = _splitParameters(parameter);

			if ((parameters == null) || (parameters.length == 1)) {
				continue;
			}

			StringBundler sb = new StringBundler(parameters.length);
			boolean changeFlg = false;
			boolean preJavaParameterFlg = false;

			for (String curParameter : parameters) {
				if (Validator.isNull(curParameter)) {
					continue;
				}

				curParameter = StringUtil.trim(curParameter);

				int level = _getLevel(curParameter);

				if (level != 0) {
					continue outLoop;
				}

				if (curParameter.startsWith(StringPool.QUOTE) &&
					curParameter.endsWith(StringPool.QUOTE)) {

					sb.append(
						curParameter.substring(1, curParameter.length() - 1));

					changeFlg = true;
					preJavaParameterFlg = false;
				}
				else {
					if (preJavaParameterFlg) {
						sb.setIndex(sb.index() - 1);
						sb.append(" + ");
					}
					else {
						sb.append("<%= ");
					}

					if (curParameter.startsWith(StringPool.OPEN_PARENTHESIS) &&
						curParameter.endsWith(StringPool.CLOSE_PARENTHESIS)) {

						curParameter = curParameter.substring(
							1, curParameter.length() - 1);
					}

					sb.append(curParameter);
					sb.append(" %>");

					preJavaParameterFlg = true;
				}
			}

			if (!StringUtil.equals(matcher.group(1), sb.toString()) &&
				changeFlg) {

				return StringUtil.replaceFirst(
					content, matcher.group(1), sb.toString(), matcher.start(1));
			}
		}

		return content;
	}

	private int _getLevel(String expression) {
		int level = 0;

		for (char e : expression.toCharArray()) {
			if (e == CharPool.OPEN_PARENTHESIS) {
				level += 1;
			}
			else if (e == CharPool.CLOSE_PARENTHESIS) {
				level -= 1;
			}
		}

		return level;
	}

	private String[] _splitParameters(String parameter) {
		int questionIndex = parameter.indexOf(StringPool.QUESTION);
		int colonIndex = parameter.indexOf(StringPool.COLON);

		String expression = null;
		int x = questionIndex;
		int y = colonIndex;
		boolean conditionalOperatorFlg = false;

		if ((questionIndex != -1) && (colonIndex != -1) &&
			(questionIndex < colonIndex) &&
			!ToolsUtil.isInsideQuotes(parameter, questionIndex) &&
			!ToolsUtil.isInsideQuotes(parameter, colonIndex)) {

			if ((StringUtil.count(parameter, StringPool.QUESTION) > 1) ||
				(StringUtil.count(parameter, StringPool.COLON) > 1)) {

				return null;
			}

			conditionalOperatorFlg = true;

			int level = 0;

			while (true) {
				if (level == 0) {
					x = parameter.lastIndexOf(
						StringPool.OPEN_PARENTHESIS, x - 1);
					y = parameter.indexOf(StringPool.CLOSE_PARENTHESIS, y + 1);
				}
				else if (level > 0) {
					y = parameter.indexOf(StringPool.CLOSE_PARENTHESIS, y + 1);
				}
				else {
					x = parameter.lastIndexOf(
						StringPool.OPEN_PARENTHESIS, x - 1);
				}

				if ((x == -1) || (y == -1)) {
					break;
				}

				expression = parameter.substring(x, y + 1);

				level = _getLevel(expression);

				if (level == 0) {
					int frontPlusIndex = parameter.lastIndexOf(
						StringPool.PLUS, x);
					int behindPlusIndex = parameter.indexOf(StringPool.PLUS, y);

					if ((frontPlusIndex == -1) && (behindPlusIndex == -1)) {
						expression = null;
					}
					else if (frontPlusIndex == -1) {
						y = behindPlusIndex;
					}
					else if (behindPlusIndex == -1) {
						x = frontPlusIndex;
					}
					else {
						x = frontPlusIndex;
						y = behindPlusIndex;
					}

					break;
				}

				expression = null;
			}
		}

		if (Validator.isNull(expression)) {
			if (conditionalOperatorFlg) {
				String[] result = new String[1];

				result[0] = parameter;

				return result;
			}

			return parameter.split("\\+");
		}

		List<String> parameters = new ArrayList<>();

		String tmpExpression = parameter.substring(0, x);

		if (Validator.isNotNull(tmpExpression)) {
			Collections.addAll(parameters, tmpExpression.split("\\+"));
		}

		parameters.add(expression);

		tmpExpression = parameter.substring(y + 1);

		if (Validator.isNotNull(tmpExpression)) {
			Collections.addAll(parameters, tmpExpression.split("\\+"));
		}

		return parameters.toArray(new String[0]);
	}

	private static final Pattern _javaSourceParameterPattern = Pattern.compile(
		"=\"(<%=(.+?)%>)\"");

}
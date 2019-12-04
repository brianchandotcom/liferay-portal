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

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.source.formatter.checks.util.JavaSourceUtil;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public abstract class StylingCheck extends BaseFileCheck {

	protected String formatStyling(String content) {
		content = _formatStyling(
			content, "!Validator.isNotNull(", "Validator.isNull(");
		content = _formatStyling(
			content, "!Validator.isNull(", "Validator.isNotNull(");

		content = _formatStyling(content, "\nfor (;;) {", "\nwhile (true) {");
		content = _formatStyling(content, "\tfor (;;) {", "\twhile (true) {");

		content = _formatStyling(
			content, "String.valueOf(false)", "Boolean.FALSE.toString()");
		content = _formatStyling(
			content, "String.valueOf(true)", "Boolean.TRUE.toString()");

		content = _formatObjectsEqualsMethodCall(content);

		content = _formatToStringMethodCall(content, "Double");
		content = _formatToStringMethodCall(content, "Float");
		content = _formatToStringMethodCall(content, "Integer");
		content = _formatToStringMethodCall(content, "Long");
		content = _formatToStringMethodCall(content, "Objects");
		content = _formatToStringMethodCall(content, "Short");

		content = _fixBooleanStatement(content);
		content = _fixLambdaStatement(content);

		content = _fixRedundantArrayInitialization(content);
		content = _fixRedundantArrayInitialization(content, "Arrays", "asList");

		return content;
	}

	protected boolean isJavaSource(String content, int pos) {
		return true;
	}

	private String _fixBooleanStatement(String content) {
		Matcher matcher = _booleanPattern.matcher(content);

		while (matcher.find()) {
			if (ToolsUtil.isInsideQuotes(content, matcher.start())) {
				continue;
			}

			boolean booleanValue = true;

			if (matcher.group(1) != null) {
				booleanValue = !booleanValue;
			}

			if (Objects.equals(matcher.group(3), "!=")) {
				booleanValue = !booleanValue;
			}

			if (Objects.equals(matcher.group(4), "false")) {
				booleanValue = !booleanValue;
			}

			if (booleanValue) {
				return StringUtil.replaceFirst(
					content, matcher.group(), "(" + matcher.group(2) + ")");
			}

			return StringUtil.replaceFirst(
				content, matcher.group(), "(!" + matcher.group(2) + ")");
		}

		return content;
	}

	private String _fixLambdaStatement(String content) {
		Matcher matcher = _lambdaPattern.matcher(content);

		return matcher.replaceAll("$1::$2");
	}

	private String _fixRedundantArrayInitialization(String content) {
		Matcher matcher = _redundantArrayInitializationPattern.matcher(content);

		while (matcher.find()) {
			if (!isJavaSource(content, matcher.start())) {
				continue;
			}

			String typeName = matcher.group(1);

			int x = content.indexOf("new " + typeName + "[] {", matcher.end());

			if (x == -1) {
				continue;
			}

			int y = _getMatchingClosingCurlyBracePos(
				content, matcher.end() - 2);

			if (x < y) {
				return StringUtil.replaceFirst(
					content, "new " + typeName + "[] {", "{", matcher.end());
			}
		}

		return content;
	}

	private String _fixRedundantArrayInitialization(
		String content, String className, String methodName) {

		Pattern pattern = Pattern.compile(
			StringBundler.concat(
				"\\W", className, "\\.", methodName,
				"\\(\\s*(new \\w+\\[\\] \\{)(\\w+)?"));

		Matcher matcher = pattern.matcher(content);

		while (matcher.find()) {
			if (Objects.equals(matcher.group(2), StringPool.NULL)) {
				continue;
			}

			List<String> parameterList = JavaSourceUtil.getParameterList(
				content.substring(matcher.start()));

			if (parameterList.size() > 1) {
				continue;
			}

			int x = _getMatchingClosingCurlyBracePos(
				content, matcher.end(1) - 1);

			content = StringUtil.replaceFirst(
				content, StringPool.CLOSE_CURLY_BRACE, StringPool.BLANK, x);

			content = StringUtil.replaceFirst(
				content, matcher.group(1), StringPool.BLANK, matcher.start());

			return content;
		}

		return content;
	}

	private String _formatObjectsEqualsMethodCall(String content) {
		Matcher matcher = _objectsEqualsPattern.matcher(content);

		while (matcher.find()) {
			if (ToolsUtil.isInsideQuotes(content, matcher.end()) ||
				!isJavaSource(content, matcher.start())) {

				continue;
			}

			List<String> parameterList = JavaSourceUtil.getParameterList(
				content.substring(matcher.start() + 1));

			if (parameterList.size() != 2) {
				continue;
			}

			String parameterName1 = parameterList.get(0);
			String parameterName2 = parameterList.get(1);

			if (!_isLiteralString(parameterName1) ||
				_isLiteralString(parameterName2)) {

				continue;
			}

			int x = _getMatchingClosingParenthesisPos(
				content, matcher.start() + 1);

			String methodCall = content.substring(matcher.start() + 1, x + 1);

			String newMethodCall = StringBundler.concat(
				"Objects.equals(", parameterName2, ", ", parameterName1, ")");

			return StringUtil.replace(content, methodCall, newMethodCall);
		}

		return content;
	}

	private String _formatStyling(
		String content, String incorrectStyling, String correctStyling) {

		int x = -1;

		while (true) {
			x = content.indexOf(incorrectStyling, x + 1);

			if (x == -1) {
				return content;
			}

			if (Character.isLetterOrDigit(incorrectStyling.charAt(0)) &&
				Character.isLetterOrDigit(content.charAt(x - 1))) {

				continue;
			}

			if (isJavaSource(content, x)) {
				return StringUtil.replaceFirst(
					content, incorrectStyling, correctStyling);
			}
		}
	}

	private String _formatToStringMethodCall(String content, String className) {
		Pattern pattern = Pattern.compile(
			StringBundler.concat("\\W", className, "\\.toString\\((.*?)\\);\n"),
			Pattern.DOTALL);

		Matcher matcher = pattern.matcher(content);

		while (matcher.find()) {
			int pos = matcher.start();

			if (!isJavaSource(content, pos)) {
				continue;
			}

			List<String> parameterList = JavaSourceUtil.getParameterList(
				matcher.group());

			if (parameterList.size() == 1) {
				return StringUtil.replaceFirst(
					content, className + ".toString(", "String.valueOf(", pos);
			}
		}

		return content;
	}

	private int _getMatchingClosingCurlyBracePos(String content, int start) {
		int x = start;

		while (true) {
			x = content.indexOf(CharPool.CLOSE_CURLY_BRACE, x + 1);

			if (getLevel(content.substring(start, x + 1), "{", "}") == 0) {
				return x;
			}
		}
	}

	private int _getMatchingClosingParenthesisPos(String content, int start) {
		int x = start;

		while (true) {
			x = content.indexOf(CharPool.CLOSE_PARENTHESIS, x + 1);

			if ((getLevel(content.substring(start, x + 1), "(", ")") == 0) &&
				(getLevel(content.substring(start, x + 1), "{", "}") == 0)) {

				return x;
			}
		}
	}

	private boolean _isLiteralString(String s) {
		if ((s == null) || (s.length() < 2)) {
			return false;
		}

		if ((s.charAt(0) == CharPool.QUOTE) &&
			(s.charAt(s.length() - 1) == CharPool.QUOTE)) {

			return true;
		}

		return false;
	}

	private static final Pattern _booleanPattern = Pattern.compile(
		"\\((\\!)?(\\w+)\\s+(==|!=)\\s+(false|true)\\)");
	private static final Pattern _lambdaPattern = Pattern.compile(
		"\\(\\)\\s+->\\s+(\\w+)\\.(\\w+)\\(\\)");
	private static final Pattern _objectsEqualsPattern = Pattern.compile(
		"\\WObjects\\.equals\\(");
	private static final Pattern _redundantArrayInitializationPattern =
		Pattern.compile("\\W(\\w+)\\[\\]\\[\\] (\\w+ = )?\\{\n");

}
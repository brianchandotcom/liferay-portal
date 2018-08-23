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
import com.liferay.portal.kernel.util.StringBundler;
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

		content = _formatToStringMethodCall(content, "Double");
		content = _formatToStringMethodCall(content, "Float");
		content = _formatToStringMethodCall(content, "Integer");
		content = _formatToStringMethodCall(content, "Long");
		content = _formatToStringMethodCall(content, "Objects");
		content = _formatToStringMethodCall(content, "Short");

		content = _fixBooleanStatement(content);

		content = _fixRedundantArrayInitialization(content);

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

	private final Pattern _booleanPattern = Pattern.compile(
		"\\((\\!)?(\\w+)\\s+(==|!=)\\s+(false|true)\\)");
	private final Pattern _redundantArrayInitializationPattern =
		Pattern.compile("\\W(\\w+)\\[\\]\\[\\] (\\w+ = )?\\{\n");

}
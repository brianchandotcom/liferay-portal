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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Stack;

/**
 * @author Nícolas Moura
 */
public class UpgradeVelocityForeachMigrationCheck
	extends BaseUpgradeVelocityMigrationCheck {

	@Override
	protected String migrateContent(String content) {
		String[] lines = content.split(StringPool.NEW_LINE);

		for (int i = 0; i < lines.length; i++) {
			String line = lines[i];

			String newLine = line;

			if (newLine.contains(_VELOCITY_FOREACH_START)) {
				newLine = StringUtil.replace(
					newLine, _VELOCITY_FOREACH_START, "<#list");

				newLine = StringUtil.replace(
					newLine, "in", "as");

				newLine = StringUtil.replaceLast(
					newLine, CharPool.CLOSE_PARENTHESIS, CharPool.GREATER_THAN);

				char nextChar = line.charAt(
					line.indexOf(_VELOCITY_FOREACH_START) +
						_VELOCITY_FOREACH_START.length());

				if (nextChar == CharPool.OPEN_PARENTHESIS) {
					newLine = StringUtil.replaceFirst(
						newLine, CharPool.OPEN_PARENTHESIS, CharPool.SPACE);
				}
				else {
					newLine = StringUtil.removeFirst(
						newLine, StringPool.OPEN_PARENTHESIS);
				}

				newLine = _changeForeachDeclarationOrder(newLine);

				_replaceStatementEnd(i, lines, _VELOCITY_FOREACH_START);
			}

			lines[i] = newLine;
		}

		return com.liferay.petra.string.StringUtil.merge(
			lines, StringPool.NEW_LINE);
	}

	private static String _changeForeachDeclarationOrder(String line) {
		String firstArgument = line.substring(
			line.indexOf("<#list") + "<#list".length() + 1,
			line.indexOf("in") - 1);
		String secondArgument = line.substring(
			line.indexOf("in") + "in".length() + 1,
			line.indexOf(CharPool.GREATER_THAN));

		String newLine = StringUtil.replaceFirst(
			line, firstArgument, secondArgument);

		return StringUtil.replaceLast(newLine, secondArgument, firstArgument);
	}

	private static boolean _isVelocityStatement(String line, String statement) {
		int previousCharIndex = line.indexOf(statement) - 1;

		if ((line.indexOf(statement) == 0) ||
			((line.charAt(previousCharIndex) != CharPool.LESS_THAN) &&
			 (line.charAt(previousCharIndex) != CharPool.SLASH))) {

			return true;
		}

		return false;
	}

	private static void _replaceStatementEnd(
		int lineIndex, String[] lines, String statement) {

		Stack<String> stack = new Stack<>();

		stack.push(statement);

		int nextLineIndex = lineIndex;

		while (!stack.empty()) {
			nextLineIndex += 1;

			String nextLine = lines[nextLineIndex];

			if (nextLine.contains(_VELOCITY_IF_START) &&
				_isVelocityStatement(nextLine, _VELOCITY_IF_START)) {

				stack.push(_VELOCITY_IF_START);
			}

			if (nextLine.contains(_VELOCITY_FOREACH_START)) {
				stack.push(_VELOCITY_FOREACH_START);
			}

			if (nextLine.contains(_VELOCITY_MACRO_START) &&
				_isVelocityStatement(nextLine, _VELOCITY_MACRO_START)) {

				stack.push(_VELOCITY_MACRO_START);
			}

			if (nextLine.contains(_VELOCITY_END)) {
				stack.pop();
			}

			if (stack.empty()) {
				if (statement.equals(_VELOCITY_IF_START)) {
					lines[nextLineIndex] = StringUtil.replace(
						nextLine, _VELOCITY_END, "</#if>");
				}
				else if (statement.equals(_VELOCITY_FOREACH_START)) {
					lines[nextLineIndex] = StringUtil.replace(
						nextLine, _VELOCITY_END, "</#list>");
				}

				if (statement.equals(_VELOCITY_MACRO_START)) {
					lines[nextLineIndex] = StringUtil.replace(
						nextLine, _VELOCITY_END, "</#macro>");
				}
			}
		}
	}

	private static final String _VELOCITY_END = "#end";

	private static final String _VELOCITY_FOREACH_START = "#foreach";

	private static final String _VELOCITY_IF_START = "#if";

	private static final String _VELOCITY_MACRO_START = "#macro";

}
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
public class UpgradeVelocityIfStatementsMigrationCheck
	extends BaseUpgradeVelocityMigrationCheck {

	@Override
	protected String migrateContent(String content) {
		String[] lines = content.split(StringPool.NEW_LINE);

		for (int i = 0; i < lines.length; i++) {
			String line = lines[i];

			String newLine = line;

			boolean parenthesis = false;

			if (line.contains(_VELOCITY_IF_START) &&
				_isVelocityStatement(line, _VELOCITY_IF_START)) {

				parenthesis = true;

				newLine = StringUtil.replace(
					newLine, _VELOCITY_IF_START, "<#if");

				_replaceIfStatementEnd(lines, i);
			}
			else if (line.contains(_VELOCITY_ELSEIF_START) &&
					 _isVelocityStatement(line, _VELOCITY_ELSEIF_START)) {

				parenthesis = true;

				newLine = StringUtil.replace(
					newLine, _VELOCITY_ELSEIF_START, "<#elseif");
			}
			else if (line.contains(_VELOCITY_ELSE_START) &&
					 !line.contains(_VELOCITY_ELSEIF_START) &&
					 _isVelocityStatement(line, _VELOCITY_ELSE_START)) {

				newLine = StringUtil.replace(
					newLine, _VELOCITY_ELSE_START, "<#else>");
			}

			if (parenthesis) {
				if (_hasBreakLine(newLine)) {
					String nextLine = lines[i + 1];

					lines[i + 1] = StringUtil.replaceLast(
						nextLine, CharPool.CLOSE_PARENTHESIS,
						CharPool.GREATER_THAN);
				}
				else {
					newLine = StringUtil.replaceLast(
						newLine, CharPool.CLOSE_PARENTHESIS,
						CharPool.GREATER_THAN);
				}

				newLine = StringUtil.removeFirst(
					newLine, StringPool.OPEN_PARENTHESIS);
			}

			lines[i] = newLine;
		}

		return com.liferay.petra.string.StringUtil.merge(
			lines, StringPool.NEW_LINE);
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

	private static void _replaceIfStatementEnd(String[] lines, int lineIndex) {
		Stack<String> stack = new Stack<>();

		stack.push(_VELOCITY_IF_START);

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
				lines[nextLineIndex] = StringUtil.replace(
					nextLine, _VELOCITY_END, "</#if>");
			}
		}
	}

	private boolean _hasBreakLine(String line) {
		if (StringUtil.count(line, CharPool.OPEN_PARENTHESIS) >
				StringUtil.count(line, CharPool.CLOSE_PARENTHESIS)) {

			return true;
		}

		return false;
	}

	private static final String _VELOCITY_ELSE_START = "#else";

	private static final String _VELOCITY_ELSEIF_START = "#elseif";

	private static final String _VELOCITY_END = "#end";

	private static final String _VELOCITY_FOREACH_START = "#foreach";

	private static final String _VELOCITY_IF_START = "#if";

	private static final String _VELOCITY_MACRO_START = "#macro";

}
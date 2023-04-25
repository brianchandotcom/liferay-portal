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

package com.liferay.source.formatter.check.util;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.check.constants.VelocityMigrationConstants;

import java.util.Stack;

/**
 * @author Nícolas Moura
 */
public class VelocityMigrationUtil {

	public static boolean isVelocityStatement(String line, String statement) {
		int previousCharIndex = line.indexOf(statement) - 1;

		if ((line.indexOf(statement) == 0) ||
			((line.charAt(previousCharIndex) != CharPool.LESS_THAN) &&
			 (line.charAt(previousCharIndex) != CharPool.SLASH))) {

			return true;
		}

		return false;
	}

	public static String removeFirstParenthesis(String line) {
		char previousChar = line.charAt(
			line.indexOf(CharPool.OPEN_PARENTHESIS) - 1);

		if (previousChar != CharPool.SPACE) {
			return StringUtil.replaceFirst(
				line, CharPool.OPEN_PARENTHESIS, CharPool.SPACE);
		}

		return StringUtil.removeFirst(line, StringPool.OPEN_PARENTHESIS);
	}

	public static void replaceStatementEnd(
		int lineIndex, String[] lines, String statement) {

		Stack<String> stack = new Stack<>();

		stack.push(statement);

		int nextLineIndex = lineIndex;

		while (!stack.empty()) {
			nextLineIndex += 1;

			String nextLine = lines[nextLineIndex];

			if (nextLine.contains(
					VelocityMigrationConstants.VELOCITY_IF_START) &&
				isVelocityStatement(
					nextLine, VelocityMigrationConstants.VELOCITY_IF_START)) {

				stack.push(VelocityMigrationConstants.VELOCITY_IF_START);
			}

			if (nextLine.contains(
					VelocityMigrationConstants.VELOCITY_FOREACH_START)) {

				stack.push(VelocityMigrationConstants.VELOCITY_FOREACH_START);
			}

			if (nextLine.contains(
					VelocityMigrationConstants.VELOCITY_MACRO_START) &&
				isVelocityStatement(
					nextLine,
					VelocityMigrationConstants.VELOCITY_MACRO_START)) {

				stack.push(VelocityMigrationConstants.VELOCITY_MACRO_START);
			}

			if (nextLine.contains(VelocityMigrationConstants.VELOCITY_END)) {
				stack.pop();
			}

			if (stack.empty()) {
				if (statement.equals(
						VelocityMigrationConstants.VELOCITY_IF_START)) {

					lines[nextLineIndex] = StringUtil.replace(
						nextLine, VelocityMigrationConstants.VELOCITY_END,
						"</#if>");
				}
				else if (statement.equals(
							VelocityMigrationConstants.
								VELOCITY_FOREACH_START)) {

					lines[nextLineIndex] = StringUtil.replace(
						nextLine, VelocityMigrationConstants.VELOCITY_END,
						"</#list>");
				}

				if (statement.equals(
						VelocityMigrationConstants.VELOCITY_MACRO_START)) {

					lines[nextLineIndex] = StringUtil.replace(
						nextLine, VelocityMigrationConstants.VELOCITY_END,
						"</#macro>");
				}
			}
		}
	}

}
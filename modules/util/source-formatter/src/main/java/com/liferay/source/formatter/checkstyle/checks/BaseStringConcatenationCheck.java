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

package com.liferay.source.formatter.checkstyle.checks;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.source.formatter.checkstyle.util.CheckstyleUtil;

/**
 * @author Hugo Huijser
 */
public abstract class BaseStringConcatenationCheck extends BaseCheck {

	protected void checkLiteralStringStartAndEndCharacter(
		String literalString1, String literalString2, int lineNumber) {

		if (literalString1.endsWith(StringPool.SLASH)) {
			log(
				lineNumber, _MSG_INVALID_END_CHARACTER,
				literalString1.charAt(literalString1.length() - 1));
		}

		if (literalString2.startsWith(StringPool.SPACE) ||
			(!literalString1.endsWith(StringPool.SPACE) &&
			 literalString2.matches("^[-:;.].*"))) {

			log(
				lineNumber + 1, _MSG_INVALID_START_CHARACTER,
				literalString2.charAt(0));
		}
	}

	protected int getMaxLineLength() {
		return GetterUtil.getInteger(
			getAttributeValue(CheckstyleUtil.MAX_LINE_LENGTH_KEY));
	}

	protected int getStringBreakPos(String s1, String s2, int i) {
		if (s2.startsWith(StringPool.SLASH)) {
			int pos = s2.lastIndexOf(StringPool.SLASH, i);

			if (pos > 0) {
				return pos - 1;
			}

			return -1;
		}

		if (s1.endsWith(StringPool.DASH)) {
			return Math.max(
				s2.lastIndexOf(StringPool.DASH, i - 1),
				s2.lastIndexOf(StringPool.SPACE, i - 1));
		}

		if (s1.endsWith(StringPool.PERIOD)) {
			return Math.max(
				s2.lastIndexOf(StringPool.PERIOD, i - 1),
				s2.lastIndexOf(StringPool.SPACE, i - 1));
		}

		if (s1.endsWith(StringPool.SPACE)) {
			return s2.lastIndexOf(StringPool.SPACE, i - 1);
		}

		return -1;
	}

	protected static final String MSG_COMBINE_LITERAL_STRINGS =
		"literal.string.combine";

	protected static final String MSG_INCORRECT_PLUS = "plus.incorrect";

	protected static final String MSG_MOVE_LITERAL_STRING =
		"literal.string.move";

	private static final String _MSG_INVALID_END_CHARACTER =
		"end.character.invalid";

	private static final String _MSG_INVALID_START_CHARACTER =
		"start.character.invalid";

}
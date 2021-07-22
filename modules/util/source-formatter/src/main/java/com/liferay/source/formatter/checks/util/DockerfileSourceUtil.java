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

package com.liferay.source.formatter.checks.util;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Objects;

/**
 * @author Peter Shin
 */
public class DockerfileSourceUtil {

	public static boolean endsWithBackSlash(String line) {
		String trimmedLine = StringUtil.trim(line);

		if (Validator.isNotNull(trimmedLine) &&
			trimmedLine.endsWith(StringPool.BACK_SLASH)) {

			return true;
		}

		return false;
	}

	public static String getInstruction(String line, String previousLine) {
		if (Validator.isNull(line) || endsWithBackSlash(previousLine)) {
			return StringPool.BLANK;
		}

		String[] words = StringUtil.split(line, CharPool.SPACE);

		String s = StringUtil.toUpperCase(StringUtil.trim(words[0]));

		if (!s.startsWith(StringPool.POUND)) {
			return s;
		}

		return StringPool.POUND;
	}

	public static boolean isNewInstruction(
		String instruction, String previousInstruction, String previousLine) {

		if (Validator.isNull(previousInstruction) ||
			endsWithBackSlash(previousLine) ||
			Objects.equals(instruction, previousInstruction)) {

			return false;
		}

		return true;
	}

}
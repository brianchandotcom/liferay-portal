/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check.util;

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
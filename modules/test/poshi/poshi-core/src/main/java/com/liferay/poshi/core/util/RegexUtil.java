/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.poshi.core.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Karen Dang
 * @author Michael Hashimoto
 */
public class RegexUtil {

	public static String escapeRegexChars(String regex) {
		return StringUtil.regexReplaceAll(regex, _REGEX_META, "\\\\$0");
	}

	public static String getGroup(String content, String regex, int group) {
		Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);

		Matcher matcher = pattern.matcher(content);

		if (matcher.find()) {
			return matcher.group(group);
		}

		return "";
	}

	public static String replace(String content, String regex, String group) {
		Pattern pattern = Pattern.compile(regex);

		Matcher matcher = pattern.matcher(content);

		if (matcher.find()) {
			return matcher.group(GetterUtil.getInteger(group));
		}

		return null;
	}

	private static final String _REGEX_META =
		"[\\\\\\^\\$\\{\\}\\[\\]\\(\\)\\.\\*\\+\\?\\|\\<\\>\\-\\&\\%]";

}
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class PropertiesLongLinesCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		if (absolutePath.endsWith("-portal.properties") ||
			absolutePath.matches(
				".*\\/portal-impl\\/src\\/portal[\\w-]+\\.properties")) {

			return content;
		}

		Matcher matcher = _commentsPattern.matcher(content);

		while (matcher.find()) {
			String match = matcher.group();

			String comment = StringUtil.removeSubstring(match, "\n    #");

			comment = _splitComment(comment);

			if (!StringUtil.equals(match, comment)) {
				return StringUtil.replaceFirst(
					content, match, comment, matcher.start());
			}
		}

		return content;
	}

	private String _splitComment(String comment) {
		if (comment.length() <= getMaxLineLength()) {
			return comment;
		}

		int x = -1;

		if (comment.startsWith("    # See http:")) {
			x = comment.indexOf(CharPool.SPACE, 10);
		}
		else {
			x = comment.indexOf(CharPool.SPACE, 6);
		}

		if (x == -1) {
			return comment;
		}

		if (x > getMaxLineLength()) {
			String s = "    # " + comment.substring(x + 1);

			return comment.substring(0, x) + "\n" + _splitComment(s);
		}

		x = comment.lastIndexOf(CharPool.SPACE, getMaxLineLength());

		String s = "    # " + comment.substring(x + 1);

		return comment.substring(0, x) + "\n" + _splitComment(s);
	}

	private static final Pattern _commentsPattern = Pattern.compile(
		"(    (?!# Env: )# (?! ).+)(\n    # (?! ).+)*");

}
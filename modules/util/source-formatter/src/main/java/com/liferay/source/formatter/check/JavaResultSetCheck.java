/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JavaResultSetCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		Matcher matcher = _tryStatementPattern.matcher(content);

		while (matcher.find()) {
			int x = content.indexOf("\"select count(", matcher.start());

			if (x == -1) {
				continue;
			}

			int y = content.indexOf("resultSet.getLong(1)", matcher.start());
			int z = content.indexOf(
				"\n" + matcher.group(1) + "}", matcher.start());

			if ((y != -1) && (z != -1) && (x < y) && (y < z)) {
				addMessage(
					fileName, "Use resultSet.getInt(1) for count",
					getLineNumber(content, y));
			}
		}

		return content;
	}

	private static final Pattern _tryStatementPattern = Pattern.compile(
		"\n(\t+)try [\\{\\(]");

}
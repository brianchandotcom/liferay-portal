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
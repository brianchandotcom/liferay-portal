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

package com.liferay.source.formatter.checks;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.checks.util.SourceUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Alan Huang
 * @author Hugo Huijser
 */
public class PropertiesStylingCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		content = content.replaceAll("(\n\n)( *#+)( [^#\\s])", "$1$2\n$2$3");

		content = content.replaceAll(
			"(\n)( *#+)( [^#\\s].*\n)(?!\\2([ \n]|\\Z))", "$1$2$3$2\n");

		content = content.replaceAll(
			"(\\A|\n)( *[\\w.-]+)(( +=)|(= +))(.*)(\\Z|\n)", "$1$2=$6$7");

		Matcher matcher = _sqlPattern.matcher(content);

		while (matcher.find()) {
			int lineNumber = getLineNumber(content, matcher.start());

			String nextLine = StringUtil.trim(
				SourceUtil.getLine(content, lineNumber + 1));

			String nextSQLValue = _getSQLValue(nextLine, matcher.group(1));

			if (nextSQLValue == null) {
				continue;
			}

			String sqlValue = matcher.group(2);

			if (sqlValue.compareTo(nextSQLValue) > 0) {
				content = StringUtil.replaceFirst(
					content, nextSQLValue, sqlValue,
					getLineStartPos(content, lineNumber + 1));

				return StringUtil.replaceFirst(
					content, sqlValue, nextSQLValue,
					getLineStartPos(content, lineNumber));
			}
		}

		return content;
	}

	private String _getSQLValue(String line, String columnName) {
		Pattern pattern = Pattern.compile("^\\(" + columnName + " ~ (\".*\")");

		Matcher matcher = pattern.matcher(line);

		if (matcher.find()) {
			return matcher.group(1);
		}

		return null;
	}

	private static final Pattern _sqlPattern = Pattern.compile(
		"\\s\\((.*) ~ (\".*\")\\) OR ");

}
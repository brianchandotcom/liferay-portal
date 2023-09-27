/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check.util;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alan Huang
 */
public class PythonSourceUtil {

	public static String getNestedStatementIndent(String statement) {
		String[] lines = StringUtil.splitLines(statement);

		if (lines.length <= 1) {
			return StringPool.BLANK;
		}

		for (int i = 1; i < lines.length; i++) {
			String line = lines[i];

			String indent = line.replaceFirst("^([\t ]+).+", "$1");

			if (!indent.equals(line)) {
				return indent;
			}
		}

		return StringPool.BLANK;
	}

	public static List<String> getPythonStatements(
		String content, String indent) {

		List<String> statements = new ArrayList<>();

		String[] lines = content.split("\n");

		StringBundler sb = new StringBundler();

		boolean insideMethod = false;

		for (String line : lines) {
			if (Validator.isNull(line.trim())) {
				sb.append("\n");

				continue;
			}

			if (!line.startsWith(indent)) {
				continue;
			}

			String s = line.substring(indent.length(), indent.length() + 1);

			String trimmedLine = line.trim();

			if (!s.equals(StringPool.SPACE) && !s.equals(StringPool.TAB) &&
				(sb.length() != 0) && !insideMethod) {

				sb.setIndex(sb.index() - 1);

				statements.add(sb.toString());

				sb.setIndex(0);
			}

			if ((trimmedLine.startsWith("def ") &&
				 !trimmedLine.endsWith("):")) ||
				(insideMethod && trimmedLine.endsWith("):"))) {

				insideMethod = insideMethod ^ true;
			}

			sb.append(line);
			sb.append("\n");
		}

		statements.add(sb.toString());

		return statements;
	}

}
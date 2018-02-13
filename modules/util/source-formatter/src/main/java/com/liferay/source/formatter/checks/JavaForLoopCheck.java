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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.source.formatter.parser.JavaTerm;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JavaForLoopCheck extends BaseJavaTermCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, JavaTerm javaTerm,
		String fileContent) {

		String javaTermContent = _formatForLoop(
			javaTerm.getContent(), _arrayPattern);

		return _formatForLoop(javaTermContent, _listPattern);
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_CONSTRUCTOR, JAVA_METHOD};
	}

	private String _formatForLoop(String javaTermContent, Pattern pattern) {
		Matcher matcher = pattern.matcher(javaTermContent);

		while (matcher.find()) {
			String collectionVariableName = matcher.group(3);
			String countVariableName = matcher.group(1);

			if (!Objects.equals(collectionVariableName, matcher.group(7)) ||
				!Objects.equals(countVariableName, matcher.group(2)) ||
				!Objects.equals(countVariableName, matcher.group(4)) ||
				!Objects.equals(countVariableName, matcher.group(8))) {

				continue;
			}

			int x = matcher.end();

			while (true) {
				x = javaTermContent.indexOf("}", x + 1);

				if (x == -1) {
					return javaTermContent;
				}

				if (ToolsUtil.isInsideQuotes(javaTermContent, x)) {
					continue;
				}

				String s = javaTermContent.substring(matcher.end(), x);

				if (getLevel(s, "{", "}") != 0) {
					continue;
				}

				if (s.matches("(?s).*\\W" + countVariableName + "\\W.*")) {
					break;
				}

				StringBundler sb = new StringBundler(7);

				sb.append("\tfor (");
				sb.append(matcher.group(5));
				sb.append(StringPool.SPACE);
				sb.append(matcher.group(6));
				sb.append(" : ");
				sb.append(collectionVariableName);
				sb.append(") {\n");

				return StringUtil.replaceFirst(
					javaTermContent, matcher.group(), sb.toString(),
					matcher.start());
			}
		}

		return javaTermContent;
	}

	private final Pattern _arrayPattern = Pattern.compile(
		"\tfor \\(int (\\w+) = 0;\\s+(\\w+) < (\\w+)\\.length;\\s+(\\w+)\\+" +
			"\\+\\) \\{\n\\s+([\\w\\[\\]]+) (\\w+) =\\s+(\\w+)\\[(\\w+)\\];\n");
	private final Pattern _listPattern = Pattern.compile(
		"\tfor \\(int (\\w+) = 0;\\s+(\\w+) < (\\w+)\\.size\\(\\);\\s+(\\w+)" +
			"\\+\\+\\) \\{\n\\s+([\\w<>]+) (\\w+) =\\s+(\\w+)\\.get\\(" +
				"(\\w+)\\);\n");

}
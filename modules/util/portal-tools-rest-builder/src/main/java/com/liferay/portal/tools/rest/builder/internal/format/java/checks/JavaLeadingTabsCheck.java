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

package com.liferay.portal.tools.rest.builder.internal.format.java.checks;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaClassParser;
import com.liferay.source.formatter.parser.JavaTerm;

import java.util.Objects;

/**
 * @author Peter Shin
 */
public class JavaLeadingTabsCheck {

	public static String format(String content, String fileName)
		throws Exception {

		String oldContent = content;

		while (true) {
			String newContent = _format(oldContent, fileName);

			if (oldContent.equals(newContent)) {
				return newContent;
			}

			oldContent = newContent;
		}
	}

	private static String _fixLeadingTabs(String line, int expectedTabCount) {
		int leadingTabCount = _getLeadingTabCount(line);

		String newLine = line;

		while (leadingTabCount != expectedTabCount) {
			if (leadingTabCount > expectedTabCount) {
				newLine = StringUtil.replaceFirst(
					newLine, CharPool.TAB, StringPool.BLANK);

				leadingTabCount--;
			}
			else {
				newLine = StringPool.TAB + newLine;

				leadingTabCount++;
			}
		}

		return newLine;
	}

	private static String _format(JavaTerm javaTerm) throws Exception {
		StringBuilder sb = new StringBuilder();

		int expectedTabsCount = 1;
		int lineBreakTabsCount = 0;

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(
					new UnsyncStringReader(javaTerm.getContent()))) {

			String line = null;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				if (line.isEmpty()) {
					sb.append("\n");

					continue;
				}

				if (_isClosingCurlyBrace(line) || _isChain(line)) {
					line = _fixLeadingTabs(line, expectedTabsCount - 1);
				}
				else {
					line = _fixLeadingTabs(line, expectedTabsCount);
				}

				sb.append(line);
				sb.append("\n");

				if (line.endsWith(">") || line.endsWith("=")) {
					expectedTabsCount = expectedTabsCount + 1;
					lineBreakTabsCount = lineBreakTabsCount + 1;
				}

				if ((lineBreakTabsCount > 0) && line.endsWith(";")) {
					expectedTabsCount = expectedTabsCount - lineBreakTabsCount;

					lineBreakTabsCount = 0;
				}

				int level = ToolsUtil.getLevel(
					line, new String[] {"{", "("}, new String[] {"}", ")"});

				expectedTabsCount = expectedTabsCount + level;
			}
		}

		return sb.toString();
	}

	private static String _format(String content, String fileName)
		throws Exception {

		JavaClass javaClass = JavaClassParser.parseJavaClass(fileName, content);

		String javaClassContent = javaClass.getContent();

		String javaClassHeader = content.substring(
			0, content.indexOf(javaClassContent));

		for (JavaTerm javaTerm : javaClass.getChildJavaTerms()) {
			javaClassContent = StringUtil.replace(
				javaClassContent, javaTerm.getContent(), _format(javaTerm));
		}

		return javaClassHeader + javaClassContent;
	}

	private static int _getLeadingTabCount(String line) {
		int leadingTabCount = 0;

		while (line.startsWith(StringPool.TAB)) {
			line = line.substring(1);

			leadingTabCount++;
		}

		return leadingTabCount;
	}

	private static boolean _isChain(String line) {
		String trimmedLine = line.trim();

		if (Objects.equals(trimmedLine, ");")) {
			return true;
		}

		if (trimmedLine.startsWith(").") && trimmedLine.endsWith("(")) {
			return true;
		}

		return false;
	}

	private static boolean _isClosingCurlyBrace(String line) {
		String trimmedLine = line.trim();

		if (Objects.equals(trimmedLine, "}") ||
			Objects.equals(trimmedLine, "};")) {

			return true;
		}

		return false;
	}

}
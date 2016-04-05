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

package com.liferay.source.formatter;

import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JSPSourceTabCalculator {

	public String calculateTabs(
			String fileName, String content,
			JSPSourceProcessor jspSourceProcessor)
		throws Exception {

		if (!fileName.endsWith(".jsp") && !fileName.endsWith(".jspf")) {
			return content;
		}

		_jspSourceProcessor = jspSourceProcessor;

		String originalContent = content;

		while (true) {
			String newContent = _calculateTabs(content, originalContent);

			if (newContent.equals(content) ||
				newContent.equals(originalContent)) {

				return newContent;
			}

			content = newContent;
		}
	}

	private int _calculateTabLevel(String text, boolean javaSource) {
		if (javaSource) {
			return _jspSourceProcessor.getLevel(
				text,
				new String[] {
					StringPool.OPEN_CURLY_BRACE, StringPool.OPEN_PARENTHESIS
				},
				new String[] {
					StringPool.CLOSE_CURLY_BRACE, StringPool.CLOSE_PARENTHESIS
				});
		}

		if (text.matches("(--)?%>")) {
			return -1;
		}

		if ((!text.startsWith("<") && !text.startsWith(">") &&
			 !text.startsWith("/>")) ||
			text.matches("(<%@ )?(page|taglib).*")) {

			return 0;
		}

		text = _stripJavaSource(text);

		text = _jspSourceProcessor.stripQuotes(text);

		int level = 0;

		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);

			if (c == '<') {
				if (((i + 1) < text.length()) && (text.charAt(i + 1) == '!')) {
					continue;
				}

				if (((i + 1) < text.length()) && (text.charAt(i + 1) == '/')) {
					level -= 1;
				}
				else {
					level += 1;
				}
			}
			else if ((c == '>') && (i > 0) && (text.charAt(i - 1) == '/')) {
				level -= 1;
			}
		}

		return level;
	}

	private String _calculateTabs(String content, String originalContent)
		throws Exception {

		List<JSPLine> jspLines = _getJSPLines(content);

		if (jspLines == null) {
			return originalContent;
		}

		for (int i = 0; i < jspLines.size(); i++) {
			JSPLine jspLine = jspLines.get(i);

			if (jspLine.isCloseTag()) {
				if (!jspLine.isClosed()) {
					return originalContent;
				}

				continue;
			}

			String line = jspLine.getLine();

			if (!jspLine.isOpenTag()) {
				if (!jspLine.isJavaSource() && line.matches("\t*<.*")) {
					int actualTabCount = jspLine.getLeadingTabCount();
					int expectedTabCount = jspLine.getTabLevel();

					if (expectedTabCount != actualTabCount) {
						return _fixTabs(
							content, jspLine.getLineCount(),
							actualTabCount - expectedTabCount);
					}
				}

				continue;
			}

			JSPLine closeTagJSPLine = _getCloseTagJSPLine(jspLine, jspLines, i);

			if (closeTagJSPLine == null) {
				return originalContent;
			}

			String closeTagName = closeTagJSPLine.getTagName();
			String openTagName = jspLine.getTagName();

			if (Validator.isNotNull(closeTagName) &&
				!closeTagName.equals(openTagName)) {

				return originalContent;
			}

			int expectedTabCount = _getExpectedTabCount(
				jspLine, closeTagJSPLine, jspLines, i);

			int actualCloseTagTabCount = closeTagJSPLine.getLeadingTabCount();
			int actualOpenTagTabCount = jspLine.getLeadingTabCount();

			if (expectedTabCount != actualOpenTagTabCount) {
				int diff = actualOpenTagTabCount - expectedTabCount;

				if (actualOpenTagTabCount == actualCloseTagTabCount) {
					return _fixTabs(
						content, jspLine.getLineCount(),
						closeTagJSPLine.getLineCount(), diff);
				}

				return _fixTabs(content, jspLine.getLineCount(), diff);
			}

			if (expectedTabCount != actualCloseTagTabCount) {
				return _fixTabs(
					content, closeTagJSPLine.getLineCount(),
					actualCloseTagTabCount - expectedTabCount);
			}

			if (line.matches("\t*<%!?")) {
				content = _checkTabsJavaSourceBlock(
					content, expectedTabCount, jspLine.getLineCount() + 1,
					closeTagJSPLine.getLineCount() - 1);
			}

			closeTagJSPLine.setClosed(true);
		}

		return content;
	}

	private String _checkTabsJavaSourceBlock(
		String content, int tabCount, int startLine, int endLine) {

		int minLeadingTabCount = -1;

		for (int i = startLine; i <= endLine; i++) {
			String line = _jspSourceProcessor.getLine(content, i);

			if (Validator.isNull(line)) {
				continue;
			}

			int leadingTabCount = _jspSourceProcessor.getLeadingTabCount(line);

			if (minLeadingTabCount == -1) {
				minLeadingTabCount = leadingTabCount;
			}
			else {
				minLeadingTabCount = Math.min(
					minLeadingTabCount, leadingTabCount);
			}
		}

		if (tabCount != minLeadingTabCount) {
			return _fixTabs(
				content, startLine, endLine, minLeadingTabCount - tabCount);
		}

		return content;
	}

	private String _fixTabs(String content, int lineCount, int diff) {
		return _fixTabs(content, lineCount, lineCount, diff);
	}

	private String _fixTabs(
		String content, int startLine, int endLine, int diff) {

		boolean insidePreTag = false;

		for (int i = startLine; i <= endLine; i++) {
			String line = _jspSourceProcessor.getLine(content, i);

			if (Validator.isNull(line)) {
				continue;
			}

			String trimmedLine = StringUtil.trimLeading(line);

			if (insidePreTag) {
				if (trimmedLine.equals("</pre>")) {
					insidePreTag = false;
				}
				else {
					continue;
				}
			}

			int lineStartPos = _jspSourceProcessor.getLineStartPos(content, i);

			if (diff < 0) {
				content =
					content.substring(0, lineStartPos) + "\t" +
						content.substring(lineStartPos);
			}
			else {
				content =
					content.substring(0, lineStartPos) +
						content.substring(lineStartPos + 1);
			}

			if (!insidePreTag && trimmedLine.equals("<pre>")) {
				insidePreTag = true;
			}
		}

		return content;
	}

	private JSPLine _getCloseTagJSPLine(
		JSPLine openTagJSPLine, List<JSPLine> jspLines, int index) {

		while (true) {
			index++;

			if (index >= jspLines.size()) {
				return null;
			}

			JSPLine jspLine = jspLines.get(index);

			if (!jspLine.isCloseTag()) {
				continue;
			}

			String openTagLine = openTagJSPLine.getLine();

			if (openTagLine.matches("\t*<%!?")) {
				String line = jspLine.getLine();

				if (line.matches("\t*%>")) {
					return jspLine;
				}

				continue;
			}

			if (jspLine.isCloseTag() &&
				(openTagJSPLine.getTabLevel() ==
					(jspLine.getLineTabLevel() + jspLine.getTabLevel()))) {

				return jspLine;
			}
		}
	}

	private int _getExpectedTabCount(
		JSPLine startTagJSPLine, JSPLine closeTagJSPLine,
		List<JSPLine> jspLines, int index) {

		String startTagLine = startTagJSPLine.getLine();

		if (!startTagLine.matches("\t*<%!?")) {
			return startTagJSPLine.getTabLevel();
		}

		int minJavaSourceLevel = startTagJSPLine.getTabLevel();

		while (true) {
			index++;

			JSPLine jspLine = jspLines.get(index);

			minJavaSourceLevel = Math.min(
				minJavaSourceLevel, jspLine.getTabLevel() - 1);

			if (jspLine.equals(closeTagJSPLine)) {
				return minJavaSourceLevel;
			}
		}
	}

	private List<JSPLine> _getJSPLines(String content) throws Exception {
		List<JSPLine> jspLines = new ArrayList();

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(content))) {

			String line = null;

			int lineCount = 0;

			int tabLevel = 0;

			boolean javaSource = false;
			boolean scriptSource = false;
			boolean multiLineComment = false;
			boolean insidePreTag = false;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				lineCount++;

				if (Validator.isNull(line)) {
					continue;
				}

				String trimmedLine = StringUtil.trimLeading(line);

				if (insidePreTag) {
					if (trimmedLine.equals("</pre>")) {
						insidePreTag = false;
					}
					else {
						continue;
					}
				}

				if (javaSource) {
					if (trimmedLine.equals("%>")) {
						javaSource = false;
					}

					if (trimmedLine.startsWith("/*") &&
						!trimmedLine.startsWith("/**")) {

						multiLineComment = true;

						continue;
					}

					if (line.endsWith("*/")) {
						multiLineComment = false;

						continue;
					}

					if (multiLineComment || trimmedLine.startsWith("*") ||
						trimmedLine.startsWith("/*") ||
						trimmedLine.startsWith("//")) {

						continue;
					}
				}
				else if (!scriptSource && line.endsWith("--%>")) {
					multiLineComment = false;
				}

				if (scriptSource && trimmedLine.matches("</(aui:)?script>")) {
					scriptSource = false;
				}

				if (trimmedLine.equals("AUI.add(")) {
					return jspLines;
				}

				if (!scriptSource && !multiLineComment) {
					int lineTabLevel = _calculateTabLevel(
						trimmedLine, javaSource);

					if (!javaSource && Math.abs(lineTabLevel) > 1) {
						return null;
					}

					JSPLine jspLine = new JSPLine(
						line, lineCount, tabLevel, lineTabLevel, javaSource);

					jspLines.add(jspLine);

					tabLevel += lineTabLevel;
				}

				if (!javaSource && trimmedLine.matches("<%!?")) {
					javaSource = true;
				}
				else if (!scriptSource) {
					if (trimmedLine.matches("<(aui:)?script.*")) {
						int lineTabLevel = _calculateTabLevel(
							trimmedLine, javaSource);

						if (lineTabLevel > 0) {
							scriptSource = true;
						}
					}
					else if (trimmedLine.startsWith("<%--")) {
						multiLineComment = true;
					}
					else if (trimmedLine.equals("<pre>")) {
						insidePreTag = true;
					}
				}
			}
		}

		return jspLines;
	}

	private String _stripJavaSource(String text) {
		while (true) {
			int x = text.indexOf("<%");

			if (x == - 1) {
				return text;
			}

			int y = text.indexOf("%>", x);

			if (y == -1) {
				return text;
			}

			text = text.substring(0, x) + text.substring(y + 2);
		}
	}

	private JSPSourceProcessor _jspSourceProcessor;

	private class JSPLine {

		public JSPLine(
			String line, int lineCount, int tabLevel, int lineTabLevel,
			boolean javaSource) {

			_line = line;
			_lineCount = lineCount;
			_tabLevel = tabLevel;
			_lineTabLevel = lineTabLevel;
			_javaSource = javaSource;
		}

		public int getLeadingTabCount() {
			return _jspSourceProcessor.getLeadingTabCount(_line);
		}

		public String getLine() {
			return _line;
		}

		public int getLineCount() {
			return _lineCount;
		}

		public int getLineTabLevel() {
			return _lineTabLevel;
		}

		public int getTabLevel() {
			return _tabLevel;
		}

		public String getTagName() {
			Matcher matcher = null;

			if (isCloseTag()) {
				matcher = _closeTagNamePattern.matcher(_line);
			}
			else if (isOpenTag()) {
				matcher = _openTagNamePattern.matcher(_line);
			}
			else {
				return null;
			}

			if (matcher.find()) {
				return matcher.group(1);
			}

			return null;
		}

		public boolean isClosed() {
			return _closed;
		}

		public boolean isCloseTag() {
			if (!_javaSource && (_lineTabLevel == -1)) {
				return true;
			}

			return false;
		}

		public boolean isJavaSource() {
			return _javaSource;
		}

		public boolean isOpenTag() {
			if (!_javaSource && (_lineTabLevel == 1)) {
				return true;
			}

			return false;
		}

		public void setClosed(boolean closed) {
			_closed = closed;
		}

		private boolean _closed = false;
		private final Pattern _closeTagNamePattern = Pattern.compile(
			"</([\\-:\\w]+?)>");
		private final boolean _javaSource;
		private final String _line;
		private final int _lineCount;
		private int _lineTabLevel;
		private final Pattern _openTagNamePattern = Pattern.compile(
			"<([\\-:\\w]+?)([ >\n].*|$)");
		private final int _tabLevel;

	}

}
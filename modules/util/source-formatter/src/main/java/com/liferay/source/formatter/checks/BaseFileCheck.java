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

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.SourceFormatterMessage;
import com.liferay.source.formatter.checks.util.SourceUtil;

import java.util.List;
import java.util.Set;

/**
 * @author Hugo Huijser
 */
public abstract class BaseFileCheck implements FileCheck {

	protected void addMessage(
		Set<SourceFormatterMessage> messages, String fileName, String message) {

		addMessage(messages, fileName, message, -1);
	}

	protected void addMessage(
		Set<SourceFormatterMessage> messages, String fileName, String message,
		int lineCount) {

		addMessage(messages, fileName, message, null, lineCount);
	}

	protected void addMessage(
		Set<SourceFormatterMessage> messages, String fileName, String message,
		String markdownFileName) {

		addMessage(messages, fileName, message, markdownFileName, -1);
	}

	protected void addMessage(
		Set<SourceFormatterMessage> messages, String fileName, String message,
		String markdownFileName, int lineCount) {

		messages.add(
			new SourceFormatterMessage(
				fileName, message, markdownFileName, lineCount));
	}

	protected int getLeadingTabCount(String line) {
		int leadingTabCount = 0;

		while (line.startsWith(StringPool.TAB)) {
			line = line.substring(1);

			leadingTabCount++;
		}

		return leadingTabCount;
	}

	protected int getLevel(String s) {
		return SourceUtil.getLevel(s);
	}

	protected int getLevel(
		String s, String increaseLevelString, String decreaseLevelString) {

		return SourceUtil.getLevel(s, increaseLevelString, decreaseLevelString);
	}

	protected int getLevel(
		String s, String[] increaseLevelStrings,
		String[] decreaseLevelStrings) {

		return SourceUtil.getLevel(
			s, increaseLevelStrings, decreaseLevelStrings);
	}

	protected int getLevel(
		String s, String[] increaseLevelStrings, String[] decreaseLevelStrings,
		int startLevel) {

		return SourceUtil.getLevel(
			s, increaseLevelStrings, decreaseLevelStrings, startLevel);
	}

	protected String getLine(String content, int lineCount) {
		int nextLineStartPos = getLineStartPos(content, lineCount);

		if (nextLineStartPos == -1) {
			return null;
		}

		int nextLineEndPos = content.indexOf(
			CharPool.NEW_LINE, nextLineStartPos);

		if (nextLineEndPos == -1) {
			return content.substring(nextLineStartPos);
		}

		return content.substring(nextLineStartPos, nextLineEndPos);
	}

	protected int getLineCount(String content, int pos) {
		return StringUtil.count(content, 0, pos, CharPool.NEW_LINE) + 1;
	}

	protected int getLineLength(String line) {
		int lineLength = 0;

		int tabLength = 4;

		for (char c : line.toCharArray()) {
			if (c == CharPool.TAB) {
				for (int i = 0; i < tabLength; i++) {
					lineLength++;
				}

				tabLength = 4;
			}
			else {
				lineLength++;

				tabLength--;

				if (tabLength <= 0) {
					tabLength = 4;
				}
			}
		}

		return lineLength;
	}

	protected int getLineStartPos(String content, int lineCount) {
		int x = 0;

		for (int i = 1; i < lineCount; i++) {
			x = content.indexOf(CharPool.NEW_LINE, x + 1);

			if (x == -1) {
				return x;
			}
		}

		return x + 1;
	}

	protected boolean hasGeneratedTag(String content) {
		if ((content.contains("* @generated") || content.contains("$ANTLR")) &&
			!content.contains("hasGeneratedTag")) {

			return true;
		}
		else {
			return false;
		}
	}

	protected boolean isExcludedPath(List<String> excludes, String path) {
		return isExcludedPath(excludes, path, -1);
	}

	protected boolean isExcludedPath(
		List<String> excludes, String path, int lineCount) {

		return isExcludedPath(excludes, path, lineCount, null);
	}

	protected boolean isExcludedPath(
		List<String> excludes, String path, int lineCount, String parameter) {

		if (ListUtil.isEmpty(excludes)) {
			return false;
		}

		String pathWithParameter = null;

		if (Validator.isNotNull(parameter)) {
			pathWithParameter = path + StringPool.AT + parameter;
		}

		String pathWithLineCount = null;

		if (lineCount > 0) {
			pathWithLineCount = path + StringPool.AT + lineCount;
		}

		for (String exclude : excludes) {
			if (Validator.isNull(exclude)) {
				continue;
			}

			if (exclude.startsWith("**")) {
				exclude = exclude.substring(2);
			}

			if (exclude.endsWith("**")) {
				exclude = exclude.substring(0, exclude.length() - 2);

				if (path.contains(exclude)) {
					return true;
				}

				continue;
			}

			if (path.endsWith(exclude) ||
				((pathWithParameter != null) &&
				 pathWithParameter.endsWith(exclude)) ||
				((pathWithLineCount != null) &&
				 pathWithLineCount.endsWith(exclude))) {

				return true;
			}
		}

		return false;
	}

	protected String stripQuotes(String s) {
		return stripQuotes(s, CharPool.APOSTROPHE, CharPool.QUOTE);
	}

	protected String stripQuotes(String s, char... delimeters) {
		List<Character> delimetersList = ListUtil.toList(delimeters);

		char delimeter = CharPool.SPACE;
		boolean insideQuotes = false;

		StringBundler sb = new StringBundler();

		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);

			if (insideQuotes) {
				if (c == delimeter) {
					int precedingBackSlashCount = 0;

					for (int j = i - 1; j >= 0; j--) {
						if (s.charAt(j) == CharPool.BACK_SLASH) {
							precedingBackSlashCount += 1;
						}
						else {
							break;
						}
					}

					if ((precedingBackSlashCount == 0) ||
						((precedingBackSlashCount % 2) == 0)) {

						insideQuotes = false;
					}
				}
			}
			else if (delimetersList.contains(c)) {
				delimeter = c;
				insideQuotes = true;
			}
			else {
				sb.append(c);
			}
		}

		return sb.toString();
	}

}
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
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.tools.ToolsUtil;
import com.liferay.source.formatter.checks.FileCheck;
import com.liferay.source.formatter.checks.WhitespaceCheck;

import java.io.File;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JSONSourceProcessor extends BaseSourceProcessor {

	protected void checkIndentation(
		String line, String fileName, int expectedTabCount, int lineCount) {

		if (Validator.isNull(line)) {
			return;
		}

		int leadingTabCount = getLeadingTabCount(line);

		if (line.matches("\t*[\\}\\]].*")) {
			expectedTabCount -= 1;
		}

		if (leadingTabCount == expectedTabCount) {
			return;
		}

		StringBundler sb = new StringBundler(5);

		sb.append("Line starts with '");
		sb.append(leadingTabCount);
		sb.append("' tabs, but '");
		sb.append(expectedTabCount);
		sb.append("' tabs are expected");

		processMessage(fileName, sb.toString(), lineCount);
	}

	@Override
	protected String doFormat(
			File file, String fileName, String absolutePath, String content)
		throws Exception {

		int expectedTabCount = 0;

		StringBundler sb = new StringBundler();

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(content))) {

			String line = null;

			int lineCount = 0;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				lineCount++;

				checkIndentation(line, fileName, expectedTabCount, lineCount);

				while (true) {
					Matcher matcher = _leadingSpacesPattern.matcher(line);

					if (!matcher.find()) {
						break;
					}

					line = matcher.replaceAll("$1\t$3");
				}

				line = StringUtil.replace(
					line, StringPool.DOUBLE_SPACE, StringPool.SPACE);

				sb.append(line);

				sb.append("\n");

				expectedTabCount = getLevel(
					line,
					new String[] {
						StringPool.OPEN_BRACKET, StringPool.OPEN_CURLY_BRACE
					},
					new String[] {
						StringPool.CLOSE_BRACKET, StringPool.CLOSE_CURLY_BRACE
					},
					expectedTabCount);
			}
		}

		content = sb.toString();

		if (content.endsWith("\n")) {
			content = content.substring(0, content.length() - 1);
		}

		Matcher matcher = _incorrectLineBreakPattern.matcher(content);

		if (matcher.find()) {
			processMessage(
				fileName, "There should be a line break after '}'",
				getLineCount(content, matcher.start()));
		}
		else {
			content = sort(content);
		}

		matcher = _missingWhitespacePattern.matcher(content);

		while (matcher.find()) {
			if (!ToolsUtil.isInsideQuotes(content, matcher.start())) {
				return StringUtil.insert(
					content, StringPool.SPACE, matcher.start() + 1);
			}
		}

		return content;
	}

	@Override
	protected List<String> doGetFileNames() throws Exception {
		return getFileNames(new String[0], getIncludes());
	}

	@Override
	protected String[] doGetIncludes() {
		return _INCLUDES;
	}

	@Override
	protected List<FileCheck> getFileChecks() {
		return _fileChecks;
	}

	@Override
	protected void populateFileChecks() {
		_fileChecks.add(new WhitespaceCheck(true));
	}

	protected String sort(String content) {
		String tabs = StringPool.BLANK;

		while (true) {
			Pattern pattern1 = Pattern .compile(
				"(\n|^)" + tabs + "[^\n\t]*[\\{\\[]\n");

			Matcher matcher1 = pattern1.matcher(content);

			if (!matcher1.find()) {
				break;
			}

			Pattern pattern2 = Pattern.compile(
				"((\n|^)" + tabs + "[^\n\t]*\\{\n" + tabs +
					"\t[^\n\t][\\s\\S]*?)\n" + tabs + "\\}");

			Matcher matcher2 = pattern2.matcher(content);

			while (matcher2.find()) {
				Pattern pattern3 = Pattern.compile(
					"(" + tabs + "\t[^\n\t]*?([^\\{\\[]|([\\{\\[]\n[\\s\\S]*?" +
						"\n" + tabs + "\t[\\}\\]]))),?(\n|$)");

				String match = matcher2.group(1);

				Matcher matcher3 = pattern3.matcher(match);

				String previousProperty = null;

				while (matcher3.find()) {
					String property = "\n" + matcher3.group(1);

					if (Validator.isNotNull(previousProperty) &&
						(previousProperty.compareTo(property) > 0)) {

						String replacement = StringUtil.replaceFirst(
							match, previousProperty, property);
						replacement = StringUtil.replaceLast(
							replacement, property, previousProperty);

						return StringUtil.replace(content, match, replacement);
					}

					previousProperty = property;
				}
			}

			tabs += "\t";
		}

		return content;
	}

	private static final String[] _INCLUDES = new String[] {"**/*.json"};

	private final List<FileCheck> _fileChecks = new ArrayList<>();
	private final Pattern _incorrectLineBreakPattern = Pattern.compile(
		"\t[\\}\\]]{2}");
	private final Pattern _leadingSpacesPattern = Pattern.compile(
		"(^[\t ]*)(  )([^ ])");
	private final Pattern _missingWhitespacePattern = Pattern.compile(":\\S");

}
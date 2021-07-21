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
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JSPLineBreakCheck extends BaseLineBreakCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(content))) {

			String line = null;
			String previousLine = StringPool.BLANK;

			boolean javaSource = false;
			boolean jsSource = false;

			int lineNumber = 0;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				lineNumber++;

				String trimmedLine = StringUtil.trimLeading(line);

				if (trimmedLine.equals("<%") || trimmedLine.equals("<%!")) {
					javaSource = true;
				}
				else if (trimmedLine.equals("%>")) {
					javaSource = false;
				}
				else if (trimmedLine.equals("<aui:script>") ||
						 trimmedLine.startsWith("<aui:script ") ||
						 trimmedLine.equals("<script>") ||
						 trimmedLine.startsWith("<script ")) {

					jsSource = true;
				}
				else if (trimmedLine.equals("</aui:script>") ||
						 trimmedLine.equals("</script>")) {

					jsSource = false;
				}

				if (!line.startsWith(StringPool.POUND) &&
					(!jsSource || javaSource)) {

					checkLineBreaks(line, previousLine, fileName, lineNumber);
				}

				previousLine = line;
			}
		}

		Matcher matcher = _missingLineBreakPattern.matcher(content);

		content = matcher.replaceAll("$1\n$3");

		return _fixRedundantCommaInsideArray(content);
	}

	private String _fixRedundantCommaInsideArray(String content) {
		Matcher matcher = _redundantCommaPattern.matcher(content);

		while (matcher.find()) {
			if (isJavaSource(content, matcher.start())) {
				return StringUtil.replaceFirst(
					content, StringPool.COMMA, StringPool.BLANK,
					matcher.start());
			}
		}

		return content;
	}

	private static final Pattern _missingLineBreakPattern = Pattern.compile(
		"([\n\t]((?!<%)[^\n\t])+?) *(%>[\"']\n)");
	private static final Pattern _redundantCommaPattern = Pattern.compile(
		",\n\t*\\}");

}
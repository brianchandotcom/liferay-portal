/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

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
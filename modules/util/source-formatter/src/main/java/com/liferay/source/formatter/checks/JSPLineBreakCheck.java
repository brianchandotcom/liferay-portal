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
import com.liferay.source.formatter.checks.util.JSPSourceUtil;

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

		content = _fixRedundantLineBreaks(content);

		return fixRedundantCommaInsideArray(content);
	}

	private String _fixRedundantLineBreaks(String content) {
		Matcher matcher = _redundantLineBreakPattern1.matcher(content);

		while (matcher.find()) {
			if (!JSPSourceUtil.isJavaSource(content, matcher.start(1))) {
				continue;
			}

			int x = matcher.start(1);

			while (true) {
				x = content.indexOf(StringPool.CLOSE_PARENTHESIS, x + 1);

				if (x == -1) {
					break;
				}

				String codeBlock = content.substring(matcher.start(1), x + 1);

				if (codeBlock.contains("{\n")) {
					break;
				}

				if (getLevel(codeBlock) != 0) {
					continue;
				}

				String lastLine = StringUtil.trim(
					getLine(content, getLineNumber(content, x)));

				if (lastLine.startsWith(StringPool.CLOSE_PARENTHESIS)) {
					break;
				}

				String codeSingleLine = StringUtil.replace(
					codeBlock, new String[] {StringPool.TAB, ",\n", "\n"},
					new String[] {StringPool.BLANK, ", ", StringPool.BLANK});

				return StringUtil.replaceFirst(
					content, codeBlock, codeSingleLine, matcher.start(1));
			}
		}

		matcher = _redundantLineBreakPattern2.matcher(content);

		while (matcher.find()) {
			if (JSPSourceUtil.isJavaSource(content, matcher.start())) {
				return StringUtil.replaceFirst(
					content, matcher.group(1), StringPool.SPACE,
					matcher.start());
			}
		}

		return content;
	}

	private static final Pattern _redundantLineBreakPattern1 = Pattern.compile(
		"[\n\t][^/\n\t].*(\\(\n)");
	private static final Pattern _redundantLineBreakPattern2 = Pattern.compile(
		"[\n\t][^/\n\t].*[|&](\n[\t ]*)");

}
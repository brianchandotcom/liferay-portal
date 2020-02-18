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

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
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
public class JSPWhitespaceCheck extends WhitespaceCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		content = _formatWhitespace(fileName, absolutePath, content);

		content = _formatDirectivesWhitespace(content);

		content = StringUtil.replace(
			content,
			new String[] {
				"@page import", "@tag import", "\"%>", ")%>", "function (",
				"javascript: ", "){\n", "\n\n\n"
			},
			new String[] {
				"@ page import", "@ tag import", "\" %>", ") %>", "function(",
				"javascript:", ") {\n", "\n\n"
			});

		return content;
	}

	@Override
	protected String formatDoubleSpace(String line) {
		String trimmedLine = StringUtil.trim(line);

		if (trimmedLine.startsWith(StringPool.DOUBLE_SLASH) ||
			trimmedLine.startsWith(StringPool.POUND) ||
			trimmedLine.startsWith(StringPool.STAR)) {

			return line;
		}

		Matcher matcher = _javaSourceInsideJSPLinePattern.matcher(line);

		while (matcher.find()) {
			String group = matcher.group();

			line = StringUtil.replace(
				line, group, super.formatDoubleSpace(group));
		}

		return super.formatDoubleSpace(line);
	}

	private String _formatDirectivesWhitespace(String content) {
		Matcher matcher = _directiveLinePattern.matcher(content);

		while (matcher.find()) {
			String directiveLine = matcher.group();

			String newDirectiveLine = formatIncorrectSyntax(
				directiveLine, " =", "=", false);

			newDirectiveLine = formatIncorrectSyntax(
				newDirectiveLine, "= ", "=", false);

			if (!directiveLine.equals(newDirectiveLine)) {
				content = StringUtil.replace(
					content, directiveLine, newDirectiveLine);
			}
		}

		return content.replaceAll(
			"(\\s(page|taglib))\\s+((import|uri)=)", "$1 $3");
	}

	private String _formatWhitespace(String line, boolean javaSource) {
		String trimmedLine = StringUtil.trimLeading(line);

		line = formatWhitespace(line, trimmedLine, javaSource);

		if (javaSource) {
			return line;
		}

		Matcher matcher = _javaSourceInsideJSPLinePattern.matcher(line);

		while (matcher.find()) {
			String linePart = matcher.group(1);

			if (!linePart.startsWith(StringPool.SPACE)) {
				return StringUtil.replace(
					line, matcher.group(), "<%= " + linePart + "%>");
			}

			if (!linePart.endsWith(StringPool.SPACE)) {
				return StringUtil.replace(
					line, matcher.group(), "<%=" + linePart + " %>");
			}

			line = formatWhitespace(line, linePart, true);
		}

		return line;
	}

	private String _formatWhitespace(
			String fileName, String absolutePath, String content)
		throws IOException {

		StringBundler sb = new StringBundler();

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(content))) {

			String line = null;

			boolean javaSource = false;
			boolean jsSource = false;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				if (!fileName.endsWith("/jsonws/action.jsp")) {
					line = trimLine(fileName, absolutePath, line);
				}

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

				if (jsSource && !javaSource) {
					sb.append(line);
					sb.append("\n");

					continue;
				}

				if (!trimmedLine.equals("%>") && line.contains("%>") &&
					!line.contains("--%>") && !line.contains(" %>")) {

					line = StringUtil.replace(line, "%>", " %>");
				}

				int pos = -1;

				while (true) {
					pos = line.indexOf("<%=", pos + 1);

					if ((pos == -1) || ((pos + 3) == line.length())) {
						break;
					}

					if (line.charAt(pos + 3) != CharPool.SPACE) {
						line = StringUtil.replaceFirst(
							line, "<%=", "<%= ", pos);
					}
				}

				if (trimmedLine.startsWith(StringPool.DOUBLE_SLASH) ||
					trimmedLine.startsWith(StringPool.STAR)) {

					sb.append(line);
					sb.append("\n");

					continue;
				}

				line = formatIncorrectSyntax(line, "\t ", "\t", false);

				line = _formatWhitespace(line, javaSource);

				if (line.endsWith(">")) {
					if (line.endsWith("/>")) {
						if (!trimmedLine.equals("/>") &&
							!line.endsWith(" />")) {

							line = StringUtil.replaceLast(line, "/>", " />");
						}
					}
					else if (line.endsWith(" >")) {
						line = StringUtil.replaceLast(line, " >", ">");
					}
				}

				while (trimmedLine.contains(StringPool.TAB)) {
					line = StringUtil.replaceLast(
						line, CharPool.TAB, StringPool.SPACE);

					trimmedLine = StringUtil.replaceLast(
						trimmedLine, CharPool.TAB, StringPool.SPACE);
				}

				line = formatSelfClosingTags(line);

				sb.append(line);

				sb.append("\n");
			}
		}

		content = sb.toString();

		if (content.endsWith("\n")) {
			content = content.substring(0, content.length() - 1);
		}

		return content;
	}

	private static final Pattern _directiveLinePattern = Pattern.compile(
		"<%@\n?.*%>");
	private static final Pattern _javaSourceInsideJSPLinePattern =
		Pattern.compile("<%=(.+?)%>");

}
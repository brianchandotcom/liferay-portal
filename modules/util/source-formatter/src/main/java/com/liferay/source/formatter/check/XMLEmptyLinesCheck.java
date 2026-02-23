/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.check.util.XMLSourceUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class XMLEmptyLinesCheck extends BaseEmptyLinesCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (fileName.matches(".*\\.(action|function|macro|testcase)") ||
			fileName.endsWith("/content.xml")) {

			return content;
		}

		content = fixEmptyLinesInMultiLineTags(content);
		content = fixEmptyLinesInNestedTags(content);
		content = fixMissingEmptyLineAfterDoctype(content);
		content = _fixEmptyLinesAroundConditionalTags(content);
		content = _fixEmptyLinesBetweenTags(fileName, content);
		content = _fixEmptyLinesInTag(content);
		content = _fixMissingEmptyLinesAroundComments(content);

		Matcher matcher = _redundantEmptyLinePattern.matcher(content);

		if (matcher.find()) {
			return StringUtil.replaceFirst(
				content, "\n\n", "\n", matcher.start());
		}

		return content;
	}

	private String _fixEmptyLinesAroundConditionalTags(String content) {
		Matcher matcher = _emptyLineAroundConditionalTagsPattern.matcher(
			content);

		while (matcher.find()) {
			if (XMLSourceUtil.isInsideCDATAMarkup(content, matcher.start())) {
				continue;
			}

			String lineBreaks = matcher.group(3);

			String tagName = matcher.group(2);

			if (lineBreaks.equals("\n") && tagName.equals("if")) {
				return StringUtil.replaceFirst(
					content, "\n", "\n\n", matcher.start(3));
			}
			else if (lineBreaks.equals("\n\n") &&
					 (tagName.equals("else") || tagName.equals("elseif") ||
					  tagName.equals("then"))) {

				return StringUtil.replaceFirst(
					content, "\n\n", "\n", matcher.start(3));
			}
		}

		return content;
	}

	private String _fixEmptyLinesBetweenTags(String fileName, String content) {
		if (fileName.endsWith("-log4j-ext.xml") ||
			fileName.endsWith("-log4j.xml") ||
			fileName.endsWith("-logback.xml") ||
			fileName.endsWith("/ivy.xml") ||
			fileName.endsWith("/struts-config.xml") ||
			fileName.endsWith("/tiles-defs.xml")) {

			return fixEmptyLinesBetweenTags(content);
		}

		return content;
	}

	private String _fixEmptyLinesInTag(String content) {
		Matcher matcher = _emptyLineInTagPattern1.matcher(content);

		while (matcher.find()) {
			String trimmedLine = StringUtil.trimLeading(
				getLine(content, getLineNumber(content, matcher.start())));

			if (trimmedLine.startsWith("<content") ||
				trimmedLine.startsWith("<echo") ||
				XMLSourceUtil.isInsideCDATAMarkup(content, matcher.start())) {

				continue;
			}

			return StringUtil.replaceFirst(
				content, "\n\n", "\n", matcher.start());
		}

		matcher = _emptyLineInTagPattern2.matcher(content);

		while (matcher.find()) {
			String tagName = matcher.group(1);

			if (tagName.startsWith("content") || tagName.startsWith("echo")) {
				continue;
			}

			String trimmedLine = StringUtil.trim(
				getLine(content, getLineNumber(content, matcher.start())));

			if (trimmedLine.startsWith("<") || trimmedLine.endsWith(">") ||
				XMLSourceUtil.isInsideCDATAMarkup(content, matcher.start())) {

				continue;
			}

			return StringUtil.replaceFirst(
				content, "\n\n", "\n", matcher.start());
		}

		return content;
	}

	private String _fixMissingEmptyLinesAroundComments(String content) {
		Matcher matcher = _missingEmptyLineAfterCommentPattern.matcher(content);

		if (matcher.find()) {
			return StringUtil.replaceFirst(
				content, "-->\n", "-->\n\n", matcher.start());
		}

		matcher = _missingEmptyLineBeforeCommentPattern.matcher(content);

		if (matcher.find()) {
			return StringUtil.replaceFirst(
				content, ">\n", ">\n\n", matcher.start());
		}

		return content;
	}

	private static final Pattern _emptyLineAroundConditionalTagsPattern =
		Pattern.compile("\n(\t*)</([-\\w:]+)>(\n+)\\1<[-\\w:]+[> \n]");
	private static final Pattern _emptyLineInTagPattern1 = Pattern.compile(
		">\n\n\t*+(?!<)");
	private static final Pattern _emptyLineInTagPattern2 = Pattern.compile(
		"\n\n\t+</(.+)>");
	private static final Pattern _missingEmptyLineAfterCommentPattern =
		Pattern.compile("[\t ]-->\n[\t<]");
	private static final Pattern _missingEmptyLineBeforeCommentPattern =
		Pattern.compile(">\n\t+<!--[\n ]");
	private static final Pattern _redundantEmptyLinePattern = Pattern.compile(
		"<\\?xml .*\\?>\n\n<\\!DOCTYPE");

}
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.portal.kernel.util.StringUtil;

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

		content = _fixEmptyLinesBetweenTags(fileName, content);

		content = _fixMissingEmptyLinesAroundComments(content);

		Matcher matcher = _redundantEmptyLinePattern.matcher(content);

		if (matcher.find()) {
			return StringUtil.replaceFirst(
				content, "\n\n", "\n", matcher.start());
		}

		return content;
	}

	private String _fixEmptyLinesBetweenTags(String fileName, String content) {
		if (fileName.startsWith(getBaseDirName() + "build") ||
			fileName.matches(".*/(build|tools/).*")) {

			return content;
		}

		if (fileName.endsWith("-log4j-ext.xml") ||
			fileName.endsWith("-log4j.xml") ||
			fileName.endsWith("-logback.xml") ||
			fileName.endsWith("/ivy.xml") ||
			fileName.endsWith("/struts-config.xml") ||
			fileName.endsWith("/tiles-defs.xml")) {

			return fixEmptyLinesBetweenTags(content);
		}

		Matcher matcher = _emptyLineBetweenTagsPattern.matcher(content);

		if (matcher.find()) {
			return StringUtil.replaceFirst(
				content, "\n\n", "\n", matcher.end(1));
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

	private static final Pattern _emptyLineBetweenTagsPattern = Pattern.compile(
		"\n(\t*)<[\\w/].*[^-]>(\n\n)(\t*)<(\\w)");
	private static final Pattern _missingEmptyLineAfterCommentPattern =
		Pattern.compile("[\t ]-->\n[\t<]");
	private static final Pattern _missingEmptyLineBeforeCommentPattern =
		Pattern.compile(">\n\t+<!--[\n ]");
	private static final Pattern _redundantEmptyLinePattern = Pattern.compile(
		"<\\?xml .*\\?>\n\n<\\!DOCTYPE");

}
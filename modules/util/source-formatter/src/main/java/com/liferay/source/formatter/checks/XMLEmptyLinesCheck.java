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

import com.liferay.portal.kernel.util.StringUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class XMLEmptyLinesCheck extends EmptyLinesCheck {

	@Override
	public boolean isPortalCheck() {
		return true;
	}

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (fileName.matches(".*\\.(action|function|macro|testcase)") ||
			fileName.startsWith(getBaseDirName() + "build") ||
			fileName.matches(".*/(build|tools/).*") ||
			fileName.endsWith("/content.xml")) {

			return content;
		}

		content = fixEmptyLinesInMultiLineTags(content);

		content = fixEmptyLinesInNestedTags(content);

		content = _fixEmptyLinesBetweenTags(fileName, content);

		content = _fixMissingEmptyLinesAroundComments(content);

		return content;
	}

	private String _fixEmptyLinesBetweenTags(String fileName, String content) {
		if (fileName.endsWith("-log4j.xml") ||
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

	private final Pattern _emptyLineBetweenTagsPattern = Pattern.compile(
		"\n(\t*)<[\\w/].*[^-]>(\n\n)(\t*)<(\\w)");
	private final Pattern _missingEmptyLineAfterCommentPattern =
		Pattern.compile("[\t ]-->\n[\t<]");
	private final Pattern _missingEmptyLineBeforeCommentPattern =
		Pattern.compile(">\n\t+<!--[\n ]");

}
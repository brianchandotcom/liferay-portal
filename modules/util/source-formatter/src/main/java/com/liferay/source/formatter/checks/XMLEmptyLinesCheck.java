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
import com.liferay.portal.kernel.util.Tuple;

import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class XMLEmptyLinesCheck extends EmptyLinesCheck {

	public XMLEmptyLinesCheck(String baseDirName) {
		_baseDirName = baseDirName;
	}

	@Override
	public Tuple process(String fileName, String absolutePath, String content)
		throws Exception {

		if (fileName.matches(".*\\.(action|function|macro|testcase)") ||
			fileName.startsWith(_baseDirName + "build") ||
			fileName.matches(".*/(build|tools/).*") ||
			fileName.endsWith("/content.xml")) {

			return new Tuple(content, Collections.emptySet());
		}

		content = fixEmptyLinesInMultiLineTags(content);

		content = fixEmptyLinesInNestedTags(content);

		content = _fixEmptyLinesBetweenTags(fileName, content);

		return new Tuple(content, Collections.emptySet());
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

	private final String _baseDirName;
	private final Pattern _emptyLineBetweenTagsPattern = Pattern.compile(
		"\n(\t*)<[\\w/].*[^-]>(\n\n)(\t*)<(\\w)");

}
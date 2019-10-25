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
public class PropertiesEmptyLinesCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		content = _fixMissingEmptyLinesAroundCategory(content);

		if (!fileName.endsWith("/liferay-plugin-package.properties")) {
			content = _fixMissingEmptyLinesAroundMultiLineProperty(content);
		}

		return content;
	}

	private String _fixMissingEmptyLinesAroundCategory(String content) {
		Matcher matcher = _missingEmptyLineAfterCategoryPattern.matcher(
			content);

		if (matcher.find()) {
			return StringUtil.replaceFirst(
				content, "\n", "\n\n", matcher.start(1) - 1);
		}

		matcher = _missingEmptyLineBeforeCategoryPattern.matcher(content);

		if (matcher.find()) {
			return StringUtil.replaceFirst(
				content, "\n", "\n\n", matcher.end(1));
		}

		return content;
	}

	private String _fixMissingEmptyLinesAroundMultiLineProperty(
		String content) {

		Matcher matcher = _missingEmptyLineAfterMultiLinePattern.matcher(
			content);

		if (matcher.find()) {
			return StringUtil.replaceFirst(
				content, "\n", "\n\n", matcher.start(1));
		}

		matcher = _missingEmptyLineBeforeMultiLinePattern.matcher(content);

		if (matcher.find()) {
			return StringUtil.replaceFirst(
				content, "\n", "\n\n", matcher.start(1));
		}

		return content;
	}

	private static final Pattern _missingEmptyLineAfterCategoryPattern =
		Pattern.compile("\n## \\w.*\n##\n([^\n#]|#[^#])");
	private static final Pattern _missingEmptyLineAfterMultiLinePattern =
		Pattern.compile(
			"\n *[^ #\n].*\\\\\n *[^ #\n][^#\n]*([^\\\\\n])\n[^\n]");
	private static final Pattern _missingEmptyLineBeforeCategoryPattern =
		Pattern.compile("\n([^\n#]|#[^#]).*\n##\n");
	private static final Pattern _missingEmptyLineBeforeMultiLinePattern =
		Pattern.compile("\n *(?![ #]).+(\n(?!\n) *[^#].*=\\\\\n)");

}
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

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

		while (matcher.find()) {
			String nextLinePropertyKey = StringUtil.extractFirst(
				matcher.group(4), "=");

			if (Validator.isNotNull(nextLinePropertyKey)) {
				continue;
			}

			String currentPropertyKeyPattern =
				Pattern.quote(matcher.group(1)) + "#?" +
					Pattern.quote(matcher.group(2));

			if (!nextLinePropertyKey.matches(currentPropertyKeyPattern)) {
				return StringUtil.replaceFirst(
					content, "\n", "\n\n", matcher.start(4) - 1);
			}
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
		Pattern.compile("( *)([^#\n].*?)=\\\\(\n\\1 +.+)++\n(.+)");
	private static final Pattern _missingEmptyLineBeforeCategoryPattern =
		Pattern.compile("\n([^\n#]|#[^#]).*\n##\n");
	private static final Pattern _missingEmptyLineBeforeMultiLinePattern =
		Pattern.compile("\n *(?![ #]).+(\n(?!\n) *[^#].*=\\\\\n)");

}
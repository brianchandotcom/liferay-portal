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
public class JavaEmptyLinesCheck extends BaseEmptyLinesCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		content = fixMissingEmptyLines(absolutePath, content);

		content = fixMissingEmptyLinesAroundComments(content);

		content = fixRedundantEmptyLines(content);

		content = fixMissingEmptyLineAfterSettingVariable(content);

		content = _fixRedundantEmptyLineInLambdaExpression(content);

		return content;
	}

	private String _fixRedundantEmptyLineInLambdaExpression(String content) {
		Matcher matcher = _redundantEmptyLinePattern.matcher(content);

		while (matcher.find()) {
			if (getLevel(matcher.group(1)) == 0) {
				return StringUtil.replaceFirst(
					content, "\n\n", "\n", matcher.start());
			}
		}

		return content;
	}

	private static final Pattern _redundantEmptyLinePattern = Pattern.compile(
		"\n(.*)-> \\{\n\n[\t ]*(?!// )\\S");

}
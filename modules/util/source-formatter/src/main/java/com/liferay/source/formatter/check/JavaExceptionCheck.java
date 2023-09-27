/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JavaExceptionCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		return _renameVariableNames(content);
	}

	private String _renameVariableNames(String content) {
		Matcher matcher = _catchExceptionPattern.matcher(content);

		int skipVariableNameCheckEndPos = -1;

		while (matcher.find()) {
			if (Validator.isNotNull(matcher.group(2))) {
				return StringUtil.replaceFirst(
					content, "final ", StringPool.BLANK, matcher.start());
			}

			String exceptionClassName = matcher.group(3);
			String exceptionVariableName = matcher.group(4);
			String tabs = matcher.group(1);

			String expectedExceptionVariableName = "e";

			if (!exceptionClassName.contains(" |")) {
				Matcher lowerCaseNumberOrPeriodMatcher =
					_lowerCaseNumberOrPeriodPattern.matcher(exceptionClassName);

				expectedExceptionVariableName = StringUtil.toLowerCase(
					lowerCaseNumberOrPeriodMatcher.replaceAll(
						StringPool.BLANK));
			}

			Pattern exceptionVariablePattern = Pattern.compile(
				"(\\W)" + exceptionVariableName + "(\\W)");

			int pos = content.indexOf(
				"\n" + tabs + StringPool.CLOSE_CURLY_BRACE, matcher.end() - 1);

			String insideCatchCode = content.substring(matcher.end(), pos + 1);

			if (insideCatchCode.contains("catch (" + exceptionClassName)) {
				skipVariableNameCheckEndPos = pos;
			}

			if ((skipVariableNameCheckEndPos < matcher.start()) &&
				!expectedExceptionVariableName.equals(exceptionVariableName)) {

				String catchExceptionCodeBlock = content.substring(
					matcher.start(), pos + 1);

				Matcher exceptionVariableMatcher =
					exceptionVariablePattern.matcher(catchExceptionCodeBlock);

				String catchExceptionReplacement =
					exceptionVariableMatcher.replaceAll(
						"$1" + expectedExceptionVariableName + "$2");

				return StringUtil.replaceFirst(
					content, catchExceptionCodeBlock, catchExceptionReplacement,
					matcher.start() - 1);
			}
		}

		return content;
	}

	private static final Pattern _catchExceptionPattern = Pattern.compile(
		"\n(\t+)catch \\((final )?(.+Exception) (.+)\\) \\{\n");
	private static final Pattern _lowerCaseNumberOrPeriodPattern =
		Pattern.compile("[a-z0-9.]");

}
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.check.util.JavaSourceUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Tamyris Bernardo
 */
public class UpgradeSetResultsSetTotalMethodCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		if (!fileName.endsWith(".java") && !fileName.endsWith(".jsp") &&
			!fileName.endsWith(".jspf")) {

			return content;
		}

		content = _removeSetTotal(content);
		content = _replaceSetResults(content);

		return content;
	}

	private String _removeSetTotal(String content) {
		String newContent = content;

		Matcher setTotalMatcher = _setTotalPattern.matcher(content);

		while (setTotalMatcher.find()) {
			if (hasClassOrVariableName(
					"SearchContainer", content, content,
					JavaSourceUtil.getMethodCall(
						content, setTotalMatcher.start()))) {

				newContent = StringUtil.removeSubstring(
					newContent, setTotalMatcher.group());
			}
		}

		return newContent;
	}

	private String _replaceSetResults(String content) {
		String newContent = content;

		Matcher setResultsMatcher = _setResultsPattern.matcher(content);

		while (setResultsMatcher.find()) {
			String methodCall = JavaSourceUtil.getMethodCall(
				content, setResultsMatcher.start());

			if (hasClassOrVariableName(
					"SearchContainer", content, content, methodCall)) {

				newContent = StringUtil.replace(
					newContent, methodCall,
					StringUtil.replace(
						methodCall, ".setResults(", ".setResultsAndTotal("));
			}
		}

		return newContent;
	}

	private static final Pattern _setResultsPattern = Pattern.compile(
		"\\w+\\.setResults\\(");
	private static final Pattern _setTotalPattern = Pattern.compile(
		"(\\s*)\\w+\\.setTotal\\([^;]+;");

}
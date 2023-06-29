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
					"SearchContainer", content,
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
					"SearchContainer", content, methodCall)) {

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
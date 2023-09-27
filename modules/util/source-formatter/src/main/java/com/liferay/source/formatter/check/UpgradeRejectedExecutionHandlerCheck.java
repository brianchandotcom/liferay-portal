/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringUtil;
import com.liferay.source.formatter.check.util.JavaSourceUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Nícolas Moura
 * @author Tamyris Bernardo
 */
public class UpgradeRejectedExecutionHandlerCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		if (!fileName.endsWith(".java")) {
			return content;
		}

		if (content.contains(_REJECTED_EXECUTION_HANDLER_IMPORT)) {
			content = com.liferay.portal.kernel.util.StringUtil.replace(
				content, _REJECTED_EXECUTION_HANDLER_IMPORT,
				"import java.util.concurrent.RejectedExecutionHandler;");
			content = _replaceRejectedExecutionHandler(content);
		}

		return content;
	}

	private String _replaceRejectedExecutionHandler(String content) {
		String newContent = content;

		boolean replaced = false;

		Matcher matcher = _rejectedExecutionHandlerPattern.matcher(content);

		while (matcher.find()) {
			String methodCall = matcher.group();

			newContent = StringUtil.replace(
				newContent, methodCall,
				StringUtil.replace(
					methodCall, matcher.group(1),
					"new ThreadPoolExecutor.CallerRunsPolicy()"));

			replaced = true;
		}

		if (replaced) {
			newContent = JavaSourceUtil.addImports(
				newContent, "java.util.concurrent.ThreadPoolExecutor");
		}

		return newContent;
	}

	private static final String _REJECTED_EXECUTION_HANDLER_IMPORT =
		"import com.liferay.portal.kernel.concurrent.RejectedExecutionHandler;";

	private static final Pattern _rejectedExecutionHandlerPattern =
		Pattern.compile(
			"RejectedExecutionHandler\\s+\\w+\\s*=\\s*" +
				"(\\s*new\\s*CallerRunsPolicy\\s*\\(\\))");

}
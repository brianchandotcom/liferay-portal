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
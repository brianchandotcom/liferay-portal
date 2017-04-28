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
import com.liferay.source.formatter.checks.util.JavaSourceUtil;

import java.io.File;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class JavaModuleInternalImportsCheck extends BaseFileCheck {

	@Override
	public boolean isModulesCheck() {
		return true;
	}

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (absolutePath.contains("/modules/core/") ||
			absolutePath.contains("/modules/util/") ||
			fileName.contains("/test/") ||
			fileName.contains("/testIntegration/")) {

			return content;
		}

		String packagePath = JavaSourceUtil.getPackagePath(content);

		if (!packagePath.startsWith("com.liferay")) {
			return content;
		}

		_checkInternalImports(fileName, absolutePath, content);

		return content;
	}

	private void _checkInternalImports(
		String fileName, String absolutePath, String content) {

		Matcher matcher = _internalImportPattern.matcher(content);

		int pos = -1;

		while (matcher.find()) {
			if (pos == -1) {
				pos = absolutePath.lastIndexOf("/com/liferay/");
			}

			String expectedImportFileLocation =
				absolutePath.substring(0, pos + 13) +
					StringUtil.replace(matcher.group(1), '.', '/') + ".java";

			File file = new File(expectedImportFileLocation);

			if (!file.exists()) {
				addMessage(
					fileName,
					"Do not import internal class from another module",
					getLineCount(content, matcher.start(1)));
			}
		}
	}

	private final Pattern _internalImportPattern = Pattern.compile(
		"\nimport com\\.liferay\\.(.*\\.internal\\.([a-z].*?\\.)?[A-Z].*?)" +
			"[\\.|;]");

}
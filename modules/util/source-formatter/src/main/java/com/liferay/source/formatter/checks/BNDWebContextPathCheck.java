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

import com.liferay.petra.string.StringPool;
import com.liferay.source.formatter.checks.util.BNDSourceUtil;
import com.liferay.source.formatter.util.FileUtil;

import java.io.File;
import java.io.IOException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hugo Huijser
 */
public class BNDWebContextPathCheck extends BaseFileCheck {

	@Override
	public boolean isModuleSourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		if (fileName.endsWith("/bnd.bnd") &&
			!isModulesApp(absolutePath, true) &&
			!absolutePath.contains("/testIntegration/") &&
			!absolutePath.contains("/third-party/")) {

			_checkWebContextPath(fileName, absolutePath, content);
		}

		return content;
	}

	private void _checkWebContextPath(
			String fileName, String absolutePath, String content)
		throws IOException {

		String moduleName = BNDSourceUtil.getModuleName(absolutePath);

		if (moduleName.contains("-import-") ||
			moduleName.contains("-private-")) {

			return;
		}

		String webContextPath = BNDSourceUtil.getDefinitionValue(
			content, "Web-ContextPath");

		if (_hasPackageJSONNameProperty(absolutePath)) {
			if (webContextPath == null) {
				addMessage(fileName, "Missing Web-ContextPath");
			}
		}
		else if ((webContextPath != null) &&
				 !webContextPath.equals("/" + moduleName)) {

			addMessage(
				fileName, "Incorrect Web-ContextPath '" + webContextPath + "'");
		}
	}

	private boolean _hasPackageJSONNameProperty(String absolutePath)
		throws IOException {

		int pos = absolutePath.lastIndexOf(StringPool.SLASH);

		File file = new File(
			absolutePath.substring(0, pos + 1) + "package.json");

		if (!file.exists()) {
			return false;
		}

		String content = FileUtil.read(file);

		Matcher matcher = _jsonNamePattern.matcher(content);

		while (matcher.find()) {
			if (getLevel(content.substring(0, matcher.start()), "{", "}") ==
					1) {

				return true;
			}
		}

		return false;
	}

	private static final Pattern _jsonNamePattern = Pattern.compile(
		"\n\\s*['\"]name['\"]:");

}
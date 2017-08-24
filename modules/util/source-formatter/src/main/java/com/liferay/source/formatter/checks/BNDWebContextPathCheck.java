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

import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.checks.util.BNDSourceUtil;
import com.liferay.source.formatter.util.FileUtil;

import java.io.File;
import java.io.IOException;

/**
 * @author Hugo Huijser
 */
public class BNDWebContextPathCheck extends BaseFileCheck {

	@Override
	public boolean isModulesCheck() {
		return true;
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		if (fileName.endsWith("/bnd.bnd") &&
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

		if ((webContextPath != null) &&
			!webContextPath.equals("/" + moduleName)) {

			addMessage(
				fileName, "Incorrect Web-ContextPath '" + webContextPath + "'",
				"bnd_bundle_information.markdown");
		}

		if (_hasPackageJSONNameProperty(absolutePath)) {
			if ((webContextPath == null) ||
				!webContextPath.equals("/" + moduleName)) {

				addMessage(
					fileName,
					"Incorrect Web-ContextPath '" + webContextPath + "'",
					"bnd_bundle_information.markdown");
			}
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

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(content))) {

			String line = null;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				line = StringUtil.trim(line);

				if (line.startsWith("'name':") ||
					line.startsWith("\"name\":")) {

					return true;
				}
			}
		}

		return false;
	}

}
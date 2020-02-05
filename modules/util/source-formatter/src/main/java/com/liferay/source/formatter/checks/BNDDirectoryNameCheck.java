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

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.source.formatter.checks.util.BNDSourceUtil;

/**
 * @author Hugo Huijser
 */
public class BNDDirectoryNameCheck extends BaseFileCheck {

	@Override
	public boolean isModuleSourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (fileName.endsWith("/bnd.bnd") &&
			!absolutePath.contains("/testIntegration/") &&
			!absolutePath.contains("/third-party/")) {

			_checkDirectoryName(fileName, absolutePath);
		}

		return content;
	}

	private void _checkDirectoryName(String fileName, String absolutePath) {
		String moduleName = BNDSourceUtil.getModuleName(absolutePath);

		if (absolutePath.matches(".*/apps(/.*){3,}")) {
			int x = absolutePath.lastIndexOf(StringPool.SLASH);

			int y = absolutePath.lastIndexOf(StringPool.SLASH, x - 1);

			int z = absolutePath.lastIndexOf(StringPool.SLASH, y - 1);

			String applicationName = absolutePath.substring(z + 1, y);

			if (!moduleName.startsWith(applicationName)) {
				addMessage(
					fileName,
					StringBundler.concat(
						"Module '", moduleName, "' should start with '",
						applicationName, "'"));
			}
		}

		if (moduleName.endsWith("-taglib-web")) {
			String newModuleName = moduleName.substring(
				0, moduleName.length() - 4);

			addMessage(
				fileName,
				StringBundler.concat(
					"Rename module '", moduleName, "' to '", newModuleName,
					"'"));
		}
	}

}
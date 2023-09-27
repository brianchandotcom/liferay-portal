/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.source.formatter.check.util.BNDSourceUtil;

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
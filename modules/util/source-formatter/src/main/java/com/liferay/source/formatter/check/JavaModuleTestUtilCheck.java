/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.source.formatter.check.util.JavaSourceUtil;

/**
 * @author Qi Zhang
 */
public class JavaModuleTestUtilCheck extends BaseFileCheck {

	@Override
	public boolean isModuleSourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (!fileName.endsWith("TestUtil.java") ||
			!isModulesFile(absolutePath) ||
			!absolutePath.contains("/src/testIntegration")) {

			return content;
		}

		String packageName = JavaSourceUtil.getPackageName(content);

		if (!packageName.startsWith("com.liferay")) {
			return content;
		}

		if (!packageName.endsWith(".test.util")) {
			addMessage(
				fileName,
				"Name of class ending with \"TestUtil\" should be in package " +
					"ending with \".test.util\"");
		}

		return content;
	}

}
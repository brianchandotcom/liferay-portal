/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaTerm;

/**
 * @author Qi Zhang
 */
public class JavaModuleTestUtilCheck extends BaseJavaTermCheck {

	@Override
	public boolean isModuleSourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
		String fileName, String absolutePath, JavaTerm javaTerm,
		String fileContent) {

		String content = javaTerm.getContent();

		if (!fileName.contains("/src/testIntegration")) {
			return content;
		}

		JavaClass javaClass = (JavaClass)javaTerm;

		if ((javaClass.getParentJavaClass() != null) ||
			javaClass.isAnonymous()) {

			return content;
		}

		String className = javaClass.getName();

		String packageName = javaClass.getPackageName();

		if (className.endsWith("TestUtil") &&
			packageName.startsWith("com.liferay") &&
			!packageName.endsWith(".test.util")) {

			addMessage(
				fileName,
				"TestUtil must be in package ending with '.test.util'");
		}

		return content;
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_CLASS};
	}

}
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

import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaTerm;

/**
 * @author Hugo Huijser
 */
public class JavaModuleTestCheck extends BaseJavaTermCheck {

	@Override
	public boolean isModuleSourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
		String fileName, String absolutePath, JavaTerm javaTerm,
		String fileContent) {

		String content = javaTerm.getContent();

		JavaClass javaClass = (JavaClass)javaTerm;

		if ((javaClass.getParentJavaClass() != null) ||
			javaClass.isAnonymous()) {

			return content;
		}

		String className = javaClass.getName();
		String packageName = javaClass.getPackageName();

		if (className.endsWith("Test") &&
			packageName.startsWith("com.liferay")) {

			_checkTestPackage(fileName, absolutePath, content, packageName);
		}

		return content;
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_CLASS};
	}

	private void _checkTestPackage(
		String fileName, String absolutePath, String content,
		String packageName) {

		if (absolutePath.contains("/src/testIntegration/java/") ||
			absolutePath.contains("/test/integration/")) {

			if (content.contains("@RunWith(Arquillian.class)") &&
				content.contains("import org.powermock.")) {

				addMessage(
					fileName, "Do not use PowerMock inside Arquillian tests",
					"power_mock.markdown");
			}

			if (!packageName.endsWith(".test")) {
				addMessage(
					fileName,
					"Module integration test must be under a test subpackage",
					"modules_tests.markdown");
			}
		}
		else if ((absolutePath.contains("/test/unit/") ||
				  absolutePath.contains("/src/test/java/")) &&
				 packageName.endsWith(".test")) {

			addMessage(
				fileName,
				"Module unit test should not be under a test subpackage",
				"modules_tests.markdown");
		}
	}

}
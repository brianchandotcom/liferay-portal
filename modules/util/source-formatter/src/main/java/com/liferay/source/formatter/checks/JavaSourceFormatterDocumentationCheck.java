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

import com.liferay.portal.tools.ToolsUtil;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaTerm;
import com.liferay.source.formatter.util.SourceFormatterUtil;

import java.io.File;
import java.io.IOException;

import java.util.List;

/**
 * @author Hugo Huijser
 */
public class JavaSourceFormatterDocumentationCheck extends BaseJavaTermCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
			String fileName, String absolutePath, JavaTerm javaTerm,
			String fileContent)
		throws IOException {

		if (javaTerm.isAbstract()) {
			return javaTerm.getContent();
		}

		JavaClass javaClass = (JavaClass)javaTerm;

		String packageName = javaClass.getPackageName();

		if ((packageName != null) &&
			(packageName.equals(
				"com.liferay.source.formatter.checkstyle.checks") ||
			 (packageName.equals("com.liferay.source.formatter.checks") &&
			  fileContent.contains("\taddMessage(")))) {

			_checkMissingDocumentation(fileName, javaClass);
		}

		return javaTerm.getContent();
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_CLASS};
	}

	private void _checkMissingDocumentation(
		String fileName, JavaClass javaClass) {

		if (_hasDocumentationFile(javaClass.getName())) {
			return;
		}

		List<String> extendedClassNames = javaClass.getExtendedClassNames();

		if (extendedClassNames.isEmpty()) {
			return;
		}

		String extendedClassName = extendedClassNames.get(0);

		if (!_hasDocumentationFile(extendedClassName) &&
			(!extendedClassName.startsWith("Base") ||
			 !_hasDocumentationFile(extendedClassName.substring(4)))) {

			addMessage(fileName, "Missing documentation file");
		}
	}

	private boolean _hasDocumentationFile(String checkName) {
		String markdownFileName = SourceFormatterUtil.getMarkdownFileName(
			checkName);

		File markdownFile = getFile(
			_DOCUMENTATION_DIR_LOCATION + markdownFileName,
			ToolsUtil.PORTAL_MAX_DIR_LEVEL);

		if (markdownFile != null) {
			return true;
		}

		return false;
	}

	private static final String _DOCUMENTATION_DIR_LOCATION =
		"modules/util/source-formatter/src/main/resources/documentation" +
			"/checks/";

}
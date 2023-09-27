/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

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
				"com.liferay.source.formatter.checkstyle.check") ||
			 (packageName.equals("com.liferay.source.formatter.check") &&
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
			_DOCUMENTATION_DIR_LOCATION + markdownFileName, getMaxDirLevel());

		if (markdownFile != null) {
			return true;
		}

		return false;
	}

	private static final String _DOCUMENTATION_DIR_LOCATION =
		"modules/util/source-formatter/src/main/resources/documentation/check/";

}
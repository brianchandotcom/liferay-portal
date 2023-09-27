/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.source.formatter.parser.JavaTerm;

/**
 * @author Hugo Huijser
 */
public class JavaLocalSensitiveComparisonCheck extends BaseJavaTermCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, JavaTerm javaTerm,
		String fileContent) {

		if (fileName.endsWith("Comparator.java")) {
			_checkLocalSensitiveComparison(fileName, javaTerm);
		}

		return javaTerm.getContent();
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_METHOD};
	}

	private void _checkLocalSensitiveComparison(
		String fileName, JavaTerm javaTerm) {

		String javaTermName = javaTerm.getName();

		if (!javaTermName.equals("compare")) {
			return;
		}

		String javaTermContent = javaTerm.getContent();

		if (javaTermContent.contains("_locale") &&
			javaTermContent.contains(".compareTo") &&
			!javaTermContent.contains("Collator")) {

			addMessage(
				fileName,
				"Use Collator for locale-sensitive String comparison");
		}
	}

}
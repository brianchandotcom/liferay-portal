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
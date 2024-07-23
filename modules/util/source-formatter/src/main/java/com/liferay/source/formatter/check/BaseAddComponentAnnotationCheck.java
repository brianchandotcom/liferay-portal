/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaClassParser;

/**
 * @author Drew Brokke
 * @author Kyle Miho
 */
public abstract class BaseAddComponentAnnotationCheck extends BaseUpgradeCheck {

	@Override
	protected String format(
			String fileName, String absolutePath, String content)
		throws Exception {

		JavaClass javaClass = JavaClassParser.parseJavaClass(fileName, content);

		if (javaClass.hasAnnotation("Component")) {
			return content;
		}

		for (String extendedClassName : javaClass.getExtendedClassNames()) {
			if (isValidClassName(extendedClassName)) {
				return content.replaceFirst(
					"public class",
					joinLines(
						getAnnotationContent(
							absolutePath, extendedClassName, content,
							javaClass),
						"public class"));
			}
		}

		return content;
	}

	protected abstract String getAnnotationContent(
		String absolutePath, String className, String content,
		JavaClass javaClass);

	protected abstract boolean isValidClassName(String className);

}
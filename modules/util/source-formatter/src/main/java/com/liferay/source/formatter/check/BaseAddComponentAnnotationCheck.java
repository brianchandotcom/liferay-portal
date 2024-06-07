/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.Validator;
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

		String validExtendedClassName = null;

		for (String extendedClassName : javaClass.getExtendedClassNames()) {
			if (isValidClassName(extendedClassName)) {
				validExtendedClassName = extendedClassName;

				break;
			}
		}

		if (Validator.isNull(validExtendedClassName)) {
			return content;
		}

		return content.replaceFirst(
			"public class",
			joinLines(
				getAnnotationContent(
					validExtendedClassName, content, javaClass),
				"public class"));
	}

	protected abstract String getAnnotationContent(
		String className, String content, JavaClass javaClass);

	protected abstract boolean isValidClassName(String className);

	protected String joinLines(String... lines) {
		StringBundler sb = new StringBundler((lines.length * 2) - 1);

		for (String line : lines) {
			if (sb.index() > 0) {
				sb.append(StringPool.NEW_LINE);
			}

			sb.append(line);
		}

		return sb.toString();
	}

}
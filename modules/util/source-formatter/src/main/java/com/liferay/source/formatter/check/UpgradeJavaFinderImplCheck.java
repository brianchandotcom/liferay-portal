/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaClassParser;

import java.util.List;

/**
 * @author Kyle Miho
 */
public class UpgradeJavaFinderImplCheck extends BaseUpgradeCheck {

	@Override
	protected String format(
			String fileName, String absolutePath, String content)
		throws Exception {

		JavaClass javaClass = JavaClassParser.parseJavaClass(fileName, content);

		String finderBaseImplName = _getFinderBaseImplName(javaClass);

		if (Validator.isNull(finderBaseImplName) ||
			javaClass.hasAnnotation("Component")) {

			return content;
		}

		String modelName = StringUtil.extractFirst(
			finderBaseImplName, "BaseImpl");

		return content.replaceFirst(
			"(public class)",
			"@Component(service = " + modelName + ".class)\n$1");
	}

	@Override
	protected String[] getNewImports() {
		return new String[] {
			"org.osgi.service.component.annotations.Component"
		};
	}

	private String _getFinderBaseImplName(JavaClass javaClass) {
		List<String> extendedClassNames = javaClass.getExtendedClassNames();

		for (String extendedClassName : extendedClassNames) {
			if (extendedClassName.contains("FinderBaseImpl")) {
				return extendedClassName;
			}
		}

		return null;
	}

}
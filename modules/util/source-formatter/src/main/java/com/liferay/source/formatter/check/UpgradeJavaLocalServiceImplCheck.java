/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaClassParser;

import java.util.List;

/**
 * @author Kyle Miho
 */
public class UpgradeJavaLocalServiceImplCheck extends BaseUpgradeCheck {

	@Override
	protected String format(
			String fileName, String absolutePath, String content)
		throws Exception {

		JavaClass javaClass = JavaClassParser.parseJavaClass(fileName, content);

		if (Validator.isNull(_getLocalServiceBaseImplName(javaClass)) ||
			javaClass.hasAnnotation("Component")) {

			return content;
		}

		String component = StringBundler.concat(
			"@Component(", StringPool.NEW_LINE, StringPool.TAB,
			"property = \"model.class.name=",
			_getFullyQualifiedModelClassName(javaClass), "\",",
			StringPool.NEW_LINE, StringPool.TAB, "service = AopService.class",
			StringPool.NEW_LINE, ")");

		return content.replaceFirst("(public class)", component + "\n$1");
	}

	@Override
	protected String[] getNewImports() {
		return new String[] {
			"com.liferay.portal.aop.AopService",
			"org.osgi.service.component.annotations.Component"
		};
	}

	private String _getFullyQualifiedModelClassName(JavaClass javaClass) {
		String fullyQualifiedLocalServiceImplClassName = javaClass.getName(
			true);

		fullyQualifiedLocalServiceImplClassName = StringUtil.extractFirst(
			fullyQualifiedLocalServiceImplClassName, "LocalServiceImpl");

		return StringUtil.replace(
			fullyQualifiedLocalServiceImplClassName, ".service.impl", ".model");
	}

	private String _getLocalServiceBaseImplName(JavaClass javaClass) {
		List<String> extendedClassNames = javaClass.getExtendedClassNames();

		for (String extendedClassName : extendedClassNames) {
			if (extendedClassName.contains("LocalServiceBaseImpl")) {
				return extendedClassName;
			}
		}

		return null;
	}

}
/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.parser.JavaClass;

/**
 * @author Kyle Miho
 */
public class UpgradeJavaLocalServiceImplCheck
	extends BaseAddComponentAnnotationCheck {

	@Override
	protected String getAnnotationContent(
		String absolutePath, String className, String content,
		JavaClass javaClass) {

		return joinLines(
			"@Component(",
			String.format(
				"\tproperty = \"model.class.name=%s\",",
				_getFullyQualifiedModelClassName(javaClass)),
			"\tservice = AopService.class", ")");
	}

	@Override
	protected String[] getNewImports() {
		return new String[] {
			"com.liferay.portal.aop.AopService",
			"org.osgi.service.component.annotations.Component"
		};
	}

	@Override
	protected boolean isValidClassName(String className) {
		return className.contains("LocalServiceBaseImpl");
	}

	private String _getFullyQualifiedModelClassName(JavaClass javaClass) {
		String fullyQualifiedLocalServiceImplClassName = javaClass.getName(
			true);

		fullyQualifiedLocalServiceImplClassName = StringUtil.extractFirst(
			fullyQualifiedLocalServiceImplClassName, "LocalServiceImpl");

		return StringUtil.replace(
			fullyQualifiedLocalServiceImplClassName, ".service.impl", ".model");
	}

}
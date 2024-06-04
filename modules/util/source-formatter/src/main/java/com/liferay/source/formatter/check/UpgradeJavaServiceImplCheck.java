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
public class UpgradeJavaServiceImplCheck
	extends BaseAddComponentAnnotationCheck {

	@Override
	protected String getAnnotationContent(
		String className, String content, JavaClass javaClass) {

		return joinLines(
			"@Component(", "\tproperty = {",
			String.format(
				"\t\t\"json.web.service.context.name=%s\",",
				_getContextName(javaClass)),
			String.format(
				"\t\t\"json.web.service.context.path=%s\"",
				_getContextPath(className)),
			"\t},", "\tservice = AopService.class", ")");
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
		if (className.contains("LocalServiceBaseImpl")) {
			return false;
		}

		return className.contains("ServiceBaseImpl");
	}

	private static String _getContextName(JavaClass javaClass) {
		String contextName = StringUtil.extractFirst(
			javaClass.getPackageName(), ".service.impl");

		return StringUtil.extractLast(contextName, ".");
	}

	private static String _getContextPath(String baseImplName) {
		return StringUtil.extractFirst(baseImplName, "ServiceBaseImpl");
	}

}
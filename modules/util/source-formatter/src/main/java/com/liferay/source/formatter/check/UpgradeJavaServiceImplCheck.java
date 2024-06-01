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
public class UpgradeJavaServiceImplCheck extends BaseUpgradeCheck {

	@Override
	protected String format(
			String fileName, String absolutePath, String content)
		throws Exception {

		JavaClass javaClass = JavaClassParser.parseJavaClass(fileName, content);

		String serviceBaseImplName = _getServiceBaseImplName(javaClass);

		if (Validator.isNull(serviceBaseImplName) ||
			javaClass.hasAnnotation("Component")) {

			return content;
		}

		String contextName = _getContextName(javaClass);

		String contextPath = StringUtil.extractFirst(
			serviceBaseImplName, "ServiceBaseImpl");

		String component = StringBundler.concat(
			"@Component(", StringPool.NEW_LINE, StringPool.TAB, "property = {",
			StringPool.NEW_LINE, StringPool.TAB, StringPool.TAB,
			"\"json.web.service.context.name=", contextName, "\",",
			StringPool.NEW_LINE, StringPool.TAB, StringPool.TAB,
			"\"json.web.service.context.path=", contextPath, StringPool.QUOTE,
			StringPool.NEW_LINE, StringPool.TAB, "},", StringPool.NEW_LINE,
			StringPool.TAB, "service = AopService.class", StringPool.NEW_LINE,
			")");

		return content.replaceFirst("(public class)", component + "\n$1");
	}

	@Override
	protected String[] getNewImports() {
		return new String[] {
			"com.liferay.portal.aop.AopService",
			"org.osgi.service.component.annotations.Component"
		};
	}

	private String _getContextName(JavaClass javaClass) {
		String contextName = StringUtil.extractFirst(
			javaClass.getPackageName(), ".service.impl");

		return StringUtil.extractLast(contextName, ".");
	}

	private String _getServiceBaseImplName(JavaClass javaClass) {
		List<String> extendedClassNames = javaClass.getExtendedClassNames();

		for (String extendedClassName : extendedClassNames) {
			if (extendedClassName.contains("ServiceBaseImpl")) {
				if (extendedClassName.contains("LocalServiceBaseImpl")) {
					return null;
				}

				return extendedClassName;
			}
		}

		return null;
	}

}
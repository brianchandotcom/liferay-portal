/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringBundler;
import com.liferay.source.formatter.check.util.JavaSourceUtil;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaTerm;
import com.liferay.source.formatter.parser.JavaVariable;

import java.util.List;

/**
 * @author Alan Huang
 */
public class JavaUnnamedSFCheck extends BaseJavaTermCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
		String fileName, String absolutePath, JavaTerm javaTerm,
		String fileContent) {

		String content = javaTerm.getContent();

		if (absolutePath.contains("-test/")) {
			return content;
		}

		JavaClass javaClass = (JavaClass)javaTerm;

		if (!javaClass.hasAnnotation("Component") && !javaClass.isStatic() &&
			!isUpgradeProcess(absolutePath, content)) {

			return content;
		}

		for (JavaTerm childJavaTerm : javaClass.getChildJavaTerms()) {
			if (!childJavaTerm.isJavaVariable()) {
				continue;
			}

			JavaVariable javaVariable = (JavaVariable)childJavaTerm;

			String variableName = javaVariable.getName();

			if ((!variableName.contains("Id") &&
				 !variableName.contains("id")) ||
				variableName.contains("Company") ||
				variableName.contains("company")) {

				continue;
			}

			String variableTypeName = getVariableTypeName(
				javaVariable.getContent(), childJavaTerm, fileContent, fileName,
				variableName, true, false);

			if (!variableTypeName.startsWith("Map<Long")) {
				continue;
			}

			for (String methodName : _METHOD_NAMES) {
				_check(fileName, content, javaTerm, methodName, variableName);
			}
		}

		return content;
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_CLASS};
	}

	private void _check(
		String fileName, String content, JavaTerm javaTerm, String methodName,
		String variableName) {

		int x = -1;

		while (true) {
			x = content.indexOf(
				StringBundler.concat(variableName, ".", methodName, "("),
				x + 1);

			if (x == -1) {
				break;
			}

			List<String> getParameterNames = JavaSourceUtil.getParameterNames(
				JavaSourceUtil.getMethodCall(content, x));

			if (getParameterNames.size() != 2) {
				continue;
			}

			String parameter = getParameterNames.get(0);

			if (parameter.contains("CompanyId") ||
				parameter.contains("companyId")) {

				continue;
			}

			addMessage(
				fileName, variableName + "." + methodName,
				javaTerm.getLineNumber(x));
		}
	}

	private static final String[] _METHOD_NAMES = {
		"computeIfAbsent", "computeIfPresent", "put"
	};

}
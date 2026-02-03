/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.source.formatter.check.util.JavaSourceUtil;
import com.liferay.source.formatter.parser.JavaClass;
import com.liferay.source.formatter.parser.JavaTerm;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Peter Shin
 */
public class JavaUpgradeConnectionCheck extends BaseJavaTermCheck {

	@Override
	public boolean isLiferaySourceCheck() {
		return true;
	}

	@Override
	protected String doProcess(
		String fileName, String absolutePath, JavaTerm javaTerm,
		String fileContent) {

		if (absolutePath.contains("/test/") ||
			absolutePath.contains("/testIntegration/") ||
			!absolutePath.contains("/upgrade/") ||
			!isUpgradeProcess(absolutePath, fileContent) ||
			(javaTerm.getParentJavaClass() != null)) {

			return javaTerm.getContent();
		}

		JavaClass javaClass = (JavaClass)javaTerm;

		for (JavaTerm childJavaTerm : javaClass.getChildJavaTerms()) {
			if (!childJavaTerm.isJavaMethod()) {
				continue;
			}

			_checkDataAccessGetConnectionCall(fileName, childJavaTerm);
			_checkMissingConnectionInDBRunSQLCall(
				fileName, childJavaTerm, javaClass);
		}

		return javaTerm.getContent();
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_CLASS};
	}

	private void _checkDataAccessGetConnectionCall(
		String fileName, JavaTerm javaTerm) {

		String methodName = javaTerm.getName();

		if (methodName.equals("upgrade") &&
			javaTerm.hasAnnotation("Override")) {

			return;
		}

		String content = javaTerm.getContent();

		int index = content.indexOf("DataAccess.getConnection(");

		if (index == -1) {
			return;
		}

		addMessage(
			fileName,
			"Use existing connection field instead of calling \"DataAccess." +
				"getConnection\"",
			javaTerm.getLineNumber(index));
	}

	private void _checkMissingConnectionInDBRunSQLCall(
		String fileName, JavaTerm javaTerm, JavaClass javaClass) {

		String content = javaTerm.getContent();

		Matcher matcher = _runSQLPattern.matcher(content);

		while (matcher.find()) {
			String variableName = matcher.group(1);

			String variableTypeName = getVariableTypeName(
				content, javaTerm, javaClass.getContent(), fileName,
				variableName);

			if ((variableTypeName == null) || !variableTypeName.equals("DB")) {
				continue;
			}

			List<String> parameterList = JavaSourceUtil.getParameterList(
				content.substring(matcher.start()));

			if (parameterList.size() != 1) {
				continue;
			}

			String parameter = parameterList.get(0);

			if (parameter.matches("(?i)(\\w+\\.)?\\w+SQL\\(.*\\)") ||
				parameter.startsWith("\"") ||
				parameter.startsWith("StringBundler.concat(") ||
				parameter.startsWith("new String[]")) {

				addMessage(
					fileName,
					"Missing parameter \"connection\" for \"" + variableName +
						".runSQL\"",
					javaTerm.getLineNumber(matcher.start()));

				continue;
			}

			if (!parameter.matches("\\w+")) {
				continue;
			}

			variableTypeName = getVariableTypeName(
				content, javaTerm, javaClass.getContent(), fileName, parameter,
				true, false);

			if ((variableTypeName == null) ||
				(!variableTypeName.equals("String") &&
				 !variableTypeName.equals("String[]"))) {

				continue;
			}

			addMessage(
				fileName,
				"Missing parameter \"connection\" for \"" + variableName +
					".runSQL\"",
				javaTerm.getLineNumber(matcher.start()));
		}
	}

	private static final Pattern _runSQLPattern = Pattern.compile(
		"\\b(\\w+)\\.runSQL\\(");

}
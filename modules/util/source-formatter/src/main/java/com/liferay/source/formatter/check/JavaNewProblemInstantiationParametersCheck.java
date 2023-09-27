/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.source.formatter.check.util.JavaSourceUtil;
import com.liferay.source.formatter.parser.JavaTerm;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Qi Zhang
 */
public class JavaNewProblemInstantiationParametersCheck
	extends BaseJavaTermCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, JavaTerm javaTerm,
		String fileContent) {

		List<String> importNames = javaTerm.getImportNames();

		String javaTermContent = javaTerm.getContent();

		if (!importNames.contains(
				"com.liferay.portal.vulcan.jaxrs.exception.mapper.Problem")) {

			return javaTermContent;
		}

		Matcher matcher = _newProblemPattern.matcher(javaTermContent);

		while (matcher.find()) {
			String methodCall = JavaSourceUtil.getMethodCall(
				javaTermContent, matcher.start());

			List<String> parameterList = JavaSourceUtil.getParameterList(
				methodCall);

			parameterList.replaceAll(
				parameter -> parameter.replaceAll("\n\t*", StringPool.BLANK));

			String exceptionVariableName = null;

			if (parameterList.size() == 2) {
				String parameterName = parameterList.get(1);

				if (StringUtil.equals(
						parameterList.get(0), "Response.Status.BAD_REQUEST") &&
					parameterName.matches(
						"\\w*[eE]xception\\.getMessage\\(\\)")) {

					exceptionVariableName = parameterName.substring(
						0, parameterName.indexOf("."));
				}
			}
			else if (parameterList.size() == 4) {
				String parameterName = parameterList.get(2);

				if (StringUtil.equals(parameterList.get(0), "null") &&
					StringUtil.equals(
						parameterList.get(1), "Response.Status.BAD_REQUEST") &&
					parameterName.matches(
						"\\w*[eE]xception\\.getMessage\\(\\)")) {

					exceptionVariableName = parameterName.substring(
						0, parameterName.indexOf("."));
				}
			}

			if (exceptionVariableName == null) {
				continue;
			}

			String variableTypeName = getVariableTypeName(
				javaTermContent, javaTermContent, exceptionVariableName);

			if ((variableTypeName == null) ||
				!variableTypeName.endsWith("Exception") ||
				((parameterList.size() == 4) &&
				 !StringUtil.equals(
					 parameterList.get(3),
					 variableTypeName + ".class.getSimpleName()"))) {

				continue;
			}

			return StringUtil.replaceFirst(
				javaTermContent, methodCall,
				"new Problem(" + exceptionVariableName + ")", matcher.start());
		}

		return javaTermContent;
	}

	@Override
	protected String[] getCheckableJavaTermNames() {
		return new String[] {JAVA_METHOD};
	}

	private static final Pattern _newProblemPattern = Pattern.compile(
		"new Problem\\(");

}
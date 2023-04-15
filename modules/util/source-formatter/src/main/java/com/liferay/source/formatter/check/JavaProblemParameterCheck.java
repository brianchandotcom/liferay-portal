/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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
public class JavaProblemParameterCheck extends BaseJavaTermCheck {

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
				e -> e.replaceAll("\n\t*", StringPool.BLANK));

			String exceptionVariableName = null;

			if (parameterList.size() == 2) {
				String parameterName1 = parameterList.get(0);
				String parameterName2 = parameterList.get(1);

				if (StringUtil.equals(
						parameterName1, "Response.Status.BAD_REQUEST") &&
					parameterName2.matches(
						"\\w*[eE]xception\\.getMessage\\(\\)")) {

					exceptionVariableName = parameterName2;
				}
			}
			else if (parameterList.size() == 4) {
				String parameterName1 = parameterList.get(0);
				String parameterName2 = parameterList.get(1);
				String parameterName3 = parameterList.get(2);

				if (StringUtil.equals(parameterName1, "null") &&
					StringUtil.equals(
						parameterName2, "Response.Status.BAD_REQUEST") &&
					parameterName3.matches(
						"\\w*[eE]xception\\.getMessage\\(\\)")) {

					exceptionVariableName = parameterName3;
				}
			}

			if (exceptionVariableName == null) {
				continue;
			}

			exceptionVariableName = exceptionVariableName.substring(
				0, exceptionVariableName.indexOf("."));

			String variableTypeName = getVariableTypeName(
				javaTermContent, javaTermContent, exceptionVariableName);

			if (variableTypeName == null) {
				continue;
			}

			if (variableTypeName.endsWith("Exception")) {
				if (parameterList.size() == 4) {
					String parameter = parameterList.get(3);

					if (!parameter.matches(
							variableTypeName +
								"\\.class\\.getSimpleName\\(\\)")) {

						continue;
					}
				}

				return StringUtil.replaceFirst(
					javaTermContent, methodCall,
					"new Problem(" + exceptionVariableName + ")",
					matcher.start());
			}
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
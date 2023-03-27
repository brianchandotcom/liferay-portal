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

			List<String> parameters = JavaSourceUtil.getParameterList(
				methodCall);

			parameters.replaceAll(e -> e.replaceAll("\n\t*", StringPool.BLANK));

			String exceptionParameterName = null;

			if (parameters.size() == 2) {
				String firstParameter = parameters.get(0);
				String secondParameter = parameters.get(1);

				if (StringUtil.equals(
						firstParameter, "Response.Status.BAD_REQUEST") &&
					secondParameter.matches(
						"\\w*Exception\\.getMessage\\(\\)")) {

					exceptionParameterName = secondParameter;
				}
			}
			else if (parameters.size() == 4) {
				String firstParameter = parameters.get(0);
				String secondParameter = parameters.get(1);
				String thirdParameter = parameters.get(2);

				if (StringUtil.equals(firstParameter, "null") &&
					StringUtil.equals(
						secondParameter, "Response.Status.BAD_REQUEST") &&
					thirdParameter.matches(
						"\\w*Exception\\.getMessage\\(\\)")) {

					exceptionParameterName = thirdParameter;
				}
			}

			if (exceptionParameterName == null) {
				continue;
			}

			exceptionParameterName = exceptionParameterName.substring(
				0, exceptionParameterName.indexOf("."));

			String variableTypeName = getVariableTypeName(
				javaTermContent, javaTermContent, exceptionParameterName);

			if (variableTypeName == null) {
				continue;
			}

			if (variableTypeName.endsWith("Exception")) {
				if (parameters.size() == 4) {
					String parameter = parameters.get(3);

					if (!parameter.matches(
							variableTypeName +
								"\\.class\\.getSimpleName\\(\\)")) {

						continue;
					}
				}

				return StringUtil.replaceFirst(
					javaTermContent, methodCall,
					"new Problem(" + exceptionParameterName + ")",
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
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

package com.liferay.portal.tools.java.parser;

import com.liferay.petra.string.StringBundler;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Hugo Huijser
 */
public class JavaLambdaExpression extends BaseJavaExpression {

	public JavaLambdaExpression(
		List<JavaLambdaParameter> javaLambdaParameters) {

		_javaLambdaParameters = javaLambdaParameters;
	}

	public JavaLambdaExpression(String parameterName) {
		_javaLambdaParameters.add(new JavaLambdaParameter(parameterName));
	}

	public void setLambdaActionJavaExpression(
		JavaExpression lambdaActionJavaExpression) {

		_lambdaActionJavaExpression = lambdaActionJavaExpression;
	}

	@Override
	protected String getString(
		String indent, String prefix, String suffix, int maxLineLength,
		boolean forceLineBreak) {

		String originalIndent = indent;

		StringBundler sb = new StringBundler();

		sb.append(indent);

		indent = "\t" + indent;

		if (_javaLambdaParameters.size() == 1) {
			JavaLambdaParameter javaLambdaParameter = _javaLambdaParameters.get(
				0);

			if (javaLambdaParameter.hasJavaType()) {
				indent = append(
					sb, javaLambdaParameter, indent, prefix + "(", ") -> ",
					maxLineLength);
			}
			else {
				indent = append(
					sb, javaLambdaParameter, indent, prefix, " -> ",
					maxLineLength);
			}
		}
		else {
			indent = append(
				sb, _javaLambdaParameters, indent, prefix + "(", ") -> ",
				maxLineLength);
		}

		if (_lambdaActionJavaExpression == null) {
			sb.append("{\n");
			sb.append(NESTED_CODE_BLOCK);
			sb.append("\n");
			sb.append(originalIndent);
			sb.append("}");
			sb.append(suffix);
		}
		else {
			appendAssignValue(
				sb, _lambdaActionJavaExpression, trimTrailingSpaces(indent),
				suffix, maxLineLength, forceLineBreak);
		}

		return sb.toString();
	}

	protected static final String NESTED_CODE_BLOCK =
		"${JAVA_LAMBDA_EXPRESSION_NESTED_CODE_BLOCK}";

	private List<JavaLambdaParameter> _javaLambdaParameters = new ArrayList<>();
	private JavaExpression _lambdaActionJavaExpression;

}
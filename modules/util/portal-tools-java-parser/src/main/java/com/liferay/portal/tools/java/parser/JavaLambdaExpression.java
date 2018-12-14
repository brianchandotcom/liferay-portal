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

import com.liferay.portal.kernel.util.StringBundler;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Hugo Huijser
 */
public class JavaLambdaExpression extends JavaExpression {

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

		return "TODO";
	}

	private List<JavaLambdaParameter> _javaLambdaParameters = new ArrayList<>();
	private JavaExpression _lambdaActionJavaExpression;

}
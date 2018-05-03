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

package com.liferay.dynamic.data.mapping.expression.internal;

import com.liferay.dynamic.data.mapping.expression.internal.parser.DDMExpressionBaseListener;
import com.liferay.dynamic.data.mapping.expression.internal.parser.DDMExpressionParser;

import java.util.HashSet;
import java.util.Set;

import org.antlr.v4.runtime.misc.NotNull;

/**
 * @author Marcellus Tavares
 */
public class DDMExpressionListener extends DDMExpressionBaseListener {

	@Override
	public void enterFunctionCallExpression(
		@NotNull DDMExpressionParser.FunctionCallExpressionContext context) {

		_functionNames.add(context.functionName.getText());
	}

	@Override
	public void enterLogicalVariable(
		@NotNull DDMExpressionParser.LogicalVariableContext context) {

		_variableNames.add(context.getText());
	}

	@Override
	public void enterNumericVariable(
		@NotNull DDMExpressionParser.NumericVariableContext context) {

		_variableNames.add(context.getText());
	}

	public Set<String> getFunctionNames() {
		return _functionNames;
	}

	public Set<String> getVariableNames() {
		return _variableNames;
	}

	private final Set<String> _functionNames = new HashSet<>();
	private final Set<String> _variableNames = new HashSet<>();

}
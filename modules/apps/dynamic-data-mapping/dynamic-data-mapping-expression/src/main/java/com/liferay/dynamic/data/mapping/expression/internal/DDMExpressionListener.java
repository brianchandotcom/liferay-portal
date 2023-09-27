/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.expression.internal;

import com.liferay.dynamic.data.mapping.expression.internal.parser.generated.DDMExpressionBaseListener;
import com.liferay.dynamic.data.mapping.expression.internal.parser.generated.DDMExpressionParser;

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
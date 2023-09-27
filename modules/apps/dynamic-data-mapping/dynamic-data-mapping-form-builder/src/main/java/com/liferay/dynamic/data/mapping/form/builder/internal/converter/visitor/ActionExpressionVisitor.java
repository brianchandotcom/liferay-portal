/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.builder.internal.converter.visitor;

import com.liferay.dynamic.data.mapping.expression.model.Expression;
import com.liferay.dynamic.data.mapping.expression.model.ExpressionVisitor;
import com.liferay.dynamic.data.mapping.expression.model.FloatingPointLiteral;
import com.liferay.dynamic.data.mapping.expression.model.FunctionCallExpression;
import com.liferay.dynamic.data.mapping.expression.model.IntegerLiteral;
import com.liferay.dynamic.data.mapping.expression.model.StringLiteral;
import com.liferay.dynamic.data.mapping.expression.model.Term;
import com.liferay.dynamic.data.mapping.form.builder.internal.converter.DDMFormRuleActionFactory;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.List;
import java.util.Map;

/**
 * @author Rafael Praxedes
 */
public class ActionExpressionVisitor extends ExpressionVisitor<Object> {

	public <T> T doVisit(Expression expression) {
		return (T)expression.accept(this);
	}

	@Override
	public Object visit(FloatingPointLiteral floatingPointLiteral) {
		return floatingPointLiteral.getValue();
	}

	@Override
	public Object visit(FunctionCallExpression functionCallExpression) {
		String action = _functionToActionMap.get(
			functionCallExpression.getFunctionName());

		List<Expression> parameters =
			functionCallExpression.getParameterExpressions();

		return DDMFormRuleActionFactory.create(action, parameters, this);
	}

	@Override
	public Object visit(IntegerLiteral integerLiteral) {
		return integerLiteral.getValue();
	}

	@Override
	public Object visit(StringLiteral stringLiteral) {
		return stringLiteral.getValue();
	}

	@Override
	public Object visit(Term term) {
		return term.getValue();
	}

	private static final Map<String, String> _functionToActionMap =
		HashMapBuilder.put(
			"calculate", "calculate"
		).put(
			"call", "auto-fill"
		).put(
			"jumpPage", "jump-to-page"
		).put(
			"setEnabled", "enable"
		).put(
			"setInvalid", "invalidate"
		).put(
			"setRequired", "require"
		).put(
			"setVisible", "show"
		).build();

}
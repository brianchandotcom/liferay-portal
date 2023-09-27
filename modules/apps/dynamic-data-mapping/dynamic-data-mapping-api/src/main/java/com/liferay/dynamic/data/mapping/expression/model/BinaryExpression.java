/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.expression.model;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Leonardo Barros
 */
@ProviderType
public abstract class BinaryExpression extends Expression {

	public BinaryExpression(
		String operator, Expression leftOperandExpression,
		Expression rightOperandExpression) {

		_operator = operator;
		_leftOperandExpression = leftOperandExpression;
		_rightOperandExpression = rightOperandExpression;
	}

	public Expression getLeftOperandExpression() {
		return _leftOperandExpression;
	}

	public String getOperator() {
		return _operator;
	}

	public Expression getRightOperandExpression() {
		return _rightOperandExpression;
	}

	@Override
	public String toString() {
		return String.format(
			"%s %s %s", _leftOperandExpression, _operator,
			_rightOperandExpression);
	}

	private final Expression _leftOperandExpression;
	private final String _operator;
	private final Expression _rightOperandExpression;

}
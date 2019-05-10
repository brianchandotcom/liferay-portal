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
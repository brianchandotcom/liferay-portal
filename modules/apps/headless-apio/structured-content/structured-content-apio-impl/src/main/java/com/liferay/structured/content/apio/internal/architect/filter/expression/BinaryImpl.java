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

package com.liferay.structured.content.apio.internal.architect.filter.expression;

import com.liferay.petra.string.StringBundler;
import com.liferay.structured.content.apio.architect.filter.expression.Binary;
import com.liferay.structured.content.apio.architect.filter.expression.Expression;
import com.liferay.structured.content.apio.architect.filter.expression.ExpressionVisitException;
import com.liferay.structured.content.apio.architect.filter.expression.ExpressionVisitor;

/**
 * @author Cristina González
 */
public class BinaryImpl implements Binary {

	public BinaryImpl(Expression left, Operation operation, Expression right) {
		_left = left;
		_operation = operation;
		_right = right;
	}

	@Override
	public <T> T accept(ExpressionVisitor<T> visitor)
		throws ExpressionVisitException {

		return visitor.visitBinaryOperator(
			_operation, _left.accept(visitor), _right.accept(visitor));
	}

	@Override
	public Expression getLeftOperand() {
		return _left;
	}

	@Override
	public Operation getOperation() {
		return _operation;
	}

	@Override
	public Expression getRightOperand() {
		return _right;
	}

	public String toString() {
		return StringBundler.concat(
			"{", _left, " ", _operation.name(), " ", _right, '}');
	}

	private final Expression _left;
	private final Operation _operation;
	private final Expression _right;

}
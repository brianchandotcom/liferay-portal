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

package com.liferay.petra.sql.dsl.expressions;

import com.liferay.petra.sql.dsl.ast.ASTNodeListener;
import com.liferay.petra.sql.dsl.base.BaseASTNode;
import com.liferay.petra.sql.dsl.operands.Operand;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * @author Preston Crary
 */
public class Predicate extends BaseASTNode implements Expression<Boolean> {

	public Predicate(
		Expression<?> leftExpression, Operand operand,
		Expression<?> rightExpression) {

		this(leftExpression, operand, rightExpression, false);
	}

	public Predicate and(Expression<Boolean> expression) {
		return new Predicate(this, Operand.AND, expression);
	}

	public Expression<?> getLeftExpression() {
		return _leftExpression;
	}

	public Operand getOperand() {
		return _operand;
	}

	public Expression<?> getRightExpression() {
		return _rightExpression;
	}

	public boolean isWrapParentheses() {
		return _wrapParentheses;
	}

	public Predicate or(Expression<Boolean> expression) {
		return new Predicate(this, Operand.OR, expression);
	}

	public Predicate withParentheses() {
		if (_wrapParentheses) {
			return this;
		}

		return new Predicate(_leftExpression, _operand, _rightExpression, true);
	}

	@Override
	protected void doToSQL(
		Consumer<String> consumer, ASTNodeListener astNodeListener) {

		if (_wrapParentheses) {
			consumer.accept("(");
		}

		_leftExpression.toSQL(consumer, astNodeListener);

		consumer.accept(" ");
		consumer.accept(String.valueOf(_operand));
		consumer.accept(" ");

		_rightExpression.toSQL(consumer, astNodeListener);

		if (_wrapParentheses) {
			consumer.accept(")");
		}
	}

	private Predicate(
		Expression<?> leftExpression, Operand operand,
		Expression<?> rightExpression, boolean wrapParentheses) {

		_leftExpression = Objects.requireNonNull(leftExpression);
		_operand = Objects.requireNonNull(operand);
		_rightExpression = Objects.requireNonNull(rightExpression);
		_wrapParentheses = wrapParentheses;
	}

	private final Expression<?> _leftExpression;
	private final Operand _operand;
	private final Expression<?> _rightExpression;
	private final boolean _wrapParentheses;

}
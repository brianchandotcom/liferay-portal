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

package com.liferay.petra.sql.dsl.spi.expression;

import com.liferay.petra.sql.dsl.ast.ASTNode;
import com.liferay.petra.sql.dsl.ast.ASTNodeListener;
import com.liferay.petra.sql.dsl.expression.Expression;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.petra.sql.dsl.spi.ast.BaseASTNode;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * @author Preston Crary
 */
public class DefaultPredicate
	extends BaseASTNode implements DefaultExpression<Boolean>, Predicate {

	public DefaultPredicate(
		Expression<?> leftExpression, Operand operand,
		Expression<?> rightExpression) {

		this(leftExpression, operand, rightExpression, false);
	}

	@Override
	public Predicate and(Expression<Boolean> expression) {
		if (expression == null) {
			return this;
		}

		return new DefaultPredicate(this, Operand.AND, expression);
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

	@Override
	public Predicate or(Expression<Boolean> expression) {
		if (expression == null) {
			return this;
		}

		return new DefaultPredicate(this, Operand.OR, expression);
	}

	@Override
	public void toSQL(
		Consumer<String> consumer, ASTNodeListener astNodeListener) {

		doToSQL(consumer, astNodeListener);
	}

	@Override
	public Predicate withParentheses() {
		if (_wrapParentheses) {
			return this;
		}

		return new DefaultPredicate(
			_leftExpression, _operand, _rightExpression, true);
	}

	@Override
	protected void doToSQL(
		Consumer<String> consumer, ASTNodeListener astNodeListener) {

		Deque<ASTNode> deque = new LinkedList<>();

		deque.push(this);

		ASTNode astNode = null;

		while ((astNode = deque.poll()) != null) {
			if (astNode instanceof DefaultPredicate) {
				if (astNodeListener != null) {
					astNodeListener.process(astNode);
				}

				DefaultPredicate defaultPredicate = (DefaultPredicate)astNode;

				if (defaultPredicate.isWrapParentheses()) {
					deque.push(new ASTNodeAdapter(")"));
				}

				deque.push(defaultPredicate.getRightExpression());

				Operand operand = defaultPredicate.getOperand();

				deque.push(new ASTNodeAdapter(operand.getStringWithSpaces()));

				deque.push(defaultPredicate.getLeftExpression());

				if (defaultPredicate.isWrapParentheses()) {
					deque.push(new ASTNodeAdapter("("));
				}
			}
			else {
				astNode.toSQL(consumer, astNodeListener);
			}
		}
	}

	private DefaultPredicate(
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

	private static class ASTNodeAdapter implements ASTNode {

		@Override
		public void toSQL(
			Consumer<String> consumer, ASTNodeListener astNodeListener) {

			consumer.accept(_value);
		}

		private ASTNodeAdapter(String value) {
			_value = value;
		}

		private final String _value;

	}

}
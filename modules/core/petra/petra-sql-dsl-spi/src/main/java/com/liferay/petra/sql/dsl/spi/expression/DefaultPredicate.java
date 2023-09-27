/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

		this(leftExpression, operand, rightExpression, false, false);
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

	public boolean isNot() {
		return _not;
	}

	public boolean isWrapParentheses() {
		return _wrapParentheses;
	}

	@Override
	public Predicate not() {
		if (_not) {
			return this;
		}

		return new DefaultPredicate(
			_leftExpression, _operand, _rightExpression, true, true);
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
			_leftExpression, _operand, _rightExpression, true, _not);
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

				if (defaultPredicate.isNot()) {
					deque.push(new ASTNodeAdapter("not "));
				}
			}
			else {
				astNode.toSQL(consumer, astNodeListener);
			}
		}
	}

	private DefaultPredicate(
		Expression<?> leftExpression, Operand operand,
		Expression<?> rightExpression, boolean wrapParentheses, boolean not) {

		_leftExpression = Objects.requireNonNull(leftExpression);
		_operand = Objects.requireNonNull(operand);
		_rightExpression = Objects.requireNonNull(rightExpression);
		_wrapParentheses = wrapParentheses;
		_not = not;
	}

	private final Expression<?> _leftExpression;
	private final boolean _not;
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
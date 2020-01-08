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

package com.liferay.portal.kernel.dao.model.dsl.expressions;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.model.dsl.ast.ASTNodeListener;
import com.liferay.portal.kernel.dao.model.dsl.base.BaseASTNode;
import com.liferay.portal.kernel.dao.model.dsl.operands.Operand;

import java.util.Objects;

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

	public Predicate andParentheses(Predicate predicate) {
		return new Predicate(
			this, Operand.AND,
			new Predicate(
				predicate._leftExpression, predicate._operand,
				predicate._rightExpression, true));
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

	public Predicate orParentheses(Predicate predicate) {
		return new Predicate(
			this, Operand.OR,
			new Predicate(
				predicate._leftExpression, predicate._operand,
				predicate._rightExpression, true));
	}

	@Override
	protected void doToSQL(StringBundler sb, ASTNodeListener astNodeListener) {
		if (_wrapParentheses) {
			sb.append("(");
		}

		_leftExpression.toSQL(sb, astNodeListener);

		sb.append(" ");
		sb.append(_operand);
		sb.append(" ");

		_rightExpression.toSQL(sb, astNodeListener);

		if (_wrapParentheses) {
			sb.append(")");
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
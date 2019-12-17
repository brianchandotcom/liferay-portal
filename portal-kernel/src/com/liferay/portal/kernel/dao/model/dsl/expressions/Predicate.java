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

import com.liferay.portal.kernel.dao.model.dsl.ast.ASTNodeVisitor;
import com.liferay.portal.kernel.dao.model.dsl.ast.impl.DefaultASTNodeVisitor;
import com.liferay.portal.kernel.dao.model.dsl.clause.PredicateClause;
import com.liferay.portal.kernel.dao.model.dsl.clause.WhereClause;
import com.liferay.portal.kernel.dao.model.dsl.operands.Operand;

import java.util.Objects;

/**
 * @author Preston Crary
 */
public class Predicate implements Expression<Boolean>, WhereClause {

	public Predicate(
		Expression<?> leftExpression, Operand operand,
		PredicateClause predicateClause) {

		this(leftExpression, operand, predicateClause, false);
	}

	@Override
	public void accept(ASTNodeVisitor astNodeVisitor) {
		astNodeVisitor.visit(this);
	}

	public Predicate and(Expression<Boolean> expression) {
		return new Predicate(this, Operand.AND, expression);
	}

	public Predicate andParentheses(Predicate predicate) {
		return new Predicate(
			this, Operand.AND,
			new Predicate(
				predicate._leftExpression, predicate._operand,
				predicate._predicateClause, true));
	}

	public Expression<?> getLeftExpression() {
		return _leftExpression;
	}

	public Operand getOperand() {
		return _operand;
	}

	public PredicateClause getPredicateClause() {
		return _predicateClause;
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
				predicate._predicateClause, true));
	}

	@Override
	public String toString() {
		ASTNodeVisitor astNodeVisitor = new DefaultASTNodeVisitor();

		astNodeVisitor.visit(this);

		return astNodeVisitor.toString();
	}

	private Predicate(
		Expression<?> leftExpression, Operand operand,
		PredicateClause predicateClause, boolean wrapParentheses) {

		_leftExpression = Objects.requireNonNull(leftExpression);
		_operand = Objects.requireNonNull(operand);
		_predicateClause = Objects.requireNonNull(predicateClause);
		_wrapParentheses = wrapParentheses;
	}

	private final Expression<?> _leftExpression;
	private final Operand _operand;
	private final PredicateClause _predicateClause;
	private final boolean _wrapParentheses;

}
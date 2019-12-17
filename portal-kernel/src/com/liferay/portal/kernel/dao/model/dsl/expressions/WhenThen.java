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
import com.liferay.portal.kernel.dao.model.dsl.base.BaseASTNode;

import java.util.Objects;

/**
 * @author Preston Crary
 */
public class WhenThen<T>
	extends BaseASTNode implements ElseEndStep<T>, WhenThenStep<T> {

	public WhenThen(
		WhenThenStep<T> whenThenStep, Predicate predicate,
		Expression<T> thenExpression) {

		super(whenThenStep);

		_predicate = Objects.requireNonNull(predicate);
		_thenExpression = Objects.requireNonNull(thenExpression);
	}

	public Predicate getPredicate() {
		return _predicate;
	}

	public Expression<T> getThenExpression() {
		return _thenExpression;
	}

	@Override
	protected void doAccept(ASTNodeVisitor astNodeVisitor) {
		astNodeVisitor.visit(this);
	}

	private final Predicate _predicate;
	private final Expression<T> _thenExpression;

}
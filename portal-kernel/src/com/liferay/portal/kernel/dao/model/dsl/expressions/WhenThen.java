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

import com.liferay.portal.kernel.dao.model.dsl.ast.ASTNodeListener;
import com.liferay.portal.kernel.dao.model.dsl.base.BaseASTNode;

import java.util.Objects;
import java.util.function.Consumer;

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

	@Override
	public void doToSQL(
		Consumer<String> consumer, ASTNodeListener astNodeListener) {

		consumer.accept("when ");

		_predicate.toSQL(consumer, astNodeListener);

		consumer.accept(" then ");

		_thenExpression.toSQL(consumer, astNodeListener);
	}

	public Predicate getPredicate() {
		return _predicate;
	}

	public Expression<T> getThenExpression() {
		return _thenExpression;
	}

	private final Predicate _predicate;
	private final Expression<T> _thenExpression;

}
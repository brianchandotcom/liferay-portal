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

package com.liferay.portal.kernel.dao.model.dsl.query;

import com.liferay.portal.kernel.dao.model.dsl.ast.ASTNodeVisitor;
import com.liferay.portal.kernel.dao.model.dsl.ast.impl.DefaultASTNodeVisitor;
import com.liferay.portal.kernel.dao.model.dsl.expressions.Alias;
import com.liferay.portal.kernel.dao.model.dsl.expressions.Expression;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;

import java.util.Objects;

/**
 * @author Preston Crary
 */
public class AggregateExpression<T> implements Expression<T> {

	public static final Alias<Long> COUNT_STAR_COUNT_VALUE = new Alias<>(
		new AggregateExpression<>(false, null, "count"),
		BasePersistenceImpl.COUNT_COLUMN_NAME);

	public AggregateExpression(
		boolean distinct, Expression<?> expression, String name) {

		_distinct = distinct;
		_expression = expression;
		_name = Objects.requireNonNull(name);
	}

	@Override
	public void accept(ASTNodeVisitor astNodeVisitor) {
		astNodeVisitor.visit(this);
	}

	public Expression<?> getExpression() {
		return _expression;
	}

	public String getName() {
		return _name;
	}

	public boolean isDistinct() {
		return _distinct;
	}

	@Override
	public String toString() {
		ASTNodeVisitor astNodeVisitor = new DefaultASTNodeVisitor();

		astNodeVisitor.visit(this);

		return astNodeVisitor.toString();
	}

	private final boolean _distinct;
	private final Expression<?> _expression;
	private final String _name;

}
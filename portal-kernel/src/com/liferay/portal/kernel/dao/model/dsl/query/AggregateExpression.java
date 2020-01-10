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

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.model.dsl.ast.ASTNodeListener;
import com.liferay.portal.kernel.dao.model.dsl.base.BaseASTNode;
import com.liferay.portal.kernel.dao.model.dsl.expressions.Alias;
import com.liferay.portal.kernel.dao.model.dsl.expressions.Expression;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;

import java.util.Objects;

/**
 * @author Preston Crary
 */
public class AggregateExpression<T>
	extends BaseASTNode implements Expression<T> {

	public static final Alias<Long> COUNT_STAR_COUNT_VALUE = new Alias<>(
		new AggregateExpression<>(false, null, "count"),
		BasePersistenceImpl.COUNT_COLUMN_NAME);

	public AggregateExpression(
		boolean distinct, Expression<?> expression, String name) {

		_distinct = distinct;
		_expression = expression;
		_name = Objects.requireNonNull(name);
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
	protected void doToSQL(StringBundler sb, ASTNodeListener astNodeListener) {
		sb.append(_name);

		sb.append("(");

		if (_distinct) {
			sb.append("distinct ");
		}

		if (_expression == null) {
			sb.append("*");
		}
		else {
			_expression.toSQL(sb, astNodeListener);
		}

		sb.append(")");
	}

	private final boolean _distinct;
	private final Expression<?> _expression;
	private final String _name;

}
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

package com.liferay.petra.sql.dsl.spi.query.sort;

import com.liferay.petra.sql.dsl.ast.ASTNodeListener;
import com.liferay.petra.sql.dsl.expression.Expression;
import com.liferay.petra.sql.dsl.query.sort.OrderByExpression;
import com.liferay.petra.sql.dsl.spi.ast.BaseASTNode;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * @author Preston Crary
 */
public class DefaultOrderByExpression
	extends BaseASTNode implements OrderByExpression {

	public DefaultOrderByExpression(
		Expression<?> expression, boolean ascending) {

		_expression = Objects.requireNonNull(expression);
		_ascending = ascending;
	}

	@Override
	public Expression<?> getExpression() {
		return _expression;
	}

	@Override
	public boolean isAscending() {
		return _ascending;
	}

	@Override
	protected void doToSQL(
		Consumer<String> consumer, ASTNodeListener astNodeListener) {

		_expression.toSQL(consumer, astNodeListener);

		if (_ascending) {
			consumer.accept(" asc");
		}
		else {
			consumer.accept(" desc");
		}
	}

	private final boolean _ascending;
	private final Expression<?> _expression;

}
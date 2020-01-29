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

package com.liferay.petra.sql.dsl.query.impl;

import com.liferay.petra.sql.dsl.ast.ASTNodeListener;
import com.liferay.petra.sql.dsl.ast.impl.BaseASTNode;
import com.liferay.petra.sql.dsl.expression.Alias;
import com.liferay.petra.sql.dsl.expression.Expression;
import com.liferay.petra.sql.dsl.query.FromStep;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * @author Preston Crary
 */
public class Select extends BaseASTNode implements FromStep {

	public Select(boolean distinct, Expression<?>... expressions) {
		_distinct = distinct;
		_expressions = Objects.requireNonNull(expressions);
	}

	public Expression<?>[] getExpressions() {
		return _expressions;
	}

	public boolean isDistinct() {
		return _distinct;
	}

	@Override
	protected void doToSQL(
		Consumer<String> consumer, ASTNodeListener astNodeListener) {

		consumer.accept("select ");

		if (_distinct) {
			consumer.accept("distinct ");
		}

		if (_expressions.length > 0) {
			for (int i = 0; i < _expressions.length; i++) {
				Expression<?> expression = _expressions[i];

				if (expression instanceof Alias) {
					Alias<?> alias = (Alias<?>)expression;

					Expression<?> unwrappedExpression = alias.getExpression();

					unwrappedExpression.toSQL(consumer, astNodeListener);

					consumer.accept(" ");
				}

				expression.toSQL(consumer, astNodeListener);

				if (i < (_expressions.length - 1)) {
					consumer.accept(", ");
				}
			}
		}
		else {
			consumer.accept("*");
		}
	}

	private final boolean _distinct;
	private final Expression<?>[] _expressions;

}
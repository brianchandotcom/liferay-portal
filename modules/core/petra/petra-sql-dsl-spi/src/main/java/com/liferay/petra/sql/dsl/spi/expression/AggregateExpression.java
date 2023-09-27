/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.sql.dsl.spi.expression;

import com.liferay.petra.sql.dsl.ast.ASTNodeListener;
import com.liferay.petra.sql.dsl.expression.Expression;
import com.liferay.petra.sql.dsl.spi.ast.BaseASTNode;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * @author Preston Crary
 */
public class AggregateExpression<T>
	extends BaseASTNode implements DefaultExpression<T> {

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
	protected void doToSQL(
		Consumer<String> consumer, ASTNodeListener astNodeListener) {

		consumer.accept(_name);
		consumer.accept("(");

		if (_distinct) {
			consumer.accept("distinct ");
		}

		if (_expression == null) {
			consumer.accept("*");
		}
		else {
			_expression.toSQL(consumer, astNodeListener);
		}

		consumer.accept(")");
	}

	private final boolean _distinct;
	private final Expression<?> _expression;
	private final String _name;

}
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.sql.dsl.spi.query;

import com.liferay.petra.sql.dsl.ast.ASTNodeListener;
import com.liferay.petra.sql.dsl.expression.Alias;
import com.liferay.petra.sql.dsl.expression.Expression;
import com.liferay.petra.sql.dsl.spi.ast.BaseASTNode;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * @author Preston Crary
 */
public class Select extends BaseASTNode implements DefaultFromStep {

	public Select(
		boolean distinct, Collection<? extends Expression<?>> expressions) {

		_distinct = distinct;
		_expressions = Objects.requireNonNull(expressions);
	}

	public Collection<? extends Expression<?>> getExpressions() {
		return _expressions;
	}

	public boolean isDistinct() {
		return _distinct;
	}

	@Override
	protected void doToSQL(
		Consumer<String> consumer, ASTNodeListener astNodeListener) {

		if (_distinct) {
			consumer.accept("select distinct ");
		}
		else {
			consumer.accept("select ");
		}

		if (_expressions.isEmpty()) {
			consumer.accept("*");
		}
		else {
			Iterator<? extends Expression<?>> iterator =
				_expressions.iterator();

			while (iterator.hasNext()) {
				Expression<?> expression = iterator.next();

				if (expression instanceof Alias) {
					Alias<?> alias = (Alias<?>)expression;

					Expression<?> unwrappedExpression = alias.getExpression();

					unwrappedExpression.toSQL(consumer, astNodeListener);

					consumer.accept(" ");
				}

				expression.toSQL(consumer, astNodeListener);

				if (iterator.hasNext()) {
					consumer.accept(", ");
				}
			}
		}
	}

	private final boolean _distinct;
	private final Collection<? extends Expression<?>> _expressions;

}
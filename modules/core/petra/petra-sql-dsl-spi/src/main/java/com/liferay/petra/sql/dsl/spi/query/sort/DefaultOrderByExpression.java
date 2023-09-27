/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
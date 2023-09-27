/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.sql.dsl.spi.query;

import com.liferay.petra.sql.dsl.ast.ASTNodeListener;
import com.liferay.petra.sql.dsl.expression.Expression;
import com.liferay.petra.sql.dsl.query.GroupByStep;
import com.liferay.petra.sql.dsl.spi.ast.BaseASTNode;

import java.util.function.Consumer;

/**
 * @author Preston Crary
 */
public class GroupBy extends BaseASTNode implements DefaultHavingStep {

	public GroupBy(GroupByStep groupByStep, Expression<?>... expressions) {
		super(groupByStep);

		if (expressions.length == 0) {
			throw new IllegalArgumentException("Expressions is empty");
		}

		_expressions = expressions;
	}

	public Expression<?>[] getExpressions() {
		return _expressions;
	}

	@Override
	protected void doToSQL(
		Consumer<String> consumer, ASTNodeListener astNodeListener) {

		consumer.accept("group by ");

		_expressions[0].toSQL(consumer, astNodeListener);

		for (int i = 1; i < _expressions.length; i++) {
			consumer.accept(", ");

			_expressions[i].toSQL(consumer, astNodeListener);
		}
	}

	private final Expression<?>[] _expressions;

}
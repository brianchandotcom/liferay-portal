/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.sql.dsl.spi.expression.step;

import com.liferay.petra.sql.dsl.ast.ASTNodeListener;
import com.liferay.petra.sql.dsl.expression.Expression;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.petra.sql.dsl.spi.ast.BaseASTNode;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * @author Preston Crary
 */
public class CaseWhenThen<T>
	extends BaseASTNode implements DefaultWhenThenStep<T> {

	public CaseWhenThen(Predicate predicate, Expression<T> thenExpression) {
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
	protected void doToSQL(
		Consumer<String> consumer, ASTNodeListener astNodeListener) {

		consumer.accept("case when ");

		_predicate.toSQL(consumer, astNodeListener);

		consumer.accept(" then ");

		_thenExpression.toSQL(consumer, astNodeListener);
	}

	private final Predicate _predicate;
	private final Expression<T> _thenExpression;

}
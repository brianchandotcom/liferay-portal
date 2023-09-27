/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.sql.dsl.spi.expression.step;

import com.liferay.petra.sql.dsl.ast.ASTNodeListener;
import com.liferay.petra.sql.dsl.expression.Expression;
import com.liferay.petra.sql.dsl.expression.step.ElseEndStep;
import com.liferay.petra.sql.dsl.spi.ast.BaseASTNode;
import com.liferay.petra.sql.dsl.spi.expression.DefaultExpression;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * @author Preston Crary
 */
public class ElseEnd<T> extends BaseASTNode implements DefaultExpression<T> {

	public ElseEnd(ElseEndStep<T> elseEndStep, Expression<T> elseExpression) {
		super(elseEndStep);

		_elseExpression = Objects.requireNonNull(elseExpression);
	}

	public Expression<T> getElseExpression() {
		return _elseExpression;
	}

	@Override
	protected void doToSQL(
		Consumer<String> consumer, ASTNodeListener astNodeListener) {

		consumer.accept("else ");

		_elseExpression.toSQL(consumer, astNodeListener);

		consumer.accept(" end");
	}

	private final Expression<T> _elseExpression;

}
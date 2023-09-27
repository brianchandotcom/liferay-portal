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
public class DSLFunction<T>
	extends BaseASTNode implements DefaultExpression<T> {

	public DSLFunction(
		DSLFunctionType dslFunctionType, Expression<?>... expressions) {

		_dslFunctionType = Objects.requireNonNull(dslFunctionType);

		if (expressions.length == 0) {
			throw new IllegalArgumentException("Expressions is empty");
		}

		_expressions = expressions;
	}

	public DSLFunctionType getDslFunctionType() {
		return _dslFunctionType;
	}

	public Expression<?>[] getExpressions() {
		return _expressions;
	}

	@Override
	protected void doToSQL(
		Consumer<String> consumer, ASTNodeListener astNodeListener) {

		consumer.accept(_dslFunctionType.getPrefix());

		_expressions[0].toSQL(consumer, astNodeListener);

		for (int i = 1; i < _expressions.length; i++) {
			consumer.accept(_dslFunctionType.getDelimiter());

			_expressions[i].toSQL(consumer, astNodeListener);
		}

		consumer.accept(_dslFunctionType.getPostfix());
	}

	private final DSLFunctionType _dslFunctionType;
	private final Expression<?>[] _expressions;

}
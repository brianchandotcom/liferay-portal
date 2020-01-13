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

package com.liferay.petra.sql.dsl.functions;

import com.liferay.petra.sql.dsl.ast.ASTNodeListener;
import com.liferay.petra.sql.dsl.base.BaseASTNode;
import com.liferay.petra.sql.dsl.expressions.Expression;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * @author Preston Crary
 */
public class Function<T> extends BaseASTNode implements Expression<T> {

	public Function(FunctionType functionType, Expression<?>... expressions) {
		_functionType = Objects.requireNonNull(functionType);

		if (expressions.length == 0) {
			throw new IllegalArgumentException();
		}

		_expressions = expressions;
	}

	public Expression<?>[] getExpressions() {
		return _expressions;
	}

	public FunctionType getFunctionType() {
		return _functionType;
	}

	@Override
	protected void doToSQL(
		Consumer<String> consumer, ASTNodeListener astNodeListener) {

		consumer.accept(_functionType.getPrefix());

		for (int i = 0; i < _expressions.length; i++) {
			_expressions[i].toSQL(consumer, astNodeListener);

			if (i < (_expressions.length - 1)) {
				consumer.accept(_functionType.getDelimiter());
			}
		}

		consumer.accept(_functionType.getPostfix());
	}

	private final Expression<?>[] _expressions;
	private final FunctionType _functionType;

}
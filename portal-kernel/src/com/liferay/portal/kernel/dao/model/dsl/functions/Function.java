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

package com.liferay.portal.kernel.dao.model.dsl.functions;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.model.dsl.ast.ASTNodeListener;
import com.liferay.portal.kernel.dao.model.dsl.base.BaseASTNode;
import com.liferay.portal.kernel.dao.model.dsl.expressions.Expression;

import java.util.Objects;

/**
 * @author Preston Crary
 */
public class Function<T> extends BaseASTNode implements Expression<T> {

	public Function(FunctionType functionType, Expression<?>[] expressions) {
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
	protected void doToSQL(StringBundler sb, ASTNodeListener astNodeListener) {
		sb.append(_functionType.getPrefix());

		for (Expression<?> expression : _expressions) {
			expression.toSQL(sb, astNodeListener);

			sb.append(_functionType.getDelimiter());
		}

		sb.setStringAt(_functionType.getPostfix(), sb.index() - 1);
	}

	private final Expression<?>[] _expressions;
	private final FunctionType _functionType;

}
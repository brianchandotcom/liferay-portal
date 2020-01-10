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

package com.liferay.portal.kernel.dao.model.dsl.query;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.model.dsl.ast.ASTNodeListener;
import com.liferay.portal.kernel.dao.model.dsl.base.BaseASTNode;
import com.liferay.portal.kernel.dao.model.dsl.expressions.Expression;

import java.util.Objects;

/**
 * @author Preston Crary
 */
public class Select extends BaseASTNode implements FromStep, Query {

	public Select(boolean distinct, Expression<?>[] expressions) {
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
	protected void doToSQL(StringBundler sb, ASTNodeListener astNodeListener) {
		sb.append("select ");

		if (_distinct) {
			sb.append("distinct ");
		}

		if (_expressions.length > 0) {
			for (Expression<?> expression : _expressions) {
				Expression<?> unwrappedExpression = expression.unwrapAlias();

				unwrappedExpression.toSQL(sb, astNodeListener);

				String alias = expression.getAlias();

				if (alias != null) {
					sb.append(" ");
					sb.append(alias);
				}

				sb.append(", ");
			}

			sb.setIndex(sb.index() - 1);
		}
		else {
			sb.append("*");
		}
	}

	private final boolean _distinct;
	private final Expression<?>[] _expressions;

}
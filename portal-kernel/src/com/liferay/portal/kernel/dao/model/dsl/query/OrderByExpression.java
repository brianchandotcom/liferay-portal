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
import com.liferay.portal.kernel.dao.model.dsl.ast.impl.DefaultASTNodeVisitor;
import com.liferay.portal.kernel.dao.model.dsl.expressions.Expression;

import java.util.Objects;

/**
 * @author Preston Crary
 */
public class OrderByExpression {

	public OrderByExpression(Expression<?> expression, boolean ascending) {
		_expression = Objects.requireNonNull(expression);
		_ascending = ascending;
	}

	public Expression<?> getExpression() {
		return _expression;
	}

	public boolean isAscending() {
		return _ascending;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler();

		_expression.accept(new DefaultASTNodeVisitor(sb));

		if (_ascending) {
			sb.append(" asc");
		}
		else {
			sb.append(" desc");
		}

		return sb.toString();
	}

	private final boolean _ascending;
	private final Expression<?> _expression;

}
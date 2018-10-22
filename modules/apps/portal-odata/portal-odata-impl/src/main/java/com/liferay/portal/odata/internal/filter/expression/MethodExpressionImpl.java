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

package com.liferay.portal.odata.internal.filter.expression;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.odata.filter.expression.Expression;
import com.liferay.portal.odata.filter.expression.ExpressionVisitException;
import com.liferay.portal.odata.filter.expression.ExpressionVisitor;
import com.liferay.portal.odata.filter.expression.MethodExpression;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Cristina González
 */
public class MethodExpressionImpl implements MethodExpression {

	public MethodExpressionImpl(List<Expression> expressions, Type type) {
		if (expressions == null) {
			_expressions = Collections.emptyList();
		}
		else {
			_expressions = Collections.unmodifiableList(expressions);
		}

		_type = type;
	}

	@Override
	public <T> T accept(ExpressionVisitor<T> expressionVisitor)
		throws ExpressionVisitException {

		List<T> expressions = new ArrayList<>();

		for (final Expression expression : _expressions) {
			expressions.add(expression.accept(expressionVisitor));
		}

		return expressionVisitor.visitMethodExpression(
			Collections.unmodifiableList(expressions), _type);
	}

	@Override
	public List<Expression> getExpressions() {
		return _expressions;
	}

	@Override
	public Type getType() {
		return _type;
	}

	@Override
	public String toString() {
		return StringBundler.concat("{", _type, " ", _expressions, "}");
	}

	private final List<Expression> _expressions;
	private final Type _type;

}
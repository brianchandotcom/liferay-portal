/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
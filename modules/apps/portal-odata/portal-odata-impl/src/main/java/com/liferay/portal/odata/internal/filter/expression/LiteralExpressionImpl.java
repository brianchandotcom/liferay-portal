/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.odata.internal.filter.expression;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.odata.filter.expression.ExpressionVisitException;
import com.liferay.portal.odata.filter.expression.ExpressionVisitor;
import com.liferay.portal.odata.filter.expression.LiteralExpression;

/**
 * @author Cristina González
 */
public class LiteralExpressionImpl implements LiteralExpression {

	public LiteralExpressionImpl(String text, Type type) {
		_text = text;
		_type = type;
	}

	@Override
	public <T> T accept(ExpressionVisitor<T> expressionVisitor)
		throws ExpressionVisitException {

		return expressionVisitor.visitLiteralExpression(this);
	}

	@Override
	public String getText() {
		return _text;
	}

	@Override
	public Type getType() {
		return _type;
	}

	@Override
	public String toString() {
		if (Validator.isNull(_text)) {
			return "";
		}

		return _text;
	}

	private final String _text;
	private final Type _type;

}
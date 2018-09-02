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

package com.liferay.structured.content.apio.internal.architect.filter.expression;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.structured.content.apio.architect.filter.expression.ExpressionVisitException;
import com.liferay.structured.content.apio.architect.filter.expression.ExpressionVisitor;
import com.liferay.structured.content.apio.architect.filter.expression.LiteralExpression;

/**
 * @author Cristina González
 */
public class LiteralExpressionImpl implements LiteralExpression {

	public LiteralExpressionImpl(String text, Type type) {
		_text = text;
		_type = type;
	}

	@Override
	public <T> T accept(ExpressionVisitor<T> visitor)
		throws ExpressionVisitException {

		return visitor.visitLiteralExpression(this);
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
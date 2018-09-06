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

package com.liferay.structured.content.apio.internal.architect.filter;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.structured.content.apio.architect.filter.expression.BinaryExpression;
import com.liferay.structured.content.apio.architect.filter.expression.ExpressionVisitor;
import com.liferay.structured.content.apio.architect.filter.expression.LiteralExpression;
import com.liferay.structured.content.apio.architect.filter.expression.MemberExpression;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * @author Julio Camarero
 */
public class ExpressionVisitorImpl implements ExpressionVisitor<Object> {

	@Override
	public Object visitBinaryExpressionOperation(
		BinaryExpression.Operation operation, Object left, Object right) {

		if (operation == BinaryExpression.Operation.EQ) {
			return new HashMap<String, Object>() {
				{
					put((String)left, right);
				}
			};
		}
		else {
			throw new UnsupportedOperationException(
				"Unsupported method visitBinaryExpressionOperation with " +
					"operation " + operation);
		}
	}

	@Override
	public Object visitLiteralExpression(LiteralExpression literalExpression) {
		if (Objects.equals(
				LiteralExpression.Type.STRING, literalExpression.getType())) {

			return _normalizeLiteral(literalExpression.getText());
		}

		return literalExpression.getText();
	}

	@Override
	public Object visitMemberExpression(MemberExpression memberExpression) {
		List<String> resourcePath = memberExpression.getResourcePath();

		return String.valueOf(resourcePath.get(0));
	}

	private Object _normalizeLiteral(String literal) {
		return StringUtil.replace(
			StringUtil.unquote(literal), StringPool.DOUBLE_APOSTROPHE,
			StringPool.APOSTROPHE);
	}

}
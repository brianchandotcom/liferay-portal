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

package com.liferay.segments.web.internal.odata;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.odata.filter.expression.BinaryExpression;
import com.liferay.portal.odata.filter.expression.ExpressionVisitException;
import com.liferay.portal.odata.filter.expression.ExpressionVisitor;
import com.liferay.portal.odata.filter.expression.ListExpression;
import com.liferay.portal.odata.filter.expression.LiteralExpression;
import com.liferay.portal.odata.filter.expression.MemberExpression;
import com.liferay.portal.odata.filter.expression.MethodExpression;

import java.util.List;

/**
 * @author Cristina González
 */
public class ExpressionVisitorImpl implements ExpressionVisitor<Object> {

	public ExpressionVisitorImpl() {
	}

	@Override
	public Object visitBinaryExpressionOperation(
			BinaryExpression.Operation operation, Object left,
			Object right)
		throws ExpressionVisitException {

		return null;
	}

	@Override
	public Object visitListExpressionOperation(
			ListExpression.Operation operation, Object left,
			List<Object> rights)
		throws ExpressionVisitException {

		return null;
	}

	@Override
	public Object visitLiteralExpression(
			LiteralExpression literalExpression)
		throws ExpressionVisitException {

		return null;
	}

	@Override
	public JSONObject visitMemberExpression(MemberExpression memberExpression)
		throws ExpressionVisitException {

		return null;
	}

	@Override
	public Object visitMethodExpression(
			List<Object> expressions, MethodExpression.Type type)
		throws ExpressionVisitException {

		return null;
	}

}
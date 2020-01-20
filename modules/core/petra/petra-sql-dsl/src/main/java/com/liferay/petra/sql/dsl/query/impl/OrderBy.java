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

package com.liferay.petra.sql.dsl.query.impl;

import com.liferay.petra.sql.dsl.ast.ASTNodeListener;
import com.liferay.petra.sql.dsl.ast.impl.BaseASTNode;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.petra.sql.dsl.query.LimitStep;
import com.liferay.petra.sql.dsl.query.OrderByExpression;
import com.liferay.petra.sql.dsl.query.OrderByStep;

import java.util.function.Consumer;

/**
 * @author Preston Crary
 */
public class OrderBy extends BaseASTNode implements DSLQuery, LimitStep {

	public OrderBy(
		OrderByStep orderByStep, OrderByExpression[] orderByExpressions) {

		super(orderByStep);

		if (orderByExpressions.length == 0) {
			throw new IllegalArgumentException();
		}

		_orderByExpressions = orderByExpressions;
	}

	public OrderByExpression[] getOrderByExpressions() {
		return _orderByExpressions;
	}

	@Override
	protected void doToSQL(
		Consumer<String> consumer, ASTNodeListener astNodeListener) {

		consumer.accept("order by ");

		for (int i = 0; i < _orderByExpressions.length; i++) {
			_orderByExpressions[i].toSQL(consumer, astNodeListener);

			if (i < (_orderByExpressions.length - 1)) {
				consumer.accept(", ");
			}
		}
	}

	private final OrderByExpression[] _orderByExpressions;

}
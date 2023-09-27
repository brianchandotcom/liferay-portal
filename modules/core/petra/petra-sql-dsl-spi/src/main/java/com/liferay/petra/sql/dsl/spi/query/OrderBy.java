/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.sql.dsl.spi.query;

import com.liferay.petra.sql.dsl.ast.ASTNodeListener;
import com.liferay.petra.sql.dsl.query.OrderByStep;
import com.liferay.petra.sql.dsl.query.sort.OrderByExpression;
import com.liferay.petra.sql.dsl.spi.ast.BaseASTNode;

import java.util.function.Consumer;

/**
 * @author Preston Crary
 */
public class OrderBy extends BaseASTNode implements DefaultLimitStep {

	public OrderBy(
		OrderByStep orderByStep, OrderByExpression[] orderByExpressions) {

		super(orderByStep);

		if (orderByExpressions.length == 0) {
			throw new IllegalArgumentException("Order by expressions is empty");
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

		_orderByExpressions[0].toSQL(consumer, astNodeListener);

		for (int i = 1; i < _orderByExpressions.length; i++) {
			consumer.accept(", ");

			_orderByExpressions[i].toSQL(consumer, astNodeListener);
		}
	}

	private final OrderByExpression[] _orderByExpressions;

}
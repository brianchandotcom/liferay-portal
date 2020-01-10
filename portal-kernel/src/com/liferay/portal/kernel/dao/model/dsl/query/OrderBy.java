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

/**
 * @author Preston Crary
 */
public class OrderBy extends BaseASTNode implements LimitStep, Query {

	public OrderBy(
		OrderByStep orderByStep, OrderByExpression[] orderByExpressions) {

		super(orderByStep);

		if (orderByExpressions.length == 0) {
			throw new IllegalArgumentException();
		}

		_orderByExpressions = orderByExpressions;
	}

	@Override
	public void doToSQL(StringBundler sb, ASTNodeListener astNodeListener) {
		sb.append("order by ");

		for (int i = 0; i < _orderByExpressions.length; i++) {
			_orderByExpressions[i].doToSQL(sb, astNodeListener);

			if (i < (_orderByExpressions.length - 1)) {
				sb.append(", ");
			}
		}
	}

	public OrderByExpression[] getOrderByExpressions() {
		return _orderByExpressions;
	}

	private final OrderByExpression[] _orderByExpressions;

}
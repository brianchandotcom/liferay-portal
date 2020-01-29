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

package com.liferay.petra.sql.dsl.spi.query;

import com.liferay.petra.sql.dsl.BaseTable;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.query.LimitStep;
import com.liferay.petra.sql.dsl.query.OrderByStep;
import com.liferay.petra.sql.dsl.query.sort.OrderByExpression;
import com.liferay.petra.sql.dsl.query.sort.OrderByInfo;
import com.liferay.petra.sql.dsl.spi.query.sort.DefaultOrderByExpression;
import com.liferay.petra.string.StringBundler;

/**
 * @author Preston Crary
 */
public interface DefaultOrderByStep extends DefaultLimitStep, OrderByStep {

	@Override
	public default LimitStep orderBy(
		BaseTable<?> baseTable, OrderByInfo orderByInfo) {

		if (orderByInfo == null) {
			return this;
		}

		String[] orderByFields = orderByInfo.getOrderByFields();

		OrderByExpression[] orderByExpressions =
			new OrderByExpression[orderByFields.length];

		for (int i = 0; i < orderByFields.length; i++) {
			String field = orderByFields[i];

			Column<?, ?> column = baseTable.getColumn(field);

			if (column == null) {
				throw new IllegalArgumentException(
					StringBundler.concat(
						"No column \"", field, "\" for table ",
						baseTable.getTableName()));
			}

			orderByExpressions[i] = new DefaultOrderByExpression(
				column, orderByInfo.isAscending(field));
		}

		return new OrderBy(this, orderByExpressions);
	}

	@Override
	public default LimitStep orderBy(OrderByExpression... orderByExpressions) {
		if ((orderByExpressions == null) || (orderByExpressions.length == 0)) {
			return this;
		}

		return new OrderBy(this, orderByExpressions);
	}

}
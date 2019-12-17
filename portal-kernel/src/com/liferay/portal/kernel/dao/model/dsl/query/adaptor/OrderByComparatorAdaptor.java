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

package com.liferay.portal.kernel.dao.model.dsl.query.adaptor;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.model.Column;
import com.liferay.portal.kernel.dao.model.Table;
import com.liferay.portal.kernel.dao.model.dsl.query.OrderBy;
import com.liferay.portal.kernel.dao.model.dsl.query.OrderByExpression;
import com.liferay.portal.kernel.dao.model.dsl.query.OrderByStep;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Preston Crary
 */
public class OrderByComparatorAdaptor extends OrderBy {

	public OrderByComparatorAdaptor(
		OrderByStep orderByStep, Table<?> table,
		OrderByComparator<?> orderByComparator) {

		super(orderByStep, _toOrderByExpression(table, orderByComparator));
	}

	private static OrderByExpression[] _toOrderByExpression(
		Table<?> table, OrderByComparator<?> orderByComparator) {

		String[] orderByFields = orderByComparator.getOrderByFields();

		OrderByExpression[] orderByExpressions =
			new OrderByExpression[orderByFields.length];

		for (int i = 0; i < orderByFields.length; i++) {
			String field = orderByFields[i];

			Column<?, ?> column = table.getColumn(field);

			if (column == null) {
				throw new IllegalArgumentException(
					StringBundler.concat(
						"No column \"", field, "\" for table ",
						table.getTableName()));
			}

			orderByExpressions[i] = new OrderByExpression(
				column, orderByComparator.isAscending(field));
		}

		return orderByExpressions;
	}

}
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

package com.liferay.object.rest.internal.petra.sql.dsl.expression;

import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.query.sort.OrderByExpression;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Gabriel Albuquerque
 */
public class OrderByExpressionUtil {

	public static OrderByExpression[] getOrderByExpressions(
		long objectDefinitionId, Sort[] sorts,
		ObjectFieldLocalService objectFieldLocalService) {

		if (sorts == null) {
			return null;
		}

		List<Object> orderByExpressions = new ArrayList<>();

		for (Sort sort : sorts) {
			String[] sortParts = StringUtil.split(
				sort.getFieldName(), CharPool.POUND);

			Column<?, ?> column = objectFieldLocalService.getColumn(
				objectDefinitionId, sortParts[1]);

			if (sort.isReverse()) {
				orderByExpressions.add(column.descending());
			}
			else {
				orderByExpressions.add(column.ascending());
			}
		}

		return orderByExpressions.toArray(new OrderByExpression[0]);
	}

}
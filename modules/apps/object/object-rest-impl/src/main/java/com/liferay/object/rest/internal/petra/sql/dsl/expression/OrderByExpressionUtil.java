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
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.query.sort.OrderByExpression;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Gabriel Albuquerque
 */
public class OrderByExpressionUtil {

	public static OrderByExpression[] getOrderByExpressions(
		long objectDefinitionId,
		ObjectFieldLocalService objectFieldLocalService, Sort[] sorts) {

		if (sorts == null) {
			return null;
		}

		return TransformUtil.transform(
			sorts,
			sort -> {
				String fieldName = sort.getFieldName();

				if (fieldName.startsWith("nestedFieldArray.")) {
					String[] parts = StringUtil.split(
						sort.getFieldName(), CharPool.POUND);

					fieldName = parts[1];
				}

				Column<?, ?> column = objectFieldLocalService.getColumn(
					objectDefinitionId, fieldName);

				if (sort.isReverse()) {
					return column.descending();
				}

				return column.ascending();
			},
			OrderByExpression.class);
	}

}
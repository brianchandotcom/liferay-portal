/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.rest.internal.petra.sql.dsl.expression;

import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.DSLFunctionFactoryUtil;
import com.liferay.petra.sql.dsl.expression.Expression;
import com.liferay.petra.sql.dsl.query.sort.OrderByExpression;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.StringUtil;

import java.sql.Clob;
import java.sql.Types;

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

				if (column.getSQLType() == Types.CLOB) {
					return _getOrderByExpression(
						DSLFunctionFactoryUtil.castClobText(
							(Expression<Clob>)column),
						sort);
				}

				return _getOrderByExpression(column, sort);
			},
			OrderByExpression.class);
	}

	private static OrderByExpression _getOrderByExpression(
		Expression<?> expression, Sort sort) {

		if (sort.isReverse()) {
			return expression.descending();
		}

		return expression.ascending();
	}

}
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.rest.internal.util;

import com.liferay.petra.sql.dsl.expression.Expression;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.portal.odata.filter.expression.BinaryExpression;

import java.util.Objects;

/**
 * @author Alejandro Tardín
 */
public class BinaryExpressionConverterUtil {

	public static <T> Predicate getExpressionPredicate(
		Expression<T> expression, BinaryExpression.Operation operation,
		T value) {

		if (Objects.equals(BinaryExpression.Operation.EQ, operation)) {
			if (value == null) {
				return expression.isNull();
			}

			return expression.eq(value);
		}
		else if (Objects.equals(BinaryExpression.Operation.GE, operation)) {
			return expression.gte(value);
		}
		else if (Objects.equals(BinaryExpression.Operation.GT, operation)) {
			return expression.gt(value);
		}
		else if (Objects.equals(BinaryExpression.Operation.LE, operation)) {
			return expression.lte(value);
		}
		else if (Objects.equals(BinaryExpression.Operation.LT, operation)) {
			return expression.lt(value);
		}
		else if (Objects.equals(BinaryExpression.Operation.NE, operation)) {
			if (value == null) {
				return expression.isNotNull();
			}

			return expression.neq(value);
		}

		return null;
	}

}
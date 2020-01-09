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

package com.liferay.portal.kernel.dao.model.dsl;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.model.dsl.ast.impl.DefaultASTNodeListener;
import com.liferay.portal.kernel.dao.model.dsl.expressions.Expression;
import com.liferay.portal.kernel.dao.model.dsl.query.AggregateExpression;
import com.liferay.portal.kernel.dao.model.dsl.query.Query;
import com.liferay.portal.kernel.dao.model.dsl.query.Select;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;

import java.util.List;

/**
 * @author Preston Crary
 */
public class DSLStatementUtil {

	public static Select count() {
		return new Select(false, AggregateExpression.COUNT_STAR_COUNT_VALUE);
	}

	public static Select countDistinct(Expression<?> expression) {
		return new Select(
			false, new AggregateExpression<>(true, expression, "count"));
	}

	public static SQLQuery createSynchronizedSQLQuery(
		Session session, Query query) {

		StringBundler sb = new StringBundler();

		DefaultASTNodeListener defaultASTNodeListener =
			new DefaultASTNodeListener();

		query.toSQL(sb, defaultASTNodeListener);

		SQLQuery q = session.createSynchronizedSQLQuery(
			sb.toString(), true, defaultASTNodeListener.getTableNames());

		List<Object> scalarValues = defaultASTNodeListener.getScalarValues();

		if (!scalarValues.isEmpty()) {
			QueryPos queryPos = QueryPos.getInstance(q);

			for (Object value : scalarValues) {
				queryPos.add(value);
			}
		}

		return q;
	}

	public static Select select(Expression<?>... expressions) {
		return new Select(false, expressions);
	}

	public static Select selectDistinct(Expression<?>... expressions) {
		return new Select(true, expressions);
	}

}
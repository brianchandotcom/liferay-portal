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

import com.liferay.portal.kernel.dao.model.Table;
import com.liferay.portal.kernel.dao.model.dsl.ast.impl.DefaultASTNodeVisitor;
import com.liferay.portal.kernel.dao.model.dsl.expressions.Expression;
import com.liferay.portal.kernel.dao.model.dsl.query.AggregateExpression;
import com.liferay.portal.kernel.dao.model.dsl.query.Query;
import com.liferay.portal.kernel.dao.model.dsl.query.Select;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;

import java.util.List;
import java.util.Set;

/**
 * @author Preston Crary
 */
public class DSLStatementUtil {

	public static Select count() {
		return new Select(
			false,
			new Expression[] {AggregateExpression.COUNT_STAR_COUNT_VALUE});
	}

	public static Select countDistinct(Expression<?> expression) {
		return new Select(
			false,
			new Expression[] {
				new AggregateExpression<>(true, expression, "count")
			});
	}

	public static SQLQuery createSynchronizedSQLQuery(
		Session session, Query query) {

		DefaultASTNodeVisitor defaultASTNodeVisitor =
			new DefaultASTNodeVisitor();

		query.accept(defaultASTNodeVisitor);

		Set<Table<?>> tables = defaultASTNodeVisitor.getTables();

		String[] tableNames = new String[tables.size()];

		int i = 0;

		for (Table<?> table : tables) {
			tableNames[i++] = table.getTableName();
		}

		SQLQuery q = session.createSynchronizedSQLQuery(
			defaultASTNodeVisitor.toString(), true, tableNames);

		List<Object> scalarValues = defaultASTNodeVisitor.getScalarValues();

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
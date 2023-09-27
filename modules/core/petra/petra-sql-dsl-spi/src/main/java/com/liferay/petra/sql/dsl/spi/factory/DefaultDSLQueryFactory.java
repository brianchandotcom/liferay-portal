/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.sql.dsl.spi.factory;

import com.liferay.petra.sql.dsl.Table;
import com.liferay.petra.sql.dsl.expression.Expression;
import com.liferay.petra.sql.dsl.factory.DSLQueryFactory;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.petra.sql.dsl.query.FromStep;
import com.liferay.petra.sql.dsl.spi.expression.AggregateExpression;
import com.liferay.petra.sql.dsl.spi.expression.DefaultAlias;
import com.liferay.petra.sql.dsl.spi.expression.DefaultScalarDSLQueryAlias;
import com.liferay.petra.sql.dsl.spi.expression.TableStar;
import com.liferay.petra.sql.dsl.spi.query.Select;

import java.util.Arrays;
import java.util.Collections;

/**
 * @author Preston Crary
 */
public class DefaultDSLQueryFactory implements DSLQueryFactory {

	@Override
	public FromStep count() {
		return _SELECT_COUNT_STAR_COUNT_VALUE;
	}

	@Override
	public FromStep countDistinct(Expression<?> expression) {
		return new Select(
			false,
			Collections.singletonList(
				new DefaultAlias<>(
					new AggregateExpression<>(true, expression, "count"),
					"COUNT_VALUE")));
	}

	@Override
	public <T> Expression<T> scalarSubDSLQuery(
		DSLQuery dslQuery, Class<T> javaType, String name, int sqlType) {

		return new DefaultScalarDSLQueryAlias<>(
			dslQuery, javaType, name, sqlType);
	}

	@Override
	public FromStep select() {
		return _SELECT_STAR;
	}

	@Override
	public FromStep select(Expression<?>... expressions) {
		return new Select(false, Arrays.asList(expressions));
	}

	@Override
	public <T extends Table<T>> FromStep select(Table<T> table) {
		return new Select(false, Collections.singleton(new TableStar(table)));
	}

	@Override
	public FromStep selectDistinct(Expression<?>... expressions) {
		return new Select(true, Arrays.asList(expressions));
	}

	@Override
	public <T extends Table<T>> FromStep selectDistinct(Table<T> table) {
		return new Select(true, Collections.singleton(new TableStar(table)));
	}

	private static final FromStep _SELECT_COUNT_STAR_COUNT_VALUE = new Select(
		false,
		Collections.singletonList(
			new DefaultAlias<>(
				new AggregateExpression<>(false, null, "count"),
				"COUNT_VALUE")));

	private static final FromStep _SELECT_STAR = new Select(
		false, Collections.emptyList());

}
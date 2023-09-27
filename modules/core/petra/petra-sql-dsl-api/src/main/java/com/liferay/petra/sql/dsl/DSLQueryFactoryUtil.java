/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.sql.dsl;

import com.liferay.petra.sql.dsl.expression.Expression;
import com.liferay.petra.sql.dsl.factory.DSLQueryFactory;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.petra.sql.dsl.query.FromStep;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @author Preston Crary
 */
public class DSLQueryFactoryUtil {

	public static FromStep count() {
		return _DSL_QUERY_FACTORY.count();
	}

	public static FromStep countDistinct(Expression<?> expression) {
		return _DSL_QUERY_FACTORY.countDistinct(expression);
	}

	public static <T> Expression<T> scalarSubDSLQuery(
		DSLQuery dslQuery, Class<T> javaType, String name, int sqlType) {

		return _DSL_QUERY_FACTORY.scalarSubDSLQuery(
			dslQuery, javaType, name, sqlType);
	}

	public static FromStep select() {
		return _DSL_QUERY_FACTORY.select();
	}

	public static FromStep select(Expression<?>... expressions) {
		return _DSL_QUERY_FACTORY.select(expressions);
	}

	public static <T extends Table<T>> FromStep select(Table<T> table) {
		return _DSL_QUERY_FACTORY.select(table);
	}

	public static FromStep selectDistinct(Expression<?>... expressions) {
		return _DSL_QUERY_FACTORY.selectDistinct(expressions);
	}

	public static <T extends Table<T>> FromStep selectDistinct(Table<T> table) {
		return _DSL_QUERY_FACTORY.selectDistinct(table);
	}

	private static final DSLQueryFactory _DSL_QUERY_FACTORY;

	static {
		ServiceLoader<DSLQueryFactory> serviceLoader = ServiceLoader.load(
			DSLQueryFactory.class, DSLQueryFactory.class.getClassLoader());

		Iterator<DSLQueryFactory> iterator = serviceLoader.iterator();

		_DSL_QUERY_FACTORY = iterator.next();
	}

}
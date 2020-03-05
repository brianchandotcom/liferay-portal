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

package com.liferay.petra.sql.dsl;

import com.liferay.petra.sql.dsl.expression.Expression;
import com.liferay.petra.sql.dsl.factory.DSLQueryFactory;
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

	public static FromStep select() {
		return _DSL_QUERY_FACTORY.select();
	}

	public static FromStep select(Expression<?>... expressions) {
		return _DSL_QUERY_FACTORY.select(expressions);
	}

	public static <T extends Table<T>> FromStep select(T table) {
		return _DSL_QUERY_FACTORY.select(table);
	}

	public static FromStep selectDistinct(Expression<?>... expressions) {
		return _DSL_QUERY_FACTORY.selectDistinct(expressions);
	}

	public static <T extends Table<T>> FromStep selectDistinct(T table) {
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
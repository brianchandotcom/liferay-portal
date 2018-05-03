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

package com.liferay.wiki.internal.tools.sql.provider;

import com.liferay.portal.tools.sql.SQLQueryProvider;

import java.io.InputStream;

/**
 * @author Miguel Pastor
 */
public class WikiSQLQueryProvider implements SQLQueryProvider {

	@Override
	public InputStream getIndexesSQL() {
		return getInputStream("META-INF/sql/indexes.sql");
	}

	@Override
	public InputStream getTablesSQL() {
		return getInputStream("META-INF/sql/tables.sql");
	}

	protected InputStream getInputStream(String path) {
		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		return classLoader.getResourceAsStream(path);
	}

}
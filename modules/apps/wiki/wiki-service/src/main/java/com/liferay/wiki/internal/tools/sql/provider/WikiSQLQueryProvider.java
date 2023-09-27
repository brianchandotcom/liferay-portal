/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		return _getInputStream("META-INF/sql/indexes.sql");
	}

	@Override
	public InputStream getTablesSQL() {
		return _getInputStream("META-INF/sql/tables.sql");
	}

	private InputStream _getInputStream(String path) {
		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		return classLoader.getResourceAsStream(path);
	}

}
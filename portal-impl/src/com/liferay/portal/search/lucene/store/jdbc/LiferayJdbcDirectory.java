/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.search.lucene.store.jdbc;

import java.io.IOException;

import javax.sql.DataSource;

import org.apache.lucene.store.jdbc.JdbcDirectory;
import org.apache.lucene.store.jdbc.dialect.Dialect;

/**
 * @author Matthew Kong
 */
public class LiferayJdbcDirectory extends JdbcDirectory {

	public LiferayJdbcDirectory(
		DataSource dataSource, Dialect dialect, String tableName) {

		super(dataSource, dialect, tableName);
	}

	@Override
	public String[] listAll() throws IOException {
		return list();
	}

}
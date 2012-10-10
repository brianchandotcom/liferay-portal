/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.dao.db;

import com.liferay.portal.kernel.dao.db.DB;

import java.io.IOException;

/**
 * @author Miguel Pastor
 */
public abstract class TestDB {

	protected String buildSQL(String query) throws IOException {
		DB db = getDB();

		return db.buildSQL(query);
	}

	protected abstract DB getDB();

	protected static final String RENAME_TABLE_QUERY = "alter_table_name a b";

}
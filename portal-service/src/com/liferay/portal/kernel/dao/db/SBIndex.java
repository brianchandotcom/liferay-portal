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

package com.liferay.portal.kernel.dao.db;

/**
 * @author James Lefeu
 * @auther Peter Shin
 */
public class SBIndex extends Index {

	public SBIndex(
		String indexName, String tableName, String indexSpec, String sql,
		boolean unique) {

		super(indexName, tableName, unique);

		_indexSpec = indexSpec;
		_sql = sql;
	}

	public String getIndexSpec() {
		return _indexSpec;
	}

	public String getSQL() {
		return _sql;
	}

	private String _indexSpec;
	private String _sql;

}
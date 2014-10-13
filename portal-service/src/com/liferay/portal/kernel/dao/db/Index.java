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

package com.liferay.portal.kernel.dao.db;

/**
 * @author Brian Wing Shun Chan
 */
public class Index {

	public Index(String indexName, String tableName, boolean unique) {
		_indexName = indexName;
		_tableName = tableName;
		_unique = unique;
	}

	public String getIndexName() {
		return _indexName;
	}

	public String getTableName() {
		return _tableName;
	}

	public boolean isUnique() {
		return _unique;
	}

	private final String _indexName;
	private final String _tableName;
	private final boolean _unique;

}
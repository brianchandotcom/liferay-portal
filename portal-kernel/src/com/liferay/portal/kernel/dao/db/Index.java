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

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;

import java.util.Objects;

/**
 * @author Brian Wing Shun Chan
 */
public class Index {

	public Index(String indexName, String tableName, boolean unique) {
		_indexName = indexName;
		_tableName = tableName;
		_unique = unique;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof Index)) {
			return false;
		}

		Index index = (Index)obj;

		if (Objects.equals(_indexName, index._indexName) &&
			Objects.equals(_tableName, index._tableName) &&
			(_unique == index._unique)) {

			return true;
		}

		return false;
	}

	public String getIndexName() {
		return _indexName;
	}

	public String getTableName() {
		return _tableName;
	}

	@Override
	public int hashCode() {
		int hash = HashUtil.hash(0, _indexName);

		hash = HashUtil.hash(hash, _tableName);

		return HashUtil.hash(hash, _unique);
	}

	public boolean isUnique() {
		return _unique;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(7);

		sb.append("{tableName=");
		sb.append(_tableName);
		sb.append(", indexName=");
		sb.append(_indexName);
		sb.append(", unique=");
		sb.append(_unique);
		sb.append("}");

		return sb.toString();
	}

	private final String _indexName;
	private final String _tableName;
	private final boolean _unique;

}
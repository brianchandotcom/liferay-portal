/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Index)) {
			return false;
		}

		Index index = (Index)object;

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
		return StringBundler.concat(
			"{indexName=", _indexName, ", tableName=", _tableName, ", unique=",
			_unique, "}");
	}

	private final String _indexName;
	private final String _tableName;
	private final boolean _unique;

}
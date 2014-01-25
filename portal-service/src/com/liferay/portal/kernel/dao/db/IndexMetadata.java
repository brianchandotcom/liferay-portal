/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Arrays;

/**
 * @author James Lefeu
 * @author Peter Shin
 * @author Shuyang Zhou
 */
public class IndexMetadata extends Index implements Comparable<IndexMetadata> {

	public IndexMetadata(
		String indexName, String tableName, boolean unique,
		String... columnNames) {

		super(indexName, tableName, unique);

		if (columnNames == null) {
			throw new NullPointerException("column names is missing");
		}

		_columnNames = columnNames;

		StringBundler sb = new StringBundler(8 + columnNames.length * 2);

		if (unique) {
			sb.append("create unique ");
		}
		else {
			sb.append("create ");
		}

		sb.append("index ");
		sb.append(indexName);
		sb.append(" on ");
		sb.append(tableName);

		sb.append(StringPool.SPACE);
		sb.append(StringPool.OPEN_PARENTHESIS);

		for (String columnName : columnNames) {
			sb.append(columnName);
			sb.append(StringPool.COMMA_AND_SPACE);
		}

		sb.setIndex(sb.index() - 1);

		sb.append(StringPool.CLOSE_PARENTHESIS);
		sb.append(StringPool.SEMICOLON);

		_createSQL = sb.toString();

		sb.setIndex(0);

		sb.append("drop index ");
		sb.append(indexName);
		sb.append(" on ");
		sb.append(tableName);
		sb.append(StringPool.SEMICOLON);

		_dropSQL = sb.toString();
	}

	@Override
	public int compareTo(IndexMetadata indexMetadata) {
		String columnNames = StringUtil.merge(_columnNames);

		String anotherColumnNames = StringUtil.merge(
			indexMetadata._columnNames);

		return columnNames.compareTo(anotherColumnNames);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof IndexMetadata)) {
			return false;
		}

		IndexMetadata indexMetadata = (IndexMetadata)obj;

		if (Validator.equals(getTableName(), indexMetadata.getTableName()) &&
			Arrays.equals(getColumnNames(), indexMetadata.getColumnNames())) {

			return true;
		}

		return false;
	}

	public String[] getColumnNames() {
		return _columnNames;
	}

	public String getCreateSQL() {
		return _createSQL;
	}

	public String getDropSQL() {
		return _dropSQL;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, getTableName());

		for (String columnName : _columnNames) {
			hashCode = HashUtil.hash(hashCode, columnName);
		}

		return hashCode;
	}

	@Override
	public String toString() {
		return _createSQL;
	}

	private String[] _columnNames;
	private String _createSQL;
	private String _dropSQL;

}
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

import aQute.bnd.annotation.ProviderType;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author James Lefeu
 * @author Peter Shin
 * @author Shuyang Zhou
 */
@ProviderType
public class IndexMetadata extends Index implements Comparable<IndexMetadata> {

	public IndexMetadata(
		String indexName, String tableName, boolean unique,
		String... columnNames) {

		super(indexName, tableName, unique);

		if (columnNames == null) {
			throw new NullPointerException("Column names are missing");
		}

		_columnNames = columnNames;

		StringBundler sb = new StringBundler(5);

		sb.append("drop index ");
		sb.append(indexName);
		sb.append(" on ");
		sb.append(tableName);
		sb.append(StringPool.SEMICOLON);

		_dropSQL = sb.toString();
	}

	@Override
	public int compareTo(IndexMetadata indexMetadata) {
		String columnNames = StringUtil.merge(getColumnNames());

		String indexMetadataColumnNames = StringUtil.merge(
			indexMetadata.getColumnNames());

		return columnNames.compareTo(indexMetadataColumnNames);
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

		if (Objects.equals(getTableName(), indexMetadata.getTableName()) &&
			Arrays.equals(_columnNames, indexMetadata._columnNames)) {

			return true;
		}

		return false;
	}

	public String[] getColumnNames() {
		String[] columnNames = _columnNames.clone();

		for (int i = 0; i < columnNames.length; i++) {
			int index = columnNames[i].indexOf("[$COLUMN_LENGTH:");

			if (index > 0) {
				columnNames[i] = columnNames[i].substring(0, index);
			}
		}

		return columnNames;
	}

	public String getCreateSQL(int[] lengths) {
		int sbSize = 8 + _columnNames.length * 2;

		if (lengths != null) {
			sbSize += _columnNames.length * 3;
		}

		StringBundler sb = new StringBundler(sbSize);

		if (isUnique()) {
			sb.append("create unique ");
		}
		else {
			sb.append("create ");
		}

		sb.append("index ");
		sb.append(getIndexName());
		sb.append(" on ");
		sb.append(getTableName());

		sb.append(StringPool.SPACE);
		sb.append(StringPool.OPEN_PARENTHESIS);

		for (int i = 0; i < _columnNames.length; i++) {
			sb.append(_columnNames[i]);

			if ((lengths != null) && (lengths[i] > 0)) {
				sb.append("[$COLUMN_LENGTH:");
				sb.append(lengths[i]);
				sb.append("$]");
			}

			sb.append(StringPool.COMMA_AND_SPACE);
		}

		sb.setIndex(sb.index() - 1);

		sb.append(StringPool.CLOSE_PARENTHESIS);
		sb.append(StringPool.SEMICOLON);

		return sb.toString();
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

	public Boolean redundantTo(IndexMetadata indexMetadata) {
		String[] indexMetadataColumnNames = indexMetadata._columnNames;

		if (_columnNames.length <= indexMetadataColumnNames.length) {
			for (int i = 0; i < _columnNames.length; i++) {
				if (!_columnNames[i].equals(indexMetadataColumnNames[i])) {
					return null;
				}
			}

			if (isUnique()) {
				return Boolean.FALSE;
			}

			return Boolean.TRUE;
		}

		Boolean redundant = indexMetadata.redundantTo(this);

		if (redundant == null) {
			return null;
		}

		return !redundant;
	}

	@Override
	public String toString() {
		return getCreateSQL(null);
	}

	private final String[] _columnNames;
	private final String _dropSQL;

}
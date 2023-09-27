/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.dao.jdbc;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Minhchau Dang
 * @author Brian Wing Shun Chan
 */
public class SmartResultSet {

	public SmartResultSet(ResultSet resultSet) throws SQLException {
		_resultSet = resultSet;

		_metaData = resultSet.getMetaData();

		_columnCount = _metaData.getColumnCount();

		_columnIndexCache = new HashMap<>();
	}

	public int findColumn(String columnName) throws SQLException {
		Integer columnIndex = _columnIndexCache.get(columnName);

		if (columnIndex != null) {
			return columnIndex;
		}

		// Check for the full column name

		for (int i = 1; i <= _columnCount; ++i) {
			String availableName = _metaData.getColumnName(i);

			if (StringUtil.equalsIgnoreCase(availableName, columnName)) {
				_columnIndexCache.put(columnName, i);

				return i;
			}
		}

		// Check for a shortened column name

		int pos = columnName.indexOf(CharPool.PERIOD);

		if (pos != -1) {
			String shortName = columnName.substring(pos + 1);

			for (int i = 1; i <= _columnCount; ++i) {
				String availableName = _metaData.getColumnName(i);

				if (StringUtil.equalsIgnoreCase(availableName, shortName)) {
					_columnIndexCache.put(columnName, i);

					return i;
				}
			}
		}

		// Let the result set figure it out

		columnIndex = _resultSet.findColumn(columnName);

		_columnIndexCache.put(columnName, columnIndex);

		return columnIndex;
	}

	public boolean first() throws SQLException {
		return _resultSet.first();
	}

	public Date getDate(int columnIndex) throws SQLException {
		return _resultSet.getDate(columnIndex);
	}

	public Date getDate(String columnName) throws SQLException {
		int columnIndex = findColumn(columnName);

		return _resultSet.getDate(columnIndex);
	}

	public double getDouble(int columnIndex) throws SQLException {
		return _resultSet.getDouble(columnIndex);
	}

	public double getDouble(String columnName) throws SQLException {
		int columnIndex = findColumn(columnName);

		return _resultSet.getDouble(columnIndex);
	}

	public float getFloat(int columnIndex) throws SQLException {
		return _resultSet.getFloat(columnIndex);
	}

	public float getFloat(String columnName) throws SQLException {
		int columnIndex = findColumn(columnName);

		return _resultSet.getFloat(columnIndex);
	}

	public int getInt(int columnIndex) throws SQLException {
		return _resultSet.getInt(columnIndex);
	}

	public int getInt(String columnName) throws SQLException {
		int columnIndex = findColumn(columnName);

		return _resultSet.getInt(columnIndex);
	}

	public long getLong(int columnIndex) throws SQLException {
		return _resultSet.getLong(columnIndex);
	}

	public long getLong(String columnName) throws SQLException {
		int columnIndex = findColumn(columnName);

		return _resultSet.getLong(columnIndex);
	}

	public short getShort(int columnIndex) throws SQLException {
		return _resultSet.getShort(columnIndex);
	}

	public short getShort(String columnName) throws SQLException {
		int columnIndex = findColumn(columnName);

		return _resultSet.getShort(columnIndex);
	}

	public String getString(int columnIndex) throws SQLException {
		return _resultSet.getString(columnIndex);
	}

	public String getString(String columnName) throws SQLException {
		int columnIndex = findColumn(columnName);

		return _resultSet.getString(columnIndex);
	}

	public Timestamp getTimestamp(int columnIndex) throws SQLException {
		return _resultSet.getTimestamp(columnIndex);
	}

	public Timestamp getTimestamp(String columnName) throws SQLException {
		int columnIndex = findColumn(columnName);

		return _resultSet.getTimestamp(columnIndex);
	}

	public boolean last() throws SQLException {
		return _resultSet.last();
	}

	public boolean next() throws SQLException {
		return _resultSet.next();
	}

	public boolean previous() throws SQLException {
		return _resultSet.previous();
	}

	private final int _columnCount;
	private final Map<String, Integer> _columnIndexCache;
	private final ResultSetMetaData _metaData;
	private final ResultSet _resultSet;

}
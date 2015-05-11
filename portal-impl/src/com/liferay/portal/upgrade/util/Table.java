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

package com.liferay.portal.upgrade.util;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedWriter;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.StagnantRowException;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.util.PropsUtil;

import java.io.FileReader;
import java.io.FileWriter;

import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;

import java.text.DateFormat;

import java.util.Date;

import org.apache.commons.lang.time.StopWatch;

/**
 * @author Alexander Chow
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class Table {

	public Table(String tableName) {
		_tableName = tableName;
	}

	public Table(String tableName, Object[][] columns) {
		_tableName = tableName;

		setColumns(columns);
	}

	public void appendColumn(StringBuilder sb, Object value, boolean last)
		throws Exception {

		if (value == null) {
			throw new UpgradeException(
				"Nulls should never be inserted into the database. " +
					"Attempted to append column to " + sb.toString() + ".");
		}
		else if (value instanceof byte[]) {
			sb.append(Base64.encode((byte[])value));
		}
		else if (value instanceof Clob || value instanceof String) {
			value = StringUtil.replace(
				(String)value, _SAFE_TABLE_CHARS[0], _SAFE_TABLE_CHARS[1]);

			sb.append(value);
		}
		else if (value instanceof Date) {
			DateFormat df = DateUtil.getISOFormat();

			sb.append(df.format(value));
		}
		else {
			sb.append(value);
		}

		sb.append(StringPool.COMMA);

		if (last) {
			sb.append(StringPool.NEW_LINE);
		}
	}

	public void appendColumn(
			StringBuilder sb, ResultSet rs, String name, Integer type,
			boolean last)
		throws Exception {

		Object value = null;

		try {
			value = getValue(rs, name, type);
		}
		catch (SQLException sqle) {
			if (name.equals("uuid_")) {
				sb.append(PortalUUIDUtil.generate());
			}

			sb.append(StringPool.COMMA);

			if (last) {
				sb.append(StringPool.NEW_LINE);
			}

			return;
		}

		appendColumn(sb, value, last);
	}

	public void generateTempFile() throws Exception {
		Connection con = DataAccess.getUpgradeOptimizedConnection();

		try {
			generateTempFile(con);
		}
		finally {
			DataAccess.cleanUp(con);
		}
	}

	public void generateTempFile(Connection con) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;

		boolean empty = true;

		String tempFileName =
			SystemProperties.get(SystemProperties.TMP_DIR) + "/temp-db-" +
				_tableName + "-" + System.currentTimeMillis();

		StopWatch stopWatch = new StopWatch();

		stopWatch.start();

		if (_log.isInfoEnabled()) {
			_log.info(
				"Starting backup of " + _tableName + " to " + tempFileName);
		}

		UnsyncBufferedWriter unsyncBufferedWriter = new UnsyncBufferedWriter(
			new FileWriter(tempFileName));

		try {
			ps = getSelectPreparedStatement(con);

			rs = ps.executeQuery();

			while (rs.next()) {
				String data = null;

				try {
					data = getExportedData(rs);

					unsyncBufferedWriter.write(data);

					_totalRows++;

					empty = false;
				}
				catch (StagnantRowException sre) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Skipping stagnant data in " + _tableName + ": " +
								sre.getMessage());
					}
				}
			}

			if (_log.isInfoEnabled()) {
				_log.info(
					"Finished backup of " + _tableName + " to " +
						tempFileName + " in " + stopWatch.getTime() + " ms");
			}
		}
		catch (Exception e) {
			FileUtil.delete(tempFileName);

			throw e;
		}
		finally {
			DataAccess.cleanUp(null, ps, rs);

			unsyncBufferedWriter.close();
		}

		if (!empty) {
			_tempFileName = tempFileName;

			return;
		}

		FileUtil.delete(tempFileName);
	}

	public Object[][] getColumns() {
		return _columns;
	}

	public String getCreateSQL() throws Exception {
		return _createSQL;
	}

	public String getDeleteSQL() throws Exception {
		return "DELETE FROM " + _tableName;
	}

	public String getExportedData(ResultSet rs) throws Exception {
		StringBuilder sb = new StringBuilder();

		Object[][] columns = getColumns();

		for (int i = 0; i < columns.length; i++) {
			boolean last = false;

			if ((i + 1) == columns.length) {
				last = true;
			}

			appendColumn(
				sb, rs, (String)columns[i][0], (Integer)columns[i][1], last);
		}

		return sb.toString();
	}

	public String getInsertSQL() throws Exception {
		String sql = "INSERT INTO " + getInsertTableName() + " (";

		for (int i = 0; i < _order.length; i++) {
			int pos = _order[i];

			sql += _columns[pos][0];

			if ((i + 1) < _columns.length) {
				sql += ", ";
			}
			else {
				sql += ") VALUES (";
			}
		}

		for (int i = 0; i < _columns.length; i++) {
			sql += "?";

			if ((i + 1) < _columns.length) {
				sql += ", ";
			}
			else {
				sql += ")";
			}
		}

		return sql;
	}

	public String getInsertTableName() throws Exception {
		String createSQL = getCreateSQL();

		if (Validator.isNotNull(createSQL)) {
			String createSQLLowerCase = StringUtil.toLowerCase(createSQL);

			int x = createSQLLowerCase.indexOf("create table ");

			if (x == -1) {
				return _tableName;
			}

			x += 13;

			int y = createSQL.indexOf(" ", x);

			return createSQL.substring(x, y).trim();
		}
		else {
			return _tableName;
		}
	}

	public int[] getOrder() {
		return _order;
	}

	public PreparedStatement getSelectPreparedStatement(Connection con)
		throws Exception {

		return con.prepareStatement(getSelectSQL());
	}

	public String getSelectSQL() throws Exception {
		if (_selectSQL == null) {
			/*String sql = "select ";

			for (int i = 0; i < _columns.length; i++) {
				sql += _columns[i][0];

				if ((i + 1) < _columns.length) {
					sql += ", ";
				}
				else {
					sql += " from " + _tableName;
				}
			}

			return sql;*/

			return "select * from " + _tableName;
		}
		else {
			return _selectSQL;
		}
	}

	public String getTableName() {
		return _tableName;
	}

	public String getTempFileName() {
		return _tempFileName;
	}

	public long getTotalRows() {
		return _totalRows;
	}

	public Object getValue(ResultSet rs, String name, Integer type)
		throws Exception {

		Object value = null;

		int t = type.intValue();

		if (t == Types.BIGINT) {
			try {
				value = GetterUtil.getLong(rs.getLong(name));
			}
			catch (SQLException sqle) {
				value = GetterUtil.getLong(rs.getString(name));
			}
		}
		else if (t == Types.BIT) {
			value = GetterUtil.getBoolean(rs.getBoolean(name));
		}
		else if ((t == Types.BLOB) || (t == Types.LONGVARBINARY)) {
			value = rs.getBytes(name);

			if (value == null) {
				value = new byte[0];
			}
		}
		else if (t == Types.BOOLEAN) {
			value = GetterUtil.getBoolean(rs.getBoolean(name));
		}
		else if (t == Types.CLOB) {
			try {
				Clob clob = rs.getClob(name);

				if (clob == null) {
					value = StringPool.BLANK;
				}
				else {
					UnsyncBufferedReader unsyncBufferedReader =
						new UnsyncBufferedReader(clob.getCharacterStream());

					StringBundler sb = new StringBundler();

					String line = null;

					while ((line = unsyncBufferedReader.readLine()) != null) {
						if (sb.length() != 0) {
							sb.append(_SAFE_TABLE_NEWLINE_CHARACTER);
						}

						sb.append(line);
					}

					value = sb.toString();
				}
			}
			catch (Exception e) {

				// If the database doesn't allow CLOB types for the column
				// value, then try retrieving it as a String

				value = GetterUtil.getString(rs.getString(name));
			}
		}
		else if (t == Types.DOUBLE) {
			value = GetterUtil.getDouble(rs.getDouble(name));
		}
		else if (t == Types.FLOAT) {
			value = GetterUtil.getFloat(rs.getFloat(name));
		}
		else if (t == Types.INTEGER) {
			value = GetterUtil.getInteger(rs.getInt(name));
		}
		else if (t == Types.LONGVARCHAR) {
			value = GetterUtil.getString(rs.getString(name));
		}
		else if (t == Types.NUMERIC) {
			value = GetterUtil.getLong(rs.getLong(name));
		}
		else if (t == Types.SMALLINT) {
			value = GetterUtil.getShort(rs.getShort(name));
		}
		else if (t == Types.TIMESTAMP) {
			try {
				value = rs.getTimestamp(name);
			}
			catch (Exception e) {
			}

			if (value == null) {
				value = StringPool.NULL;
			}
		}
		else if (t == Types.TINYINT) {
			value = GetterUtil.getShort(rs.getShort(name));
		}
		else if (t == Types.VARCHAR) {
			value = GetterUtil.getString(rs.getString(name));
		}
		else {
			throw new UpgradeException(
				"Upgrade code using unsupported class type " + type);
		}

		return value;
	}

	public void populateTable() throws Exception {
		Connection con = DataAccess.getUpgradeOptimizedConnection();

		try {
			populateTable(con);
		}
		finally {
			DataAccess.cleanUp(con);
		}
	}

	public void populateTable(Connection con) throws Exception {
		if (_tempFileName == null) {
			return;
		}

		PreparedStatement ps = null;

		String insertSQL = getInsertSQL();

		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new FileReader(_tempFileName));

		String line = null;

		try {
			DatabaseMetaData databaseMetaData = con.getMetaData();

			if (!databaseMetaData.supportsBatchUpdates()) {
				if (_log.isDebugEnabled()) {
					_log.debug("Database does not support batch updates");
				}
			}

			int count = 0;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				String[] values = StringUtil.split(line);

				Object[][] columns = getColumns();

				if (values.length != columns.length) {
					throw new UpgradeException(
						"Column lengths differ between temp file and schema. " +
							"Attempted to insert row " + line + ".");
				}

				if (count == 0) {
					ps = con.prepareStatement(insertSQL);
				}

				int[] order = getOrder();

				for (int i = 0; i < order.length; i++) {
					int pos = order[i];

					setColumn(ps, i, (Integer)columns[pos][1], values[pos]);
				}

				if (databaseMetaData.supportsBatchUpdates()) {
					ps.addBatch();

					if (count == _BATCH_SIZE) {
						populateTableRows(ps, true);

						count = 0;
					}
					else {
						count++;
					}
				}
				else {
					populateTableRows(ps, false);
				}
			}

			if (databaseMetaData.supportsBatchUpdates()) {
				if (count != 0) {
					populateTableRows(ps, true);
				}
			}
		}
		finally {
			DataAccess.cleanUp(null, ps);

			unsyncBufferedReader.close();
		}

		if (_log.isDebugEnabled()) {
			_log.debug(getTableName() + " table populated with data");
		}
	}

	public void populateTableRows(PreparedStatement ps, boolean batch)
		throws Exception {

		if (_log.isDebugEnabled()) {
			_log.debug("Updating rows for " + getTableName());
		}

		if (batch) {
			ps.executeBatch();
		}
		else {
			ps.executeUpdate();
		}

		ps.close();
	}

	public void setColumn(
			PreparedStatement ps, int index, Integer type, String value)
		throws Exception {

		int t = type.intValue();

		int paramIndex = index + 1;

		if (t == Types.BIGINT) {
			ps.setLong(paramIndex, GetterUtil.getLong(value));
		}
		else if ((t == Types.BLOB) || (t == Types.LONGVARBINARY)) {
			ps.setBytes(paramIndex, Base64.decode(value));
		}
		else if (t == Types.BOOLEAN) {
			ps.setBoolean(paramIndex, GetterUtil.getBoolean(value));
		}
		else if ((t == Types.CLOB) || (t == Types.LONGVARCHAR) ||
				 (t == Types.VARCHAR)) {

			value = StringUtil.replace(
				value, _SAFE_TABLE_CHARS[1], _SAFE_TABLE_CHARS[0]);

			ps.setString(paramIndex, value);
		}
		else if (t == Types.DOUBLE) {
			ps.setDouble(paramIndex, GetterUtil.getDouble(value));
		}
		else if (t == Types.FLOAT) {
			ps.setFloat(paramIndex, GetterUtil.getFloat(value));
		}
		else if (t == Types.INTEGER) {
			ps.setInt(paramIndex, GetterUtil.getInteger(value));
		}
		else if (t == Types.SMALLINT) {
			ps.setShort(paramIndex, GetterUtil.getShort(value));
		}
		else if (t == Types.TIMESTAMP) {
			if (StringPool.NULL.equals(value)) {
				ps.setTimestamp(paramIndex, null);
			}
			else {
				DateFormat df = DateUtil.getISOFormat();

				ps.setTimestamp(
					paramIndex, new Timestamp(df.parse(value).getTime()));
			}
		}
		else if (t == Types.TINYINT) {
			ps.setShort(paramIndex, GetterUtil.getShort(value));
		}
		else {
			throw new UpgradeException(
				"Upgrade code using unsupported class type " + type);
		}
	}

	public void setColumns(Object[][] columns) {
		_columns = columns;

		// LEP-7331

		_order = new int[_columns.length];

		int clobCount = 0;

		for (int i = 0; i < _columns.length; ++i) {
			Integer type = (Integer)columns[i][1];

			if (type.intValue() == Types.CLOB) {
				clobCount++;

				int pos = _columns.length - clobCount;

				_order[pos] = i;
			}
			else {
				int pos = i - clobCount;

				_order[pos] = i;
			}
		}
	}

	public void setCreateSQL(String createSQL) throws Exception {
		_createSQL = createSQL;
	}

	public void setSelectSQL(String selectSQL) throws Exception {
		_selectSQL = selectSQL;
	}

	public void updateColumnValue(
		String columnName, String oldValue, String newValue) {

		Connection con = null;
		PreparedStatement ps = null;

		StringBundler sb = new StringBundler(7);

		sb.append("update ");
		sb.append(_tableName);
		sb.append(" set ");
		sb.append(columnName);
		sb.append(" = ? where ");
		sb.append(columnName);
		sb.append(" = ?");

		String sql = sb.toString();

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(sql);

			ps.setString(1, newValue);
			ps.setString(2, oldValue);

			ps.executeUpdate();
		}
		catch (SQLException sqle) {
			if (_log.isErrorEnabled()) {
				_log.error(sqle, sqle);
			}

			throw new RuntimeException("Unable to execute " + sql, sqle);
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	private static final int _BATCH_SIZE = GetterUtil.getInteger(
		PropsUtil.get("hibernate.jdbc.batch_size"));

	private static final String[][] _SAFE_TABLE_CHARS = {
		{StringPool.COMMA, StringPool.NEW_LINE, StringPool.RETURN},
		{
			Table._SAFE_TABLE_COMMA_CHARACTER,
			Table._SAFE_TABLE_NEWLINE_CHARACTER,
			Table._SAFE_TABLE_RETURN_CHARACTER
		}
	};

	private static final String _SAFE_TABLE_COMMA_CHARACTER =
		"_SAFE_TABLE_COMMA_CHARACTER_";

	private static final String _SAFE_TABLE_NEWLINE_CHARACTER =
		"_SAFE_TABLE_NEWLINE_CHARACTER_";

	private static final String _SAFE_TABLE_RETURN_CHARACTER =
		"_SAFE_TABLE_RETURN_CHARACTER_";

	private static final Log _log = LogFactoryUtil.getLog(Table.class);

	private Object[][] _columns;
	private String _createSQL;
	private int[] _order;
	private String _selectSQL;
	private final String _tableName;
	private String _tempFileName;
	private long _totalRows;

}
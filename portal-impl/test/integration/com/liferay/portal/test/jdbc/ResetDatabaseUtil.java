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

package com.liferay.portal.test.jdbc;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.upgrade.util.Table;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.parser.JSqlParser;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.update.Update;
import net.sf.jsqlparser.util.TablesNamesFinder;

/**
 * @author Shuyang Zhou
 */
public class ResetDatabaseUtil {

	public static void processSQL(Connection connection, String sql)
		throws Exception {

		if (!_recording) {
			return;
		}

		List<String> tableNames = _getModifiedTableNames(sql);

		if (tableNames == null) {
			return;
		}

		for (String tableName : tableNames) {
			Table table = _buildTable(connection, tableName);

			if (_modifiedTables.putIfAbsent(tableName, table) ==
					null) {

				table.generateTempFile(connection);
			}
		}
	}

	public static void resetModifiedTables() {
		Connection connection = null;

		try {
			connection = DataAccess.getUpgradeOptimizedConnection();

			for (Table table : _modifiedTables.values()) {
				DB db = DBFactoryUtil.getDB();

				db.runSQL(connection, table.getDeleteSQL());

				table.populateTable();

				String tempFileName = table.getTempFileName();

				if (tempFileName != null) {
					FileUtil.delete(tempFileName);
				}
			}
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
		finally {
			DataAccess.cleanUp(connection);

			_modifiedTables.clear();

			_recording = false;
		}
	}

	public static void startRecording() {
		_recording = true;
	}

	private static Table _buildTable(Connection connection, String tableName) {
		try {
			DatabaseMetaData databaseMetaData = connection.getMetaData();

			ResultSet tableResultSet = databaseMetaData.getTables(
				null, null, tableName, null);

			try {
				if (!tableResultSet.next()) {
					throw new IllegalStateException(
						"Unable to access table " + tableName);
				}
			}
			finally {
				DataAccess.cleanUp(tableResultSet);
			}

			ResultSet columnResultSet = databaseMetaData.getColumns(
				null, null, tableName, null);

			List<Object[]> columns = new ArrayList<Object[]>();

			try {
				while (columnResultSet.next()) {
					columns.add(
						new Object[] {
							columnResultSet.getString("COLUMN_NAME"),
							columnResultSet.getInt("DATA_TYPE")});
				}
			}
			finally {
				DataAccess.cleanUp(columnResultSet);
			}

			return new Table(
				tableName, columns.toArray(new Object[columns.size()][]));
		}
		catch (SQLException sqle) {
			throw new RuntimeException(sqle);
		}
	}

	private static List<String> _getModifiedTableNames(String sql) {
		Statement statement = null;

		try {
			statement = _jSqlParser.parse(new UnsyncStringReader(sql));
		}
		catch (Exception e) {
			return null;
		}

		if (statement instanceof Delete) {
			Delete delete = (Delete)statement;

			Expression expression = delete.getWhere();

			if (expression == null) {

				// Workaround for
				// https://github.com/JSQLParser/JSqlParser/pull/55

				net.sf.jsqlparser.schema.Table table = delete.getTable();

				return Arrays.asList(table.getName());
			}

			TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();

			return tablesNamesFinder.getTableList(delete);
		}

		if (statement instanceof Insert) {
			Insert insert = (Insert)statement;

			TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();

			return tablesNamesFinder.getTableList(insert);
		}

		if (statement instanceof Update) {
			Update update = (Update)statement;

			TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();

			return tablesNamesFinder.getTableList(update);
		}

		return null;
	}

	private static JSqlParser _jSqlParser = new CCJSqlParserManager();
	private static ConcurrentMap<String, Table> _modifiedTables =
		new ConcurrentHashMap<String, Table>();
	private static volatile boolean _recording;

}
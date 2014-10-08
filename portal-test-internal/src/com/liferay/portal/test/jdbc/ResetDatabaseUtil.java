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

import com.liferay.portal.kernel.concurrent.ConcurrentHashSet;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.upgrade.util.Table;

import java.io.File;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
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

	public static synchronized boolean initialize() {
		if (_initialized) {
			reloadDatabase();

			return false;
		}

		dumpDatabase();

		_initialized = true;

		return true;
	}

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
			tableName = StringUtil.toLowerCase(tableName);

			Table table = _cachedTables.get(tableName);

			if (table == null) {
				_log.error(
					"Unable to get table " + tableName + " from cache " +
						_cachedTables.keySet());

				continue;
			}

			if (_modifiedTables.putIfAbsent(tableName, table) ==
					null) {

				table.generateTempFile(connection);
			}
		}
	}

	public static void resetModifiedTables() {
		_recording = false;

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
		}
	}

	public static void startRecording() {
		_recording = true;
	}

	protected static void dumpDatabase() {
		Connection connection = null;
		ResultSet tableResultSet = null;

		try {
			connection = DataAccess.getUpgradeOptimizedConnection();

			DatabaseMetaData databaseMetaData = connection.getMetaData();

			tableResultSet = databaseMetaData.getTables(null, null, null, null);

			while (tableResultSet.next()) {
				String tableName = tableResultSet.getString("TABLE_NAME");

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

				Table table = new Table(
					tableName, columns.toArray(new Object[columns.size()][]));

				table.generateTempFile(connection);

				String tempFileName = table.getTempFileName();

				if (tempFileName != null) {
					File tempFile = new File(tempFileName);

					tempFile.deleteOnExit();
				}

				_tables.add(table);

				_cachedTables.put(
					StringUtil.toLowerCase(tableName),
					new Table(
						tableName,
						columns.toArray(new Object[columns.size()][])));
			}
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
		finally {
			DataAccess.cleanUp(connection, null, tableResultSet);
		}
	}

	protected static void reloadDatabase() {
		Connection connection = null;

		try {
			connection = DataAccess.getUpgradeOptimizedConnection();

			for (Table table : _tables) {
				DB db = DBFactoryUtil.getDB();

				db.runSQL(connection, table.getDeleteSQL());

				table.populateTable();
			}
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
		finally {
			DataAccess.cleanUp(connection);
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

	private static final Log _log = LogFactoryUtil.getLog(
		ResetDatabaseUtil.class);

	private static final ConcurrentMap<String, Table> _cachedTables =
		new ConcurrentHashMap<String, Table>();
	private static boolean _initialized;
	private static final JSqlParser _jSqlParser = new CCJSqlParserManager();
	private static final ConcurrentMap<String, Table> _modifiedTables =
		new ConcurrentHashMap<String, Table>();
	private static volatile boolean _recording;
	private static final Set<Table> _tables = new ConcurrentHashSet<Table>();

}
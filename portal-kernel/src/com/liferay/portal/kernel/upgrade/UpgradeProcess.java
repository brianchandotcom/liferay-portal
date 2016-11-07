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

package com.liferay.portal.kernel.upgrade;

import com.liferay.portal.kernel.dao.db.BaseDBProcess;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBProcessContext;
import com.liferay.portal.kernel.dao.db.IndexMetadata;
import com.liferay.portal.kernel.dao.db.IndexMetadataFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.util.UpgradeColumn;
import com.liferay.portal.kernel.upgrade.util.UpgradeTable;
import com.liferay.portal.kernel.upgrade.util.UpgradeTableFactoryUtil;
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import java.lang.reflect.Field;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 */
public abstract class UpgradeProcess
	extends BaseDBProcess implements UpgradeStep {

	public void clearIndexesCache() {
		_portalIndexesSQL.clear();
	}

	public int getThreshold() {

		// This upgrade process will only run if the build number is larger than
		// the returned threshold value. Return 0 to always run this upgrade
		// process.

		return 0;
	}

	public void upgrade() throws UpgradeException {
		long start = System.currentTimeMillis();

		if (_log.isInfoEnabled()) {
			_log.info("Upgrading " + ClassUtil.getClassName(this));
		}

		try (Connection con = DataAccess.getUpgradeOptimizedConnection()) {
			connection = con;

			doUpgrade();
		}
		catch (Exception e) {
			throw new UpgradeException(e);
		}
		finally {
			connection = null;

			if (_log.isInfoEnabled()) {
				_log.info(
					"Completed upgrade process " +
						ClassUtil.getClassName(this) + " in " +
							(System.currentTimeMillis() - start) + "ms");
			}
		}
	}

	public void upgrade(Class<?> upgradeProcessClass) throws UpgradeException {
		UpgradeProcess upgradeProcess = null;

		try {
			upgradeProcess = (UpgradeProcess)upgradeProcessClass.newInstance();
		}
		catch (Exception e) {
			throw new UpgradeException(e);
		}

		upgradeProcess.upgrade();
	}

	@Override
	public void upgrade(DBProcessContext dbProcessContext)
		throws UpgradeException {

		upgrade();
	}

	public void upgrade(UpgradeProcess upgradeProcess) throws UpgradeException {
		upgradeProcess.upgrade();
	}

	public interface Alterable {

		public static boolean containsIgnoreCase(
			Collection<String> columnNames, String columnName) {

			for (String curColumnName : columnNames) {
				if (StringUtil.equalsIgnoreCase(curColumnName, columnName)) {
					return true;
				}
			}

			return false;
		}

		/**
		 * @deprecated As of 7.0.0, with no direct replacement
		 */
		@Deprecated
		public String getIndexedColumnName();

		public String getSQL(String tableName);

		public boolean shouldAddIndex(Collection<String> columnNames);

		public boolean shouldDropIndex(Collection<String> columnNames);

	}

	public class AlterColumnName implements Alterable {

		public AlterColumnName(String oldColumnName, String newColumn) {
			_oldColumnName = oldColumnName;
			_newColumn = newColumn;

			String newColumnName = StringUtil.extractFirst(
				newColumn, StringPool.SPACE);

			if (newColumnName != null) {
				_newColumnName = newColumnName;
			}
			else {
				_newColumnName = _newColumn;
			}
		}

		/**
		 * @deprecated As of 7.0.0, with no direct replacement
		 */
		@Deprecated
		@Override
		public String getIndexedColumnName() {
			return null;
		}

		@Override
		public String getSQL(String tableName) {
			StringBundler sb = new StringBundler(6);

			sb.append("alter_column_name ");
			sb.append(tableName);
			sb.append(StringPool.SPACE);
			sb.append(_oldColumnName);
			sb.append(StringPool.SPACE);
			sb.append(_newColumn);

			return sb.toString();
		}

		@Override
		public boolean shouldAddIndex(Collection<String> columnNames) {
			return Alterable.containsIgnoreCase(columnNames, _newColumnName);
		}

		@Override
		public boolean shouldDropIndex(Collection<String> columnNames) {
			return Alterable.containsIgnoreCase(columnNames, _oldColumnName);
		}

		private final String _newColumn;
		private final String _newColumnName;
		private final String _oldColumnName;

	}

	public class AlterColumnType implements Alterable {

		public AlterColumnType(String columnName, String newType) {
			_columnName = columnName;
			_newType = newType;
		}

		/**
		 * @deprecated As of 7.0.0, with no direct replacement
		 */
		@Deprecated
		@Override
		public String getIndexedColumnName() {
			return null;
		}

		@Override
		public String getSQL(String tableName) {
			StringBundler sb = new StringBundler(6);

			sb.append("alter_column_type ");
			sb.append(tableName);
			sb.append(StringPool.SPACE);
			sb.append(_columnName);
			sb.append(StringPool.SPACE);
			sb.append(_newType);

			return sb.toString();
		}

		@Override
		public boolean shouldAddIndex(Collection<String> columnNames) {
			return Alterable.containsIgnoreCase(columnNames, _columnName);
		}

		@Override
		public boolean shouldDropIndex(Collection<String> columnNames) {
			return Alterable.containsIgnoreCase(columnNames, _columnName);
		}

		private final String _columnName;
		private final String _newType;

	}

	public class AlterTableAddColumn implements Alterable {

		public AlterTableAddColumn(String columnName) {
			_columnName = columnName;
		}

		/**
		 * @deprecated As of 7.0.0, with no direct replacement
		 */
		@Deprecated
		@Override
		public String getIndexedColumnName() {
			return null;
		}

		@Override
		public String getSQL(String tableName) {
			StringBundler sb = new StringBundler(4);

			sb.append("alter table ");
			sb.append(tableName);
			sb.append(" add ");
			sb.append(_columnName);

			return sb.toString();
		}

		@Override
		public boolean shouldAddIndex(Collection<String> columnNames) {
			return Alterable.containsIgnoreCase(columnNames, _columnName);
		}

		@Override
		public boolean shouldDropIndex(Collection<String> columnNames) {
			return false;
		}

		private final String _columnName;

	}

	public class AlterTableDropColumn implements Alterable {

		public AlterTableDropColumn(String columnName) {
			_columnName = columnName;
		}

		/**
		 * @deprecated As of 7.0.0, with no direct replacement
		 */
		@Deprecated
		@Override
		public String getIndexedColumnName() {
			return null;
		}

		@Override
		public String getSQL(String tableName) {
			StringBundler sb = new StringBundler(4);

			sb.append("alter table ");
			sb.append(tableName);
			sb.append(" drop column ");
			sb.append(_columnName);

			return sb.toString();
		}

		@Override
		public boolean shouldAddIndex(Collection<String> columnNames) {
			return false;
		}

		@Override
		public boolean shouldDropIndex(Collection<String> columnNames) {
			return Alterable.containsIgnoreCase(columnNames, _columnName);
		}

		private final String _columnName;

	}

	protected void alter(Class<?> tableClass, Alterable... alterables)
		throws Exception {

		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			Field tableNameField = tableClass.getField("TABLE_NAME");

			String tableName = (String)tableNameField.get(null);

			DatabaseMetaData databaseMetaData = connection.getMetaData();

			try (ResultSet rs1 = databaseMetaData.getPrimaryKeys(
					null, null, tableName);
				ResultSet rs2 = databaseMetaData.getIndexInfo(
					null, null, normalizeName(tableName, databaseMetaData),
					false, false)) {

				Set<String> primaryKeyNames = new HashSet<>();

				while (rs1.next()) {
					String primaryKeyName = StringUtil.toUpperCase(
						rs1.getString("PK_NAME"));

					if (primaryKeyName != null) {
						primaryKeyNames.add(primaryKeyName);
					}
				}

				Map<String, Set<String>> columnNamesMap = new HashMap<>();

				while (rs2.next()) {
					String indexName = StringUtil.toUpperCase(
						rs2.getString("INDEX_NAME"));

					if ((indexName == null) ||
						primaryKeyNames.contains(indexName)) {

						continue;
					}

					Set<String> columnNames = columnNamesMap.get(indexName);

					if (columnNames == null) {
						columnNames = new HashSet<>();

						columnNamesMap.put(indexName, columnNames);
					}

					columnNames.add(
						StringUtil.toUpperCase(rs2.getString("COLUMN_NAME")));
				}

				for (Alterable alterable : alterables) {
					for (Map.Entry<String, Set<String>> entry :
							columnNamesMap.entrySet()) {

						if (alterable.shouldDropIndex(entry.getValue())) {
							runSQL(
								"drop index " + entry.getKey() + " on " +
									tableName);
						}
					}

					runSQL(alterable.getSQL(tableName));

					List<ObjectValuePair<String, IndexMetadata>>
						objectValuePairs = getIndexesSQL(
							tableClass.getClassLoader(), tableName);

					if (objectValuePairs == null) {
						continue;
					}

					for (ObjectValuePair<String, IndexMetadata>
							objectValuePair : objectValuePairs) {

						IndexMetadata indexMetadata =
							objectValuePair.getValue();

						if (alterable.shouldAddIndex(
								Arrays.asList(
									indexMetadata.getColumnNames()))) {

							runSQLTemplateString(
								objectValuePair.getKey(), false, true);
						}
					}
				}
			}
			catch (SQLException sqle) {
				if (_log.isWarnEnabled()) {
					_log.warn("Fallback to recreating the table", sqle);
				}

				Field tableColumnsField = tableClass.getField("TABLE_COLUMNS");
				Field tableSQLCreateField = tableClass.getField(
					"TABLE_SQL_CREATE");
				Field tableSQLAddIndexesField = tableClass.getField(
					"TABLE_SQL_ADD_INDEXES");

				upgradeTable(
					tableName, (Object[][])tableColumnsField.get(null),
					(String)tableSQLCreateField.get(null),
					(String[])tableSQLAddIndexesField.get(null));
			}
		}
	}

	protected abstract void doUpgrade() throws Exception;

	protected List<ObjectValuePair<String, IndexMetadata>> getIndexesSQL(
			ClassLoader classLoader, String tableName)
		throws IOException {

		if (!PortalClassLoaderUtil.isPortalClassLoader(classLoader)) {
			List<ObjectValuePair<String, IndexMetadata>> objectValuePairs =
				new ArrayList<>();

			try (InputStream is = classLoader.getResourceAsStream(
					"META-INF/sql/indexes.sql");
				Reader reader = new InputStreamReader(is);
				UnsyncBufferedReader unsyncBufferedReader =
					new UnsyncBufferedReader(reader)) {

				String line = null;

				while ((line = unsyncBufferedReader.readLine()) != null) {
					line = line.trim();

					if (line.isEmpty()) {
						continue;
					}

					IndexMetadata indexMetadata =
						IndexMetadataFactoryUtil.createIndexMetadata(line);

					if (tableName.equals(indexMetadata.getTableName())) {
						objectValuePairs.add(
							new ObjectValuePair<>(line, indexMetadata));
					}
				}
			}

			return objectValuePairs;
		}

		if (!_portalIndexesSQL.isEmpty()) {
			return _portalIndexesSQL.get(tableName);
		}

		try (InputStream is = classLoader.getResourceAsStream(
				"com/liferay/portal/tools/sql/dependencies/indexes.sql");
			Reader reader = new InputStreamReader(is);
			UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(reader)) {

			String line = null;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				line = line.trim();

				if (line.isEmpty()) {
					continue;
				}

				IndexMetadata indexMetadata =
					IndexMetadataFactoryUtil.createIndexMetadata(line);

				List<ObjectValuePair<String, IndexMetadata>> objectValuePairs =
					_portalIndexesSQL.get(indexMetadata.getTableName());

				if (objectValuePairs == null) {
					objectValuePairs = new ArrayList<>();

					_portalIndexesSQL.put(
						indexMetadata.getTableName(), objectValuePairs);
				}

				objectValuePairs.add(
					new ObjectValuePair<>(line, indexMetadata));
			}
		}

		return _portalIndexesSQL.get(tableName);
	}

	protected long increment() {
		DB db = DBManagerUtil.getDB();

		return db.increment();
	}

	protected long increment(String name) {
		DB db = DBManagerUtil.getDB();

		return db.increment(name);
	}

	protected long increment(String name, int size) {
		DB db = DBManagerUtil.getDB();

		return db.increment(name, size);
	}

	protected boolean isSupportsAlterColumnName() {
		DB db = DBManagerUtil.getDB();

		return db.isSupportsAlterColumnName();
	}

	protected boolean isSupportsAlterColumnType() {
		DB db = DBManagerUtil.getDB();

		return db.isSupportsAlterColumnType();
	}

	protected boolean isSupportsStringCaseSensitiveQuery() {
		DB db = DBManagerUtil.getDB();

		return db.isSupportsStringCaseSensitiveQuery();
	}

	protected boolean isSupportsUpdateWithInnerJoin() {
		DB db = DBManagerUtil.getDB();

		return db.isSupportsUpdateWithInnerJoin();
	}

	protected String normalizeName(
			String name, DatabaseMetaData databaseMetaData)
		throws SQLException {

		if (databaseMetaData.storesLowerCaseIdentifiers()) {
			return StringUtil.toLowerCase(name);
		}

		if (databaseMetaData.storesUpperCaseIdentifiers()) {
			return StringUtil.toUpperCase(name);
		}

		return name;
	}

	protected void upgradeTable(String tableName, Object[][] tableColumns)
		throws Exception {

		UpgradeTable upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			tableName, tableColumns);

		upgradeTable.updateTable();
	}

	protected void upgradeTable(
			String tableName, Object[][] tableColumns, String createSQL,
			String[] indexesSQL, UpgradeColumn... upgradeColumns)
		throws Exception {

		try (LoggingTimer loggingTimer = new LoggingTimer(tableName)) {
			UpgradeTable upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
				tableName, tableColumns, upgradeColumns);

			upgradeTable.setCreateSQL(createSQL);
			upgradeTable.setIndexesSQL(indexesSQL);

			upgradeTable.updateTable();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(UpgradeProcess.class);

	private static final Map
		<String, List<ObjectValuePair<String, IndexMetadata>>>
			_portalIndexesSQL = new HashMap<>();

}
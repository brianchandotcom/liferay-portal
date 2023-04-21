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

package com.liferay.portal.dao.db;

import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.dao.orm.common.SQLTransformer;
import com.liferay.portal.db.partition.DBPartitionUtil;
import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.dao.db.Index;
import com.liferay.portal.kernel.dao.db.IndexMetadata;
import com.liferay.portal.kernel.dao.db.IndexMetadataFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;
import java.io.InputStream;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.NamingException;

/**
 * @author Alexander Chow
 * @author Ganesh Ram
 * @author Brian Wing Shun Chan
 * @author Daniel Kocsis
 */
public abstract class BaseDB implements DB {

	@Override
	public void addIndexes(
			Connection connection, List<IndexMetadata> indexMetadatas)
		throws IOException, SQLException {

		DatabaseMetaData databaseMetaData = connection.getMetaData();

		DBInspector dbInspector = new DBInspector(connection);

		Map<String, Map<String, Integer>> columnTableSizes = new HashMap<>();

		for (IndexMetadata indexMetadata : indexMetadatas) {
			String normalizedTabledName = dbInspector.normalizeName(
				indexMetadata.getTableName(), databaseMetaData);

			if (columnTableSizes.get(normalizedTabledName) == null) {
				try (ResultSet resultSet = databaseMetaData.getColumns(
						dbInspector.getCatalog(), dbInspector.getSchema(),
						normalizedTabledName, null)) {

					Map<String, Integer> columnSizes = new HashMap<>();

					while (resultSet.next()) {
						int columnType = resultSet.getInt("DATA_TYPE");

						if (!ArrayUtil.contains(
								SQL_VARCHAR_TYPES, columnType)) {

							continue;
						}

						columnSizes.put(
							dbInspector.normalizeName(
								resultSet.getString("COLUMN_NAME"),
								databaseMetaData),
							resultSet.getInt("COLUMN_SIZE"));
					}

					columnTableSizes.put(normalizedTabledName, columnSizes);
				}
			}

			String[] columnNames = indexMetadata.getColumnNames();

			int[] columnSizes = new int[columnNames.length];

			for (int i = 0; i < columnNames.length; i++) {
				columnSizes[i] = MapUtil.getInteger(
					columnTableSizes.get(normalizedTabledName), columnNames[i],
					0);
			}

			runSQL(
				_applyMaxStringIndexLengthLimitation(
					indexMetadata.getCreateSQL(columnSizes)));
		}
	}

	public void alterColumnName(
			Connection connection, String tableName, String oldColumnName,
			String newColumnDefinition)
		throws Exception {

		StringBundler sb = new StringBundler(6);

		sb.append("alter_column_name ");
		sb.append(tableName);
		sb.append(StringPool.SPACE);
		sb.append(oldColumnName);
		sb.append(StringPool.SPACE);
		sb.append(newColumnDefinition);

		runSQL(connection, sb.toString());
	}

	public void alterColumnType(
			Connection connection, String tableName, String columnName,
			String newColumnType)
		throws Exception {

		StringBundler sb = new StringBundler(6);

		sb.append("alter_column_type ");
		sb.append(tableName);
		sb.append(StringPool.SPACE);
		sb.append(columnName);
		sb.append(StringPool.SPACE);
		sb.append(newColumnType);

		runSQL(connection, sb.toString());
	}

	public void alterTableAddColumn(
			Connection connection, String tableName, String columnName,
			String columnType)
		throws Exception {

		StringBundler sb = new StringBundler(6);

		sb.append("alter table ");
		sb.append(tableName);
		sb.append(" add ");
		sb.append(columnName);
		sb.append(StringPool.SPACE);
		sb.append(columnType);

		runSQL(connection, sb.toString());
	}

	public void alterTableDropColumn(
			Connection connection, String tableName, String columnName)
		throws Exception {

		StringBundler sb = new StringBundler(4);

		sb.append("alter table ");
		sb.append(tableName);
		sb.append(" drop column ");
		sb.append(columnName);

		runSQL(connection, sb.toString());
	}

	@Override
	public abstract String buildSQL(String template)
		throws IOException, SQLException;

	@Override
	public void copyTableRows(
			Connection connection, String sourceTableName,
			String targetTableName)
		throws Exception {

		String[] primaryKeyColumnNames = getPrimaryKeyColumnNames(
			connection, sourceTableName);

		StringBundler sb = new StringBundler(
			14 + (((primaryKeyColumnNames.length - 1) * 8) + 7));

		sb.append("insert into ");
		sb.append(targetTableName);
		sb.append(" select ");
		sb.append(sourceTableName);
		sb.append(".* from ");
		sb.append(sourceTableName);
		sb.append(" left join ");
		sb.append(targetTableName);
		sb.append(" on ");

		for (int i = 0; i < primaryKeyColumnNames.length; i++) {
			String primaryKeyColumnName = primaryKeyColumnNames[i];

			sb.append(sourceTableName);
			sb.append(".");
			sb.append(primaryKeyColumnName);
			sb.append(" = ");
			sb.append(targetTableName);
			sb.append(".");
			sb.append(primaryKeyColumnName);

			if (i < (primaryKeyColumnNames.length - 1)) {
				sb.append(" and ");
			}
		}

		sb.append(" where ");
		sb.append(targetTableName);
		sb.append(".");
		sb.append(primaryKeyColumnNames[0]);
		sb.append(" IS NULL");

		runSQL(sb.toString());
	}

	@Override
	public void copyTableStructure(
			Connection connection, String tableName, String newTableName,
			String indexNamePrefix)
		throws Exception {

		runSQL(connection, getCopyTableStructureSQL(tableName, newTableName));

		addPrimaryKey(
			connection, newTableName,
			getPrimaryKeyColumnNames(connection, tableName));

		List<IndexMetadata> indexMetadatas = new ArrayList<>();

		for (IndexMetadata indexMetadata :
				getIndexes(connection, tableName, null, false)) {

			indexMetadatas.add(
				new IndexMetadata(
					indexNamePrefix.concat(indexMetadata.getIndexName()),
					newTableName, indexMetadata.isUnique(),
					indexMetadata.getColumnNames()));
		}

		addIndexes(connection, indexMetadatas);
	}

	@Override
	public List<IndexMetadata> dropIndexes(
			Connection connection, String tableName, String columnName)
		throws IOException, SQLException {

		List<IndexMetadata> indexMetadatas = getIndexes(
			connection, tableName, columnName, false);

		for (IndexMetadata indexMetadata : indexMetadatas) {
			runSQL(connection, indexMetadata.getDropSQL());
		}

		return indexMetadatas;
	}

	@Override
	public String getCopyTableStructureSQL(
		String tableName, String newTableName) {

		return StringBundler.concat(
			"create table ", newTableName, " as select * from ", tableName,
			" where 1 = 0");
	}

	@Override
	public DBType getDBType() {
		return _dbType;
	}

	@Override
	public List<Index> getIndexes(Connection connection) throws SQLException {
		return TransformUtil.transform(
			getIndexes(connection, null, null, false),
			index -> new Index(
				index.getIndexName(), index.getTableName(), index.isUnique()));
	}

	@Override
	public ResultSet getIndexResultSet(Connection connection, String tableName)
		throws SQLException {

		DatabaseMetaData databaseMetaData = connection.getMetaData();

		DBInspector dbInspector = new DBInspector(connection);

		return databaseMetaData.getIndexInfo(
			dbInspector.getCatalog(), dbInspector.getSchema(), tableName, false,
			false);
	}

	@Override
	public int getMajorVersion() {
		return _majorVersion;
	}

	@Override
	public int getMinorVersion() {
		return _minorVersion;
	}

	@Override
	public String[] getPrimaryKeyColumnNames(
			Connection connection, String tableName)
		throws SQLException {

		List<PrimaryKey> primaryKeys = _getPrimaryKeys(connection, tableName);

		String[] primaryKeyColumnNames = new String[primaryKeys.size()];

		for (PrimaryKey primaryKey : primaryKeys) {
			primaryKeyColumnNames[primaryKey._keySeq - 1] =
				primaryKey._columnName;
		}

		return primaryKeyColumnNames;
	}

	@Override
	public Integer getSQLType(String templateType) {
		return _sqlTypes.get(templateType);
	}

	@Override
	public Integer getSQLVarcharSize(String templateType) {
		return _sqlVarcharSizes.get(templateType);
	}

	@Override
	public String getTemplateBlob() {
		return getTemplate()[5];
	}

	@Override
	public String getTemplateFalse() {
		return getTemplate()[2];
	}

	@Override
	public String getTemplateTrue() {
		return getTemplate()[1];
	}

	@Override
	public String getVersionString() {
		return _majorVersion + StringPool.PERIOD + _minorVersion;
	}

	@Override
	public boolean isSupportsAlterColumnName() {
		return _SUPPORTS_ALTER_COLUMN_NAME;
	}

	@Override
	public boolean isSupportsAlterColumnType() {
		return _SUPPORTS_ALTER_COLUMN_TYPE;
	}

	@Override
	public boolean isSupportsInlineDistinct() {
		return _SUPPORTS_INLINE_DISTINCT;
	}

	@Override
	public boolean isSupportsQueryingAfterException() {
		return _SUPPORTS_QUERYING_AFTER_EXCEPTION;
	}

	@Override
	public boolean isSupportsScrollableResults() {
		return _SUPPORTS_SCROLLABLE_RESULTS;
	}

	@Override
	public boolean isSupportsStringCaseSensitiveQuery() {
		return _supportsStringCaseSensitiveQuery;
	}

	@Override
	public boolean isSupportsUpdateWithInnerJoin() {
		return _SUPPORTS_UPDATE_WITH_INNER_JOIN;
	}

	@Override
	public void process(UnsafeConsumer<Long, Exception> unsafeConsumer)
		throws Exception {

		DBPartitionUtil.forEachCompanyId(unsafeConsumer);
	}

	@Override
	public void removePrimaryKey(Connection connection, String tableName)
		throws Exception {

		DatabaseMetaData databaseMetaData = connection.getMetaData();

		DBInspector dbInspector = new DBInspector(connection);

		String normalizedTableName = dbInspector.normalizeName(
			tableName, databaseMetaData);

		runSQL(
			StringBundler.concat(
				"alter table ", normalizedTableName, " drop primary key"));
	}

	@Override
	public void runSQL(Connection connection, String sql)
		throws IOException, SQLException {

		runSQL(connection, new String[] {sql});
	}

	@Override
	public void runSQL(Connection connection, String[] sqls)
		throws IOException, SQLException {

		try (Statement s = connection.createStatement()) {
			for (String sql : sqls) {
				sql = buildSQL(sql);

				if (Validator.isNull(sql)) {
					continue;
				}

				sql = SQLTransformer.transform(sql.trim());

				if (sql.endsWith(";")) {
					sql = sql.substring(0, sql.length() - 1);
				}

				if (sql.endsWith("\ngo")) {
					sql = sql.substring(0, sql.length() - 3);
				}

				if (sql.endsWith("\n/")) {
					sql = sql.substring(0, sql.length() - 2);
				}

				if (_log.isDebugEnabled()) {
					_log.debug(sql);
				}

				try {
					s.executeUpdate(sql);
				}
				catch (SQLException sqlException) {
					if (_log.isDebugEnabled()) {
						_log.debug(
							StringBundler.concat(
								"SQL: ", sql, "\nSQL state: ",
								sqlException.getSQLState(), "\nVendor: ",
								getDBType(), "\nVendor error code: ",
								sqlException.getErrorCode(),
								"\nVendor error message: ",
								sqlException.getMessage()));
					}

					throw sqlException;
				}
			}
		}
	}

	@Override
	public void runSQL(String sql) throws IOException, SQLException {
		runSQL(new String[] {sql});
	}

	@Override
	public void runSQL(String[] sqls) throws IOException, SQLException {
		try (Connection connection = DataAccess.getConnection()) {
			runSQL(connection, sqls);
		}
	}

	@Override
	public void runSQLTemplateString(
			Connection connection, String template, boolean failOnError)
		throws IOException, NamingException, SQLException {

		template = StringUtil.trim(template);

		if ((template == null) || template.isEmpty()) {
			return;
		}

		if (!template.endsWith(StringPool.SEMICOLON)) {
			template += StringPool.SEMICOLON;
		}

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(template))) {

			StringBundler sb = new StringBundler();

			String line = null;

			Thread currentThread = Thread.currentThread();

			ClassLoader classLoader = currentThread.getContextClassLoader();

			while ((line = unsyncBufferedReader.readLine()) != null) {
				if (line.isEmpty() || line.startsWith("##")) {
					continue;
				}

				if (line.startsWith("@include ")) {
					int pos = line.indexOf(" ");

					int end = line.length();

					if (StringUtil.endsWith(line, StringPool.SEMICOLON)) {
						end -= 1;
					}

					String includeFileName = line.substring(pos + 1, end);

					InputStream inputStream = classLoader.getResourceAsStream(
						"com/liferay/portal/tools/sql/dependencies/" +
							includeFileName);

					if (inputStream == null) {
						inputStream = classLoader.getResourceAsStream(
							includeFileName);
					}

					String include = StringUtil.read(inputStream);

					include = replaceTemplate(include);

					runSQLTemplateString(include, true);
				}
				else {
					sb.append(line);
					sb.append(StringPool.NEW_LINE);

					if (line.endsWith(";")) {
						String sql = sb.toString();

						sb.setIndex(0);

						try {
							if (!sql.equals("COMMIT_TRANSACTION;\n")) {
								runSQL(connection, sql);
							}
							else {
								if (_log.isDebugEnabled()) {
									_log.debug("Skip commit sql");
								}
							}
						}
						catch (IOException ioException) {
							if (failOnError) {
								throw ioException;
							}
							else if (_log.isWarnEnabled()) {
								_log.warn(ioException);
							}
						}
						catch (SecurityException securityException) {
							if (failOnError) {
								throw securityException;
							}
							else if (_log.isWarnEnabled()) {
								_log.warn(securityException);
							}
						}
						catch (SQLException sqlException) {
							if (failOnError) {
								throw sqlException;
							}

							String message = GetterUtil.getString(
								sqlException.getMessage());

							if (!message.startsWith("Duplicate key name") &&
								_log.isWarnEnabled()) {

								_log.warn(message + ": " + buildSQL(sql));
							}

							if (message.startsWith("Duplicate entry") ||
								message.startsWith(
									"Specified key was too long")) {

								_log.error(line);
							}
						}
					}
				}
			}
		}
	}

	@Override
	public void runSQLTemplateString(String template, boolean failOnError)
		throws IOException, NamingException, SQLException {

		try (Connection connection = DataAccess.getConnection()) {
			runSQLTemplateString(connection, template, failOnError);
		}
	}

	@Override
	public void setSupportsStringCaseSensitiveQuery(
		boolean supportsStringCaseSensitiveQuery) {

		if (_log.isDebugEnabled()) {
			if (supportsStringCaseSensitiveQuery) {
				_log.debug("Database supports case sensitive queries");
			}
			else {
				_log.debug("Database does not support case sensitive queries");
			}
		}

		_supportsStringCaseSensitiveQuery = supportsStringCaseSensitiveQuery;

		SQLTransformer.reloadSQLTransformer();
	}

	@Override
	public void updateIndexes(
			Connection connection, String tablesSQL, String indexesSQL,
			boolean dropIndexes)
		throws Exception {

		List<Index> indexes = getIndexes(connection);

		Set<String> validIndexNames = null;

		if (dropIndexes) {
			validIndexNames = dropIndexes(
				connection, tablesSQL, indexesSQL, indexes);
		}
		else {
			validIndexNames = new HashSet<>();

			for (Index index : indexes) {
				String indexName = StringUtil.toUpperCase(index.getIndexName());

				validIndexNames.add(indexName);
			}
		}

		_addIndexes(
			connection, _applyMaxStringIndexLengthLimitation(indexesSQL),
			validIndexNames);
	}

	protected BaseDB(DBType dbType, int majorVersion, int minorVersion) {
		_dbType = dbType;
		_majorVersion = majorVersion;
		_minorVersion = minorVersion;

		String[] actual = getTemplate();

		for (int i = 0; i < TEMPLATE.length; i++) {
			_templates.put(TEMPLATE[i], actual[i]);
		}

		String[] templateTypes = ArrayUtil.clone(TEMPLATE, 5, 15);

		for (int i = 0; i < templateTypes.length; i++) {
			_sqlTypes.put(StringUtil.trim(templateTypes[i]), getSQLTypes()[i]);
		}

		String[] sqlTypeStringAndText = ArrayUtil.clone(TEMPLATE, 12, 14);

		for (int i = 0; i < sqlTypeStringAndText.length; i++) {
			_sqlVarcharSizes.put(
				StringUtil.trim(sqlTypeStringAndText[i]),
				getSQLVarcharSizes()[i]);
		}
	}

	protected void addPrimaryKey(
			Connection connection, String tableName, String[] columnNames)
		throws IOException, SQLException {

		DatabaseMetaData databaseMetaData = connection.getMetaData();

		DBInspector dbInspector = new DBInspector(connection);

		StringBundler sb = new StringBundler();

		sb.append("alter table ");
		sb.append(dbInspector.normalizeName(tableName, databaseMetaData));
		sb.append(" add primary key (");

		for (String columnName : columnNames) {
			sb.append(columnName);
			sb.append(", ");
		}

		sb.setIndex(sb.index() - 1);

		sb.append(")");

		runSQL(sb.toString());
	}

	protected String[] buildColumnNameTokens(String line) {
		String[] words = StringUtil.split(line, CharPool.SPACE);

		String nullable = "";

		if (words.length == 7) {
			nullable = "not null;";
		}

		return new String[] {words[1], words[2], words[3], words[4], nullable};
	}

	protected String[] buildColumnTypeTokens(String line) {
		String[] words = StringUtil.split(line, CharPool.SPACE);

		String nullable = "";

		if (words.length == 6) {
			nullable = "not null";
		}
		else if (words.length == 5) {
			nullable = "null";
		}
		else if (words.length == 4) {
			if (words[3].endsWith(";")) {
				words[3] = words[3].substring(0, words[3].length() - 1);
			}
		}

		return new String[] {words[1], words[2], "", words[3], nullable};
	}

	protected String[] buildTableNameTokens(String line) {
		String[] words = StringUtil.split(line, CharPool.SPACE);

		return new String[] {words[1], words[2]};
	}

	protected Set<String> dropIndexes(
			Connection connection, String tablesSQL, String indexesSQL,
			List<Index> indexes)
		throws IOException, SQLException {

		if (_log.isInfoEnabled()) {
			_log.info("Dropping stale indexes");
		}

		Set<String> validIndexNames = new HashSet<>();

		if (indexes.isEmpty()) {
			return validIndexNames;
		}

		String tablesSQLLowerCase = StringUtil.toLowerCase(tablesSQL);
		String indexesSQLLowerCase = StringUtil.toLowerCase(indexesSQL);

		String[] lines = StringUtil.splitLines(indexesSQL);

		Set<String> indexNames = new HashSet<>();

		for (String line : lines) {
			if (Validator.isNull(line)) {
				continue;
			}

			IndexMetadata indexMetadata =
				IndexMetadataFactoryUtil.createIndexMetadata(line);

			indexNames.add(
				StringUtil.toLowerCase(indexMetadata.getIndexName()));
		}

		for (Index index : indexes) {
			String indexNameUpperCase = StringUtil.toUpperCase(
				index.getIndexName());

			String indexNameLowerCase = StringUtil.toLowerCase(
				indexNameUpperCase);

			String tableName = index.getTableName();

			String tableNameLowerCase = StringUtil.toLowerCase(tableName);

			validIndexNames.add(indexNameUpperCase);

			if (indexNames.contains(indexNameLowerCase)) {
				boolean unique = index.isUnique();

				if (unique &&
					indexesSQLLowerCase.contains(
						"create unique index " + indexNameLowerCase + " ")) {

					continue;
				}

				if (!unique &&
					indexesSQLLowerCase.contains(
						"create index " + indexNameLowerCase + " ")) {

					continue;
				}
			}
			else if (!tablesSQLLowerCase.contains(
						CREATE_TABLE + tableNameLowerCase + " (")) {

				continue;
			}

			validIndexNames.remove(indexNameUpperCase);

			String sql = StringBundler.concat(
				"drop index ", indexNameUpperCase, " on ", tableName);

			if (_log.isInfoEnabled()) {
				_log.info(sql);
			}

			runSQL(connection, sql);
		}

		return validIndexNames;
	}

	protected List<IndexMetadata> getIndexes(
			Connection connection, String tableName, String columnName,
			boolean onlyUnique)
		throws SQLException {

		List<IndexMetadata> indexMetadatas = new ArrayList<>();

		DatabaseMetaData databaseMetaData = connection.getMetaData();

		DBInspector dbInspector = new DBInspector(connection);

		String catalog = dbInspector.getCatalog();
		String schema = dbInspector.getSchema();

		String normalizedTableName = tableName;

		if (normalizedTableName != null) {
			normalizedTableName = dbInspector.normalizeName(
				tableName, databaseMetaData);
		}

		String normalizedColumnName = columnName;

		if (normalizedColumnName != null) {
			normalizedColumnName = dbInspector.normalizeName(
				columnName, databaseMetaData);
		}

		try (ResultSet tableResultSet = databaseMetaData.getTables(
				catalog, schema, normalizedTableName, new String[] {"TABLE"})) {

			while (tableResultSet.next()) {
				normalizedTableName = dbInspector.normalizeName(
					tableResultSet.getString("TABLE_NAME"), databaseMetaData);

				try (ResultSet indexResultSet = databaseMetaData.getIndexInfo(
						catalog, schema, normalizedTableName, onlyUnique,
						false)) {

					boolean unique = false;

					String[] columnNames = new String[0];
					String previousIndexName = null;

					while (indexResultSet.next()) {
						String indexName = indexResultSet.getString(
							"INDEX_NAME");

						if (indexName == null) {
							continue;
						}

						String lowerCaseIndexName = StringUtil.toLowerCase(
							indexName);

						if (!lowerCaseIndexName.startsWith("liferay_") &&
							!lowerCaseIndexName.startsWith("ix_")) {

							continue;
						}

						if ((previousIndexName != null) &&
							!previousIndexName.equals(indexName)) {

							if ((normalizedColumnName == null) ||
								ArrayUtil.contains(
									columnNames, normalizedColumnName)) {

								indexMetadatas.add(
									new IndexMetadata(
										previousIndexName, normalizedTableName,
										unique, columnNames));
							}

							columnNames = new String[0];
						}

						previousIndexName = indexName;

						unique = !indexResultSet.getBoolean("NON_UNIQUE");

						columnNames = ArrayUtil.append(
							columnNames,
							dbInspector.normalizeName(
								indexResultSet.getString("COLUMN_NAME"),
								databaseMetaData));
					}

					if ((previousIndexName != null) &&
						((normalizedColumnName == null) ||
						 ArrayUtil.contains(
							 columnNames, normalizedColumnName))) {

						indexMetadatas.add(
							new IndexMetadata(
								previousIndexName, normalizedTableName, unique,
								columnNames));
					}
				}
			}
		}

		return new ArrayList<>(indexMetadatas);
	}

	protected abstract int[] getSQLTypes();

	protected int[] getSQLVarcharSizes() {
		return new int[] {-1, -1};
	}

	protected abstract String[] getTemplate();

	protected String limitColumnLength(String column, int length) {
		return StringBundler.concat(column, "\\(", length, "\\)");
	}

	protected String replaceTemplate(String template) {
		if (Validator.isNull(template)) {
			return null;
		}

		StringBundler sb = null;

		int endIndex = 0;

		Matcher matcher = _templatePattern.matcher(template);

		while (matcher.find()) {
			int startIndex = matcher.start();

			if (sb == null) {
				sb = new StringBundler();
			}

			sb.append(template.substring(endIndex, startIndex));

			endIndex = matcher.end();

			String matched = template.substring(startIndex, endIndex);

			sb.append(_templates.get(matched));
		}

		if (sb == null) {
			return _applyMaxStringIndexLengthLimitation(template);
		}

		if (template.length() > endIndex) {
			sb.append(template.substring(endIndex));
		}

		return _applyMaxStringIndexLengthLimitation(sb.toString());
	}

	protected abstract String reword(String data)
		throws IOException, SQLException;

	protected static final String ALTER_COLUMN_NAME = "alter_column_name ";

	protected static final String ALTER_COLUMN_TYPE = "alter_column_type ";

	protected static final String ALTER_TABLE_NAME = "alter_table_name ";

	protected static final String CREATE_TABLE = "create table ";

	protected static final String DROP_INDEX = "drop index";

	protected static final String DROP_PRIMARY_KEY = "drop primary key";

	protected static final String[] RENAME_TABLE_TEMPLATE = {
		"@old-table@", "@new-table@"
	};

	protected static final String[] REWORD_TEMPLATE = {
		"@table@", "@old-column@", "@new-column@", "@type@", "@nullable@"
	};

	protected static final int[] SQL_VARCHAR_TYPES = {
		Types.LONGNVARCHAR, Types.LONGVARCHAR, Types.NVARCHAR, Types.VARCHAR
	};

	protected static final String[] TEMPLATE = {
		"##", "TRUE", "FALSE", "'01/01/1970'", "CURRENT_TIMESTAMP", " BLOB",
		" SBLOB", " BOOLEAN", " DATE", " DOUBLE", " INTEGER", " LONG",
		" STRING", " TEXT", " VARCHAR", " IDENTITY", "COMMIT_TRANSACTION"
	};

	protected static final Pattern columnTypePattern = Pattern.compile(
		"(^\\w+)", Pattern.CASE_INSENSITIVE);

	private void _addIndexes(
			Connection connection, String indexesSQL,
			Set<String> validIndexNames)
		throws Exception {

		if (_log.isInfoEnabled()) {
			_log.info("Adding indexes");
		}

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(indexesSQL))) {

			String sql = null;

			while ((sql = unsyncBufferedReader.readLine()) != null) {
				if (Validator.isNull(sql)) {
					continue;
				}

				int y = sql.indexOf(" on ");

				int x = sql.lastIndexOf(" ", y - 1);

				String indexName = sql.substring(x + 1, y);

				if (validIndexNames.contains(indexName)) {
					continue;
				}

				if (_log.isInfoEnabled()) {
					_log.info(sql);
				}

				try {
					runSQL(connection, sql);
				}
				catch (Exception exception) {
					if (_log.isWarnEnabled()) {
						_log.warn(exception.getMessage() + ": " + sql);
					}
				}
			}
		}
	}

	private String _applyMaxStringIndexLengthLimitation(String template) {
		if (!template.contains("[$COLUMN_LENGTH:")) {
			return template;
		}

		DBType dbType = getDBType();

		int stringIndexMaxLength = GetterUtil.getInteger(
			PropsUtil.get(
				PropsKeys.DATABASE_STRING_INDEX_MAX_LENGTH,
				new Filter(dbType.getName())),
			-1);

		Matcher matcher = _columnLengthPattern.matcher(template);

		if (stringIndexMaxLength < 0) {
			return matcher.replaceAll("$1");
		}

		StringBuffer sb = new StringBuffer();

		while (matcher.find()) {
			int length = Integer.valueOf(matcher.group(2));

			if (length > stringIndexMaxLength) {
				matcher.appendReplacement(
					sb,
					limitColumnLength(matcher.group(1), stringIndexMaxLength));
			}
			else {
				matcher.appendReplacement(sb, matcher.group(1));
			}
		}

		matcher.appendTail(sb);

		return sb.toString();
	}

	private List<PrimaryKey> _getPrimaryKeys(
			Connection connection, String tableName)
		throws SQLException {

		List<PrimaryKey> primaryKeys = new ArrayList<>();

		DatabaseMetaData databaseMetaData = connection.getMetaData();
		DBInspector dbInspector = new DBInspector(connection);

		try (ResultSet resultSet = databaseMetaData.getPrimaryKeys(
				dbInspector.getCatalog(), dbInspector.getSchema(),
				dbInspector.normalizeName(tableName, databaseMetaData))) {

			while (resultSet.next()) {
				primaryKeys.add(
					new PrimaryKey(
						dbInspector.normalizeName(
							resultSet.getString("COLUMN_NAME"),
							databaseMetaData),
						resultSet.getInt("KEY_SEQ")));
			}
		}

		return primaryKeys;
	}

	private static final boolean _SUPPORTS_ALTER_COLUMN_NAME = true;

	private static final boolean _SUPPORTS_ALTER_COLUMN_TYPE = true;

	private static final boolean _SUPPORTS_INLINE_DISTINCT = true;

	private static final boolean _SUPPORTS_QUERYING_AFTER_EXCEPTION = true;

	private static final boolean _SUPPORTS_SCROLLABLE_RESULTS = true;

	private static final boolean _SUPPORTS_UPDATE_WITH_INNER_JOIN = true;

	private static final Log _log = LogFactoryUtil.getLog(BaseDB.class);

	private static final Pattern _columnLengthPattern = Pattern.compile(
		"([^,(\\s]+)\\[\\$COLUMN_LENGTH:(\\d+)\\$\\]");
	private static final Pattern _templatePattern;

	static {
		StringBundler sb = new StringBundler((TEMPLATE.length * 5) - 6);

		for (int i = 0; i < TEMPLATE.length; i++) {
			String variable = TEMPLATE[i];

			if (variable.equals("##") || variable.equals("'01/01/1970'")) {
				sb.append(variable);
			}
			else {
				sb.append("(?<!\\[\\$)");
				sb.append(variable);
				sb.append("(?!\\$\\])");

				sb.append("\\b");
			}

			sb.append(StringPool.PIPE);
		}

		sb.setIndex(sb.index() - 1);

		_templatePattern = Pattern.compile(sb.toString());
	}

	private final DBType _dbType;
	private final int _majorVersion;
	private final int _minorVersion;
	private final Map<String, Integer> _sqlTypes = new HashMap<>();
	private final Map<String, Integer> _sqlVarcharSizes = new HashMap<>();
	private boolean _supportsStringCaseSensitiveQuery = true;
	private final Map<String, String> _templates = new HashMap<>();

	private static class PrimaryKey {

		private PrimaryKey(String columnName, int keySeq) {
			_columnName = columnName;
			_keySeq = keySeq;
		}

		private final String _columnName;
		private final int _keySeq;

	}

}
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

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.petra.function.UnsafeConsumer;
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
			Connection connection, String indexesSQL,
			Set<String> validIndexNames)
		throws IOException {

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

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public void buildCreateFile(String sqlDir, String databaseName)
		throws IOException {
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public void buildCreateFile(
			String sqlDir, String databaseName, int population)
		throws IOException {
	}

	@Override
	public abstract String buildSQL(String template)
		throws IOException, SQLException;

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public void buildSQLFile(String sqlDir, String fileName)
		throws IOException {
	}

	@Override
	public DBType getDBType() {
		return _dbType;
	}

	@Override
	public ResultSet getIndexResultSet(Connection connection, String tableName) 
		throws SQLException {
		DatabaseMetaData databaseMetaData = connection.getMetaData();
		
		DBInspector dbInspector = new DBInspector(connection);
		
		String catalog = dbInspector.getCatalog();
 		String schema = dbInspector.getSchema();
 		
 		return databaseMetaData.getIndexInfo(
 				catalog, schema, tableName, false, false);
	}

	@Override
	public List<Index> getIndexes(Connection connection) throws SQLException {
		Set<Index> indexes = new HashSet<>();

		DatabaseMetaData databaseMetaData = connection.getMetaData();

		DBInspector dbInspector = new DBInspector(connection);

		String catalog = dbInspector.getCatalog();
		String schema = dbInspector.getSchema();

		try (ResultSet tableResultSet = databaseMetaData.getTables(
				catalog, schema, null, new String[] {"TABLE"})) {

			while (tableResultSet.next()) {
				String tableName = dbInspector.normalizeName(
					tableResultSet.getString("TABLE_NAME"));

				try (ResultSet indexResultSet = getIndexResultSet(
						connection, tableName)) {

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

						boolean unique = !indexResultSet.getBoolean(
							"NON_UNIQUE");

						indexes.add(new Index(indexName, tableName, unique));
					}
				}
			}
		}

		return new ArrayList<>(indexes);
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
	public Integer getSQLType(String templateType) {
		return _sqlTypes.get(templateType);
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

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             CounterLocalServiceUtil#increment()}
	 */
	@Deprecated
	@Override
	public long increment() {
		return CounterLocalServiceUtil.increment();
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             CounterLocalServiceUtil#increment(String)}
	 */
	@Deprecated
	@Override
	public long increment(String name) {
		return CounterLocalServiceUtil.increment(name);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             CounterLocalServiceUtil#increment(String, int)}
	 */
	@Deprecated
	@Override
	public long increment(String name, int size) {
		return CounterLocalServiceUtil.increment(name, size);
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

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             DBProcess#runSQLTemplate(String)}
	 */
	@Deprecated
	@Override
	public void runSQLTemplate(String path)
		throws IOException, NamingException, SQLException {

		runSQLTemplate(path, true);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             DBProcess#runSQLTemplate(String, boolean)}
	 */
	@Deprecated
	@Override
	public void runSQLTemplate(String path, boolean failOnError)
		throws IOException, NamingException, SQLException {

		Thread currentThread = Thread.currentThread();

		ClassLoader classLoader = currentThread.getContextClassLoader();

		InputStream inputStream = classLoader.getResourceAsStream(
			"com/liferay/portal/tools/sql/dependencies/" + path);

		if (inputStream == null) {
			inputStream = classLoader.getResourceAsStream(path);
		}

		if (inputStream == null) {
			_log.error("Invalid path " + path);

			if (failOnError) {
				throw new IOException("Invalid path " + path);
			}

			return;
		}

		String template = StringUtil.read(inputStream);

		runSQLTemplateString(template, failOnError);
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
								_log.warn(ioException.getMessage());
							}
						}
						catch (SecurityException securityException) {
							if (failOnError) {
								throw securityException;
							}
							else if (_log.isWarnEnabled()) {
								_log.warn(securityException.getMessage());
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

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #runSQLTemplateString(Connection, String, boolean)}
	 */
	@Deprecated
	@Override
	public void runSQLTemplateString(
			Connection connection, String template, boolean evaluate,
			boolean failOnError)
		throws IOException, NamingException, SQLException {

		runSQLTemplateString(connection, template, failOnError);
	}

	@Override
	public void runSQLTemplateString(String template, boolean failOnError)
		throws IOException, NamingException, SQLException {

		try (Connection connection = DataAccess.getConnection()) {
			runSQLTemplateString(connection, template, failOnError);
		}
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #runSQLTemplateString(String, boolean)}
	 */
	@Deprecated
	@Override
	public void runSQLTemplateString(
			String template, boolean evaluate, boolean failOnError)
		throws IOException, NamingException, SQLException {

		runSQLTemplateString(template, failOnError);
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

		process(
			companyId -> {
				if (Validator.isNotNull(companyId) && _log.isInfoEnabled()) {
					_log.info(
						"Updating database indexes for company " + companyId);
				}

				List<Index> indexes = getIndexes(connection);

				Set<String> validIndexNames = null;

				if (dropIndexes) {
					validIndexNames = dropIndexes(
						connection, tablesSQL, indexesSQL, indexes);
				}
				else {
					validIndexNames = new HashSet<>();

					for (Index index : indexes) {
						String indexName = StringUtil.toUpperCase(
							index.getIndexName());

						validIndexNames.add(indexName);
					}
				}

				addIndexes(
					connection,
					_applyMaxStringIndexLengthLimitation(indexesSQL),
					validIndexNames);
			});
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

	protected abstract int[] getSQLTypes();

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

	protected static final String[] TEMPLATE = {
		"##", "TRUE", "FALSE", "'01/01/1970'", "CURRENT_TIMESTAMP", " BLOB",
		" SBLOB", " BOOLEAN", " DATE", " DOUBLE", " INTEGER", " LONG",
		" STRING", " TEXT", " VARCHAR", " IDENTITY", "COMMIT_TRANSACTION"
	};

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
	private boolean _supportsStringCaseSensitiveQuery = true;
	private final Map<String, String> _templates = new HashMap<>();

}
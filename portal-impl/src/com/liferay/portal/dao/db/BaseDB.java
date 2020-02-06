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
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.dao.orm.common.SQLTransformer;
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
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.File;
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
			Connection con, String indexesSQL, Set<String> validIndexNames)
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
					runSQL(con, sql);
				}
				catch (Exception exception) {
					if (_log.isWarnEnabled()) {
						_log.warn(exception.getMessage() + ": " + sql);
					}
				}
			}
		}
	}

	@Override
	public void buildCreateFile(String sqlDir, String databaseName)
		throws IOException {

		buildCreateFile(sqlDir, databaseName, BARE);
		buildCreateFile(sqlDir, databaseName, DEFAULT);
	}

	@Override
	public void buildCreateFile(
			String sqlDir, String databaseName, int population)
		throws IOException {

		if (population == BARE) {
			FileUtil.write(
				StringBundler.concat(
					sqlDir, "/create-bare/create-bare-", _dbType, ".sql"),
				getRecreateSQL(databaseName));

			return;
		}

		File file = new File(
			StringBundler.concat(sqlDir, "/create/create-", _dbType,	".sql"));

		StringBundler sb = new StringBundler(6);

		String tablesPrefix = "/portal/portal-";

		if (sqlDir.endsWith("/WEB-INF/sql")) {
			tablesPrefix = "/tables/tables-";
		}

		sb.append(
			_readFile(
				StringBundler.concat(
					sqlDir, tablesPrefix, _dbType, ".sql")));

		sb.append("\n\n");

		sb.append(
			_readFile(
				StringBundler.concat(
					sqlDir, "/indexes/indexes-", _dbType, ".sql")));

		sb.append("\n\n");

		sb.append(
			_readFile(
				StringBundler.concat(
					sqlDir, "/sequences/sequences-", _dbType, ".sql")));

		sb.append("\n");

		String content = buildCreateFileContent(
			sqlDir, databaseName, sb.toString());

		if (content != null) {
			FileUtil.write(file, content);
		}
	}

	@Override
	public abstract String buildSQL(String template) throws IOException;

	@Override
	public void buildSQLFile(String sqlDir, String fileName)
		throws IOException {

		String template = buildTemplate(sqlDir, fileName);

		if (Validator.isNull(template)) {
			return;
		}

		template = buildSQL(template);

		FileUtil.write(
			StringBundler.concat(
				sqlDir, "/", fileName, "/", fileName, "-", _dbType, ".sql"),
			template);
	}

	@Override
	public DBType getDBType() {
		return _dbType;
	}

	@Override
	public List<Index> getIndexes(Connection con) throws SQLException {
		Set<Index> indexes = new HashSet<>();

		DatabaseMetaData databaseMetaData = con.getMetaData();

		DBInspector dbInspector = new DBInspector(con);

		String catalog = dbInspector.getCatalog();
		String schema = dbInspector.getSchema();

		try (ResultSet tableRS = databaseMetaData.getTables(
				catalog, schema, null, new String[] {"TABLE"})) {

			while (tableRS.next()) {
				String tableName = dbInspector.normalizeName(
					tableRS.getString("TABLE_NAME"));

				try (ResultSet indexRS = databaseMetaData.getIndexInfo(
						catalog, schema, tableName, false, false)) {

					while (indexRS.next()) {
						String indexName = indexRS.getString("INDEX_NAME");

						if (indexName == null) {
							continue;
						}

						String lowerCaseIndexName = StringUtil.toLowerCase(
							indexName);

						if (!lowerCaseIndexName.startsWith("liferay_") &&
							!lowerCaseIndexName.startsWith("ix_")) {

							continue;
						}

						boolean unique = !indexRS.getBoolean("NON_UNIQUE");

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
	public void runSQL(Connection con, String sql)
		throws IOException, SQLException {

		runSQL(con, new String[] {sql});
	}

	@Override
	public void runSQL(Connection con, String[] sqls)
		throws IOException, SQLException {

		Statement s = null;

		try {
			s = con.createStatement();

			for (String sql : sqls) {
				sql = buildSQL(applyMaxStringIndexLengthLimitation(sql));

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
						StringBundler sb = new StringBundler(10);

						sb.append("SQL: ");
						sb.append(sql);
						sb.append("\nSQL state: ");
						sb.append(sqlException.getSQLState());
						sb.append("\nVendor: ");
						sb.append(getDBType());
						sb.append("\nVendor error code: ");
						sb.append(sqlException.getErrorCode());
						sb.append("\nVendor error message: ");
						sb.append(sqlException.getMessage());

						_log.debug(sb.toString());
					}

					throw sqlException;
				}
			}
		}
		finally {
			DataAccess.cleanUp(s);
		}
	}

	@Override
	public void runSQL(String sql) throws IOException, SQLException {
		runSQL(new String[] {sql});
	}

	@Override
	public void runSQL(String[] sqls) throws IOException, SQLException {
		Connection con = DataAccess.getConnection();

		try {
			runSQL(con, sqls);
		}
		finally {
			DataAccess.cleanUp(con);
		}
	}

	@Override
	public void runSQLTemplate(String path)
		throws IOException, NamingException, SQLException {

		runSQLTemplate(path, true);
	}

	@Override
	public void runSQLTemplate(String path, boolean failOnError)
		throws IOException, NamingException, SQLException {

		Thread currentThread = Thread.currentThread();

		ClassLoader classLoader = currentThread.getContextClassLoader();

		InputStream is = classLoader.getResourceAsStream(
			"com/liferay/portal/tools/sql/dependencies/" + path);

		if (is == null) {
			is = classLoader.getResourceAsStream(path);
		}

		if (is == null) {
			_log.error("Invalid path " + path);

			if (failOnError) {
				throw new IOException("Invalid path " + path);
			}

			return;
		}

		String template = StringUtil.read(is);

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

		template = applyMaxStringIndexLengthLimitation(template);

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

					InputStream is = classLoader.getResourceAsStream(
						"com/liferay/portal/tools/sql/dependencies/" +
							includeFileName);

					if (is == null) {
						is = classLoader.getResourceAsStream(includeFileName);
					}

					String include = StringUtil.read(is);

					include = replaceTemplate(include, getTemplate());

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
			Connection con, String tablesSQL, String indexesSQL,
			boolean dropIndexes)
		throws IOException, SQLException {

		List<Index> indexes = getIndexes(con);

		Set<String> validIndexNames = null;

		if (dropIndexes) {
			validIndexNames = dropIndexes(con, tablesSQL, indexesSQL, indexes);
		}
		else {
			validIndexNames = new HashSet<>();

			for (Index index : indexes) {
				String indexName = StringUtil.toUpperCase(index.getIndexName());

				validIndexNames.add(indexName);
			}
		}

		indexesSQL = applyMaxStringIndexLengthLimitation(indexesSQL);

		addIndexes(con, indexesSQL, validIndexNames);
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

	protected String applyMaxStringIndexLengthLimitation(String template) {
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
			return matcher.replaceAll(StringPool.BLANK);
		}

		StringBuffer sb = new StringBuffer();

		String replacement = "\\(" + stringIndexMaxLength + "\\)";

		while (matcher.find()) {
			int length = Integer.valueOf(matcher.group(1));

			if (length > stringIndexMaxLength) {
				matcher.appendReplacement(sb, replacement);
			}
			else {
				matcher.appendReplacement(sb, StringPool.BLANK);
			}
		}

		matcher.appendTail(sb);

		return sb.toString();
	}

	protected String[] buildColumnNameTokens(String line) {
		String[] words = StringUtil.split(line, ' ');

		String nullable = "";

		if (words.length == 7) {
			nullable = "not null;";
		}

		return new String[] {words[1], words[2], words[3], words[4], nullable};
	}

	protected String[] buildColumnTypeTokens(String line) {
		String[] words = StringUtil.split(line, ' ');

		String nullable = "";

		if (words.length == 6) {
			nullable = "not null;";
		}
		else if (words.length == 5) {
			nullable = words[4];
		}
		else if (words.length == 4) {
			nullable = "not null;";

			if (words[3].endsWith(";")) {
				words[3] = words[3].substring(0, words[3].length() - 1);
			}
		}

		return new String[] {words[1], words[2], "", words[3], nullable};
	}

	protected abstract String buildCreateFileContent(
			String sqlDir, String databaseName, String createTablesContent)
		throws IOException;

	protected String[] buildTableNameTokens(String line) {
		String[] words = StringUtil.split(line, StringPool.SPACE);

		return new String[] {words[1], words[2]};
	}

	protected String buildTemplate(String sqlDir, String fileName)
		throws IOException {

		String template = _readFile(
			StringBundler.concat(sqlDir, "/", fileName, ".sql"));

		if (fileName.equals("portal")) {
			StringBundler sb = new StringBundler();

			try (UnsyncBufferedReader unsyncBufferedReader =
					new UnsyncBufferedReader(
						new UnsyncStringReader(template))) {

				String line = null;

				while ((line = unsyncBufferedReader.readLine()) != null) {
					if (line.startsWith("@include ")) {
						int pos = line.indexOf(" ");

						String includeFileName = line.substring(pos + 1);

						File includeFile = new File(
							sqlDir + "/" + includeFileName);

						if (!includeFile.exists()) {
							continue;
						}

						String include = FileUtil.read(includeFile);

						include = replaceTemplate(include, getTemplate());

						sb.append(include);

						sb.append("\n\n");
					}
					else {
						sb.append(line);
						sb.append("\n");
					}
				}
			}

			template = sb.toString();
		}

		if (fileName.equals("indexes")) {
			template = applyMaxStringIndexLengthLimitation(template);

			if (getDBType() == DBType.SYBASE) {
				template = removeBooleanIndexes(sqlDir, template);
			}
		}

		return template;
	}

	protected Set<String> dropIndexes(
			Connection con, String tablesSQL, String indexesSQL,
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

			runSQL(con, sql);
		}

		return validIndexNames;
	}

	protected abstract int[] getSQLTypes();

	protected abstract String[] getTemplate();

	protected String removeBooleanIndexes(String sqlDir, String data)
		throws IOException {

		String portalData = _readFile(sqlDir + "/portal-tables.sql");

		if (Validator.isNull(portalData)) {
			return StringPool.BLANK;
		}

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(data))) {

			StringBundler sb = new StringBundler();

			String line = null;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				boolean append = true;

				int x = line.indexOf(" on ");

				if (x != -1) {
					int y = line.indexOf(" (", x);

					String table = line.substring(x + 4, y);

					x = y + 2;

					y = line.indexOf(")", x);

					String[] columns = StringUtil.split(line.substring(x, y));

					x = portalData.indexOf(CREATE_TABLE + table + " (");

					y = portalData.indexOf(");", x);

					String portalTableData = portalData.substring(x, y);

					for (String column : columns) {
						if (portalTableData.contains(
								column.trim() + " BOOLEAN")) {

							append = false;

							break;
						}
					}
				}

				if (append) {
					sb.append(line);
					sb.append("\n");
				}
			}

			return sb.toString();
		}
	}

	protected String removeNull(String content) {
		content = StringUtil.replace(content, " = null", " = NULL");
		content = StringUtil.replace(content, " is null", " IS NULL");
		content = StringUtil.replace(content, " not null", " not_null");
		content = StringUtil.removeSubstring(content, " null");
		content = StringUtil.replace(content, " not_null", " not null");

		return content;
	}

	protected String replaceTemplate(String template, String[] actual) {
		if ((template == null) || (TEMPLATE == null) || (actual == null)) {
			return null;
		}

		if (TEMPLATE.length != actual.length) {
			return template;
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
			return template;
		}

		if (template.length() > endIndex) {
			sb.append(template.substring(endIndex));
		}

		return sb.toString();
	}

	protected abstract String reword(String data) throws IOException;

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

	private String _readFile(String fileName) throws IOException {
		if (FileUtil.exists(fileName)) {
			return FileUtil.read(fileName);
		}

		return StringPool.BLANK;
	}

	private static final boolean _SUPPORTS_ALTER_COLUMN_NAME = true;

	private static final boolean _SUPPORTS_ALTER_COLUMN_TYPE = true;

	private static final boolean _SUPPORTS_INLINE_DISTINCT = true;

	private static final boolean _SUPPORTS_QUERYING_AFTER_EXCEPTION = true;

	private static final boolean _SUPPORTS_SCROLLABLE_RESULTS = true;

	private static final boolean _SUPPORTS_UPDATE_WITH_INNER_JOIN = true;

	private static final Log _log = LogFactoryUtil.getLog(BaseDB.class);

	private static final Pattern _columnLengthPattern = Pattern.compile(
		"\\[\\$COLUMN_LENGTH:(\\d+)\\$\\]");
	private static final Pattern _templatePattern;

	static {
		StringBundler sb = new StringBundler(TEMPLATE.length * 5 - 6);

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
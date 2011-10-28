/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.dao.orm.common.SQLTransformer;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.dao.db.Index;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.velocity.VelocityUtil;
import com.liferay.util.SimpleCounter;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.NamingException;

/**
 * @author Alexander Chow
 * @author Ganesh Ram
 * @author Brian Wing Shun Chan
 */
public abstract class BaseDB implements DB {

	public void buildCreateFile(String sqlDir, String databaseName)
		throws IOException {

		buildCreateFile(sqlDir, databaseName, POPULATED);
		buildCreateFile(sqlDir, databaseName, MINIMAL);
		buildCreateFile(sqlDir, databaseName, SHARDED);
	}

	public void buildCreateFile(
			String sqlDir, String databaseName, int population)
		throws IOException {

		String suffix = getSuffix(population);

		File file = new File(
			sqlDir + "/create" + suffix + "/create" + suffix + "-" +
				getServerName() + ".sql");

		if (population != SHARDED) {
			String content = buildCreateFileContent(
				sqlDir, databaseName, population);

			if (content != null) {
				FileUtil.write(file, content);
			}
		}
		else {
			String content = buildCreateFileContent(
				sqlDir, databaseName, MINIMAL);

			if (content != null) {
				FileUtil.write(file, content);
			}

			content = buildCreateFileContent(
				sqlDir, databaseName + "1", MINIMAL);

			if (content != null) {
				FileUtil.write(file, content, false, true);
			}

			content = buildCreateFileContent(
				sqlDir, databaseName + "2", MINIMAL);

			if (content != null) {
				FileUtil.write(file, content, false, true);
			}
		}
	}

	public abstract String buildSQL(String template) throws IOException;

	public void buildSQLFile(String sqlDir, String fileName)
		throws IOException {

		String template = buildTemplate(sqlDir, fileName);

		if (Validator.isNull(template)) {
			return;
		}

		template = buildSQL(template);

		FileUtil.write(
			sqlDir + "/" + fileName + "/" + fileName + "-" + getServerName() +
				".sql",
			template);
	}

	@SuppressWarnings("unused")
	public List<Index> getIndexes() throws SQLException {
		return Collections.emptyList();
	}

	public String getTemplateFalse() {
		return getTemplate()[2];
	}

	public String getTemplateTrue() {
		return getTemplate()[1];
	}

	public String getType() {
		return _type;
	}

	public long increment() throws SystemException {
		return CounterLocalServiceUtil.increment();
	}

	public long increment(String name) throws SystemException {
		return CounterLocalServiceUtil.increment(name);
	}

	public boolean isSupportsAlterColumnName() {
		return _SUPPORTS_ALTER_COLUMN_NAME;
	}

	public boolean isSupportsAlterColumnType() {
		return _SUPPORTS_ALTER_COLUMN_TYPE;
	}

	public boolean isSupportsDateMilliseconds() {
		return _SUPPORTS_DATE_MILLISECONDS;
	}

	public boolean isSupportsInlineDistinct() {
		return _SUPPORTS_INLINE_DISTINCT;
	}

	public boolean isSupportsScrollableResults() {
		return _SUPPORTS_SCROLLABLE_RESULTS;
	}

	public boolean isSupportsStringCaseSensitiveQuery() {
		return _supportsStringCaseSensitiveQuery;
	}

	public boolean isSupportsUpdateWithInnerJoin() {
		return _SUPPORTS_UPDATE_WITH_INNER_JOIN;
	}

	public void runSQL(String sql) throws IOException, SQLException {
		runSQL(new String[] {sql});
	}

	public void runSQL(Connection con, String sql)
		throws IOException, SQLException {

		runSQL(con, new String[] {sql});
	}

	public void runSQL(String[] sqls) throws IOException, SQLException {
		Connection con = DataAccess.getConnection();

		try {
			runSQL(con, sqls);
		}
		finally {
			DataAccess.cleanUp(con);
		}
	}

	public void runSQL(Connection con, String[] sqls)
		throws IOException, SQLException {

		Statement s = null;

		try {
			s = con.createStatement();

			for (int i = 0; i < sqls.length; i++) {
				String sql = buildSQL(sqls[i]);

				sql = SQLTransformer.transform(sql.trim());

				if (sql.endsWith(";")) {
					sql = sql.substring(0, sql.length() - 1);
				}

				if (sql.endsWith("go")) {
					sql = sql.substring(0, sql.length() - 2);
				}

				if (_log.isDebugEnabled()) {
					_log.debug(sql);
				}

				try {
					s.executeUpdate(sql);
				}
				catch (SQLException sqle) {
					throw sqle;
				}
			}
		}
		finally {
			DataAccess.cleanUp(s);
		}
	}

	public void runSQLTemplate(String path)
		throws IOException, NamingException, SQLException {

		runSQLTemplate(path, true);
	}

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
			else {
				return;
			}
		}

		String template = StringUtil.read(is);

		is.close();

		boolean evaluate = path.endsWith(".vm");

		runSQLTemplateString(template, evaluate, failOnError);
	}

	public void runSQLTemplateString(
			String template, boolean evaluate, boolean failOnError)
		throws IOException, NamingException, SQLException {

		if (evaluate) {
			try {
				template = evaluateVM(template);
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		StringBundler sb = new StringBundler();

		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new UnsyncStringReader(template));

		String line = null;

		while ((line = unsyncBufferedReader.readLine()) != null) {
			if (!line.startsWith("##")) {
				if (line.startsWith("@include ")) {
					int pos = line.indexOf(" ");

					String includeFileName = line.substring(pos + 1);

					Thread currentThread = Thread.currentThread();

					ClassLoader classLoader =
						currentThread.getContextClassLoader();

					InputStream is = classLoader.getResourceAsStream(
						"com/liferay/portal/tools/sql/dependencies/" +
							includeFileName);

					if (is == null) {
						is = classLoader.getResourceAsStream(includeFileName);
					}

					String include = StringUtil.read(is);

					is.close();

					if (includeFileName.endsWith(".vm")) {
						try {
							include = evaluateVM(include);
						}
						catch (Exception e) {
							_log.error(e, e);
						}
					}

					include = convertTimestamp(include);
					include = replaceTemplate(include, getTemplate());

					runSQLTemplateString(include, false, true);
				}
				else{
					sb.append(line);

					if (line.endsWith(";")) {
						String sql = sb.toString();

						sb.setIndex(0);

						try {
							if (!sql.equals("COMMIT_TRANSACTION;")) {
								runSQL(sql);
							}
							else {
								if (_log.isDebugEnabled()) {
									_log.debug("Skip commit sql");
								}
							}
						}
						catch (IOException ioe) {
							if (failOnError) {
								throw ioe;
							}
							else if (_log.isWarnEnabled()) {
								_log.warn(ioe.getMessage());
							}
						}
						catch (SQLException sqle) {
							if (failOnError) {
								throw sqle;
							}
							else if (_log.isWarnEnabled()) {
								String message = GetterUtil.getString(
									sqle.getMessage());

								if (!message.startsWith("Duplicate key name")) {
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

		unsyncBufferedReader.close();
	}

	public void setSupportsStringCaseSensitiveQuery(
		boolean supportsStringCaseSensitiveQuery) {

		if (_log.isInfoEnabled()) {
			if (supportsStringCaseSensitiveQuery) {
				_log.info("Database supports case sensitive queries");
			}
			else {
				_log.info("Database does not support case sensitive queries");
			}
		}

		_supportsStringCaseSensitiveQuery = supportsStringCaseSensitiveQuery;
	}

	public void updateIndexes(
			String tablesSQL, String indexesSQL, String indexesProperties,
			boolean dropIndexes)
		throws IOException, SQLException {

		List<Index> indexes = getIndexes();

		Set<String> validIndexNames = null;

		if (dropIndexes) {
			validIndexNames = dropIndexes(
				tablesSQL, indexesSQL, indexesProperties, indexes);
		}
		else {
			validIndexNames = new HashSet<String>();

			for (Index index : indexes) {
				String indexName = index.getIndexName().toUpperCase();

				validIndexNames.add(indexName);
			}
		}

		addIndexes(indexesSQL, validIndexNames);
	}

	protected BaseDB(String type) {
		_type = type;

		String[] actual = getTemplate();

		for (int i = 0; i < TEMPLATE.length; i++) {
			_templateMap.put(TEMPLATE[i], actual[i]);
		}
	}

	protected void addIndexes(String indexesSQL, Set<String> validIndexNames)
		throws IOException {

		if (_log.isInfoEnabled()) {
			_log.info("Adding indexes");
		}

		DB db = DBFactoryUtil.getDB();

		UnsyncBufferedReader bufferedReader = new UnsyncBufferedReader(
			new UnsyncStringReader(indexesSQL));

		String sql = null;

		while ((sql = bufferedReader.readLine()) != null) {
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
				db.runSQL(sql);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(e.getMessage() + ": " + sql);
				}
			}
		}
	}

	protected abstract String buildCreateFileContent(
			String sqlDir, String databaseName, int population)
		throws IOException;

	protected String[] buildColumnNameTokens(String line) {
		String[] words = StringUtil.split(line, ' ');

		if (words.length == 7) {
			words[5] = "not null;";
		}

		String[] template = {
			words[1], words[2], words[3], words[4], words[5]
		};

		return template;
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

		String[] template = {
			words[1], words[2], "", words[3], nullable
		};

		return template;
	}

	protected String buildTemplate(String sqlDir, String fileName)
		throws IOException {

		String template = readFile(sqlDir + "/" + fileName + ".sql");

		if (fileName.equals("portal") || fileName.equals("portal-minimal") ||
			fileName.equals("update-5.0.1-5.1.0")) {

			UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new UnsyncStringReader(template));

			StringBundler sb = new StringBundler();

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

					if (includeFileName.endsWith(".vm")) {
						try {
							include = evaluateVM(include);
						}
						catch (Exception e) {
							_log.error(e, e);
						}
					}

					include = convertTimestamp(include);
					include = replaceTemplate(include, getTemplate());

					sb.append(include);
					sb.append("\n\n");
				}
				else {
					sb.append(line);
					sb.append("\n");
				}
			}

			unsyncBufferedReader.close();

			template = sb.toString();
		}

		if (fileName.equals("indexes") && (this instanceof SybaseDB)) {
			template = removeBooleanIndexes(sqlDir, template);
		}

		return template;
	}

	protected String convertTimestamp(String data) {
		String s = null;

		if (this instanceof MySQLDB) {
			s = StringUtil.replace(data, "SPECIFIC_TIMESTAMP_", "");
		}
		else {
			Matcher matcher = _timestampPattern.matcher(data);

			s = matcher.replaceAll("CURRENT_TIMESTAMP");
		}

		return s;
	}

	protected Set<String> dropIndexes(
			String tablesSQL, String indexesSQL, String indexesProperties,
			List<Index> indexes)
		throws IOException, SQLException {

		if (_log.isInfoEnabled()) {
			_log.info("Dropping stale indexes");
		}

		Set<String> validIndexNames = new HashSet<String>();

		if (indexes.isEmpty()) {
			return validIndexNames;
		}

		DB db = DBFactoryUtil.getDB();

		String tablesSQLLowerCase = tablesSQL.toLowerCase();
		String indexesSQLLowerCase = indexesSQL.toLowerCase();

		Properties indexesPropertiesObj = PropertiesUtil.load(
			indexesProperties);

		Enumeration<String> enu =
			(Enumeration<String>)indexesPropertiesObj.propertyNames();

		while (enu.hasMoreElements()) {
			String key = enu.nextElement();

			String value = indexesPropertiesObj.getProperty(key);

			indexesPropertiesObj.setProperty(key.toLowerCase(), value);
		}

		for (Index index : indexes) {
			String indexNameUpperCase = index.getIndexName().toUpperCase();
			String indexNameLowerCase = indexNameUpperCase.toLowerCase();
			String tableName = index.getTableName();
			String tableNameLowerCase = tableName.toLowerCase();
			boolean unique = index.isUnique();

			validIndexNames.add(indexNameUpperCase);

			if (indexesPropertiesObj.containsKey(indexNameLowerCase)) {
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
			else {
				if (!tablesSQLLowerCase.contains(
						"create table " + tableNameLowerCase + " (")) {

					continue;
				}
			}

			validIndexNames.remove(indexNameUpperCase);

			db.runSQL("drop index " + indexNameUpperCase + " on " + tableName);
		}

		return validIndexNames;
	}

	protected String evaluateVM(String template) throws Exception {
		Map<String, Object> variables = new HashMap<String, Object>();

		variables.put("counter", new SimpleCounter());

		Thread currentThread = Thread.currentThread();

		ClassLoader classLoader = currentThread.getContextClassLoader();

		try {
			currentThread.setContextClassLoader(
				PortalClassLoaderUtil.getClassLoader());

			template = VelocityUtil.evaluate(template, variables);
		}
		finally {
			currentThread.setContextClassLoader(classLoader);
		}

		// Trim insert statements because it breaks MySQL Query Browser

		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new UnsyncStringReader(template));

		StringBundler sb = new StringBundler();

		String line = null;

		while ((line = unsyncBufferedReader.readLine()) != null) {
			line = line.trim();

			sb.append(line);
			sb.append("\n");
		}

		unsyncBufferedReader.close();

		template = sb.toString();
		template = StringUtil.replace(template, "\n\n\n", "\n\n");

		return template;
	}

	protected abstract String getServerName();

	protected String getSuffix(int type) {
		if (type == MINIMAL) {
			return "-minimal";
		}
		else if (type == SHARDED) {
			return "-sharded";
		}
		else {
			return StringPool.BLANK;
		}
	}

	protected abstract String[] getTemplate();

	protected String readFile(String fileName) throws IOException {
		if (FileUtil.exists(fileName)) {
			return FileUtil.read(fileName);
		}
		else {
			return StringPool.BLANK;
		}
	}

	protected String readSQL(String fileName, String comments, String eol)
		throws IOException {

		if (!FileUtil.exists(fileName)) {
			return StringPool.BLANK;
		}

		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new FileReader(new File(fileName)));

		StringBundler sb = new StringBundler();

		String line = null;

		while ((line = unsyncBufferedReader.readLine()) != null) {
			if (!line.startsWith(comments)) {
				line = StringUtil.replace(
					line,
					new String[] {"\n", "\t"},
					new String[] {"", ""});

				if (line.endsWith(";")) {
					sb.append(line.substring(0, line.length() - 1));
					sb.append(eol);
				}
				else {
					sb.append(line);
				}
			}
		}

		unsyncBufferedReader.close();

		return sb.toString();
	}

	protected String removeBooleanIndexes(String sqlDir, String data)
		throws IOException {

		String portalData = readFile(sqlDir + "/portal-tables.sql");

		if (Validator.isNull(portalData)) {
			return StringPool.BLANK;
		}

		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new UnsyncStringReader(data));

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

				x = portalData.indexOf("create table " + table + " (");
				y = portalData.indexOf(");", x);

				String portalTableData = portalData.substring(x, y);

				for (int i = 0; i < columns.length; i++) {
					if (portalTableData.indexOf(
							columns[i].trim() + " BOOLEAN") != -1) {

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

		unsyncBufferedReader.close();

		return sb.toString();
	}

	protected String removeInserts(String data) throws IOException {
		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new UnsyncStringReader(data));

		StringBundler sb = new StringBundler();

		String line = null;

		while ((line = unsyncBufferedReader.readLine()) != null) {
			if (!line.startsWith("insert into ") &&
				!line.startsWith("update ")) {

				sb.append(line);
				sb.append("\n");
			}
		}

		unsyncBufferedReader.close();

		return sb.toString();
	}

	protected String removeLongInserts(String data) throws IOException {
		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new UnsyncStringReader(data));

		StringBundler sb = new StringBundler();

		String line = null;

		while ((line = unsyncBufferedReader.readLine()) != null) {
			if (!line.startsWith("insert into Image (") &&
				!line.startsWith("insert into JournalArticle (") &&
				!line.startsWith("insert into JournalStructure (") &&
				!line.startsWith("insert into JournalTemplate (")) {

				sb.append(line);
				sb.append("\n");
			}
		}

		unsyncBufferedReader.close();

		return sb.toString();
	}

	protected String removeNull(String content) {
		content = StringUtil.replace(content, " = null", " = NULL");
		content = StringUtil.replace(content, " is null", " IS NULL");
		content = StringUtil.replace(content, " not null", " not_null");
		content = StringUtil.replace(content, " null", "");
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

			sb.append(_templateMap.get(matched));
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

	protected static String ALTER_COLUMN_TYPE = "alter_column_type ";

	protected static String ALTER_COLUMN_NAME = "alter_column_name ";

	protected static String DROP_INDEX = "drop index";

	protected static String DROP_PRIMARY_KEY = "drop primary key";

	protected static String[] REWORD_TEMPLATE = {
		"@table@", "@old-column@", "@new-column@", "@type@", "@nullable@"
	};

	protected static String[] TEMPLATE = {
		"##", "TRUE", "FALSE",
		"'01/01/1970'", "CURRENT_TIMESTAMP",
		" BLOB", " BOOLEAN", " DATE",
		" DOUBLE", " INTEGER", " LONG",
		" STRING", " TEXT", " VARCHAR",
		" IDENTITY", "COMMIT_TRANSACTION"
	};

	private static final boolean _SUPPORTS_ALTER_COLUMN_NAME = true;

	private static final boolean _SUPPORTS_ALTER_COLUMN_TYPE = true;

	private static final boolean _SUPPORTS_DATE_MILLISECONDS = true;

	private static final boolean _SUPPORTS_INLINE_DISTINCT = true;

	private static final boolean _SUPPORTS_SCROLLABLE_RESULTS = true;

	private static final boolean _SUPPORTS_UPDATE_WITH_INNER_JOIN = true;

	private static Log _log = LogFactoryUtil.getLog(BaseDB.class);

	private static Pattern _templatePattern;
	private static Pattern _timestampPattern = Pattern.compile(
		"SPECIFIC_TIMESTAMP_\\d+");

	private Map<String, String> _templateMap = new HashMap<String, String>();
	private String _type;
	private boolean _supportsStringCaseSensitiveQuery;

	static {
		StringBundler sb = new StringBundler(TEMPLATE.length * 3 - 3);

		for (int i = 0; i < TEMPLATE.length; i++) {
			String variable = TEMPLATE[i];

			if (variable.equals("##") || variable.equals("'01/01/1970'")) {
				sb.append(variable);
			}
			else {
				sb.append(variable);
				sb.append("\\b");
			}

			if (i < TEMPLATE.length - 1) {
				sb.append(StringPool.PIPE);
			}
		}

		_templatePattern = Pattern.compile(sb.toString());
	}

}
/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.db.schema.importer;

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.module.framework.ThrowableCollector;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.db.schema.importer.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.tools.db.schema.importer.jdbc.DataSourceFactoryUtil;

import java.io.File;
import java.io.FileFilter;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.sql.DataSource;

/**
 * @author Mariano Álvaro Sáiz
 */
public class DBSchemaImporterProcess {

	public DBSchemaImporterProcess(
			String path, String sourceJDBCURL, String sourcePassword,
			String sourceUser, String targetJDBCURL, String targetPassword,
			String targetUser)
		throws Exception {

		_path = path;
		_sourceJDBCURL = sourceJDBCURL;
		_sourcePassword = sourcePassword;
		_sourceUser = sourceUser;
		_targetJDBCURL = targetJDBCURL;
		_targetPassword = targetPassword;
		_targetUser = targetUser;

		_sourceDataSource = DataSourceFactoryUtil.initDataSource(
			sourceJDBCURL, sourcePassword, sourceUser);

		_targetDataSource = DataSourceFactoryUtil.initDataSource(
			_targetJDBCURL, _targetPassword, _targetUser);

		_targetCharsetEncoding = _getSessionCharsetEncoding(_targetDataSource);
	}

	public void run() throws Exception {
		_createTables();

		_copyTables();

		_createIndexes();

		_executorService.shutdownNow();

		_executorService.awaitTermination(10, TimeUnit.SECONDS);
	}

	private void _copyTables() throws Exception {
		AutoBatchPreparedStatementUtil.start();

		Set<Future<?>> futures = Collections.newSetFromMap(
			new ConcurrentHashMap<>());
		ThrowableCollector throwableCollector = new ThrowableCollector();

		futures.add(
			_executorService.submit(
				() -> {
					try {
						new DBCopyTablesProcess(
							_sourceDataSource, _targetDataSource
						).run();
					}
					catch (Exception exception) {
						throwableCollector.collect(exception);
					}

					return null;
				}));

		for (String partitionName : _partitionNames) {
			futures.add(
				_executorService.submit(
					() -> {
						try {
							new DBCopyTablesProcess(
								DataSourceFactoryUtil.initDataSource(
									_sourceJDBCURL, _sourcePassword,
									_sourceUser, partitionName),
								DataSourceFactoryUtil.initDataSource(
									_targetJDBCURL, _targetPassword,
									_targetUser, partitionName)
							).run();
						}
						catch (Exception exception) {
							throwableCollector.collect(exception);
						}

						return null;
					}));
		}

		for (Future<?> future : futures) {
			future.get();
		}

		AutoBatchPreparedStatementUtil.stop();

		Throwable throwable = throwableCollector.getThrowable();

		if (throwable != null) {
			ReflectionUtil.throwException(throwable);
		}
	}

	private void _createIndexes() throws Exception {
		_executeFilesSQL("indexes.sql");
	}

	private void _createTables() throws Exception {
		_runSQLTemplate(
			_targetDataSource, _readFile(new File(_path, "tables.sql")));

		_executeFilesSQL("_tables.sql");
	}

	private void _executeFilesSQL(String suffix) throws Exception {
		File[] files = _listFiles(suffix);

		StringBundler sb = new StringBundler();

		int count = 0;

		for (File file : files) {
			sb.append(_readFile(file));

			if ((++count % _COMPANY_BATCH_SIZE) == 0) {
				_runSQLTemplate(_targetDataSource, sb.toString());

				sb.setIndex(0);
			}
		}

		if (sb.index() > 0) {
			_runSQLTemplate(_targetDataSource, sb.toString());
		}
	}

	private String _getSessionCharsetEncoding(DataSource dataSource)
		throws Exception {

		try (Connection connection = dataSource.getConnection()) {
			DatabaseMetaData databaseMetaData = connection.getMetaData();

			if (!StringUtil.startsWith(
					GetterUtil.getString(
						databaseMetaData.getDatabaseProductName()),
					"MySQL")) {

				return null;
			}

			try (PreparedStatement preparedStatement =
					connection.prepareStatement(
						"select variable_value from performance_schema." +
							"session_variables where variable_name = " +
								"'character_set_client'");
				ResultSet resultSet = preparedStatement.executeQuery()) {

				if (resultSet.next()) {
					return resultSet.getString("variable_value");
				}

				return "utf8";
			}
		}
	}

	private File[] _listFiles(String suffix) {
		File dir = new File(_path);

		return dir.listFiles(
			new FileFilter() {

				@Override
				public boolean accept(File file) {
					if (file.isDirectory()) {
						return false;
					}

					return StringUtil.endsWith(file.getName(), suffix);
				}

			});
	}

	private void _preprocessSQLTemplate(String template) throws Exception {
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

			while ((line = unsyncBufferedReader.readLine()) != null) {
				if (line.isEmpty() || line.startsWith("##")) {
					continue;
				}

				sb.append(line);
				sb.append(StringPool.NEW_LINE);

				if (line.endsWith(";")) {
					String sql = sb.toString();

					sb.setIndex(0);

					if (StringUtil.startsWith(sql, "create or replace rule")) {
						_syncFinalSQLs.add(sql);
					}
					else if (StringUtil.startsWith(
								sql, "create schema if not exists")) {

						String[] parts = StringUtil.split(sql, CharPool.SPACE);

						String partitionName = StringUtil.removeChar(
							parts[5], CharPool.SEMICOLON);

						_partitionNames.add(partitionName);

						if (_targetCharsetEncoding != null) {
							_syncInitialSQLs.add(
								StringBundler.concat(
									"create schema if not exists ",
									partitionName, " character set ",
									_targetCharsetEncoding));
						}
						else {
							_syncInitialSQLs.add(sql);
						}
					}
					else {
						_asyncSQLs.add(sql);
					}
				}
			}
		}
	}

	private String _readFile(File file) throws Exception {
		return new String(
			Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
	}

	private void _runSQLTemplate(DataSource dataSource, String template)
		throws Exception {

		_preprocessSQLTemplate(template);

		for (String sql : _syncInitialSQLs) {
			try (Connection connection = dataSource.getConnection();
				Statement statement = connection.createStatement()) {

				statement.executeUpdate(sql);
			}
		}

		_syncInitialSQLs.clear();

		List<Future<?>> futures = new ArrayList<>();
		ThrowableCollector throwableCollector = new ThrowableCollector();

		for (String sql : _asyncSQLs) {
			futures.add(
				_executorService.submit(
					() -> {
						try (Connection connection = dataSource.getConnection();
							Statement statement =
								connection.createStatement()) {

							statement.executeUpdate(sql);
						}
						catch (Exception exception) {
							throwableCollector.collect(exception);
						}
					}));
		}

		_asyncSQLs.clear();

		for (Future<?> future : futures) {
			future.get();
		}

		Throwable throwable = throwableCollector.getThrowable();

		if (throwable != null) {
			ReflectionUtil.throwException(throwable);
		}

		for (String sql : _syncFinalSQLs) {
			try (Connection connection = dataSource.getConnection();
				Statement statement = connection.createStatement()) {

				statement.executeUpdate(sql);
			}
		}

		_syncFinalSQLs.clear();
	}

	private static final int _COMPANY_BATCH_SIZE = 5;

	private final List<String> _asyncSQLs = new ArrayList<>();
	private final ExecutorService _executorService =
		Executors.newFixedThreadPool(5);
	private final List<String> _partitionNames = new ArrayList<>();
	private final String _path;
	private final DataSource _sourceDataSource;
	private final String _sourceJDBCURL;
	private final String _sourcePassword;
	private final String _sourceUser;
	private final List<String> _syncFinalSQLs = new ArrayList<>();
	private final List<String> _syncInitialSQLs = new ArrayList<>();
	private final String _targetCharsetEncoding;
	private final DataSource _targetDataSource;
	private final String _targetJDBCURL;
	private final String _targetPassword;
	private final String _targetUser;

}
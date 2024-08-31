/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.db.schema.importer;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.dao.jdbc.postgresql.PostgreSQLJDBCUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.db.schema.importer.jdbc.AutoBatchPreparedStatementUtil;

import java.io.File;
import java.io.Reader;

import java.math.BigDecimal;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
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
		String path, DataSource sourceDataSource, DataSource targetDataSource) {

		_path = path;
		_sourceDataSource = sourceDataSource;
		_targetDataSource = targetDataSource;
	}

	public void run() throws Exception {
		_runSQLTemplate(
			_targetDataSource, _readFile(new File(_path, "tables.sql")));

		_loadColumnNamesMap(
			_sourceColumnNamesMap, _sourceColumnsType, _sourceDataSource);
		_loadColumnNamesMap(
			_targetColumnNamesMap, _targetColumnsType, _targetDataSource);

		AutoBatchPreparedStatementUtil.start();

		_copyTables();

		AutoBatchPreparedStatementUtil.stop();

		_runSQLTemplate(
			_targetDataSource, _readFile(new File(_path, "indexes.sql")));

		_executorService.shutdownNow();

		_executorService.awaitTermination(10, TimeUnit.SECONDS);
	}

	private void _copyTable(
			Connection sourceConnection, String sourceTableName,
			Connection targetConnection, String targetTableName)
		throws Exception {

		List<String> sourceColumnNames = _sourceColumnNamesMap.get(
			sourceTableName);

		List<String> targetColumnNames = _targetColumnNamesMap.get(
			targetTableName);

		if (sourceColumnNames.size() > targetColumnNames.size()) {
			throw new IllegalStateException(
				StringBundler.concat(
					"Incorrect number of columns for table ", targetTableName,
					". Source has ", sourceColumnNames.size(),
					" and target has ", targetColumnNames.size(),
					StringPool.PERIOD));
		}
		else if (sourceColumnNames.size() < targetColumnNames.size()) {
			Set<String> sourceColumnNamesSet = new TreeSet<String>(
				String.CASE_INSENSITIVE_ORDER) {

				{
					addAll(sourceColumnNames);
				}
			};

			targetColumnNames.removeIf(
				columnName -> !sourceColumnNamesSet.contains(columnName));
		}

		String selectSQL = StringBundler.concat(
			"select ", StringUtil.merge(sourceColumnNames), " from ",
			sourceTableName);

		String insertSQL = StringBundler.concat(
			"insert into ", targetTableName, "(",
			StringUtil.merge(targetColumnNames), ") values (",
			StringUtil.merge(
				Collections.nCopies(targetColumnNames.size(), "?")),
			")");

		try (PreparedStatement preparedStatement1 =
				sourceConnection.prepareStatement(selectSQL);
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					targetConnection, insertSQL)) {

			preparedStatement1.setFetchSize(_FETCH_SIZE);

			try (ResultSet resultSet = preparedStatement1.executeQuery()) {
				while (resultSet.next()) {
					for (int i = 0; i < sourceColumnNames.size(); i++) {
						String columnName = sourceColumnNames.get(i);

						_getAndSetColumn(
							columnName, i + 1, preparedStatement2, resultSet,
							_sourceColumnsType.get(
								sourceTableName + "." + columnName),
							_targetColumnsType.get(
								targetTableName + "." +
									targetColumnNames.get(i)));
					}

					preparedStatement2.addBatch();
				}
			}

			preparedStatement2.executeBatch();
		}
	}

	private void _copyTable(String sourceTableName, String targetTableName) {
		try (Connection sourceConnection = _sourceDataSource.getConnection();
			Connection targetConnection = _targetDataSource.getConnection()) {

			_copyTable(
				sourceConnection, sourceTableName, targetConnection,
				targetTableName);
		}
		catch (Exception exception) {
			_log.error(exception);
		}
	}

	private void _copyTables() throws Exception {
		List<Future<?>> futures = new ArrayList<>();

		ExecutorService executorService = Executors.newFixedThreadPool(5);

		Set<String> sourceTableNames = _sourceColumnNamesMap.keySet();
		Set<String> targetTableNames = _targetColumnNamesMap.keySet();

		Iterator<String> sourceIterator = sourceTableNames.iterator();
		Iterator<String> targetIterator = targetTableNames.iterator();

		while (sourceIterator.hasNext()) {
			String sourceTableName = sourceIterator.next();
			String targetTableName = targetIterator.next();

			futures.add(
				executorService.submit(
					() -> _copyTable(sourceTableName, targetTableName)));
		}

		for (Future<?> future : futures) {
			future.get();
		}
	}

	private void _getAndSetColumn(
			String columnName, int index, PreparedStatement preparedStatement,
			ResultSet resultSet, int sourceType, int targetType)
		throws Exception {

		String alternativeValue = null;

		if ((sourceType == Types.BIGINT) || (sourceType == Types.NUMERIC)) {
			if ((targetType == Types.BINARY) ||
				(targetType == Types.LONGVARBINARY) ||
				(targetType == Types.BLOB)) {

				preparedStatement.setBytes(
					index,
					PostgreSQLJDBCUtil.getLargeObject(resultSet, columnName));

				return;
			}

			long value = resultSet.getLong(columnName);

			if ((value == 0L) && resultSet.wasNull()) {
				preparedStatement.setNull(index, targetType);

				return;
			}

			if ((targetType == Types.BIGINT) || (targetType == Types.NUMERIC)) {
				preparedStatement.setLong(index, value);

				return;
			}

			alternativeValue = String.valueOf(value);
		}
		else if ((sourceType == Types.BINARY) ||
				 (sourceType == Types.LONGVARBINARY)) {

			byte[] value = resultSet.getBytes(columnName);

			if (value == null) {
				preparedStatement.setNull(index, targetType);

				return;
			}

			if ((targetType == Types.BINARY) ||
				(targetType == Types.LONGVARBINARY)) {

				preparedStatement.setBytes(index, value);

				return;
			}
			else if (targetType == Types.BIGINT) {
				PostgreSQLJDBCUtil.setLargeObject(
					preparedStatement, index, value);

				return;
			}

			alternativeValue = new String(value);
		}
		else if (sourceType == Types.BLOB) {
			Blob value = resultSet.getBlob(columnName);

			if (value == null) {
				preparedStatement.setNull(index, targetType);

				return;
			}

			if (targetType == Types.BLOB) {
				preparedStatement.setBlob(index, value);

				return;
			}
			else if (targetType == Types.BIGINT) {
				PostgreSQLJDBCUtil.setLargeObject(
					preparedStatement, index,
					value.getBytes(1, (int)value.length()));

				return;
			}

			alternativeValue = new String(
				value.getBytes(1, (int)value.length()));
		}
		else if ((sourceType == Types.BOOLEAN) || (sourceType == Types.BIT)) {
			boolean value = resultSet.getBoolean(columnName);

			if (!value && resultSet.wasNull()) {
				preparedStatement.setNull(index, targetType);

				return;
			}

			if ((targetType == Types.BOOLEAN) || (targetType == Types.BIT)) {
				preparedStatement.setBoolean(index, value);

				return;
			}

			alternativeValue = value ? "1" : "0";
		}
		else if (sourceType == Types.CLOB) {
			Clob value = resultSet.getClob(columnName);

			if (value == null) {
				preparedStatement.setNull(index, targetType);

				return;
			}

			if (targetType == Types.CLOB) {
				preparedStatement.setClob(index, value);

				return;
			}

			try (Reader reader = value.getCharacterStream();
				UnsyncBufferedReader unsyncBufferedReader =
					new UnsyncBufferedReader(reader)) {

				StringBundler sb = new StringBundler();

				String line = null;

				while ((line = unsyncBufferedReader.readLine()) != null) {
					if (sb.length() != 0) {
						sb.append("\n");
					}

					sb.append(line);
				}

				alternativeValue = sb.toString();
			}
		}
		else if (sourceType == Types.DECIMAL) {
			BigDecimal value = resultSet.getBigDecimal(columnName);

			if (value == null) {
				preparedStatement.setNull(index, targetType);

				return;
			}

			if (targetType == Types.DECIMAL) {
				preparedStatement.setBigDecimal(index, value);

				return;
			}

			alternativeValue = value.toString();
		}
		else if (sourceType == Types.DOUBLE) {
			double value = resultSet.getDouble(columnName);

			if ((value == 0.0) && resultSet.wasNull()) {
				preparedStatement.setNull(index, targetType);

				return;
			}

			if (targetType == Types.DOUBLE) {
				preparedStatement.setDouble(index, value);

				return;
			}

			alternativeValue = String.valueOf(value);
		}
		else if (sourceType == Types.FLOAT) {
			float value = resultSet.getFloat(columnName);

			if ((value == 0.0F) && resultSet.wasNull()) {
				preparedStatement.setNull(index, targetType);

				return;
			}

			if (targetType == Types.FLOAT) {
				preparedStatement.setFloat(index, value);

				return;
			}

			alternativeValue = String.valueOf(value);
		}
		else if (sourceType == Types.INTEGER) {
			int value = resultSet.getInt(columnName);

			if ((value == 0) && resultSet.wasNull()) {
				preparedStatement.setNull(index, targetType);

				return;
			}

			if (targetType == Types.INTEGER) {
				preparedStatement.setInt(index, value);

				return;
			}

			alternativeValue = String.valueOf(value);
		}
		else if ((sourceType == Types.LONGVARCHAR) ||
				 (sourceType == Types.VARCHAR)) {

			String value = resultSet.getString(columnName);

			if (value == null) {
				preparedStatement.setNull(index, targetType);

				return;
			}

			if ((targetType == Types.LONGNVARCHAR) ||
				(targetType == Types.VARCHAR)) {

				preparedStatement.setString(index, value);

				return;
			}

			alternativeValue = value;
		}
		else if (sourceType == Types.TIMESTAMP) {
			Timestamp value = resultSet.getTimestamp(columnName);

			if (value == null) {
				preparedStatement.setNull(index, targetType);

				return;
			}

			if (targetType == Types.TIMESTAMP) {
				preparedStatement.setTimestamp(index, value);

				return;
			}

			alternativeValue = value.toString();
		}
		else if ((sourceType == Types.TINYINT) ||
				 (sourceType == Types.SMALLINT)) {

			short value = resultSet.getShort(columnName);

			if ((value == 0) && resultSet.wasNull()) {
				preparedStatement.setNull(index, targetType);

				return;
			}

			if ((targetType == Types.TINYINT) ||
				(targetType == Types.SMALLINT)) {

				preparedStatement.setShort(index, value);

				return;
			}

			alternativeValue = String.valueOf(value);
		}
		else {
			throw new PortalException("Invalid type " + sourceType);
		}

		_setColumn(index, preparedStatement, targetType, alternativeValue);
	}

	private void _loadColumnNamesMap(
			Map<String, List<String>> columnNamesMap,
			Map<String, Integer> columnTypes, DataSource dataSource)
		throws Exception {

		try (Connection connection = dataSource.getConnection()) {
			DatabaseMetaData databaseMetaData = connection.getMetaData();

			try (ResultSet resultSet = databaseMetaData.getColumns(
					connection.getCatalog(), connection.getSchema(), null,
					null)) {

				while (resultSet.next()) {
					String tableName = resultSet.getString("TABLE_NAME");
					String columnName = resultSet.getString("COLUMN_NAME");

					List<String> columnNames = columnNamesMap.computeIfAbsent(
						tableName, key -> new ArrayList<>());

					columnNames.add(columnName);

					columnTypes.put(
						tableName + "." + columnName,
						resultSet.getInt("DATA_TYPE"));
				}
			}
		}

		for (List<String> columnNames : columnNamesMap.values()) {
			Collections.sort(columnNames, String.CASE_INSENSITIVE_ORDER);
		}
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
						_syncSQLs.add(sql);
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

		List<Future<?>> futures = new ArrayList<>();

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
							_log.error(exception);
						}
					}));
		}

		_asyncSQLs.clear();

		for (Future<?> future : futures) {
			future.get();
		}

		for (String sql : _syncSQLs) {
			try (Connection connection = dataSource.getConnection();
				Statement statement = connection.createStatement()) {

				statement.executeUpdate(sql);
			}
		}

		_syncSQLs.clear();
	}

	private void _setColumn(
			int index, PreparedStatement preparedStatement, int targetType,
			String value)
		throws Exception {

		if ((targetType == Types.BIGINT) || (targetType == Types.NUMERIC)) {
			preparedStatement.setLong(index, GetterUtil.getLong(value));
		}
		else if ((targetType == Types.BIT) || (targetType == Types.BOOLEAN)) {
			preparedStatement.setBoolean(index, GetterUtil.getBoolean(value));
		}
		else if ((targetType == Types.BLOB) ||
				 (targetType == Types.LONGVARBINARY) ||
				 (targetType == Types.BINARY)) {

			preparedStatement.setBytes(index, Base64.decode(value));
		}
		else if ((targetType == Types.CLOB) ||
				 (targetType == Types.LONGVARCHAR) ||
				 (targetType == Types.VARCHAR)) {

			preparedStatement.setString(index, value);
		}
		else if (targetType == Types.DECIMAL) {
			preparedStatement.setBigDecimal(
				index, (BigDecimal)GetterUtil.get(value, BigDecimal.ZERO));
		}
		else if (targetType == Types.DOUBLE) {
			preparedStatement.setDouble(index, GetterUtil.getDouble(value));
		}
		else if (targetType == Types.FLOAT) {
			preparedStatement.setFloat(index, GetterUtil.getFloat(value));
		}
		else if (targetType == Types.INTEGER) {
			preparedStatement.setInt(index, GetterUtil.getInteger(value));
		}
		else if ((targetType == Types.SMALLINT) ||
				 (targetType == Types.TINYINT)) {

			preparedStatement.setShort(index, GetterUtil.getShort(value));
		}
		else if (targetType == Types.TIMESTAMP) {
			DateFormat dateFormat = DateUtil.getISOFormat();

			Date date = dateFormat.parse(value);

			preparedStatement.setTimestamp(
				index, new Timestamp(date.getTime()));
		}
		else {
			throw new PortalException("Invalid type: " + targetType);
		}
	}

	private static final int _FETCH_SIZE = 2500;

	private static final Log _log = LogFactoryUtil.getLog(
		DBSchemaImporterProcess.class);

	private final List<String> _asyncSQLs = new ArrayList<>();
	private final ExecutorService _executorService =
		Executors.newFixedThreadPool(5);
	private final String _path;
	private final Map<String, List<String>> _sourceColumnNamesMap =
		new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
	private final Map<String, Integer> _sourceColumnsType = new HashMap<>();
	private final DataSource _sourceDataSource;
	private final List<String> _syncSQLs = new ArrayList<>();
	private final Map<String, List<String>> _targetColumnNamesMap =
		new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
	private final Map<String, Integer> _targetColumnsType = new HashMap<>();
	private final DataSource _targetDataSource;

}
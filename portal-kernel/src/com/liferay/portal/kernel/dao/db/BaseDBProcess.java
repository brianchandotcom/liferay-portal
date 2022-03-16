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

package com.liferay.portal.kernel.dao.db;

import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.framework.ThrowableCollector;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;
import java.io.InputStream;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

import javax.naming.NamingException;

/**
 * @author Hugo Huijser
 * @author Brian Wing Shun Chan
 */
public abstract class BaseDBProcess implements DBProcess {

	@Override
	public void runSQL(Connection connection, String template)
		throws IOException, SQLException {

		DB db = DBManagerUtil.getDB();

		db.runSQL(connection, template);
	}

	@Override
	public void runSQL(DBTypeToSQLMap dbTypeToSQLMap)
		throws IOException, SQLException {

		DB db = DBManagerUtil.getDB();

		if (connection == null) {
			db.runSQL(dbTypeToSQLMap);
		}
		else {
			db.runSQL(connection, dbTypeToSQLMap);
		}
	}

	@Override
	public void runSQL(String template) throws IOException, SQLException {
		DB db = DBManagerUtil.getDB();

		if (connection == null) {
			db.runSQL(template);
		}
		else {
			db.runSQL(connection, template);
		}
	}

	@Override
	public void runSQL(String[] templates) throws IOException, SQLException {
		DB db = DBManagerUtil.getDB();

		if (connection == null) {
			db.runSQL(templates);
		}
		else {
			db.runSQL(connection, templates);
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

		try (LoggingTimer loggingTimer = new LoggingTimer(path)) {
			ClassLoader classLoader = PortalClassLoaderUtil.getClassLoader();

			InputStream inputStream = classLoader.getResourceAsStream(
				"com/liferay/portal/tools/sql/dependencies/" + path);

			if (inputStream == null) {
				inputStream = classLoader.getResourceAsStream(path);
			}

			if (inputStream == null) {
				Thread currentThread = Thread.currentThread();

				classLoader = currentThread.getContextClassLoader();

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
	}

	@Override
	public void runSQLTemplateString(String template, boolean failOnError)
		throws IOException, NamingException, SQLException {

		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			DB db = DBManagerUtil.getDB();

			if (connection == null) {
				db.runSQLTemplateString(template, failOnError);
			}
			else {
				db.runSQLTemplateString(connection, template, failOnError);
			}
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

	protected void addIndexes(
			Connection connection, List<IndexMetadata> indexMetadatas)
		throws IOException, SQLException {

		DB db = DBManagerUtil.getDB();

		db.addIndexes(connection, indexMetadatas);
	}

	protected void alterColumnName(
			String tableName, String oldColumnName, String newColumnDefinition)
		throws Exception {

		DB db = DBManagerUtil.getDB();

		db.alterColumnName(
			connection, tableName, oldColumnName, newColumnDefinition);
	}

	protected void alterColumnType(
			String tableName, String columnName, String newColumnType)
		throws Exception {

		DB db = DBManagerUtil.getDB();

		db.alterColumnType(connection, tableName, columnName, newColumnType);
	}

	protected void alterTableAddColumn(
			String tableName, String columnName, String columnType)
		throws Exception {

		DB db = DBManagerUtil.getDB();

		db.alterTableAddColumn(connection, tableName, columnName, columnType);
	}

	protected void alterTableDropColumn(String tableName, String columnName)
		throws Exception {

		DB db = DBManagerUtil.getDB();

		db.alterTableDropColumn(connection, tableName, columnName);
	}

	protected boolean doHasTable(String tableName) throws Exception {
		DBInspector dbInspector = new DBInspector(connection);

		return dbInspector.hasTable(tableName, true);
	}

	protected List<IndexMetadata> dropIndexes(
			String tableName, String columnName)
		throws Exception {

		DB db = DBManagerUtil.getDB();

		return db.dropIndexes(connection, tableName, columnName);
	}

	protected String[] getPrimaryKeyColumnNames(
			Connection connection, String tableName)
		throws SQLException {

		DB db = DBManagerUtil.getDB();

		return db.getPrimaryKeyColumnNames(connection, tableName);
	}

	protected boolean hasColumn(String tableName, String columnName)
		throws Exception {

		DBInspector dbInspector = new DBInspector(connection);

		return dbInspector.hasColumn(tableName, columnName);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 *             #hasColumnType(String, String, String)}
	 */
	@Deprecated
	protected boolean hasColumnType(
			Class<?> tableClass, String columnName, String columnType)
		throws Exception {

		DBInspector dbInspector = new DBInspector(connection);

		return dbInspector.hasColumnType(tableClass, columnName, columnType);
	}

	protected boolean hasColumnType(
			String tableName, String columnName, String columnType)
		throws Exception {

		DBInspector dbInspector = new DBInspector(connection);

		return dbInspector.hasColumnType(tableName, columnName, columnType);
	}

	protected boolean hasIndex(String tableName, String indexName)
		throws Exception {

		DBInspector dbInspector = new DBInspector(connection);

		return dbInspector.hasIndex(tableName, indexName);
	}

	protected boolean hasRows(Connection connection, String tableName) {
		DBInspector dbInspector = new DBInspector(connection);

		return dbInspector.hasRows(tableName);
	}

	protected boolean hasRows(String tableName) throws Exception {
		return hasRows(connection, tableName);
	}

	protected boolean hasTable(String tableName) throws Exception {
		DBInspector dbInspector = new DBInspector(connection);

		return dbInspector.hasTable(tableName);
	}

	protected void process(UnsafeConsumer<Long, Exception> unsafeConsumer)
		throws Exception {

		DB db = DBManagerUtil.getDB();

		db.process(unsafeConsumer);
	}

	protected void processConcurrently(
			String sqlQuery,
			UnsafeFunction<ResultSet, Object[], Exception> unsafeFunction,
			UnsafeConsumer<Object[], Exception> unsafeConsumer,
			String exceptionMessage)
		throws Exception {

		int fetchSize = GetterUtil.getInteger(
			PropsUtil.get(PropsKeys.UPGRADE_CONCURRENT_FETCH_SIZE));

		try (Statement statement = connection.createStatement()) {
			statement.setFetchSize(fetchSize);

			try (ResultSet resultSet = statement.executeQuery(sqlQuery)) {
				_processConcurrently(
					() -> {
						if (resultSet.next()) {
							return unsafeFunction.apply(resultSet);
						}

						return null;
					},
					unsafeConsumer, exceptionMessage);
			}
		}
	}

	protected <T> void processConcurrently(
			T[] array, UnsafeConsumer<T, Exception> unsafeConsumer,
			String exceptionMessage)
		throws Exception {

		AtomicInteger atomicInteger = new AtomicInteger();

		_processConcurrently(
			() -> {
				int index = atomicInteger.getAndIncrement();

				if (index < array.length) {
					return array[index];
				}

				return null;
			},
			unsafeConsumer, exceptionMessage);
	}

	protected void removePrimaryKey(String tableName) throws Exception {
		DB db = DBManagerUtil.getDB();

		db.removePrimaryKey(connection, tableName);
	}

	protected Connection connection;

	private <T> void _processConcurrently(
			UnsafeSupplier<T, Exception> unsafeSupplier,
			UnsafeConsumer<T, Exception> unsafeConsumer,
			String exceptionMessage)
		throws Exception {

		Objects.requireNonNull(unsafeSupplier);
		Objects.requireNonNull(unsafeConsumer);

		ExecutorService executorService = Executors.newWorkStealingPool();

		ThrowableCollector throwableCollector = new ThrowableCollector();

		List<Future<Void>> futures = new ArrayList<>();

		try {
			long companyId = CompanyThreadLocal.getCompanyId();

			T next = null;

			while ((next = unsafeSupplier.get()) != null) {
				T current = next;

				Future<Void> future = executorService.submit(
					() -> {
						try (SafeCloseable safeCloseable =
								CompanyThreadLocal.lock(companyId)) {

							unsafeConsumer.accept(current);
						}
						catch (Exception exception) {
							throwableCollector.collect(exception);
						}

						return null;
					});

				int futuresMaxSize = GetterUtil.getInteger(
					PropsUtil.get(
						PropsKeys.
							UPGRADE_CONCURRENT_PROCESS_FUTURE_LIST_MAX_SIZE));

				if (futures.size() >= futuresMaxSize) {
					for (Future<Void> curFuture : futures) {
						curFuture.get();
					}

					futures.clear();
				}

				futures.add(future);
			}
		}
		finally {
			executorService.shutdown();

			for (Future<Void> future : futures) {
				future.get();
			}
		}

		Throwable throwable = throwableCollector.getThrowable();

		if (throwable != null) {
			if (exceptionMessage != null) {
				throw new Exception(exceptionMessage, throwable);
			}

			ReflectionUtil.throwException(throwable);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(BaseDBProcess.class);

}
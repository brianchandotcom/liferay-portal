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

package com.liferay.portal.test.rule;

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.io.unsync.UnsyncPrintWriter;
import com.liferay.portal.kernel.test.rule.ClassTestRule;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.util.PropsImpl;
import com.liferay.portal.util.PropsValues;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.hsqldb.jdbc.JDBCDriver;
import org.hsqldb.server.Server;
import org.hsqldb.server.ServerConstants;

import org.junit.runner.Description;

/**
 * @author William Newbury
 * @author Shuyang Zhou
 */
public class HypersonicServerTestRule extends ClassTestRule<Server> {

	public static final String DATABASE_URL_BASE =
		"jdbc:hsqldb:hsql://localhost/";

	public static final HypersonicServerTestRule INSTANCE;

	@Override
	public void afterClass(Description description, Server server)
		throws Exception {

		try (Connection connection = DriverManager.getConnection(
				DATABASE_URL_BASE + _DATABASE_NAME, "sa", "");
			Statement statement = connection.createStatement()) {

			statement.execute("SHUTDOWN COMPACT");
		}

		server.stop();

		deleteFolder(Paths.get(_HYPERSONIC_TEMP_DIR_NAME));
	}

	@Override
	public Server beforeClass(Description description) throws Exception {
		final CountDownLatch startCountDownLatch = new CountDownLatch(1);

		Server server = new Server() {

			@Override
			public int stop() {
				try (PrintWriter logPrintWriter = getLogWriter();
					PrintWriter errPrintWriter = getErrWriter()) {

					int state = super.stop();

					if (!_shutdownCountDownLatch.await(1, TimeUnit.MINUTES)) {
						throw new IllegalStateException(
							"Unable to shut down Hypersonic " + _DATABASE_NAME);
					}

					return state;
				}
				catch (InterruptedException ie) {
					return ReflectionUtil.throwException(ie);
				}
			}

			@Override
			protected synchronized void setState(int state) {
				super.setState(state);

				if (state == ServerConstants.SERVER_STATE_ONLINE) {
					startCountDownLatch.countDown();
				}
				else if (state == ServerConstants.SERVER_STATE_SHUTDOWN) {
					_shutdownCountDownLatch.countDown();
				}
			}

			private final CountDownLatch _shutdownCountDownLatch =
				new CountDownLatch(1);

		};

		try (Connection connection = DriverManager.getConnection(
				PropsValues.JDBC_DEFAULT_URL, "sa", "");
			Statement statement = connection.createStatement()) {

			statement.execute(
				"BACKUP DATABASE TO '" + _HYPERSONIC_TEMP_DIR_NAME +
					"' BLOCKING AS FILES");
		}

		server.setErrWriter(
			new UnsyncPrintWriter(
				new File(
					_HYPERSONIC_TEMP_DIR_NAME, _DATABASE_NAME + ".err.log")));
		server.setLogWriter(
			new UnsyncPrintWriter(
				new File(
					_HYPERSONIC_TEMP_DIR_NAME, _DATABASE_NAME + ".std.log")));

		server.setDatabaseName(0, _DATABASE_NAME);
		server.setDatabasePath(0, _HYPERSONIC_TEMP_DIR_NAME + _DATABASE_NAME);

		server.start();

		if (!startCountDownLatch.await(1, TimeUnit.MINUTES)) {
			throw new IllegalStateException(
				"Unable to start up Hypersonic " + _DATABASE_NAME);
		}

		return server;
	}

	public List<String> getJdbcProperties() {
		if (_HYPERSONIC) {
			return Arrays.asList(
				"portal:jdbc.default.url=" + _DATABASE_URL,
				"portal:jdbc.default.username=sa",
				"portal:jdbc.default.password=");
		}

		return Collections.emptyList();
	}

	protected void copyFile(
			String fileName, Path fromFolderPath, Path toFolderPath)
		throws IOException {

		Path filePath = fromFolderPath.resolve(fileName);

		if (Files.exists(filePath)) {
			Files.createDirectories(toFolderPath);

			Files.copy(filePath, toFolderPath.resolve(fileName));
		}
	}

	@Override
	protected org.junit.runners.model.Statement createClassStatement(
		org.junit.runners.model.Statement statement, Description description) {

		if (!_HYPERSONIC) {
			return statement;
		}

		return super.createClassStatement(statement, description);
	}

	protected void deleteFolder(Path folderPath) throws IOException {
		if (!Files.exists(folderPath)) {
			return;
		}

		Files.walkFileTree(
			folderPath,
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult postVisitDirectory(
						Path dirPath, IOException ioe)
					throws IOException {

					if (ioe != null) {
						throw ioe;
					}

					Files.delete(dirPath);

					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFile(
						Path filePath, BasicFileAttributes basicFileAttributes)
					throws IOException {

					Files.delete(filePath);

					return FileVisitResult.CONTINUE;
				}

			});
	}

	private HypersonicServerTestRule() {
	}

	private static final String _DATABASE_NAME;

	private static final String _DATABASE_URL;

	private static final boolean _HYPERSONIC;

	private static final String _HYPERSONIC_TEMP_DIR_NAME =
		PropsValues.LIFERAY_HOME + "/data/hypersonic_temp/";

	static {
		Props props = new PropsImpl();

		String className = props.get("jdbc.default.driverClassName");

		_HYPERSONIC = className.equals(JDBCDriver.class.getName());

		if (_HYPERSONIC) {
			String jdbcURL = props.get("jdbc.default.url");

			int index = jdbcURL.lastIndexOf(CharPool.SLASH);

			if (index < 0) {
				throw new ExceptionInInitializerError(
					"Invalid Hypersonic JDBC URL " + jdbcURL);
			}

			String databaseName = jdbcURL.substring(index + 1);

			index = databaseName.indexOf(CharPool.SEMICOLON);

			if (index >= 0) {
				databaseName = databaseName.substring(0, index);
			}

			_DATABASE_NAME = databaseName;

			_DATABASE_URL = DATABASE_URL_BASE + _DATABASE_NAME;
		}
		else {
			_DATABASE_NAME = null;
			_DATABASE_URL = null;
		}

		INSTANCE = new HypersonicServerTestRule();
	}

}
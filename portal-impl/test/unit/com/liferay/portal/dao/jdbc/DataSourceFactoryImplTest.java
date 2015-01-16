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

package com.liferay.portal.dao.jdbc;

import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.dao.jdbc.DataSourceFactoryUtil;
import com.liferay.portal.kernel.process.ProcessCallable;
import com.liferay.portal.kernel.process.ProcessChannel;
import com.liferay.portal.kernel.process.ProcessConfig;
import com.liferay.portal.kernel.process.ProcessException;
import com.liferay.portal.kernel.process.ProcessExecutorUtil;
import com.liferay.portal.kernel.process.local.LocalProcessExecutor;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.util.FileImpl;
import com.liferay.portal.util.InitUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;

import java.io.File;
import java.io.Serializable;

import java.lang.management.ManagementFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Future;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import javax.sql.DataSource;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Dante Wang
 */
public class DataSourceFactoryImplTest {

	@BeforeClass
	public static void setUpClass() {
		ProcessExecutorUtil processExecutorUtil = new ProcessExecutorUtil();

		processExecutorUtil.setProcessExecutor(new LocalProcessExecutor());

		FileUtil fileUtil = new FileUtil();

		fileUtil.setFile(new FileImpl());
	}

	@AfterClass
	public static void tearDownClass() {
		String jarName = PropsUtil.get(
			PropsKeys.SETUP_LIFERAY_POOL_PROVIDER_JAR_NAME,
			new Filter("hikaricp"));

		FileUtil.delete(new File(PropsValues.LIFERAY_LIB_PORTAL_DIR, jarName));
		FileUtil.deltree(new File(_WORKING_DIRECTORY, "liferay"));
	}

	@Test
	public void testHikariCP() throws Exception {
		ProcessConfig.Builder processConfigBuilder =
			new ProcessConfig.Builder();

		List<String> arguments = new ArrayList<>();

		arguments.add(
			"-D" + PropsKeys.LIFERAY_LIB_PORTAL_DIR + "=" +
				PropsValues.LIFERAY_LIB_PORTAL_DIR);
		arguments.add("-Dportal:jdbc.default.liferay.pool.provider=hikaricp");

		processConfigBuilder.setArguments(arguments);
		processConfigBuilder.setBootstrapClassPath(
			System.getProperty("java.class.path"));

		ProcessChannel<TestResult> processChannel = ProcessExecutorUtil.execute(
			processConfigBuilder.build(), new TestProcessCallable());

		Future<TestResult> processNoticeableFuture =
			processChannel.getProcessNoticeableFuture();

		TestResult testResult = processNoticeableFuture.get();

		Assert.assertEquals(
				"com.zaxxer.hikari.HikariDataSource",
				testResult._dataSourceClassName);

		Assert.assertEquals(_CHECKOUT_COUNT, testResult._activeConnections);

		Assert.assertEquals(
			testResult._validConnectionCount, testResult._activeConnections);

		Assert.assertEquals(
			testResult._totalConnections,
			testResult._activeConnections + testResult._idleConnections);
	}

	private static final int _CHECKOUT_COUNT = 5;

	private static final String _WORKING_DIRECTORY = System.getProperty(
		"user.dir");

	private static class TestProcessCallable
		implements ProcessCallable<TestResult> {

		@Override
		public TestResult call() throws ProcessException {
			try {
				InitUtil.init();

				ServerDetector.init(ServerDetector.TOMCAT_ID);

				Properties properties = new Properties();

				properties.setProperty(
					"driverClassName", "org.hsqldb.jdbcDriver");
				properties.setProperty(
					"url", "jdbc:hsqldb:mem:testDB;shutdown=true");
				properties.setProperty("username", "sa");
				properties.setProperty("password", "");
				properties.setProperty("maximumPoolSize", "10");
				properties.setProperty("poolName", "TestJDBCPool");

				DataSource dataSource = DataSourceFactoryUtil.initDataSource(
					properties);

				String dataSourceClassName = dataSource.getClass().getName();

				int validConnectionCount = _CHECKOUT_COUNT;

				for (int i = 0; i < _CHECKOUT_COUNT; i++) {
					Connection connection = dataSource.getConnection();

					try {
						PreparedStatement preparedStatement =
							connection.prepareStatement(_CONNECTION_TEST_QUERY);

						preparedStatement.execute();
					}
					catch (SQLException sqle) {
						validConnectionCount--;
					}
				}

				MBeanServer mBeanServer =
					ManagementFactory.getPlatformMBeanServer();

				ObjectName poolName = new ObjectName(
					"com.zaxxer.hikari:type=Pool (TestJDBCPool)");

				Integer idleConnections = (Integer)mBeanServer.getAttribute(
					poolName, "IdleConnections");

				Integer activeConnections = (Integer)mBeanServer.getAttribute(
					poolName, "ActiveConnections");

				Integer totalConnections = (Integer)mBeanServer.getAttribute(
					poolName, "TotalConnections");

				return new TestResult(
					dataSourceClassName, validConnectionCount,
					activeConnections, idleConnections, totalConnections);
			}
			catch (Exception e) {
				throw new ProcessException(e);
			}
		}

		private static final String _CONNECTION_TEST_QUERY =
			"SELECT 1 FROM INFORMATION_SCHEMA.SYSTEM_USERS";

		private static final long serialVersionUID = 1L;

	}

	private static class TestResult implements Serializable {

		public TestResult(
			String dataSourceClassName, int validConnectionCount,
			int activeConnections, int idleConnections, int totalConnections) {

			_dataSourceClassName = dataSourceClassName;
			_validConnectionCount = validConnectionCount;
			_activeConnections = activeConnections;
			_idleConnections = idleConnections;
			_totalConnections = totalConnections;
		}

		private static final long serialVersionUID = 1L;

		private final int _activeConnections;
		private final String _dataSourceClassName;
		private final int _idleConnections;
		private final int _totalConnections;
		private final int _validConnectionCount;

	}

}
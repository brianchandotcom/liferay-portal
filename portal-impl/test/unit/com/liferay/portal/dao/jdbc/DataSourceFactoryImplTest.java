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
import com.liferay.portal.kernel.test.rule.NewEnv;
import com.liferay.portal.kernel.test.rule.NewEnvTestRule;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.util.InitUtil;
import com.liferay.portal.util.PropsValues;

import java.io.IOException;

import java.lang.management.ManagementFactory;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.sql.Connection;
import java.sql.PreparedStatement;

import java.util.Properties;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Dante Wang
 */
public class DataSourceFactoryImplTest {

	@Before
	public void setUp() {
		_properties.setProperty("driverClassName", "org.hsqldb.jdbcDriver");
		_properties.setProperty("maximumPoolSize", "10");
		_properties.setProperty("password", "");
		_properties.setProperty("poolName", "TestJDBCPool");
		_properties.setProperty("url", "jdbc:hsqldb:mem:testDB;shutdown=true");
		_properties.setProperty("username", "sa");
	}

	@After
	public void tearDown() throws IOException {
		String jarName = PropsUtil.get(
			PropsKeys.SETUP_LIFERAY_POOL_PROVIDER_JAR_NAME,
			new Filter("hikaricp"));

		Files.deleteIfExists(
			Paths.get(PropsValues.LIFERAY_LIB_PORTAL_DIR, jarName));
	}

	@NewEnv(type = NewEnv.Type.JVM)
	@Test
	public void testHikariCP() throws Exception {
		System.setProperty(
			"portal:jdbc.default.liferay.pool.provider", "hikaricp");

		InitUtil.init();

		ServerDetector.init(ServerDetector.TOMCAT_ID);

		DataSource dataSource = DataSourceFactoryUtil.initDataSource(
			_properties);

		Class<?> dataSourceClass = dataSource.getClass();

		Assert.assertEquals(
			"com.zaxxer.hikari.HikariDataSource", dataSourceClass.getName());

		for (int i = 0; i < _CHECKOUT_COUNT; i++) {
			Connection connection = dataSource.getConnection();

			PreparedStatement preparedStatement = connection.prepareStatement(
				_CONNECTION_TEST_QUERY);

			preparedStatement.execute();
		}

		MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();

		ObjectName poolName = new ObjectName(
			"com.zaxxer.hikari:type=Pool (TestJDBCPool)");

		int activeConnections = (int)mBeanServer.getAttribute(
			poolName, "ActiveConnections");

		Assert.assertEquals(_CHECKOUT_COUNT, activeConnections);

		int idleConnections = (int)mBeanServer.getAttribute(
			poolName, "IdleConnections");

		int totalConnections = (int)mBeanServer.getAttribute(
			poolName, "TotalConnections");

		Assert.assertEquals(
			totalConnections, activeConnections + idleConnections);
	}

	@Rule
	public final NewEnvTestRule newEnvTestRule = NewEnvTestRule.INSTANCE;

	private static final int _CHECKOUT_COUNT = 5;

	private static final String _CONNECTION_TEST_QUERY =
		"SELECT 1 FROM INFORMATION_SCHEMA.SYSTEM_USERS";

	private final Properties _properties = new Properties();

}
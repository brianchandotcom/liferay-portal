/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.counter;

import com.liferay.portal.dao.jdbc.DataSourceFactoryImpl;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.dao.jdbc.DataSourceFactoryUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import org.testng.Assert;

/**
 * @author Shuyang Zhou
 */
public class ClusterCounterTest {

	@BeforeClass
	public static void setUpClass() {
		DataSourceFactoryUtil dataSourceFactoryUtil =
			new DataSourceFactoryUtil();

		dataSourceFactoryUtil.setDataSourceFactory(new DataSourceFactoryImpl());
	}

	@Before
	public void setUp() throws Exception {
		_dataSources = new DataSource[_JDBC_URLS.length];

		for (int i = 0; i < _JDBC_URLS.length; i++) {
			_dataSources[i] = _createDataSource(_JDBC_URLS[i]);
		}

		_dropTable();

		_createTable();
	}

	@After
	public void tearDown() throws Exception {
		_dropTable();

		for (DataSource dataSource : _dataSources) {
			DataSourceFactoryUtil.destroyDataSource(dataSource);
		}
	}

	@Test
	public void testIncrement() throws Exception {
		int incrementCount = 1000;

		List<IncrementJob> incrementJobs = new ArrayList<IncrementJob>(
			_dataSources.length);

		for (DataSource dataSource : _dataSources) {
			incrementJobs.add(new IncrementJob(dataSource, incrementCount));
		}

		ExecutorService executorService = Executors.newFixedThreadPool(
			_dataSources.length);

		List<Future<List<Long>>> futures = executorService.invokeAll(
			incrementJobs);

		List<Long> mergedResults = new ArrayList<Long>();

		for (Future<List<Long>> future : futures) {
			mergedResults.addAll(future.get());
		}

		executorService.shutdown();

		Assert.assertEquals(
			incrementCount * _dataSources.length, mergedResults.size());

		Collections.sort(mergedResults);

		for (int i = 0; i < incrementCount * _dataSources.length; i++) {
			Assert.assertEquals(new Long(i), mergedResults.get(i));
		}
	}

	private void _assertTable(DataSource dataSource, boolean hasTable)
		throws SQLException {

		Connection connection = dataSource.getConnection();

		Statement statement = connection.createStatement();

		ResultSet resultSet = null;

		if (hasTable) {
			Assert.assertTrue(statement.execute("desc TEST_COUNTER"));

			resultSet = statement.executeQuery(
				"select currentId from TEST_COUNTER where name='test_counter'");

			Assert.assertTrue(resultSet.next());

			Assert.assertTrue(resultSet.getLong(1) >= 0);
		}
		else {
			try {
				statement.execute("desc TEST_COUNTER");

				Assert.fail();
			}
			catch (SQLException sqle) {
			}
		}

		DataAccess.cleanUp(connection, statement, resultSet);
	}

	private DataSource _createDataSource(String jdbcURL) throws Exception {
		Properties properties = new Properties();

		properties.setProperty("driverClassName", "com.mysql.jdbc.Driver");
		properties.setProperty("url", jdbcURL);
		properties.setProperty("username", _USERNAME);
		properties.setProperty("password", _PASSWORD);
		properties.setProperty("maxPoolSize", "5");
		properties.setProperty("minPoolSize", "1");
		properties.setProperty("numHelperThreads", "1");

		return DataSourceFactoryUtil.initDataSource(properties);
	}

	private void _createTable() throws SQLException {
		DataSource dataSource = _dataSources[0];

		Connection connection = dataSource.getConnection();

		Statement statement = connection.createStatement();

		statement.execute(
			"create table TEST_COUNTER (" +
				"name varchar(75) not null primary key, currentId bigint)" +
					"engine InnoDB");

		statement.execute(
			"insert into TEST_COUNTER values ('test_counter', 0)");

		for (int i = 1; i < _dataSources.length; i++) {
			_assertTable(_dataSources[0], true);
		}

		DataAccess.cleanUp(connection, statement);
	}

	private void _dropTable() {
		DataSource dataSource = _dataSources[0];

		Connection connection = null;

		Statement statement = null;
		try {
			connection = dataSource.getConnection();

			statement = connection.createStatement();

			statement.execute("drop table TEST_COUNTER");

			for (int i = 1; i < _dataSources.length; i++) {
				_assertTable(_dataSources[0], false);
			}
		}
		catch (SQLException sqle) {
		}
		finally {
			DataAccess.cleanUp(connection, statement);
		}
	}

	private static final String[] _JDBC_URLS = {
		"jdbc:mysql://mysql-vm-1/lrdcom_vm_60?" +
			"useUnicode=true&characterEncoding=UTF-8&useFastDateParsing=false",

		"jdbc:mysql://mysql-vm-2/lrdcom_vm_60?" +
			"useUnicode=true&characterEncoding=UTF-8&useFastDateParsing=false",

		"jdbc:mysql://mysql-vm-3/lrdcom_vm_60?" +
			"useUnicode=true&characterEncoding=UTF-8&useFastDateParsing=false"
	};

	private static final String _PASSWORD = "liferay123";

	private static final String _USERNAME = "lrdcom_vm_60";

	private DataSource[] _dataSources;

	private static class IncrementJob implements Callable<List<Long>> {

		public IncrementJob(DataSource dataSource, int incrementCount) {
			_dataSource = dataSource;
			_incrementCount = incrementCount;
		}

		@Override
		public List<Long> call() throws Exception {
			Connection connection = _dataSource.getConnection();

			List<Long> results = new ArrayList<Long>(_incrementCount);

			Statement statement = connection.createStatement();

			connection.setAutoCommit(false);

			for (int i = 0; i < _incrementCount; i++) {
				ResultSet resultSet = statement.executeQuery(
					"select currentId from TEST_COUNTER where " +
						"name='test_counter' for update");

				Assert.assertTrue(resultSet.next());

				long result = resultSet.getLong(1);

				results.add(result);

				result++;

				DataAccess.cleanUp(resultSet);

				Assert.assertEquals(
					1,
					statement.executeUpdate(
						"update TEST_COUNTER set currentId = " + result +
							" where name='test_counter'"));

				connection.commit();
			}

			DataAccess.cleanUp(connection, statement);

			return results;
		}

		private final DataSource _dataSource;
		private final int _incrementCount;

	}

}
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

package com.liferay.portal.tools.db.support.commands;

import com.liferay.portal.tools.db.support.DBSupportArgs;
import com.liferay.portal.tools.db.support.util.DBTestUtil;

import java.io.File;
import java.io.IOException;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 * @author Andrea Di Giorgi
 */
@RunWith(Parameterized.class)
public class CleanServiceBuilderCommandTest extends BaseCommandTestCase {

	public CleanServiceBuilderCommandTest(String mode) throws IOException {
		super(mode);
	}

	@Test
	public void testCleanServiceBuilderDefault() throws Exception {
		_testCleanServiceBuilder(
			"default", "SampleBar", "Sample_Foo", "Sample_User");
	}

	@Test
	public void testCleanServiceBuilderNoAutoNamespace() throws Exception {
		_testCleanServiceBuilder(
			"no-auto-namespace", "Foo", "SampleBar", "User_");
	}

	protected void cleanServiceBuilder(
			File serviceXmlFile, String servletContextName, String url)
		throws Exception {

		CleanServiceBuilderCommand cleanServiceBuilderCommand =
			new CleanServiceBuilderCommand();

		cleanServiceBuilderCommand.setServiceXmlFile(serviceXmlFile);
		cleanServiceBuilderCommand.setServletContextName(_SERVLET_CONTEXT_NAME);

		DBSupportArgs dbSupportArgs = new DBSupportArgs();

		dbSupportArgs.setUrl(url);

		cleanServiceBuilderCommand.execute(dbSupportArgs);
	}

	private void _addReleaseRow(
			PreparedStatement preparedStatement, long releaseId,
			String servletContextName, int buildNumber)
		throws SQLException {

		preparedStatement.setLong(1, 0);
		preparedStatement.setLong(2, releaseId);
		preparedStatement.setString(3, servletContextName);
		preparedStatement.setInt(4, buildNumber);
		preparedStatement.setBoolean(5, true);
		preparedStatement.setInt(6, 1);

		Assert.assertEquals(1, preparedStatement.executeUpdate());
	}

	private void _addServiceComponentRow(
			PreparedStatement preparedStatement, long serviceComponentId,
			String buildNamespace, int buildNumber)
		throws SQLException {

		preparedStatement.setLong(1, 0);
		preparedStatement.setLong(2, serviceComponentId);
		preparedStatement.setString(3, buildNamespace);
		preparedStatement.setInt(4, buildNumber);
		preparedStatement.setLong(5, System.currentTimeMillis());

		Assert.assertEquals(1, preparedStatement.executeUpdate());
	}

	private void _testCleanServiceBuilder(String prefix, String... tableNames)
		throws Exception {

		String url = getUrl(
			"init.sql", prefix + "-tables.sql", prefix + "-indexes.sql");

		try (Connection connection = DriverManager.getConnection(url)) {
			DatabaseMetaData databaseMetaData = connection.getMetaData();

			DBTestUtil.assertTableExists(databaseMetaData, "Release_");
			DBTestUtil.assertTableExists(databaseMetaData, "ServiceComponent");

			for (String tableName : tableNames) {
				DBTestUtil.assertTableExists(databaseMetaData, tableName);
			}

			String sql =
				"insert into Release_ (mvccVersion, releaseId, " +
					"servletContextName, buildNumber, verified, state_) " +
						"values (?, ?, ?, ?, ?, ?)";

			try (PreparedStatement preparedStatement =
					connection.prepareStatement(sql)) {

				_addReleaseRow(
					preparedStatement, 1, _SERVLET_CONTEXT_NAME, _BUILD_NUMBER);
				_addReleaseRow(preparedStatement, 2, "foo.bar", 1);
			}

			sql =
				"insert into ServiceComponent (mvccVersion, " +
					"serviceComponentId, buildNamespace, buildNumber, " +
						"buildDate) values (?, ?, ?, ?, ?)";

			try (PreparedStatement preparedStatement =
					connection.prepareStatement(sql)) {

				for (int buildNumber = 1; buildNumber <= _BUILD_NUMBER;
						buildNumber++) {

					_addServiceComponentRow(
						preparedStatement, buildNumber, _NAMESPACE,
						buildNumber);
				}
			}
		}

		url = getUrl();

		File serviceXmlFile = new File(
			dependenciesDir, prefix + "-service.xml");

		cleanServiceBuilder(serviceXmlFile, _SERVLET_CONTEXT_NAME, url);

		try (Connection connection = DriverManager.getConnection(url)) {
			DBTestUtil.assertTableRowCount(connection, "Release_", 1);
			DBTestUtil.assertTableRowCount(connection, "ServiceComponent", 0);

			DatabaseMetaData databaseMetaData = connection.getMetaData();

			for (String tableName : tableNames) {
				DBTestUtil.assertTableNotExists(databaseMetaData, tableName);
			}
		}
	}

	private static final int _BUILD_NUMBER = 5;

	private static final String _NAMESPACE = "Sample";

	private static final String _SERVLET_CONTEXT_NAME =
		"com.example.sample.service";

}
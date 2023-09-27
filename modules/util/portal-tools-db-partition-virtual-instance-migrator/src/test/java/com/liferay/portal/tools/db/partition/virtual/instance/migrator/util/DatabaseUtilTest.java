/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.db.partition.virtual.instance.migrator.util;

import com.liferay.portal.kernel.version.Version;
import com.liferay.portal.tools.db.partition.virtual.instance.migrator.Release;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import org.junit.Assert;
import org.junit.Test;

import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

/**
 * @author Luis Ortiz
 */
public class DatabaseUtilTest {

	@Test
	public void testGetFailedServletContextNames() throws SQLException {
		_testGetFailedServletContextNames(
			failedServletContextNames -> {
				Assert.assertEquals(
					failedServletContextNames.toString(), 2,
					failedServletContextNames.size());

				Assert.assertTrue(
					failedServletContextNames.contains("module1"));
				Assert.assertTrue(
					failedServletContextNames.contains("module2"));
			},
			false);
		_testGetFailedServletContextNames(
			failedServletContextNames -> Assert.assertTrue(
				failedServletContextNames.isEmpty()),
			true);
	}

	@Test
	public void testGetPartitionedTableNames() throws Exception {

		// Mock _connection

		Mockito.when(
			_connection.getMetaData()
		).thenReturn(
			_databaseMetaData
		);

		PreparedStatement preparedStatement = Mockito.mock(
			PreparedStatement.class);

		Mockito.when(
			_connection.prepareStatement("select companyId from Company")
		).thenReturn(
			preparedStatement
		);

		ResultSet resultSet1 = Mockito.mock(ResultSet.class);

		Mockito.when(
			preparedStatement.executeQuery()
		).thenReturn(
			resultSet1
		);

		Mockito.when(
			resultSet1.getLong("companyId")
		).thenReturn(
			25000L
		);

		Mockito.when(
			resultSet1.next()
		).thenReturn(
			true
		).thenReturn(
			false
		);

		// Mock _databaseMetaData

		ResultSet resultSet2 = Mockito.mock(ResultSet.class);

		Mockito.when(
			_databaseMetaData.getColumns(
				Mockito.nullable(String.class), Mockito.nullable(String.class),
				Mockito.any(), Mockito.nullable(String.class))
		).thenReturn(
			resultSet2
		);

		Mockito.when(
			resultSet2.next()
		).thenReturn(
			false
		);

		ResultSet resultSet3 = Mockito.mock(ResultSet.class);

		Mockito.when(
			_databaseMetaData.getColumns(
				Mockito.nullable(String.class), Mockito.nullable(String.class),
				Mockito.eq("company"), Mockito.nullable(String.class))
		).thenReturn(
			resultSet3
		);

		Mockito.when(
			resultSet2.next()
		).thenReturn(
			true
		);

		Mockito.when(
			_databaseMetaData.getTables(
				Mockito.nullable(String.class), Mockito.nullable(String.class),
				Mockito.nullable(String.class), Mockito.any(String[].class))
		).thenReturn(
			_resultSet
		);

		// Mock _resultSet

		Mockito.when(
			_resultSet.getString("TABLE_NAME")
		).thenReturn(
			"Table1"
		).thenReturn(
			"Company"
		).thenReturn(
			"Table2"
		).thenReturn(
			"Object_x_25000"
		);

		Mockito.when(
			_resultSet.next()
		).thenReturn(
			true
		).thenReturn(
			true
		).thenReturn(
			true
		).thenReturn(
			false
		);

		List<String> tableNames = DatabaseUtil.getPartitionedTableNames(
			_connection);

		Assert.assertEquals(tableNames.toString(), 2, tableNames.size());
		Assert.assertFalse(tableNames.contains("Company"));
		Assert.assertFalse(tableNames.contains("Object_x_25000"));
		Assert.assertTrue(tableNames.contains("Table1"));
		Assert.assertTrue(tableNames.contains("Table2"));
	}

	@Test
	public void testGetReleases() throws SQLException {
		Release module1Release = new Release(
			Version.parseVersion("14.2.4"), "module1", true);
		Release module2Release = new Release(
			Version.parseVersion("2.0.1"), "module2", false);

		Mockito.when(
			_connection.prepareStatement(
				"select servletContextName, schemaVersion, verified from " +
					"Release_")
		).thenReturn(
			_preparedStatement
		);

		Mockito.when(
			_preparedStatement.executeQuery()
		).thenReturn(
			_resultSet
		);

		Mockito.when(
			_resultSet.next()
		).thenReturn(
			true
		).thenReturn(
			true
		).thenReturn(
			false
		);

		Mockito.when(
			_resultSet.getBoolean(3)
		).thenReturn(
			module1Release.getVerified()
		).thenReturn(
			module2Release.getVerified()
		);

		Mockito.when(
			_resultSet.getString(1)
		).thenReturn(
			module1Release.getServletContextName()
		).thenReturn(
			module2Release.getServletContextName()
		);

		Version module1SchemaVersion = module1Release.getSchemaVersion();
		Version module2SchemaVersion = module2Release.getSchemaVersion();

		Mockito.when(
			_resultSet.getString(2)
		).thenReturn(
			module1SchemaVersion.toString()
		).thenReturn(
			module2SchemaVersion.toString()
		);

		List<Release> releases = DatabaseUtil.getReleases(_connection);

		Assert.assertEquals(releases.toString(), 2, releases.size());
		Assert.assertTrue(module1Release.equals(releases.get(0)));
		Assert.assertTrue(module2Release.equals(releases.get(1)));
	}

	@Test
	public void testGetReleasesMap() throws SQLException {
		_testGetReleasesMap(
			(release, releasesMap) -> {
				Assert.assertNotNull(releasesMap.get("module"));

				Assert.assertTrue(release.equals(releasesMap.get("module")));
			},
			new Release(Version.parseVersion("14.2.4"), "module", true));
		_testGetReleasesMap(
			(release, releasesMap) -> Assert.assertNull(
				releasesMap.get("module")),
			null);
	}

	@Test
	public void testHasSingleCompanyInfo() throws SQLException {
		_testHasSingleCompanyInfo(false);
		_testHasSingleCompanyInfo(true);
	}

	@Test
	public void testHasWebId() throws SQLException {
		_testHasWebId(false);
		_testHasWebId(true);
	}

	@Test
	public void testIsDefaultPartition() throws Exception {
		_testIsDefaultPartition(false);
		_testIsDefaultPartition(true);
	}

	private void _testGetFailedServletContextNames(
			Consumer<List<String>> consumer, boolean state)
		throws SQLException {

		Mockito.when(
			_connection.prepareStatement(
				"select servletContextName from Release_ where state_ != 0;")
		).thenReturn(
			_preparedStatement
		);

		Mockito.when(
			_preparedStatement.executeQuery()
		).thenReturn(
			_resultSet
		);

		if (state) {
			Mockito.when(
				_resultSet.next()
			).thenReturn(
				false
			);
		}
		else {
			Mockito.when(
				_resultSet.getString(1)
			).thenReturn(
				"module1"
			).thenReturn(
				"module2"
			);

			Mockito.when(
				_resultSet.next()
			).thenReturn(
				true
			).thenReturn(
				true
			).thenReturn(
				false
			);
		}

		consumer.accept(DatabaseUtil.getFailedServletContextNames(_connection));
	}

	private void _testGetReleasesMap(
			BiConsumer<Release, Map<String, Release>> biConsumer,
			Release release)
		throws SQLException {

		Mockito.when(
			_connection.prepareStatement(
				"select servletContextName, schemaVersion, verified from " +
					"Release_")
		).thenReturn(
			_preparedStatement
		);

		Mockito.when(
			_preparedStatement.executeQuery()
		).thenReturn(
			_resultSet
		);

		if (release != null) {
			Mockito.when(
				_resultSet.getBoolean(3)
			).thenReturn(
				release.getVerified()
			);

			Mockito.when(
				_resultSet.getString(1)
			).thenReturn(
				release.getServletContextName()
			);

			Version releaseVersion = release.getSchemaVersion();

			Mockito.when(
				_resultSet.getString(2)
			).thenReturn(
				releaseVersion.toString()
			);

			Mockito.when(
				_resultSet.next()
			).thenReturn(
				true
			).thenReturn(
				false
			);
		}
		else {
			Mockito.when(
				_resultSet.next()
			).thenReturn(
				false
			);
		}

		biConsumer.accept(release, DatabaseUtil.getReleasesMap(_connection));
	}

	private void _testHasSingleCompanyInfo(boolean singleCompanyInfo)
		throws SQLException {

		Mockito.when(
			_connection.prepareStatement("select count(1) from CompanyInfo")
		).thenReturn(
			_preparedStatement
		);

		Mockito.when(
			_preparedStatement.executeQuery()
		).thenReturn(
			_resultSet
		);

		Mockito.when(
			_resultSet.getInt(1)
		).thenReturn(
			singleCompanyInfo ? 1 : 4
		);

		Mockito.when(
			_resultSet.next()
		).thenReturn(
			true
		);

		Assert.assertEquals(
			singleCompanyInfo, DatabaseUtil.hasSingleCompanyInfo(_connection));
	}

	private void _testHasWebId(boolean hasWebId) throws SQLException {
		Mockito.reset(_preparedStatement);

		Mockito.when(
			_connection.prepareStatement(
				"select companyId from Company where webId = ?")
		).thenReturn(
			_preparedStatement
		);

		Mockito.when(
			_preparedStatement.executeQuery()
		).thenReturn(
			_resultSet
		);

		Mockito.when(
			_resultSet.next()
		).thenReturn(
			hasWebId
		);

		Assert.assertEquals(
			hasWebId, DatabaseUtil.hasWebId(_connection, "webId"));

		ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(
			String.class);

		Mockito.verify(
			_preparedStatement
		).setString(
			Mockito.eq(1), argumentCaptor.capture()
		);

		Assert.assertEquals("webId", argumentCaptor.getValue());
	}

	private void _testIsDefaultPartition(boolean defaultPartition)
		throws Exception {

		Mockito.when(
			_connection.getMetaData()
		).thenReturn(
			_databaseMetaData
		);

		Mockito.when(
			_databaseMetaData.getTables(
				Mockito.nullable(String.class), Mockito.nullable(String.class),
				Mockito.eq("company"), Mockito.nullable(String[].class))
		).thenReturn(
			_resultSet
		);

		Mockito.when(
			_databaseMetaData.storesLowerCaseIdentifiers()
		).thenReturn(
			true
		);

		Mockito.when(
			_resultSet.next()
		).thenReturn(
			defaultPartition
		);

		Assert.assertEquals(
			defaultPartition, DatabaseUtil.isDefaultPartition(_connection));
	}

	private final Connection _connection = Mockito.mock(Connection.class);
	private final DatabaseMetaData _databaseMetaData = Mockito.mock(
		DatabaseMetaData.class);
	private final PreparedStatement _preparedStatement = Mockito.mock(
		PreparedStatement.class);
	private final ResultSet _resultSet = Mockito.mock(ResultSet.class);

}
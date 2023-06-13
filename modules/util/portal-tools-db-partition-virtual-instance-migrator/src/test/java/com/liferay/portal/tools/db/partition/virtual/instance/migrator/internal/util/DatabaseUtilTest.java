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

package com.liferay.portal.tools.db.partition.virtual.instance.migrator.internal.util;

import com.liferay.portal.kernel.version.Version;
import com.liferay.portal.tools.db.partition.virtual.instance.migrator.internal.release.Release;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

/**
 * @author Luis Ortiz
 */
public class DatabaseUtilTest {

	@Test
	public void testGetPartitionedTableNames() throws Exception {
		Mockito.when(
			_connection.getMetaData()
		).thenReturn(
			_databaseMetaData
		);

		Mockito.when(
			_databaseMetaData.getTables(
				Mockito.nullable(String.class), Mockito.nullable(String.class),
				Mockito.nullable(String.class), Mockito.any(String[].class))
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
			true
		).thenReturn(
			false
		);

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

		PreparedStatement preparedStatement1 = Mockito.mock(
			PreparedStatement.class);

		ResultSet resultSet1 = Mockito.mock(ResultSet.class);

		Mockito.when(
			_connection.prepareStatement("select companyId from Company")
		).thenReturn(
			preparedStatement1
		);

		Mockito.when(
			preparedStatement1.executeQuery()
		).thenReturn(
			resultSet1
		);

		Mockito.when(
			resultSet1.next()
		).thenReturn(
			true
		).thenReturn(
			false
		);

		Mockito.when(
			resultSet1.getLong("companyId")
		).thenReturn(
			25000L
		);

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

		List<String> tableNames = DatabaseUtil.getPartitionedTableNames(
			_connection);

		Assert.assertEquals(tableNames.toString(), 2, tableNames.size());

		Assert.assertTrue(tableNames.contains("Table1"));
		Assert.assertTrue(tableNames.contains("Table2"));
		Assert.assertFalse(tableNames.contains("Company"));
		Assert.assertFalse(tableNames.contains("Object_x_25000"));
	}

	@Test
	public void testGetReleases() throws SQLException {
		Release module1Release = new Release(
			"module1", Version.parseVersion("14.2.4"), true);
		Release module2Release = new Release(
			"module2", Version.parseVersion("2.0.1"), false);

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

		Mockito.when(
			_resultSet.getBoolean(3)
		).thenReturn(
			module1Release.getVerified()
		).thenReturn(
			module2Release.getVerified()
		);

		List<Release> releases = DatabaseUtil.getReleases(_connection);

		Assert.assertEquals(releases.toString(), 2, releases.size());

		Release module1Entry = releases.get(0);

		Assert.assertTrue(module1Entry.equals(module1Release));

		Release module2Entry = releases.get(1);

		Assert.assertTrue(module2Entry.equals(module2Release));
	}

	@Test
	public void testGetReleasesMapEntry() throws SQLException {
		Release release = new Release(
			"module", Version.parseVersion("14.2.4"), true);

		_mockGetReleasesMap(release, true);

		Map<String, Release> releasesMap = DatabaseUtil.getReleasesMap(
			_connection);

		Assert.assertNotNull(releasesMap.get("module"));

		Assert.assertTrue(release.equals(releasesMap.get("module")));
	}

	@Test
	public void testGetReleasesMapNotFoundEntry() throws SQLException {
		Release release = new Release(
			"module", Version.parseVersion("14.2.4"), true);

		_mockGetReleasesMap(release, false);

		Map<String, Release> releasesMap = DatabaseUtil.getReleasesMap(
			_connection);

		Assert.assertNull(releasesMap.get("module"));
	}

	@Test
	public void testHasNotWebId() throws SQLException {
		_mockWebId(false);

		Assert.assertFalse(DatabaseUtil.hasWebId(_connection, "webId"));

		ArgumentCaptor<String> valueCapture = ArgumentCaptor.forClass(
			String.class);

		Mockito.verify(
			_preparedStatement
		).setString(
			Mockito.eq(1), valueCapture.capture()
		);
		Assert.assertEquals("webId", valueCapture.getValue());
	}

	@Test
	public void testHasWebId() throws SQLException {
		_mockWebId(true);

		Assert.assertTrue(DatabaseUtil.hasWebId(_connection, "webId"));

		ArgumentCaptor<String> valueCapture = ArgumentCaptor.forClass(
			String.class);

		Mockito.verify(
			_preparedStatement
		).setString(
			Mockito.eq(1), valueCapture.capture()
		);
		Assert.assertEquals("webId", valueCapture.getValue());
	}

	@Test
	public void testInvalidReleaseState() throws SQLException {
		_mockReleaseState(false);

		List<String> failedServletContextNames =
			DatabaseUtil.getFailedServletContextNames(_connection);

		Assert.assertEquals(
			failedServletContextNames.toString(), 2,
			failedServletContextNames.size());

		Assert.assertTrue(failedServletContextNames.contains("module1"));
		Assert.assertTrue(failedServletContextNames.contains("module2"));
	}

	@Test
	public void testIsDefaultPartition() throws Exception {
		_mockDefaultPartition(true);

		Assert.assertTrue(DatabaseUtil.isDefaultPartition(_connection));
	}

	@Test
	public void testIsNotDefaultPartition() throws Exception {
		_mockDefaultPartition(false);

		Assert.assertFalse(DatabaseUtil.isDefaultPartition(_connection));
	}

	@Test
	public void testIsNotSingleVirtualInstance() throws SQLException {
		_mockSingleVirtualInstance(false);

		Assert.assertFalse(DatabaseUtil.isSingleVirtualInstance(_connection));
	}

	@Test
	public void testIsSingleVirtualInstance() throws SQLException {
		_mockSingleVirtualInstance(true);

		Assert.assertTrue(DatabaseUtil.isSingleVirtualInstance(_connection));
	}

	@Test
	public void testValidReleaseState() throws SQLException {
		_mockReleaseState(true);

		List<String> failedServletContextNames =
			DatabaseUtil.getFailedServletContextNames(_connection);

		Assert.assertTrue(failedServletContextNames.isEmpty());
	}

	private void _mockDefaultPartition(boolean defaultPartition)
		throws Exception {

		Mockito.when(
			_connection.getMetaData()
		).thenReturn(
			_databaseMetaData
		);

		Mockito.when(
			_databaseMetaData.storesLowerCaseIdentifiers()
		).thenReturn(
			true
		);

		Mockito.when(
			_databaseMetaData.getTables(
				Mockito.nullable(String.class), Mockito.nullable(String.class),
				Mockito.eq("company"), Mockito.nullable(String[].class))
		).thenReturn(
			_resultSet
		);

		Mockito.when(
			_resultSet.next()
		).thenReturn(
			defaultPartition
		);
	}

	private void _mockGetReleasesMap(Release release, boolean found)
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

		if (found) {
			Mockito.when(
				_resultSet.next()
			).thenReturn(
				true
			).thenReturn(
				false
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
				_resultSet.getBoolean(3)
			).thenReturn(
				release.getVerified()
			);
		}
		else {
			Mockito.when(
				_resultSet.next()
			).thenReturn(
				false
			);
		}
	}

	private void _mockReleaseState(boolean stateGood) throws SQLException {
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

		if (stateGood) {
			Mockito.when(
				_resultSet.next()
			).thenReturn(
				false
			);
		}
		else {
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
				_resultSet.getString(1)
			).thenReturn(
				"module1"
			).thenReturn(
				"module2"
			);
		}
	}

	private void _mockSingleVirtualInstance(boolean singleVirtualInstance)
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
			singleVirtualInstance ? 1 : 4
		);

		Mockito.when(
			_resultSet.next()
		).thenReturn(
			true
		);
	}

	private void _mockWebId(boolean hasWebId) throws SQLException {
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
	}

	private final Connection _connection = Mockito.mock(Connection.class);
	private final DatabaseMetaData _databaseMetaData = Mockito.mock(
		DatabaseMetaData.class);
	private final PreparedStatement _preparedStatement = Mockito.mock(
		PreparedStatement.class);
	private final ResultSet _resultSet = Mockito.mock(ResultSet.class);

}
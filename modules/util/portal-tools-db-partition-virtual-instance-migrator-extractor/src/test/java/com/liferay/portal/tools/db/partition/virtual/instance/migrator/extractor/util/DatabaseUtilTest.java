/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.db.partition.virtual.instance.migrator.extractor.util;

import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.version.Version;
import com.liferay.portal.tools.db.partition.virtual.instance.migrator.common.Company;
import com.liferay.portal.tools.db.partition.virtual.instance.migrator.common.InstanceData;
import com.liferay.portal.tools.db.partition.virtual.instance.migrator.common.Release;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/**
 * @author Luis Ortiz
 */
public class DatabaseUtilTest {

	@Before
	public void setUp() throws SQLException {
		Mockito.when(
			_connection.getMetaData()
		).thenReturn(
			_databaseMetaData
		);

		Mockito.when(
			_databaseMetaData.getURL()
		).thenReturn(
			RandomTestUtil.randomString()
		);

		ResultSet resultSet = Mockito.mock(ResultSet.class);

		Mockito.when(
			_databaseMetaData.getTables(
				Mockito.nullable(String.class), Mockito.nullable(String.class),
				Mockito.nullable(String.class), Mockito.any(String[].class))
		).thenReturn(
			resultSet
		);

		PreparedStatement preparedStatement = Mockito.mock(
			PreparedStatement.class);

		Mockito.when(
			_connection.prepareStatement(Mockito.any())
		).thenReturn(
			preparedStatement
		);

		Mockito.when(
			preparedStatement.executeQuery()
		).thenReturn(
			resultSet
		);
	}

	@Test
	public void testCompanyId() throws Exception {
		List<Long> companies = new ArrayList<>();

		companies.add(RandomTestUtil.randomLong());

		_testCompanyId(
			companies,
			instanceData -> Assert.assertEquals(
				companies.get(0), instanceData.getCompanyId()));

		companies.add(RandomTestUtil.randomLong());

		_testCompanyId(
			companies,
			instanceData -> Assert.assertNull(instanceData.getCompanyId()));
	}

	@Test
	public void testDefaultPartition() throws Exception {
		_testDefaultPartition(
			true,
			instanceData -> Assert.assertTrue(
				instanceData.isDefaultPartition()));

		_testDefaultPartition(
			false,
			instanceData -> Assert.assertFalse(
				instanceData.isDefaultPartition()));
	}

	@Test
	public void testGetCompanies() throws Exception {
		Company company1 = new Company(
			RandomTestUtil.randomLong(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString());
		Company company2 = new Company(
			RandomTestUtil.randomLong(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString());

		Mockito.when(
			_connection.prepareStatement(
				"select Company.companyId, webId, name, hostname from " +
					"Company left join VirtualHost on Company.companyId = " +
						"VirtualHost.companyId")
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
			_resultSet.getLong(1)
		).thenReturn(
			company1.getCompanyId()
		).thenReturn(
			company2.getCompanyId()
		);

		Mockito.when(
			_resultSet.getString(2)
		).thenReturn(
			company1.getWebId()
		).thenReturn(
			company2.getWebId()
		);

		Mockito.when(
			_resultSet.getString(3)
		).thenReturn(
			company1.getCompanyName()
		).thenReturn(
			company2.getCompanyName()
		);

		Mockito.when(
			_resultSet.getString(4)
		).thenReturn(
			company1.getVirtualHostName()
		).thenReturn(
			company2.getVirtualHostName()
		);

		InstanceData instanceData = DatabaseUtil.exportInstanceData(
			_connection);

		List<Company> companies = instanceData.getCompanies();

		Assert.assertEquals(companies.toString(), 2, companies.size());
		Assert.assertEquals(company1, companies.get(0));
		Assert.assertEquals(company2, companies.get(1));
	}

	@Test
	public void testGetPartitionedTableNames() throws Exception {

		// Mock _connection

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
			true
		).thenReturn(
			false
		);

		InstanceData instanceData = DatabaseUtil.exportInstanceData(
			_connection);

		List<String> tableNames = instanceData.getTableNames();

		Assert.assertEquals(tableNames.toString(), 2, tableNames.size());
		Assert.assertFalse(tableNames.contains("Company"));
		Assert.assertFalse(tableNames.contains("Object_x_25000"));
		Assert.assertTrue(tableNames.contains("Table1"));
		Assert.assertTrue(tableNames.contains("Table2"));
	}

	@Test
	public void testGetReleases() throws Exception {
		Release module1Release = new Release(
			Version.parseVersion("14.2.4"), "module1", 0, true);
		Release module2Release = new Release(
			Version.parseVersion("2.0.1"), "module2", 1, false);

		Mockito.when(
			_connection.prepareStatement(
				"select servletContextName, schemaVersion, state_, verified " +
					"from Release_")
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
			_resultSet.getBoolean(4)
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

		Mockito.when(
			_resultSet.getInt(3)
		).thenReturn(
			module1Release.getState()
		).thenReturn(
			module2Release.getState()
		);

		InstanceData instanceData = DatabaseUtil.exportInstanceData(
			_connection);

		List<Release> releases = instanceData.getReleases();

		Assert.assertEquals(releases.toString(), 2, releases.size());
		Assert.assertEquals(module1Release, releases.get(0));
		Assert.assertEquals(module2Release, releases.get(1));
	}

	private void _testCompanyId(
			List<Long> companies, Consumer<InstanceData> consumer)
		throws Exception {

		Mockito.when(
			_connection.prepareStatement("select companyId from CompanyInfo")
		).thenReturn(
			_preparedStatement
		);

		Mockito.when(
			_preparedStatement.executeQuery()
		).thenReturn(
			_resultSet
		);

		final List<Integer> nextCounter = new ArrayList<>();

		nextCounter.add(0);

		Mockito.when(
			_resultSet.next()
		).thenAnswer(
			new Answer<Boolean>() {

				@Override
				public Boolean answer(InvocationOnMock invocationOnMock)
					throws Throwable {

					int counter = nextCounter.get(0);

					if (counter >= companies.size()) {
						return false;
					}

					nextCounter.set(0, ++counter);

					return true;
				}

			}
		);

		final List<Integer> getLongCounter = new ArrayList<>();

		getLongCounter.add(0);

		Mockito.when(
			_resultSet.getLong(1)
		).thenAnswer(
			new Answer<Long>() {

				@Override
				public Long answer(InvocationOnMock invocationOnMock)
					throws Throwable {

					int counter = getLongCounter.get(0);

					if (counter >= companies.size()) {
						throw new IndexOutOfBoundsException();
					}

					return companies.get(counter);
				}

			}
		);

		consumer.accept(DatabaseUtil.exportInstanceData(_connection));
	}

	private void _testDefaultPartition(
			boolean defaultPartition, Consumer<InstanceData> consumer)
		throws Exception {

		Mockito.when(
			_databaseMetaData.getTables(
				Mockito.nullable(String.class), Mockito.nullable(String.class),
				Mockito.eq("Company"), Mockito.any(String[].class))
		).thenReturn(
			_resultSet
		);

		Mockito.when(
			_resultSet.next()
		).thenReturn(
			defaultPartition
		);

		consumer.accept(DatabaseUtil.exportInstanceData(_connection));
	}

	private final Connection _connection = Mockito.mock(Connection.class);
	private final DatabaseMetaData _databaseMetaData = Mockito.mock(
		DatabaseMetaData.class);
	private final PreparedStatement _preparedStatement = Mockito.mock(
		PreparedStatement.class);
	private final ResultSet _resultSet = Mockito.mock(ResultSet.class);

}
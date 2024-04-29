/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.db.partition.migration.validator;

import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.version.Version;
import com.liferay.portal.tools.db.partition.migration.validator.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/**
 * @author Luis Ortiz
 */
public class LiferayDatabaseTest {

	@Before
	public void setUp() throws SQLException {
		_mockGetColumns(Collections.emptyList());
		_mockGetCompanies(Collections.emptyList());
		_mockGetCompanyIds(Collections.emptyList());
		_mockGetCompanyInfos(Collections.emptyList());
		_mockGetConnection(
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString());
		_mockGetReleases(Collections.emptyList());
		_mockGetTables(true);
	}

	@Test
	public void testGetCompanies() throws Exception {
		Company company1 = new Company(
			RandomTestUtil.randomLong(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString());
		Company company2 = new Company(
			RandomTestUtil.randomLong(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString());

		_mockGetCompanies(Arrays.asList(company1, company2));

		LiferayDatabase liferayDatabase = DatabaseUtil.exportLiferayDatabase(
			_connection);

		List<Company> companies = liferayDatabase.getCompanies();

		Assert.assertEquals(companies.toString(), 2, companies.size());
		Assert.assertEquals(company1, companies.get(0));
		Assert.assertEquals(company2, companies.get(1));
	}

	@Test
	public void testGetExportedCompanyId() throws Exception {
		List<Long> companyIds = new ArrayList<>();

		companyIds.add(RandomTestUtil.randomLong());

		_testGetExportedCompanyId(
			companyIds,
			liferayDatabase -> Assert.assertEquals(
				companyIds.get(0),
				(Long)liferayDatabase.getExportedCompanyId()));

		companyIds.add(RandomTestUtil.randomLong());

		try {
			_testGetExportedCompanyId(
				companyIds, liferayDatabase -> Assert.fail());
		}
		catch (Exception exception) {
			Assert.assertTrue(
				exception instanceof UnsupportedOperationException);
			Assert.assertEquals(
				"Database schema has to have a single company or database " +
					"partitioning must be enabled",
				exception.getMessage());
		}
	}

	@Test
	public void testGetReleases() throws Exception {
		Release module1Release = new Release(
			Version.parseVersion("14.2.4"), "module1", 0, true);
		Release module2Release = new Release(
			Version.parseVersion("2.0.1"), "module2", 1, false);

		_mockGetReleases(Arrays.asList(module1Release, module2Release));

		LiferayDatabase liferayDatabase = DatabaseUtil.exportLiferayDatabase(
			_connection);

		List<Release> releases = liferayDatabase.getReleases();

		Assert.assertEquals(releases.toString(), 2, releases.size());
		Assert.assertEquals(module1Release, releases.get(0));
		Assert.assertEquals(module2Release, releases.get(1));
	}

	@Test
	public void testGetTableNames() throws Exception {
		_mockGetColumns(
			Arrays.asList("Table1", "Company", "Table2", "Object_x_25000"));
		_mockGetCompanyIds(Collections.singletonList(25000L));

		LiferayDatabase liferayDatabase = DatabaseUtil.exportLiferayDatabase(
			_connection);

		List<String> tableNames = liferayDatabase.getTableNames();

		Assert.assertEquals(tableNames.toString(), 2, tableNames.size());
		Assert.assertFalse(tableNames.contains("Company"));
		Assert.assertFalse(tableNames.contains("Object_x_25000"));
		Assert.assertTrue(tableNames.contains("Table1"));
		Assert.assertTrue(tableNames.contains("Table2"));
	}

	@Test
	public void testIsExportedCompanyDefault() throws Exception {
		_testIsExportedCompanyDefault(
			liferayDatabase -> Assert.assertFalse(
				liferayDatabase.isExportedCompanyDefault()),
			false);
		_testIsExportedCompanyDefault(
			liferayDatabase -> Assert.assertTrue(
				liferayDatabase.isExportedCompanyDefault()),
			true);
	}

	private void _mockGetColumns(List<String> tableNames) throws SQLException {
		ResultSet resultSet1 = Mockito.mock(ResultSet.class);

		Mockito.when(
			_databaseMetaData.getColumns(
				Mockito.nullable(String.class), Mockito.nullable(String.class),
				Mockito.any(), Mockito.nullable(String.class))
		).thenReturn(
			resultSet1
		);

		Mockito.when(
			resultSet1.next()
		).thenReturn(
			true
		);

		ResultSet resultSet2 = Mockito.mock(ResultSet.class);

		Mockito.when(
			_databaseMetaData.getTables(
				Mockito.nullable(String.class), Mockito.nullable(String.class),
				Mockito.isNull(), Mockito.any(String[].class))
		).thenReturn(
			resultSet2
		);

		final List<Integer> nextCounter = new ArrayList<>();

		nextCounter.add(0);

		Mockito.when(
			resultSet2.next()
		).thenAnswer(
			new Answer<Boolean>() {

				@Override
				public Boolean answer(InvocationOnMock invocationOnMock)
					throws Throwable {

					int counter = nextCounter.get(0);

					if (counter >= tableNames.size()) {
						return false;
					}

					nextCounter.set(0, ++counter);

					return true;
				}

			}
		);

		final List<Integer> getStringCounter = new ArrayList<>();

		getStringCounter.add(0);

		Mockito.when(
			resultSet2.getString("TABLE_NAME")
		).thenAnswer(
			new Answer<String>() {

				@Override
				public String answer(InvocationOnMock invocationOnMock)
					throws Throwable {

					int counter = getStringCounter.get(0);

					if (counter >= tableNames.size()) {
						throw new IndexOutOfBoundsException();
					}

					getStringCounter.set(0, ++counter);

					return tableNames.get(counter - 1);
				}

			}
		);
	}

	private void _mockGetCompanies(List<Company> companies)
		throws SQLException {

		PreparedStatement preparedStatement = Mockito.mock(
			PreparedStatement.class);
		ResultSet resultSet = Mockito.mock(ResultSet.class);

		Mockito.when(
			_connection.prepareStatement(
				"select Company.companyId, webId, name, hostname from " +
					"Company left join VirtualHost on Company.companyId = " +
						"VirtualHost.companyId")
		).thenReturn(
			preparedStatement
		);

		Mockito.when(
			preparedStatement.executeQuery()
		).thenReturn(
			resultSet
		);

		final List<Integer> nextCounter = new ArrayList<>();

		nextCounter.add(0);

		Mockito.when(
			resultSet.next()
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

		final List<Integer> companyIdCounter = new ArrayList<>();

		companyIdCounter.add(0);

		Mockito.when(
			resultSet.getLong(1)
		).thenAnswer(
			new Answer<Long>() {

				@Override
				public Long answer(InvocationOnMock invocationOnMock)
					throws Throwable {

					int counter = companyIdCounter.get(0);

					if (counter >= companies.size()) {
						throw new IndexOutOfBoundsException();
					}

					companyIdCounter.set(0, ++counter);

					return companies.get(
						counter - 1
					).getCompanyId();
				}

			}
		);

		final List<Integer> webIdCounter = new ArrayList<>();

		webIdCounter.add(0);

		Mockito.when(
			resultSet.getString(2)
		).thenAnswer(
			new Answer<String>() {

				@Override
				public String answer(InvocationOnMock invocationOnMock)
					throws Throwable {

					int counter = webIdCounter.get(0);

					if (counter >= companies.size()) {
						throw new IndexOutOfBoundsException();
					}

					webIdCounter.set(0, ++counter);

					return companies.get(
						counter - 1
					).getWebId();
				}

			}
		);

		final List<Integer> companyNameCounter = new ArrayList<>();

		companyNameCounter.add(0);

		Mockito.when(
			resultSet.getString(3)
		).thenAnswer(
			new Answer<String>() {

				@Override
				public String answer(InvocationOnMock invocationOnMock)
					throws Throwable {

					int counter = companyNameCounter.get(0);

					if (counter >= companies.size()) {
						throw new IndexOutOfBoundsException();
					}

					companyNameCounter.set(0, ++counter);

					return companies.get(
						counter - 1
					).getCompanyName();
				}

			}
		);

		final List<Integer> virtualHostCounter = new ArrayList<>();

		virtualHostCounter.add(0);

		Mockito.when(
			resultSet.getString(4)
		).thenAnswer(
			new Answer<String>() {

				@Override
				public String answer(InvocationOnMock invocationOnMock)
					throws Throwable {

					int counter = virtualHostCounter.get(0);

					if (counter >= companies.size()) {
						throw new IndexOutOfBoundsException();
					}

					virtualHostCounter.set(0, ++counter);

					return companies.get(
						counter - 1
					).getVirtualHostname();
				}

			}
		);
	}

	private void _mockGetCompanyIds(List<Long> companyIds) throws SQLException {
		PreparedStatement preparedStatement = Mockito.mock(
			PreparedStatement.class);
		ResultSet resultSet = Mockito.mock(ResultSet.class);

		Mockito.when(
			_connection.prepareStatement("select companyId from Company")
		).thenReturn(
			preparedStatement
		);

		Mockito.when(
			preparedStatement.executeQuery()
		).thenReturn(
			resultSet
		);

		final List<Integer> nextCounter = new ArrayList<>();

		nextCounter.add(0);

		Mockito.when(
			resultSet.next()
		).thenAnswer(
			new Answer<Boolean>() {

				@Override
				public Boolean answer(InvocationOnMock invocationOnMock)
					throws Throwable {

					int counter = nextCounter.get(0);

					if (counter >= companyIds.size()) {
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
			resultSet.getLong("companyId")
		).thenAnswer(
			new Answer<Long>() {

				@Override
				public Long answer(InvocationOnMock invocationOnMock)
					throws Throwable {

					int counter = getLongCounter.get(0);

					if (counter >= companyIds.size()) {
						throw new IndexOutOfBoundsException();
					}

					getLongCounter.set(0, ++counter);

					return companyIds.get(counter - 1);
				}

			}
		);
	}

	private void _mockGetCompanyInfos(List<Long> companyIds)
		throws SQLException {

		PreparedStatement preparedStatement = Mockito.mock(
			PreparedStatement.class);
		ResultSet resultSet = Mockito.mock(ResultSet.class);

		Mockito.when(
			_connection.prepareStatement("select companyId from CompanyInfo")
		).thenReturn(
			preparedStatement
		);

		Mockito.when(
			preparedStatement.executeQuery()
		).thenReturn(
			resultSet
		);

		final List<Integer> nextCounter = new ArrayList<>();

		nextCounter.add(0);

		Mockito.when(
			resultSet.next()
		).thenAnswer(
			new Answer<Boolean>() {

				@Override
				public Boolean answer(InvocationOnMock invocationOnMock)
					throws Throwable {

					int counter = nextCounter.get(0);

					if (counter >= companyIds.size()) {
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
			resultSet.getLong(1)
		).thenAnswer(
			new Answer<Long>() {

				@Override
				public Long answer(InvocationOnMock invocationOnMock)
					throws Throwable {

					int counter = getLongCounter.get(0);

					if (counter >= companyIds.size()) {
						throw new IndexOutOfBoundsException();
					}

					getLongCounter.set(0, ++counter);

					return companyIds.get(counter - 1);
				}

			}
		);
	}

	private void _mockGetConnection(String password, String url, String user)
		throws SQLException {

		_driverManagerMockedStatic.when(
			() -> DriverManager.getConnection(url, user, password)
		).thenReturn(
			_connection
		);

		Mockito.when(
			_connection.getMetaData()
		).thenReturn(
			_databaseMetaData
		);

		Mockito.when(
			_databaseMetaData.getURL()
		).thenReturn(
			url
		);
	}

	private void _mockGetReleases(List<Release> releases) throws SQLException {
		PreparedStatement preparedStatement = Mockito.mock(
			PreparedStatement.class);

		Mockito.when(
			_connection.prepareStatement(
				"select servletContextName, schemaVersion, state_, verified " +
					"from Release_")
		).thenReturn(
			preparedStatement
		);

		ResultSet resultSet = Mockito.mock(ResultSet.class);

		Mockito.when(
			preparedStatement.executeQuery()
		).thenReturn(
			resultSet
		);

		final List<Integer> nextCounter = new ArrayList<>();

		nextCounter.add(0);

		Mockito.when(
			resultSet.next()
		).thenAnswer(
			new Answer<Boolean>() {

				@Override
				public Boolean answer(InvocationOnMock invocationOnMock)
					throws Throwable {

					int counter = nextCounter.get(0);

					if (counter >= releases.size()) {
						return false;
					}

					nextCounter.set(0, ++counter);

					return true;
				}

			}
		);

		final List<Integer> servletContextNameCounter = new ArrayList<>();

		servletContextNameCounter.add(0);

		Mockito.when(
			resultSet.getString(1)
		).thenAnswer(
			new Answer<String>() {

				@Override
				public String answer(InvocationOnMock invocationOnMock)
					throws Throwable {

					int counter = servletContextNameCounter.get(0);

					if (counter >= releases.size()) {
						throw new IndexOutOfBoundsException();
					}

					servletContextNameCounter.set(0, ++counter);

					return releases.get(
						counter - 1
					).getServletContextName();
				}

			}
		);

		final List<Integer> schemaVersionCounter = new ArrayList<>();

		schemaVersionCounter.add(0);

		Mockito.when(
			resultSet.getString(2)
		).thenAnswer(
			new Answer<String>() {

				@Override
				public String answer(InvocationOnMock invocationOnMock)
					throws Throwable {

					int counter = schemaVersionCounter.get(0);

					if (counter >= releases.size()) {
						throw new IndexOutOfBoundsException();
					}

					schemaVersionCounter.set(0, ++counter);

					Version version = releases.get(
						counter - 1
					).getSchemaVersion();

					return version.toString();
				}

			}
		);

		final List<Integer> stateCounter = new ArrayList<>();

		stateCounter.add(0);

		Mockito.when(
			resultSet.getInt(3)
		).thenAnswer(
			new Answer<Integer>() {

				@Override
				public Integer answer(InvocationOnMock invocationOnMock)
					throws Throwable {

					int counter = stateCounter.get(0);

					if (counter >= releases.size()) {
						throw new IndexOutOfBoundsException();
					}

					stateCounter.set(0, ++counter);

					return releases.get(
						counter - 1
					).getState();
				}

			}
		);

		final List<Integer> verifiedCounter = new ArrayList<>();

		verifiedCounter.add(0);

		Mockito.when(
			resultSet.getBoolean(4)
		).thenAnswer(
			new Answer<Boolean>() {

				@Override
				public Boolean answer(InvocationOnMock invocationOnMock)
					throws Throwable {

					int counter = verifiedCounter.get(0);

					if (counter >= releases.size()) {
						throw new IndexOutOfBoundsException();
					}

					verifiedCounter.set(0, ++counter);

					return releases.get(
						counter - 1
					).getVerified();
				}

			}
		);
	}

	private void _mockGetTables(boolean defaultPartition) throws SQLException {
		ResultSet resultSet = Mockito.mock(ResultSet.class);

		Mockito.when(
			_databaseMetaData.getTables(
				Mockito.nullable(String.class), Mockito.nullable(String.class),
				Mockito.eq("Company"), Mockito.any(String[].class))
		).thenReturn(
			resultSet
		);

		Mockito.when(
			resultSet.next()
		).thenReturn(
			defaultPartition
		);
	}

	private void _testGetExportedCompanyId(
			List<Long> companyIds, Consumer<LiferayDatabase> consumer)
		throws Exception {

		_mockGetCompanyInfos(companyIds);

		consumer.accept(DatabaseUtil.exportLiferayDatabase(_connection));
	}

	private void _testIsExportedCompanyDefault(
			Consumer<LiferayDatabase> consumer, boolean defaultPartition)
		throws Exception {

		_mockGetTables(defaultPartition);

		consumer.accept(DatabaseUtil.exportLiferayDatabase(_connection));
	}

	private static final Connection _connection = Mockito.mock(
		Connection.class);
	private static final DatabaseMetaData _databaseMetaData = Mockito.mock(
		DatabaseMetaData.class);
	private static final MockedStatic<DriverManager>
		_driverManagerMockedStatic = Mockito.mockStatic(DriverManager.class);

}
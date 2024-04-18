/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.db.partition.migration.validator.util;

import com.liferay.portal.kernel.version.Version;
import com.liferay.portal.tools.db.partition.migration.validator.Company;
import com.liferay.portal.tools.db.partition.migration.validator.Release;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/**
 * @author Luis Ortiz
 */
public class MockDatabaseUtil {

	protected static void mockCompanies(List<Company> companies)
		throws SQLException {

		PreparedStatement preparedStatement = Mockito.mock(
			PreparedStatement.class);
		ResultSet resultSet = Mockito.mock(ResultSet.class);

		Mockito.when(
			connection.prepareStatement(
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
					).getVirtualHostName();
				}

			}
		);
	}

	protected static void mockDatabaseConnection(
			String password, String url, String user)
		throws SQLException {

		driverManagerMockedStatic.when(
			() -> DriverManager.getConnection(url, user, password)
		).thenReturn(
			connection
		);

		Mockito.when(
			connection.getMetaData()
		).thenReturn(
			databaseMetaData
		);

		Mockito.when(
			databaseMetaData.getURL()
		).thenReturn(
			url
		);
	}

	protected static void mockDefaultPartition(boolean defaultPartition)
		throws SQLException {

		ResultSet resultSet = Mockito.mock(ResultSet.class);

		Mockito.when(
			databaseMetaData.getTables(
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

	protected static void mockGetCompanyIds(List<Long> companyIds)
		throws SQLException {

		PreparedStatement preparedStatement = Mockito.mock(
			PreparedStatement.class);
		ResultSet resultSet = Mockito.mock(ResultSet.class);

		Mockito.when(
			connection.prepareStatement("select companyId from Company")
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

	protected static void mockGetCompanyInfoIds(List<Long> companyIds)
		throws SQLException {

		PreparedStatement preparedStatement = Mockito.mock(
			PreparedStatement.class);
		ResultSet resultSet = Mockito.mock(ResultSet.class);

		Mockito.when(
			connection.prepareStatement("select companyId from CompanyInfo")
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

	protected static void mockReleases(List<Release> releases)
		throws SQLException {

		PreparedStatement preparedStatement = Mockito.mock(
			PreparedStatement.class);

		Mockito.when(
			connection.prepareStatement(
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

	protected static void mockTables(List<String> tableNames)
		throws SQLException {

		ResultSet resultSet1 = Mockito.mock(ResultSet.class);

		Mockito.when(
			databaseMetaData.getColumns(
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
			databaseMetaData.getTables(
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

	protected static final Connection connection = Mockito.mock(
		Connection.class);
	protected static final DatabaseMetaData databaseMetaData = Mockito.mock(
		DatabaseMetaData.class);
	protected static final MockedStatic<DriverManager>
		driverManagerMockedStatic = Mockito.mockStatic(DriverManager.class);

}
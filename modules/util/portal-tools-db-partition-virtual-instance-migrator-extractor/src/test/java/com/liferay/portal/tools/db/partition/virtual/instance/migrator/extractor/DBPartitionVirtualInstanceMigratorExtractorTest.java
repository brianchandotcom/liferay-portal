/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.db.partition.virtual.instance.migrator.extractor;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.version.Version;
import com.liferay.portal.tools.db.partition.virtual.instance.migrator.common.Company;
import com.liferay.portal.tools.db.partition.virtual.instance.migrator.common.Release;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/**
 * @author Luis Ortiz
 */
public class DBPartitionVirtualInstanceMigratorExtractorTest {

	@Test
	public void testMultipleCompanyDefaultDatabase()
		throws IOException, SQLException {

		List<Long> companyInfoIds = new ArrayList<>();

		companyInfoIds.add(RandomTestUtil.randomLong());
		companyInfoIds.add(RandomTestUtil.randomLong());

		boolean defaultPartition = true;

		List<Long> companyIds = new ArrayList<>();

		companyIds.add(RandomTestUtil.randomLong());
		companyIds.add(RandomTestUtil.randomLong());

		Release module1Release = new Release(
			Version.parseVersion("14.2.4"), "module1", 0, true);
		Release module2Release = new Release(
			Version.parseVersion("2.0.1"), "module2", 1, false);

		List<Release> releases = new ArrayList<>();

		releases.add(module1Release);
		releases.add(module2Release);

		List<Company> companies = new ArrayList<>();

		companies.add(
			new Company(
				RandomTestUtil.randomLong(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString()));
		companies.add(
			new Company(
				RandomTestUtil.randomLong(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString()));

		_executeTest(
			companyInfoIds, defaultPartition, companyIds, releases, companies);
	}

	@Test
	public void testMultipleCompanyNondefaultDatabase()
		throws IOException, SQLException {

		List<Long> companyInfoIds = new ArrayList<>();

		companyInfoIds.add(RandomTestUtil.randomLong());
		companyInfoIds.add(RandomTestUtil.randomLong());

		boolean defaultPartition = false;

		List<Long> companyIds = new ArrayList<>();

		companyIds.add(RandomTestUtil.randomLong());
		companyIds.add(RandomTestUtil.randomLong());

		Release module1Release = new Release(
			Version.parseVersion("14.2.4"), "module1", 0, true);
		Release module2Release = new Release(
			Version.parseVersion("2.0.1"), "module2", 1, false);

		List<Release> releases = new ArrayList<>();

		releases.add(module1Release);
		releases.add(module2Release);

		List<Company> companies = new ArrayList<>();

		companies.add(
			new Company(
				RandomTestUtil.randomLong(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString()));
		companies.add(
			new Company(
				RandomTestUtil.randomLong(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString()));

		_executeTest(
			companyInfoIds, defaultPartition, companyIds, releases, companies);
	}

	@Test
	public void testSingleCompanyDefaultDatabase()
		throws IOException, SQLException {

		List<Long> companyInfoIds = new ArrayList<>();

		companyInfoIds.add(RandomTestUtil.randomLong());

		boolean defaultPartition = true;

		List<Long> companyIds = new ArrayList<>();

		companyIds.add(RandomTestUtil.randomLong());
		companyIds.add(RandomTestUtil.randomLong());

		Release module1Release = new Release(
			Version.parseVersion("14.2.4"), "module1", 0, true);
		Release module2Release = new Release(
			Version.parseVersion("2.0.1"), "module2", 1, false);

		List<Release> releases = new ArrayList<>();

		releases.add(module1Release);
		releases.add(module2Release);

		List<Company> companies = new ArrayList<>();

		companies.add(
			new Company(
				RandomTestUtil.randomLong(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString()));
		companies.add(
			new Company(
				RandomTestUtil.randomLong(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString()));

		_executeTest(
			companyInfoIds, defaultPartition, companyIds, releases, companies);
	}

	@Test
	public void testSingleCompanyNondefaultDatabase()
		throws IOException, SQLException {

		List<Long> companyInfoIds = new ArrayList<>();

		companyInfoIds.add(RandomTestUtil.randomLong());

		boolean defaultPartition = false;

		List<Long> companyIds = new ArrayList<>();

		companyIds.add(RandomTestUtil.randomLong());
		companyIds.add(RandomTestUtil.randomLong());

		Release module1Release = new Release(
			Version.parseVersion("14.2.4"), "module1", 0, true);
		Release module2Release = new Release(
			Version.parseVersion("2.0.1"), "module2", 1, false);

		List<Release> releases = new ArrayList<>();

		releases.add(module1Release);
		releases.add(module2Release);

		List<Company> companies = new ArrayList<>();

		companies.add(
			new Company(
				RandomTestUtil.randomLong(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString()));
		companies.add(
			new Company(
				RandomTestUtil.randomLong(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString()));

		_executeTest(
			companyInfoIds, defaultPartition, companyIds, releases, companies);
	}

	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();

	private void _assertFileContent(
		String content, List<Long> companyInfoIds, boolean defaultPartition,
		List<Release> releases, List<Company> companies) {

		content = content.replaceAll("\n", "");
		content = content.replaceAll("\r", "");
		content = content.replaceAll(" ", "");

		Assert.assertTrue(content.contains("\"jdbcUrl\":\"" + _URL + "\""));

		if (companyInfoIds.size() > 1) {
			Assert.assertTrue(content.contains("\"companyId\":null"));
		}
		else {
			Assert.assertTrue(
				content.contains("\"companyId\":" + companyInfoIds.get(0)));
		}

		if (defaultPartition) {
			Assert.assertTrue(content.contains("\"defaultPartition\":true"));
		}
		else {
			Assert.assertTrue(content.contains("\"defaultPartition\":false"));
		}

		Assert.assertTrue(
			content.contains("\"tableNames\":[\"Table1\",\"Table2"));

		Assert.assertTrue(content.contains(_getReleasesOutput(releases)));

		Assert.assertTrue(content.contains(_getCompaniesOutput(companies)));
	}

	private void _executeTest(
			List<Long> companyInfoIds, boolean defaultPartition,
			List<Long> companyIds, List<Release> releases,
			List<Company> companies)
		throws IOException, SQLException {

		_mockDatabase(
			companyInfoIds, defaultPartition, companyIds, releases, companies);

		File outputDirectory = temporaryFolder.newFolder("tempExports");

		DBPartitionVirtualInstanceMigratorExtractor.main(
			new String[] {
				"-url", _URL, "-user", _USER, "-pass", _PASSWORD, "-path",
				outputDirectory.getAbsolutePath()
			});

		File[] files = outputDirectory.listFiles();

		Assert.assertEquals(Arrays.toString(files), 1, files.length);

		String content = _getFileContent(files[0]);

		_assertFileContent(
			content, companyInfoIds, defaultPartition, releases, companies);
	}

	private String _getCompaniesOutput(List<Company> companies) {
		StringBundler sb = new StringBundler();
		int count = 0;

		sb.append("\"companies\":[");

		for (Company company : companies) {
			sb.append("{\"virtualHostName\":\"");
			sb.append(company.getVirtualHostName());
			sb.append("\",\"companyName\":\"");
			sb.append(company.getCompanyName());
			sb.append("\",\"webId\":\"");
			sb.append(company.getWebId());
			sb.append("\",\"companyId\":");
			sb.append(company.getCompanyId());
			sb.append("}");

			if (++count < companies.size()) {
				sb.append(",");
			}
		}

		sb.append("]");

		return sb.toString();
	}

	private String _getFileContent(File file) throws IOException {
		StringBuilder sb = new StringBuilder();

		try (BufferedReader bufferedReader = new BufferedReader(
				new FileReader(file))) {

			String line;

			while ((line = bufferedReader.readLine()) != null) {
				sb.append(line);
			}
		}

		return sb.toString();
	}

	private String _getReleasesOutput(List<Release> releases) {
		StringBundler sb = new StringBundler();
		int count = 0;

		sb.append("\"releases\":[");

		for (Release release : releases) {
			sb.append("{\"state\":");
			sb.append(release.getState());
			sb.append(",\"schemaVersion\":{\"major\":");
			sb.append(
				release.getSchemaVersion(
				).getMajor());
			sb.append(",\"micro\":");
			sb.append(
				release.getSchemaVersion(
				).getMicro());
			sb.append(",\"minor\":");
			sb.append(
				release.getSchemaVersion(
				).getMinor());
			sb.append(",\"qualifier\":");

			if (release.getSchemaVersion(
				).getQualifier(
				).isEmpty()) {

				sb.append("\"\"");
			}
			else {
				sb.append(
					release.getSchemaVersion(
					).getQualifier());
			}

			sb.append("},\"verified\":");
			sb.append(release.getVerified() ? "true" : "false");
			sb.append(",\"servletContextName\":\"");
			sb.append(release.getServletContextName());
			sb.append("\"}");

			if (++count < releases.size()) {
				sb.append(",");
			}
		}

		sb.append("]");

		return sb.toString();
	}

	private void _mockCompanies(List<Company> companies) throws SQLException {
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
					).getVirtualHostName();
				}

			}
		);
	}

	private void _mockDatabase(
			List<Long> companyInfoIds, boolean defaultPartition,
			List<Long> companyIds, List<Release> releases,
			List<Company> companies)
		throws SQLException {

		_driverManagerMockedStatic.when(
			() -> DriverManager.getConnection(_URL, _USER, _PASSWORD)
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
			_URL
		);

		_mockGetCompanyId(companyInfoIds);
		_mockDefaultPartition(defaultPartition);
		_mockGetCompanyIds(companyIds);
		_mockTables();
		_mockReleases(releases);
		_mockCompanies(companies);
	}

	private void _mockDefaultPartition(boolean defaultPartition)
		throws SQLException {

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

	private void _mockGetCompanyId(List<Long> companyIds) throws SQLException {
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

	private void _mockReleases(List<Release> releases) throws SQLException {
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

	private void _mockTables() throws SQLException {
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

		/*ResultSet resultSet2 = Mockito.mock(ResultSet.class);

		Mockito.when(
			_databaseMetaData.getColumns(
				Mockito.nullable(String.class), Mockito.nullable(String.class),
				Mockito.eq("Company"), Mockito.nullable(String.class))
		).thenReturn(
			resultSet2
		);

		Mockito.when(
			resultSet2.next()
		).thenReturn(
			true
		);*/

		ResultSet resultSet3 = Mockito.mock(ResultSet.class);

		Mockito.when(
			_databaseMetaData.getTables(
				Mockito.nullable(String.class), Mockito.nullable(String.class),
				Mockito.isNull(), Mockito.any(String[].class))
		).thenReturn(
			resultSet3
		);

		// Mock _resultSet

		Mockito.when(
			resultSet3.getString("TABLE_NAME")
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
			resultSet3.next()
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
	}

	private static final String _PASSWORD = RandomTestUtil.randomString();

	private static final String _URL = RandomTestUtil.randomString();

	private static final String _USER = RandomTestUtil.randomString();

	private static final MockedStatic<DriverManager>
		_driverManagerMockedStatic = Mockito.mockStatic(DriverManager.class);

	private final Connection _connection = Mockito.mock(Connection.class);
	private final DatabaseMetaData _databaseMetaData = Mockito.mock(
		DatabaseMetaData.class);

}
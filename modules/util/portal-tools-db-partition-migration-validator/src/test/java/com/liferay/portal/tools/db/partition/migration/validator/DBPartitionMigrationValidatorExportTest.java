/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.db.partition.migration.validator;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.version.Version;
import com.liferay.portal.tools.db.partition.migration.validator.util.DatabaseMockUtil;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.PrintStream;

import java.security.Permission;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import org.skyscreamer.jsonassert.JSONAssert;

/**
 * @author Luis Ortiz
 */
public class DBPartitionMigrationValidatorExportTest extends DatabaseMockUtil {

	@Before
	public void setUp() {
		System.setErr(new PrintStream(_errByteArrayOutputStream));
		System.setOut(new PrintStream(_outByteArrayOutputStream));
		System.setSecurityManager(
			new DBPartitionMigrationValidatorExportTest.
				DisallowExitSecurityManager());
	}

	@After
	public void tearDown() {
		System.setErr(_originalErr);
		System.setOut(_originalOut);
	}

	@Test
	public void testMultipleCompanyDefaultDatabase() throws Exception {
		_export(
			_generateCompanies(),
			Arrays.asList(
				RandomTestUtil.randomLong(), RandomTestUtil.randomLong()),
			Arrays.asList(
				RandomTestUtil.randomLong(), RandomTestUtil.randomLong()),
			true, _generateReleases());
	}

	@Test
	public void testMultipleCompanyNondefaultDatabase() throws Exception {
		_export(
			_generateCompanies(),
			Arrays.asList(
				RandomTestUtil.randomLong(), RandomTestUtil.randomLong()),
			Arrays.asList(
				RandomTestUtil.randomLong(), RandomTestUtil.randomLong()),
			false, _generateReleases());
	}

	@Test
	public void testSingleCompanyDefaultDatabase() throws Exception {
		_export(
			_generateCompanies(),
			Arrays.asList(
				RandomTestUtil.randomLong(), RandomTestUtil.randomLong()),
			Collections.singletonList(RandomTestUtil.randomLong()), true,
			_generateReleases());
	}

	@Test
	public void testSingleCompanyNondefaultDatabase() throws Exception {
		_export(
			_generateCompanies(),
			Arrays.asList(
				RandomTestUtil.randomLong(), RandomTestUtil.randomLong()),
			Collections.singletonList(RandomTestUtil.randomLong()), false,
			_generateReleases());
	}

	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();

	private void _assertFileContent(
			List<Company> companies, List<Long> companyInfoIds, String content,
			boolean defaultPartition, List<Release> releases)
		throws Exception {

		if (companyInfoIds.size() > 1) {
			JSONAssert.assertEquals(
				"{\"exportedCompanyId\":null}", content, false);
		}
		else {
			JSONAssert.assertEquals(
				"{\"exportedCompanyId\": " + companyInfoIds.get(0) + "}",
				content, false);
		}

		if (defaultPartition) {
			JSONAssert.assertEquals(
				"{\"exportedCompanyDefault\":true}", content, false);
		}
		else {
			JSONAssert.assertEquals(
				"{\"exportedCompanyDefault\":false}", content, false);
		}

		JSONAssert.assertEquals(
			"{\"tableNames\":[\"Table1\",\"Table2\"]}", content, false);

		JSONAssert.assertEquals(_getReleasesOutput(releases), content, false);

		JSONAssert.assertEquals(_getCompaniesOutput(companies), content, false);
	}

	private void _export(
			List<Company> companies, List<Long> companyIds,
			List<Long> companyInfoIds, boolean defaultPartition,
			List<Release> releases)
		throws Exception {

		_mockDatabase(
			companies, companyIds, companyInfoIds, defaultPartition, releases,
			Arrays.asList(
				"Table1", "Company", "Table2",
				"Object_x_" + companyIds.get(0)));

		File outputDirectory = temporaryFolder.newFolder("tempExports");

		try {
			DBPartitionMigrationValidator.main(
				new String[] {
					"--export", "--jdbc-url", _URL, "--user", _USER,
					"--password", _PASSWORD, "--output-dir",
					outputDirectory.getAbsolutePath(), "--schema-name",
					_SCHEMA_NAME
				});
		}
		catch (RuntimeException runtimeException) {
			if (companyInfoIds.size() > 1) {
				Assert.assertEquals("1", runtimeException.getMessage());
				Assert.assertTrue(
					_errByteArrayOutputStream.toString(
					).contains(
						"Database schema has to have a single company or " +
							"database partitioning must be enabled"
					));

				File[] files = outputDirectory.listFiles();

				Assert.assertEquals(Arrays.toString(files), 0, files.length);

				return;
			}

			Assert.assertEquals("0", runtimeException.getMessage());
		}

		File[] files = outputDirectory.listFiles();

		Assert.assertEquals(Arrays.toString(files), 1, files.length);

		String content = _getFileContent(files[0]);

		_assertFileContent(
			companies, companyInfoIds, content, defaultPartition, releases);
	}

	private List<Company> _generateCompanies() {
		return Arrays.asList(
			new Company(
				RandomTestUtil.randomLong(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString()),
			new Company(
				RandomTestUtil.randomLong(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString()));
	}

	private List<Release> _generateReleases() {
		return Arrays.asList(
			new Release(Version.parseVersion("14.2.4"), "module1", 0, true),
			new Release(Version.parseVersion("2.0.1"), "module2", 1, false));
	}

	private String _getCompaniesOutput(List<Company> companies) {
		StringBundler sb = new StringBundler();
		int count = 0;

		sb.append("{\"companies\":[");

		for (Company company : companies) {
			sb.append("{\"companyId\":");
			sb.append(company.getCompanyId());
			sb.append(",\"companyName\":\"");
			sb.append(company.getCompanyName());
			sb.append("\",\"virtualHostname\":\"");
			sb.append(company.getVirtualHostname());
			sb.append("\",\"webId\":\"");
			sb.append(company.getWebId());
			sb.append("\"}");

			if (++count < companies.size()) {
				sb.append(",");
			}
		}

		sb.append("]}");

		return sb.toString();
	}

	private String _getFileContent(File file) throws Exception {
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

		sb.append("{\"releases\":[");

		for (Release release : releases) {
			sb.append("{\"schemaVersion\":{\"major\":");
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

			sb.append("},\"servletContextName\":\"");
			sb.append(release.getServletContextName());
			sb.append("\",\"state\":");
			sb.append(release.getState());
			sb.append(",\"verified\":");
			sb.append(release.getVerified() ? "true" : "false");
			sb.append("}");

			if (++count < releases.size()) {
				sb.append(",");
			}
		}

		sb.append("]}");

		return sb.toString();
	}

	private void _mockDatabase(
			List<Company> companies, List<Long> companyIds,
			List<Long> companyInfoIds, boolean defaultPartition,
			List<Release> releases, List<String> tableNames)
		throws Exception {

		mockCompanies(companies);
		mockDatabaseConnection(
			_PASSWORD, StringUtil.replace(_URL, "lportal", _SCHEMA_NAME),
			_USER);
		mockDefaultPartition(defaultPartition);
		mockGetCompanyIds(companyIds);
		mockGetCompanyInfoIds(companyInfoIds);
		mockReleases(releases);
		mockTables(tableNames);
	}

	private static final String _PASSWORD = RandomTestUtil.randomString();

	private static final String _SCHEMA_NAME = RandomTestUtil.randomString();

	private static final String _URL =
		"jdbc:mysql://localhost:3306/lportal?useUnicode=true";

	private static final String _USER = RandomTestUtil.randomString();

	private final ByteArrayOutputStream _errByteArrayOutputStream =
		new ByteArrayOutputStream();
	private final PrintStream _originalErr = System.err;
	private final PrintStream _originalOut = System.out;
	private final ByteArrayOutputStream _outByteArrayOutputStream =
		new ByteArrayOutputStream();

	private class DisallowExitSecurityManager extends SecurityManager {

		@Override
		public void checkExit(int status) {
			super.checkExit(status);

			throw new RuntimeException(String.valueOf(status));
		}

		@Override
		public void checkPermission(Permission perm) {
		}

	}

}
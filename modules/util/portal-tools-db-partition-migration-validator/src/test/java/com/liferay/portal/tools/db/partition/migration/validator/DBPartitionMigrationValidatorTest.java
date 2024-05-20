/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.db.partition.migration.validator;

import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.version.Version;
import com.liferay.portal.tools.db.partition.migration.validator.util.BaseTestCase;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.PrintStream;

import java.security.Permission;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

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
public class DBPartitionMigrationValidatorTest extends BaseTestCase {

	@Before
	public void setUp() {
		System.setErr(new PrintStream(_errByteArrayOutputStream));
		System.setOut(new PrintStream(_outByteArrayOutputStream));
		System.setSecurityManager(new DisallowExitSecurityManager());
	}

	@After
	public void tearDown() {
		System.setErr(_originalErr);
		System.setOut(_originalOut);
	}

	@Test
	public void testExportDefaultDatabase() throws Exception {
		_export(
			_generateCompanies(),
			Arrays.asList(
				RandomTestUtil.randomLong(), RandomTestUtil.randomLong()),
			Collections.singletonList(RandomTestUtil.randomLong()), true,
			_generateReleases());
	}

	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();

	private void _assertFileContent(
			List<Company> companies, List<Long> companyInfoIds, String content,
			boolean defaultPartition, List<Release> releases)
		throws Exception {

		JSONAssert.assertEquals(
			_getExportedCompanyIdOutput(companyInfoIds), content, false);

		JSONAssert.assertEquals(
			_getExportedCompanyDefaultOutput(defaultPartition), content, false);

		JSONAssert.assertEquals(
			_getTableNamesOutput(Arrays.asList("Table1", "Table2")), content,
			false);

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
		JSONArray companiesJSONArray = new JSONArray();

		for (Company company : companies) {
			JSONObject jsonObject = new JSONObject();

			jsonObject.put(
				"companyId", company.getCompanyId()
			).put(
				"companyName", company.getCompanyName()
			).put(
				"virtualHostname", company.getVirtualHostname()
			).put(
				"webId", company.getWebId()
			);

			companiesJSONArray.put(jsonObject);
		}

		JSONObject jsonObject = new JSONObject();

		jsonObject.put("companies", companiesJSONArray);

		return jsonObject.toString();
	}

	private String _getExportedCompanyDefaultOutput(boolean defaultPartition) {
		JSONObject jsonObject = new JSONObject();

		jsonObject.put("exportedCompanyDefault", defaultPartition);

		return jsonObject.toString();
	}

	private String _getExportedCompanyIdOutput(List<Long> companyInfoIds) {
		JSONObject jsonObject = new JSONObject();

		if (companyInfoIds.size() > 1) {
			jsonObject.put("exportedCompanyId", (Collection<?>)null);
		}
		else {
			jsonObject.put("exportedCompanyId", companyInfoIds.get(0));
		}

		return jsonObject.toString();
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
		JSONArray releasesJSONArray = new JSONArray();

		for (Release release : releases) {
			JSONObject schemaVersionJSONObject = new JSONObject();

			schemaVersionJSONObject.put(
				"major",
				release.getSchemaVersion(
				).getMajor()
			).put(
				"micro",
				release.getSchemaVersion(
				).getMicro()
			).put(
				"minor",
				release.getSchemaVersion(
				).getMinor()
			).put(
				"qualifier",
				release.getSchemaVersion(
				).getQualifier()
			);

			JSONObject releaseJSONObject = new JSONObject();

			releaseJSONObject.put(
				"schemaVersion", schemaVersionJSONObject
			).put(
				"servletContextName", release.getServletContextName()
			).put(
				"state", release.getState()
			).put(
				"verified", release.getVerified()
			);

			releasesJSONArray.put(releaseJSONObject);
		}

		JSONObject jsonObject = new JSONObject();

		jsonObject.put("releases", releasesJSONArray);

		return jsonObject.toString();
	}

	private String _getTableNamesOutput(List<String> tableNames) {
		JSONArray tableNamesJSONArray = new JSONArray();

		for (String tableName : tableNames) {
			tableNamesJSONArray.put(tableName);
		}

		JSONObject jsonObject = new JSONObject();

		jsonObject.put("tableNames", tableNamesJSONArray);

		return jsonObject.toString();
	}

	private void _mockDatabase(
			List<Company> companies, List<Long> companyIds,
			List<Long> companyInfoIds, boolean defaultPartition,
			List<Release> releases, List<String> tableNames)
		throws Exception {

		mockGetColumns(tableNames);
		mockGetCompanies(companies);
		mockGetCompanyIds(companyIds);
		mockGetCompanyInfos(companyInfoIds);
		mockGetConnection(
			_PASSWORD, StringUtil.replace(_URL, "lportal", _SCHEMA_NAME),
			_USER);
		mockGetReleases(releases);
		mockGetTables(defaultPartition);
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
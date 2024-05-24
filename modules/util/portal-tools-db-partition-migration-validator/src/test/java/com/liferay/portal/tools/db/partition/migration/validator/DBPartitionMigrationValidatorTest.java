/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.db.partition.migration.validator;

import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.version.Version;
import com.liferay.portal.tools.db.partition.migration.validator.util.BaseTestCase;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.PrintStream;

import java.net.URL;

import java.nio.file.Path;
import java.nio.file.Paths;

import java.security.Permission;

import java.util.Arrays;
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
		_testExport(
			Collections.singletonList(RandomTestUtil.randomLong()), true);
	}

	@Test
	public void testExportDefaultDatabaseWithMultipleCompanies()
		throws Exception {

		_testExport(
			Arrays.asList(
				RandomTestUtil.randomLong(), RandomTestUtil.randomLong()),
			true);
	}

	@Test
	public void testExportNondefaultDatabase() throws Exception {
		_testExport(
			Collections.singletonList(RandomTestUtil.randomLong()), false);
	}

	@Test
	public void testExportNondefaultDatabaseWithMultipleCompanies()
		throws Exception {

		_testExport(
			Arrays.asList(
				RandomTestUtil.randomLong(), RandomTestUtil.randomLong()),
			false);
	}

	@Test
	public void testValidateFailure() throws Exception {
		String[] messages = {
			"[ERROR] Company ID 3007447931789165977 already exists in the " +
				"target database",
			"[ERROR] Module com.liferay.address.impl needs to be verified in " +
				"the source database before the migration",
			"[ERROR] Module com.liferay.comment.page.comments.web has a " +
				"failed release state in the source database",
			"[ERROR] Module com.liferay.exportimport.service needs to be " +
				"installed in the source database before the migration",
			"[ERROR] Module com.liferay.knowledge.base.web needs to be " +
				"upgraded in the target database before the migration",
			"[ERROR] Module com.liferay.organizations.service has a failed " +
				"release state in the target database",
			"[ERROR] Module com.liferay.organizations.service needs to be " +
				"verified in the target database before the migration",
			"[ERROR] Module com.liferay.wiki.web needs to be upgraded in the " +
				"source database before the migration",
			"[WARN] Company name Liferay DXP already exists in the target " +
				"database. You must set a different value in " +
					"DBPartitionInsertVirtualInstanceConfiguration.config.",
			"[WARN] Module com.liferay.asset.publisher.web is not present in " +
				"the source database",
			"[WARN] Module com.liferay.license.manager.web is not present in " +
				"the target database",
			"[WARN] Table CommercePriceList is not present in the source " +
				"database",
			"[WARN] Table DDMTemplate is not present in the target database",
			"[WARN] Virtual host localhost already exists in the target " +
				"database. You must set a different value in " +
					"DBPartitionInsertVirtualInstanceConfiguration.config.",
			"[WARN] Web ID liferay.com already exists in the target " +
				"database. You must set a different value in " +
					"DBPartitionInsertVirtualInstanceConfiguration.config."
		};

		_testValidate(
			runtimeException -> {
				Assert.assertEquals("1", runtimeException.getMessage());

				String string = _outByteArrayOutputStream.toString();

				for (String message : messages) {
					Assert.assertTrue(string.contains(message));
				}
			},
			"source-failure.json", "target-failure.json",
			() -> {
			});
	}

	@Test
	public void testValidateSuccess() throws Exception {
		_testValidate(
			runtimeException -> Assert.assertEquals(
				"0", runtimeException.getMessage()),
			"source-success.json", "target-success.json",
			() -> {
				Assert.assertTrue(
					_errByteArrayOutputStream.toString(
					).isEmpty());
				Assert.assertTrue(
					_outByteArrayOutputStream.toString(
					).isEmpty());
			});
	}

	@Test
	public void testValidateTargetNondefaultPartition() throws Exception {
		_testValidate(
			runtimeException -> {
				Assert.assertEquals("1", runtimeException.getMessage());
				Assert.assertTrue(
					_errByteArrayOutputStream.toString(
					).contains(
						"Target is not the default partition"
					));
				Assert.assertTrue(
					_outByteArrayOutputStream.toString(
					).isEmpty());
			},
			"source-success.json", "target-nondefault.json",
			() -> {
			});
	}

	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();

	private String _getPathString(String fileName) throws Exception {
		URL url = DBPartitionMigrationValidatorTest.class.getResource(
			"dependencies/" + fileName);

		Path path = Paths.get(url.toURI());

		return path.toString();
	}

	private void _mockDatabase(
			List<Company> companies, List<Long> companyIds,
			List<Long> companyInfoIds, boolean defaultPartition,
			String password, List<Release> releases, String schemaName,
			List<String> tableNames, String url, String user)
		throws Exception {

		mockGetColumns(tableNames);
		mockGetCompanies(companies);
		mockGetCompanyIds(companyIds);
		mockGetCompanyInfos(companyInfoIds);
		mockGetConnection(
			password, StringUtil.replace(url, "lportal", schemaName), user);
		mockGetReleases(releases);
		mockGetTables(defaultPartition);
	}

	private String _read(File file) throws Exception {
		StringBuilder sb = new StringBuilder();

		try (BufferedReader bufferedReader = new BufferedReader(
				new FileReader(file))) {

			String line = null;

			while ((line = bufferedReader.readLine()) != null) {
				sb.append(line);
			}
		}

		return sb.toString();
	}

	private void _testExport(List<Long> companyIds, boolean defaultPartition)
		throws Exception {

		List<Company> companies = Arrays.asList(
			new Company(
				RandomTestUtil.randomLong(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString()),
			new Company(
				RandomTestUtil.randomLong(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString()));
		String password = RandomTestUtil.randomString();
		List<Release> releases = Arrays.asList(
			new Release(Version.parseVersion("14.2.4"), "module1", 0, true),
			new Release(Version.parseVersion("2.0.1"), "module2", 1, false));
		String schemaName = RandomTestUtil.randomString();
		String url = "jdbc:mysql://localhost:3306/lportal?useUnicode=true";
		String user = RandomTestUtil.randomString();

		_mockDatabase(
			companies, companyIds, companyIds, defaultPartition, password,
			releases, schemaName,
			Arrays.asList(
				"Company", "Object_x_" + companyIds.get(0), "Table1", "Table2"),
			url, user);

		File outputDirectory = temporaryFolder.newFolder();

		try {
			DBPartitionMigrationValidator.main(
				new String[] {
					"--export", "--jdbc-url", url, "--output-dir",
					outputDirectory.getAbsolutePath(), "--password", password,
					"--schema-name", schemaName, "--user", user
				});
		}
		catch (RuntimeException runtimeException) {
			if (companyIds.size() > 1) {
				Assert.assertTrue(
					_errByteArrayOutputStream.toString(
					).contains(
						"Database schema has to have a single company or " +
							"database partitioning must be enabled"
					));
				Assert.assertEquals("1", runtimeException.getMessage());

				File[] files = outputDirectory.listFiles();

				Assert.assertEquals(Arrays.toString(files), 0, files.length);

				return;
			}

			Assert.assertEquals("0", runtimeException.getMessage());
		}

		File[] files = outputDirectory.listFiles();

		Assert.assertEquals(Arrays.toString(files), 1, files.length);

		String content = _read(files[0]);

		JSONAssert.assertEquals(
			new JSONObject(
			).put(
				"companies", new JSONArray(companies)
			).toString(),
			content, false);
		JSONAssert.assertEquals(
			new JSONObject(
			).put(
				"exportedCompanyDefault", defaultPartition
			).toString(),
			content, false);

		Long exportedCompanyId = null;

		if (companyIds.size() == 1) {
			exportedCompanyId = companyIds.get(0);
		}

		JSONAssert.assertEquals(
			new JSONObject(
			).put(
				"exportedCompanyId", exportedCompanyId
			).toString(),
			content, false);

		JSONAssert.assertEquals(
			new JSONObject(
			).put(
				"releases", new JSONArray(releases)
			).toString(),
			content, false);
		JSONAssert.assertEquals(
			new JSONObject(
			).put(
				"tableNames", new JSONArray(Arrays.asList("Table1", "Table2"))
			).toString(),
			content, false);
	}

	private void _testValidate(
			UnsafeConsumer<RuntimeException, Exception>
				catchValidationsUnsafeConsumer,
			String sourceFileName, String targetFileName,
			UnsafeRunnable<Exception> validationsUnsafeRunnable)
		throws Exception {

		try {
			DBPartitionMigrationValidator.main(
				new String[] {
					"--source-file", _getPathString(sourceFileName),
					"--target-file", _getPathString(targetFileName),
					"--validate"
				});
		}
		catch (RuntimeException runtimeException) {
			catchValidationsUnsafeConsumer.accept(runtimeException);
		}

		validationsUnsafeRunnable.run();
	}

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
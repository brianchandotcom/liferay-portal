/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.db.partition.virtual.instance.migration.extractor;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.version.Version;
import com.liferay.portal.tools.db.partition.virtual.instance.migration.common.Company;
import com.liferay.portal.tools.db.partition.virtual.instance.migration.common.Release;
import com.liferay.portal.tools.db.partition.virtual.instance.migration.extractor.util.DatabaseMockupUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.sql.SQLException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * @author Luis Ortiz
 */
public class DBPartitionVirtualInstanceMigrationExtractorTest
	extends DatabaseMockupUtil {

	@Test
	public void testMultipleCompanyDefaultDatabase()
		throws IOException, SQLException {

		_executeTest(
			_generateCompanies(),
			Arrays.asList(
				RandomTestUtil.randomLong(), RandomTestUtil.randomLong()),
			Arrays.asList(
				RandomTestUtil.randomLong(), RandomTestUtil.randomLong()),
			true, _generateReleases());
	}

	@Test
	public void testMultipleCompanyNondefaultDatabase()
		throws IOException, SQLException {

		_executeTest(
			_generateCompanies(),
			Arrays.asList(
				RandomTestUtil.randomLong(), RandomTestUtil.randomLong()),
			Arrays.asList(
				RandomTestUtil.randomLong(), RandomTestUtil.randomLong()),
			false, _generateReleases());
	}

	@Test
	public void testSingleCompanyDefaultDatabase()
		throws IOException, SQLException {

		_executeTest(
			_generateCompanies(),
			Arrays.asList(
				RandomTestUtil.randomLong(), RandomTestUtil.randomLong()),
			Collections.singletonList(RandomTestUtil.randomLong()), true,
			_generateReleases());
	}

	@Test
	public void testSingleCompanyNondefaultDatabase()
		throws IOException, SQLException {

		_executeTest(
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
		boolean defaultPartition, List<Release> releases) {

		content = content.replaceAll("\n", "");
		content = content.replaceAll("\r", "");
		content = content.replaceAll(" ", "");

		if (companyInfoIds.size() > 1) {
			Assert.assertTrue(content.contains("\"extractedCompanyId\":null"));
		}
		else {
			Assert.assertTrue(
				content.contains(
					"\"extractedCompanyId\":" + companyInfoIds.get(0)));
		}

		if (defaultPartition) {
			Assert.assertTrue(
				content.contains("\"extractedCompanyDefault\":true"));
		}
		else {
			Assert.assertTrue(
				content.contains("\"extractedCompanyDefault\":false"));
		}

		Assert.assertTrue(
			content.contains("\"tableNames\":[\"Table1\",\"Table2"));

		Assert.assertTrue(content.contains(_getReleasesOutput(releases)));

		Assert.assertTrue(content.contains(_getCompaniesOutput(companies)));
	}

	private void _executeTest(
			List<Company> companies, List<Long> companyIds,
			List<Long> companyInfoIds, boolean defaultPartition,
			List<Release> releases)
		throws IOException, SQLException {

		_mockDatabase(
			companies, companyIds, companyInfoIds, defaultPartition, releases,
			Arrays.asList("Table1", "Company", "Table2", "Object_x_25000"));

		File outputDirectory = temporaryFolder.newFolder("tempExports");

		DBPartitionVirtualInstanceMigrationExtractor.main(
			new String[] {
				"-j", _URL, "-u", _USER, "-p", _PASSWORD, "-d",
				outputDirectory.getAbsolutePath(), "-s", _SCHEMA_NAME
			});

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

	private void _mockDatabase(
			List<Company> companies, List<Long> companyIds,
			List<Long> companyInfoIds, boolean defaultPartition,
			List<Release> releases, List<String> tableNames)
		throws SQLException {

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

}
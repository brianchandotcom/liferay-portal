/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.db.partition.migration.validator.util;

import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.version.Version;
import com.liferay.portal.tools.db.partition.migration.validator.Company;
import com.liferay.portal.tools.db.partition.migration.validator.LiferayDatabase;
import com.liferay.portal.tools.db.partition.migration.validator.Release;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Luis Ortiz
 */
public class DatabaseUtilTest {

	@Before
	public void setUp() throws SQLException {
		DatabaseMockUtil.mockGetColumns(Collections.emptyList());
		DatabaseMockUtil.mockGetCompanies(Collections.emptyList());
		DatabaseMockUtil.mockGetCompanyIds(Collections.emptyList());
		DatabaseMockUtil.mockGetCompanyInfos(Collections.emptyList());
		DatabaseMockUtil.mockGetConnection(
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString());
		DatabaseMockUtil.mockGetReleases(Collections.emptyList());
		DatabaseMockUtil.mockGetTables(true);
	}

	@Test
	public void testGetCompanies() throws Exception {
		Company company1 = new Company(
			RandomTestUtil.randomLong(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString());
		Company company2 = new Company(
			RandomTestUtil.randomLong(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString());

		DatabaseMockUtil.mockGetCompanies(Arrays.asList(company1, company2));

		LiferayDatabase liferayDatabase = DatabaseUtil.exportLiferayDatabase(
			DatabaseMockUtil.getConnection());

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

		DatabaseMockUtil.mockGetReleases(
			Arrays.asList(module1Release, module2Release));

		LiferayDatabase liferayDatabase = DatabaseUtil.exportLiferayDatabase(
			DatabaseMockUtil.getConnection());

		List<Release> releases = liferayDatabase.getReleases();

		Assert.assertEquals(releases.toString(), 2, releases.size());
		Assert.assertEquals(module1Release, releases.get(0));
		Assert.assertEquals(module2Release, releases.get(1));
	}

	@Test
	public void testGetTableNames() throws Exception {
		DatabaseMockUtil.mockGetColumns(
			Arrays.asList("Table1", "Company", "Table2", "Object_x_25000"));
		DatabaseMockUtil.mockGetCompanyIds(Collections.singletonList(25000L));

		LiferayDatabase liferayDatabase = DatabaseUtil.exportLiferayDatabase(
			DatabaseMockUtil.getConnection());

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

	private void _testGetExportedCompanyId(
			List<Long> companyIds, Consumer<LiferayDatabase> consumer)
		throws Exception {

		DatabaseMockUtil.mockGetCompanyInfos(companyIds);

		consumer.accept(
			DatabaseUtil.exportLiferayDatabase(
				DatabaseMockUtil.getConnection()));
	}

	private void _testIsExportedCompanyDefault(
			Consumer<LiferayDatabase> consumer, boolean defaultPartition)
		throws Exception {

		DatabaseMockUtil.mockGetTables(defaultPartition);

		consumer.accept(
			DatabaseUtil.exportLiferayDatabase(
				DatabaseMockUtil.getConnection()));
	}

}
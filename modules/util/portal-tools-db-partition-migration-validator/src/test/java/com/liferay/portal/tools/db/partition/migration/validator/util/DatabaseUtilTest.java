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
public class DatabaseUtilTest extends MockDatabaseUtil {

	@Before
	public void setUp() throws SQLException {
		mockCompanies(Collections.emptyList());
		mockDatabaseConnection(
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString());
		mockDefaultPartition(true);
		mockGetCompanyIds(Collections.emptyList());
		mockGetCompanyInfoIds(Collections.emptyList());
		mockReleases(Collections.emptyList());
		mockTables(Collections.emptyList());
	}

	@Test
	public void testCompanyId() throws Exception {
		List<Long> companyInfoIds = new ArrayList<>();

		companyInfoIds.add(RandomTestUtil.randomLong());

		_testCompanyId(
			companyInfoIds,
			liferayDatabase -> Assert.assertEquals(
				companyInfoIds.get(0),
				(Long)liferayDatabase.getExportedCompanyId()));

		companyInfoIds.add(RandomTestUtil.randomLong());

		try {
			_testCompanyId(companyInfoIds, liferayDatabase -> Assert.fail());
		}
		catch (Exception exception) {
			Assert.assertTrue(
				exception instanceof UnsupportedOperationException);

			Assert.assertEquals(
				"Source multi company or target with DB Partitioning " +
					"disabled environments are not supported",
				exception.getMessage());
		}
	}

	@Test
	public void testDefaultPartition() throws Exception {
		_testDefaultPartition(
			true,
			liferayDatabase -> Assert.assertTrue(
				liferayDatabase.isExportedCompanyDefault()));

		_testDefaultPartition(
			false,
			liferayDatabase -> Assert.assertFalse(
				liferayDatabase.isExportedCompanyDefault()));
	}

	@Test
	public void testGetCompanies() throws Exception {
		Company company1 = new Company(
			RandomTestUtil.randomLong(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString());
		Company company2 = new Company(
			RandomTestUtil.randomLong(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString());

		mockCompanies(Arrays.asList(company1, company2));

		LiferayDatabase liferayDatabase = DatabaseUtil.exportLiferayDatabase(
			connection);

		List<Company> companies = liferayDatabase.getCompanies();

		Assert.assertEquals(companies.toString(), 2, companies.size());
		Assert.assertEquals(company1, companies.get(0));
		Assert.assertEquals(company2, companies.get(1));
	}

	@Test
	public void testGetPartitionedTableNames() throws Exception {
		mockGetCompanyIds(Collections.singletonList(25000L));
		mockTables(
			Arrays.asList("Table1", "Company", "Table2", "Object_x_25000"));

		LiferayDatabase liferayDatabase = DatabaseUtil.exportLiferayDatabase(
			connection);

		List<String> tableNames = liferayDatabase.getTableNames();

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

		mockReleases(Arrays.asList(module1Release, module2Release));

		LiferayDatabase liferayDatabase = DatabaseUtil.exportLiferayDatabase(
			connection);

		List<Release> releases = liferayDatabase.getReleases();

		Assert.assertEquals(releases.toString(), 2, releases.size());
		Assert.assertEquals(module1Release, releases.get(0));
		Assert.assertEquals(module2Release, releases.get(1));
	}

	private void _testCompanyId(
			List<Long> companyInfoIds, Consumer<LiferayDatabase> consumer)
		throws Exception {

		mockGetCompanyInfoIds(companyInfoIds);

		consumer.accept(DatabaseUtil.exportLiferayDatabase(connection));
	}

	private void _testDefaultPartition(
			boolean defaultPartition, Consumer<LiferayDatabase> consumer)
		throws Exception {

		mockDefaultPartition(defaultPartition);

		consumer.accept(DatabaseUtil.exportLiferayDatabase(connection));
	}

}
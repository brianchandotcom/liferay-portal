/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.db.partition.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.db.partition.DBPartitionUtil;
import com.liferay.portal.db.partition.test.util.BaseDBPartitionTestCase;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.util.PortalInstances;

import java.sql.Statement;

import java.util.Arrays;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alberto Chaparro
 */
@RunWith(Arquillian.class)
public class DBPartitionTest extends BaseDBPartitionTestCase {

	@BeforeClass
	public static void setUpClass() throws Exception {
		enableDBPartition();

		addDBPartition();

		insertCompanyAndDefaultUser();
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		_deleteCompanyAndDefaultUser();

		dropSchema();

		disableDBPartition();
	}

	@After
	public void tearDown() throws Exception {
		dropTable(TEST_TABLE_NAME);
	}

	@Test
	public void testAddIndexControlTable() throws Exception {
		try {
			DBPartitionUtil.forEachCompanyId(
				companyId -> db.runSQL(
					"create index IX_test on ClassName_ (value)"));
		}
		finally {
			DBPartitionUtil.forEachCompanyId(
				companyId -> {
					if (dbInspector.hasIndex("ClassName_", "IX_test")) {
						db.runSQL("drop index IX_test on ClassName_");
					}
				});
		}
	}

	@Test
	public void testAddUniqueIndexControlTable() throws Exception {
		try {
			DBPartitionUtil.forEachCompanyId(
				companyId -> db.runSQL(
					"create unique index IX_test on ClassName_ (value)"));
		}
		finally {
			DBPartitionUtil.forEachCompanyId(
				companyId -> {
					if (dbInspector.hasIndex("ClassName_", "IX_test")) {
						db.runSQL("drop index IX_test on ClassName_");
					}
				});
		}
	}

	@Test
	public void testAlterControlTable() throws Exception {
		try {
			DBPartitionUtil.forEachCompanyId(
				companyId -> db.runSQL(
					"alter table ClassName_ add column testColumn bigint"));
		}
		finally {
			DBPartitionUtil.forEachCompanyId(
				companyId -> {
					if (dbInspector.hasColumn("ClassName_", "testColumn")) {
						db.runSQL(
							"alter table ClassName_ drop column testColumn");
					}
				});
		}
	}

	@Test
	public void testDropIndexControlTable() throws Exception {
		try {
			DBPartitionUtil.forEachCompanyId(
				companyId -> db.runSQL("drop index IX_B27A301F on ClassName_"));
		}
		finally {
			DBPartitionUtil.forEachCompanyId(
				companyId -> {
					if (dbInspector.hasIndex("ClassName_", "IX_test")) {
						db.runSQL(
							"create unique index IX_B27A301F on ClassName_ " +
								"(value);");
					}
				});
		}
	}

	@Test
	public void testUpdateIndexes() throws Exception {
		try {
			DBPartitionUtil.forEachCompanyId(
				companyId -> {
					createAndPopulateTable(TEST_TABLE_NAME);

					Assert.assertFalse(
						dbInspector.hasIndex(TEST_TABLE_NAME, TEST_INDEX_NAME));

					db.updateIndexes(
						connection, getCreateTableSQL(TEST_TABLE_NAME),
						getCreateIndexSQL(TEST_TABLE_NAME), true);

					Assert.assertTrue(
						dbInspector.hasIndex(TEST_TABLE_NAME, TEST_INDEX_NAME));
				});
		}
		finally {
			DBPartitionUtil.forEachCompanyId(
				companyId -> dropTable(TEST_TABLE_NAME));
		}
	}

	@Test
	public void testUpgrade() throws Exception {
		DBPartitionUpgradeProcess dbPartitionUpgradeProcess =
			new DBPartitionUpgradeProcess();

		dbPartitionUpgradeProcess.upgrade();

		long[] expectedCompanyIds = PortalInstances.getCompanyIdsBySQL();

		Arrays.sort(expectedCompanyIds);

		long[] actualCompanyIds = dbPartitionUpgradeProcess.getCompanyIds();

		Arrays.sort(actualCompanyIds);

		Assert.assertArrayEquals(expectedCompanyIds, actualCompanyIds);
	}

	public class DBPartitionUpgradeProcess extends UpgradeProcess {

		public long[] getCompanyIds() {
			return _companyIds;
		}

		@Override
		protected void doUpgrade() throws Exception {
			_companyIds = ArrayUtil.append(
				_companyIds, CompanyThreadLocal.getCompanyId());
		}

		private long[] _companyIds = new long[0];

	}

	private static void _deleteCompanyAndDefaultUser() throws Exception {
		try (Statement statement = connection.createStatement()) {
			statement.execute(
				"delete from Company where companyId = " + COMPANY_ID);

			statement.execute(
				"delete from User_ where companyId = " + COMPANY_ID);
		}
	}

}
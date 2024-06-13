/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.db.partition.db;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Mariano Álvaro Sáiz
 */
public class DBPartitionPostgreSQLDBTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetSafeAlterTable() {
		_testGetSafeAlterTable(
			"ALTER TABLE test ALTER COLUMN column1 DROP DEFAULT",
			StringPool.BLANK);
		_testGetSafeAlterTable(
			"ALTER TABLE test ALTER COLUMN column1 DROP NOT NULL",
			StringPool.BLANK);
		_testGetSafeAlterTable(
			"ALTER TABLE test DROP COLUMN column1 CASCADE", StringPool.BLANK);
		_testGetSafeAlterTable(
			"ALTER TABLE test DROP COLUMN column1", " cascade");
		_testGetSafeAlterTable(
			"ALTER TABLE test DROP CONSTRAINT constraint1 CASCADE",
			StringPool.BLANK);
		_testGetSafeAlterTable(
			"ALTER TABLE test DROP CONSTRAINT constraint1", " cascade");
		_testGetSafeAlterTable("ALTER TABLE test DROP column1", " cascade");
	}

	private void _testGetSafeAlterTable(String sql, String sqlExpectedSuffix) {
		Assert.assertEquals(
			sql + sqlExpectedSuffix,
			_dbPartitionPostgreSQLDB.getSafeAlterTable(sql));
	}

	private final DBPartitionPostgreSQLDB _dbPartitionPostgreSQLDB =
		new DBPartitionPostgreSQLDB();

}
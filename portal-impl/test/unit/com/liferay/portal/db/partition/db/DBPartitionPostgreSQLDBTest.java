/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.db.partition.db;

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
	public void testGetSafeAlterTable() throws Exception {
		DBPartitionPostgreSQLDB dbPartitionPostgreSQLDB =
			new DBPartitionPostgreSQLDB();

		// No change performed over SQL

		for (String sql :
				new String[] {
					"ALTER TABLE test ALTER COLUMN column1 DROP DEFAULT",
					"ALTER TABLE test ALTER COLUMN column1 DROP NOT NULL",
					"ALTER TABLE test DROP CONSTRAINT constraint1 CASCADE",
					"ALTER TABLE test DROP COLUMN column1 CASCADE"
				}) {

			Assert.assertEquals(
				sql, dbPartitionPostgreSQLDB.getSafeAlterTable(sql));
		}

		// cascade appended at the end of the SQL

		for (String sql :
				new String[] {
					"ALTER TABLE test DROP CONSTRAINT constraint1",
					"ALTER TABLE test DROP COLUMN column1",
					"ALTER TABLE test DROP column1"
				}) {

			Assert.assertEquals(
				sql + " cascade",
				dbPartitionPostgreSQLDB.getSafeAlterTable(sql));
		}
	}

}
/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.dao.db.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.dao.db.IndexMetadata;
import com.liferay.portal.kernel.test.ReflectionTestUtil;

import java.sql.Connection;
import java.sql.Statement;

import java.util.List;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jorge Avalos
 */
@RunWith(Arquillian.class)
public class OracleDBTest extends DBTest {

	public static void assume() {
		db = DBManagerUtil.getDB();

		dbInspector = new DBInspector(connection);

		Assume.assumeTrue(db.getDBType() == DBType.ORACLE);
	}

	@Test
	public void testGetIndexMetadatasWithLockedStatisticsTable()
		throws Exception {

		addIndex(new String[] {"typeVarchar", "typeBoolean"});

		try (Statement statement = connection.createStatement()) {
			statement.execute(
				StringBundler.concat(
					"CALL dbms_stats.lock_table_stats(ownname => '",
					connection.getSchema(), "', tabname => '", TABLE_NAME_1,
					"')"));

			try {
				List<IndexMetadata> indexMetadatas = ReflectionTestUtil.invoke(
					db, "getIndexMetadatas",
					new Class<?>[] {
						Connection.class, String.class, String.class,
						boolean.class
					},
					connection, TABLE_NAME_1, "typeVarchar", false);

				for (IndexMetadata indexMetadata : indexMetadatas) {
					Assert.assertEquals(
						dbInspector.normalizeName(INDEX_NAME),
						indexMetadata.getIndexName());
				}
			}
			finally {
				statement.execute(
					StringBundler.concat(
						"CALL dbms_stats.unlock_table_stats(ownname => '",
						connection.getSchema(), "', tabname => '", TABLE_NAME_1,
						"')"));
			}
		}
	}

}
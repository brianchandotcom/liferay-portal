/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.dao.db.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.dao.db.IndexMetadata;

import java.util.List;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Luis Ortiz
 */
@RunWith(Arquillian.class)
public class PostgreSQLDBTest extends DBTest {

	public static void assume() {
		db = DBManagerUtil.getDB();

		dbInspector = new DBInspector(connection);

		Assume.assumeTrue(db.getDBType() == DBType.POSTGRESQL);
	}

	@Test
	public void testGetAndAddIndexWithLeftClause() throws Exception {
		addIndex(new String[] {_INDEX_COLUMN_NAME_LEFT_CLAUSE});

		List<IndexMetadata> indexMetadatas = db.getIndexes(
			connection, TABLE_NAME_1, null, false);

		_assertIndex(indexMetadatas);

		db.dropIndexes(connection, TABLE_NAME_1, null);

		db.addIndexes(connection, indexMetadatas);

		_assertIndex(db.getIndexes(connection, TABLE_NAME_1, null, false));

		db.dropIndexes(connection, TABLE_NAME_1, null);

		db.runSQL(
			indexMetadatas.get(
				0
			).getCreateSQL(
				null
			));

		_assertIndex(db.getIndexes(connection, TABLE_NAME_1, null, false));
	}

	private void _assertIndex(List<IndexMetadata> indexMetadatas)
		throws Exception {

		Assert.assertEquals(
			indexMetadatas.toString(), 1, indexMetadatas.size());
		Assert.assertEquals(
			dbInspector.normalizeName(INDEX_NAME),
			indexMetadatas.get(
				0
			).getIndexName());
		Assert.assertEquals(
			1,
			indexMetadatas.get(
				0
			).getColumnNames(
			).length);
		Assert.assertEquals(
			dbInspector.normalizeName(_INDEX_COLUMN_NAME_LEFT_CLAUSE),
			indexMetadatas.get(
				0
			).getColumnNames()[0]);
	}

	private static final String _INDEX_COLUMN_NAME_LEFT_CLAUSE =
		"left(typeText, 15)";

}
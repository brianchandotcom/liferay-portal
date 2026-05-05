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

import java.util.Arrays;
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

		List<IndexMetadata> indexMetadatas = db.getIndexMetadatas(
			connection, TABLE_NAME_1, null, false);

		_assertIndexMetadatas(indexMetadatas);

		db.dropIndexes(connection, TABLE_NAME_1, null);

		db.addIndexes(connection, indexMetadatas);

		_assertIndexMetadatas(
			db.getIndexMetadatas(connection, TABLE_NAME_1, null, false));

		db.dropIndexes(connection, TABLE_NAME_1, null);

		IndexMetadata indexMetadata = indexMetadatas.get(0);

		db.runSQL(indexMetadata.getCreateSQL(null));

		_assertIndexMetadatas(
			db.getIndexMetadatas(connection, TABLE_NAME_1, null, false));
	}

	private void _assertIndexMetadatas(List<IndexMetadata> indexMetadatas)
		throws Exception {

		Assert.assertEquals(
			indexMetadatas.toString(), 1, indexMetadatas.size());

		IndexMetadata indexMetadata = indexMetadatas.get(0);

		Assert.assertEquals(
			dbInspector.normalizeName(INDEX_NAME),
			indexMetadata.getIndexName());

		String[] columnNames = indexMetadata.getColumnNames();

		Assert.assertEquals(
			Arrays.toString(columnNames), 1, columnNames.length);
		Assert.assertEquals(
			dbInspector.normalizeName(_INDEX_COLUMN_NAME_LEFT_CLAUSE),
			columnNames[0]);
	}

	private static final String _INDEX_COLUMN_NAME_LEFT_CLAUSE =
		"left(typeText, 15)";

}
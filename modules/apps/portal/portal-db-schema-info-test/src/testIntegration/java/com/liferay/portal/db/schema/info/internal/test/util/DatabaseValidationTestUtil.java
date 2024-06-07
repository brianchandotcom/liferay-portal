/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.db.schema.info.internal.test.util;

import java.io.File;

import java.util.List;

import javax.sql.DataSource;

import org.junit.Assert;

/**
 * @author Mariano Álvaro Sáiz
 */
public class DatabaseValidationTestUtil {

	public static void assertDatabaseDumpMirrorsCurrentDatabase(String dumpPath)
		throws Exception {

		DatabaseTestUtil.createSchema(_COPY_SCHEMA_NAME);

		DataSource targetDataSource = null;

		try {
			targetDataSource = DatabaseTestUtil.initSchemaDataSource(
				_COPY_SCHEMA_NAME);

			DatabaseTestUtil.importFileTo(
				new File(dumpPath, "tables.sql"), targetDataSource);
			DatabaseTestUtil.importFileTo(
				new File(dumpPath, "indexes.sql"), targetDataSource);
			DatabaseTestUtil.importFileTo(
				new File(dumpPath, "sequences.sql"), targetDataSource);

			_assertSameIndexesStructure(targetDataSource);
			_assertSameTablesStructure(targetDataSource);
		}
		finally {
			DatabaseTestUtil.dropSchema(_COPY_SCHEMA_NAME);

			if (targetDataSource != null) {
				DatabaseTestUtil.destroyDataSource(targetDataSource);
			}
		}
	}

	private static void _assertSameIndexesStructure(DataSource targetDataSource)
		throws Exception {

		List<String> sourceIndexes = DatabaseTestUtil.getSourceIndexes();
		List<String> targetIndexes = DatabaseTestUtil.getTargetIndexes(
			targetDataSource);

		Assert.assertEquals(
			targetIndexes.toString(), sourceIndexes.size(),
			targetIndexes.size());

		for (int i = 0; i < sourceIndexes.size(); i++) {
			Assert.assertEquals(sourceIndexes.get(i), targetIndexes.get(i));
		}
	}

	private static void _assertSameTablesStructure(DataSource targetDataSource)
		throws Exception {

		List<String> sourceColumns = DatabaseTestUtil.getSourceTables();
		List<String> targetColumns = DatabaseTestUtil.getTargetTables(
			targetDataSource);

		Assert.assertEquals(
			targetColumns.toString(), sourceColumns.size(),
			targetColumns.size());

		for (int i = 0; i < sourceColumns.size(); i++) {
			Assert.assertEquals(sourceColumns.get(i), targetColumns.get(i));
		}
	}

	private static final String _COPY_SCHEMA_NAME = "copyschema";

}
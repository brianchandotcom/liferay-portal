/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.db;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import java.net.URL;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import org.mockito.ArgumentMatchers;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import org.osgi.framework.Bundle;

/**
 * @author Mariano Álvaro Sáiz
 */
public class DBResourceUtilTest {

	@Test
	public void testGetModuleIndexesSQL() throws Exception {
		_testGetModuleIndexesSQL(StringPool.NEW_LINE);
		_testGetModuleIndexesSQL(StringPool.RETURN_NEW_LINE);
	}

	@Test
	public void testGetModuleTablesPrimaryKeyColumnNamesWithNullTablesSQL()
		throws Exception {

		Bundle bundle = Mockito.mock(Bundle.class);

		Mockito.when(
			bundle.getResource(ArgumentMatchers.anyString())
		).thenReturn(
			null
		);

		Map<String, String[]> moduleTablesPrimaryKeyColumnNames =
			DBResourceUtil.getModuleTablesPrimaryKeyColumnNames(bundle);

		Assert.assertTrue(moduleTablesPrimaryKeyColumnNames.isEmpty());
	}

	@Test
	public void testGetModuleTablesPrimaryKeyColumnNamesWithTablesSQL()
		throws Exception {

		Bundle bundle = Mockito.mock(Bundle.class);

		URL url = Mockito.mock(URL.class);

		String sql = StringBundler.concat(
			"create table TestTable (testTableId LONG not null primary key);",
			"create table TestTable2 (columnA LONG default 0 not null, ",
			"columnB LONG not null, primary key (columnA, columnB));");

		Mockito.when(
			url.openStream()
		).thenReturn(
			new ByteArrayInputStream(sql.getBytes())
		);

		Mockito.when(
			bundle.getResource(ArgumentMatchers.anyString())
		).thenReturn(
			url
		);

		Map<String, String[]> moduleTablesPrimaryKeyColumnNames =
			DBResourceUtil.getModuleTablesPrimaryKeyColumnNames(bundle);

		Assert.assertArrayEquals(
			new String[] {"testTableId"},
			moduleTablesPrimaryKeyColumnNames.get("TestTable"));
		Assert.assertArrayEquals(
			new String[] {"columnA", "columnB"},
			moduleTablesPrimaryKeyColumnNames.get("TestTable2"));
	}

	@Test
	public void testGetPortalTablesPrimaryKeyColumnNames() throws Exception {
		try (MockedStatic<StringUtil> stringUtilMockedStatic =
				Mockito.mockStatic(
					StringUtil.class, Mockito.CALLS_REAL_METHODS)) {

			stringUtilMockedStatic.when(
				() -> StringUtil.read(
					Mockito.nullable(Class.class), Mockito.anyString())
			).thenReturn(
				StringBundler.concat(
					"create table Company (companyId LONG not null primary key",
					"); create table VirtualHost (ctCollectionId LONG default ",
					"0 not null, virtualHostId LONG not null, primary key ",
					"(virtualHostId, ctCollectionId));")
			);

			Map<String, String[]> portalTablesPrimaryKeyColumnNames =
				DBResourceUtil.getPortalTablesPrimaryKeyColumnNames();

			Assert.assertArrayEquals(
				new String[] {"companyId"},
				portalTablesPrimaryKeyColumnNames.get("Company"));
			Assert.assertArrayEquals(
				new String[] {"virtualHostId", "ctCollectionId"},
				portalTablesPrimaryKeyColumnNames.get("VirtualHost"));
		}
	}

	private InputStream _getSQLFileInputStream(String lineSeparator) {
		String sqlFile = StringBundler.concat(
			"create index IX_TEST1 on Table1 (field1);", lineSeparator,
			"create index IX_TEST2 on Table1 (field2);", lineSeparator,
			lineSeparator, "create index IX_TEST3 on Table2 (field);",
			lineSeparator);

		return new ByteArrayInputStream(sqlFile.getBytes());
	}

	private void _testGetModuleIndexesSQL(String lineSeparator)
		throws Exception {

		Bundle bundle = Mockito.mock(Bundle.class);

		URL url = Mockito.mock(URL.class);

		Mockito.when(
			url.openStream()
		).thenReturn(
			_getSQLFileInputStream(lineSeparator)
		);

		Mockito.when(
			bundle.getResource(ArgumentMatchers.anyString())
		).thenReturn(
			url
		);

		String moduleIndexesSQL = DBResourceUtil.getModuleIndexesSQL(bundle);

		Assert.assertTrue(
			!moduleIndexesSQL.contains(StringPool.RETURN_NEW_LINE));
	}

}
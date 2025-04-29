/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.upgrade.util;

import com.liferay.portal.kernel.util.HashMapBuilder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * @author Luis Ortiz
 */
public class JakartaUpgradeProcessUtilTest {

	@Before
	public void setUp() {
		_mockitoAnnotationsAutoCloseable = MockitoAnnotations.openMocks(this);
	}

	@After
	public void tearDown() throws Exception {
		_mockitoAnnotationsAutoCloseable.close();
	}

	@Test
	public void testGetSelectResultSetData() throws SQLException {
		String columnName = "testColumn";
		String columnValue = "javax.servlet.http.HttpServlet";
		Object primaryKey1Value = 1;
		Object primaryKey2Value = "test";
		String[] primaryKeyColumnNames = {"primaryKey1", "primaryKey2"};

		Mockito.when(
			_resultSet.getObject("primaryKey1")
		).thenReturn(
			primaryKey1Value
		);

		Mockito.when(
			_resultSet.getObject("primaryKey2")
		).thenReturn(
			primaryKey2Value
		);

		Mockito.when(
			_resultSet.getString("testColumn")
		).thenReturn(
			columnValue
		);

		Object[] result = JakartaUpgradeProcessUtil.getSelectResultSetData(
			columnName, primaryKeyColumnNames, _resultSet);

		Assert.assertArrayEquals(
			new Object[] {primaryKey1Value, primaryKey2Value, columnValue},
			result);
	}

	@Test
	public void testGetSelectSQL() {
		String columnName = "testColumn";
		String[] primaryKeyColumnNames = {"primaryKey1"};
		String tableName = "testTable";

		Assert.assertEquals(
			"select primaryKey1, testColumn from testTable where testColumn " +
				"is not null",
			JakartaUpgradeProcessUtil.getSelectSQL(
				columnName, primaryKeyColumnNames, tableName));

		primaryKeyColumnNames = new String[] {"primaryKey1", "primaryKey2"};

		Assert.assertEquals(
			"select primaryKey1, primaryKey2, testColumn from testTable " +
				"where testColumn is not null",
			JakartaUpgradeProcessUtil.getSelectSQL(
				columnName, primaryKeyColumnNames, tableName));
	}

	@Test
	public void testGetUpdateSQL() {
		String columnName = "testColumn";
		String[] primaryKeyColumnNames = {"primaryKey1"};
		String tableName = "testTable";

		Assert.assertEquals(
			"update testTable set testColumn = ? where primaryKey1 = ?",
			JakartaUpgradeProcessUtil.getUpdateSQL(
				columnName, primaryKeyColumnNames, tableName));

		primaryKeyColumnNames = new String[] {"primaryKey1", "primaryKey2"};

		Assert.assertEquals(
			"update testTable set testColumn = ? where primaryKey1 = ? and " +
				"primaryKey2 = ?",
			JakartaUpgradeProcessUtil.getUpdateSQL(
				columnName, primaryKeyColumnNames, tableName));
	}

	@Test
	public void testReplace() {
		_testReplace(
			HashMapBuilder.put(
				"javax-batch-operations", "jakarta-batch-operations"
			).put(
				"javax.portlet.Portlet", "jakarta.portlet.Portlet"
			).put(
				"javax/persistence/cache", "jakarta/persistence/cache"
			).build());
	}

	@Test
	public void testReplaceNullValue() {
		Assert.assertNull(JakartaUpgradeProcessUtil.replace(null));
	}

	@Test
	public void testReplaceWithCustomSeparators() {
		_testReplace(
			HashMapBuilder.put(
				"javax$persistence$cache", "jakarta$persistence$cache"
			).put(
				"javax.portlet.Portlet", "jakarta.portlet.Portlet"
			).put(
				"javax@batch@operations", "jakarta@batch@operations"
			).build(),
			new char[] {'@', '$'});
	}

	@Test
	public void testReplaceWithFixupSubpackage() {
		_testReplace(
			HashMapBuilder.put(
				"javax-transaction-xa-XAResource",
				"javax-transaction-xa-XAResource"
			).put(
				"javax.annotation.processing.Processor",
				"javax.annotation.processing.Processor"
			).build());
	}

	@Test
	public void testReplaceWithFixupSubpackageAndCustomSeparator() {
		_testReplace(
			HashMapBuilder.put(
				"javax$transaction$xa$XAResource",
				"javax$transaction$xa$XAResource"
			).put(
				"javax@annotation@processing@Processor",
				"javax@annotation@processing@Processor"
			).build(),
			new char[] {'@', '$'});
	}

	@Test
	public void testReplaceWithMultipleSubpackages() {
		_testReplace(
			HashMapBuilder.put(
				"import javax.portlet.Portlet;\nimport javax.batch.operations;",
				"import jakarta.portlet.Portlet;\nimport " +
					"jakarta.batch.operations;"
			).build());
	}

	@Test
	public void testReplaceWithMultipleSubpackagesAndCustomSeparator() {
		_testReplace(
			HashMapBuilder.put(
				"import javax@portlet@Portlet;\nimport javax$batch$operations;",
				"import jakarta@portlet@Portlet;\nimport " +
					"jakarta$batch$operations;"
			).build(),
			new char[] {'@', '$'});
	}

	@Test
	public void testReplaceWithNoMatch() {
		_testReplace(
			HashMapBuilder.put(
				"com.liferay.portal.kernel.util.StringUtil",
				"com.liferay.portal.kernel.util.StringUtil"
			).build());
	}

	@Test
	public void testReplaceWithNoMatchAndCustomSeparator() {
		_testReplace(
			HashMapBuilder.put(
				"com@liferay@portal@kernel@util@StringUtil",
				"com@liferay@portal@kernel@util@StringUtil"
			).put(
				"javax$activity$ActivityCompletedException",
				"javax$activity$ActivityCompletedException"
			).build(),
			new char[] {'@', '$'});
	}

	@Test
	public void testReplaceWithXJavaxPortletNamespacedResponse() {
		_testReplace(
			HashMapBuilder.put(
				"X-JAVAX-PORTLET-NAMESPACED-RESPONSE",
				"X-JAKARTA-PORTLET-NAMESPACED-RESPONSE"
			).build());
	}

	@Test
	public void testUpdateJakartaValue() throws SQLException {
		char[] customSeparators = {};
		String[] primaryKeyColumnNames = {"id", "uuid"};
		Object[] values = {1L, "uuid", "import javax.portlet.Portlet"};

		Assert.assertEquals(
			"(1, uuid)",
			JakartaUpgradeProcessUtil.updateJakartaValue(
				customSeparators, _preparedStatement, primaryKeyColumnNames,
				values));

		values = new Object[] {1L, "uuid", "import jakarta.portlet.Portlet"};

		Assert.assertNull(
			JakartaUpgradeProcessUtil.updateJakartaValue(
				customSeparators, _preparedStatement, primaryKeyColumnNames,
				values));

		values = new Object[] {1L, "uuid", null};

		Assert.assertNull(
			JakartaUpgradeProcessUtil.updateJakartaValue(
				customSeparators, _preparedStatement, primaryKeyColumnNames,
				values));

		customSeparators = new char[] {'$'};
		values = new Object[] {1L, "uuid", "import javax$portlet$Portlet"};

		Assert.assertEquals(
			"(1, uuid)",
			JakartaUpgradeProcessUtil.updateJakartaValue(
				customSeparators, _preparedStatement, primaryKeyColumnNames,
				values));
	}

	private void _testReplace(Map<String, String> replacements) {
		for (Map.Entry<String, String> entry : replacements.entrySet()) {
			Assert.assertEquals(
				entry.getValue(),
				JakartaUpgradeProcessUtil.replace(entry.getKey()));
		}
	}

	private void _testReplace(
		Map<String, String> replacements, char[] customSeparators) {

		for (Map.Entry<String, String> entry : replacements.entrySet()) {
			Assert.assertEquals(
				entry.getValue(),
				JakartaUpgradeProcessUtil.replace(
					entry.getKey(), customSeparators));
		}
	}

	private AutoCloseable _mockitoAnnotationsAutoCloseable;

	@Mock
	private PreparedStatement _preparedStatement;

	@Mock
	private ResultSet _resultSet;

}
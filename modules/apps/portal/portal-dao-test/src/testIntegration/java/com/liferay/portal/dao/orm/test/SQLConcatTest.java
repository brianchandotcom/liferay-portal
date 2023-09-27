/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.dao.orm.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.dao.orm.common.SQLTransformer;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Michael Bowerman
 */
@RunWith(Arquillian.class)
public class SQLConcatTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_db = DBManagerUtil.getDB();

		_db.runSQL(
			"create table SQLConcatTest (data VARCHAR(10) not null primary " +
				"key)");

		_db.runSQL("insert into SQLConcatTest values ('test')");
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		_db.runSQL("drop table SQLConcatTest");
	}

	@Test
	public void testConcat() throws Exception {
		_assertConcat(
			"select CONCAT('This is a ', data) from SQLConcatTest",
			"This is a test");
	}

	@Test
	public void testConcatWithCommas() throws Exception {
		_assertConcat(
			"select CONCAT('This, is, a, ', data, ', for, commas') from " +
				"SQLConcatTest",
			"This, is, a, test, for, commas");
	}

	@Test
	public void testConcatWithManyExpressions() throws Exception {
		_assertConcat(
			"select CONCAT('This ', 'is ', 'a ', data, ' with ', 'seven '" +
				", 'expressions') from SQLConcatTest",
			"This is a test with seven expressions");
	}

	@Test
	public void testConcatWithNestedConcats() throws Exception {
		_assertConcat(
			"select CONCAT('This ', 'is ', 'a ', CONCAT(data, ' for '" +
				", CONCAT('nested ', 'concats'))) from SQLConcatTest",
			"This is a test for nested concats");
	}

	@Test
	public void testConcatWithParentheses() throws Exception {
		_assertConcat(
			"select CONCAT('This ( ', 'is ( ', '(a) ', (REPLACE(data, 'test'" +
				", 'test to ensure parentheses are parsed correctly'))) from " +
					"SQLConcatTest",
			"This ( is ( (a) test to ensure parentheses are parsed correctly");
	}

	@Test
	public void testConcatWithUnspacedArguments() throws Exception {
		_assertConcat(
			"select CONCAT('This is a ',data,' for unspaced arguments') from " +
				"SQLConcatTest",
			"This is a test for unspaced arguments");
	}

	private void _assertConcat(String query, String expected) throws Exception {
		try (Connection connection = DataAccess.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				SQLTransformer.transform(query));
			ResultSet resultSet = preparedStatement.executeQuery()) {

			Assert.assertTrue(resultSet.next());

			Assert.assertEquals(expected, resultSet.getString(1));

			Assert.assertFalse(resultSet.next());
		}
	}

	private static DB _db;

}
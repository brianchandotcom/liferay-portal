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

package com.liferay.portal.dao.orm;

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

/**
 * @author Michael Bowerman
 */
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

	private void _assertConcat(String query, String expected) throws Exception {
		try (Connection con = DataAccess.getUpgradeOptimizedConnection();
			PreparedStatement ps = con.prepareStatement(
				SQLTransformer.transform(query));
			ResultSet rs = ps.executeQuery()) {

			Assert.assertTrue(rs.next());

			Assert.assertEquals(expected, rs.getString(1));

			Assert.assertFalse(rs.next());
		}
	}

	private static DB _db;

}
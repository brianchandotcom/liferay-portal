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
			"create table SQLConcatTest (id LONG not null primary key, data " +
				"VARCHAR(10) null)");

		_db.runSQL("insert into SQLConcatTest (id, data) values (1, 'test')");
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		_db.runSQL("drop table SQLConcatTest");
	}

	@Test
	public void testConcat() throws Exception {
		String sql = _db.buildSQL(
			"select CONCAT('This is a ', data) from SQLConcatTest where id = " +
				"1");

		sql = SQLTransformer.transform(sql);

		try (Connection connection = DataAccess.getUpgradeOptimizedConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				sql);
			ResultSet resultSet = preparedStatement.executeQuery()) {

			Assert.assertTrue(resultSet.next());

			Assert.assertEquals("This is a test", resultSet.getString(1));

			Assert.assertFalse(resultSet.next());
		}
	}

	@Test
	public void testConcatWithManyExpressions() throws Exception {
		String sql = _db.buildSQL(
			"select CONCAT('This ', 'is ', 'a ', data, ' with ', 'seven ', " +
				"'expressions') from SQLConcatTest where id = 1");

		sql = SQLTransformer.transform(sql);

		try (Connection connection = DataAccess.getUpgradeOptimizedConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				sql);
			ResultSet resultSet = preparedStatement.executeQuery()) {

			Assert.assertTrue(resultSet.next());

			Assert.assertEquals(
				"This is a test with seven expressions",
				resultSet.getString(1));

			Assert.assertFalse(resultSet.next());
		}
	}

	@Test
	public void testConcatWithNestedConcats() throws Exception {
		String sql = _db.buildSQL(
			"select CONCAT('This ', 'is ', 'a ', CONCAT(data, ' for ', " +
				"CONCAT('nested ', 'concats'))) from SQLConcatTest where id " +
					"= 1");

		sql = SQLTransformer.transform(sql);

		try (Connection connection = DataAccess.getUpgradeOptimizedConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				sql);
			ResultSet resultSet = preparedStatement.executeQuery()) {

			Assert.assertTrue(resultSet.next());

			Assert.assertEquals(
				"This is a test for nested concats", resultSet.getString(1));

			Assert.assertFalse(resultSet.next());
		}
	}

	@Test
	public void testConcatWithParentheses() throws Exception {
		String sql = _db.buildSQL(
			"select CONCAT('This ( ', 'is ( ', '(a) ', (REPLACE(data, " +
				"'test', 'test to ensure parentheses are parsed " +
					"correctly'))) from SQLConcatTest where id = 1");

		sql = SQLTransformer.transform(sql);

		try (Connection connection = DataAccess.getUpgradeOptimizedConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				sql);
			ResultSet resultSet = preparedStatement.executeQuery()) {

			Assert.assertTrue(resultSet.next());

			Assert.assertEquals(
				"This ( is ( (a) test to ensure parentheses are parsed " +
					"correctly",
				resultSet.getString(1));

			Assert.assertFalse(resultSet.next());
		}
	}

	private static DB _db;

}
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
import com.liferay.portal.kernel.test.rule.AssumeTestRule;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PropsValues;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Michael Bowerman
 */
public class SQLEscapedConcatTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new AssumeTestRule("assume"), new LiferayIntegrationTestRule());

	public static void assume() {
		String jdbcDefaultURL = PropsValues.JDBC_DEFAULT_URL;

		Assume.assumeTrue(
			jdbcDefaultURL.contains("mysql") ||
			jdbcDefaultURL.contains("mariadb"));
	}

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
	public void testConcatWithEscapedQuotes() throws Exception {
		try (Connection con = DataAccess.getUpgradeOptimizedConnection();
			PreparedStatement ps = con.prepareStatement(
				SQLTransformer.transform(
					"select CONCAT('This is a \\'', data, '\\' for escaped " +
						"quotes') from SQLConcatTest"));
			ResultSet rs = ps.executeQuery()) {

			Assert.assertTrue(rs.next());

			Assert.assertEquals(
				"This is a 'test' for escaped quotes", rs.getString(1));

			Assert.assertFalse(rs.next());
		}
	}

	private static DB _db;

}
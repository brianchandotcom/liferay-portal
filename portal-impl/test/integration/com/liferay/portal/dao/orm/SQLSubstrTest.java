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
 * @author Cristina González
 */
public class SQLSubstrTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_db = DBManagerUtil.getDB();

		_db.runSQL(
			"create table TestSubStr (id LONG not null primary key, data " +
				"VARCHAR(10) null)");

		_db.runSQL("insert into TestSubStr values (1, 'EXAMPLE')");
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		_db.runSQL("drop table TestSubStr");
	}

	@Test
	public void testSubstr() throws Exception {
		String sql = _db.buildSQL(
			"select substr(data,3,2) from TestSubStr where id = 1");

		sql = SQLTransformer.transform(sql);

		try (Connection connection = DataAccess.getConnection();
			PreparedStatement preparedStatement =
				connection.prepareStatement(sql)) {

			ResultSet resultSet = preparedStatement.executeQuery();

			Assert.assertNotNull(resultSet.next());

			String substring = resultSet.getString(1);

			Assert.assertEquals("AM", substring);
		}
	}

	@Test
	public void testSubstrStart() throws Exception {
		String sql = _db.buildSQL(
			"select substr(data,1,3) from TestSubStr where id = 1");

		sql = SQLTransformer.transform(sql);

		try (Connection connection = DataAccess.getConnection();
			PreparedStatement ps = connection.prepareStatement(sql)) {

			ResultSet rs = ps.executeQuery();

			Assert.assertNotNull(rs.next());

			String substring = rs.getString(1);

			Assert.assertEquals("EXA", substring);
		}
	}

	private static DB _db;

}
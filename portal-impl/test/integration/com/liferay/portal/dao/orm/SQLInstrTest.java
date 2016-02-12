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
import com.liferay.portal.kernel.test.rule.TransactionalTestRule;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Cristina González
 */
public class SQLInstrTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), TransactionalTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() throws Exception {
		_db = DBManagerUtil.getDB();

		_db.runSQL(_CREATE_TABLE_SQL);

		_db.runSQL(_INSERT_SQL);
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		_db.runSQL(_DROP_TABLE);
	}

	@Test
	public void testInstr() throws IOException, SQLException {
		String sql = "select INSTR(data,'X') from TestInstr where id = 1";

		String sqlBuild = _db.buildSQL(sql);

		String sqlTransform = SQLTransformer.transform(sqlBuild);

		try (Connection connection = DataAccess.getConnection();
			PreparedStatement preparedStatement =
				connection.prepareStatement(sqlTransform)) {

			ResultSet resultSet = preparedStatement.executeQuery();

			Assert.assertNotNull(resultSet.next());

			long location = resultSet.getLong(1);

			Assert.assertEquals(2, location);
		}
	}

	@Test
	public void testInstrNotFound() throws IOException, SQLException {
		String sql = "select INSTR(data,'?') from TestInstr where id = 1";

		String sqlBuild = _db.buildSQL(sql);

		String sqlTransform = SQLTransformer.transform(sqlBuild);

		try (Connection connection = DataAccess.getConnection();
			PreparedStatement preparedStatement =
				connection.prepareStatement(sqlTransform)) {

			ResultSet resultSet = preparedStatement.executeQuery();

			Assert.assertNotNull(resultSet.next());

			long location = resultSet.getLong(1);

			Assert.assertEquals(0, location);
		}
	}

	private static final String _CREATE_TABLE_SQL =
		"create table TestInstr (id LONG not null primary key, " +
			"data VARCHAR(10) null)";

	private static final String _DROP_TABLE = "drop table TestInstr";

	private static final String _INSERT_SQL =
		"insert into TestInstr values (1, 'EXAMPLE')";

	private static DB _db;

}
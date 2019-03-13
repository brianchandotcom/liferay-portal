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
 * @author Cristina González
 */
@RunWith(Arquillian.class)
public class SQLInstrTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_db = DBManagerUtil.getDB();

		_db.runSQL(
			"create table SQLInstrTest (id LONG not null primary key, data " +
				"VARCHAR(10) null)");

		_db.runSQL("insert into SQLInstrTest values (1, 'EXAMPLE')");
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		_db.runSQL("drop table SQLInstrTest");
	}

	@Test
	public void testInstr() throws Exception {
		String sql = _db.buildSQL(
			"select INSTR(data, 'X') from SQLInstrTest where id = 1");

		sql = SQLTransformer.transform(sql);

		try (Connection connection = DataAccess.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				sql)) {

			ResultSet resultSet = preparedStatement.executeQuery();

			Assert.assertNotNull(resultSet.next());

			long location = resultSet.getLong(1);

			Assert.assertEquals(2, location);
		}
	}

	@Test
	public void testInstrNotFound() throws Exception {
		String sql = _db.buildSQL(
			"select INSTR(data, '?') from SQLInstrTest where id = 1");

		sql = SQLTransformer.transform(sql);

		try (Connection connection = DataAccess.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				sql)) {

			ResultSet resultSet = preparedStatement.executeQuery();

			Assert.assertNotNull(resultSet.next());

			long location = resultSet.getLong(1);

			Assert.assertEquals(0, location);
		}
	}

	private static DB _db;

}
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
import java.sql.ResultSet;
import java.sql.Statement;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Adolfo Pérez
 */
@RunWith(Arquillian.class)
public class SQLNullDateTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_db = DBManagerUtil.getDB();

		_db.runSQL(
			"create table SQLNullDateTest1 (id LONG not null primary key, " +
				"date_ DATE null)");

		_db.runSQL("insert into SQLNullDateTest1 (id) values (1)");

		_db.runSQL("create table SQLNullDateTest2 (id LONG not null)");

		_db.runSQL("insert into SQLNullDateTest2 (id) values (1)");
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		_db.runSQL("drop table SQLNullDateTest1");

		_db.runSQL("drop table SQLNullDateTest2");
	}

	@Test
	public void testNullDate() throws Exception {
		try (Connection connection = DataAccess.getConnection();
			Statement statement = connection.createStatement()) {

			String sql =
				"(select date_ from SQLNullDateTest1) union all (select " +
					"[$NULL_DATE$] from SQLNullDateTest2)";

			ResultSet resultSet = statement.executeQuery(
				SQLTransformer.transform(sql));

			resultSet.close();
		}
	}

	private static DB _db;

}
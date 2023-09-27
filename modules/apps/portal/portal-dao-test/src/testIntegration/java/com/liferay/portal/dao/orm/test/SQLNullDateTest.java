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
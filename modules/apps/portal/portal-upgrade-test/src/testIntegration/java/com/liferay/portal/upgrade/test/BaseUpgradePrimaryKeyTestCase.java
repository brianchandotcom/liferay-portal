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

package com.liferay.portal.upgrade.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Alberto Chaparro
 */
@RunWith(Arquillian.class)
public abstract class BaseUpgradePrimaryKeyTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	public List<String> getPrimaryKeyColumnNames() throws SQLException {
		DatabaseMetaData databaseMetaData = connection.getMetaData();

		SortedMap<Short, String> sortedPrimaryKeyColumnName = new TreeMap<>();

		try (ResultSet rs = databaseMetaData.getPrimaryKeys(
				dbInspector.getCatalog(), dbInspector.getSchema(),
				dbInspector.normalizeName(getTableName(), databaseMetaData))) {

			while (rs.next()) {
				sortedPrimaryKeyColumnName.put(
					rs.getShort("KEY_SEQ"), rs.getString("COLUMN_NAME"));
			}
		}

		return new ArrayList(sortedPrimaryKeyColumnName.values());
	}

	@Before
	public void setUp() throws Exception {
		connection = DataAccess.getConnection();

		dbInspector = new DBInspector(connection);

		db = DBManagerUtil.getDB();

		db.runSQL(getSqlCreateTable());
	}

	@After
	public void tearDown() throws Exception {
		db.runSQL("drop table " + getTableName());

		DataAccess.cleanUp(connection);
	}

	protected abstract String getSqlCreateTable();

	protected abstract String getTableName();

	protected static Connection connection;
	protected static DB db;
	protected static DBInspector dbInspector;

}
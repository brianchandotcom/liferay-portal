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

package com.liferay.portal.upgrade.v7_0_3;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.service.ReleaseLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.junit.After;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Alberto Chaparro
 */
public class UpgradeOracleTest extends UpgradeOracle {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_db = DBManagerUtil.getDB();

		if (_db.getDBType() != DBType.ORACLE) {
			return;
		}
	}

	@Before
	public void setUp() throws Exception {
		Assume.assumeTrue(_db.getDBType() == DBType.ORACLE);

		runSQL(
			"alter table " + _TABLE_NAME + " modify " + _FIELD_NAME +
				" varchar2(300 BYTE)");

		Release portalRelease = ReleaseLocalServiceUtil.fetchRelease("portal");

		portalRelease.setBuildNumber(ReleaseInfo.RELEASE_6_1_20_BUILD_NUMBER);

		ReleaseLocalServiceUtil.updateRelease(portalRelease);
	}

	@After
	public void tearDown() throws Exception {
		runSQL(
			"alter table " + _TABLE_NAME + " modify " + _FIELD_NAME +
				" varchar2(75 CHAR)");

		Release portalRelease = ReleaseLocalServiceUtil.fetchRelease("portal");

		portalRelease.setBuildNumber(ReleaseInfo.getBuildNumber());

		ReleaseLocalServiceUtil.updateRelease(portalRelease);
	}

	@Test
	public void testUpgradeReduceFieldSizes() throws Exception {
		upgrade();

		Assert.assertEquals(75, getDataLenght(_TABLE_NAME, _FIELD_NAME));
	}

	@Test
	public void testUpgradeVarcharAsChar() throws Exception {
		upgrade();

		Assert.assertEquals("C", getCharUsed(_TABLE_NAME, _FIELD_NAME));
	}

	protected String getCharUsed(String tableName, String tableColumn)
		throws Exception {

		try (Connection connection = DataAccess.getUpgradeOptimizedConnection();
			PreparedStatement ps = connection.prepareStatement(
				"select char_used from user_tab_columns where " +
					"table_name = '" + tableName + "' and column_name = '" +
					tableColumn + "'")) {

			ResultSet rs = ps.executeQuery();

			rs.next();

			return rs.getString(1);
		}
	}

	protected int getDataLenght(String tableName, String tableColumn)
		throws Exception {

		try (Connection connection = DataAccess.getUpgradeOptimizedConnection();
			PreparedStatement ps = connection.prepareStatement(
				"select data_length from user_tab_columns where " +
					"table_name = '" + tableName + "' and column_name = '" +
					tableColumn + "'")) {

			ResultSet rs = ps.executeQuery();

			rs.next();

			return rs.getInt(1);
		}
	}

	private static final String _FIELD_NAME = "INDUSTRY";

	private static final String _TABLE_NAME = "ACCOUNT_";

	private static DB _db;

}
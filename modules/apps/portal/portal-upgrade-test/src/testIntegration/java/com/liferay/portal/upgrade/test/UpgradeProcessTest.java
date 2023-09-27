/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.sql.Connection;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Mariano Álvaro Sáiz
 * @author Alberto Chaparro
 */
@RunWith(Arquillian.class)
public class UpgradeProcessTest {

	public static final String TABLE_NAME = "UpgradeProcessTest";

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_connection = DataAccess.getConnection();

		_dbInspector = new DBInspector(_connection);

		_db = DBManagerUtil.getDB();
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		DataAccess.cleanUp(_connection);
	}

	@Before
	public void setUp() throws Exception {
		_db.runSQL(
			StringBundler.concat(
				"create table ", TABLE_NAME, " (id LONG not null primary key, ",
				"typeVarchar VARCHAR(75) not null);"));
	}

	@After
	public void tearDown() throws Exception {
		_db.runSQL("drop table " + TABLE_NAME);
	}

	@Test
	public void testAddTemporaryIndex() throws Exception {
		UpgradeProcess upgradeProcess = new UpgradeProcess() {

			@Override
			protected void doUpgrade() throws Exception {
				try (SafeCloseable safeCloseable = addTemporaryIndex(
						TABLE_NAME, false, "typeVarchar")) {

					Assert.assertTrue(hasIndex(TABLE_NAME, "IX_TEMP"));
				}

				Assert.assertFalse(hasIndex(TABLE_NAME, "IX_TEMP"));
			}

		};

		upgradeProcess.upgrade();
	}

	private static Connection _connection;
	private static DB _db;
	private static DBInspector _dbInspector;

}
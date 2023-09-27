/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.util.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.upgrade.MVCCVersionUpgradeProcess;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alicia García
 * @author Alberto Chaparro
 */
@RunWith(Arquillian.class)
public class MVCCVersionUpgradeProcessTest extends MVCCVersionUpgradeProcess {

	@Before
	public void setUp() throws Exception {
		connection = DataAccess.getConnection();

		_createTable(_HIBERNATE_MAPPING_TABLE_NAME);
		_createTable(_TABLE_NAME);
	}

	@After
	public void tearDown() throws Exception {
		dropTable(_HIBERNATE_MAPPING_TABLE_NAME);
		dropTable(_TABLE_NAME);

		connection.close();
	}

	@Test
	public void testUpgradeMVCCVersion() throws Exception {
		_tableNames = new String[] {_TABLE_NAME};

		doUpgrade();

		DBInspector dbInspector = new DBInspector(connection);

		Assert.assertFalse(
			dbInspector.hasColumn(
				_HIBERNATE_MAPPING_TABLE_NAME, "mvccversion"));

		Assert.assertTrue(dbInspector.hasColumn(_TABLE_NAME, "mvccversion"));
	}

	@Override
	protected String[] getTableNames() {
		return _tableNames;
	}

	private void _createTable(String tableName) throws Exception {
		runSQL(
			StringBundler.concat(
				"create table ", tableName, "(id LONG not null primary key, ",
				"userId LONG)"));
	}

	private static final String _HIBERNATE_MAPPING_TABLE_NAME =
		"UpgradeMVCCVersionHBMTest";

	private static final String _TABLE_NAME = "UpgradeMVCCVersionTest";

	private String[] _tableNames = new String[0];

}
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
		_dropTable(_HIBERNATE_MAPPING_TABLE_NAME);
		_dropTable(_TABLE_NAME);

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

	private void _dropTable(String tableName) throws Exception {
		runSQL("drop table " + tableName);
	}

	private static final String _HIBERNATE_MAPPING_TABLE_NAME =
		"UpgradeMVCCVersionHBMTest";

	private static final String _TABLE_NAME = "UpgradeMVCCVersionTest";

	private String[] _tableNames = new String[0];

}
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
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.test.util.DBAssertionUtil;
import com.liferay.portal.kernel.upgrade.UpgradeMVCCVersion;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alicia García
 * @author Alberto Chaparro
 */
@RunWith(Arquillian.class)
public class UpgradeMVCCVersionTest extends UpgradeMVCCVersion {

	@Before
	public void setUp() throws Exception {
		connection = DataAccess.getConnection();

		runSQL(UpgradeMVCCVersionTestModuleTable.TABLE_SQL_CREATE);
		runSQL(UpgradeMVCCVersionTestPortalTable.TABLE_SQL_CREATE);

		_excludedTableNames = new String[0];
		_moduleTableNames = new String[0];
	}

	@After
	public void tearDown() throws Exception {
		runSQL(UpgradeMVCCVersionTestModuleTable.TABLE_SQL_DROP);
		runSQL(UpgradeMVCCVersionTestPortalTable.TABLE_SQL_DROP);

		connection.close();
	}

	@Test
	public void testUpgradeModuleMVCCVersion() throws Exception {
		_excludedTableNames = new String[] {
			UpgradeMVCCVersionTestPortalTable.TABLE_NAME
		};

		_moduleTableNames = new String[] {
			UpgradeMVCCVersionTestModuleTable.TABLE_NAME
		};

		doUpgrade();

		DBAssertionUtil.assertColumns(
			UpgradeMVCCVersionTestModuleTable.TABLE_NAME, "_id", "_userId",
			"mvccVersion");
	}

	@Test
	public void testUpgradePortalMVCCVersion() throws Exception {
		_excludedTableNames = new String[] {
			UpgradeMVCCVersionTestModuleTable.TABLE_NAME
		};

		doUpgrade();

		DBAssertionUtil.assertColumns(
			UpgradeMVCCVersionTestPortalTable.TABLE_NAME, "_id", "_userId",
			"mvccversion");
	}

	@Override
	protected String[] getExcludedTableNames() {
		return _excludedTableNames;
	}

	@Override
	protected String[] getModuleTableNames() {
		return _moduleTableNames;
	}

	private String[] _excludedTableNames;
	private String[] _moduleTableNames;

	private class UpgradeMVCCVersionTestModuleTable {

		public static final String TABLE_NAME = "UpgradeMVCCVersionTestModule";

		public static final String TABLE_SQL_CREATE =
			"create table UpgradeMVCCVersionTestModule(_id LONG not null " +
				"primary key, _userId LONG)";

		public static final String TABLE_SQL_DROP =
			"drop table UpgradeMVCCVersionTestModule";

	}

	private class UpgradeMVCCVersionTestPortalTable {

		public static final String TABLE_NAME = "UpgradeMVCCVersionTestPortal";

		public static final String TABLE_SQL_CREATE =
			"create table UpgradeMVCCVersionTestPortal(_id LONG not null " +
				"primary key, _userId LONG)";

		public static final String TABLE_SQL_DROP =
			"drop table UpgradeMVCCVersionTestPortal";

	}

}
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

		runSQL(UpgradeMVCCVersionModuleTestTableClass.TABLE_SQL_CREATE);
		runSQL(UpgradeMVCCVersionPortalTestTableClass.TABLE_SQL_CREATE);

		_excludedTableNames = new String[0];
		_moduleTableNames = new String[0];
	}

	@After
	public void tearDown() throws Exception {
		runSQL(UpgradeMVCCVersionModuleTestTableClass.TABLE_SQL_DROP);
		runSQL(UpgradeMVCCVersionPortalTestTableClass.TABLE_SQL_DROP);

		connection.close();
	}

	@Test
	public void testUpgradeModuleMVCCVersion() throws Exception {
		_excludedTableNames = new String[] {
			UpgradeMVCCVersionPortalTestTableClass.TABLE_NAME
		};

		_moduleTableNames = new String[] {
			UpgradeMVCCVersionModuleTestTableClass.TABLE_NAME
		};

		doUpgrade();

		DBAssertionUtil.assertColumns(
			UpgradeMVCCVersionModuleTestTableClass.TABLE_NAME, "_id", "_userId",
			"mvccVersion");
	}

	@Test
	public void testUpgradePortalMVCCVersion() throws Exception {
		_excludedTableNames = new String[] {
			UpgradeMVCCVersionModuleTestTableClass.TABLE_NAME
		};

		doUpgrade();

		DBAssertionUtil.assertColumns(
			UpgradeMVCCVersionPortalTestTableClass.TABLE_NAME, "_id", "_userId",
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

	private class UpgradeMVCCVersionModuleTestTableClass {

		public static final String TABLE_NAME = "UpgradeMVCCVersionModuleTest";

		public static final String TABLE_SQL_CREATE =
			"create table UpgradeMVCCVersionModuleTest(_id LONG not null " +
				"primary key, _userId LONG)";

		public static final String TABLE_SQL_DROP =
			"drop table UpgradeMVCCVersionModuleTest";

	}

	private class UpgradeMVCCVersionPortalTestTableClass {

		public static final String TABLE_NAME = "UpgradeMVCCVersionPortalTest";

		public static final String TABLE_SQL_CREATE =
			"create table UpgradeMVCCVersionPortalTest(_id LONG not null " +
				"primary key, _userId LONG)";

		public static final String TABLE_SQL_DROP =
			"drop table UpgradeMVCCVersionPortalTest";

	}

}
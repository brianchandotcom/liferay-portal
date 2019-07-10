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

import java.sql.DatabaseMetaData;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alicia García
 */
@RunWith(Arquillian.class)
public class UpgradeMVCCVersionTest extends UpgradeMVCCVersion {

	@Before
	public void setUp() throws Exception {
		connection = DataAccess.getConnection();

		runSQL(UpgradeMVCCVersionTestTableClass.TABLE_SQL_CREATE);
	}

	@After
	public void tearDown() throws Exception {
		runSQL(UpgradeMVCCVersionTestTableClass.TABLE_SQL_DROP);

		connection.close();
	}

	@Test
	public void testDoUpgrade() throws Exception {
		doUpgrade();

		DBAssertionUtil.assertColumns(
			UpgradeMVCCVersionTestTableClass.TABLE_NAME, "_id", "_userId",
			"mvccversion");
	}

	@Test
	public void testUpgradeMVCCVersion() throws Exception {
		DatabaseMetaData databaseMetaData = connection.getMetaData();

		upgradeMVCCVersion(
			databaseMetaData, UpgradeMVCCVersionTestTableClass.TABLE_NAME);

		DBAssertionUtil.assertColumns(
			UpgradeMVCCVersionTestTableClass.TABLE_NAME, "_id", "_userId",
			"mvccVersion");
	}

}
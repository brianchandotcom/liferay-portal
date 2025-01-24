/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v7_0_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.upgrade.v7_0_0.UpgradeDDMTemplate;

import java.sql.Connection;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Pedro Leite
 */
@RunWith(Arquillian.class)
public class UpgradeDDMTemplateTest {

	@Test
	public void testUpgrade() throws Exception {
		Connection connection = DataAccess.getConnection();

		DB db = DBManagerUtil.getDB();

		db.alterColumnType(
			connection, "DDMTemplate", "name", "VARCHAR(4000) null");

		UpgradeDDMTemplate upgradeDDMTemplate = new UpgradeDDMTemplate();

		upgradeDDMTemplate.upgrade();

		DBInspector dbInspector = new DBInspector(connection);

		Assert.assertTrue(
			dbInspector.hasColumnType("DDMTemplate", "name", "TEXT null"));
	}

}
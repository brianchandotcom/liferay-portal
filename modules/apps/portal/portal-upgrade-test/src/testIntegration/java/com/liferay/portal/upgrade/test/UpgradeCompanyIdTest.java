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
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.v7_0_0.UpgradeCompanyId;
import com.liferay.portal.util.PropsImpl;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Luis Ortiz
 */
@RunWith(Arquillian.class)
public class UpgradeCompanyIdTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws SQLException {
		PropsUtil.setProps(new PropsImpl());

		_connection = DataAccess.getConnection();

		_dbInspector = new DBInspector(_connection);
	}

	@Before
	public void setUp() throws Exception {
		_company = null;

		_upgradeProcess = new UpgradeCompanyIdCustom();

		_upgradeProcess.runSQL(
			StringBundler.concat(
				"create table ", _TABLE_NAME, " (", _COLUMN_NAME,
				" LONG not null primary key, companyId LONG);"));

		_upgradeProcess.runSQL(
			StringBundler.concat(
				"create table ", _MAPPING_TABLE_NAME, " (", _COLUMN_NAME,
				" LONG not null primary key);"));

		_upgradeProcess.runSQL(
			StringBundler.concat(
				"insert into ", _TABLE_NAME, " (", _COLUMN_NAME,
				", companyId) values (", _COLUMN_VALUE,
				", (select max(companyId) from Company));"));

		_upgradeProcess.runSQL(
			StringBundler.concat(
				"insert into ", _MAPPING_TABLE_NAME, " (", _COLUMN_NAME,
				") values (", _COLUMN_VALUE, ");"));
	}

	@After
	public void tearDown() throws Exception {
		_upgradeProcess.runSQL("drop table " + _TABLE_NAME);
		_upgradeProcess.runSQL("drop table " + _MAPPING_TABLE_NAME);

		if (_company != null) {
			_companyLocalService.deleteCompany(_company);
		}
	}

	@Test
	public void testUpgradeNullColumn() throws Exception {
		_upgradeProcess.upgrade();

		Assert.assertTrue(
			_dbInspector.hasColumnType(
				_MAPPING_TABLE_NAME, "companyId", "LONG NOT NULL"));
	}

	@Test
	public void testUpgradeWithNullValues() throws Exception {
		_company = CompanyTestUtil.addCompany();

		_upgradeProcess.runSQL(
			StringBundler.concat(
				"insert into ", _MAPPING_TABLE_NAME, " (", _COLUMN_NAME,
				") values (", _COLUMN_VALUE - 1, ");"));

		try {
			_upgradeProcess.upgrade();

			Assert.fail("Expected exception was not thrown");
		}
		catch (Exception exception) {
		}
	}

	private static final String _COLUMN_NAME = "id";

	private static final int _COLUMN_VALUE = 99999;

	private static final String _MAPPING_TABLE_NAME =
		"UpgradeCompanyIdMappingTest";

	private static final String _TABLE_NAME = "UpgradeCompanyIdTest";

	private static Company _company;

	@Inject
	private static CompanyLocalService _companyLocalService;

	private static Connection _connection;
	private static DBInspector _dbInspector;
	private static UpgradeCompanyIdCustom _upgradeProcess;

	private class UpgradeCompanyIdCustom extends UpgradeCompanyId {

		@Override
		protected TableUpdater[] getTableUpdaters() {
			return new TableUpdater[] {
				new CompanyIdNotNullTableUpdater(
					_MAPPING_TABLE_NAME, _TABLE_NAME, _COLUMN_NAME)
			};
		}

	}

}
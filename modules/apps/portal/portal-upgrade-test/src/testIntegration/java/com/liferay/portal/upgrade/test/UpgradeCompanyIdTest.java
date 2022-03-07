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

	public static final String MAIN_TABLE_NAME = "MainTableTest";

	public static final String NXM_TABLE_NAME = "MainTableTest_Related";

	public static final String RELATED_COLUMN = "mainId";

	public static final int RELATED_KEY = 99999;

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
		_upgrader = new UpgradeCompanyIdCustom();

		_upgrader.runSQL(
			StringBundler.concat(
				"create table ", MAIN_TABLE_NAME, " (", RELATED_COLUMN,
				" LONG not null primary key, companyId LONG);"));

		_upgrader.runSQL(
			StringBundler.concat(
				"create table ", NXM_TABLE_NAME, " (", RELATED_COLUMN,
				" LONG not null primary key);"));

		_upgrader.runSQL(
			StringBundler.concat(
				"insert into ", MAIN_TABLE_NAME, " (", RELATED_COLUMN,
				", companyId) values (", RELATED_KEY,
				", (select max(companyId) from Company));"));

		_upgrader.runSQL(
			StringBundler.concat(
				"insert into ", NXM_TABLE_NAME, " (", RELATED_COLUMN, ")",
				" values (", RELATED_KEY, ");"));
	}

	@After
	public void tearDown() throws Exception {
		_upgrader.runSQL("drop table " + MAIN_TABLE_NAME);
		_upgrader.runSQL("drop table " + NXM_TABLE_NAME);

		if (_company != null) {
			_companyLocalService.deleteCompany(_company);
			_company = null;
		}
	}

	@Test
	public void testUpgradeFrom6_2() throws Exception {
		_upgrader.upgrade();

		Assert.assertTrue(
			_dbInspector.hasColumnType(
				NXM_TABLE_NAME, "companyId", "LONG NOT NULL"));
	}

	@Test
	public void testUpgradeWithNullValues() throws Exception {
		_company = CompanyTestUtil.addCompany();

		_upgrader.runSQL(
			StringBundler.concat(
				"insert into ", NXM_TABLE_NAME, " (", RELATED_COLUMN, ")",
				" values (", RELATED_KEY - 1, ");"));

		try {
			_upgrader.upgrade();
			Assert.fail("Expected exception was not thrown");
		}
		catch (Exception exception) {
			Assert.assertNotNull(exception);
		}
	}

	private static Company _company;

	@Inject
	private static CompanyLocalService _companyLocalService;

	private static Connection _connection;
	private static DBInspector _dbInspector;
	private static UpgradeCompanyIdCustom _upgrader;

	private class UpgradeCompanyIdCustom extends UpgradeCompanyId {

		@Override
		protected TableUpdater[] getTableUpdaters() {
			return new TableUpdater[] {
				new CompanyIdNotNullTableUpdater(
					NXM_TABLE_NAME, MAIN_TABLE_NAME, RELATED_COLUMN)
			};
		}

	}

}
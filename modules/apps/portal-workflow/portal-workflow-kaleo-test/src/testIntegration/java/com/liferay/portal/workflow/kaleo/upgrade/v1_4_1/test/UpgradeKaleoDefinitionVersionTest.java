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

package com.liferay.portal.workflow.kaleo.upgrade.v1_4_1.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinition;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;
import com.liferay.portal.workflow.kaleo.service.KaleoDefinitionLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoDefinitionVersionLocalService;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;

import java.util.function.BiConsumer;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Inácio Nery
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class UpgradeKaleoDefinitionVersionTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_name = StringUtil.randomString();
		_timestamp = new Timestamp(System.currentTimeMillis());

		_db = DBManagerUtil.getDB();

		_setUpUpgradeKaleoDefinitionVersion();

		_setUpOldColumnsAndIndexes();
	}

	@After
	public void tearDown() throws Exception {
		_deleteKaleoDefinitionVersion(_name);
	}

	@Test
	public void testCreateKaleoDefinitionVersion() throws Exception {
		Company company1 = CompanyTestUtil.addCompany();
		Company company2 = CompanyTestUtil.addCompany();

		_addKaleoDefinition(
			company1.getCompanyId(), company1.getGroupId(), _name, 1);
		_addKaleoDefinition(
			company1.getCompanyId(), company1.getGroupId(), _name, 2);
		_addKaleoDefinition(
			company2.getCompanyId(), company2.getGroupId(), _name, 3);

		_upgradeKaleoDefinitionVersion.upgrade();

		_getKaleoDefinition(company1.getCompanyId(), _name);
		_getKaleoDefinitionVersion(company1.getCompanyId(), _name, 1);
		_getKaleoDefinitionVersion(company1.getCompanyId(), _name, 2);
		_getKaleoDefinition(company2.getCompanyId(), _name);
		_getKaleoDefinitionVersion(company2.getCompanyId(), _name, 3);
	}

	private void _addColumn(String table, String column) throws Exception {
		_addColumn(table, column, null);
	}

	private void _addColumn(
			String table, String column, BiConsumer<String, String> postProcess)
		throws Exception {

		if (!_dbInspector.hasColumn(table, column)) {
			_db.runSQLTemplateString(
				StringBundler.concat(
					"alter table ", table, " add ", column, " LONG;"),
				true);

			if (postProcess != null) {
				postProcess.accept(table, column);
			}
		}
	}

	private void _addKaleoDefinition(
			long companyId, long groupId, String name, int version)
		throws Exception {

		StringBundler sb = new StringBundler(5);

		sb.append("insert into KaleoDefinition (kaleoDefinitionId, groupId, ");
		sb.append("companyId, userId, userName, createDate, modifiedDate, ");
		sb.append("name, title, description, content, version, active_, ");
		sb.append("startKaleoNodeId) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ");
		sb.append("?, ?, ?, ?)");

		String sql = sb.toString();

		try (Connection con = DataAccess.getConnection();
			PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setLong(1, RandomTestUtil.randomLong());
			ps.setLong(2, groupId);
			ps.setLong(3, companyId);
			ps.setLong(4, TestPropsValues.getUserId());

			User user = TestPropsValues.getUser();

			ps.setString(5, user.getFullName());

			ps.setTimestamp(6, _timestamp);
			ps.setTimestamp(7, _timestamp);
			ps.setString(8, name);
			ps.setString(9, StringUtil.randomString());
			ps.setString(10, StringUtil.randomString());
			ps.setString(11, StringUtil.randomString());
			ps.setInt(12, version);
			ps.setBoolean(13, true);
			ps.setLong(14, RandomTestUtil.randomLong());

			ps.executeUpdate();
		}
	}

	private void _deleteKaleoDefinitionVersion(String name) throws Exception {
		_db.runSQL(
			"delete from KaleoDefinitionVersion where name = '" + name + "'");

		_db.runSQL("delete from KaleoDefinition where name = '" + name + "'");
	}

	private KaleoDefinition _getKaleoDefinition(long companyId, String name)
		throws Exception {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(companyId);

		return _kaleoDefinitionLocalService.getKaleoDefinition(
			name, serviceContext);
	}

	private KaleoDefinitionVersion _getKaleoDefinitionVersion(
			long companyId, String name, int version)
		throws Exception {

		return _kaleoDefinitionVersionLocalService.getKaleoDefinitionVersion(
			companyId, name, _getVersion(version));
	}

	private String _getVersion(int version) {
		return version + StringPool.PERIOD + 0;
	}

	private void _setUpOldColumnsAndIndexes() throws Exception {
		try (Connection con = DataAccess.getConnection()) {
			_dbInspector = new DBInspector(con);

			_addColumn("KaleoDefinition", "startKaleoNodeId");

			_dbInspector = null;
		}
	}

	private void _setUpUpgradeKaleoDefinitionVersion() {
		Registry registry = RegistryUtil.getRegistry();

		UpgradeStepRegistrator upgradeStepRegistror = registry.getService(
			registry.getServiceReference(
				"com.liferay.portal.workflow.kaleo.internal.upgrade." +
					"KaleoServiceUpgrade"));

		upgradeStepRegistror.register(
			new UpgradeStepRegistrator.Registry() {

				@Override
				public void register(
					String fromSchemaVersionString,
					String toSchemaVersionString, UpgradeStep... upgradeSteps) {

					for (UpgradeStep upgradeStep : upgradeSteps) {
						Class<?> clazz = upgradeStep.getClass();

						String className = clazz.getName();

						if (className.contains(
								"UpgradeKaleoDefinitionVersion")) {

							_upgradeKaleoDefinitionVersion =
								(UpgradeProcess)upgradeStep;
						}
					}
				}

			});
	}

	private DB _db;
	private DBInspector _dbInspector;

	@Inject
	private KaleoDefinitionLocalService _kaleoDefinitionLocalService;

	@Inject
	private KaleoDefinitionVersionLocalService
		_kaleoDefinitionVersionLocalService;

	private String _name;
	private Timestamp _timestamp;
	private UpgradeProcess _upgradeKaleoDefinitionVersion;

}
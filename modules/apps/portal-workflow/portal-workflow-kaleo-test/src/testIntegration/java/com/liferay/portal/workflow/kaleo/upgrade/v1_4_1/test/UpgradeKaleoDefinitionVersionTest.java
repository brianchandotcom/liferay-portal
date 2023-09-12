/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinition;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;
import com.liferay.portal.workflow.kaleo.service.KaleoDefinitionLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoDefinitionVersionLocalService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;

import java.util.function.BiConsumer;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Ignore;
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
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

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

	@Ignore
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

		_kaleoDefinitionVersionUpgradeProcess.upgrade();

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

		try (Connection connection = DataAccess.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"insert into KaleoDefinition (kaleoDefinitionId, groupId, ",
					"companyId, userId, userName, createDate, modifiedDate, ",
					"name, title, description, content, version, active_, ",
					"startKaleoNodeId) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ",
					"?, ?, ?, ?)"))) {

			preparedStatement.setLong(1, RandomTestUtil.randomLong());
			preparedStatement.setLong(2, groupId);
			preparedStatement.setLong(3, companyId);
			preparedStatement.setLong(4, TestPropsValues.getUserId());

			User user = TestPropsValues.getUser();

			preparedStatement.setString(5, user.getFullName());

			preparedStatement.setTimestamp(6, _timestamp);
			preparedStatement.setTimestamp(7, _timestamp);
			preparedStatement.setString(8, name);
			preparedStatement.setString(9, StringUtil.randomString());
			preparedStatement.setString(10, StringUtil.randomString());
			preparedStatement.setString(11, StringUtil.randomString());
			preparedStatement.setInt(12, version);
			preparedStatement.setBoolean(13, true);
			preparedStatement.setLong(14, RandomTestUtil.randomLong());

			preparedStatement.executeUpdate();
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
		try (Connection connection = DataAccess.getConnection()) {
			_dbInspector = new DBInspector(connection);

			_addColumn("KaleoDefinition", "startKaleoNodeId");

			_dbInspector = null;
		}
	}

	private void _setUpUpgradeKaleoDefinitionVersion() {
		_upgradeStepRegistrator.register(
			new UpgradeStepRegistrator.Registry() {

				@Override
				public void register(
					String fromSchemaVersionString,
					String toSchemaVersionString, UpgradeStep... upgradeSteps) {

					for (UpgradeStep upgradeStep : upgradeSteps) {
						Class<?> clazz = upgradeStep.getClass();

						String className = clazz.getName();

						if (className.contains(
								"KaleoDefinitionVersionUpgradeProcess")) {

							_kaleoDefinitionVersionUpgradeProcess =
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

	private UpgradeProcess _kaleoDefinitionVersionUpgradeProcess;
	private String _name;
	private Timestamp _timestamp;

	@Inject(
		filter = "component.name=com.liferay.portal.workflow.kaleo.internal.upgrade.registry.KaleoServiceUpgradeStepRegistrator"
	)
	private UpgradeStepRegistrator _upgradeStepRegistrator;

}
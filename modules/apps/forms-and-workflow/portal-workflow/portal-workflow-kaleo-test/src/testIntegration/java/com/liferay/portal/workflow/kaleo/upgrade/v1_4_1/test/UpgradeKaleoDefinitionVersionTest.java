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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;
import com.liferay.portal.workflow.kaleo.service.KaleoDefinitionVersionLocalServiceUtil;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;

import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
@RunWith(Arquillian.class)
public class UpgradeKaleoDefinitionVersionTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
		_name = StringUtil.randomString();
		_timestamp = new Timestamp(System.currentTimeMillis());

		_db = DBManagerUtil.getDB();

		setUpUpgradeKaleoDefinitionVersion();

		setUpOldColumnsAndIndexes();
	}

	@After
	public void tearDown() throws Exception {
		deleteKaleoDefinitionVersion(_name);
	}

	@Test
	public void testCreateKaleoDefinitionVersion() throws Exception {
		addKaleoDefinition(_name, 1);

		_upgradeKaleoDefinitionVersion.upgrade();

		getKaleoDefinitionVersion(_name, 1);
	}

	protected void addColumn(String table, String column) throws Exception {
		addColumn(table, column, null);
	}

	protected void addColumn(
			String table, String column, BiConsumer<String, String> postProcess)
		throws Exception {

		if (!_dbInspector.hasColumn(table, column)) {
			_db.runSQLTemplateString(
				"alter table " + table + " add " + column + " LONG;", false,
				true);

			if (postProcess != null) {
				postProcess.accept(table, column);
			}
		}
	}

	protected void addColumnAndIndex(String table, String column, String index)
		throws Exception {

		addColumn(
			table, column,
			(table2, column2) -> addIndex(table2, index, column2));
	}

	protected void addIndex(String table, String index, String... columns) {
		try {
			_db.runSQL(
				"create index " + index + " on " + table + " (" +
					StringUtil.merge(columns) + ");");
		}
		catch (IOException | SQLException e) {
			throw new AssertionError(e);
		}
	}

	protected void addKaleoDefinition(String name, int version)
		throws Exception {

		StringBundler sb = new StringBundler(5);

		sb.append("insert into KaleoDefinition (kaleoDefinitionId, groupId, ");
		sb.append("companyId, userId, userName, createDate, modifiedDate, ");
		sb.append("name, title, description, content, version, active_, ");
		sb.append("startKaleoNodeId) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ");
		sb.append("?, ?, ?, ?)");

		String sql = sb.toString();

		try (Connection con = DataAccess.getUpgradeOptimizedConnection();
			PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setLong(1, RandomTestUtil.randomLong());
			ps.setLong(2, _group.getGroupId());
			ps.setLong(3, _group.getCompanyId());
			ps.setLong(4, TestPropsValues.getUserId());
			ps.setString(5, TestPropsValues.getUser().getFullName());
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

	protected void deleteKaleoDefinitionVersion(String name) throws Exception {
		_db.runSQL(
			"delete from KaleoDefinitionVersion where name = '" + name + "'");

		_db.runSQL("delete from KaleoDefinition where name = '" + name + "'");
	}

	protected KaleoDefinitionVersion getKaleoDefinitionVersion(
			String name, int version)
		throws PortalException {

		return KaleoDefinitionVersionLocalServiceUtil.getKaleoDefinitionVersion(
			_group.getCompanyId(), name, getVersion(version));
	}

	protected String getVersion(int version) {
		return version + StringPool.PERIOD + 0;
	}

	protected void setUpOldColumnsAndIndexes() throws Exception {
		try (Connection con = DataAccess.getUpgradeOptimizedConnection()) {
			_dbInspector = new DBInspector(con);

			addColumn("KaleoDefinition", "startKaleoNodeId");

			addColumnAndIndex("KaleoAction", "kaleoDefinitionId", "IX_F95A622");
			addColumnAndIndex(
				"KaleoCondition", "kaleoDefinitionId", "IX_DC978A5D");
			addColumnAndIndex(
				"KaleoInstance", "kaleoDefinitionId", "IX_ACF16238");
			addColumnAndIndex(
				"KaleoInstanceToken", "kaleoDefinitionId", "IX_7BDB04B4");
			addColumnAndIndex("KaleoLog", "kaleoDefinitionId", "IX_6C64B7D4");

			addColumn(
				"KaleoNode", "kaleoDefinitionId",
				(table, column) -> {
					addIndex("KaleoNode", "IX_32E94DD6", "kaleoDefinitionId");
					addIndex(
						"KaleoNode", "IX_F28C443E", "companyId",
						"kaleoDefinitionId");
				});

			addColumnAndIndex(
				"KaleoNotification", "kaleoDefinitionId", "IX_4B968E8D");
			addColumnAndIndex(
				"KaleoNotificationRecipient", "kaleoDefinitionId",
				"IX_AA6697EA");
			addColumnAndIndex("KaleoTask", "kaleoDefinitionId", "IX_3FFA633");
			addColumnAndIndex(
				"KaleoTaskAssignment", "kaleoDefinitionId", "IX_575C03A6");
			addColumnAndIndex(
				"KaleoTaskAssignmentInstance", "kaleoDefinitionId",
				"IX_C851011");
			addColumnAndIndex(
				"KaleoTaskForm", "kaleoDefinitionId", "IX_60D1964F");
			addColumnAndIndex(
				"KaleoTaskFormInstance", "kaleoDefinitionId", "IX_B975E9BA");
			addColumnAndIndex(
				"KaleoTaskInstanceToken", "kaleoDefinitionId", "IX_608E9519");
			addColumn("KaleoTimer", "kaleoDefinitionId");
			addColumn("KaleoTimerInstanceToken", "kaleoDefinitionId");
			addColumnAndIndex(
				"KaleoTransition", "kaleoDefinitionId", "IX_479F3063");

			_dbInspector = null;
		}
	}

	protected void setUpUpgradeKaleoDefinitionVersion() {
		Registry registry = RegistryUtil.getRegistry();

		UpgradeStepRegistrator upgradeStepRegistror = registry.getService(
			"com.liferay.portal.workflow.kaleo.internal.upgrade." +
				"KaleoServiceUpgrade");

		upgradeStepRegistror.register(
			new UpgradeStepRegistrator.Registry() {

				@Override
				public void register(
					String bundleSymbolicName, String fromSchemaVersionString,
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

	@DeleteAfterTestRun
	private Group _group;

	private String _name;
	private Timestamp _timestamp;
	private UpgradeProcess _upgradeKaleoDefinitionVersion;

}
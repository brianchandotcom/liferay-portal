/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.verify.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.field.builder.TextObjectFieldBuilder;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.db.DBResourceUtil;
import com.liferay.portal.db.partition.util.DBPartitionUtil;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.instance.PortalInstancePool;
import com.liferay.portal.kernel.model.ServiceComponent;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceComponentLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.PropsValues;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.version.Version;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.PortalUpgradeProcess;
import com.liferay.portal.verify.PreupgradeVerifyDatabaseState;
import com.liferay.portal.verify.VerifyProcess;
import com.liferay.portal.verify.test.util.BaseVerifyProcessTestCase;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.sql.Connection;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jorge Avalos
 */
@RunWith(Arquillian.class)
public class PreupgradeVerifyDatabaseStateTest
	extends BaseVerifyProcessTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		try (Connection connection = DataAccess.getConnection()) {
			_currentSchemaVersion =
				PortalUpgradeProcess.getCurrentSchemaVersion(connection);

			PortalUpgradeProcess.updateSchemaVersion(
				connection, _TEST_SCHEMA_VERSION);
		}

		if (PropsValues.DATABASE_PARTITION_ENABLED) {
			_safeCloseable = CompanyThreadLocal.setCompanyIdWithSafeCloseable(
				PortalInstancePool.getDefaultCompanyId());
		}
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		try (Connection connection = DataAccess.getConnection()) {
			PortalUpgradeProcess.updateSchemaVersion(
				connection, _currentSchemaVersion);
		}
		finally {
			if (_safeCloseable != null) {
				_safeCloseable.close();
			}
		}
	}

	@Test
	public void testVerifyPreupgradeFalsePositive74MissingTable()
		throws Exception {

		ServiceComponent serviceComponent =
			_serviceComponentLocalService.createServiceComponent(
				RandomTestUtil.nextLong());

		String tableName = _getNormalizedName("Account_");

		serviceComponent.setMvccVersion(0);
		serviceComponent.setBuildNamespace("com.liferay.test.service.impl");
		serviceComponent.setData(
			StringBundler.concat("<![CDATA[create table ", tableName, " ("));

		_serviceComponentLocalService.addServiceComponent(serviceComponent);

		try {
			testVerify();
		}
		finally {
			_serviceComponentLocalService.deleteServiceComponent(
				serviceComponent);
		}
	}

	@Test
	public void testVerifyPreupgradeIsCaseInsensitive() throws Exception {
		ServiceComponent serviceComponent =
			_serviceComponentLocalService.createServiceComponent(
				RandomTestUtil.nextLong());

		String tableName = "TestTable";

		serviceComponent.setMvccVersion(0);
		serviceComponent.setBuildNamespace("com.liferay.test.service.impl");
		serviceComponent.setData(
			StringBundler.concat(
				"<![CDATA[create table ", StringUtil.toUpperCase(tableName),
				" ("));

		_serviceComponentLocalService.addServiceComponent(serviceComponent);

		DB db = DBManagerUtil.getDB();

		String lowerCaseTestTable = StringUtil.toLowerCase("testtable");

		try {
			DBPartitionUtil.forEachCompanyId(
				companyId -> db.runSQL(
					"create table " + lowerCaseTestTable + "(id LONG)"));

			testVerify();
		}
		finally {
			_serviceComponentLocalService.deleteServiceComponent(
				serviceComponent);

			DBPartitionUtil.forEachCompanyId(
				companyId -> db.runSQL(
					"DROP_TABLE_IF_EXISTS(" + lowerCaseTestTable + ")"));
		}
	}

	@Test
	public void testVerifyPreupgradeMissingColumnName() throws Exception {
		_alterColumnName("UserTracker", "companyId", "companyId_backup LONG");

		try {
			testVerify();

			Assert.fail();
		}
		catch (Exception exception) {
			Assert.assertEquals(
				StringBundler.concat(
					"Column ", _getNormalizedName("companyId"),
					" is missing for ", _getNormalizedName("UserTracker"),
					_getPartitionSuffix(), StringPool.NEW_LINE),
				exception.getMessage());
		}
		finally {
			_alterColumnName(
				"UserTracker", "companyId_backup", "companyId LONG");
		}
	}

	@Test
	public void testVerifyPreupgradeMissingNonserviceBuilderTable()
		throws Exception {

		User user = UserTestUtil.getAdminUser(
			CompanyThreadLocal.getCompanyId());

		ObjectField objectField = new TextObjectFieldBuilder(
		).userId(
			user.getUserId()
		).labelMap(
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString())
		).localized(
			true
		).name(
			"localizedField"
		).build();

		ObjectDefinition objectDefinition =
			ObjectDefinitionTestUtil.publishObjectDefinition(
				Collections.singletonList(objectField),
				ObjectDefinitionConstants.SCOPE_COMPANY, user.getUserId());

		DB db = DBManagerUtil.getDB();

		db.runSQL("drop table " + objectDefinition.getDBTableName());

		try {
			testVerify();

			Assert.fail();
		}
		catch (Exception exception) {
			Assert.assertEquals(
				"Missing tables detected: [" +
					objectDefinition.getDBTableName() + "]",
				exception.getMessage());
		}
		finally {
			_objectDefinitionLocalService.deleteObjectDefinition(
				objectDefinition);
		}
	}

	@Test
	public void testVerifyPreupgradeMissingServiceComponentTable()
		throws Exception {

		ServiceComponent serviceComponent =
			_serviceComponentLocalService.createServiceComponent(
				RandomTestUtil.nextLong());

		String tableName = _getNormalizedName("TestTable");

		serviceComponent.setMvccVersion(0);
		serviceComponent.setBuildNamespace("com.liferay.test.service.impl");
		serviceComponent.setData(
			StringBundler.concat("<![CDATA[create table ", tableName, " ("));

		_serviceComponentLocalService.addServiceComponent(serviceComponent);

		try {
			testVerify();

			Assert.fail();
		}
		catch (Exception exception) {
			Assert.assertEquals(
				"Missing tables detected: [" + tableName + "]",
				exception.getMessage());
		}
		finally {
			_serviceComponentLocalService.deleteServiceComponent(
				serviceComponent);
		}
	}

	@Test
	public void testVerifyPreupgradeMissingView() throws Exception {
		Assume.assumeTrue(PropsValues.DATABASE_PARTITION_ENABLED);

		_renameView("Release_", "Release_backup");

		try {
			testVerify();

			Assert.fail();
		}
		catch (Exception exception) {
			String viewName = _getNormalizedName("Release_");

			Assert.assertEquals(
				StringBundler.concat(
					"Missing views detected: [", viewName, "] in company ",
					TestPropsValues.getCompanyId()),
				exception.getMessage());
		}
		finally {
			_renameView("Release_backup", "Release_");
		}
	}

	@Test
	public void testVerifyPreupgradePartiallyUpgradedTable() throws Exception {
		ServiceComponent serviceComponent = _getServiceComponent();

		String originalData = serviceComponent.getData();

		try {
			serviceComponent.setData(RandomTestUtil.randomString());

			serviceComponent =
				_serviceComponentLocalService.updateServiceComponent(
					serviceComponent);

			testVerify();

			Assert.fail();
		}
		catch (Exception exception) {
			try (Connection connection = DataAccess.getConnection()) {
				DBInspector dbInspector = new DBInspector(connection);

				Set<String> tableNames = DBResourceUtil.parseCreateTableSQL(
					dbInspector, originalData);

				Assert.assertEquals(
					"Stale tables from a previous upgrade detected: " +
						new TreeSet<>(tableNames),
					exception.getMessage());
			}
		}
		finally {
			serviceComponent.setData(originalData);

			_serviceComponentLocalService.updateServiceComponent(
				serviceComponent);
		}
	}

	@Test
	public void testVerifyPreupgradeWrongColumnType() throws Exception {
		_alterColumnType("Address", "city", "VARCHAR(100)");

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				PreupgradeVerifyDatabaseState.class.getName(),
				LoggerTestUtil.WARN)) {

			testVerify();

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Assert.assertEquals(
				logEntry.getMessage(),
				StringBundler.concat(
					"Column ", _getNormalizedName("city"),
					" is not defined as VARCHAR(75) null for ",
					_getNormalizedName("Address"), _getPartitionSuffix()));
		}
		finally {
			_alterColumnType("Address", "city", "VARCHAR(75)");
		}
	}

	@Override
	protected VerifyProcess getVerifyProcess() {
		return new PreupgradeVerifyDatabaseState();
	}

	private void _alterColumnName(
			String tableName, String oldColumnName, String newColumnDefinition)
		throws Exception {

		DB db = DBManagerUtil.getDB();

		try (Connection connection = DataAccess.getConnection()) {
			db.alterColumnName(
				connection, tableName, oldColumnName, newColumnDefinition);
		}
	}

	private void _alterColumnType(
			String tableName, String columnName, String columnType)
		throws Exception {

		DB db = DBManagerUtil.getDB();

		try (Connection connection = DataAccess.getConnection()) {
			db.alterColumnType(connection, tableName, columnName, columnType);
		}
	}

	private String _getNormalizedName(String tableName) throws Exception {
		try (Connection connection = DataAccess.getConnection()) {
			DBInspector dbInspector = new DBInspector(connection);

			return dbInspector.normalizeName(tableName);
		}
	}

	private String _getPartitionSuffix() {
		String partitionSuffix = StringPool.BLANK;

		if (PropsValues.DATABASE_PARTITION_ENABLED) {
			String partitionName = DBPartitionUtil.getPartitionName(
				CompanyThreadLocal.getNonsystemCompanyId());

			partitionSuffix = " in " + partitionName;
		}

		return partitionSuffix;
	}

	private ServiceComponent _getServiceComponent() {
		for (ServiceComponent serviceComponent :
				_serviceComponentLocalService.getLatestServiceComponents()) {

			String buildNamespace = serviceComponent.getBuildNamespace();

			if (buildNamespace.startsWith("com.liferay")) {
				return serviceComponent;
			}
		}

		return null;
	}

	private void _renameView(String fromViewName, String toViewName)
		throws Exception {

		try (SafeCloseable safeCloseable =
				CompanyThreadLocal.setCompanyIdWithSafeCloseable(
					TestPropsValues.getCompanyId())) {

			DB db = DBManagerUtil.getDB();

			if (db.getDBType() == DBType.MYSQL) {
				db.runSQL(
					StringBundler.concat(
						"rename table ", fromViewName, " to ", toViewName));
			}
			else {
				db.runSQL(
					StringBundler.concat(
						"alter view ", fromViewName, " rename to ",
						toViewName));
			}
		}
	}

	private static final Version _TEST_SCHEMA_VERSION = new Version(0, 0, 0);

	private static Version _currentSchemaVersion;
	private static SafeCloseable _safeCloseable;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ServiceComponentLocalService _serviceComponentLocalService;

}
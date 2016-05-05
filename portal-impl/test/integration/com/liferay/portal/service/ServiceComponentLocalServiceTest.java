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

package com.liferay.portal.service;

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBProcessContext;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.model.ServiceComponent;
import com.liferay.portal.kernel.service.ServiceComponentLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceRegistration;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Alberto Chaparro
 */
public class ServiceComponentLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() {
		_serviceComponentsCount =
			ServiceComponentLocalServiceUtil.getServiceComponentsCount();

		_serviceComponent1 = addServiceComponent(_SERVICE_COMPONENT_1, 1);
		_serviceComponent2 = addServiceComponent(_SERVICE_COMPONENT_2, 1);
	}

	@Test
	public void testGetLatestServiceComponentsWithMultipleVersions()
		throws Exception {

		ServiceComponent serviceComponent = addServiceComponent(
			_SERVICE_COMPONENT_1, 2);

		List<ServiceComponent> serviceComponents =
			ServiceComponentLocalServiceUtil.getLatestServiceComponents();

		Assert.assertEquals(
			2, serviceComponents.size() - _serviceComponentsCount);

		ServiceComponent latestServiceComponent = getServiceComponent(
			serviceComponents, _SERVICE_COMPONENT_1);

		Assert.assertEquals(2, latestServiceComponent.getBuildNumber());

		latestServiceComponent = getServiceComponent(
			serviceComponents, _SERVICE_COMPONENT_2);

		Assert.assertEquals(1, latestServiceComponent.getBuildNumber());

		ServiceComponentLocalServiceUtil.deleteServiceComponent(
			serviceComponent);
	}

	@Test
	public void testGetLatestServiceComponentsWithSingleVersion()
		throws Exception {

		List<ServiceComponent> serviceComponents =
			ServiceComponentLocalServiceUtil.getLatestServiceComponents();

		Assert.assertEquals(
			2, serviceComponents.size() - _serviceComponentsCount);

		ServiceComponent latestServiceComponent = getServiceComponent(
			serviceComponents, _SERVICE_COMPONENT_1);

		Assert.assertEquals(1, latestServiceComponent.getBuildNumber());

		latestServiceComponent = getServiceComponent(
			serviceComponents, _SERVICE_COMPONENT_2);

		Assert.assertEquals(1, latestServiceComponent.getBuildNumber());
	}

	@Test
	public void testVerifyDBWithUpgradeInitialDatabaseCreationTrueAndUpgradeFromSchemaVersion000()
		throws Exception {

		Registry registry = RegistryUtil.getRegistry();

		Map<String, Object> properties = new HashMap<>();

		properties.put("upgrade.initial.database.creation", true);
		properties.put("upgrade.from.schema.version", "0.0.0");

		final DB db = DBManagerUtil.getDB();

		ServiceRegistration<UpgradeStep> upgradeStepServiceRegistration =
			registry.registerService(
				UpgradeStep.class, new SimpleUpgradeStep(db), properties);

		String tableName = _TEST_TABLE;

		try {
			ServiceComponentLocalServiceUtil.verifyDB();

			try (Connection conn = DataAccess.getConnection()) {
				DatabaseMetaData metadata = conn.getMetaData();

				tableName = normalizeTableName(metadata, _TEST_TABLE);

				try (ResultSet rs = metadata.getTables(
						null, null, tableName, new String[] {"TABLE"})) {

					Assert.assertTrue(rs.next());
				}
			}
		}
		finally {
			db.runSQL("drop table " + tableName);

			upgradeStepServiceRegistration.unregister();
		}
	}

	@Test
	public void testVerifyWithoutUpgradeFromSchemaVersion000()
		throws Exception {

		Registry registry = RegistryUtil.getRegistry();

		Map<String, Object> properties = new HashMap<>();

		properties.put("upgrade.initial.database.creation", true);
		properties.put("upgrade.from.schema.version", "0.0.1");

		final DB db = DBManagerUtil.getDB();

		ServiceRegistration<UpgradeStep> upgradeStepServiceRegistration =
			registry.registerService(
				UpgradeStep.class, new SimpleUpgradeStep(db), properties);

		try {
			ServiceComponentLocalServiceUtil.verifyDB();

			try (Connection connection = DataAccess.getConnection()) {
				DatabaseMetaData metadata = connection.getMetaData();

				String tableName = normalizeTableName(metadata, _TEST_TABLE);

				try (ResultSet verifyTable = metadata.getTables(
						null, null, tableName, new String[] {"TABLE"})) {

					Assert.assertFalse(verifyTable.next());
				}
			}
		}
		finally {
			upgradeStepServiceRegistration.unregister();
		}
	}

	@Test
	public void testVerifyWithUpgradeInitialDatabaseCreationFalse()
		throws Exception {

		Registry registry = RegistryUtil.getRegistry();

		Map<String, Object> properties = new HashMap<>();

		properties.put("upgrade.initial.database.creation", false);
		properties.put("upgrade.from.schema.version", "0.0.0");

		final DB db = DBManagerUtil.getDB();

		ServiceRegistration<UpgradeStep> upgradeStepServiceRegistration =
			registry.registerService(
				UpgradeStep.class, new SimpleUpgradeStep(db), properties);

		try {
			ServiceComponentLocalServiceUtil.verifyDB();

			try (Connection connection = DataAccess.getConnection()) {
				DatabaseMetaData metadata = connection.getMetaData();

				String tableName = normalizeTableName(metadata, _TEST_TABLE);

				try (ResultSet verifyTable = metadata.getTables(
						null, null, tableName, new String[] {"TABLE"})) {

					Assert.assertFalse(verifyTable.next());
				}
			}
		}
		finally {
			upgradeStepServiceRegistration.unregister();
		}
	}

	public class SimpleUpgradeStep implements UpgradeStep {

		public SimpleUpgradeStep(DB db) {
			_db = db;
		}

		@Override
		public String toString() {
			return "Testing Verify DB";
		}

		@Override
		public void upgrade(DBProcessContext dbProcessContext) {
			try {
				_db.runSQL(
					"create table " + _TEST_TABLE + " (name VARCHAR(20))");
			}
			catch (Exception e) {
				new UpgradeException(e);
			}
		}

		private final DB _db;

	}

	protected ServiceComponent addServiceComponent(
		String buildNameSpace, long buildNumber) {

		long serviceComponentId = CounterLocalServiceUtil.increment();

		ServiceComponent serviceComponent =
			ServiceComponentLocalServiceUtil.createServiceComponent(
				serviceComponentId);

		serviceComponent.setBuildNamespace(buildNameSpace);
		serviceComponent.setBuildNumber(buildNumber);

		return ServiceComponentLocalServiceUtil.updateServiceComponent(
			serviceComponent);
	}

	protected ServiceComponent getServiceComponent(
		List<ServiceComponent> serviceComponents, String buildNamespace) {

		for (ServiceComponent serviceComponent : serviceComponents) {
			if (buildNamespace.equals(serviceComponent.getBuildNamespace())) {
				return serviceComponent;
			}
		}

		return null;
	}

	protected String normalizeTableName(
			DatabaseMetaData databaseMetaData, String tableName)
		throws SQLException {

		if (databaseMetaData.storesLowerCaseIdentifiers()) {
			return StringUtil.toLowerCase(tableName);
		}
		else if (databaseMetaData.storesUpperCaseIdentifiers()) {
			return StringUtil.toUpperCase(tableName);
		}

		return tableName;
	}

	private static final String _SERVICE_COMPONENT_1 = "SERVICE_COMPONENT_1";

	private static final String _SERVICE_COMPONENT_2 = "SERVICE_COMPONENT_2";

	private static final String _TEST_TABLE = "TestVerifyDB";

	@DeleteAfterTestRun
	private ServiceComponent _serviceComponent1;

	@DeleteAfterTestRun
	private ServiceComponent _serviceComponent2;

	private int _serviceComponentsCount;

}
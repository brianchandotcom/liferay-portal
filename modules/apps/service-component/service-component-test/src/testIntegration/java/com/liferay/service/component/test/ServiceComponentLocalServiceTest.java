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

package com.liferay.service.component.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBProcessContext;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.model.ServiceComponent;
import com.liferay.portal.kernel.service.ReleaseLocalService;
import com.liferay.portal.kernel.service.ServiceComponentLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Alberto Chaparro
 */
@RunWith(Arquillian.class)
public class ServiceComponentLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() {
		_serviceComponentsCount =
			_serviceComponentLocalService.getServiceComponentsCount();

		_serviceComponent1 = addServiceComponent(_SERVICE_COMPONENT_1, 1);
		_serviceComponent2 = addServiceComponent(_SERVICE_COMPONENT_2, 1);

		_release = _releaseLocalService.addRelease(
			"ServiceComponentLocalServiceTest", "0.0.0");
	}

	@Test
	public void testGetLatestServiceComponentsWithMultipleVersions()
		throws Exception {

		ServiceComponent serviceComponent = addServiceComponent(
			_SERVICE_COMPONENT_1, 2);

		List<ServiceComponent> serviceComponents =
			_serviceComponentLocalService.getLatestServiceComponents();

		Assert.assertEquals(
			2, serviceComponents.size() - _serviceComponentsCount);

		ServiceComponent latestServiceComponent = getServiceComponent(
			serviceComponents, _SERVICE_COMPONENT_1);

		Assert.assertEquals(2, latestServiceComponent.getBuildNumber());

		latestServiceComponent = getServiceComponent(
			serviceComponents, _SERVICE_COMPONENT_2);

		Assert.assertEquals(1, latestServiceComponent.getBuildNumber());

		_serviceComponentLocalService.deleteServiceComponent(serviceComponent);
	}

	@Test
	public void testGetLatestServiceComponentsWithSingleVersion()
		throws Exception {

		List<ServiceComponent> serviceComponents =
			_serviceComponentLocalService.getLatestServiceComponents();

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
	public void testVerifyFromSchemaVersion000WithInitialDatabaseCreation()
		throws Exception {

		Bundle bundle = FrameworkUtil.getBundle(
			ServiceComponentLocalServiceTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		final DB db = DBManagerUtil.getDB();

		ServiceRegistration<UpgradeStep> upgradeStepServiceRegistration =
			bundleContext.registerService(
				UpgradeStep.class, new TestUpgradeStep(db),
				new HashMapDictionary<String, Object>() {
					{
						put(
							"upgrade.bundle.symbolic.name",
							"ServiceComponentLocalServiceTest");
						put("upgrade.from.schema.version", "0.0.0");
						put("upgrade.initial.database.creation", true);
					}
				});

		String tableName = _TEST_TABLE;

		try {
			_serviceComponentLocalService.verifyDB();

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
	public void testVerifyFromSchemaVersion000WitouthInitialDatabaseCreation()
		throws Exception {

		Bundle bundle = FrameworkUtil.getBundle(
			ServiceComponentLocalServiceTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		final DB db = DBManagerUtil.getDB();

		ServiceRegistration<UpgradeStep> upgradeStepServiceRegistration =
			bundleContext.registerService(
				UpgradeStep.class, new TestUpgradeStep(db),
				new HashMapDictionary<String, Object>() {
					{
						put(
							"upgrade.bundle.symbolic.name",
							"ServiceComponentLocalServiceTest");
						put("upgrade.from.schema.version", "0.0.0");
						put("upgrade.initial.database.creation", false);
					}
				});

		try {
			_serviceComponentLocalService.verifyDB();

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
	public void testVerifyFromSchemaVersion001WithInitialDatabaseCreation()
		throws Exception {

		Bundle bundle = FrameworkUtil.getBundle(
			ServiceComponentLocalServiceTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		final DB db = DBManagerUtil.getDB();

		ServiceRegistration<UpgradeStep> upgradeStepServiceRegistration =
			bundleContext.registerService(
				UpgradeStep.class, new TestUpgradeStep(db),
				new HashMapDictionary<String, Object>() {
					{
						put(
							"upgrade.bundle.symbolic.name",
							"ServiceComponentLocalServiceTest");
						put("upgrade.from.schema.version", "0.0.1");
						put("upgrade.initial.database.creation", true);
					}
				});

		try {
			_serviceComponentLocalService.verifyDB();

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

	public class TestUpgradeStep implements UpgradeStep {

		public TestUpgradeStep(DB db) {
			_db = db;
		}

		@Override
		public String toString() {
			return "Test Upgrade Step";
		}

		@Override
		public void upgrade(DBProcessContext dbProcessContext)
			throws UpgradeException {

			try {
				_db.runSQL(
					"create table " + _TEST_TABLE + " (name VARCHAR(20))");
			}
			catch (Exception e) {
				throw new UpgradeException(e);
			}
		}

		private final DB _db;

	}

	protected ServiceComponent addServiceComponent(
		String buildNameSpace, long buildNumber) {

		long serviceComponentId = _counterLocalService.increment();

		ServiceComponent serviceComponent =
			_serviceComponentLocalService.createServiceComponent(
				serviceComponentId);

		serviceComponent.setBuildNamespace(buildNameSpace);
		serviceComponent.setBuildNumber(buildNumber);

		return _serviceComponentLocalService.updateServiceComponent(
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

	@Inject
	private CounterLocalService _counterLocalService;

	@DeleteAfterTestRun
	private Release _release;

	@Inject
	private ReleaseLocalService _releaseLocalService;

	@DeleteAfterTestRun
	private ServiceComponent _serviceComponent1;

	@DeleteAfterTestRun
	private ServiceComponent _serviceComponent2;

	@Inject
	private ServiceComponentLocalService _serviceComponentLocalService;

	private int _serviceComponentsCount;

}
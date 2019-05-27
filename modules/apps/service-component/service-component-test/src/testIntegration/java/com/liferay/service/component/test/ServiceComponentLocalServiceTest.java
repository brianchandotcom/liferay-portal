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

import java.util.ArrayList;
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
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() {
		_serviceComponentsCount =
			_serviceComponentLocalService.getServiceComponentsCount();

		_serviceComponents.add(_addServiceComponent(_SERVICE_COMPONENT_1, 1));
		_serviceComponents.add(_addServiceComponent(_SERVICE_COMPONENT_2, 1));

		_release = _releaseLocalService.addRelease(
			"ServiceComponentLocalServiceTest", "0.0.0");
	}

	@Test
	public void testGetLatestServiceComponentsWithMultipleVersions() {
		_serviceComponents.add(_addServiceComponent(_SERVICE_COMPONENT_1, 2));

		List<ServiceComponent> serviceComponents =
			_serviceComponentLocalService.getLatestServiceComponents();

		Assert.assertEquals(
			2, serviceComponents.size() - _serviceComponentsCount);

		ServiceComponent latestServiceComponent1 = _getServiceComponent(
			serviceComponents, _SERVICE_COMPONENT_1);

		Assert.assertEquals(2, latestServiceComponent1.getBuildNumber());

		ServiceComponent latestServiceComponent2 = _getServiceComponent(
			serviceComponents, _SERVICE_COMPONENT_2);

		Assert.assertEquals(1, latestServiceComponent2.getBuildNumber());
	}

	@Test
	public void testGetLatestServiceComponentsWithSingleVersion() {
		List<ServiceComponent> serviceComponents =
			_serviceComponentLocalService.getLatestServiceComponents();

		Assert.assertEquals(
			2, serviceComponents.size() - _serviceComponentsCount);

		ServiceComponent latestServiceComponent1 = _getServiceComponent(
			serviceComponents, _SERVICE_COMPONENT_1);

		Assert.assertEquals(1, latestServiceComponent1.getBuildNumber());

		ServiceComponent latestServiceComponent2 = _getServiceComponent(
			serviceComponents, _SERVICE_COMPONENT_2);

		Assert.assertEquals(1, latestServiceComponent2.getBuildNumber());
	}

	@Test
	public void testVerifyFromSchemaVersion000WithInitialDatabaseCreation()
		throws Exception {

		_testVerify(true, "0.0.0", true);
	}

	@Test
	public void testVerifyFromSchemaVersion000WitouthInitialDatabaseCreation()
		throws Exception {

		_testVerify(false, "0.0.0", false);
	}

	@Test
	public void testVerifyFromSchemaVersion001WithInitialDatabaseCreation()
		throws Exception {

		_testVerify(false, "0.0.1", true);
	}

	private ServiceComponent _addServiceComponent(
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

	private ServiceComponent _getServiceComponent(
		List<ServiceComponent> serviceComponents, String buildNamespace) {

		for (ServiceComponent serviceComponent : serviceComponents) {
			if (buildNamespace.equals(serviceComponent.getBuildNamespace())) {
				return serviceComponent;
			}
		}

		return null;
	}

	private String _normalizeTableName(
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

	private void _testVerify(
			boolean expected, String schemaVersion, boolean databaseCreation)
		throws Exception {

		Bundle bundle = FrameworkUtil.getBundle(
			ServiceComponentLocalServiceTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		DB db = DBManagerUtil.getDB();

		ServiceRegistration<UpgradeStep> upgradeStepServiceRegistration =
			bundleContext.registerService(
				UpgradeStep.class, new TestUpgradeStep(db),
				new HashMapDictionary<String, Object>() {
					{
						put(
							"upgrade.bundle.symbolic.name",
							"ServiceComponentLocalServiceTest");
						put("upgrade.from.schema.version", schemaVersion);
						put(
							"upgrade.initial.database.creation",
							databaseCreation);
					}
				});

		String tableName = _TEST_TABLE;

		try {
			_serviceComponentLocalService.verifyDB();

			try (Connection conn = DataAccess.getConnection()) {
				DatabaseMetaData metadata = conn.getMetaData();

				tableName = _normalizeTableName(metadata, _TEST_TABLE);

				try (ResultSet rs = metadata.getTables(
						null, null, tableName, new String[] {"TABLE"})) {

					Assert.assertEquals(expected, rs.next());
				}
			}
		}
		finally {
			if (expected) {
				db.runSQL("drop table " + tableName);
			}

			upgradeStepServiceRegistration.unregister();
		}
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

	@Inject
	private ServiceComponentLocalService _serviceComponentLocalService;

	@DeleteAfterTestRun
	private final List<ServiceComponent> _serviceComponents = new ArrayList<>();

	private int _serviceComponentsCount;

	private class TestUpgradeStep implements UpgradeStep {

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

}
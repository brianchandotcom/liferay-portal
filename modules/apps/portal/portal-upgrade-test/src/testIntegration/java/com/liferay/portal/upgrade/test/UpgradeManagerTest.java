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
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PropsValues;

import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.FrameworkUtil;
import org.osgi.service.component.runtime.ServiceComponentRuntime;
import org.osgi.util.promise.Promise;

/**
 * @author Alberto Chaparro
 */
@RunWith(Arquillian.class)
public class UpgradeManagerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() {
		_originalUpgradeDatabaseAutoRun = ReflectionTestUtil.getFieldValue(
			PropsValues.class, "UPGRADE_DATABASE_AUTO_RUN");
	}

	@AfterClass
	public static void tearDownClass() {
		ReflectionTestUtil.setFieldValue(
			PropsValues.class, "UPGRADE_DATABASE_AUTO_RUN",
			_originalUpgradeDatabaseAutoRun);
	}

	@Test
	public void testUpgradeManager() throws Exception {
		_restartComponentEnabler(true);

		String originalResult = ReflectionTestUtil.getFieldValue(
			_upgradeRecorder, "_result");
		String originalType = ReflectionTestUtil.getFieldValue(
			_upgradeRecorder, "_type");

		try {
			Assert.assertEquals(
				originalResult, _upgradeManagerInvoke("getResult"));
			Assert.assertEquals(originalType, _upgradeManagerInvoke("getType"));

			ReflectionTestUtil.setFieldValue(
				_upgradeRecorder, "_result", "testResult");
			ReflectionTestUtil.setFieldValue(
				_upgradeRecorder, "_type", "testType");

			Assert.assertEquals(
				"testResult", _upgradeManagerInvoke("getResult"));
			Assert.assertEquals("testType", _upgradeManagerInvoke("getType"));
		}
		finally {
			ReflectionTestUtil.setFieldValue(
				_upgradeRecorder, "_result", originalResult);
			ReflectionTestUtil.setFieldValue(
				_upgradeRecorder, "_type", originalType);
		}
	}

	@Test
	public void testUpgradeManagerMBeanDisabled() throws Exception {
		_restartComponentEnabler(false);

		Assert.assertFalse(_isUpgradeManagerMBeanRegistered());
	}

	@Test
	public void testUpgradeManagerMBeanEnabled() throws Exception {
		_restartComponentEnabler(true);

		Assert.assertTrue(_isUpgradeManagerMBeanRegistered());
	}

	private boolean _isUpgradeManagerMBeanRegistered() throws Exception {
		ObjectName objectName = new ObjectName(
			"com.liferay.portal.upgrade:classification=upgrade," +
				"name=UpgradeManager");

		for (int i = 0; i < 10; i++) {
			Thread.sleep(100);

			MBeanServer mBeanServer =
				ManagementFactory.getPlatformMBeanServer();

			if (mBeanServer.isRegistered(objectName)) {
				return true;
			}
		}

		return false;
	}

	private void _restartComponentEnabler(boolean upgradeDatabaseAutoRun)
		throws Exception {

		Promise<?> promise = _serviceComponentRuntime.disableComponent(
			_serviceComponentRuntime.getComponentDescriptionDTO(
				FrameworkUtil.getBundle(_upgradeRecorder.getClass()),
				"com.liferay.portal.upgrade.internal.component.enabler." +
					"ComponentEnabler"));

		promise.getValue();

		ReflectionTestUtil.setFieldValue(
			PropsValues.class, "UPGRADE_DATABASE_AUTO_RUN",
			upgradeDatabaseAutoRun);

		promise = _serviceComponentRuntime.enableComponent(
			_serviceComponentRuntime.getComponentDescriptionDTO(
				FrameworkUtil.getBundle(_upgradeRecorder.getClass()),
				"com.liferay.portal.upgrade.internal.component.enabler." +
					"ComponentEnabler"));

		promise.getValue();
	}

	private String _upgradeManagerInvoke(String methodName) {
		return ReflectionTestUtil.invoke(
			_upgradeManager, methodName, new Class<?>[0], null);
	}

	private static boolean _originalUpgradeDatabaseAutoRun;

	@Inject
	private ServiceComponentRuntime _serviceComponentRuntime;

	@Inject(
		blocking = false,
		filter = "component.name=com.liferay.portal.upgrade.internal.jmx.UpgradeManager",
		type = Inject.NoType.class
	)
	private Object _upgradeManager;

	@Inject(
		filter = "component.name=com.liferay.portal.upgrade.internal.recorder.UpgradeRecorder",
		type = Inject.NoType.class
	)
	private Object _upgradeRecorder;

}
/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.db.partition.internal.configuration.persistence.listener.test;

import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListener;
import com.liferay.portal.configuration.test.util.ConfigurationTestUtil;
import com.liferay.portal.db.partition.test.util.BaseDBPartitionTestCase;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.util.PropsValues;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.felix.cm.PersistenceManager;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Mariano Álvaro Sáiz
 */
public abstract class BaseConfigurationModelListenerTestCase
	extends BaseDBPartitionTestCase {

	@BeforeClass
	public static void setUpClass() throws Exception {
		enableDBPartition();
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		disableDBPartition();
	}

	@After
	public void tearDown() throws Exception {
		_deleteConfiguration();
	}

	protected abstract String getListenerName();

	protected void testConfigurationIsDeletedAfterDeploy(
			String pid, String content)
		throws Exception {

		_waitForConfigurationModelListenerEnabled();

		Assert.assertNull(
			_configurationAdmin.listConfigurations(
				"(service.pid=" + pid + ")"));

		_configurationPath = Paths.get(
			PropsValues.MODULE_FRAMEWORK_CONFIGS_DIR, pid.concat(".config"));

		try (AutoCloseable autoCloseable =
				_registerOnAfterDeleteConfigurationModelListener(pid)) {

			_createConfiguration(pid, content);

			Assert.assertNotNull(
				_configurationAdmin.listConfigurations(
					"(service.pid=" + pid + ")"));

			_countDownLatch.await(10, TimeUnit.SECONDS);

			Assert.assertFalse(Files.exists(_configurationPath));

			Assert.assertNull(
				_configurationAdmin.listConfigurations(
					"(service.pid=" + pid + ")"));

			Assert.assertNull(
				ReflectionTestUtil.invoke(
					_persistenceManager, "_getDictionary",
					new Class<?>[] {String.class}, pid));
		}
	}

	private Configuration _createConfiguration(
			String configurationPid, String content)
		throws Exception {

		return ConfigurationTestUtil.updateConfiguration(
			configurationPid,
			() -> Files.write(
				_configurationPath,
				content.getBytes(Charset.defaultCharset())));
	}

	private void _deleteConfiguration() throws Exception {
		if (_configurationPath != null) {
			Files.deleteIfExists(_configurationPath);
		}
	}

	private AutoCloseable _registerOnAfterDeleteConfigurationModelListener(
		String pid) {

		BundleContext bundleContext = SystemBundleUtil.getBundleContext();

		_countDownLatch = new CountDownLatch(1);

		ServiceRegistration<?> serviceRegistration =
			bundleContext.registerService(
				ConfigurationModelListener.class,
				new ConfigurationModelListener() {

					@Override
					public void onAfterDelete(String pid) {
						_countDownLatch.countDown();
					}

				},
				HashMapDictionaryBuilder.put(
					"model.class.name", pid
				).build());

		return serviceRegistration::unregister;
	}

	private void _waitForConfigurationModelListenerEnabled() throws Exception {
		ServiceTracker<?, ?> serviceTracker = ServiceTrackerFactory.open(
			SystemBundleUtil.getBundleContext(),
			"(component.name=*." + getListenerName() + ")");

		serviceTracker.waitForService(10000);

		serviceTracker.close();
	}

	@Inject
	private ConfigurationAdmin _configurationAdmin;

	private Path _configurationPath;
	private CountDownLatch _countDownLatch;

	@Inject
	private PersistenceManager _persistenceManager;

}
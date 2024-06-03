/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.db.schema.info.internal.test.helper;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListener;
import com.liferay.portal.configuration.test.util.ConfigurationTestUtil;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.util.PropsValues;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Dictionary;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.felix.cm.PersistenceManager;

import org.junit.Assert;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Mariano Álvaro Sáiz
 */
public class ConfigurationTestHelper {

	public ConfigurationTestHelper(
		ConfigurationAdmin configurationAdmin,
		PersistenceManager persistenceManager) {

		_configurationAdmin = configurationAdmin;
		_persistenceManager = persistenceManager;
	}

	public void deleteConfiguration() throws Exception {
		if (_configurationPath != null) {
			Files.deleteIfExists(_configurationPath);
		}
	}

	public void deployConfiguration(
			String pid, String databaseType, String dumpPath)
		throws Exception {

		Assert.assertNull(
			_configurationAdmin.listConfigurations(
				"(service.pid=" + pid + ")"));

		_configurationPath = Paths.get(
			PropsValues.MODULE_FRAMEWORK_CONFIGS_DIR, pid.concat(".config"));

		try (AutoCloseable autoCloseable =
				_registerOnAfterDeleteConfigurationModelListener(pid)) {

			_createConfiguration(
				pid, _getConfigurationContent(databaseType, dumpPath));

			Assert.assertNotNull(
				_configurationAdmin.listConfigurations(
					"(service.pid=" + pid + ")"));

			_countDownLatch.await(1000, TimeUnit.SECONDS);
		}
	}

	public boolean isConfigurationFileDeleted() {
		return !Files.exists(_configurationPath);
	}

	public boolean isDictionaryNull(String pid) {
		Dictionary<Object, Object> dictionary = ReflectionTestUtil.invoke(
			_persistenceManager, "_getDictionary",
			new Class<?>[] {String.class}, pid);

		if (dictionary == null) {
			return true;
		}

		return false;
	}

	public boolean isListConfigurationsNull(String pid) throws Exception {
		Configuration[] configurations = _configurationAdmin.listConfigurations(
			"(service.pid=" + pid + ")");

		if (configurations == null) {
			return true;
		}

		return false;
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

	private String _getConfigurationContent(
		String databaseType, String dumpPath) {

		return StringBundler.concat(
			"databaseType=\"", databaseType, "\"", StringPool.NEW_LINE,
			"path=\"", dumpPath, "\"", StringPool.NEW_LINE);
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

	private final ConfigurationAdmin _configurationAdmin;
	private Path _configurationPath;
	private CountDownLatch _countDownLatch;
	private final PersistenceManager _persistenceManager;

}
/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.db.schema.definition.internal.test.util;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListener;
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
public class ConfigurationTestUtil {

	public static void deployConfiguration(
			ConfigurationAdmin configurationAdmin, String pid,
			String databaseType, String path)
		throws Exception {

		Assert.assertNull(
			configurationAdmin.listConfigurations("(service.pid=" + pid + ")"));

		try (AutoCloseable autoCloseable =
				_registerOnAfterDeleteConfigurationModelListener(pid)) {

			_createConfiguration(
				pid, _getConfigurationContent(databaseType, path));

			Assert.assertNotNull(
				configurationAdmin.listConfigurations(
					"(service.pid=" + pid + ")"));

			_countDownLatch.await(1000, TimeUnit.SECONDS);
		}
	}

	public static Path getConfigurationPath(String pid) {
		return Paths.get(
			PropsValues.MODULE_FRAMEWORK_CONFIGS_DIR, pid.concat(".config"));
	}

	public static boolean isDictionaryNull(
		PersistenceManager persistenceManager, String pid) {

		Dictionary<Object, Object> dictionary = ReflectionTestUtil.invoke(
			persistenceManager, "_getDictionary", new Class<?>[] {String.class},
			pid);

		if (dictionary == null) {
			return true;
		}

		return false;
	}

	public static boolean isListConfigurationsNull(
			ConfigurationAdmin configurationAdmin, String pid)
		throws Exception {

		Configuration[] configurations = configurationAdmin.listConfigurations(
			"(service.pid=" + pid + ")");

		if (configurations == null) {
			return true;
		}

		return false;
	}

	private static Configuration _createConfiguration(
			String pid, String content)
		throws Exception {

		return com.liferay.portal.configuration.test.util.ConfigurationTestUtil.
			updateConfiguration(
				pid,
				() -> Files.write(
					getConfigurationPath(pid),
					content.getBytes(Charset.defaultCharset())));
	}

	private static String _getConfigurationContent(
		String databaseType, String dumpPath) {

		return StringBundler.concat(
			"databaseType=\"", databaseType, "\"", StringPool.NEW_LINE,
			"path=\"", dumpPath, "\"", StringPool.NEW_LINE);
	}

	private static AutoCloseable
		_registerOnAfterDeleteConfigurationModelListener(String pid) {

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

	private static CountDownLatch _countDownLatch;

}
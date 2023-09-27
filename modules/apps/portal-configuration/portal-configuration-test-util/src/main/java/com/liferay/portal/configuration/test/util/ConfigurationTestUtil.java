/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.configuration.test.util;

import com.liferay.osgi.util.configuration.ConfigurationFactoryUtil;
import com.liferay.osgi.util.service.OSGiServiceUtil;
import com.liferay.petra.function.UnsafeBiConsumer;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Dictionary;
import java.util.concurrent.CountDownLatch;

import org.junit.Assert;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ConfigurationListener;
import org.osgi.service.cm.ManagedService;
import org.osgi.service.cm.ManagedServiceFactory;

/**
 * @author Drew Brokke
 */
public class ConfigurationTestUtil {

	public static String createFactoryConfiguration(
			String factoryPid, Dictionary<String, Object> properties)
		throws Exception {

		Configuration configuration = _createFactoryConfiguration(factoryPid);

		_updateProperties(configuration, properties);

		return configuration.getPid();
	}

	public static void deleteConfiguration(Configuration configuration)
		throws Exception {

		_updateProperties(configuration, null);
	}

	public static void deleteConfiguration(String pid) throws Exception {
		_updateProperties(_getConfiguration(pid), null);
	}

	public static void deleteFactoryConfiguration(String pid, String factoryPid)
		throws Exception {

		Configuration configuration = _getFactoryConfiguration(pid, factoryPid);

		if (configuration != null) {
			_updateProperties(configuration, null);
		}
	}

	public static void saveConfiguration(
			Configuration configuration, Dictionary<String, Object> properties)
		throws Exception {

		_updateProperties(configuration, properties);
	}

	public static void saveConfiguration(
			String pid, Dictionary<String, Object> properties)
		throws Exception {

		_updateProperties(_getConfiguration(pid), properties);
	}

	public static Configuration updateConfiguration(
			String pid, UnsafeRunnable<Exception> unsafeRunnable)
		throws Exception {

		CountDownLatch countDownLatch = new CountDownLatch(2);

		ServiceRegistration<ManagedService> serviceRegistration =
			_bundleContext.registerService(
				ManagedService.class, props -> countDownLatch.countDown(),
				MapUtil.singletonDictionary(Constants.SERVICE_PID, pid));

		unsafeRunnable.run();

		try {
			countDownLatch.await();
		}
		finally {
			serviceRegistration.unregister();
		}

		Configuration[] configurations = OSGiServiceUtil.callService(
			_bundleContext, ConfigurationAdmin.class,
			configurationAdmin -> configurationAdmin.listConfigurations(
				StringBundler.concat(
					"(", Constants.SERVICE_PID, "=", pid, ")")));

		if ((configurations == null) || (configurations.length == 0)) {
			return null;
		}

		return configurations[0];
	}

	public static Configuration updateFactoryConfiguration(
			String pid, UnsafeRunnable<Exception> unsafeRunnable)
		throws Exception {

		String factoryPid = ConfigurationFactoryUtil.getFactoryPidFromPid(pid);

		Assert.assertNotNull(factoryPid);

		CountDownLatch countDownLatch = new CountDownLatch(1);

		ServiceRegistration<ManagedServiceFactory> serviceRegistration =
			_bundleContext.registerService(
				ManagedServiceFactory.class,
				new InternalManagerServiceFactory(
					factoryPid,
					(servicePid, props) -> countDownLatch.countDown()),
				MapUtil.singletonDictionary(Constants.SERVICE_PID, factoryPid));

		unsafeRunnable.run();

		try {
			countDownLatch.await();
		}
		finally {
			serviceRegistration.unregister();
		}

		Configuration[] configurations = OSGiServiceUtil.callService(
			_bundleContext, ConfigurationAdmin.class,
			configurationAdmin -> configurationAdmin.listConfigurations(
				StringBundler.concat(
					"(", Constants.SERVICE_PID, "=", pid, ")")));

		if ((configurations == null) || (configurations.length == 0)) {
			return null;
		}

		return configurations[0];
	}

	public static class InternalManagerServiceFactory
		implements ManagedServiceFactory {

		public InternalManagerServiceFactory(
			String factoryPid,
			UnsafeBiConsumer
				<String, Dictionary<String, ?>, ConfigurationException>
					unsafeBiConsumer) {

			_factoryPid = factoryPid;
			_unsafeBiConsumer = unsafeBiConsumer;
		}

		@Override
		public void deleted(String pid) {
		}

		@Override
		public String getName() {
			return _factoryPid;
		}

		@Override
		public void updated(String pid, Dictionary<String, ?> properties)
			throws ConfigurationException {

			_unsafeBiConsumer.accept(pid, properties);
		}

		private final String _factoryPid;
		private final UnsafeBiConsumer
			<String, Dictionary<String, ?>, ConfigurationException>
				_unsafeBiConsumer;

	}

	private static Configuration _createFactoryConfiguration(String factoryPid)
		throws Exception {

		return OSGiServiceUtil.callService(
			_bundleContext, ConfigurationAdmin.class,
			(ConfigurationAdmin configurationAdmin) ->
				configurationAdmin.createFactoryConfiguration(
					factoryPid, StringPool.QUESTION));
	}

	private static Configuration _getConfiguration(String pid)
		throws Exception {

		return OSGiServiceUtil.callService(
			_bundleContext, ConfigurationAdmin.class,
			(ConfigurationAdmin configurationAdmin) ->
				configurationAdmin.getConfiguration(pid, StringPool.QUESTION));
	}

	private static Configuration _getFactoryConfiguration(
			String pid, String factoryPid)
		throws Exception {

		String tempFilterString = "(service.pid=" + pid + ")";

		if (Validator.isNotNull(factoryPid)) {
			tempFilterString = StringBundler.concat(
				"(&", tempFilterString, "(service.factoryPid=", factoryPid,
				"))");
		}

		String filterString = tempFilterString;

		return OSGiServiceUtil.callService(
			_bundleContext, ConfigurationAdmin.class,
			(ConfigurationAdmin configurationAdmin) -> {
				Configuration[] configurations =
					configurationAdmin.listConfigurations(filterString);

				if (configurations != null) {
					return configurations[0];
				}

				return null;
			});
	}

	private static void _updateProperties(
			Configuration configuration, Dictionary<String, Object> dictionary)
		throws Exception {

		CountDownLatch eventCountDownLatch = new CountDownLatch(1);
		CountDownLatch updateCountDownLatch = new CountDownLatch(2);

		String markerPID = ConfigurationTestUtil.class.getName();

		ConfigurationListener configurationListener = configurationEvent -> {
			if (markerPID.equals(configurationEvent.getPid())) {
				eventCountDownLatch.countDown();
			}
		};

		ServiceRegistration<ConfigurationListener>
			configurationListenerServiceRegistration =
				_bundleContext.registerService(
					ConfigurationListener.class, configurationListener, null);

		ManagedService managedService = properties -> {
			try {
				eventCountDownLatch.await();
			}
			catch (InterruptedException interruptedException) {
				ReflectionUtil.throwException(interruptedException);
			}

			updateCountDownLatch.countDown();
		};

		ServiceRegistration<ManagedService> managedServiceServiceRegistration =
			_bundleContext.registerService(
				ManagedService.class, managedService,
				HashMapDictionaryBuilder.<String, Object>put(
					Constants.SERVICE_PID, markerPID
				).build());

		try {
			if (dictionary == null) {
				configuration.delete();
			}
			else {
				configuration.update(dictionary);
			}

			Configuration markerConfiguration = OSGiServiceUtil.callService(
				_bundleContext, ConfigurationAdmin.class,
				configurationAdmin -> configurationAdmin.getConfiguration(
					markerPID, StringPool.QUESTION));

			markerConfiguration.update();

			markerConfiguration.delete();

			updateCountDownLatch.await();
		}
		finally {
			configurationListenerServiceRegistration.unregister();

			managedServiceServiceRegistration.unregister();
		}
	}

	private static final BundleContext _bundleContext;

	static {
		Bundle bundle = FrameworkUtil.getBundle(ConfigurationTestUtil.class);

		_bundleContext = bundle.getBundleContext();
	}

}
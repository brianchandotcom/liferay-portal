/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.configuration.persistence.listener.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.osgi.util.service.OSGiServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListener;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListenerException;
import com.liferay.portal.configuration.test.util.ConfigurationTestUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.felix.cm.PersistenceManager;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Drew Brokke
 */
@RunWith(Arquillian.class)
public class ConfigurationModelListenerTest {

	@After
	public void tearDown() throws Exception {
		_serviceRegistrations.forEach(ServiceRegistration::unregister);

		if (_configuration != null) {
			Object delegatee = ReflectionTestUtil.getFieldValue(
				_configuration, "delegatee");

			ReflectionTestUtil.invoke(delegatee, "delete", new Class<?>[0]);
		}
	}

	@Test(expected = ConfigurationModelListenerException.class)
	public void testListenForScopedConfiguration() throws Exception {
		String pid = RandomTestUtil.randomString(20);

		_registerConfigurationModelListener(
			new ConfigurationModelListener() {

				@Override
				public void onBeforeSave(
						String pid, Dictionary<String, Object> properties)
					throws ConfigurationModelListenerException {

					throw new ConfigurationModelListenerException(
						new Exception(), Object.class, getClass(), properties);
				}

			},
			pid);

		_configuration = OSGiServiceUtil.callService(
			_bundleContext, ConfigurationAdmin.class,
			configurationAdmin -> {
				Configuration configuration =
					configurationAdmin.createFactoryConfiguration(
						pid + ".scoped");

				configuration.update(new HashMapDictionary<>());

				return configuration;
			});
	}

	@Test
	public void testOnAfterDelete() throws Exception {
		String pid = StringUtil.randomString(20);

		AtomicBoolean called = new AtomicBoolean();

		ConfigurationModelListener configurationModelListener =
			new ConfigurationModelListener() {

				@Override
				public void onAfterDelete(String pid) {
					called.set(true);
				}

			};

		_registerConfigurationModelListener(configurationModelListener, pid);

		_configuration = _getConfiguration(pid);

		_configuration.delete();

		Assert.assertTrue(called.get());
		Assert.assertFalse(_hasPid(pid));
	}

	@Test
	public void testOnAfterSave() throws Exception {
		String pid = StringUtil.randomString(20);

		Dictionary<String, Object> testProperties =
			HashMapDictionaryBuilder.<String, Object>put(
				_TEST_KEY, _TEST_VALUE
			).build();

		AtomicBoolean called = new AtomicBoolean();

		ConfigurationModelListener configurationModelListener =
			new ConfigurationModelListener() {

				@Override
				public void onAfterSave(
					String pid, Dictionary<String, Object> properties) {

					Assert.assertEquals(_TEST_VALUE, properties.get(_TEST_KEY));

					called.set(true);
				}

			};

		_registerConfigurationModelListener(configurationModelListener, pid);

		_configuration = _getConfiguration(pid);

		_configuration.update(testProperties);

		Assert.assertTrue(called.get());
	}

	@Test
	public void testOnBeforeDelete() throws Exception {
		String pid = StringUtil.randomString(20);

		ConfigurationModelListenerException
			configurationModelListenerException1 =
				new ConfigurationModelListenerException(
					"There was an issue", ConfigurationModelListenerTest.class,
					getClass(), new HashMapDictionary<>());

		ConfigurationModelListener configurationModelListener =
			new ConfigurationModelListener() {

				@Override
				public void onBeforeDelete(String pid)
					throws ConfigurationModelListenerException {

					throw configurationModelListenerException1;
				}

			};

		_registerConfigurationModelListener(configurationModelListener, pid);

		_configuration = _getConfiguration(pid);

		try {
			_configuration.delete();

			Assert.fail();
		}
		catch (ConfigurationModelListenerException
					configurationModelListenerException2) {

			Assert.assertSame(
				configurationModelListenerException1,
				configurationModelListenerException2);
			Assert.assertTrue(_hasPid(pid));
		}
	}

	@Test
	public void testOnBeforeSave() throws Exception {
		String pid = StringUtil.randomString(20);

		Dictionary<String, Object> testProperties =
			HashMapDictionaryBuilder.<String, Object>put(
				_TEST_KEY, _TEST_VALUE
			).build();

		_configuration = _getConfiguration(pid);

		_configuration.update(testProperties);

		String newValue = StringUtil.randomString(20);

		ConfigurationModelListenerException
			configurationModelListenerException1 =
				new ConfigurationModelListenerException(
					"There was an issue", ConfigurationModelListenerTest.class,
					getClass(), new HashMapDictionary<>());

		ConfigurationModelListener configurationModelListener =
			new ConfigurationModelListener() {

				@Override
				public void onBeforeSave(
						String pid, Dictionary<String, Object> properties)
					throws ConfigurationModelListenerException {

					Assert.assertEquals(newValue, properties.get(_TEST_KEY));

					throw configurationModelListenerException1;
				}

			};

		_registerConfigurationModelListener(configurationModelListener, pid);

		testProperties.put(_TEST_KEY, newValue);

		try {
			_configuration.update(testProperties);

			Assert.fail();
		}
		catch (ConfigurationModelListenerException
					configurationModelListenerException2) {

			Assert.assertSame(
				configurationModelListenerException1,
				configurationModelListenerException2);

			_configuration = _getConfiguration(pid);

			Dictionary<String, Object> properties =
				_configuration.getProperties();

			Assert.assertEquals(_TEST_VALUE, properties.get(_TEST_KEY));
		}
	}

	@Test
	public void testRegisterConfigurationModelListeners() throws Exception {
		int configurationModelListenersCount = 3;
		AtomicInteger methodInvocationsCount = new AtomicInteger();
		String pid = RandomTestUtil.randomString(20);

		for (int i = 0; i < configurationModelListenersCount; i++) {
			_registerConfigurationModelListener(
				new ConfigurationModelListener() {

					@Override
					public void onAfterDelete(String pid) {
						methodInvocationsCount.incrementAndGet();
					}

					@Override
					public void onAfterSave(
						String pid, Dictionary<String, Object> properties) {

						methodInvocationsCount.incrementAndGet();
					}

					@Override
					public void onBeforeDelete(String pid) {
						methodInvocationsCount.incrementAndGet();
					}

					@Override
					public void onBeforeSave(
						String pid, Dictionary<String, Object> properties) {

						methodInvocationsCount.incrementAndGet();
					}

				},
				pid);
		}

		ConfigurationTestUtil.saveConfiguration(pid, new HashMapDictionary<>());

		ConfigurationTestUtil.deleteConfiguration(pid);

		Assert.assertEquals(
			configurationModelListenersCount * 4, methodInvocationsCount.get());
	}

	private Configuration _getConfiguration(String pid) throws IOException {
		return OSGiServiceUtil.callService(
			_bundleContext, ConfigurationAdmin.class,
			configurationAdmin -> configurationAdmin.getConfiguration(
				pid, StringPool.QUESTION));
	}

	private boolean _hasPid(String pid) {
		return OSGiServiceUtil.callService(
			_bundleContext, PersistenceManager.class,
			persistenceManager -> persistenceManager.exists(pid));
	}

	private void _registerConfigurationModelListener(
		ConfigurationModelListener configurationModelListener, String pid) {

		_serviceRegistrations.add(
			_bundleContext.registerService(
				ConfigurationModelListener.class.getName(),
				configurationModelListener,
				HashMapDictionaryBuilder.put(
					"model.class.name", pid
				).build()));
	}

	private static final String _TEST_KEY = StringUtil.randomString(20);

	private static final String _TEST_VALUE = StringUtil.randomString(20);

	private static final BundleContext _bundleContext;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			ConfigurationModelListenerTest.class);

		if (bundle == null) {
			_bundleContext = null;
		}
		else {
			_bundleContext = bundle.getBundleContext();
		}
	}

	private Configuration _configuration;
	private final List<ServiceRegistration<?>> _serviceRegistrations =
		new ArrayList<>();

}
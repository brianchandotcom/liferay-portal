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

package com.liferay.portal.configuration.persistence.listener.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.osgi.util.service.OSGiServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListener;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListenerException;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;

import java.util.Dictionary;
import java.util.concurrent.atomic.AtomicBoolean;

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
		_serviceRegistration.unregister();

		Object delegatee = ReflectionTestUtil.getFieldValue(
			_configuration, "delegatee");

		ReflectionTestUtil.invoke(delegatee, "delete", new Class<?>[0]);
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

		_serviceRegistration = _registerConfigurationModelListener(
			configurationModelListener, pid);

		_configuration = _getConfiguration(pid);

		_configuration.delete();

		Assert.assertTrue(called.get());
		Assert.assertFalse(_hasPid(pid));
	}

	@Test
	public void testOnAfterSave() throws Exception {
		String pid = StringUtil.randomString(20);

		Dictionary<String, Object> testProperties = new HashMapDictionary<>();

		testProperties.put(_TEST_KEY, _TEST_VALUE);

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

		_serviceRegistration = _registerConfigurationModelListener(
			configurationModelListener, pid);

		_configuration = _getConfiguration(pid);

		_configuration.update(testProperties);

		Assert.assertTrue(called.get());
	}

	@Test
	public void testOnBeforeDelete() throws Exception {
		String pid = StringUtil.randomString(20);

		ConfigurationModelListenerException cmle1 =
			new ConfigurationModelListenerException(
				"There was an issue", ConfigurationModelListenerTest.class,
				getClass(), new HashMapDictionary<>());

		ConfigurationModelListener configurationModelListener =
			new ConfigurationModelListener() {

				@Override
				public void onBeforeDelete(String pid)
					throws ConfigurationModelListenerException {

					throw cmle1;
				}

			};

		_serviceRegistration = _registerConfigurationModelListener(
			configurationModelListener, pid);

		_configuration = _getConfiguration(pid);

		try {
			_configuration.delete();

			Assert.fail();
		}
		catch (ConfigurationModelListenerException cmle) {
			Assert.assertSame(cmle1, cmle);
			Assert.assertTrue(_hasPid(pid));
		}
	}

	@Test
	public void testOnBeforeSave() throws Exception {
		String pid = StringUtil.randomString(20);

		Dictionary<String, Object> testProperties = new HashMapDictionary<>();

		testProperties.put(_TEST_KEY, _TEST_VALUE);

		_configuration = _getConfiguration(pid);

		_configuration.update(testProperties);

		String newValue = StringUtil.randomString(20);

		ConfigurationModelListenerException cmle1 =
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

					throw cmle1;
				}

			};

		_serviceRegistration = _registerConfigurationModelListener(
			configurationModelListener, pid);

		testProperties.put(_TEST_KEY, newValue);

		try {
			_configuration.update(testProperties);

			Assert.fail();
		}
		catch (ConfigurationModelListenerException cmle) {
			Assert.assertSame(cmle1, cmle);

			_configuration = _getConfiguration(pid);

			Dictionary<String, Object> properties =
				_configuration.getProperties();

			Assert.assertEquals(_TEST_VALUE, properties.get(_TEST_KEY));
		}
	}

	private static Configuration _getConfiguration(String pid)
		throws IOException {

		return OSGiServiceUtil.callService(
			_bundleContext, ConfigurationAdmin.class,
			configurationAdmin -> configurationAdmin.getConfiguration(
				pid, StringPool.QUESTION));
	}

	private static boolean _hasPid(String pid) {
		return OSGiServiceUtil.callService(
			_bundleContext, PersistenceManager.class,
			persistenceManager -> persistenceManager.exists(pid));
	}

	private ServiceRegistration<?> _registerConfigurationModelListener(
		ConfigurationModelListener configurationModelListener, String pid) {

		Dictionary<String, String> properties = new HashMapDictionary<>();

		properties.put("model.class.name", pid);

		return _bundleContext.registerService(
			ConfigurationModelListener.class.getName(),
			configurationModelListener, properties);
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
	private ServiceRegistration<?> _serviceRegistration;

}
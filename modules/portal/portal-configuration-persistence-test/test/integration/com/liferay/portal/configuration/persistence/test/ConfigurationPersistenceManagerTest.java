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

package com.liferay.portal.configuration.persistence.test;

import com.liferay.portal.configuration.persistence.ConfigurationPersistenceManager;
import com.liferay.portal.kernel.test.rule.Sync;

import java.io.IOException;

import java.util.Dictionary;
import java.util.Hashtable;

import org.apache.felix.cm.PersistenceManager;

import org.jboss.arquillian.junit.Arquillian;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Raymond Augé
 */
@RunWith(Arquillian.class)
@Sync
public class ConfigurationPersistenceManagerTest {

	@Before
	public void setUp() throws Exception {
		Bundle bundle = FrameworkUtil.getBundle(
			ConfigurationPersistenceManagerTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		_configAdminTracker = new ServiceTracker<>(
			bundleContext, ConfigurationAdmin.class, null);
		_persistenceManagerTracker = new ServiceTracker<>(
			bundleContext, PersistenceManager.class, null);

		_configAdminTracker.open();
		_persistenceManagerTracker.open();

		_configurationAdmin = _configAdminTracker.waitForService(5000);
		_persistenceManager = _persistenceManagerTracker.waitForService(5000);
	}

	@After
	public void tearDown() throws Exception {
		_configAdminTracker.close();
		_persistenceManagerTracker.close();
	}

	@Test
	public void testCreateAndDeleteConfiguration() throws Exception {
		String testPid = "test.pid";

		Configuration configuration = _configurationAdmin.getConfiguration(
			testPid);

		Assert.assertTrue(_persistenceManager.exists(testPid));

		configuration.delete();

		Assert.assertFalse(_persistenceManager.exists(testPid));
	}

	@Test
	public void testPortalPersistenceManager() throws Exception {
		Assert.assertEquals(
			ConfigurationPersistenceManager.class,
			_persistenceManager.getClass());
	}

	@Test
	public void testSetConfigurationProperties() throws Exception {
		Configuration configuration = _configurationAdmin.getConfiguration(
			"test.pid");

		doConfigurationChecks(configuration);
	}

	@Test
	public void testSetFactoryConfigurationProperties() throws Exception {
		Configuration configuration =
			_configurationAdmin.createFactoryConfiguration("test.pid");

		doConfigurationChecks(configuration);
	}

	@SuppressWarnings("unchecked")
	protected void doConfigurationChecks(Configuration configuration)
		throws IOException {

		String pid = configuration.getPid();

		Dictionary<String, Object> properties = new Hashtable<>();

		properties.put("foo", "bar");

		configuration.update(properties);

		Assert.assertTrue(_persistenceManager.exists(pid));

		properties = _persistenceManager.load(pid);

		Assert.assertEquals("bar", properties.get("foo"));

		properties.put("fee", "fum");

		configuration.update(properties);

		properties = _persistenceManager.load(pid);

		Assert.assertEquals("bar", properties.get("foo"));
		Assert.assertEquals("fum", properties.get("fee"));

		configuration.delete();

		Assert.assertFalse(_persistenceManager.exists(pid));
	}

	private ServiceTracker<ConfigurationAdmin, ConfigurationAdmin>
		_configAdminTracker;
	private ConfigurationAdmin _configurationAdmin;
	private PersistenceManager _persistenceManager;
	private ServiceTracker<PersistenceManager, PersistenceManager>
		_persistenceManagerTracker;

}
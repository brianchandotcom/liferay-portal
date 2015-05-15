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
import org.osgi.framework.ServiceReference;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

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

		_bundleContext = bundle.getBundleContext();

		_configurationAdminReference = _bundleContext.getServiceReference(
			ConfigurationAdmin.class);

		_configurationAdmin = _bundleContext.getService(
			_configurationAdminReference);

		_persisenceManagerReference = _bundleContext.getServiceReference(
			PersistenceManager.class);

		_persistenceManager = _bundleContext.getService(
			_persisenceManagerReference);
	}

	@After
	public void tearDown() throws Exception {
		_bundleContext.ungetService(_configurationAdminReference);
		_bundleContext.ungetService(_persisenceManagerReference);
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

	private BundleContext _bundleContext;
	private ConfigurationAdmin _configurationAdmin;
	private ServiceReference<ConfigurationAdmin> _configurationAdminReference;
	private ServiceReference<PersistenceManager> _persisenceManagerReference;
	private PersistenceManager _persistenceManager;

}